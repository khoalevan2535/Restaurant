import axios from 'axios';
import { API_URL } from '../../configs/api_url';
import { DataState, OrderDetailResponse } from '../../interface/OrderDetail.ts';
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


export const addDishToOrder = async (p0: number, p1: { id: number; name: string; description: string; price: number; quantity: number; type: string; }, data: {
  orderId: number;
  dishOrComboId: number;
  price: number;
  quantity: number;
  type: boolean;
  discountPercentage: number;
}): Promise<OrderDetailResponse> => {
  const response = await axios.post(`${API_URL}/Admin/Order/AddDishToOrder`, data);
  return response.data; // 👈 BẮT BUỘC phải trả về kết quả
};
