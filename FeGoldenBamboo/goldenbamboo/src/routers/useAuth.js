// Đây là một ví dụ đơn giản. Trong thực tế, bạn sẽ giải mã JWT hoặc đọc cookie.
export const useAuth = () => {
  // Logic lấy vai trò người dùng
  // Ví dụ: Đọc từ localStorage
  const user = JSON.parse(localStorage.getItem("user")); // Giả sử bạn lưu user object
  const userRole = user ? user.role : null; // Lấy thuộc tính role

  // Trong ví dụ này, ta giả định vai trò là 'admin' để test
  // return { userRole: 'admin' };

  return { userRole };
};
