import React from 'react';
import MaterialItemTable from "./item/MaterialItemTable";
import CustomerMaterialTable from "./customer/CustomerMaterialTable";
import BasketMaterialTable from "./basket/BasketMaterialTable";


function Home(props) {
    return (
        <div>
            Home page content
            <MaterialItemTable showError={props.showError}/>
            <CustomerMaterialTable showError={props.showError}/>
            <BasketMaterialTable showError={props.showError}/>
        </div>

    )
}

export default Home;