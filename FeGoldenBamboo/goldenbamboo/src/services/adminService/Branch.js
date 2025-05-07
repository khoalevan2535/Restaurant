import axios from "axios";
import { API_URL } from "../../configs/api_url";

// Lấy danh sách chi nhánh
export const findBranch = async () => {
  const response = await axios.get(`${API_URL}/Branch`);
  return response.data;
};

// Thêm mới chi nhánh
export const createBranch = async (branch) => {
  const response = await axios.post(`${API_URL}/Manager/Branch/Add`, branch);
  return response.data;
};

// Cập nhật chi nhánh
export const updateBranch = async (id, branch) => {
  const response = await axios.put(`${API_URL}/Manager/Branch/Update/${id}`, branch);
  return response.data;
};

// Xóa chi nhánh
export const deleteBranch = async (id) => {
  await axios.delete(`${API_URL}/Manager/Branch/Delete/${id}`);
};

