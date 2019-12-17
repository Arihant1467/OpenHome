import React, { Component } from 'react';
import './Usercard.css';
import axios from 'axios';
import {BASE_URL,FRONTEND_URL} from '../../constants.js';
import StarRatingComponent from 'react-star-rating-component';
import Swal from 'sweetalert2';


class UserCard extends Component {

    constructor(props){
        super(props);
      //  this.checkIn.bind(this);
       // this.checkOut.bind(this);

       this.state = {
        rating: 5,
        review : ""
    }

    }


    rateReservation = (bookingId)=> e=> {
 
      const trip = this.props.data; 

      const body = {
     
        "postingId" : trip.postingId,
        "userId" : trip.userId,
        "rating" : this.state.rating,
        "review" : this.state.review,
        "bookingId" : trip.bookingId
    }

    axios.post(`${BASE_URL}/postingRating`,body).then((response)=>{
        // alert("You have successfully booked the property");
        Swal.fire({
            title: "Rating received!",
            text: "Rating is saved!"
        }).then(()=>{
          console.log("reservation rating saved");
        });
      
    }).catch((error)=>{
        Swal.fire('Oops...', `${error.response.data}`, 'error');
    })
  
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


    cancel =(postingid)=> e=> {

      console.log("Cancelling reservation"+postingid)
      axios({
        method : 'PUT',
        url:`${BASE_URL}/cancelReservation/${postingid}`,
        headers:{
          'Content-Type' : 'application/json',
          
        }
      }
      )
      .then((result) => {
        console.log("result"+result)
        alert("Your reservation is cancelled")

      })
      .catch(error =>
        { 
            
            alert(error.response.data)
        }
      )
    }

    onStarClick(nextValue, prevValue, name) {
      this.setState({rating: nextValue});
    }
    
    reviewChangeHandler = (e) => {
      this.setState({
          review : e.target.value
      })
  }

    
    rating1() {

      const { rating } = this.state;

   const trip = this.props.data; 

    console.log("i m in rating")

      if(true ) {
        return (
          <div  style = { { left : "1000px", top : "10px"}}>
          <hr></hr>
          <h2> How did you like your stay with us, please let us know.. </h2>

          <div style = { { left : "1000px", width : "500px"}}>

          <StarRatingComponent   
          name="rating" 
          starCount={5}
          value={rating}
          onStarClick={this.onStarClick.bind(this)}
         
        />

        </div>


        <div class= "row"  style = { { left : "1000px", top : "10px"}} >

       
        <textarea style = { { width : "800px"}} onChange = {this.reviewChangeHandler} type="text"  name="Message" placeholder="Message">
            </textarea> 
            
          

        <button type="submit" style = { { width : "200px"}} className="btn btn-primary btn-block" onClick={this.rateReservation(trip.bookingId)}> Submit Rating</button>
        </div> 
     
        </div>
        
      );
       
      } 
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
                            <button type="submit" className="btn btn-primary btn-block" onClick={this.cancel(trip.bookingId)}  > Cancel Reservation </button>
                          </div>
                      </div>

                    
           
                </div>
                <hr></hr>
            </div>
           
            <div > 
            <p>{this.rating1()}</p>

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

// <button onClick = {this.announcement.bind(this)} class="btn btn-success btn-md"> + Make announcement</button> */