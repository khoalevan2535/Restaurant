import axios from 'axios';

const API_BASE_URL = 'http://localhost:8080';

const api = axios.create({
  baseURL: API_BASE_URL,
  timeout: 5000,
  headers: {
    'Content-Type': 'application/json',
  }
});

export const getAllDiscountCombos = async () => {
  try {
    const response = await api.get('/Discount-Combo');
    return response.data;
  } catch (error) {
    console.error('Error fetching discount combos:', error);
    throw new Error('Không thể tải danh sách combo giảm giá');
  }
};

export const createDiscountCombo = async (data) => {
  try {
    const response = await api.post('/Discount-Combo', {
      comboId: data.comboId, 
      discountId: data.discountId, 
      discountPercentage: data.discountPercentage
    });
    console.log("Created Discount Combo:", response.data);
    return response.data;
  } catch (error) {
    console.error('Error creating discount combo:', error);
    throw new Error('Không thể tạo combo giảm giá');
  }
};


export const updateDiscountCombo = async (id, data) => {
  try {
    const response = await api.put(`/Discount-Combo/${id}`, {
      comboId: data.comboId, 
      discountId: data.discountId, 
      discountPercentage: data.discountPercentage
    });
    console.log("Updated Discount Combo:", response.data);
    return response.data;
  } catch (error) {
    console.error('Error updating discount combo:', error);
    throw new Error('Không thể cập nhật combo giảm giá');
  }
};


export const deleteDiscountCombo = async (id) => {
  try {
    const response = await api.delete(`/Discount-Combo/${id}`); 
    return response.data;
  } catch (error) {
    console.error('Error deleting discount combo:', error);
    if (error.response) {
      if (error.response.status === 409) {
        throw new Error('Không thể xóa do tồn tại ràng buộc dữ liệu');
      }
      throw new Error(error.response.data.message || 'Không thể xóa combo giảm giá');
    }
    throw new Error('Lỗi kết nối mạng');
  }
};

export const getAllCombos = async () => {
  try {
    const response = await api.get('/Combo');
    return response.data;
  } catch (error) {
    console.error('Error fetching combos:', error);
    throw new Error('Không thể tải danh sách combo');
  }
};

export const getAllDiscounts = async () => {
  try {
    const response = await api.get('/Discount');
    return response.data;
  } catch (error) {
    console.error('Error fetching discounts:', error);
    throw new Error('Không thể tải danh sách giảm giá');
  }
};
