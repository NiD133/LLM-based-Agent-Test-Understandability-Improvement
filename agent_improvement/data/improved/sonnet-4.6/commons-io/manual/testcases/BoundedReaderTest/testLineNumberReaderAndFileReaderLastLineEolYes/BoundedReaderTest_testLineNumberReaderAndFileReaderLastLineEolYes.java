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
 * Verifies that wrapping a file-backed reader in BoundedReader and then LineNumberReader
 * does not hang (infinite loop) when the file content ends with a newline character.
 */
public class BoundedReaderTest_testLineNumberReaderAndFileReaderLastLineEolYes {

    // Content whose last line ends with '\n' — the case being tested for hang-free behaviour.
    private static final String CONTENT_ENDING_WITH_EOL = "0\n1\n2\n";

    // Large enough bound so BoundedReader never interrupts reading before EOF.
    private static final int BOUND_LARGER_THAN_FILE = 10_000_000;

    private static final Duration TIMEOUT = Duration.ofSeconds(10);

    /**
     * Reads all lines from {@code source} through a BoundedReader to confirm no infinite loop occurs.
     */
    private void readAllLines(final Reader source) throws IOException {
        try (LineNumberReader reader = new LineNumberReader(new BoundedReader(source, BOUND_LARGER_THAN_FILE))) {
            while (reader.readLine() != null) {
                // consume all lines; a hang here would be caught by assertTimeout
            }
        }
    }

    /**
     * Writes {@code content} to a temp file, then reads every line through a BoundedReader.
     */
    private void readAllLinesFromTempFile(final String content) throws IOException {
        try (TempFile tempFile = TempFile.create(getClass().getSimpleName(), ".txt")) {
            final File file = tempFile.toFile();
            FileUtils.write(file, content, StandardCharsets.ISO_8859_1);
            try (Reader fileReader = Files.newBufferedReader(file.toPath())) {
                readAllLines(fileReader);
            }
        }
    }

    @Test
    void testLineNumberReaderAndFileReaderLastLineEolYes() {
        // Must complete within the timeout; a bug would cause LineNumberReader to loop forever
        // on the trailing newline when BoundedReader signals EOF prematurely.
        assertTimeout(TIMEOUT, () -> readAllLinesFromTempFile(CONTENT_ENDING_WITH_EOL));
    }
}
