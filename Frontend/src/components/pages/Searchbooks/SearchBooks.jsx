import { useContext, useEffect, useState } from "react";
import { DisplayContext } from "../../../contexts/DisplayContext";
import "./SearchBooks.css";
import { LeftArrowAlt, SearchAlt } from "styled-icons/boxicons-regular";
import { Gear } from "styled-icons/bootstrap";
import BookModal from "./components/BookModal";

function SearchBooks() {
    const { user, setDisplay } = useContext(DisplayContext);

    const [isLoading, setIsLoading] = useState(false);
    const [typeSearch, setTypeSearch] = useState("name");
    const [searchOptions, setSearchOptions] = useState(true);
    const [seeUnavailable, setSeeUnavailable] = useState(true);

    const [books, setBooks] = useState([]);
    const [libName, setLibName] = useState("");

    const [titleInputContent, setTitleInputContent] = useState("");
    const [authorInputContent, setAuthorInputContent] = useState("");
    const [idInputContent, setIdInputContent] = useState("");

    const [openedBook, setOpenedBook] = useState(null);

    const handleOpenSearchOptions = () => {
        setSearchOptions(!searchOptions);
    };

    const clearInputs = () => {
        setTitleInputContent("");
        setAuthorInputContent("");
        setIdInputContent("");
        getData();
    };

    const handleSearchByName = () => {
        setTypeSearch("name");
        clearInputs();
    };

    const handleSearchById = () => {
        setTypeSearch("id");
        clearInputs();
    };

    const handleSearchByAuthor = () => {
        setTypeSearch("author");
        clearInputs();
    };

    const getData = async () => {
        setIsLoading(true);
        try {
            const response = await fetch(`http://localhost:8080/library/${user}`);
            if (!response.ok) {
                console.log("error");
                return;
            }
            const result = await response.json();
            
            const filteredBooks = seeUnavailable ? result.booksDTO : result.booksDTO.filter(book => !book.loaned);
            setBooks(filteredBooks);
            setLibName(result.libraryName);
        } catch (error) {
            console.log(error);
        } finally {
            setIsLoading(false);
        }
    };

    const handleNameInputChange = (e) => {
        const value = e.target.value;
        setTitleInputContent(value);
        if (value.trim() === "") {
            getData();
        } else {
            getDataByName(value);
        }
    };

    useEffect(() => {
        if (typeSearch === "name" && titleInputContent.trim() !== "") {
            getDataByName(titleInputContent);
        } else if (typeSearch === "author" && authorInputContent.trim() !== "") {
            getDataByAuthor(authorInputContent);
        } else if (typeSearch === "id" && idInputContent.trim() !== "") {
            // Aqui você precisaria implementar a função getDataById caso ainda não tenha
            getDataById(idInputContent);
        } else {
            getData();
        }
    }, [seeUnavailable, typeSearch, titleInputContent, authorInputContent, idInputContent, openedBook]);

    const getDataByName = async (searchTerm) => {
        setIsLoading(true);
        if (searchTerm.trim() === "") {
            getData();
            return;
        }

        try {
            const response = await fetch(`http://localhost:8080/library/${user}/search?bookName=${searchTerm}`);
            if (!response.ok) {
                console.log("error");
                return;
            }

            const result = await response.json();
            const filteredBooks = seeUnavailable ? result : result.filter(book => !book.loaned);
            setBooks(filteredBooks);
        } catch (error) {
            console.log(error);
        } finally {
            setIsLoading(false);
        }
    };

    const getDataByAuthor = async (searchTerm) => {
        setIsLoading(true);
        if (searchTerm.trim() === "") {
            getData();
            return;
        }

        try {
            const response = await fetch(`http://localhost:8080/library/${user}/author-search?authorName=${searchTerm}`);
            if (!response.ok) {
                console.log("error");
                return;
            }

            const result = await response.json();
            const filteredBooks = seeUnavailable ? result : result.filter(book => !book.loaned);
            setBooks(filteredBooks);
        } catch (error) {
            console.log(error);
        } finally {
            setIsLoading(false);
        }
    };

    const handleToggleVisibleBooks = () => {
        setSeeUnavailable((prev) => !prev);
    };

    return (
        <div className="dashboard">
            <div className="dashboard-area">
                <div className="back-button" onClick={() => setDisplay("dashboard")}><LeftArrowAlt className="back-arrow"/></div>
                <div className="book-search-area">
                    <div className="book-search-header">
                        <div className="libName">{libName} <span> - Book Searching </span></div>
                        <div className="search-bar">
                            <SearchAlt className="search-icon" />
                            {typeSearch === "name" && (
                                <input type="text" placeholder="Search by title" value={titleInputContent} onChange={handleNameInputChange} />
                            )}
                            {typeSearch === "author" && (
                                <input type="text" placeholder="Search by author" value={authorInputContent} onChange={(e) => setAuthorInputContent(e.target.value)} />
                            )}
                            {typeSearch === "id" && (
                                <input type="text" placeholder="Search by ID" value={idInputContent} />
                            )}
                            <Gear className={searchOptions ? "gear-icon-desactive" : "gear-icon"} onClick={handleOpenSearchOptions} />
                        </div>
                    </div>
                    {searchOptions && (
                        <div className="search-options">
                            <div>
                                Select the search type:
                                <button className={typeSearch === "name" ? "active" : ""} onClick={handleSearchByName}>Search by Title</button>
                                <button className={typeSearch === "id" ? "active" : ""} onClick={handleSearchById}>Search by ID</button>
                                <button className={typeSearch === "author" ? "active" : ""} onClick={handleSearchByAuthor}>Search by Author</button>
                            </div>
                            <button className={seeUnavailable ? "active" : ""} onClick={handleToggleVisibleBooks}>
                                {seeUnavailable ? "Hide Unavailable books" : "Show Unavailable books"}
                            </button>
                        </div>
                    )}
                    <div className="book-table">
                        <div className="table-header">
                            <div className="header-id">ID</div>
                            <div className="header-name">Title</div>
                            <div className="header-author">Author</div>
                            <div className="header-genre">Genre</div>
                        </div>
                        {isLoading && ""}
                        {!isLoading && (
                            <div className={searchOptions ? 'table-body-w-options' : "table-body"}>
                                {books.map((book) => (
                                    <div className={book.loaned ? "table-item unavailable" : "table-item"} key={book.id} onClick={()=> setOpenedBook(book.id)}>
                                        <div className="item-id">{book.id}</div>
                                        <div className="item-name">{book.title}</div>
                                        <div className="item-author">{book.author}</div>
                                        <div className="item-genre">{book.genre}</div>
                                    </div>
                                ))}
                            </div>
                        )}
                    </div>
                </div>
            </div>
            {openedBook !== null &&
                <BookModal bookId={openedBook} setBook={setOpenedBook} />
            }
        </div>
    );
}

export default SearchBooks;
