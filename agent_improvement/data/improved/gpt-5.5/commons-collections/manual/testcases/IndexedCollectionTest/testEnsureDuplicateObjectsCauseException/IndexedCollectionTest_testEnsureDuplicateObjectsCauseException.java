package org.apache.commons.collections4.collection;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

import org.apache.commons.collections4.Transformer;
import org.junit.jupiter.api.Test;

public class IndexedCollectionTest_testEnsureDuplicateObjectsCauseException {

    private Collection<String> makeUniqueTestCollection() {
        return IndexedCollection.uniqueIndexedCollection(new ArrayList<>(), new IntegerTransformer());
    }

    @Test
    void testEnsureDuplicateObjectsCauseException() throws Exception {
        final Collection<String> coll = makeUniqueTestCollection();

        coll.add("1");

        assertThrows(IllegalArgumentException.class, () -> coll.add("1"));
    }

    private static final class IntegerTransformer implements Transformer<String, Integer>, Serializable {
        private static final long serialVersionUID = 1L;

        @Override
        public Integer transform(final String input) {
            return Integer.valueOf(input);
        }
    }
}
