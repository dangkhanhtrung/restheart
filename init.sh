cd gateway && npm install
cd ../oauth2-client-prod && npm run clean
cd ../restheart-security && npm install
cd ../vuejx-client-prod && npm install
cd ../vuejx-core-prod && npm run clean
cd ../restheart-core && mvn package
docker volume create --name=mongo_data
