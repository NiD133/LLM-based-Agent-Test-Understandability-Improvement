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
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;

import org.apache.commons.io.IOUtils;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class XXHash32Test {

    /**
     * Test data: classpath resource paths paired with their expected XXHash32 checksums.
     * Reference checksums were produced by the {@code xxh32sum} command-line tool
     * (see https://cyan4973.github.io/xxHash/).
     */
    static Stream<Arguments> fileChecksumPairs() {
        return Stream.of(
            Arguments.of("org/apache/commons/codec/bla.tar",    "fbb5c8d1"),
            Arguments.of("org/apache/commons/codec/bla.tar.xz", "4106a208"),
            Arguments.of("org/apache/commons/codec/small.bin",  "f66c26f8")
        );
    }

    /** Resolves a classpath resource to a filesystem {@link Path}. */
    private static Path resolveResource(final String resourcePath) throws Exception {
        final URL url = XXHash32Test.class.getClassLoader().getResource(resourcePath);
        if (url == null) {
            throw new FileNotFoundException("Couldn't find classpath resource: " + resourcePath);
        }
        return Paths.get(url.toURI());
    }

    /** Reads all bytes from {@code input} into a byte array using a 10 KB intermediate buffer. */
    private static byte[] readAllBytes(final InputStream input) throws IOException {
        final ByteArrayOutputStream output = new ByteArrayOutputStream();
        IOUtils.copyLarge(input, output, new byte[10240]);
        return output.toByteArray();
    }

    /**
     * Verifies that hashing an entire file in a single {@code update} call produces
     * the expected XXHash32 checksum.
     */
    @ParameterizedTest
    @MethodSource("fileChecksumPairs")
    void verifySingleUpdateChecksum(final String resourcePath, final String expectedChecksum) throws Exception {
        final Path file = resolveResource(resourcePath);
        final XXHash32 hasher = new XXHash32();
        try (InputStream in = Files.newInputStream(file)) {
            final byte[] bytes = readAllBytes(in);
            hasher.update(bytes, 0, bytes.length);
        }
        assertEquals(expectedChecksum, Long.toHexString(hasher.getValue()),
                "checksum for " + file);
    }

    /**
     * Verifies that XXHash32 produces the correct checksum when bytes are fed incrementally
     * across multiple {@code update} calls, and that {@code reset()} and negative-length
     * updates are handled correctly:
     * <ol>
     *   <li>Update with the first byte, then call {@code reset()} — state must be cleared.</li>
     *   <li>Re-feed the full content in three separate chunks: first byte, middle bytes, last byte.</li>
     *   <li>Call {@code update} with a negative length — must be silently ignored.</li>
     * </ol>
     */
    @ParameterizedTest
    @MethodSource("fileChecksumPairs")
    void verifyIncrementalChecksum(final String resourcePath, final String expectedChecksum) throws Exception {
        final Path file = resolveResource(resourcePath);
        final XXHash32 hasher = new XXHash32();
        try (InputStream in = Files.newInputStream(file)) {
            final byte[] bytes = readAllBytes(in);

            // Verify reset: any update made before reset must not affect the final hash.
            hasher.update(bytes[0]);
            hasher.reset();

            // Feed the entire file content across three separate chunks.
            hasher.update(bytes[0]);                        // first byte (single-byte overload)
            hasher.update(bytes, 1, bytes.length - 2);     // middle bytes
            hasher.update(bytes, bytes.length - 1, 1);     // last byte

            // Negative length must be ignored — the hash value must remain unchanged.
            hasher.update(bytes, 0, -1);
        }
        assertEquals(expectedChecksum, Long.toHexString(hasher.getValue()),
                "checksum for " + file);
    }
}
