import React, { Component } from 'react';
import './Usercard.css';
import axios from 'axios';
import {BASE_URL,FRONTEND_URL} from '../../constants.js';


class UserCard extends Component {
    constructor(props){
        super(props);
      //  this.checkIn.bind(this);
       // this.checkOut.bind(this);

    } 
    
     checkIn =(postingid)=> e=> {

      console.log("Checked In"+postingid)
      axios({
        method : 'PUT',
        url:`${BASE_URL}/checkIn`,
        headers:{
          'Content-Type' : 'application/json',
          
        },
        data : postingid
      }
      )
      .then((result) => {
        console.log("result"+result)
        alert("Your checkin is complete")
        //Grey out checkin
      })
      .catch(error =>
        { 
            
            alert(error.response.data)
        }
      )
    }

    checkOut =(postingid)=> e=> {

      console.log("Checked Out"+postingid)
      axios({
        method : 'PUT',
        url:`${BASE_URL}/checkOut`,
        headers:{
          'Content-Type' : 'application/json',
          
        },
        data : postingid
      }
      )
      .then((result) => {
        console.log("result"+result)
        alert("Your checkout is complete")
        //Grey out checkin
      })
      .catch(error =>
        { 
            
            alert(error.response.data)
        }
      )
    }

    

    render() { 
        const trip = this.props.data;
        
        const startdate = new Date(trip.startDate).toLocaleDateString();
        const enddate = new Date(trip.endDate).toLocaleDateString();
        
       console.log("***** Trip ****"+JSON.stringify(trip) )

       
       const status = (trip.isCancelled === 0)? "RESERVED" : "CANCELLED"

       //Change the URL
       const url = FRONTEND_URL+"/overview/"+trip.postingId

      
        
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
                            <button type="submit" className="btn btn-primary btn-block"  onClick={this.checkIn(trip.bookingId)}> Check In </button>
                          </div>
                          <div className="col-md-4">
                            <button type="submit" className="btn btn-primary btn-block"  onClick={this.checkOut(trip.bookingId)}> Check Out </button>
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