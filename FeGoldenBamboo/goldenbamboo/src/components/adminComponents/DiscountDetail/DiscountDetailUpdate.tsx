import React, { useState, useEffect, useCallback } from "react";
import { useParams, useNavigate } from "react-router-dom";
import {
  getDiscountComboById,
  updateDiscountCombo,
  getCombosForSelect,
  getDiscountsForSelect,
} from "../../../services/staffService/combodiscountService";

interface FormData {
  comboId: string;
  discountId: string;
  discountPercentage: string;
  comboName: string;
  discountName: string;
}

interface ComboOption {
  id: string | number;
  name: string;
}

interface DiscountOption {
  id: string | number;
  name: string;
  percentage: string | number;
}

const DiscountDetailUpdate = () => {
  const { discountDetailId } = useParams<{ discountDetailId: string }>(); // ✅ Đúng tên param
  const navigate = useNavigate();

  const [formData, setFormData] = useState<FormData>({
    comboId: "",
    discountId: "",
    discountPercentage: "",
    comboName: "Đang tải...",
    discountName: "Đang tải...",
  });

  const [combos, setCombos] = useState<ComboOption[]>([]);
  const [discounts, setDiscounts] = useState<DiscountOption[]>([]);
  const [errors, setErrors] = useState<Record<string, string>>({});
  const [loading, setLoading] = useState(false);
  const [apiError, setApiError] = useState("");

  const normalizeId = (id: any): string => (id == null ? "" : id.toString());

  const fetchData = useCallback(async () => {
    if (!discountDetailId || isNaN(Number(discountDetailId))) {
      setApiError("ID không hợp lệ.");
      return;
    }

    try {
      setLoading(true);
      const [comboList, discountList, existingData] = await Promise.all([
        getCombosForSelect(),
        getDiscountsForSelect(),
        getDiscountComboById(discountDetailId), // ✅ Sử dụng discountDetailId
      ]);

      if (!existingData) {
        setApiError("Không tìm thấy combo giảm giá.");
        return;
      }

      const currentComboId = normalizeId(existingData.comboId);
      const currentDiscountId = normalizeId(existingData.discountId);

      const selectedCombo = comboList.find(
        (c) => normalizeId(c.id) === currentComboId
      );
      const selectedDiscount = discountList.find(
        (d) => normalizeId(d.id) === currentDiscountId
      );

      setCombos(comboList);
      setDiscounts(discountList);

      setFormData({
        comboId: currentComboId,
        discountId: currentDiscountId,
        discountPercentage:
          selectedDiscount?.percentage?.toString() ??
          existingData.discountPercentage?.toString() ??
          "",
        comboName: selectedCombo?.name || "",
        discountName: selectedDiscount?.name || "",
      });
    } catch (error) {
      console.error("Lỗi khi tải dữ liệu:", error);
      setApiError(
        (error as any)?.response?.data?.message ||
          (error as any)?.message ||
          "Không thể tải dữ liệu. Vui lòng thử lại."
      );
    } finally {
      setLoading(false);
    }
  }, [discountDetailId]);

  useEffect(() => {
    fetchData();
  }, [fetchData]);

  const handleComboChange = (e: React.ChangeEvent<HTMLSelectElement>) => {
    const comboId = e.target.value;
    const selectedCombo = combos.find((c) => normalizeId(c.id) === comboId);

    setFormData((prev) => ({
      ...prev,
      comboId,
      comboName: selectedCombo?.name || `Combo #${comboId}`,
    }));
    setErrors((prev) => ({ ...prev, comboId: "" }));
  };

  const handleDiscountChange = (e: React.ChangeEvent<HTMLSelectElement>) => {
    const discountId = e.target.value;
    const selectedDiscount = discounts.find(
      (d) => normalizeId(d.id) === discountId
    );

    setFormData((prev) => ({
      ...prev,
      discountId,
      discountPercentage: selectedDiscount?.percentage?.toString() || "",
      discountName: selectedDiscount?.name || `Discount #${discountId}`,
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

      await updateDiscountCombo(discountDetailId!, payload); // ✅ Dùng đúng id
      navigate("/Admin/DiscountCombo/List");
    } catch (error) {
      console.error("Lỗi API:", error);
      setApiError(
        (error as any)?.response?.data?.message ||
          (error as any)?.message ||
          "Không thể lưu dữ liệu."
      );
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
      <div className="card">
        <div className="card-header bg-primary text-white">
          <h2 className="mb-0">Chỉnh sửa Combo Giảm Giá</h2>
        </div>
        <div className="card-body">
          <div className="mb-4 p-3 bg-light rounded">
            <h5>Thông tin hiện tại</h5>
            <div className="row">
              <div className="col-md-4">
                <p>
                  <strong>Combo:</strong> {formData.comboName}
                </p>
              </div>
              <div className="col-md-4">
                <p>
                  <strong>Giảm giá:</strong> {formData.discountName}
                </p>
              </div>
              <div className="col-md-4">
                <p>
                  <strong>Tỷ lệ (%):</strong> {formData.discountPercentage}
                </p>
              </div>
            </div>
          </div>

          <form onSubmit={handleSubmit}>
            <div className="row mb-3">
              <div className="col-md-6">
                <label htmlFor="combo-select" className="form-label">
                  Combo
                </label>
                <select
                  id="combo-select"
                  className={`form-select ${errors?.comboId ? "is-invalid" : ""}`}
                  value={formData.comboId || ""}
                  onChange={handleComboChange}
                >
                  <option value="">-- Chọn combo --</option>
                  {combos?.map((combo) => (
                    <option key={combo.id} value={normalizeId(combo.id)}>
                      {combo.name}
                    </option>
                  ))}
                </select>
                {errors?.comboId && (
                  <div className="invalid-feedback">{errors.comboId}</div>
                )}
              </div>

              <div className="col-md-6">
                <label htmlFor="discount-select" className="form-label">
                  Giảm giá
                </label>
                <select
                  id="discount-select"
                  className={`form-select ${errors?.discountId ? "is-invalid" : ""}`}
                  value={formData.discountId || ""}
                  onChange={handleDiscountChange}
                >
                  <option value="">-- Chọn giảm giá --</option>
                  {discounts?.map((discount) => (
                    <option key={discount.id} value={normalizeId(discount.id)}>
                      {discount.name} ({discount.percentage}%)
                    </option>
                  ))}
                </select>
                {errors?.discountId && (
                  <div className="invalid-feedback">{errors.discountId}</div>
                )}
              </div>
            </div>

            <div className="mb-3">
              <label className="form-label">Tỷ lệ giảm giá (%)</label>
              <input
                type="text"
                className={`form-control ${
                  errors.discountPercentage ? "is-invalid" : ""
                }`}
                value={formData.discountPercentage}
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
              >
                Hủy
              </button>
              <button type="submit" className="btn btn-primary">
                Cập nhật
              </button>
            </div>
          </form>
        </div>
      </div>
    </div>
  );
};

export default DiscountDetailUpdate;
