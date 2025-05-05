import axios from 'axios';

const API_URL = 'http://localhost:8080/';

const api = axios.create({
  baseURL: API_URL,
  timeout: 5000,
});


api.interceptors.response.use(
  (response) => response.data,
  (error) => {
    console.error('API Error:', error.response?.data || error.message);
    return Promise.reject(error.response?.data || error.message);
  }
);

export const getCombos = async () => {
  try {
    return await api.get('/Combo');
  } catch (error) {
    throw new Error('Failed to fetch combos');
  }
};

export const getDishes = async () => {
  try {
    return await api.get('/Dish');
  } catch (error) {
    throw new Error('Failed to fetch dishes');
  }
};

export const getComboDishes = async () => {
  try {
    return await api.get('/ComboDishe');
  } catch (error) {
    throw new Error('Failed to fetch combo dishes');
  }
};

export const getComboDishesFull = async () => {
  try {
    return await api.get('/ComboDishe');
  } catch (error) {
    throw new Error('Failed to fetch full combo dishes');
  }
};

export const createComboDish = async (comboId, dish1Id, dish2Id) => {
  try {
    return await api.post('/ComboDishe', {
      comboId,
      dish1Id,
      dish2Id
    });
  } catch (error) {
    throw new Error('Failed to create combo dish');
  }
};

export const updateComboDish = async (id, payload) => {
  try {
    return await api.put(`/ComboDishe/${id}`, payload);
  } catch (error) {
    throw new Error('Failed to update combo dish');
  }
};

export const deleteComboDish = async (id) => {
  try {
    return await api.delete(`/ComboDishe/${id}`); 
  } catch (error) {
    throw new Error('Failed to delete combo dish');
  }
};


