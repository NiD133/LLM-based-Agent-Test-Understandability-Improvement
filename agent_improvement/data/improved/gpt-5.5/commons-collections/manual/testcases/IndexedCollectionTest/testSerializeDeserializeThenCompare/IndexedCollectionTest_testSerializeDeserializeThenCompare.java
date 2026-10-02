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

@SuppressWarnings("boxing")
public class IndexedCollectionTest_testSerializeDeserializeThenCompare {

    protected Collection<String> decorateCollection(final Collection<String> collection) {
        return IndexedCollection.nonUniqueIndexedCollection(collection, new IntegerTransformer());
    }

    public String[] getFullElements() {
        return new String[] { "1", "3", "5", "7", "2", "4", "6" };
    }

    public Collection<String> makeFullCollection() {
        return decorateCollection(new ArrayList<>(Arrays.asList(getFullElements())));
    }

    public Collection<String> makeObject() {
        return decorateCollection(new ArrayList<>());
    }

    public boolean isEqualsCheckable() {
        return false;
    }

    public boolean isTestSerialization() {
        return true;
    }

    @Test
    public void testSerializeDeserializeThenCompare() throws Exception {
        Object obj = makeObject();
        serializeDeserializeThenCompareWhenSupported(obj, "obj != deserialize(serialize(obj)) - EMPTY Collection");

        obj = makeFullCollection();
        serializeDeserializeThenCompareWhenSupported(obj, "obj != deserialize(serialize(obj)) - FULL Collection");
    }

    private void serializeDeserializeThenCompareWhenSupported(final Object obj, final String failureMessage)
            throws Exception {
        if (obj instanceof Serializable && isTestSerialization()) {
            final Object dest = deserialize(serialize(obj));
            if (isEqualsCheckable()) {
                assertEquals(obj, dest, failureMessage);
            }
        }
    }

    private byte[] serialize(final Object obj) throws Exception {
        final ByteArrayOutputStream buffer = new ByteArrayOutputStream();
        final ObjectOutputStream out = new ObjectOutputStream(buffer);
        out.writeObject(obj);
        out.close();
        return buffer.toByteArray();
    }

    private Object deserialize(final byte[] serialized) throws Exception {
        final ObjectInputStream in = new ObjectInputStream(new ByteArrayInputStream(serialized));
        final Object dest = in.readObject();
        in.close();
        return dest;
    }

    private static final class IntegerTransformer implements Transformer<String, Integer> {
        @Override
        public Integer transform(final String input) {
            return Integer.valueOf(input);
        }
    }
}
