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

public class XXHash32Test_verifyIncrementalChecksum {

    // Reference checksums were computed externally with xxh32sum:
    // https://cyan4973.github.io/xxHash/
    static Stream<Arguments> data() {
        return Stream.of(
            Arguments.of("org/apache/commons/codec/bla.tar",    "fbb5c8d1"),
            Arguments.of("org/apache/commons/codec/bla.tar.xz", "4106a208"),
            Arguments.of("org/apache/commons/codec/small.bin",  "f66c26f8")
        );
    }

    private static Path resolveTestResource(final String resourcePath) throws Exception {
        final URL url = XXHash32Test.class.getClassLoader().getResource(resourcePath);
        if (url == null) {
            throw new FileNotFoundException("couldn't find " + resourcePath);
        }
        return Paths.get(url.toURI());
    }

    private static byte[] readAllBytes(final InputStream input) throws IOException {
        final ByteArrayOutputStream output = new ByteArrayOutputStream();
        IOUtils.copyLarge(input, output, new byte[10240]);
        return output.toByteArray();
    }

    @ParameterizedTest
    @MethodSource("data")
    void verifyIncrementalChecksum(final String resourcePath, final String expectedChecksum) throws Exception {
        final Path file = resolveTestResource(resourcePath);
        final XXHash32 hasher = new XXHash32();

        try (InputStream in = Files.newInputStream(file)) {
            final byte[] fileBytes = readAllBytes(in);

            // Verify that reset() discards any previously accumulated state
            hasher.update(fileBytes[0]);
            hasher.reset();

            // Feed the file content in three separate chunks to exercise incremental hashing:
            // first byte alone, the middle portion, and the final byte alone
            final int lastIndex = fileBytes.length - 1;
            hasher.update(fileBytes[0]);
            hasher.update(fileBytes, 1, lastIndex - 1);
            hasher.update(fileBytes, lastIndex, 1);

            // Confirm that an update with a negative length is silently ignored
            hasher.update(fileBytes, 0, -1);
        }

        final String actualChecksum = Long.toHexString(hasher.getValue());
        assertEquals(expectedChecksum, actualChecksum, "checksum for " + file);
    }
}
