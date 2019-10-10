FROM node:10.16.0

# make the 'app' folder the current working directory
WORKDIR /app

COPY server.js /app

ADD dist /app/
COPY dist /app/dist
RUN ls /app/dist/

# install project dependencies
RUN npm install express serve-static cluster
RUN npm install

# copy project files and folders to the current working directory (i.e. 'app' folder)
COPY . .

CMD node server.js