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
 * Tests for {@link Weeks}.
 * <p>
 * The tests are grouped by the behaviour they exercise: constants, factory
 * methods, parsing, queries, arithmetic, conversions and comparison. The two
 * boundary values {@code Integer.MAX_VALUE} and {@code Integer.MIN_VALUE} are
 * used throughout to probe overflow handling.
 */
public class TestWeeks {

    // The largest and smallest week counts a Weeks can hold; used to drive
    // boundary and overflow scenarios so the intent of those tests is explicit.
    private static final int MAX_WEEKS = Integer.MAX_VALUE;
    private static final int MIN_WEEKS = Integer.MIN_VALUE;

    //-----------------------------------------------------------------------
    // Serialization
    //-----------------------------------------------------------------------
    @Test
    public void weeks_isSerializable() {
        assertTrue(Serializable.class.isAssignableFrom(Weeks.class));
    }

    @Test
    public void deserialization_returnsSameSingletonInstance() throws Exception {
        Weeks original = Weeks.ZERO;

        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        try (ObjectOutputStream out = new ObjectOutputStream(bytes)) {
            out.writeObject(original);
        }
        try (ObjectInputStream in = new ObjectInputStream(new ByteArrayInputStream(bytes.toByteArray()))) {
            // readResolve() should hand back the shared ZERO singleton, not a copy.
            assertSame(original, in.readObject());
        }
    }

    //-----------------------------------------------------------------------
    // Constants
    //-----------------------------------------------------------------------
    @Test
    public void zero_isTheSingletonForZeroWeeks() {
        assertSame(Weeks.ZERO, Weeks.of(0));
        assertEquals(Weeks.ZERO, Weeks.of(0));
        assertEquals(0, Weeks.ZERO.getAmount());
        assertFalse(Weeks.ZERO.isNegative());
        assertTrue(Weeks.ZERO.isZero());
        assertFalse(Weeks.ZERO.isPositive());
    }

    @Test
    public void one_isTheSingletonForOneWeek() {
        assertSame(Weeks.ONE, Weeks.of(1));
        assertEquals(Weeks.ONE, Weeks.of(1));
        assertEquals(1, Weeks.ONE.getAmount());
        assertFalse(Weeks.ONE.isNegative());
        assertFalse(Weeks.ONE.isZero());
        assertTrue(Weeks.ONE.isPositive());
    }

    //-----------------------------------------------------------------------
    // Factory: of(int)
    //-----------------------------------------------------------------------
    @Test
    public void of_storesTheGivenAmount_includingTheIntBoundaries() {
        assertEquals(1, Weeks.of(1).getAmount());
        assertEquals(2, Weeks.of(2).getAmount());
        assertEquals(MAX_WEEKS, Weeks.of(MAX_WEEKS).getAmount());
        assertEquals(-1, Weeks.of(-1).getAmount());
        assertEquals(-2, Weeks.of(-2).getAmount());
        assertEquals(MIN_WEEKS, Weeks.of(MIN_WEEKS).getAmount());
    }

    @Test
    public void of_minusOne_isNegativeAndNeitherZeroNorPositive() {
        Weeks minusOne = Weeks.of(-1);

        assertEquals(-1, minusOne.getAmount());
        assertTrue(minusOne.isNegative());
        assertFalse(minusOne.isZero());
        assertFalse(minusOne.isPositive());
    }

    //-----------------------------------------------------------------------
    // Factory: from(TemporalAmount)
    //-----------------------------------------------------------------------
    @Test
    public void from_periodOfZeroWeeks() {
        assertEquals(Weeks.of(0), Weeks.from(Period.ofWeeks(0)));
    }

    @Test
    public void from_periodOfWholeWeeks() {
        assertEquals(Weeks.of(2), Weeks.from(Period.ofWeeks(2)));
    }

    @Test
    public void from_periodOfDays_convertsExactMultiplesOfSeven() {
        // 14 days is exactly 2 weeks.
        assertEquals(Weeks.of(2), Weeks.from(Period.ofDays(14)));
    }

    @Test
    public void from_durationOfDays_convertsExactMultiplesOfSeven() {
        // 14 days is exactly 2 weeks.
        assertEquals(Weeks.of(2), Weeks.from(Duration.ofDays(14)));
    }

    @Test
    public void from_periodOfDays_rejectsNonWholeNumberOfWeeks() {
        // 3 days cannot be expressed as a whole number of weeks.
        assertThrows(DateTimeException.class, () -> Weeks.from(Period.ofDays(3)));
    }

    @Test
    public void from_periodOfMonths_rejectsUnconvertibleUnit() {
        // Months have no fixed conversion to weeks.
        assertThrows(DateTimeException.class, () -> Weeks.from(Period.ofMonths(2)));
    }

    @Test
    public void from_null_throwsNullPointerException() {
        //noinspection DataFlowIssue - testing nulls
        assertThrows(NullPointerException.class, () -> Weeks.from((TemporalAmount) null));
    }

    //-----------------------------------------------------------------------
    // Factory: parse(CharSequence)
    //-----------------------------------------------------------------------
    @Test
    public void parse_acceptsIsoWeekFormatIncludingSigns() {
        assertEquals(Weeks.of(0), Weeks.parse("P0W"));
        assertEquals(Weeks.of(1), Weeks.parse("P1W"));
        assertEquals(Weeks.of(2), Weeks.parse("P2W"));
        assertEquals(Weeks.of(123456789), Weeks.parse("P123456789W"));
        // A sign may appear on the amount, before the 'P', or on both (which cancel out).
        assertEquals(Weeks.of(-2), Weeks.parse("P-2W"));
        assertEquals(Weeks.of(-2), Weeks.parse("-P2W"));
        assertEquals(Weeks.of(2), Weeks.parse("-P-2W"));
    }

    public static Object[][] data_invalid() {
        return new Object[][] {
            // Right shape, wrong unit.
            {"P3Y"},
            {"P3M"},
            {"P3D"},
            // Missing the 'P' prefix and/or 'W' suffix.
            {"3"},
            {"-3"},
            {"3Y"},
            {"-3Y"},
            {"P3"},
            {"P-3"},
            // Missing the numeric amount.
            {"PY"},
        };
    }

    @ParameterizedTest
    @MethodSource("data_invalid")
    public void parse_rejectsMalformedText(String text) {
        assertThrows(DateTimeParseException.class, () -> Weeks.parse(text));
    }

    @Test
    public void parse_null_throwsNullPointerException() {
        //noinspection DataFlowIssue - testing nulls
        assertThrows(NullPointerException.class, () -> Weeks.parse((CharSequence) null));
    }

    //-----------------------------------------------------------------------
    // Factory: between(Temporal, Temporal)
    //-----------------------------------------------------------------------
    @Test
    public void between_countsWholeWeeksFromStartToEnd() {
        // 2019-01-01 to 2021-01-01 spans exactly 104 weeks.
        assertEquals(Weeks.of(104), Weeks.between(LocalDate.of(2019, 1, 1), LocalDate.of(2021, 1, 1)));
    }

    @Test
    public void between_nullEndDate_throwsNullPointerException() {
        //noinspection DataFlowIssue - testing nulls
        assertThrows(NullPointerException.class, () -> Weeks.between(LocalDate.now(), (Temporal) null));
    }

    @Test
    public void between_nullStartDate_throwsNullPointerException() {
        //noinspection DataFlowIssue - testing nulls
        assertThrows(NullPointerException.class, () -> Weeks.between((Temporal) null, LocalDate.now()));
    }

    //-----------------------------------------------------------------------
    // get(TemporalUnit)
    //-----------------------------------------------------------------------
    @Test
    public void get_weeksUnit_returnsTheAmount() {
        assertEquals(6, Weeks.of(6).get(ChronoUnit.WEEKS));
    }

    @Test
    public void get_unsupportedUnit_throwsDateTimeException() {
        assertThrows(DateTimeException.class, () -> Weeks.of(6).get(IsoFields.QUARTER_YEARS));
    }

    //-----------------------------------------------------------------------
    // plus(TemporalAmount)
    //-----------------------------------------------------------------------
    @Test
    public void plus_weeksAmount_addsTheWeeks() {
        Weeks five = Weeks.of(5);

        assertEquals(Weeks.of(5), five.plus(Weeks.of(0)));
        assertEquals(Weeks.of(7), five.plus(Weeks.of(2)));
        assertEquals(Weeks.of(3), five.plus(Weeks.of(-2)));
        assertEquals(Weeks.of(MAX_WEEKS), Weeks.of(MAX_WEEKS - 1).plus(Weeks.of(1)));
        assertEquals(Weeks.of(MIN_WEEKS), Weeks.of(MIN_WEEKS + 1).plus(Weeks.of(-1)));
    }

    @Test
    public void plus_periodOfWeeks_addsTheWeeks() {
        Weeks five = Weeks.of(5);

        assertEquals(Weeks.of(5), five.plus(Period.ofWeeks(0)));
        assertEquals(Weeks.of(7), five.plus(Period.ofWeeks(2)));
        assertEquals(Weeks.of(3), five.plus(Period.ofWeeks(-2)));
        assertEquals(Weeks.of(MAX_WEEKS), Weeks.of(MAX_WEEKS - 1).plus(Period.ofWeeks(1)));
        assertEquals(Weeks.of(MIN_WEEKS), Weeks.of(MIN_WEEKS + 1).plus(Period.ofWeeks(-1)));
    }

    @Test
    public void plus_periodOfMonths_throwsDateTimeException() {
        assertThrows(DateTimeException.class, () -> Weeks.of(1).plus(Period.ofMonths(2)));
    }

    @Test
    public void plus_duration_throwsDateTimeException() {
        assertThrows(DateTimeException.class, () -> Weeks.of(1).plus(Duration.ofHours(2)));
    }

    @Test
    public void plus_amount_overflowAboveMax_throwsArithmeticException() {
        assertThrows(ArithmeticException.class, () -> Weeks.of(MAX_WEEKS - 1).plus(Weeks.of(2)));
    }

    @Test
    public void plus_amount_overflowBelowMin_throwsArithmeticException() {
        assertThrows(ArithmeticException.class, () -> Weeks.of(MIN_WEEKS + 1).plus(Weeks.of(-2)));
    }

    @Test
    public void plus_nullAmount_throwsNullPointerException() {
        //noinspection DataFlowIssue - testing nulls
        assertThrows(NullPointerException.class, () -> Weeks.of(MIN_WEEKS + 1).plus(null));
    }

    //-----------------------------------------------------------------------
    // plus(int)
    //-----------------------------------------------------------------------
    @Test
    public void plus_int_addsTheWeeks() {
        Weeks five = Weeks.of(5);

        assertEquals(Weeks.of(5), five.plus(0));
        assertEquals(Weeks.of(7), five.plus(2));
        assertEquals(Weeks.of(3), five.plus(-2));
        assertEquals(Weeks.of(MAX_WEEKS), Weeks.of(MAX_WEEKS - 1).plus(1));
        assertEquals(Weeks.of(MIN_WEEKS), Weeks.of(MIN_WEEKS + 1).plus(-1));
    }

    @Test
    public void plus_int_overflowAboveMax_throwsArithmeticException() {
        assertThrows(ArithmeticException.class, () -> Weeks.of(MAX_WEEKS - 1).plus(2));
    }

    @Test
    public void plus_int_overflowBelowMin_throwsArithmeticException() {
        assertThrows(ArithmeticException.class, () -> Weeks.of(MIN_WEEKS + 1).plus(-2));
    }

    //-----------------------------------------------------------------------
    // minus(TemporalAmount)
    //-----------------------------------------------------------------------
    @Test
    public void minus_weeksAmount_subtractsTheWeeks() {
        Weeks five = Weeks.of(5);

        assertEquals(Weeks.of(5), five.minus(Weeks.of(0)));
        assertEquals(Weeks.of(3), five.minus(Weeks.of(2)));
        assertEquals(Weeks.of(7), five.minus(Weeks.of(-2)));
        assertEquals(Weeks.of(MAX_WEEKS), Weeks.of(MAX_WEEKS - 1).minus(Weeks.of(-1)));
        assertEquals(Weeks.of(MIN_WEEKS), Weeks.of(MIN_WEEKS + 1).minus(Weeks.of(1)));
    }

    @Test
    public void minus_periodOfWeeks_subtractsTheWeeks() {
        Weeks five = Weeks.of(5);

        assertEquals(Weeks.of(5), five.minus(Period.ofWeeks(0)));
        assertEquals(Weeks.of(3), five.minus(Period.ofWeeks(2)));
        assertEquals(Weeks.of(7), five.minus(Period.ofWeeks(-2)));
        assertEquals(Weeks.of(MAX_WEEKS), Weeks.of(MAX_WEEKS - 1).minus(Period.ofWeeks(-1)));
        assertEquals(Weeks.of(MIN_WEEKS), Weeks.of(MIN_WEEKS + 1).minus(Period.ofWeeks(1)));
    }

    @Test
    public void minus_periodOfMonths_throwsDateTimeException() {
        assertThrows(DateTimeException.class, () -> Weeks.of(1).minus(Period.ofMonths(2)));
    }

    @Test
    public void minus_duration_throwsDateTimeException() {
        assertThrows(DateTimeException.class, () -> Weeks.of(1).minus(Duration.ofHours(2)));
    }

    @Test
    public void minus_amount_overflowAboveMax_throwsArithmeticException() {
        assertThrows(ArithmeticException.class, () -> Weeks.of(MAX_WEEKS - 1).minus(Weeks.of(-2)));
    }

    @Test
    public void minus_amount_overflowBelowMin_throwsArithmeticException() {
        assertThrows(ArithmeticException.class, () -> Weeks.of(MIN_WEEKS + 1).minus(Weeks.of(2)));
    }

    @Test
    public void minus_nullAmount_throwsNullPointerException() {
        //noinspection DataFlowIssue - testing nulls
        assertThrows(NullPointerException.class, () -> Weeks.of(MIN_WEEKS + 1).minus(null));
    }

    //-----------------------------------------------------------------------
    // minus(int)
    //-----------------------------------------------------------------------
    @Test
    public void minus_int_subtractsTheWeeks() {
        Weeks five = Weeks.of(5);

        assertEquals(Weeks.of(5), five.minus(0));
        assertEquals(Weeks.of(3), five.minus(2));
        assertEquals(Weeks.of(7), five.minus(-2));
        assertEquals(Weeks.of(MAX_WEEKS), Weeks.of(MAX_WEEKS - 1).minus(-1));
        assertEquals(Weeks.of(MIN_WEEKS), Weeks.of(MIN_WEEKS + 1).minus(1));
    }

    @Test
    public void minus_int_overflowAboveMax_throwsArithmeticException() {
        assertThrows(ArithmeticException.class, () -> Weeks.of(MAX_WEEKS - 1).minus(-2));
    }

    @Test
    public void minus_int_overflowBelowMin_throwsArithmeticException() {
        assertThrows(ArithmeticException.class, () -> Weeks.of(MIN_WEEKS + 1).minus(2));
    }

    //-----------------------------------------------------------------------
    // multipliedBy(int)
    //-----------------------------------------------------------------------
    @Test
    public void multipliedBy_scalesTheAmount() {
        Weeks five = Weeks.of(5);

        assertEquals(Weeks.of(0), five.multipliedBy(0));
        assertEquals(Weeks.of(5), five.multipliedBy(1));
        assertEquals(Weeks.of(10), five.multipliedBy(2));
        assertEquals(Weeks.of(15), five.multipliedBy(3));
        assertEquals(Weeks.of(-15), five.multipliedBy(-3));
    }

    @Test
    public void multipliedBy_negativeScalar_negatesAndScales() {
        Weeks five = Weeks.of(5);

        assertEquals(Weeks.of(-15), five.multipliedBy(-3));
    }

    @Test
    public void multipliedBy_overflowAboveMax_throwsArithmeticException() {
        assertThrows(ArithmeticException.class, () -> Weeks.of(MAX_WEEKS / 2 + 1).multipliedBy(2));
    }

    @Test
    public void multipliedBy_overflowBelowMin_throwsArithmeticException() {
        assertThrows(ArithmeticException.class, () -> Weeks.of(MIN_WEEKS / 2 - 1).multipliedBy(2));
    }

    //-----------------------------------------------------------------------
    // dividedBy(int)
    //-----------------------------------------------------------------------
    @Test
    public void dividedBy_usesIntegerDivision() {
        Weeks twelve = Weeks.of(12);

        assertEquals(Weeks.of(12), twelve.dividedBy(1));
        assertEquals(Weeks.of(6), twelve.dividedBy(2));
        assertEquals(Weeks.of(4), twelve.dividedBy(3));
        assertEquals(Weeks.of(3), twelve.dividedBy(4));
        assertEquals(Weeks.of(2), twelve.dividedBy(5)); // 12 / 5 == 2 (remainder dropped)
        assertEquals(Weeks.of(2), twelve.dividedBy(6));
        assertEquals(Weeks.of(-4), twelve.dividedBy(-3));
    }

    @Test
    public void dividedBy_negativeDivisor_negatesResult() {
        Weeks twelve = Weeks.of(12);

        assertEquals(Weeks.of(-4), twelve.dividedBy(-3));
    }

    @Test
    public void dividedBy_zero_throwsArithmeticException() {
        assertThrows(ArithmeticException.class, () -> Weeks.of(1).dividedBy(0));
    }

    //-----------------------------------------------------------------------
    // negated()
    //-----------------------------------------------------------------------
    @Test
    public void negated_flipsTheSign() {
        assertEquals(Weeks.of(0), Weeks.of(0).negated());
        assertEquals(Weeks.of(-12), Weeks.of(12).negated());
        assertEquals(Weeks.of(12), Weeks.of(-12).negated());
        assertEquals(Weeks.of(-MAX_WEEKS), Weeks.of(MAX_WEEKS).negated());
    }

    @Test
    public void negated_minValue_throwsArithmeticException() {
        // -Integer.MIN_VALUE is not representable as an int.
        assertThrows(ArithmeticException.class, () -> Weeks.of(MIN_WEEKS).negated());
    }

    //-----------------------------------------------------------------------
    // abs()
    //-----------------------------------------------------------------------
    @Test
    public void abs_returnsTheMagnitude() {
        assertEquals(Weeks.of(0), Weeks.of(0).abs());
        assertEquals(Weeks.of(12), Weeks.of(12).abs());
        assertEquals(Weeks.of(12), Weeks.of(-12).abs());
        assertEquals(Weeks.of(MAX_WEEKS), Weeks.of(MAX_WEEKS).abs());
        assertEquals(Weeks.of(MAX_WEEKS), Weeks.of(-MAX_WEEKS).abs());
    }

    @Test
    public void abs_minValue_throwsArithmeticException() {
        // |Integer.MIN_VALUE| is not representable as an int.
        assertThrows(ArithmeticException.class, () -> Weeks.of(MIN_WEEKS).abs());
    }

    //-----------------------------------------------------------------------
    // addTo / subtractFrom
    //-----------------------------------------------------------------------
    @Test
    public void addTo_advancesTheTemporalByWholeWeeks() {
        LocalDate start = LocalDate.of(2019, 1, 10);

        assertEquals(start, Weeks.of(0).addTo(start));
        assertEquals(LocalDate.of(2019, 2, 14), Weeks.of(5).addTo(start));
    }

    @Test
    public void subtractFrom_rewindsTheTemporalByWholeWeeks() {
        LocalDate start = LocalDate.of(2019, 1, 10);

        assertEquals(start, Weeks.of(0).subtractFrom(start));
        assertEquals(LocalDate.of(2018, 12, 6), Weeks.of(5).subtractFrom(start));
    }

    //-----------------------------------------------------------------------
    // toPeriod()
    //-----------------------------------------------------------------------
    @Test
    public void toPeriod_producesAPeriodWithTheSameWeeks() {
        for (int weeks = -20; weeks < 20; weeks++) {
            assertEquals(Period.ofWeeks(weeks), Weeks.of(weeks).toPeriod());
        }
    }

    //-----------------------------------------------------------------------
    // compareTo()
    //-----------------------------------------------------------------------
    @Test
    public void compareTo_ordersByAmount() {
        Weeks five = Weeks.of(5);
        Weeks six = Weeks.of(6);

        assertEquals(0, five.compareTo(five));
        assertEquals(-1, five.compareTo(six));
        assertEquals(1, six.compareTo(five));
    }

    @Test
    public void compareTo_null_throwsNullPointerException() {
        Weeks five = Weeks.of(5);
        //noinspection DataFlowIssue - testing nulls
        assertThrows(NullPointerException.class, () -> five.compareTo(null));
    }

    //-----------------------------------------------------------------------
    // equals() and hashCode()
    //-----------------------------------------------------------------------
    @Test
    public void equals_and_hashCode_areBasedOnTheAmount() {
        new EqualsTester()
            .addEqualityGroup(Weeks.of(5), Weeks.of(5))
            .addEqualityGroup(Weeks.of(6), Weeks.of(6))
            .testEquals();
    }

    //-----------------------------------------------------------------------
    // toString()
    //-----------------------------------------------------------------------
    @Test
    public void toString_usesIsoWeekFormat() {
        assertEquals("P5W", Weeks.of(5).toString());
        assertEquals("P-1W", Weeks.of(-1).toString());
    }

}
