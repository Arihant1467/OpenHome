import React, { Component } from 'react';
import './Usercard.css';

class UserCard extends Component {
    constructor(props){
        super(props);
    } 

    render() { 
        const trip = this.props.data;
        const startdate = new Date(trip.start_date*1000).toLocaleDateString();
        const enddate = new Date(trip.end_date*1000).toLocaleDateString();
        
        return ( 
            <div className="row row-style row-booking">
                 <div style={{padding:'8px 4px 4px 8px'}}>
                    <h2>You have reservation from</h2>

                    <p className="clearfix" >Start Date : {startdate}</p>
                    <p> to </p>
                    <p className="clearfix" >End Date : {enddate}</p>
                 </div>
            </div>
         );
    }
}
 
export default UserCard;