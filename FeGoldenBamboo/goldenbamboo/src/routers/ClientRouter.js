import { Routes, Route } from "react-router-dom";
import ClientLayout from "../layouts/ClientLayout.tsx";
import ClientHome from "../pages/clientPages/ClientHome.js";

export default function ClientRouter() {
  return (
    <Routes>
      <Route path="/" element={<ClientLayout />}>
        <Route path="Home" element={<ClientHome />} />
      </Route>
    </Routes>
  );
}
