import React, { Component } from 'react';
import OwnerCard from './OwnerCard/OwnerCard';
import {BASE_URL} from './../constants.js';
import axios from 'axios';
import HomeAwayPlainNavBar from './../HomeAwayPlainNavBar/HomeAwayPlainNavBar.js';


class OwnerDashboard extends Component {
    
    constructor(props){
        super(props);
        
        this.state = {
            userid : this.props.match.params.ownerid,
            results:[],
            stepper : 1
        }
        this.stepperHandler = this.stepperHandler.bind(this);
    }

    componentDidMount(){
        const {stepper} = this.state;
        
        axios.get(BASE_URL+"/ownerlisting/"+this.state.userid+"/"+stepper).then((response)=>{
        
                if(response.status === 200){
                    const {listings} = response.data
                    this.setState({ results : listings });
                }else{
                    alert("There was an error in fetching your properties");
                }
        });
    }

    stepperHandler = (e)=>{
        const stepper_value = parseInt(e.target.value);
        var {stepper} = this.state;
        stepper = stepper+stepper_value;
        stepper = (stepper<=0 ? 1:stepper);
        this.setState({stepper});

        axios.get(BASE_URL+"/ownerlisting/"+this.state.userid+"/"+stepper).then((response)=>{
            if(response.status === 200){
                const {listings} = response.data
                this.setState({ results : listings });
            }else{
                alert("There was an error in fetching your properties");
            }
        });
    }

    render() { 
        const style={
            height:'100%',top:'0',left:'0',right:'0',left:'0',
            position:'absolute',backgroundColor:'#F4F4F4'
        }

        const {stepper,results} = this.state;
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
                        <h2 style={{textAlign:'center'}}>No Properties to Fetch</h2>
                    </div>
                    
                    <div style={{ display: visibleBlock? 'none':'block'}}>
                    {
                        this.state.results.map((result,index)=>{
                                return (
                                    <OwnerCard data={result} key={index} />
                                );
                        })
                    }
                    </div>

                
                </div>
         );
    }
}
 
export default OwnerDashboard;