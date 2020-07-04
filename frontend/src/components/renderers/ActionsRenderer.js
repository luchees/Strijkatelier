import React, {useEffect, useState} from 'react';
import {useComponentWillMount} from '../utils';

export default (props) => {
    const url = "http://localhost:8081/api/items/"
    //const url2 = "http://localhost:8081/api/item/"
    let [editing, setEditing] = useState(false);
    let [disabled, setDisabled] = useState(false);

    // custom hook
    useComponentWillMount(() => {
        let editingCells = props.api.getEditingCells();
        if (editingCells.length !== 0) {
            setDisabled(true);
        }
    })

    useEffect(() => {
        props.api.addEventListener('rowEditingStarted', onRowEditingStarted);
        props.api.addEventListener('rowEditingStopped', onRowEditingStopped);

        return () => {
            props.api.removeEventListener('rowEditingStarted', onRowEditingStarted);
            props.api.removeEventListener('rowEditingStopped', onRowEditingStopped);
        };
    }, []);

    function onRowEditingStarted(params) {
        if (props.node === params.node) {
            setEditing(true);
        } else {
            setDisabled(true);
        }
    };

    function onRowEditingStopped(params) {
        if (props.node === params.node) {
            if (isEmptyRow(params.data)) {
                deleteRow(true);
            } else {
                setEditing(false);
            }
        } else {
            setDisabled(false);
        }
    }

    function startEditing() {
        props.api.startEditingCell({
            rowIndex: props.rowIndex,
            colKey: props.column.colId
        });
    }

    function stopEditing(bool) {
        console.log(bool);
        let data = props.data;
        fetch(url + data.id, {
                method: "PUT",
                headers: {
                    "Content-Type": "application/json"
                },
                body: JSON.stringify(data)
            }
        ).then((response) => {
                console.log("updated: " + response.status)
            console.log(data);
                props.api.updateRowData({ update: [data]});
                props.api.refreshCells({force: true});
            }
        )
        props.api.stopEditing(bool);
    }

    function deleteRow(force = false) {
        let data = props.data;
        console.log(url + "" + props.data.id)
        let confirm = true;
        if (!force) {
            confirm = window.confirm(`are you sure you want to delete this row: ${JSON.stringify(data)})`)
        }
        if (confirm) {
            fetch(url + props.data.id, {
                    method: "delete"
                }
            ).then((response) => {
                    console.log("deleted: " + response.status)
                    props.api.updateRowData({remove: [data]});
                    props.api.refreshCells({force: true});
                }
            )

        }
    };

    function isEmptyRow(data) {
        let dataCopy = {...data};
        delete dataCopy.id;
        return !Object.values(dataCopy).some(value => value);
    }

    return (
        <div>
            {editing
                ? <>
                    <button
                        color="primary"
                        variant="contained"
                        onClick={() => stopEditing(false)}
                        disabled={disabled}>Update
                    </button>
                    <button
                        color="secondary"
                        variant="contained"
                        onClick={() => stopEditing(true)}
                        disabled={disabled}>Cancel
                    </button>
                </>
                : <>
                    <button
                        color="primary"
                        variant="outlined"
                        onClick={startEditing}
                        disabled={disabled}>Edit
                    </button>
                    <button
                        color="secondary"
                        variant="outlined"
                        onClick={() => deleteRow()}
                        disabled={disabled}>Delete
                    </button>
                </>
            }
        </div>
    )
}
