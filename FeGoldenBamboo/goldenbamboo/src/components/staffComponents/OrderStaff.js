import React, { useState, useEffect } from "react";
import { getBranch, createOrder } from "../../services/staffService/OrderService.js"; // Import service
import { useNavigate } from "react-router-dom";
import { Link } from "react-router-dom";
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
      <div>
        <nav className="navbar navbar-expand-lg bg-body-tertiary">
          <div className="container-fluid">
            <Link className="navbar-brand" to="#">
              Navbar
            </Link>
            <button
              className="navbar-toggler"
              type="button"
              data-bs-toggle="collapse"
              data-bs-target="#navbarNav"
              aria-controls="navbarNav"
              aria-expanded="false"
              aria-label="Toggle navigation"
            >
              <span className="navbar-toggler-icon"></span>
            </button>
            <div className="collapse navbar-collapse" id="navbarNav">
              <ul className="navbar-nav">
                <li className="nav-item">
                  <Link className="nav-link active" aria-current="page" to="/Staff/Branch/:branchId/Tables">
                    Đặt món
                  </Link>
                </li>
                <li className="nav-item">
                  <Link className="nav-link active" aria-current="page" to="/Staff">
                    Bàn ăn
                  </Link>
                </li>
              </ul>
            </div>
          </div>
        </nav>
      </div>
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
