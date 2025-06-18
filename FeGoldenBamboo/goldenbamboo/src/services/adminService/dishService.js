import axios from "axios";

const BASE_URL = "http://localhost:8080/api/dishes";

export const getAllDishes = () => {
  return axios.get(BASE_URL);
};

export const getDishesById = (id) => {
  return axios.get(`${BASE_URL}/${id}`);
};

export const createDish = (dish) => {
  return axios.post(BASE_URL, dish, {
    headers: {
      "Content-Type": "multipart/form-data",
    },
  });
};

export const updateDish = (id, formData) => {
  return axios.put(`${BASE_URL}/${id}`, formData, {
    headers: {
      "Content-Type": "multipart/form-data",
    },
  });
};

export const deleteDish = (id) => {
  return axios.delete(`${BASE_URL}/${id}`);
};