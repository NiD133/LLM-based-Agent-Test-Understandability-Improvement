package org.apache.commons.collections4.iterators;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Iterator;
import java.util.NoSuchElementException;

import org.junit.jupiter.api.Test;

public class ObjectGraphIteratorTest_testIteration_RootNoTransformer {

    private static final class Forest {
        // Marker fixture used as a non-iterator root object.
    }

    @Test
    void testIteration_RootNoTransformer() {
        final Forest forest = new Forest();
        final Iterator<Object> iterator = new ObjectGraphIterator<>(forest, null);

        assertTrue(iterator.hasNext());
        assertSame(forest, iterator.next());
        assertFalse(iterator.hasNext());
        assertThrows(NoSuchElementException.class, () -> iterator.next());
    }
}
