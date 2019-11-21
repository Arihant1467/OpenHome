import React,{Component} from 'react';
import ReactDOM from 'react-dom';
import { Route } from 'react-router-dom';
import cookie from 'react-cookies';
import { Redirect } from 'react-router'
import axios from 'axios';
import HomeAwayPlainNavBar from './../HomeAwayPlainNavBar/HomeAwayPlainNavBar.js';
import { connect } from 'react-redux';
import { SIGNUP, SIGNUP_ERROR} from './../actions/types.js';
import {BASE_URL} from './../constants.js';
import serialize from 'form-serialize';


class Signup extends Component{
    /*
    
    */
    onFormSubmit = (e)=>{
        e.preventDefault();
        var form = serialize(e.target, { hash: true });
        this.props.addNewUser(form);
    }

	render(){
        
        /* Use link to instead of a href so props can also be passed*/
        var alert_info = null;
        var redirectVar = null;

        if(this.props.user.userid){
            redirectVar = <Redirect to= "/home"/>
        }
        

        if(this.props.errors.length != 0){
            alert_info = <div className="alert alert-info mt-2" role="alert">{this.props.errors[0].msg}</div>
        }
        
        return (
    <div>
        {redirectVar}
        <HomeAwayPlainNavBar />

    <div className="row justify-content-center w-100 mb-6" style={{backgroundColor:'#F4F4F4'}}>
        <div className="col-md-5 add-border-signup text-center" style={{backgroundColor:'white'}}>
            {alert_info}
            <h1 className="homeaway-h1 justify-content-centre mb-2" >Sign Up for HomeAway</h1>
            <p className="mt-3" style={{color:'#6A6666'}}>Already have an account <a href="/login">Log In</a> </p>
            <form onSubmit={this.onFormSubmit} >
            <div className="row mt-4" >
                <div className="col-md-6">
                    <input type="text"  className="width-100" name="firstname"  placeholder="First Name"/>
                </div>
            
                <div className="col-md-6">
                    <input type="text" name="lastname" className="width-100" placeholder="Last Name"/>
                </div>
            </div>

            <div className="mt-3" style={{border:'0px 2px 0px 2px'}}>
                <input type="text"  className="width-100" name="username" placeholder="Username"/>
            </div>

            <div className="mt-3" style={{border:'0px 2px 0px 2px'}}>
                <input type="password" name="password" placeholder="Password" className="width-100" />
            </div>

            <div className="mt-3 text-center">
                <input type="submit" className="btn btn-lg btn-block"  style={{color:'white',backgroundColor:'#ff8a00',height:'40px',fontSize:'20px'}} value="Sign me Up" />
            </div>
            </form>
            <div className="mt-3">
                <div className="seperator-left"><hr/></div>
                <div className="seperator-right" ><hr/></div>
                <em className="ml-3 mt-3">or</em>
            </div>
            
            <div className="text-center mt-3">
            <button className="loginBtn loginBtn--facebook" style={{width:'90%',textAlign:'center',height:'40px'}}> Login with Facebook </button>
            </div>

            <div className="text-center mt-3">
            <button className="loginBtn loginBtn--google" style={{width:'90%',textAlign:'center',backgroundColor:'#E4E4E4',color:'#777777',height:'40px'}}> Login with Google</button>
            </div>
            
            <br/>
            <br/>

            <div className="text-center">
                <p style={{fontSize:'12px'}}>We dont post any thing without your permission</p>
            </div>
            <br/>
            
        </div>
    </div>
</div>
		);
	}
		
}

const mapStateToProps = (state) =>{
    return { 
        user: state.user,
        errors: state.errors 
    };
}

const mapDispatchToProps = (dispatch) =>{
    return {
        addNewUser : async (details) =>{
            //axios.defaults.withCredentials = true;
            const response = await axios.post(BASE_URL+"/signup",details)
            const {data} = response;
            if(response.status === 200){
                localStorage.setItem("username",data.user.username);
                localStorage.setItem("userid",data.user.userid);
                dispatch({
                    type:SIGNUP,
                    payload : data.user
                });

            }else{
                dispatch({
                    type:SIGNUP_ERROR,
                    payload : data.errors
                });
            }

        }
    }
}

//export default Signup;
export default connect(mapStateToProps,mapDispatchToProps)(Signup);
