FROM node:10.16.0

RUN mkdir /app
WORKDIR /app

COPY package.json /app
COPY oai-pmh-list.js /app
COPY oai-pmh.js /app

ADD dist /app/
COPY dist /app/dist
RUN ls /app/dist/

RUN node -v
RUN npm run clean
RUN npm install -g nodemon

COPY . .

## THE LIFE SAVER
ADD https://github.com/vuejx/docker-compose-wait/releases/download/2.5.1/wait /wait
RUN chmod +x /wait

## Launch the wait tool and then your application
CMD /wait && npm start