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

/**
 * Verifies that reading every line through a {@link LineNumberReader} layered on top of a
 * {@link BoundedReader} terminates promptly (does not loop forever) when the input's final
 * line is NOT terminated by an end-of-line character.
 */
public class BoundedReaderTest_testLineNumberReaderAndFileReaderLastLineEolNo {

    /** Upper bound on how long reading all lines may take before the test is considered hung. */
    private static final Duration READ_TIMEOUT = Duration.ofSeconds(10);

    /** Three lines ("0", "1", "2") where the last line has no trailing newline. */
    private static final String LINES_WITHOUT_TRAILING_EOL = "0\n1\n2";

    /** Character limit large enough that the bound is never reached for this small input. */
    private static final int MAX_CHARS = 10_000_000;

    @Test
    void testLineNumberReaderAndFileReaderLastLineEolNo() {
        assertTimeout(READ_TIMEOUT, () -> readAllLinesFromFileContaining(LINES_WITHOUT_TRAILING_EOL));
    }

    /**
     * Writes {@code data} to a temporary file, then reads every line back through a
     * {@link BoundedReader} wrapped in a {@link LineNumberReader}.
     */
    private void readAllLinesFromFileContaining(final String data) throws IOException {
        try (TempFile tempFile = TempFile.create(getClass().getSimpleName(), ".txt")) {
            final File file = tempFile.toFile();
            FileUtils.write(file, data, StandardCharsets.ISO_8859_1);
            try (Reader fileReader = Files.newBufferedReader(file.toPath())) {
                readAllLines(fileReader);
            }
        }
    }

    /** Drains every line from {@code source} via a bounded, line-numbering reader. */
    private void readAllLines(final Reader source) throws IOException {
        try (LineNumberReader reader = new LineNumberReader(new BoundedReader(source, MAX_CHARS))) {
            while (reader.readLine() != null) {
                // Discard each line; we only care that the loop terminates.
            }
        }
    }
}
