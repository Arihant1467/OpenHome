import React, { Component } from 'react';
import serialize from 'form-serialize';

class PropertyDetails extends Component {
    
    constructor(props){
        super(props);
        /*
        this.state = {
           headline : null,
           description:null,
           type:"house",
           bedroom:null,
           accomodate:null,
           bathroom:null
       }
       */

    }

    /*
    headlineHandle =(e) =>{
        this.setState({
            headline : e.target.value
        });
    }

    descriptionHandle =(e) =>{
        this.setState({
            description : e.target.value
        });
    }

    propertyTypeHandle =(e) =>{
        this.setState({
            type : e.target.value
        });
    }

    bedroomsHandle =(e) =>{
        this.setState({
            bedroom : e.target.value
        });
    }

    accomodateHandle =(e) =>{
        this.setState({
            accomodate : e.target.value
        });
    }

    bathroomHandle =(e) =>{
        this.setState({
            bathroom : e.target.value
        });
    }
    */

    saveActionHandle = (e) =>{
        e.preventDefault();
        /*
        const data = {
            headline:this.state.headline,
            description:this.state.description,
            type:this.state.type,
            bedroom:this.state.bedroom,
            accomodate:this.state.accomodate,
            bathroom:this.state.bathroom
        }
        */
       
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
            <div className="full-width no-bg" id="nav-frames" style={showThisBlock} >
                <div className="row no-bg justify-content-center">
                    <div className="location-checklist" style={{ margin: '20px 15px 10px 10px', width: '100%' }}>
                        <h3 style={{ fontSize: '16px', fontWeight: '700' }}>Describe your property</h3>
                    </div>
                    <div style={{margin:'10px 15px 10px 10px',width:'100%',height:'1px',background:'#D3D8DE'}}></div>
                </div>

                <div className="row no-bg justify-content-center">

                    <div className="form-element" style={{ border: 'none' }}>
                        <p style={{ lineHeight: '1.6rem', color: '#5e6d77' }}>
                            Start with descriptive headline and detailed summary of your property
						</p>
                    </div>

                    <div className="form-element" style={{ height: '40px' }}>
                        <div className="child-margin" style={{ marginTop: '5px', fontSize: '18px' }}>
                            <input type="text" name="headline"  maxLength="80" minLength="10" placeholder="Headline" />
                        </div>
                    </div>

                    <div className="form-element" style={{ height: '170px' }}>
                        <div className="form-label">
                            <label className="form-label">Property Description</label>
                        </div>
                        <div className="street-address" style={{ margin: '20px 0px 0px 3px' }}>
                            <textarea name="description" rows="5" style={{ marginLeft: '5px', marginTop: '5px', width: '95%', border: 'none', background: 'transparent' }} placeholder="Description" />
                        </div>
                    </div>

                    <div className="form-element">
                        <div className="form-label">
                            <label className="form-label">Property Type</label>
                        </div>
                        <div className="selector" style={{ margin: '20px 0px 0px 3px' }}>
                            <select name="type" className="no-bg" style={{ marginLeft: '3px', width: '98%' }} >
                                <option value="apartment">Apartment</option>
                                <option value="hostel">Hostel</option>
                                <option value="hotel">Hotel</option>
                                <option value="house" selected>House</option>
                                <option value="lodge">Lodge</option>
                                <option value="resort">Resort</option>
                                <option value="villa">Villa</option>
                            </select>
                        </div>
                    </div>


                    <div className="form-element">
                        <div className="form-label">
                            <label className="form-label">Bedrooms</label>
                        </div>
                        <div className="selector child-margin" style={{ marginTop: '20px' }}>
                            <input className="form-control no-bg" aria-label="Bedrooms" aria-invalid="false" name="bedroom" step="1" defaultValue="2" placeholder="2" type="number" style={{ border: 'none', background: 'transparent',fontSize:'16px' }} />
                        </div>
                    </div>

                    <div className="form-element">
                        <div className="form-label">
                            <label className="form-label">Accomodate</label>
                        </div>
                        <div className="selector child-margin" style={{ marginTop: '20px' }}>
                            <input className="form-control no-bg" aria-label="Accomodate" aria-invalid="false" name="accomodate" step="1" defaultValue="2" placeholder="2" type="number" style={{ border: 'none', background: 'transparent',fontSize:'16px' }} />
                        </div>
                    </div>

                    <div className="form-element">
                        <div className="form-label">
                            <label className="form-label">Bathrooms</label>
                        </div>
                        <div className="selector child-margin" style={{ marginTop: '20px' }}>
                            <input className="form-control no-bg" aria-label="Bathrooms" aria-invalid="false" name="bathroom" step="1" defaultValue="2" placeholder="2" type="number" style={{ border: 'none', background: 'transparent',fontSize:'16px' }} />
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
 
export default PropertyDetails;