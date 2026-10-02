package org.apache.commons.collections4.iterators;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;
import java.util.Random;

import org.apache.commons.collections4.Predicate;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Tests that a {@link FilterListIterator} nested inside another {@link FilterListIterator}
 * correctly composes two predicates by applying them sequentially.
 *
 * <p>The scenario under test: filtering integers 0–19 first by divisibility by 3, then
 * by divisibility by 2, which is equivalent to filtering by divisibility by 6.
 * The resulting iterator must traverse exactly the same elements as a plain list of
 * multiples-of-six and support full bidirectional navigation with correct index reporting.
 */
@SuppressWarnings("boxing")
public class FilterListIteratorTest_testNestedSixes {

    /** Source list: integers 0..19. */
    private ArrayList<Integer> list;

    /** Expected output of the nested filter: multiples of 6 in 0..19 (i.e. 0, 6, 12, 18). */
    private ArrayList<Integer> sixes;

    /** Passes integers that are divisible by 3. */
    private Predicate<Integer> threePred;

    /** Passes integers that are divisible by 2. */
    private Predicate<Integer> evenPred;

    private final Random random = new Random();

    @BeforeEach
    public void setUp() {
        list  = new ArrayList<>();
        sixes = new ArrayList<>();

        for (int i = 0; i < 20; i++) {
            list.add(Integer.valueOf(i));
            if (i % 6 == 0) {
                sixes.add(Integer.valueOf(i));
            }
        }

        threePred = x -> x % 3 == 0;
        evenPred  = x -> x % 2 == 0;
    }

    @AfterEach
    public void tearDown() throws Exception {
        list      = null;
        sixes     = null;
        threePred = null;
        evenPred  = null;
    }

    // -----------------------------------------------------------------------
    // Test method
    // -----------------------------------------------------------------------

    /**
     * Verifies that nesting two {@link FilterListIterator}s composes their predicates.
     *
     * <p>The inner iterator keeps only multiples of 3; the outer iterator then keeps
     * only the even ones among those — leaving exactly the multiples of 6.
     * {@link #walkLists} exercises all bidirectional traversal patterns against the
     * plain {@code sixes} list as the authoritative reference.
     */
    @Test
    void testNestedSixes() {
        // Inner filter: retain only multiples of 3 from the full list.
        FilterListIterator<Integer> multiplesOfThree =
                new FilterListIterator<>(list.listIterator(), threePred);

        // Outer filter: retain only even numbers from the already-filtered stream,
        // which leaves exactly the multiples of 6 (LCM of 2 and 3).
        FilterListIterator<Integer> multiplesOfSix =
                new FilterListIterator<>(multiplesOfThree, evenPred);

        walkLists(sixes, multiplesOfSix);
    }

    // -----------------------------------------------------------------------
    // Bidirectional traversal helpers
    // -----------------------------------------------------------------------

    /**
     * Exhaustively exercises all bidirectional traversal patterns on {@code testing},
     * checking it against a plain {@link ListIterator} over {@code list} at every step.
     *
     * <p>Traversal phases:
     * <ol>
     *   <li>Full forward sweep</li>
     *   <li>Full backward sweep</li>
     *   <li>Zigzag: forward one step, back one step, forward one step — across all elements</li>
     *   <li>Full backward sweep</li>
     *   <li>Nested loops: walk forward i steps, back i/2, forward i/2, back i — for each i</li>
     *   <li>500 random single steps, recording the path for failure messages</li>
     * </ol>
     */
    private <E> void walkLists(final List<E> list, final ListIterator<E> testing) {
        final ListIterator<E> expected = list.listIterator();

        // Phase 1: full forward sweep.
        walkForward(expected, testing);

        // Phase 2: full backward sweep.
        walkBackward(expected, testing);

        // Phase 3: forward one, back one, forward one — across all elements.
        while (expected.hasNext()) {
            assertEquals(expected.nextIndex(),     testing.nextIndex());
            assertEquals(expected.previousIndex(), testing.previousIndex());
            assertTrue(testing.hasNext());
            assertEquals(expected.next(),     testing.next());
            assertTrue(testing.hasPrevious());
            assertEquals(expected.previous(), testing.previous());
            assertTrue(testing.hasNext());
            assertEquals(expected.next(),     testing.next());
        }

        // Phase 4: full backward sweep after the zigzag above.
        walkBackward(expected, testing);

        // Phase 5: for each stride length i, walk forward i, back i/2, forward i/2, back i.
        for (int i = 0; i < list.size(); i++) {
            for (int j = 0; j < i; j++) {
                assertEquals(expected.nextIndex(),     testing.nextIndex());
                assertEquals(expected.previousIndex(), testing.previousIndex());
                assertTrue(expected.hasNext());  // logic guard — should never fail
                assertTrue(testing.hasNext());
                assertEquals(expected.next(), testing.next());
            }
            for (int j = 0; j < i / 2; j++) {
                assertEquals(expected.nextIndex(),     testing.nextIndex());
                assertEquals(expected.previousIndex(), testing.previousIndex());
                assertTrue(expected.hasPrevious());  // logic guard
                assertTrue(testing.hasPrevious());
                assertEquals(expected.previous(), testing.previous());
            }
            for (int j = 0; j < i / 2; j++) {
                assertEquals(expected.nextIndex(),     testing.nextIndex());
                assertEquals(expected.previousIndex(), testing.previousIndex());
                assertTrue(expected.hasNext());  // logic guard
                assertTrue(testing.hasNext());
                assertEquals(expected.next(), testing.next());
            }
            for (int j = 0; j < i; j++) {
                assertEquals(expected.nextIndex(),     testing.nextIndex());
                assertEquals(expected.previousIndex(), testing.previousIndex());
                assertTrue(expected.hasPrevious());  // logic guard
                assertTrue(testing.hasPrevious());
                assertEquals(expected.previous(), testing.previous());
            }
        }

        // Phase 6: 500 random single steps; the path string is appended to failure messages
        // so it is easy to reproduce a specific sequence when a test fails.
        final StringBuilder walkDescr = new StringBuilder(500);
        for (int i = 0; i < 500; i++) {
            if (random.nextBoolean()) {
                walkDescr.append("+");  // step forward
                if (expected.hasNext()) {
                    assertEquals(expected.next(), testing.next(), walkDescr.toString());
                }
            } else {
                walkDescr.append("-");  // step backward
                if (expected.hasPrevious()) {
                    assertEquals(expected.previous(), testing.previous(), walkDescr.toString());
                }
            }
            assertEquals(expected.nextIndex(),     testing.nextIndex(),     walkDescr.toString());
            assertEquals(expected.previousIndex(), testing.previousIndex(), walkDescr.toString());
        }
    }

    /** Advances both iterators in lock-step until {@code expected} is exhausted, asserting equality at each step. */
    private void walkForward(final ListIterator<?> expected, final ListIterator<?> testing) {
        while (expected.hasNext()) {
            assertEquals(expected.nextIndex(),     testing.nextIndex());
            assertEquals(expected.previousIndex(), testing.previousIndex());
            assertTrue(testing.hasNext());
            assertEquals(expected.next(), testing.next());
        }
    }

    /** Retreats both iterators in lock-step until {@code expected} is exhausted, asserting equality at each step. */
    private void walkBackward(final ListIterator<?> expected, final ListIterator<?> testing) {
        while (expected.hasPrevious()) {
            assertEquals(expected.nextIndex(),     testing.nextIndex());
            assertEquals(expected.previousIndex(), testing.previousIndex());
            assertTrue(testing.hasPrevious());
            assertEquals(expected.previous(), testing.previous());
        }
    }
}
