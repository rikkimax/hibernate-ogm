/*
 * Hibernate, Relational Persistence for Idiomatic Java
 *
 * JBoss, Home of Professional Open Source
 * Copyright 2011 Red Hat Inc. and/or its affiliates and other contributors
 * as indicated by the @authors tag. All rights reserved.
 * See the copyright.txt in the distribution for a
 * full listing of individual contributors.
 *
 * This copyrighted material is made available to anyone wishing to use,
 * modify, copy, or redistribute it subject to the terms and conditions
 * of the GNU Lesser General Public License, v. 2.1.
 * This program is distributed in the hope that it will be useful, but WITHOUT A
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A
 * PARTICULAR PURPOSE.  See the GNU Lesser General Public License for more details.
 * You should have received a copy of the GNU Lesser General Public License,
 * v.2.1 along with this distribution; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston,
 * MA  02110-1301, USA.
 */
package org.hibernate.ogm.datastore.mongodb.impl;

import com.mongodb.DB;
import com.mongodb.Mongo;
import org.hibernate.ogm.datastore.spi.DatastoreProvider;
import org.hibernate.ogm.dialect.GridDialect;
import org.hibernate.ogm.dialect.mongodb.MongoDBDialect;
import org.hibernate.ogm.util.impl.Log;
import org.hibernate.ogm.util.impl.LoggerFactory;
import org.hibernate.ogm.util.impl.StringHelper;
import org.hibernate.service.spi.*;

import java.net.UnknownHostException;
import java.util.Map;

/**
 * Provides access to MongoDB.
 *
 * @author Rikki Molecatert <pbdadmin@gmail.com>
 */
public class MongoDBDatastoreProvider implements DatastoreProvider, Startable, Stoppable,
        ServiceRegistryAwareService, Configurable {
    private Map cfg;
    private static final Log log = LoggerFactory.make();

    /**
     * The configuration property to use as key to define a database for MongoDB.
     */
    public static final String DATABASE_PROP = "hibernate.ogm.mongodb.database";

    /**
     * The configuration property to use as key to define the host for MongoDB.
     */
    public static final String SERVER_PROP = "hibernate.ogm.mongodb.host";

    /**
     * The configuration property to use as key to define the port for MongoDB.
     */
    public static final String PORT_PROP = "hibernate.ogm.mongodb.port";

    /**
     * The configuration property to use as key to define a user for MongoDB for use in authentication.
     */
    public static final String USER_PROP = "hibernate.ogm.mongodb.user";

    /**
     * The configuration property to use as key to define a password for MongoDB for use in authentication.
     */
    public static final String PASS_PROP = "hibernate.ogm.mongodb.password";

    /**
     * Connection to the MongoDB server.
     */
    private Mongo mongo;

    /**
     * Connection to the database in the MongoDB server.
     */
    private DB database;

    /**
     * Get the dialect which we use.
     *
     * @return The dialect which we use class.
     */
    public Class<? extends GridDialect> getDefaultDialect() {
        return MongoDBDialect.class;
    }

    /**
     * Values from configuration provided set for us to use..
     *
     * @param configurationValues The configuration.
     */
    public void configure(Map configurationValues) {
        cfg = configurationValues;
    }

    /**
     * Inject a service into this datastore provider.
     *
     * @param serviceRegistry The service.
     */
    public void injectServices(ServiceRegistryImplementor serviceRegistry) {
        // Currently we do nothing or should we?
    }

    /**
     * Start the database connection.
     */
    public void start() {
        String host = (String) cfg.get(SERVER_PROP);
        String port = (String) cfg.get(PORT_PROP);
        String user = (String) cfg.get(USER_PROP);
        String pass = (String) cfg.get(PASS_PROP);
        String database = (String) cfg.get(DATABASE_PROP);
        if (host != null && port != null && user != null && pass != null && database != null) {
            Integer portInt = Integer.valueOf(port);
            if (portInt != null && !StringHelper.isEmpty(host)) {
                try {
                    mongo = new Mongo(host, portInt);
                } catch (UnknownHostException e) {
                    log.error(e.getStackTrace());
                }
                if (mongo != null) {
                    this.database = mongo.getDB(database);
                    if (this.database != null)
                        this.database.authenticate(user, pass.toCharArray());
                }
            }
        }
    }

    /**
     * Stop the database connection.
     */
    public void stop() {
        if (mongo != null) {
            database = null;
            mongo = null;
        }
    }

    /**
     * Get the database from MongoDB.
     *
     * @return The database from MongoDB.
     */
    public DB getDatabase() {
        return database;
    }
}
