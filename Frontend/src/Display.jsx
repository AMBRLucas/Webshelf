import { useContext } from "react";
import LoginPage from "./components/pages/Login/LoginPage";
import { DisplayContext } from "./contexts/DisplayContext";
import Dashboard from "./components/pages/Dashboard/Dashboard";
import SearchBooks from "./components/pages/Searchbooks/SearchBooks";

function Display() {

    const{display, setDisplay, user, setUser} = useContext(DisplayContext) 

    return(
        <>
            {(display === "login" && user === null) && <LoginPage />}
            {(display === "login" && user !== null) && <p>{setDisplay("dashboard")}</p>}
            {(display === "dashboard" && user === null) && <p>{setDisplay("login")}</p>}
            {(display === 'dashboard' && user !== null) && <Dashboard />}
            {(display === 'booksearch' && user !== null) && <SearchBooks />}
        </>
    )
}

export default Display;