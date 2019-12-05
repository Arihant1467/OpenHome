import React, { Component } from 'react';
import './OwnerCard.css';
import {BASE_URL} from './../../constants.js';
import ImageGallery from './../../PropertyOverview/ImageGallery/ImageGallery.js';
import {getDay} from './../../../utils/DayUtils.js';


class OwnerCard extends Component {
    constructor(props){
        super(props);
    } 

    render() { 
        
        const property = this.props.data;

        let days = [];
    for(let i=0;i<7;++i){
      if(property.dayAvailability[i]=="1"){
        
        days.push(getDay(i));
      }
    }
        return ( 
            
            <div className="row justify-content-center row-style row-booking">
                <div className="col-md-3">
                    
                        <ImageGallery photos={property.pictureUrl.split(";")} height="150px" />
                    
                </div>
                <div className="col-md-9">
                    
                <table className="table">
              <thead>
                <tr>
                  <th scope="col">City</th>
                  <th scope="col">Address</th>
                  <th scope="col">State</th>
                  <th scope="col">Area</th>
                  <th scope="col">Week Rent</th>
                  <th scope="col">Weekend Rent</th>
                  <th scope="col">Bedrooms</th>
                  <th scope="col">Days</th>
                </tr>
              </thead>
              <tbody>
                <tr>
                  <td>{property.cityName}</td>
                  <td>{property.streetAddress}</td>
                  <td>{property.state}</td>
                  <td>{property.placeArea} sqft</td>
                  <td>{property.weekRent}</td>
                  <td>{property.weekendRent}</td>
                  <td>{property.noOfBedrooms}</td>
                  <td>{days.join(",")}</td>
                </tr>
              </tbody>
            </table>
                    
                </div>
            </div>
            
         );
    }
}
 
export default OwnerCard;