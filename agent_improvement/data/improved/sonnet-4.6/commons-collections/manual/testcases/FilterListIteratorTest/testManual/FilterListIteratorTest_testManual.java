package org.apache.commons.collections4.iterators;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import java.util.ArrayList;
import org.apache.commons.collections4.Predicate;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

@SuppressWarnings("boxing")
public class FilterListIteratorTest_testManual {

    // The full list [0, 19] used as the source for filtering
    private ArrayList<Integer> list;

    // Accepts only multiples of 3: {0, 3, 6, 9, 12, 15, 18}
    private Predicate<Integer> threePred;

    @BeforeEach
    public void setUp() {
        list = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            list.add(Integer.valueOf(i));
        }
        threePred = x -> x % 3 == 0;
    }

    @AfterEach
    public void tearDown() throws Exception {
        list = null;
        threePred = null;
    }

    @Test
    void testManual() {
        // Sanity-check the filtered view of multiples of 3 in [0, 19]: {0, 3, 6, 9, 12, 15, 18}
        final FilterListIterator<Integer> filtered = new FilterListIterator<>(list.listIterator(), threePred);

        // Traverse forward through all seven multiples of 3
        assertEquals(Integer.valueOf(0),  filtered.next());
        assertEquals(Integer.valueOf(3),  filtered.next());
        assertEquals(Integer.valueOf(6),  filtered.next());
        assertEquals(Integer.valueOf(9),  filtered.next());
        assertEquals(Integer.valueOf(12), filtered.next());
        assertEquals(Integer.valueOf(15), filtered.next());
        assertEquals(Integer.valueOf(18), filtered.next());

        // Traverse backward through all seven multiples of 3
        assertEquals(Integer.valueOf(18), filtered.previous());
        assertEquals(Integer.valueOf(15), filtered.previous());
        assertEquals(Integer.valueOf(12), filtered.previous());
        assertEquals(Integer.valueOf(9),  filtered.previous());
        assertEquals(Integer.valueOf(6),  filtered.previous());
        assertEquals(Integer.valueOf(3),  filtered.previous());
        assertEquals(Integer.valueOf(0),  filtered.previous());

        // Cursor is now before 0: no previous element should exist
        assertFalse(filtered.hasPrevious());

        // Traverse forward again through all seven multiples of 3
        assertEquals(Integer.valueOf(0),  filtered.next());
        assertEquals(Integer.valueOf(3),  filtered.next());
        assertEquals(Integer.valueOf(6),  filtered.next());
        assertEquals(Integer.valueOf(9),  filtered.next());
        assertEquals(Integer.valueOf(12), filtered.next());
        assertEquals(Integer.valueOf(15), filtered.next());
        assertEquals(Integer.valueOf(18), filtered.next());

        // Cursor is now after 18: no next element should exist
        assertFalse(filtered.hasNext());

        // Traverse backward again through all seven multiples of 3
        assertEquals(Integer.valueOf(18), filtered.previous());
        assertEquals(Integer.valueOf(15), filtered.previous());
        assertEquals(Integer.valueOf(12), filtered.previous());
        assertEquals(Integer.valueOf(9),  filtered.previous());
        assertEquals(Integer.valueOf(6),  filtered.previous());
        assertEquals(Integer.valueOf(3),  filtered.previous());
        assertEquals(Integer.valueOf(0),  filtered.previous());

        // Zigzag around the start boundary (0): verify cursor state is consistent
        assertEquals(Integer.valueOf(0), filtered.next());
        assertEquals(Integer.valueOf(0), filtered.previous());
        assertEquals(Integer.valueOf(0), filtered.next());

        // Advance to 6, then step back two positions to 3
        assertEquals(Integer.valueOf(3), filtered.next());
        assertEquals(Integer.valueOf(6), filtered.next());
        assertEquals(Integer.valueOf(6), filtered.previous());
        assertEquals(Integer.valueOf(3), filtered.previous());

        // Re-advance past 3, navigate to 15, then step back to 9
        assertEquals(Integer.valueOf(3),  filtered.next());
        assertEquals(Integer.valueOf(6),  filtered.next());
        assertEquals(Integer.valueOf(9),  filtered.next());
        assertEquals(Integer.valueOf(12), filtered.next());
        assertEquals(Integer.valueOf(15), filtered.next());
        assertEquals(Integer.valueOf(15), filtered.previous());
        assertEquals(Integer.valueOf(12), filtered.previous());
        assertEquals(Integer.valueOf(9),  filtered.previous());
    }
}
