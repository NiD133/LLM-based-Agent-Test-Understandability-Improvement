package org.apache.commons.lang3;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.Test;

public class EnumUtilsTest_testGetEnumMap_keyFunction extends AbstractLangTest {

    @Test
    void testGetEnumMap_keyFunction() {
        final Map<Integer, Month> test = EnumUtils.getEnumMap(Month.class, Month::getId);

        // Verify the map contains exactly the expected ID-to-Month entries
        final Map<Integer, Month> expected = new HashMap<>();
        expected.put(1, Month.JAN);
        expected.put(2, Month.FEB);
        expected.put(3, Month.MAR);
        expected.put(4, Month.APR);
        expected.put(5, Month.MAY);
        expected.put(6, Month.JUN);
        expected.put(7, Month.JUL);
        expected.put(8, Month.AUG);
        expected.put(9, Month.SEP);
        expected.put(10, Month.OCT);
        expected.put(11, Month.NOV);
        expected.put(12, Month.DEC);
        assertEquals(expected, test, "getEnumMap not created correctly");
        assertEquals(12, test.size());

        // Verify IDs outside the valid month range [1..12] are absent
        assertFalse(test.containsKey(0));
        assertFalse(test.containsKey(13));

        // Verify each month is reachable by its numeric ID
        assertTrue(test.containsKey(1));
        assertEquals(Month.JAN, test.get(1));
        assertTrue(test.containsKey(2));
        assertEquals(Month.FEB, test.get(2));
        assertTrue(test.containsKey(3));
        assertEquals(Month.MAR, test.get(3));
        assertTrue(test.containsKey(4));
        assertEquals(Month.APR, test.get(4));
        assertTrue(test.containsKey(5));
        assertEquals(Month.MAY, test.get(5));
        assertTrue(test.containsKey(6));
        assertEquals(Month.JUN, test.get(6));
        assertTrue(test.containsKey(7));
        assertEquals(Month.JUL, test.get(7));
        assertTrue(test.containsKey(8));
        assertEquals(Month.AUG, test.get(8));
        assertTrue(test.containsKey(9));
        assertEquals(Month.SEP, test.get(9));
        assertTrue(test.containsKey(10));
        assertEquals(Month.OCT, test.get(10));
        assertTrue(test.containsKey(11));
        assertEquals(Month.NOV, test.get(11));
        assertTrue(test.containsKey(12));
        assertEquals(Month.DEC, test.get(12));
    }
}
