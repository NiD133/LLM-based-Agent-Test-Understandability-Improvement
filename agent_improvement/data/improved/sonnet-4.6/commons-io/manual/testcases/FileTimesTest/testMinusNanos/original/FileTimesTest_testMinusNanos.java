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

public class FileTimesTest_testMinusNanos {

    @Test
    void testMinusNanos() {
        final int millis = 2;
        assertEquals(Instant.EPOCH.minusNanos(millis), FileTimes.minusNanos(FileTimes.EPOCH, millis).toInstant());
        assertEquals(Instant.EPOCH, FileTimes.minusNanos(FileTimes.EPOCH, 0).toInstant());
    }
}
