// components/adminComponents/Combo/ComboDelete.js
import React, { useState } from "react";
import { useParams, useNavigate } from "react-router-dom";
import { deleteCombo } from "../../../services/staffService/comboService";

const ComboDelete = () => {
  const { id } = useParams();
  const navigate = useNavigate();
  const [error, setError] = useState(null);

  const handleConfirmDelete = async () => {
    try {
      await deleteCombo(Number(id));
      navigate("/Admin/Combo/List");
    } catch (err) {
      setError(err.message);
    }
  };

  const handleCancel = () => {
    navigate("/Admin/Combo/List");
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
              <div className="alert alert-danger">Lỗi: {error}</div>
            ) : (
              <p>Bạn có chắc chắn muốn xóa combo này không?</p>
            )}
          </div>
          <div className="modal-footer">
            <button type="button" className="btn btn-secondary" onClick={handleCancel}>
              Hủy bỏ
            </button>
            <button type="button" className="btn btn-danger" onClick={handleConfirmDelete}>
              Xóa
            </button>
          </div>
        </div>
      </div>
    </div>
  );
};

export default ComboDelete;