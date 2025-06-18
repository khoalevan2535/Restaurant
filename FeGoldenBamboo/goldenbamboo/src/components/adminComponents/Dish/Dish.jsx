import React, { useEffect, useState } from "react";
import { getAllDishes, createDish, updateDish, deleteDish } from "../services/dishService";
import { getAllCategories } from "../services/cateroryService";

function DishList() {
  const [dishes, setDishes] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);

  // Dữ liệu form dùng chung cho Thêm & Cập nhật
  const [dishForm, setDishForm] = useState({
    id: null,
    name: "",
    price: "",
    description: "",
    category_id: ""
  });
  const [imageFile, setImageFile] = useState(null);
  const [isEditing, setIsEditing] = useState(false);
  const [formError, setFormError] = useState(null);
  const [submitting, setSubmitting] = useState(false);

  // Categories từ backend
  const [categories, setCategories] = useState([]);

  useEffect(() => {
    fetchDishes();
    fetchCategories();
  }, []);

  const fetchDishes = async () => {
    try {
      const response = await getAllDishes();
      setDishes(response.data);
    } catch (err) {
      setError(err);
    }
    setLoading(false);
  };

  const fetchCategories = async () => {
    try {
      const response = await getAllCategories();
      setCategories(response.data);
    } catch (err) {
      console.error("Error fetching categories:", err);
    }
  };

  const handleInputChange = (e) => {
    const { name, value } = e.target;
    setDishForm((prev) => ({ ...prev, [name]: value }));
  };

  const handleFileChange = (e) => {
    setImageFile(e.target.files[0]);
  };

  const resetForm = () => {
    setDishForm({ id: null, name: "", price: "", description: "", category_id: "" });
    setImageFile(null);
    setIsEditing(false);
    setFormError(null);
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    setSubmitting(true);
    setFormError(null);

    const trimmedName = dishForm.name.trim();
    const trimmedPrice = dishForm.price.toString().trim();

    // Kiểm tra dữ liệu bắt buộc
    if (!trimmedName || !trimmedPrice || !dishForm.category_id) {
      setFormError({ message: "Vui lòng điền đầy đủ thông tin bắt buộc." });
      setSubmitting(false);
      return;
    }

    // Kiểm tra trùng tên món ăn
    const duplicate = dishes.find(
      (d) =>
        d.name.trim().toLowerCase() === trimmedName.toLowerCase() &&
        (!isEditing || d.id !== dishForm.id)
    );

    if (duplicate) {
      setFormError({ message: "Tên món ăn đã tồn tại. Vui lòng chọn tên khác." });
      setSubmitting(false);
      return;
    }

    const formData = new FormData();
    formData.append("name", trimmedName);
    formData.append("price", parseFloat(trimmedPrice));
    formData.append("description", dishForm.description || "");
    formData.append("categoryId", parseInt(dishForm.category_id));
    if (imageFile) {
      formData.append("image", imageFile);
    }

    try {
      if (isEditing && dishForm.id) {
        await updateDish(dishForm.id, formData);
      } else {
        await createDish(formData);
      }
      await fetchDishes();
      resetForm();
    } catch (err) {
      setFormError({ message: err.message || "Đã xảy ra lỗi khi gửi dữ liệu." });
      console.error("Error submitting dish:", err);
    }

    setSubmitting(false);
  };


  const handleEditDish = (dish) => {
    setDishForm({
      id: dish.id,
      name: dish.name,
      price: dish.price,
      description: dish.description,
      category_id: dish.categoryId || dish.category
    });
    setIsEditing(true);
    setImageFile(null);
    setFormError(null);
    window.scrollTo({ top: 0, behavior: "smooth" });
  };

  const handleDeleteDish = async (id) => {
    if (!window.confirm("Bạn có chắc muốn xóa món ăn này?")) return;
    try {
      await deleteDish(id);
      await fetchDishes();
    } catch (err) {
      console.error("Error deleting dish:", err);
      alert("Xóa món ăn thất bại.");
    }
  };

  // Helper: Lấy tên danh mục theo ID
  const getCategoryName = (dish) => {
    const categoryId = dish.categoryId || dish.category;
    return categories.find((cat) => String(cat.id) === String(categoryId))?.name || "Không xác định";
  };

  return (
    <div className="container my-4">
      <h2 className="mb-4 text-center">Danh sách Món Ăn</h2>

      {/* Form dùng chung cho Thêm và Cập nhật */}
      <div className="card shadow-sm mb-4">
        <div className={`card-header ${isEditing ? "bg-warning" : "bg-primary"} text-white`}>
          {isEditing ? "Cập nhật Món Ăn" : "Thêm Món Ăn Mới"}
        </div>
        <div className="card-body">
          <form onSubmit={handleSubmit}>
            <div className="mb-3">
              <label className="form-label">Tên món ăn</label>
              <input
                type="text"
                className="form-control"
                name="name"
                value={dishForm.name}
                onChange={handleInputChange}
              />
            </div>
            <div className="mb-3">
              <label className="form-label">Giá</label>
              <input
                type="number"
                className="form-control"
                name="price"
                value={dishForm.price}
                onChange={handleInputChange}
              />
            </div>
            <div className="mb-3">
              <label className="form-label">Mô tả</label>
              <textarea
                className="form-control"
                name="description"
                value={dishForm.description}
                onChange={handleInputChange}
              />
            </div>
            <div className="mb-3">
              <label className="form-label">Danh mục</label>
              <select
                className="form-select"
                name="category_id"
                value={dishForm.category_id}
                onChange={handleInputChange}
              >
                <option value="">-- Chọn danh mục --</option>
                {categories.map((cat) => (
                  <option key={cat.id} value={cat.id}>
                    {cat.name}
                  </option>
                ))}
              </select>
            </div>
            <div className="mb-3">
              <label className="form-label">
                Hình ảnh {isEditing ? "(nếu cập nhật)" : ""}
              </label>
              <input
                type="file"
                className="form-control"
                onChange={handleFileChange}
                accept="image/png, image/jpeg, image/jpg"
              />

            </div>
            {formError && (
              <p className="text-danger">Lỗi: {formError.message}</p>
            )}
            <div className="d-flex">
              <button type="submit" className="btn btn-primary" disabled={submitting}>
                {submitting
                  ? isEditing
                    ? "Đang cập nhật..."
                    : "Đang thêm..."
                  : isEditing
                    ? "Cập nhật"
                    : "Thêm món ăn"}
              </button>
              {isEditing && (
                <button type="button" className="btn btn-secondary ms-2" onClick={resetForm}>
                  Hủy
                </button>
              )}
            </div>
          </form>
        </div>
      </div>

      {/* Bảng danh sách món ăn */}
      <div className="card shadow-sm">
        <div className="card-header bg-success text-white">Danh sách Món Ăn</div>
        <div className="card-body table-responsive">
          {loading && <p>Đang tải danh sách món ăn...</p>}
          {error && <p className="text-danger">Lỗi: {error.message}</p>}
          {!loading && !error && (
            dishes.length > 0 ? (
              <table className="table table-striped table-bordered mb-0">
                <thead className="table-light">
                  <tr>
                    <th>STT</th>
                    <th>ID</th>
                    <th>Tên món ăn</th>
                    <th>Giá</th>
                    <th>Danh mục</th>
                    <th>Hình ảnh</th>
                    <th>Hành động</th>
                  </tr>
                </thead>
                <tbody>
                  {dishes.map((dish, index) => (
                    <tr key={dish.id}>
                      <td>{index + 1}</td>
                      <td>{dish.id}</td>
                      <td>{dish.name}</td>
                      <td>{dish.price}</td>
                      <td>{getCategoryName(dish)}</td>
                      <td>
                        {dish.image ? (
                          <img
                            src={dish.image}
                            alt={dish.name}
                            style={{ width: "80px", height: "auto", objectFit: "cover" }}
                          />
                        ) : (
                          "Không có hình"
                        )}
                      </td>
                      <td>
                        <button className="btn btn-sm btn-warning me-2" onClick={() => handleEditDish(dish)}>
                          Sửa
                        </button>
                        <button className="btn btn-sm btn-danger" onClick={() => handleDeleteDish(dish.id)}>
                          Xóa
                        </button>
                      </td>
                    </tr>
                  ))}
                </tbody>
              </table>
            ) : (
              <p>Không có món ăn nào.</p>
            )
          )}
        </div>
      </div>
    </div>
  );
}

export default DishList;
