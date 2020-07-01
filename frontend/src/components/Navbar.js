import React from 'react';
import { Link } from 'react-router-dom'
import withStyles from "@material-ui/core/es/styles/withStyles";
import {white} from "color-name";

const style = {
    title: {
        marginTop: "20px"
    }
};
const Navbar = ()=>{
    return(
        <nav className="nav-wrapper">
            <div className="container">
                <Link to="/" className="brand-logo">Strijkatelier</Link>

                <ul className="right" style={{color:white}}>
                    <li><Link to="/login">Login</Link></li>
                    <li><Link to="/register">Register</Link></li>
                    <li><Link to="/home">Home</Link></li>
                </ul>
            </div>
        </nav>
    )
}
export default withStyles(style)(Navbar);