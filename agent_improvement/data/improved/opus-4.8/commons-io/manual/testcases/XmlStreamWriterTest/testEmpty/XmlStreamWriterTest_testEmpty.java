package org.apache.commons.io.output;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import org.junit.jupiter.api.Test;

/**
 * Tests that an {@link XmlStreamWriter} tolerates being flushed before any
 * content is written, and tolerates writing empty and tiny fragments while it
 * is still buffering the XML prolog to detect the encoding.
 *
 * <p>The same scenario is exercised through both the deprecated
 * {@link XmlStreamWriter#XmlStreamWriter(java.io.OutputStream)} constructor and
 * the {@link XmlStreamWriter#builder()} factory.</p>
 */
public class XmlStreamWriterTest_testEmpty {

    /**
     * Flushes before writing, writes an empty string, then writes a single
     * character, flushing between each step. None of these calls should fail.
     */
    private static void writeEmptyThenTinyFragment(final XmlStreamWriter writer) throws IOException {
        writer.flush();
        writer.write("");
        writer.flush();
        writer.write(".");
        writer.flush();
    }

    @Test
    void testEmpty() throws IOException {
        // Deprecated constructor: defaults to UTF-8.
        try (ByteArrayOutputStream out = new ByteArrayOutputStream();
                XmlStreamWriter writer = new XmlStreamWriter(out)) {
            writeEmptyThenTinyFragment(writer);
        }

        // Builder: equivalent behaviour via the recommended API.
        try (ByteArrayOutputStream out = new ByteArrayOutputStream();
                XmlStreamWriter writer = XmlStreamWriter.builder().setOutputStream(out).get()) {
            writeEmptyThenTinyFragment(writer);
        }
    }
}
