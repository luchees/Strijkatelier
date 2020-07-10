import React, {useEffect, useState} from "react";
import TextField from "@material-ui/core/TextField";
import makeStyles from "@material-ui/core/styles/makeStyles";
import CustomerTableService from "../customer/CustomerTableService";
import Autocomplete from '@material-ui/lab/Autocomplete';
import Button from "@material-ui/core/Button";
import AddIcon from '@material-ui/icons/Add';
import IconButton from "@material-ui/core/IconButton";
import SaveIcon from '@material-ui/icons/Save';
import ItemTableService from "../item/ItemTableService";


const useStyles = makeStyles((theme) => ({
    root: {
        '& > *': {
            margin: theme.spacing(1),
            width: '25ch',
        },
    },
}));

export default function CreateBasketComponent(props) {
    const classes = useStyles();
    const customerTableService = new CustomerTableService();
    const itemTableService = new ItemTableService();

    const [items,setItems] = useState([{ }])

    const [customers, setCustomers] = useState([])
    // const handleChange = (e) => {
    //     const {id, value} = e.target
    //     setState(prevState => ({
    //         ...prevState,
    //         [id]: value
    //     }))
    // }
    useEffect(() => {
        customerTableService.getCustomers(props.showError, setCustomers);
        itemTableService.getItems((props.showError,setItems))
    },[]);


    function addItem() {


    }

    return (

        <form className={classes.root} noValidate autoComplete="off">
            <Autocomplete
                id="customer"
                options={customers}
                getOptionLabel={(option) => option.emailAddress}
                style={{width: 300}}
                renderInput={(params) => <TextField {...params} label="Customer" variant="outlined"/>}
            />

            <Button
                variant="contained"
                color="primary"
                size="large"
                className={classes.button}
                startIcon={<AddIcon />}
                onClick={addItem}
            >
                Add Item
            </Button>

            <TextField id="outlined-basic" label="Outlined" variant="outlined"/>
            <TextField id="outlined-basic" label="Outlined" variant="outlined"/>
            <Button
                variant="contained"
                color="primary"
                size="large"
                className={classes.button}
                startIcon={<SaveIcon />}
            >
                Save
            </Button>
        </form>
    );
}

