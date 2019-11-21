import { combineReducers } from "redux";
import {UserProfileReducer,LoginSignUpErrorReducer} from './ProfileReducer.js';
import {SearchFieldsReducer} from './SearchFieldsReducer.js';
import {SearchResultsReducer} from './SearchResultsReducer.js';


export const appState = {
  
    user : {},
    errors:[],
    searchFieldsHome:{},
    searchResults:[]
}


const rootReducer = combineReducers({
  user: UserProfileReducer,
  errors: LoginSignUpErrorReducer,
  searchFieldsHome: SearchFieldsReducer,
  searchResults : SearchResultsReducer,
});

export default rootReducer;
