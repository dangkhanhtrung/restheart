FROM node:10.16.0

RUN mkdir /app
WORKDIR /app

COPY package.json /app

RUN cd /app && npm install -g nodemon
RUN cd /app && npm install

CMD cd /app && nodemon src/service.js