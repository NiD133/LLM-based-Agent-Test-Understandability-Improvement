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

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import com.google.common.testing.EqualsTester;

/**
 * Tests for {@link Seconds}.
 */
public class TestSeconds {

    // Conversion factors used by the factory methods and the ISO-8601 parser.
    private static final int SECONDS_PER_MINUTE = 60;
    private static final int SECONDS_PER_HOUR = 60 * 60;
    private static final int SECONDS_PER_DAY = 60 * 60 * 24;

    // Largest hour/minute counts that convert to seconds without overflowing an int.
    private static final int MAX_HOURS_WITHOUT_OVERFLOW = Integer.MAX_VALUE / SECONDS_PER_HOUR;
    private static final int MIN_HOURS_WITHOUT_OVERFLOW = Integer.MIN_VALUE / SECONDS_PER_HOUR;
    private static final int MAX_MINUTES_WITHOUT_OVERFLOW = Integer.MAX_VALUE / SECONDS_PER_MINUTE;
    private static final int MIN_MINUTES_WITHOUT_OVERFLOW = Integer.MIN_VALUE / SECONDS_PER_MINUTE;

    //-----------------------------------------------------------------------
    // serialization
    //-----------------------------------------------------------------------
    @Test
    public void test_isSerializable() {
        assertTrue(Serializable.class.isAssignableFrom(Seconds.class));
    }

    @Test
    public void test_deserializationSingleton() throws Exception {
        Seconds original = Seconds.ZERO;

        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        try (ObjectOutputStream out = new ObjectOutputStream(bytes)) {
            out.writeObject(original);
        }

        try (ObjectInputStream in = new ObjectInputStream(new ByteArrayInputStream(bytes.toByteArray()))) {
            // Deserialization must resolve back to the shared ZERO singleton.
            assertSame(original, in.readObject());
        }
    }

    //-----------------------------------------------------------------------
    // constants and factory methods
    //-----------------------------------------------------------------------
    @Test
    public void test_ZERO() {
        assertSame(Seconds.ZERO, Seconds.of(0));
        assertEquals(Seconds.ZERO, Seconds.of(0));
        assertEquals(0, Seconds.ZERO.getAmount());
        assertFalse(Seconds.ZERO.isNegative());
        assertTrue(Seconds.ZERO.isZero());
        assertFalse(Seconds.ZERO.isPositive());
    }

    @Test
    public void test_of() {
        assertEquals(0, Seconds.of(0).getAmount());
        assertEquals(1, Seconds.of(1).getAmount());
        assertEquals(2, Seconds.of(2).getAmount());
        assertEquals(Integer.MAX_VALUE, Seconds.of(Integer.MAX_VALUE).getAmount());
        assertEquals(-1, Seconds.of(-1).getAmount());
        assertEquals(-2, Seconds.of(-2).getAmount());
        assertEquals(Integer.MIN_VALUE, Seconds.of(Integer.MIN_VALUE).getAmount());
    }

    @Test
    public void test_ofMinusOne() {
        Seconds minusOne = Seconds.of(-1);
        assertEquals(-1, minusOne.getAmount());
        assertTrue(minusOne.isNegative());
        assertFalse(minusOne.isZero());
        assertFalse(minusOne.isPositive());
    }

    @Test
    public void test_ofPlusOne() {
        Seconds plusOne = Seconds.of(1);
        assertEquals(1, plusOne.getAmount());
        assertFalse(plusOne.isNegative());
        assertFalse(plusOne.isZero());
        assertTrue(plusOne.isPositive());
    }

    //-----------------------------------------------------------------------
    @Test
    public void test_ofHours() {
        assertEquals(0, Seconds.ofHours(0).getAmount());
        assertEquals(3600, Seconds.ofHours(1).getAmount());
        assertEquals(7200, Seconds.ofHours(2).getAmount());
        assertEquals(MAX_HOURS_WITHOUT_OVERFLOW * SECONDS_PER_HOUR,
                Seconds.ofHours(MAX_HOURS_WITHOUT_OVERFLOW).getAmount());
        assertEquals(-3600, Seconds.ofHours(-1).getAmount());
        assertEquals(-7200, Seconds.ofHours(-2).getAmount());
        assertEquals(MIN_HOURS_WITHOUT_OVERFLOW * SECONDS_PER_HOUR,
                Seconds.ofHours(MIN_HOURS_WITHOUT_OVERFLOW).getAmount());
    }

    @Test
    public void test_ofHours_overflow() {
        assertThrows(ArithmeticException.class,
                () -> Seconds.ofHours(MAX_HOURS_WITHOUT_OVERFLOW + SECONDS_PER_HOUR));
    }

    //-----------------------------------------------------------------------
    @Test
    public void test_ofMinutes() {
        assertEquals(0, Seconds.ofMinutes(0).getAmount());
        assertEquals(60, Seconds.ofMinutes(1).getAmount());
        assertEquals(120, Seconds.ofMinutes(2).getAmount());
        assertEquals(MAX_MINUTES_WITHOUT_OVERFLOW * SECONDS_PER_MINUTE,
                Seconds.ofMinutes(MAX_MINUTES_WITHOUT_OVERFLOW).getAmount());
        assertEquals(-60, Seconds.ofMinutes(-1).getAmount());
        assertEquals(-120, Seconds.ofMinutes(-2).getAmount());
        assertEquals(MIN_MINUTES_WITHOUT_OVERFLOW * SECONDS_PER_MINUTE,
                Seconds.ofMinutes(MIN_MINUTES_WITHOUT_OVERFLOW).getAmount());
    }

    @Test
    public void test_ofMinutes_overflow() {
        assertThrows(ArithmeticException.class,
                () -> Seconds.ofMinutes(MAX_MINUTES_WITHOUT_OVERFLOW + SECONDS_PER_MINUTE));
    }

    //-----------------------------------------------------------------------
    // parse(CharSequence)
    //-----------------------------------------------------------------------
    /**
     * Valid ISO-8601 period strings paired with the total number of seconds they represent.
     */
    public static Object[][] data_valid() {
        return new Object[][] {
            // seconds-only forms, including explicit signs
            {"PT0S", 0},
            {"PT1S", 1},
            {"PT2S", 2},
            {"PT123456789S", 123456789},
            {"PT+0S", 0},
            {"PT+2S", 2},
            {"PT-0S", 0},
            {"PT-2S", -2},

            // minutes-only forms
            {"PT0M", 0},
            {"PT1M", 60},
            {"PT2M", 120},
            {"PT1234M", 1234 * 60},
            {"PT+0M", 0},
            {"PT+2M", 120},
            {"PT-0M", 0},
            {"PT-2M", -120},

            // hours-only forms
            {"PT0H", 0},
            {"PT1H", 60 * 60},
            {"PT2H", 120 * 60},
            {"PT1234H", 1234 * 60 * 60},
            {"PT+0H", 0},
            {"PT+2H", 120 * 60},
            {"PT-0H", 0},
            {"PT-2H", -120 * 60},

            // days-only forms
            {"P0D", 0},
            {"P1D", 60 * 60 * 24},
            {"P2D", 120 * 60 * 24},
            {"P1234D", 1234 * 60 * 60 * 24},
            {"P+0D", 0},
            {"P+2D", 120 * 60 * 24},
            {"P-0D", 0},
            {"P-2D", -120 * 60 * 24},

            // mixed minutes and seconds
            {"PT0M0S", 0},
            {"PT2M3S", 2 * 60 + 3},
            {"PT+2M3S", 2 * 60 + 3},
            {"PT2M+3S", 2 * 60 + 3},
            {"PT-2M3S", -2 * 60 + 3},
            {"PT2M-3S", 2 * 60 - 3},
            {"PT-2M-3S", -2 * 60 - 3},

            // mixed hours and seconds
            {"PT0H0S", 0},
            {"PT2H3S", 2 * 3600 + 3},
            {"PT+2H3S", 2 * 3600 + 3},
            {"PT2H+3S", 2 * 3600 + 3},
            {"PT-2H3S", -2 * 3600 + 3},
            {"PT2H-3S", 2 * 3600 - 3},
            {"PT-2H-3S", -2 * 3600 - 3},

            // fully specified days/hours/minutes/seconds
            {"P0DT0H0M0S", 0},
            {"P5DT2H4M3S", 5 * 86400 + 2 * 3600 + 4 * 60 + 3},
        };
    }

    @ParameterizedTest
    @MethodSource("data_valid")
    public void test_parse_CharSequence_valid(String text, int expectedSeconds) {
        assertEquals(Seconds.of(expectedSeconds), Seconds.parse(text));
    }

    @ParameterizedTest
    @MethodSource("data_valid")
    public void test_parse_CharSequence_valid_initialPlus(String text, int expectedSeconds) {
        // A leading '+' on the whole period leaves the value unchanged.
        assertEquals(Seconds.of(expectedSeconds), Seconds.parse("+" + text));
    }

    @ParameterizedTest
    @MethodSource("data_valid")
    public void test_parse_CharSequence_valid_initialMinus(String text, int expectedSeconds) {
        // A leading '-' on the whole period negates the value.
        assertEquals(Seconds.of(-expectedSeconds), Seconds.parse("-" + text));
    }

    /**
     * Strings that are not valid second-based ISO-8601 periods.
     */
    public static Object[][] data_invalid() {
        return new Object[][] {
            {"P3W"},   // weeks are not supported
            {"P3Q"},   // unknown unit
            {"P1M2Y"}, // units out of order / years unsupported

            {"3"},     // missing 'P' prefix
            {"-3"},
            {"3S"},
            {"-3S"},
            {"P3S"},   // 'S' without the required 'T'
            {"P3"},    // number without a unit
            {"P-3"},
            {"PS"},    // unit without a number
            {"T3"},    // missing 'P'
            {"PT3"},   // number without a unit after 'T'
        };
    }

    @ParameterizedTest
    @MethodSource("data_invalid")
    public void test_parse_CharSequence_invalid(String text) {
        assertThrows(DateTimeParseException.class, () -> Seconds.parse(text));
    }

    @Test
    public void test_parse_CharSequence_null() {
        //noinspection DataFlowIssue - testing nulls
        assertThrows(NullPointerException.class, () -> Seconds.parse((CharSequence) null));
    }

    //-----------------------------------------------------------------------
    // plus(TemporalAmount)
    //-----------------------------------------------------------------------
    @Test
    public void test_plus_TemporalAmount_Seconds() {
        Seconds five = Seconds.of(5);
        assertEquals(Seconds.of(5), five.plus(Seconds.of(0)));
        assertEquals(Seconds.of(7), five.plus(Seconds.of(2)));
        assertEquals(Seconds.of(3), five.plus(Seconds.of(-2)));
        assertEquals(Seconds.of(Integer.MAX_VALUE), Seconds.of(Integer.MAX_VALUE - 1).plus(Seconds.of(1)));
        assertEquals(Seconds.of(Integer.MIN_VALUE), Seconds.of(Integer.MIN_VALUE + 1).plus(Seconds.of(-1)));
    }

    @Test
    public void test_plus_TemporalAmount_overflowTooBig() {
        assertThrows(ArithmeticException.class, () -> Seconds.of(Integer.MAX_VALUE - 1).plus(Seconds.of(2)));
    }

    @Test
    public void test_plus_TemporalAmount_overflowTooSmall() {
        assertThrows(ArithmeticException.class, () -> Seconds.of(Integer.MIN_VALUE + 1).plus(Seconds.of(-2)));
    }

    @Test
    public void test_plus_TemporalAmount_null() {
        //noinspection DataFlowIssue - testing nulls
        assertThrows(NullPointerException.class, () -> Seconds.of(Integer.MIN_VALUE + 1).plus(null));
    }

    //-----------------------------------------------------------------------
    // plus(int)
    //-----------------------------------------------------------------------
    @Test
    public void test_plus_int() {
        Seconds five = Seconds.of(5);
        assertEquals(Seconds.of(5), five.plus(0));
        assertEquals(Seconds.of(7), five.plus(2));
        assertEquals(Seconds.of(3), five.plus(-2));
        assertEquals(Seconds.of(Integer.MAX_VALUE), Seconds.of(Integer.MAX_VALUE - 1).plus(1));
        assertEquals(Seconds.of(Integer.MIN_VALUE), Seconds.of(Integer.MIN_VALUE + 1).plus(-1));
    }

    @Test
    public void test_plus_int_overflowTooBig() {
        assertThrows(ArithmeticException.class, () -> Seconds.of(Integer.MAX_VALUE - 1).plus(2));
    }

    @Test
    public void test_plus_int_overflowTooSmall() {
        assertThrows(ArithmeticException.class, () -> Seconds.of(Integer.MIN_VALUE + 1).plus(-2));
    }

    //-----------------------------------------------------------------------
    // minus(TemporalAmount)
    //-----------------------------------------------------------------------
    @Test
    public void test_minus_TemporalAmount_Seconds() {
        Seconds five = Seconds.of(5);
        assertEquals(Seconds.of(5), five.minus(Seconds.of(0)));
        assertEquals(Seconds.of(3), five.minus(Seconds.of(2)));
        assertEquals(Seconds.of(7), five.minus(Seconds.of(-2)));
        assertEquals(Seconds.of(Integer.MAX_VALUE), Seconds.of(Integer.MAX_VALUE - 1).minus(Seconds.of(-1)));
        assertEquals(Seconds.of(Integer.MIN_VALUE), Seconds.of(Integer.MIN_VALUE + 1).minus(Seconds.of(1)));
    }

    @Test
    public void test_minus_TemporalAmount_overflowTooBig() {
        assertThrows(ArithmeticException.class, () -> Seconds.of(Integer.MAX_VALUE - 1).minus(Seconds.of(-2)));
    }

    @Test
    public void test_minus_TemporalAmount_overflowTooSmall() {
        assertThrows(ArithmeticException.class, () -> Seconds.of(Integer.MIN_VALUE + 1).minus(Seconds.of(2)));
    }

    @Test
    public void test_minus_TemporalAmount_null() {
        //noinspection DataFlowIssue - testing nulls
        assertThrows(NullPointerException.class, () -> Seconds.of(Integer.MIN_VALUE + 1).minus(null));
    }

    //-----------------------------------------------------------------------
    // minus(int)
    //-----------------------------------------------------------------------
    @Test
    public void test_minus_int() {
        Seconds five = Seconds.of(5);
        assertEquals(Seconds.of(5), five.minus(0));
        assertEquals(Seconds.of(3), five.minus(2));
        assertEquals(Seconds.of(7), five.minus(-2));
        assertEquals(Seconds.of(Integer.MAX_VALUE), Seconds.of(Integer.MAX_VALUE - 1).minus(-1));
        assertEquals(Seconds.of(Integer.MIN_VALUE), Seconds.of(Integer.MIN_VALUE + 1).minus(1));
    }

    @Test
    public void test_minus_int_overflowTooBig() {
        assertThrows(ArithmeticException.class, () -> Seconds.of(Integer.MAX_VALUE - 1).minus(-2));
    }

    @Test
    public void test_minus_int_overflowTooSmall() {
        assertThrows(ArithmeticException.class, () -> Seconds.of(Integer.MIN_VALUE + 1).minus(2));
    }

    //-----------------------------------------------------------------------
    // multipliedBy(int)
    //-----------------------------------------------------------------------
    @Test
    public void test_multipliedBy() {
        Seconds five = Seconds.of(5);
        assertEquals(Seconds.of(0), five.multipliedBy(0));
        assertEquals(Seconds.of(5), five.multipliedBy(1));
        assertEquals(Seconds.of(10), five.multipliedBy(2));
        assertEquals(Seconds.of(15), five.multipliedBy(3));
        assertEquals(Seconds.of(-15), five.multipliedBy(-3));
    }

    @Test
    public void test_multipliedBy_negate() {
        Seconds five = Seconds.of(5);
        assertEquals(Seconds.of(-15), five.multipliedBy(-3));
    }

    @Test
    public void test_multipliedBy_overflowTooBig() {
        assertThrows(ArithmeticException.class, () -> Seconds.of(Integer.MAX_VALUE / 2 + 1).multipliedBy(2));
    }

    @Test
    public void test_multipliedBy_overflowTooSmall() {
        assertThrows(ArithmeticException.class, () -> Seconds.of(Integer.MIN_VALUE / 2 - 1).multipliedBy(2));
    }

    //-----------------------------------------------------------------------
    // dividedBy(int)
    //-----------------------------------------------------------------------
    @Test
    public void test_dividedBy() {
        Seconds twelve = Seconds.of(12);
        assertEquals(Seconds.of(12), twelve.dividedBy(1));
        assertEquals(Seconds.of(6), twelve.dividedBy(2));
        assertEquals(Seconds.of(4), twelve.dividedBy(3));
        assertEquals(Seconds.of(3), twelve.dividedBy(4));
        assertEquals(Seconds.of(2), twelve.dividedBy(5));
        assertEquals(Seconds.of(2), twelve.dividedBy(6));
        assertEquals(Seconds.of(-4), twelve.dividedBy(-3));
    }

    @Test
    public void test_dividedBy_negate() {
        Seconds twelve = Seconds.of(12);
        assertEquals(Seconds.of(-4), twelve.dividedBy(-3));
    }

    @Test
    public void test_dividedBy_divideByZero() {
        assertThrows(ArithmeticException.class, () -> Seconds.of(1).dividedBy(0));
    }

    //-----------------------------------------------------------------------
    // negated()
    //-----------------------------------------------------------------------
    @Test
    public void test_negated() {
        assertEquals(Seconds.of(0), Seconds.of(0).negated());
        assertEquals(Seconds.of(-12), Seconds.of(12).negated());
        assertEquals(Seconds.of(12), Seconds.of(-12).negated());
        assertEquals(Seconds.of(-Integer.MAX_VALUE), Seconds.of(Integer.MAX_VALUE).negated());
    }

    @Test
    public void test_negated_overflow() {
        // Negating Integer.MIN_VALUE has no positive int counterpart.
        assertThrows(ArithmeticException.class, () -> Seconds.of(Integer.MIN_VALUE).negated());
    }

    //-----------------------------------------------------------------------
    // abs()
    //-----------------------------------------------------------------------
    @Test
    public void test_abs() {
        assertEquals(Seconds.of(0), Seconds.of(0).abs());
        assertEquals(Seconds.of(12), Seconds.of(12).abs());
        assertEquals(Seconds.of(12), Seconds.of(-12).abs());
        assertEquals(Seconds.of(Integer.MAX_VALUE), Seconds.of(Integer.MAX_VALUE).abs());
        assertEquals(Seconds.of(Integer.MAX_VALUE), Seconds.of(-Integer.MAX_VALUE).abs());
    }

    @Test
    public void test_abs_overflow() {
        // abs(Integer.MIN_VALUE) overflows for the same reason negation does.
        assertThrows(ArithmeticException.class, () -> Seconds.of(Integer.MIN_VALUE).abs());
    }

    //-----------------------------------------------------------------------
    // addTo / subtractFrom
    //-----------------------------------------------------------------------
    @Test
    public void test_addTo() {
        LocalTime base = LocalTime.of(11, 30);
        assertEquals(LocalTime.of(11, 30), Seconds.of(0).addTo(base));
        assertEquals(LocalTime.of(11, 30, 6), Seconds.of(6).addTo(base));
    }

    @Test
    public void test_subtractFrom() {
        LocalTime base = LocalTime.of(11, 30);
        assertEquals(LocalTime.of(11, 30), Seconds.of(0).subtractFrom(base));
        assertEquals(LocalTime.of(11, 29, 54), Seconds.of(6).subtractFrom(base));
    }

    //-----------------------------------------------------------------------
    // toDuration()
    //-----------------------------------------------------------------------
    @Test
    public void test_toDuration() {
        for (int amount = -20; amount < 20; amount++) {
            assertEquals(Duration.ofSeconds(amount), Seconds.of(amount).toDuration());
        }
    }

    //-----------------------------------------------------------------------
    // compareTo()
    //-----------------------------------------------------------------------
    @Test
    public void test_compareTo() {
        Seconds five = Seconds.of(5);
        Seconds six = Seconds.of(6);
        assertEquals(0, five.compareTo(five));
        assertEquals(-1, five.compareTo(six));
        assertEquals(1, six.compareTo(five));
    }

    @Test
    public void test_compareTo_null() {
        Seconds five = Seconds.of(5);
        //noinspection DataFlowIssue - testing nulls
        assertThrows(NullPointerException.class, () -> five.compareTo(null));
    }

    //-----------------------------------------------------------------------
    // equals() and hashCode()
    //-----------------------------------------------------------------------
    @Test
    public void test_equals_and_hashCode() {
        new EqualsTester()
            .addEqualityGroup(Seconds.of(5), Seconds.of(5))
            .addEqualityGroup(Seconds.of(6), Seconds.of(6))
            .testEquals();
    }

    //-----------------------------------------------------------------------
    // toString()
    //-----------------------------------------------------------------------
    @Test
    public void test_toString() {
        assertEquals("PT5S", Seconds.of(5).toString());
        assertEquals("PT-1S", Seconds.of(-1).toString());
    }

}
