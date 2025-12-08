# Quan Ly Diem (QLDIEM) - Hệ thống quản lý điểm sinh viên



## Mục lục
1. [Fork repository](#fork-repository)
2. [Gửi Pull Requests](#gửi-pull-requests)


## Fork repository

1. **Truy cập repository**: Truy cập vào repository GitHub mà bạn muốn đóng góp.

2. **Fork repository**:
   - Nhấn vào nút "Fork" ở góc trên bên phải của trang repository.
   - Chọn tài khoản GitHub của bạn khi được yêu cầu.
   - Chờ GitHub tạo bản sao repository của bạn.

3. **Clone bản fork về máy cục bộ**:
   ```bash
   git clone https://github.com/YOUR_USERNAME/QLDIEM.git
   cd QLDIEM
   ```

4. **Thiết lập remote upstream** (để giữ cho bản fork được đồng bộ):
   ```bash
   git remote add upstream https://github.com/ORIGINAL_OWNER/QLDIEM.git
   ```

5. **Xác minh remote**:
   ```bash
   git remote -v
   ```

## Gửi Pull Requests

### Trước khi tạo Pull Request

1. **Cập nhật kho lưu trữ cục bộ của bạn**:
   ```bash
   # Chuyển sang nhánh chính
   git checkout main

   # Lấy các thay đổi mới nhất từ upstream
   git fetch upstream

   # Hợp nhất các thay đổi từ upstream
   git merge upstream/main

   # Đẩy các cập nhật lên bản fork của bạn
   git push origin main
   ```

2. **Tạo một nhánh mới**:
   ```bash
   # Tạo và chuyển sang nhánh mới
   git checkout -b feature-descriptive-branch-name

   # Đối với sửa lỗi
   git checkout -b fix-descriptive-fix-name
   ```

3. **Thực hiện các thay đổi của bạn**:
   - Tuân theo mẫu MVC như đã mô tả ở trên
   - Viết các thông báo commit rõ ràng, ngắn gọn
   - Viết các bài kiểm thử cho chức năng mới

4. **Kiểm thử các thay đổi của bạn**:
   - Chạy các bài kiểm thử hiện có để đảm bảo không có gì bị hỏng
   - Thêm các bài kiểm thử mới nếu cần
   - Xác minh rằng các thay đổi của bạn hoạt động như mong đợi

5. **Cam kết các thay đổi của bạn**:
   ```bash
   # Kiểm tra các tập tin đã thay đổi
   git status

   # Thêm các tập tin cụ thể
   git add path/to/changed/files

   # Hoặc thêm tất cả các thay đổi (cẩn thận với điều này)
   git add .

   # Cam kết với một thông báo mô tả
   git commit -m "Thêm mô tả chi tiết về các thay đổi"
   ```

6. **Đẩy các thay đổi của bạn**:
   ```bash
   git push origin feature-descriptive-branch-name
   ```

### Tạo Pull Request

1. **Truy cập vào bản fork của bạn**: Truy cập vào kho lưu trữ bản fork của bạn trên GitHub.

2. **Chuyển sang nhánh của bạn**: Chọn nhánh nơi bạn đã thực hiện các thay đổi.

3. **Nhấn vào "New Pull Request"**: GitHub sẽ phát hiện các thay đổi và đề xuất tạo một pull request mới.

4. **Điền vào Pull Request**:
   - **Tiêu đề**: Viết một tiêu đề rõ ràng, mô tả (tối đa 50 ký tự)
   - **Mô tả**: Bao gồm thông tin chi tiết về các thay đổi của bạn:
     - Lý do các thay đổi này là cần thiết
     - Các thay đổi cụ thể đã được thực hiện
     - Các thay đổi tuân theo mẫu MVC như thế nào
     - Các thay đổi ảnh hưởng đến ứng dụng như thế nào
     - Bất kỳ thông tin bổ sung nào người xem xét nên biết

5. **Gửi Pull Request**: Nhấn vào "Create Pull Request"
