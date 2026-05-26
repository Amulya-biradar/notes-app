import { useNavigate } from "react-router-dom";
import { useEffect, useState } from "react";
import { getAllNotes,createNote,deleteNote,updateNote,searchNotes } from "../services/noteService";

function DashboardPage() {

  const navigate = useNavigate();
  const [notes, setNotes] = useState([]);
  const [formData, setFormData] = useState({
  title: "",
  content: "",
  pinned: false,
});
  const [editingNoteId, setEditingNoteId] = useState(null);
  const [searchKeyword, setSearchKeyword] =
  useState("");

  const handleLogout = () => {

    localStorage.removeItem("token");

    navigate("/");
  };

  const fetchNotes = async () => {

  try {

    const response = await getAllNotes();

    console.log(response);

    const sortedNotes = response.content.sort(
  (a, b) => b.pinned - a.pinned
);

setNotes(sortedNotes);

  } catch (error) {

    console.error(error);
  }
};

    const handleSearch = async (keyword) => {

  setSearchKeyword(keyword);

  try {

    if (keyword.trim() === "") {

      fetchNotes();

      return;
    }

    const response = await searchNotes(keyword);

    setNotes(response);

  } catch (error) {

    console.error(error);
  }
};

    const handleChange = (event) => {

    setFormData({
    ...formData,
    [event.target.name]: event.target.value,
    });
    };

    const handleCreateNote = async (event) => {

  event.preventDefault();

  try {

    if (editingNoteId) {

  await updateNote(
    editingNoteId,
    formData
  );

} else {

  await createNote(formData);
}

    setFormData({
      title: "",
      content: "",
      pinned: false,
    });

    setEditingNoteId(null);

    fetchNotes();

  } catch (error) {

    console.error(error);
  }
};

const handleDeleteNote = async (id) => {

  try {

    await deleteNote(id);

    fetchNotes();

  } catch (error) {

    console.error(error);
  }
};
    const handleEditNote = (note) => {

  setEditingNoteId(note.id);

  setFormData({
    title: note.title,
    content: note.content,
    pinned: note.pinned,
  });
};

const handleTogglePin = async (note) => {

  try {

    await updateNote(note.id, {
      title: note.title,
      content: note.content,
      pinned: !note.pinned,
    });

    fetchNotes();

  } catch (error) {

    console.error(error);
  }
};

    useEffect(() => {
    fetchNotes();
    }, []);

  return (
    <div className="min-h-screen bg-gray-100 p-6">

      <div className="flex justify-between items-center mb-6">

        <h1 className="text-3xl font-bold">
          Dashboard
        </h1>

        <button
          onClick={handleLogout}
          className="bg-red-500 text-white px-4 py-2 rounded-lg hover:bg-red-600 transition"
        >
          Logout
        </button>

      </div>

      <div className="mb-6">

  <input
    type="text"
    placeholder="Search notes..."
    value={searchKeyword}
    onChange={(e) =>
      handleSearch(e.target.value)
    }
    className="w-full border border-gray-300 rounded-lg p-3"
  />

</div>

    <div className="bg-white p-6 rounded-xl shadow-md mb-6">

  <h2 className="text-2xl font-bold mb-4">
    {editingNoteId ? "Update Note" : "Create Note"}
  </h2>

  <form
    onSubmit={handleCreateNote}
    className="space-y-4"
  >

    <input
      type="text"
      name="title"
      placeholder="Enter title"
      value={formData.title}
      onChange={handleChange}
      className="w-full border border-gray-300 rounded-lg p-3"
    />

    <textarea
      name="content"
      placeholder="Enter content"
      value={formData.content}
      onChange={handleChange}
      className="w-full border border-gray-300 rounded-lg p-3"
      rows="4"
    />

    <button
      type="submit"
      className="bg-blue-600 text-white px-5 py-2 rounded-lg hover:bg-blue-700 transition"
    >
      Create Note
    </button>

  </form>

</div>

      <div className="grid gap-4">

  {notes.map((note) => (

    <div
      key={note.id}
      className={`p-5 rounded-xl shadow-md ${
  note.pinned
    ? "bg-yellow-100"
    : "bg-white"
}`}
    >

      <div className="flex justify-between items-center mb-2">

  <h2 className="text-xl font-bold">
    {note.title}
  </h2>

  <button
    onClick={() => handleTogglePin(note)}
    className="text-xl"
  >
    {note.pinned ? "📌" : "📍"}
  </button>

</div>
      <p className="text-gray-700">
        {note.content}
      </p>

      <button
  onClick={() => handleEditNote(note)}
  className="mt-4 mr-3 bg-yellow-500 text-white px-4 py-2 rounded-lg hover:bg-yellow-600 transition"
>
  Edit
</button>

      <button
  onClick={() => handleDeleteNote(note.id)}
  className="mt-4 bg-red-500 text-white px-4 py-2 rounded-lg hover:bg-red-600 transition"
>
  Delete
</button>

    </div>
    ))}

    </div>

    </div>
  );
}

export default DashboardPage;