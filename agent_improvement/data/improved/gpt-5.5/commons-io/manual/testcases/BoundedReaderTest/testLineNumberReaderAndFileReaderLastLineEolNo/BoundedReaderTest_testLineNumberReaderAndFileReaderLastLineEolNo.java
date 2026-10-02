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

    private static final Duration TIMEOUT = Duration.ofSeconds(10);
    private static final int MAX_CHARS_FROM_TARGET_READER = 10_000_000;
    private static final String CONTENT_WITH_LAST_LINE_WITHOUT_EOL = "0\n1\n2";

    @Test
    void testLineNumberReaderAndFileReaderLastLineEolNo() {
        assertTimeout(TIMEOUT, () -> readFileThroughBoundedLineNumberReader(CONTENT_WITH_LAST_LINE_WITHOUT_EOL));
    }

    private void readFileThroughBoundedLineNumberReader(final String data) throws IOException {
        try (TempFile path = TempFile.create(getClass().getSimpleName(), ".txt")) {
            final File file = path.toFile();
            FileUtils.write(file, data, StandardCharsets.ISO_8859_1);

            try (Reader source = Files.newBufferedReader(file.toPath())) {
                readAllLines(source);
            }
        }
    }

    private void readAllLines(final Reader source) throws IOException {
        try (LineNumberReader reader = new LineNumberReader(new BoundedReader(source, MAX_CHARS_FROM_TARGET_READER))) {
            while (reader.readLine() != null) {
                // Exhaust the reader to verify the last line is handled without a trailing EOL.
            }
        }
    }
}
