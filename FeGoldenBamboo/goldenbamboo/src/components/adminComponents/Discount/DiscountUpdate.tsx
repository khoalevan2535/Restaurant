import React, { useEffect, useState } from "react";
import { useParams, useNavigate } from "react-router-dom";
import * as discountService from "../../../services/staffService/discountService";

const DiscountUpdate: React.FC = () => {
  const { id } = useParams<{ id?: string }>();
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

  useEffect(() => {
    const fetchData = async () => {
      try {
        if (id) {
          const discount = await discountService.getDiscountById(Number(id));
          setFormState({
            name: discount.name,
            percentage: discount.percentage.toString(),
            startDate: convertToDateTimeLocal(discount.startDate),
            endDate: convertToDateTimeLocal(discount.endDate),
            status: discount.status,
          });
        }
      } catch (err) {
        setError((err as Error).message);
      }
    };
    fetchData();
  }, [id]);

  const convertToDateTimeLocal = (dateString: string) => {
    const date = new Date(dateString);
    const pad = (num: number) => String(num).padStart(2, "0");
    return `${date.getFullYear()}-${pad(date.getMonth() + 1)}-${pad(date.getDate())}T${pad(date.getHours())}:${pad(date.getMinutes())}:${pad(date.getSeconds())}`;
  };

  const toISOStringWithLocalOffset = (localDateTimeString: string) => {
    const date = new Date(localDateTimeString);
    return new Date(date.getTime() - date.getTimezoneOffset() * 60000).toISOString();
  };

  const handleInputChange = (
    e: React.ChangeEvent<HTMLInputElement | HTMLSelectElement>
  ) => {
    const { name, value } = e.target;
    setFormState((prev) => ({
      ...prev,
      [name]: name === "status" ? value === "true" : value,
    }));
  };

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();
    setErrors({ name: "", percentage: "", startDate: "", endDate: "" });

    let hasError = false;

    if (!formState.name) {
      setErrors((prev) => ({ ...prev, name: "Tên khuyến mãi không được để trống." }));
      hasError = true;
    }

    const percentage = Number(formState.percentage);
    if (isNaN(percentage) || percentage < 0 || percentage > 100) {
      setErrors((prev) => ({ ...prev, percentage: "Tỷ lệ giảm giá phải từ 0 đến 100." }));
      hasError = true;
    }

    if (!formState.startDate) {
      setErrors((prev) => ({ ...prev, startDate: "Ngày bắt đầu không được để trống." }));
      hasError = true;
    }

    if (!formState.endDate) {
      setErrors((prev) => ({ ...prev, endDate: "Ngày kết thúc không được để trống." }));
      hasError = true;
    }

    if (formState.startDate && formState.endDate) {
      if (new Date(formState.startDate) >= new Date(formState.endDate)) {
        setErrors((prev) => ({ ...prev, endDate: "Ngày kết thúc phải sau ngày bắt đầu." }));
        hasError = true;
      }
    }

    if (hasError || !id) return;

    try {
      const updatedDiscount = {
        name: formState.name,
        percentage: percentage,
        startDate: toISOStringWithLocalOffset(formState.startDate),
        endDate: toISOStringWithLocalOffset(formState.endDate),
        status: formState.status,
      };

      await discountService.updateDiscount(Number(id), updatedDiscount);
      navigate("/Admin/Discount/List");
    } catch (err) {
      setError((err as Error).message);
    }
  };

  const handleCancel = () => {
    navigate("/Admin/Discount/List");
  };

  return (
    <div className="container mt-4">
      <h4>Cập nhật Khuyến mãi</h4>
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
          {errors.percentage && <div className="invalid-feedback">{errors.percentage}</div>}
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
            {errors.startDate && <div className="invalid-feedback">{errors.startDate}</div>}
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
            {errors.endDate && <div className="invalid-feedback">{errors.endDate}</div>}
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
          <button type="submit" className="btn btn-primary">
            Cập nhật
          </button>
        </div>
      </form>
    </div>
  );
};

export default DiscountUpdate;
