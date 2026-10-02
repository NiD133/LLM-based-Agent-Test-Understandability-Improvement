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
package org.apache.commons.io.output;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

import org.junit.jupiter.api.Test;
import org.junitpioneer.jupiter.DefaultLocale;

/**
 * Tests {@link XmlStreamWriter}.
 * <p>
 * The writer inspects the {@code <?xml ... encoding="..."?>} prolog of the text it is given and
 * encodes the output bytes using that declared encoding (falling back to a configurable default
 * when no encoding is declared). These tests feed it XML carrying characters from various charsets
 * and verify that the bytes it produces match those charsets exactly.
 * </p>
 */
class XmlStreamWriterTest {

    /** A French character ("e acute") that exists in Latin-1. */
    private static final String TEXT_LATIN1 = "eacute: é";

    /** A Greek character ("alpha") that exists in Latin-7. */
    private static final String TEXT_LATIN7 = "alpha: α";

    /** The euro sign, which exists in Latin-15 but not Latin-1. */
    private static final String TEXT_LATIN15 = "euro: €";

    /** A Japanese character ("hiragana A") that exists in EUC-JP. */
    private static final String TEXT_EUC_JP = "hiragana A: あ";

    /** Combines all of the above; only a Unicode encoding can represent every character. */
    private static final String TEXT_UNICODE = TEXT_LATIN1 + ", " + TEXT_LATIN7
            + ", " + TEXT_LATIN15 + ", " + TEXT_EUC_JP;

    /**
     * Writes {@code xml} through an {@link XmlStreamWriter} configured with {@code defaultEncodingName}
     * as its fallback, then asserts that:
     * <ul>
     *   <li>the encoding the writer actually selected equals {@code expectedEncodingName}, and</li>
     *   <li>the bytes written match {@code xml} encoded with {@code expectedEncodingName}.</li>
     * </ul>
     *
     * @param xml                  the XML document to write.
     * @param expectedEncodingName the charset the writer is expected to use for the output bytes.
     * @param defaultEncodingName  the writer's fallback charset, used when the XML declares none.
     */
    @SuppressWarnings("resource")
    private static void checkXmlContent(final String xml, final String expectedEncodingName,
            final String defaultEncodingName) throws IOException {
        final ByteArrayOutputStream out = new ByteArrayOutputStream();
        // Keep a reference to the writer so its detected encoding can be read after it is closed.
        final XmlStreamWriter detectingWriter;
        try (XmlStreamWriter writer = XmlStreamWriter.builder()
                .setOutputStream(out)
                .setCharset(defaultEncodingName)
                .get()) {
            detectingWriter = writer;
            writer.write(xml);
        }
        final byte[] actualBytes = out.toByteArray();

        final Charset expectedCharset = Charset.forName(expectedEncodingName);
        final Charset detectedCharset = Charset.forName(detectingWriter.getEncoding());
        assertEquals(expectedCharset, detectedCharset);
        assertTrue(detectedCharset.contains(expectedCharset), detectedCharset.name());
        assertArrayEquals(xml.getBytes(expectedEncodingName), actualBytes);
    }

    /**
     * Wraps {@code text} in an XML document whose prolog declares {@code encoding}, then verifies the
     * writer reproduces it using that same encoding. The writer's fallback default is left unset.
     */
    private static void checkXmlWriter(final String text, final String encoding) throws IOException {
        checkXmlWriter(text, encoding, null);
    }

    /**
     * Wraps {@code text} in an XML document (declaring {@code encoding} in its prolog when non-null),
     * then verifies the writer reproduces it. When the XML declares no encoding, the writer is expected
     * to fall back to {@code defaultEncoding}, or to UTF-8 when that is also null.
     */
    private static void checkXmlWriter(final String text, final String encoding, final String defaultEncoding)
            throws IOException {
        final String xml = createXmlContent(text, encoding);
        final String expectedEncoding;
        if (encoding != null) {
            expectedEncoding = encoding;
        } else if (defaultEncoding != null) {
            expectedEncoding = defaultEncoding;
        } else {
            expectedEncoding = StandardCharsets.UTF_8.name();
        }
        checkXmlContent(xml, expectedEncoding, defaultEncoding);
    }

    /**
     * Builds a minimal XML document of the form {@code <?xml ...?>\n<text>...</text>}. The prolog
     * includes an {@code encoding} attribute only when {@code encoding} is non-null.
     */
    private static String createXmlContent(final String text, final String encoding) {
        final String xmlDecl;
        if (encoding != null) {
            xmlDecl = "<?xml version=\"1.0\" encoding=\"" + encoding + "\"?>";
        } else {
            xmlDecl = "<?xml version=\"1.0\"?>";
        }
        return xmlDecl + "\n<text>" + text + "</text>";
    }

    @Test
    void testDefaultEncoding() throws IOException {
        // No encoding is declared in the XML, so the writer must use its fallback default
        // (UTF-8 when the default is unset).
        checkXmlWriter(TEXT_UNICODE, null, null);
        checkXmlWriter(TEXT_UNICODE, null, StandardCharsets.UTF_8.name());
        checkXmlWriter(TEXT_UNICODE, null, StandardCharsets.UTF_16.name());
        checkXmlWriter(TEXT_UNICODE, null, StandardCharsets.UTF_16BE.name());
        checkXmlWriter(TEXT_UNICODE, null, StandardCharsets.ISO_8859_1.name());
    }

    @Test
    void testEBCDICEncoding() throws IOException {
        checkXmlWriter("simple text in EBCDIC", "CP1047");
    }

    @Test
    void testEmpty() throws IOException {
        // Interleaving flush() with empty and tiny writes must not fail, via either constructor.
        try (ByteArrayOutputStream out = new ByteArrayOutputStream();
                XmlStreamWriter writer = new XmlStreamWriter(out)) {
            writer.flush();
            writer.write("");
            writer.flush();
            writer.write(".");
            writer.flush();
        }
        try (ByteArrayOutputStream out = new ByteArrayOutputStream();
                XmlStreamWriter writer = XmlStreamWriter.builder().setOutputStream(out).get()) {
            writer.flush();
            writer.write("");
            writer.flush();
            writer.write(".");
            writer.flush();
        }
    }

    @Test
    void testEUC_JPEncoding() throws IOException {
        checkXmlWriter(TEXT_EUC_JP, "EUC-JP");
    }

    @Test
    void testLatin15Encoding() throws IOException {
        checkXmlWriter(TEXT_LATIN15, "ISO-8859-15");
    }

    @Test
    void testLatin1Encoding() throws IOException {
        checkXmlWriter(TEXT_LATIN1, StandardCharsets.ISO_8859_1.name());
    }

    @Test
    void testLatin7Encoding() throws IOException {
        checkXmlWriter(TEXT_LATIN7, "ISO-8859-7");
    }

    /**
     * Encoding names declared in the prolog are lower case here. Under the Turkish locale a naive
     * upper-casing of "i" produces a dotted capital "I", which would corrupt charset lookups; this
     * test guards against that regression (IO-557).
     */
    @Test
    @DefaultLocale(language = "tr")
    void testLowerCaseEncodingWithTurkishLocale_IO_557() throws IOException {
        checkXmlWriter(TEXT_UNICODE, "utf-8");
        checkXmlWriter(TEXT_LATIN1, "iso-8859-1");
        checkXmlWriter(TEXT_LATIN7, "iso-8859-7");
    }

    @Test
    void testNoXmlHeader() throws IOException {
        // Plain content with no XML prolog: the writer falls back to UTF-8.
        checkXmlContent("<text>text with no XML header</text>", StandardCharsets.UTF_8.name(), null);
    }

    @Test
    void testUTF16BEEncoding() throws IOException {
        checkXmlWriter(TEXT_UNICODE, StandardCharsets.UTF_16BE.name());
    }

    @Test
    void testUTF16Encoding() throws IOException {
        checkXmlWriter(TEXT_UNICODE, StandardCharsets.UTF_16.name());
    }

    @Test
    void testUTF16LEEncoding() throws IOException {
        checkXmlWriter(TEXT_UNICODE, StandardCharsets.UTF_16LE.name());
    }

    @Test
    void testUTF8Encoding() throws IOException {
        checkXmlWriter(TEXT_UNICODE, StandardCharsets.UTF_8.name());
    }
}
