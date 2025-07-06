// src/services/staffService/discountService.js
import axios from "axios";

const API_URL = "http://localhost:8080/Discount"; // Thay bằng URL backend thực tế

export const getDiscounts = async () => {
  try {
    const response = await axios.get(`${API_URL}`);
    return response.data;
  } catch (error) {
    throw new Error(error.response?.data?.message || "Lỗi khi lấy danh sách khuyến mãi");
  }
};

export const getDiscountById = async (id) => {
  try {
    const response = await axios.get(`${API_URL}/${id}`);
    return response.data;
  } catch (error) {
    throw new Error(error.response?.data?.message || "Lỗi khi lấy khuyến mãi theo ID");
  }
};

export const searchDiscounts = async (name) => {
  try {
    const response = await axios.get(`${API_URL}/search`, {
      params: { name },
    });
    return response.data;
  } catch (error) {
    throw new Error(error.response?.data?.message || "Lỗi khi tìm kiếm khuyến mãi");
  }
};

export const createDiscount = async (data) => {
  try {
    const response = await axios.post(`${API_URL}/Manager/Discount/Add`, data);
    return response.data;
  } catch (error) {
    throw new Error(error.response?.data?.message || "Lỗi khi tạo khuyến mãi");
  }
};

export const updateDiscount = async (id, data) => {
  try {
    const response = await axios.put(`${API_URL}/Manager/Discount/Update/${id}`, data);
    return response.data;
  } catch (error) {
    throw new Error(error.response?.data?.message || "Lỗi khi cập nhật khuyến mãi");
  }
};

export const deleteDiscount = async (id) => {
  try {
    const response = await axios.delete(`${API_URL}/Manager/Discount/Delete/${id}`);
    return response.data;
  } catch (error) {
    throw new Error(error.response?.data?.message || "Lỗi khi xóa khuyến mãi");
  }
};
