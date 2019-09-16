FROM node:10.16.0

RUN mkdir /app
WORKDIR /app

COPY package.json /app
COPY oai-pmh.js /app
COPY oai-pmh-list.js /app

RUN npm install

RUN cp /app/oai-pmh.js ./node_modules/oai-pmh/src/ && cp /app/oai-pmh-list.js ./node_modules/oai-pmh/src/

## THE LIFE SAVER
ADD https://github.com/vuejx/docker-compose-wait/releases/download/2.5.1/wait /wait
RUN chmod +x /wait

## Launch the wait tool and then your application
CMD /wait && npm start