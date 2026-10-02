package org.apache.commons.lang3;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class CharRangeTest_testContains_Charrange extends AbstractLangTest {

    @Test
    void testContains_Charrange() {
        final CharRange a = CharRange.is('a');
        final CharRange b = CharRange.is('b');
        final CharRange c = CharRange.is('c');
        final CharRange c2 = CharRange.is('c');
        final CharRange d = CharRange.is('d');
        final CharRange e = CharRange.is('e');

        final CharRange ab = CharRange.isIn('a', 'b');
        final CharRange bc = CharRange.isIn('b', 'c');
        final CharRange bd = CharRange.isIn('b', 'd');
        final CharRange cd = CharRange.isIn('c', 'd');
        final CharRange de = CharRange.isIn('d', 'e');
        final CharRange ef = CharRange.isIn('e', 'f');
        final CharRange ae = CharRange.isIn('a', 'e');

        assertNormalRangeContainingNormalRanges(a, b, c, c2, d, ab, bc, bd, cd, de, ae);

        final CharRange notb = CharRange.isNot('b');
        final CharRange notc = CharRange.isNot('c');
        final CharRange notd = CharRange.isNot('d');

        final CharRange notab = CharRange.isNotIn('a', 'b');
        final CharRange notbc = CharRange.isNotIn('b', 'c');
        final CharRange notbd = CharRange.isNotIn('b', 'd');
        final CharRange notcd = CharRange.isNotIn('c', 'd');
        final CharRange notde = CharRange.isNotIn('d', 'e');
        final CharRange notae = CharRange.isNotIn('a', 'e');

        final CharRange all = CharRange.isIn((char) 0, Character.MAX_VALUE);
        final CharRange allbutfirst = CharRange.isIn((char) 1, Character.MAX_VALUE);

        assertNormalRangeContainingNegatedRanges(c, notc, notbd, all, allbutfirst);
        assertNegatedRangeContainingNormalRanges(a, b, c, d, e, ab, bc, bd, cd, de, ef, ae,
                notc, notbd, notcd, all, allbutfirst);
        assertNegatedRangeContainingNegatedRanges(notb, notc, notd, notab, notbc, notbd, notcd, notde,
                notae);
    }

    private void assertNormalRangeContainingNormalRanges(final CharRange a, final CharRange b,
            final CharRange c, final CharRange c2, final CharRange d, final CharRange ab,
            final CharRange bc, final CharRange bd, final CharRange cd, final CharRange de,
            final CharRange ae) {
        assertDoesNotContain(c, b);
        assertContains(c, c);
        assertContains(c, c2);
        assertDoesNotContain(c, d);
        assertDoesNotContain(c, cd);
        assertDoesNotContain(c, bd);
        assertDoesNotContain(c, bc);
        assertDoesNotContain(c, ab);
        assertDoesNotContain(c, de);

        assertContains(cd, c);
        assertContains(bd, c);
        assertContains(bc, c);
        assertDoesNotContain(ab, c);
        assertDoesNotContain(de, c);

        assertContains(ae, b);
        assertContains(ae, ab);
        assertContains(ae, bc);
        assertContains(ae, cd);
        assertContains(ae, de);
    }

    private void assertNormalRangeContainingNegatedRanges(final CharRange c, final CharRange notc,
            final CharRange notbd, final CharRange all, final CharRange allbutfirst) {
        assertDoesNotContain(c, notc);
        assertDoesNotContain(c, notbd);

        assertContains(all, notc);
        assertContains(all, notbd);

        assertDoesNotContain(allbutfirst, notc);
        assertDoesNotContain(allbutfirst, notbd);
    }

    private void assertNegatedRangeContainingNormalRanges(final CharRange a, final CharRange b,
            final CharRange c, final CharRange d, final CharRange e, final CharRange ab,
            final CharRange bc, final CharRange bd, final CharRange cd, final CharRange de,
            final CharRange ef, final CharRange ae, final CharRange notc, final CharRange notbd,
            final CharRange notcd, final CharRange all, final CharRange allbutfirst) {
        assertContains(notc, a);
        assertContains(notc, b);
        assertDoesNotContain(notc, c);
        assertContains(notc, d);
        assertContains(notc, e);
        assertContains(notc, ab);
        assertDoesNotContain(notc, bc);
        assertDoesNotContain(notc, bd);
        assertDoesNotContain(notc, cd);
        assertContains(notc, de);
        assertDoesNotContain(notc, ae);
        assertDoesNotContain(notc, all);
        assertDoesNotContain(notc, allbutfirst);

        assertContains(notbd, a);
        assertDoesNotContain(notbd, b);
        assertDoesNotContain(notbd, c);
        assertDoesNotContain(notbd, d);
        assertContains(notbd, e);

        assertContains(notcd, ab);
        assertDoesNotContain(notcd, bc);
        assertDoesNotContain(notcd, bd);
        assertDoesNotContain(notcd, cd);
        assertDoesNotContain(notcd, de);
        assertDoesNotContain(notcd, ae);
        assertContains(notcd, ef);
        assertDoesNotContain(notcd, all);
        assertDoesNotContain(notcd, allbutfirst);
    }

    private void assertNegatedRangeContainingNegatedRanges(final CharRange notb,
            final CharRange notc, final CharRange notd, final CharRange notab,
            final CharRange notbc, final CharRange notbd, final CharRange notcd,
            final CharRange notde, final CharRange notae) {
        assertDoesNotContain(notc, notb);
        assertContains(notc, notc);
        assertDoesNotContain(notc, notd);
        assertDoesNotContain(notc, notab);
        assertContains(notc, notbc);
        assertContains(notc, notbd);
        assertContains(notc, notcd);
        assertDoesNotContain(notc, notde);

        assertDoesNotContain(notbd, notb);
        assertDoesNotContain(notbd, notc);
        assertDoesNotContain(notbd, notd);
        assertDoesNotContain(notbd, notab);
        assertDoesNotContain(notbd, notbc);
        assertContains(notbd, notbd);
        assertDoesNotContain(notbd, notcd);
        assertDoesNotContain(notbd, notde);
        assertContains(notbd, notae);
    }

    private void assertContains(final CharRange range, final CharRange candidate) {
        assertTrue(range.contains(candidate));
    }

    private void assertDoesNotContain(final CharRange range, final CharRange candidate) {
        assertFalse(range.contains(candidate));
    }
}
