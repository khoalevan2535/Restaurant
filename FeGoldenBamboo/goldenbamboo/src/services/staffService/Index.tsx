import { API_URL } from "../../configs/api_url";

const orderStaffService = {
  /**
   * Lấy tất cả thông tin cần thiết cho trang Order của nhân viên.
   * @param {number} branchId ID của chi nhánh.
   * @param {number} tableId ID của bàn.
   * @param {number} orderId ID của đơn hàng.
   * @param {number} categoryId ID của danh mục (-1 nếu muốn lấy tất cả món ăn).
   * @returns {Promise<Object>} Một Promise chứa dữ liệu JSON từ backend.
   */
  getOrderPageData: async (branchId, tableId, orderId, categoryId) => {
    try {
      const url = `${API_URL}/Staff/Branch/${branchId}/Table/${tableId}/Order/${orderId}/Category/${categoryId}`;

      const response = await fetch(url);

      if (!response.ok) {
        const errorBody = await response.json().catch(() => ({ message: "Unknown error" }));
        throw new Error(`HTTP error! status: ${response.status}, message: ${errorBody.message || response.statusText}`);
      }

      const data = await response.json();
      return data;
    } catch (error) {
      console.error("Lỗi khi lấy dữ liệu trang Order của nhân viên:", error);
      // Bạn có thể ném lại lỗi để component gọi hàm này xử lý cụ thể hơn
      throw error;
    }
  },

  // Bạn có thể thêm các hàm khác vào đây nếu có các API khác liên quan đến order, table, category, v.v.
  // Ví dụ:
  // getTableById: async (tableId) => { ... }
  // createOrder: async (orderData) => { ... }
};

export default orderStaffService;