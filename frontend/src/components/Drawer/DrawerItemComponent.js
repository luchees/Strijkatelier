import React from "react";
import ListItem from "@material-ui/core/ListItem";
import Link from "@material-ui/core/Link";
import ListItemText from "@material-ui/core/ListItemText";
import {withRouter} from "react-router-dom";
import ContactsIcon from "@material-ui/icons/Contacts";
import ListItemIcon from "@material-ui/core/ListItemIcon";

class DrawerItemComponent extends React.Component {

    render() {
        const { name, link, clicked,image } = this.props;
        return (
            <ListItem tabIndex={0} button onClick={() => this.navigate(link, clicked)}>
                <Link to={link}>
                    <ListItemIcon>{image} </ListItemIcon>
                    <ListItemText primary={name}/>

                </Link>
            </ListItem>
        )
    }

    navigate = (link, clicked) => {
       // clicked();
        this.props.history.push(link)
    };
}
export default withRouter(DrawerItemComponent)