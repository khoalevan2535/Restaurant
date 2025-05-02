import axios from "axios";
const API_URL = "http://localhost:8080";

export const findDefaultMenuDishesByBranch = async (branchId) => {
  const response = await axios.get(`${API_URL}/Client/Branch/${branchId}`);
  return response.data;
};
