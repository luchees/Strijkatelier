import React from "react";
import Button from "@material-ui/core/Button";
import {useAuth} from "../context/auth";

function Admin(props) {
    const { setAuthTokens } = useAuth();

    function logOut() {
        setAuthTokens();
    }

    return (
        <div>
            <div>Admin Page</div>
            <Button onClick={logOut}>Log out</Button>
        </div>
    );
}

export default Admin;