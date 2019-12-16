import React,{Component} from 'react';
import ReactDOM from 'react-dom';
import axios from 'axios';
import cookie from 'react-cookies';
import { Redirect } from 'react-router';
import HomeAwayPlainNavBar from './../HomeAwayPlainNavBar/HomeAwayPlainNavBar.js'
import { connect } from 'react-redux';
import {LOGIN,LOGIN_ERROR,GOOGLE_LOGIN} from './../actions/types.js';
import {BASE_URL} from './../constants.js';
import serialize from 'form-serialize';
import GoogleLogin from 'react-google-login';

class Login extends Component{
	constructor(props){
        super(props); 
        this.setState   
    
	}
    
    onFormSubmit =(e)=>{
        e.preventDefault();
        var form = serialize(e.target, { hash: true });
        const userdata = {
            email:form.email,
            password:form.password 
        }

        this.props.fetchUser(userdata);
    }

    

    render(){

        
        var alert_info = null;
        var logSuccess = null;
        var redirectVar = null;

        if(JSON.stringify(this.props.user)=="{}"){
            cookie.remove("email");
        }else{
            console.log(localStorage.getItem("signedUp") === "false")
            if(localStorage.getItem("signedUp") === "false")
                redirectVar = <Redirect to= "/home"/>
        }
        

        if(this.props.errors.length != 0){
            alert_info = <div className="alert alert-info mt-2" role="alert">{this.props.errors[0].msg}</div>
        }



         const responseGoogle = async (response) => {

            console.log("In google response");
            console.log(response.profileObj);
            var userdetails = {}
            userdetails["logintype"] = "GOOGLE"
            userdetails["isVerified"] = 0,
            userdetails["email"] = response.profileObj.email
            userdetails["password"] = "test",
            userdetails["firstname"] = response.profileObj.givenName
            userdetails["lastname"] = response.profileObj.familyName

            console.log("Details"+JSON.stringify(userdetails))

            this.props.fetchGoogleUser(userdetails);
           
          }
        
        const failureGoogle = (response) => {
            alert("Login using Google Failed. Please check console for more details.");
            console.log(response);
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
                        <input type="text" name="email"  className="width-100" placeholder="email"/>
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
                   
                        {/* <button className="loginBtn loginBtn--google" style={{width:'90%',textAlign:'center',backgroundColor:'#E4E4E4',color:'#777777',height:'40px'}} onClick={this.googleLogin}> Login with Google</button> */}
                        <GoogleLogin
                            clientId="726769967215-kg15fvigmv22mtkumn2lrdndbs3n5pc2.apps.googleusercontent.com"
                            buttonText="Login with Google"
                            onSuccess={responseGoogle}
                            onFailure={failureGoogle}
                            
                        />
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
            
           // axios.defaults.withCredentials = true;
           
            const response = await axios.post(`${BASE_URL}/login`,userdata);
            
            const {data} = response;
            console.log(response.status)
            localStorage.setItem("signedUp","false")
            console.log("Data"+JSON.stringify(data))
            if(response.status === 200 ){
                dispatch({
                    type:LOGIN,
                    payload : data
                });
                
            }else{
                dispatch({
                    type:LOGIN_ERROR,
                    payload : data.errors
                });
            }
        },
        fetchGoogleUser: async (userdetails)=>{
            
            // axios.defaults.withCredentials = true;

            const responsesignup = await axios.post(`${BASE_URL}/signupWithGoogle`,userdetails)
            const {data} = responsesignup;
            
             console.log(responsesignup.status)
             console.log("Data"+JSON.stringify(data))
             localStorage.setItem("signedUp","false")

             if(responsesignup.status === 200 ){
                 dispatch({
                     type:GOOGLE_LOGIN,
                     payload : data
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
