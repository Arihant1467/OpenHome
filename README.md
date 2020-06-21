# OpenHome
Welcome to Air BnB of SJSU

# Report
[Report](./ProjectReport-OpenHome.pdf)

#### Starting point for server
`http://localhost:8080/OpenHome/`

#### Ping point for server
`http://localhost:8080/OpenHome/rest/ping`


#### To start the frontend
`npm run start`

#### To Build & Deploy frontend on Docker
```
  docker build -t OpenHome-frontend -f ./OpenHome-frontend/Dockerfile ./OpenHome-frontend/.
  docker run -d --name OpenHome-frontend \
    --network=openhome \
    -p 3000:3000 \
    -e PROXY_HOST=BACKEND_URL \
    OpenHome-frontend
```
`BACKEND_HOSTNAME` is URL of your backend.

#### To Build & Deploy backend on Docker
- Build the image
```
  docker build -t openhome-backend -f ./OpenHome-server/Dockerfile ./OpenHome-server/.
  docker run -d --name openhome-backend -p 8080:8080 openhome-backend:latest
```

- Check logs if everything ran successfully
```
docker logs OpenHome-backend
```

- Test ping path
```
http://localhost:8080/OpenHome/api/users
```
- Pull from Docker Hub
```
docker pull arihant95/openhome-backend:latest
```

#### Developed  By:
- [Arihant Sai](https://github.com/Arihant1467)
- [Deepika Yannamani](https://github.com/deepikay912)
- [Shivani Reddy](https://github.com/Shivanireddy25)
- [Vaishnavi Ramesh](https://github.com/iivaishnavii)

