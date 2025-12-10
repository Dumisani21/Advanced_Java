# Advanced_Java

A Java-based project containing a server and a placeholder client. This repository includes a CI pipeline (Jenkinsfile) and a Maven-based Java server.

Repository layout
- Jenkinsfile            — CI pipeline configuration (at repo root)
- client/                — frontend or client-side code (currently placeholder)
- server/                — Maven Java server application
- docs/                  — architecture and design documentation
- CONTRIBUTING.md        — contribution guidelines

Quick start (developer)
1. Clone the repository:
   git clone https://github.com/Dumisani21/Advanced_Java.git
2. Enter the server directory:
   cd Advanced_Java/server
3. Build the server (uses the included Maven wrapper):
   - Unix / macOS: ./mvnw clean package
   - Windows: mvnw.cmd clean package
4. Run tests:
   ./mvnw test
5. Run (if packaged as an executable jar):
   java -jar target/<artifactId>-<version>.jar

Notes
- The server uses the Maven wrapper so a local Maven installation is not required.
- Check server/pom.xml to confirm Java version, artifactId, and any additional build properties — if you want, I can populate exact values from pom.xml in these docs.

What I added in docs
- Root README with quick start instructions
- server/README.md with server-specific instructions and common tasks
- client/README.md with suggested client structure and examples
- CONTRIBUTING.md with workflow, style, and CI guidance
- docs/ARCHITECTURE.md with a high-level architecture overview

Next steps
- Approve these files and push them to branch add-docs and open a PR into main.
- If you want me to extract exact values (Java version, artifactId, etc.) from server/pom.xml and fill them into the docs before pushing, say “fetch and fill” and I'll update the docs with those specifics.
