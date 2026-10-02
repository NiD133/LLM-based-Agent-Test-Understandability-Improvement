package org.apache.commons.lang3;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

public class CharRangeTest_testContains_Charrange extends AbstractLangTest {

    // --- Single-character normal ranges ---
    private static final CharRange a  = CharRange.is('a');
    private static final CharRange b  = CharRange.is('b');
    private static final CharRange c  = CharRange.is('c');
    private static final CharRange c2 = CharRange.is('c'); // distinct instance equal to c
    private static final CharRange d  = CharRange.is('d');
    private static final CharRange e  = CharRange.is('e');

    // --- Multi-character normal ranges ---
    private static final CharRange ab = CharRange.isIn('a', 'b');
    private static final CharRange bc = CharRange.isIn('b', 'c');
    private static final CharRange bd = CharRange.isIn('b', 'd');
    private static final CharRange cd = CharRange.isIn('c', 'd');
    private static final CharRange de = CharRange.isIn('d', 'e');
    private static final CharRange ef = CharRange.isIn('e', 'f');
    private static final CharRange ae = CharRange.isIn('a', 'e');

    // --- Full-character-space normal ranges ---
    private static final CharRange all         = CharRange.isIn((char) 0, Character.MAX_VALUE);
    private static final CharRange allbutfirst = CharRange.isIn((char) 1, Character.MAX_VALUE);

    // --- Negated ranges (complement of the named span) ---
    private static final CharRange notb  = CharRange.isNot('b');
    private static final CharRange notc  = CharRange.isNot('c');
    private static final CharRange notd  = CharRange.isNot('d');
    private static final CharRange notab = CharRange.isNotIn('a', 'b');
    private static final CharRange notbc = CharRange.isNotIn('b', 'c');
    private static final CharRange notbd = CharRange.isNotIn('b', 'd');
    private static final CharRange notcd = CharRange.isNotIn('c', 'd');
    private static final CharRange notde = CharRange.isNotIn('d', 'e');
    private static final CharRange notae = CharRange.isNotIn('a', 'e');

    /**
     * A normal (non-negated) range R1 contains a normal range R2 if and only if
     * R2's start/end are both within R1's bounds.
     */
    @Test
    void testContains_normalContainsNormal() {
        // A single-char range contains only itself
        assertFalse(c.contains(b));
        assertTrue(c.contains(c));
        assertTrue(c.contains(c2));  // distinct but equal instance is still contained
        assertFalse(c.contains(d));

        // A single-char range can never contain a multi-char range
        assertFalse(c.contains(cd));
        assertFalse(c.contains(bd));
        assertFalse(c.contains(bc));
        assertFalse(c.contains(ab));
        assertFalse(c.contains(de));

        // Multi-char range [c,d] contains 'c' but not characters outside its bounds
        assertTrue(cd.contains(c));
        assertTrue(bd.contains(c));
        assertTrue(bc.contains(c));
        assertFalse(ab.contains(c));
        assertFalse(de.contains(c));

        // Wide range [a,e] contains all sub-ranges and single chars within it
        assertTrue(ae.contains(b));
        assertTrue(ae.contains(ab));
        assertTrue(ae.contains(bc));
        assertTrue(ae.contains(cd));
        assertTrue(ae.contains(de));
    }

    /**
     * A normal range can contain a negated range only when it spans the full
     * character space [0..MAX_VALUE], because a negated range is an infinite complement.
     */
    @Test
    void testContains_normalContainsNegated() {
        // Ordinary ranges are too small to contain any negated (complement) range
        assertFalse(c.contains(notc));
        assertFalse(c.contains(notbd));

        // The full character range contains any negated range
        assertTrue(all.contains(notc));
        assertTrue(all.contains(notbd));

        // Missing even one character means it cannot contain a negated range
        assertFalse(allbutfirst.contains(notc));
        assertFalse(allbutfirst.contains(notbd));
    }

    /**
     * A negated range "not[x,y]" contains a normal range R when R falls entirely
     * outside the excluded span [x,y].
     */
    @Test
    void testContains_negatedContainsNormal() {
        // notc excludes only 'c'; all other single chars are contained
        assertTrue(notc.contains(a));
        assertTrue(notc.contains(b));
        assertFalse(notc.contains(c));   // 'c' is the excluded character
        assertTrue(notc.contains(d));
        assertTrue(notc.contains(e));

        // notc contains a range only if that range has no overlap with 'c'
        assertTrue(notc.contains(ab));
        assertFalse(notc.contains(bc));  // bc includes 'c'
        assertFalse(notc.contains(bd));  // bd includes 'c'
        assertFalse(notc.contains(cd));  // cd includes 'c'
        assertTrue(notc.contains(de));
        assertFalse(notc.contains(ae));  // ae includes 'c'
        assertFalse(notc.contains(all));
        assertFalse(notc.contains(allbutfirst));

        // notbd excludes [b,d]; only chars strictly outside [b,d] are contained
        assertTrue(notbd.contains(a));
        assertFalse(notbd.contains(b));
        assertFalse(notbd.contains(c));
        assertFalse(notbd.contains(d));
        assertTrue(notbd.contains(e));

        // notcd excludes [c,d]; ranges are contained only if they don't overlap [c,d]
        assertTrue(notcd.contains(ab));
        assertFalse(notcd.contains(bc));  // bc overlaps [c,d] at 'c'
        assertFalse(notcd.contains(bd));  // bd overlaps [c,d]
        assertFalse(notcd.contains(cd));
        assertFalse(notcd.contains(de));  // de overlaps [c,d] at 'd'
        assertFalse(notcd.contains(ae));  // ae spans across [c,d]
        assertTrue(notcd.contains(ef));   // ef is fully beyond [c,d]
        assertFalse(notcd.contains(all));
        assertFalse(notcd.contains(allbutfirst));
    }

    /**
     * A negated range "not[x,y]" contains another negated range "not[p,q]" when
     * the excluded region [p,q] is a superset of [x,y], meaning not[p,q] excludes
     * at least as much as not[x,y] does.
     */
    @Test
    void testContains_negatedContainsNegated() {
        // notc (excludes 'c') vs. single-char negated ranges:
        // the other must also exclude 'c' to be contained within notc
        assertFalse(notc.contains(notb));  // notb excludes 'b', not 'c'
        assertTrue(notc.contains(notc));   // identical excluded region
        assertFalse(notc.contains(notd));  // notd excludes 'd', not 'c'

        // notc vs. range-negated ranges: the excluded span must cover 'c'
        assertFalse(notc.contains(notab)); // [a,b] does not include 'c'
        assertTrue(notc.contains(notbc));  // [b,c] includes 'c'
        assertTrue(notc.contains(notbd));  // [b,d] includes 'c'
        assertTrue(notc.contains(notcd));  // [c,d] includes 'c'
        assertFalse(notc.contains(notde)); // [d,e] does not include 'c'

        // notbd (excludes [b,d]) vs. single-char negated ranges:
        // no single-char exclusion can cover the full span [b,d]
        assertFalse(notbd.contains(notb));
        assertFalse(notbd.contains(notc));
        assertFalse(notbd.contains(notd));

        // notbd vs. range-negated ranges: the excluded span must fully cover [b,d]
        assertFalse(notbd.contains(notab)); // [a,b] misses 'c' and 'd'
        assertFalse(notbd.contains(notbc)); // [b,c] misses 'd'
        assertTrue(notbd.contains(notbd));  // identical excluded region
        assertFalse(notbd.contains(notcd)); // [c,d] misses 'b'
        assertFalse(notbd.contains(notde)); // [d,e] misses 'b' and 'c'
        assertTrue(notbd.contains(notae));  // [a,e] fully covers [b,d]
    }
}
