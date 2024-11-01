import "./Login.css"
import logo from "../../../images/WebShelfLogo.png"
import { useEffect, useState, useContext } from "react";
import { ContentProvider } from "./components/loginContext";
import { DisplayContext } from "../../../contexts/DisplayContext";
import Inputs from "./components/InputContent";

function LoginPage() {

    const {display, setDisplay, user, setUser} = useContext(DisplayContext) 

    useEffect(()=>{
        setUser(localStorage.getItem("id"))
    })

    return(
        <div className="login-area">
            <img src={logo} />
            <ContentProvider>
                <Inputs />
            </ContentProvider>
        </div>
    );
}

export default LoginPage;