package org.apache.commons.io.file.attribute;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.nio.file.attribute.FileTime;
import java.time.Instant;
import java.util.stream.Stream;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

public class FileTimesTest_testIsUnixTime {

    private static final String LAST_UNIX_TIME_SECOND = "2038-01-19T03:14:07Z";
    private static final String FIRST_SECOND_AFTER_UNIX_TIME_RANGE = "2038-01-19T03:14:08Z";
    private static final String FIRST_UNIX_TIME_SECOND = "1901-12-13T23:14:08Z";
    private static final String SECOND_BEFORE_UNIX_TIME_RANGE = "1901-12-13T03:14:08Z";

    public static Stream<Arguments> isUnixFileTimeProvider() {
        return Stream.of(
                Arguments.of("2022-12-27T12:45:22Z", true),
                Arguments.of(LAST_UNIX_TIME_SECOND, true),
                Arguments.of(FIRST_UNIX_TIME_SECOND, true),
                Arguments.of(SECOND_BEFORE_UNIX_TIME_RANGE, false),
                Arguments.of(FIRST_SECOND_AFTER_UNIX_TIME_RANGE, false),
                Arguments.of("2099-06-30T12:31:42Z", false));
    }

    @ParameterizedTest
    @MethodSource("isUnixFileTimeProvider")
    void testIsUnixTime(final String instant, final boolean expected) {
        assertEquals(expected, FileTimes.isUnixTime(FileTime.from(Instant.parse(instant))));
    }
}
