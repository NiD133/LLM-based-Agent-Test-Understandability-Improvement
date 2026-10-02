package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.format.DateTimeParseException;

import org.junit.jupiter.api.Test;

/**
 * Tests for {@link Years#parse(CharSequence)}.
 *
 * <p>The ISO-8601 format accepted is {@code PnY}, optionally preceded by a sign.
 * A sign before the "P" negates the whole value; a sign before the digit negates
 * the digit only — so {@code "-P-2Y"} resolves to {@code +2}.
 */
public class TestYears_test_parse_CharSequence {

    // -----------------------------------------------------------------------
    // Basic positive values
    // -----------------------------------------------------------------------

    @Test
    public void test_parse_zero() {
        assertEquals(Years.of(0), Years.parse("P0Y"));
    }

    @Test
    public void test_parse_one() {
        assertEquals(Years.of(1), Years.parse("P1Y"));
    }

    @Test
    public void test_parse_positiveSmall() {
        assertEquals(Years.of(2), Years.parse("P2Y"));
    }

    @Test
    public void test_parse_positiveLarge() {
        assertEquals(Years.of(123456789), Years.parse("P123456789Y"));
    }

    // -----------------------------------------------------------------------
    // Negative values — sign inside vs. sign outside the "P"
    // -----------------------------------------------------------------------

    /** {@code "P-2Y"} — minus sign on the digit itself → -2 years. */
    @Test
    public void test_parse_negativeDigit() {
        assertEquals(Years.of(-2), Years.parse("P-2Y"));
    }

    /** {@code "-P2Y"} — leading minus before "P" negates the whole value → -2 years. */
    @Test
    public void test_parse_negativePrefix() {
        assertEquals(Years.of(-2), Years.parse("-P2Y"));
    }

    /** {@code "-P-2Y"} — double negation: prefix negates the already-negative digit → +2 years. */
    @Test
    public void test_parse_doubleNegativeEqualsPositive() {
        assertEquals(Years.of(2), Years.parse("-P-2Y"));
    }
}
