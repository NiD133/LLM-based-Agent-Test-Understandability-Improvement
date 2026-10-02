package org.apache.commons.lang3;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import org.junit.jupiter.api.Test;

public class CharRangeTest_testEquals_Object extends AbstractLangTest {

    @Test
    void testEquals_Object() {
        final CharRange rangea   = CharRange.is('a');
        final CharRange rangeae  = CharRange.isIn('a', 'e');
        final CharRange rangebf  = CharRange.isIn('b', 'f');

        // A range must not equal null
        assertNotEquals(null, rangea);

        // A range must equal itself (identity)
        assertEquals(rangea,  rangea);
        assertEquals(rangeae, rangeae);
        assertEquals(rangebf, rangebf);

        // A range must equal a distinct but equivalent instance
        assertEquals(rangea,  CharRange.is('a'));
        assertEquals(rangeae, CharRange.isIn('a', 'e'));
        assertEquals(rangebf, CharRange.isIn('b', 'f'));

        // Ranges with different definitions must not equal each other
        assertNotEquals(rangea,  rangeae);
        assertNotEquals(rangea,  rangebf);
        assertNotEquals(rangeae, rangea);
        assertNotEquals(rangeae, rangebf);
        assertNotEquals(rangebf, rangea);
        assertNotEquals(rangebf, rangeae);
    }
}
