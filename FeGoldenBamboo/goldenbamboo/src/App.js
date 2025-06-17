import { BrowserRouter, Routes, Route } from "react-router-dom";
import ClientRouter from "./routers/ClientRouter";
import StaffRouter from "./routers/StaffRouter";
import AdminRouter from "./routers/AdminRouter";
import Login from "./pages/Login.js";
import Register from "./pages/Register.tsx";
import Index from "./pages/Index.js";

function App() {
  return (
    <BrowserRouter>
      <Routes>
        <Route path="/Login" element={<Login />} />
        <Route path="/Register" element={<Register />} />

        <Route path="/" element={<Index />} />

        <Route path="/Client/*" element={<ClientRouter />} />

        <Route path="/Admin/*" element={<AdminRouter />} />
        <Route path="/Manager/*" element={<AdminRouter />} />

        <Route path="/Staff/*" element={<StaffRouter />} />
      </Routes>
    </BrowserRouter>
  );
}

export default App;
