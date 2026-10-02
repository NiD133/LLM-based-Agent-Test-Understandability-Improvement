package org.apache.commons.io.output;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import org.junit.jupiter.api.Test;

/**
 * Tests that XmlStreamWriter handles empty or minimal content without errors,
 * covering both the legacy constructor and the builder API.
 */
public class XmlStreamWriterTest_testEmpty {

    @Test
    void testEmpty() throws IOException {
        // Legacy constructor: flush and write with no XML content should not throw
        try (ByteArrayOutputStream out = new ByteArrayOutputStream();
             XmlStreamWriter writer = new XmlStreamWriter(out)) {
            writer.flush();
            writer.write("");
            writer.flush();
            writer.write(".");
            writer.flush();
        }

        // Builder API: same behavior expected via the modern construction path
        try (ByteArrayOutputStream out = new ByteArrayOutputStream();
             XmlStreamWriter writer = XmlStreamWriter.builder().setOutputStream(out).get()) {
            writer.flush();
            writer.write("");
            writer.flush();
            writer.write(".");
            writer.flush();
        }
    }
}
