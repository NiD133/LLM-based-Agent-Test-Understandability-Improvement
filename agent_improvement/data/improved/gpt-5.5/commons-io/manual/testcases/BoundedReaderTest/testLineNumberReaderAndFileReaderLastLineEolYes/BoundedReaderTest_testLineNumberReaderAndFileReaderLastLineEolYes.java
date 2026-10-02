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

public class BoundedReaderTest_testLineNumberReaderAndFileReaderLastLineEolYes {

    private static final Duration TIMEOUT = Duration.ofSeconds(10);

    private static final String DATA_ENDING_WITH_EOL = "0\n1\n2\n";

    void testLineNumberReaderAndFileReaderLastLine(final String data) throws IOException {
        try (TempFile path = TempFile.create(getClass().getSimpleName(), ".txt")) {
            final File file = path.toFile();
            FileUtils.write(file, data, StandardCharsets.ISO_8859_1);

            try (Reader source = Files.newBufferedReader(file.toPath())) {
                readAllLinesWithBoundedReader(source);
            }
        }
    }

    private void readAllLinesWithBoundedReader(final Reader source) throws IOException {
        try (LineNumberReader reader = new LineNumberReader(new BoundedReader(source, 10_000_000))) {
            while (reader.readLine() != null) {
                // Consume every line to verify LineNumberReader reaches EOF normally.
            }
        }
    }

    @Test
    void testLineNumberReaderAndFileReaderLastLineEolYes() {
        assertTimeout(TIMEOUT, () -> testLineNumberReaderAndFileReaderLastLine(DATA_ENDING_WITH_EOL));
    }
}
