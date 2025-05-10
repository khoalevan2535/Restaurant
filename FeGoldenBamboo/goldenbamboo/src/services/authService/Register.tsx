import axios from "axios";
import { API_URL } from "../../configs/api_url";

export const register = async (data: { name: string; phone: string; password: string; branchId: number }) => {
    const response = await axios.post(`${API_URL}/Register`, data);
    return response.data;
  };