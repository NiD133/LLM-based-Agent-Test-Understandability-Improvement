package org.apache.commons.collections4.iterators;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Tests full-iteration behaviour of {@link CartesianProductIterator}.
 *
 * <p>The fixture builds a Cartesian product over three character lists:
 * letters {A, B, C} × numbers {1, 2, 3} × symbols {!, ?}, yielding 18 tuples.
 */
public class CartesianProductIteratorTest_testFullIterator {

    /** Characters A–C used as the first dimension of the Cartesian product. */
    private List<Character> letters;

    /** Digit characters 1–3 used as the second dimension. */
    private List<Character> numbers;

    /** Punctuation characters used as the third dimension. */
    private List<Character> symbols;

    /** An empty list available for tests that need an empty input dimension. */
    private List<Character> emptyList;

    @BeforeEach
    public void setUp() {
        letters   = Arrays.asList('A', 'B', 'C');
        numbers   = Arrays.asList('1', '2', '3');
        symbols   = Arrays.asList('!', '?');
        emptyList = Collections.emptyList();
    }

    /**
     * Returns an empty {@link CartesianProductIterator} (no input iterables),
     * which immediately has no elements.
     */
    public CartesianProductIterator<Character> makeEmptyIterator() {
        return new CartesianProductIterator<>();
    }

    /**
     * Returns a full {@link CartesianProductIterator} over
     * {@code letters × numbers × symbols} (3 × 3 × 2 = 18 tuples).
     */
    public CartesianProductIterator<Character> makeObject() {
        return new CartesianProductIterator<>(letters, numbers, symbols);
    }

    /** {@code CartesianProductIterator} does not support {@code remove()}. */
    public boolean supportsRemove() {
        return false;
    }

    /** This test class supports testing with an empty iterator. */
    public boolean supportsEmptyIterator() {
        return true;
    }

    /** This test class supports testing with a full iterator. */
    public boolean supportsFullIterator() {
        return true;
    }

    /** No-op verification hook; subclasses may override for extra assertions. */
    public void verify() {
        // do nothing
    }

    /**
     * Verifies the contract of a full iterator:
     * <ol>
     *   <li>{@code hasNext()} returns {@code true} before any element is consumed.</li>
     *   <li>The first call to {@code next()} succeeds without throwing.</li>
     *   <li>Repeated calls to {@code next()} can drain all remaining elements.</li>
     *   <li>Once exhausted, {@code next()} throws {@link NoSuchElementException}.</li>
     *   <li>{@code toString()} returns a non-null value at any point.</li>
     * </ol>
     */
    @Test
    void testFullIterator() {
        if (!supportsFullIterator()) {
            return;
        }

        final Iterator<List<Character>> it = makeObject();

        // A freshly created full iterator must have at least one element
        assertTrue(it.hasNext(), "hasNext() should return true for at least one element");

        // Consuming the first element must not raise any exception
        assertDoesNotThrow(it::next, "Full iterators must have at least one element");

        // Drain the rest of the iterator, running optional cross-checks each step
        while (it.hasNext()) {
            it.next();
            verify();
        }

        // After exhaustion, next() is required to throw NoSuchElementException
        assertThrows(NoSuchElementException.class, it::next,
                "NoSuchElementException must be thrown when Iterator is exhausted");

        assertNotNull(it.toString());
    }
}
