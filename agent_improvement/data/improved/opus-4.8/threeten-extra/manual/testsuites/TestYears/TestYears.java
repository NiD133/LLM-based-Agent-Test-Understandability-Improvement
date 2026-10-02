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
 * Tests for {@link Years}.
 * <p>
 * The tests are grouped by the feature of {@code Years} under test:
 * serialization, the shared constants, the factory methods, parsing,
 * arithmetic, conversion and the standard object methods.
 */
public class TestYears {

    // Boundary values used throughout the overflow tests. Naming them makes the
    // intent ("just inside the int range") clearer than the inline arithmetic.
    private static final int MAX_MINUS_ONE = Integer.MAX_VALUE - 1;
    private static final int MIN_PLUS_ONE = Integer.MIN_VALUE + 1;

    //-----------------------------------------------------------------------
    // serialization
    //-----------------------------------------------------------------------
    @Test
    public void years_isSerializable() {
        assertTrue(Serializable.class.isAssignableFrom(Years.class));
    }

    @Test
    public void deserialization_preservesSingletonIdentity() throws Exception {
        Years original = Years.ZERO;

        byte[] serialized = serialize(original);
        Object deserialized = deserialize(serialized);

        // readResolve() must return the same singleton instance, not a copy.
        assertSame(original, deserialized);
    }

    //-----------------------------------------------------------------------
    // constants: ZERO and ONE
    //-----------------------------------------------------------------------
    @Test
    public void zero_isCanonicalZeroValuedInstance() {
        assertSame(Years.ZERO, Years.of(0));
        assertEquals(Years.ZERO, Years.of(0));
        assertEquals(0, Years.ZERO.getAmount());
        assertFalse(Years.ZERO.isNegative());
        assertTrue(Years.ZERO.isZero());
        assertFalse(Years.ZERO.isPositive());
    }

    @Test
    public void one_isCanonicalOneValuedInstance() {
        assertSame(Years.ONE, Years.of(1));
        assertEquals(Years.ONE, Years.of(1));
        assertEquals(1, Years.ONE.getAmount());
        assertFalse(Years.ONE.isNegative());
        assertFalse(Years.ONE.isZero());
        assertTrue(Years.ONE.isPositive());
    }

    //-----------------------------------------------------------------------
    // of(int)
    //-----------------------------------------------------------------------
    @Test
    public void of_storesGivenAmount_acrossFullIntRange() {
        assertEquals(1, Years.of(1).getAmount());
        assertEquals(2, Years.of(2).getAmount());
        assertEquals(Integer.MAX_VALUE, Years.of(Integer.MAX_VALUE).getAmount());
        assertEquals(-1, Years.of(-1).getAmount());
        assertEquals(-2, Years.of(-2).getAmount());
        assertEquals(Integer.MIN_VALUE, Years.of(Integer.MIN_VALUE).getAmount());
    }

    @Test
    public void of_minusOne_isNegative() {
        Years minusOne = Years.of(-1);

        assertEquals(-1, minusOne.getAmount());
        assertTrue(minusOne.isNegative());
        assertFalse(minusOne.isZero());
        assertFalse(minusOne.isPositive());
    }

    //-----------------------------------------------------------------------
    // from(TemporalAmount)
    //-----------------------------------------------------------------------
    @Test
    public void from_zeroYearPeriod() {
        assertEquals(Years.of(0), Years.from(Period.ofYears(0)));
    }

    @Test
    public void from_wholeYearPeriod() {
        assertEquals(Years.of(2), Years.from(Period.ofYears(2)));
    }

    @Test
    public void from_monthsThatDivideIntoWholeYears() {
        // 24 months == 2 years.
        assertEquals(Years.of(2), Years.from(Period.ofMonths(24)));
    }

    @Test
    public void from_combinesYearsAndMonthsParts() {
        // 3 years + 24 months (= 2 years) == 5 years.
        assertEquals(Years.of(5), Years.from(Period.of(3, 24, 0)));
    }

    @Test
    public void from_convertsMultipleUnitsToYears() {
        // 2 decades (= 20 years) + (-12 months) (= -1 year) == 19 years.
        assertEquals(Years.of(19), Years.from(new MockDecadesMonths(2, -12)));
    }

    @Test
    public void from_monthsWithRemainder_throws() {
        // 3 months cannot be expressed as a whole number of years.
        assertThrows(DateTimeException.class, () -> Years.from(Period.ofMonths(3)));
    }

    @Test
    public void from_unitNotConvertibleToYears_throws() {
        assertThrows(DateTimeException.class, () -> Years.from(Period.ofDays(2)));
    }

    @Test
    public void from_null_throws() {
        //noinspection DataFlowIssue - testing nulls
        assertThrows(NullPointerException.class, () -> Years.from((TemporalAmount) null));
    }

    //-----------------------------------------------------------------------
    // parse(CharSequence)
    //-----------------------------------------------------------------------
    @Test
    public void parse_validIsoStrings() {
        assertEquals(Years.of(0), Years.parse("P0Y"));
        assertEquals(Years.of(1), Years.parse("P1Y"));
        assertEquals(Years.of(2), Years.parse("P2Y"));
        assertEquals(Years.of(123456789), Years.parse("P123456789Y"));
        // Negative amounts may be written with the sign on the value or on "P".
        assertEquals(Years.of(-2), Years.parse("P-2Y"));
        assertEquals(Years.of(-2), Years.parse("-P2Y"));
        // Two negations cancel out.
        assertEquals(Years.of(2), Years.parse("-P-2Y"));
    }

    public static Object[][] data_invalid() {
        return new Object[][] {
            {"P3M"},
            {"P3W"},
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
    public void parse_invalidString_throws(String str) {
        assertThrows(DateTimeParseException.class, () -> Years.parse(str));
    }

    @Test
    public void parse_null_throws() {
        //noinspection DataFlowIssue - testing nulls
        assertThrows(NullPointerException.class, () -> Years.parse((CharSequence) null));
    }

    //-----------------------------------------------------------------------
    // between(Temporal, Temporal)
    //-----------------------------------------------------------------------
    @Test
    public void between_countsWholeYearsFromStartToEnd() {
        assertEquals(Years.of(2), Years.between(LocalDate.of(2019, 1, 1), LocalDate.of(2021, 1, 1)));
    }

    @Test
    public void between_nullEndDate_throws() {
        //noinspection DataFlowIssue - testing nulls
        assertThrows(NullPointerException.class, () -> Years.between(LocalDate.now(), (Temporal) null));
    }

    @Test
    public void between_nullStartDate_throws() {
        //noinspection DataFlowIssue - testing nulls
        assertThrows(NullPointerException.class, () -> Years.between((Temporal) null, LocalDate.now()));
    }

    //-----------------------------------------------------------------------
    // get(TemporalUnit)
    //-----------------------------------------------------------------------
    @Test
    public void get_yearsUnit_returnsAmount() {
        assertEquals(6, Years.of(6).get(ChronoUnit.YEARS));
    }

    @Test
    public void get_unsupportedUnit_throws() {
        assertThrows(DateTimeException.class, () -> Years.of(6).get(IsoFields.QUARTER_YEARS));
    }

    //-----------------------------------------------------------------------
    // plus(TemporalAmount)
    //-----------------------------------------------------------------------
    @Test
    public void plus_yearsAmount_addsValues() {
        Years five = Years.of(5);
        assertEquals(Years.of(5), five.plus(Years.of(0)));
        assertEquals(Years.of(7), five.plus(Years.of(2)));
        assertEquals(Years.of(3), five.plus(Years.of(-2)));
        assertEquals(Years.of(Integer.MAX_VALUE), Years.of(MAX_MINUS_ONE).plus(Years.of(1)));
        assertEquals(Years.of(Integer.MIN_VALUE), Years.of(MIN_PLUS_ONE).plus(Years.of(-1)));
    }

    @Test
    public void plus_periodAmount_addsValues() {
        Years five = Years.of(5);
        assertEquals(Years.of(5), five.plus(Period.ofYears(0)));
        assertEquals(Years.of(7), five.plus(Period.ofYears(2)));
        assertEquals(Years.of(3), five.plus(Period.ofYears(-2)));
        assertEquals(Years.of(Integer.MAX_VALUE), Years.of(MAX_MINUS_ONE).plus(Period.ofYears(1)));
        assertEquals(Years.of(Integer.MIN_VALUE), Years.of(MIN_PLUS_ONE).plus(Period.ofYears(-1)));
    }

    @Test
    public void plus_periodWithMonths_throws() {
        assertThrows(DateTimeException.class, () -> Years.of(1).plus(Period.ofMonths(2)));
    }

    @Test
    public void plus_duration_throws() {
        assertThrows(DateTimeException.class, () -> Years.of(1).plus(Duration.ofHours(2)));
    }

    @Test
    public void plus_amount_overflowsAboveMax_throws() {
        assertThrows(ArithmeticException.class, () -> Years.of(MAX_MINUS_ONE).plus(Years.of(2)));
    }

    @Test
    public void plus_amount_overflowsBelowMin_throws() {
        assertThrows(ArithmeticException.class, () -> Years.of(MIN_PLUS_ONE).plus(Years.of(-2)));
    }

    @Test
    public void plus_nullAmount_throws() {
        //noinspection DataFlowIssue - testing nulls
        assertThrows(NullPointerException.class, () -> Years.of(MIN_PLUS_ONE).plus(null));
    }

    //-----------------------------------------------------------------------
    // plus(int)
    //-----------------------------------------------------------------------
    @Test
    public void plus_int_addsValues() {
        Years five = Years.of(5);
        assertEquals(Years.of(5), five.plus(0));
        assertEquals(Years.of(7), five.plus(2));
        assertEquals(Years.of(3), five.plus(-2));
        assertEquals(Years.of(Integer.MAX_VALUE), Years.of(MAX_MINUS_ONE).plus(1));
        assertEquals(Years.of(Integer.MIN_VALUE), Years.of(MIN_PLUS_ONE).plus(-1));
    }

    @Test
    public void plus_int_overflowsAboveMax_throws() {
        assertThrows(ArithmeticException.class, () -> Years.of(MAX_MINUS_ONE).plus(2));
    }

    @Test
    public void plus_int_overflowsBelowMin_throws() {
        assertThrows(ArithmeticException.class, () -> Years.of(MIN_PLUS_ONE).plus(-2));
    }

    //-----------------------------------------------------------------------
    // minus(TemporalAmount)
    //-----------------------------------------------------------------------
    @Test
    public void minus_yearsAmount_subtractsValues() {
        Years five = Years.of(5);
        assertEquals(Years.of(5), five.minus(Years.of(0)));
        assertEquals(Years.of(3), five.minus(Years.of(2)));
        assertEquals(Years.of(7), five.minus(Years.of(-2)));
        assertEquals(Years.of(Integer.MAX_VALUE), Years.of(MAX_MINUS_ONE).minus(Years.of(-1)));
        assertEquals(Years.of(Integer.MIN_VALUE), Years.of(MIN_PLUS_ONE).minus(Years.of(1)));
    }

    @Test
    public void minus_periodAmount_subtractsValues() {
        Years five = Years.of(5);
        assertEquals(Years.of(5), five.minus(Period.ofYears(0)));
        assertEquals(Years.of(3), five.minus(Period.ofYears(2)));
        assertEquals(Years.of(7), five.minus(Period.ofYears(-2)));
        assertEquals(Years.of(Integer.MAX_VALUE), Years.of(MAX_MINUS_ONE).minus(Period.ofYears(-1)));
        assertEquals(Years.of(Integer.MIN_VALUE), Years.of(MIN_PLUS_ONE).minus(Period.ofYears(1)));
    }

    @Test
    public void minus_periodWithMonths_throws() {
        assertThrows(DateTimeException.class, () -> Years.of(1).minus(Period.ofMonths(2)));
    }

    @Test
    public void minus_duration_throws() {
        assertThrows(DateTimeException.class, () -> Years.of(1).minus(Duration.ofHours(2)));
    }

    @Test
    public void minus_amount_overflowsAboveMax_throws() {
        assertThrows(ArithmeticException.class, () -> Years.of(MAX_MINUS_ONE).minus(Years.of(-2)));
    }

    @Test
    public void minus_amount_overflowsBelowMin_throws() {
        assertThrows(ArithmeticException.class, () -> Years.of(MIN_PLUS_ONE).minus(Years.of(2)));
    }

    @Test
    public void minus_nullAmount_throws() {
        //noinspection DataFlowIssue - testing nulls
        assertThrows(NullPointerException.class, () -> Years.of(MIN_PLUS_ONE).minus(null));
    }

    //-----------------------------------------------------------------------
    // minus(int)
    //-----------------------------------------------------------------------
    @Test
    public void minus_int_subtractsValues() {
        Years five = Years.of(5);
        assertEquals(Years.of(5), five.minus(0));
        assertEquals(Years.of(3), five.minus(2));
        assertEquals(Years.of(7), five.minus(-2));
        assertEquals(Years.of(Integer.MAX_VALUE), Years.of(MAX_MINUS_ONE).minus(-1));
        assertEquals(Years.of(Integer.MIN_VALUE), Years.of(MIN_PLUS_ONE).minus(1));
    }

    @Test
    public void minus_int_overflowsAboveMax_throws() {
        assertThrows(ArithmeticException.class, () -> Years.of(MAX_MINUS_ONE).minus(-2));
    }

    @Test
    public void minus_int_overflowsBelowMin_throws() {
        assertThrows(ArithmeticException.class, () -> Years.of(MIN_PLUS_ONE).minus(2));
    }

    //-----------------------------------------------------------------------
    // multipliedBy(int)
    //-----------------------------------------------------------------------
    @Test
    public void multipliedBy_scalesAmount() {
        Years five = Years.of(5);
        assertEquals(Years.of(0), five.multipliedBy(0));
        assertEquals(Years.of(5), five.multipliedBy(1));
        assertEquals(Years.of(10), five.multipliedBy(2));
        assertEquals(Years.of(15), five.multipliedBy(3));
        assertEquals(Years.of(-15), five.multipliedBy(-3));
    }

    @Test
    public void multipliedBy_negativeScalar_negatesAndScales() {
        Years five = Years.of(5);
        assertEquals(Years.of(-15), five.multipliedBy(-3));
    }

    @Test
    public void multipliedBy_overflowsAboveMax_throws() {
        assertThrows(ArithmeticException.class, () -> Years.of(Integer.MAX_VALUE / 2 + 1).multipliedBy(2));
    }

    @Test
    public void multipliedBy_overflowsBelowMin_throws() {
        assertThrows(ArithmeticException.class, () -> Years.of(Integer.MIN_VALUE / 2 - 1).multipliedBy(2));
    }

    //-----------------------------------------------------------------------
    // dividedBy(int)
    //-----------------------------------------------------------------------
    @Test
    public void dividedBy_usesIntegerDivision() {
        Years twelve = Years.of(12);
        assertEquals(Years.of(12), twelve.dividedBy(1));
        assertEquals(Years.of(6), twelve.dividedBy(2));
        assertEquals(Years.of(4), twelve.dividedBy(3));
        assertEquals(Years.of(3), twelve.dividedBy(4));
        assertEquals(Years.of(2), twelve.dividedBy(5));
        assertEquals(Years.of(2), twelve.dividedBy(6));
        assertEquals(Years.of(-4), twelve.dividedBy(-3));
    }

    @Test
    public void dividedBy_negativeDivisor_negatesResult() {
        Years twelve = Years.of(12);
        assertEquals(Years.of(-4), twelve.dividedBy(-3));
    }

    @Test
    public void dividedBy_zero_throws() {
        assertThrows(ArithmeticException.class, () -> Years.of(1).dividedBy(0));
    }

    //-----------------------------------------------------------------------
    // negated()
    //-----------------------------------------------------------------------
    @Test
    public void negated_flipsSign() {
        assertEquals(Years.of(0), Years.of(0).negated());
        assertEquals(Years.of(-12), Years.of(12).negated());
        assertEquals(Years.of(12), Years.of(-12).negated());
        assertEquals(Years.of(-Integer.MAX_VALUE), Years.of(Integer.MAX_VALUE).negated());
    }

    @Test
    public void negated_minValue_overflows_throws() {
        // MIN_VALUE has no positive counterpart in int.
        assertThrows(ArithmeticException.class, () -> Years.of(Integer.MIN_VALUE).negated());
    }

    //-----------------------------------------------------------------------
    // abs()
    //-----------------------------------------------------------------------
    @Test
    public void abs_returnsNonNegativeAmount() {
        assertEquals(Years.of(0), Years.of(0).abs());
        assertEquals(Years.of(12), Years.of(12).abs());
        assertEquals(Years.of(12), Years.of(-12).abs());
        assertEquals(Years.of(Integer.MAX_VALUE), Years.of(Integer.MAX_VALUE).abs());
        assertEquals(Years.of(Integer.MAX_VALUE), Years.of(-Integer.MAX_VALUE).abs());
    }

    @Test
    public void abs_minValue_overflows_throws() {
        assertThrows(ArithmeticException.class, () -> Years.of(Integer.MIN_VALUE).abs());
    }

    //-----------------------------------------------------------------------
    // addTo / subtractFrom
    //-----------------------------------------------------------------------
    @Test
    public void addTo_advancesTemporalByYears() {
        LocalDate start = LocalDate.of(2019, 1, 10);
        assertEquals(start, Years.of(0).addTo(start));
        assertEquals(LocalDate.of(2024, 1, 10), Years.of(5).addTo(start));
    }

    @Test
    public void subtractFrom_movesTemporalBackByYears() {
        LocalDate start = LocalDate.of(2019, 1, 10);
        assertEquals(start, Years.of(0).subtractFrom(start));
        assertEquals(LocalDate.of(2014, 1, 10), Years.of(5).subtractFrom(start));
    }

    //-----------------------------------------------------------------------
    // toPeriod()
    //-----------------------------------------------------------------------
    @Test
    public void toPeriod_matchesPeriodOfYears() {
        for (int years = -20; years < 20; years++) {
            assertEquals(Period.ofYears(years), Years.of(years).toPeriod());
        }
    }

    //-----------------------------------------------------------------------
    // compareTo(Years)
    //-----------------------------------------------------------------------
    @Test
    public void compareTo_ordersByAmount() {
        Years five = Years.of(5);
        Years six = Years.of(6);
        assertEquals(0, five.compareTo(five));
        assertEquals(-1, five.compareTo(six));
        assertEquals(1, six.compareTo(five));
    }

    @Test
    public void compareTo_null_throws() {
        Years five = Years.of(5);
        //noinspection DataFlowIssue - testing nulls
        assertThrows(NullPointerException.class, () -> five.compareTo(null));
    }

    //-----------------------------------------------------------------------
    // equals() and hashCode()
    //-----------------------------------------------------------------------
    @Test
    public void equals_and_hashCode_basedOnAmount() {
        new EqualsTester()
            .addEqualityGroup(Years.of(0), Years.of(0))
            .addEqualityGroup(Years.of(1), Years.of(1))
            .testEquals();
    }

    //-----------------------------------------------------------------------
    // toString()
    //-----------------------------------------------------------------------
    @Test
    public void toString_usesIsoPnYFormat() {
        assertEquals("P5Y", Years.of(5).toString());
        assertEquals("P-1Y", Years.of(-1).toString());
    }

    //-----------------------------------------------------------------------
    // helpers
    //-----------------------------------------------------------------------
    /**
     * Serializes the given object to a byte array using standard Java serialization.
     */
    private static byte[] serialize(Object value) throws Exception {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        try (ObjectOutputStream out = new ObjectOutputStream(bytes)) {
            out.writeObject(value);
        }
        return bytes.toByteArray();
    }

    /**
     * Reconstructs an object previously produced by {@link #serialize(Object)}.
     */
    private static Object deserialize(byte[] bytes) throws Exception {
        try (ObjectInputStream in = new ObjectInputStream(new ByteArrayInputStream(bytes))) {
            return in.readObject();
        }
    }

}
