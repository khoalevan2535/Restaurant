import axios from "axios";
import { API_URL } from "../../configs/api_url";

export const createOrder = async (branchId: string, tableId: string) => {
  const response = await axios.post(
    `${API_URL}/Admin/Branch/${branchId}/Table/${tableId}`,
    {}, // Gửi body rỗng hoặc truyền OrderDTO nếu cần
    { withCredentials: true }
  );
  return response.data;
};