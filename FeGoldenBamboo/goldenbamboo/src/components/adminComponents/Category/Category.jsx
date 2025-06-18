import React, { useEffect, useState } from "react";
import { 
    getAllCategories, 
    createCategory, 
    updateCategory, 
    deleteCategory 
} from "../services/cateroryService";

function CategoryList() {
    const [categories, setCategories] = useState([]);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState(null);
    const [newCategoryName, setNewCategoryName] = useState("");
    const [adding, setAdding] = useState(false);
    const [addError, setAddError] = useState(null);

    // for update functionality
    const [editingId, setEditingId] = useState(null);
    const [editingName, setEditingName] = useState("");

    const fetchCategories = () => {
        setLoading(true);
        getAllCategories()
            .then((response) => {
                setCategories(response.data);
                setLoading(false);
            })
            .catch((err) => {
                setError(err);
                setLoading(false);
            });
    };

    useEffect(() => {
        fetchCategories();
    }, []);

    const handleAddCategory = (e) => {
        e.preventDefault();
        const trimmedName = newCategoryName.trim();
    
        if (!trimmedName) {
            setAddError({ message: "Category name không được để trống" });
            return;
        }
    
        const isDuplicate = categories.some(cat => cat.name.toLowerCase() === trimmedName.toLowerCase());
        if (isDuplicate) {
            setAddError({ message: "Category name không được để trống" });
            return;
        }
    
        setAdding(true);
        setAddError(null);
        const category = { name: trimmedName };
    
        createCategory(category)
            .then((response) => {
                setCategories([...categories, response.data]);
                setNewCategoryName("");
                setAdding(false);
            })
            .catch((err) => {
                setAddError(err);
                setAdding(false);
            });
    };
    

    const handleDelete = (id) => {
        if (!window.confirm("Are you sure you want to delete this category?")) return;
        deleteCategory(id)
            .then(() => {
                setCategories(categories.filter((cat) => cat.id !== id));
            })
            .catch((err) => {
                alert("Error deleting category: " + err.message);
            });
    };

    const startEditing = (id, name) => {
        setEditingId(id);
        setEditingName(name);
    };

    const handleUpdate = (e) => {
        e.preventDefault();
        const trimmedName = editingName.trim();
    
        if (!trimmedName) {
            alert("Category name không được để trống");
            return;
        }
    
        const isDuplicate = categories.some(cat =>
            cat.name.toLowerCase() === trimmedName.toLowerCase() && cat.id !== editingId
        );
        if (isDuplicate) {
            alert("Category name không được để trống");
            return;
        }
    
        updateCategory(editingId, { name: trimmedName })
            .then((response) => {
                setCategories(categories.map((cat) =>
                    cat.id === editingId ? response.data : cat
                ));
                setEditingId(null);
                setEditingName("");
            })
            .catch((err) => {
                alert("Error updating category: " + err.message);
            });
    };
    

    const cancelEditing = () => {
        setEditingId(null);
        setEditingName("");
    };

    return (
        <div className="container my-4">
            <div className="card shadow">
                <div className="card-header bg-primary text-white">
                    <h4 className="mb-0">Category List</h4>
                </div>
                <div className="card-body">
                    {/* Add Category Form */}
                    <form onSubmit={handleAddCategory} className="mb-4">
                        <div className="input-group">
                            <input
                                type="text"
                                className="form-control"
                                placeholder="Enter category name"
                                value={newCategoryName}
                                onChange={(e) => setNewCategoryName(e.target.value)}
                            />
                            <button type="submit" className="btn btn-success" disabled={adding}>
                                {adding ? "Adding..." : "Add Category"}
                            </button>
                        </div>
                        {addError && (
                            <div className="alert alert-danger mt-2">
                                Error: {addError.message}
                            </div>
                        )}
                    </form>

                    {/* Loading & Error UI */}
                    {loading && (
                        <div className="text-center">
                            <div className="spinner-border text-primary" role="status">
                                <span className="visually-hidden">Loading...</span>
                            </div>
                            <p className="mt-2">Loading categories...</p>
                        </div>
                    )}
                    {error && (
                        <div className="alert alert-danger" role="alert">
                            Error fetching categories: {error.message}
                        </div>
                    )}

                    {/* Categories Table */}
                    {!loading && !error && categories.length > 0 ? (
                        <div className="table-responsive">
                            <table className="table table-bordered table-hover">
                                <thead className="table-light">
                                    <tr>
                                        <th>#</th>
                                        <th>Category Name</th>
                                        <th>Actions</th>
                                    </tr>
                                </thead>
                                <tbody>
                                    {categories.map((category, index) => (
                                        <tr key={category.id}>
                                            <td>{index + 1}</td>
                                            <td>
                                                {editingId === category.id ? (
                                                    <form onSubmit={handleUpdate}>
                                                        <input
                                                            type="text"
                                                            className="form-control"
                                                            value={editingName}
                                                            onChange={(e) => setEditingName(e.target.value)}
                                                        />
                                                    </form>
                                                ) : (
                                                    category.name
                                                )}
                                            </td>
                                            <td>
                                                {editingId === category.id ? (
                                                    <>
                                                        <button 
                                                            className="btn btn-sm btn-primary me-2"
                                                            onClick={handleUpdate}
                                                        >
                                                            Save
                                                        </button>
                                                        <button 
                                                            className="btn btn-sm btn-secondary"
                                                            onClick={cancelEditing}
                                                        >
                                                            Cancel
                                                        </button>
                                                    </>
                                                ) : (
                                                    <>
                                                        <button 
                                                            className="btn btn-sm btn-info me-2"
                                                            onClick={() => startEditing(category.id, category.name)}
                                                        >
                                                            Update
                                                        </button>
                                                        <button 
                                                            className="btn btn-sm btn-danger"
                                                            onClick={() => handleDelete(category.id)}
                                                        >
                                                            Delete
                                                        </button>
                                                    </>
                                                )}
                                            </td>
                                        </tr>
                                    ))}
                                </tbody>
                            </table>
                        </div>
                    ) : (!loading && !error) && (
                        <p className="text-muted">No categories found.</p>
                    )}
                </div>
            </div>
        </div>
    );
}

export default CategoryList;