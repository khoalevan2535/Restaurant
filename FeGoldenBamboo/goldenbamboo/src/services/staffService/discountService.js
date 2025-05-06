import axios from 'axios';

const API_URL = 'http://localhost:8080/Discount';   

// CRUD Operations
export const getDiscounts = async () => {
    try {
        const response = await axios.get(API_URL);
        return response.data;
    } catch (error) {
        console.error("Lỗi khi lấy dữ liệu:", error);
        return [];
    }
};

export const getDiscountById = async (id) => {
    try {
        const response = await axios.get(`${API_URL}/${id}`);
        return response.data;
    } catch (error) {
        console.error("Lỗi khi lấy chi tiết khuyến mãi:", error);
        return null;
    }
};

export const createDiscount = async (discountData) => {
    console.log("Dữ liệu gửi lên:", discountData);
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

// Additional methods
export const findDiscountsByStatus = async (status) => {
    try {
        const response = await axios.get(`${API_URL}/status`, {
            params: { status }
        });
        return response.data;
    } catch (error) {
        console.error("Lỗi khi lấy khuyến mãi theo trạng thái:", error);
        return [];
    }
};

export const searchDiscountsByName = async (keyword) => {
    try {
        const response = await axios.get(`${API_URL}/search`, {
            params: { keyword: keyword || '' }
        });
        return response.data;
    } catch (error) {
        console.error("Lỗi khi tìm kiếm khuyến mãi:", error);
        return [];
    }
};
