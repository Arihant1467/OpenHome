import React, { Component } from 'react';
import HomeAwayPlainNavBar from  '../HomeAwayPlainNavBar/HomeAwayPlainNavBar';

class Booking extends Component {
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
                        <h1 className="text-align-center">Thank you for booking</h1>
                        <h3 className="text-align-center">Your booking has been confirmed</h3>
                    </div>
                </div>
            </div>
         );
    }
}
 
export default Booking;