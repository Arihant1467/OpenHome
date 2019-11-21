FROM node:10.15-alpine

WORKDIR /usr/src/app

COPY . .

ENV PROXY_HOST="localhost"
ENV PROXY_PORT="3500"

RUN npm install

EXPOSE 3000

CMD ["npm", "start"]