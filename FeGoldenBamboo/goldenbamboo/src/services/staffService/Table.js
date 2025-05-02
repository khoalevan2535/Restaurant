import axios from "axios";
import { API_URL } from "../../constants/apiUrl";

export const getTableByBranch = async (branchId) => {
  try {
    const response = await axios.get(`${API_URL}/Staff/Branch/${branchId}/Table`, { withCredentials: true });
    return response.data;
  } catch (error) {
    console.error("❌ Lỗi khi lấy danh sách bàn:", error);
    throw error;
  }
}