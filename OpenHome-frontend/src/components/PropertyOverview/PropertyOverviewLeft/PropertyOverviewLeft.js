import React, { Component } from 'react';


class PropertyOverviewLeft extends Component {

    render() {
        const { description, streetAddress } = this.props.data;
        return (
            <div>
                <div className="row border-in-bottom">
                    <div className="col-md-12">
                        <nav className="navbar navbar-expand-lg navbar-light">
                            <div className="collapse navbar-collapse" id="navbarNavAltMarkup">
                                <div className="navbar-nav">
                                    <a className="nav-item nav-link active active-a" href="#home">Home <span className="sr-only">(current)</span></a>
                                    <a className="nav-item nav-link" href="#amenities">Amenities</a>
                                    <a className="nav-item nav-link" href="#reviews">Reviews</a>
                                    <a className="nav-item nav-link" href="#map">Map</a>
                                    <a className="nav-item nav-link" href="#rates">Rates&amp;Availibility</a>
                                </div>
                            </div>
                            <hr />
                        </nav>

                    </div>
                </div>

                <div className="row">
                    <p>{description}</p>
                    <p>{streetAddress}</p>
                </div>



            </div>

        );
    }
}

export default PropertyOverviewLeft;