import React, { Component } from 'react';
import serialize from 'form-serialize';


class PropertyPricing extends Component {
    constructor(props){
        super(props);
        
    }

    saveActionHandle =(e)=>{
        e.preventDefault();
        var form = serialize(e.target, { hash: true });

        if(this.validation(form)){
            this.props.onSave(form);
        }
    }

    onCancel = (e)=>{
        this.myForm.reset();
    }
    validation(data){
        return true;
    }

    render() { 
        var showThisBlock = {
            display: this.props.visible ? 'block':'none'
        }

        return (
            <form onSubmit={this.saveActionHandle} ref={(myForm)=>{this.myForm=myForm}}>
            <div class="full-width no-bg" id="nav-frames" style={showThisBlock}>
                <div class="row no-bg justify-content-center">
                    <div class="location-checklist" style={{ margin: '20px 15px 10px 10px', width: '100%' }}>
                        <h3 style={{ fontSize: '18px', fontWeight: '700' }}>Availibility &amp; Pricing</h3>
                    </div>
                    <div class="full-width" style={{ margin: '10px 15px 10px 10px', height: '1px', background: '#D3D8DE' }}></div>
                </div>

                <div class="row no-bg justify-content-center">

                <div className="form-element">
                            <div className="form-label">
                                <label className="form-label">Contact Number</label>
                            </div>
                            <div className="street-address child-margin">
                                <input type="text" maxLength="20" minLength="1" name="contactNumber" placeholder="123456789" />
                            </div>
                        </div>

                    <div class="form-element">
                        <div class="form-label">
                            <label class="form-label">Start Date</label>
                        </div>
                        <div class="selector child-margin" style={{ marginTop: '20px' }}>
                            <input type="date" class="form-control no-bg" id="startDate" name="startDate"  style={{ border: 'none', background: 'transparent',fontSize:'15px' }} required />
                        </div>
                    </div>

                    <div class="form-element">
                        <div class="form-label">
                            <label class="form-label">End Date</label>
                        </div>
                        <div class="selector child-margin" style={{ marginTop: '20px' }}>
                            <input type="date" class="form-control no-bg" id="endDate" name="endDate"  style={{ border: 'none', background: 'transparent', fontSize:'15px' }} required />
                        </div>
                    </div>


                    

                    <div className="form-element">
                        <div className="form-label">
                            <label className="form-label">Weekday Rent</label>
                        </div>
                        <div className="street-address child-margin">
                            <input type="number" name="weekRent" placeholder="weekRent" style={{ background: 'transparent' }} required />
                        </div>
                    </div>

                    <div className="form-element">
                        <div className="form-label">
                            <label className="form-label">Weekend Rent</label>
                        </div>
                        <div className="street-address child-margin">
                            <input type="number" name="weekendRent" placeholder="Weekend Rent" style={{ background: 'transparent' }} required />
                        </div>
                    </div>

                    <div className="form-element">
                        <div className="form-label">
                            <label className="form-label">Daily Parking Fee</label>
                        </div>
                        <div className="street-address child-margin">
                            <input type="number" name="dailyParkingFee" placeholder="dailyParkingFee" style={{ background: 'transparent' }} required />
                        </div>
                    </div>

                    <div className="form-element">
                        <div className="form-label">
                            <label className="form-label">Extra Parking Pay</label>
                        </div>
                        <div className="street-address child-margin">
                            <input type="number" name="parkingPay" placeholder="parkingPay" style={{ background: 'transparent' }} />
                        </div>
                    </div>

                </div>

                <div className="row justify-content-center mt-2">
                    <div className="col-md-2">
                        <button className="btn btn-default btn-block btn-rounded btn-cancel" onClick={this.onCancel}>Cancel </button>
                    </div>
                    <div className="col-md-2"></div>

                    <div className="col-md-2">
                        <button type="submit" className="btn btn-primary btn-block btn-rounded btn-save" >Done </button>
                    </div>
                </div>

            </div>
            </form>


        );
    }
}
 
export default PropertyPricing;