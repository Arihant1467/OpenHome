import React, { Component } from 'react';
import { connect } from 'react-redux';
import {BASE_URL} from './../constants.js';
import axios from 'axios';
import cookie from 'react-cookies';
import { Redirect } from 'react-router';
import serialize from 'form-serialize';
import {LOGIN,LOGIN_ERROR} from './../actions/types.js';
import HomeAwayPlainNavBar from './../HomeAwayPlainNavBar/HomeAwayPlainNavBar.js';

class UserProfile extends Component {
    
    constructor(props){
        super(props);
        this.submitHandler = this.submitHandler.bind(this);
    }

    submitHandler =(e)=>{
        e.preventDefault();
        var form = serialize(e.target, { hash: true });
        this.props.updateuser(form,this.props.user.userid);
    }

    render() { 

        const {user} = this.props;
        return ( <div>

             <HomeAwayPlainNavBar />
             <div className="row justify-content-center w-100 " style={{backgroundColor:'#F4F4F4',height:'100%'}}>
                <div className="col-md-6 add-border-signup text-center mt-3" style={{backgroundColor:'white'}}>
                    <h1 className="homeaway-h1 justify-content-centre mb-2" style={{fontSize:'34px'}} >Update your Profile Information</h1>
            
                    
                    <form onSubmit={this.submitHandler} >
                    <div className="mt-4" style={{border:'0px 2px 0px 2px'}}>
                        <input type="text" name="username" defaultValue={user.username}  className="width-100" placeholder="Username"/>
                    </div>

                    <div className="mt-4" style={{border:'0px 2px 0px 2px'}}>
                        <input type="text" name="firstname" defaultValue={user.firstname}  className="width-100" placeholder="Firstname"/>
                    </div>

                    <div className="mt-4" style={{border:'0px 2px 0px 2px'}}>
                        <input type="text" name="lastname" defaultValue={user.lastname}  className="width-100" placeholder="Lastname"/>
                    </div>

                    <div className="mt-4" style={{border:'0px 2px 0px 2px'}}>
                        <input type="password" name="password" placeholder="Password" className="width-100" />
                    </div>
        
                    <div className="mt-4">
                        <button type="submit" className="btn btn-lg btn-block" style={{color:'white',backgroundColor:'#ff8a00',height:'40px',fontSize:'22px'}} >Update</button> 
                    </div>
                    </form>
                    
                    <br />
                </div>
            </div>

        </div> );
    }
}
 
const mapStateToProps = (state)=>{
    return{ user : state.user }
}

const mapDispatchToProps = (dispatch) =>{

    return{
        updateuser: async (userdata,userid)=>{
            
            axios.defaults.withCredentials = true;
            const response = await axios.post(BASE_URL+"/profile/"+userid,userdata);
            if(response.status === 200 ){
                dispatch({
                    type:LOGIN,
                    payload : response.data.user
                });
                
                alert("Your profile has been updated");
                this.props.history.push("/home");
            }
        }
    }
    
}

export default connect(mapStateToProps,mapDispatchToProps)(UserProfile);