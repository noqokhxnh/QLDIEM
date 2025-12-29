# Hệ Thống Quản Lý Điểm Sinh Viên (QLDIEM)

## Mô tả
Hệ thống quản lý điểm sinh viên được phát triển bằng Java Swing với giao diện hiện đại, hỗ trợ phân quyền người dùng và quản lý điểm tự động.

## Tính năng chính

### 🔐 Phân quyền người dùng
- **Admin (type = 0)**: Quản lý toàn bộ hệ thống
- **Giáo viên (type = 1)**: Quản lý điểm môn học được phân công
- **Sinh viên (type = 2)**: Xem điểm cá nhân

### 📊 Quản lý điểm
- ✅ Tự động tính điểm tổng kết (CC×10% + GK×30% + CK×60%)
- ✅ Validation dữ liệu thông minh
- ✅ Kiểm tra trùng lặp điểm
- ✅ Tìm kiếm và lọc dữ liệu
- ✅ Giao diện thân thiện với người dùng

### 👥 Quản lý sinh viên & giáo viên
- Thêm, sửa, xóa thông tin sinh viên
- Quản lý lớp học và phân công giáo viên
- Phân quyền môn học cho giáo viên

## Công nghệ sử dụng

- **Java SE**: Core programming language
- **Java Swing**: GUI framework
- **MySQL**: Database management
- **JDBC**: Database connectivity
- **Maven**: Build tool

## Cấu trúc dự án

```
src/main/java/
├── connection/      # Database connection utilities
├── Model/           # Data models và database operations
├── View/            # UI components (Swing panels)
└── Controller/      # Business logic controllers
```

## Cài đặt và chạy

### Yêu cầu hệ thống
- Java JDK 8 hoặc cao hơn
- MySQL Server
- JDBC Driver (mysql-connector-java)

### Thiết lập database
1. Import file `CREATE_DATABASE_COMPLETE.sql` vào MySQL
2. Cập nhật thông tin kết nối trong `connection/DatabaseConnection.java`:
   ```java
   private static final String URL = "jdbc:mysql://localhost:3306/quanlydiem";
   private static final String USERNAME = "your_username";
   private static final String PASSWORD = "your_password";
   ```

### Cách chạy ứng dụng

#### 🖱️ **Cách 1: Double-click (Windows)**
```
Double-click file `run.bat`
```

#### 💻 **Cách 2: Command line**
**Windows:**
```cmd
run.bat
```

**Linux/Mac:**
```bash
chmod +x run.sh
./run.sh
```

#### ⚡ **Cách 3: Manual**
```bash
# Compile
javac -cp "lib/*" -d target/classes src/main/java/**/*.java

# Run
java -cp "target/classes;lib/*" View.Main
```

## Tài khoản mặc định

| Username | Password | Quyền |
|----------|----------|-------|
| admin | admin | Admin |
| gv001 | password | Giáo viên |
| sv001 | password | Sinh viên |

## Tính năng nổi bật

### ⚡ Tự động tính điểm
- Điểm tổng kết được tính real-time khi nhập điểm thành phần
- Hiển thị xếp loại tự động (Xuất sắc, Giỏi, Khá, Trung bình, Yếu)

### 🛡️ Validation thông minh
- Kiểm tra định dạng mã sinh viên, môn học
- Validation điểm số (0-10)
- Kiểm tra trùng lặp dữ liệu

### 🎨 Giao diện hiện đại
- Material Design inspired
- Responsive layout
- Color-coded grade classification

## Cấu trúc Database

### Các bảng chính:
- `tbluser`: Quản lý tài khoản đăng nhập
- `tblsinhvien`: Thông tin sinh viên
- `tblgiaovien`: Thông tin giáo viên
- `tblclass`: Thông tin lớp học
- `tblmonhoc`: Thông tin môn học
- `tbldiem`: Điểm số sinh viên
- `tblphancong`: Phân công giảng dạy

## Troubleshooting

### Lỗi kết nối Database:
1. Kiểm tra MySQL server đang chạy
2. Xác nhận thông tin kết nối trong `DatabaseConnection.java`
3. Kiểm tra JDBC driver có trong classpath

### Lỗi compile:
1. Kiểm tra Java JDK đã cài đặt
2. Kiểm tra JAVA_HOME environment variable
3. Đảm bảo tất cả file .java có syntax đúng

## Đóng góp
Mọi đóng góp và phản hồi đều được chào đón! Vui lòng tạo issue hoặc pull request.

## Giấy phép
MIT License

---
*Hệ thống quản lý điểm sinh viên hiện đại và thân thiện*