import axios from 'axios';
import { API_URL } from '../../configs/api_url';
export interface Menu {
  id: number;
  name: string;
  isDefault: boolean;
}

export const getMenusByBranch = async (branchId: number): Promise<Menu[]> => {
  const response = await axios.get<Menu[]>(`${API_URL}/ListMenu/Branch/${branchId}`);
 console.log(response.data);
  return response.data;
};