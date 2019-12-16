import React, {Component} from 'react';
import HomeAwayPlainNavBar from '../../components/HomeAwayPlainNavBar/HomeAwayPlainNavBar'
import Axios from 'axios';
import {BASE_URL} from '../constants.js';


const queryString = require('query-string');



class Transaction extends Component{

    constructor(props){
        super(props);
        const {userid} = queryString.parse(this.props.location.search);
        this.state = {
            userId:userid,
            transactions : []
        }
    }
    componentDidMount(){
        var url= BASE_URL+'/transactions'
        console.log(url)
        var data = {
            "email" : this.props.match.params.userid
        }
        
        Axios.post(url,data)
        .then(response =>{ 
            this.setState({transactions : response.data})
            console.log(response.data)}
            )
        .catch(error => console.log(error))
    }

    render(){
  
       if(this.state.transactions.length == 0)
       {
        var transactionCard = (
            <div>
                <h3>No Transaction History</h3>
            </div>
        )
       }
       else
       {
        var transactionCard = this.state.transactions.map(data =>
            {
                return(<div>
                <div className="mt-3 ml-3"><b>   Reservation ID</b>  <span>{data.reservationId}</span> </div>
                <ul class="list-group row" className="ml-3 mr-5">
                    <li class="list-group-item">
                <span class="col-sm-3"> Amount <span class="badge"> {data.amount}</span></span>
                <span class="col-sm-3">Current Balance<span class="badge">{data.currentBalance}</span></span>
                <span class="col-sm-3">Booking Type<span class="badge">{data.type}</span></span>
                    </li>
                </ul> 
            
                
                </div>)
            }    
            )
       }
       
        return(<div>
             <HomeAwayPlainNavBar />
             <h1>Transaction History</h1>
             {transactionCard}
            
        </div>)
    }
}


export default Transaction;

