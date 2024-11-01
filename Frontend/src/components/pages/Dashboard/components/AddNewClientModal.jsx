import { useState, useContext } from "react";
import { DisplayContext } from "../../../../contexts/DisplayContext";

function AddNewClientModal({setAddNewClient}) {

    const { user } = useContext(DisplayContext);

    const [nameInputValue, setNameInputValue] = useState();
    const [cpfInputValue, setCpfInputValue] = useState();
    const [emailInputValue, setEmailInputValue] = useState();

    const [error, setError] = useState(false);

    const validateInputs = () => {
        if(nameInputValue.trim() == null || cpfInputValue.trim() == null || emailInputValue.trim() == null){
            setError(true)
            return
        }
    }

    const handleNameChange = (event) => {
        setNameInputValue(event.target.value)
    }

    const handleCpfChange = (event) => {
        setCpfInputValue(event.target.value)
    }

    const handleEmailChange = (event) => {
        setEmailInputValue(event.target.value)
    }

    const addNewClient = async() => {
        validateInputs()

        try{
            const response = await fetch("http://localhost:8080/client", {
                method: "POST",
                headers: {
                    'Content-Type': "application/json",
                },
                body: JSON.stringify({
                    cpf: cpfInputValue,
                    name: nameInputValue,
                    email: emailInputValue,
                    library: {
                        id: user
                    }
                })
            })

            setAddNewClient(false)
        }catch(error){
            console.log(error);
        }
    }

    return (
        <div className="new-book-modal">
            <div className="new-book-area">
                <div className="new-book-title">Add new Client</div>
                <div className="new-book-inputs">
                    <input type="text" name="cpf" placeholder="client CPF" value={cpfInputValue} onChange={handleCpfChange}/>
                    <input type="text" name="name" placeholder="client name" value={nameInputValue} onChange={handleNameChange}/>
                    <input type="text" name="name" placeholder="client email" value={emailInputValue} onChange={handleEmailChange}/>
                    <button className="add" onClick={() => addNewClient()}>Add</button>
                    <button className="cancel" onClick={() => setAddNewClient(false)}>Cancel</button>
                </div>
            </div>
        </div>
    );

}

export default AddNewClientModal;