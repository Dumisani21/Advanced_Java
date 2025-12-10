# Contributing to Advanced_Java

Thank you for contributing! This file explains how to set up a local dev environment, the commit and PR workflow, and testing expectations.

How to contribute
1. Fork the repository (if you do not have push rights), then create a new branch:
   git checkout -b feature/my-feature
2. Implement changes and add tests where appropriate.
3. Run tests locally:
   cd server
   ./mvnw test
4. Commit with a clear message and push your branch:
   git push origin feature/my-feature
5. Open a pull request describing the change and why it is needed.

Branching and PRs
- Use feature branches for new work.
- Keep PRs small and focused.
- Link to any related issues in the PR description.
- CI (Jenkinsfile) will run on PRs; ensure your changes pass CI.

Commit message guidelines
- Use present tense and be concise.
- Use a short subject line (< 72 characters) and a longer description if needed.

Code style
- Java: use a consistent formatter (e.g., google-java-format or your project's standard). Document and enforce via CI if desired.
- Include unit tests for new features and ensure existing tests pass.

Security & secrets
- Do not commit secrets or credentials.
- Use environment variables or secret management in CI.

Support
- If you need help, open an issue or mention a maintainer in the PR.
