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
 * Tests for {@link Minutes}.
 */
public class TestMinutes {

    // Conversion factors used to express the expected results in a readable form.
    private static final int MINUTES_PER_HOUR = 60;
    private static final int MINUTES_PER_DAY = 24 * 60;

    // The largest/smallest hour count that can be converted to minutes without overflow.
    private static final int MAX_HOURS_WITHOUT_OVERFLOW = Integer.MAX_VALUE / 60;
    private static final int MIN_HOURS_WITHOUT_OVERFLOW = Integer.MIN_VALUE / 60;

    //-----------------------------------------------------------------------
    // Serialization
    //-----------------------------------------------------------------------
    @Test
    public void minutesClassIsSerializable() {
        assertTrue(Serializable.class.isAssignableFrom(Minutes.class));
    }

    @Test
    public void deserializationReturnsTheSingletonInstance() throws Exception {
        Minutes original = Minutes.ZERO;

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try (ObjectOutputStream oos = new ObjectOutputStream(baos)) {
            oos.writeObject(original);
        }
        try (ObjectInputStream ois = new ObjectInputStream(new ByteArrayInputStream(baos.toByteArray()))) {
            assertSame(original, ois.readObject(), "Deserialized ZERO should be the same singleton");
        }
    }

    //-----------------------------------------------------------------------
    // ZERO constant
    //-----------------------------------------------------------------------
    @Test
    public void zeroConstantIsTheCanonicalZeroValue() {
        assertSame(Minutes.ZERO, Minutes.of(0));
        assertEquals(Minutes.ZERO, Minutes.of(0));
        assertEquals(0, Minutes.ZERO.getAmount());
        assertFalse(Minutes.ZERO.isNegative());
        assertTrue(Minutes.ZERO.isZero());
        assertFalse(Minutes.ZERO.isPositive());
    }

    //-----------------------------------------------------------------------
    // of(int)
    //-----------------------------------------------------------------------
    @Test
    public void of_keepsTheGivenAmountAcrossTheIntRange() {
        assertEquals(0, Minutes.of(0).getAmount());
        assertEquals(1, Minutes.of(1).getAmount());
        assertEquals(2, Minutes.of(2).getAmount());
        assertEquals(Integer.MAX_VALUE, Minutes.of(Integer.MAX_VALUE).getAmount());
        assertEquals(-1, Minutes.of(-1).getAmount());
        assertEquals(-2, Minutes.of(-2).getAmount());
        assertEquals(Integer.MIN_VALUE, Minutes.of(Integer.MIN_VALUE).getAmount());
    }

    @Test
    public void of_negativeAmountIsReportedAsNegative() {
        assertEquals(-1, Hours.of(-1).getAmount());
        assertTrue(Minutes.of(-1).isNegative());
        assertFalse(Minutes.of(-1).isZero());
        assertFalse(Minutes.of(-1).isPositive());
    }

    @Test
    public void of_positiveAmountIsReportedAsPositive() {
        assertEquals(1, Hours.of(1).getAmount());
        assertFalse(Minutes.of(1).isNegative());
        assertFalse(Minutes.of(1).isZero());
        assertTrue(Minutes.of(1).isPositive());
    }

    //-----------------------------------------------------------------------
    // ofHours(int)
    //-----------------------------------------------------------------------
    @Test
    public void ofHours_convertsHoursToMinutes() {
        assertEquals(0, Minutes.ofHours(0).getAmount());
        assertEquals(60, Minutes.ofHours(1).getAmount());
        assertEquals(120, Minutes.ofHours(2).getAmount());
        assertEquals(MAX_HOURS_WITHOUT_OVERFLOW * MINUTES_PER_HOUR,
                Minutes.ofHours(MAX_HOURS_WITHOUT_OVERFLOW).getAmount());
        assertEquals(-60, Minutes.ofHours(-1).getAmount());
        assertEquals(-120, Minutes.ofHours(-2).getAmount());
        assertEquals(MIN_HOURS_WITHOUT_OVERFLOW * MINUTES_PER_HOUR,
                Minutes.ofHours(MIN_HOURS_WITHOUT_OVERFLOW).getAmount());
    }

    @Test
    public void ofHours_throwsWhenConversionOverflows() {
        assertThrows(ArithmeticException.class,
                () -> Minutes.ofHours(MAX_HOURS_WITHOUT_OVERFLOW + 60));
    }

    //-----------------------------------------------------------------------
    // parse(CharSequence)
    //-----------------------------------------------------------------------
    public static Object[][] data_valid() {
        return new Object[][] {
            // minutes-only forms
            {"PT0M", 0},
            {"PT1M", 1},
            {"PT2M", 2},
            {"PT123456789M", 123456789},
            {"PT+0M", 0},
            {"PT+2M", 2},
            {"PT-0M", 0},
            {"PT-2M", -2},

            // hours-only forms
            {"PT0H", 0},
            {"PT1H", 60},
            {"PT2H", 120},
            {"PT1234H", 1234 * MINUTES_PER_HOUR},
            {"PT+0H", 0},
            {"PT+2H", 120},
            {"PT-0H", 0},
            {"PT-2H", -120},

            // days-only forms
            {"P0D", 0},
            {"P1D", 1 * MINUTES_PER_DAY},
            {"P2D", 2 * MINUTES_PER_DAY},
            {"P1234D", 1234 * MINUTES_PER_DAY},
            {"P+0D", 0},
            {"P+2D", 2 * MINUTES_PER_DAY},
            {"P-0D", 0},
            {"P-2D", -2 * MINUTES_PER_DAY},

            // combined hours and minutes
            {"PT0H0M", 0},
            {"PT2H3M", 123},
            {"PT+2H3M", 123},
            {"PT2H+3M", 123},
            {"PT-2H3M", -117},
            {"PT2H-3M", 117},
            {"PT-2H-3M", -123},

            // combined days, hours and minutes
            {"P0DT0H0M", 0},
            {"P5DT2H4M", 5 * MINUTES_PER_DAY + 2 * MINUTES_PER_HOUR + 4},
        };
    }

    @ParameterizedTest
    @MethodSource("data_valid")
    public void parse_acceptsValidIso8601Text(String text, int expectedMinutes) {
        assertEquals(Minutes.of(expectedMinutes), Minutes.parse(text));
    }

    @ParameterizedTest
    @MethodSource("data_valid")
    public void parse_leadingPlusSignKeepsTheSign(String text, int expectedMinutes) {
        assertEquals(Minutes.of(expectedMinutes), Minutes.parse("+" + text));
    }

    @ParameterizedTest
    @MethodSource("data_valid")
    public void parse_leadingMinusSignNegatesTheWholeAmount(String text, int expectedMinutes) {
        assertEquals(Minutes.of(-expectedMinutes), Minutes.parse("-" + text));
    }

    public static Object[][] data_invalid() {
        return new Object[][] {
            {"P3W"},
            {"P3Q"},
            {"P1M2Y"},

            {"3"},
            {"-3"},
            {"3M"},
            {"-3M"},
            {"P3M"},
            {"P3"},
            {"P-3"},
            {"PM"},
            {"T3"},
            {"P3M"},
            {"PT3S"},
            {"PT3"},
        };
    }

    @ParameterizedTest
    @MethodSource("data_invalid")
    public void parse_rejectsMalformedText(String text) {
        assertThrows(DateTimeParseException.class, () -> Minutes.parse(text));
    }

    @Test
    public void parse_rejectsNullText() {
        //noinspection DataFlowIssue - testing nulls
        assertThrows(NullPointerException.class, () -> Minutes.parse((CharSequence) null));
    }

    //-----------------------------------------------------------------------
    // plus(TemporalAmount)
    //-----------------------------------------------------------------------
    @Test
    public void plusTemporalAmount_addsTheOtherAmount() {
        Minutes five = Minutes.of(5);
        assertEquals(Minutes.of(5), five.plus(Minutes.of(0)));
        assertEquals(Minutes.of(7), five.plus(Minutes.of(2)));
        assertEquals(Minutes.of(3), five.plus(Minutes.of(-2)));
        assertEquals(Minutes.of(Integer.MAX_VALUE), Minutes.of(Integer.MAX_VALUE - 1).plus(Minutes.of(1)));
        assertEquals(Minutes.of(Integer.MIN_VALUE), Minutes.of(Integer.MIN_VALUE + 1).plus(Minutes.of(-1)));
    }

    @Test
    public void plusTemporalAmount_throwsOnOverflowAboveMax() {
        assertThrows(ArithmeticException.class,
                () -> Minutes.of(Integer.MAX_VALUE - 1).plus(Minutes.of(2)));
    }

    @Test
    public void plusTemporalAmount_throwsOnOverflowBelowMin() {
        assertThrows(ArithmeticException.class,
                () -> Minutes.of(Integer.MIN_VALUE + 1).plus(Minutes.of(-2)));
    }

    @Test
    public void plusTemporalAmount_rejectsNull() {
        //noinspection DataFlowIssue - testing nulls
        assertThrows(NullPointerException.class,
                () -> Minutes.of(Integer.MIN_VALUE + 1).plus(null));
    }

    //-----------------------------------------------------------------------
    // plus(int)
    //-----------------------------------------------------------------------
    @Test
    public void plusInt_addsTheGivenMinutes() {
        Minutes five = Minutes.of(5);
        assertEquals(Minutes.of(5), five.plus(0));
        assertEquals(Minutes.of(7), five.plus(2));
        assertEquals(Minutes.of(3), five.plus(-2));
        assertEquals(Minutes.of(Integer.MAX_VALUE), Minutes.of(Integer.MAX_VALUE - 1).plus(1));
        assertEquals(Minutes.of(Integer.MIN_VALUE), Minutes.of(Integer.MIN_VALUE + 1).plus(-1));
    }

    @Test
    public void plusInt_throwsOnOverflowAboveMax() {
        assertThrows(ArithmeticException.class, () -> Minutes.of(Integer.MAX_VALUE - 1).plus(2));
    }

    @Test
    public void plusInt_throwsOnOverflowBelowMin() {
        assertThrows(ArithmeticException.class, () -> Minutes.of(Integer.MIN_VALUE + 1).plus(-2));
    }

    //-----------------------------------------------------------------------
    // minus(TemporalAmount)
    //-----------------------------------------------------------------------
    @Test
    public void minusTemporalAmount_subtractsTheOtherAmount() {
        Minutes five = Minutes.of(5);
        assertEquals(Minutes.of(5), five.minus(Minutes.of(0)));
        assertEquals(Minutes.of(3), five.minus(Minutes.of(2)));
        assertEquals(Minutes.of(7), five.minus(Minutes.of(-2)));
        assertEquals(Minutes.of(Integer.MAX_VALUE), Minutes.of(Integer.MAX_VALUE - 1).minus(Minutes.of(-1)));
        assertEquals(Minutes.of(Integer.MIN_VALUE), Minutes.of(Integer.MIN_VALUE + 1).minus(Minutes.of(1)));
    }

    @Test
    public void minusTemporalAmount_throwsOnOverflowAboveMax() {
        assertThrows(ArithmeticException.class,
                () -> Minutes.of(Integer.MAX_VALUE - 1).minus(Minutes.of(-2)));
    }

    @Test
    public void minusTemporalAmount_throwsOnOverflowBelowMin() {
        assertThrows(ArithmeticException.class,
                () -> Minutes.of(Integer.MIN_VALUE + 1).minus(Minutes.of(2)));
    }

    @Test
    public void minusTemporalAmount_rejectsNull() {
        //noinspection DataFlowIssue - testing nulls
        assertThrows(NullPointerException.class,
                () -> Minutes.of(Integer.MIN_VALUE + 1).minus(null));
    }

    //-----------------------------------------------------------------------
    // minus(int)
    //-----------------------------------------------------------------------
    @Test
    public void minusInt_subtractsTheGivenMinutes() {
        Minutes five = Minutes.of(5);
        assertEquals(Minutes.of(5), five.minus(0));
        assertEquals(Minutes.of(3), five.minus(2));
        assertEquals(Minutes.of(7), five.minus(-2));
        assertEquals(Minutes.of(Integer.MAX_VALUE), Minutes.of(Integer.MAX_VALUE - 1).minus(-1));
        assertEquals(Minutes.of(Integer.MIN_VALUE), Minutes.of(Integer.MIN_VALUE + 1).minus(1));
    }

    @Test
    public void minusInt_throwsOnOverflowAboveMax() {
        assertThrows(ArithmeticException.class, () -> Minutes.of(Integer.MAX_VALUE - 1).minus(-2));
    }

    @Test
    public void minusInt_throwsOnOverflowBelowMin() {
        assertThrows(ArithmeticException.class, () -> Minutes.of(Integer.MIN_VALUE + 1).minus(2));
    }

    //-----------------------------------------------------------------------
    // multipliedBy(int)
    //-----------------------------------------------------------------------
    @Test
    public void multipliedBy_scalesTheAmount() {
        Minutes five = Minutes.of(5);
        assertEquals(Minutes.of(0), five.multipliedBy(0));
        assertEquals(Minutes.of(5), five.multipliedBy(1));
        assertEquals(Minutes.of(10), five.multipliedBy(2));
        assertEquals(Minutes.of(15), five.multipliedBy(3));
        assertEquals(Minutes.of(-15), five.multipliedBy(-3));
    }

    @Test
    public void multipliedBy_negativeScalarFlipsTheSign() {
        Minutes five = Minutes.of(5);
        assertEquals(Minutes.of(-15), five.multipliedBy(-3));
    }

    @Test
    public void multipliedBy_throwsOnOverflowAboveMax() {
        assertThrows(ArithmeticException.class,
                () -> Minutes.of(Integer.MAX_VALUE / 2 + 1).multipliedBy(2));
    }

    @Test
    public void multipliedBy_throwsOnOverflowBelowMin() {
        assertThrows(ArithmeticException.class,
                () -> Minutes.of(Integer.MIN_VALUE / 2 - 1).multipliedBy(2));
    }

    //-----------------------------------------------------------------------
    // dividedBy(int)
    //-----------------------------------------------------------------------
    @Test
    public void dividedBy_usesIntegerDivision() {
        Minutes twelve = Minutes.of(12);
        assertEquals(Minutes.of(12), twelve.dividedBy(1));
        assertEquals(Minutes.of(6), twelve.dividedBy(2));
        assertEquals(Minutes.of(4), twelve.dividedBy(3));
        assertEquals(Minutes.of(3), twelve.dividedBy(4));
        assertEquals(Minutes.of(2), twelve.dividedBy(5));
        assertEquals(Minutes.of(2), twelve.dividedBy(6));
        assertEquals(Minutes.of(-4), twelve.dividedBy(-3));
    }

    @Test
    public void dividedBy_negativeDivisorFlipsTheSign() {
        Minutes twelve = Minutes.of(12);
        assertEquals(Minutes.of(-4), twelve.dividedBy(-3));
    }

    @Test
    public void dividedBy_throwsWhenDividingByZero() {
        assertThrows(ArithmeticException.class, () -> Minutes.of(1).dividedBy(0));
    }

    //-----------------------------------------------------------------------
    // negated()
    //-----------------------------------------------------------------------
    @Test
    public void negated_flipsTheSign() {
        assertEquals(Minutes.of(0), Minutes.of(0).negated());
        assertEquals(Minutes.of(-12), Minutes.of(12).negated());
        assertEquals(Minutes.of(12), Minutes.of(-12).negated());
        assertEquals(Minutes.of(-Integer.MAX_VALUE), Minutes.of(Integer.MAX_VALUE).negated());
    }

    @Test
    public void negated_throwsWhenNegatingMinValue() {
        assertThrows(ArithmeticException.class, () -> Minutes.of(Integer.MIN_VALUE).negated());
    }

    //-----------------------------------------------------------------------
    // abs()
    //-----------------------------------------------------------------------
    @Test
    public void abs_returnsTheMagnitude() {
        assertEquals(Minutes.of(0), Minutes.of(0).abs());
        assertEquals(Minutes.of(12), Minutes.of(12).abs());
        assertEquals(Minutes.of(12), Minutes.of(-12).abs());
        assertEquals(Minutes.of(Integer.MAX_VALUE), Minutes.of(Integer.MAX_VALUE).abs());
        assertEquals(Minutes.of(Integer.MAX_VALUE), Minutes.of(-Integer.MAX_VALUE).abs());
    }

    @Test
    public void abs_throwsWhenTakingAbsOfMinValue() {
        assertThrows(ArithmeticException.class, () -> Minutes.of(Integer.MIN_VALUE).abs());
    }

    //-----------------------------------------------------------------------
    // addTo(Temporal) / subtractFrom(Temporal)
    //-----------------------------------------------------------------------
    @Test
    public void addTo_addsTheMinutesToATemporal() {
        LocalTime base = LocalTime.of(11, 30);
        assertEquals(LocalTime.of(11, 30), Minutes.of(0).addTo(base));
        assertEquals(LocalTime.of(11, 36), Minutes.of(6).addTo(base));
    }

    @Test
    public void subtractFrom_subtractsTheMinutesFromATemporal() {
        LocalTime base = LocalTime.of(11, 30);
        assertEquals(LocalTime.of(11, 30), Minutes.of(0).subtractFrom(base));
        assertEquals(LocalTime.of(11, 24), Minutes.of(6).subtractFrom(base));
    }

    //-----------------------------------------------------------------------
    // toDuration()
    //-----------------------------------------------------------------------
    @Test
    public void toDuration_matchesAnEquivalentDuration() {
        for (int minutes = -20; minutes < 20; minutes++) {
            assertEquals(Duration.ofMinutes(minutes), Minutes.of(minutes).toDuration());
        }
    }

    //-----------------------------------------------------------------------
    // compareTo(Minutes)
    //-----------------------------------------------------------------------
    @Test
    public void compareTo_ordersBySize() {
        Minutes five = Minutes.of(5);
        Minutes six = Minutes.of(6);
        assertEquals(0, five.compareTo(five));
        assertEquals(-1, five.compareTo(six));
        assertEquals(1, six.compareTo(five));
    }

    @Test
    public void compareTo_rejectsNull() {
        Minutes five = Minutes.of(5);
        //noinspection DataFlowIssue - testing nulls
        assertThrows(NullPointerException.class, () -> five.compareTo(null));
    }

    //-----------------------------------------------------------------------
    // equals() / hashCode()
    //-----------------------------------------------------------------------
    @Test
    public void equalsAndHashCode_areBasedOnTheAmount() {
        new EqualsTester()
            .addEqualityGroup(Minutes.of(5), Minutes.of(5))
            .addEqualityGroup(Minutes.of(6), Minutes.of(6))
            .testEquals();
    }

    //-----------------------------------------------------------------------
    // toString()
    //-----------------------------------------------------------------------
    @Test
    public void toString_usesIso8601MinutesFormat() {
        assertEquals("PT5M", Minutes.of(5).toString());
        assertEquals("PT-1M", Minutes.of(-1).toString());
    }

}
