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
        final Map<Integer, Month> expected = new HashMap<>();

        addExpectedMonth(expected, 1, Month.JAN);
        addExpectedMonth(expected, 2, Month.FEB);
        addExpectedMonth(expected, 3, Month.MAR);
        addExpectedMonth(expected, 4, Month.APR);
        addExpectedMonth(expected, 5, Month.MAY);
        addExpectedMonth(expected, 6, Month.JUN);
        addExpectedMonth(expected, 7, Month.JUL);
        addExpectedMonth(expected, 8, Month.AUG);
        addExpectedMonth(expected, 9, Month.SEP);
        addExpectedMonth(expected, 10, Month.OCT);
        addExpectedMonth(expected, 11, Month.NOV);
        addExpectedMonth(expected, 12, Month.DEC);

        assertEquals(expected, test, "getEnumMap not created correctly");
        assertEquals(12, test.size());
        assertFalse(test.containsKey(0));
        assertMonthMapping(test, 1, Month.JAN);
        assertMonthMapping(test, 2, Month.FEB);
        assertMonthMapping(test, 3, Month.MAR);
        assertMonthMapping(test, 4, Month.APR);
        assertMonthMapping(test, 5, Month.MAY);
        assertMonthMapping(test, 6, Month.JUN);
        assertMonthMapping(test, 7, Month.JUL);
        assertMonthMapping(test, 8, Month.AUG);
        assertMonthMapping(test, 9, Month.SEP);
        assertMonthMapping(test, 10, Month.OCT);
        assertMonthMapping(test, 11, Month.NOV);
        assertMonthMapping(test, 12, Month.DEC);
        assertFalse(test.containsKey(13));
    }

    private void addExpectedMonth(final Map<Integer, Month> expected, final int id, final Month month) {
        expected.put(id, month);
    }

    private void assertMonthMapping(final Map<Integer, Month> test, final int id, final Month month) {
        assertTrue(test.containsKey(id));
        assertEquals(month, test.get(id));
    }

    private enum Month {
        JAN(1),
        FEB(2),
        MAR(3),
        APR(4),
        MAY(5),
        JUN(6),
        JUL(7),
        AUG(8),
        SEP(9),
        OCT(10),
        NOV(11),
        DEC(12);

        private final int id;

        Month(final int id) {
            this.id = id;
        }

        int getId() {
            return id;
        }
    }
}
