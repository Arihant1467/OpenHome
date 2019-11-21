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
            stepper :1
        }
    }

    componentDidMount(){
        
        const url = BASE_URL+"/tripboards/"+this.state.userid+"/1"
        axios.get(url).then((response)=>{
                
                if(response.status === 200){
                    const {trips} = response.data;
                    this.setState({ results : trips });
                }else{
                    alert("Sorry!! we could not fetch your trips")
                }
        });
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
                const {trips} = response.data;
                this.setState({ results : trips });
            }else{
                alert("There was an error in fetching your Trips");
            }
        });
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

                    <div className="row w-100" >
                    <div className="col-md-1" ></div>
                    <div className="col-md-2">
                        <button className="btn btn-primary btn-lg btn-block" onClick={this.stepperHandler} disabled={!(stepper > 1)} value="-1" style={{ marginTop: '1rem' }}>Previous</button>
                    </div>

                    <div className="col-md-6"></div>

                    <div className="col-md-2">
                        <button className="btn btn-primary btn-lg btn-block" onClick={this.stepperHandler} disabled={disableNext} value="1" style={{ marginTop: '1rem' }}>Next</button>
                    </div>

                    <div className="col-md-1"></div>
                </div>

                    <div style={{display:visibleBlock? 'block':'none'}}>
                        <h2 style={{textAlign:'center'}}>No Trips to Fetch</h2>
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