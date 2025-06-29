<!DOCTYPE html>
<html lang="en">

<body>

  <h1>🔐 Spring Security All-in-One Auth System</h1>
  <h2>🔐 Spring Security Full Authentication System</h2>
  <p>
    This Spring Boot project demonstrates how to implement a comprehensive authentication system using:
  </p>
  <ul>
    <li>✅ JWT (JSON Web Token) for API security</li>
    <li>✅ Form Login for traditional web-based authentication</li>
    <li>✅ Basic Auth for testing/debugging</li>
    <li>✅ OAuth2 Login (e.g., Google) for social login</li>
  </ul>
  <p>
    Built using <strong>Spring Boot + Spring Security</strong>, this backend supports secure user registration, login, token-based access, and role-based authorization.
  </p>

  <section class="section">
    <h2>🔄 Authentication Flow (JWT)</h2>
    <pre>
1️⃣  User sends login request to <code>/auth/login</code> with username &amp; password
      |
      ↓
2️⃣  AuthController receives request
      |
      ↓
3️⃣  AuthController authenticates user using <code>AuthenticationManager</code>
      |
      ↓
4️⃣  <code>CustomUserDetailsService</code> loads user from database
      |
      ↓
5️⃣  If valid, AuthController calls <code>JwtUtil</code> to generate JWT
      |
      ↓
6️⃣  JWT token is returned to the client
      |
      ↓
7️⃣  Client stores JWT (in localStorage or cookies)
      |
      ↓
8️⃣  Client sends JWT in Authorization header for protected routes
      |
      ↓
9️⃣  <code>JwtAuthenticationFilter</code> intercepts request
      |
      ↓
🔟  <code>JwtUtil</code> validates token (checks signature, expiration)
      |
      ↓
1️⃣1️⃣ If valid, create <code>Authentication</code> object with user info
      |
      ↓
1️⃣2️⃣ Set authentication into <code>SecurityContextHolder</code>
      |
      ↓
1️⃣3️⃣ Request is authenticated — controller processes the request
    </pre>
  </section>

  <section class="section">
    <h2>🌐 OAuth2 Login Flow (e.g., Google)</h2>
    <pre>
1️⃣  User clicks "Login with Google"
      |
      ↓
2️⃣  Spring Security redirects to Google OAuth2 provider
      |
      ↓
3️⃣  After successful login, user is redirected to <code>/oauth-success</code>
      |
      ↓
4️⃣  The system checks if the user exists → if not, saves to DB
      |
      ↓
5️⃣  JWT is generated and returned with success message
    </pre>
  </section>

  <section class="section">
    <h2>🔑 Supported Endpoints</h2>
    <table>
      <thead>
        <tr>
          <th>Method</th>
          <th>Endpoint</th>
          <th>Description</th>
          <th>Auth Required</th>
        </tr>
      </thead>
      <tbody>
        <tr>
          <td>POST</td>
          <td><code>/auth/login</code></td>
          <td>Login with username &amp; password</td>
          <td>❌ No</td>
        </tr>
        <tr>
          <td>POST</td>
          <td><code>/user/register</code></td>
          <td>User registration</td>
          <td>❌ No</td>
        </tr>
        <tr>
          <td>GET</td>
          <td><code>/user/all</code></td>
          <td>Get all users</td>
          <td>✅ JWT</td>
        </tr>
        <tr>
          <td>GET</td>
          <td><code>/auth/oauth-success</code></td>
          <td>OAuth2 login success response</td>
          <td>✅ OAuth2</td>
        </tr>
        <tr>
          <td>GET</td>
          <td><code>/basic-success</code></td>
          <td>Success page for basic login</td>
          <td>✅ Basic Auth</td>
        </tr>
      </tbody>
    </table>
  </section>

  <section class="section">
    <h2>🧪 Tech Stack</h2>
    <ul>
      <li>Spring Boot 3+</li>
      <li>Spring Security</li>
      <li>JWT (with <code>io.jsonwebtoken</code>)</li>
      <li>Spring OAuth2 Client</li>
      <li>H2 Database</li>
      <li>Lombok</li>
      <li>Postman for testing</li>
    </ul>
  </section>

  <section class="section">
    <h2>🚀 How to Run</h2>
    <pre>
# Clone the repo
git clone https://github.com/k-al/spring-security-auth-system.git

# Go to project folder
cd spring-security-auth-system

# Run the app
./mvnw spring-boot:run
    </pre>
    <p>The app will start at:<br/>📍 <a href="http://localhost:9090">http://localhost:9090</a></p>
  </section>

  <section class="section">
    <h2>📁 Directory Structure</h2>
    <pre>
src/
├── controller/
├── jwt/
├── model/
├── repository/
├── security/
├── services/
├── utils/
└── resources/
    </pre>
  </section>

  <section class="section">
    <h2>🧑‍💻 Author</h2>
    <p><strong>Kalyan Katwal</strong><br />
    Passionate Java Developer | Spring Boot Enthusiast<br />
    🔗 <a href="https://www.linkedin.com/in/kalyan-katwal">LinkedIn</a> | 💻 #OpenToWork</p>
  </section>

 ]

</body>
</html>
