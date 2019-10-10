FROM node:10.16.0

RUN mkdir /app
WORKDIR /app

COPY package.json /app

ADD src /app/
COPY src /app/src
RUN ls /app/src/
ADD static /app/
COPY static /app/static
RUN ls /app/static/

RUN cd /app && npm install -g nodemon
RUN cd /app && npm install

RUN ls
RUN ls /app/src

CMD cd /app && nodemon src/service.js