import React, { useState, useEffect } from "react";
import { useNavigate } from "react-router-dom";
import {
  getDiscountCombos,
  deleteDiscountCombo,
  getCombosForSelect,
  getDiscountsForSelect,
} from "../../../services/staffService/combodiscountService";

const DiscountComboTable = () => {
  const [discountCombos, setDiscountCombos] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);
  const [searchTerm, setSearchTerm] = useState("");
  const [currentPage, setCurrentPage] = useState(1);
  const [itemsPerPage] = useState(5);
  const [showDeleteConfirmation, setShowDeleteConfirmation] = useState(false);
  const [deleteId, setDeleteId] = useState(null);
  const navigate = useNavigate();

  useEffect(() => {
    fetchData();
  }, []);

  const fetchData = async () => {
    try {
      setLoading(true);
      setError(null);
      const [combos, discounts, comboDiscounts] = await Promise.all([
        getCombosForSelect(),
        getDiscountsForSelect(),
        getDiscountCombos(),
      ]);

      const enrichedData = comboDiscounts.map((item) => {
        const combo = combos.find((c) => c.id === item.comboId) || {};
        const discount = discounts.find((d) => d.id === item.discountId) || {};
        return {
          ...item,
          comboName: combo.name || `Combo #${item.comboId}`,
          discountName: discount.name || `Discount #${item.discountId}`,
          discountPercentage: discount.percentage || item.discountPercentage || 0,
        };
      });

      setDiscountCombos(enrichedData);
    } catch (error) {
      console.error("Lỗi khi tải danh sách:", error.response?.data || error);
      setError(
        error.response?.data?.message ||
          error.message ||
          "Không thể tải dữ liệu. Vui lòng thử lại."
      );
    } finally {
      setLoading(false);
    }
  };

  const handleDelete = async (id) => {
    if (!id || isNaN(id)) {
      setError("ID combo không hợp lệ");
      setShowDeleteConfirmation(false); // Close modal on invalid ID
      setDeleteId(null);
      return;
    }

    try {
      setLoading(true);
      setError(null);
      await deleteDiscountCombo(id);
      await fetchData(); // Refetch data after successful deletion
      setShowDeleteConfirmation(false);
      setDeleteId(null);
    } catch (error) {
      console.error("Lỗi khi xóa:", error.response?.data || error);
      setError(
        error.response?.data?.message ||
          error.message ||
          "Không thể xóa combo giảm giá. Vui lòng thử lại."
      );
    } finally {
      setLoading(false);
    }
  };

  const filteredData = discountCombos.filter(
    (item) =>
      item.comboName.toLowerCase().includes(searchTerm.toLowerCase()) ||
      item.discountName.toLowerCase().includes(searchTerm.toLowerCase())
  );

  const indexOfLastItem = currentPage * itemsPerPage;
  const indexOfFirstItem = indexOfLastItem - itemsPerPage;
  const currentItems = filteredData.slice(indexOfFirstItem, indexOfLastItem);
  const totalPages = Math.ceil(filteredData.length / itemsPerPage);

  if (loading) {
    return (
      <div className="text-center my-4">
        <div className="spinner-border text-primary" role="status">
          <span className="visually-hidden">Đang tải...</span>
        </div>
      </div>
    );
  }

  if (error) {
    return (
      <div className="container mt-4">
        <div className="alert alert-danger">
          {error}
          <button className="btn btn-link" onClick={fetchData}>
            Thử lại
          </button>
        </div>
      </div>
    );
  }

  return (
    <div className="container mt-4">
      <div className="d-flex justify-content-between align-items-center mb-4">
        <h2>Quản lý Combo Giảm Giá</h2>
        <button
          className="btn btn-primary"
          onClick={() => navigate("/Admin/DiscountDetail/Create")}
        >
          Thêm Combo Giảm Giá
        </button>
      </div>

      <div className="mb-3">
        <input
          type="text"
          className="form-control"
          placeholder="Tìm kiếm theo tên combo hoặc giảm giá..."
          value={searchTerm}
          onChange={(e) => setSearchTerm(e.target.value)}
        />
      </div>

      {showDeleteConfirmation && (
        <div
          className="modal show d-block"
          style={{ backgroundColor: "rgba(0,0,0,0.5)" }}
          tabIndex="-1"
        >
          <div className="modal-dialog">
            <div className="modal-content">
              <div className="modal-header">
                <h5 className="modal-title">Xác nhận xóa</h5>
                <button
                  type="button"
                  className="btn-close"
                  onClick={() => {
                    setShowDeleteConfirmation(false);
                    setDeleteId(null);
                  }}
                ></button>
              </div>
              <div className="modal-body">
                <p>Bạn có chắc chắn muốn xóa combo giảm giá này không?</p>
              </div>
              <div className="modal-footer">
                <button
                  className="btn btn-secondary"
                  onClick={() => {
                    setShowDeleteConfirmation(false);
                    setDeleteId(null);
                  }}
                >
                  Hủy
                </button>
                <button
                  className="btn btn-danger"
                  onClick={() => handleDelete(deleteId)}
                >
                  Xóa
                </button>
              </div>
            </div>
          </div>
        </div>
      )}

      <div className="table-responsive">
        <table className="table table-hover">
          <thead className="table-dark">
            <tr>
              <th>Tên Combo</th>
              <th>Tên Giảm Giá</th>
              <th>Giảm Giá (%)</th>
              <th>Hành Động</th>
            </tr>
          </thead>
          <tbody>
            {currentItems.map((item) => (
              <tr key={item.id}>
                <td>{item.comboName}</td>
                <td>{item.discountName}</td>
                <td>{item.discountPercentage}%</td>
                <td>
                  <div className="d-flex gap-2">
                    <button
                      className="btn btn-sm btn-warning"
                      onClick={() =>
                        navigate(`/Admin/DiscountDetail/Update/${item.id}`)
                      }
                    >
                      Sửa
                    </button>
                    <button
                      className="btn btn-sm btn-danger"
                      onClick={() => {
                        setDeleteId(item.id);
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

      {filteredData.length === 0 && (
        <div className="alert alert-info mt-3">Không tìm thấy kết quả phù hợp</div>
      )}

      {totalPages > 1 && (
        <nav className="mt-3">
          <ul className="pagination justify-content-center">
            {Array.from({ length: totalPages }, (_, i) => (
              <li
                key={i}
                className={`page-item ${currentPage === i + 1 ? "active" : ""}`}
              >
                <button
                  className="page-link"
                  onClick={() => setCurrentPage(i + 1)}
                >
                  {i + 1}
                </button>
              </li>
            ))}
          </ul>
        </nav>
      )}
    </div>
  );
};

export default DiscountComboTable;