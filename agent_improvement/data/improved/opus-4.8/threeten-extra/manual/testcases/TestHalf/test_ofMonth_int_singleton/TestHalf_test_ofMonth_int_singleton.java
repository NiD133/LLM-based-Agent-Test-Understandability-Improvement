package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertSame;

import org.junit.jupiter.api.Test;

/**
 * Verifies that {@link Half#ofMonth(int)} maps each month of the year to the
 * correct half-of-year singleton:
 * <ul>
 *   <li>January (1) through June (6) belong to the first half, {@link Half#H1}.</li>
 *   <li>July (7) through December (12) belong to the second half, {@link Half#H2}.</li>
 * </ul>
 */
public class TestHalf_test_ofMonth_int_singleton {

    @Test
    public void monthsJanuaryToJune_areFirstHalf() {
        assertSame(Half.H1, Half.ofMonth(1));   // January
        assertSame(Half.H1, Half.ofMonth(2));   // February
        assertSame(Half.H1, Half.ofMonth(3));   // March
        assertSame(Half.H1, Half.ofMonth(4));   // April
        assertSame(Half.H1, Half.ofMonth(5));   // May
        assertSame(Half.H1, Half.ofMonth(6));   // June
    }

    @Test
    public void monthsJulyToDecember_areSecondHalf() {
        assertSame(Half.H2, Half.ofMonth(7));   // July
        assertSame(Half.H2, Half.ofMonth(8));   // August
        assertSame(Half.H2, Half.ofMonth(9));   // September
        assertSame(Half.H2, Half.ofMonth(10));  // October
        assertSame(Half.H2, Half.ofMonth(11));  // November
        assertSame(Half.H2, Half.ofMonth(12));  // December
    }
}
