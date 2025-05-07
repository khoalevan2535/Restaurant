import React from "react";
import { Routes, Route } from "react-router-dom";
import AdminLayout from "../layouts/AdminLayout.tsx";
import BranchDetail from "../components/adminComponents/Branch/BranchDetail.tsx";
import BranchList from "../components/adminComponents/Branch/BranchList.js";
import BranchCreate from "../components/adminComponents/Branch/BranchCreate.tsx";
import Combo from "../components/adminComponents/Combo.js";
import ComboDish from "../components/adminComponents/ComboDish.js";
import Discount from "../components/adminComponents/Discount.js";
import ListTable from "../components/adminComponents/Table/ListTable.tsx";
export default function AdminRouter() {
  return (
    <Routes>
      <Route path="/" element={<AdminLayout />}>
        <Route path="Branch" element={<BranchList />} />
        <Route path="Branch/Detail/:branchId" element={<BranchDetail />} />
        <Route path="Branch/Create" element={<BranchCreate />} />
        <Route path="Branch/Update/:branchId" element={<BranchCreate />} />
        <Route path="Combo" element={<Combo />} />
        <Route path="ComboDish" element={<ComboDish />} />
        <Route path="Discount" element={<Discount />} />
        <Route path="Branch/:branchId/Tables" element={<ListTable />} />
      </Route>
    </Routes>
  );
}
