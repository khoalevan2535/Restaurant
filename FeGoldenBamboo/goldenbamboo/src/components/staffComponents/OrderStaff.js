import React, { useState, useEffect } from "react";
import { getBranch, createOrder } from "../../services/staffService/OrderService.js"; // Import service
import { useNavigate } from "react-router-dom";

const OrderStaff = () => {
  const [branch, setBranch] = useState(null);
  const navigate = useNavigate();
  useEffect(() => {
    const fetchBranch = async () => {
      try {
        const data = await getBranch(); // Gọi service để lấy dữ liệu
        setBranch(data);
      } catch (error) {
        console.error("Lỗi khi lấy chi nhánh:", error);
      }
    };

    fetchBranch();
  }, []);

  const handleCreateOrder = async () => {
    try {
      const newOrder = await createOrder({
        branchId: branch.id,
      });
      const branchId = branch.id;
      const orderId = newOrder.id;
      const categoryId = 1;
      navigate(`/Staff/Branch/${branchId}/Order/${orderId}/Category/${categoryId}`); // Điều hướng sau khi tạo thành công
    } catch (error) {
      console.error("Lỗi khi tạo đơn hàng:", error);
    }
  };

  return (
    <div>
      {branch ? (
        <div className="mb-4">
          <h3>Chi nhánh: {branch.name}</h3>
        </div>
      ) : (
        <p>Đang tải thông tin chi nhánh...</p>
      )}
      <button className="btn btn-primary" onClick={handleCreateOrder} disabled={!branch}>
        Tạo đơn hàng mới
      </button>
    </div>
  );
};

export default OrderStaff;
