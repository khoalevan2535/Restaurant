import axios from "axios";
import { API_URL } from "../../configs/api_url";

const login = async (phone, password) => {
  try {
    const response = await axios.post(`${API_URL}/Login`, { phone, password }, { withCredentials: true });
    return response.data;
  } catch (error) {
    console.error("Đăng nhập thất bại:", error);
    throw error;
  }
};

export default login;
