import axios from "axios";
import { API_URL } from "../../configs/api_url";

export const getBranch = async () => {
  try {
    const response = await axios.get(`${API_URL}/Staff`, { withCredentials: true });
    return response.data;
  } catch (error) {
    console.error("Lỗi khi lấy chi nhánh:", error);
    throw error;
  }
};
export const getBranchById = async (branchId) => {
  try {
    const response = await axios.get(`${API_URL}/Staff/Branch/${branchId}`, {
      withCredentials: true,
    });
    return response.data;
  } catch (error) {
    console.error("❌ Lỗi khi lấy chi nhánh theo ID:", error);
    throw error;
  }
};

export const createOrder = async (orderData) => {
  const response = await axios.post(`${API_URL}/Staff`, orderData, { withCredentials: true });
  return response.data;
};

export const dataStaff = async (orderId, categoryId, branchId) => {
  try {
    const response = await axios.get(`${API_URL}/Staff/Branch/${branchId}/Order/${orderId}/Category/${categoryId}`, {
      withCredentials: true,
    });
    return response.data;
  } catch (error) {
    console.error("Lỗi khi gọi API dataStaff:", error);
    return { categories: [], foods: [] };
  }
};

export const findBranchActive = async () => {
  try {
    const response = await axios.get(`${API_URL}/Branch/Active`, { withCredentials: true });
    return response.data;
  } catch (error) {
    console.error("Lỗi khi lấy danh sách chi nhánh:", error);
    throw error;
  }
};

export const addDishToOrder = async ({ orderId, dishOrComboId, price, quantity, type, discountPercentage }) => {
  try {
    const payload = { orderId, dishOrComboId, price, quantity, type, discountPercentage };
    const response = await axios.post(`${API_URL}/AddFoodToOrder`, payload);
    return response.data; // Kết quả trả về từ backend
  } catch (error) {
    console.error("Lỗi khi thêm món:", error);
    throw error;
  }
};

export const removeDishFromOrder = async (orderId, orderDetailId, branchId) => {
  try {
    // Gửi yêu cầu DELETE để xóa món khỏi giỏ hàng
    const response = await axios.delete(`${API_URL}/Staff/Branch/${branchId}/Order/${orderId}/OrderDetail/${orderDetailId}`, {
      withCredentials: true,
    });

    return response.data;
  } catch (error) {
    console.error("Lỗi khi xóa món khỏi giỏ hàng:", error);
    throw error;
  }
};

export const updateDishQuantity = async (orderId, orderDetailId, newQuantity) => {
  try {
    const payload = { quantity: newQuantity };
    const response = await axios.put(`${API_URL}/Staff/Order/${orderId}/OrderDetail/${orderDetailId}/updateQuantity`, payload, {
      withCredentials: true,
    });
    return response.data; // Trả về kết quả sau khi cập nhật số lượng
  } catch (error) {
    console.error("❌ Lỗi khi cập nhật số lượng món:", error);
    throw error;
  }
};
