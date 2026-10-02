package org.threeten.extra.chrono;

import static java.time.temporal.ChronoUnit.CENTURIES;
import static java.time.temporal.ChronoUnit.DAYS;
import static java.time.temporal.ChronoUnit.DECADES;
import static java.time.temporal.ChronoUnit.MILLENNIA;
import static java.time.temporal.ChronoUnit.MONTHS;
import static java.time.temporal.ChronoUnit.WEEKS;
import static java.time.temporal.ChronoUnit.YEARS;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.temporal.TemporalUnit;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

public class TestDiscordianChronology_test_plus_TemporalUnit {

    /**
     * Test cases for DiscordianDate.plus(amount, unit).
     *
     * Each row: { year, month, dom, amount, unit, expectedYear, expectedMonth, expectedDom }
     *
     * The base date used throughout is 2014-5-26 (Discordian year 2014, month 5 "Discord", day 26).
     * A Discordian week has 5 days; a month has 73 days; a year has 5 months (365/366 days).
     */
    public static Object[][] data_plus() {
        return new Object[][] {
            // --- DAYS ---
            { 2014, 5, 26,  0, DAYS, 2014, 5, 26 },  // zero: no change
            { 2014, 5, 26,  8, DAYS, 2014, 5, 34 },  // forward within same month
            { 2014, 5, 26, -3, DAYS, 2014, 5, 23 },  // backward within same month

            // --- WEEKS (1 Discordian week = 5 days) ---
            { 2014, 5, 26,  0, WEEKS, 2014, 5, 26 },  // zero: no change
            { 2014, 5, 26,  3, WEEKS, 2014, 5, 41 },  // +15 days forward
            { 2014, 5, 26, -5, WEEKS, 2014, 5,  1 },  // -25 days backward to start of month

            // --- MONTHS ---
            { 2014, 5, 26,  0, MONTHS, 2014, 5, 26 },  // zero: no change
            { 2014, 5, 26,  3, MONTHS, 2015, 3, 26 },  // wraps into next year
            { 2014, 5, 26, -5, MONTHS, 2013, 5, 26 },  // wraps back to previous year

            // --- YEARS ---
            { 2014, 5, 26,  0, YEARS, 2014, 5, 26 },  // zero: no change
            { 2014, 5, 26,  3, YEARS, 2017, 5, 26 },  // forward 3 years
            { 2014, 5, 26, -5, YEARS, 2009, 5, 26 },  // backward 5 years

            // --- DECADES (1 decade = 10 years) ---
            { 2014, 5, 26,  0, DECADES, 2014, 5, 26 },  // zero: no change
            { 2014, 5, 26,  3, DECADES, 2044, 5, 26 },  // +30 years
            { 2014, 5, 26, -5, DECADES, 1964, 5, 26 },  // -50 years

            // --- CENTURIES (1 century = 100 years) ---
            { 2014, 5, 26,  0, CENTURIES, 2014, 5, 26 },  // zero: no change
            { 2014, 5, 26,  3, CENTURIES, 2314, 5, 26 },  // +300 years
            { 2014, 5, 26, -5, CENTURIES, 1514, 5, 26 },  // -500 years

            // --- MILLENNIA (1 millennium = 1000 years) ---
            { 2014, 5, 26,    0, MILLENNIA,        2014, 5, 26 },  // zero: no change
            { 2014, 5, 26,    3, MILLENNIA,         5014, 5, 26 },  // +3000 years
            { 2014, 5, 26,   -1, MILLENNIA, 2014 - 1000, 5, 26 },  // -1000 years
        };
    }

    @ParameterizedTest
    @MethodSource("data_plus")
    public void test_plus_TemporalUnit(
            int year, int month, int dom,
            long amount, TemporalUnit unit,
            int expectedYear, int expectedMonth, int expectedDom) {
        assertEquals(
            DiscordianDate.of(expectedYear, expectedMonth, expectedDom),
            DiscordianDate.of(year, month, dom).plus(amount, unit));
    }
}
