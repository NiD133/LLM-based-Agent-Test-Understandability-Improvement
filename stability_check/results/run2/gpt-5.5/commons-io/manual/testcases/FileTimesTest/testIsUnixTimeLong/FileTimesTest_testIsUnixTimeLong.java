package org.apache.commons.io.file.attribute;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.Instant;
import java.util.stream.Stream;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

public class FileTimesTest_testIsUnixTimeLong {

    public static Stream<Arguments> isUnixFileTimeProvider() {
        return Stream.of(
                Arguments.of("2022-12-27T12:45:22Z", true),
                Arguments.of("2038-01-19T03:14:07Z", true),
                Arguments.of("1901-12-13T23:14:08Z", true),
                Arguments.of("1901-12-13T03:14:08Z", false),
                Arguments.of("2038-01-19T03:14:08Z", false),
                Arguments.of("2099-06-30T12:31:42Z", false));
    }

    @ParameterizedTest
    @MethodSource("isUnixFileTimeProvider")
    void testIsUnixTimeLong(final String instantText, final boolean expectedUnixTime) {
        final long epochSecond = Instant.parse(instantText).getEpochSecond();

        assertEquals(expectedUnixTime, FileTimes.isUnixTime(epochSecond));
    }
}
