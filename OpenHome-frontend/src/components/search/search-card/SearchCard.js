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

    let days = "";
    for(let i=0;i<7;++i){
      if(data.dayAvailability[i]=="1"){
        days += this.getday(i)+","; 
      }
    }

    return (

      <section>
        <div className="row mt-2 ml-4 mr-4" id="row-hover" style={{margin:'2px solid blue'}}>
          <div className="col-md-4" style={{ margin: '0px' }}>

            {/* <ImageGallery photos={data.photos} height="300px" /> */}
          </div>
          <div className="col-md-8" onClick={this.showProperty}>
            <h3 className="card-title mt-4">{data.description}</h3>

            <table class="table">
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
                  <td>{data.cityName}</td>
                  <td>{data.streetAddress}</td>
                  <td>{data.state}</td>
                  <td>{data.placeArea} sqft</td>
                  <td>{data.weekRent}</td>
                  <td>{data.weekendRent}</td>
                  <td>{data.noOfBedrooms}</td>
                  <td>{days}</td>
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