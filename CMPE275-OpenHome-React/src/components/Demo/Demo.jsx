import React from "react";
import { geolocated } from "react-geolocated";
import axios from 'axios';
 
class Demo extends React.Component {

    constructor(props){
        super(props);
    }

    cordsAvailable = (lat,long)=>{
        console.log(lat);
        console.log(long);
        const key = `AIzaSyBM8OK0R5lltfOOsIaP0GD1wBrR2JRbNoI`;
        const url = `https://maps.googleapis.com/maps/api/geocode/json?latlng=${lat},${long}&key=${key}`;
        
        axios.get(url).then((response)=>{
            console.log(response.data);
            const city = response.data.results[0]['address_components'][3].short_name;
            this.props.onGetLocation(city);
        }).catch((error)=>{
            console.log(error.data);
            this.props.onGetLocation("");
        })
    }
     render() {


        let lat = null;
        let long = null;
        if(this.props.isGeolocationEnabled && this.props.coords!=null){
            lat = this.props.coords.latitude;
            long = this.props.coords.longitude;
            this.cordsAvailable(lat,long);
        }

        return (<div></div>)

        /*
        return !this.props.isGeolocationAvailable ? (
            <div>Your browser does not support Geolocation</div>
        ) : !this.props.isGeolocationEnabled ? (
            <div>Geolocation is not enabled</div>
        ) : this.props.coords ? (
            <table>
                <tbody>
                    <tr>
                        <td>latitude</td>
                        <td>{this.props.coords.latitude}</td>
                    </tr>
                    <tr>
                        <td>longitude</td>
                        <td>{this.props.coords.longitude}</td>
                    </tr>
                    <tr>
                        <td>altitude</td>
                        <td>{this.props.coords.altitude}</td>
                    </tr>
                    <tr>
                        <td>heading</td>
                        <td>{this.props.coords.heading}</td>
                    </tr>
                    <tr>
                        <td>speed</td>
                        <td>{this.props.coords.speed}</td>
                    </tr>
                </tbody>
            </table>
        ) : (
            <div>Getting the location data&hellip; </div>
        );
        */
    }
}
 
export default geolocated({
    positionOptions: {
        enableHighAccuracy: false,
    },
    userDecisionTimeout: 5000,
})(Demo);