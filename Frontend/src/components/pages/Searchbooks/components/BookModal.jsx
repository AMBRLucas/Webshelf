import { useEffect, useState, useContext } from "react";
import { DisplayContext } from "../../../../contexts/DisplayContext";
import UpdateModal from './UpdateModal'
import "./BookModal.css";
import LoanModal from "./LoanModal";
import { LeftArrowAlt } from "styled-icons/boxicons-regular";

function BookModal({ bookId, setBook }) {

    const { user } = useContext(DisplayContext);

    const [bookData, setBookData] = useState();
    const [isLoading, setIsLoading] = useState(true);

    const [ordenedHistory, setOrdenedHistory] = useState([]);

    const [updateModal, setUpdateModal] = useState(false);
    const [loanModal, setLoanModal] = useState(false);

    useEffect(() => {
        if (!bookId) return;

        const fetchData = async () => {
            try {
                const response = await fetch(`http://localhost:8080/book/${bookId}/history`);
                const result = await response.json();
                setBookData(result);

                const sortedHistory = result.history.sort((a, b) => new Date(b.registryDate) - new Date(a.registryDate));
                setOrdenedHistory(sortedHistory);
            } catch (error) {
                console.log(error);
            } finally {
                setIsLoading(false);
            }
        };
        fetchData();
    }, [bookId, updateModal, loanModal]);

    const dataFormatter = (data) => {
        const date = new Date(data);
        return date.toLocaleDateString('pt-BR', {
            day: '2-digit',
            month: '2-digit',
            year: 'numeric'
        });
    };

    const isLate = (data) => {
        const givenDate = new Date(data);
        const actualDate = new Date();

        givenDate.setHours(0,0,0,0);
        actualDate.setHours(0,0,0,0);

        return givenDate < actualDate;
    }

    return (
        <div className="modal-background">
            {!isLoading &&
                <div className="book-modal">
                    <div className="back-button" onClick={() => setBook(null)}>
                        <LeftArrowAlt className="back-arrow"/>
                    </div>
                    <div className="book-infos">
                        <div className={bookData.loaned ? "indisponible-information" : "information"}>
                            <p>{"#"}{bookId} <span> - {bookData.title}</span></p>
                            <span>Author: {bookData.author}</span>
                            <span>Genre: {bookData.genre}</span>
                            <span>Loan count: {ordenedHistory.length}</span>
                        </div>
                        <div className="book-history">
                            <div className="history-header">History:</div>
                            <div className="history-list">
                                {ordenedHistory.length === 0 && "Ainda não houve empréstimos desse item"}
                                {ordenedHistory.map((loan) => (
                                    <div className={`${loan.active ? "history-active-item" : "history-item"} ${(isLate(loan.expected_return) && loan.active) ? "late" : ""}`} key={loan.id}>
                                        <div className="id">{loan.id}</div>
                                        <div className="client_name">{loan.client_name}</div>
                                        <div className="registryDate">{dataFormatter(loan.registryDate)}</div>
                                        <div className="expected_return">{dataFormatter(loan.expected_return)}</div>
                                    </div>
                                ))}
                            </div>
                        </div>
                        <div className="buttons">
                            <button disabled={bookData.loaned} onClick={() => setLoanModal(true)}>Loan this book</button>
                            <button disabled={bookData.loaned} onClick={() => setUpdateModal(true)}>Update this book info</button>
                            <button disabled={!bookData.loaned}>Return book</button>
                        </div>
                    </div>
                    {updateModal && <UpdateModal BookData={bookData} LibraryId={user} setUpdateModal={setUpdateModal}/>}
                    {loanModal && <LoanModal bookData={bookData} library={user} setLoanModal={setLoanModal} />}
                </div>
            }
        </div>
    );
}

export default BookModal;
