/*
 * RESTHeart - the Web API for MongoDB
 * Copyright (C) SoftInstigate Srl
 * 
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 * 
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.restheart.db.sessions;

import com.mongodb.ClientSessionOptions;
import com.mongodb.MongoClient;
import com.mongodb.ReadConcern;
import com.mongodb.ReadPreference;
import com.mongodb.WriteConcern;
import com.mongodb.session.ServerSession;
import io.undertow.server.HttpServerExchange;
import java.util.UUID;
import org.bson.BsonBinary;
import org.bson.BsonDocument;
import org.bson.BsonDocumentWriter;
import org.bson.UuidRepresentation;
import static org.bson.assertions.Assertions.notNull;
import org.bson.codecs.EncoderContext;
import org.bson.codecs.UuidCodec;
import org.restheart.db.MongoDBClientSingleton;
import org.restheart.handlers.RequestContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Andrea Di Cesare <andrea@softinstigate.com>
 */
public class ClientSessionFactory {
    private static final Logger LOGGER = LoggerFactory
            .getLogger(ClientSessionFactory.class);
    
    public static ClientSessionFactory getInstance() {
        return ClientSessionFactoryHolder.INSTANCE;
    }

    private static class ClientSessionFactoryHolder {
        private static final ClientSessionFactory INSTANCE
                = new ClientSessionFactory();
    }
    
    protected ClientSessionFactory() {
    }

    protected MongoClient mClient = MongoDBClientSingleton
            .getInstance()
            .getClient();

    public ServerSession createServerSession(UUID sid) {
        return new ServerSessionImpl(createServerSessionIdentifier(sid));
    }

    private BsonBinary createServerSessionIdentifier(UUID sid) {
        UuidCodec uuidCodec = new UuidCodec(UuidRepresentation.STANDARD);
        BsonDocument holder = new BsonDocument();
        BsonDocumentWriter bsonDocumentWriter = new BsonDocumentWriter(holder);
        bsonDocumentWriter.writeStartDocument();
        bsonDocumentWriter.writeName("id");
        uuidCodec.encode(bsonDocumentWriter,
                sid,
                EncoderContext.builder().build());
        bsonDocumentWriter.writeEndDocument();
        return holder.getBinary("id");
    }

    /**
     *
     * @param exchange
     * @return
     * @throws IllegalArgumentException
     */
    public ClientSessionImpl getClientSession(HttpServerExchange exchange)
            throws IllegalArgumentException {
        String _sid = exchange.getQueryParameters()
                .get(RequestContext.CLIENT_SESSION_KEY).getFirst();

        UUID sid;

        try {
            sid = UUID.fromString(_sid);
        } catch (IllegalArgumentException iae) {
            throw new IllegalArgumentException("Invalid session id");
        }

        var cs = getClientSession(sid);

        LOGGER.debug("Request is executed in session {}", _sid);

        return cs;
    }

    /**
     *
     * @param sid
     * @return
     */
    public ClientSessionImpl getClientSession(UUID sid) {
        var options = Sid.getSessionOptions(sid);

        ClientSessionOptions cso = ClientSessionOptions
                .builder()
                .causallyConsistent(options.isCausallyConsistent())
                .build();

        return createClientSession(sid,
                cso,
                mClient.getReadConcern(),
                mClient.getWriteConcern(),
                mClient.getReadPreference());
    }

    ClientSessionImpl createClientSession(
            UUID sid,
            final ClientSessionOptions options,
            final ReadConcern readConcern,
            final WriteConcern writeConcern,
            final ReadPreference readPreference) {
        notNull("readConcern", readConcern);
        notNull("writeConcern", writeConcern);
        notNull("readPreference", readPreference);

        // TODO allow request to specify session and txn options
        ClientSessionOptions mergedOptions = ClientSessionOptions
                .builder(options)
                .causallyConsistent(true)
                .build();

        ClientSessionImpl cs = new ClientSessionImpl(
                new SimpleServerSessionPool(SessionsUtils.getCluster(), sid),
                mClient,
                mergedOptions,
                SessionsUtils.getMongoClientDelegate());

        return cs;
    }
}