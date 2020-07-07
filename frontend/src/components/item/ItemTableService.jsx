import React, {Component} from 'react';
import axios from 'axios';
import {API_BASE_URL} from '../../constants/apiContants';

export default class ItemTableService {


    getItems(showError, callback){
        axios.get(API_BASE_URL + 'items')
            .then(function (response) {
                if (response.status === 200) {
                    console.log(response.data);
                    callback(response.data);
                }

            })
            .catch(function (error) {
                if (error.response) {
                    if (error.response.status === 404) {
                        showError(error.response.statusText);
                    }
                }
            })
    }
    updateItem(showError,newData, callback){
        axios.put(API_BASE_URL + 'items/'+newData.id,
            newData)
            .then(function (response) {
                if (response.status === 200) {
                    console.log(response.data);
                    callback(response.data);
                }

            })
            .catch(function (error) {
                if (error.response) {
                    if (error.response.status === 404) {
                        showError(error.response.statusText);
                    }
                }
            })
    }


    deleteItem(showError,data, callback) {
        axios.delete(API_BASE_URL + 'items/'+data.id)
            .then(function (response) {
                if (response.status === 200) {
                    console.log( "deleted:" + response.data);
                    callback(response.data);
                }

            })
            .catch(function (error) {
                if (error.response) {
                    if (error.response.status === 404) {
                        showError(error.response.statusText);
                    }
                }
            })
    }
    addItem(showError,newData, callback){
        axios.post(API_BASE_URL + 'items/',
            newData)
            .then(function (response) {
                if (response.status === 200) {
                    console.log(response.data);
                    callback(response.data);
                }

            })
            .catch(function (error) {
                if (error.response) {
                    if (error.response.status === 404) {
                        showError(error.response.statusText);
                    }
                    if (error.response.status === 400) {
                        showError(error.response.statusText);
                    }
                }
            })
    }
}