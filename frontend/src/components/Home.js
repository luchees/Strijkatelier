import React, {useState} from 'react';
import Admin from "./Admin";
import {Link, Route} from "react-router-dom";
import ChangePassword from "./ChangePassword";
import ItemTableComponent from "./item/ItemTableComponent";
import Box from "@material-ui/core/Box";
import Items from "./Items";
function Home(props) {
    return(
        <div >
            Home page content
            <ul>
                <li>
                    <Link to="/">Home Page</Link>
                </li>
                <li>
                    <Link to="/admin">Admin Page</Link>
                </li>
                <li>
                    <Link to="/account/change-password">Change password</Link>
                </li>
                <Route exact path="/" component={Home} />
                <Route path="/admin" component={Admin} />
                <Route path="/account/change-password" component={ChangePassword} />

            </ul>
            <Items showError={props.showError} />

        </div>

    )
}

export default Home;