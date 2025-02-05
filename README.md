## Giới thiệu dự án
<p>Dự án Đặt Vé Xem Phim Online là một ứng dụng web hiện đại được xây dựng nhằm tạo điều kiện thuận lợi cho người dùng trong việc tìm kiếm thông tin phim, xem lịch chiếu và đặt vé xem phim một cách nhanh chóng và tiện lợi. Ứng dụng tích hợp đầy đủ các tính năng từ việc lựa chọn phim, suất chiếu, chọn chỗ ngồi đến thanh toán trực tuyến, đảm bảo mang lại trải nghiệm người dùng mượt mà và an toàn.

### Các điểm nổi bật của dự án bao gồm:

<p>Tìm kiếm và lọc phim: Người dùng có thể dễ dàng tra cứu các bộ phim theo thể loại, ngày chiếu hay rạp chiếu.
<p></p>Quy trình đặt vé đơn giản: Hệ thống đặt vé trực tuyến cho phép người dùng lựa chọn suất chiếu, chọn chỗ ngồi và thanh toán nhanh chóng.
<p>Quản trị viên: Hỗ trợ quản lý thông tin phim, lịch chiếu và vé, giúp dễ dàng cập nhật nội dung theo thời gian thực.
<p>Người dùng: Có thể đăng ký, đăng nhập, Xác thực tài khoản, quản lý thông tin cá nhân, quên mật khâu, xác thực email,..
<p>Đăng nhập: Xừ dụng JSON Web Token, cookie để lưu trữ refresh token.

### Các công nghệ sử dụng bao gồm:
<p>Spring Security: Áp dụng để bảo vệ các endpoint và quản lý phân quyền người dùng.
<p>JWT (JSON Web Token): Sử dụng cho cơ chế xác thực và ủy quyền, giúp đảm bảo các yêu cầu truy cập API được kiểm tra một cách an toàn.
<p>Java Mail: Được tích hợp để gửi email thông báo, xác nhận hoặc reset mật khẩu cho người dùng.
<p>Cloudinary: Lưu trữ và quản lý ảnh một cách hiệu quả
<p>Stripe: Tích hợp thanh toán trực tuyến
<p>Cache Caffeine: Sử dụng để lưu trữ OTP tạm thời


Dự án được phát triển trên nền tảng Spring Boot và sử dụng PostgreSQL làm hệ thống lưu trữ dữ liệu, với mục tiêu tạo ra một giải pháp hiệu quả, bảo mật và dễ dàng mở rộng trong tương lai.
