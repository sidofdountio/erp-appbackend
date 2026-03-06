Here is the **FULL login test workflow from terminal until you get the access token** for your `shop-app` (PKCE, no client secret).

We assume:

```
Authorization Server → http://localhost:9000
Client ID → shop-app
Redirect URI → http://localhost:3000/callback
```

Your client config:

```java
.clientAuthenticationMethod(ClientAuthenticationMethod.NONE)
.requireProofKey(true)
.authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE)
```

So:

* ❌ No client_secret
* ✅ PKCE required

---

# 🔐 COMPLETE LOGIN FLOW (Terminal + Browser)

---

# 🟢 STEP 1 — Generate PKCE (Terminal)

### 1️⃣ Generate code_verifier

```bash
CODE_VERIFIER=$(openssl rand -base64 64 | tr -d "=+/" | cut -c1-64)

echo "CODE_VERIFIER:"
echo $CODE_VERIFIER
```

---

### 2️⃣ Generate code_challenge

```bash
CODE_CHALLENGE=$(echo -n $CODE_VERIFIER | \
openssl dgst -sha256 -binary | \
openssl base64 | \
tr '+/' '-_' | tr -d '=')

echo "CODE_CHALLENGE:"
echo $CODE_CHALLENGE
```

Now you have:

```
CODE_VERIFIER=xxxxx
CODE_CHALLENGE=yyyyy
```

---

# 🟢 STEP 2 — Call Authorization Endpoint (Browser)

Open this URL in your browser (replace challenge):

```
http://localhost:9000/oauth2/authorize?response_type=code&client_id=shop&redirect_uri=http://localhost:3000/callback&scope=openid%20SHOP_READ%20SHOP_CREATE%20PRODUCT_CREATE%20%20PRODUCT_READ%20ORDER_CREATE&code_challenge=et7LaEDjeocaADDqW04pgTCF4Kg2WlqGPEA7w6Uo2N0&code_challenge_method=S256
```

```
http://localhost:9000/oauth2/authorize?response_type=code&client_id=shop&redirect_uri=http://localhost:3000/callback&scope=openid%20SHOP_READ%20SHOP_CREATE%20PRODUCT_CREATE%20PRODUCT_READ%20ORDER_CREATE&code_challenge=o_IVCcvvYSpRZFlIgQ59FyrXxj6TolZfAX3ocDmTFjs&code_challenge_method=S256

```

👉 Important:

* Replace `YOUR_CODE_CHALLENGE`
* Keep `%20` between scopes

---

# 🟢 STEP 3 — Login

1. Spring login page appears
2. Enter username & password
3. After login you are redirected to:

```
http://localhost:3000/callback?code=abc123XYZ
```

Copy the `code` value.

---

# 🟢 STEP 4 — Exchange Code for Token (Terminal)

Now run:

```bash
curl -X POST http://localhost:9000/oauth2/token \
  -H "Content-Type: application/x-www-form-urlencoded" \
  -d "grant_type=authorization_code" \
  -d "client_id=shop-app" \
  -d "code=PASTE_CODE_HERE" \
  -d "redirect_uri=http://localhost:3000/callback" \
  -d "code_verifier=$CODE_VERIFIER"
```

---

# 🟢 SUCCESS RESPONSE

You should receive:

```json
{
  "access_token": "eyJraWQiOiJmZjI...",
  "refresh_token": "def50200...",
  "scope": "SHOP_READ PRODUCT_READ",
  "token_type": "Bearer",
  "expires_in": 3600
}
```

🎉 You now have your access token.

---

# 🟢 STEP 5 — Test Protected API

Example:

```bash
curl http://localhost:8081/api/products \
  -H "Authorization: Bearer eyJraWQiOiJmZjI..."
```

If scope is correct → 200 OK
If missing scope → 403 Forbidden

---

# 🔍 Optional — Decode JWT

Copy access_token and run:

```bash
echo "ACCESS_TOKEN" | cut -d "." -f2 | base64 -d 2>/dev/null | jq
```

You’ll see:

```json
{
  "sub": "user",
  "scope": ["SHOP_READ", "PRODUCT_READ"],
  "iss": "http://localhost:9000",
  "exp": 1710000000
}
```

---

# 🧠 What Happened Internally

1. You sent `code_challenge`
2. Server stored it with authorization code
3. You sent `code_verifier`
4. Server did:

```
SHA256(code_verifier)
```

5. Compared with original challenge
6. If match → issued token

That’s PKCE protection.

---

# 🚀 One Command Script Version (Fully Automated)

If you want everything in one block:

```bash
# Generate PKCE
CODE_VERIFIER=$(openssl rand -base64 64 | tr -d "=+/" | cut -c1-64)
CODE_CHALLENGE=$(echo -n $CODE_VERIFIER | openssl dgst -sha256 -binary | openssl base64 | tr '+/' '-_' | tr -d '=')

echo "Open this URL in browser:"
echo "http://localhost:9000/oauth2/authorize?response_type=code&client_id=shop-app&redirect_uri=http://localhost:3000/callback&scope=SHOP_READ%20PRODUCT_READ&code_challenge=$CODE_CHALLENGE&code_challenge_method=S256"

echo ""
echo "After login, paste the code:"
read AUTH_CODE

curl -X POST http://localhost:9000/oauth2/token \
  -H "Content-Type: application/x-www-form-urlencoded" \
  -d "grant_type=authorization_code" \
  -d "client_id=shop-app" \
  -d "code=$AUTH_CODE" \
  -d "redirect_uri=http://localhost:3000/callback" \
  -d "code_verifier=$CODE_VERIFIER"
```


