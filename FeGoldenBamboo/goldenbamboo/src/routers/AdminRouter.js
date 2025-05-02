import React from "react";
import { Routes, Route } from "react-router-dom";
import AdminLayout from "../layouts/AdminLayout.tsx";
import Dashboad from "../pages/adminPages/Dashboad.tsx";
import Branch from "../pages/adminPages/Branch.tsx";
export default function AdminRouter() {
  return (
    <Routes>
      <Route path="/" element={<AdminLayout />}>
        <Route path="Dashboard" element={<Dashboad />} />
        <Route path="Branch" element={<Branch />} />
      </Route>
    </Routes>
  );
}
