// components/adminComponents/Combo/ComboTable.js
import React, { useState, useEffect } from "react";
import { useNavigate } from "react-router-dom";
import { getCombos, deleteCombo } from "../../../services/staffService/comboService";
import { getAllDiscountCombos } from "../../../services/staffService/combodiscountService";

const ComboTable = () => {
  const [combos, setCombos] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);
  const [searchTerm, setSearchTerm] = useState("");
  const [currentPage, setCurrentPage] = useState(1);
  const [itemsPerPage] = useState(5);
  const [showDeleteConfirmation, setShowDeleteConfirmation] = useState(false);
  const [deleteId, setDeleteId] = useState(null);
  const navigate = useNavigate();

  useEffect(() => {
    fetchCombos();
  }, []);

  const fetchCombos = async () => {
    try {
      const [comboData, discountComboData] = await Promise.all([
        getCombos(),
        getAllDiscountCombos(),
      ]);

      const combosWithDiscount = comboData.map((combo) => {
        const discount = discountComboData.find((dc) => dc.comboId === combo.id);
        const discountPercentage = discount ? discount.discountPercentage : 0;
        const discountedPrice = combo.price * (1 - discountPercentage / 100);

        return {
          ...combo,
          discountPercentage,
          discountedPrice,
        };
      });

      setCombos(combosWithDiscount);
    } catch (err) {
      setError(err.message);
    } finally {
      setLoading(false);
    }
  };

  const handleDelete = async (id) => {
    setLoading(true);
    try {
      await deleteCombo(id);
      fetchCombos();
      setShowDeleteConfirmation(false);
    } catch (err) {
      setError(err.message);
    } finally {
      setLoading(false);
    }
  };

  const filteredCombos = combos.filter((combo) =>
    combo.name.toLowerCase().includes(searchTerm.toLowerCase())
  );

  const indexOfLastCombo = currentPage * itemsPerPage;
  const indexOfFirstCombo = indexOfLastCombo - itemsPerPage;
  const currentCombos = filteredCombos.slice(indexOfFirstCombo, indexOfLastCombo);
  const totalPages = Math.ceil(filteredCombos.length / itemsPerPage);

  const handlePageChange = (pageNumber) => {
    setCurrentPage(pageNumber);
  };

  if (loading) {
    return (
      <div className="text-center">
        <div className="spinner-border text-primary"></div>
      </div>
    );
  }

  if (error) {
    return <div className="alert alert-danger">Lỗi: {error}</div>;
  }

  return (
    <div className="container mt-4">
      <div className="d-flex justify-content-between align-items-center mb-4">
        <h2>Quản lý Combo</h2>
        <button className="btn btn-primary" onClick={() => navigate("/Admin/Combo/Create")}>
          Thêm Combo
        </button>
      </div>

      <div className="mb-3">
        <div className="input-group">
          <input
            type="text"
            className="form-control"
            placeholder="Tìm kiếm theo tên combo..."
            value={searchTerm}
            onChange={(e) => setSearchTerm(e.target.value)}
          />
        </div>
      </div>

      {showDeleteConfirmation && (
        <div className="modal show d-block" style={{ backgroundColor: "rgba(0, 0, 0, 0.5)" }}>
          <div className="modal-dialog">
            <div className="modal-content">
              <div className="modal-header">
                <h5 className="modal-title">Xác nhận</h5>
                <button
                  type="button"
                  className="btn-close"
                  onClick={() => setShowDeleteConfirmation(false)}
                ></button>
              </div>
              <div className="modal-body">
                <p>Bạn có chắc chắn muốn xóa combo này không?</p>
              </div>
              <div className="modal-footer">
                <button
                  type="button"
                  className="btn btn-secondary"
                  onClick={() => setShowDeleteConfirmation(false)}
                >
                  Hủy bỏ
                </button>
                <button
                  type="button"
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
        <table className="table table-hover align-middle">
          <thead className="table-dark">
            <tr>
              <th>Tên</th>
              <th>Giá</th>
              <th>Mô tả</th>
              <th>Trạng thái</th>
              <th>Ảnh</th>
              <th>Hành động</th>
            </tr>
          </thead>
          <tbody>
            {currentCombos.map((combo) => (
              <tr key={combo.id}>
                <td>{combo.name}</td>
                <td>
                  {combo.discountPercentage > 0 ? (
                    <>
                      <span className="text-danger fw-bold">
                        {combo.discountedPrice.toLocaleString()} VND
                      </span>
                      <br />
                      <small className="text-muted text-decoration-line-through">
                        {combo.price.toLocaleString()} VND
                      </small>
                      <br />
                      <span className="badge bg-warning text-dark">
                        -{combo.discountPercentage}%
                      </span>
                    </>
                  ) : (
                    <>{combo.price.toLocaleString()} VND</>
                  )}
                </td>
                <td
                  style={{ maxWidth: "200px" }}
                  className="text-truncate"
                  title={combo.description}
                >
                  {combo.description}
                </td>
                <td>
                  <span
                    className={`badge ${combo.status ? "bg-success" : "bg-secondary"}`}
                  >
                    {combo.status ? "Hoạt động" : "Ngừng hoạt động"}
                  </span>
                </td>
                <td>
                  {combo.image ? (
                    <img
                      src={combo.image}
                      alt={combo.name}
                      className="img-thumbnail"
                      style={{ width: "70px", height: "70px", objectFit: "cover" }}
                    />
                  ) : (
                    <span className="text-secondary">[Không có ảnh]</span>
                  )}
                </td>
                <td>
                  <div className="d-flex">
                    <button
                      className="btn btn-warning btn-sm me-2"
                      onClick={() => navigate(`/Admin/Combo/Update/${combo.id}`)}
                      title="Chỉnh sửa"
                    >
                      Sửa
                    </button>
                    <button
                      className="btn btn-danger btn-sm"
                      onClick={() => {
                        setDeleteId(combo.id);
                        setShowDeleteConfirmation(true);
                      }}
                      title="Xóa"
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
    </div>
  );
};

export default ComboTable;