import axios from 'axios';

const API_URL = 'http://localhost:8080/Discount';

export const getDiscounts = async () => {
    try {
        const response = await axios.get(API_URL);
        return response.data;
    } catch (error) {
        console.error("Lỗi khi lấy dữ liệu:", error);
        return [];
    }
};

export const createDiscount = async (discountData) => {
    console.log("Dữ liệu gửi lên:", discountData); // Thêm dòng này để kiểm tra dữ liệu
    try {
        const response = await axios.post(API_URL, discountData);
        return response.data;
    } catch (error) {
        console.error("Lỗi khi tạo khuyến mãi:", error.response?.data || error.message);
        return null;
    }
};


export const updateDiscount = async (id, discountData) => {
    try {
        const response = await axios.put(`${API_URL}/${id}`, discountData);
        return response.data;
    } catch (error) {
        console.error("Lỗi khi cập nhật khuyến mãi:", error.response?.data || error.message);
        return null;
    }
};

export const deleteDiscount = async (id) => {
    try {
        await axios.delete(`${API_URL}/${id}`);
        return true;
    } catch (error) {
        console.error("Lỗi khi xóa khuyến mãi:", error.response?.data || error.message);
        return false;
    }
};
