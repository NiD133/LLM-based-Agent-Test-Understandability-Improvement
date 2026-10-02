package org.apache.commons.io.output;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import org.junit.jupiter.api.Test;

public class XmlStreamWriterTest_testEmpty {

    private static void writeEmptyStringThenDot(final XmlStreamWriter writer) throws IOException {
        writer.flush();
        writer.write("");
        writer.flush();
        writer.write(".");
        writer.flush();
    }

    @Test
    void testEmpty() throws IOException {
        try (ByteArrayOutputStream out = new ByteArrayOutputStream();
            XmlStreamWriter writer = new XmlStreamWriter(out)) {
            writeEmptyStringThenDot(writer);
        }
        try (ByteArrayOutputStream out = new ByteArrayOutputStream();
            XmlStreamWriter writer = XmlStreamWriter.builder().setOutputStream(out).get()) {
            writeEmptyStringThenDot(writer);
        }
    }
}
