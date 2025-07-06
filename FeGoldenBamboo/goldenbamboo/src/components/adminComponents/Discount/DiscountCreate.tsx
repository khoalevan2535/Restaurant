import React, { useState } from "react";
import { useNavigate } from "react-router-dom";
import * as discountService from "../../../services/staffService/discountService";

const DiscountCreate: React.FC = () => {
  const navigate = useNavigate();

  const [formState, setFormState] = useState({
    name: "",
    percentage: "",
    startDate: "",
    endDate: "",
    status: true,
  });

  const [errors, setErrors] = useState({
    name: "",
    percentage: "",
    startDate: "",
    endDate: "",
  });

  const [error, setError] = useState<string | null>(null);

  const handleInputChange = (
    e: React.ChangeEvent<HTMLInputElement | HTMLSelectElement>
  ) => {
    const { name, value } = e.target;
    setFormState((prev) => ({
      ...prev,
      [name]: name === "status" ? value === "true" : value,
    }));
  };

  // ✅ Đã sửa: không trừ timezoneOffset thủ công nữa
  const toUTCISOString = (localDateTimeString: string) => {
    if (!localDateTimeString) return "";
    const date = new Date(localDateTimeString);
    if (isNaN(date.getTime())) return "";
    return date.toISOString(); // giữ chính xác thời gian bạn chọn
  };

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();
    setErrors({ name: "", percentage: "", startDate: "", endDate: "" });

    let hasError = false;

    if (!formState.name.trim()) {
      setErrors((prev) => ({ ...prev, name: "Tên khuyến mãi không được để trống." }));
      hasError = true;
    }

    const percentage = Number(formState.percentage);
    if (isNaN(percentage) || percentage < 0 || percentage > 100) {
      setErrors((prev) => ({
        ...prev,
        percentage: "Tỷ lệ giảm giá phải từ 0 đến 100.",
      }));
      hasError = true;
    }

    if (!formState.startDate) {
      setErrors((prev) => ({
        ...prev,
        startDate: "Ngày bắt đầu không được để trống.",
      }));
      hasError = true;
    }

    if (!formState.endDate) {
      setErrors((prev) => ({ ...prev, endDate: "Ngày kết thúc không được để trống." }));
      hasError = true;
    }

    if (formState.startDate && formState.endDate) {
      const start = new Date(formState.startDate);
      const end = new Date(formState.endDate);
      if (start >= end) {
        setErrors((prev) => ({
          ...prev,
          endDate: "Ngày kết thúc phải sau ngày bắt đầu.",
        }));
        hasError = true;
      }
    }

    if (hasError) return;

    try {
      const discountData = {
        name: formState.name,
        percentage,
        startDate: toUTCISOString(formState.startDate),
        endDate: toUTCISOString(formState.endDate),
        status: formState.status,
      };

      await discountService.createDiscount(discountData);
      navigate("/Admin/Discount/List");
    } catch (err) {
      setError((err as Error).message || "Lỗi khi tạo khuyến mãi");
    }
  };

  const handleCancel = () => {
    navigate("/Admin/Discount/List");
  };

  return (
    <div className="container mt-4">
      <h4>Thêm Khuyến mãi mới</h4>
      {error && <div className="alert alert-danger">Lỗi: {error}</div>}

      <form onSubmit={handleSubmit} className="border p-4 rounded shadow-sm bg-light">
        <div className="mb-3">
          <label className="form-label">Tên khuyến mãi</label>
          <input
            type="text"
            className={`form-control ${errors.name ? "is-invalid" : ""}`}
            name="name"
            value={formState.name}
            onChange={handleInputChange}
          />
          {errors.name && <div className="invalid-feedback">{errors.name}</div>}
        </div>

        <div className="mb-3">
          <label className="form-label">Tỷ lệ giảm giá (%)</label>
          <input
            type="number"
            min="0"
            max="100"
            className={`form-control ${errors.percentage ? "is-invalid" : ""}`}
            name="percentage"
            value={formState.percentage}
            onChange={handleInputChange}
          />
          {errors.percentage && (
            <div className="invalid-feedback">{errors.percentage}</div>
          )}
        </div>

        <div className="row mb-3">
          <div className="col-md-6">
            <label className="form-label">Ngày bắt đầu</label>
            <input
              type="datetime-local"
              step="1"
              className={`form-control ${errors.startDate ? "is-invalid" : ""}`}
              name="startDate"
              value={formState.startDate}
              onChange={handleInputChange}
            />
            {errors.startDate && (
              <div className="invalid-feedback">{errors.startDate}</div>
            )}
          </div>

          <div className="col-md-6">
            <label className="form-label">Ngày kết thúc</label>
            <input
              type="datetime-local"
              step="1"
              className={`form-control ${errors.endDate ? "is-invalid" : ""}`}
              name="endDate"
              value={formState.endDate}
              onChange={handleInputChange}
            />
            {errors.endDate && (
              <div className="invalid-feedback">{errors.endDate}</div>
            )}
          </div>
        </div>

        <div className="mb-3">
          <label className="form-label">Trạng thái</label>
          <select
            className="form-control"
            name="status"
            value={formState.status ? "true" : "false"}
            onChange={handleInputChange}
          >
            <option value="true">Hoạt động</option>
            <option value="false">Ngừng hoạt động</option>
          </select>
        </div>

        <div className="d-flex justify-content-end">
          <button type="button" className="btn btn-secondary me-2" onClick={handleCancel}>
            Hủy
          </button>
          <button type="submit" className="btn btn-success">
            Thêm mới
          </button>
        </div>
      </form>
    </div>
  );
};

export default DiscountCreate;
