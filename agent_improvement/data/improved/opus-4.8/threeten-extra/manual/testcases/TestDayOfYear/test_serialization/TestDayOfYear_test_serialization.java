package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.time.LocalDate;
import java.time.ZoneId;

import org.junit.jupiter.api.Test;
import org.junitpioneer.jupiter.RetryingTest;

/**
 * Tests {@link DayOfYear#now()}, {@link DayOfYear#now(ZoneId)} and the
 * serialization round-trip of {@link DayOfYear}.
 */
public class TestDayOfYear_test_serialization {

    //-----------------------------------------------------------------------
    // now()
    //-----------------------------------------------------------------------
    @RetryingTest(100)
    public void now_matchesCurrentDayOfYearInDefaultZone() {
        int expectedDayOfYear = LocalDate.now().getDayOfYear();

        assertEquals(expectedDayOfYear, DayOfYear.now().getValue());
    }

    //-----------------------------------------------------------------------
    // now(ZoneId)
    //-----------------------------------------------------------------------
    @RetryingTest(100)
    public void now_withZone_matchesCurrentDayOfYearInThatZone() {
        ZoneId tokyo = ZoneId.of("Asia/Tokyo");
        int expectedDayOfYear = LocalDate.now(tokyo).getDayOfYear();

        assertEquals(expectedDayOfYear, DayOfYear.now(tokyo).getValue());
    }

    //-----------------------------------------------------------------------
    // serialization
    //-----------------------------------------------------------------------
    @Test
    public void serialization_returnsSameCachedInstance() throws IOException, ClassNotFoundException {
        DayOfYear original = DayOfYear.of(1);

        // Write the instance to a byte stream.
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        try (ObjectOutputStream out = new ObjectOutputStream(bytes)) {
            out.writeObject(original);
        }

        // Read it back; readResolve() should yield the same cached singleton.
        try (ObjectInputStream in = new ObjectInputStream(new ByteArrayInputStream(bytes.toByteArray()))) {
            assertSame(original, in.readObject());
        }
    }
}
