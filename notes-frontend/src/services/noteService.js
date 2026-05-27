import axiosInstance from "../api/axiosInstance";

export const getAllNotes = async (page = 0) => {

  const response = await axiosInstance.get(
    `/notes?pageNo=${page}&pageSize=5`
  );

  return response.data;
};

export const createNote = async (noteData) => {

  const response = await axiosInstance.post(
    "/notes",
    noteData
  );

  return response.data;
};

export const deleteNote = async (id) => {

  const response = await axiosInstance.delete(
    `/notes/${id}`
  );

  return response.data;
};

export const updateNote = async (id, noteData) => {

  const response = await axiosInstance.put(
    `/notes/${id}`,
    noteData
  );

  return response.data;
};

export const searchNotes = async (keyword) => {

  const response = await axiosInstance.get(
    `/notes/search?keyword=${keyword}`
  );

  return response.data;
};