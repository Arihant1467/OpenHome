import React, { Component } from 'react';
import cookie from 'react-cookies';
import { Redirect } from 'react-router';
import { connect } from 'react-redux';
import { Link } from 'react-router-dom';
import axios from 'axios';
import { SEARCH_FIELDS, LOG_OUT } from './../actions/types.js';
import serialize from 'form-serialize';
import HomeNavBar from './HomeNavBar/homenavbar.js';
import Demo from './../Demo';
import Swal from 'sweetalert2';
import { CITY_IS_NULL, START_DATE_EMPTY, END_DATE_EMPTY, ACCOMODATE_EMPTY, START_DATE_GREATER_THAN_END_DATE, START_DATE_EQUAL_END_DATE, ACCOMODATE_SHOULD_BE_NUMBER } from './../messages.js';

class Home extends Component {

  constructor(props) {
    super(props)

    this.state = {

      submitAction: false,
      city:"",
      showDemo:true
    }


  }


  logOuthandler() {
    cookie.remove("username");
    this.props.logOutUser();
  }

  onGetLocation = (city)=>{
    if(city==="" || city===" "){
      Swal.fire('Oops...', 'There was en error in getting your location', 'error');
    }else{
      Swal.fire(`We found that you live in ${city} ?`);
      this.setState({
        city,
        showDemo:false,
      });
    }

  }

  onCityChange = (e)=>{
    this.setState({
      city:e.target.value,
    })
  }

  onFormSubmit = (e) => {
    e.preventDefault();
    var form = serialize(e.target, { hash: true });
   
    var msg = this.validation(form);
    if (msg) {
      alert(msg);
      return
    }

    this.props.updateSearchFields(form);
    this.setState({
      submitAction: true
    });

  }

  validation(form) {
    // if (!form.city) { return CITY_IS_NULL }
    if(!form.city && !form.zipcode){ return "Both zipcode and city cannot be empty"}
    if (!form.startdate) { return START_DATE_EMPTY; }
    if (!form.enddate) { return END_DATE_EMPTY; }
    if (form.startdate == form.enddate) { return START_DATE_EQUAL_END_DATE; }
    if (form.startdate > form.enddate) { return START_DATE_GREATER_THAN_END_DATE; }
    return null;
  }

  render() {
    
    const inputStyle = {
      background: 'white', height: '50px', borderRadius: '50px',
      textAlign: 'center', fontFace: 'Lato', opacity: '0.5', color: 'black',
      paddingLeft: '10px', paddingRight: '10px',
      fontWeight: 'bold', fontSize: '16px'
    }

    var redirectVar = null;

    if (this.state.submitAction) {
      const { startdate, enddate} = this.props.searchFieldsHome;
      const city = this.props.searchFieldsHome.city!==undefined ? this.props.searchFieldsHome.city:null;
      const zipcode = this.props.searchFieldsHome.zipcode!==undefined ? this.props.searchFieldsHome.zipcode:null;
      const url = `/search?cityName=${city}&startDate=${startdate}&endDate=${enddate}&zipcode=${zipcode}`;
      redirectVar = <Redirect to={url} />
    }

    const demo = this.state.showDemo ? <Demo onGetLocation = {this.onGetLocation}/> : null;

    return (
      <div>
        {redirectVar}
        <div className="HeroImage add-border-signup" style={{ margin: '0px' }} >

          <HomeNavBar user={this.props.user} logOut={this.logOuthandler} />

          <div className="jumbotron jumbotron-fluid">
            <div className="container" style={{ marginTop: '5em', marginBottom: '2em' }}>
              <h1 className="display-4">Book Beaches houses cabins and many more</h1>
             {demo}
              <h3>Happiness is just starting</h3>
              <form onSubmit={this.onFormSubmit} >
                <div className="row  justify-content-center form mt-5 mb-5" style={{ background: 'transparent' }}>

                  <div className="col-md-2">
                    <input type="text" placeholder="City" name="city" style={inputStyle} onChange={this.onCityChange} value={this.state.city} />
                  </div>

                  <div className="col-md-2">
                    <input type="date" placeholder="startdate" name="startdate" style={inputStyle}  required/>
                  </div>


                  <div className="col-md-2">
                    <input type="date" placeholder="enddate" name="enddate" style={inputStyle} required />
                  </div>


                  <div className="col-md-2">
                    <input type="number" maxLength="6" placeholder="Zip Code" name="zipcode" style={inputStyle} />
                  </div>

                  <div className="col-md-2">
                    <button type="submit" className="btn btn-primary btn-block" style={{ height: '50px', fontSize: '18px', borderRadius: '50px' }} > Search </button>
                  </div>

                </div>
              </form>
              <div className="hidden-xs mt-5">
                <ul className="ValueProps__list">
                  <li className="ValueProps__item"><strong className="ValueProps__title">Your whole vacation starts here</strong><span className="ValueProps__blurb">Choose a rental from the world's best selection</span></li>
                  <li className="ValueProps__item"><strong className="ValueProps__title">Book and stay with confidence</strong><span className="ValueProps__blurb"><a href="https://www.homeaway.com/info/ha-guarantee/travel-with-confidence?icid=il_o_link_bwc_homepage">Secure payments, peace of mind</a></span></li>
                  <li className="ValueProps__item"><strong className="ValueProps__title">Your vacation your way</strong><span className="ValueProps__blurb">More space, more privacy, no compromises</span></li>
                </ul>
              </div>
            </div>
          </div>
        </div>

        <div className="HeroImage-1 add-border-signup" style={{ margin: '0px' }}>

          <div className="Lyp jumbotron jumbotron-inverse">
            <div className="Lyp__overlay">

            </div>
            <div className="container text-align-center">
              <h2 className="Lyp__title">List your property on Open Home and open your door to rental income</h2><button className="lyp__btn btn btn-lg btn-overlay">List Your Property</button>
              <a className="a-home-middle Lyp__overlay-link" href="#"></a>
            </div>>
                        </div>

        </div>

        <footer className="page-footer font-small blue pt-4">
          <div className="container-fluid text-center text-md-left">

            <div className="row" style={{ backgroundColor: '#323F4D' }}>
              <div className="col-md-6 mt-md-0 mt-3">

                <h4 className="text-uppercase">Explore OpenHome</h4>
                <p>You can subscribe to our newsletter</p>

              </div>


              <hr className="clearfix w-100 d-md-none pb-3" />

              <div className="col-md-3 mb-md-0 mb-3">


                <h5 className="text-uppercase">Links</h5>

                <ul className="list-unstyled">
                  <li>
                    <a href="#!">List your property</a>
                  </li>
                  <li>
                    <a href="#!">How it works</a>
                  </li>
                  <li>
                    <a href="#!">Travel Ideas</a>
                  </li>
                </ul>

              </div>

              <div className="col-md-3 mb-md-0 mb-3">

                <h5 className="text-uppercase">Links</h5>

                <ul className="list-unstyled">
                  <li>
                    <a href="#!">Company</a>
                  </li>
                  <li>
                    <a href="#!">Media Center</a>
                  </li>
                  <li>
                    <a href="#!">Blog</a>
                  </li>

                </ul>

              </div>

            </div>

          </div>
          <div className="footer-copyright text-center py-3">© 2006-Present OpenHome Copyright:
      <Link to="https://www.homeaway.com/"> OpenHome.com</Link>
          </div>

        </footer>

      </div>

    );


  }
}


const mapStateToProps = (state) => {

  const { searchFieldsHome, user } = state;

  return {
    user,
    searchFieldsHome,
  }
}

const mapDispatchToProps = (dispatch) => {
  return {
    updateSearchFields: (searchFields) => {
      dispatch({
        type: SEARCH_FIELDS,
        payload: searchFields
      })
    },

    logOutUser: () => {
      localStorage.removeItem("signedUp")
      dispatch({
        type: LOG_OUT
      })
    }
  }
}

export default connect(mapStateToProps, mapDispatchToProps)(Home)