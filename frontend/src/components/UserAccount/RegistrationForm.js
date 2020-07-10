import React, {useState} from 'react';
import axios from 'axios';
import {API_BASE_URL} from '../../constants/apiContants';
import {withRouter} from "react-router-dom";
import withStyles from "@material-ui/core/styles/withStyles";
import green from "@material-ui/core/colors/green";

const style = {
    loginText : {
        color: "#007bff",
        fontWeight: "bold",
        cursor: "pointer"
    },
    successMessage :{
        color: green,
        fontWeight: "bold"
    }
}
function RegistrationForm(props) {


    const [state, setState] = useState({
        email: "",
        lastName: "",
        firstName: "",
        password: "",
        matchingPassword: "",
        successMessage: null,
        authorized: "false"
    })
    const handleChange = (e) => {
        const {id, value} = e.target
        setState(prevState => ({
            ...prevState,
            [id]: value
        }))
    }
    const sendDetailsToServer = () => {
        if (state.email.length && state.password.length && state.firstName.length && state.lastName.length && state.matchingPassword.length) {
            props.showError(null);
            const payload = {
                "email": state.email,
                "lastName": state.lastName,
                "firstName": state.firstName,
                "password": state.password,
                "matchingPassword": state.matchingPassword
            }

            axios.post(API_BASE_URL + 'users/register', payload)
                .then(result => {
                    if (result.status === 200) {
                        props.showError(null)
                        redirectToHome();
                        setState(prevState => ({
                            ...prevState,
                            'successMessage': result.status
                        }))
                        props.showError(null)
                    } else {
                        props.showError('Register failed')
                    }
                }).catch(e => {
                props.showError(e.toString());
            });
            // const returnValue= register(payload);
            //  console.log(returnValue);


        } else {
            props.showError('Please fill in all fields')
        }

    }
    const redirectToHome = () => {
        props.updateTitle('Home')
        props.history.push('/home');
    }
    const redirectToLogin = () => {
        props.updateTitle('Login')
        props.history.push('/login');
    }
    const handleSubmitClick = (e) => {
        e.preventDefault();
        if (state.password === state.matchingPassword) {
            sendDetailsToServer()
        } else {
            props.showError('Passwords do not match');
        }
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
                    <label htmlFor="exampleInputFirstName1">Firstname</label>
                    <input type="firstName"
                           className="form-control"
                           id="firstName"
                           placeholder="Enter firstname"
                           value={state.firstName}
                           onChange={handleChange}
                    />
                </div>
                <div className="form-group text-left">
                    <label htmlFor="exampleInputLastName1">Lastname</label>
                    <input type="lastName"
                           className="form-control"
                           id="lastName"
                           placeholder="Enter lastname"
                           value={state.lastName}
                           onChange={handleChange}
                    />
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
                <div className="form-group text-left">
                    <label htmlFor="exampleInputPassword1">Confirm Password</label>
                    <input type="password"
                           className="form-control"
                           id="matchingPassword"
                           placeholder="Confirm Password"
                           value={state.matchingPassword}
                           onChange={handleChange}
                    />
                </div>
                <button
                    type="submit"
                    className="btn btn-primary"
                    onClick={handleSubmitClick}
                >
                    Register
                </button>
            </form>
            <div className="alert alert-success mt-2" style={{display: state.successMessage ? 'block' : 'none'}}
                 role="alert">
                {state.successMessage}
            </div>
            <div className="mt-2">
                <span>Already have an account? </span>
                <span className="loginText" onClick={() => redirectToLogin()}>Login here</span>
            </div>

        </div>
    )
}

export default  withRouter(withStyles(style)(RegistrationForm));