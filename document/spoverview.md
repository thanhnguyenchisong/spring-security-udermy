## 1. Spring security internal
1. User entered credentials
2. Spring Security Filters will extract the username and password in step `2` then convert it to authentication object that store the details of our end user.
3. `Step 3` hand over the request to Authentication Manager - that check the Authentication Providers availabel inside our webapp at step 4.
We can have multiple Authentication providers.
UserDetails Manager/Service and PasswordEncoder works together to determine credential of an end user is valid or not. One of Authentication Provider is done -> that is valid
4. -> that will handover back to Authentication manager then SpringSecurityFilter that will also stored in Security Context na display for user know.


Purpose :
UserDetailService: System User Details that match with user provided Username. Here it just gets the users that have same username and does not tell the application whether authentication is successful or failed.
AuthenticationProvider: xác thực (so sánh) người dùng (yêu cầu) đã cung cấp tên người dùng và mật khẩu với Người dùng hệ thống (Đây có thể là bất kỳ hệ thống nào như DB duy trì danh sách người dùng đã đăng ký)

Example:
DaoAuthenticationProvider  sẽ xác thực bằng việc bảo UserDetailService lấy user detail object tử DB lên thông qua JdbcDaoImpl  để xác thực. Nếu ko tìm thấy  -> UserDetailService throw e

Nếu tìm thấy trong DB thì DaoAuthenticationProvider sẽ tiến trình check user password với DB để xác thực authentication.

Cái này là luồng spring cung cấp default có thể bấm vào JdbcDaoImpl để biết.

