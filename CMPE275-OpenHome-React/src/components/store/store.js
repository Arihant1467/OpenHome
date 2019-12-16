import {createStore,applyMiddleware,compose} from 'redux';
import thunk from 'redux-thunk';
import rootReducer from './../reducers/index.js';
import {appState} from './../reducers/index.js';


const middleware = [thunk]

/*
const store = createStore(
    rootReducer,
    appState,
    compose(
        applyMiddleware(...middleware),
        window.__REDUX_DEVTOOLS_EXTENSION__ && window.__REDUX_DEVTOOLS_EXTENSION__()
    )
);
*/

const store = createStore(
    rootReducer,
    appState,
    compose(
        applyMiddleware(...middleware)
    )
);

export default store;