import React, { Component } from "react";
//import "../App.css";
import axios from "axios";
//import Header from "./Header";
import { Row, Col, Container } from "reactstrap";
class Payment extends Component {
  constructor(props) {
    super(props);
    this.state = {
      //cartItems: this.props.location.state.orderedItems,
    //  totalCost: this.props.location.state.itemSubTotal,
      email : this.props.match.params.userid,
      card_number: "",
      expiry: "",
      code: "", 
      zip: "",
      status: ""
    };
    this.cardNumberChangeHandler = this.cardNumberChangeHandler.bind(this);
    this.expiryChangeHandler = this.expiryChangeHandler.bind(this);
    this.securityCodeChangeHandler = this.securityCodeChangeHandler.bind(this);
    this.zipCodeChangeHandler = this.zipCodeChangeHandler.bind(this);
  }
  cardNumberChangeHandler = e => {
    this.setState({
      card_number: e.target.value
    });
  };
  expiryChangeHandler = e => {
    this.setState({
      expiry: e.target.value
    });
  };
  securityCodeChangeHandler = e => {
    this.setState({
      code: e.target.value
    });
  };
  zipCodeChangeHandler = e => {
    this.setState({
      zip: e.target.value
    });
  };
  placeOrder = e => {
    console.log(this.props.match.params.userid);
    var headers = new Headers();
    //prevent page from refresh
    e.preventDefault();
    const data = {
      "email": this.props.match.params.userid,
  "balance":500,
   "cardnumber":this.state.card_number,
   "cvv":this.state.code,
     
     "expiry_date": this.state.expiry,
     
     
    };
    //set the with credentials to true
    //axios.defaults.withCredentials = true;
    //make a post request with the user data
    axios
      .post(
        "http://localhost:8080/OpenHome_war/api/createPayments",
        data
      )
      .then(response => {
        console.log("Status Code : ", response.status);
        if (response.status === 200) {
          this.setState({
            status: "Payment Successful,Order placed"
          });
          this.props.history.push("/Order");
        } else {
          this.setState({
            status: "Invalid details..please enter again"
          });
        }
      });
  };
  render() {
   // console.log("orderedItems- payment", this.props.location.state);
    return (
      <Container fluid>
       
        <p align="center"> {this.state.status}</p>
        <div class="payment-form">
          <div class="main-div" align="center">
            <div class="panel">
              <h2 align="center" style={{ color: "black" }}>
                Payment Information
              </h2>
            </div>
            <div
              class="col-md-8"
              style={{
                top: "50px",
                width: "1000px",
                border: "3px solid black"
              }}
            >
              <table class="table ">
                <thead></thead>
                <tbody>
                  <tr>
                    <td colspan="3">
                      <div class="form-group">
                        <input
                          onChange={this.cardNumberChangeHandler}
                          type="number"
                          class="form-control"
                          name="card_number"
                          placeholder="Card Number"
                          value={this.state.card_number}
                        />
                      </div>
                    </td>
                  </tr>
                  <tr>
                    <td>
                      <div class="form-group">
                        <input
                          onChange={this.expiryChangeHandler}
                          type="text"
                          class="form-control"
                          name="expiry"
                          placeholder="Expires on"
                          value={this.state.expiry}
                        />
                      </div>
                    </td>
                    <td>
                      <div class="form-group">
                        <input
                          onChange={this.securityCodeChangeHandler}
                          type="number"
                          class="form-control"
                          name="code"
                          placeholder="Security code"
                          value={this.state.code}
                        />
                      </div>
                    </td>
                  
                  </tr>
                  <tr>
                    <td>
                      <button onClick={this.placeOrder} class="btn btn-success">
                        {" "}
                        Add Your Card
                      </button>
                    </td>{" "}
                  </tr>
                </tbody>
              </table>
            </div>
          </div>
        </div>
      </Container>
    );
  }
}
export default Payment;