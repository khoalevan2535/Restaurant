import { BrowserRouter, Routes, Route } from "react-router-dom";
import ClientRouter from "./routers/ClientRouter";
import StaffRouter from "./routers/StaffRouter";
import AdminRouter from "./routers/AdminRouter";
import Login from "./pages/Login.js";
import Register from "./pages/Register.tsx";
import Index from "./pages/Index.js";
import ProtectedRoute from "./routers/ProtectedRoute.js";
function App() {
  return (
    <BrowserRouter>
      <Routes>
        <Route path="/Login" element={<Login />} />
        <Route path="/Register" element={<Register />} />

        <Route path="/" element={<Index />} />

        <Route
          path="/Client/*"
          element={
            <ProtectedRoute requiredRole="client">
              <ClientRouter />
            </ProtectedRoute>
          }
        />

        <Route
          path="/Admin/*"
          element={
            <ProtectedRoute requiredRole="admin">
              <AdminRouter />
            </ProtectedRoute>
          }
        />

        <Route
          path="/Staff/*"
          element={
            <ProtectedRoute requiredRole="staff">
              <StaffRouter />
            </ProtectedRoute>
          }
        />
      </Routes>
    </BrowserRouter>
  );
}

export default App;
