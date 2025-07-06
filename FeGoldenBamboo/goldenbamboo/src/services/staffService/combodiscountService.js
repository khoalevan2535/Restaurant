// services/staffService/combodiscountService.js
import axios from "axios";

const API_URL = "http://localhost:8080/Discount-Combo";
const MANAGER_URL = "http://localhost:8080/Discount-Combo/Manager"; 

// Lấy tất cả Discount Combos
export const getAllDiscountCombos = async () => {
  try {
    const response = await axios.get(API_URL);
    return response.data;
  } catch (error) {
    throw new Error(error.response?.data?.message || "Lỗi khi lấy danh sách combo giảm giá");
  }
};

// Alias
export const getDiscountCombos = getAllDiscountCombos;

// Lấy Discount Combo theo ID
export const getDiscountComboById = async (id) => {
  try {
    const response = await axios.get(`${API_URL}/${id}`);
    return response.data;
  } catch (error) {
    throw new Error(error.response?.data?.message || "Lỗi khi lấy combo giảm giá theo ID");
  }
};

// Tạo mới Discount Combo
export const createDiscountCombo = async (data) => {
  try {
    const response = await axios.post(`${MANAGER_URL}/Add`, data);
    return response.data;
  } catch (error) {
    throw new Error(error.response?.data?.message || "Lỗi khi tạo combo giảm giá");
  }
};

// Cập nhật Discount Combo
export const updateDiscountCombo = async (id, data) => {
  try {
    const response = await axios.put(`${MANAGER_URL}/Update/${id}`, data);
    return response.data;
  } catch (error) {
    throw new Error(error.response?.data?.message || "Lỗi khi cập nhật combo giảm giá");
  }
};

// Xoá Discount Combo
export const deleteDiscountCombo = async (id) => {
  try {
    const response = await axios.delete(`${MANAGER_URL}/Delete/${id}`);
    return response.data;
  } catch (error) {
    if (error.response?.status === 409) {
      throw new Error("Không thể xóa do tồn tại ràng buộc dữ liệu");
    }
    throw new Error(error.response?.data?.message || "Lỗi khi xóa combo giảm giá");
  }
};

// Lấy danh sách Combo cho select box
export const getCombosForSelect = async () => {
  try {
    const response = await axios.get("http://localhost:8080/Combo");
    return response.data;
  } catch (error) {
    throw new Error(error.response?.data?.message || "Lỗi khi lấy danh sách combo");
  }
};

// Lấy danh sách Discount cho select box
export const getDiscountsForSelect = async () => {
  try {
    const response = await axios.get("http://localhost:8080/Discount");
    return response.data;
  } catch (error) {
    throw new Error(error.response?.data?.message || "Lỗi khi lấy danh sách khuyến mãi");
  }
};