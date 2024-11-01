import { useEffect, useState,useContext } from "react";
import { DisplayContext } from "../../../contexts/DisplayContext";
import AddNewBookModal from "./components/AddNewBookModal"
import "./Dashboard.css"
import AddNewClientModal from "./components/AddNewClientModal";

function Dashboard(){

    const {display, setDisplay, user, setUser} = useContext(DisplayContext) 

    const [library, setLibrary] = useState();
    const [isLoaded, setIsLoaded] = useState(false);
    const [modalIsOpen, setModalIsOpen] = useState();

    const [addNewBook, setAddNewBook] = useState(false);
    const [addNewClient, setAddNewClient] = useState(false);

    const colectData = async(id) => {
        try{
            const response = await fetch(`http://localhost:8080/library/${id}/w-count`)
            
            if(!response.ok){
                console.log("error")
                return
            }

            const result = await response.json()
            setLibrary(result);
            console.log(library)
            setIsLoaded(true);
        }catch(error){
            console.log(error)
        }
    }

    const logout = () => {
        localStorage.removeItem("id");
        setUser(null)
        window.location.reload();
    }

    useEffect(()=>{
        colectData(user)
    }, [])

    return(
        <div className="dashboard">
            {!isLoaded ? <p>CARREGANDO...</p> :

                <div className="dashboard-area">
                    <div className="return-button" onClick={()=> setModalIsOpen(true)}> {"<-"} </div>
                    <div className="actions-area">
                        <div className="libName">{library.libraryName}</div>
                        <div className="section">
                        <div className="action-section">
                            <div className="title-actions"><span>Collection</span></div>
                            <div className="actions">
                                <div className="action-card" onClick={() => setDisplay("booksearch")}>
                                    <span>Check Collection</span>
                                </div>
                                <div className="action-card" onClick={() => setAddNewBook(true)}>
                                    <span>Add new book</span>
                                </div>
                                <div className="action-card">
                                    <span>Update book data</span>
                                </div>
                                <div className="action-card">
                                    <span>Remove book</span>
                                </div>
                            </div>
                        </div>
                        <div className="action-section">
                            <div className="title-actions"><span>Loans</span></div>
                            <div className="actions">
                                <div className="action-card">
                                    <span>Make loan</span>
                                </div>
                                <div className="action-card">
                                    <span>Check Active loans</span>
                                </div>
                                <div className="action-card">
                                    <span>Loan History</span>
                                </div>
                            </div>
                        </div>
                        <div className="action-section">
                            <div className="title-actions"><span>Client</span></div>
                            <div className="actions">
                                <div className="action-card" onClick={() => setAddNewClient(true)}>
                                    <span>Registry client</span>
                                </div>
                                <div className="action-card">
                                    <span>Check Active loans</span>
                                </div>
                                <div className="action-card">
                                    <span>Loan History</span>
                                </div>
                            </div>
                        </div>
                        </div>
                    </div>
                    {modalIsOpen &&
                        <div className="logout-modal">
                            <div className="modal">
                                <div className="exit-title">
                                    <span className="x-button" onClick={() => setModalIsOpen(false)}>X</span>
                                    <span className="x-title">Are you sure you want to leave?</span>
                                </div>
                                <div className="exit-buttons">
                                    <button className="exit" onClick={() => logout()}>Yes, exit</button>
                                </div>
                            </div>
                        </div>
                    }

                    {addNewBook && 
                        <AddNewBookModal setAddNewBook={setAddNewBook} />
                    }

                    {addNewClient &&
                        <AddNewClientModal setAddNewClient={setAddNewClient} />
                    }
                </div>
            }
        </div>
    )
}

export default Dashboard;