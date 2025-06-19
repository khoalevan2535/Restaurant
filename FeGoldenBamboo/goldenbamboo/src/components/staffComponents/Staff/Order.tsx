import React, { useEffect, useState } from 'react'
import { useParams, useNavigate } from 'react-router-dom'
import { getTables } from '../../../services/adminService/Table.tsx'
import { createOrder } from '../../../services/adminService/Order.tsx'

export default function Order() {
  const { branchId } = useParams<{ branchId: string }>();
  const [tables, setTables] = useState([]);
  const [loading, setLoading] = useState(true);
  const [selectedTable, setSelectedTable] = useState<string | undefined>(undefined);
  const navigate = useNavigate();

useEffect(() => {
  if (!branchId) return;
  getTables(branchId)
    .then(data => {
      if (data.status === "success") {
        setTables(data.tables);
      } else {
        setTables([]);
        alert(data.message || "Bạn không có quyền truy cập chi nhánh này.");
      }
    })
    .catch(() => setTables([]))
    .finally(() => setLoading(false));
}, [branchId]);

  const handleCreateOrder = async () => {
    if (!branchId || !selectedTable) return;
    try {
      const result = await createOrder(branchId, selectedTable);
      // Giả sử BE trả về { id: ... }
      navigate(`/Staff/Branch/${branchId}/Table/${selectedTable}/Order/${result.id}/Category/-1`);
    } catch (error) {
      alert("Tạo hóa đơn thất bại!");
    }
  };

  return (
    <div className="container mt-4">
      <h3>Chọn bàn</h3>
      <div className='d-flex'>
        {loading ? (
          <div>Đang tải...</div>
        ) : (
          <select
            className="form-select me-2"
            value={selectedTable}
            onChange={e => setSelectedTable(e.target.value)}
          >
            <option value="">-- Chọn bàn --</option>
            {tables.map((table: any) => (
              <option key={table.id} value={table.id}>
                Bàn {table.number} ({table.status ? 'Đang hoạt động' : 'Ngừng hoạt động'})
              </option>
            ))}
          </select>
        )}
        <button
          className='btn btn-primary'
          onClick={handleCreateOrder}
          disabled={!selectedTable}
        >
          Tạo đơn hàng
        </button>
      </div>
      {selectedTable && (
        <div className="mt-3">
          <strong>Bạn đã chọn bàn:</strong> {selectedTable}
        </div>
      )}
    </div>
  )
}