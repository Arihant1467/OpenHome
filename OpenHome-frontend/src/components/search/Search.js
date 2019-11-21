import React, { Component } from 'react';
import cookie from 'react-cookies';
import {Redirect} from 'react-router';
import SearchForm from './search-form/SearchForm';
import SearchCard from './search-card/SearchCard';
import HomeAwayPlainNavBar from './../HomeAwayPlainNavBar/HomeAwayPlainNavBar.js';
import axios from 'axios';
import { connect } from 'react-redux';
import {SEARCH_FIELDS,SEARCH_RESULTS,UPDATE_STEPPER} from './../actions/types.js';
import serialize from 'form-serialize';
import {BASE_URL} from './../constants.js';
import {CITY_IS_NULL,START_DATE_EMPTY,END_DATE_EMPTY,ACCOMODATE_EMPTY,START_DATE_GREATER_THAN_END_DATE,START_DATE_EQUAL_END_DATE,ACCOMODATE_SHOULD_BE_NUMBER} from './../messages.js';
import {FILTER_PRICE,FILTER_BEDROOM} from './../messages.js';
import { stat } from 'fs';
const queryString = require('query-string');


class Search extends Component {
    
    constructor(props){
        super(props);
        /*
        this.state = {
            city:localStorage.getItem("city"),
            startdate:localStorage.getItem("startdate"),
            enddate:localStorage.getItem("enddate"),
            accomodate:localStorage.getItem("accomodate"),
            results:[],
            propertySelected:false,
            propertyid:null,
        }

        this.criteriaHandler = this.criteriaHandler.bind(this);
        this.showPropertyHandler = this.showPropertyHandler.bind(this);
        */
       this.state ={
           propertySelected : false,
           selectedPropertyId : null,
           stepper : 1,
           maxPriceFilter:null,
           minBedroomFilter:0
       }
       this.showPropertyHandler = this.showPropertyHandler.bind(this);
       this.criteriaHandler = this.criteriaHandler.bind(this);
       this.filterFormSubmitHandler = this.filterFormSubmitHandler.bind(this);
     }
    

    componentDidUpdate(prevProps,prevState){
        //Current 
        const {searchCriteria} = this.props;
        var currentStepper = this.state.stepper;
        var {maxPriceFilter,minBedroomFilter} = this.state;

        //Prev
        const prevSearchCriteria = prevProps.searchCriteria;
        const prevStepper = prevState.stepper;
        const prevMaxPriceFilter=prevState.maxPriceFilter;
        const prevMinBedroomFilter = prevState.minBedroomFilter;
        
        //modifying criterias
        const modifiedSearchCriteria = Object.assign({},searchCriteria,{stepper:currentStepper,bedroom:minBedroomFilter,price:maxPriceFilter});
        const modifiedPrevSearchCriteria = Object.assign({},prevSearchCriteria,{stepper:prevStepper,bedroom:prevMinBedroomFilter,price:prevMaxPriceFilter});
        
        //Comparison
        const areSearchCriteriaDifferent = !(JSON.stringify(modifiedSearchCriteria) == JSON.stringify(modifiedPrevSearchCriteria));
        const areStepperDifferent = !(prevStepper==currentStepper);
        //const areFilterDifferent = !(JSON.stringify({maxPriceFilter,minBedroomFilter}) === JSON.stringify({maxPriceFilter:prevMaxPriceFilter,minBedroomFilter:prevMinBedroomFilter}) );
        
        
        const userid = this.props.user ? this.props.user.userid:null;

        if(areSearchCriteriaDifferent){
            currentStepper =1;
            const newSearchCriteria = Object.assign({},modifiedSearchCriteria,{stepper:currentStepper,userid:userid});
            this.props.fetchResults(newSearchCriteria);
            this.setState({ stepper:currentStepper});
            return;
        }

        if(areStepperDifferent){
            const newSearchCriteria = Object.assign({},modifiedSearchCriteria,{stepper:currentStepper,userid:userid});
            this.props.fetchResults(newSearchCriteria);
            return;
        }
        /*
        
        */
        
    }

    componentDidMount(){
        /*
        
        */
        const {stepper} = this.state;
        var userid = null;
        if(this.props.user){ userid = this.props.user.userid;}
        const data = Object.assign({},this.props.searchCriteria,{stepper,userid:userid});
        
        if(!(JSON.stringify(this.props.searchCriteria) == "{}")){
            this.props.fetchResults(data);    
        }

    }

    criteriaHandler(data){
            /*
            
            */
        var msg = this.searchFieldsFormValidation(data);
        if(msg){
            alert(msg);
            return;
        }
        
        this.props.updateSearchFields(data);
    }

    
    stepperHandler = (e)=>{
        const stepper_value = parseInt(e.target.value);
        var {stepper} = this.state;
        stepper = stepper+stepper_value;
        this.setState({stepper : stepper<=0 ? 1:stepper });
    }
    
    filterFormSubmitHandler = (e)=>{
        e.preventDefault();
        var form = serialize(e.target, { hash: true });
        
        if(!form.price && !form.bedroom){ return; }

        var msg = this.filterFormvalidation(form)
        if(msg){ alert(msg);return}

        var {bedroom,price} = form;

        if(bedroom && price){
            this.setState({
                minBedroomFilter:bedroom,
                maxPriceFilter:price
            });
        }else{
            if(bedroom){
                this.setState({minBedroomFilter:bedroom});
            }
            if(price){
                this.setState({maxPriceFilter:price});
            }
        }        
    }

    searchFieldsFormValidation(form){
      if(!form.city){ return CITY_IS_NULL}
      if(!form.startdate){ return START_DATE_EMPTY;}
      if(!form.enddate){ return END_DATE_EMPTY;}
      if(!form.accomodate){ return ACCOMODATE_EMPTY}
      if(form.startdate==form.enddate){return START_DATE_EQUAL_END_DATE;}
      if(form.startdate>form.enddate){return START_DATE_GREATER_THAN_END_DATE;}
      if(isNaN(form.accomodate)){return ACCOMODATE_SHOULD_BE_NUMBER;}
      return null;
    }

    filterFormvalidation(form){
        if(form.price && isNaN(form.price)){ return FILTER_PRICE;}
        if(form.bedroom && isNaN(form.bedroom)){ return FILTER_BEDROOM;}
        return null;
    }

    showPropertyHandler(propertyid){
        if(propertyid == null || typeof(propertyid) == "undefined"){ return; }
        this.setState({
            selectedPropertyId : propertyid,
            propertySelected : propertyid ? true : false
        });
    }
    
    
    render() { 
        let redirectVar = null;
        var {searchCriteria,results} = this.props;
        const {stepper,maxPriceFilter,minBedroomFilter} = this.state;

        /*
        
        */

        const visibleBlock = (results.length==0);
        const nextBtnDisable = (results.length<5);

        if(this.state.propertySelected){
            
            const {startdate,enddate} = searchCriteria;
            const redirecturl =`/overview/${this.state.selectedPropertyId}?startdate=${startdate}&enddate=${enddate}`;
            redirectVar = <Redirect to={redirecturl} />
        }
        
        return (  
            <div>
                {redirectVar}

                <div><HomeAwayPlainNavBar /></div>

                <div><SearchForm onSave={this.criteriaHandler} fields={searchCriteria} /></div>

                <form onSubmit={this.filterFormSubmitHandler}>
                    <div className="row w-100 form-group" style={{ border: '0.5px solid #0069D9' }}>
                        <div className="col-md-1"></div>
                        <div className="col-md-2 pb-1 mb-1">
                            <label for="price">Max Price Per Day (in $)</label>
                            <input type="text" name="price" placeholder="Price" style={{ border: '0.3px solid grey' }} />
                        </div>
                        <div className="col-md-2 pb-1 mb-1">
                            <label for="bedroom">Minimum Bedrooms</label>
                            <input type="number" name="bedroom" defaultValue="0" placeholder="bedroom" style={{ border: '0.3px solid grey' }} />
                        </div>

                        <div className="col-md-2" style={{ marginTop: 'auto', marginBottom: 'auto' }}>
                            <button type="submit" className="btn btn-primary btn-lg btn-block">SUBMIT</button>
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
                        results.map((search, index) => {
                            return (<SearchCard data={search} key={index} onSave={this.showPropertyHandler} />);
                        })
                    }
                </div>

                <div className="row w-100" style={{ bottom: 0, height: '4rem', position: 'fixed' }} >
                    <div className="col-md-1" ></div>
                    <div className="col-md-2">
                        <button className="btn btn-primary btn-lg btn-block" onClick={this.stepperHandler} disabled={!(stepper > 1)} value="-1" style={{ marginTop: '1rem' }}>Previous</button>
                    </div>

                    <div className="col-md-6"></div>

                    <div className="col-md-2">
                        <button className="btn btn-primary btn-lg btn-block" onClick={this.stepperHandler} disabled={nextBtnDisable} value="1" style={{ marginTop: '1rem' }}>Next</button>
                    </div>

                    <div className="col-md-1"></div>
                </div>

            </div>
        );
    }
}

const mapStateToProps = (state) =>{
    
    return{
        user:state.user,
        searchCriteria: state.searchFieldsHome,
        results: state.searchResults
    }
}

const mapDispatchToProps = (dispatch) =>{
    return{
        fetchResults : async (criteria) =>{
            
            const response = await axios.post(BASE_URL+"/search",criteria)
            if(response.status === 200){
                const searchresults = response.data.search;
                dispatch({
                    type: SEARCH_RESULTS,
                    payload:searchresults
                });
            }else{
                
            }
            
        },
        updateSearchFields : (searchFields)=>{
            dispatch({
              type:SEARCH_FIELDS,
              payload:searchFields
            });
        }        

    }
}

//export default Search;
export default connect(mapStateToProps,mapDispatchToProps)(Search)
