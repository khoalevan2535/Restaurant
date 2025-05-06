import axios from 'axios';
import { API_URL } from "../../configs/api_url.js";

export interface Product {
  id: number;
  description: string;
  image: string | null;
  name: string;
  price: number;
  status: boolean;
}

export const getProducts = async (): Promise<Product[]> => {
  const res = await axios.get<Product[]>(`${API_URL}/Dish`);
  return res.data;
};