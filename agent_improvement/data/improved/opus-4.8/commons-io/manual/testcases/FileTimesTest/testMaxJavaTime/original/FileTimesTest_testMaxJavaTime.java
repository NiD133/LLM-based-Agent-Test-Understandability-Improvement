package org.apache.commons.io.file.attribute;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.nio.file.attribute.FileTime;
import java.time.Instant;
import java.util.Date;
import java.util.concurrent.TimeUnit;
import java.util.stream.Stream;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

public class FileTimesTest_testMaxJavaTime {

    @Test
    void testMaxJavaTime() {
        final long javaTime = Long.MAX_VALUE;
        final Instant instant = Instant.ofEpochMilli(javaTime);
        // sanity check
        assertEquals(javaTime, instant.toEpochMilli());
        final long ntfsTime = FileTimes.toNtfsTime(javaTime);
        final Instant instant2 = FileTimes.ntfsTimeToInstant(ntfsTime);
        if (ntfsTime == Long.MAX_VALUE) {
            // toNtfsTime returns max long instead of overflowing
        } else {
            assertEquals(javaTime, instant2.toEpochMilli());
        }
    }
}
