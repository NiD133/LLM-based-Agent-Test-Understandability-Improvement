package org.apache.commons.lang3;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import org.junit.jupiter.api.Test;

public class CharRangeTest_testEquals_Object extends AbstractLangTest {

    @Test
    void testEquals_Object() {
        final CharRange singleA = CharRange.is('a');
        final CharRange rangeAToE = CharRange.isIn('a', 'e');
        final CharRange rangeBToF = CharRange.isIn('b', 'f');

        assertNotEquals(null, singleA);

        assertEquals(singleA, singleA);
        assertEquals(singleA, CharRange.is('a'));
        assertEquals(rangeAToE, rangeAToE);
        assertEquals(rangeAToE, CharRange.isIn('a', 'e'));
        assertEquals(rangeBToF, rangeBToF);
        assertEquals(rangeBToF, CharRange.isIn('b', 'f'));

        assertNotEquals(singleA, rangeAToE);
        assertNotEquals(singleA, rangeBToF);
        assertNotEquals(rangeAToE, singleA);
        assertNotEquals(rangeAToE, rangeBToF);
        assertNotEquals(rangeBToF, singleA);
        assertNotEquals(rangeBToF, rangeAToE);
    }
}
