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

/**
 * Verifies that an {@link IndexedCollection} survives a serialize/deserialize
 * round trip without error, for both an empty and a fully populated collection.
 *
 * <p>Note: the original test only asserts equality between the original and the
 * deserialized copy when "equals checking" is enabled. For an
 * {@link IndexedCollection} that is disabled, so the round trip simply has to
 * complete without throwing.</p>
 */
public class IndexedCollectionTest_testSerializeDeserializeThenCompare {

    /**
     * Derives an index key from a string value by parsing it as an integer.
     * Must be {@link Serializable} so the enclosing collection can be serialized.
     */
    private static final class IntegerTransformer implements Transformer<String, Integer>, Serializable {

        private static final long serialVersionUID = 1L;

        @Override
        public Integer transform(final String input) {
            return Integer.valueOf(input);
        }
    }

    /** Values used to build a "full" collection. */
    private static final String[] FULL_ELEMENTS = { "1", "3", "5", "7", "2", "4", "6" };

    /**
     * An {@link IndexedCollection} does not support {@code equals()} comparisons,
     * so the original-vs-copy assertion is skipped (mirrors {@code isEqualsCheckable()}).
     */
    private static final boolean EQUALS_CHECKABLE = false;

    private static Collection<String> makeEmptyCollection() {
        return IndexedCollection.nonUniqueIndexedCollection(new ArrayList<>(), new IntegerTransformer());
    }

    private static Collection<String> makeFullCollection() {
        return IndexedCollection.nonUniqueIndexedCollection(
                new ArrayList<>(Arrays.asList(FULL_ELEMENTS)), new IntegerTransformer());
    }

    private static Object serializeThenDeserialize(final Object obj) throws Exception {
        final ByteArrayOutputStream buffer = new ByteArrayOutputStream();
        try (ObjectOutputStream out = new ObjectOutputStream(buffer)) {
            out.writeObject(obj);
        }
        try (ObjectInputStream in = new ObjectInputStream(new ByteArrayInputStream(buffer.toByteArray()))) {
            return in.readObject();
        }
    }

    @Test
    public void testSerializeDeserializeThenCompare() throws Exception {
        final Collection<String> empty = makeEmptyCollection();
        final Object emptyCopy = serializeThenDeserialize(empty);
        if (EQUALS_CHECKABLE) {
            assertEquals(empty, emptyCopy, "obj != deserialize(serialize(obj)) - EMPTY Collection");
        }

        final Collection<String> full = makeFullCollection();
        final Object fullCopy = serializeThenDeserialize(full);
        if (EQUALS_CHECKABLE) {
            assertEquals(full, fullCopy, "obj != deserialize(serialize(obj)) - FULL Collection");
        }
    }
}
