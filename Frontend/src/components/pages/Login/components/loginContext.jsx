import React, {useState, createContext} from "react";

const ContentContext = createContext();

const ContentProvider = ({ children }) => {
    const [logOrReg, setLogOrReg] = useState("registration");

    return(
        <ContentContext.Provider value={{logOrReg, setLogOrReg}}>
            {children}
        </ContentContext.Provider>
    )
}

export {ContentContext, ContentProvider};