// src/services/staffService/comboService.js
import axios from "axios";

const API_URL = "http://localhost:8080/Combo";

export const getCombos = async () => {
  try {
    const response = await axios.get(`${API_URL}`);
    return response.data;
  } catch (error) {
    console.error("Lỗi khi lấy danh sách combo:", error);
    throw new Error(error.response?.data?.message || "Lỗi khi lấy danh sách combo");
  }
};

export const getComboById = async (id) => {
  try {
    const response = await axios.get(`${API_URL}/${id}`);
    return response.data;
  } catch (error) {
    console.error("Lỗi khi lấy combo theo ID:", error);
    throw new Error(error.response?.data?.message || "Lỗi khi lấy combo theo ID");
  }
};

export const createCombo = async (formData) => {
  try {
    const response = await axios.post(`${API_URL}/Manager/Combo/Add`, formData, {
      headers: {
        "Content-Type": "multipart/form-data",
      },
    });
    return response.data;
  } catch (error) {
    console.error("Lỗi khi tạo combo:", error);
    throw new Error(error.response?.data?.message || "Lỗi khi tạo combo");
  }
};

export const updateCombo = async (id, formData) => {
  try {
    const response = await axios.put(`${API_URL}/Manager/Combo/Update/${id}`, formData, {
      headers: {
        "Content-Type": "multipart/form-data",
      },
    });
    return response.data;
  } catch (error) {
    console.error("Lỗi khi cập nhật combo:", error);
    throw new Error(error.response?.data?.message || "Lỗi khi cập nhật combo");
  }
};

export const deleteCombo = async (id) => {
  try {
    const response = await axios.delete(`${API_URL}/Manager/Combo/Delete/${id}`);
    return response.data;
  } catch (error) {
    console.error("Lỗi khi xóa combo:", error);
    throw new Error(error.response?.data?.message || "Lỗi khi xóa combo");
  }
};
