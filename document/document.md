# Spring Security gihub repo   https://github.com/eazybytes/springsecurity6
Provide 2 ways store login details.
- UserDetail interface (user class) -> return UserDetail - have all user infomration as expired time.
- Principal interface (UsernamePasswordAuthenticationToken class) -> return Authentication(UserPasswordAuthenticationToken) where we try to determine the authenication is success or not inside AuthenticationProvider and AuthenticationManager.
just store the authenication data which are username, password, ... to determine authentication is success or not => just care about authenication then use Authentication object is better, no unused data.


### `User detail and User Detail manager`
- User details provides user core information
- User detail manager manage the user includes create, update, delete, changePassword, userExist
  Spring security provides InMemoryUserDetailsManager, JdbcUserDetailManager, LdapUserDetailManager
- User detail service - provides the detail actions for user detail manager.
# 1. `Different ways of Pws management`

### a. `Encoding`
- Converting the data from one form to others and has nothing to do with cryptography
- Involving no secret and completely reversible
- Encoding can't be used for securing data

Some public algorithms used for encoding: ASCII, BASE64, UNICODE, UTF8

### b. `Encryption`
- Transforming data in such a way that guarantees confidentiality
- Requiring the use of a secret which in cryptographic terms, called `key`
- Can be reversible by using decryption with the help of `key`

As long as the `key` is confidential, encryption can be considered as secured

### c. `Hashing`
- Data is converted to hash value using some hashing function
- Data once hashed is non-reversible, one can't determine the original data from hash value generated
- Given some arbitrary data along with the output of a hashing algorithm, one can verify whether this data matches the original
input data without needing to see the original data.
  


### The hashing is best way (recommendation)
## `why endcode with SHA-256 isn't secured` ?

## 2. `PasswordEncoder`
### a. `BCryptPasswordEncoder`
- It is going to use BCrypt hashing algorithm which is invented in 1999, It demand CPU so hard to hacker can
get the original pain text because cost of CPU to execute milion cases.
  
### b. `ScryptPasswordEncoder`
Advanced version of BCryptPassEncoder because It demands 2 parameters with first one parameter is computer power (CPU), second parameter is memory => you can assume the cost for hacker to get pain text by trying many the password cases.

### c. `Argon2PasswordEncoder`
Demand 3 parameter with 2 firsts are same mean with BCrypt and SCrypt, last one parameter is multiple thread
### SCrypt and Argon2 we expect lot of resource even register and login for your web application so more common is BCrypt


## 3. `Considering Authentication Provider`
 It is called by Authentication Manager.
 As you know about default authentication provider which is DaoAuthenticationProvider 
 It provides the loading, checking, hashing along with supporting from PasswordEncode. But in real world we have many the complex requiements where we want to authenticate a end user 
 
 **Example**  For access this service, we just allow the people who have age above 18 years. It is a logic need to be consider in our authenciation process so we need to create our Authentication Provider to 
 execute our specific logic.

 ProviderManager which is implemented of AuthenticationManager check with all implementation of Authentication Providers and try to authen the user - One of provider is passed -> it authen successfully

 **Example** Our application apply 2 requirements for authentication which one is login by username and password and another one is login with OAuth2.0 so we need 2 different AuthenticationProviders.


### AuthenticationProvider
 - authenticate method : received and return Authentication object. we can implement all our custom authentication logic inside this method.
 - supports method: true id current AP supports the type of Authentication object provided. 

## 4. `CORS & CSRF`
#### a. `CORS - Cross-Origin Resource Sharing`
- Enables scripts running on browser client to interact with resource from a different origin.
- By default browser will block this communication due CORS
So CORS is not a security issue/attrack but the default protection provided by browsers to stop sharing the data bw different origin

Different origin is match one of this cases
- Different schema (HTTP or HTTPs)
- Different domain
- Different port

#### b. `CSRF` 
It isn't allow post and put operation create or update data in DB, by default the SpringSecurity stops them. Response status = 403.

Situation - User can be steal all information in webside cookie, credential or sessionID. TO know diffentiate bw legitimate user and hacker we introduce the CSRF token

Follow for implement CSRF
When user login in UI -> UI recived csrf token from BackEnd then store that in session -> in all other requests they will use this csrf token in
session to attach in request -> Server reviced csrf and do the compare

- In the case our stateless API use token based authentication such as JWT, we don't need CSRF protection, we must disable it.
- Attracker just teal the cookie easier because it's store in our browser but JWT token is store some where in our code where they can't found out and we often refresh the JwtToken.

 ## 7. Authentication and Authorization
- Authentication: providing the access to the system and done before authorization. It need user's login detail, error code response 401
- Authorization: Check for access the resource, happens after Authentication, need privilege or roles, error code response 403. so that decide what kind of actions I can do

#### a. Authorities detail in spring security
GrantedAuthority inside UserDetails interface stores authorities in form of collection

A user have multiple authorities, A authority can assign for multiple users.

#### b. Authority and Role
`Authority`
- Like an individual privilege (dac quyen ca nhan) or an individual action
- Restricting access in a fine-grained(y la o muc detail) manner
- It can complex for enterprise application because thousands of APIs. Can't config detail for each controller, API -> role is properly
Example: VIEWACCOUNT, VIEWCARDS, READ_ONLY, WRITE, etc

`Role`
- Group of privileges/actions
- Restricting access in a coarse-grain( y la o muc high level) manner
- Ex: ADMIN, USER, PUBLISHER

`Some points:`
- The name of authorities/role are arbitrary in nature and these names can be customized as per business requirement
- Roles are also represented using same contract GrantedAuthority in SpringSecurity
- When define the name, the role name should start with ROLE_ prefix. That prefix specifies the difference between a role and an authority
- SpringSecurity provides the hasAuthority, hasAnyAuthorites, access, hasRole, hasAnyRole, access methods to check
- SpringSecurity provides prefix automatically - so you just need to send name = ADMIN -> SpringS will make it become ROLE_ADMIN to map with ROLE_ADMIN value in DB which is stored in UserDetails
- Role and Authorities is same one just some small different things. so you can store both in a table `Authority` in DB

## 8. Servlet and Filters
`Servlet`
WebServer (Tomcat, OpenLiberty, ...) translate HTTP messages for Java code to understand by converting the HTTP messages into ServletRequest and hand over to Servlet method as parameter. ServletResponse as the output to WebServer from Servlet.

`Role of Filter`
- Intercepting each request/response and do some pre-work before our business logic
- SpringSecurity is based on a chain of servlet filters. Each filter has specific responsibility and depending on the config, filters are added or removed - we can add our custom filters.

`How can we inject our custom filter`
They provide the method addFilterBefore(), addFilterAfter, addFilterAt

## 9. JWT

### a. JSESSIONID and issues with it
- It stored in cookie of browser
- When call to BE that cookie will be automaticaly appended by your browser

`With JSESSIONID works great extent for smaller applications, but in enterprise will not be sufficient because 2 reasons`
- This token doesn't hold any user data that just is a ramdomly generated value so it is't giving any ability to store some user data inside that token.
- It saved as a cookie inside your browser and this cookie will be tied to user session, if end user isn't closing his browser and ss still is valie -> good chance someone can misuse this kind of tokens with just store inside the browser as cookies

### b. Role of Tokens in Authn and Authz
- Token can be plain string of fotmat UUID or it can be of type JWT ussalyly
- Client login -> recived token from server response -> interact with server by this token  -> server valid and do the jobs.

`ADVANTAGES`
- Token helps us to share the credentials for every request, it's a security risk to send creasentials over the network frequently
- Token can be invalidated during any suspicious activities without invalidating the user credentials
- Tokens can be created with a short life span
- Tokens can be used to store the user related information like roles/authorities
- Reusablity - we can have many separate server, running on mulple platform and domain, reusing the same toejn for thenticating the user.
- Stateless, easier to scale. token contains all the infor to identify user. elimimating the need for session state (store something in server ). If we use load balancer, we can pass the user to any server, instead of being bound to same server we logged in on
- We already used token in previous sections in form of CSRF and JSEESIONID tokens

### c. JWT
- JSON Web Token with JSON format and designed to use for the web requests. It is used for both Authen and Autho
`Advantages`
- Store and share the user-related inside the token itself
   - Header : infor about token as alg, typ
   - Payload: Detail releated to user such as sub, name, iat
   - Singature (Optional): Server digitally sign that token so in future, if someone trying to tamper the token, server can detect that faster and easier by singature


Internaly, we can use firewall to protect our application, all app in internal can connect each others, so don't need the singature, don't need to checking the security just checking for some user information. But about externaly, someone can change the JWT token about role and send the token to server, we need to check so we can check Singature = CHMACSHA256(base64UrlEncode(header) + "." + base64UrlEncode(payload), your_secret). There are no one can change your JWT because they can't fake the Singature due they don't know your secret.


#### c.1 `JWT implementation in Spring Security`
- Import libs to help generate JWT token. io.jsonwebtoken group with artifactId jjwt-id, jjwt-impl, jjwt-jackson.
- Tell the SpringSecurity don't generate SESSIONID
- Config to exposeHeader authorization to allow  BE send the authentication token to UI
- Generate the JWT token in a filter which for login process only, then set that token to response header of login-process, to order running that filter, we addFilterAfter BasicAuthenticationFilter
- Valid the JWT which is received from UI - Use signature to valid is good performance. Better than request DB anytime to valid - `TODO`: check more in source code

`Note` Need to check the Issuer : Person, orgnazation who is issuing JWT token.

## 10. Method Level Security
We have applied authorization rules on the API paths using SpringSecurity. Method Level security allows to apply the authorization tiles at any layer of an application like service layer or repo layer. Ennable by @EnnablMethodSecurity in application class.

-> It can support authz rules even in the non-web application where we won't have nay endpoints.

`Prodvides`
- Invocation authorization - validates if someone can invoke a method or not based on their roles/authorities.
- Filtering authoz ...
- Anotation @PreAuthorize(run before go in the method)/@PostAuthorize(run after method return value) , hasRole - hasAuthority - hasAnyRole
- Can use @PreFilter to filter the input params object which will not accepted in that methods - filter for collections => so the method run without that params which match filter condition.
- @PostFilter : Run the method first -> then do the filter -> if your logic is insert-update. it will persist to DB before filtering.

`To do method level sercurity they use Spring AOP - that is best practice`

Some demo description


## `11. OAUTH2`
Situation: Eveythings such as business logic, security logic, authentication, authorization are clubbed in single application - It can work for single application but if you have multiple applications and all of them need security => We gotta separate security logic into a separate commponent 

`Need a common component for authentication and authorization`

`OAUTH2`
Common Security to without sharing credentials bw many services, without duplicate logic and all the change in one place but applied for all the other services

### 11.1 OAuth
OAuth stands for Open Authorization, it's free and open protocol, built on IETF standards and licenses from Open Web Foundation. It is very old

### 11.2. OAuth2
is a security standard where you give one application permission to access your data in another application, the steps to grant permisstion, or consent, are often referred to as authorization or even delegated authorization. You authoriza one application to access your data, or use features in another application on your behalf without giving them the password. Refrence `https://oauth.net/2/`

### 11.3 OAuth2 Terminology
 - Resource owner: The end user
 - Client: Application interact with some application after taking permistion from resource owner
 - Authorization Server: Which knows about resource owner. The resource owner should have an account in this server, that can be the server which provide the authorization logic.
 - Resource Server: This is server where the APIs, services that client want to consume are hosted
 - Scopes: granular permissions the Client wants, such as access to data or to perform certain actions.
**In screnario TweetAnalyzer** that need the your tweeter permission to access and get data for analyzing in TweetAnalyzer.
Resource owner is you the end user, Client is TweetAnalyzer, Authorization Server is Twitter server, Resource Server is Twitter server. And Scope is read_tweets

`Note` In small application Authorization Server and Resource Server is one server but for big application as google that is different resource server.

### 11.4 OAuth2 Sample folow for TweetAnalyzer screnario
 - `Step 1` TweetAnalyzer team will reach out to Twitter and express their interest working with them by allowing their user to login with Twitter -> The twitter team collect the details, logo etc from TweetAnalyzer and issued a `ClientID` & `Client Secret`
 - `Step 2` Resource owner visited TweetAnalyzer website and excited about the idea and decided to use the website -> Resouce owner click to "Signup wwith Twitter". The end user licked on it and boom it has redirect the user to Twitter login page.
 - `Step 3` The resource owner entered his credentials -> post successful Authentication, the twitter will display a consent page asking the user if he is fine to share his Tweet data READ ONLY scope to client which is TweetAnalyzer app. He said YES.
 - `Step 4` The Twitter Authorization server issue an access & refresh tokens to the TweetAnalyzer website -> ACCESS & REFRESH tokens are random complex strings which are tough to guess. Client stores tokens for future interaction with Twitter server
 - `Step 5` My TweetAnalyzer send the request to resource server with access token which is received from step 4 -> Twitter server validates the access token with the Auth server and if it is valid, it return the resource owner Tweets data inside the response.
 - `Step 6` TweetAnalyzer process it and display a report to it's end user -> End user can looks at report and he is super happy.

### 11.5 Deep dive on Authorization code grant type flow in OAUTH2
Check sample in `https://oauth.com/playground/`

Refresh token:
User login then having token and refresh token. With refresh token the client can get the new access token by this token.

### 11.6 OAuth2 flows by playground
Already have before : Authentication Server.


### 11.6 OpenID (OIDC = OpenID Connect)
- That is a protocol that sits on top of the OAuth2.0  framework. Wile Oauth2.0 provides authorization via an access token containing scopes, OpenID connect provides authentication by introducing a new ID token which contains a new set of information and claims specifically for identify
- With the ID token, OpenID connect brings standard around sharing identity details among the applications.

Motivation
- OAuth2.0 you getting access_token, scope but client don't know who is my end user, what is his detail as mail  => Some people send the logged in user details to client application =. no uniformity for it => they introduce new concept called OpenID Connect
- When you implement OpenID Connect - you getting 2 kind of token one is Access Token , one is ID token beside refresh token. Access_Token handle authorization, ID_token handle the user detail

They have uniform of sharing the user details in the form of ID token by OpenID Connect

####  Why OpenID connect important
- Identify is key to any application, implement OpenID Connect on top of OAuth2.0 completes an IAM (Identity & Access Management) strategy


