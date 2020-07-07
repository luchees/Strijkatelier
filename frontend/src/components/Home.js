import React, {useState} from 'react';
import Admin from "./Admin";
import {Link, Route} from "react-router-dom";
import ChangePassword from "./ChangePassword";
import MaterialItemTable from "./item/MaterialItemTable";
function Home(props) {
    return(
        <div >
            Home page content
            <MaterialItemTable showError={props.showError}  />

        </div>

    )
}

export default Home;