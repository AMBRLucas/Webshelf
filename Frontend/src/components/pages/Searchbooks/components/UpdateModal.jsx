import { useState } from "react";

function UpdateModal({BookData, LibraryId, setUpdateModal}){
    const [titleInputValue, setTitleInputValue] = useState(BookData.title);
    const [authorInputValue, setAuthorInputValue] = useState(BookData.author);
    const [genreInputValue, setGenreInputValue] = useState(BookData.genre);

    const handleTitleChange = (e) => {
        setTitleInputValue(e.target.value);
    }

    const handleAuthorChange = (e) => {
        setAuthorInputValue(e.target.value);
    }

    const handleGenreChange = (e) => {
        setGenreInputValue(e.target.value);
    }

    const updateApiData = async() => {
        try{
            const response = await fetch(`http://localhost:8080/book/update/${BookData.id}`, {
                method: "PUT",
                headers: {
                    'Content-Type': 'application/json',
                },
                body: JSON.stringify({
                    title: titleInputValue,
                    author: authorInputValue,
                    genre: genreInputValue
                })
            })

            setUpdateModal(false)
        }catch(error){
            console.log(error);
        }
    }

    return (
        <div className="update-modal">
            <div className="update-area">
                <span className="title">Update book data</span>
                <span className="subtitle">Warning: Updates are permanent, do it with caution</span>
                <input type="text" name="title" value={titleInputValue} onChange={handleTitleChange}/>
                <input type="text" name="author" value={authorInputValue} onChange={handleAuthorChange}/>
                <input type="text" name="genre" value={genreInputValue} onChange={handleGenreChange}/>
                <button className="update-button" onClick={updateApiData}>Update</button>
                <button className="cancel-button" onClick={()=> setUpdateModal(false)}>Cancel</button>
            </div>
        </div>
    )
}

export default UpdateModal;