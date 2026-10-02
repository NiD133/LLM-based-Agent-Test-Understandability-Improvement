package org.apache.commons.codec.digest;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
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

    private static long copy(final InputStream input, final OutputStream output, final int bufferSize) throws IOException {
        return IOUtils.copyLarge(input, output, new byte[bufferSize]);
    }

    static Stream<Arguments> data() {
        // Reference checksums created with xxh32sum:
        // https://cyan4973.github.io/xxHash/
        return Stream.of(
            Arguments.of("org/apache/commons/codec/bla.tar", "fbb5c8d1"),
            Arguments.of("org/apache/commons/codec/bla.tar.xz", "4106a208"),
            Arguments.of("org/apache/commons/codec/small.bin", "f66c26f8"));
    }

    private static byte[] toByteArray(final InputStream input) throws IOException {
        final ByteArrayOutputStream output = new ByteArrayOutputStream();
        copy(input, output, 10240);
        return output.toByteArray();
    }

    private Path file;

    private String expectedChecksum;

    private void initData(final String path, final String c) throws Exception {
        final URL url = XXHash32Test.class.getClassLoader().getResource(path);
        if (url == null) {
            throw new FileNotFoundException("couldn't find " + path);
        }
        file = Paths.get(url.toURI());
        expectedChecksum = c;
    }

    @ParameterizedTest
    @MethodSource("data")
    void verifyIncrementalChecksum(final String path, final String c) throws Exception {
        initData(path, c);

        final XXHash32 hasher = new XXHash32();
        try (InputStream in = Files.newInputStream(file)) {
            final byte[] bytes = toByteArray(in);

            // Hit the case where the hash should be reset.
            hasher.update(bytes[0]);
            hasher.reset();

            // Pass in chunks.
            hasher.update(bytes[0]);
            hasher.update(bytes, 1, bytes.length - 2);
            hasher.update(bytes, bytes.length - 1, 1);

            // Check the hash ignores negative length.
            hasher.update(bytes, 0, -1);
        }

        assertEquals(expectedChecksum, Long.toHexString(hasher.getValue()), "checksum for " + file);
    }
}
