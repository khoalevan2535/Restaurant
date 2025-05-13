import axios from 'axios';
import { API_URL } from '../../configs/api_url';
import { DataState } from '../../interface/OrderDetail.ts';

// Truyền tham số vào hàm
export const getMenuData = async (
  branchId: number,
  tableId: number,
  orderId: number,
  categoryId: number
): Promise<DataState> => {
  const response = await axios.get<DataState>(
    `${API_URL}/Admin/Branch/${branchId}/Table/${tableId}/Order/${orderId}/Category/${categoryId}`
  );
  return response.data;
};
