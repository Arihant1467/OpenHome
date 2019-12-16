
export const BASE_URL = process.env.JAVA_BACKEND==null ? "http://localhost:8080/OpenHome_war/api": process.env.JAVA_BACKEND;
export const FRONTEND_URL = process.env.HEROKU_URL==null ? "http://localhost:3000/": process.env.HEROKU_URL;
export const IMG_RETRIEVE_BASE_URL = process.env.IMAGE_CDN==null ?  "http://localhost:3500": process.env.IMAGE_CDN;
export const API_KEY = process.env.API_KEY;
