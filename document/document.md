# Spring Security
Provide 2 ways store login details.
- UserDetail interface (user class) -> return UserDetail - have all user infomration as expired time.
- Principal interface (UsernamePasswordAuthenticationToken class) -> return Authentication(UserPasswordAuthenticationToken) where we try to determine the authenication is success or not inside AuthenticationProvider and AuthenticationManager.
just store the authenication data which are username, password, ... to determine authentication is success or not => just care about authenication then use Authentication object is better, no unused data.


### `User detail and User Detail manager`
- User details provides user core information
- User detail manager manage the user includes create, update, delete, changePassword, userExist
  Spring security provides InMemoryUserDetailsManager, JdbcUserDetailManager, LdapUserDetailManager
- User detail service - provides the detail actions for user detail manager.
## I. `Different ways of Pws management`

### 1. `Encoding`
- Converting the data from one form to others and has nothing to do with cryptography
- Involving no secret and completely reversible
- Encoding can't be used for securing data

Some public algorithms used for encoding: ASCII, BASE64, UNICODE, UTF8

### 2. `Encryption`
- Transforming data in such a way that guarantees confidentiality
- Requiring the use of a secret which in cryptographic terms, called `key`
- Can be reversible by using decryption with the help of `key`

As long as the `key` is confidential, encryption can be considered as secured

### 3. `Hashing`
- Data is converted to hash value using some hashing function
- Data once hashed is non-reversible, one can't determine the original data from hash value generated
- Given some arbitrary data along with the output of a hashing algorithm, one can verify whether this data matches the original
input data without needing to see the original data.
  


### The hashing is best way (recommendation)
## `why endcode with SHA-256 isn't secured` ?

## II. `PasswordEncoder`
### 1. `BCryptPasswordEncoder`
- It is going to use BCrypt hashing algorithm which is invented in 1999, It demand CPU so hard to hacker can
get the original pain text because cost of CPU to execute milion cases.
  
### 2. `ScryptPasswordEncoder`
Advanced version of BCryptPassEncoder because It demands 2 parameters with first one parameter is computer power (CPU), second parameter is memory => you can assume the cost for hacker to get pain text by trying many the password cases.

### 3. `Argon2PasswordEncoder`
Demand 3 parameter with 2 firsts are same mean with BCrypt and SCrypt, last one parameter is multiple thread
### SCrypt and Argon2 we expect lot of resource even register and login for your web application so more common is BCrypt


## III. `Considering Authentication Provider`
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

### IV. `CORS & CSRF`
#### 1. `CORS - Cross-Origin Resource Sharing`
- Enables scripts running on browser client to interact with resource from a different origin.
- By default browser will block this communication due CORS
So CORS is not a security issue/attrack but the default protection provided by browsers to stop sharing the data bw different origin

Different origin is match one of this cases
- Different schema (HTTP or HTTPs)
- Different domain
- Different port

#### 2. `CSRF` 
It isn't allow post and put operation create or update data in DB, by default the SpringSecurity stops them. Response status = 403.

Situation - User can be steal all information in webside cookie, credential or sessionID. TO know diffentiate bw legitimate user and hacker we introduce the CSRF token

  