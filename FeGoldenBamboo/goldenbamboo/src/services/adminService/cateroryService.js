import axios from "axios";

const BASE_URL = "http://localhost:8080/api/categories";

export const getAllCategories = () => {
    return axios.get(BASE_URL);
};

export const getCategoryById = (id) => {
    return axios.get(`${BASE_URL}/${id}`);
};

export const createCategory = (category) => {
    return axios.post(BASE_URL, category, {
        headers: {
            "Content-Type": "application/json",
        },
    });
};

export const updateCategory = (id, category) => {
    return axios.put(`${BASE_URL}/${id}`, category, {
        headers: {
            "Content-Type": "application/json",
        },
    });
};

export const deleteCategory = (id) => {
    return axios.delete(`${BASE_URL}/${id}`);
};