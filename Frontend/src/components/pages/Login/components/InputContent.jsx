import { useContext } from "react";
import { ContentContext } from "./loginContext";
import Registry from "./Registry";
import Login from "./Login";

function Inputs(){
    const {logOrReg, setLogOrReg} = useContext(ContentContext)

    return(
        <div>
            {logOrReg == "registration" ? <Registry /> : ""}
            {logOrReg == "login" ? <Login /> : ""}
        </div>
    )
    
}

export default Inputs;