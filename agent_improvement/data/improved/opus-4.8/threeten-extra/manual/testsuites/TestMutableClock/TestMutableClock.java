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
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.time.DayOfWeek;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Period;
import java.time.Year;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoField;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalAdjusters;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.jspecify.annotations.Nullable;
import org.junit.jupiter.api.Test;

import com.google.common.testing.EqualsTester;

/**
 * Tests for {@link MutableClock}.
 * <p>
 * The tests are grouped by the area of behaviour they exercise:
 * <ul>
 * <li>Factory methods ({@code of}, {@code epochUTC})</li>
 * <li>Mutation operations ({@code setInstant}, {@code add}, {@code set})</li>
 * <li>Time-zone handling ({@code getZone}, {@code withZone}, {@code instant})</li>
 * <li>Equality and hashing (including the "shared updates" contract)</li>
 * <li>String representation</li>
 * <li>Serialization</li>
 * <li>Thread-safety</li>
 * </ul>
 */
public class TestMutableClock {

    // ZonedDateTime.ofInstant(EPOCH, UTC), used to compute the expected instant
    // for the various update operations against a known reference point.
    private static ZonedDateTime epochAtUtc() {
        return ZonedDateTime.ofInstant(Instant.EPOCH, ZoneOffset.UTC);
    }

    // -----------------------------------------------------------------------
    // Factory methods
    // -----------------------------------------------------------------------

    @Test
    public void test_of() {
        // The created clock reports back the instant it was given...
        assertEquals(
                Instant.EPOCH,
                MutableClock.of(Instant.EPOCH, ZoneOffset.UTC).instant());
        assertEquals(
                Instant.MIN,
                MutableClock.of(Instant.MIN, ZoneOffset.UTC).instant());
        assertEquals(
                Instant.MAX,
                MutableClock.of(Instant.MAX, ZoneOffset.UTC).instant());
        // ...and the time-zone it was given.
        assertEquals(
                ZoneOffset.UTC,
                MutableClock.of(Instant.EPOCH, ZoneOffset.UTC).getZone());
        assertEquals(
                ZoneOffset.MIN,
                MutableClock.of(Instant.EPOCH, ZoneOffset.MIN).getZone());
        assertEquals(
                ZoneOffset.MAX,
                MutableClock.of(Instant.EPOCH, ZoneOffset.MAX).getZone());
    }

    @Test
    public void test_of_nullInstant() {
        //noinspection DataFlowIssue - testing nulls
        assertThrows(NullPointerException.class, () -> MutableClock.of(null, ZoneOffset.UTC));
    }

    @Test
    public void test_of_nullZone() {
        //noinspection DataFlowIssue - testing nulls
        assertThrows(NullPointerException.class, () -> MutableClock.of(Instant.EPOCH, null));
    }

    @Test
    public void test_epochUTC() {
        // epochUTC() is shorthand for of(EPOCH, UTC).
        assertEquals(Instant.EPOCH, MutableClock.epochUTC().instant());
        assertEquals(ZoneOffset.UTC, MutableClock.epochUTC().getZone());
    }

    // -----------------------------------------------------------------------
    // Mutation: setInstant
    // -----------------------------------------------------------------------

    @Test
    public void test_setInstant() {
        MutableClock clock = MutableClock.epochUTC();
        assertEquals(Instant.EPOCH, clock.instant());

        // Each setInstant fully replaces the current instant.
        clock.setInstant(Instant.MIN);
        assertEquals(Instant.MIN, clock.instant());

        clock.setInstant(Instant.MAX);
        assertEquals(Instant.MAX, clock.instant());

        clock.setInstant(Instant.EPOCH.plusSeconds(10));
        assertEquals(Instant.EPOCH.plusSeconds(10), clock.instant());
    }

    @Test
    public void test_setInstant_null() {
        //noinspection DataFlowIssue - testing nulls
        assertThrows(NullPointerException.class, () -> MutableClock.epochUTC().setInstant(null));
    }

    // -----------------------------------------------------------------------
    // Mutation: add
    // -----------------------------------------------------------------------

    @Test
    public void test_add_amountOnly() {
        MutableClock clock = MutableClock.epochUTC();

        // A mix of Duration and Period amounts, including negatives and zero.
        clock.add(Duration.ofNanos(3));
        clock.add(Period.ofMonths(2));
        clock.add(Duration.ofSeconds(-5));
        clock.add(Period.ofWeeks(-7));
        clock.add(Duration.ZERO);
        clock.add(Period.ZERO);

        // The same operations applied to a ZonedDateTime yield the expected instant.
        assertEquals(
                epochAtUtc()
                        .plusNanos(3)
                        .plusMonths(2)
                        .minusSeconds(5)
                        .minusWeeks(7)
                        .toInstant(),
                clock.instant());
    }

    @Test
    public void test_add_amountOnly_null() {
        //noinspection DataFlowIssue - testing nulls
        assertThrows(NullPointerException.class, () -> MutableClock.epochUTC().add(null));
    }

    @Test
    public void test_add_amountAndUnit() {
        MutableClock clock = MutableClock.epochUTC();

        // The (amount, unit) overload mirrors the amount-only additions above,
        // again including negatives and zero amounts.
        clock.add(3, ChronoUnit.NANOS);
        clock.add(2, ChronoUnit.MONTHS);
        clock.add(-5, ChronoUnit.SECONDS);
        clock.add(-7, ChronoUnit.WEEKS);
        clock.add(0, ChronoUnit.MILLIS);
        clock.add(0, ChronoUnit.YEARS);

        assertEquals(
                epochAtUtc()
                        .plusNanos(3)
                        .plusMonths(2)
                        .minusSeconds(5)
                        .minusWeeks(7)
                        .toInstant(),
                clock.instant());
    }

    @Test
    public void test_add_amountAndUnit_nullUnit() {
        //noinspection DataFlowIssue - testing nulls
        assertThrows(NullPointerException.class, () -> MutableClock.epochUTC().add(0, null));
    }

    // -----------------------------------------------------------------------
    // Mutation: set
    // -----------------------------------------------------------------------

    @Test
    public void test_set_adjuster() {
        MutableClock clock = MutableClock.epochUTC();

        // Apply a sequence of TemporalAdjusters.
        clock.set(LocalDate.of(0, 1, 2));
        clock.set(LocalTime.of(3, 4, 5));
        clock.set(TemporalAdjusters.firstDayOfNextMonth());
        clock.set(TemporalAdjusters.next(DayOfWeek.WEDNESDAY));
        assertEquals(
                LocalDateTime.of(0, 1, 2, 3, 4, 5)
                        .with(TemporalAdjusters.firstDayOfNextMonth())
                        .with(TemporalAdjusters.next(DayOfWeek.WEDNESDAY))
                        .atZone(ZoneOffset.UTC)
                        .toInstant(),
                clock.instant());

        // An Instant is itself a TemporalAdjuster, so set overwrites the clock.
        clock.set(Instant.EPOCH);
        assertEquals(Instant.EPOCH, clock.instant());
    }

    @Test
    public void test_set_adjuster_null() {
        //noinspection DataFlowIssue - testing nulls
        assertThrows(NullPointerException.class, () -> MutableClock.epochUTC().set(null));
    }

    @Test
    public void test_set_fieldAndValue() {
        MutableClock clock = MutableClock.epochUTC();

        // Set individual date-time fields to build up 0000-01-02T03:04:05.
        clock.set(ChronoField.YEAR, 0);
        clock.set(ChronoField.MONTH_OF_YEAR, 1);
        clock.set(ChronoField.DAY_OF_MONTH, 2);
        clock.set(ChronoField.HOUR_OF_DAY, 3);
        clock.set(ChronoField.MINUTE_OF_HOUR, 4);
        clock.set(ChronoField.SECOND_OF_MINUTE, 5);
        assertEquals(
                LocalDateTime.of(0, 1, 2, 3, 4, 5)
                        .atZone(ZoneOffset.UTC)
                        .toInstant(),
                clock.instant());
    }

    @Test
    public void test_set_fieldAndValue_nullField() {
        //noinspection DataFlowIssue - testing nulls
        assertThrows(NullPointerException.class, () -> MutableClock.epochUTC().set(null, 0));
    }

    // -----------------------------------------------------------------------
    // Time-zone handling
    // -----------------------------------------------------------------------

    @Test
    public void test_getZone() {
        MutableClock clock = MutableClock.epochUTC();
        MutableClock withOtherZone = clock.withZone(ZoneOffset.MIN);
        MutableClock ofOtherZone = MutableClock.of(Instant.EPOCH, ZoneOffset.MAX);
        assertEquals(ZoneOffset.UTC, clock.getZone());
        assertEquals(ZoneOffset.MIN, withOtherZone.getZone());
        assertEquals(ZoneOffset.MAX, ofOtherZone.getZone());
    }

    @Test
    public void test_withZone() {
        MutableClock clock = MutableClock.epochUTC();
        MutableClock withOtherZone = clock.withZone(ZoneOffset.MIN);
        MutableClock withSameZone = withOtherZone.withZone(ZoneOffset.UTC);

        // withZone produces views that share updates with the original clock,
        // so updating the original is visible through every view.
        clock.setInstant(Instant.MIN);
        assertEquals(Instant.MIN, withOtherZone.instant());
        assertEquals(Instant.MIN, withSameZone.instant());

        // Each view keeps its own time-zone.
        assertEquals(ZoneOffset.MIN, withOtherZone.getZone());
        assertEquals(ZoneOffset.UTC, withSameZone.getZone());

        // Views are equal only when they share updates AND have the same zone.
        assertNotEquals(clock, withOtherZone);
        assertEquals(clock, withSameZone);
    }

    @Test
    public void test_withZone_null() {
        //noinspection DataFlowIssue - testing nulls
        assertThrows(NullPointerException.class, () -> MutableClock.epochUTC().withZone(null));
    }

    @Test
    public void test_instant() {
        MutableClock clock = MutableClock.epochUTC();
        MutableClock withOtherZone = clock.withZone(ZoneOffset.MIN);
        assertEquals(Instant.EPOCH, clock.instant());

        // add and setInstant both update the instant returned by instant().
        clock.add(Duration.ofSeconds(5));
        assertEquals(Instant.EPOCH.plusSeconds(5), clock.instant());

        clock.setInstant(Instant.MIN);
        assertEquals(Instant.MIN, clock.instant());

        // The shared-update view sees the same instant as the original clock.
        assertEquals(Instant.MIN, withOtherZone.instant());
    }

    // -----------------------------------------------------------------------
    // Equality and hashing
    // -----------------------------------------------------------------------

    @Test
    public void test_equals() {
        MutableClock clock = MutableClock.epochUTC();
        MutableClock withOtherZone = clock.withZone(ZoneOffset.MIN);
        MutableClock withSameZone = withOtherZone.withZone(ZoneOffset.UTC);
        MutableClock independent = MutableClock.epochUTC();

        // Equal: clocks that share updates and have the same zone.
        // Unequal: a differently-zoned view, and a clock created independently.
        new EqualsTester()
            .addEqualityGroup(clock, clock, withSameZone)
            .addEqualityGroup(withOtherZone)
            .addEqualityGroup(independent)
            .testEquals();
    }

    @Test
    public void test_hashCode_isConstant() {
        MutableClock clock = MutableClock.epochUTC();
        int hash = clock.hashCode();

        // The hash code is fixed per instance and does not change as the
        // clock's instant is mutated.
        clock.add(Period.ofMonths(1));
        assertEquals(hash, clock.hashCode());
        clock.add(1, ChronoUnit.DAYS);
        assertEquals(hash, clock.hashCode());
        clock.set(Year.of(2000));
        assertEquals(hash, clock.hashCode());
        clock.set(ChronoField.INSTANT_SECONDS, -1);
        assertEquals(hash, clock.hashCode());
    }

    @Test
    public void test_hashCode_sameWhenSharedUpdates() {
        MutableClock clock = MutableClock.epochUTC();
        MutableClock withOtherZone = clock.withZone(ZoneOffset.MIN);
        MutableClock withSameZone = withOtherZone.withZone(ZoneOffset.UTC);

        // Equal clocks (shared updates, same zone) have equal hash codes.
        assertEquals(clock.hashCode(), withSameZone.hashCode());
    }

    // -----------------------------------------------------------------------
    // String representation
    // -----------------------------------------------------------------------

    @Test
    public void test_toString() {
        MutableClock clock = MutableClock.epochUTC();
        assertEquals(
                "MutableClock[1970-01-01T00:00:00Z,Z]",
                clock.toString());

        // toString reflects the current instant...
        clock.add(Period.ofYears(30));
        assertEquals(
                "MutableClock[2000-01-01T00:00:00Z,Z]",
                clock.toString());

        // ...and the clock's time-zone.
        MutableClock withOtherZone = clock.withZone(ZoneOffset.MIN);
        assertEquals(
                "MutableClock[2000-01-01T00:00:00Z,-18:00]",
                withOtherZone.toString());
    }

    // -----------------------------------------------------------------------
    // Serialization
    // -----------------------------------------------------------------------

    @Test
    public void test_isSerializable() {
        assertTrue(Serializable.class.isAssignableFrom(MutableClock.class));
    }

    @Test
    public void test_serialization() throws Exception {
        MutableClock test = MutableClock.epochUTC();

        // Round-trip the clock through Java serialization.
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try (ObjectOutputStream oos = new ObjectOutputStream(baos)) {
            oos.writeObject(test);
        }
        try (ObjectInputStream ois = new ObjectInputStream(new ByteArrayInputStream(baos.toByteArray()))) {
            MutableClock ser = (MutableClock) ois.readObject();

            // The deserialized clock has the same instant and zone...
            assertEquals(test.instant(), ser.instant());
            assertEquals(test.getZone(), ser.getZone());

            // ...but does NOT share updates with the original, so it is not
            // equal and updates to one do not affect the other.
            assertNotEquals(ser, test);
            test.add(Duration.ofSeconds(1));
            assertNotEquals(ser.instant(), test.instant());
        }
    }

    // -----------------------------------------------------------------------
    // Thread-safety
    // -----------------------------------------------------------------------

    @Test
    public void test_updatesAreAtomic() throws Exception {
        MutableClock clock = MutableClock.epochUTC();
        Duration increment = Duration.ofSeconds(1);

        // A single task that advances the clock by one increment.
        Callable<@Nullable Void> applyOneUpdate = () -> {
            clock.add(increment);
            return null;
        };

        // Run many such updates concurrently across a pool of threads.
        int updateCount = 10000;
        List<Callable<Void>> tasks = Collections.nCopies(updateCount, applyOneUpdate);
        int threads = Runtime.getRuntime().availableProcessors() * 4;
        ExecutorService service = Executors.newFixedThreadPool(threads);
        try {
            service.invokeAll(tasks);
            service.shutdown();
            service.awaitTermination(1, TimeUnit.MINUTES);
        } finally {
            if (!service.isTerminated()) {
                service.shutdownNow();
            }
        }

        // If updates are atomic, none are lost: the total advance equals
        // increment * updateCount.
        assertEquals(
                Instant.EPOCH.plus(increment.multipliedBy(updateCount)),
                clock.instant());
    }
}
