import { BrowserRouter, Routes, Route } from "react-router-dom";
import ClientRouter from "./routers/ClientRouter";
import StaffRouter from "./routers/StaffRouter";
import AdminRouter from "./routers/AdminRouter";
import Login from "./pages/authPages/Login";
import Register from "./pages/Register.tsx";
function App() {
  return (
    <BrowserRouter>
      <Routes>
        <Route path="/Login" element={<Login />} />
        <Route path="/Register" element={<Register />} />

        <Route path="/*" element={<ClientRouter />} />

        <Route path="/Admin/*" element={<AdminRouter />} />

        <Route path="/Staff/*" element={<StaffRouter />} />
      </Routes>
    </BrowserRouter>
  );
}

export default App;
