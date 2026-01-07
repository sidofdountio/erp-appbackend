
---

# Technical Deep-Dive: Spring Authorization Server & JDBC Persistence

## Part 1: Core Concepts (The "What" and "Why")

### The OAuth2 & OIDC Landscape

In our architecture, we implemented **OAuth2** for authorization and **OpenID Connect (OIDC)** for identity.

**The Four Roles:**

1. **Resource Owner:** The User (e.g., `admin21`).
2. **Client:** The application requesting access (e.g., your Angular frontend).
3. **Authorization Server:** The gatekeeper (your Spring Boot app).
4. **Resource Server:** The API protecting the data (your backend services).

**The Authorization Code Flow with PKCE:**
This is the most secure flow for modern web apps. It involves a two-step handshake:

1. Redirecting the user to log in and receiving an **Authorization Code**.
2. Exchanging that code via a back-channel for an **Access Token**, **ID Token**, and **Refresh Token**.

---

## Part 2: Implementation Pillars

### I. Registered Client Repository

We configured our clients (like `backend-client`) in the database. This defines:

* **Redirect URIs:** Prevents token hijacking by only sending codes to "trusted" URLs.
* **Scopes:** Defines the "depth" of access (e.g., `read`, `write`, `SHOP_READ`).

### II. Token Customization (`OAuth2TokenCustomizer`)

Standard JWTs are generic. We customized ours to be "Self-Contained," meaning the token carries all the info the Resource Server needs to make decisions without hitting the database:

* `user_uuid`, `full_name`, `mfa_status`, and a flattened string of `authorities`.

---

## Part 3: The Technical Challenge (The "Problem" and "Solution")

### The Problem: Deserialization Failure

When switching from In-Memory to **JDBC Persistence**, the server failed to deserialize our custom `User` entity.

**Root Cause:** Spring Security's strict **Allowlist**. It refuses to recreate classes it doesn't recognize to prevent security attacks. Furthermore, Hibernate's **Lazy-Loading Proxies** (`PersistentSet`) are blocked by default.

### The Solution: The "Mixin" Strategy

We implemented **Jackson Mixins** to act as "Security Passports" for our domain model.

1. **Serialization Mixins:** We created abstract Mixin classes for `User`, `Role`, and `Permission` using `@JsonTypeInfo(use = JsonTypeInfo.Id.CLASS)`. This tells Jackson to store the class name in the JSON so it can be rebuilt safely.
2. **Hibernate Module:** We registered the `Hibernate6Module` to handle database-specific collection types.
3. **Collection Detachment:** In the `CustomUserDetailsService`, we manually "cleaned" the user object by converting Hibernate `PersistentSet` into standard Java `HashSet` before serialization.

### The ObjectMapper Configuration

The final fix involved injecting a customized `ObjectMapper` into the `RowMapper` of the `JdbcOAuth2AuthorizationService`.

```java
private ObjectMapper createObjectMapperWithUserSupport() {
    ObjectMapper mapper = new ObjectMapper();
    // Register security modules
    mapper.registerModules(SecurityJackson2Modules.getModules(classLoader));
    mapper.registerModule(new OAuth2AuthorizationServerJackson2Module());
    // Register Hibernate support
    mapper.registerModule(new Hibernate6Module());
    // Register Mixins (The Allowlist)
    mapper.addMixIn(User.class, UserMixin.class);
    mapper.addMixIn(Role.class, RoleMixin.class);
    mapper.addMixIn(org.hibernate.collection.spi.PersistentSet.class, HibernateCollectionMixin.class);
    return mapper;
}

```

---

## Part 4: Interview Summary (The "Elevator Pitch")

"I implemented a production-ready Authorization Server using Spring Boot and JDBC. The main challenge was managing the serialization of a complex, nested User entity (User -> Role -> Permissions). I solved this by leveraging **Jackson Mixins** to satisfy Spring Security’s strict deserialization allowlist and by integrating the **Hibernate Jackson Module** to handle lazy-loaded collections. This architecture allows the system to be horizontally scalable and resilient to server restarts."

---