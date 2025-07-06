import React, { useState } from "react";
import { useParams, useNavigate } from "react-router-dom";
import { deleteDiscountCombo } from "../../../services/staffService/combodiscountService";

const DiscountDetailDelete = () => {
  const { id } = useParams();
  const navigate = useNavigate();
  const [error, setError] = useState(null);
  const [loading, setLoading] = useState(false);

  const handleConfirmDelete = async () => {
    try {
      setLoading(true);
      setError(null);
      if (!id || isNaN(Number(id))) {
        throw new Error("ID combo giảm giá không hợp lệ");
      }
      await deleteDiscountCombo(Number(id));
      navigate("/Admin/DiscountCombo/List");
    } catch (err) {
      setError(err.message);
    } finally {
      setLoading(false);
    }
  };

  const handleCancel = () => {
    navigate("/Admin/DiscountCombo/List");
  };

  return (
    <div className="modal show d-block" style={{ backgroundColor: "rgba(0, 0, 0, 0.5)" }}>
      <div className="modal-dialog">
        <div className="modal-content">
          <div className="modal-header">
            <h5 className="modal-title">Xác nhận xóa</h5>
            <button type="button" className="btn-close" onClick={handleCancel}></button>
          </div>
          <div className="modal-body">
            {error ? (
              <div className="alert alert-danger">
                {error}
                {error === "Combo giảm giá không tồn tại." && (
                  <button
                    className="btn btn-link"
                    onClick={() => navigate("/Admin/DiscountCombo/List")}
                  >
                    Quay lại danh sách
                  </button>
                )}
              </div>
            ) : (
              <p>Bạn có chắc chắn muốn xóa combo giảm giá này không?</p>
            )}
          </div>
          <div className="modal-footer">
            <button
              type="button"
              className="btn btn-secondary"
              onClick={handleCancel}
              disabled={loading}
            >
              Hủy bỏ
            </button>
            <button
              type="button"
              className="btn btn-danger"
              onClick={handleConfirmDelete}
              disabled={loading}
            >
              {loading ? (
                <>
                  <span className="spinner-border spinner-border-sm me-2"></span>
                  Đang xóa...
                </>
              ) : (
                "Xóa"
              )}
            </button>
          </div>
        </div>
      </div>
    </div>
  );
};

export default DiscountDetailDelete;