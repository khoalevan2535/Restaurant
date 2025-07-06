import React, { useState, useEffect, useCallback } from "react";
import { useNavigate } from "react-router-dom";
import {
  createDiscountCombo,
  getCombosForSelect,
  getDiscountsForSelect,
} from "../../../services/staffService/combodiscountService";

interface FormData {
  comboId: string;
  discountId: string;
  discountPercentage: string;
}

interface ComboOption {
  id: string;
  name: string;
}

interface DiscountOption {
  id: string;
  name: string;
  percentage: string;
}

const DiscountDetailCreate = () => {
  const navigate = useNavigate();

  const [formData, setFormData] = useState<FormData>({
    comboId: "",
    discountId: "",
    discountPercentage: "",
  });

  const [combos, setCombos] = useState<ComboOption[]>([]);
  const [discounts, setDiscounts] = useState<DiscountOption[]>([]);
  const [errors, setErrors] = useState<Record<string, string>>({});
  const [loading, setLoading] = useState(false);
  const [apiError, setApiError] = useState("");

  const fetchData = useCallback(async () => {
    try {
      setLoading(true);
      setApiError("");

      const [comboList, discountList] = await Promise.all([
        getCombosForSelect(),
        getDiscountsForSelect(),
      ]);

      setCombos(Array.isArray(comboList) ? comboList : []);
      setDiscounts(Array.isArray(discountList) ? discountList : []);

      setFormData({
        comboId: "",
        discountId: "",
        discountPercentage: "",
      });
    } catch (error) {
      console.error("Lỗi khi tải dữ liệu:", error.response?.data || error);
      setApiError(
        error.response?.data?.message ||
          error.message ||
          "Không thể tải dữ liệu. Vui lòng thử lại."
      );
    } finally {
      setLoading(false);
    }
  }, []);

  useEffect(() => {
    fetchData();
  }, [fetchData]);

  const handleChange = (e: React.ChangeEvent<HTMLSelectElement>) => {
    const { name, value } = e.target;
    setFormData((prev) => ({ ...prev, [name]: value }));
    setErrors((prev) => ({ ...prev, [name]: "" }));
  };

  const handleDiscountChange = (e: React.ChangeEvent<HTMLSelectElement>) => {
    const discountId = e.target.value;
    const selectedDiscount = discounts.find((d) => d.id.toString() === discountId);
    setFormData((prev) => ({
      ...prev,
      discountId,
      discountPercentage: selectedDiscount?.percentage?.toString() || "",
    }));
    setErrors((prev) => ({ ...prev, discountId: "" }));
  };

  const validateForm = () => {
    const newErrors: Record<string, string> = {};
    if (!formData.comboId || isNaN(Number(formData.comboId))) {
      newErrors.comboId = "Vui lòng chọn combo hợp lệ";
    }
    if (!formData.discountId || isNaN(Number(formData.discountId))) {
      newErrors.discountId = "Vui lòng chọn giảm giá hợp lệ";
    }
    if (
      !formData.discountPercentage ||
      isNaN(Number(formData.discountPercentage)) ||
      Number(formData.discountPercentage) < 0
    ) {
      newErrors.discountPercentage = "Tỷ lệ giảm giá phải là số không âm";
    }
    setErrors(newErrors);
    return Object.keys(newErrors).length === 0;
  };

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();
    if (!validateForm()) return;

    try {
      setLoading(true);
      setApiError("");
      const payload = {
        comboId: Number(formData.comboId),
        discountId: Number(formData.discountId),
        discountPercentage: Number(formData.discountPercentage),
      };

      await createDiscountCombo(payload);
      navigate("/Admin/DiscountCombo/List");
    } catch (error) {
      const errorMessage =
        error.response?.data?.message ||
        error.message ||
        "Không thể lưu dữ liệu. Vui lòng thử lại.";
      console.error("Lỗi API khi gửi form:", error.response?.data || error);
      setApiError(errorMessage);
    } finally {
      setLoading(false);
    }
  };

  if (loading) {
    return (
      <div className="text-center my-4">
        <div className="spinner-border text-primary" role="status">
          <span className="visually-hidden">Đang tải...</span>
        </div>
      </div>
    );
  }

  if (apiError) {
    return (
      <div className="container mt-4">
        <div className="alert alert-danger">
          {apiError}
          <button className="btn btn-link" onClick={fetchData}>
            Thử lại
          </button>
        </div>
      </div>
    );
  }

  return (
    <div className="container mt-4">
      <h2>Thêm Combo Giảm Giá Mới</h2>

      <form onSubmit={handleSubmit} className="mt-3 border p-4 rounded">
        <div className="mb-3">
          <label className="form-label">Combo</label>
          <select
            name="comboId"
            className={`form-select ${errors.comboId ? "is-invalid" : ""}`}
            value={formData.comboId}
            onChange={handleChange}
            disabled={loading}
          >
            <option value="">Chọn combo</option>
            {combos.length > 0 ? (
              combos.map((combo) => (
                <option key={combo.id} value={combo.id.toString()}>
                  {combo.name}
                </option>
              ))
            ) : (
              <option disabled>Đang tải combos hoặc không có combo nào</option>
            )}
          </select>
          {errors.comboId && (
            <div className="invalid-feedback">{errors.comboId}</div>
          )}
        </div>

        <div className="mb-3">
          <label className="form-label">Giảm giá</label>
          <select
            name="discountId"
            className={`form-select ${errors.discountId ? "is-invalid" : ""}`}
            value={formData.discountId}
            onChange={handleDiscountChange}
            disabled={loading}
          >
            <option value="">Chọn giảm giá</option>
            {discounts.length > 0 ? (
              discounts.map((discount) => (
                <option key={discount.id} value={discount.id.toString()}>
                  {discount.name} ({discount.percentage}%)
                </option>
              ))
            ) : (
              <option disabled>Đang tải giảm giá hoặc không có giảm giá nào</option>
            )}
          </select>
          {errors.discountId && (
            <div className="invalid-feedback">{errors.discountId}</div>
          )}
        </div>

        <div className="mb-3">
          <label className="form-label">Tỷ lệ giảm giá (%)</label>
          <input
            type="text"
            className={`form-control ${
              errors.discountPercentage ? "is-invalid" : ""
            }`}
            value={formData.discountPercentage || ""}
            readOnly
          />
          {errors.discountPercentage && (
            <div className="invalid-feedback">{errors.discountPercentage}</div>
          )}
        </div>

        <div className="d-flex justify-content-end gap-2">
          <button
            type="button"
            className="btn btn-secondary"
            onClick={() => navigate("/Admin/DiscountDetail/List")}
            disabled={loading}
          >
            Hủy bỏ
          </button>
          <button type="submit" className="btn btn-primary" disabled={loading}>
            {loading ? (
              <>
                <span className="spinner-border spinner-border-sm me-2"></span>
                Đang lưu...
              </>
            ) : "Thêm mới"}
          </button>
        </div>
      </form>
    </div>
  );
};

export default DiscountDetailCreate;