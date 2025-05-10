import React, { useEffect, useState } from 'react'
import { useParams } from 'react-router-dom'
import { getTables } from '../../../services/adminService/Table.tsx'

export default function Order() {
  const { branchId } = useParams<{ branchId: string }>();
  const [tables, setTables] = useState([]);
  const [loading, setLoading] = useState(true);
  const [selectedTable, setSelectedTable] = useState<string | undefined>(undefined);

  useEffect(() => {
    if (!branchId) return;
    getTables(branchId)
      .then(data => setTables(data))
      .catch(() => setTables([]))
      .finally(() => setLoading(false));
  }, [branchId]);

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
      <button className='btn btn-primary' onClick={() => alert(`Bạn đã chọn bàn: ${selectedTable}`)} disabled={!selectedTable}>
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