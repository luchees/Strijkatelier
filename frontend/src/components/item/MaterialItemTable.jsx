import React, {Component} from "react";
import MaterialTable from "material-table";
import ItemTableService from "./ItemTableService";


class MaterialItemTable extends Component {

    state = {
        itemTableService: new ItemTableService(),
        loading: false,
        data: [],

        columns: [
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
        console.log(newData);
        console.log(oldData);
        this.state.itemTableService.updateItem(this.props.showError, newData);
        const dataUpdate = [...this.state.data];
        const index = oldData.tableData.id;
        dataUpdate[index] = newData;
        this.setState({data: dataUpdate});

    }

    handleRowDelete(oldData) {
        this.state.itemTableService.deleteItem(this.props.showError, oldData);
        const dataDelete = [...this.state.data];
        const index = oldData.tableData.id;
        dataDelete.splice(index, 1);
        this.setState({data: dataDelete});

    }


    handelRowAdd(newData, resolve) {
        this.state.itemTableService.addItem(this.props.showError, newData, (addItem) => {
            let dataToAdd = [...this.state.data];
            dataToAdd.push(addItem);
            this.setState({data: dataToAdd});
            resolve();
        });

    }


    componentDidMount() {
        this.state.itemTableService.getItems(this.props.showError, this.updateState);
    }

    updateState = (rowsItems) => {
        this.setState({data: rowsItems});
    }

    render() {
        return (
            <MaterialTable
                title="Item"
                columns={this.state.columns}
                data={this.state.data}
                //actions={this.state.actions}
                //options={this.state.options}
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

export default MaterialItemTable;
