# Client — Advanced_Java/client

Status
- The client/ directory is currently a placeholder. Add your frontend or client-side application code here (React, Angular, Vue, or plain HTML/JS).

Suggested structure
- client/
  - README.md
  - package.json (if using Node)
  - src/
  - public/ or static/
  - Dockerfile (optional)

Suggested quick start (example for a Node-based client)
1. Install Node.js (LTS)
2. From client/:
   npm install
   npm run start
3. Build for production:
   npm run build

Integration with server
- The client should call server APIs at the configured server base URL. During development, use environment variables or proxy configuration to avoid CORS issues.

What to document when adding the client
- Framework and version used
- Local dev commands (install, start, build)
- Environment variables and how to set the backend API URL
- Authentication flow (if any)
- How to produce a production build and where to deploy the static assets
