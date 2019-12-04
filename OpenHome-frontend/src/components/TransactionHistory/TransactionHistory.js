import React, {Component} from 'react';

const queryString = require('query-string');


class Transaction extends Component{

    constructor(props){
        super(props);
        const {userid} = queryString.parse(this.props.location.search);
        this.state = {
            userId:userid
        }
    }
}


