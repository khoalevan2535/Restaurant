import React, { useEffect, useState } from 'react';
import { Link, useParams } from 'react-router-dom';
import { getMenuData, addDishToOrder } from '../../../services/adminService/OrderDetail.tsx';
import { Food, Category, Table } from '../../../interface/OrderDetail';

export default function OrderDetail() {
  const { branchId, tableId, orderId, categoryId } = useParams<{
    branchId: string;
    tableId: string;
    orderId: string;
    categoryId: string;
  }>();

  const [table, setTable] = useState<Table | null>(null);
  const [categories, setCategories] = useState<Category[]>([]);
  const [foods, setFoods] = useState<Food[]>([]);
  const [combos, setCombos] = useState<Food[]>([]);
  const [orderDetails, setOrderDetails] = useState<any[]>([]);
  const [loading, setLoading] = useState(true);
  const isComboPage = categoryId === "-1";

  const fetchData = async () => {
    if (!branchId || !tableId || !orderId || !categoryId) return;
    try {
      const res = await getMenuData(
        Number(branchId),
        Number(tableId),
        Number(orderId),
        Number(categoryId)
      );
      setTable(res.table);
      setCategories(res.categories);
      setFoods(res.foods);
      setCombos(res.combos);
      setOrderDetails(res.orderDetails);
      console.log("Dữ liệu đã lấy:", res);
    } finally {
      setLoading(false);
    }
  };

  useEffect(() => {
    fetchData();
  }, );

  if (loading) return <div>Đang tải...</div>;
  if (!table) return <div>Không có dữ liệu</div>;

  

  const handleAddToOrder = async (food: Food, isCombo: boolean) => {
    if (!orderId) return;
    try {
      await addDishToOrder(
        Number(orderId),
        {
          id: food.id,
          name: food.name,
          description: food.description,
          price: food.price,
          quantity: 1,
          type: isCombo ? "combo" : "food",
        },
        {
          orderId: Number(orderId),
          dishOrComboId: food.id,
          price: food.price,
          quantity: 1,
          type: isCombo,
          discountPercentage: 0,
        }
      );
      await fetchData(); 
    } catch (err) {
      alert("Thêm vào đơn hàng thất bại!");
    }
  };

  const handleQuantityChange = (id: number, newQuantity: number) => {
    const updated = orderDetails.map(detail =>
      detail.id === id ? { ...detail, quantity: newQuantity } : detail
    );
    setOrderDetails(updated);
  };

  return (
    <div className="container mt-4">
      <h4>Thông tin bàn</h4>
      <div>
        <strong>Số bàn:</strong> {table.number} <br />
        <strong>Trạng thái:</strong>{" "}
        <span className={`badge ${table.status ? "bg-success" : "bg-secondary"}`}>
          {table.status ? "Đang hoạt động" : "Ngừng hoạt động"}
        </span>
      </div>

      <h4 className="mt-3">Danh mục</h4>
      <ul className="nav">
        <li className="nav-item">
          <Link className="nav-link" to={`/Category/-1`}>Combo</Link>
        </li>
        {categories.map((cat) => (
          <li className="nav-item" key={cat.id}>
            <Link
              className="nav-link"
              aria-current={String(cat.id) === categoryId ? "page" : undefined}
              to={`/Category/${cat.id}`}
            >
              {cat.name}
            </Link>
          </li>
        ))}
      </ul>

      <h4 className="mt-3">{isComboPage ? "Danh sách Combo" : "Danh sách Món ăn"}</h4>
      <div className="row">
        {(isComboPage ? combos : foods).map((food) => (
          <div key={food.id} className="col-md-3 mb-3">
            <div className="card h-100">
              <img src={food.image || "https://placehold.co/300x200"} className="card-img-top" alt={food.name} />
              <div className="card-body">
                <h5 className="card-title">{food.name}</h5>
                <p className="card-text">{food.description}</p>
                <p className="fw-bold">{food.price.toLocaleString()}₫</p>
              </div>
              <button
                className="btn btn-primary mt-2"
                onClick={() => handleAddToOrder(food, isComboPage)}
              >
                Thêm vào đơn hàng
              </button>
            </div>
          </div>
        ))}
      </div>

      <h4 className="mt-3">Chi tiết đơn hàng</h4>
      <ul>
        {orderDetails.map((detail) => (
          <li key={detail.id}>
            <strong>{detail.name}</strong> - {detail.description} <br />
            Số lượng: 
            <input
              type="number"
              value={detail.quantity}
              min={1}
              onChange={(e) => handleQuantityChange(detail.id, +e.target.value)}
            /> <br />
            Giá: {detail.price.toLocaleString()}₫ <br />
            Loại: {detail.type ? "Combo" : "Món lẻ"}
          </li>
        ))}
      </ul>

      <Link to={`/Admin/Staff/Branch/${branchId}/Tables`}>
        <button className="btn btn-primary mt-3">Hủy đơn hàng</button>
      </Link>
    </div>
  );
}
