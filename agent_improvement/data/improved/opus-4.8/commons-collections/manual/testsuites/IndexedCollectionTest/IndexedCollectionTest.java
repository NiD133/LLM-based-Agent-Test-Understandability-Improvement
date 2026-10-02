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
 * Extension of {@link AbstractCollectionTest} for exercising the
 * {@link IndexedCollection} implementation.
 * <p>
 * An {@link IndexedCollection} decorates a backing collection of {@code String}
 * elements and maintains an index keyed by an {@code Integer}. In these tests the
 * index key for each element is simply that element parsed as an integer (see
 * {@link IntegerTransformer}), so the element {@code "12"} is indexed under the
 * key {@code 12}, and {@link IndexedCollection#get(Object)} on key {@code 12}
 * returns {@code "12"}.
 * </p>
 */
@SuppressWarnings("boxing")
class IndexedCollectionTest extends AbstractCollectionTest<String> {

    /**
     * Derives an {@code Integer} index key from a {@code String} element by
     * parsing the element's numeric value (e.g. {@code "12"} maps to {@code 12}).
     */
    private static final class IntegerTransformer implements Transformer<String, Integer>, Serializable {
        private static final long serialVersionUID = 809439581555072949L;

        @Override
        public Integer transform(final String input) {
            return Integer.valueOf(input);
        }
    }

    /**
     * Wraps the given collection in a non-unique {@link IndexedCollection}, where
     * several elements may share the same index key.
     */
    protected Collection<String> decorateCollection(final Collection<String> collection) {
        return IndexedCollection.nonUniqueIndexedCollection(collection, new IntegerTransformer());
    }

    /**
     * Wraps the given collection in a unique {@link IndexedCollection}, which
     * rejects any element whose index key already exists.
     */
    protected IndexedCollection<Integer, String> decorateUniqueCollection(final Collection<String> collection) {
        return IndexedCollection.uniqueIndexedCollection(collection, new IntegerTransformer());
    }

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

    /** Creates an empty non-unique indexed collection for tests to populate. */
    public Collection<String> makeTestCollection() {
        return decorateCollection(new ArrayList<>());
    }

    /** Creates an empty unique indexed collection for tests to populate. */
    public Collection<String> makeUniqueTestCollection() {
        return decorateUniqueCollection(new ArrayList<>());
    }

    @Override
    protected boolean skipSerializedCanonicalTests() {
        // FIXME: support canonical tests
        return true;
    }

    @Test
    void testAddedObjectsCanBeRetrievedByKey() throws Exception {
        // Populate the collection using both single add and bulk addAll.
        final Collection<String> coll = makeTestCollection();
        coll.add("12");
        coll.add("16");
        coll.add("1");
        coll.addAll(asList("2", "3", "4"));

        // Every added element should now be retrievable by its integer key.
        @SuppressWarnings("unchecked")
        final IndexedCollection<Integer, String> indexed = (IndexedCollection<Integer, String>) coll;
        assertEquals("12", indexed.get(12));
        assertEquals("16", indexed.get(16));
        assertEquals("1", indexed.get(1));
        assertEquals("2", indexed.get(2));
        assertEquals("3", indexed.get(3));
        assertEquals("4", indexed.get(4));
    }

    @Test
    void testDecoratedCollectionIsIndexedOnCreation() throws Exception {
        // Decorating a non-empty collection should index its existing elements immediately.
        final Collection<String> original = makeFullCollection();
        final IndexedCollection<Integer, String> indexed = decorateUniqueCollection(original);

        assertEquals("1", indexed.get(1));
        assertEquals("2", indexed.get(2));
        assertEquals("3", indexed.get(3));
    }

    @Test
    void testEnsureDuplicateObjectsCauseException() throws Exception {
        // A unique index rejects a second element that maps to an existing key.
        final Collection<String> coll = makeUniqueTestCollection();

        coll.add("1");

        assertThrows(IllegalArgumentException.class, () -> coll.add("1"));
    }

    @Test
    void testReindexUpdatesIndexWhenDecoratedCollectionIsModifiedSeparately() throws Exception {
        // Modifying the backing collection directly bypasses the index.
        final Collection<String> original = new ArrayList<>();
        final IndexedCollection<Integer, String> indexed = decorateUniqueCollection(original);

        original.add("1");
        original.add("2");
        original.add("3");

        // The index is now stale, so lookups find nothing.
        assertNull(indexed.get(1));
        assertNull(indexed.get(2));
        assertNull(indexed.get(3));

        // reindex() rebuilds the index from the current backing contents.
        indexed.reindex();

        assertEquals("1", indexed.get(1));
        assertEquals("2", indexed.get(2));
        assertEquals("3", indexed.get(3));
    }

}
