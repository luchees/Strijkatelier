import React from 'react';
import Admin from "./Admin";
import {Link, Route} from "react-router-dom";
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
                <Route exact path="/" component={Home} />
                <Route path="/admin" component={Admin} />
            </ul>
        </div>
    )
}

export default Home;