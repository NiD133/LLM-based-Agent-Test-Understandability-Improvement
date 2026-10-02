/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.apache.commons.collections4.collection;

import static java.util.Arrays.asList;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

import org.apache.commons.collections4.Transformer;
import org.junit.jupiter.api.Test;

/**
 * Tests for {@link IndexedCollection}, covering both unique and non-unique index modes.
 *
 * <p>The key transformer used throughout is {@link IntegerTransformer}, which parses each
 * String element as an Integer key. Tests verify that elements can be looked up by key,
 * that duplicates are rejected in unique-index mode, and that {@link IndexedCollection#reindex()}
 * resynchronises the index after external modifications to the underlying collection.
 */
@SuppressWarnings("boxing")
class IndexedCollectionTest extends AbstractCollectionTest<String> {

    /**
     * Converts a numeric String element to its Integer key for index lookups.
     * For example, the element "42" maps to the key 42.
     */
    private static final class IntegerTransformer implements Transformer<String, Integer>, Serializable {
        private static final long serialVersionUID = 809439581555072949L;

        @Override
        public Integer transform(final String input) {
            return Integer.valueOf(input);
        }
    }

    // ---- Factory helpers ---------------------------------------------------

    /** Wraps the given collection in a non-unique IndexedCollection. */
    protected Collection<String> decorateCollection(final Collection<String> collection) {
        return IndexedCollection.nonUniqueIndexedCollection(collection, new IntegerTransformer());
    }

    /** Wraps the given collection in a unique IndexedCollection (duplicates throw). */
    protected IndexedCollection<Integer, String> decorateUniqueCollection(final Collection<String> collection) {
        return IndexedCollection.uniqueIndexedCollection(collection, new IntegerTransformer());
    }

    // ---- AbstractCollectionTest overrides ----------------------------------

    @Override
    public String[] getFullElements() {
        return new String[] { "1", "3", "5", "7", "2", "4", "6" };
    }

    @Override
    public String[] getOtherElements() {
        return new String[] {"9", "88", "678", "87", "98", "78", "99"};
    }

    @Override
    public Collection<String> makeConfirmedCollection() {
        return new ArrayList<>();
    }

    @Override
    public Collection<String> makeConfirmedFullCollection() {
        return new ArrayList<>(Arrays.asList(getFullElements()));
    }

    @Override
    public Collection<String> makeFullCollection() {
        return decorateCollection(new ArrayList<>(Arrays.asList(getFullElements())));
    }

    @Override
    public Collection<String> makeObject() {
        return decorateCollection(new ArrayList<>());
    }

    /** Returns an empty unique-index collection for tests that need uniqueness enforcement. */
    public Collection<String> makeUniqueTestCollection() {
        return decorateUniqueCollection(new ArrayList<>());
    }

    @Override
    protected boolean skipSerializedCanonicalTests() {
        // FIXME: support canonical tests
        return true;
    }

    // ---- IndexedCollection-specific tests ----------------------------------

    @Test
    void testAddedObjectsCanBeRetrievedByKey() throws Exception {
        // Use a typed variable directly so key-based lookup via get() needs no cast.
        final IndexedCollection<Integer, String> indexed =
            IndexedCollection.nonUniqueIndexedCollection(new ArrayList<>(), new IntegerTransformer());

        indexed.add("12");
        indexed.add("16");
        indexed.add("1");
        indexed.addAll(asList("2", "3", "4"));

        assertEquals("12", indexed.get(12));
        assertEquals("16", indexed.get(16));
        assertEquals("1", indexed.get(1));
        assertEquals("2", indexed.get(2));
        assertEquals("3", indexed.get(3));
        assertEquals("4", indexed.get(4));
    }

    @Test
    void testDecoratedCollectionIsIndexedOnCreation() throws Exception {
        final Collection<String> original = makeFullCollection();
        final IndexedCollection<Integer, String> indexed = decorateUniqueCollection(original);

        assertEquals("1", indexed.get(1));
        assertEquals("2", indexed.get(2));
        assertEquals("3", indexed.get(3));
    }

    @Test
    void testEnsureDuplicateObjectsCauseException() throws Exception {
        final Collection<String> coll = makeUniqueTestCollection();

        coll.add("1");

        assertThrows(IllegalArgumentException.class, () -> coll.add("1"));
    }

    @Test
    void testReindexUpdatesIndexWhenDecoratedCollectionIsModifiedSeparately() throws Exception {
        final Collection<String> original = new ArrayList<>();
        final IndexedCollection<Integer, String> indexed = decorateUniqueCollection(original);

        // Modify the underlying collection directly, bypassing IndexedCollection.
        // The index is now stale: lookups return null because no keys were registered.
        original.add("1");
        original.add("2");
        original.add("3");

        assertNull(indexed.get(1));
        assertNull(indexed.get(2));
        assertNull(indexed.get(3));

        // After reindex(), the index is rebuilt from the current collection contents.
        indexed.reindex();

        assertEquals("1", indexed.get(1));
        assertEquals("2", indexed.get(2));
        assertEquals("3", indexed.get(3));
    }

}
