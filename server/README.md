# Server — Advanced_Java/server

This directory contains the Java server application. It is a Maven project and includes the Maven wrapper.

Contents
- .gitattributes
- .gitignore
- .mvn/
- mvnw, mvnw.cmd — Maven wrapper
- pom.xml — Maven project descriptor
- src/ — application source and tests

Prerequisites
- JDK 11+ (confirm exact version in server/pom.xml)
- No global Maven installation required if you use the Maven wrapper (mvnw / mvnw.cmd)

Common tasks

Build
- Unix / macOS:
  ./mvnw clean package
- Windows:
  mvnw.cmd clean package

Run (local development)
- If this is a Spring Boot app:
  ./mvnw spring-boot:run
- If a runnable jar is produced:
  java -jar target/<artifactId>-<version>.jar

Run tests
- ./mvnw test

Debugging
- Run from your IDE (IntelliJ IDEA, Eclipse). Import as Maven project.
- Use breakpoints and run configurations from the IDE for fast iteration.

Configuration and environment variables
- Check src/main/resources (e.g., application.properties or application.yml) for configuration keys.
- Do not commit secrets or credentials. Use environment variables or a secret manager for production.
- Document any required env vars below (I can fill these in after scanning the code):
  - DATABASE_URL
  - DATABASE_USER
  - DATABASE_PASSWORD
  - SERVER_PORT

Logging
- Logging configuration is typically in src/main/resources or controlled by the framework.
- For production, ensure logs are shipped/aggregated (ELK, CloudWatch, etc.).

Packaging & deployment
- Maven package phase produces an artifact under target/.
- For containerized deployment, add a Dockerfile at the repository root or in server/ and use CI to build/push images.

Troubleshooting
- If build fails, run with -e or check mvn debug output:
  ./mvnw -e clean package
- If you need me to add specific troubleshooting tips based on actual error messages, supply the failing logs or allow me to inspect CI runs.

Open items (to be filled after scanning pom.xml and source)
- Java target version
- Artifact coordinates (groupId/artifactId/version)
- Main class (if applicable)
- Exposed server port and API base path
