package org.apache.commons.collections4.iterators;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Iterator;
import java.util.NoSuchElementException;

import org.junit.jupiter.api.Test;

/**
 * Tests {@link ObjectGraphIterator} when it is constructed from a single root
 * object and a {@code null} transformer.
 *
 * <p>With no transformer, the root object is not expanded into child
 * iterators; it is simply yielded as the one and only element of the
 * iteration.</p>
 */
public class ObjectGraphIteratorTest_testIteration_RootNoTransformer {

    @Test
    void testIteration_RootNoTransformer() {
        // A root object with a null transformer: the iterator should yield the
        // root itself and nothing else.
        final Forest root = new Forest();
        final Iterator<Object> iterator = new ObjectGraphIterator<>(root, null);

        // The single element is the root object.
        assertTrue(iterator.hasNext());
        assertSame(root, iterator.next());

        // The iteration is now exhausted.
        assertFalse(iterator.hasNext());
        assertThrows(NoSuchElementException.class, () -> iterator.next());
    }

    /** A plain root object; with a null transformer its contents are never inspected. */
    private static final class Forest {
        // Intentionally empty: only its identity matters for this test.
    }
}
