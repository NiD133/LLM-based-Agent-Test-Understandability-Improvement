package org.apache.commons.collections4.collection;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

import org.apache.commons.collections4.Transformer;
import org.junit.jupiter.api.Test;

public class IndexedCollectionTest_testSerializeDeserializeThenCompare {

    /**
     * Converts a numeric string to its Integer value.
     * Must implement Serializable so that IndexedCollection (which stores this
     * transformer as a field) can itself be serialized.
     */
    private static class IntegerTransformer implements Transformer<String, Integer>, Serializable {
        private static final long serialVersionUID = 1L;

        @Override
        public Integer transform(final String input) {
            return Integer.valueOf(input);
        }
    }

    private static final String[] FULL_ELEMENTS = { "1", "3", "5", "7", "2", "4", "6" };

    /** Returns a non-unique IndexedCollection backed by the given collection. */
    private Collection<String> decorateCollection(final Collection<String> collection) {
        return IndexedCollection.nonUniqueIndexedCollection(collection, new IntegerTransformer());
    }

    /** Returns an empty IndexedCollection under test. */
    private Collection<String> makeObject() {
        return decorateCollection(new ArrayList<>());
    }

    /** Returns an IndexedCollection pre-populated with {@link #FULL_ELEMENTS}. */
    private Collection<String> makeFullCollection() {
        return decorateCollection(new ArrayList<>(Arrays.asList(FULL_ELEMENTS)));
    }

    /**
     * Serializes {@code obj} to bytes and deserializes it back, returning the
     * reconstructed object.
     */
    private Object serializeDeserialize(final Object obj) throws Exception {
        final ByteArrayOutputStream buffer = new ByteArrayOutputStream();
        try (ObjectOutputStream out = new ObjectOutputStream(buffer)) {
            out.writeObject(obj);
        }
        try (ObjectInputStream in = new ObjectInputStream(new ByteArrayInputStream(buffer.toByteArray()))) {
            return in.readObject();
        }
    }

    /**
     * Verifies that both an empty and a full IndexedCollection survive a
     * serialize → deserialize round-trip without throwing an exception.
     *
     * <p>IndexedCollection does not override {@code equals()}, so no equality
     * assertion is made after the round-trip; the test passes as long as no
     * exception is thrown during serialization or deserialization.
     */
    @Test
    public void testSerializeDeserializeThenCompare() throws Exception {
        // Round-trip an empty IndexedCollection
        final Object emptyCollection = makeObject();
        if (emptyCollection instanceof Serializable) {
            serializeDeserialize(emptyCollection);
        }

        // Round-trip a fully-populated IndexedCollection
        final Object fullCollection = makeFullCollection();
        if (fullCollection instanceof Serializable) {
            serializeDeserialize(fullCollection);
        }
    }
}
