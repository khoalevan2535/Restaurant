import axios from "axios";
import { API_URL } from "../../configs/api_url";

export const getTables = async (branchId: string) => {
  const response = await axios.get(`${API_URL}/Admin/Branch/${branchId}/Tables`, {
    withCredentials: true,
  });

  // Bây giờ backend trả về: { status, tables }
  return response.data;
};
