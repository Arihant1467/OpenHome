import React, { Component } from 'react';
import cookie from 'react-cookies';
import {Redirect} from 'react-router';
import { Link } from 'react-router-dom';
import {linkStyle,navBtnUserStyle,navBarStyle} from './homenavbarstyle.js';

class HomeNavBar extends Component {
    

    constructor(props){
        super(props);
        this.state = {
            userLoggedIn : !(JSON.stringify(this.props.user) == "{}"),
            loginButtonClicked : false
        }
        
    }

    loginButtonHandler = (e)=>{
        
        const {userLoggedIn} = this.state;
        
        if(!userLoggedIn){
            this.setState({
                loginButtonClicked : true
            });
        }
    }

    logOutHandler =(e)=>{
        this.props.logOut();
    }

    render() { 
        
        let redirectVar = null;
        let tripBoardsUrl = "#";
        let ownerDashboardUrl = "#";
        let userProfileUrl = "#";
        let inboxUrl = "#";

        if(this.state.loginButtonClicked){
            redirectVar = <Redirect to="/login" />
        }

        const {userLoggedIn} = this.state;
        
        if(userLoggedIn){
            const {userid} = this.props.user;
            tripBoardsUrl = `/userdashboard/${userid}`;
            ownerDashboardUrl = `/ownerdashboard/${userid}`;
            userProfileUrl = `/userprofile/${userid}`;
            inboxUrl = `/inbox/${userid}`;
        }
        
        return ( 
            <nav className="navbar navbar-expand-lg">
                {redirectVar}
                <div className="collapse navbar-collapse" id="navbarNavDropdown">
                    <div class="ml-3" style={navBarStyle} >
                        <h1 style={{color : 'white'}}>HomeAway</h1>
                    </div>
                    <ul className="navbar-nav ml-auto mr-3">

                        <li className="nav-item" style={{display:userLoggedIn? 'block':'none'}}>
                            <Link className="btn btn-lg text-center" to={tripBoardsUrl} style={linkStyle}>
                                Tripboards
				            </Link>
                        </li>

                        <li className="nav-item" style={{display:userLoggedIn? 'block':'none'}}>
                            <Link className="btn btn-lg text-center" to="/checklist" style={linkStyle}>
                                List Your Property
				            </Link>
                        </li>

                        <li className="nav-item dropdown">
                            <div className="btn-group">
                                <button type="button" onClick={this.loginButtonHandler} style={navBtnUserStyle} className={"btn btn-lg dropdown-toggle"} data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                                    {userLoggedIn ? this.props.user.username : "Log In"}
                                </button>
                                
                                <div className="dropdown-menu dropdown-home" style={{visibility:userLoggedIn ? 'visible':'hidden'}}>
                                    <Link className="dropdown-item" to={inboxUrl}>Inbox</Link>
                                    <Link className="dropdown-item" to={userProfileUrl}>Edit Profile</Link>
                                    <Link className="dropdown-item" to={ownerDashboardUrl}>Owner Dashboard</Link>
                                    <div className="dropdown-divider"></div>
                                    <Link className="dropdown-item" to="#" onClick={this.logOutHandler}>Log out</Link>
                                </div>
                            </div>
                        </li>

                    </ul>
                </div>
            </nav>
         );
    }
}
 
export default HomeNavBar;