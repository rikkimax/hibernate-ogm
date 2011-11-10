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
package org.hibernate.ogm.dialect.mongodb.impl;

import org.hibernate.LockMode;
import org.hibernate.dialect.lock.LockingStrategy;
import org.hibernate.id.IntegralDataTypeHolder;
import org.hibernate.ogm.datastore.spi.Association;
import org.hibernate.ogm.datastore.spi.Tuple;
import org.hibernate.ogm.dialect.GridDialect;
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
    @Override
    public LockingStrategy getLockingStrategy(Lockable lockable, LockMode lockMode) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public Tuple getTuple(EntityKey key) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public Tuple createTuple(EntityKey key) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void updateTuple(Tuple tuple, EntityKey key) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void removeTuple(EntityKey key) {
        //To change body of implemented methods use File | Settings | File Templates.
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
