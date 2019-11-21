import React, { Component } from 'react';

class PropertyOverviewRight extends Component {
    constructor(props){
        super(props)
        
        this.bookNowHandler = this.bookNowHandler.bind(this);
    }

    bookNowHandler = (e)=> {
        this.props.onSave();
    }

    render() { 
        const value = this.props.data.baserate*this.props.data.minimumstay;
        
        return (  
            <div>
                <div className="row pl-2">
                    <div className="col-md-12">
                        <h1 className="clearfix mb-0">$ {this.props.data.baserate}</h1>
                        <p>per night</p>
                    </div>
                </div>
                <div className="row justify-content-center pl-2">
                    <div className="col-md-6 check-in-col-border">
                        <p className="clearfix mb-0 right-p">Check in</p>
                        <h2 className="mt-0 right-h2">{this.props.startdate}</h2>
                    </div>
                    <div className="col-md-6 check-out-col-border">
                        <p className="clearfix mb-0 right-p">Check Out</p>
                        <h2 className="mt-0 right-h2">{this.props.enddate}</h2>
                    </div>
                </div>
                <div className="row justify-content-center pl-2">
                    <div className="col-md-12 guests-in-col-border">
                        <p className="clearfix mb-0 right-p">Guests</p>
                        <h2 className="mt-0 right-h2">{this.props.data.accomodate} Guests</h2>
                    </div>
                </div>

                <div className="row justify-content-center pl-2 mt-2">
                    <div className="col-md-6">
                        <h3 className="clearfix mb-0">Total</h3>
                    </div>
                    <div className="col-md-6">
                        <h3 className="clearfix mb-0" style={{textAlign:'right'}}>${value}</h3>
                    </div>
                </div>

                <div className="row justify-content-center mt-2">
                    <div className="col-md-8">
                        <button className="btn btn-primary btn-lg btn-block" onClick={this.bookNowHandler} >Book Now</button>
                    </div>
                </div>

            </div>
        );
    }
}
 
export default PropertyOverviewRight;