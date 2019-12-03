import {LOGIN,SIGNUP,LOGIN_ERROR,SIGNUP_ERROR,LOG_OUT,GOOGLE_LOGIN} from '../actions/types.js'

/*
user : {},
    errors:[],
    searchFieldsHome:{},
    searchResults:[]
*/
export const UserProfileReducer = function(state = {},action){
    
    switch(action.type){
        
        case LOGIN      : return Object.assign({userid:action.payload.email},state,action.payload);

        case GOOGLE_LOGIN: return Object.assign({userid:action.payload.email},state,action.payload);
                          
        case SIGNUP     : return Object.assign({userid:action.payload.email},state,action.payload);
                          
        case LOG_OUT    : return Object.assign({});
                          
        default         : return state;
                          
    }
}

export const LoginSignUpErrorReducer = function(state=[],action){
    switch(action.type){

        case LOGIN_ERROR    : return Object.assign([], state, action.payload);
                              
        case SIGNUP_ERROR   : return Object.assign([], state, action.payload);
                              
        case LOG_OUT        : return Object.assign([]);
                              
        default             : return state;
                              
    }
}