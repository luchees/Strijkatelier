import React, {useState} from 'react';
import './App.css';
import {BrowserRouter as Router} from "react-router-dom";
import AlertComponent from './components/AlertComponent';
import {AuthContext} from "./context/auth";
import RoutesComponent from "./components/routes/routes-component";
import {DRAWER_WIDTH} from "./constants/apiContants";
import useMediaQuery from "@material-ui/core/useMediaQuery";
import Navbar from "./components/Drawer/Navbar";
import makeStyles from "@material-ui/core/styles/makeStyles";


const useStyles = makeStyles({
    container: {
        paddingLeft: DRAWER_WIDTH,
        flexShrink: 0,
    },
    mobile: {
        paddingLeft: 0
    }
});

function App() {
    const [title, updateTitle] = useState(null);
    const matches = useMediaQuery('(min-width:600px)');

    const classes = useStyles();

    const [errorMessage, updateErrorMessage] = useState(null);
    const mobile = matches ? classes.container : classes.mobile;
    return (
        <AuthContext.Provider value= {true} >
            < Router>
                <div className= "App" >
                    <Navbar title= {title} showError={updateErrorMessage}/>
                    <div className={mobile}><RoutesComponent/></div>


                    <AlertComponent errorMessage={errorMessage} hideError={updateErrorMessage}/>
                </div>
            </Router>
        </AuthContext.Provider>

    );

}

export default App;
