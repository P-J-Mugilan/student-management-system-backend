[1mdiff --git a/LICENSE b/LICENSE[m
[1mindex e69de29..bd17fea 100644[m
[1m--- a/LICENSE[m
[1m+++ b/LICENSE[m
[36m@@ -0,0 +1,9 @@[m
[32m+[m[32mMIT License[m
[32m+[m
[32m+[m[32mCopyright (c) 2025 P J Mugilan (https://github.com/P-J-Mugilan)[m
[32m+[m
[32m+[m[32mPermission is given to anyone to use, copy, modify, merge, publish, and distribute this software for free.[m
[32m+[m[32mYou can also sell copies or use it for commercial purposes.[m
[32m+[m
[32m+[m[32mThis software is provided "as is", without any warranty.[m
[32m+[m[32mThe author is not responsible for any damages or issues that may happen when using it.[m
[1mdiff --git a/pom.xml b/pom.xml[m
[1mindex 72e06d0..dfead8c 100644[m
[1m--- a/pom.xml[m
[1m+++ b/pom.xml[m
[36m@@ -3,12 +3,22 @@[m
 		 xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"[m
 		 xsi:schemaLocation="http://maven.apache.org/POM/4.0.0[m
          http://maven.apache.org/xsd/maven-4.0.0.xsd">[m
[32m+[m
[32m+[m	[32m<licenses>[m
[32m+[m		[32m<license>[m
[32m+[m			[32m<name>MIT License</name>[m
[32m+[m			[32m<url>https://opensource.org/licenses/MIT</url>[m
[32m+[m			[32m<distribution>repo</distribution>[m
[32m+[m		[32m</license>[m
[32m+[m	[32m</licenses>[m
[32m+[m
[32m+[m
 	<modelVersion>4.0.0</modelVersion>[m
 [m
 	<parent>[m
 		<groupId>org.springframework.boot</groupId>[m
 		<artifactId>spring-boot-starter-parent</artifactId>[m
[31m-		<version>3.5.7</version>[m
[32m+[m		[32m<version>3.2.0</version>[m
 		<relativePath/>[m
 	</parent>[m
 [m
[36m@@ -18,7 +28,7 @@[m
 	<name>student-management</name>[m
 [m
 	<properties>[m
[31m-		<java.version>17</java.version>[m
[32m+[m		[32m<java.version>24</java.version>[m
 	</properties>[m
 [m
 	<dependencies>[m
[36m@@ -32,11 +42,6 @@[m
 			<artifactId>spring-boot-starter-data-jpa</artifactId>[m
 		</dependency>[m
 [m
[31m-		<dependency>[m
[31m-			<groupId>org.springdoc</groupId>[m
[31m-			<artifactId>springdoc-openapi-starter-webmvc-ui</artifactId>[m
[31m-			<version>2.3.0</version>[m
[31m-		</dependency>[m
 		<dependency>[m
 			<groupId>org.springframework.boot</groupId>[m
 			<artifactId>spring-boot-starter-security</artifactId>[m
[36m@@ -53,9 +58,15 @@[m
 		</dependency>[m
 [m
 		<dependency>[m
[31m-			<groupId>com.mysql</groupId>[m
[31m-			<artifactId>mysql-connector-j</artifactId>[m
[31m-			<scope>runtime</scope>[m
[32m+[m			[32m<groupId>org.springdoc</groupId>[m
[32m+[m			[32m<artifactId>springdoc-openapi-starter-webmvc-ui</artifactId>[m
[32m+[m			[32m<version>2.5.0</version>[m
[32m+[m		[32m</dependency>[m
[32m+[m
[32m+[m		[32m<dependency>[m
[32m+[m			[32m<groupId>mysql</groupId>[m
[32m+[m			[32m<artifactId>mysql-connector-java</artifactId>[m
[32m+[m			[32m<version>8.0.33</version>[m
 		</dependency>[m
 [m
 		<dependency>[m
[36m@@ -77,6 +88,19 @@[m
 			<version>0.11.5</version>[m
 			<scope>runtime</scope>[m
 		</dependency>[m
[32m+[m
[32m+[m		[32m<!-- Proper Spring Boot testing starter -->[m
[32m+[m		[32m<dependency>[m
[32m+[m			[32m<groupId>org.springframework.boot</groupId>[m
[32m+[m			[32m<artifactId>spring-boot-starter-test</artifactId>[m
[32m+[m			[32m<scope>test</scope>[m
[32m+[m		[32m</dependency>[m
[32m+[m		[32m<dependency>[m
[32m+[m			[32m<groupId>org.testng</groupId>[m
[32m+[m			[32m<artifactId>testng</artifactId>[m
[32m+[m			[32m<version>RELEASE</version>[m
[32m+[m			[32m<scope>test</scope>[m
[32m+[m		[32m</dependency>[m
 	</dependencies>[m
 [m
 	<build>[m
[36m@@ -87,4 +111,5 @@[m
 			</plugin>[m
 		</plugins>[m
 	</build>[m
[31m-</project>[m
\ No newline at end of file[m
[32m+[m
[32m+[m[32m</project>[m
[1mdiff --git a/src/main/java/com/example/studentmanagement/config/SecurityConfig.java b/src/main/java/com/example/studentmanagement/config/SecurityConfig.java[m
[1mindex e3cf9fe..554490d 100644[m
[1m--- a/src/main/java/com/example/studentmanagement/config/SecurityConfig.java[m
[1m+++ b/src/main/java/com/example/studentmanagement/config/SecurityConfig.java[m
[36m@@ -12,7 +12,6 @@[m [mimport org.springframework.security.config.http.SessionCreationPolicy;[m
 import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;[m
 import org.springframework.security.crypto.password.PasswordEncoder;[m
 import org.springframework.security.web.SecurityFilterChain;[m
[31m-import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;[m
 [m
 @Configuration[m
 public class SecurityConfig {[m
[36m@@ -50,17 +49,39 @@[m [mpublic class SecurityConfig {[m
                 .cors(cors -> {})[m
                 .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))[m
                 .authorizeHttpRequests(auth -> auth[m
[31m-                        .requestMatchers("/api/auth/**").permitAll()[m
[31m-                        .requestMatchers("/api/debug/**").permitAll()[m
[31m-                        .requestMatchers("/api/students/public/**").permitAll()[m
[32m+[m[32m                        // Allow ALL Swagger and OpenAPI endpoints[m
[32m+[m[32m                        .requestMatchers([m
[32m+[m[32m                                "/swagger-ui.html",[m
[32m+[m[32m                                "/swagger-ui/**",[m
[32m+[m[32m                                "/v3/api-docs/**",[m
[32m+[m[32m                                "/api-docs/**",[m
[32m+[m[32m                                "/webjars/**",[m
[32m+[m[32m                                "/swagger-resources/**",[m
[32m+[m[32m                                "/swagger-resources",[m
[32m+[m[32m                                "/configuration/ui",[m
[32m+[m[32m                                "/configuration/security",[m
[32m+[m[32m                                "/v3/api-docs",[m
[32m+[m[32m                                "/api-docs",[m
[32m+[m[32m                                "/api/auth/login",[m
[32m+[m[32m                                "/api/debug/**",[m
[32m+[m[32m                                "/api/students/public/**"[m
[32m+[m[32m                        ).permitAll()[m
[32m+[m
[32m+[m[32m                        // Admin endpoints[m
                         .requestMatchers("/api/users/**").hasRole("ADMIN")[m
                         .requestMatchers("/api/branches/**").hasRole("ADMIN")[m
[32m+[m
[32m+[m[32m                        // Professor and Admin endpoints[m
                         .requestMatchers("/api/students/**").hasAnyRole("PROFESSOR", "ADMIN")[m
[31m-                        .requestMatchers("/api/users/me").authenticated() // Changed from permitAll to authenticated[m
[32m+[m
[32m+[m[32m                        // Authenticated endpoints[m
[32m+[m[32m                        .requestMatchers("/api/auth/me").authenticated()[m
[32m+[m[32m                        .requestMatchers("/api/auth/logout").authenticated()[m
[32m+[m
                         .anyRequest().authenticated()[m
                 )[m
                 .authenticationProvider(authenticationProvider())[m
[31m-                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);[m
[32m+[m[32m                .addFilterBefore(jwtAuthenticationFilter, org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter.class);[m
 [m
         return http.build();[m
     }[m
[1mdiff --git a/src/main/java/com/example/studentmanagement/controller/AuthController.java b/src/main/java/com/example/studentmanagement/controller/AuthController.java[m
[1mindex e48ca00..5d45d9c 100644[m
[1m--- a/src/main/java/com/example/studentmanagement/controller/AuthController.java[m
[1m+++ b/src/main/java/com/example/studentmanagement/controller/AuthController.java[m
[36m@@ -20,6 +20,7 @@[m [mimport org.springframework.web.bind.annotation.*;[m
 @RestController[m
 @RequestMapping("/api/auth")[m
 @Tag(name = "1. Authentication", description = "User authentication and authorization endpoints")[m
[32m+[m[32m@CrossOrigin(origins = "*")[m
 public class AuthController {[m
 [m
     private final AuthService authService;[m
[1mdiff --git a/src/main/java/com/example/studentmanagement/controller/BranchController.java b/src/main/java/com/example/studentmanagement/controller/BranchController.java[m
[1mindex 8254e21..ca5174b 100644[m
[1m--- a/src/main/java/com/example/studentmanagement/controller/BranchController.java[m
[1m+++ b/src/main/java/com/example/studentmanagement/controller/BranchController.java[m
[36m@@ -23,6 +23,7 @@[m [mimport java.util.List;[m
 @RestController[m
 @RequestMapping("/api/branches")[m
 @Tag(name = "2. Branch Management", description = "Academic branch management endpoints (Admin access only)")[m
[32m+[m[32m@CrossOrigin(origins = "*")[m
 public class BranchController {[m
 [m
     private final BranchService branchService;[m
[1mdiff --git a/src/main/java/com/example/studentmanagement/controller/StudentController.java b/src/main/java/com/example/studentmanagement/controller/StudentController.java[m
[1mindex e97ed84..7ee2152 100644[m
[1m--- a/src/main/java/com/example/studentmanagement/controller/StudentController.java[m
[1m+++ b/src/main/java/com/example/studentmanagement/controller/StudentController.java[m
[36m@@ -102,6 +102,7 @@[m [mpublic class StudentController {[m
      */[m
     @GetMapping("/branch/{branchId}")[m
     @Operation(summary = "Get Students by Branch", description = "Retrieve all students belonging to a specific branch")[m
[32m+[m[32m    @CrossOrigin(origins = "*")[m
     public ResponseEntity<ApiResponse<List<Student>>> getStudentsByBranch([m
             @PathVariable Long branchId) {[m
         List<Student> students = studentService.getStudentsByBranch(branchId);[m
[1mdiff --git a/src/main/java/com/example/studentmanagement/controller/UserController.java b/src/main/java/com/example/studentmanagement/controller/UserController.java[m
[1mindex e6df9ab..ebbb96c 100644[m
[1m--- a/src/main/java/com/example/studentmanagement/controller/UserController.java[m
[1m+++ b/src/main/java/com/example/studentmanagement/controller/UserController.java[m
[36m@@ -23,6 +23,7 @@[m [mimport java.util.List;[m
 @RestController[m
 @RequestMapping("/api/users")[m
 @Tag(name = "3. User Management", description = "System user management endpoints (Admin access only)")[m
[32m+[m[32m@CrossOrigin(origins = "*")[m
 public class UserController {[m
 [m
     private final UserService userService;[m
[1mdiff --git a/src/main/java/com/example/studentmanagement/security/JwtAuthenticationFilter.java b/src/main/java/com/example/studentmanagement/security/JwtAuthenticationFilter.java[m
[1mindex 4812f07..c486810 100644[m
[1m--- a/src/main/java/com/example/studentmanagement/security/JwtAuthenticationFilter.java[m
[1m+++ b/src/main/java/com/example/studentmanagement/security/JwtAuthenticationFilter.java[m
[36m@@ -32,8 +32,13 @@[m [mpublic class JwtAuthenticationFilter extends OncePerRequestFilter {[m
 [m
         final String requestURI = request.getRequestURI();[m
 [m
[31m-        // Skip JWT filter for login and public endpoints[m
[31m-        if (requestURI.startsWith("/api/auth/login") ||[m
[32m+[m[32m        // Skip JWT filter for Swagger, login, and public endpoints[m
[32m+[m[32m        if (requestURI.startsWith("/swagger-ui") ||[m
[32m+[m[32m                requestURI.startsWith("/v3/api-docs") ||[m
[32m+[m[32m                requestURI.startsWith("/api-docs") ||[m
[32m+[m[32m                requestURI.startsWith("/webjars") ||[m
[32m+[m[32m                requestURI.startsWith("/swagger-resources") ||[m
[32m+[m[32m                requestURI.startsWith("/api/auth/login") ||[m
                 requestURI.startsWith("/api/debug/") ||[m
                 requestURI.startsWith("/api/students/public/")) {[m
             chain.doFilter(request, response);[m
[36m@@ -50,7 +55,7 @@[m [mpublic class JwtAuthenticationFilter extends OncePerRequestFilter {[m
 [m
             // Check if token is blacklisted[m
             if (tokenBlacklist.isBlacklisted(jwt)) {[m
[31m-                System.out.println("🚫 Blocked blacklisted token for: " + requestURI);[m
[32m+[m[32m                System.out.println("Blocked blacklisted token for: " + requestURI);[m
                 response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);[m
                 response.setContentType("application/json");[m
                 response.getWriter().write("{\"success\": false, \"message\": \"Token has been invalidated. Please login again.\"}");[m
[36m@@ -60,7 +65,7 @@[m [mpublic class JwtAuthenticationFilter extends OncePerRequestFilter {[m
             try {[m
                 username = jwtUtil.extractUsername(jwt);[m
             } catch (Exception e) {[m
[31m-                System.out.println("❌ Invalid JWT token: " + e.getMessage());[m
[32m+[m[32m                System.out.println("Invalid JWT token: " + e.getMessage());[m
             }[m
         }[m
 [m
[36m@@ -73,10 +78,10 @@[m [mpublic class JwtAuthenticationFilter extends OncePerRequestFilter {[m
                             new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());[m
                     authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));[m
                     SecurityContextHolder.getContext().setAuthentication(authentication);[m
[31m-                    System.out.println("✅ Authenticated user: " + username);[m
[32m+[m[32m                    System.out.println("Authenticated user: " + username);[m
                 }[m
             } catch (Exception e) {[m
[31m-                System.out.println("❌ Authentication failed for user: " + username);[m
[32m+[m[32m                System.out.println("Authentication failed for user: " + username);[m
             }[m
         }[m
         chain.doFilter(request, response);[m
[1mdiff --git a/src/main/resources/application.properties b/src/main/resources/application.properties[m
[1mindex 347bfc3..fe8360b 100644[m
[1m--- a/src/main/resources/application.properties[m
[1m+++ b/src/main/resources/application.properties[m
[36m@@ -31,8 +31,37 @@[m [mspring.mail.properties.mail.smtp.ssl.trust=smtp.gmail.com[m
 # Disable Spring Security default login page[m
 spring.security.user.password=disabled[m
 [m
[31m-# OpenAPI Documentation[m
[31m-springdoc.api-docs.path=/api-docs[m
[32m+[m[32m# ========== FIXED SPRINGDOC CONFIGURATION ==========[m
[32m+[m[32m# Use default paths that match what SpringDoc expects[m
[32m+[m[32mspringdoc.api-docs.path=/v3/api-docs[m
 springdoc.swagger-ui.path=/swagger-ui.html[m
 springdoc.swagger-ui.enabled=true[m
[31m-springdoc.api-docs.enabled=true[m
\ No newline at end of file[m
[32m+[m[32mspringdoc.api-docs.enabled=true[m
[32m+[m
[32m+[m[32m# Optional: Customize Swagger UI[m
[32m+[m[32mspringdoc.swagger-ui.operations-sorter=method[m
[32m+[m[32mspringdoc.swagger-ui.tags-sorter=alpha[m
[32m+[m[32mspringdoc.swagger-ui.display-request-duration=true[m
[32m+[m[32mspringdoc.swagger-ui.doc-expansion=none[m
[32m+[m
[32m+[m[32m# Show API docs without authentication[m
[32m+[m[32mspringdoc.swagger-ui.oauth.client-id=test-client[m
[32m+[m[32mspringdoc.swagger-ui.oauth.scopes=openid,profile,email[m
[32m+[m
[32m+[m[32m# ========== LOGGING CONFIGURATION ==========[m
[32m+[m[32m# Enable detailed error messages (disable in production)[m
[32m+[m[32mserver.error.include-stacktrace=on_param[m
[32m+[m[32mserver.error.include-message=always[m
[32m+[m[32mserver.error.include-exception=false[m
[32m+[m
[32m+[m[32m# SpringDoc debugging[m
[32m+[m[32mlogging.level.org.springdoc=INFO[m
[32m+[m[32mlogging.level.org.springframework.web=DEBUG[m
[32m+[m
[32m+[m[32m# Application logging[m
[32m+[m[32mlogging.level.com.example.studentmanagement=DEBUG[m
[32m+[m[32mlogging.pattern.console=%d{yyyy-MM-dd HH:mm:ss} - %logger{36} - %msg%n[m
[32m+[m
[32m+[m[32m# Reduce Hibernate logging noise[m
[32m+[m[32mlogging.level.org.hibernate.SQL=DEBUG[m
[32m+[m[32mlogging.level.org.hibernate.type.descriptor.sql.BasicBinder=TRACE[m
\ No newline at end of file[m
