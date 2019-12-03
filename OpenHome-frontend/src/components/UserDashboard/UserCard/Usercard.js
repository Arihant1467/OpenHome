import React, { Component } from 'react';
import './Usercard.css';

class UserCard extends Component {
    constructor(props){
        super(props);
    } 

    render() { 
        const trip = this.props.data;
        const startdate = new Date(trip.startDate).toLocaleDateString();
        const enddate = new Date(trip.endDate).toLocaleDateString();
        
        return ( 
            <div className="row row-style row-booking">
                 <div style={{padding:'8px 4px 4px 8px'}}>
                    <h2>You have reservation from</h2>

                    <p className="clearfix" >Start Date : {startdate}</p>
                    <p> to </p>
                    <p className="clearfix" >End Date : {enddate}</p>
                 </div>

                 <div className="col-md-4">
                      <button type="submit" className="btn btn-primary btn-block" style={{ height: '50px', fontSize: '18px', borderRadius: '50px' }} > Check In </button>
                    </div>
  
                    <div className="col-md-4">
                      <button type="submit" className="btn btn-primary btn-block" style={{ height: '50px', fontSize: '18px', borderRadius: '50px' }} > Check Out </button>
                    </div>

                    <div className="col-md-5">
                      <button type="submit" className="btn btn-primary btn-block" style={{ height: '50px', fontSize: '18px', borderRadius: '50px' }} > Cancel Reservation </button>
                    </div>
            </div>
         );
    }
}
 
export default UserCard;