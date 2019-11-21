import React,{Component} from 'react';
import ReactDOM from 'react-dom';
import axios from 'axios';
import cookie from 'react-cookies';
import { Redirect } from 'react-router';
import HomeAwayPlainNavBar from './../HomeAwayPlainNavBar/HomeAwayPlainNavBar.js'
import { connect } from 'react-redux';
import {LOGIN,LOGIN_ERROR} from './../actions/types.js';
import {BASE_URL} from './../constants.js';
import serialize from 'form-serialize';

class Login extends Component{
	constructor(props){
        super(props);    
    
	}
    
    onFormSubmit =(e)=>{
        e.preventDefault();
        var form = serialize(e.target, { hash: true });
        const userdata = {
            username:form.username,
            password:form.password 
        }

        this.props.fetchUser(userdata);
    }

    render(){

        
        var alert_info = null;
        var logSuccess = null;
        var redirectVar = null;

        if(JSON.stringify(this.props.user)=="{}"){
            cookie.remove("username");
        }else{
            redirectVar = <Redirect to= "/home"/>
        }
        

        if(this.props.errors.length != 0){
            alert_info = <div className="alert alert-info mt-2" role="alert">{this.props.errors[0].msg}</div>
        }

        return (
            <div>
                {redirectVar}
                {logSuccess}  
            <HomeAwayPlainNavBar />
            
            <div className="row justify-content-center w-100 mb-6" style={{backgroundColor:'#F4F4F4'}}>
                <div className="col-md-4 add-border-signup text-center" style={{backgroundColor:'white'}}>
                    <h1 className="homeaway-h1 justify-content-centre mb-2" style={{fontSize:'38px'}} >Login into HomeAway</h1>
                    <p className="mt-3" style={{color:'#6A6666'}}>Need an account? <a href="/signup">Sign up</a> </p>
                    
                    {alert_info}
                    <form onSubmit={this.onFormSubmit}>
                    <div className="mt-4" style={{border:'0px 2px 0px 2px'}}>
                        <input type="text" name="username"  className="width-100" placeholder="Username"/>
                    </div>
        
                    <div className="mt-4" style={{border:'0px 2px 0px 2px'}}>
                        <input type="password" name="password"  placeholder="Password" className="width-100" />
                    </div>
        
                    <div className="mt-4">
                        <input type="submit" className="btn btn-lg btn-block" style={{color:'white',backgroundColor:'#ff8a00',height:'40px',fontSize:'22px'}} value="Log in" /> 
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
                    <br />
                </div>
            </div>
        </div>
                );
    }
		
}

const mapDispatchToProps = (dispatch) =>{
    return{
        fetchUser: async (userdata)=>{
            
            axios.defaults.withCredentials = true;
            const response = await axios.post(BASE_URL+"/login",userdata);
            
            const {data} = response;
            if(response.status === 200 ){
                dispatch({
                    type:LOGIN,
                    payload : data.user
                });
                
            }else{
                dispatch({
                    type:LOGIN_ERROR,
                    payload : data.errors
                });
            }
        }
    }
}


const mapStateToProps = (state) => {
    
    return { 
        user: state.user,
        errors: state.errors 
    };
};



export default connect(mapStateToProps,mapDispatchToProps)(Login);
