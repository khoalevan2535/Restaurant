import axios from "axios";

const API_URL = "http://localhost:8080/Combo";

export const getCombos = async () => {
  try {
    const response = await axios.get(API_URL);
    return response.data;
  } catch (error) {
    throw new Error(error.response?.data?.message || "Failed to fetch combos");
  }
};

export const createCombo = async (formData) => {
  try {
    const response = await axios.post(API_URL, formData, {
      headers: {
        "Content-Type": "multipart/form-data",
      },
    });    
    return response.data;
  } catch (error) {
    throw new Error(error.response?.data?.message || "Failed to create combo");
  }
};

export const updateCombo = async (id, formData) => {
  try {
    const response = await axios.patch(`${API_URL}/${id}`, formData, {
      headers: {
        "Content-Type": "multipart/form-data",
      },
    });
    return response.data;
  } catch (error) {
    console.error("Error details:", error);
    if (error.response) {
      console.error("Response data:", error.response.data); 
      throw new Error(error.response.data.message || "Failed to update combo");
    } else if (error.request) {
      throw new Error("Failed to send request to update combo");
    } else {
      throw new Error("An unexpected error occurred while updating combo");
    }
  }
};

export const deleteCombo = async (id) => {
  try {
    const response = await axios.delete(`${API_URL}/${id}`);
    return response.data; 
  } catch (error) {
    throw new Error(error.response?.data?.message || "Failed to delete combo");
  }
};

export const searchCombos = async (keyword) => {
  try {
    const response = await axios.get(`${API_URL}/search`, {
      params: { keyword },
    });
    return response.data;
  } catch (error) {
    throw new Error(error.response?.data?.message || "Failed to search combos");
  }
};