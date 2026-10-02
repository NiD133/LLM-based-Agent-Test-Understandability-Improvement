package org.apache.commons.codec.digest;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;

import org.apache.commons.io.IOUtils;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

public class XXHash32Test_verifyChecksum {

    private static final int COPY_BUFFER_SIZE = 10240;

    // Reference checksums created with xxh32sum: https://cyan4973.github.io/xxHash/
    static Stream<Arguments> data() {
        return Stream.of(
            Arguments.of("org/apache/commons/codec/bla.tar",    "fbb5c8d1"),
            Arguments.of("org/apache/commons/codec/bla.tar.xz", "4106a208"),
            Arguments.of("org/apache/commons/codec/small.bin",  "f66c26f8")
        );
    }

    private static byte[] readAllBytes(final InputStream input) throws IOException {
        final ByteArrayOutputStream output = new ByteArrayOutputStream();
        IOUtils.copyLarge(input, output, new byte[COPY_BUFFER_SIZE]);
        return output.toByteArray();
    }

    @ParameterizedTest
    @MethodSource("data")
    void verifyChecksum(final String resourcePath, final String expectedChecksum) throws Exception {
        final URL url = XXHash32Test.class.getClassLoader().getResource(resourcePath);
        if (url == null) {
            throw new FileNotFoundException("couldn't find " + resourcePath);
        }
        final Path file = Paths.get(url.toURI());

        final XXHash32 hasher = new XXHash32();
        try (InputStream in = Files.newInputStream(file)) {
            final byte[] bytes = readAllBytes(in);
            hasher.update(bytes, 0, bytes.length);
        }

        assertEquals(expectedChecksum, Long.toHexString(hasher.getValue()), "checksum for " + file);
    }
}
