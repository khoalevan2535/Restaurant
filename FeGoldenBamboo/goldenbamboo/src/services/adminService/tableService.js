import axios from 'axios';

const API_BASE_URL = 'http://localhost:8080/api/table'; // hoặc URL tương ứng với backend của bạn

// Lấy danh sách tất cả bàn
export const getAllTables = async () => {
  const response = await axios.get(API_BASE_URL);
  return response.data;
};

// Lấy bàn theo ID (hoặc branchId nếu đúng logic)
export const getTableById = async (id) => {
  const response = await axios.get(`${API_BASE_URL}/${id}`);
  return response.data;
};

// Thêm bàn mới
export const createTable = async (tableData) => {
  const response = await axios.post(API_BASE_URL, tableData);
  return response.data;
};

// Xóa bàn theo ID
export const deleteTable = async (id) => {
  const response = await axios.delete(`${API_BASE_URL}/${id}`);
  return response.data;
};

// Cập nhật số bàn và/hoặc branchId
export const updateTable = async (id, number, branchId) => {
  const params = new URLSearchParams();
  if (number !== undefined) params.append('number', number);
  if (branchId !== undefined) params.append('branchId', branchId);

  const response = await axios.put(`${API_BASE_URL}/${id}?${params.toString()}`);
  return response.data;
};
