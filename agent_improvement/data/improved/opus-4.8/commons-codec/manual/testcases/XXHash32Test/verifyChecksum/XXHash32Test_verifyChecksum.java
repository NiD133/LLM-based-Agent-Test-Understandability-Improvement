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

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

/**
 * Verifies that {@link XXHash32} reproduces reference checksums for a set of
 * sample files.
 *
 * <p>The expected hex checksums were generated with the {@code xxh32sum} tool
 * (see <a href="https://cyan4973.github.io/xxHash/">xxHash</a>).</p>
 */
public class XXHash32Test_verifyChecksum {

    /** Buffer size used when reading a sample file fully into memory. */
    private static final int READ_BUFFER_SIZE = 10240;

    /**
     * Supplies each test case as a (classpath resource, expected hex checksum) pair.
     */
    static Stream<Arguments> data() {
        return Stream.of(
            Arguments.of("org/apache/commons/codec/bla.tar",    "fbb5c8d1"),
            Arguments.of("org/apache/commons/codec/bla.tar.xz", "4106a208"),
            Arguments.of("org/apache/commons/codec/small.bin",  "f66c26f8"));
    }

    @ParameterizedTest
    @MethodSource("data")
    void verifyChecksum(final String resourcePath, final String expectedChecksum) throws Exception {
        final Path file = resolveResource(resourcePath);

        final XXHash32 hasher = new XXHash32();
        final byte[] content = readAllBytes(file);
        hasher.update(content, 0, content.length);

        final String actualChecksum = Long.toHexString(hasher.getValue());
        assertEquals(expectedChecksum, actualChecksum, "checksum for " + file);
    }

    /**
     * Locates a file on the test classpath and returns it as a {@link Path}.
     *
     * @throws FileNotFoundException if the resource is not on the classpath.
     */
    private static Path resolveResource(final String resourcePath) throws Exception {
        final URL url = XXHash32Test_verifyChecksum.class.getClassLoader().getResource(resourcePath);
        if (url == null) {
            throw new FileNotFoundException("couldn't find " + resourcePath);
        }
        return Paths.get(url.toURI());
    }

    /**
     * Reads the entire file into a byte array.
     */
    private static byte[] readAllBytes(final Path file) throws IOException {
        try (InputStream in = Files.newInputStream(file)) {
            final ByteArrayOutputStream output = new ByteArrayOutputStream();
            final byte[] buffer = new byte[READ_BUFFER_SIZE];
            int read;
            while ((read = in.read(buffer)) != -1) {
                output.write(buffer, 0, read);
            }
            return output.toByteArray();
        }
    }
}
