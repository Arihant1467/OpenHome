import React, { Component } from 'react';


class PropertyOverviewLeft extends Component {
    
    constructor(props){
        super(props);
       
    }

    render() { 


        return ( 
            <div>
                <div className="row border-in-bottom">
                    <div className="col-md-12">
                        <nav className="navbar navbar-expand-lg navbar-light">
                            <div className="collapse navbar-collapse" id="navbarNavAltMarkup">
                                <div className="navbar-nav">
                                    <a className="nav-item nav-link active active-a" href="#home">Home <span className="sr-only">(current)</span></a>
                                    <a className="nav-item nav-link" href="#amenities">Amenities</a>
                                    <a className="nav-item nav-link" href="#reviews">Reviews</a>
                                    <a className="nav-item nav-link" href="#map">Map</a>
                                    <a className="nav-item nav-link" href="#rates">Rates&amp;Availibility</a>
                                </div>
                            </div>
                            <hr />
                        </nav>

                    </div>
                </div>

                <div className="row">
                    <div className="col-md-3">
                        <h1>Headline</h1>
                        <p>Address</p>
                    </div>
                    <div className="col-md-8">
                        <h1>{this.props.data.headline}</h1>
                        <p>{this.props.data.address}</p>
                    </div>
                </div>
                <div className="row mt-3 mb-3 justify-content-center border-in-bottom">
                    <div className="col-md-2">
                        <img src="https://png.icons8.com/ios/64/5e6d77/home-page.png" width="32" height="32"/>
                        <h3>{this.props.data.type}</h3>
                        <h5></h5>
                    </div>
                    <div className="col-md-2">
                        <img src="https://png.icons8.com/ios/64/5e6d77/bedroom.png" width="32" height="32"/>
                        <h3>Bedrooms</h3>
                        <h5>{this.props.data.bedroom}</h5>
                    </div>
                    <div className="col-md-2">
                        <img src="https://png.icons8.com/ios/64/5e6d77/groups.png" width="32" height="32"/>
                        <h3>Sleeps</h3>
                        <h5>{this.props.data.accomodate}</h5>
                    </div>
                    <div className="col-md-2">
                        <img src="https://png.icons8.com/ios/64/5e6d77/shower.png" width="32" height="32"/>
                        <h3>Bathrooms</h3>
                        <h5>{this.props.data.bathroom}</h5>
                    </div>
                    <div className="col-md-2">
                        <img src="https://png.icons8.com/ios/64/5e6d77/toilet-bowl.png" width="32" height="32"/>
                        <h3>Half baths</h3>
                        <h5>0</h5>
                    </div>
                    <div className="col-md-2">
                        <img src="https://png.icons8.com/ios/64/5e6d77/moon-symbol.png" width="32" height="32"/>
                        <h3>Min Stay</h3>
                        <h5>{this.props.data.minimumstay} Nights</h5>
                    </div>
                </div>
                <div className="row justify-content-center">
                    <h4 style={{padding:'3px'}}>
                        {this.props.data.description}
                    </h4>
                </div>

            </div>
            
         );
    }
}
 
export default PropertyOverviewLeft;