import React, { useState, useEffect, useCallback } from 'react';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faLockOpen, faLock, faUserClock, faQuestionCircle } from '@fortawesome/free-solid-svg-icons';
import { orderStaffService } from '../../services/staffService/Index.tsx';
import { toast } from 'react-toastify';
import { useNavigate, useParams  } from 'react-router-dom';

// Định nghĩa kiểu dữ liệu cho bàn
interface Table {
    id: number;
    number: string;
    status: number;
    branchId: number;
    quantity: number;
}

// Hàm trợ giúp để lấy style dựa trên trạng thái bàn
const getTableStyle = (status: number) => {
    switch (status) {
        case 0: // Trống
            return { icon: faLockOpen, color: 'success', text: 'Trống' };
        case 1: // Có khách
            return { icon: faLock, color: 'warning', text: 'Có khách' };
        case 3: // Cần xử lý, đặt trước,...
            return { icon: faUserClock, color: 'danger', text: 'Cần xử lý' };
        default: // Trạng thái khác
            return { icon: faQuestionCircle, color: 'secondary', text: 'Không xác định' };
    }
};

export default function StaffDashboard() {
    const [tables, setTables] = useState<Table[]>([]);
    const [loading, setLoading] = useState<boolean>(true);
    const [error, setError] = useState<string | null>(null);
    const navigate = useNavigate();
     const { branchId } = useParams<{ branchId: string }>();

 const fetchTables = useCallback(async () => {
        // SỬA ĐỔI: Kiểm tra nếu có branchId thì mới fetch
        if (!branchId) {
            setError("Không tìm thấy ID chi nhánh trong đường dẫn.");
            setLoading(false);
            return;
        }

        setLoading(true);
        try {
            // SỬA ĐỔI: Sử dụng branchId từ URL thay vì hardcode
            const numericBranchId = parseInt(branchId);
            const data = await orderStaffService.getTablesByBranch(numericBranchId);
            setTables(data || []);
        } catch (err: any) {
            setError(err.message);
            toast.error("Không thể tải danh sách bàn: " + err.message);
        } finally {
            setLoading(false);
        }
    }, [branchId]); // SỬA ĐỔI: Thêm branchId vào dependency

    useEffect(() => {
        fetchTables();
    }, [fetchTables]);


  // Hàm xử lý khi bấm vào một bàn
    const handleTableSelect = async (table: Table) => {
        try {
            toast.info(`Đang xử lý bàn ${table.number}...`);
            
            // 1. Gọi API để lấy hoặc tạo orderId
            const order = await orderStaffService.findOrCreateOrder(table.id);
            
            if (order && order.id) {
                // 2. Chuyển đến trang gọi món với thông tin đã nhận được
                navigate(`/Staff/Branch/${branchId}/Table/${table.id}/Order/${order.id}/Category/-1`);
            } else {
                toast.error("Không nhận được thông tin đơn hàng.");
            }

        } catch (err: any) {
            toast.error("Có lỗi xảy ra: " + err.message);
        }
    };

    if (loading) return <div className="text-center mt-5"><h3>Đang tải sơ đồ bàn...</h3></div>;
    if (error) return <div className="alert alert-danger">Lỗi: {error}</div>;

    return (
        <div className="container mt-4">
            <h1 className="text-center mb-4">Sơ đồ bàn</h1>
            <div className="row">
                {tables.map(table => {
                    const style = getTableStyle(table.status);
                    return (
                        <div className="col-lg-2 col-md-3 col-sm-4 col-6 mb-4" key={table.id}>
                            <div 
                                className={`card h-100 text-center border-${style.color}`} 
                                onClick={() => handleTableSelect(table)}
                                style={{ cursor: 'pointer', transition: 'transform 0.2s' }}
                                onMouseOver={e => e.currentTarget.style.transform = 'scale(1.05)'}
                                onMouseOut={e => e.currentTarget.style.transform = 'scale(1)'}
                            >
                                <div className={`card-body d-flex flex-column justify-content-center align-items-center text-${style.color}`}>
                                    <FontAwesomeIcon icon={style.icon} size="3x" />
                                    <h5 className="card-title mt-3 mb-1">Bàn {table.number}</h5>
                                    <p className="card-text"><small>{style.text}</small></p>
                                </div>
                            </div>
                        </div>
                    );
                })}
            </div>
        </div>
    );
}