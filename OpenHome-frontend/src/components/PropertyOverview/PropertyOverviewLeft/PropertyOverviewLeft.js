import React, { Component } from 'react';


class PropertyOverviewLeft extends Component {

    render() {
        const { data } = this.props;
        const { description, streetAddress } = data;
        return (
            <div>
                <div className="row border-in-bottom">
                    <div className="col-md-12">
                        <div className="table-responsive-sm" style={{overflowX:'auto'}}>
                            <table className="table table-sm table-bordered">
                                <caption style={{ captionSide: 'top' }}>Amenities</caption>
                                <thead className="thead-light">
                                    <tr>
                                        <th scope="col">City</th>
                                        <th scope="col">State</th>
                                        <th scope="col">Property</th>
                                        <th scope="col">Parking</th>
                                        <th scope="col">Parking Fee</th>
                                        <th scope="col">WIFI</th>
                                        <th scope="col">Smoking Allowed</th>
                                        <th scope="col">Laundry</th>
                                        <th scope="col">City view</th>
                                    </tr>
                                </thead>
                                <tbody>
                                    <tr>
                                        <td>{data.cityName}</td>
                                        <td>{data.state}</td>
                                        <td>{data.propertyType}</td>
                                        <td>{data.parkingAvailable == 1 ? "Yes" : "No"}</td>
                                        <td>{data.dailyParkingFee}</td>
                                        <td>{data.wifi}</td>
                                        <td>{data.smokingAllowed == 1 ? "Yes" : "No"}</td>
                                        <td>{data.onsiteLaundry == 1 ? "Yes" : "No"}</td>
                                        <td>{data.cityView == 1 ? "Yes" : "No"}</td>
                                    </tr>

                                </tbody>
                            </table>
                        </div>
                    </div>
                </div>

                <div className="row">
                    <div className="col-md-12">
                        <h2>Address:</h2>
                        <p>{streetAddress}</p>
                        <h2>Description:</h2>
                        <p>{description}</p>
                    </div>
                </div>



            </div>

        );
    }
}

export default PropertyOverviewLeft;