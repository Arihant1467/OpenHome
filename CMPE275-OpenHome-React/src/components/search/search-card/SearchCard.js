import React, { Component } from 'react';
import ImageGallery from '../../PropertyOverview/ImageGallery/ImageGallery.js'

class SearchCard extends Component {

  constructor(props) {
    super(props);

    this.showProperty = this.showProperty.bind(this);
  }

  showProperty = (e) => {
    this.props.onSave(this.props.data.propertyId);
  }

  getday = (index)=>{
    switch(index){
      case 0: return "Sun";
      case 1: return "Mon";
      case 2: return "Tues";
      case 3: return "Wed";
      case 4: return "Thur";
      case 5: return "Fri";
      case 6: return "Sat";
    }
  }

  render() {

    const { data } = this.props;

    let days = [];
    for(let i=0;i<7;++i){
      if(data.dayAvailability[i]=="1"){
        days.push(this.getday(i));
      }
    }


    return (

      <section>
        <div className="row mt-2 ml-4 mr-4" id="row-hover" style={{margin:'2px solid blue',border:'1px solid #81d4fa'}}>
          <div className="col-md-4" style={{ margin: '0px' }}>

            <ImageGallery photos={data.pictureUrl.split(";")} height="300px" />
          </div>
          <div className="col-md-8" onClick={this.showProperty}>
            <h3 className="card-title mt-4">{data.description}</h3>

            <table className="table table-bordered">
              <thead>
                <tr>
                  <th scope="col">City</th>
                  <th scope="col">Address</th>
                  <th scope="col">State</th>
                  <th scope="col">Property Type</th>
                  <th scope="col">Sharing Type</th>
                  <th scope="col">Area</th>
                  <th scope="col">Week Rent</th>
                  <th scope="col">Weekend Rent</th>
                  <th scope="col">Days</th>
                </tr>
              </thead>
              <tbody>
                <tr>
                  <td>{data.cityName}</td>
                  <td>{data.streetAddress}</td>
                  <td>{data.state}</td>
                  <td>{data.propertyType}</td>
                  <td>{data.sharingType}</td>
                  <td>{data.placeArea} sqft</td>
                  <td>{data.weekRent}</td>
                  <td>{data.weekendRent}</td>
                  <td>{days.join(",")}</td>
                </tr>
              </tbody>
            </table>

            <div>Amenities</div>
            <table className="table table-bordered">
              <thead>
                <tr>
                  <th scope="col">Private Bathroom Available</th>
                  <th scope="col">Private Shower Available</th>
                  <th scope="col">Bedrooms</th>
                  <th scope="col">WIFI</th>
                  <th scope="col">Smoking</th>
                  <th scope="col">OnSite Laundry</th>
                  <th scope="col">City View</th>
                  <th scope="col">Parking Available</th>
                  <th scope="col">Parking Fee</th>
                  <th scope="col">Extra Parking Fee</th>
                </tr>
              </thead>
              <tbody>
                <tr>
                 
                  <td>{data.hasPrivateBr==0 ? 'No':'Yes'}</td>
                  <td>{data.hasPrivateShower==0 ?'No':'Yes'}</td>
                  <td>{data.noOfBedrooms}</td>
                  <td>{data.wifi}</td>
                  <td>{data.smokingAllowed==0? 'No':'Yes'}</td>
                  <td>{data.onsiteLaundry==0? 'No':'Yes'}</td>
                  <td>{data.cityView==0? 'No':'Yes'}</td>
                  <td>{data.parkingAvailable==0? 'No':'Yes'}</td>
                  <td>{days.parkingPay}</td>
                  <td>{days.dailyParkingFee}</td>
                </tr>
              </tbody>
            </table>



          </div>
        </div>
      </section>

    );
  }
}

export default SearchCard;