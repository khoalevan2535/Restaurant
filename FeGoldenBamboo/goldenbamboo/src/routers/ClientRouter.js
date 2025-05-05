import { Routes, Route } from "react-router-dom";
import ClientLayout from "../layouts/ClientLayout.tsx";

export default function ClientRouter() {
  return (
    <Routes>
      <Route path="/" element={<ClientLayout />}></Route>
    </Routes>
  );
}
