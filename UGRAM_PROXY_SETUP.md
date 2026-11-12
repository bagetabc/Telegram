# UGRAM local build configuration

UGRAM can preselect its default MTProto proxy at application startup. Keep the proxy secret out of Git.

Add these lines to your local, untracked `local.properties` file in the repository root:

```properties
UGRAM_PROXY_HOST=pl5.h1cloud.net
UGRAM_PROXY_PORT=25729
UGRAM_PROXY_SECRET=PASTE_THE_FULL_SECRET_HERE
```

Use the complete secret from your `tg://proxy` link, including its Fake TLS suffix. Do not commit `local.properties` or paste the secret into a public workflow file.

If these properties are empty or absent, the app builds without forcing a proxy. The app's normal proxy screen remains available.
