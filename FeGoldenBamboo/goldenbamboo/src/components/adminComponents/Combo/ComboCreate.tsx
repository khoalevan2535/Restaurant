import React, { useState } from "react";
import { useNavigate } from "react-router-dom";
import { createCombo } from "../../../services/staffService/comboService";

const ComboCreate: React.FC = () => {
  const navigate = useNavigate();
  const [formState, setFormState] = useState({
    name: "",
    price: "",
    description: "",
    status: "active",
    image: null as File | null,
    currentImage: null as string | null,
    imageName: "Chưa chọn file",
  });

  const [errors, setErrors] = useState({
    name: "",
    price: "",
    description: "",
  });
  const [error, setError] = useState<string | null>(null);

  const handleInputChange = (e: React.ChangeEvent<any>) => {
    const { name, value } = e.target;
    setFormState((prev) => ({ ...prev, [name]: value }));
  };

  const handleFileChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    const file = e.target.files?.[0];
    setFormState((prev) => ({
      ...prev,
      image: file || null,
      currentImage: file ? URL.createObjectURL(file) : null,
      imageName: file ? file.name : "Chưa chọn file",
    }));
  };

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();
    setErrors({ name: "", price: "", description: "" });

    let hasError = false;
    if (!formState.name) {
      setErrors((prev) => ({ ...prev, name: "Tên combo không được để trống." }));
      hasError = true;
    }

    const priceValue = parseFloat(formState.price);
    if (isNaN(priceValue) || priceValue < 0) {
      setErrors((prev) => ({ ...prev, price: "Giá phải lớn hơn hoặc bằng 0." }));
      hasError = true;
    } else {
      formState.price = priceValue.toFixed(2);
    }

    if (!formState.description) {
      setErrors((prev) => ({ ...prev, description: "Mô tả không được để trống." }));
      hasError = true;
    }

    if (hasError) return;

    try {
      const formData = new FormData();
      formData.append("name", formState.name);
      formData.append("price", formState.price);
      formData.append("description", formState.description);
      formData.append("status", formState.status === "active" ? "true" : "false");
      if (formState.image) formData.append("image", formState.image);

      await createCombo(formData);
      navigate("/Admin/Combo/List");
    } catch (err) {
      setError((err as Error).message);
    }
  };

  return (
    <div className="container mt-4">
      <h4>Thêm Combo mới</h4>
      {error && <div className="alert alert-danger">Lỗi: {error}</div>}
      <form onSubmit={handleSubmit} className="border p-4 rounded shadow-sm bg-light">
        <div className="mb-3">
          <label htmlFor="name" className="form-label">Tên Combo</label>
          <input
            type="text"
            className={`form-control ${errors.name && "is-invalid"}`}
            id="name"
            name="name"
            value={formState.name}
            onChange={handleInputChange}
          />
          {errors.name && <div className="invalid-feedback">{errors.name}</div>}
        </div>

        <div className="mb-3">
          <label htmlFor="price" className="form-label">Giá (VND)</label>
          <input
            type="number"
            className={`form-control ${errors.price && "is-invalid"}`}
            id="price"
            name="price"
            value={formState.price}
            onChange={handleInputChange}
          />
          {errors.price && <div className="invalid-feedback">{errors.price}</div>}
        </div>

        <div className="mb-3">
          <label htmlFor="description" className="form-label">Mô tả</label>
          <textarea
            className={`form-control ${errors.description && "is-invalid"}`}
            id="description"
            name="description"
            rows={3}
            value={formState.description}
            onChange={handleInputChange}
          />
          {errors.description && <div className="invalid-feedback">{errors.description}</div>}
        </div>

        <div className="mb-3">
          <label htmlFor="status" className="form-label">Trạng thái</label>
          <select
            className="form-select"
            id="status"
            name="status"
            value={formState.status}
            onChange={handleInputChange}
          >
            <option value="active">Hoạt động</option>
            <option value="inactive">Ngừng hoạt động</option>
          </select>
        </div>

        <div className="mb-3">
          <label className="form-label">Ảnh đại diện</label>
          <input
            type="file"
            className="form-control"
            accept="image/*"
            onChange={handleFileChange}
          />
          {formState.currentImage && (
            <div className="mt-2">
              <img
                src={formState.currentImage}
                alt="Preview"
                className="img-thumbnail"
                style={{ width: "150px", height: "150px", objectFit: "cover" }}
              />
            </div>
          )}
        </div>

        <button type="submit" className="btn btn-primary">Tạo Combo</button>
      </form>
    </div>
  );
};

export default ComboCreate;
