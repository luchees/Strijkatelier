import React, {useEffect, useState} from "react";
import MaterialTable from "material-table";
import ItemTableService from "../item/ItemTableService";
import CustomerTableService from "../customer/CustomerTableService";
import Autocomplete from "@material-ui/lab/Autocomplete";
import TextField from "@material-ui/core/TextField";


export default function AddBasketComponent(props) {

    const itemTableService = new ItemTableService();
    const customerTableService = new CustomerTableService();
    const [items, setItems] = useState([])
    const [basketItems, setBasketItems]= useState([])
    const [customers, setCustomers] = useState([])
    //const [itemFilterList, setItemFilterList] = useState([])
    const [columns, setColumns] = useState([    ]);

    function filterItems(dropDownItems){
        const itemFilterList = [];
        console.log(dropDownItems)
        setItems(dropDownItems);
        dropDownItems.forEach((item) => itemFilterList[item.itemName] = item.itemName)
        setColumns([
            {title: 'id', field: 'id', editable: 'never'},
            {title: 'name', field: 'itemName' , lookup: itemFilterList },
            {title: 'amount', field: 'amount'},
        ]);
    }


    const [options, setOptions] = useState({
        headerStyle: {
            backgroundColor: '#3f51b5',
            color: '#FFFF'
        }

    });


    function handleRowUpdate(newData, oldData) {
        //validation
        console.log(newData);
        console.log(oldData);
        itemTableService.updateItem(props.showError, newData);
        const dataUpdate = [...basketItems];
        const index = oldData.tableData.id;
        dataUpdate[index] = newData;

        setItems(dataUpdate);

    }

    function handleRowDelete(oldData) {
        itemTableService.deleteItem(props.showError, oldData);
        const dataDelete = [...basketItems];
        const index = oldData.tableData.id;
        dataDelete.splice(index, 1);
        setItems(dataDelete);

    }


    function handelRowAdd(newData, resolve) {
        itemTableService.addItem(props.showError, newData, (addItem) => {
            let dataToAdd = [...basketItems];
            dataToAdd.push(addItem);
            setItems(dataToAdd);
            resolve();
        });

    }

    useEffect(() => {
        customerTableService.getCustomers(props.showError, setCustomers);
        itemTableService.getItems(props.showError, filterItems);

    }, []);


    return (

        <div><Autocomplete
            id="customer"
            options={customers}
            getOptionLabel={(option) => option.emailAddress}
            style={{width: 300}}
            renderInput={(params) => <TextField {...params} label="Customer" variant="outlined"/>}
        />

            <MaterialTable
                title="Item"
                columns={columns}
                data={basketItems}
                //actions={this.state.actions}
                //options={this.state.options}
                editable={{
                    onRowAdd: newData =>
                        new Promise((resolve, reject) => {
                            handelRowAdd(newData)
                            resolve();
                        }),
                    onRowUpdate: (newData, oldData) =>
                        new Promise((resolve, reject) => {
                            handleRowUpdate(newData, oldData);
                            resolve();
                        }),


                    onRowDelete: oldData =>
                        new Promise((resolve, reject) => {
                            handleRowDelete(oldData)
                            resolve();
                        }),
                }}
            />
        </div>
    )
}


