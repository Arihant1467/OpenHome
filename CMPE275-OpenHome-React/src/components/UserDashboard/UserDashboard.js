import React, { Component } from 'react';
import UserCard from './UserCard/Usercard';
import axios from 'axios';
import HomeAwayPlainNavBar from './../HomeAwayPlainNavBar/HomeAwayPlainNavBar.js';
import {BASE_URL} from '../constants.js';

class UserDashboard extends Component {
    
    constructor(props){
        super(props);
        this.state = {
            userid : this.props.match.params.userid,
            results:[],
            reservationType:"ALL",
            stepper :1
        }
        
        this.reservationTypeHandler = this.reservationTypeHandler.bind(this);
    }

    componentDidMount(){
        
        const url = BASE_URL+"/reservationsByEmail/?email="+this.state.userid;
        axios.get(url).then((response)=>{
                console.log(response);
                if(response.status === 200){
                    this.setState({ results : response.data });
                }else{
                    alert("Sorry!! we could not fetch your reservations")
                }
        }).catch((error)=>{
            console.log(error);
        })
    }


    reservationTypeHandler = (e)=>{
        const reservationType = e.target.value;
        this.setState({
            reservationType
        })
    }

    stepperHandler = (e)=>{
        const stepper_value = parseInt(e.target.value);
        var {stepper} = this.state;
        stepper = stepper+stepper_value;
        stepper = (stepper<=0 ? 1:stepper);
        this.setState({stepper});

        const url = BASE_URL+"/tripboards/"+this.state.userid+"/"+stepper
        axios.get(url).then((response)=>{
            if(response.status === 200){
                this.setState({ results : response.data });
            }else{
                alert("There was an error in fetching your Reservations");
            }
        }).catch((err)=>{
            console.log(err);
        })
    }

    render() { 

        const style={
            height:'100%',top:'0',left:'0',right:'0',left:'0',
            position:'absolute',backgroundColor:'#F4F4F4'
        }

        const {results,stepper} = this.state;
        const visibleBlock = (results.length==0);
        const disableNext = (results.length<5);

        return (  
            <div style={style}>
                    
                    <HomeAwayPlainNavBar />
                    {/* <div className="col-md-1" ></div>
                    <div className="col-md-2">
                        <button className="btn btn-primary btn-lg btn-block" disabled onClick={this.stepperHandler} disabled={!(stepper > 1)} value="-1" style={{ marginTop: '1rem' }}>Previous</button>
                    </div>

                    <div className="col-md-6"></div>

                    <div className="col-md-2">
                        <button className="btn btn-primary btn-lg btn-block" disabled onClick={this.stepperHandler} disabled={disableNext} value="1" style={{ marginTop: '1rem' }}>Next</button>
                    </div>

                    <div className="col-md-1"></div> */}
                    <form onSubmit={this.filterFormSubmitHandler}>

                    <div className="row form-group" style={{ border: '0.5px solid #0069D9', padding: '5px' }}>
                    <div className="col-md-2">
                            <label htmlFor="reservationType">Reservation Type</label>
                            <select name="reservationType" className="no-bg" style={{ border: '0.3px solid grey' }}  >
                                <option selected value="ALL">ALL</option>
                                <option value="FUTURE">UPCOMING</option>
                                <option value="PRESENT">PLACE</option>
                                <option value="PAST">PAST</option>
                            </select>
                        </div>
                    </div>


                    </form>
                

                    <div style={{display:visibleBlock? 'block':'none'}}>
                        <h2 style={{textAlign:'center'}}>No Reservations to Fetch</h2>
                    </div>

                    <div style={{display:visibleBlock? 'none':'block'}}>
                    {
                        results.map((trip,index)=>{
                                return (
                                    <UserCard data={trip} key={index} />
                                );
                        })
                    }    
                    </div>
                    

                </div>
        );
    }
}
 
export default UserDashboard;