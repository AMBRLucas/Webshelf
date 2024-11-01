import { useState, useContext } from "react";
import { ContentContext } from "./loginContext";
import { DisplayContext } from "../../../../contexts/DisplayContext";
import Error from "./error";

function Login(){
    const {logOrReg, setLogOrReg} = useContext(ContentContext)
    const {display, setDisplay, user, setUser} = useContext(DisplayContext) 

    const [isError, setIsError] = useState(false);

    const [username, setUsername] = useState("");
    const [password, setPassword] = useState("");


    const handleUsernameChange = (event) => {
        setUsername(event.target.value);
    }
    const handlePasswordChange = (event) => {
        setPassword(event.target.value);
    }

    const handleLoginClick = async() =>{
        setIsError(false)
        if(username === "" || password === "" ){
            setIsError(true);
        }else{
            try{
                const response = await fetch('http://localhost:8080/auth/login', {
                    method: 'POST',
                    headers: {
                        'Content-Type': 'application/json',
                    },
                    body: JSON.stringify({
                        username: username,
                        password: password
                    })
                })

                if(response.status === 401){
                    setIsError(true);
                    return
                }

                const result = await response.json();
                console.log(result);

                localStorage.setItem("id",result);
                setUser(result);

                setDisplay("dashboard");

            }catch(error){
                console.log(error.message);
            }
        }
    }

    return(
        <div className="input-area">
            {isError ? <Error msg="Error: Invalid Username or Password" /> : ""}
            <input className="input" type="text" name="username" id="username" placeholder="Your username" value={username} onChange={handleUsernameChange} />
            <input className="input" type="password" name="password" id="password" placeholder="Password"  value={password} onChange={handlePasswordChange}/>

            <button onClick={() => handleLoginClick()}>Login</button>
            <p>Doesn't have an account? <span onClick={() => setLogOrReg("registration")}>Sing in</span></p>
        </div>
    );
}

export default Login;