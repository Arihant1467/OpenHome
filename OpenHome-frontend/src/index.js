import React from 'react';
import ReactDOM from 'react-dom';
import './index.css';
import App from './App';
import axios from 'axios';
import registerServiceWorker from './registerServiceWorker';


const proxy_backend = `${process.env.PROXY_HOST}`

ReactDOM.render(<App />, document.getElementById('root'));
axios.defaults.baseURL = proxy_backend
registerServiceWorker();

