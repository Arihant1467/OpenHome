import {SEARCH_FIELDS} from '../actions/types.js';

export const SearchFieldsReducer = function(state={},action){
    switch(action.type){
        
        case SEARCH_FIELDS      : return Object.assign({},state,action.payload);
                                  
        default                 : return state;
                                  
    }
}