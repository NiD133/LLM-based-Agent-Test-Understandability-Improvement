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

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import com.google.common.testing.EqualsTester;

/**
 * Tests for {@link Months}.
 */
public class TestMonths {

    //-----------------------------------------------------------------------
    @Test
    @DisplayName("Months implements Serializable")
    public void test_isSerializable() {
        assertTrue(Serializable.class.isAssignableFrom(Months.class));
    }

    //-----------------------------------------------------------------------
    @Test
    @DisplayName("Months.ZERO is preserved as a singleton through serialization round-trip")
    public void test_deserializationSingleton() throws Exception {
        Months zero = Months.ZERO;
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try (ObjectOutputStream oos = new ObjectOutputStream(baos)) {
            oos.writeObject(zero);
        }
        try (ObjectInputStream ois = new ObjectInputStream(new ByteArrayInputStream(baos.toByteArray()))) {
            assertSame(zero, ois.readObject());
        }
    }

    //-----------------------------------------------------------------------
    @Test
    @DisplayName("Months.ZERO constant has amount 0 and reports isZero=true, isNegative=false, isPositive=false")
    public void test_ZERO() {
        assertSame(Months.ZERO, Months.of(0));
        assertEquals(Months.ZERO, Months.of(0));
        assertEquals(0, Months.ZERO.getAmount());
        assertFalse(Months.ZERO.isNegative());
        assertTrue(Months.ZERO.isZero());
        assertFalse(Months.ZERO.isPositive());
    }

    @Test
    @DisplayName("Months.ONE constant has amount 1 and reports isPositive=true, isNegative=false, isZero=false")
    public void test_ONE() {
        assertSame(Months.ONE, Months.of(1));
        assertEquals(Months.ONE, Months.of(1));
        assertEquals(1, Months.ONE.getAmount());
        assertFalse(Months.ONE.isNegative());
        assertFalse(Months.ONE.isZero());
        assertTrue(Months.ONE.isPositive());
    }

    //-----------------------------------------------------------------------
    @Test
    @DisplayName("Months.of stores the given value including positive, negative, and boundary values")
    public void test_of() {
        assertEquals(0, Months.of(0).getAmount());
        assertEquals(1, Months.of(1).getAmount());
        assertEquals(2, Months.of(2).getAmount());
        assertEquals(Integer.MAX_VALUE, Months.of(Integer.MAX_VALUE).getAmount());
        assertEquals(-1, Months.of(-1).getAmount());
        assertEquals(-2, Months.of(-2).getAmount());
        assertEquals(Integer.MIN_VALUE, Months.of(Integer.MIN_VALUE).getAmount());
    }

    @Test
    @DisplayName("Months.of(-1) reports isNegative=true, isZero=false, isPositive=false")
    public void test_ofMinusOne() {
        assertEquals(-1, Months.of(-1).getAmount());
        assertTrue(Months.of(-1).isNegative());
        assertFalse(Months.of(-1).isZero());
        assertFalse(Months.of(-1).isPositive());
    }

    //-----------------------------------------------------------------------
    @Test
    @DisplayName("Months.ofYears converts years to months (×12) including negative and boundary values")
    public void test_ofYears() {
        assertEquals(0, Months.ofYears(0).getAmount());
        assertEquals(12, Months.ofYears(1).getAmount());
        assertEquals(24, Months.ofYears(2).getAmount());
        assertEquals((Integer.MAX_VALUE / 12) * 12, Months.ofYears(Integer.MAX_VALUE / 12).getAmount());
        assertEquals(-12, Months.ofYears(-1).getAmount());
        assertEquals(-24, Months.ofYears(-2).getAmount());
        assertEquals((Integer.MIN_VALUE / 12) * 12, Months.ofYears(Integer.MIN_VALUE / 12).getAmount());
    }

    @Test
    @DisplayName("Months.ofYears throws ArithmeticException when the result would overflow int")
    public void test_ofYears_overflow() {
        assertThrows(ArithmeticException.class, () -> Months.ofYears((Integer.MAX_VALUE / 12) + 12));
    }

    //-----------------------------------------------------------------------
    @Test
    @DisplayName("Months.from(Period.ofMonths(0)) returns zero months")
    public void test_from_Period_P0M() {
        assertEquals(Months.of(0), Months.from(Period.ofMonths(0)));
    }

    @Test
    @DisplayName("Months.from(Period.ofMonths(2)) returns two months")
    public void test_from_Period_P2M() {
        assertEquals(Months.of(2), Months.from(Period.ofMonths(2)));
    }

    @Test
    @DisplayName("Months.from converts 2 years with no months to 24 months")
    public void test_from_P2Y() {
        assertEquals(Months.of(24), Months.from(new MockYearsMonths(2, 0)));
    }

    @Test
    @DisplayName("Months.from converts 2 years and 3 months to 27 months total")
    public void test_from_P2Y3M() {
        assertEquals(Months.of(27), Months.from(new MockYearsMonths(2, 3)));
    }

    @Test
    @DisplayName("Months.from converts a Period with both years and months to the total month count")
    public void test_from_yearsAndMonths() {
        assertEquals(Months.of(41), Months.from(Period.of(3, 5, 0)));
    }

    @Test
    @DisplayName("Months.from throws DateTimeException when the amount uses an incompatible unit (days)")
    public void test_from_wrongUnit_noConversion() {
        assertThrows(DateTimeException.class, () -> Months.from(Period.ofDays(2)));
    }

    @Test
    @DisplayName("Months.from throws NullPointerException for null input")
    public void test_from_null() {
        //noinspection DataFlowIssue - testing nulls
        assertThrows(NullPointerException.class, () -> Months.from((TemporalAmount) null));
    }

    //-----------------------------------------------------------------------
    public static Object[][] data_valid() {
        return new Object[][] {
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
        assertEquals(Months.of(expectedMonths), Months.parse("+" + str));
    }

    @ParameterizedTest
    @MethodSource("data_valid")
    public void test_parse_CharSequence_valid_initialMinus(String str, int expectedMonths) {
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
    @DisplayName("Months.parse throws NullPointerException for null input")
    public void test_parse_CharSequence_null() {
        //noinspection DataFlowIssue - testing nulls
        assertThrows(NullPointerException.class, () -> Months.parse((CharSequence) null));
    }

    //-----------------------------------------------------------------------
    @Test
    @DisplayName("Months.between returns the correct number of whole months between two dates")
    public void test_between() {
        assertEquals(Months.of(24), Months.between(LocalDate.of(2019, 1, 1), LocalDate.of(2021, 1, 1)));
    }

    @Test
    @DisplayName("Months.between throws NullPointerException when the end date is null")
    public void test_between_date_null() {
        //noinspection DataFlowIssue - testing nulls
        assertThrows(NullPointerException.class, () -> Months.between(LocalDate.now(), (Temporal) null));
    }

    @Test
    @DisplayName("Months.between throws NullPointerException when the start date is null")
    public void test_between_null_date() {
        //noinspection DataFlowIssue - testing nulls
        assertThrows(NullPointerException.class, () -> Months.between((Temporal) null, LocalDate.now()));
    }

    //-----------------------------------------------------------------------
    @Test
    @DisplayName("get(ChronoUnit.MONTHS) returns the month count as a long")
    public void test_get() {
        assertEquals(6, Months.of(6).get(ChronoUnit.MONTHS));
    }

    @Test
    @DisplayName("get throws DateTimeException for unsupported temporal units such as QUARTER_YEARS")
    public void test_get_invalidType() {
        assertThrows(DateTimeException.class, () -> Months.of(6).get(IsoFields.QUARTER_YEARS));
    }

    //-----------------------------------------------------------------------
    @Test
    @DisplayName("plus(TemporalAmount) with a Months argument adds correctly including boundary values")
    public void test_plus_TemporalAmount_Months() {
        Months fiveMonths = Months.of(5);
        assertEquals(Months.of(5), fiveMonths.plus(Months.of(0)));
        assertEquals(Months.of(7), fiveMonths.plus(Months.of(2)));
        assertEquals(Months.of(3), fiveMonths.plus(Months.of(-2)));
        assertEquals(Months.of(Integer.MAX_VALUE), Months.of(Integer.MAX_VALUE - 1).plus(Months.of(1)));
        assertEquals(Months.of(Integer.MIN_VALUE), Months.of(Integer.MIN_VALUE + 1).plus(Months.of(-1)));
    }

    @Test
    @DisplayName("plus(TemporalAmount) with a Period argument adds correctly including boundary values")
    public void test_plus_TemporalAmount_Period() {
        Months fiveMonths = Months.of(5);
        assertEquals(Months.of(5), fiveMonths.plus(Period.ofMonths(0)));
        assertEquals(Months.of(7), fiveMonths.plus(Period.ofMonths(2)));
        assertEquals(Months.of(3), fiveMonths.plus(Period.ofMonths(-2)));
        assertEquals(Months.of(Integer.MAX_VALUE), Months.of(Integer.MAX_VALUE - 1).plus(Period.ofMonths(1)));
        assertEquals(Months.of(Integer.MIN_VALUE), Months.of(Integer.MIN_VALUE + 1).plus(Period.ofMonths(-1)));
    }

    @Test
    @DisplayName("plus(TemporalAmount) throws DateTimeException when given a day-based Period")
    public void test_plus_TemporalAmount_PeriodDays() {
        assertThrows(DateTimeException.class, () -> Months.of(1).plus(Period.ofDays(2)));
    }

    @Test
    @DisplayName("plus(TemporalAmount) throws DateTimeException when given a time-based Duration")
    public void test_plus_TemporalAmount_Duration() {
        assertThrows(DateTimeException.class, () -> Months.of(1).plus(Duration.ofHours(2)));
    }

    @Test
    @DisplayName("plus(TemporalAmount) throws ArithmeticException on positive overflow")
    public void test_plus_TemporalAmount_overflowTooBig() {
        assertThrows(ArithmeticException.class, () -> Months.of(Integer.MAX_VALUE - 1).plus(Months.of(2)));
    }

    @Test
    @DisplayName("plus(TemporalAmount) throws ArithmeticException on negative overflow")
    public void test_plus_TemporalAmount_overflowTooSmall() {
        assertThrows(ArithmeticException.class, () -> Months.of(Integer.MIN_VALUE + 1).plus(Months.of(-2)));
    }

    @Test
    @DisplayName("plus(TemporalAmount) throws NullPointerException for null input")
    public void test_plus_TemporalAmount_null() {
        //noinspection DataFlowIssue - testing nulls
        assertThrows(NullPointerException.class, () -> Months.of(Integer.MIN_VALUE + 1).plus(null));
    }

    //-----------------------------------------------------------------------
    @Test
    @DisplayName("plus(int) adds the given number of months correctly including boundary values")
    public void test_plus_int() {
        Months fiveMonths = Months.of(5);
        assertEquals(Months.of(5), fiveMonths.plus(0));
        assertEquals(Months.of(7), fiveMonths.plus(2));
        assertEquals(Months.of(3), fiveMonths.plus(-2));
        assertEquals(Months.of(Integer.MAX_VALUE), Months.of(Integer.MAX_VALUE - 1).plus(1));
        assertEquals(Months.of(Integer.MIN_VALUE), Months.of(Integer.MIN_VALUE + 1).plus(-1));
    }

    @Test
    @DisplayName("plus(int) throws ArithmeticException on positive overflow")
    public void test_plus_int_overflowTooBig() {
        assertThrows(ArithmeticException.class, () -> Months.of(Integer.MAX_VALUE - 1).plus(2));
    }

    @Test
    @DisplayName("plus(int) throws ArithmeticException on negative overflow")
    public void test_plus_int_overflowTooSmall() {
        assertThrows(ArithmeticException.class, () -> Months.of(Integer.MIN_VALUE + 1).plus(-2));
    }

    //-----------------------------------------------------------------------
    @Test
    @DisplayName("minus(TemporalAmount) with a Months argument subtracts correctly including boundary values")
    public void test_minus_TemporalAmount_Months() {
        Months fiveMonths = Months.of(5);
        assertEquals(Months.of(5), fiveMonths.minus(Months.of(0)));
        assertEquals(Months.of(3), fiveMonths.minus(Months.of(2)));
        assertEquals(Months.of(7), fiveMonths.minus(Months.of(-2)));
        assertEquals(Months.of(Integer.MAX_VALUE), Months.of(Integer.MAX_VALUE - 1).minus(Months.of(-1)));
        assertEquals(Months.of(Integer.MIN_VALUE), Months.of(Integer.MIN_VALUE + 1).minus(Months.of(1)));
    }

    @Test
    @DisplayName("minus(TemporalAmount) with a Period argument subtracts correctly including boundary values")
    public void test_minus_TemporalAmount_Period() {
        Months fiveMonths = Months.of(5);
        assertEquals(Months.of(5), fiveMonths.minus(Period.ofMonths(0)));
        assertEquals(Months.of(3), fiveMonths.minus(Period.ofMonths(2)));
        assertEquals(Months.of(7), fiveMonths.minus(Period.ofMonths(-2)));
        assertEquals(Months.of(Integer.MAX_VALUE), Months.of(Integer.MAX_VALUE - 1).minus(Period.ofMonths(-1)));
        assertEquals(Months.of(Integer.MIN_VALUE), Months.of(Integer.MIN_VALUE + 1).minus(Period.ofMonths(1)));
    }

    @Test
    @DisplayName("minus(TemporalAmount) throws DateTimeException when given a day-based Period")
    public void test_minus_TemporalAmount_PeriodDays() {
        assertThrows(DateTimeException.class, () -> Months.of(1).minus(Period.ofDays(2)));
    }

    @Test
    @DisplayName("minus(TemporalAmount) throws DateTimeException when given a time-based Duration")
    public void test_minus_TemporalAmount_Duration() {
        assertThrows(DateTimeException.class, () -> Months.of(1).minus(Duration.ofHours(2)));
    }

    @Test
    @DisplayName("minus(TemporalAmount) throws ArithmeticException on positive overflow")
    public void test_minus_TemporalAmount_overflowTooBig() {
        assertThrows(ArithmeticException.class, () -> Months.of(Integer.MAX_VALUE - 1).minus(Months.of(-2)));
    }

    @Test
    @DisplayName("minus(TemporalAmount) throws ArithmeticException on negative overflow")
    public void test_minus_TemporalAmount_overflowTooSmall() {
        assertThrows(ArithmeticException.class, () -> Months.of(Integer.MIN_VALUE + 1).minus(Months.of(2)));
    }

    @Test
    @DisplayName("minus(TemporalAmount) throws NullPointerException for null input")
    public void test_minus_TemporalAmount_null() {
        //noinspection DataFlowIssue - testing nulls
        assertThrows(NullPointerException.class, () -> Months.of(Integer.MIN_VALUE + 1).minus(null));
    }

    //-----------------------------------------------------------------------
    @Test
    @DisplayName("minus(int) subtracts the given number of months correctly including boundary values")
    public void test_minus_int() {
        Months fiveMonths = Months.of(5);
        assertEquals(Months.of(5), fiveMonths.minus(0));
        assertEquals(Months.of(3), fiveMonths.minus(2));
        assertEquals(Months.of(7), fiveMonths.minus(-2));
        assertEquals(Months.of(Integer.MAX_VALUE), Months.of(Integer.MAX_VALUE - 1).minus(-1));
        assertEquals(Months.of(Integer.MIN_VALUE), Months.of(Integer.MIN_VALUE + 1).minus(1));
    }

    @Test
    @DisplayName("minus(int) throws ArithmeticException on positive overflow")
    public void test_minus_int_overflowTooBig() {
        assertThrows(ArithmeticException.class, () -> Months.of(Integer.MAX_VALUE - 1).minus(-2));
    }

    @Test
    @DisplayName("minus(int) throws ArithmeticException on negative overflow")
    public void test_minus_int_overflowTooSmall() {
        assertThrows(ArithmeticException.class, () -> Months.of(Integer.MIN_VALUE + 1).minus(2));
    }

    //-----------------------------------------------------------------------
    @Test
    @DisplayName("multipliedBy scales the month count by the given scalar including negation")
    public void test_multipliedBy() {
        Months fiveMonths = Months.of(5);
        assertEquals(Months.of(0), fiveMonths.multipliedBy(0));
        assertEquals(Months.of(5), fiveMonths.multipliedBy(1));
        assertEquals(Months.of(10), fiveMonths.multipliedBy(2));
        assertEquals(Months.of(15), fiveMonths.multipliedBy(3));
        assertEquals(Months.of(-15), fiveMonths.multipliedBy(-3));
    }

    @Test
    @DisplayName("multipliedBy with a negative scalar produces a negative result")
    public void test_multipliedBy_negate() {
        Months fiveMonths = Months.of(5);
        assertEquals(Months.of(-15), fiveMonths.multipliedBy(-3));
    }

    @Test
    @DisplayName("multipliedBy throws ArithmeticException on positive overflow")
    public void test_multipliedBy_overflowTooBig() {
        assertThrows(ArithmeticException.class, () -> Months.of(Integer.MAX_VALUE / 2 + 1).multipliedBy(2));
    }

    @Test
    @DisplayName("multipliedBy throws ArithmeticException on negative overflow")
    public void test_multipliedBy_overflowTooSmall() {
        assertThrows(ArithmeticException.class, () -> Months.of(Integer.MIN_VALUE / 2 - 1).multipliedBy(2));
    }

    //-----------------------------------------------------------------------
    @Test
    @DisplayName("dividedBy divides the month count using integer (truncating) division")
    public void test_dividedBy() {
        Months twelveMonths = Months.of(12);
        assertEquals(Months.of(12), twelveMonths.dividedBy(1));
        assertEquals(Months.of(6), twelveMonths.dividedBy(2));
        assertEquals(Months.of(4), twelveMonths.dividedBy(3));
        assertEquals(Months.of(3), twelveMonths.dividedBy(4));
        assertEquals(Months.of(2), twelveMonths.dividedBy(5));
        assertEquals(Months.of(2), twelveMonths.dividedBy(6));
        assertEquals(Months.of(-4), twelveMonths.dividedBy(-3));
    }

    @Test
    @DisplayName("dividedBy with a negative divisor produces a negative result")
    public void test_dividedBy_negate() {
        Months twelveMonths = Months.of(12);
        assertEquals(Months.of(-4), twelveMonths.dividedBy(-3));
    }

    @Test
    @DisplayName("dividedBy throws ArithmeticException when the divisor is zero")
    public void test_dividedBy_divideByZero() {
        assertThrows(ArithmeticException.class, () -> Months.of(1).dividedBy(0));
    }

    //-----------------------------------------------------------------------
    @Test
    @DisplayName("negated returns the arithmetic negation of the month count")
    public void test_negated() {
        assertEquals(Months.of(0), Months.of(0).negated());
        assertEquals(Months.of(-12), Months.of(12).negated());
        assertEquals(Months.of(12), Months.of(-12).negated());
        assertEquals(Months.of(-Integer.MAX_VALUE), Months.of(Integer.MAX_VALUE).negated());
    }

    @Test
    @DisplayName("negated throws ArithmeticException when negating Integer.MIN_VALUE (no positive equivalent)")
    public void test_negated_overflow() {
        assertThrows(ArithmeticException.class, () -> Months.of(Integer.MIN_VALUE).negated());
    }

    //-----------------------------------------------------------------------
    @Test
    @DisplayName("abs returns the non-negative magnitude of the month count")
    public void test_abs() {
        assertEquals(Months.of(0), Months.of(0).abs());
        assertEquals(Months.of(12), Months.of(12).abs());
        assertEquals(Months.of(12), Months.of(-12).abs());
        assertEquals(Months.of(Integer.MAX_VALUE), Months.of(Integer.MAX_VALUE).abs());
        assertEquals(Months.of(Integer.MAX_VALUE), Months.of(-Integer.MAX_VALUE).abs());
    }

    @Test
    @DisplayName("abs throws ArithmeticException for Integer.MIN_VALUE because its positive equivalent overflows int")
    public void test_abs_overflow() {
        assertThrows(ArithmeticException.class, () -> Months.of(Integer.MIN_VALUE).abs());
    }

    //-----------------------------------------------------------------------
    @Test
    @DisplayName("addTo applies the month count to the given temporal object")
    public void test_addTo() {
        assertEquals(LocalDate.of(2019, 1, 10), Months.of(0).addTo(LocalDate.of(2019, 1, 10)));
        assertEquals(LocalDate.of(2019, 6, 10), Months.of(5).addTo(LocalDate.of(2019, 1, 10)));
    }

    @Test
    @DisplayName("subtractFrom removes the month count from the given temporal object")
    public void test_subtractFrom() {
        assertEquals(LocalDate.of(2019, 1, 10), Months.of(0).subtractFrom(LocalDate.of(2019, 1, 10)));
        assertEquals(LocalDate.of(2018, 8, 10), Months.of(5).subtractFrom(LocalDate.of(2019, 1, 10)));
    }

    //-----------------------------------------------------------------------
    @Test
    @DisplayName("toPeriod converts each Months value to an equivalent Period for a range of values")
    public void test_toPeriod() {
        for (int i = -20; i < 20; i++) {
            assertEquals(Period.ofMonths(i), Months.of(i).toPeriod());
        }
    }

    //-----------------------------------------------------------------------
    @Test
    @DisplayName("compareTo orders Months by their month count: equal returns 0, less returns negative, greater returns positive")
    public void test_compareTo() {
        Months fiveMonths = Months.of(5);
        Months sixMonths = Months.of(6);
        assertEquals(0, fiveMonths.compareTo(fiveMonths));
        assertEquals(-1, fiveMonths.compareTo(sixMonths));
        assertEquals(1, sixMonths.compareTo(fiveMonths));
    }

    @Test
    @DisplayName("compareTo throws NullPointerException when compared to null")
    public void test_compareTo_null() {
        Months fiveMonths = Months.of(5);
        //noinspection DataFlowIssue - testing nulls
        assertThrows(NullPointerException.class, () -> fiveMonths.compareTo(null));
    }

    //-----------------------------------------------------------------------
    @Test
    @DisplayName("equals and hashCode satisfy the contract: same count is equal, different count is not")
    public void test_equals_and_hashCode() {
        new EqualsTester()
            .addEqualityGroup(Months.of(5), Months.of(5))
            .addEqualityGroup(Months.of(6), Months.of(6))
            .testEquals();
    }

    //-----------------------------------------------------------------------
    @Test
    @DisplayName("toString produces ISO-8601 PnM format for positive and negative values")
    public void test_toString() {
        Months fiveMonths = Months.of(5);
        assertEquals("P5M", fiveMonths.toString());
        Months negOneMonth = Months.of(-1);
        assertEquals("P-1M", negOneMonth.toString());
    }

}
