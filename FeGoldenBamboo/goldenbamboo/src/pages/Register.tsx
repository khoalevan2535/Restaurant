import React, { useState } from 'react'
import 'bootstrap/dist/css/bootstrap.min.css'

export default function Register() {
  const [phone, setPhone] = useState('');
  const [password, setPassword] = useState('');
  const [confirmPassword, setConfirmPassword] = useState('');
  const [error, setError] = useState('');

  const handleSubmit = (e: React.FormEvent) => {
    e.preventDefault();
    if (!phone.match(/^\d{10,11}$/)) {
      setError('Số điện thoại không hợp lệ');
      return;
    }
    if (password.length < 6) {
      setError('Mật khẩu phải có ít nhất 6 ký tự');
      return;
    }
    if (password !== confirmPassword) {
      setError('Mật khẩu xác nhận không khớp');
      return;
    }
    setError('');
    // Xử lý đăng ký ở đây
    alert('Đăng ký thành công!');
  };

  return (
    <div className="container mt-5" style={{ maxWidth: 400 }}>
      <div className="card p-4 shadow-sm">
        <h2 className="mb-4 text-center">Đăng ký</h2>
        <form onSubmit={handleSubmit}>
          <div className="mb-3">
            <label className="form-label">Số điện thoại</label>
            <input
              type="tel"
              className="form-control"
              value={phone}
              onChange={e => setPhone(e.target.value)}
              placeholder="Nhập số điện thoại"
            />
          </div>
          <div className="mb-3">
            <label className="form-label">Mật khẩu</label>
            <input
              type="password"
              className="form-control"
              value={password}
              onChange={e => setPassword(e.target.value)}
              placeholder="Nhập mật khẩu"
            />
          </div>
          <div className="mb-3">
            <label className="form-label">Xác nhận mật khẩu</label>
            <input
              type="password"
              className="form-control"
              value={confirmPassword}
              onChange={e => setConfirmPassword(e.target.value)}
              placeholder="Nhập lại mật khẩu"
            />
          </div>
          {error && <div className="alert alert-danger py-2">{error}</div>}
          <button type="submit" className="btn btn-primary w-100">Đăng ký</button>
        </form>
      </div>
    </div>
  )
}
