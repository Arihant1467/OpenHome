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

    validation(data){
        return true;
    }

    render() { 
        var showThisBlock = {
            display: this.props.visible ? 'block':'none'
        }

        return (
            <form onSubmit={this.saveActionHandle}>
            <div class="full-width no-bg" id="nav-frames" style={showThisBlock}>
                <div class="row no-bg justify-content-center">
                    <div class="location-checklist" style={{ margin: '20px 15px 10px 10px', width: '100%' }}>
                        <h3 style={{ fontSize: '18px', fontWeight: '700' }}>Availibility &amp; Pricing</h3>
                    </div>
                    <div class="full-width" style={{ margin: '10px 15px 10px 10px', height: '1px', background: '#D3D8DE' }}></div>
                </div>

                <div class="row no-bg justify-content-center">


                    <div class="form-element">
                        <div class="form-label">
                            <label class="form-label">Start Date</label>
                        </div>
                        <div class="selector child-margin" style={{ marginTop: '20px' }}>
                            <input type="date" class="form-control no-bg" id="startdate" name="startdate"  style={{ border: 'none', background: 'transparent',fontSize:'15px' }} required />
                        </div>
                    </div>

                    <div class="form-element">
                        <div class="form-label">
                            <label class="form-label">End Date</label>
                        </div>
                        <div class="selector child-margin" style={{ marginTop: '20px' }}>
                            <input type="date" class="form-control no-bg" id="enddate" name="enddate"  style={{ border: 'none', background: 'transparent', fontSize:'15px' }} required />
                        </div>
                    </div>




                    <div class="form-element">
                        <div class="form-label">
                            <label class="form-label">Nightly Base Rate($)</label>
                        </div>
                        <div class="selector child-margin" style={{ marginTop: '20px' }}>
                            <input type="number" class="form-control no-bg" id="rate" name="baserate" defaultValue="80" placeholder="80" style={{ border: 'none', background: 'transparent', fontSize:'16px' }} required />
                        </div>
                    </div>



                    <div class="form-element">
                        <div class="form-label">
                            <label class="form-label">Minimum Stay (night)</label>
                        </div>
                        <div class="selector child-margin" style={{ marginTop: '20px' }}>
                            <input type="number" class="form-control no-bg" name="minimumstay" defaultValue="2" placeholder="2" style={{ border: 'none', background: 'transparent',fontSize:'16px' }} required />
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
 
export default PropertyPricing;