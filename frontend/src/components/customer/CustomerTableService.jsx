import React from 'react';
import axios from 'axios';
import {API_BASE_URL} from '../../constants/apiContants';

export default class CustomerTableService {

    url = API_BASE_URL + 'customers/';
    getCustomers(showError, callback) {
        axios.get(this.url)
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

    updateCustomer(showError, newData, callback) {
        axios.put(this.url + newData.id,
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


    deleteCustomer(showError, data, callback) {
        axios.delete(this.url + data.id)
            .then(function (response) {
                if (response.status === 200) {
                    console.log("deleted:" + response.data);
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

    addCustomer(showError, newData, callback) {
        axios.post(this.url,
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