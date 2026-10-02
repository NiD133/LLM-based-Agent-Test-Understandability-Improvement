package org.apache.commons.io.file.attribute;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.nio.file.attribute.FileTime;
import java.time.Instant;
import java.util.stream.Stream;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

public class FileTimesTest_testToUnixTime {

    public static Stream<Arguments> unixTimestampBoundaryCases() {
        return Stream.of(
                Arguments.of("2022-12-27T12:45:22Z", true),
                Arguments.of("2038-01-19T03:14:07Z", true),
                Arguments.of("1901-12-13T23:14:08Z", true),
                Arguments.of("1901-12-13T03:14:08Z", false),
                Arguments.of("2038-01-19T03:14:08Z", false),
                Arguments.of("2099-06-30T12:31:42Z", false));
    }

    @ParameterizedTest
    @MethodSource("unixTimestampBoundaryCases")
    void testToUnixTime(final String instantText, final boolean expectedUnixTime) {
        final Instant instant = Instant.parse(instantText);
        final FileTime fileTime = FileTime.from(instant);
        final long unixTime = FileTimes.toUnixTime(fileTime);

        assertEquals(expectedUnixTime, FileTimes.isUnixTime(unixTime));
    }
}
