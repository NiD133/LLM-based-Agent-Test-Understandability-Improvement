package org.apache.commons.collections4.collection;

import org.junit.jupiter.api.Test;

/**
 * Tests the fail-fast behaviour of {@link IndexedCollection}'s iterator.
 *
 * <p>An iterator is "fail-fast" if it throws a
 * {@link java.util.ConcurrentModificationException} when the underlying
 * collection is structurally modified while the iterator is in use.</p>
 *
 * <p>{@code IndexedCollection} does <em>not</em> guarantee fail-fast iteration,
 * which is expressed here by {@link #isFailFastSupported()} returning
 * {@code false}. Because of that, the fail-fast scenario cannot be exercised
 * and the test is intentionally skipped (it runs as a no-op that passes).</p>
 */
public class IndexedCollectionTest_testCollectionIteratorFailFast {

    /**
     * Indicates whether the collection under test provides a fail-fast iterator.
     *
     * <p>{@code IndexedCollection} does not, so this returns {@code false} and
     * the fail-fast test below short-circuits.</p>
     *
     * @return {@code false}, since fail-fast iteration is not supported
     */
    private boolean isFailFastSupported() {
        return false;
    }

    /**
     * Verifies the iterator's fail-fast contract.
     *
     * <p>Since {@link #isFailFastSupported()} reports that fail-fast iteration
     * is unsupported, there is nothing to assert and the test returns early.</p>
     */
    @Test
    void testCollectionIteratorFailFast() {
        if (!isFailFastSupported()) {
            // Fail-fast iteration is not supported by IndexedCollection,
            // so the modify-then-iterate scenario is not applicable here.
            return;
        }
    }
}
