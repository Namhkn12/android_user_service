Đổi application.properties cho đúng hệ thống của mình.
file.image-dir là địa chỉ thư mục chứa ảnh.

Mỗi user trong bảng user_info của database sẽ có img_path, kèm với file.image-dir sẽ tạo thành directory cho ảnh.
(VD: file.image-dir = "D:\\IntelliJ Projects\\AndroidUserSevice\\image_file", img_path = "charlie.jpg" thì đường dẫn là "D:\\IntelliJ Projects\\AndroidUserSevice\\image_file\\charlie.jpg"

Ảnh được gửi dưới dạng byte[] trong ResponseBody.

Endpoint:
- GET /api/image/users/{id} : ResponseBody là ảnh của user với id trong path, dạng byte[], dùng Glide trong app để đọc sẽ tiện hơn.
- GET /api/users/{id} : ResponseBody là json, gồm id, displayName, address, phoneNumber. VD:
  {
    "id": 1,
    "displayName": "Alice Nguyen",
    "address": "123 Elm Street, Hanoi",
    "phoneNumber": "0987654321"
  }
