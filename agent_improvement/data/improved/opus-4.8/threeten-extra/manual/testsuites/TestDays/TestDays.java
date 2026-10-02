/*
 * Copyright (c) 2007-present, Stephen Colebourne & Michael Nascimento Santos
 *
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 *  * Redistributions of source code must retain the above copyright notice,
 *    this list of conditions and the following disclaimer.
 *
 *  * Redistributions in binary form must reproduce the above copyright notice,
 *    this list of conditions and the following disclaimer in the documentation
 *    and/or other materials provided with the distribution.
 *
 *  * Neither the name of JSR-310 nor the names of its contributors
 *    may be used to endorse or promote products derived from this software
 *    without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 * "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT
 * LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR
 * A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR
 * CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL,
 * EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,
 * PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR
 * PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF
 * LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 * NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.time.DateTimeException;
import java.time.Duration;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeParseException;
import java.time.temporal.ChronoUnit;
import java.time.temporal.IsoFields;
import java.time.temporal.Temporal;
import java.time.temporal.TemporalAmount;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import com.google.common.testing.EqualsTester;

/**
 * Tests for {@link Days}, a day-based {@code TemporalAmount}.
 * <p>
 * The tests are grouped by the feature under test: serialization, the
 * predefined singletons, the factory methods ({@code of}, {@code ofWeeks},
 * {@code from}, {@code parse}, {@code between}), the arithmetic operations
 * ({@code plus}, {@code minus}, {@code multipliedBy}, {@code dividedBy},
 * {@code negated}, {@code abs}), the temporal adjusters ({@code addTo},
 * {@code subtractFrom}), and the value-type behaviour ({@code compareTo},
 * {@code equals}/{@code hashCode}, {@code toString}).
 */
public class TestDays {

    // Common boundary values used to exercise overflow handling. Naming them
    // makes the intent of each near-limit calculation explicit at the call site.
    private static final int MAX = Integer.MAX_VALUE;
    private static final int MIN = Integer.MIN_VALUE;

    //-----------------------------------------------------------------------
    // Serialization
    //-----------------------------------------------------------------------
    @Test
    public void days_implementsSerializable() {
        assertTrue(Serializable.class.isAssignableFrom(Days.class));
    }

    @Test
    public void deserialization_returnsTheSameSingletonInstance() throws Exception {
        Days original = Days.ZERO;

        // Round-trip the singleton through serialization...
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        try (ObjectOutputStream out = new ObjectOutputStream(bytes)) {
            out.writeObject(original);
        }

        // ...and confirm readResolve restored the very same instance, not a copy.
        try (ObjectInputStream in = new ObjectInputStream(new ByteArrayInputStream(bytes.toByteArray()))) {
            assertSame(original, in.readObject());
        }
    }

    //-----------------------------------------------------------------------
    // Predefined singletons: ZERO and ONE
    //-----------------------------------------------------------------------
    @Test
    public void zeroSingleton_hasZeroAmountAndIsNeitherNegativeNorPositive() {
        assertSame(Days.ZERO, Days.of(0));
        assertEquals(Days.ZERO, Days.of(0));
        assertEquals(0, Days.ZERO.getAmount());
        assertFalse(Days.ZERO.isNegative());
        assertTrue(Days.ZERO.isZero());
        assertFalse(Days.ZERO.isPositive());
    }

    @Test
    public void oneSingleton_hasAmountOneAndIsPositive() {
        assertSame(Days.ONE, Days.of(1));
        assertEquals(Days.ONE, Days.of(1));
        assertEquals(1, Days.ONE.getAmount());
        assertFalse(Days.ONE.isNegative());
        assertFalse(Days.ONE.isZero());
        assertTrue(Days.ONE.isPositive());
    }

    //-----------------------------------------------------------------------
    // Factory: of(int)
    //-----------------------------------------------------------------------
    @Test
    public void of_keepsTheGivenAmountAcrossTheFullIntRange() {
        assertEquals(0, Days.of(0).getAmount());
        assertEquals(1, Days.of(1).getAmount());
        assertEquals(2, Days.of(2).getAmount());
        assertEquals(MAX, Days.of(MAX).getAmount());
        assertEquals(-1, Days.of(-1).getAmount());
        assertEquals(-2, Days.of(-2).getAmount());
        assertEquals(MIN, Days.of(MIN).getAmount());
    }

    @Test
    public void of_minusOne_isNegative() {
        assertEquals(-1, Days.of(-1).getAmount());
        assertTrue(Days.of(-1).isNegative());
        assertFalse(Days.of(-1).isZero());
        assertFalse(Days.of(-1).isPositive());
    }

    //-----------------------------------------------------------------------
    // Factory: ofWeeks(int) -- converts weeks to days (1 week = 7 days)
    //-----------------------------------------------------------------------
    @Test
    public void ofWeeks_multipliesTheWeekCountBySeven() {
        assertEquals(0, Days.ofWeeks(0).getAmount());
        assertEquals(7, Days.ofWeeks(1).getAmount());
        assertEquals(14, Days.ofWeeks(2).getAmount());
        assertEquals((MAX / 7) * 7, Days.ofWeeks(MAX / 7).getAmount());
        assertEquals(-7, Days.ofWeeks(-1).getAmount());
        assertEquals(-14, Days.ofWeeks(-2).getAmount());
        assertEquals((MIN / 7) * 7, Days.ofWeeks(MIN / 7).getAmount());
    }

    @Test
    public void ofWeeks_throwsWhenWeekToDayConversionOverflows() {
        // (MAX / 7) + 7 weeks is more than Integer.MAX_VALUE days once multiplied by 7.
        assertThrows(ArithmeticException.class, () -> Days.ofWeeks((MAX / 7) + 7));
    }

    //-----------------------------------------------------------------------
    // Factory: from(TemporalAmount)
    //-----------------------------------------------------------------------
    @Test
    public void from_period_ofZeroDays() {
        assertEquals(Days.of(0), Days.from(Period.ofDays(0)));
    }

    @Test
    public void from_period_ofTwoDays() {
        assertEquals(Days.of(2), Days.from(Period.ofDays(2)));
    }

    @Test
    public void from_weeksOnlyAmount_isConvertedToDays() {
        // 2 weeks, 0 days -> 14 days.
        assertEquals(Days.of(14), Days.from(new MockWeeksDays(2, 0)));
    }

    @Test
    public void from_weeksAndDaysAmount_areSummedAsDays() {
        // 2 weeks + 3 days -> 17 days.
        assertEquals(Days.of(17), Days.from(new MockWeeksDays(2, 3)));
    }

    @Test
    public void from_duration_ofWholeDays() {
        assertEquals(Days.of(2), Days.from(Duration.ofDays(2)));
    }

    @Test
    public void from_throwsWhenAmountDoesNotDivideIntoWholeDays() {
        // 3 hours has a remainder when converted to days.
        assertThrows(DateTimeException.class, () -> Days.from(Duration.ofHours(3)));
    }

    @Test
    public void from_throwsWhenUnitCannotBeConvertedToDays() {
        // Months have no fixed length in days, so the conversion is rejected.
        assertThrows(DateTimeException.class, () -> Days.from(Period.ofMonths(2)));
    }

    @Test
    public void from_throwsOnNullAmount() {
        //noinspection DataFlowIssue - testing nulls
        assertThrows(NullPointerException.class, () -> Days.from((TemporalAmount) null));
    }

    //-----------------------------------------------------------------------
    // Factory: parse(CharSequence)
    //-----------------------------------------------------------------------
    /**
     * Valid ISO-8601-style strings paired with the day count they represent.
     * Covers day-only ("PnD"), week-only ("PnW") and combined ("PnWnD") forms,
     * each with the various sign placements that {@link Days#parse} accepts.
     */
    public static Object[][] data_valid() {
        return new Object[][] {
            {"P0D", 0},
            {"P1D", 1},
            {"P2D", 2},
            {"P123456789D", 123456789},
            {"P+0D", 0},
            {"P+2D", 2},
            {"P-0D", 0},
            {"P-2D", -2},

            {"P0W", 0},
            {"P1W", 7},
            {"P2W", 14},
            {"P12345678W", 12345678 * 7},
            {"P+0W", 0},
            {"P+2W", 14},
            {"P-0W", 0},
            {"P-2W", -14},

            {"P0W0D", 0},
            {"P2W3D", 17},
            {"P+2W3D", 17},
            {"P2W+3D", 17},
            {"P-2W3D", -11},
            {"P2W-3D", 11},
            {"P-2W-3D", -17},
        };
    }

    @ParameterizedTest
    @MethodSource("data_valid")
    public void parse_acceptsValidStrings(String text, int expectedDays) {
        assertEquals(Days.of(expectedDays), Days.parse(text));
    }

    @ParameterizedTest
    @MethodSource("data_valid")
    public void parse_leadingPlusSign_leavesTheValueUnchanged(String text, int expectedDays) {
        assertEquals(Days.of(expectedDays), Days.parse("+" + text));
    }

    @ParameterizedTest
    @MethodSource("data_valid")
    public void parse_leadingMinusSign_negatesTheWholeValue(String text, int expectedDays) {
        assertEquals(Days.of(-expectedDays), Days.parse("-" + text));
    }

    /**
     * Strings that are not valid {@code Days} representations: wrong units,
     * missing the "P" prefix, units out of order, or numbers with no unit.
     */
    public static Object[][] data_invalid() {
        return new Object[][] {
            {"P3Y"},
            {"P3M"},
            {"P3Q"},
            {"P1D2W"},

            {"3"},
            {"-3"},
            {"3D"},
            {"-3D"},
            {"P3"},
            {"P-3"},
            {"P"},
            {"PD"},
            {"PW"},
        };
    }

    @ParameterizedTest
    @MethodSource("data_invalid")
    public void parse_rejectsInvalidStrings(String text) {
        assertThrows(DateTimeParseException.class, () -> Days.parse(text));
    }

    @Test
    public void parse_throwsOnNullText() {
        //noinspection DataFlowIssue - testing nulls
        assertThrows(NullPointerException.class, () -> Days.parse((CharSequence) null));
    }

    //-----------------------------------------------------------------------
    // get(TemporalUnit)
    //-----------------------------------------------------------------------
    @Test
    public void get_returnsTheAmountForTheDaysUnit() {
        assertEquals(6, Days.of(6).get(ChronoUnit.DAYS));
    }

    @Test
    public void get_throwsForAnUnsupportedUnit() {
        assertThrows(DateTimeException.class, () -> Days.of(6).get(IsoFields.QUARTER_YEARS));
    }

    //-----------------------------------------------------------------------
    // Factory: between(Temporal, Temporal)
    //-----------------------------------------------------------------------
    @Test
    public void between_countsTheDaysFromStartInclusiveToEndExclusive() {
        // 2019 is a common year (365 days), 2020 is a leap year (366 days).
        assertEquals(Days.of(365 + 366), Days.between(LocalDate.of(2019, 1, 1), LocalDate.of(2021, 1, 1)));
    }

    @Test
    public void between_throwsOnNullEndDate() {
        //noinspection DataFlowIssue - testing nulls
        assertThrows(NullPointerException.class, () -> Days.between(LocalDate.now(), (Temporal) null));
    }

    @Test
    public void between_throwsOnNullStartDate() {
        //noinspection DataFlowIssue - testing nulls
        assertThrows(NullPointerException.class, () -> Days.between((Temporal) null, LocalDate.now()));
    }

    //-----------------------------------------------------------------------
    // plus(TemporalAmount)
    //-----------------------------------------------------------------------
    @Test
    public void plus_anotherDaysAmount_addsTheTwoAmounts() {
        Days fiveDays = Days.of(5);
        assertEquals(Days.of(5), fiveDays.plus(Days.of(0)));
        assertEquals(Days.of(7), fiveDays.plus(Days.of(2)));
        assertEquals(Days.of(3), fiveDays.plus(Days.of(-2)));
        assertEquals(Days.of(MAX), Days.of(MAX - 1).plus(Days.of(1)));
        assertEquals(Days.of(MIN), Days.of(MIN + 1).plus(Days.of(-1)));
    }

    @Test
    public void plus_aPeriod_addsItsDayCount() {
        Days fiveDays = Days.of(5);
        assertEquals(Days.of(5), fiveDays.plus(Period.ofDays(0)));
        assertEquals(Days.of(7), fiveDays.plus(Period.ofDays(2)));
        assertEquals(Days.of(3), fiveDays.plus(Period.ofDays(-2)));
        assertEquals(Days.of(MAX), Days.of(MAX - 1).plus(Period.ofDays(1)));
        assertEquals(Days.of(MIN), Days.of(MIN + 1).plus(Period.ofDays(-1)));
    }

    @Test
    public void plus_aPeriodWithYears_isRejected() {
        assertThrows(DateTimeException.class, () -> Days.of(1).plus(Period.ofYears(2)));
    }

    @Test
    public void plus_aDuration_isRejected() {
        assertThrows(DateTimeException.class, () -> Days.of(1).plus(Duration.ofHours(2)));
    }

    @Test
    public void plus_amount_throwsWhenResultOverflowsAboveMax() {
        assertThrows(ArithmeticException.class, () -> Days.of(MAX - 1).plus(Days.of(2)));
    }

    @Test
    public void plus_amount_throwsWhenResultOverflowsBelowMin() {
        assertThrows(ArithmeticException.class, () -> Days.of(MIN + 1).plus(Days.of(-2)));
    }

    @Test
    public void plus_amount_throwsOnNull() {
        //noinspection DataFlowIssue - testing nulls
        assertThrows(NullPointerException.class, () -> Days.of(MIN + 1).plus(null));
    }

    //-----------------------------------------------------------------------
    // plus(int)
    //-----------------------------------------------------------------------
    @Test
    public void plus_int_addsTheGivenNumberOfDays() {
        Days fiveDays = Days.of(5);
        assertEquals(Days.of(5), fiveDays.plus(0));
        assertEquals(Days.of(7), fiveDays.plus(2));
        assertEquals(Days.of(3), fiveDays.plus(-2));
        assertEquals(Days.of(MAX), Days.of(MAX - 1).plus(1));
        assertEquals(Days.of(MIN), Days.of(MIN + 1).plus(-1));
    }

    @Test
    public void plus_int_throwsWhenResultOverflowsAboveMax() {
        assertThrows(ArithmeticException.class, () -> Days.of(MAX - 1).plus(2));
    }

    @Test
    public void plus_int_throwsWhenResultOverflowsBelowMin() {
        assertThrows(ArithmeticException.class, () -> Days.of(MIN + 1).plus(-2));
    }

    //-----------------------------------------------------------------------
    // minus(TemporalAmount)
    //-----------------------------------------------------------------------
    @Test
    public void minus_anotherDaysAmount_subtractsTheTwoAmounts() {
        Days fiveDays = Days.of(5);
        assertEquals(Days.of(5), fiveDays.minus(Days.of(0)));
        assertEquals(Days.of(3), fiveDays.minus(Days.of(2)));
        assertEquals(Days.of(7), fiveDays.minus(Days.of(-2)));
        assertEquals(Days.of(MAX), Days.of(MAX - 1).minus(Days.of(-1)));
        assertEquals(Days.of(MIN), Days.of(MIN + 1).minus(Days.of(1)));
    }

    @Test
    public void minus_aPeriod_subtractsItsDayCount() {
        Days fiveDays = Days.of(5);
        assertEquals(Days.of(5), fiveDays.minus(Period.ofDays(0)));
        assertEquals(Days.of(3), fiveDays.minus(Period.ofDays(2)));
        assertEquals(Days.of(7), fiveDays.minus(Period.ofDays(-2)));
        assertEquals(Days.of(MAX), Days.of(MAX - 1).minus(Period.ofDays(-1)));
        assertEquals(Days.of(MIN), Days.of(MIN + 1).minus(Period.ofDays(1)));
    }

    @Test
    public void minus_aPeriodWithYears_isRejected() {
        assertThrows(DateTimeException.class, () -> Days.of(1).minus(Period.ofYears(2)));
    }

    @Test
    public void minus_aDuration_isRejected() {
        assertThrows(DateTimeException.class, () -> Days.of(1).minus(Duration.ofHours(2)));
    }

    @Test
    public void minus_amount_throwsWhenResultOverflowsAboveMax() {
        assertThrows(ArithmeticException.class, () -> Days.of(MAX - 1).minus(Days.of(-2)));
    }

    @Test
    public void minus_amount_throwsWhenResultOverflowsBelowMin() {
        assertThrows(ArithmeticException.class, () -> Days.of(MIN + 1).minus(Days.of(2)));
    }

    @Test
    public void minus_amount_throwsOnNull() {
        //noinspection DataFlowIssue - testing nulls
        assertThrows(NullPointerException.class, () -> Days.of(MIN + 1).minus(null));
    }

    //-----------------------------------------------------------------------
    // minus(int)
    //-----------------------------------------------------------------------
    @Test
    public void minus_int_subtractsTheGivenNumberOfDays() {
        Days fiveDays = Days.of(5);
        assertEquals(Days.of(5), fiveDays.minus(0));
        assertEquals(Days.of(3), fiveDays.minus(2));
        assertEquals(Days.of(7), fiveDays.minus(-2));
        assertEquals(Days.of(MAX), Days.of(MAX - 1).minus(-1));
        assertEquals(Days.of(MIN), Days.of(MIN + 1).minus(1));
    }

    @Test
    public void minus_int_throwsWhenResultOverflowsAboveMax() {
        assertThrows(ArithmeticException.class, () -> Days.of(MAX - 1).minus(-2));
    }

    @Test
    public void minus_int_throwsWhenResultOverflowsBelowMin() {
        assertThrows(ArithmeticException.class, () -> Days.of(MIN + 1).minus(2));
    }

    //-----------------------------------------------------------------------
    // multipliedBy(int)
    //-----------------------------------------------------------------------
    @Test
    public void multipliedBy_scalesTheAmountByTheGivenFactor() {
        Days fiveDays = Days.of(5);
        assertEquals(Days.of(0), fiveDays.multipliedBy(0));
        assertEquals(Days.of(5), fiveDays.multipliedBy(1));
        assertEquals(Days.of(10), fiveDays.multipliedBy(2));
        assertEquals(Days.of(15), fiveDays.multipliedBy(3));
        assertEquals(Days.of(-15), fiveDays.multipliedBy(-3));
    }

    @Test
    public void multipliedBy_negativeFactor_flipsTheSign() {
        Days fiveDays = Days.of(5);
        assertEquals(Days.of(-15), fiveDays.multipliedBy(-3));
    }

    @Test
    public void multipliedBy_throwsWhenResultOverflowsAboveMax() {
        assertThrows(ArithmeticException.class, () -> Days.of(MAX / 2 + 1).multipliedBy(2));
    }

    @Test
    public void multipliedBy_throwsWhenResultOverflowsBelowMin() {
        assertThrows(ArithmeticException.class, () -> Days.of(MIN / 2 - 1).multipliedBy(2));
    }

    //-----------------------------------------------------------------------
    // dividedBy(int)
    //-----------------------------------------------------------------------
    @Test
    public void dividedBy_usesIntegerDivisionAndTruncatesTowardsZero() {
        Days twelveDays = Days.of(12);
        assertEquals(Days.of(12), twelveDays.dividedBy(1));
        assertEquals(Days.of(6), twelveDays.dividedBy(2));
        assertEquals(Days.of(4), twelveDays.dividedBy(3));
        assertEquals(Days.of(3), twelveDays.dividedBy(4));
        assertEquals(Days.of(2), twelveDays.dividedBy(5));
        assertEquals(Days.of(2), twelveDays.dividedBy(6));
        assertEquals(Days.of(-4), twelveDays.dividedBy(-3));
    }

    @Test
    public void dividedBy_negativeDivisor_flipsTheSign() {
        Days twelveDays = Days.of(12);
        assertEquals(Days.of(-4), twelveDays.dividedBy(-3));
    }

    @Test
    public void dividedBy_zero_throws() {
        assertThrows(ArithmeticException.class, () -> Days.of(1).dividedBy(0));
    }

    //-----------------------------------------------------------------------
    // negated()
    //-----------------------------------------------------------------------
    @Test
    public void negated_flipsTheSignOfTheAmount() {
        assertEquals(Days.of(0), Days.of(0).negated());
        assertEquals(Days.of(-12), Days.of(12).negated());
        assertEquals(Days.of(12), Days.of(-12).negated());
        assertEquals(Days.of(-MAX), Days.of(MAX).negated());
    }

    @Test
    public void negated_throwsWhenNegatingMinValue() {
        // -Integer.MIN_VALUE cannot be represented as an int.
        assertThrows(ArithmeticException.class, () -> Days.of(MIN).negated());
    }

    //-----------------------------------------------------------------------
    // abs()
    //-----------------------------------------------------------------------
    @Test
    public void abs_returnsTheNonNegativeMagnitude() {
        assertEquals(Days.of(0), Days.of(0).abs());
        assertEquals(Days.of(12), Days.of(12).abs());
        assertEquals(Days.of(12), Days.of(-12).abs());
        assertEquals(Days.of(MAX), Days.of(MAX).abs());
        assertEquals(Days.of(MAX), Days.of(-MAX).abs());
    }

    @Test
    public void abs_throwsWhenTakingMagnitudeOfMinValue() {
        // |Integer.MIN_VALUE| cannot be represented as an int.
        assertThrows(ArithmeticException.class, () -> Days.of(MIN).abs());
    }

    //-----------------------------------------------------------------------
    // addTo(Temporal) / subtractFrom(Temporal)
    //-----------------------------------------------------------------------
    @Test
    public void addTo_advancesTheTemporalByTheDayCount() {
        assertEquals(LocalDate.of(2019, 1, 10), Days.of(0).addTo(LocalDate.of(2019, 1, 10)));
        assertEquals(LocalDate.of(2019, 1, 15), Days.of(5).addTo(LocalDate.of(2019, 1, 10)));
    }

    @Test
    public void subtractFrom_movesTheTemporalBackByTheDayCount() {
        assertEquals(LocalDate.of(2019, 1, 10), Days.of(0).subtractFrom(LocalDate.of(2019, 1, 10)));
        assertEquals(LocalDate.of(2019, 1, 5), Days.of(5).subtractFrom(LocalDate.of(2019, 1, 10)));
    }

    //-----------------------------------------------------------------------
    // toPeriod()
    //-----------------------------------------------------------------------
    @Test
    public void toPeriod_producesAPeriodWithTheSameNumberOfDays() {
        for (int days = -20; days < 20; days++) {
            assertEquals(Period.ofDays(days), Days.of(days).toPeriod());
        }
    }

    //-----------------------------------------------------------------------
    // compareTo(Days)
    //-----------------------------------------------------------------------
    @Test
    public void compareTo_ordersByDayCount() {
        Days fiveDays = Days.of(5);
        Days sixDays = Days.of(6);
        assertEquals(0, fiveDays.compareTo(fiveDays));
        assertEquals(-1, fiveDays.compareTo(sixDays));
        assertEquals(1, sixDays.compareTo(fiveDays));
    }

    @Test
    public void compareTo_throwsOnNull() {
        Days fiveDays = Days.of(5);
        //noinspection DataFlowIssue - testing nulls
        assertThrows(NullPointerException.class, () -> fiveDays.compareTo(null));
    }

    //-----------------------------------------------------------------------
    // equals() / hashCode()
    //-----------------------------------------------------------------------
    @Test
    public void equalsAndHashCode_areBasedOnTheDayCount() {
        new EqualsTester()
            .addEqualityGroup(Days.of(5), Days.of(5))
            .addEqualityGroup(Days.of(6), Days.of(6))
            .testEquals();
    }

    //-----------------------------------------------------------------------
    // toString()
    //-----------------------------------------------------------------------
    @Test
    public void toString_usesTheIso8601PnDFormat() {
        assertEquals("P5D", Days.of(5).toString());
        assertEquals("P-1D", Days.of(-1).toString());
    }

}
