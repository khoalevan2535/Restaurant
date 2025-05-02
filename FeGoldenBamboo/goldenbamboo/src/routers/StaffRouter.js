import { Routes, Route } from "react-router-dom";
import StaffLayout from "../layouts/StaffLayout.tsx";
import Dashboard from "../components/staffComponents/DashboardStaff.js";
export default function StaffRouter() {
  return (
    <Routes>
      <Route path="/*" element={<StaffLayout />}>
        <Route path="Branch/:branchId/Order/:orderId/Category/:categoryId" element={<Dashboard />} />
      </Route>
    </Routes>
  );
}
