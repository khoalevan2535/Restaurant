import React from "react";
import { Routes, Route } from "react-router-dom";
import AdminLayout from "../layouts/AdminLayout.tsx";
import Branch from "../components/adminComponents/Branch.js";
export default function AdminRouter() {
  return (
    <Routes>
      <Route path="/" element={<AdminLayout />}>
        <Route path="Branch" element={<Branch />} />
      </Route>
    </Routes>
  );
}
