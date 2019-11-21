import React, { Component } from 'react';
import './OwnerCard.css';
import {BASE_URL} from './../../constants.js';
import ImageGallery from './../../PropertyOverview/ImageGallery/ImageGallery.js'


class OwnerCard extends Component {
    constructor(props){
        super(props);
    } 

    render() { 
        
        const property = this.props.data;
        const booking = property.booking ? property.booking:[];
        
        return ( 
            
            <div className="row justify-content-center row-style row-booking">
                <div className="col-md-3">
                    <div className="pt-2 pb-2 pl-2 pr-2">
                        <ImageGallery photos={property.photos} height="100px" />
                    </div>
                </div>
                <div className="col-md-9 pt-2 pb-2 pl-2 pr-2">
                    <h4 className="clearfix">{property.headline}</h4>
                    <h5 className="clearfix">{property.address}`</h5>
                    <p>{property.city}, {property.subState}</p>

                    <p style={{ color: 'black', fontSize: '16px' }}>{booking.length == 0 ? "No Bookings Availabe" : "Booking Dates"}</p>
                    {
                        booking.map((element, index) => {
                            const startdate = new Date(element.startdate * 1000).toLocaleDateString();
                            const enddate = new Date(element.enddate * 1000).toLocaleDateString();

                            return (
                                <div className="row pb-1" style={{ background: 'transparent' }}>
                                    <div className="col-md-3" style={{ border: '0.5px dashed black' }}>
                                        <p>By : {element.travellername}</p>
                                        <p>Start : {startdate}</p>
                                        <p>End : {enddate}</p>
                                    </div>
                                </div>
                            );
                        })
                    }
                </div>
            </div>
            
         );
    }
}
 
export default OwnerCard;