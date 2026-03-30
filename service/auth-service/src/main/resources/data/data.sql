

SELECT 1 + 1;
select * from oauth2_registered_client;

-- Delete all registered clients
DELETE FROM oauth2_registered_client;

-- If you are using authorization persistence, clear these too
DELETE FROM oauth2_authorization;
DELETE FROM oauth2_authorization_consent;