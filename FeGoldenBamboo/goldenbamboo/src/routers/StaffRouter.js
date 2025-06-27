import { Routes, Route } from "react-router-dom";
import StaffLayout from "../layouts/StaffLayout.tsx";
import Index from "../components/staffComponents/Index.tsx";
export default function StaffRouter() {
  return (
    <Routes>
      <Route path="/" element={<StaffLayout />}>
        <Route path="Branch/:branchId/Order/:orderId/Category/:categoryId" element={<Index />} />
      </Route>
      {/* <Route path="/*" element={<S />}>
        <Route path="Branch/:branchId/Order/:orderId/Category/:categoryId" element={<Index />} />
        <Route path="Branch/:branchId/Tables" element={<Dashboard />} />
        <Route path="Branch/:branchId/Table/:tableId/Order/:orderId/Category/:categoryId" element={<OrderDetail />} />
      </Route> */}
    </Routes>
  );
}
