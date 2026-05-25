import axiosInstance from "../api/axiosInstance";

export const registerUser = async (userData) => {

  const response = await axiosInstance.post(
    "/auth/register",
    userData
  );

  return response.data;
};