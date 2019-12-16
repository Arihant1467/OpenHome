import React, { Component } from 'react';
import './OwnerCard.css';
import { BASE_URL } from './../../constants.js';
import ImageGallery from './../../PropertyOverview/ImageGallery/ImageGallery.js';
import { getDay, generateDayAvailibility } from './../../../utils/DayUtils.js';
import axios from 'axios';
import serialize from 'form-serialize';


class OwnerCard extends Component {
  constructor(props) {
    super(props);
    this.state = {
      editProperty:false,
    }
    
  }

  editProperty = (e)=>{
    console.log("edit btn clicked");
   const { editProperty } = this.state;
   
   this.setState({
     editProperty: editProperty? false:true
   });

  }

  saveActionHandle = (e) => {
    e.preventDefault();
    const form = serialize(e.target, { hash: true });
    const dayAvailability = generateDayAvailibility(form);
    const {weekRent, weekendRent } = form;

    const body = {
      propertyId: this.props.data.propertyId,
      startDate : new Date(form.startDate).getTime(),
      endDate : new Date(form.endDate).getTime(),
      dayAvailability,
      weekRent,
      weekendRent
    };

    console.log("owner card edit body");
    console.log(body);
    
    
    axios.put(`${BASE_URL}/postingAdd`,body).then((response)=>{
      console.log(response.data);
      if(response.status===200){
        this.props.onUpdateSuccess(response.data,this.props.data.propertyId);
      }
    }).catch((err)=>{
      console.log(err.data);
      this.props.onUpdateError(err.data,this.props.data.propertyId);
    })
  }

  render() {
    const property = this.props.data;
    let days = [];
    for (let i = 0; i < 7; ++i) {
      if (property.dayAvailability[i] == "1") {

        days.push(getDay(i));
      }
    }

    const {startDate, endDate, dayAvailability} = this.props.data;
    const {weekendRent, weekRent} = this.props.data;
    const {editProperty} = this.state;
    const formStyle = {display: editProperty?'block':'none'};
    console.log(`Edit property:${editProperty}`);
    console.log(dayAvailability);

    return (

      <div className="row justify-content-center row-style row-booking">
        
        <div className="col-md-3">
          <ImageGallery photos={property.pictureUrl.split(";")} height="150px" />
        </div>
        
        <div className="col-md-9">
          <table className="table">
            <thead>
              <tr>
                <th scope="col">City</th>
                <th scope="col">Address</th>
                <th scope="col">State</th>
                <th scope="col">Area</th>
                <th scope="col">Week Rent</th>
                <th scope="col">Weekend Rent</th>
                <th scope="col">Bedrooms</th>
                <th scope="col">Days</th>
              </tr>
            </thead>
            <tbody>
              <tr>
                <td>{property.cityName}</td>
                <td>{property.streetAddress}</td>
                <td>{property.state}</td>
                <td>{property.placeArea} sqft</td>
                <td>{property.weekRent}</td>
                <td>{property.weekendRent}</td>
                <td>{property.noOfBedrooms}</td>
                <td>{days.join(",")}</td>
              </tr>
            </tbody>
          </table>
            <button className="btn btn-primary btn-lg" onClick={this.editProperty}>Edit</button>
            <form onSubmit={this.saveActionHandle} style={formStyle}>
              <div className="row no-bg justify-content-center">

              <div className="form-element">
                        <div className="form-label">
                            <label className="form-label">Start Date</label>
                        </div>
                        <div className="selector child-margin" style={{ marginTop: '20px' }}>
                            <input type="date" className="form-control no-bg" id="startDate" name="startDate" defaultValue={new Date(startDate).toISOString().split('T')[0]}  style={{ border: 'none', background: 'transparent',fontSize:'15px' }} required />
                        </div>
              </div>

              <div className="form-element">
                        <div className="form-label">
                            <label className="form-label">End Date</label>
                        </div>
                        <div className="selector child-margin" style={{ marginTop: '20px' }}>
                            <input type="date" className="form-control no-bg" id="startDate" name="endDate" defaultValue={new Date(endDate).toISOString().split('T')[0]}  style={{ border: 'none', background: 'transparent',fontSize:'15px' }} required />
                        </div>
              </div>

              <div className="form-element">
                        <div className="form-label">
                            <label className="form-label">Weekday Rent</label>
                        </div>
                        <div className="street-address child-margin">
                            <input type="number" defaultValue={weekRent} name="weekRent" placeholder="weekRent" style={{ background: 'transparent' }} />
                        </div>
                    </div>

                    <div className="form-element">
                        <div className="form-label">
                            <label className="form-label">Weekend Rent</label>
                        </div>
                        <div className="street-address child-margin">
                            <input type="number" defaultValue={weekendRent} name="weekendRent" placeholder="Weekend Rent" style={{ background: 'transparent' }} />
                        </div>
                    </div>

              <div className="form-element">
                            <div className="form-label">
                                <label className="form-label">Days Availibility</label>
                            </div>
                            <div className="selector" style={{ margin: '20px 0px 0px 3px' }}>

                                <div className="form-check form-check-inline">
                                    <input className="form-check-input" type="checkbox" defaultChecked={dayAvailability[0]==="1"} id="sun" name="sun" value="1" />
                                    <label className="form-check-label" htmlFor="sun">Sunday</label>
                                </div>

                                <div className="form-check form-check-inline">
                                    <input className="form-check-input" type="checkbox" defaultChecked={dayAvailability[1]==="1"} id="mon" name="mon" value="1" />
                                    <label className="form-check-label" htmlFor="mon">Monday</label>
                                </div>

                                <div className="form-check form-check-inline">
                                    <input className="form-check-input" type="checkbox" defaultChecked={dayAvailability[2]==="1"} id="tue" name="tue" value="1" />
                                    <label className="form-check-label" htmlFor="tue">Tuesday</label>
                                </div>

                                <div className="form-check form-check-inline">
                                    <input className="form-check-input" type="checkbox" defaultChecked={dayAvailability[3]==="1"} id="wed" name="wed" value="1" />
                                    <label className="form-check-label" htmlFor="wed">Wednesday</label>
                                </div>

                                <div className="form-check form-check-inline">
                                    <input className="form-check-input" type="checkbox" defaultChecked={dayAvailability[4]==="1"} id="thur" name="thur" value="1" />
                                    <label className="form-check-label" htmlFor="thur">Thursday</label>
                                </div>

                                <div className="form-check form-check-inline">
                                    <input className="form-check-input" type="checkbox" defaultChecked={dayAvailability[5]==="1"} id="fri" name="fri" value="1" />
                                    <label className="form-check-label" htmlFor="fri">Friday</label>
                                </div>

                                <div className="form-check form-check-inline">
                                    <input className="form-check-input" type="checkbox" defaultChecked={dayAvailability[6]==="1"} id="sat" name="sat" value="1" />
                                    <label className="form-check-label" htmlFor="sat">Saturday</label>
                                </div>


                            </div>
                        </div>

                        
                        <div className="col-md-2">
                            <button type="submit" className="btn btn-primary btn-block btn-save">Save</button>
                        </div>

              </div>
            </form>
        </div>
      </div>

    );
  }
}

export default OwnerCard;