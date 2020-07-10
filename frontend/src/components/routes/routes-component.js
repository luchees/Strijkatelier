import React from "react";
import {Switch} from "react-router-dom";
import Redirect from "react-router-dom/Redirect";
import Route from "react-router-dom/Route";
import ProtectedRoute from "./protected-route-component";
import CustomerMaterialTable from "../customer/CustomerMaterialTable";
import BasketMaterialTable from "../basket/BasketMaterialTable";
import Home from "../Home";
import MaterialItemTable from "../item/MaterialItemTable";
import RegistrationForm from "../UserAccount/RegistrationForm";
import LoginForm from "../UserAccount/LoginForm";
import Admin from "../Admin";
import ChangePassword from "../UserAccount/ChangePassword";
import { useTheme } from '@material-ui/core/styles';
import {DRAWER_WIDTH} from "../../constants/apiContants";
import {withStyles} from "@material-ui/styles";
import AddBasketComponent from "../basket/AddBasketComponent";



class RoutesComponent extends React.Component {

    state = {
        drawerOpen: false,
    };

    render() {
        const {clientId, authenticated, idToken, getInitialAuthentication} = this.props;

        return (
            <Switch >

                {/* Base Routes */}
                <Redirect exact path="/" to="/home"/>
                <Route name="home" exact path="/home" render={props => <Home/>}/>


                {/* Routes requiring authentication  */}
                <ProtectedRoute name="customers" exact path="/customers"
                                render={props => <CustomerMaterialTable showError={props.showError} {...props}/>}
                />
                <ProtectedRoute name="items" exact path="/items"
                                render={props => <MaterialItemTable showError={props.showError} {...props} />}
                />
                <ProtectedRoute name="baskets" exact path="/baskets"
                                render={props => <BasketMaterialTable showError={props.showError} {...props} />}
                />
                <ProtectedRoute name="addBasket" exact path="/addBasket"
                                render={props => <AddBasketComponent showError={props.showError} {...props} />}
                />
                <Route path="/register">
                    <RegistrationForm showError={this.props.showError}/>
                </Route>
                <Route path="/login">
                    <LoginForm showError={this.props.showError}/>
                </Route>

                <ProtectedRoute name="admin" path="/admin"
                                render={props => <Admin showError={props.showError} {...props} />}
                />
                <ProtectedRoute path="/account/change-password"
                                render={props => <ChangePassword showError={props.showError} {...props}/>}/>


                {/*<Route path="/home">*/}
                {/*    <Home showError={props.showError}/>*/}
                {/*</Route>*/}
                {/*<ProtectedRoute name="baskets" exact path="/baskets" render={props => <QuizApp {...props} />}*/}
                {/*                clientId={clientId} authenticated={authenticated} />*/}

            </Switch>
        )
    }
}

export default (RoutesComponent);