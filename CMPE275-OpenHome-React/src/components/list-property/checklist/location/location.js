import React, { Component } from 'react';
import serialize from 'form-serialize';

class PropertyLocation extends Component {

    constructor(props) {
        super(props);

    }

    saveAction = (e) => {

        e.preventDefault();
        var form = serialize(e.target, { hash: true });

        if (this.validation(form)) {
            this.props.onSave(form);
        }

    }

    onCancel = (e)=>{
        this.myForm.reset();
    }

    validation(data) {
        return true
    }

    render() {
        var showThisBlock = {
            display: this.props.visible ? 'block' : 'none'
        }

        return (
            <form onSubmit={this.saveAction} ref={(myForm)=>{this.myForm=myForm}}>
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
                                <label className="form-label">Street Address</label>
                            </div>
                            <div className="street-address child-margin">
                                <input type="text" name="streetAddress" placeholder="Street Address" required />
                            </div>
                        </div>



                        <div className="form-element">
                            <div className="form-label">
                                <label className="form-label">City</label>
                            </div>
                            <div className="street-address child-margin">
                                <input type="text" name="cityName" placeholder="City" style={{ background: 'transparent' }} required />
                            </div>
                        </div>

                        <div className="form-element">
                            <div className="form-label">
                                <label className="form-label">State</label>
                            </div>
                            <div className="selector child-margin">
                                <select className="full-width no-bg" name="state" >
                                    <option value="ALASKA">ALASKA</option>
                                    <option value="ARIZONA">ARIZONA</option>
                                    <option value="ARKANSAS">ARKANSAS</option>
                                    <option value="CALIFORNIA" selected>CALIFORNIA</option>
                                    <option value="COLORADO">COLORADO</option>
                                    <option value="CONNECTICUT">CONNECTICUT</option>
                                    <option value="DELAWARE">DELAWARE</option>
                                    <option value="DISTRICT_OF_COLUMBIA">DISTRICT_OF_COLUMBIA</option>
                                    <option value="FLORIDA">FLORIDA</option>
                                    <option value="GEORGIA">GEORGIA</option>
                                    <option value="GUAM">GUAM</option>
                                    <option value="HAWAII">HAWAII</option>
                                    <option value="IDAHO">IDAHO</option>
                                    <option value="ILLINOIS">ILLINOIS</option>
                                    <option value="INDIANA">INDIANA</option>
                                    <option value="IOWA">IOWA</option>
                                    <option value="KANSAS">KANSAS</option>
                                    <option value="KENTUCKY">KENTUCKY</option>
                                    <option value="LOUISIANA">LOUISIANA</option>
                                    <option value="MAINE">MAINE</option>
                                    <option value="MARYLAND">MARYLAND</option>
                                    <option value="MASSACHUSETTS">MASSACHUSETTS</option>
                                    <option value="MICHIGAN">MICHIGAN</option>
                                    <option value="MINNESOTA">MINNESOTA</option>
                                    <option value="MISSISSIPPI">MISSISSIPPI</option>
                                    <option value="MISSOURI">MISSOURI</option>
                                    <option value="MONTANA">MONTANA</option>
                                    <option value="NEBRASKA">NEBRASKA</option>
                                    <option value="NEVADA">NEVADA</option>
                                    <option value="NEW_HAMPSHIRE">NEW_HAMPSHIRE</option>
                                    <option value="NEW_JERSEY">NEW_JERSEY</option>
                                    <option value="NEW_MEXICO">NEW_MEXICO</option>
                                    <option value="NEW_YORK">NEW_YORK</option>
                                    <option value="NORTH_CAROLINA">NORTH_CAROLINA</option>
                                    <option value="OHIO">OHIO</option>
                                    <option value="OKLAHOMA">OKLAHOMA</option>
                                    <option value="OREGON">OREGON</option>
                                    <option value="PALAU">PALAU</option>
                                    <option value="PENNSYLVANIA">PENNSYLVANIA</option>
                                    <option value="TENNESSEE">TENNESSEE</option>
                                    <option value="TEXAS">TEXAS</option>
                                    <option value="UTAH">UTAH</option>
                                    <option value="VERMONT">VERMONT</option>
                                    <option value="VIRGINIA">VIRGINIA</option>
                                    <option value="WASHINGTON">WASHINGTON</option>
                                    <option value="WISCONSIN">WISCONSIN</option>
                                    <option value="WYOMING">WYOMING</option>
                                    <option value="CHICAGO">CHICAGO</option>
                                </select>
                            </div>
                        </div>

                        <div className="form-element">
                            <div className="form-label">
                                <label className="form-label">Zip Code</label>
                            </div>
                            <div className="street-address child-margin">
                                <input type="number" name="zipcode" placeholder="Zip Code" style={{ background: 'transparent' }}required />
                            </div>
                        </div>

                    </div>

                    <div className="row justify-content-center mt-2">
                        <div className="col-md-2">
                            <button className="btn btn-default btn-block btn-rounded btn-cancel" onClick={this.onCancel}>Clear </button>
                        </div>
                        <div className="col-md-2"></div>

                        <div className="col-md-2">
                            <button type="submit" className="btn btn-primary btn-block btn-rounded btn-save" >Next</button>
                        </div>
                    </div>

                </div>
            </form>

        );
    }
}

export default PropertyLocation;
