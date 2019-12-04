import React, { Component } from 'react';
import ImageGallery from './ImageGallery/ImageGallery.js'
import PropertyOverviewLeft from './PropertyOverviewLeft/PropertyOverviewLeft';
import PropertyOverviewRight from './PropertyOverviewRight/PropertyOverviewRight.js';
import HomeAwayPlainNavBar from '../HomeAwayPlainNavBar/HomeAwayPlainNavBar.js';
import axios from 'axios';
import {Redirect} from 'react-router';
import { connect } from 'react-redux';
import MessageBar from './MessageBar/MessageBar.js';
import {BASE_URL} from "../constants.js";
const queryString = require('query-string');


class PropertyOverview extends Component {
    
    constructor(props){
        super(props)
        const {startDate,endDate} = queryString.parse(this.props.location.search);
        this.state ={
            propertyId  : this.props.match.params.propertyid,
            property    : null,
            booked      : false,
            startDate,
            endDate
        }
        
        this.bookNowHandler = this.bookNowHandler.bind(this);
        this.sendMessage = this.sendMessage.bind(this);
        
    }


   componentDidMount(){

    const {propertyId} = this.state;
    `${BASE_URL}/posting/${propertyId}`
        axios.get(`${BASE_URL}/posting/${propertyId}`).then((response)=>{
            const data = response.data;
            this.setState({
                property:data
            });
        });
        
    }

    async sendMessage(message){
        if(JSON.stringify(this.props.user)=== "{}"){
            alert("You need to login to send a message to owner");
            return;
        }
        
        
        const {ownerid,ownername,propertyid} = this.state.property;
        const {username,userid} = this.props.user;
        
        const data = {
            ownerid,ownername,
            propertyid,message,username
        }

        
        const url = BASE_URL+"/message/"+userid;
        const response = await axios.post(url,data);

        if(response.status == 200){
            alert("Owner will be notified");
        }else{
            alert("We could not post the message");
        }
    }

    async bookNowHandler(){
        
        // if(JSON.stringify(this.props.user) == "{}" || this.state.startdate==="" || this.state.enddate===""){
        //     alert("Please login into your account to continue with your booking");
        //     return;
        // }
        
        // const {username,userid} = this.props.user;
        // const {ownerid,ownername,city,headline} = this.state.property;
        
        // const data = {
        //     userid,username,
        //     startdate  : this.state.startdate, 
        //     enddate    : this.state.enddate,
        //     ownerid,ownername,
        //     city,headline
        // }
        
        // const response = await axios.post(BASE_URL+"/booking/"+this.state.propertyid,data);
        // if(response.status === 200){
            
        //     alert("We have confirmed your booking. We are redirecting you too our confirmation page");
        //     setTimeout(()=>{
        //         this.setState({booked : true});
        //     },3000);
            
        // }else{
        //     alert("We had some inconvenience in confirming your booking");
        // }

        
        
    }

    render() { 
        
        var redirectVar = null
        var renderGallery = null
        var renderRightView = null
        var renderLeftView = null
        var renderMessageBar = null
        
        if(this.state.booked){
            redirectVar = <Redirect to="/booking" />
        }

        const {property, startDate, endDate} = this.state;
        if(this.state.property){
            // renderGallery = <ImageGallery photos={this.state.property.photos} />
            //renderLeftView = <PropertyOverviewLeft data={property}/>
            renderRightView =<PropertyOverviewRight data={property} startDate={startDate} endDate={endDate} onSave={this.bookNowHandler}/>
            // renderMessageBar = <MessageBar onSend={this.sendMessage} />
        }

        return ( 
            <div>
                {redirectVar}
                    <HomeAwayPlainNavBar />
                    <div className="row" style={{bottom: '5rem'}}>
                    <div className="col-md-8 .add-border-property-selected" id="left">
                        {renderGallery}
                        {renderLeftView}
                    </div>
                    <div className="col-md-4 .add-border-property-selected" id="right">
                        {renderRightView}
                    </div>
                </div>

                {renderMessageBar}
                
            </div>
         );
    }
}
 
const mapStateToProps = (state) =>{
    return{
        user : state.user
    }
}

//export default PropertyOverview;
export default connect(mapStateToProps,null)(PropertyOverview);