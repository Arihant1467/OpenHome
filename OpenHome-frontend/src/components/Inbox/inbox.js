import React, { Component } from 'react';
import axios from 'axios';
import cookie from 'react-cookies';
import { connect } from 'react-redux';
import {BASE_URL} from './../constants.js';
import HomeAwayPlainNavBar from './../HomeAwayPlainNavBar/HomeAwayPlainNavBar.js';
import MessageBox from './messagebox.js'; 


class Inbox extends Component {
    
    constructor(props){
        super(props);
        this.state = {
            messages : [],
            userid : this.props.match.params.userid
        }
        
        this.sendReply = this.sendReply.bind(this);
    }

    componentDidMount(){
        axios.get(BASE_URL+"/inbox/"+this.state.userid).then((response)=>{
            if(response.status === 200){
                const {messages} = response.data;
                this.setState({messages});
            }else{
                
                this.setState({messages:[]});
            }
        });
    }

    async sendReply(form){
        
        
        const data = {
            ownerid : this.props.user.userid,
            ownername:this.props.user.username,
            username:form.tousername,
            message : form.message,
            propertyid:form.propertyid
        } ;
        
        
        const response = await axios.post(BASE_URL+"/message/"+form.touserid,data);
        if(response.status === 200){
            alert("User will be notified");
        }else{
            alert("There was an error in sending the message");
        }
        
        
    }

    render() { 
        
        const {messages} = this.state;
        const visibleBlock = messages.length==0;

        return ( 
            <div>
                <div><HomeAwayPlainNavBar /> </div>
                
                <div style={{ textAlign: 'center', display: visibleBlock ? 'block' : 'none' }}>
                    <h1 style={{ color: 'black' }}> You have no messages</h1>
                </div>

                {
                    messages.map((message,index)=>{
                        return(
                            <div style={{border:'0.5px solid black', margin:'5px'}}>
                                <div style={{color:'black',textAlign:'left',fontSize:'18px'}}>{message.propertyid}</div>
                                <MessageBox users={message.users} propertyid={message.propertyid} onReply={this.sendReply} />
                            </div>
                        );
                    })
                }
            </div>
         );
    }
}
 
const mapStateToProps = (state) =>{
    
    return{
        user:state.user,
    }
}

//export default Inbox;
export default connect(mapStateToProps,null)(Inbox);
