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
 * Tests for {@link Hours}.
 */
public class TestHours {

    /** Number of hours in a day, used when building expectations for day-based parsing. */
    private static final int HOURS_PER_DAY = 24;

    /** The largest amount that can overflow when one more hour is added. */
    private static final int MAX_HOURS = Integer.MAX_VALUE;

    /** The smallest amount that can overflow when one more hour is subtracted. */
    private static final int MIN_HOURS = Integer.MIN_VALUE;

    //-----------------------------------------------------------------------
    // serialization
    //-----------------------------------------------------------------------
    @Test
    public void hours_implementsSerializable() {
        assertTrue(Serializable.class.isAssignableFrom(Hours.class));
    }

    @Test
    public void deserialization_preservesSingletonIdentity() throws Exception {
        Hours original = Hours.ZERO;

        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        try (ObjectOutputStream out = new ObjectOutputStream(bytes)) {
            out.writeObject(original);
        }
        try (ObjectInputStream in = new ObjectInputStream(new ByteArrayInputStream(bytes.toByteArray()))) {
            assertSame(original, in.readObject());
        }
    }

    //-----------------------------------------------------------------------
    // ZERO constant and of(int) factory
    //-----------------------------------------------------------------------
    @Test
    public void zeroConstant_isTheSameInstanceAndHasZeroState() {
        assertSame(Hours.ZERO, Hours.of(0));
        assertEquals(Hours.ZERO, Hours.of(0));
        assertEquals(0, Hours.ZERO.getAmount());
        assertFalse(Hours.ZERO.isNegative());
        assertTrue(Hours.ZERO.isZero());
        assertFalse(Hours.ZERO.isPositive());
    }

    @Test
    public void of_storesTheGivenAmountIncludingExtremes() {
        assertEquals(0, Hours.of(0).getAmount());
        assertEquals(1, Hours.of(1).getAmount());
        assertEquals(2, Hours.of(2).getAmount());
        assertEquals(MAX_HOURS, Hours.of(MAX_HOURS).getAmount());
        assertEquals(-1, Hours.of(-1).getAmount());
        assertEquals(-2, Hours.of(-2).getAmount());
        assertEquals(MIN_HOURS, Hours.of(MIN_HOURS).getAmount());
    }

    @Test
    public void of_negativeAmount_isNegativeOnly() {
        Hours minusOne = Hours.of(-1);
        assertEquals(-1, minusOne.getAmount());
        assertTrue(minusOne.isNegative());
        assertFalse(minusOne.isZero());
        assertFalse(minusOne.isPositive());
    }

    @Test
    public void of_positiveAmount_isPositiveOnly() {
        Hours plusOne = Hours.of(1);
        assertEquals(1, plusOne.getAmount());
        assertFalse(plusOne.isNegative());
        assertFalse(plusOne.isZero());
        assertTrue(plusOne.isPositive());
    }

    //-----------------------------------------------------------------------
    // parse(CharSequence)
    //-----------------------------------------------------------------------
    public static Object[][] data_valid() {
        return new Object[][] {
            // hour-only forms, suffix "H"
            {"PT0H", 0},
            {"PT1H", 1},
            {"PT2H", 2},
            {"PT123456789H", 123456789},
            {"PT+0H", 0},
            {"PT+2H", 2},
            {"PT-0H", 0},
            {"PT-2H", -2},

            // day-only forms, suffix "D" (each day is 24 hours)
            {"P0D", 0 * HOURS_PER_DAY},
            {"P1D", 1 * HOURS_PER_DAY},
            {"P2D", 2 * HOURS_PER_DAY},
            {"P1234567D", 1234567 * HOURS_PER_DAY},
            {"P+0D", 0 * HOURS_PER_DAY},
            {"P+2D", 2 * HOURS_PER_DAY},
            {"P-0D", 0 * HOURS_PER_DAY},
            {"P-2D", -2 * HOURS_PER_DAY},

            // combined day and hour forms
            {"P0DT0H", 0},
            {"P1DT2H", 1 * HOURS_PER_DAY + 2},
        };
    }

    @ParameterizedTest
    @MethodSource("data_valid")
    public void parse_validText_returnsExpectedHours(String text, int expectedHours) {
        assertEquals(Hours.of(expectedHours), Hours.parse(text));
    }

    @ParameterizedTest
    @MethodSource("data_valid")
    public void parse_validTextWithLeadingPlus_keepsSign(String text, int expectedHours) {
        assertEquals(Hours.of(expectedHours), Hours.parse("+" + text));
    }

    @ParameterizedTest
    @MethodSource("data_valid")
    public void parse_validTextWithLeadingMinus_negatesWholeAmount(String text, int expectedHours) {
        assertEquals(Hours.of(-expectedHours), Hours.parse("-" + text));
    }

    public static Object[][] data_invalid() {
        return new Object[][] {
            {"P3W"},
            {"P3Q"},
            {"P1M2Y"},

            {"3"},
            {"-3"},
            {"3H"},
            {"-3H"},
            {"P3H"},
            {"P3"},
            {"P-3"},
            {"PH"},
            {"T"},
            {"T3H"},
        };
    }

    @ParameterizedTest
    @MethodSource("data_invalid")
    public void parse_invalidText_throwsDateTimeParseException(String text) {
        assertThrows(DateTimeParseException.class, () -> Hours.parse(text));
    }

    @Test
    public void parse_null_throwsNullPointerException() {
        //noinspection DataFlowIssue - testing nulls
        assertThrows(NullPointerException.class, () -> Hours.parse((CharSequence) null));
    }

    //-----------------------------------------------------------------------
    // plus(TemporalAmount)
    //-----------------------------------------------------------------------
    @Test
    public void plus_hoursAmount_addsTheAmount() {
        Hours five = Hours.of(5);
        assertEquals(Hours.of(5), five.plus(Hours.of(0)));
        assertEquals(Hours.of(7), five.plus(Hours.of(2)));
        assertEquals(Hours.of(3), five.plus(Hours.of(-2)));
        assertEquals(Hours.of(MAX_HOURS), Hours.of(MAX_HOURS - 1).plus(Hours.of(1)));
        assertEquals(Hours.of(MIN_HOURS), Hours.of(MIN_HOURS + 1).plus(Hours.of(-1)));
    }

    @Test
    public void plus_durationAmount_addsEquivalentHours() {
        Hours five = Hours.of(5);
        assertEquals(Hours.of(5), five.plus(Duration.ofHours(0)));
        assertEquals(Hours.of(7), five.plus(Duration.ofHours(2)));
        assertEquals(Hours.of(3), five.plus(Duration.ofHours(-2)));
        assertEquals(Hours.of(MAX_HOURS), Hours.of(MAX_HOURS - 1).plus(Duration.ofHours(1)));
        assertEquals(Hours.of(MIN_HOURS), Hours.of(MIN_HOURS + 1).plus(Duration.ofHours(-1)));
    }

    @Test
    public void plus_temporalAmount_overflowingAboveMax_throws() {
        assertThrows(ArithmeticException.class, () -> Hours.of(MAX_HOURS - 1).plus(Hours.of(2)));
    }

    @Test
    public void plus_temporalAmount_overflowingBelowMin_throws() {
        assertThrows(ArithmeticException.class, () -> Hours.of(MIN_HOURS + 1).plus(Hours.of(-2)));
    }

    @Test
    public void plus_nullTemporalAmount_throwsNullPointerException() {
        //noinspection DataFlowIssue - testing nulls
        assertThrows(NullPointerException.class, () -> Hours.of(MIN_HOURS + 1).plus(null));
    }

    //-----------------------------------------------------------------------
    // plus(int)
    //-----------------------------------------------------------------------
    @Test
    public void plus_int_addsTheAmount() {
        Hours five = Hours.of(5);
        assertEquals(Hours.of(5), five.plus(0));
        assertEquals(Hours.of(7), five.plus(2));
        assertEquals(Hours.of(3), five.plus(-2));
        assertEquals(Hours.of(MAX_HOURS), Hours.of(MAX_HOURS - 1).plus(1));
        assertEquals(Hours.of(MIN_HOURS), Hours.of(MIN_HOURS + 1).plus(-1));
    }

    @Test
    public void plus_int_overflowingAboveMax_throws() {
        assertThrows(ArithmeticException.class, () -> Hours.of(MAX_HOURS - 1).plus(2));
    }

    @Test
    public void plus_int_overflowingBelowMin_throws() {
        assertThrows(ArithmeticException.class, () -> Hours.of(MIN_HOURS + 1).plus(-2));
    }

    //-----------------------------------------------------------------------
    // minus(TemporalAmount)
    //-----------------------------------------------------------------------
    @Test
    public void minus_hoursAmount_subtractsTheAmount() {
        Hours five = Hours.of(5);
        assertEquals(Hours.of(5), five.minus(Hours.of(0)));
        assertEquals(Hours.of(3), five.minus(Hours.of(2)));
        assertEquals(Hours.of(7), five.minus(Hours.of(-2)));
        assertEquals(Hours.of(MAX_HOURS), Hours.of(MAX_HOURS - 1).minus(Hours.of(-1)));
        assertEquals(Hours.of(MIN_HOURS), Hours.of(MIN_HOURS + 1).minus(Hours.of(1)));
    }

    @Test
    public void minus_durationAmount_subtractsEquivalentHours() {
        Hours five = Hours.of(5);
        assertEquals(Hours.of(5), five.minus(Duration.ofHours(0)));
        assertEquals(Hours.of(3), five.minus(Duration.ofHours(2)));
        assertEquals(Hours.of(7), five.minus(Duration.ofHours(-2)));
        assertEquals(Hours.of(MAX_HOURS), Hours.of(MAX_HOURS - 1).minus(Duration.ofHours(-1)));
        assertEquals(Hours.of(MIN_HOURS), Hours.of(MIN_HOURS + 1).minus(Duration.ofHours(1)));
    }

    @Test
    public void minus_temporalAmount_overflowingAboveMax_throws() {
        assertThrows(ArithmeticException.class, () -> Hours.of(MAX_HOURS - 1).minus(Hours.of(-2)));
    }

    @Test
    public void minus_temporalAmount_overflowingBelowMin_throws() {
        assertThrows(ArithmeticException.class, () -> Hours.of(MIN_HOURS + 1).minus(Hours.of(2)));
    }

    @Test
    public void minus_nullTemporalAmount_throwsNullPointerException() {
        //noinspection DataFlowIssue - testing nulls
        assertThrows(NullPointerException.class, () -> Hours.of(MIN_HOURS + 1).minus(null));
    }

    //-----------------------------------------------------------------------
    // minus(int)
    //-----------------------------------------------------------------------
    @Test
    public void minus_int_subtractsTheAmount() {
        Hours five = Hours.of(5);
        assertEquals(Hours.of(5), five.minus(0));
        assertEquals(Hours.of(3), five.minus(2));
        assertEquals(Hours.of(7), five.minus(-2));
        assertEquals(Hours.of(MAX_HOURS), Hours.of(MAX_HOURS - 1).minus(-1));
        assertEquals(Hours.of(MIN_HOURS), Hours.of(MIN_HOURS + 1).minus(1));
    }

    @Test
    public void minus_int_overflowingAboveMax_throws() {
        assertThrows(ArithmeticException.class, () -> Hours.of(MAX_HOURS - 1).minus(-2));
    }

    @Test
    public void minus_int_overflowingBelowMin_throws() {
        assertThrows(ArithmeticException.class, () -> Hours.of(MIN_HOURS + 1).minus(2));
    }

    //-----------------------------------------------------------------------
    // multipliedBy(int)
    //-----------------------------------------------------------------------
    @Test
    public void multipliedBy_scalesTheAmount() {
        Hours five = Hours.of(5);
        assertEquals(Hours.of(0), five.multipliedBy(0));
        assertEquals(Hours.of(5), five.multipliedBy(1));
        assertEquals(Hours.of(10), five.multipliedBy(2));
        assertEquals(Hours.of(15), five.multipliedBy(3));
        assertEquals(Hours.of(-15), five.multipliedBy(-3));
    }

    @Test
    public void multipliedBy_negativeScalar_negatesResult() {
        Hours five = Hours.of(5);
        assertEquals(Hours.of(-15), five.multipliedBy(-3));
    }

    @Test
    public void multipliedBy_overflowingAboveMax_throws() {
        assertThrows(ArithmeticException.class, () -> Hours.of(MAX_HOURS / 2 + 1).multipliedBy(2));
    }

    @Test
    public void multipliedBy_overflowingBelowMin_throws() {
        assertThrows(ArithmeticException.class, () -> Hours.of(MIN_HOURS / 2 - 1).multipliedBy(2));
    }

    //-----------------------------------------------------------------------
    // dividedBy(int)
    //-----------------------------------------------------------------------
    @Test
    public void dividedBy_usesIntegerDivision() {
        Hours twelve = Hours.of(12);
        assertEquals(Hours.of(12), twelve.dividedBy(1));
        assertEquals(Hours.of(6), twelve.dividedBy(2));
        assertEquals(Hours.of(4), twelve.dividedBy(3));
        assertEquals(Hours.of(3), twelve.dividedBy(4));
        assertEquals(Hours.of(2), twelve.dividedBy(5));
        assertEquals(Hours.of(2), twelve.dividedBy(6));
        assertEquals(Hours.of(-4), twelve.dividedBy(-3));
    }

    @Test
    public void dividedBy_negativeDivisor_negatesResult() {
        Hours twelve = Hours.of(12);
        assertEquals(Hours.of(-4), twelve.dividedBy(-3));
    }

    @Test
    public void dividedBy_zero_throws() {
        assertThrows(ArithmeticException.class, () -> Hours.of(1).dividedBy(0));
    }

    //-----------------------------------------------------------------------
    // negated()
    //-----------------------------------------------------------------------
    @Test
    public void negated_flipsTheSign() {
        assertEquals(Hours.of(0), Hours.of(0).negated());
        assertEquals(Hours.of(-12), Hours.of(12).negated());
        assertEquals(Hours.of(12), Hours.of(-12).negated());
        assertEquals(Hours.of(-MAX_HOURS), Hours.of(MAX_HOURS).negated());
    }

    @Test
    public void negated_minValue_overflows() {
        assertThrows(ArithmeticException.class, () -> Hours.of(MIN_HOURS).negated());
    }

    //-----------------------------------------------------------------------
    // abs()
    //-----------------------------------------------------------------------
    @Test
    public void abs_returnsNonNegativeAmount() {
        assertEquals(Hours.of(0), Hours.of(0).abs());
        assertEquals(Hours.of(12), Hours.of(12).abs());
        assertEquals(Hours.of(12), Hours.of(-12).abs());
        assertEquals(Hours.of(MAX_HOURS), Hours.of(MAX_HOURS).abs());
        assertEquals(Hours.of(MAX_HOURS), Hours.of(-MAX_HOURS).abs());
    }

    @Test
    public void abs_minValue_overflows() {
        assertThrows(ArithmeticException.class, () -> Hours.of(MIN_HOURS).abs());
    }

    //-----------------------------------------------------------------------
    // addTo / subtractFrom
    //-----------------------------------------------------------------------
    @Test
    public void addTo_addsHoursToTemporal() {
        LocalTime base = LocalTime.of(11, 30);
        assertEquals(LocalTime.of(11, 30), Hours.of(0).addTo(base));
        assertEquals(LocalTime.of(17, 30), Hours.of(6).addTo(base));
    }

    @Test
    public void subtractFrom_subtractsHoursFromTemporal() {
        LocalTime base = LocalTime.of(11, 30);
        assertEquals(LocalTime.of(11, 30), Hours.of(0).subtractFrom(base));
        assertEquals(LocalTime.of(5, 30), Hours.of(6).subtractFrom(base));
    }

    //-----------------------------------------------------------------------
    // conversion to Duration
    //-----------------------------------------------------------------------
    @SuppressWarnings("deprecation")
    @Test
    public void toDuration_andToPeriod_returnEquivalentDuration() {
        for (int amount = -20; amount < 20; amount++) {
            assertEquals(Duration.ofHours(amount), Hours.of(amount).toPeriod());
            assertEquals(Duration.ofHours(amount), Hours.of(amount).toDuration());
        }
    }

    //-----------------------------------------------------------------------
    // compareTo
    //-----------------------------------------------------------------------
    @Test
    public void compareTo_ordersBySize() {
        Hours five = Hours.of(5);
        Hours six = Hours.of(6);
        assertEquals(0, five.compareTo(five));
        assertEquals(-1, five.compareTo(six));
        assertEquals(1, six.compareTo(five));
    }

    @Test
    public void compareTo_null_throwsNullPointerException() {
        Hours five = Hours.of(5);
        //noinspection DataFlowIssue - testing nulls
        assertThrows(NullPointerException.class, () -> five.compareTo(null));
    }

    //-----------------------------------------------------------------------
    // equals / hashCode
    //-----------------------------------------------------------------------
    @Test
    public void equals_and_hashCode_areConsistentWithAmount() {
        new EqualsTester()
            .addEqualityGroup(Hours.of(5), Hours.of(5))
            .addEqualityGroup(Hours.of(6), Hours.of(6))
            .testEquals();
    }

    //-----------------------------------------------------------------------
    // toString
    //-----------------------------------------------------------------------
    @Test
    public void toString_usesIso8601Format() {
        assertEquals("PT5H", Hours.of(5).toString());
        assertEquals("PT-1H", Hours.of(-1).toString());
    }

}
