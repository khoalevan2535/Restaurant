import React, { useState, useEffect } from "react";
import { useNavigate } from "react-router-dom";
import * as discountService from "../../../services/staffService/discountService";

const DiscountTable = () => {
  const [discounts, setDiscounts] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);
  const [searchTerm, setSearchTerm] = useState("");
  const [currentPage, setCurrentPage] = useState(1);
  const itemsPerPage = 5;

  const [deleteId, setDeleteId] = useState(null);
  const [showDeleteConfirmation, setShowDeleteConfirmation] = useState(false);

  const navigate = useNavigate();

  useEffect(() => {
    fetchDiscounts();
  }, []);

  const fetchDiscounts = async () => {
    try {
      const data = await discountService.getDiscounts();
      setDiscounts(data);
    } catch (err) {
      setError(err.message || "Lỗi khi tải dữ liệu");
    } finally {
      setLoading(false);
    }
  };

  const handleDeleteDiscount = async () => {
    try {
      await discountService.deleteDiscount(deleteId);
      setDiscounts(discounts.filter((d) => d.id !== deleteId));
    } catch (err) {
      console.error(err);
    } finally {
      setShowDeleteConfirmation(false);
      setDeleteId(null);
    }
  };

  const filteredDiscounts = discounts.filter((discount) =>
    discount.name.toLowerCase().includes(searchTerm.toLowerCase())
  );

  const indexOfLastItem = currentPage * itemsPerPage;
  const indexOfFirstItem = indexOfLastItem - itemsPerPage;
  const currentItems = filteredDiscounts.slice(indexOfFirstItem, indexOfLastItem);
  const totalPages = Math.ceil(filteredDiscounts.length / itemsPerPage);

  const handlePageChange = (pageNumber) => {
    setCurrentPage(pageNumber);
  };

  const formatDisplayDateTime = (dateString) => {
    const date = new Date(dateString);
    if (isNaN(date)) return "Không hợp lệ";
    const pad = (n) => String(n).padStart(2, "0");
    return `${pad(date.getDate())}/${pad(date.getMonth() + 1)}/${date.getFullYear()} ${pad(date.getHours())}:${pad(date.getMinutes())}:${pad(date.getSeconds())}`;
  };

  const getStatusBadge = (discount) => {
    if (!discount.status) return "bg-secondary";
    const now = new Date();
    const start = new Date(discount.startDate);
    const end = new Date(discount.endDate);
    if (now < start) return "bg-warning text-dark"; // Sắp diễn ra
    if (now > end) return "bg-secondary"; // Đã kết thúc
    return "bg-success"; // Đang hoạt động
  };

  const getStatusText = (discount) => {
    if (!discount.status) return "Ngừng hoạt động";
    const now = new Date();
    const start = new Date(discount.startDate);
    const end = new Date(discount.endDate);
    if (now < start) return "Sắp diễn ra";
    if (now > end) return "Đã kết thúc";
    return "Đang hoạt động";
  };

  if (loading) {
    return (
      <div className="text-center mt-5">
        <div className="spinner-border text-primary" role="status" />
      </div>
    );
  }

  if (error) {
    return <div className="alert alert-danger mt-4">Lỗi: {error}</div>;
  }

  return (
    <div className="container mt-4">
      <div className="d-flex justify-content-between align-items-center mb-4">
        <h2>Quản lý Khuyến mãi</h2>
        <button className="btn btn-primary" onClick={() => navigate("/Admin/Discount/Create")}>
          Thêm Khuyến mãi
        </button>
      </div>

      <div className="mb-3">
        <input
          type="text"
          className="form-control"
          placeholder="Tìm kiếm khuyến mãi..."
          value={searchTerm}
          onChange={(e) => setSearchTerm(e.target.value)}
        />
      </div>

      <div className="table-responsive">
        <table className="table table-hover align-middle">
          <thead className="table-dark">
            <tr>
              <th>Tên khuyến mãi</th>
              <th>Tỷ lệ (%)</th>
              <th>Ngày bắt đầu</th>
              <th>Ngày kết thúc</th>
              <th>Trạng thái</th>
              <th>Hành động</th>
            </tr>
          </thead>
          <tbody>
            {currentItems.map((discount) => (
              <tr key={discount.id}>
                <td>{discount.name}</td>
                <td>{discount.percentage} %</td>
                <td>{formatDisplayDateTime(discount.startDate)}</td>
                <td>{formatDisplayDateTime(discount.endDate)}</td>
                <td>
                  <span className={`badge ${getStatusBadge(discount)}`}>
                    {getStatusText(discount)}
                  </span>
                </td>
                <td>
                  <div className="d-flex">
                    <button
                      className="btn btn-warning btn-sm me-2"
                      onClick={() => navigate(`/Admin/Discount/Update/${discount.id}`)}
                    >
                      Sửa
                    </button>
                    <button
                      className="btn btn-danger btn-sm"
                      onClick={() => {
                        setDeleteId(discount.id);
                        setShowDeleteConfirmation(true);
                      }}
                    >
                      Xóa
                    </button>
                  </div>
                </td>
              </tr>
            ))}
          </tbody>
        </table>
      </div>

      {/* Pagination */}
      <nav aria-label="Page navigation">
        <ul className="pagination justify-content-center">
          {Array.from({ length: totalPages }, (_, index) => (
            <li
              key={index}
              className={`page-item ${currentPage === index + 1 ? "active" : ""}`}
            >
              <button
                className="page-link"
                onClick={() => handlePageChange(index + 1)}
              >
                {index + 1}
              </button>
            </li>
          ))}
        </ul>
      </nav>

      {/* Modal Xác nhận Xóa */}
      {showDeleteConfirmation && (
        <div className="modal fade show d-block" tabIndex={-1} role="dialog" style={{ backgroundColor: "rgba(0,0,0,0.5)" }}>
          <div className="modal-dialog" role="document">
            <div className="modal-content">
              <div className="modal-header">
                <h5 className="modal-title">Xác nhận xóa</h5>
                <button type="button" className="btn-close" onClick={() => setShowDeleteConfirmation(false)} />
              </div>
              <div className="modal-body">
                <p>Bạn có chắc chắn muốn xóa khuyến mãi này?</p>
              </div>
              <div className="modal-footer">
                <button className="btn btn-secondary" onClick={() => setShowDeleteConfirmation(false)}>Hủy</button>
                <button className="btn btn-danger" onClick={handleDeleteDiscount}>Xóa</button>
              </div>
            </div>
          </div>
        </div>
      )}
    </div>
  );
};

export default DiscountTable;
