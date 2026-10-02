/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
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
 * Tests {@link XXHash32} by hashing real resource files and comparing the
 * result against reference checksums produced by the {@code xxh32sum} tool
 * (see <a href="https://cyan4973.github.io/xxHash/">xxHash</a>).
 */
class XXHash32Test {

    /** Buffer size used when reading a test resource fully into memory. */
    private static final int READ_BUFFER_SIZE = 10240;

    /**
     * Supplies the parameters for the tests: a classpath resource to hash and
     * the hexadecimal xxHash32 checksum expected for that resource.
     *
     * @return one {@link Arguments} per resource: (resource path, expected hex checksum).
     */
    static Stream<Arguments> data() {
        // @formatter:off
        // Reference checksums created with xxh32sum (https://cyan4973.github.io/xxHash/).
        return Stream.of(
            Arguments.of("org/apache/commons/codec/bla.tar",    "fbb5c8d1"),
            Arguments.of("org/apache/commons/codec/bla.tar.xz", "4106a208"),
            Arguments.of("org/apache/commons/codec/small.bin",  "f66c26f8")
        );
        // @formatter:on
    }

    /**
     * Resolves a classpath resource to a file system path.
     *
     * @param resourcePath the classpath location of the resource.
     * @return the resolved path on the file system.
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
     * Reads the whole input stream into a byte array.
     *
     * @param input the stream to drain.
     * @return the stream contents as a byte array.
     * @throws IOException if reading fails.
     */
    private static byte[] readAllBytes(final InputStream input) throws IOException {
        final ByteArrayOutputStream output = new ByteArrayOutputStream();
        IOUtils.copyLarge(input, output, new byte[READ_BUFFER_SIZE]);
        return output.toByteArray();
    }

    /**
     * Hashes a resource in a single {@code update} call and verifies the checksum.
     */
    @ParameterizedTest
    @MethodSource("data")
    void verifyChecksum(final String resourcePath, final String expectedChecksum) throws Exception {
        final Path file = resolveResource(resourcePath);

        final XXHash32 hasher = new XXHash32();
        try (InputStream in = Files.newInputStream(file)) {
            final byte[] bytes = readAllBytes(in);
            hasher.update(bytes, 0, bytes.length);
        }

        assertEquals(expectedChecksum, Long.toHexString(hasher.getValue()), "checksum for " + file);
    }

    /**
     * Hashes a resource through several incremental {@code update} calls and
     * verifies that the checksum still matches the single-shot result. This also
     * exercises {@link XXHash32#reset()} and the no-op for a negative length.
     */
    @ParameterizedTest
    @MethodSource("data")
    void verifyIncrementalChecksum(final String resourcePath, final String expectedChecksum) throws Exception {
        final Path file = resolveResource(resourcePath);

        final XXHash32 hasher = new XXHash32();
        try (InputStream in = Files.newInputStream(file)) {
            final byte[] bytes = readAllBytes(in);

            // Feed one byte, then reset, so the upcoming hash starts from a clean state.
            hasher.update(bytes[0]);
            hasher.reset();

            // Feed the data in chunks: first byte, middle bytes, last byte.
            hasher.update(bytes[0]);
            hasher.update(bytes, 1, bytes.length - 2);
            hasher.update(bytes, bytes.length - 1, 1);

            // A negative length must be ignored and leave the hash unchanged.
            hasher.update(bytes, 0, -1);
        }

        assertEquals(expectedChecksum, Long.toHexString(hasher.getValue()), "checksum for " + file);
    }
}
