import React from "react";
import {Route} from "react-router-dom";
import Typography from "@material-ui/core/Typography";
import Toolbar from "@material-ui/core/Toolbar";


class ProtectedRoute extends Route {

    componentDidMount = async () => {
        const {authenticated, clientId} = this.props;
        //this.props.authenticated=true;
        // if(authenticated === false){
        //     await initiateAuthentication(oAuth2Config, clientId);
        // }
    };

    render(){
        const {authenticated} = this.props;
        return  super.render();
        //return (authenticated) ? super.render() : (
        //     <div style={{position: 'absolute', left: '44%', top: '50%'}}>
        //         <Typography variant="h6" >
        //             not authenticated
        //         </Typography>
        //     </div>
        // )
    }

}
export default ProtectedRoute