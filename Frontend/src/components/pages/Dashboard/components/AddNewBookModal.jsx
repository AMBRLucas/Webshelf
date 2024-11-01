import { useState, useContext } from "react";
import { DisplayContext } from "../../../../contexts/DisplayContext";

function AddNewBookModal({setAddNewBook}){

    const { user } = useContext(DisplayContext);

    const [titleInputContent, setTitleInputContent] = useState();
    const [authorInputContent, setAuthorInputContent] = useState();
    const [genreInputContent, setGenreInputContent] = useState();

    const [error, setError] = useState(false);

    const verifyInputs = () => {
        if(titleInputContent.trim() == '' || authorInputContent.trim() == '' || genreInputContent.trim() == ''){
          setError(true);
          return  
        }
    }

    const handleTitleChange = (event) => {
        setTitleInputContent(event.target.value);
    }

    const handleAuthorChange = (event) => {
        setAuthorInputContent(event.target.value);
    }

    const handleGenreChange = (event) => {
        setGenreInputContent(event.target.value);
    }

    const addNewBook = async() => {
        verifyInputs();

        try{
            const response = await fetch("http://localhost:8080/book", {
                method: "POST",
                headers: {
                    "Content-Type": "application/json",
                },
                body: JSON.stringify({
                    title: titleInputContent,
                    author: authorInputContent,
                    genre: genreInputContent,
                    library: {
                        id: user
                    }
                })
            })
            setAddNewBook(false)
        }catch(error){
            console.log(error);
        }
    }

    return (
        <div className="new-book-modal">
            <div className="new-book-area">
                <div className="new-book-title">Add new book</div>
                <div className="new-book-inputs">
                    <input type="text" placeholder="Title" value={titleInputContent} onChange={handleTitleChange}/>
                    <input type="text" placeholder="Author" value={authorInputContent} onChange={handleAuthorChange}/>
                    <input type="text" placeholder="Genre" value={genreInputContent} onChange={handleGenreChange}/>
                    <button className="add" onClick={() => addNewBook()}>Add</button>
                    <button className="cancel" onClick={() => setAddNewBook(false)}>Cancel</button>
                </div>
            </div>
        </div>
    );
}

export default AddNewBookModal;