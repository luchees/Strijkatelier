import React, {Component} from "react";
import MaterialTable from "material-table";
import BasketTableService from "./BasketTableService";

class BasketMaterialTable extends Component {

    state = {
        basketTableService: new BasketTableService(),
        loading: false,
        data: [],

        columns: [
            {title: 'id', field: 'id', editable: 'never'},
            {title: 'active', field: 'active'},
            {title: 'cash', field: 'cash'},
            {title: 'start date', field: 'startDateTime'},
            {title: 'done date', field: 'doneDateTime'},
            {title: 'price', field: 'price', type: 'numeric'}
        ],
        columnsItem: [
            {title: 'id', field: 'id', editable: 'never'},
            {title: 'name', field: 'itemName'},
            {title: 'minutes', field: 'minutes', type: 'numeric'},
            {
                title: 'price', field: 'price', type: 'numeric',
            },
        ],
        actions:
            [
                {
                    icon: 'refresh',
                    tooltip: 'Refresh',
                    isFreeAction: true,
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
        // if (!pattern.test(newData.emailAddress)) {
        //     isValid = false;
        //     this.props.showError("Please enter valid email address.");
        // }
        if (isValid) {
            this.state.basketTableService.updateBasket(this.props.showError, newData);
            const dataUpdate = [...this.state.data];
            const index = oldData.tableData.id;
            dataUpdate[index] = newData;
            this.setState({data: dataUpdate});
        }
    }

    handleRowDelete(oldData) {
        this.state.basketTableService.deleteBasket(this.props.showError, oldData);
        const dataDelete = [...this.state.data];
        const index = oldData.tableData.id;
        dataDelete.splice(index, 1);
        this.setState({data: dataDelete});

    }


    handelRowAdd(newData, resolve) {
        //validation
        let isValid = true;
        // if (!pattern.test(newData.emailAddress)) {
        //     isValid = false;
        //     this.props.showError("Please enter valid email address.");
        // }
        if (isValid) {
            this.state.basketTableService.addBasket(this.props.showError, newData, (addItem) => {
                console.log(addItem);
                let dataToAdd = [...this.state.data];
                dataToAdd.push(addItem);
                this.setState({data: dataToAdd});
            });
        }

    }


    componentDidMount() {
        this.state.basketTableService.getBaskets(this.props.showError, this.updateState);

    }

    updateState = (rowsItems) => {
        this.setState({data: rowsItems});

    }

    render() {
        return (
            <MaterialTable
                title="Baskets"
                columns={this.state.columns}
                data={this.state.data}
                //actions={this.state.actions}
                //options={this.state.options}
                detailPanel={basket => {
                    return (<MaterialTable
                    icons={{ Filter: () => <div /> }} // <== this solves it
                    //title="Non Filtering Field Preview"
                    columns={this.state.columnsItem}
                    data={basket.itemDtos}
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

export default BasketMaterialTable;
