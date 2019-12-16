import React, { Component } from 'react';
import ImageGallery from './ImageGallery/ImageGallery.js'
import PropertyOverviewLeft from './PropertyOverviewLeft/PropertyOverviewLeft';
import PropertyOverviewRight from './PropertyOverviewRight/PropertyOverviewRight.js';
import HomeAwayPlainNavBar from '../HomeAwayPlainNavBar/HomeAwayPlainNavBar.js';
import axios from 'axios';
import Swal from 'sweetalert2';
import { Redirect } from 'react-router';
import { connect } from 'react-redux';
import {getWeekRepresentation} from './../../utils/DayUtils';
import MessageBar from './MessageBar/MessageBar.js';
import { BASE_URL } from "../constants.js";
const queryString = require('query-string');


class PropertyOverview extends Component {

    constructor(props) {
        super(props)
        const { startDate, endDate } = queryString.parse(this.props.location.search);
        console.log(queryString.parse(this.props.location.search));

        this.state = {
            propertyId: this.props.match.params.propertyid,
            property: null,
            booked: false,
            startDate: new Date(parseInt(startDate,10)),
            endDate: new Date(parseInt(endDate,10))
        }

        this.bookNowHandler = this.bookNowHandler.bind(this);
        this.sendMessage = this.sendMessage.bind(this);

    }


    componentDidMount() {

        const { propertyId } = this.state;
        `${BASE_URL}/posting/${propertyId}`
        axios.get(`${BASE_URL}/posting/${propertyId}`).then((response) => {
            const data = response.data;
            this.setState({
                property: data
            });
        });

    }

    async sendMessage(message) {
        if (JSON.stringify(this.props.user) === "{}") {
            alert("You need to login to send a message to owner");
            return;
        }


        const { ownerid, ownername, propertyid } = this.state.property;
        const { username, userid } = this.props.user;

        const data = {
            ownerid, ownername,
            propertyid, message, username
        }


        const url = BASE_URL + "/message/" + userid;
        const response = await axios.post(url, data);

        if (response.status == 200) {
            alert("Owner will be notified");
        } else {
            alert("We could not post the message");
        }
    }

    async bookNowHandler() {

        const { userId, propertyId, weekendRent,weekRent } = this.state.property;
        const { startDate, endDate } = this.state;
        const { numberOfWeekends,numberOfWeekdays } = this.getNumberOfWeekDaysAndWeekEnds(startDate,endDate);
        const bookingCost = numberOfWeekends*weekendRent + numberOfWeekdays*weekRent;
        const dayAvailibility = getWeekRepresentation(startDate.toISOString().split('T')[0],endDate.toISOString().split('T')[0]);

        const body = {
            "hostEmailId": userId,
            "tenantEmailId": this.props.user.userid,
            "startDate": startDate.getTime(),//new Date(startDate).getTime(),
            "endDate": endDate.getTime(),//new Date(endDate).getTime(),
            "postingId": propertyId,
            bookingCost,
            "isCancelled": 0,
            "checkIn": null,
            "checkOut": null,
            dayAvailibility
        }

        axios.post(`${BASE_URL}/reservation`,body).then((response)=>{
            // alert("You have successfully booked the property");
            Swal.fire({
                title: "Congratulations!",
                text: "Property has been successfuly booked.",
            }).then(()=>{
                this.setState({ complete: true });
            });
            this.setState({
                booked:true
            });
        }).catch((error)=>{
            // alert(error.response.data);
            Swal.fire('Oops...', `${error.response.data}`, 'error');
        })

    }

    getNumberOfWeekDaysAndWeekEnds = (start, end) => {
        let startDate = new Date(start);
        const endDate = new Date(end);

        const days = {
            numberOfWeekends:0,
            numberOfWeekdays:0
        }

        const milliSecondInOneDay = 24 * 60 * 60 * 1000;
        while (startDate.getTime() <= endDate.getTime()) {
            const day = startDate.getDay();
            if (day == 1 || day == 2 || day == 3 || day == 4) {
                days.numberOfWeekdays += 1;
            }
            else {
                days.numberOfWeekends += 1;
            }
            startDate = new Date(startDate.getTime() + milliSecondInOneDay);
        }

        return days;
    }

    render() {

        let redirectVar = null
        let renderGallery = null
        let renderRightView = null
        let renderLeftView = null
        let renderMessageBar = null

        if (this.state.booked) {
            redirectVar = <Redirect to="/booking" />
        }
        
        const { property, startDate, endDate } = this.state;
        const userLoggedIn = !(JSON.stringify(this.props.user)==="{}") ;
        if (this.state.property) {
            
            renderGallery = <ImageGallery photos={this.state.property.pictureUrl.split(";")} />
            renderLeftView = <PropertyOverviewLeft data={property}/>
            renderRightView = <PropertyOverviewRight data={property} user={this.props.user} startDate={startDate} endDate={endDate} userLoggedIn={userLoggedIn}  onSave={this.bookNowHandler} />
            // renderMessageBar = <MessageBar onSend={this.sendMessage} />
        }

        return (
            <div>
                {redirectVar}
                <HomeAwayPlainNavBar />
                
                <div className="row" style={{ bottom: '5rem' }}>
                    
                    <div className="col-md-8 .add-border-property-selected" id="left">
                        {renderGallery}
                        {renderLeftView}
                    </div>
                    <div className="col-md-4 .add-border-property-selected" id="right">
                        {renderRightView}
                    </div>
                </div>


            </div>
        );
    }
}

const mapStateToProps = (state) => {
    return {
        user: state.user
    }
}

//export default PropertyOverview;
export default connect(mapStateToProps, null)(PropertyOverview);