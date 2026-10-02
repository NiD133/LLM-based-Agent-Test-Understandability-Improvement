package org.apache.commons.lang3;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

/**
 * Tests {@link CharSet#hashCode()}.
 *
 * <p>The contract verified here is twofold:</p>
 * <ul>
 *   <li><b>Consistency:</b> calling {@code hashCode()} repeatedly on the same
 *       instance always yields the same value.</li>
 *   <li><b>Equality compatibility:</b> two instances built from the same
 *       definition string represent the same set and therefore share a
 *       hash code.</li>
 * </ul>
 */
public class CharSetTest_testHashCode extends AbstractLangTest {

    @Test
    void testHashCode() {
        // Three representative definition styles, each built twice so we can
        // compare a hash code against an equal-but-distinct instance.
        final CharSet listedChars = CharSet.getInstance("abc");
        final CharSet listedCharsCopy = CharSet.getInstance("abc");

        final CharSet range = CharSet.getInstance("a-c");
        final CharSet rangeCopy = CharSet.getInstance("a-c");

        final CharSet negatedRange = CharSet.getInstance("^a-c");
        final CharSet negatedRangeCopy = CharSet.getInstance("^a-c");

        // Consistency: repeated calls on one instance agree.
        assertEquals(listedChars.hashCode(), listedChars.hashCode());
        assertEquals(range.hashCode(), range.hashCode());
        assertEquals(negatedRange.hashCode(), negatedRange.hashCode());

        // Equality compatibility: equal definitions yield equal hash codes.
        assertEquals(listedChars.hashCode(), listedCharsCopy.hashCode());
        assertEquals(range.hashCode(), rangeCopy.hashCode());
        assertEquals(negatedRange.hashCode(), negatedRangeCopy.hashCode());
    }
}
