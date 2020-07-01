import React, {useState} from 'react';
import './App.css';
import Header from './components/Header';
import LoginForm from './components/LoginForm';
import RegistrationForm from './components/RegistrationForm';
import Home from './components/Home';
import {BrowserRouter as Router, Route} from "react-router-dom";
import AlertComponent from './components/AlertComponent';
import Navbar from "./components/Navbar";
import Admin from "./components/Admin";
import {AuthContext} from "./context/auth";
import PrivateRoute from './components/PrivateRoute';
import ChangePassword from "./components/ChangePassword";

function App() {
    const [title, updateTitle] = useState(null);


    const [errorMessage, updateErrorMessage] = useState(null);
    return (
        <AuthContext.Provider value={true}>
            <Router>
                <div className="App">
                    <Header title={title}/>
                    <div className="container d-flex align-items-center flex-column">
                        <Navbar
                        />
                        <Route path="/" exact={true}>
                            <RegistrationForm showError={updateErrorMessage} updateTitle={updateTitle}/>
                        </Route>
                        <Route path="/register">
                            <RegistrationForm showError={updateErrorMessage} updateTitle={updateTitle}/>
                        </Route>
                        <Route path="/login">
                            <LoginForm showError={updateErrorMessage} updateTitle={updateTitle}/>
                        </Route>
                        <Route path="/home">
                            <Home/>
                        </Route>
                        <PrivateRoute path="/admin" component={Admin}/>
                        <PrivateRoute path="/account/change-password" component={ChangePassword}/>

                        <AlertComponent errorMessage={errorMessage} hideError={updateErrorMessage}/>
                    </div>
                </div>
            </Router>
        </AuthContext.Provider>

    );

}

export default App;
