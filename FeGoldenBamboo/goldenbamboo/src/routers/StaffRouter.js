import { Routes, Route } from "react-router-dom";
import StaffLayout from "../layouts/StaffLayout.tsx";
import Dashboard from "../components/staffComponents/DashboardStaff.js";
import Order from "../components/adminComponents/Staff/Order.tsx";
import OrderDetail from "../components/adminComponents/Staff/OrderDetail.tsx";
import Index from "../components/staffComponents/Index.tsx";
export default function StaffRouter() {
  return (
    <Routes>
      <Route path="/*" element={<Dashboard />}>
        <Route path="Branch/:branchId/Order/:orderId/Category/:categoryId" element={<Index />} />
        <Route path="Branch/:branchId/Tables" element={<Dashboard />} />
        <Route path="Branch/:branchId/Table/:tableId/Order/:orderId/Category/:categoryId" element={<OrderDetail />} />
      </Route>
    </Routes>
  );
}
