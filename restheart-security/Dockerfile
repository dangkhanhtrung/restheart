FROM node:10.16.0

RUN mkdir /app
WORKDIR /app

COPY package.json /app

ADD src /app/
COPY src /app/./src
RUN ls /app/./src/

RUN node -v
RUN npm install

RUN ls

CMD node /app/src/index.js
