import React, { useState } from "react";
import { AgGridReact } from "ag-grid-react";
import "ag-grid-enterprise";
import ItemTableService from "./ItemTableService";
import 'ag-grid-community/dist/styles/ag-grid.css';
import 'ag-grid-community/dist/styles/ag-theme-alpine.css';
//import {withRouter} from "react-router-dom";
import { columnDefs, defaultColDef } from "./ItemTableColumns";

import { uuid } from "uuidv4";
import "./ItemTable.css";
import SimpleEditor from "../editors/SimpleEditor";
import DatePicker from "../DatePicker";
import DateEditor from "../editors/DateEditor";
import ActionsRenderer from "../renderers/ActionsRenderer";
import AddRowStatusBar from "../status-bar/AddRowStatusBar";

const style = () => ({
    font: "sans-serif"
});


class ItemTableComponent extends React.Component {

    rowData = useState(null);
    setRowData = useState(null);
    gridApi = useState(null);
    setGridApi = useState(null);
    setColumnApi = useState(null);
    columnApi = useState(null);



    state = {
    frameworkComponents : {
        simpleEditor: SimpleEditor,
        //  asyncValidationEditor: AsyncValidationEditor,
        // autoCompleteEditor: AutoCompleteEditor,
        agDateInput: DatePicker,
        dateEditor: DateEditor,
        actionsRenderer: ActionsRenderer,
        addRowStatusBar: AddRowStatusBar
    }
};

    constructor(props) {
        super(props)
        this.state = {
            rows: [],

        };
        this.itemTableService = new ItemTableService()
    }


    updateState = (rowsItems) =>  this.setRowData(rowsItems);

    onGridReady(params) {
        this.setGridApi(params.api);
        this.setColumnApi(params.columnApi);
        this.itemTableService.getItems(this.props,this.updateState);

        params.api.sizeColumnsToFit();
    }
    componentDidMount() {

    }


    render() {
        return (
            <div className="ag-theme-alpine" style={{height: '300px', width: '50%'}}>
                <AgGridReact
                    columnDefs={columnDefs}
                    defaultColDef={defaultColDef}
                    rowData={this.state.rowData}
                    getRowNodeId={data => data.id}
                    onGridReady={this.onGridReady}
                    frameworkComponents={this.frameworkComponents}
                    editType="fullRow"
                    suppressClickEdit
                    statusBar={{
                        statusPanels: [{ statusPanel: "addRowStatusBar" }]
                    }}
                />
            </div>
        );

    }
}

//export default withRouter(withStyles(style)(ItemTableComponent))
export default (ItemTableComponent)