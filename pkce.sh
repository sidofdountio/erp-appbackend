#!/bin/bash

# 1. Generate a random 64-character Code Verifier
VERIFIER=$(openssl rand -base64 64 | tr -d '+/=' | cut -c1-64)

# 2. Generate the Code Challenge (SHA256 -> Base64URL encode)
CHALLENGE=$(echo -n "$VERIFIER" | openssl dgst -sha256 -binary | openssl base64 | tr -d '=' | tr '+/' '-_')

echo "------------------------------------------------------------"
echo "CODE_VERIFIER:  $VERIFIER"
echo "CODE_CHALLENGE: $CHALLENGE"
echo "METHOD:         S256"
echo "------------------------------------------------------------"