import React, {useState, createContext} from "react";

const DisplayContext = createContext();

const DisplayProvider = ({ children }) => {
    const [display, setDisplay] = useState("login");
    const [user, setUser] = useState(null);

    return(
        <DisplayContext.Provider value={{display, setDisplay, user, setUser}}>
            {children}
        </DisplayContext.Provider>
    )
}

export {DisplayContext, DisplayProvider};