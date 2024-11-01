import { useState, useContext } from "react";
import { ContentContext } from "./loginContext";
import { DisplayContext } from "../../../../contexts/DisplayContext";

function Registry(){

    const {logOrReg, setLogOrReg} = useContext(ContentContext)
    const { user, setUser, display, setDisplay } = useContext(DisplayContext)

    const [libName, setLibName] = useState();
    const [username, setUsername] = useState();
    const [email, setEmail] = useState();
    const [password, setPassword] = useState();

    const handleLibNameChange = (event) => {
        setLibName(event.target.value);
    }
    const handleUsernameChange = (event) => {
        setUsername(event.target.value);
    }
    const handlePasswordChange = (event) => {
        setPassword(event.target.value);
    }

    const handleEmailChange = (event) => {
        setEmail(event.target.value);
    }

    const registryNewLibrary = async() => {
        try{
            const response = await fetch('http://localhost:8080/library', {
                method: "POST",
                headers: {
                    "Content-Type": "application/json",
                },
                body: JSON.stringify({
                    libraryName: libName,
                    username: username,
                    email: email,
                    password: password
                })
            })
            
            const result = await response.json();

            console.log(result)

            localStorage.setItem("id",result.id);
            setUser(result.id);

            setDisplay("dashboard")

        }catch(error){
            console.log(error)
        }
    }

    return(
        <div className="input-area">
            <input className="input" type="text" name="libraryname" id="libraryname" placeholder="Your library name" value={libName} onChange={handleLibNameChange} />
            <input className="input" type="text" name="username" id="username" placeholder="Your username" value={username} onChange={handleUsernameChange} />
            <input className="input" type="text" name="email" id="email" placeholder="Your email" value={email} onChange={handleEmailChange} />
            <input className="input" type="password" name="password" id="password" placeholder="Password"  value={password} onChange={handlePasswordChange}/>

            <button onClick={registryNewLibrary}>Create Account</button>
            <p>Already have an account? <span onClick={() => setLogOrReg("login")}>Login</span></p>
        </div>
    );
}

export default Registry;