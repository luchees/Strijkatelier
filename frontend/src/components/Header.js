import React from 'react';
import {Link, withRouter} from "react-router-dom";

function Header(props) {
    const capitalize = (s) => {
        if (typeof s !== 'string') return ''
        return s.charAt(0).toUpperCase() + s.slice(1)
    }
    const title = capitalize(props.location.pathname.substring(1,props.location.pathname.length))
    return(
        <nav className="navbar navbar-dark bg-primary">
            <div className="row col-12 d-flex text-white">
                    <Link to="/" className="brand-logo">Strijkatelier</Link>

                    {/*<ul className="right">*/}
                    {/*    <li><Link to="/login">Login</Link></li>*/}
                    {/*    <li><Link to="/register">Register</Link></li>*/}
                    {/*    <li><Link to="/home">Home</Link></li>*/}
                    {/*</ul>*/}


                <span className="h3">{props.title || title}</span>
            </div>
        </nav>
    )
}
export default withRouter(Header);