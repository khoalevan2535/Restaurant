import React, { useState, useEffect } from 'react';
import { Container, Table, Button, Spinner, Alert, Badge, Modal, Form } from 'react-bootstrap';
import { getAllTables, createTable, updateTable, deleteTable } from '../services/tableService';
import { findBranch } from '../services/branchService'; // ← Thêm dòng này

function Home() {
  const [tables, setTables] = useState([]);
  const [branches, setBranches] = useState([]); // ← Danh sách chi nhánh
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);

  const [showModal, setShowModal] = useState(false);
  const [editingTable, setEditingTable] = useState(null);
  const [formData, setFormData] = useState({ number: '', branchId: '' });

  useEffect(() => {
    fetchData();
  }, []);

  const fetchData = async () => {
    try {
      const [tableData, branchData] = await Promise.all([
        getAllTables(),
        findBranch()
      ]);
      setTables(tableData);
      setBranches(branchData);
    } catch (err) {
      setError('Lỗi khi lấy dữ liệu: ' + err.message);
    } finally {
      setLoading(false);
    }
  };

  const handleOpenModal = (table = null) => {
    setEditingTable(table);
    setFormData(table ? { number: table.number, branchId: table.branchId } : { number: '', branchId: '' });
    setShowModal(true);
  };

  const handleCloseModal = () => {
    setShowModal(false);
    setEditingTable(null);
    setFormData({ number: '', branchId: '' });
  };

  const handleChange = (e) => {
    const { name, value } = e.target;
    setFormData(prev => ({ ...prev, [name]: value }));
  };

  const handleSubmit = async () => {
    const { number, branchId } = formData;

    if (!number || isNaN(number) || Number(number) <= 0 || !Number.isInteger(Number(number))) {
      alert("Số bàn phải là số nguyên dương.");
      return;
    }

    if (!branchId) {
      alert("Vui lòng chọn chi nhánh.");
      return;
    }

    try {
      if (editingTable) {
        await updateTable(editingTable.id, formData);
      } else {
        await createTable({ ...formData, status: true });
      }
      fetchData();
      handleCloseModal();
    } catch (err) {
      alert('Lỗi khi lưu dữ liệu: ' + err.message);
    }
  };


  const handleDelete = async (id) => {
    if (window.confirm('Bạn có chắc muốn xóa bàn này?')) {
      try {
        await deleteTable(id);
        fetchData();
      } catch (err) {
        alert('Lỗi khi xóa bàn: ' + err.message);
      }
    }
  };

  if (loading) {
    return (
      <Container className="d-flex justify-content-center align-items-center" style={{ height: '100vh' }}>
        <Spinner animation="border" variant="primary" />
        <span className="ms-2">Đang tải dữ liệu...</span>
      </Container>
    );
  }

  if (error) {
    return (
      <Container className="mt-4">
        <Alert variant="danger">{error}</Alert>
      </Container>
    );
  }

  return (
    <Container className="mt-4">
      <h2 className="text-center mb-4">Quản lý Bàn</h2>

      <Button variant="primary" className="mb-3" onClick={() => handleOpenModal()}>
        Thêm bàn mới
      </Button>

      <div className="table-responsive">
        <Table striped bordered hover>
          <thead className="table-dark">
            <tr>
              <th>ID</th>
              <th>Số bàn</th>
              {/* <th>Trạng thái</th> */}
              <th>Chi nhánh</th>
              <th>Hành động</th>
            </tr>
          </thead>
          <tbody>
            {tables.map((table) => (
              <tr key={table.id}>
                <td>{table.id}</td>
                <td>{table.number}</td>
                {/* <td>
                  {table.status ? (
                    <Badge bg="success">Hoạt động</Badge>
                  ) : (
                    <Badge bg="secondary">Không hoạt động</Badge>
                  )}
                </td> */}
                <td>
                  {
                    branches.find(branch => branch.id === table.branchId)?.name
                    || `Chi nhánh ${table.branchId}`
                  }
                </td>
                <td>
                  <Button variant="warning" size="sm" onClick={() => handleOpenModal(table)} className="me-2">
                    Sửa
                  </Button>
                  <Button variant="danger" size="sm" onClick={() => handleDelete(table.id)}>
                    Xóa
                  </Button>
                </td>
              </tr>
            ))}
          </tbody>

        </Table>
      </div>

      {/* Modal Thêm/Sửa */}
      <Modal show={showModal} onHide={handleCloseModal}>
        <Modal.Header closeButton>
          <Modal.Title>{editingTable ? 'Cập nhật bàn' : 'Thêm bàn mới'}</Modal.Title>
        </Modal.Header>
        <Modal.Body>
          <Form>
            <Form.Group controlId="formNumber" className="mb-3">
              <Form.Label>Số bàn</Form.Label>
              <Form.Control
                type="number"
                name="number"
                value={formData.number}
                onChange={(e) => {
                  const value = e.target.value;
                  // Chỉ cho phép số nguyên dương hoặc rỗng
                  if (/^\d*$/.test(value)) {
                    setFormData(prev => ({ ...prev, number: value }));
                  }
                }}
                min="1"
                placeholder="Nhập số bàn"
              />

            </Form.Group>
            <Form.Group controlId="formBranchId">
              <Form.Label>Chi nhánh</Form.Label>
              <Form.Select name="branchId" value={formData.branchId} onChange={handleChange} required>
                <option value="">-- Chọn chi nhánh --</option>
                {branches.map(branch => (
                  <option key={branch.id} value={branch.id}>
                    {branch.name || `Chi nhánh ${branch.id}`}
                  </option>
                ))}
              </Form.Select>
            </Form.Group>
          </Form>
        </Modal.Body>
        <Modal.Footer>
          <Button variant="secondary" onClick={handleCloseModal}>
            Hủy
          </Button>
          <Button variant="primary" onClick={handleSubmit}>
            {editingTable ? 'Cập nhật' : 'Thêm mới'}
          </Button>
        </Modal.Footer>
      </Modal>
    </Container>
  );
}

export default Home;
