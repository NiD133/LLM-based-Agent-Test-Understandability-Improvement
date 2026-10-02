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
 * Test class for {@link Months}.
 */
public class TestMonths {

    // The largest/smallest amounts a Months can hold; used to probe the
    // boundaries where arithmetic operations start to overflow an int.
    private static final int MAX = Integer.MAX_VALUE;
    private static final int MIN = Integer.MIN_VALUE;

    //-----------------------------------------------------------------------
    // serialization
    //-----------------------------------------------------------------------
    @Test
    public void test_isSerializable() {
        assertTrue(Serializable.class.isAssignableFrom(Months.class));
    }

    @Test
    public void test_deserializationSingleton() throws Exception {
        Months original = Months.ZERO;

        // Round-trip the singleton through serialization...
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try (ObjectOutputStream oos = new ObjectOutputStream(baos)) {
            oos.writeObject(original);
        }

        // ...and confirm the very same singleton instance comes back.
        try (ObjectInputStream ois = new ObjectInputStream(new ByteArrayInputStream(baos.toByteArray()))) {
            assertSame(original, ois.readObject());
        }
    }

    //-----------------------------------------------------------------------
    // constants: ZERO and ONE
    //-----------------------------------------------------------------------
    @Test
    public void test_ZERO() {
        assertSame(Months.ZERO, Months.of(0));
        assertEquals(Months.ZERO, Months.of(0));
        assertEquals(0, Months.ZERO.getAmount());
        assertFalse(Months.ZERO.isNegative());
        assertTrue(Months.ZERO.isZero());
        assertFalse(Months.ZERO.isPositive());
    }

    @Test
    public void test_ONE() {
        assertSame(Months.ONE, Months.of(1));
        assertEquals(Months.ONE, Months.of(1));
        assertEquals(1, Months.ONE.getAmount());
        assertFalse(Months.ONE.isNegative());
        assertFalse(Months.ONE.isZero());
        assertTrue(Months.ONE.isPositive());
    }

    //-----------------------------------------------------------------------
    // of(int)
    //-----------------------------------------------------------------------
    @Test
    public void test_of() {
        assertEquals(0, Months.of(0).getAmount());
        assertEquals(1, Months.of(1).getAmount());
        assertEquals(2, Months.of(2).getAmount());
        assertEquals(MAX, Months.of(MAX).getAmount());
        assertEquals(-1, Months.of(-1).getAmount());
        assertEquals(-2, Months.of(-2).getAmount());
        assertEquals(MIN, Months.of(MIN).getAmount());
    }

    @Test
    public void test_ofMinusOne() {
        assertEquals(-1, Months.of(-1).getAmount());
        assertTrue(Months.of(-1).isNegative());
        assertFalse(Months.of(-1).isZero());
        assertFalse(Months.of(-1).isPositive());
    }

    //-----------------------------------------------------------------------
    // ofYears(int) - one year equals 12 months
    //-----------------------------------------------------------------------
    @Test
    public void test_ofYears() {
        assertEquals(0, Months.ofYears(0).getAmount());
        assertEquals(12, Months.ofYears(1).getAmount());
        assertEquals(24, Months.ofYears(2).getAmount());
        // Largest whole number of years that still fits in an int once multiplied by 12.
        assertEquals((MAX / 12) * 12, Months.ofYears(MAX / 12).getAmount());
        assertEquals(-12, Months.ofYears(-1).getAmount());
        assertEquals(-24, Months.ofYears(-2).getAmount());
        assertEquals((MIN / 12) * 12, Months.ofYears(MIN / 12).getAmount());
    }

    @Test
    public void test_ofYears_overflow() {
        // Multiplying this many years by 12 exceeds Integer.MAX_VALUE.
        assertThrows(ArithmeticException.class, () -> Months.ofYears((MAX / 12) + 12));
    }

    //-----------------------------------------------------------------------
    // from(TemporalAmount)
    //-----------------------------------------------------------------------
    @Test
    public void test_from_Period_P0M() {
        assertEquals(Months.of(0), Months.from(Period.ofMonths(0)));
    }

    @Test
    public void test_from_Period_P2M() {
        assertEquals(Months.of(2), Months.from(Period.ofMonths(2)));
    }

    @Test
    public void test_from_P2Y() {
        // 2 years -> 24 months.
        assertEquals(Months.of(24), Months.from(new MockYearsMonths(2, 0)));
    }

    @Test
    public void test_from_P2Y3M() {
        // 2 years + 3 months -> 27 months.
        assertEquals(Months.of(27), Months.from(new MockYearsMonths(2, 3)));
    }

    @Test
    public void test_from_yearsAndMonths() {
        // 3 years + 5 months -> 41 months.
        assertEquals(Months.of(41), Months.from(Period.of(3, 5, 0)));
    }

    @Test
    public void test_from_wrongUnit_noConversion() {
        // Days cannot be converted to a whole number of months.
        assertThrows(DateTimeException.class, () -> Months.from(Period.ofDays(2)));
    }

    @Test
    public void test_from_null() {
        //noinspection DataFlowIssue - testing nulls
        assertThrows(NullPointerException.class, () -> Months.from((TemporalAmount) null));
    }

    //-----------------------------------------------------------------------
    // parse(CharSequence)
    //-----------------------------------------------------------------------
    public static Object[][] data_valid() {
        return new Object[][] {
            // text, expected number of months
            {"P0M", 0},
            {"P1M", 1},
            {"P2M", 2},
            {"P123456789M", 123456789},
            {"P+0M", 0},
            {"P+2M", 2},
            {"P-0M", 0},
            {"P-2M", -2},

            {"P0Y", 0},
            {"P1Y", 12},
            {"P2Y", 24},
            {"P1234567Y", 1234567 * 12},
            {"P+0Y", 0},
            {"P+2Y", 24},
            {"P-0Y", 0},
            {"P-2Y", -24},

            {"P0Y0M", 0},
            {"P2Y3M", 27},
            {"P+2Y3M", 27},
            {"P2Y+3M", 27},
            {"P-2Y3M", -21},
            {"P2Y-3M", 21},
            {"P-2Y-3M", -27},
        };
    }

    @ParameterizedTest
    @MethodSource("data_valid")
    public void test_parse_CharSequence_valid(String str, int expectedMonths) {
        assertEquals(Months.of(expectedMonths), Months.parse(str));
    }

    @ParameterizedTest
    @MethodSource("data_valid")
    public void test_parse_CharSequence_valid_initialPlus(String str, int expectedMonths) {
        // A leading '+' leaves the parsed value unchanged.
        assertEquals(Months.of(expectedMonths), Months.parse("+" + str));
    }

    @ParameterizedTest
    @MethodSource("data_valid")
    public void test_parse_CharSequence_valid_initialMinus(String str, int expectedMonths) {
        // A leading '-' negates the whole parsed value.
        assertEquals(Months.of(-expectedMonths), Months.parse("-" + str));
    }

    public static Object[][] data_invalid() {
        return new Object[][] {
            {"P3W"},
            {"P3D"},
            {"P3Q"},
            {"P1M2Y"},

            {"3"},
            {"-3"},
            {"3M"},
            {"-3M"},
            {"P3"},
            {"P-3"},
            {"PM"},
        };
    }

    @ParameterizedTest
    @MethodSource("data_invalid")
    public void test_parse_CharSequence_invalid(String str) {
        assertThrows(DateTimeParseException.class, () -> Months.parse(str));
    }

    @Test
    public void test_parse_CharSequence_null() {
        //noinspection DataFlowIssue - testing nulls
        assertThrows(NullPointerException.class, () -> Months.parse((CharSequence) null));
    }

    //-----------------------------------------------------------------------
    // between(Temporal, Temporal)
    //-----------------------------------------------------------------------
    @Test
    public void test_between() {
        // Two full years between the dates -> 24 months.
        assertEquals(Months.of(24), Months.between(LocalDate.of(2019, 1, 1), LocalDate.of(2021, 1, 1)));
    }

    @Test
    public void test_between_date_null() {
        //noinspection DataFlowIssue - testing nulls
        assertThrows(NullPointerException.class, () -> Months.between(LocalDate.now(), (Temporal) null));
    }

    @Test
    public void test_between_null_date() {
        //noinspection DataFlowIssue - testing nulls
        assertThrows(NullPointerException.class, () -> Months.between((Temporal) null, LocalDate.now()));
    }

    //-----------------------------------------------------------------------
    // get(TemporalUnit)
    //-----------------------------------------------------------------------
    @Test
    public void test_get() {
        assertEquals(6, Months.of(6).get(ChronoUnit.MONTHS));
    }

    @Test
    public void test_get_invalidType() {
        // Only the MONTHS unit is supported.
        assertThrows(DateTimeException.class, () -> Months.of(6).get(IsoFields.QUARTER_YEARS));
    }

    //-----------------------------------------------------------------------
    // plus(TemporalAmount)
    //-----------------------------------------------------------------------
    @Test
    public void test_plus_TemporalAmount_Months() {
        Months five = Months.of(5);
        assertEquals(Months.of(5), five.plus(Months.of(0)));
        assertEquals(Months.of(7), five.plus(Months.of(2)));
        assertEquals(Months.of(3), five.plus(Months.of(-2)));
        assertEquals(Months.of(MAX), Months.of(MAX - 1).plus(Months.of(1)));
        assertEquals(Months.of(MIN), Months.of(MIN + 1).plus(Months.of(-1)));
    }

    @Test
    public void test_plus_TemporalAmount_Period() {
        Months five = Months.of(5);
        assertEquals(Months.of(5), five.plus(Period.ofMonths(0)));
        assertEquals(Months.of(7), five.plus(Period.ofMonths(2)));
        assertEquals(Months.of(3), five.plus(Period.ofMonths(-2)));
        assertEquals(Months.of(MAX), Months.of(MAX - 1).plus(Period.ofMonths(1)));
        assertEquals(Months.of(MIN), Months.of(MIN + 1).plus(Period.ofMonths(-1)));
    }

    @Test
    public void test_plus_TemporalAmount_PeriodDays() {
        // A day-based period has no whole-month equivalent.
        assertThrows(DateTimeException.class, () -> Months.of(1).plus(Period.ofDays(2)));
    }

    @Test
    public void test_plus_TemporalAmount_Duration() {
        // A time-based duration cannot be added to a month-based amount.
        assertThrows(DateTimeException.class, () -> Months.of(1).plus(Duration.ofHours(2)));
    }

    @Test
    public void test_plus_TemporalAmount_overflowTooBig() {
        assertThrows(ArithmeticException.class, () -> Months.of(MAX - 1).plus(Months.of(2)));
    }

    @Test
    public void test_plus_TemporalAmount_overflowTooSmall() {
        assertThrows(ArithmeticException.class, () -> Months.of(MIN + 1).plus(Months.of(-2)));
    }

    @Test
    public void test_plus_TemporalAmount_null() {
        //noinspection DataFlowIssue - testing nulls
        assertThrows(NullPointerException.class, () -> Months.of(MIN + 1).plus(null));
    }

    //-----------------------------------------------------------------------
    // plus(int)
    //-----------------------------------------------------------------------
    @Test
    public void test_plus_int() {
        Months five = Months.of(5);
        assertEquals(Months.of(5), five.plus(0));
        assertEquals(Months.of(7), five.plus(2));
        assertEquals(Months.of(3), five.plus(-2));
        assertEquals(Months.of(MAX), Months.of(MAX - 1).plus(1));
        assertEquals(Months.of(MIN), Months.of(MIN + 1).plus(-1));
    }

    @Test
    public void test_plus_int_overflowTooBig() {
        assertThrows(ArithmeticException.class, () -> Months.of(MAX - 1).plus(2));
    }

    @Test
    public void test_plus_int_overflowTooSmall() {
        assertThrows(ArithmeticException.class, () -> Months.of(MIN + 1).plus(-2));
    }

    //-----------------------------------------------------------------------
    // minus(TemporalAmount)
    //-----------------------------------------------------------------------
    @Test
    public void test_minus_TemporalAmount_Months() {
        Months five = Months.of(5);
        assertEquals(Months.of(5), five.minus(Months.of(0)));
        assertEquals(Months.of(3), five.minus(Months.of(2)));
        assertEquals(Months.of(7), five.minus(Months.of(-2)));
        assertEquals(Months.of(MAX), Months.of(MAX - 1).minus(Months.of(-1)));
        assertEquals(Months.of(MIN), Months.of(MIN + 1).minus(Months.of(1)));
    }

    @Test
    public void test_minus_TemporalAmount_Period() {
        Months five = Months.of(5);
        assertEquals(Months.of(5), five.minus(Period.ofMonths(0)));
        assertEquals(Months.of(3), five.minus(Period.ofMonths(2)));
        assertEquals(Months.of(7), five.minus(Period.ofMonths(-2)));
        assertEquals(Months.of(MAX), Months.of(MAX - 1).minus(Period.ofMonths(-1)));
        assertEquals(Months.of(MIN), Months.of(MIN + 1).minus(Period.ofMonths(1)));
    }

    @Test
    public void test_minus_TemporalAmount_PeriodDays() {
        assertThrows(DateTimeException.class, () -> Months.of(1).minus(Period.ofDays(2)));
    }

    @Test
    public void test_minus_TemporalAmount_Duration() {
        assertThrows(DateTimeException.class, () -> Months.of(1).minus(Duration.ofHours(2)));
    }

    @Test
    public void test_minus_TemporalAmount_overflowTooBig() {
        assertThrows(ArithmeticException.class, () -> Months.of(MAX - 1).minus(Months.of(-2)));
    }

    @Test
    public void test_minus_TemporalAmount_overflowTooSmall() {
        assertThrows(ArithmeticException.class, () -> Months.of(MIN + 1).minus(Months.of(2)));
    }

    @Test
    public void test_minus_TemporalAmount_null() {
        //noinspection DataFlowIssue - testing nulls
        assertThrows(NullPointerException.class, () -> Months.of(MIN + 1).minus(null));
    }

    //-----------------------------------------------------------------------
    // minus(int)
    //-----------------------------------------------------------------------
    @Test
    public void test_minus_int() {
        Months five = Months.of(5);
        assertEquals(Months.of(5), five.minus(0));
        assertEquals(Months.of(3), five.minus(2));
        assertEquals(Months.of(7), five.minus(-2));
        assertEquals(Months.of(MAX), Months.of(MAX - 1).minus(-1));
        assertEquals(Months.of(MIN), Months.of(MIN + 1).minus(1));
    }

    @Test
    public void test_minus_int_overflowTooBig() {
        assertThrows(ArithmeticException.class, () -> Months.of(MAX - 1).minus(-2));
    }

    @Test
    public void test_minus_int_overflowTooSmall() {
        assertThrows(ArithmeticException.class, () -> Months.of(MIN + 1).minus(2));
    }

    //-----------------------------------------------------------------------
    // multipliedBy(int)
    //-----------------------------------------------------------------------
    @Test
    public void test_multipliedBy() {
        Months five = Months.of(5);
        assertEquals(Months.of(0), five.multipliedBy(0));
        assertEquals(Months.of(5), five.multipliedBy(1));
        assertEquals(Months.of(10), five.multipliedBy(2));
        assertEquals(Months.of(15), five.multipliedBy(3));
        assertEquals(Months.of(-15), five.multipliedBy(-3));
    }

    @Test
    public void test_multipliedBy_negate() {
        Months five = Months.of(5);
        assertEquals(Months.of(-15), five.multipliedBy(-3));
    }

    @Test
    public void test_multipliedBy_overflowTooBig() {
        // Just over half of MAX, so doubling overflows.
        assertThrows(ArithmeticException.class, () -> Months.of(MAX / 2 + 1).multipliedBy(2));
    }

    @Test
    public void test_multipliedBy_overflowTooSmall() {
        // Just below half of MIN, so doubling overflows.
        assertThrows(ArithmeticException.class, () -> Months.of(MIN / 2 - 1).multipliedBy(2));
    }

    //-----------------------------------------------------------------------
    // dividedBy(int)
    //-----------------------------------------------------------------------
    @Test
    public void test_dividedBy() {
        Months twelve = Months.of(12);
        assertEquals(Months.of(12), twelve.dividedBy(1));
        assertEquals(Months.of(6), twelve.dividedBy(2));
        assertEquals(Months.of(4), twelve.dividedBy(3));
        assertEquals(Months.of(3), twelve.dividedBy(4));
        assertEquals(Months.of(2), twelve.dividedBy(5));   // integer division truncates
        assertEquals(Months.of(2), twelve.dividedBy(6));
        assertEquals(Months.of(-4), twelve.dividedBy(-3));
    }

    @Test
    public void test_dividedBy_negate() {
        Months twelve = Months.of(12);
        assertEquals(Months.of(-4), twelve.dividedBy(-3));
    }

    @Test
    public void test_dividedBy_divideByZero() {
        assertThrows(ArithmeticException.class, () -> Months.of(1).dividedBy(0));
    }

    //-----------------------------------------------------------------------
    // negated()
    //-----------------------------------------------------------------------
    @Test
    public void test_negated() {
        assertEquals(Months.of(0), Months.of(0).negated());
        assertEquals(Months.of(-12), Months.of(12).negated());
        assertEquals(Months.of(12), Months.of(-12).negated());
        assertEquals(Months.of(-MAX), Months.of(MAX).negated());
    }

    @Test
    public void test_negated_overflow() {
        // MIN has no positive counterpart in int range.
        assertThrows(ArithmeticException.class, () -> Months.of(MIN).negated());
    }

    //-----------------------------------------------------------------------
    // abs()
    //-----------------------------------------------------------------------
    @Test
    public void test_abs() {
        assertEquals(Months.of(0), Months.of(0).abs());
        assertEquals(Months.of(12), Months.of(12).abs());
        assertEquals(Months.of(12), Months.of(-12).abs());
        assertEquals(Months.of(MAX), Months.of(MAX).abs());
        assertEquals(Months.of(MAX), Months.of(-MAX).abs());
    }

    @Test
    public void test_abs_overflow() {
        // |MIN| cannot be represented as a positive int.
        assertThrows(ArithmeticException.class, () -> Months.of(MIN).abs());
    }

    //-----------------------------------------------------------------------
    // addTo / subtractFrom a Temporal
    //-----------------------------------------------------------------------
    @Test
    public void test_addTo() {
        assertEquals(LocalDate.of(2019, 1, 10), Months.of(0).addTo(LocalDate.of(2019, 1, 10)));
        assertEquals(LocalDate.of(2019, 6, 10), Months.of(5).addTo(LocalDate.of(2019, 1, 10)));
    }

    @Test
    public void test_subtractFrom() {
        assertEquals(LocalDate.of(2019, 1, 10), Months.of(0).subtractFrom(LocalDate.of(2019, 1, 10)));
        assertEquals(LocalDate.of(2018, 8, 10), Months.of(5).subtractFrom(LocalDate.of(2019, 1, 10)));
    }

    //-----------------------------------------------------------------------
    // toPeriod()
    //-----------------------------------------------------------------------
    @Test
    public void test_toPeriod() {
        // Every amount maps to an equivalent month-based Period.
        for (int months = -20; months < 20; months++) {
            assertEquals(Period.ofMonths(months), Months.of(months).toPeriod());
        }
    }

    //-----------------------------------------------------------------------
    // compareTo()
    //-----------------------------------------------------------------------
    @Test
    public void test_compareTo() {
        Months five = Months.of(5);
        Months six = Months.of(6);
        assertEquals(0, five.compareTo(five));
        assertEquals(-1, five.compareTo(six));
        assertEquals(1, six.compareTo(five));
    }

    @Test
    public void test_compareTo_null() {
        Months five = Months.of(5);
        //noinspection DataFlowIssue - testing nulls
        assertThrows(NullPointerException.class, () -> five.compareTo(null));
    }

    //-----------------------------------------------------------------------
    // equals() and hashCode()
    //-----------------------------------------------------------------------
    @Test
    public void test_equals_and_hashCode() {
        new EqualsTester()
            .addEqualityGroup(Months.of(5), Months.of(5))
            .addEqualityGroup(Months.of(6), Months.of(6))
            .testEquals();
    }

    //-----------------------------------------------------------------------
    // toString()
    //-----------------------------------------------------------------------
    @Test
    public void test_toString() {
        assertEquals("P5M", Months.of(5).toString());
        assertEquals("P-1M", Months.of(-1).toString());
    }

}
