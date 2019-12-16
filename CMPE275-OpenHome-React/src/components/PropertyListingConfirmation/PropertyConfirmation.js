import React, { Component } from 'react';
import HomeAwayPlainNavBar from  '../HomeAwayPlainNavBar/HomeAwayPlainNavBar';
import { Redirect } from 'react-router';

class PropertyListingConfirmation extends Component {
    constructor(props){
        super(props);
    }

    render() { 
            const style={
                height:'100%',
                top:'0',
                left:'0',
                right:'0',
                left:'0',
                position:'absolute',
                backgroundColor:'#0069D9'
            }
        return ( 
            

            <div style={style}>
                <HomeAwayPlainNavBar />
                <div className="row justify-content-center pt-5" style={{height:'300px',top:'50%'}}>
                    <div className="col-md-4">
                        <h1 className="text-align-center">Thank you for Listing your property</h1>
                        <h3 className="text-align-center">Your listing will go live after a background check</h3>
                        
                        <h3 className="text-align-center">We will soon redirect to our home page</h3>
                        

                    </div>
                </div>
            </div>
         );
    }
}
 
export default PropertyListingConfirmation;