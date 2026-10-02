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
import java.time.Duration;
import java.time.LocalTime;
import java.time.format.DateTimeParseException;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import com.google.common.testing.EqualsTester;

/**
 * Test class for {@link Seconds}.
 */
public class TestSeconds {

    private static final int SECONDS_PER_MINUTE = 60;
    private static final int SECONDS_PER_HOUR   = 3600;
    private static final int SECONDS_PER_DAY    = 86400;

    //-----------------------------------------------------------------------
    @Test
    @DisplayName("Seconds implements Serializable")
    public void test_isSerializable() {
        assertTrue(Serializable.class.isAssignableFrom(Seconds.class));
    }

    //-----------------------------------------------------------------------
    @Test
    @DisplayName("Seconds.ZERO round-trips through serialization as singleton")
    public void test_deserializationSingleton() throws Exception {
        Seconds test = Seconds.ZERO;
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
    @DisplayName("Seconds.ZERO is the canonical zero instance with correct state")
    public void test_ZERO() {
        Seconds zero = Seconds.ZERO;
        assertSame(zero, Seconds.of(0));
        assertEquals(zero, Seconds.of(0));
        assertEquals(0, zero.getAmount());
        assertFalse(zero.isNegative());
        assertTrue(zero.isZero());
        assertFalse(zero.isPositive());
    }

    //-----------------------------------------------------------------------
    @Test
    @DisplayName("Seconds.of creates instances with the expected amount")
    public void test_of() {
        assertEquals(0,                  Seconds.of(0).getAmount());
        assertEquals(1,                  Seconds.of(1).getAmount());
        assertEquals(2,                  Seconds.of(2).getAmount());
        assertEquals(Integer.MAX_VALUE,  Seconds.of(Integer.MAX_VALUE).getAmount());
        assertEquals(-1,                 Seconds.of(-1).getAmount());
        assertEquals(-2,                 Seconds.of(-2).getAmount());
        assertEquals(Integer.MIN_VALUE,  Seconds.of(Integer.MIN_VALUE).getAmount());
    }

    @Test
    @DisplayName("Seconds.of(-1) is negative, not zero, not positive")
    public void test_ofMinusOne() {
        Seconds minusOne = Seconds.of(-1);
        assertEquals(-1, minusOne.getAmount());
        assertTrue(minusOne.isNegative());
        assertFalse(minusOne.isZero());
        assertFalse(minusOne.isPositive());
    }

    @Test
    @DisplayName("Seconds.of(1) is positive, not zero, not negative")
    public void test_ofPlusOne() {
        Seconds plusOne = Seconds.of(1);
        assertEquals(1, plusOne.getAmount());
        assertFalse(plusOne.isNegative());
        assertFalse(plusOne.isZero());
        assertTrue(plusOne.isPositive());
    }

    //-----------------------------------------------------------------------
    @Test
    @DisplayName("Seconds.ofHours converts hours to seconds correctly")
    public void test_ofHours() {
        int maxSafeHours = Integer.MAX_VALUE / SECONDS_PER_HOUR;
        int minSafeHours = Integer.MIN_VALUE / SECONDS_PER_HOUR;

        assertEquals(0,                              Seconds.ofHours(0).getAmount());
        assertEquals(SECONDS_PER_HOUR,               Seconds.ofHours(1).getAmount());
        assertEquals(2 * SECONDS_PER_HOUR,           Seconds.ofHours(2).getAmount());
        assertEquals(maxSafeHours * SECONDS_PER_HOUR, Seconds.ofHours(maxSafeHours).getAmount());
        assertEquals(-SECONDS_PER_HOUR,              Seconds.ofHours(-1).getAmount());
        assertEquals(-2 * SECONDS_PER_HOUR,          Seconds.ofHours(-2).getAmount());
        assertEquals(minSafeHours * SECONDS_PER_HOUR, Seconds.ofHours(minSafeHours).getAmount());
    }

    @Test
    @DisplayName("Seconds.ofHours throws ArithmeticException on overflow")
    public void test_ofHours_overflow() {
        int overflowHours = (Integer.MAX_VALUE / SECONDS_PER_HOUR) + SECONDS_PER_HOUR;
        assertThrows(ArithmeticException.class, () -> Seconds.ofHours(overflowHours));
    }

    //-----------------------------------------------------------------------
    @Test
    @DisplayName("Seconds.ofMinutes converts minutes to seconds correctly")
    public void test_ofMinutes() {
        int maxSafeMinutes = Integer.MAX_VALUE / SECONDS_PER_MINUTE;
        int minSafeMinutes = Integer.MIN_VALUE / SECONDS_PER_MINUTE;

        assertEquals(0,                                  Seconds.ofMinutes(0).getAmount());
        assertEquals(SECONDS_PER_MINUTE,                 Seconds.ofMinutes(1).getAmount());
        assertEquals(2 * SECONDS_PER_MINUTE,             Seconds.ofMinutes(2).getAmount());
        assertEquals(maxSafeMinutes * SECONDS_PER_MINUTE, Seconds.ofMinutes(maxSafeMinutes).getAmount());
        assertEquals(-SECONDS_PER_MINUTE,                Seconds.ofMinutes(-1).getAmount());
        assertEquals(-2 * SECONDS_PER_MINUTE,            Seconds.ofMinutes(-2).getAmount());
        assertEquals(minSafeMinutes * SECONDS_PER_MINUTE, Seconds.ofMinutes(minSafeMinutes).getAmount());
    }

    @Test
    @DisplayName("Seconds.ofMinutes throws ArithmeticException on overflow")
    public void test_ofMinutes_overflow() {
        int overflowMinutes = (Integer.MAX_VALUE / SECONDS_PER_MINUTE) + SECONDS_PER_MINUTE;
        assertThrows(ArithmeticException.class, () -> Seconds.ofMinutes(overflowMinutes));
    }

    //-----------------------------------------------------------------------
    public static Object[][] data_valid() {
        return new Object[][] {
            // Seconds component only
            {"PT0S",          0},
            {"PT1S",          1},
            {"PT2S",          2},
            {"PT123456789S",  123456789},
            {"PT+0S",         0},
            {"PT+2S",         2},
            {"PT-0S",         0},
            {"PT-2S",         -2},

            // Minutes component only
            {"PT0M",          0},
            {"PT1M",          1 * 60},
            {"PT2M",          2 * 60},
            {"PT1234M",       1234 * 60},
            {"PT+0M",         0},
            {"PT+2M",         2 * 60},
            {"PT-0M",         0},
            {"PT-2M",         -2 * 60},

            // Hours component only
            {"PT0H",          0},
            {"PT1H",          1 * 60 * 60},
            {"PT2H",          2 * 60 * 60},
            {"PT1234H",       1234 * 60 * 60},
            {"PT+0H",         0},
            {"PT+2H",         2 * 60 * 60},
            {"PT-0H",         0},
            {"PT-2H",         -2 * 60 * 60},

            // Days component only
            {"P0D",           0},
            {"P1D",           1 * 60 * 60 * 24},
            {"P2D",           2 * 60 * 60 * 24},
            {"P1234D",        1234 * 60 * 60 * 24},
            {"P+0D",          0},
            {"P+2D",          2 * 60 * 60 * 24},
            {"P-0D",          0},
            {"P-2D",          -2 * 60 * 60 * 24},

            // Mixed minutes and seconds
            {"PT0M0S",        0},
            {"PT2M3S",        2 * 60 + 3},
            {"PT+2M3S",       2 * 60 + 3},
            {"PT2M+3S",       2 * 60 + 3},
            {"PT-2M3S",       -2 * 60 + 3},
            {"PT2M-3S",       2 * 60 - 3},
            {"PT-2M-3S",      -2 * 60 - 3},

            // Mixed hours and seconds
            {"PT0H0S",        0},
            {"PT2H3S",        2 * 3600 + 3},
            {"PT+2H3S",       2 * 3600 + 3},
            {"PT2H+3S",       2 * 3600 + 3},
            {"PT-2H3S",       -2 * 3600 + 3},
            {"PT2H-3S",       2 * 3600 - 3},
            {"PT-2H-3S",      -2 * 3600 - 3},

            // All components combined
            {"P0DT0H0M0S",    0},
            {"P5DT2H4M3S",    5 * 86400 + 2 * 3600 + 4 * 60 + 3},
        };
    }

    @ParameterizedTest
    @MethodSource("data_valid")
    @DisplayName("Seconds.parse accepts valid ISO-8601 duration strings")
    public void test_parse_CharSequence_valid(String str, int expectedSeconds) {
        assertEquals(Seconds.of(expectedSeconds), Seconds.parse(str));
    }

    @ParameterizedTest
    @MethodSource("data_valid")
    @DisplayName("Seconds.parse with leading '+' sign parses same as without sign")
    public void test_parse_CharSequence_valid_initialPlus(String str, int expectedSeconds) {
        assertEquals(Seconds.of(expectedSeconds), Seconds.parse("+" + str));
    }

    @ParameterizedTest
    @MethodSource("data_valid")
    @DisplayName("Seconds.parse with leading '-' sign negates the parsed value")
    public void test_parse_CharSequence_valid_initialMinus(String str, int expectedSeconds) {
        assertEquals(Seconds.of(-expectedSeconds), Seconds.parse("-" + str));
    }

    public static Object[][] data_invalid() {
        return new Object[][] {
            {"P3W"},
            {"P3Q"},
            {"P1M2Y"},

            {"3"},
            {"-3"},
            {"3S"},
            {"-3S"},
            {"P3S"},
            {"P3"},
            {"P-3"},
            {"PS"},
            {"T3"},
            {"PT3"},
        };
    }

    @ParameterizedTest
    @MethodSource("data_invalid")
    @DisplayName("Seconds.parse throws DateTimeParseException for invalid strings")
    public void test_parse_CharSequence_invalid(String str) {
        assertThrows(DateTimeParseException.class, () -> Seconds.parse(str));
    }

    @Test
    @DisplayName("Seconds.parse throws NullPointerException for null input")
    public void test_parse_CharSequence_null() {
        //noinspection DataFlowIssue - testing nulls
        assertThrows(NullPointerException.class, () -> Seconds.parse((CharSequence) null));
    }

    //-----------------------------------------------------------------------
    @Test
    @DisplayName("plus(TemporalAmount) adds a Seconds amount correctly")
    public void test_plus_TemporalAmount_Seconds() {
        Seconds test5 = Seconds.of(5);
        assertEquals(Seconds.of(5),                Seconds.of(5).plus(Seconds.of(0)));
        assertEquals(Seconds.of(7),                test5.plus(Seconds.of(2)));
        assertEquals(Seconds.of(3),                test5.plus(Seconds.of(-2)));
        assertEquals(Seconds.of(Integer.MAX_VALUE), Seconds.of(Integer.MAX_VALUE - 1).plus(Seconds.of(1)));
        assertEquals(Seconds.of(Integer.MIN_VALUE), Seconds.of(Integer.MIN_VALUE + 1).plus(Seconds.of(-1)));
    }

    @Test
    @DisplayName("plus(TemporalAmount) throws ArithmeticException on positive overflow")
    public void test_plus_TemporalAmount_overflowTooBig() {
        assertThrows(ArithmeticException.class, () -> Seconds.of(Integer.MAX_VALUE - 1).plus(Seconds.of(2)));
    }

    @Test
    @DisplayName("plus(TemporalAmount) throws ArithmeticException on negative overflow")
    public void test_plus_TemporalAmount_overflowTooSmall() {
        assertThrows(ArithmeticException.class, () -> Seconds.of(Integer.MIN_VALUE + 1).plus(Seconds.of(-2)));
    }

    @Test
    @DisplayName("plus(TemporalAmount) throws NullPointerException for null input")
    public void test_plus_TemporalAmount_null() {
        //noinspection DataFlowIssue - testing nulls
        assertThrows(NullPointerException.class, () -> Seconds.of(Integer.MIN_VALUE + 1).plus(null));
    }

    //-----------------------------------------------------------------------
    @Test
    @DisplayName("plus(int) adds a number of seconds correctly")
    public void test_plus_int() {
        Seconds test5 = Seconds.of(5);
        assertEquals(Seconds.of(5),                test5.plus(0));
        assertEquals(Seconds.of(7),                test5.plus(2));
        assertEquals(Seconds.of(3),                test5.plus(-2));
        assertEquals(Seconds.of(Integer.MAX_VALUE), Seconds.of(Integer.MAX_VALUE - 1).plus(1));
        assertEquals(Seconds.of(Integer.MIN_VALUE), Seconds.of(Integer.MIN_VALUE + 1).plus(-1));
    }

    @Test
    @DisplayName("plus(int) throws ArithmeticException on positive overflow")
    public void test_plus_int_overflowTooBig() {
        assertThrows(ArithmeticException.class, () -> Seconds.of(Integer.MAX_VALUE - 1).plus(2));
    }

    @Test
    @DisplayName("plus(int) throws ArithmeticException on negative overflow")
    public void test_plus_int_overflowTooSmall() {
        assertThrows(ArithmeticException.class, () -> Seconds.of(Integer.MIN_VALUE + 1).plus(-2));
    }

    //-----------------------------------------------------------------------
    @Test
    @DisplayName("minus(TemporalAmount) subtracts a Seconds amount correctly")
    public void test_minus_TemporalAmount_Seconds() {
        Seconds test5 = Seconds.of(5);
        assertEquals(Seconds.of(5),                test5.minus(Seconds.of(0)));
        assertEquals(Seconds.of(3),                test5.minus(Seconds.of(2)));
        assertEquals(Seconds.of(7),                test5.minus(Seconds.of(-2)));
        assertEquals(Seconds.of(Integer.MAX_VALUE), Seconds.of(Integer.MAX_VALUE - 1).minus(Seconds.of(-1)));
        assertEquals(Seconds.of(Integer.MIN_VALUE), Seconds.of(Integer.MIN_VALUE + 1).minus(Seconds.of(1)));
    }

    @Test
    @DisplayName("minus(TemporalAmount) throws ArithmeticException on positive overflow")
    public void test_minus_TemporalAmount_overflowTooBig() {
        assertThrows(ArithmeticException.class, () -> Seconds.of(Integer.MAX_VALUE - 1).minus(Seconds.of(-2)));
    }

    @Test
    @DisplayName("minus(TemporalAmount) throws ArithmeticException on negative overflow")
    public void test_minus_TemporalAmount_overflowTooSmall() {
        assertThrows(ArithmeticException.class, () -> Seconds.of(Integer.MIN_VALUE + 1).minus(Seconds.of(2)));
    }

    @Test
    @DisplayName("minus(TemporalAmount) throws NullPointerException for null input")
    public void test_minus_TemporalAmount_null() {
        //noinspection DataFlowIssue - testing nulls
        assertThrows(NullPointerException.class, () -> Seconds.of(Integer.MIN_VALUE + 1).minus(null));
    }

    //-----------------------------------------------------------------------
    @Test
    @DisplayName("minus(int) subtracts a number of seconds correctly")
    public void test_minus_int() {
        Seconds test5 = Seconds.of(5);
        assertEquals(Seconds.of(5),                test5.minus(0));
        assertEquals(Seconds.of(3),                test5.minus(2));
        assertEquals(Seconds.of(7),                test5.minus(-2));
        assertEquals(Seconds.of(Integer.MAX_VALUE), Seconds.of(Integer.MAX_VALUE - 1).minus(-1));
        assertEquals(Seconds.of(Integer.MIN_VALUE), Seconds.of(Integer.MIN_VALUE + 1).minus(1));
    }

    @Test
    @DisplayName("minus(int) throws ArithmeticException on positive overflow")
    public void test_minus_int_overflowTooBig() {
        assertThrows(ArithmeticException.class, () -> Seconds.of(Integer.MAX_VALUE - 1).minus(-2));
    }

    @Test
    @DisplayName("minus(int) throws ArithmeticException on negative overflow")
    public void test_minus_int_overflowTooSmall() {
        assertThrows(ArithmeticException.class, () -> Seconds.of(Integer.MIN_VALUE + 1).minus(2));
    }

    //-----------------------------------------------------------------------
    @Test
    @DisplayName("multipliedBy scales the amount by the given scalar")
    public void test_multipliedBy() {
        Seconds test5 = Seconds.of(5);
        assertEquals(Seconds.of(0),   test5.multipliedBy(0));
        assertEquals(Seconds.of(5),   test5.multipliedBy(1));
        assertEquals(Seconds.of(10),  test5.multipliedBy(2));
        assertEquals(Seconds.of(15),  test5.multipliedBy(3));
        assertEquals(Seconds.of(-15), test5.multipliedBy(-3));
    }

    @Test
    @DisplayName("multipliedBy with a negative scalar negates the amount")
    public void test_multipliedBy_negate() {
        Seconds test5 = Seconds.of(5);
        assertEquals(Seconds.of(-15), test5.multipliedBy(-3));
    }

    @Test
    @DisplayName("multipliedBy throws ArithmeticException on positive overflow")
    public void test_multipliedBy_overflowTooBig() {
        assertThrows(ArithmeticException.class, () -> Seconds.of(Integer.MAX_VALUE / 2 + 1).multipliedBy(2));
    }

    @Test
    @DisplayName("multipliedBy throws ArithmeticException on negative overflow")
    public void test_multipliedBy_overflowTooSmall() {
        assertThrows(ArithmeticException.class, () -> Seconds.of(Integer.MIN_VALUE / 2 - 1).multipliedBy(2));
    }

    //-----------------------------------------------------------------------
    @Test
    @DisplayName("dividedBy divides the amount using integer division")
    public void test_dividedBy() {
        Seconds test12 = Seconds.of(12);
        assertEquals(Seconds.of(12), test12.dividedBy(1));
        assertEquals(Seconds.of(6),  test12.dividedBy(2));
        assertEquals(Seconds.of(4),  test12.dividedBy(3));
        assertEquals(Seconds.of(3),  test12.dividedBy(4));
        assertEquals(Seconds.of(2),  test12.dividedBy(5));
        assertEquals(Seconds.of(2),  test12.dividedBy(6));
        assertEquals(Seconds.of(-4), test12.dividedBy(-3));
    }

    @Test
    @DisplayName("dividedBy with a negative divisor negates the result")
    public void test_dividedBy_negate() {
        Seconds test12 = Seconds.of(12);
        assertEquals(Seconds.of(-4), test12.dividedBy(-3));
    }

    @Test
    @DisplayName("dividedBy throws ArithmeticException for divisor zero")
    public void test_dividedBy_divideByZero() {
        assertThrows(ArithmeticException.class, () -> Seconds.of(1).dividedBy(0));
    }

    //-----------------------------------------------------------------------
    @Test
    @DisplayName("negated returns the arithmetic negation of the amount")
    public void test_negated() {
        assertEquals(Seconds.of(0),               Seconds.of(0).negated());
        assertEquals(Seconds.of(-12),             Seconds.of(12).negated());
        assertEquals(Seconds.of(12),              Seconds.of(-12).negated());
        assertEquals(Seconds.of(-Integer.MAX_VALUE), Seconds.of(Integer.MAX_VALUE).negated());
    }

    @Test
    @DisplayName("negated throws ArithmeticException for Integer.MIN_VALUE (unrepresentable positive)")
    public void test_negated_overflow() {
        assertThrows(ArithmeticException.class, () -> Seconds.of(Integer.MIN_VALUE).negated());
    }

    //-----------------------------------------------------------------------
    @Test
    @DisplayName("abs returns the non-negative magnitude of the amount")
    public void test_abs() {
        assertEquals(Seconds.of(0),               Seconds.of(0).abs());
        assertEquals(Seconds.of(12),              Seconds.of(12).abs());
        assertEquals(Seconds.of(12),              Seconds.of(-12).abs());
        assertEquals(Seconds.of(Integer.MAX_VALUE), Seconds.of(Integer.MAX_VALUE).abs());
        assertEquals(Seconds.of(Integer.MAX_VALUE), Seconds.of(-Integer.MAX_VALUE).abs());
    }

    @Test
    @DisplayName("abs throws ArithmeticException for Integer.MIN_VALUE (unrepresentable positive)")
    public void test_abs_overflow() {
        assertThrows(ArithmeticException.class, () -> Seconds.of(Integer.MIN_VALUE).abs());
    }

    //-----------------------------------------------------------------------
    @Test
    @DisplayName("addTo adds the seconds amount to a LocalTime")
    public void test_addTo() {
        LocalTime base = LocalTime.of(11, 30);
        assertEquals(LocalTime.of(11, 30, 0), Seconds.of(0).addTo(base));
        assertEquals(LocalTime.of(11, 30, 6), Seconds.of(6).addTo(base));
    }

    //-----------------------------------------------------------------------
    @Test
    @DisplayName("subtractFrom subtracts the seconds amount from a LocalTime")
    public void test_subtractFrom() {
        LocalTime base = LocalTime.of(11, 30);
        assertEquals(LocalTime.of(11, 30, 0),  Seconds.of(0).subtractFrom(base));
        assertEquals(LocalTime.of(11, 29, 54), Seconds.of(6).subtractFrom(base));
    }

    //-----------------------------------------------------------------------
    @Test
    @DisplayName("toDuration returns a Duration with the same number of seconds")
    public void test_toDuration() {
        for (int i = -20; i < 20; i++) {
            assertEquals(Duration.ofSeconds(i), Seconds.of(i).toDuration());
        }
    }

    //-----------------------------------------------------------------------
    @Test
    @DisplayName("compareTo orders Seconds by their value")
    public void test_compareTo() {
        Seconds test5 = Seconds.of(5);
        Seconds test6 = Seconds.of(6);
        assertEquals(0,  test5.compareTo(test5));
        assertEquals(-1, test5.compareTo(test6));
        assertEquals(1,  test6.compareTo(test5));
    }

    @Test
    @DisplayName("compareTo throws NullPointerException for null argument")
    public void test_compareTo_null() {
        Seconds test5 = Seconds.of(5);
        //noinspection DataFlowIssue - testing nulls
        assertThrows(NullPointerException.class, () -> test5.compareTo(null));
    }

    //-----------------------------------------------------------------------
    @Test
    @DisplayName("equals and hashCode follow the contract: equal amounts are equal, unequal amounts are not")
    public void test_equals_and_hashCode() {
        new EqualsTester()
            .addEqualityGroup(Seconds.of(5), Seconds.of(5))
            .addEqualityGroup(Seconds.of(6), Seconds.of(6))
            .testEquals();
    }

    //-----------------------------------------------------------------------
    @Test
    @DisplayName("toString returns ISO-8601 PTnS format")
    public void test_toString() {
        assertEquals("PT5S",  Seconds.of(5).toString());
        assertEquals("PT-1S", Seconds.of(-1).toString());
    }

}
