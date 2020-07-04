import React, { useState } from "react";
import { AgGridReact } from "ag-grid-react";
import "ag-grid-enterprise";
import "ag-grid-community/dist/styles/ag-grid.css";
import "ag-grid-community/dist/styles/ag-theme-alpine.css";

import { columnDefs, defaultColDef } from "./item/ItemTableColumns";

import { uuid } from "uuidv4";
import "../App.css";
import SimpleEditor from "./editors/SimpleEditor";
import DatePicker from "./DatePicker";
import DateEditor from "./editors/DateEditor";
import ActionsRenderer from "./renderers/ActionsRenderer";
import AddRowStatusBar from "./status-bar/AddRowStatusBar";
import ItemTableService from "./item/ItemTableService";

function Items(props) {
    const url="http://localhost:8081/api/items/"
    const [gridApi, setGridApi] = useState(null);
    const [columnApi, setColumnApi] = useState(null);
    const itemTableService = new ItemTableService();
    const [rowData, setRowData] = useState(null);

    const frameworkComponents = {
        simpleEditor: SimpleEditor,
       // asyncValidationEditor: AsyncValidationEditor,
       // autoCompleteEditor: AutoCompleteEditor,
        agDateInput: DatePicker,
        dateEditor: DateEditor,
        actionsRenderer: ActionsRenderer,
        addRowStatusBar: AddRowStatusBar
    };

    function onGridReady(params) {
        setGridApi(params.api);
        setColumnApi(params.columnApi);
        fetch(
            url        )
            .then(res => res.json())
            .then(data => {
                setRowData(data.slice(0, 100));
            });
        params.api.sizeColumnsToFit();
    }
    //     setRowData(itemTableService.getItems(props,(rowsItems) =>  this.setRowData(rowsItems)));
    //
    //     params.api.sizeColumnsToFit();
    // }

    return (
        <div className="my-app">
            <div
                id="myGrid"
                style={{ height: "100%", width: "100%" }}
                className="ag-theme-alpine"
            >
                <AgGridReact
                    columnDefs={columnDefs}
                    defaultColDef={defaultColDef}
                    rowData={rowData}
                    getRowNodeId={data => data.id}
                    onGridReady={onGridReady}
                    frameworkComponents={frameworkComponents}
                    actionsRenderer={ActionsRenderer}
                    editType="fullRow"
                    suppressClickEdit
                    statusBar={{
                        statusPanels: [{ statusPanel: "addRowStatusBar" }]
                    }}
                />
            </div>
        </div>
    );
}

export default Items;
