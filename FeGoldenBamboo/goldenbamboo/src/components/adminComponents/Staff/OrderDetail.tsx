import React, { useEffect, useState } from 'react';
import { Link, useParams, useLocation } from 'react-router-dom';  // Import useLocation
import { getMenuData } from '../../../services/adminService/OrderDetail.tsx';
import { DataState, Food, Category } from '../../../interface/OrderDetail';

export default function OrderDetail() {
  const { branchId, tableId, orderId, categoryId } = useParams<{
    branchId: string;
    tableId: string;
    orderId: string;
    categoryId: string;
  }>();

  const [data, setData] = useState<DataState | null>(null);
  const [loading, setLoading] = useState(true);
  const location = useLocation();  // Lấy đường dẫn hiện tại

  useEffect(() => {
    if (!branchId || !tableId || !orderId || !categoryId) return;
    getMenuData(
      Number(branchId),
      Number(tableId),
      Number(orderId),
      Number(categoryId)
    )
      .then(res => setData(res))
      .finally(() => setLoading(false));
  }, [branchId, tableId, orderId, categoryId]);

  if (loading) return <div>Đang tải...</div>;
  if (!data) return <div>Không có dữ liệu</div>;

  // Xử lý đường dẫn hiện tại, xóa phần không mong muốn và cộng thêm categoryId
  const basePath = location.pathname.split('/Category')[0];  // Lấy đường dẫn trước /Category nếu có

  return (
    <div className="container mt-4">
      <h4>Thông tin bàn</h4>
      <div>
        <strong>ID:</strong> {data.table.id} <br />
        <strong>Số bàn:</strong> {data.table.number} <br />
        <strong>Trạng thái:</strong>{" "}
        <span className={`badge ${data.table.status ? "bg-success" : "bg-secondary"}`}>
          {data.table.status ? "Đang hoạt động" : "Ngừng hoạt động"}
        </span>
      </div>

      <h4 className="mt-3">Danh mục</h4>
      <ul className="nav">
        <li className="nav-item">
            <Link className='nav-link' to={`${basePath}/Category/-1`}>Combo</Link>
          </li>
        {data.categories.map((cat: Category) => (
          <li className="nav-item" key={cat.id}>
            <Link
              className='nav-link'
              aria-current={String(cat.id) === categoryId ? "page" : undefined}
              to={`${basePath}/Category/${cat.id}`}  // Dùng basePath để thay đổi category mà không làm thay đổi các phần khác
            >
              {cat.name}
            </Link>
          </li>
        ))}
      </ul>

      <h4 className="mt-3">Món ăn</h4>
      <ul>
        {data.foods.map((food: Food) => (
          <li key={food.id}>
            <strong>{food.name}</strong> - {food.description} - {food.price.toLocaleString()}đ
          </li>
        ))}
      </ul>

      <h4 className="mt-3">Combos</h4>
      <pre>{JSON.stringify(data.combos, null, 2)}</pre>

      <h4 className="mt-3">Order Details</h4>
      <pre>{JSON.stringify(data.orderDetails, null, 2)}</pre>

      <Link to={`/Admin/Staff/Branch/${branchId}/Tables`}>
        <button className='btn btn-primary mt-3'>
          Hủy đơn hàng
        </button>
      </Link>
    </div>
  );
}
