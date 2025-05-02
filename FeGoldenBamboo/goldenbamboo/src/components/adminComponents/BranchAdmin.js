import { useParams } from "react-router-dom";
import { useEffect, useState } from "react";
import { findMenuByBranch } from "../services/branchService";

const BranchMenu = () => {
  const { branchId } = useParams();
  const [menus, setMenus] = useState([]);

  useEffect(() => {
    if (branchId) {
      findMenuByBranch(branchId)
        .then((data) => setMenus(data))
        .catch((err) => console.error("Error fetching menus:", err));
    }
  }, [branchId]);

  return (
    <div>
      <h2>Menu của Chi nhánh {branchId}</h2>
      {menus.length > 0 ? (
        <ul>
          {menus.map((menu) => (
            <li key={menu.id}>
              <h3>{menu.name}</h3>
              <p>Món ăn: {menu.menuDishes.length} món</p>
              <p>Combo: {menu.menuCombos.length} combo</p>
            </li>
          ))}
        </ul>
      ) : (
        <p>Không có menu nào!</p>
      )}
    </div>
  );
};

export default BranchMenu;
