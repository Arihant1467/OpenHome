import React, { Component } from 'react';
import './Usercard.css';
//import { CANCELLED } from 'dns';

class UserCard extends Component {
    constructor(props){
        super(props);
    } 

    render() { 
        const trip = this.props.data;
        
        const startdate = new Date(trip.startDate).toLocaleDateString();
        const enddate = new Date(trip.endDate).toLocaleDateString();
        
       console.log("***** Trip ****"+JSON.stringify(trip) )

       
       const status = (trip.isCancelled === 0)? "CANCELLED" : "RESERVED"

       //Change the URL
       const url = "http://localhost:3000/overview/"+trip.postingId

        
        return ( 
          <div className="row-style ">


            <div className="row">
              <h2>You have reservation from</h2>
              <hr></hr>
            </div>
            
            <div className="row">
                <div className="col-md-4">   
                   <p className="clearfix" >Start Date : {startdate} to End Date : {enddate}</p>
                </div>


                <div className="col-md-7" >
                      <div className="row">
                          <div className="col-md-4">
                            <button type="submit" className="btn btn-primary btn-block"  > Check In </button>
                          </div>
                          <div className="col-md-4">
                            <button type="submit" className="btn btn-primary btn-block"  > Check Out </button>
                          </div>
                          <div className="col-md-4">
                            <button type="submit" className="btn btn-primary btn-block"  > Cancel Reservation </button>
                          </div>
                      </div>
           
                </div>
                <hr></hr>
            </div>
           

            

            <div className="row">
              <h3>Status {status} </h3>
              <hr></hr>
            </div>

            <div className="row">
              <h3><a href={url}>View your Reservation </a></h3>
            </div>
           
          </div>
                 

                 
                   
                   
                    
                 
            
         );
    }
}
 
export default UserCard;