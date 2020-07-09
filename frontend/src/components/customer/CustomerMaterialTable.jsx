import React, {Component} from "react";
import MaterialTable from "material-table";
import CustomerTableService from "./CustomerTableService";

const  pattern = new RegExp(/^(("[\w-\s]+")|([\w-]+(?:\.[\w-]+)*)|("[\w-\s]+")([\w-]+(?:\.[\w-]+)*))(@((?:[\w-]+\.)*\w[\w-]{0,66})\.([a-z]{2,6}(?:\.[a-z]{2})?)$)|(@\[?((25[0-5]\.|2[0-4][0-9]\.|1[0-9]{2}\.|[0-9]{1,2}\.))((25[0-5]|2[0-4][0-9]|1[0-9]{2}|[0-9]{1,2})\.){2}(25[0-5]|2[0-4][0-9]|1[0-9]{2}|[0-9]{1,2})\]?$)/i);
class CustomerMaterialTable extends Component {

    state = {
        customerTableService: new CustomerTableService(),
        loading: false,
        data: [],

        columns: [
            {title: 'id', field: 'id', editable: 'never'},
            {title: 'email address', field: 'emailAddress'},
            {title: 'first name', field: 'firstName'},
            {title: 'last name', field: 'lastName'},
            {title: 'account number', field: 'accountNumber'},
            {title: 'minutes left', field: 'minutesLeft', type: 'numeric'},
            {title: 'phone number', field: 'phoneNumber'},
            {title: 'note', field: 'note'},
        ],
        basketColumns: [
            {title: 'id', field: 'id', editable: 'never'},
            {title: 'active', field: 'active'},
            {title: 'cash', field: 'cash'},
            {title: 'start date', field: 'startDateTime'},
            {title: 'done date', field: 'doneDateTime'},
            {title: 'price', field: 'price', type: 'numeric'}
        ],
        actions:
            [
                {
                    icon: 'refresh',
                    tooltip: 'Refresh',
                    isFreeAction: true,
                    onClick: () => this.tableRef.current && this.tableRef.current.onQueryChange(),
                }
            ]
        ,
        options: {
            headerStyle: {
                backgroundColor: '#3f51b5',
                color: '#FFFF'
            }

        }
    }

    handleRowUpdate(newData, oldData) {
        //validation
        let isValid = true;
        if (!pattern.test(newData.emailAddress)) {
            isValid = false;
            this.props.showError("Please enter valid email address.");
        }
        if (isValid) {
            this.state.customerTableService.updateCustomer(this.props.showError, newData);
            const dataUpdate = [...this.state.data];
            const index = oldData.tableData.id;
            dataUpdate[index] = newData;
            this.setState({data: dataUpdate});
        }
    }

    handleRowDelete(oldData) {
        this.state.customerTableService.deleteCustomer(this.props.showError, oldData);
        const dataDelete = [...this.state.data];
        const index = oldData.tableData.id;
        dataDelete.splice(index, 1);
        this.setState({data: dataDelete});

    }


    handelRowAdd(newData, resolve) {
        //validation
        let isValid = true;
        if (!pattern.test(newData.emailAddress)) {
            isValid = false;
            this.props.showError("Please enter valid email address.");
        }
        if (isValid) {
            this.state.customerTableService.addCustomer(this.props.showError, newData, (addItem) => {
                console.log(addItem);
                let dataToAdd = [...this.state.data];
                dataToAdd.push(addItem);
                this.setState({data: dataToAdd});
            });
        }

    }


    componentDidMount() {
        this.state.customerTableService.getCustomers(this.props.showError, this.updateState);
    }

    updateState = (rowsItems) => {
        this.setState({data: rowsItems});
        // this.setState({columns :);
    }

    render() {
        return (
            <MaterialTable
                title="Customer"
                columns={this.state.columns}
                data={this.state.data}
                //actions={this.state.actions}
                //options={this.state.options}
                detailPanel={customer => {
                    return (<MaterialTable
                        icons={{ Filter: () => <div /> }} // <== this solves it
                        //title="Non Filtering Field Preview"
                        columns={this.state.basketColumns}
                        data={customer.basketDtos}
                        options={{
                            filtering: false
                        }}

                    />);}}
                onRowClick={(event, rowData, togglePanel) => togglePanel()}
                editable={{
                    onRowAdd: newData =>
                        new Promise((resolve, reject) => {
                            this.handelRowAdd(newData)
                            resolve();
                        }),
                    onRowUpdate: (newData, oldData) =>
                        new Promise((resolve, reject) => {
                            console.log(newData)
                            this.handleRowUpdate(newData, oldData);
                            resolve();
                        }),
                    onRowDelete: oldData =>
                        new Promise((resolve, reject) => {
                            this.handleRowDelete(oldData)
                            resolve();
                        }),
                }}
            />
        )
    }


}

export default CustomerMaterialTable;
