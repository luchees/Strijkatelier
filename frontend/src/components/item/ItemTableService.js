import React from 'react';
import axios from 'axios';
import {API_BASE_URL} from '../../constants/apiContants';

export default class ItemTableService{

    getItems(props, callback){
        axios.get(API_BASE_URL + 'items')
            .then(function (response) {
                if (response.status === 200) {
                    console.log(response.data);
                    callback(response.data);
                    //callback(response.data);
                }

            })
            .catch(function (error) {
                if (error.response) {
                    if (error.response.status === 404) {
                        props.showError(error.response.toString());
                    }
                }
            })
    }
}