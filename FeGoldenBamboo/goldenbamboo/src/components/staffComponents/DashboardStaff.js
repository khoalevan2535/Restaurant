import React, { useState, useEffect } from "react";
import { useParams, Link } from "react-router-dom";
import { dataStaff, addDishToOrder, removeDishFromOrder, updateDishQuantity } from "../../services/staffService/OrderService.js";

export default function Dashboard() {
  const { branchId, orderId, categoryId } = useParams();
  const isComboPage = categoryId === "-1";

  const [data, setData] = useState({
    categories: [],
    foods: [],
    orderDetails: [],
    combos: [],
  });

  useEffect(() => {
    if (!(branchId && orderId && categoryId)) return;

    (async () => {
      try {
        const res = await dataStaff(orderId, categoryId, branchId);
        setData({
          categories: res.categories || [],
          foods: res.foods || [],
          orderDetails: res.orderDetails || [],
          combos: res.combos || [],
        });
      } catch (error) {
        console.error("❌ Lỗi khi lấy dữ liệu:", error);
      }
    })();
  }, [branchId, orderId, categoryId]);

  const handleAddToCart = async (item, isCombo) => {
    const payload = {
      orderId: Number(orderId),
      dishOrComboId: Number(item.id),
      price: item.price,
      quantity: 1,
      type: isCombo,
      discountPercentage: 0,
    };

    try {
      await addDishToOrder(payload);
      const newOrderDetails = [
        ...data.orderDetails,
        {
          id: new Date().getTime(),
          name: item.name,
          quantity: 1,
          price: item.price,
          image: item.image,
          description: item.description,
        },
      ];
      setData((prevData) => ({
        ...prevData,
        orderDetails: newOrderDetails,
      }));
    } catch (error) {
      console.error("❌ Lỗi khi thêm vào giỏ:", error);
      alert("Thêm vào giỏ hàng thất bại.");
    }
  };

  const handleRemoveFromCart = async (id) => {
    await removeDishFromOrder(orderId, id, branchId);
    const updatedOrderDetails = data.orderDetails.filter((d) => d.id !== id);
    setData((prevData) => ({
      ...prevData,
      orderDetails: updatedOrderDetails,
    }));
  };

  const handleChangeQuantity = async (id, change) => {
    const currentItem = data.orderDetails.find((item) => item.id === id);
    if (!currentItem) return;

    const newQuantity = currentItem.quantity + change;
    if (newQuantity <= 0) {
      handleRemoveFromCart(id);
      return;
    }

    try {
      // Gửi request cập nhật lên server
      await updateDishQuantity(orderId, id, newQuantity);

      // Cập nhật giao diện
      const updatedOrderDetails = data.orderDetails.map((item) => (item.id === id ? { ...item, quantity: newQuantity } : item));

      setData((prevData) => ({
        ...prevData,
        orderDetails: updatedOrderDetails,
      }));
    } catch (error) {
      console.error("❌ Lỗi khi cập nhật số lượng:", error);
      alert("Cập nhật số lượng thất bại.");
    }
  };
  return (
    <div className="container-fluid">
      {/* Danh mục và Combo */}
      {data.categories.length > 0 ? (
        <ul className="d-flex list-group list-group-horizontal mb-3 flex-wrap">
          <li className="list-group-item pe-4">
            <Link to={`/Staff/Branch/${branchId}/Order/${orderId}/Category/-1`}>Combo</Link>
          </li>
          {data.categories.map((cat) => (
            <li key={cat.id} className="list-group-item pe-4">
              <Link to={`/Staff/Branch/${branchId}/Order/${orderId}/Category/${cat.id}`}>{cat.name}</Link>
            </li>
          ))}
        </ul>
      ) : (
        <p>Không có danh mục nào.</p>
      )}

      <div className="row">
        <div className="col-md-9">
          <div className="row">
            {(isComboPage ? data.combos : data.foods).map((item) => (
              <div key={item.id} className="col-md-3 mb-3">
                <div className="card h-100">
                  <img src={item.image || "https://placehold.co/300x200"} className="card-img-top" alt={item.name} />
                  <div className="card-body">
                    <h5 className="card-title">{item.name}</h5>
                    <p className="card-text">{item.description}</p>
                    <p className="fw-bold">{item.price}₫</p>
                    <button className="btn btn-primary mt-2" onClick={() => handleAddToCart(item, isComboPage)}>
                      Thêm vào giỏ hàng
                    </button>
                  </div>
                </div>
              </div>
            ))}
          </div>
        </div>

        <div className="col-md-3">
          <h3>Chi tiết đơn hàng</h3>
          {data.orderDetails.length > 0 && (
            <div className="border p-3 bg-light rounded shadow-sm">
              {data.orderDetails.map((detail) => (
                <div key={detail.id} className="mb-3 d-flex align-items-start">
                  <img
                    src={detail.image || "https://placehold.co/60x60"}
                    alt={detail.name}
                    className="me-2"
                    style={{ width: "60px", height: "60px", objectFit: "cover", borderRadius: "8px" }}
                  />
                  <div className="flex-grow-1">
                    <strong>{detail.name}</strong>
                    <p className="mb-1 small text-muted">{detail.description}</p>

                    <div className="d-flex align-items-center mb-1">
                      <button className="btn btn-sm btn-secondary me-2" onClick={() => handleChangeQuantity(detail.id, -1)}>
                        -
                      </button>
                      <span>{detail.quantity}</span>
                      <button className="btn btn-sm btn-secondary ms-2" onClick={() => handleChangeQuantity(detail.id, 1)}>
                        +
                      </button>
                    </div>

                    <p className="mb-1">Đơn giá: {detail.price}₫</p>
                    <p className="mb-1 fw-bold">Tạm tính: {detail.quantity * detail.price}₫</p>
                  </div>
                  <button className="btn btn-sm btn-danger ms-2 mt-2" onClick={() => handleRemoveFromCart(detail.id)}>
                    Xóa
                  </button>
                </div>
              ))}
            </div>
          )}
        </div>
      </div>
      <hr />
      <div className="mt-3">
        <h5>Thông tin hóa đơn</h5>
        <p className="mb-1">Tổng số món: {data.orderDetails.reduce((sum, item) => sum + item.quantity, 0)}</p>
        <p className="mb-1 fw-bold">
          Tổng tiền: {data.orderDetails.reduce((sum, item) => sum + item.quantity * item.price, 0).toLocaleString("vi-VN")}₫
        </p>
        <button className="btn btn-success w-100 mt-2">Xác nhận thanh toán</button>
      </div>
    </div>
  );
}
