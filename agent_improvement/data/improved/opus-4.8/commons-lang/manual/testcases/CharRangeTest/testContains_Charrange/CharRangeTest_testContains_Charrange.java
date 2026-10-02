package org.apache.commons.lang3;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

/**
 * Tests {@link CharRange#contains(CharRange)}.
 *
 * <p>A {@code CharRange} describes either a contiguous block of characters
 * (normal) or everything outside that block (negated). {@code outer.contains(inner)}
 * is {@code true} only when every character matched by {@code inner} is also
 * matched by {@code outer}. The cases below are grouped by the (normal/negated)
 * combination of the two ranges, since the containment rule differs for each.</p>
 */
public class CharRangeTest_testContains_Charrange extends AbstractLangTest {

    @Test
    void testContains_Charrange() {
        // Single-character ranges: each matches exactly one letter.
        final CharRange isA = CharRange.is('a');
        final CharRange isB = CharRange.is('b');
        final CharRange isC = CharRange.is('c');
        final CharRange isCDuplicate = CharRange.is('c');
        final CharRange isD = CharRange.is('d');
        final CharRange isE = CharRange.is('e');

        // Normal (non-negated) multi-character ranges, named after their endpoints.
        final CharRange rangeCtoD = CharRange.isIn('c', 'd');
        final CharRange rangeBtoD = CharRange.isIn('b', 'd');
        final CharRange rangeBtoC = CharRange.isIn('b', 'c');
        final CharRange rangeAtoB = CharRange.isIn('a', 'b');
        final CharRange rangeDtoE = CharRange.isIn('d', 'e');
        final CharRange rangeEtoF = CharRange.isIn('e', 'f');
        final CharRange rangeAtoE = CharRange.isIn('a', 'e');

        // --- normal range contains normal range ---
        // A normal range contains another normal range only when it fully spans it.
        assertFalse(isC.contains(isB), "'c' does not contain disjoint 'b'");
        assertTrue(isC.contains(isC), "'c' contains itself");
        assertTrue(isC.contains(isCDuplicate), "'c' contains an equal 'c'");
        assertFalse(isC.contains(isD), "'c' does not contain disjoint 'd'");
        assertFalse(isC.contains(rangeCtoD), "single 'c' is too small to contain 'c'-'d'");
        assertFalse(isC.contains(rangeBtoD), "single 'c' is too small to contain 'b'-'d'");
        assertFalse(isC.contains(rangeBtoC), "single 'c' is too small to contain 'b'-'c'");
        assertFalse(isC.contains(rangeAtoB), "'c' does not contain disjoint 'a'-'b'");
        assertFalse(isC.contains(rangeDtoE), "'c' does not contain disjoint 'd'-'e'");

        assertTrue(rangeCtoD.contains(isC), "'c'-'d' contains 'c'");
        assertTrue(rangeBtoD.contains(isC), "'b'-'d' contains 'c'");
        assertTrue(rangeBtoC.contains(isC), "'b'-'c' contains 'c'");
        assertFalse(rangeAtoB.contains(isC), "'a'-'b' does not reach 'c'");
        assertFalse(rangeDtoE.contains(isC), "'d'-'e' does not reach 'c'");

        assertTrue(rangeAtoE.contains(isB), "'a'-'e' contains 'b'");
        assertTrue(rangeAtoE.contains(rangeAtoB), "'a'-'e' contains sub-range 'a'-'b'");
        assertTrue(rangeAtoE.contains(rangeBtoC), "'a'-'e' contains sub-range 'b'-'c'");
        assertTrue(rangeAtoE.contains(rangeCtoD), "'a'-'e' contains sub-range 'c'-'d'");
        assertTrue(rangeAtoE.contains(rangeDtoE), "'a'-'e' contains sub-range 'd'-'e'");

        // Negated ranges: match every character EXCEPT the named block.
        final CharRange notB = CharRange.isNot('b');
        final CharRange notC = CharRange.isNot('c');
        final CharRange notD = CharRange.isNot('d');
        final CharRange notAtoB = CharRange.isNotIn('a', 'b');
        final CharRange notBtoC = CharRange.isNotIn('b', 'c');
        final CharRange notBtoD = CharRange.isNotIn('b', 'd');
        final CharRange notCtoD = CharRange.isNotIn('c', 'd');
        final CharRange notDtoE = CharRange.isNotIn('d', 'e');
        final CharRange notAtoE = CharRange.isNotIn('a', 'e');

        // The full character space, and everything except the very first character.
        final CharRange all = CharRange.isIn((char) 0, Character.MAX_VALUE);
        final CharRange allButFirst = CharRange.isIn((char) 1, Character.MAX_VALUE);

        // --- normal range contains negated range ---
        // A negated range covers nearly every character, so only the all-encompassing
        // range can contain one.
        assertFalse(isC.contains(notC), "single 'c' cannot contain the huge 'not c' set");
        assertFalse(isC.contains(notBtoD), "single 'c' cannot contain the huge 'not b-d' set");
        assertTrue(all.contains(notC), "the full range contains 'not c'");
        assertTrue(all.contains(notBtoD), "the full range contains 'not b-d'");
        assertFalse(allButFirst.contains(notC), "missing char 0, so it cannot contain 'not c'");
        assertFalse(allButFirst.contains(notBtoD), "missing char 0, so it cannot contain 'not b-d'");

        // --- negated range contains normal range ---
        // 'not c' contains a normal range exactly when that range avoids 'c'.
        assertTrue(notC.contains(isA), "'not c' contains 'a'");
        assertTrue(notC.contains(isB), "'not c' contains 'b'");
        assertFalse(notC.contains(isC), "'not c' excludes 'c'");
        assertTrue(notC.contains(isD), "'not c' contains 'd'");
        assertTrue(notC.contains(isE), "'not c' contains 'e'");
        assertTrue(notC.contains(rangeAtoB), "'not c' contains 'a'-'b', which avoids 'c'");
        assertFalse(notC.contains(rangeBtoC), "'b'-'c' includes the excluded 'c'");
        assertFalse(notC.contains(rangeBtoD), "'b'-'d' includes the excluded 'c'");
        assertFalse(notC.contains(rangeCtoD), "'c'-'d' includes the excluded 'c'");
        assertTrue(notC.contains(rangeDtoE), "'not c' contains 'd'-'e', which avoids 'c'");
        assertFalse(notC.contains(rangeAtoE), "'a'-'e' includes the excluded 'c'");
        assertFalse(notC.contains(all), "'not c' cannot contain the full range");
        assertFalse(notC.contains(allButFirst), "'not c' cannot contain 'all but first'");

        // 'not b-d' contains a normal range exactly when that range avoids 'b'-'d'.
        assertTrue(notBtoD.contains(isA), "'a' is outside 'b'-'d'");
        assertFalse(notBtoD.contains(isB), "'b' is inside the excluded 'b'-'d'");
        assertFalse(notBtoD.contains(isC), "'c' is inside the excluded 'b'-'d'");
        assertFalse(notBtoD.contains(isD), "'d' is inside the excluded 'b'-'d'");
        assertTrue(notBtoD.contains(isE), "'e' is outside 'b'-'d'");

        // 'not c-d' contains a normal range exactly when that range avoids 'c'-'d'.
        assertTrue(notCtoD.contains(rangeAtoB), "'a'-'b' avoids 'c'-'d'");
        assertFalse(notCtoD.contains(rangeBtoC), "'b'-'c' overlaps the excluded 'c'-'d'");
        assertFalse(notCtoD.contains(rangeBtoD), "'b'-'d' overlaps the excluded 'c'-'d'");
        assertFalse(notCtoD.contains(rangeCtoD), "'c'-'d' is the excluded block itself");
        assertFalse(notCtoD.contains(rangeDtoE), "'d'-'e' overlaps the excluded 'c'-'d'");
        assertFalse(notCtoD.contains(rangeAtoE), "'a'-'e' overlaps the excluded 'c'-'d'");
        assertTrue(notCtoD.contains(rangeEtoF), "'e'-'f' avoids 'c'-'d'");
        assertFalse(notCtoD.contains(all), "'not c-d' cannot contain the full range");
        assertFalse(notCtoD.contains(allButFirst), "'not c-d' cannot contain 'all but first'");

        // --- negated range contains negated range ---
        // 'not X' contains 'not Y' only when the excluded block X is within block Y.
        assertFalse(notC.contains(notB), "excluding only 'c' cannot contain excluding only 'b'");
        assertTrue(notC.contains(notC), "'not c' contains itself");
        assertFalse(notC.contains(notD), "excluding only 'c' cannot contain excluding only 'd'");
        assertFalse(notC.contains(notAtoB), "'c' is not within excluded block 'a'-'b'");
        assertTrue(notC.contains(notBtoC), "excluded 'c' lies within block 'b'-'c'");
        assertTrue(notC.contains(notBtoD), "excluded 'c' lies within block 'b'-'d'");
        assertTrue(notC.contains(notCtoD), "excluded 'c' lies within block 'c'-'d'");
        assertFalse(notC.contains(notDtoE), "'c' is not within excluded block 'd'-'e'");

        assertFalse(notBtoD.contains(notB), "block 'b'-'d' is not within single 'b'");
        assertFalse(notBtoD.contains(notC), "block 'b'-'d' is not within single 'c'");
        assertFalse(notBtoD.contains(notD), "block 'b'-'d' is not within single 'd'");
        assertFalse(notBtoD.contains(notAtoB), "block 'b'-'d' is not within 'a'-'b'");
        assertFalse(notBtoD.contains(notBtoC), "block 'b'-'d' is not within 'b'-'c'");
        assertTrue(notBtoD.contains(notBtoD), "'not b-d' contains itself");
        assertFalse(notBtoD.contains(notCtoD), "block 'b'-'d' is not within 'c'-'d'");
        assertFalse(notBtoD.contains(notDtoE), "block 'b'-'d' is not within 'd'-'e'");
        assertTrue(notBtoD.contains(notAtoE), "excluded block 'b'-'d' lies within 'a'-'e'");
    }
}
