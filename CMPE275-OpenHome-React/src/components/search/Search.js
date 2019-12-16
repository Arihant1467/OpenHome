import React, { Component } from 'react';
import cookie from 'react-cookies';
import { Redirect } from 'react-router';
import SearchForm from './search-form/SearchForm';
import SearchCard from './search-card/SearchCard';
import HomeAwayPlainNavBar from './../HomeAwayPlainNavBar/HomeAwayPlainNavBar.js';
import axios from 'axios';
import { connect } from 'react-redux';
import { SEARCH_FIELDS, SEARCH_RESULTS, UPDATE_STEPPER } from './../actions/types.js';
import serialize from 'form-serialize';
import { BASE_URL } from './../constants.js';
import { getWeekRepresentation } from './../../utils/DayUtils';
import { CITY_IS_NULL, START_DATE_EMPTY, END_DATE_EMPTY, ACCOMODATE_EMPTY, START_DATE_GREATER_THAN_END_DATE, START_DATE_EQUAL_END_DATE, ACCOMODATE_SHOULD_BE_NUMBER } from './../messages.js';
import { FILTER_PRICE, FILTER_BEDROOM } from './../messages.js';
import { stat } from 'fs';
const queryString = require('query-string');


class Search extends Component {

    constructor(props) {
        super(props);

        const { startDate, endDate, cityName, zipcode } = queryString.parse(this.props.location.search);
        this.state = {

            startDate: new Date(startDate),
            endDate: new Date(endDate),
            cityName,
            zipcode: zipcode==="null"? null:parseInt(zipcode,10),
            fromPrice: null,
            toPrice: null,
            sharingType: null,
            propertyType: null,
            wifi: null,
            description: null,

            propertySelected: false,
            selectedPropertyId: null,

            /* For pagination */
            currentPage:1,
            minPages:1,
            maxPages:1,
            noOfRecordsPerPage:10,

            results: []

        }

        this.showPropertyHandler = this.showPropertyHandler.bind(this);
        this.filterFormSubmitHandler = this.filterFormSubmitHandler.bind(this);
        this.stepperHandler = this.stepperHandler.bind(this);
    }



    componentDidMount() {

        const { startDate,
            endDate,
            cityName,
            zipcode,
            fromPrice,
            toPrice,
            sharingType,
            propertyType,
            wifi,
            description,
            noOfRecordsPerPage
         } = this.state;
        
        const dayAvailibility = getWeekRepresentation(startDate,endDate);
        const body = {
            startDate: startDate.getTime(),
            endDate: endDate.getTime(),
            cityName,
            zipcode,
            fromPrice,
            toPrice,
            sharingType,
            propertyType,
            wifi,
            description,
            dayAvailibility
        }


        axios.put(`${BASE_URL}/posting/search`, body).then((response) => {
            if (response.status == 200) {
                const results = response.data;
                const minPages=1;
                const maxPages=parseInt(results.length/noOfRecordsPerPage,10)+1;
                console.log(results);
                this.setState({
                    results,
                    minPages,
                    maxPages,
                    currentPage:1
                })
            }
        })

    }

    


    stepperHandler = (e) => {
        
        const stepper_value = parseInt(e.target.value, 10);
        let { currentPage, maxPages, minPages } = this.state;
        if (stepper_value == 1) {
            currentPage = currentPage + 1;
            currentPage = currentPage > maxPages ? maxPages : currentPage
        }
        else if (stepper_value == -1) {
            currentPage = currentPage - 1;
            currentPage = currentPage < minPages ? minPages : currentPage
        }

        this.setState({ currentPage });
    }

    filterFormSubmitHandler = (e) => {
        e.preventDefault();
        const form = serialize(e.target, { hash: true });

        const msg = this.formValidation(form);
        if (msg) {
            alert(msg);
            return;
        }

        const { startDate,
            endDate,
            cityName,
            zipcode,
            fromPrice,
            toPrice,
            sharingType,
            propertyType,
            wifi,
            description } = form;
        
        const dayAvailibility = getWeekRepresentation(startDate,endDate);  
        
        const body = {
            startDate: new Date(startDate),
            endDate: new Date(endDate),
            dayAvailibility,
            cityName: cityName == null ? this.state.cityName : cityName,
            zipcode: zipcode == null ? this.state.zipcode : parseInt(zipcode, 10),
            fromPrice: fromPrice == null ? this.state.fromPrice : parseInt(fromPrice, 10),
            toPrice: toPrice == null ? this.state.toPrice : parseInt(toPrice, 10),
            sharingType: sharingType==="none" ? null:sharingType,
            propertyType: propertyType==="none" ? null:propertyType,
            wifi: wifi==="none"? null : wifi,
            description: description == null ? this.state.description : description
        };
        console.log(`Body sent to filter search ${JSON.stringify(body)}`);

        axios.put(`${BASE_URL}/posting/search`, body).then((response) => {
            let results = []
            console.log(response);
            if (response.status == 200) {
                results = response.data;
            }else{
                alert("We could not fetch results from server");
            }
            const minPages = 1;
            const maxPages=parseInt(results.length/this.state.noOfRecordsPerPage,10)+1;
            this.setState({
                results: results,
                startDate: body.startDate,
                endDate: body.endDate,
                cityName: body.cityName,
                zipcode: body.zipcode,
                fromPrice: body.fromPrice,
                toPrice: body.toPrice,
                sharingType: body.sharingType,
                propertyType: body.propertyType,
                wifi: body.wifi,
                description: body.description,
                minPages,maxPages,
                currentPage:1
            })
        });
    }

    formValidation = (form) => {
        if (!form.cityName) { return "City cannot be empty" }
        if (!form.startDate) { return "Start date cannot be empty"; }
        if (!form.endDate) { return "End Date cannot be empty"; }
        if (form.startDate > form.endDate) { return "Start data has to be less than end date"; }
        if (!form.cityName && !form.zipcode) { return "City and zipcode both cannot be empty "; }
        // if (!form.toPrice || !form.fromPrice){ return "Enter a range";}
        // if (form.toPrice<form.fromPrice){ return "Enter a range";}
        return null;
    }


    showPropertyHandler(propertyId) {
        if (propertyId == null || typeof (propertyId) == "undefined") { return; }
        this.setState({
            selectedPropertyId: propertyId,
            propertySelected: true
        });
    }


    render() {
        let redirectVar = null;
        const { cityName, startDate, endDate, zipcode, propertySelected,selectedPropertyId } = this.state;
        const { results } = this.state;
        const visibleBlock = (results.length == 0);
        if (propertySelected) {
            const redirecturl = `/overview/${selectedPropertyId}?startDate=${startDate.getTime()}&endDate=${endDate.getTime()}`;
            redirectVar = <Redirect to={redirecturl} />
        }


        const { currentPage, minPages, maxPages, noOfRecordsPerPage } = this.state;
        const disbaledPrev = (currentPage <= minPages);
        const disableNext = (currentPage >= maxPages);
        let resultsSlice = [];
        if (results.length < noOfRecordsPerPage) {
            resultsSlice = results;
        } else {

            if ((currentPage) * noOfRecordsPerPage > results.length) {
                resultsSlice = results.slice((currentPage - 1) * noOfRecordsPerPage, results.length);
            } else {
                resultsSlice = results.slice((currentPage - 1) * noOfRecordsPerPage, currentPage * noOfRecordsPerPage);
            }
        }

        return (
            <div>
                {redirectVar}

                <div><HomeAwayPlainNavBar /></div>

                {/* <div><SearchForm onSave={this.criteriaHandler} fields={searchCriteria} /></div> */}

                <form onSubmit={this.filterFormSubmitHandler}>

                    <div className="row form-group" style={{ border: '0.5px solid #0069D9', padding: '5px' }}>

                        <div className="col-md-1"></div>

                        <div className="col-md-2">
                            <div className="inner-child add-border-search-form" >
                                <label className="pl-1 pl-2" style={{ fontSize: '13px', bottom: '0px', color: '#A4AC9D', }}>Where</label>
                                <input className="ml-1 pl-2 mt-0 remove-bg-input" type="text" name="cityName" defaultValue={cityName ? cityName : ""} style={{ width: '90%', fontSize: '18px', lineHeight: '1.0rem', color: 'black' }} placeholder="Where to?" />
                            </div>
                        </div>

                        <div className="col-md-2">
                            <div className="add-border-search-form" style={{ width: '100%', height: '100%' }}>
                                <div className="mt-2 mb-2 pt-1">
                                    <img className="ml-1 mt-2" width="20px" height="20px" src="https://png.icons8.com/ios/64/cccccc/calendar-filled.png" style={{ verticalAlign: 'middle' }} />
                                    <input className="ml-1 mt-2 remove-bg-input" type="date" name="startDate" defaultValue={startDate.toISOString().split('T')[0]}  style={{ width: '75%', fontSize: '14px', lineHeight: '1.5rem', verticalAlign: 'middle', color: 'black' }} />
                                </div>

                            </div>
                        </div>

                        <div className="col-md-2">
                            <div className="add-border-search-form" style={{ width: '100%', height: '100%' }}>
                                <div className="mt-2 mb-2 pt-1">
                                    <img className="ml-1 mt-2" width="20px" height="20px" src="https://png.icons8.com/ios/64/cccccc/calendar-filled.png" style={{ verticalAlign: 'middle' }} />
                                    <input className="ml-1 mt-2 remove-bg-input" type="date" name="endDate" defaultValue={endDate.toISOString().split('T')[0]}  style={{ width: '75%', fontSize: '14px', lineHeight: '1.5rem', verticalAlign: 'middle', color: 'black' }} />
                                </div>

                            </div>
                        </div>


                    </div>


                    <div className="row form-group" style={{ border: '0.5px solid #0069D9', padding: '5px' }}>
                        <div className="col-sm-1"></div>
                        <div className="col-md-1">
                            <label htmlFor="fromPrice">From</label>
                            <input type="number" name="fromPrice" placeholder="20" style={{ border: '0.3px solid grey' }} />
                        </div>

                        <div className="col-md-1">
                            <label htmlFor="toPrice">To Price</label>
                            <input type="number" name="toPrice" placeholder="100" style={{ border: '0.3px solid grey' }} />
                        </div>

                        <div className="col-md-2">
                            <label htmlFor="sharingType">Sharing Type</label>
                            <select name="sharingType" className="no-bg" style={{ border: '0.3px solid grey' }} onChange={this.selectedSharingType} value={this.state.selectedSharingType} >
                                <option selected value="none"></option>
                                <option value="PLACE">PLACE</option>
                                <option value="PRIVATE_ROOM">PRIVATE_ROOM</option>
                            </select>
                        </div>

                        <div className="col-md-2">
                            <label htmlFor="propertyType">Property Type</label>
                            <select name="propertyType" className="no-bg" style={{ border: '0.3px solid grey' }} >
                                <option selected value="none"></option>
                                <option value="HOUSE">HOUSE</option>
                                <option value="TOWN_HOUSE">TOWN_HOUSE</option>
                                <option value="APARTMENT">APARTMENT</option>
                                <option value="OTHER">OTHER</option>
                            </select>
                        </div>

                        <div className="col-md-1">
                            <label htmlFor="wifi">WIFI Type</label>
                            <select name="wifi" className="no-bg" style={{ border: '0.3px solid grey' }} >
                                <option selected value="none"></option>
                                <option value="FREE_WIFI">FREE_WIFI</option>
                                <option value="PAID_WIFI">PAID_WIFI</option>
                                <option value="NO_WIFI">NO_WIFI</option>
                            </select>
                        </div>



                        <div className="col-md-1">
                            <label htmlFor="description">Description</label>
                            <input type="text" name="description" placeholder="small" style={{ border: '0.3px solid grey' }} />
                        </div>


                        <div className="col-md-1" style={{ marginTop: 'auto', marginBottom: 'auto' }}>
                            <button type="submit" className="btn btn-primary btn-lg">Search</button>
                        </div>
                    </div>
                </form>

                <div style={{ textAlign: 'center', display: visibleBlock ? 'block' : 'none' }}>
                    <h1 style={{ color: 'black' }}>
                        No results to display.Try another query
                    </h1>
                </div>
                <div style={{ marginBottom: '5rem', display: visibleBlock ? 'none' : 'block' }}>
                    {
                        resultsSlice.map((search, index) => {
                            return (<SearchCard data={search} key={index} onSave={this.showPropertyHandler} />);
                        })
                    }
                </div>

                <div className="row w-100" style={{ bottom: 0, height: '4rem', position: 'fixed', border:'2px solid grey' }} >
                    <div className="col-md-1" ></div>
                    <div className="col-md-2">
                        <button className="btn btn-primary btn-lg" onClick={this.stepperHandler} disabled={disbaledPrev} value="-1" style={{ marginTop: '10px' }}>Previous</button>
                    </div>

                    <div className="col-md-2"></div>
                    <div className="col-md-2">
                        {currentPage}/{maxPages}
                    </div>
                    <div className="col-md-2"></div>

                    <div className="col-md-2">
                        <button className="btn btn-primary btn-lg" onClick={this.stepperHandler} disabled={disableNext} value="1" style={{ marginTop: '10px' }}>Next</button>
                    </div>

                    <div className="col-md-1"></div>
                </div>

            </div>
        );
    }
}

const mapStateToProps = (state) => {

    return {
        user: state.user,
        searchCriteria: state.searchFieldsHome,
        
    }
}

const mapDispatchToProps = (dispatch) => {
    return {
        fetchResults: async (criteria) => {

            const response = await axios.post(BASE_URL + "/search", criteria)
            if (response.status === 200) {
                const searchresults = response.data.search;
                dispatch({
                    type: SEARCH_RESULTS,
                    payload: searchresults
                });
            } else {

            }

        },
        updateSearchFields: (searchFields) => {
            dispatch({
                type: SEARCH_FIELDS,
                payload: searchFields
            });
        }

    }
}

export default connect(mapStateToProps, mapDispatchToProps)(Search)
