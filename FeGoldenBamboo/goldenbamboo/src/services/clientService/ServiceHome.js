import axios from "axios";
import { API_URL } from "../../config/apiConfig";

export const findDefaultMenuDishesByBranch = async (branchId) => {
  const response = await axios.get(`${API_URL}/Client/Branch/${branchId}`);
  return response.data;
};
