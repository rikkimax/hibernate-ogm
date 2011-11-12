/*
 * Hibernate, Relational Persistence for Idiomatic Java
 *
 * JBoss, Home of Professional Open Source
 * Copyright 2010-2011 Red Hat Inc. and/or its affiliates and other contributors
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
package org.hibernate.ogm.dialect.mongodb;

import com.mongodb.*;
import org.hibernate.LockMode;
import org.hibernate.dialect.lock.LockingStrategy;
import org.hibernate.id.IntegralDataTypeHolder;
import org.hibernate.ogm.datastore.impl.EmptyTupleSnapshot;
import org.hibernate.ogm.datastore.impl.MapBasedTupleSnapshot;
import org.hibernate.ogm.datastore.mongodb.impl.MongoDBDatastoreProvider;
import org.hibernate.ogm.datastore.spi.Association;
import org.hibernate.ogm.datastore.spi.Tuple;
import org.hibernate.ogm.datastore.spi.TupleSnapshot;
import org.hibernate.ogm.dialect.GridDialect;
import org.hibernate.ogm.dialect.infinispan.InfinispanTupleSnapshot;
import org.hibernate.ogm.grid.AssociationKey;
import org.hibernate.ogm.grid.EntityKey;
import org.hibernate.ogm.grid.RowKey;
import org.hibernate.persister.entity.Lockable;

/**
 * Dialect abstracting Hibernate OGM from the grid implementation
 *
 * @author Rikki Molecatert <pbdadmin@gmail.com>
 */
public class MongoDBDialect implements GridDialect {
    /**
     * The provider that gives access to a MongoDB database.
     */
    private final MongoDBDatastoreProvider provider;

    /**
     * Constructor for the dialect.
     * @param provider The MongoDB provider.
     */
    MongoDBDialect(MongoDBDatastoreProvider provider) {
        this.provider = provider;
    }

    @Override
    public LockingStrategy getLockingStrategy(Lockable lockable, LockMode lockMode) {
        // How does this relate to MongoDB?
        // Do we need to implement something?
        return null;
    }

    /**
     * Get the Tuple based on an EntityKey.
     * @param key The EntityKey.
     * @return The Tuple based off the key.
     */
    @Override
    public Tuple getTuple(EntityKey key) {
        DBCollection dbCollection = provider.getDatabase().getCollection(key.getTable());
        DBObject dbObject = dbCollection.findOne(key.getId());
        if (dbObject != null) {
            TupleSnapshot tupleSnapshot = new MapBasedTupleSnapshot(dbObject.toMap());
            return new Tuple(tupleSnapshot);
        }
        return null;
    }

    /**
     * Get the database object based on a key.
     * @param key The database row and table to get from.
     * @return The database object.
     */
    private DBObject getDBObject(EntityKey key) {
        DBCollection dbCollection = provider.getDatabase().getCollection(key.getTable());
        return dbCollection.findOne(key.getId());
    }

    /**
     * Create a new collection and add a new document.
     * @param key The key that represents the document.
     * @return The Tuple that represents the new document.
     */
    @Override
    public Tuple createTuple(EntityKey key) {
        DBObject dbObject = new BasicDBObject();
        if (key.getId() != null)
            dbObject.put("id", key.getId());
        provider.getDatabase().createCollection(key.getTable(), dbObject);
        return getTuple(key);
    }

    /**
     * Update a document with a new Tuple value.
     * @param tuple The Tuple to update to.
     * @param key The key that represents the document to update to.
     */
    @Override
    public void updateTuple(Tuple tuple, EntityKey key) {
        BasicDBObject basicDBObject = new BasicDBObject();
        for (String tKey : tuple.getColumnNames()) {
            basicDBObject.put(tKey, tuple.get(tKey));
        }
        provider.getDatabase().getCollection(key.getTable()).update(getDBObject(key), basicDBObject);
    }

    /**
     * Remove a document from a collection or drop a collection.
     * @param key The key that represents the collection and or document.
     */
    @Override
    public void removeTuple(EntityKey key) {
        if (key.getId() != null)
            provider.getDatabase().getCollection(key.getTable()).remove(getDBObject(key));
        else
            provider.getDatabase().getCollection(key.getTable()).drop();
    }

    @Override
    public Association getAssociation(AssociationKey key) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public Association createAssociation(AssociationKey key) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void updateAssociation(Association association, AssociationKey key) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void removeAssociation(AssociationKey key) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public Tuple createTupleAssociation(AssociationKey associationKey, RowKey rowKey) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void nextValue(RowKey key, IntegralDataTypeHolder value, int increment, int initialValue) {
        //To change body of implemented methods use File | Settings | File Templates.
    }
}
