import React, { useState, useEffect } from 'react';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faLockOpen, faLock, faUserClock } from '@fortawesome/free-solid-svg-icons';
import { Link, useParams } from 'react-router-dom';
import orderStaffService from '../../services/staffService/Index.tsx'; // Đảm bảo đường dẫn đúng

// Định nghĩa kiểu dữ liệu cho dữ liệu từ backend (Rất khuyến khích cho TypeScript)
// Bạn có thể đặt các interface này vào một file riêng như 'types.ts' và import vào
interface OrderDetailDTO {
    id: number;
    orderId: number;
    dishOrComboId: number;
    price: number;
    quantity: number;
    type: boolean; // true = Combo, false = Dish
    discountPercentage: number;
    name: string | null;
    image: string | null;
    description: string | null;
    branchId: number;
}

interface FoodEntity {
    id: number;
    description: string | null;
    image: string | null;
    name: string;
    price: number;
    status: boolean;
}

interface ComboEntity {
    id: number;
    description: string | null;
    image: string | null;
    name: string;
    price: number;
    status: boolean;
}

interface CategoryEntity {
    id: number;
    name: string;
}

interface TableEntity {
    id: number;
    number: string; // Theo JSON mẫu, 'number' là string
    quantity: number; // Trong JSON mẫu là 'quantity' thay vì 'capacity'
    status: number;
}

interface OrderPageData {
    orderDetails: OrderDetailDTO[];
    foods: FoodEntity[];
    combos: ComboEntity[];
    categories: CategoryEntity[];
    table: TableEntity;
}


export default function Index() {
    // Sử dụng useState với kiểu dữ liệu cụ thể
    const [orderDetails, setOrderDetails] = useState<OrderDetailDTO[]>([]);
    const [categories, setCategories] = useState<CategoryEntity[]>([]);
    const [foods, setFoods] = useState<FoodEntity[]>([]);
    const [combos, setCombos] = useState<ComboEntity[]>([]);
    const [table, setTable] = useState<TableEntity | null>(null); // Có thể là null ban đầu
    const [loading, setLoading] = useState<boolean>(true);
    const [error, setError] = useState<string | null>(null);

    // Lấy các tham số từ URL
    const { branchId, tableId, orderId, categoryId } = useParams<{
        branchId: string;
        tableId: string;
        orderId: string;
        categoryId: string;
    }>();

    useEffect(() => {
        const fetchData = async () => {
            setLoading(true);
            setError(null);
            try {
                // Kiểm tra sự tồn tại của tham số trước khi parse
                if (!branchId || !tableId || !orderId || !categoryId) {
                    throw new Error("Thiếu tham số cần thiết trong URL. Vui lòng kiểm tra đường dẫn.");
                }

                // Chuyển đổi các tham số từ string sang number
                const parsedBranchId = parseInt(branchId);
                const parsedTableId = parseInt(tableId);
                const parsedOrderId = parseInt(orderId);
                const parsedCategoryId = parseInt(categoryId); // Đây là ID danh mục hoặc -1 cho combo

                // Kiểm tra kết quả parse có phải là NaN không
                if (isNaN(parsedBranchId) || isNaN(parsedTableId) || isNaN(parsedOrderId) || isNaN(parsedCategoryId)) {
                    throw new Error("Tham số trong URL không hợp lệ (không phải số). Vui lòng kiểm tra đường dẫn.");
                }

                // Gọi service để lấy dữ liệu
                const data: OrderPageData = await orderStaffService.getOrderPageData(
                    parsedBranchId,
                    parsedTableId,
                    parsedOrderId,
                    parsedCategoryId
                );

                // Cập nhật state với dữ liệu nhận được
                setOrderDetails(data.orderDetails || []);
                setCategories(data.categories || []);
                setFoods(data.foods || []);
                setCombos(data.combos || []);
                setTable(data.table || null);

            } catch (err: any) {
                console.error("Failed to fetch data:", err);
                setError(err.message || "Không thể tải dữ liệu. Vui lòng thử lại sau.");
            } finally {
                setLoading(false);
            }
        };

        fetchData();
        // Dependencies array
    }, [branchId, tableId, orderId, categoryId]);

    if (loading) {
        return <div className="text-center mt-5">Đang tải dữ liệu...</div>;
    }

    if (error) {
        return <div className="alert alert-danger text-center mt-5">{error}</div>;
    }

    // Hàm để chọn icon dựa trên trạng thái bàn
    const getTableIcon = (status: number) => {
        switch (status) {
            case 0: // Ví dụ: 0 là bàn trống (Open)
                return faLockOpen;
            case 1: // Ví dụ: 1 là bàn đang có người (Occupied)
                return faLock;
            case 2: // Ví dụ: 2 là bàn đang chờ dọn (Cleaning/UserClock)
                return faUserClock;
            default:
                return faLockOpen;
        }
    };

    return (
        <div className='container-fluid'>
            <div className='row border'>
                <div className='col-3 border '>
                    <h1 className='text-center'>Tables</h1>
                    <p className='text-center'>This is the tables page.</p>
                    <div className='row p-2'>
                        {/* Hiển thị thông tin bàn được lấy từ BE */}
                        {table && (
                            <div className='col-12 card p-3 mb-2'>
                                <FontAwesomeIcon icon={getTableIcon(table.status)} />
                                Bàn {table.id} - {table.number} {/* Sửa từ table.name thành table.number */}
                                <p>Số chỗ: {table.quantity}</p> {/* Sửa từ table.capacity thành table.quantity */}
                            </div>
                        )}
                        {/* Hiện tại chỉ hiển thị bàn đang chọn, nếu muốn hiển thị tất cả bàn thì cần API riêng */}
                    </div>
                </div>
                <div className='col-9'>
                    <h1 className='text-center'>Staff</h1>
                    <p className='text-center'>This is the staff page.</p>
                    <nav className="navbar navbar-expand-lg bg-body-tertiary">
                        <div className="container-fluid">
                            {/* Chuyển hướng đến Combos */}
                            <Link className="navbar-brand" to={`/Staff/Branch/${branchId}/Table/${tableId}/Order/${orderId}/Category/-1`}>Combo</Link>
                            <button className="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarNav" aria-controls="navbarNav" aria-expanded="false" aria-label="Toggle navigation">
                                <span className="navbar-toggler-icon"></span>
                            </button>
                            <div className="collapse navbar-collapse" id="navbarNav">
                                <ul className="navbar-nav">
                                    {/* Duyệt qua danh mục từ BE và tạo link */}
                                    {categories.map((cat) => (
                                        <li className="nav-item" key={cat.id}>
                                            <Link
                                                className="nav-link"
                                                to={`/Staff/Branch/${branchId}/Table/${tableId}/Order/${orderId}/Category/${cat.id}`}
                                            >
                                                {cat.name}
                                            </Link>
                                        </li>
                                    ))}
                                </ul>
                            </div>
                        </div>
                    </nav>
                    <div className='row border'>
                        {/* Hiển thị Combos */}
                        {parseInt(categoryId ?? "-1") === -1 && combos.map((combo) => ( // So sánh với số sau khi parse
                            <div className='col-3 card p-3' key={combo.id}>
                                {combo.image && <img src={combo.image} alt={combo.name} style={{ width: '100%', height: 'auto' }} />}
                                {!combo.image && <div style={{ width: '100%', height: '100px', backgroundColor: '#e0e0e0', display: 'flex', alignItems: 'center', justifyContent: 'center' }}>No Image</div>}
                                <h5>{combo.name}</h5>
                                <p>{combo.description}</p>
                                <p>Giá: {combo.price.toLocaleString('vi-VN')} VNĐ</p> {/* Định dạng tiền tệ */}
                                {/* Thêm nút chọn món hoặc combo */}
                            </div>
                        ))}

                        {/* Hiển thị Món ăn theo category */}
                        {parseInt(categoryId ?? "-1") !== -1 && foods.map((dish) => ( // So sánh với số sau khi parse
                            <div className='col-3 card p-3' key={dish.id}>
                                {dish.image && <img src={dish.image} alt={dish.name} style={{ width: '100%', height: 'auto' }} />}
                                {!dish.image && <div style={{ width: '100%', height: '100px', backgroundColor: '#e0e0e0', display: 'flex', alignItems: 'center', justifyContent: 'center' }}>No Image</div>}
                                <h5>{dish.name}</h5>
                                <p>{dish.description}</p>
                                <p>Giá: {dish.price.toLocaleString('vi-VN')} VNĐ</p> {/* Định dạng tiền tệ */}
                                {/* Thêm nút chọn món */}
                            </div>
                        ))}
                    </div>

                    <hr />

                    {/* Hiển thị chi tiết đơn hàng */}
                    <h2 className='text-center mt-3'>Chi tiết đơn hàng</h2>
                    <table className="table table-bordered mt-3">
                        <thead>
                            <tr>
                                <th>#</th>
                                <th>Tên</th>
                                <th>Số lượng</th>
                                <th>Giá</th>
                                <th>Loại</th>
                                {/* Thêm cột hành động nếu cần (tăng/giảm số lượng, xóa) */}
                            </tr>
                        </thead>
                        <tbody>
                            {orderDetails.length > 0 ? (
                                orderDetails.map((detail, index) => (
                                    <tr key={index}>
                                        <td>{index + 1}</td>
                                        <td>{detail.name || 'N/A'}</td> {/* Xử lý name là null */}
                                        <td>{detail.quantity}</td>
                                        <td>{detail.price.toLocaleString('vi-VN')} VNĐ</td> {/* Định dạng tiền tệ */}
                                        <td>{detail.type ? "Combo" : "Món ăn"}</td>
                                    </tr>
                                ))
                            ) : (
                                <tr>
                                    <td colSpan={5} className="text-center">Chưa có món nào trong đơn hàng.</td>
                                </tr>
                            )}
                        </tbody>
                    </table>
                </div>
            </div>
        </div>
    );
}