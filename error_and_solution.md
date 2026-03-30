Master Technical Troubleshooting Ledger

Project: Auth-Service & Shop-Service (OAuth2 Architecture)



Solving the "Multiple CORS Headers" Error
1. The Problem (Root Cause)

The browser's Same-Origin Policy allows only one Access-Control-Allow-Origin header per request.

    The Conflict: The API Gateway (9009) was adding a CORS header to satisfy the browser.

    The Conflict: The Auth Service (9000) was also adding a CORS header because of its internal SecurityFilterChain configuration.

    The Result: The browser received two headers, saw a protocol violation, and blocked the request with a Status 0 / Unknown Error.

2. The Solution: Centralized CORS Management

The fix requires a two-step "Mirror" configuration.
Step A: Gateway as the "Gatekeeper"

Configure the Gateway to be the only service that talks to the browser. Update application.yml:

    Add globalcors to define allowed origins (Port 3001).

    Add DedupeResponseHeader with the RETAIN_UNIQUE strategy. This ensures that if any duplicate headers slip through, the Gateway merges them into one before the browser sees them.

Step B: Microservice "Muzzle"

Disable CORS on all downstream microservices (Auth, Shop, Product, etc.). This prevents them from injecting their own headers into the response that the Gateway is already handling.

In Java (SecurityConfig.java):
Java

@Bean
public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
http
// CRITICAL: Disable CORS here so it doesn't conflict with the Gateway
.cors(AbstractHttpConfigurer::disable)
.csrf(AbstractHttpConfigurer::disable)
// ... rest of config
}
