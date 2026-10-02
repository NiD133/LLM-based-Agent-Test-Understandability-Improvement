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
 * Tests that a {@link BoundedReader} wrapped in a {@link LineNumberReader} can read a file to the end without hanging,
 * when that file's content ends with an end-of-line character.
 *
 * <p>
 * This guards against a regression where reading the trailing (final) line of an EOL-terminated file could loop
 * forever, so each assertion is wrapped in a timeout.
 * </p>
 */
public class BoundedReaderTest_testLineNumberReaderAndFileReaderLastLineEolYes {

    /** Fails the test if reading the file does not finish within this duration (guards against an infinite loop). */
    private static final Duration TIMEOUT = Duration.ofSeconds(10);

    /** Three lines, where the final line is terminated by an end-of-line character. */
    private static final String CONTENT_ENDING_WITH_EOL = "0\n1\n2\n";

    /** A character limit far larger than the test content, so the bound never truncates the data. */
    private static final int UNREACHABLE_CHAR_LIMIT = 10_000_000;

    @Test
    void testLineNumberReaderAndFileReaderLastLineEolYes() {
        assertTimeout(TIMEOUT, () -> readAllLinesFromFileContaining(CONTENT_ENDING_WITH_EOL));
    }

    /**
     * Writes the given text to a temporary file, then reads every line of it back through a
     * {@code LineNumberReader(BoundedReader(...))} until end-of-file.
     *
     * @param data the text to write to the temporary file.
     * @throws IOException if writing or reading the temporary file fails.
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

    /**
     * Reads and discards every line available from the given reader, after wrapping it in a {@link BoundedReader}.
     *
     * @param source the reader to drain.
     * @throws IOException if reading fails.
     */
    private void readAllLines(final Reader source) throws IOException {
        try (LineNumberReader reader = new LineNumberReader(new BoundedReader(source, UNREACHABLE_CHAR_LIMIT))) {
            while (reader.readLine() != null) {
                // Read until EOF; the line content itself is irrelevant to this test.
            }
        }
    }
}
