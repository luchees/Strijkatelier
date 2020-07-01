import React from 'react';
import Admin from "./Admin";
import {Link, Route} from "react-router-dom";
import ChangePassword from "./ChangePassword";
function Home(props) {
    return(
        <div className="mt-2">
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
        </div>
    )
}

export default Home;