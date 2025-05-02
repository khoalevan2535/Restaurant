import axios from "axios";

const API_URL = "http://localhost:8080/Menus"; // URL API của bạn

// Lấy tất cả menu
export const getMenus = async () => {
  try {
    const response = await axios.get(API_URL);
    return response.data;
  } catch (error) {
    console.error("Có lỗi khi lấy danh sách menu", error);
    throw error;
  }
};

// Lấy menu theo ID
export const getMenuById = async (id) => {
  try {
    const response = await axios.get(`${API_URL}/${id}`);
    return response.data;
  } catch (error) {
    console.error("Có lỗi khi lấy menu theo ID", error);
    throw error;
  }
};

// Tạo mới menu
export const addMenu = async (menu) => {
  try {
    const response = await axios.post(API_URL, menu);
    return response.data;
  } catch (error) {
    console.error("Có lỗi khi tạo menu", error);
    throw error;
  }
};

// Cập nhật menu
export const updateMenu = async (id, menu) => {
  try {
    const response = await axios.put(`${API_URL}/${id}`, menu);
    return response.data;
  } catch (error) {
    console.error("Có lỗi khi cập nhật menu", error);
    throw error;
  }
};

// Xóa menu
export const deleteMenu = async (id) => {
  try {
    await axios.delete(`${API_URL}/${id}`);
  } catch (error) {
    console.error("Có lỗi khi xóa menu", error);
    throw error;
  }
};
