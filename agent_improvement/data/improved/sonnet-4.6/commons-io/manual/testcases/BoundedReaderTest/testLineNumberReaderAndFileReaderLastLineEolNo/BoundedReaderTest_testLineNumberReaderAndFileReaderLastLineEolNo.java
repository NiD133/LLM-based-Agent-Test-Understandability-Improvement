package org.apache.commons.io.input;

import static org.junit.jupiter.api.Assertions.assertTimeout;

import java.io.File;
import java.io.IOException;
import java.io.LineNumberReader;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.time.Duration;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.file.TempFile;
import org.junit.jupiter.api.Test;

public class BoundedReaderTest_testLineNumberReaderAndFileReaderLastLineEolNo {

    // Maximum time allowed for reading a file through BoundedReader — guards against infinite loops
    private static final Duration TIMEOUT = Duration.ofSeconds(10);

    // File content whose last line has no trailing newline, which is the edge case under test
    private static final String STRING_END_NO_EOL = "0\n1\n2";

    /**
     * Verifies that reading a file whose last line lacks a trailing newline completes within the
     * allowed timeout. A BoundedReader wrapping a LineNumberReader must not hang when EOF is reached
     * mid-line.
     */
    @Test
    void testLineNumberReaderAndFileReaderLastLineEolNo() {
        assertTimeout(TIMEOUT, () -> testLineNumberReaderAndFileReaderLastLine(STRING_END_NO_EOL));
    }

    void testLineNumberReaderAndFileReaderLastLine(final String data) throws IOException {
        try (TempFile path = TempFile.create(getClass().getSimpleName(), ".txt")) {
            final File file = path.toFile();
            FileUtils.write(file, data, StandardCharsets.ISO_8859_1);
            try (Reader source = Files.newBufferedReader(file.toPath())) {
                testLineNumberReader(source);
            }
        }
    }

    private void testLineNumberReader(final Reader source) throws IOException {
        try (LineNumberReader reader = new LineNumberReader(new BoundedReader(source, 10_000_000))) {
            while (reader.readLine() != null) {
                // drain all lines; no assertions needed — the assertTimeout above catches hangs
            }
        }
    }
}
