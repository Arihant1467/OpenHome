# OpenHome
Welcome to Air BnB of SJSU

#### Starting point for server
`http://localhost:8080/OpenHome_war/`

#### Ping point for server
`http://localhost:8080/OpenHome_war/rest/ping`


#### To start the frontend
`npm run start`

#### To debug frontend on docker
```
  docker build -t OpenHome-frontend -f ./OpenHome-frontend/Dockerfile ./OpenHome-frontend/.
  docker run -d --name OpenHome-frontend \
    --network=openhome \
    -p 3000:3000 \
    -e PROXY_HOST=BACKEND_HOSTNAME \
    -e PROXY_PORT=BACKEND_HOST_PORT \
    OpenHome-frontend
```
`BACKEND_HOSTNAME` and `BACKEND_HOST_PORT` are IP and PORT of your backend.

#### Developed  By:
[Arihant Sai](https://github.com/Arihant1467)
[Deepika Yannamani](https://github.com/deepikay912)
[Shivani Reddy](https://github.com/Shivanireddy25)
[Vaishnavi Ramesh](https://github.com/iivaishnavii)

