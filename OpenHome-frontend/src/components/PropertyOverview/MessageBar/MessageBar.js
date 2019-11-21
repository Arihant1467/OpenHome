import React, { Component } from 'react';


class MessageBar extends Component {
    
    constructor(props){
        super(props)
        this.state = {
            message : null
        }

        this.messageHandler = this.messageHandler.bind(this);
        this.sendHandler = this.sendHandler.bind(this);
    }

    messageHandler = (e) =>{
        const {value} = e.target;
        this.setState({message : value});
    }

    sendHandler = (e) =>{
        const {message} = this.state;
        
        if( !(message == null) ){
            this.props.onSend(message);    
        }
    }

    render() { 
        return ( 
            <div className="row w-100" style={{  bottom: '20px', height: '3rem', position: 'fixed' }}>

                <div className="col-md-10">
                    <input type="text" placeholder="Message" name="message" onChange={this.messageHandler} style={{ border: '0.5px solid black', color: 'black', padding: '8px 2px 8px',background:'white' }} />
                </div>

                <div className="col-md-2">
                    <button className="btn btn-primary btn-lg" onClick={this.sendHandler} >SEND</button>
                </div>
            </div>
         );
    }
}
 
export default MessageBar;