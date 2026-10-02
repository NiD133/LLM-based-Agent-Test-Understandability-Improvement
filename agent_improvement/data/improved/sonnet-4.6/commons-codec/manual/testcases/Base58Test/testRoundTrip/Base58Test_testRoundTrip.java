package org.apache.commons.codec.binary;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

import java.nio.charset.StandardCharsets;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

public class Base58Test_testRoundTrip {

    @ParameterizedTest(name = "roundTrip[\"{0}\"]")
    @ValueSource(strings = {
        "",
        "a",
        "ab",
        "abc",
        "abcd",
        "abcde",
        "abcdef",
        "Hello World",
        "The quick brown fox jumps over the lazy dog",
        "1234567890",
        "!@#$%^&*()"
    })
    void testRoundTrip(final String input) {
        final byte[] originalBytes = input.getBytes(StandardCharsets.UTF_8);
        final Base58 codec = new Base58();
        final byte[] encoded = codec.encode(originalBytes);
        final byte[] decoded = codec.decode(encoded);
        assertArrayEquals(originalBytes, decoded, "Base58 round-trip failed for: " + input);
    }
}
