import React, {useState} from 'react';
import axios from 'axios';
import {API_BASE_URL} from '../../constants/apiContants';
import {withRouter} from "react-router-dom";
import {useAuth} from "../../context/auth";
import withStyles from "@material-ui/core/styles/withStyles";

const style = {
    loginCard: {
        minHeight: "60vh"
    },

    registerMessage: {
        marginTop: "10vh"
    }
}

function ChangePassword(props) {
    const [state, setState] = useState({
        password: "",
        successMessage: null,
        isLoggedIn: "false",
        prevPassword: "",
        matchingPassword: ""
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
            "prevPassword": state.prevPassword,
            "matchingPassword": state.matchingPassword
        }
        axios.post(API_BASE_URL + 'user/changePassword', payload)
            .then(result => {
                if (result.status === 200) {

                    //setAuthTokens(result.data);
                    props.showError(null);
                    redirectToHome();
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
    return (
        <div className="card col-12 col-lg-4 loginCard mt-2 hv-center">
            <form>
                <div className="form-group text-left">
                    <label htmlFor="exampleInputPassword1">Previous Password</label>
                    <input type="password"
                           className="form-control"
                           id="prevPassword"
                           placeholder="*******"
                           value={state.prevPassword}
                           onChange={handleChange}
                    />
                </div>
                <div className="form-group text-left">
                    <label htmlFor="exampleInputPassword1">New Password</label>
                    <input type="password"
                           className="form-control"
                           id="password"
                           placeholder="*****"
                           value={state.password}
                           onChange={handleChange}
                    />
                </div>
                <div className="form-group text-left">
                    <label htmlFor="exampleInputPassword2">Confirm password</label>
                    <input type="matchingPassword"
                           className="form-control"
                           id="matchingPassword"
                           placeholder="******"
                           value={state.matchingPassword}
                           onChange={handleChange}
                    />
                    <small id="emailHelp" className="form-text text-muted">We'll never share your email with anyone
                        else.</small>
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
        </div>
    )
}

export default withStyles(style)(ChangePassword);