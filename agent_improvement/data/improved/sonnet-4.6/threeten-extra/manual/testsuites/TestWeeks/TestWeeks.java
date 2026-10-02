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
 * Test class.
 */
public class TestWeeks {

    // Aliases used throughout to keep boundary-value assertions concise.
    private static final int INT_MAX = Integer.MAX_VALUE;
    private static final int INT_MIN = Integer.MIN_VALUE;

    //-----------------------------------------------------------------------
    @Test
    public void test_isSerializable() {
        assertTrue(Serializable.class.isAssignableFrom(Weeks.class));
    }

    //-----------------------------------------------------------------------
    @Test
    public void test_deserializationSingleton() throws Exception {
        Weeks test = Weeks.ZERO;
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try (ObjectOutputStream oos = new ObjectOutputStream(baos)) {
            oos.writeObject(test);
        }
        try (ObjectInputStream ois = new ObjectInputStream(new ByteArrayInputStream(baos.toByteArray()))) {
            assertSame(test, ois.readObject());
        }
    }

    //-----------------------------------------------------------------------
    @Test
    public void test_ZERO() {
        assertSame(Weeks.ZERO, Weeks.of(0));
        assertEquals(Weeks.ZERO, Weeks.of(0));
        assertEquals(0, Weeks.ZERO.getAmount());
        assertFalse(Weeks.ZERO.isNegative());
        assertTrue(Weeks.ZERO.isZero());
        assertFalse(Weeks.ZERO.isPositive());
    }

    @Test
    public void test_ONE() {
        assertSame(Weeks.ONE, Weeks.of(1));
        assertEquals(Weeks.ONE, Weeks.of(1));
        assertEquals(1, Weeks.ONE.getAmount());
        assertFalse(Weeks.ONE.isNegative());
        assertFalse(Weeks.ONE.isZero());
        assertTrue(Weeks.ONE.isPositive());
    }

    //-----------------------------------------------------------------------
    @Test
    public void test_of() {
        assertEquals(1, Weeks.of(1).getAmount());
        assertEquals(2, Weeks.of(2).getAmount());
        assertEquals(INT_MAX, Weeks.of(INT_MAX).getAmount());
        assertEquals(-1, Weeks.of(-1).getAmount());
        assertEquals(-2, Weeks.of(-2).getAmount());
        assertEquals(INT_MIN, Weeks.of(INT_MIN).getAmount());
    }

    @Test
    public void test_ofMinusOne() {
        assertEquals(-1, Weeks.of(-1).getAmount());
        assertTrue(Weeks.of(-1).isNegative());
        assertFalse(Weeks.of(-1).isZero());
        assertFalse(Weeks.of(-1).isPositive());
    }

    //-----------------------------------------------------------------------
    // Covers P0W (zero weeks), P2W (weeks unit), P14D (days converted to weeks),
    // and a Duration (also convertible to weeks).
    public static Object[][] data_from_valid() {
        return new Object[][] {
            {Period.ofWeeks(0),   Weeks.of(0)},
            {Period.ofWeeks(2),   Weeks.of(2)},
            {Period.ofDays(14),   Weeks.of(2)},
            {Duration.ofDays(14), Weeks.of(2)},
        };
    }

    @ParameterizedTest
    @MethodSource("data_from_valid")
    public void test_from_validAmount(TemporalAmount amount, Weeks expected) {
        assertEquals(expected, Weeks.from(amount));
    }

    @Test
    public void test_from_wrongUnit_remainder() {
        assertThrows(DateTimeException.class, () -> Weeks.from(Period.ofDays(3)));
    }

    @Test
    public void test_from_wrongUnit_noConversion() {
        assertThrows(DateTimeException.class, () -> Weeks.from(Period.ofMonths(2)));
    }

    @Test
    public void test_from_null() {
        //noinspection DataFlowIssue - testing nulls
        assertThrows(NullPointerException.class, () -> Weeks.from((TemporalAmount) null));
    }

    //-----------------------------------------------------------------------
    // Valid ISO-8601 week-period strings and their expected Weeks values.
    public static Object[][] data_parse_valid() {
        return new Object[][] {
            {"P0W",        Weeks.of(0)},
            {"P1W",        Weeks.of(1)},
            {"P2W",        Weeks.of(2)},
            {"P123456789W", Weeks.of(123456789)},
            {"P-2W",       Weeks.of(-2)},
            {"-P2W",       Weeks.of(-2)},
            {"-P-2W",      Weeks.of(2)},
        };
    }

    @ParameterizedTest
    @MethodSource("data_parse_valid")
    public void test_parse_validInput(String text, Weeks expected) {
        assertEquals(expected, Weeks.parse(text));
    }

    public static Object[][] data_invalid() {
        return new Object[][] {
            {"P3Y"},
            {"P3M"},
            {"P3D"},

            {"3"},
            {"-3"},
            {"3Y"},
            {"-3Y"},
            {"P3"},
            {"P-3"},
            {"PY"},
        };
    }

    @ParameterizedTest
    @MethodSource("data_invalid")
    public void test_parse_CharSequence_invalid(String str) {
        assertThrows(DateTimeParseException.class, () -> Weeks.parse(str));
    }

    @Test
    public void test_parse_CharSequence_null() {
        //noinspection DataFlowIssue - testing nulls
        assertThrows(NullPointerException.class, () -> Weeks.parse((CharSequence) null));
    }

    //-----------------------------------------------------------------------
    @Test
    public void test_between() {
        assertEquals(Weeks.of(104), Weeks.between(LocalDate.of(2019, 1, 1), LocalDate.of(2021, 1, 1)));
    }

    @Test
    public void test_between_date_null() {
        //noinspection DataFlowIssue - testing nulls
        assertThrows(NullPointerException.class, () -> Weeks.between(LocalDate.now(), (Temporal) null));
    }

    @Test
    public void test_between_null_date() {
        //noinspection DataFlowIssue - testing nulls
        assertThrows(NullPointerException.class, () -> Weeks.between((Temporal) null, LocalDate.now()));
    }

    //-----------------------------------------------------------------------
    @Test
    public void test_get() {
        assertEquals(6, Weeks.of(6).get(ChronoUnit.WEEKS));
    }

    @Test
    public void test_get_invalidType() {
        assertThrows(DateTimeException.class, () -> Weeks.of(6).get(IsoFields.QUARTER_YEARS));
    }

    //-----------------------------------------------------------------------
    @Test
    public void test_plus_TemporalAmount_Weeks() {
        Weeks test5 = Weeks.of(5);
        assertEquals(Weeks.of(5), test5.plus(Weeks.of(0)));
        assertEquals(Weeks.of(7), test5.plus(Weeks.of(2)));
        assertEquals(Weeks.of(3), test5.plus(Weeks.of(-2)));
        assertEquals(Weeks.of(INT_MAX), Weeks.of(INT_MAX - 1).plus(Weeks.of(1)));
        assertEquals(Weeks.of(INT_MIN), Weeks.of(INT_MIN + 1).plus(Weeks.of(-1)));
    }

    @Test
    public void test_plus_TemporalAmount_Period() {
        Weeks test5 = Weeks.of(5);
        assertEquals(Weeks.of(5), test5.plus(Period.ofWeeks(0)));
        assertEquals(Weeks.of(7), test5.plus(Period.ofWeeks(2)));
        assertEquals(Weeks.of(3), test5.plus(Period.ofWeeks(-2)));
        assertEquals(Weeks.of(INT_MAX), Weeks.of(INT_MAX - 1).plus(Period.ofWeeks(1)));
        assertEquals(Weeks.of(INT_MIN), Weeks.of(INT_MIN + 1).plus(Period.ofWeeks(-1)));
    }

    @Test
    public void test_plus_TemporalAmount_PeriodMonths() {
        assertThrows(DateTimeException.class, () -> Weeks.of(1).plus(Period.ofMonths(2)));
    }

    @Test
    public void test_plus_TemporalAmount_Duration() {
        assertThrows(DateTimeException.class, () -> Weeks.of(1).plus(Duration.ofHours(2)));
    }

    @Test
    public void test_plus_TemporalAmount_overflowTooBig() {
        assertThrows(ArithmeticException.class, () -> Weeks.of(INT_MAX - 1).plus(Weeks.of(2)));
    }

    @Test
    public void test_plus_TemporalAmount_overflowTooSmall() {
        assertThrows(ArithmeticException.class, () -> Weeks.of(INT_MIN + 1).plus(Weeks.of(-2)));
    }

    @Test
    public void test_plus_TemporalAmount_null() {
        //noinspection DataFlowIssue - testing nulls
        assertThrows(NullPointerException.class, () -> Weeks.of(INT_MIN + 1).plus(null));
    }

    //-----------------------------------------------------------------------
    @Test
    public void test_plus_int() {
        Weeks test5 = Weeks.of(5);
        assertEquals(Weeks.of(5), test5.plus(0));
        assertEquals(Weeks.of(7), test5.plus(2));
        assertEquals(Weeks.of(3), test5.plus(-2));
        assertEquals(Weeks.of(INT_MAX), Weeks.of(INT_MAX - 1).plus(1));
        assertEquals(Weeks.of(INT_MIN), Weeks.of(INT_MIN + 1).plus(-1));
    }

    @Test
    public void test_plus_int_overflowTooBig() {
        assertThrows(ArithmeticException.class, () -> Weeks.of(INT_MAX - 1).plus(2));
    }

    @Test
    public void test_plus_int_overflowTooSmall() {
        assertThrows(ArithmeticException.class, () -> Weeks.of(INT_MIN + 1).plus(-2));
    }

    //-----------------------------------------------------------------------
    @Test
    public void test_minus_TemporalAmount_Weeks() {
        Weeks test5 = Weeks.of(5);
        assertEquals(Weeks.of(5), test5.minus(Weeks.of(0)));
        assertEquals(Weeks.of(3), test5.minus(Weeks.of(2)));
        assertEquals(Weeks.of(7), test5.minus(Weeks.of(-2)));
        assertEquals(Weeks.of(INT_MAX), Weeks.of(INT_MAX - 1).minus(Weeks.of(-1)));
        assertEquals(Weeks.of(INT_MIN), Weeks.of(INT_MIN + 1).minus(Weeks.of(1)));
    }

    @Test
    public void test_minus_TemporalAmount_Period() {
        Weeks test5 = Weeks.of(5);
        assertEquals(Weeks.of(5), test5.minus(Period.ofWeeks(0)));
        assertEquals(Weeks.of(3), test5.minus(Period.ofWeeks(2)));
        assertEquals(Weeks.of(7), test5.minus(Period.ofWeeks(-2)));
        assertEquals(Weeks.of(INT_MAX), Weeks.of(INT_MAX - 1).minus(Period.ofWeeks(-1)));
        assertEquals(Weeks.of(INT_MIN), Weeks.of(INT_MIN + 1).minus(Period.ofWeeks(1)));
    }

    @Test
    public void test_minus_TemporalAmount_PeriodMonths() {
        assertThrows(DateTimeException.class, () -> Weeks.of(1).minus(Period.ofMonths(2)));
    }

    @Test
    public void test_minus_TemporalAmount_Duration() {
        assertThrows(DateTimeException.class, () -> Weeks.of(1).minus(Duration.ofHours(2)));
    }

    @Test
    public void test_minus_TemporalAmount_overflowTooBig() {
        assertThrows(ArithmeticException.class, () -> Weeks.of(INT_MAX - 1).minus(Weeks.of(-2)));
    }

    @Test
    public void test_minus_TemporalAmount_overflowTooSmall() {
        assertThrows(ArithmeticException.class, () -> Weeks.of(INT_MIN + 1).minus(Weeks.of(2)));
    }

    @Test
    public void test_minus_TemporalAmount_null() {
        //noinspection DataFlowIssue - testing nulls
        assertThrows(NullPointerException.class, () -> Weeks.of(INT_MIN + 1).minus(null));
    }

    //-----------------------------------------------------------------------
    @Test
    public void test_minus_int() {
        Weeks test5 = Weeks.of(5);
        assertEquals(Weeks.of(5), test5.minus(0));
        assertEquals(Weeks.of(3), test5.minus(2));
        assertEquals(Weeks.of(7), test5.minus(-2));
        assertEquals(Weeks.of(INT_MAX), Weeks.of(INT_MAX - 1).minus(-1));
        assertEquals(Weeks.of(INT_MIN), Weeks.of(INT_MIN + 1).minus(1));
    }

    @Test
    public void test_minus_int_overflowTooBig() {
        assertThrows(ArithmeticException.class, () -> Weeks.of(INT_MAX - 1).minus(-2));
    }

    @Test
    public void test_minus_int_overflowTooSmall() {
        assertThrows(ArithmeticException.class, () -> Weeks.of(INT_MIN + 1).minus(2));
    }

    //-----------------------------------------------------------------------
    @Test
    public void test_multipliedBy() {
        Weeks test5 = Weeks.of(5);
        assertEquals(Weeks.of(0), test5.multipliedBy(0));
        assertEquals(Weeks.of(5), test5.multipliedBy(1));
        assertEquals(Weeks.of(10), test5.multipliedBy(2));
        assertEquals(Weeks.of(15), test5.multipliedBy(3));
        assertEquals(Weeks.of(-15), test5.multipliedBy(-3));
    }

    @Test
    public void test_multipliedBy_negate() {
        Weeks test5 = Weeks.of(5);
        assertEquals(Weeks.of(-15), test5.multipliedBy(-3));
    }

    @Test
    public void test_multipliedBy_overflowTooBig() {
        assertThrows(ArithmeticException.class, () -> Weeks.of(INT_MAX / 2 + 1).multipliedBy(2));
    }

    @Test
    public void test_multipliedBy_overflowTooSmall() {
        assertThrows(ArithmeticException.class, () -> Weeks.of(INT_MIN / 2 - 1).multipliedBy(2));
    }

    //-----------------------------------------------------------------------
    @Test
    public void test_dividedBy() {
        Weeks test12 = Weeks.of(12);
        assertEquals(Weeks.of(12), test12.dividedBy(1));
        assertEquals(Weeks.of(6), test12.dividedBy(2));
        assertEquals(Weeks.of(4), test12.dividedBy(3));
        assertEquals(Weeks.of(3), test12.dividedBy(4));
        assertEquals(Weeks.of(2), test12.dividedBy(5));
        assertEquals(Weeks.of(2), test12.dividedBy(6));
        assertEquals(Weeks.of(-4), test12.dividedBy(-3));
    }

    @Test
    public void test_dividedBy_negate() {
        Weeks test12 = Weeks.of(12);
        assertEquals(Weeks.of(-4), test12.dividedBy(-3));
    }

    @Test
    public void test_dividedBy_divideByZero() {
        assertThrows(ArithmeticException.class, () -> Weeks.of(1).dividedBy(0));
    }

    //-----------------------------------------------------------------------
    @Test
    public void test_negated() {
        assertEquals(Weeks.of(0), Weeks.of(0).negated());
        assertEquals(Weeks.of(-12), Weeks.of(12).negated());
        assertEquals(Weeks.of(12), Weeks.of(-12).negated());
        assertEquals(Weeks.of(-INT_MAX), Weeks.of(INT_MAX).negated());
    }

    @Test
    public void test_negated_overflow() {
        assertThrows(ArithmeticException.class, () -> Weeks.of(INT_MIN).negated());
    }

    //-----------------------------------------------------------------------
    @Test
    public void test_abs() {
        assertEquals(Weeks.of(0), Weeks.of(0).abs());
        assertEquals(Weeks.of(12), Weeks.of(12).abs());
        assertEquals(Weeks.of(12), Weeks.of(-12).abs());
        assertEquals(Weeks.of(INT_MAX), Weeks.of(INT_MAX).abs());
        assertEquals(Weeks.of(INT_MAX), Weeks.of(-INT_MAX).abs());
    }

    @Test
    public void test_abs_overflow() {
        assertThrows(ArithmeticException.class, () -> Weeks.of(INT_MIN).abs());
    }

    //-----------------------------------------------------------------------
    @Test
    public void test_addTo() {
        assertEquals(LocalDate.of(2019, 1, 10), Weeks.of(0).addTo(LocalDate.of(2019, 1, 10)));
        assertEquals(LocalDate.of(2019, 2, 14), Weeks.of(5).addTo(LocalDate.of(2019, 1, 10)));
    }

    @Test
    public void test_subtractFrom() {
        assertEquals(LocalDate.of(2019, 1, 10), Weeks.of(0).subtractFrom(LocalDate.of(2019, 1, 10)));
        assertEquals(LocalDate.of(2018, 12, 6), Weeks.of(5).subtractFrom(LocalDate.of(2019, 1, 10)));
    }

    //-----------------------------------------------------------------------
    @Test
    public void test_toPeriod() {
        for (int i = -20; i < 20; i++) {
            assertEquals(Period.ofWeeks(i), Weeks.of(i).toPeriod());
        }
    }

    //-----------------------------------------------------------------------
    @Test
    public void test_compareTo() {
        Weeks test5 = Weeks.of(5);
        Weeks test6 = Weeks.of(6);
        assertEquals(0, test5.compareTo(test5));
        assertEquals(-1, test5.compareTo(test6));
        assertEquals(1, test6.compareTo(test5));
    }

    @Test
    public void test_compareTo_null() {
        Weeks test5 = Weeks.of(5);
        //noinspection DataFlowIssue - testing nulls
        assertThrows(NullPointerException.class, () -> test5.compareTo(null));
    }

    //-----------------------------------------------------------------------
    @Test
    public void test_equals_and_hashCode() {
        new EqualsTester()
            .addEqualityGroup(Weeks.of(5), Weeks.of(5))
            .addEqualityGroup(Weeks.of(6), Weeks.of(6))
            .testEquals();
    }

    //-----------------------------------------------------------------------
    @Test
    public void test_toString() {
        Weeks test5 = Weeks.of(5);
        assertEquals("P5W", test5.toString());
        Weeks testM1 = Weeks.of(-1);
        assertEquals("P-1W", testM1.toString());
    }

}
