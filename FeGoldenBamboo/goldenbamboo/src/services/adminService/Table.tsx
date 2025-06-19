import axios from "axios";
import { API_URL } from "../../configs/api_url";

export const getTables = async (branchId: string) => {
  const response = await axios.get(`${API_URL}/Staff/Branch/${branchId}/Tables`, {
    withCredentials: true,
  });
  return response.data;
};
