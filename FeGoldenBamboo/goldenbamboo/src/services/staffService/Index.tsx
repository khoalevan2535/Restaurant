import axios from "axios";
import { API_URL } from "../../configs/api_url";

// Hàm xử lý lỗi dùng chung cho axios
function handleAxiosError(error) {
    if (error.response) {
        // Server trả về lỗi
        const message = error.response.data?.message || error.response.statusText;
        throw new Error(`HTTP error! status: ${error.response.status}, message: ${message}`);
    } else if (error.request) {
        throw new Error("No response from server.");
    } else {
        throw new Error(error.message);
    }
}

const orderStaffService = {
    /**
     * Lấy dữ liệu trang order của nhân viên
     */
    getOrderPageData: async (branchId, tableId, orderId, categoryId) => {
        try {
            const url = `${API_URL}/Staff/Branch/${branchId}/Table/${tableId}/Order/${orderId}/Category/${categoryId}`;
            const response = await axios.get(url);
            return response.data;
        } catch (error) {
            handleAxiosError(error);
        }
    },

    // MỚI: Hàm bị thiếu để lấy chi tiết đơn hàng
    getOrderDetailData: async (orderId) => {
        try {
            // API này đã được tối ưu ở backend để chỉ trả về orderDetails
            const url = `${API_URL}/Staff/Order/${orderId}/Details`;
            const response = await axios.get(url);
            return response.data;
        } catch (error) {
            handleAxiosError(error);
        }
    },

    /**
     * Thêm món vào đơn hàng
     */
    addToOrder: async (data) => {
        try {
            const url = `${API_URL}/Order/AddFoodToOrder`;
            const response = await axios.post(url, data, {
                headers: { 'Content-Type': 'application/json' }
            });
            return response.data;
        } catch (error) {
            handleAxiosError(error);
        }
    },

    /**
     * Cập nhật số lượng món trong đơn hàng
     */
    updateOrderDetail: async (orderId, detailId, quantity) => {
        try {
            const url = `${API_URL}/Order/${orderId}/UpdateQuantity`;
            const response = await axios.put(url, { orderDetailId: detailId, quantity: quantity }, {
                headers: { 'Content-Type': 'application/json' }
            });
            return response.data;
        } catch (error) {
            handleAxiosError(error);
        }
    },

    /**
     * Xóa một món khỏi đơn hàng
     */
    removeOrderDetail: async (detailId) => {
        try {
            const url = `${API_URL}/Order/RemoveFood/${detailId}`;
            const response = await axios.delete(url);
            return response.data;
        } catch (error) {
            handleAxiosError(error);
        }
    },

    checkoutOrder: async (orderId) => {
        try {
            const url = `${API_URL}/api/orders/${orderId}/checkout`;
            const response = await axios.post(url);
            return response.data;
        } catch (error) {
            handleAxiosError(error);
        }
    },
    
    // MỚI: Hàm lấy danh sách bàn theo chi nhánh
    getTablesByBranch: async (branchId) => {
        try {
            // API này chúng ta đã tạo ở backend
            const url = `${API_URL}/api/tables/branch/${branchId}`; 
            const response = await axios.get(url);
            return response.data;
        } catch (error) {
            handleAxiosError(error);
        }
    },

   // Trong orderStaffService

findOrCreateOrder: async (tableId) => {
    try {
        // URL giờ đây sẽ gọn hơn, không còn tham số
        const url = `${API_URL}/api/orders/find-or-create`;
        // Gửi tableId dưới dạng một object JSON trong body
        const response = await axios.post(url, { tableId: tableId });
        console.log("Phản hồi đầy đủ từ backend:", response);
            console.log("Dữ liệu nhận được (response.data):", response.data);
        return response.data;
    } catch (error) {
        console.error("Chi tiết lỗi:", error.response);
        handleAxiosError(error);
    }
},

 forceCreateNewOrder: async (tableId) => {
        try {
            const url = `${API_URL}/api/orders/force-create`;
            const response = await axios.post(url, { tableId: tableId });
            return response.data;
        } catch (error) {
            handleAxiosError(error);
        }
    },
     updateOrder: async (orderId, orderData) => {
        try {
            // API này bạn cần có ở backend để cập nhật thông tin order
            const url = `${API_URL}/api/orders/${orderId}`;
            const response = await axios.put(url, orderData);
            return response.data;
        } catch (error) {
            handleAxiosError(error);
        }
    },

   updateTableStatus: async (tableId, status) => {
    try {
        const response = await axios.put(`${API_URL}/api/tables/${tableId}/status`, { status });
        // Nếu thành công, trả về dữ liệu từ server
        return response.data;
    } catch (error) {
        // Nếu có lỗi, log lỗi ra console để debug
        console.error(`Lỗi khi cập nhật trạng thái cho bàn ${tableId}:`, error.response?.data || error.message);
        
        // Ném lỗi ra ngoài để component gọi nó có thể bắt và xử lý (ví dụ: hiển thị toast)
        throw error;
    }
}
};


export { orderStaffService };
