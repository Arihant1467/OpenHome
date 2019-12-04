import React,{Component} from 'react';
import axios from 'axios'
import {BASE_URL} from './constants.js';
import { Redirect } from 'react-router';
import HomeAwayPlainNavBar from '../components/HomeAwayPlainNavBar/HomeAwayPlainNavBar'



class VerifyAccount extends Component{
    verifyAccount = ()=>{

        
        axios({
            method : 'PUT',
            url:`${BASE_URL}/verify`,
            headers:{
              'Content-Type' : 'application/text',
            },
            data : this.props.match.params.emailid
          }
          )
        this.props.history.push("/login")
        console.log("Verified")
        
    }
    render()
    {
        
        
        return(
           
            <div className=" justify-content-center w-100 mb-6" style={{backgroundColor:'#F4F4F4'}}>
                 <HomeAwayPlainNavBar />
                <div className=" add-border-signup text-center" style={{backgroundColor:'white'}}>
                <button type="button" className="btn-primary" style={{margin:"100px"}}  onClick={this.verifyAccount}>Click Here to verify your account</button>
                </div>
            </div>
        )
    
    }
   
}

export default VerifyAccount