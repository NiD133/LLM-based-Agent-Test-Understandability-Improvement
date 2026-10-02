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

public class TestDayOfYear_test_serialization {

    @RetryingTest(100)
    public void test_now() {
        assertEquals(LocalDate.now().getDayOfYear(), DayOfYear.now().getValue());
    }

    @RetryingTest(100)
    public void test_now_ZoneId() {
        ZoneId zone = ZoneId.of("Asia/Tokyo");
        assertEquals(LocalDate.now(zone).getDayOfYear(), DayOfYear.now(zone).getValue());
    }

    @Test
    public void test_serialization() throws IOException, ClassNotFoundException {
        DayOfYear originalDayOfYear = DayOfYear.of(1);
        ByteArrayOutputStream serializedBytes = new ByteArrayOutputStream();

        try (ObjectOutputStream objectOutput = new ObjectOutputStream(serializedBytes)) {
            objectOutput.writeObject(originalDayOfYear);
        }

        try (ObjectInputStream objectInput = new ObjectInputStream(
                new ByteArrayInputStream(serializedBytes.toByteArray()))) {
            assertSame(originalDayOfYear, objectInput.readObject());
        }
    }
}
