import axios from "axios";
import { API_URL } from "../../configs/api_url";

const LoginService = {
  login: async (phone, password) => {
    try {
      const response = await axios.post(`${API_URL}/Login`, { phone, password }, { withCredentials: true });
      return {
        success: true,
        message: response.data.message,
      };
    } catch (error) {
      return {
        success: false,
        message: error.response?.data?.message || "Đăng nhập thất bại",
      };
    }
  },
};

export default LoginService;
