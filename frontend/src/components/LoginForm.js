import React, {useState} from 'react';
import axios from 'axios';
import {API_BASE_URL} from '../constants/apiContants';
import {Redirect, withRouter} from "react-router-dom";
import {useAuth} from "../context/auth";
import withStyles from "@material-ui/core/styles/withStyles";

const style = {
    loginCard: {
        minHeight: "60vh"
    },

    registerMessage: {
        marginTop: "10vh"
    }
}

function LoginForm(props) {
    const [state, setState] = useState({
        email: "",
        password: "",
        successMessage: null,
        isLoggedIn: "false"
    })
    const {setAuthTokens} = useAuth();
    const handleChange = (e) => {
        const {id, value} = e.target
        setState(prevState => ({
            ...prevState,
            [id]: value
        }))
    }

    const handleSubmitClick = (e) => {

        e.preventDefault();
        const payload = {
            "email": state.email,
            "password": state.password,
        }
        axios.post(API_BASE_URL + 'login', payload)
            .then(result => {
                if (result.status === 200) {

                    //setAuthTokens(result.data);
                    props.showError(null);
                    state.isLoggedIn(true);
                } else {
                    props.showError(result);
                }
            }).catch(e => {
            props.showError(e.toString());
        });

    }
    const redirectToHome = () => {
        props.updateTitle('Home')
        props.history.push('/home');
    }
    const redirectToRegister = () => {
        props.history.push('/users/register');
        props.updateTitle('Register');
    }


    if (state.isLoggedIn === true) {
        return <Redirect to="/admin"/>;
    }
    return (
        <div className="card col-12 col-lg-4 loginCard mt-2 hv-center">
            <form>
                <div className="form-group text-left">
                    <label htmlFor="exampleInputEmail1">Email address</label>
                    <input type="email"
                           className="form-control"
                           id="email"
                           aria-describedby="emailHelp"
                           placeholder="Enter email"
                           value={state.email}
                           onChange={handleChange}
                    />
                    <small id="emailHelp" className="form-text text-muted">We'll never share your email with anyone
                        else.</small>
                </div>
                <div className="form-group text-left">
                    <label htmlFor="exampleInputPassword1">Password</label>
                    <input type="password"
                           className="form-control"
                           id="password"
                           placeholder="Password"
                           value={state.password}
                           onChange={handleChange}
                    />
                </div>
                <div className="form-check">
                </div>
                <button
                    type="submit"
                    className="btn btn-primary"
                    onClick={handleSubmitClick}
                >Submit
                </button>
            </form>
            <div className="alert alert-success mt-2" style={{display: state.successMessage ? 'block' : 'none'}}
                 role="alert">
                {state.successMessage}
            </div>
            <div className="registerMessage">
                <span>Dont have an account? </span>
                <span className="loginText" onClick={() => redirectToRegister()}>Register</span>
            </div>
        </div>
    )
}

export default withRouter(withStyles(style)(LoginForm));