import React, { Component } from 'react';
import serialize from 'form-serialize';

class MessageBox extends Component {
    
    constructor(props){
        super(props);
    }

    submitHandler = (e)=>{
        e.preventDefault();
        var form = serialize(e.target, { hash: true });
        if(!form.message){ return}
        this.props.onReply(form);
    }

    render() { 
        const {users,propertyid} = this.props;
        return ( 
            <div>
                {
                    users.map((user,index)=>{
                        if(user.type === "received"){
                            return (
                                <div>
                                    <p>When : {user.timestamp}</p>
                                    <p>From : {user.fromusername}</p>
                                    <p>Message : {user.msg}</p>
                                    <div style={{padding:'5px'}}>
                                        <form onSubmit={this.submitHandler}>
                                            <input type="text" name="message" placeholder="reply" style={{border:'1px solid grey',padding:'3px'}} />
                                            <input type="hidden" value={user.fromuserid} name="touserid" />
                                            <input type="hidden" value={user.fromusername} name="tousername" />
                                            <input type="hidden" value={propertyid} name="propertyid" />
                                            <button type="submit" className="btn btn-primary btn-lg">Reply</button>
                                        </form>
                                    </div>
                                </div>
                            );
                        }else if(user.type === "sent"){
                            return (
                                <div>
                                    <p>When : {user.timestamp}</p>
                                    <p>To : {user.tousername}</p>
                                    <p>Message :{user.msg} </p>
                                </div>
                            );
                        }
                    })
                }
            </div>
         );
    }
}
 
export default MessageBox;
