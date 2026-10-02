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

/**
 * Verifies that {@link XXHash32} produces the correct checksum when the input is
 * fed incrementally (byte-by-byte and in chunks) rather than all at once.
 *
 * <p>The expected checksums were generated with the reference tool {@code xxh32sum}
 * (see <a href="https://cyan4973.github.io/xxHash/">xxHash</a>).</p>
 */
public class XXHash32Test_verifyIncrementalChecksum {

    /** Buffer size used when reading a test resource fully into memory. */
    private static final int READ_BUFFER_SIZE = 10240;

    /**
     * Supplies each test case as a (resource path, expected checksum) pair.
     */
    static Stream<Arguments> data() {
        return Stream.of(
            Arguments.of("org/apache/commons/codec/bla.tar", "fbb5c8d1"),
            Arguments.of("org/apache/commons/codec/bla.tar.xz", "4106a208"),
            Arguments.of("org/apache/commons/codec/small.bin", "f66c26f8"));
    }

    /**
     * Resolves a classpath resource to a filesystem {@link Path}.
     *
     * @param resourcePath the classpath-relative location of the resource.
     * @return the resolved path.
     * @throws Exception if the resource cannot be found or its URL cannot be converted.
     */
    private static Path resolveResource(final String resourcePath) throws Exception {
        final URL url = XXHash32Test.class.getClassLoader().getResource(resourcePath);
        if (url == null) {
            throw new FileNotFoundException("couldn't find " + resourcePath);
        }
        return Paths.get(url.toURI());
    }

    /**
     * Reads the entire stream into a byte array.
     */
    private static byte[] readAllBytes(final InputStream input) throws IOException {
        final ByteArrayOutputStream output = new ByteArrayOutputStream();
        copy(input, output, READ_BUFFER_SIZE);
        return output.toByteArray();
    }

    private static long copy(final InputStream input, final OutputStream output, final int bufferSize) throws IOException {
        return IOUtils.copyLarge(input, output, new byte[bufferSize]);
    }

    @ParameterizedTest
    @MethodSource("data")
    void verifyIncrementalChecksum(final String resourcePath, final String expectedChecksum) throws Exception {
        final Path file = resolveResource(resourcePath);

        final byte[] bytes;
        try (InputStream in = Files.newInputStream(file)) {
            bytes = readAllBytes(in);
        }

        final XXHash32 hasher = new XXHash32();

        // Feed a byte then reset, so the reset-before-use path is exercised.
        hasher.update(bytes[0]);
        hasher.reset();

        // Feed the same data again, this time split into chunks:
        // first byte, then the middle, then the last byte.
        hasher.update(bytes[0]);
        hasher.update(bytes, 1, bytes.length - 2);
        hasher.update(bytes, bytes.length - 1, 1);

        // A negative length must be ignored and leave the checksum unchanged.
        hasher.update(bytes, 0, -1);

        assertEquals(expectedChecksum, Long.toHexString(hasher.getValue()), "checksum for " + file);
    }
}
