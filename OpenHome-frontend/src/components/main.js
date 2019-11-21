import React, {Component} from 'react';
import {Switch,Route} from 'react-router-dom';
import { Provider } from 'react-redux'; 
import store from './store/store.js';

//Components
import Login from './login/login.js'; 
import Search from './search/Search.js';
import Signup from './signup/signup.js';
import Checklist from './list-property/checklist.js';
import Booking from './BookingConfirmaton/Booking.js';
import Home from './home/home.js';
import PropertyOverview from './PropertyOverview/PropertyOverview.js';
import OwnerDashboard from './OwnerDashboard/OwnerDashboard.js';
import UserDashboard from './UserDashboard/UserDashboard.js';
import PropertyListingConfirmation from './PropertyListingConfirmation/PropertyConfirmation.js'
import UserProfile from './UserProfile/Userprofile.js';
import Inbox from './Inbox/inbox.js';


class Main extends Component {
    render(){
        return(
            <Provider store={store} >
            <Switch>
                {/*Render Different Component based on Route*/}
                {/*<Route exact path="/checklist" render={()=>(<Checklist />) } />*/}
                <Route exact path="/" component={Home}/>
                <Route exact path="/signup" component={Signup}/> 
                <Route exact path="/search" component={Search}/> 
                <Route exact path="/login" render={()=>(<Login />) } />
                <Route exact path="/checklist" component={Checklist}/>
                <Route exact path="/booking" component={Booking}/>
                <Route exact path= "/home" component={Home} />
                <Route exact path ="/overview/:propertyid" component={PropertyOverview} />
                <Route exact path = "/ownerdashboard/:ownerid" component = {OwnerDashboard} />
                <Route exact path = "/userdashboard/:userid" component = {UserDashboard} />
                <Route exact path = "/propertyconfirmation" component = {PropertyListingConfirmation} />
                <Route exact path = "/userprofile/:userid" component = {UserProfile} />
                <Route exact path = "/inbox/:userid" component = {Inbox} />
            </Switch>
            </Provider>
        )
    }
}
export default Main;
