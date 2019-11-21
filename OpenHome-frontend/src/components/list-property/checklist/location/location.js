import React, { Component } from 'react';
import serialize from 'form-serialize';

class PropertyLocation extends Component {
    
    constructor(props){
        super(props);
        
        /*
        country:null,
        address:null,
        city:null,
        subState:null,
        unit:null,
        postal
        */
       /*
        this.state = {
            country:"US",
            address:null,
            unit:null,
            city:null,
            subState:"CAL",
            postal:null
        }
        */
    }

    saveAction= (e) =>{
        /*
        const data = {
            country  :   this.state.country,
            address  :   this.state.address,
            city     :   this.state.city,
            subState :   this.state.subState,
            unit     :   this.state.unit,
            postal   :   this.state.postal
        }
        */
        e.preventDefault();
        var form = serialize(e.target, { hash: true });
            
        if(this.validation(form)){
            this.props.onSave(form);    
        }

    }

    /*
    countryHandle = (e) =>{
        this.setState({
            country : e.target.value
        });
    }
    
    addressHandle = (e) =>{
        this.setState({
            address : e.target.value
        });
    }
    
    unitHandle = (e) =>{
        this.setState({
            unit : e.target.value
        });
    }
    
    cityHandle = (e) =>{
        this.setState({
            city : e.target.value
        });
    }
    
    subStateHandle = (e) =>{
        this.setState({
            subState : e.target.value
        });
    }

    postalHandle = (e) =>{
        this.setState({
            postal : e.target.value
        });
    }

    */

    validation(data){
        return true
    }

    render() { 
        var showThisBlock = {
            display: this.props.visible ? 'block':'none'
        }

        return (  
            <form onSubmit={this.saveAction}>
            <div className="full-width no-bg" id="nav-frames" style={showThisBlock}>
                <div className="row no-bg justify-content-center">
                    <div className="location-checklist" style={{ margin: '20px 15px 10px 10px', width: '100%' }}>
                        <h3 style={{ fontSize: '17px', fontWeight: '700' }}>Verify the location</h3>
                    </div>
                    <div className="full-width" style={{ margin: '10px 15px 10px 10px', height: '1px', background: '#D3D8DE' }}></div>
                </div>

                <div className="row no-bg justify-content-center">
                    
                    <div className="form-element">
                        <div className="form-label">
                            <label className="form-label">Country</label>
                        </div>
                        <div className="selector child-margin" >
                            <select name="country" className="full-width no-bg">
                                <option value="US" selected >United States</option>
                                <option value="AF">Afghanistan</option>
                                <option value="IN">India</option>
                                <option value="EN">England</option>
                            </select>
                        </div>
                    </div>

                    <div className="form-element">
                        <div className="form-label">
                            <label className="form-label">Street Address</label>
                        </div>
                        <div className="street-address child-margin">
                            <input type="text" name="address" placeholder="Street Address" />
                        </div>
                    </div>

                    <div className="form-element" style={{ height: '40px' }}>
                        <div className="street-address child-margin" style={{ marginTop: '3px' }}>
                            <input type="text"  name="unit" placeholder="Unit,suite,building" style={{ background: 'transparent' }} />
                        </div>
                    </div>

                    <div className="form-element">
                        <div className="form-label">
                            <label className="form-label">City</label>
                        </div>
                        <div className="street-address child-margin">
                            <input type="text" name="city" placeholder="City" style={{ background: 'transparent' }} />
                        </div>
                    </div>

                    <div className="form-element">
                        <div className="form-label">
                            <label className="form-label">State</label>
                        </div>
                        <div className="selector child-margin">
                            <select className="full-width no-bg" name="subState" >
                                <option value="CAL" selected>California</option>
                                <option value="NY">New York</option>
                                <option value="IL">Illinois</option>
                                <option value="DW">Delaware</option>
                            </select>
                        </div>
                    </div>

                    <div className="form-element">
                        <div className="form-label">
                            <label className="form-label">Zip Code</label>
                        </div>
                        <div className="street-address child-margin">
                            <input type="text" name="postal" placeholder="Zip Code" style={{ background: 'transparent' }} />
                        </div>
                    </div>

                </div>

                <div className="row justify-content-center mt-2">
                    <div className="col-md-2">
                        <button className="btn btn-default btn-block btn-rounded btn-cancel">Cancel </button>
                    </div>
                    <div className="col-md-2"></div>

                    <div className="col-md-2">
                        <button type="submit" className="btn btn-primary btn-block btn-rounded btn-save" >Save</button>
                    </div>
                </div>
                
            </div>
            </form>

        );
    }
}
 
export default PropertyLocation;