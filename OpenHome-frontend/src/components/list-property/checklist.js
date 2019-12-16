import React, { Component } from 'react';
import axios from 'axios';
import cookie from 'react-cookies';
import { Redirect } from 'react-router';
import Swal from 'sweetalert2';
import './checklist.css';
import PropertyDetails from './checklist/details/details.js';
import PropertyLocation from './checklist/location/location';
import PropertyPhotos from './checklist/photo/photo';
import PropertyPricing from './checklist/pricing/pricing';
import { connect } from 'react-redux';
import { Link } from 'react-router-dom';
import { BASE_URL,IMG_RETRIEVE_BASE_URL } from './../constants.js';

const uuidv1 = require('uuid/v1');


class Checklist extends Component {

    constructor(props) {
        super(props);

        const hash = this.props.location.hash
        this.state = {

            complete: false,
            currentpage: {
                location: (hash === "" || hash === " " || hash === "#location"),
                details: (hash === "#details"),
                photos: (hash === "#photos"),
                pricing: (hash === "#pricing")
            },

            property: {
                propertyid: null,
                country: null,
                address: null,
                city: null,
                subState: null,
                unit: null,
                postal: null,

                headline: null,
                description: null,
                type: null,
                bedroom: null,
                accomodate: null,
                bathroom: null,

                photos: [],

                startdate: null,
                enddate: null,
                baserate: null,
                minimumstay: null
            }
        };
        this.savePropertyLocationHandle = this.savePropertyLocationHandle.bind(this);
        this.savePropertyDetailsHandle = this.savePropertyDetailsHandle.bind(this);
        this.savePropertyPhotosHandle = this.savePropertyPhotosHandle.bind(this);
        this.savePropertyPricingHandle = this.savePropertyPricingHandle.bind(this);
    }

    handlePageLocation = (pageLoc) => (e) => {

        this.moveToSelected(pageLoc);
    }

    savePropertyLocationHandle(data) {
        const property = Object.assign({}, this.state.property, data);
        this.setState({
            property: property
        }, () => {

            this.moveToSelected("details");
        });
    }

    savePropertyDetailsHandle(data) {
        const property = Object.assign({}, this.state.property, data);
        this.setState({
            property: property
        }, () => {

            this.moveToSelected("photos");
            // this.moveToSelected("pricing");
        });
    }

    savePropertyPhotosHandle(files) {

        const data = { photos: files }
        const property = Object.assign({}, this.state.property, data);
        this.setState({
            property: property
        }, () => {
            this.moveToSelected("pricing");
        });
    }

    savePropertyPricingHandle(data) {
        const property = Object.assign({}, this.state.property, data);
        this.setState({
            property: property
        }, () => {
            
            
            const { property } = this.state;
            const propertyData = {
                property
            }
            console.log("user");
            console.log(this.props.user);
            this.sendData(propertyData, this.props.user.email).then((msg) => {
                //alert("Property has been successfuly added. You will be soon redirected to our homepage.");
                // this.setState({ complete: true });
                Swal.fire({
                    title: "Congratulations!",
                    text: "Property has been successfuly added. You will be soon redirected to our homepage.",
                }).then(()=>{
                    this.setState({ complete: true });
                });
                
            }).catch((msg) => {
                //alert(`${msg}`);
                Swal.fire('Oops...', `${msg}`, 'error')
            })

        });
    }


    async sendData(propertyData, ownerEmailId) {
        const posting = {}
        posting["streetAddress"] = propertyData.property.streetAddress
        posting["cityName"] = propertyData.property.cityName;
        posting["state"] = propertyData.property.state;
        posting["zipcode"] = parseInt(propertyData.property.zipcode, 10);
        posting["description"] = propertyData.property.description;
        posting["propertyType"] = propertyData.property.propertyType;
        posting["noOfBedrooms"] = parseInt(propertyData.property.noOfBedrooms, 10);
        posting["sharingType"] = propertyData.property.sharingType;
        posting["placeArea"] = parseInt(propertyData.property.placeArea, 10);
        posting["privateRoomArea"] = parseInt(propertyData.property.privateRoomArea, 10);
        posting["wifi"] = propertyData.property.wifi;

        posting["hasPrivateBr"] = propertyData.property["hasPrivateBr"] !== undefined ? 1 : 0;
        posting["hasPrivateShower"] = propertyData.property["hasPrivateShower"] !== undefined ? 1 : 0;
        posting["parkingAvailable"] = propertyData.property["parkingAvailable"] !== undefined ? 1 : 0;
        posting["onsiteLaundry"] = propertyData.property["onsiteLaundry"] !== undefined ? 1 : 0;
        posting["cityView"] = propertyData.property["cityView"] !== undefined ? 1 : 0;
        posting["smokingAllowed"] = propertyData.property["smokingAllowed"] != undefined ? 1 : 0;
        const sun = propertyData.property["sun"] !== undefined ? "1" : "0"
        const mon = propertyData.property["mon"] !== undefined ? "1" : "0";
        const tue = propertyData.property["tue"] !== undefined ? "1" : "0";
        const wed = propertyData.property["wed"] !== undefined ? "1" : "0";
        const thur = propertyData.property["thur"] !== undefined ? "1" : "0";
        const fri = propertyData.property["tue"] !== undefined ? "1" : "0";
        const sat = propertyData.property["sat"] !== undefined ? "1" : "0";
        posting["dayAvailability"] = sun + mon + tue + wed + thur + fri + sat;
        posting["startDate"] = new Date(propertyData.property.startDate).getTime();
        posting["endDate"] = new Date(propertyData.property.endDate).getTime();
        posting["weekRent"] = parseInt(propertyData.property.weekRent, 10);
        posting["weekendRent"] = parseInt(propertyData.property.weekendRent, 10);
        posting["dailyParkingFee"] = parseInt(propertyData.property.dailyParkingFee, 10);
        posting["parkingPay"] = parseInt(propertyData.property.parkingPay, 10);
        posting["userId"] = ownerEmailId;
        posting["contactNumber"]=parseInt(propertyData.property.contactNumber,10);
        //posting["pictureUrl"]="http://www.sample/image.jpeg";
        posting["pageViews"]=0;
        

        const multipartConfig = {
            headers:{
                'Content-Type':'multipart/form-data'
            }
        }

        const photoServerUrl = `${IMG_RETRIEVE_BASE_URL}/uploadPhotos`;
        let formData = new FormData();
        const { photos } = propertyData.property;
        var fileNames = []
        for(var i=0;i<photos.length;++i){
            formData.append("files",photos[i]);
            fileNames.push(photos[i].name);
        };

        console.log(`The fileNames from upload: ${JSON.stringify(fileNames)}`);
        const responsePhotos = await axios.post(photoServerUrl,formData,multipartConfig);
        if(responsePhotos.status!== 200){
            alert("We could not upload your photos");
        }
        // if(responsePhotos.status == 200){
        //     console.log(`The fileNames from response:`);
        //     console.log(responsePhotos.data.msg);
        // }else{
        //     console.log(`The fileNames from response:`);
        //     console.log(responsePhotos.data.msg);
        // }
        // console.log(`The fileNames from upload: ${JSON.stringify(fileNames)}`);

        posting["pictureUrl"]=fileNames.join(";")
        

        const config = {
            headers: {
                'Content-Type': 'application/json'
            }
        }
        const response = await axios.post(`${BASE_URL}/posting`, posting, config);
        
        if (response.status === 200) {
            console.log("success");
            return Promise.resolve("success");
        } else {
            return Promise.reject("fail");
        }
        

    }

    moveToSelected(pageLoc) {
        this.setState({
            currentpage: {
                location: pageLoc === "location",
                details: pageLoc === "details",
                photos: pageLoc === "photos",
                pricing: pageLoc === "pricing"
            }
        });
    }

    render() {

        var redirectVar = null;
        if (this.state.complete) {
            redirectVar = <Redirect to="/propertyconfirmation" />
        }

        if (JSON.stringify(this.props.user) === "{}") {
            cookie.remove("username");
            redirectVar = <Redirect to="/login" />
        }

        return (
            <div style={{ margin: '0px' }}>
                {redirectVar}
                <div className="top-content-bar">
                    <div className="row">
                        <div className="col-md-1 col">
                            <img className="mt-1 ml-2" width="30px" height="30px" src="https://png.icons8.com/ios-glyphs/60/537d77/menu.png" />
                        </div>

                        <div className="col-md-4 col">
                            <Link to="/home"> <img className="element-margin" src=" https://csvcus.homeaway.com/rsrcs/cdn-logos/2.10.6/bce/moniker/homeaway_us/logo-bceheader.svg" alt="" /></Link>
                        </div>
                        <div className="col-md-3 col hover-color">

                            <div className="btn btn-lg btn-block dropdown-toggle toggle-after" id="dropdownMenuButton" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false" style={{ textAlign: 'left' }}>
                                <p className="mb-0 pt-1" style={{ float: 'left', width: '100%', fontSize: '15px', textOverflow: 'ellipsis', lineHeight: '15px', overflow: 'hidden', color: '#5e6d77', whiteSpace: 'nowrap', fontWeight: '400' }}> 1 S Market St, San Jose, CA, US</p>
                                <p className="mt-0 pt-1" style={{ float: 'left', width: '90%', color: '#adb2b8', fontSize: '13px', textOverflow: 'ellipsis', lineHeight: '15px', overflow: 'hidden' }}>HA 7360319</p>
                            </div>

                            <div className="dropdown-menu" aria-labelledby="dropdownMenuButton">
                                <div className="dropdown-item">
                                    <p className="pl-1 mb-0" style={{ fontSize: '15px', textOverflow: 'ellipsis', fontWeight: '400' }}> 1 S Market St, San Jose, CA, US</p>
                                    <p className="pl-1" style={{ color: '#adb2b8', fontSize: '13px', textOverflow: 'ellipsis', overflow: 'hidden' }}>HA 7360319</p>
                                </div>
                                <div className="dropdown-item">
                                    <p className="pl-1 mb-0" style={{ fontSize: '15px', textOverflow: 'ellipsis', fontWeight: '400' }}> 1 S Market St, San Jose, CA, US</p>
                                    <p className="pl-1" style={{ color: '#adb2b8', fontSize: '13px', textOverflow: 'ellipsis', overflow: 'hidden' }}>HA 7360319</p>
                                </div>

                                <div className="dropdown-divider"></div>
                                <a className="dropdown-item" href="#">See all your properties</a>
                            </div>
                        </div>

                        <div className="col-md-2 col hover-color">
                            <div className="row full-width" style={{ margin: '0px', background: 'none' }}>
                                <div className="col-md-3 mb-0">
                                    <div className="full-height full-width" >
                                        <img src="https://png.icons8.com/material-sharp/48/353e44/user-male-circle.png" width="48px" height="48px" alt="" />
                                    </div>
                                </div>
                                <div className="col-md-5">
                                    <button className="btn btn-block dropdown-toggle mt-2 pr-2" type="button" id="dropdownMyAccount" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false" style={{ background: 'none', fontSize: '16px' }}>
                                        My Account
                                    </button>
                                    <div className="dropdown-menu" aria-labelledby="dropdownMyAccount">
                                        <div className="dropdown-item">
                                            <p>Account Setting</p>
                                        </div>

                                        <div className="dropdown-item">
                                            <p>Property Details</p>
                                        </div>

                                        <div className="dropdown-item">
                                            <p>Property Archive</p>
                                        </div>

                                        <div className="dropdown-item">
                                            <p>Add new Property</p>
                                        </div>

                                        <div className="dropdown-item">
                                            <p>Sign out</p>
                                        </div>

                                    </div>
                                </div>
                            </div>
                        </div>

                        <div className="col-md-1 col text-center align-center hover-color">
                            <svg className="mt-3" width="22px" height="22px" viewBox="0 0 20 22" version="1.1" xmlns="http://www.w3.org/2000/svg"><g stroke="#353E44" strokeWidth="1.55" fill="none" fillRule="evenodd" strokeLinecap="square" transform="translate(-1310.000000, -19.000000)"><g transform="translate(1312.000000, 14.000000)"><g transform="translate(0.000000, 6.000000)"><path d="M10.9090909,17.2727273 C10.9090909,18.8181818 9.72727273,20 8.18181818,20 C6.63636364,20 5.45454545,18.8181818 5.45454545,17.2727273"></path><path d="M14.5454545,10.9090909 C14.5454545,8.63636364 14.5454545,6.36363636 14.5454545,6.36363636 C14.5454545,2.81818182 11.7272727,0 8.18181818,0 C4.63636364,0 1.81818182,2.81818182 1.81818182,6.36363636 C1.81818182,6.36363636 1.81818182,8.63636364 1.81818182,10.9090909 C1.81818182,14.5454545 0,17.2727273 0,17.2727273 L16.3636364,17.2727273 C16.3636364,17.2727273 14.5454545,14.5454545 14.5454545,10.9090909 Z"></path></g></g></g></svg>
                        </div>
                        <div className="col-md-1 col text-center hover-color" >
                            <div className="full-height">
                                <svg xmlns="http://www.w3.org/2000/svg" width="22" height="22" style={{ marginTop: '20px' }} viewBox="0 0 22 22"><g fill="currentColor"><rect x="1" y="1" width="4" height="4"></rect><rect x="9" y="1" width="4" height="4"></rect><rect x="17" y="1" width="4" height="4"></rect><rect x="1" y="9" width="4" height="4"></rect><rect x="9" y="9" width="4" height="4"></rect><rect x="17" y="9" width="4" height="4"></rect><rect x="1" y="17" width="4" height="4"></rect><rect x="9" y="17" width="4" height="4"></rect><rect x="17" y="17" width="4" height="4"></rect></g></svg>
                            </div>
                        </div>
                    </div>
                </div>


                <div className="flex-container justify-content-center w-100">
                    <div className="inner-element" style={{ width: '85%' }} >
                        <div className="user-progress">
                            <p className="pt-2" style={{ color: '#5e6d77', lineHeight: '1.5rem', fontSize: '16px' }}>Progress</p>
                            <div className="progress mb-2">
                                <div className="progress-bar" role="progressbar" style={{ width: '25%' }} aria-valuenow="25" aria-valuemin="0" aria-valuemax="100">
                                </div>
                            </div>
                            <hr />
                        </div>
                    </div>
                </div>

                <div className="flex-container justify-content-center w-100 pt-3">
                    <div className="inner-element" style={{ width: '85%' }} >
                        <div className="row checklist-content-header" style={{ background: 'none' }}>
                            <div className="col-md-4">
                                <p style={{ marginTop: '10px', positon: 'relative', textAlign: 'center', fontSize: '18px' }}>Welcome</p>
                            </div>
                            <div className="col-md-8">
                                <p style={{ fontSize: '30px' }}>Describe your property</p>
                            </div>
                        </div>

                        <div className="row" style={{ background: 'none' }}>
                            <div className="col-md-4 nav-content house-properties-checklist">
                                <ul style={{ listStyleType: 'none' }}>
                                    <li style={{ height: '50px' }}><span><a href="#location" onClick={this.handlePageLocation("location")} style={{ color: 'black' }}>Location</a></span></li>
                                    <li style={{ height: '50px' }}><span><a href="#details" onClick={this.handlePageLocation("details")} style={{ color: 'black' }}>Details</a></span></li>
                                    <li style={{ height: '50px' }}><span><a href="#photos"   onClick={this.handlePageLocation("photos")} photos="" style={{ color: 'black' }}>Photos</a></span></li>
                                    <li style={{ height: '50px' }}><span><a href="#pricing" onClick={this.handlePageLocation("pricing")} style={{ color: 'black' }}>Pricing</a></span></li>
                                </ul>
                            </div>

                            <div className="col-md-8" style={{ background: 'white' }}>
                                <PropertyLocation visible={this.state.currentpage.location} onSave={this.savePropertyLocationHandle} />
                                <PropertyDetails visible={this.state.currentpage.details} onSave={this.savePropertyDetailsHandle} />
                                <PropertyPhotos visible={this.state.currentpage.photos} onSave={this.savePropertyPhotosHandle} />
                                <PropertyPricing visible={this.state.currentpage.pricing} onSave={this.savePropertyPricingHandle} />
                            </div>
                        </div>
                    </div>
                </div>

            </div>

        );
    }
}

const mapStateToProps = (state) => {
    const { user } = state;
    return {
        user
    }
}

export default connect(mapStateToProps, null)(Checklist);