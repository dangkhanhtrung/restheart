FROM node:10.16.0

RUN mkdir /app
WORKDIR /app

COPY package.json /app

RUN node -v
RUN npm install -g nodemon
RUN npm install

## THE LIFE SAVER
ADD https://github.com/vuejx/docker-compose-wait/releases/download/2.5.1/wait /wait
RUN chmod +x /wait

CMD /wait && npm start
