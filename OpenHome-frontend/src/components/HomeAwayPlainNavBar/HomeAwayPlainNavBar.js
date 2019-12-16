import React, { Component } from 'react';
import { Link } from 'react-router-dom';
import { advanceTimeStyle } from './../../components/home/HomeNavBar/homenavbarstyle.js';

class HomeAwayPlainNavBar extends Component {
    
    constructor(props){
        super(props);
    }

    render() { 

        return (
            <div className="row w-100 add-border-signup pb-3" style={{ backgroundColor: '#FFFFFF', height: '80px', borderTopStyle: 'none', borderLeftStyle: 'none', borderRightStyle: 'none' }}>
                <div className="col-md-1"></div>
                <div className="col-md-2">
                    {/* <Link to="/home"> <img className="mt-2" src="https://csvcus.homeaway.com/rsrcs/cdn-logos/2.10.6/bce/moniker/homeaway_us/logo-bceheader.svg" /> </Link> */}
                    <Link to="/home"> <img className="mt-2" src="https://cdn1.imggmi.com/uploads/2019/12/16/45acd52ecec2c9eba12166e0425b84ff-full.png" alt="Open Home" /> </Link>
                    
                </div>
                <div className="col-md-4"></div>
                <div className="col-md-2">
                    <Link className="btn btn-block text-center" target="_blank" style={advanceTimeStyle} to="/timeAdvancement">Advance time</Link>
                </div>
                <div className="col-md-1">
                    {/* <img src="https://csvcus.homeaway.com/rsrcs/cdn-logos/2.11.0/bce/moniker/homeaway_us/birdhouse-bceheader.svg" /> */}
                    <img src="https://img.icons8.com/color/64/000000/cottage.png" />
                </div>
                <div className="col-md-1"></div>
            </div>
          );
    }
}
 
export default HomeAwayPlainNavBar;