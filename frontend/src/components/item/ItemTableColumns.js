export const columnDefs = [
    {field: "id", headerName: "Id", sortable: true, filter: true},
    {field: "itemName", headerName: "Name", sortable: true, filter: true},
    {field: "price", headerName: "Price"},
    {field: "minutes", headerName: "Minutes"},
    {
        headerName: "Date (Datepicker)",
        field: "date",
        cellEditor: "dateEditor",
        filter: "agDateColumnFilter",
        filterParams: {
            clearButton: true,
            suppressAndOrCondition: true,
            comparator: function(filterLocalDateAtMidnight, cellValue) {
                let dateAsString = cellValue;
                let dateParts = dateAsString.split("/");
                let cellDate = new Date(
                    Number(dateParts[2]),
                    Number(dateParts[1]) - 1,
                    Number(dateParts[0])
                );
                if (filterLocalDateAtMidnight.getTime() === cellDate.getTime()) {
                    return 0;
                }
                if (cellDate < filterLocalDateAtMidnight) {
                    return -1;
                }
                if (cellDate > filterLocalDateAtMidnight) {
                    return 1;
                }
            }
        }
    },
    {
        headerName: "",
        colId: "actions",
        cellRenderer: "actionsRenderer",
        editable: false,
        filter: false,
        minWidth: 220
    }
]
export const defaultColDef = {
    editable: true,
    resizable: true,
    filter: true,
    floatingFilter: true,
    suppressKeyboardEvent: params => params.editing
};