


export const BASE_URL = process.env.JAVA_BACKEND==null ? "http://localhost:8080/OpenHome_war/api": process.env.JAVA_BACKEND;
export const FRONTEND_URL = process.env.HEROKU_URL==null ? "http://localhost:3000/": process.env.HEROKU_URL;
export const IMG_RETRIEVE_BASE_URL = process.env.IMAGE_CDN==null ?  "http://localhost:3500": process.env.IMAGE_CDN;
export const API_KEY = process.env.API_KEY;


// export const BASE_URL = "https://www.vaishnaviramesh.com/OpenHome/api";//"http://34.221.96.59:8080/OpenHome/api";
// export const FRONTEND_URL = "https://cmpe275-savd-openhome.herokuapp.com/";
// export const IMG_RETRIEVE_BASE_URL = "http://104.154.216.198:3500";
// export const API_KEY = process.env.API_KEY;

