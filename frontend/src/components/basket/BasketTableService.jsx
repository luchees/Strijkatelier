import React from 'react';
import axios from 'axios';
import {API_BASE_URL} from '../../constants/apiContants';

export default class BasketTableService {

    url = API_BASE_URL + 'baskets/';
    getBaskets(showError, callback) {
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

    updateBasket(showError, newData, callback) {
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


    deleteBasket(showError, data, callback) {
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

    addBasket(showError, newData, callback) {
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