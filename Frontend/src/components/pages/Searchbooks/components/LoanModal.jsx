import { useContext, useEffect, useState } from "react";
import { Gear } from "styled-icons/bootstrap";
import { LeftArrowAlt, SearchAlt, User } from "styled-icons/boxicons-regular";
import { BackgroundColor } from "styled-icons/foundation";
import { DisplayContext } from "../../../../contexts/DisplayContext";

function LoanModal({bookData, library, setLoanModal}){

    const { user } = useContext(DisplayContext);
    
    const [clients, setClients] = useState([]);
    const [actualDate, setActualDate] = useState();

    const [filteredClientList, setFilteredClientList] = useState();

    const [selectedClient, setSelectedClient] = useState({id: '', name: '', cpf: ""});
    const [returnDate, setReturnDate] = useState(()=> {
        const today = new Date();
        return today.toISOString().slice(0, 10);
    })

    const [typeSearch, setTypeSearch] = useState("cpf");
    const [searchOptions, setSearchOptions] = useState(false)


    const handleDataChange = (event) => {
        setReturnDate(event.target.value);
    }
    
    const [searchTerm, setSearchTerm] = useState('');

    const handleSearchTerm = (event) => {
        setSearchTerm(event.target.value)
    }

    const clearInputs = () => {
        setSearchTerm('');
    }

    const handleSearchByName = () => {
        setTypeSearch("name")
        clearInputs();
    }

    const handleSearchByCpf = () => {
        setTypeSearch("cpf")
        clearInputs();
    }

    const handleSearchByEmail = () => {
        setTypeSearch("email")
        clearInputs();
    }

    useEffect(()=>{
        const getClients = async() => {
            try{
                const response = await fetch(`http://localhost:8080/library/${library}`);
                const result = await response.json();
                
                setClients(result.clientDTO.filter(client => client.hasActiveLoan === false));
            }catch(error){
                console.log(error);
            }
        }

        const today = new Date();
        const formattedDate = today.toISOString().split('T')[0];
        setActualDate(formattedDate);

        getClients();

        console.log(clients)
    }, [])

    const makeLoan = async() => {
        if(selectedClient.id !== '' || returnDate != actualDate){
            try{
                const response = await fetch("http://localhost:8080/loan", {
                    method: "POST",
                    headers: {
                        'Content-Type': 'application/json',
                    },
                    body: JSON.stringify({
                        library: {
                            id: user
                        },
                        book: {
                            id: bookData.id
                        },
                        client: {
                            id: selectedClient.id,
                        },
                        returnDate: returnDate
                    })
                })
                setLoanModal(false);
            }catch(error){
                console.log(error)
            }
        }
    }

    return (
        <div className="loan-modal">
            <div className="loan-area">
                <div className="back-button" onClick={() => setLoanModal(false)}> <LeftArrowAlt className="back-arrow"/> </div>
                <div className="loan-data">
                    <span className="title" onClick={() => console.log(selectedClient)}>Make a loan</span>
                    <div className="loan-inputs">
                        <div className="input-area">
                            <input type="text" name="id" value={bookData.id} disabled/>
                            <input type="text" name="title" value={bookData.title} disabled/>
                            <input type="text" name="client-id" disabled placeholder="Client ID" value={selectedClient.id}/>
                            <input type="text" name="client-name" disabled placeholder="Client Name" value={selectedClient.name}/>
                            <input type="date" name="actual-date" disabled value={actualDate}/>
                            <input type="date" name="return-date" placeholder="Expected return" className="return-date" value={returnDate} onChange={handleDataChange}/>
                            <button className="loan-btn" onClick={makeLoan}>Loan</button>
                        </div>
                        <div className="client-list">
                            <div className="client-search">
                                <div className="client-input"> 
                                    <SearchAlt className="search-icon"/>
                                    {typeSearch === "cpf" && <input type="text" name="client-search" placeholder="Search client by CPF" className="search-client-list" value={searchTerm} onChange={handleSearchTerm}/>}
                                    {typeSearch === "name" && <input type="text" name="client-search" placeholder="Search client by name" className="search-client-list" value={searchTerm} onChange={handleSearchTerm}/>}
                                    {typeSearch === "email" && <input type="text" name="client-search" placeholder="Search client by email" className="search-client-list" value={searchTerm} onChange={handleSearchTerm}/>}
                                    <div className={searchOptions ? "client-options" : "client-options-desactive"} onClick={() => setSearchOptions(!searchOptions)}>
                                        <Gear className="search-ico"/>
                                    </div>
                                </div>
                                {searchOptions &&
                                    <div className="client-search-options">
                                    <div className={typeSearch === 'cpf' ? "opt-btn active" : "opt-btn"} onClick={() => handleSearchByCpf()}>search by CPF</div>
                                    <div className={typeSearch === 'name' ? "opt-btn active" : "opt-btn"} onClick={() => handleSearchByName()}>search by name</div>
                                    <div className={typeSearch === 'email' ? "opt-btn active" : "opt-btn"} onClick={() => handleSearchByEmail()}>search by email</div>
                                </div>
                                }
                            </div>
                            {searchTerm.trim() === '' ? (
                                <div className={searchOptions ? "list-reduced" : "list"}>
                                    {clients.map((client) => (
                                        <div className={`${selectedClient.cpf === client.cpf ? "list-item selected" : "list-item"} ${client.hasActiveLoan ? "vermelho" : 'normal'}`} key={client.id} onClick={() => setSelectedClient(client)}>
                                                <div className="client-icon">
                                                    <User className="icon-img" />
                                                </div>
                                            <div className="client-data">
                                                <div className={selectedClient.cpf === client.cpf ? "data-cpf selected" : "data-cpf"}>
                                                    CPF: {client.cpf}
                                                </div>
                                                <div className={selectedClient.cpf === client.cpf ? "data-name selected" : "data-name"}>
                                                    {client.name}
                                                </div>
                                                <div className={selectedClient.cpf === client.cpf ? "data-email selected" : "data-email"}>
                                                    {client.email}
                                                </div>
                                            </div>
                                        </div>
                                    ))}
                                </div>
                            ) : (
                            <div className={searchOptions ? "list-reduced" : "list"}>
                            {typeSearch === 'cpf' && 
                            <>
                            {clients.filter((client) => client.cpf.toLowerCase().includes(searchTerm.toLowerCase()) && !client.hasActiveLoan).map((client) => (
                                <div className={`${selectedClient.cpf === client.cpf ? "list-item selected" : "list-item"} ${client.hasActiveLoan ? "vermelho" : 'normal'}`} key={client.id} onClick={() => setSelectedClient(client)}>
                                    <div className="client-icon">
                                        <User className="icon-img" />
                                    </div>
                                    <div className="client-data">
                                        <div className={selectedClient.cpf === client.cpf ? "data-cpf selected" : "data-cpf"}>
                                            CPF: {client.cpf}
                                        </div>
                                        <div className={selectedClient.cpf === client.cpf ? "data-name selected" : "data-name"}>
                                            {client.name}
                                        </div>
                                        <div className={selectedClient.cpf === client.cpf ? "data-email selected" : "data-email"}>
                                            {client.email}
                                        </div>
                                    </div>
                                </div>
                            ))}
                            </>
                            }
                            {typeSearch === 'name' && <>
                            {clients
                            .filter((client) => client.name.toLowerCase().includes(searchTerm.toLowerCase()) && !client.hasActiveLoan)
                            .map((client) => (
                                <div
                                    className={`${selectedClient.cpf === client.cpf ? "list-item selected" : "list-item"} ${client.hasActiveLoan ? "vermelho" : 'normal'}`}
                                    key={client.id}
                                    onClick={() => setSelectedClient(client)}
                                >
                                    <div className="client-icon">
                                        <User className="icon-img" />
                                    </div>
                                    <div className="client-data">
                                        <div className={selectedClient.cpf === client.cpf ? "data-cpf selected" : "data-cpf"}>
                                            CPF: {client.cpf}
                                        </div>
                                        <div className={selectedClient.cpf === client.cpf ? "data-name selected" : "data-name"}>
                                            {client.name}
                                        </div>
                                        <div className={selectedClient.cpf === client.cpf ? "data-email selected" : "data-email"}>
                                            {client.email}
                                        </div>
                                    </div>
                                </div>
                            ))}
                            </>}
                            {typeSearch === 'email' && <>
                            {clients
                            .filter((client) => client.email.toLowerCase().includes(searchTerm.toLowerCase()) && !client.hasActiveLoan)
                            .map((client) => (
                                <div
                                    className={`${selectedClient.cpf === client.cpf ? "list-item selected" : "list-item"} ${client.hasActiveLoan ? "vermelho" : 'normal'}`}
                                    key={client.id}
                                    onClick={() => setSelectedClient(client)}
                                >
                                    <div className="client-icon">
                                        <User className="icon-img" />
                                    </div>
                                    <div className="client-data">
                                        <div className={selectedClient.cpf === client.cpf ? "data-cpf selected" : "data-cpf"}>
                                            CPF: {client.cpf}
                                        </div>
                                        <div className={selectedClient.cpf === client.cpf ? "data-name selected" : "data-name"}>
                                            {client.name}
                                        </div>
                                        <div className={selectedClient.cpf === client.cpf ? "data-email selected" : "data-email"}>
                                            {client.email}
                                        </div>
                                    </div>
                                </div>
                            ))}
                            </>}

                        </div>
                        )}
                        </div>
                    </div>
                </div>
            </div>
        </div>
    )
}

export default LoanModal;