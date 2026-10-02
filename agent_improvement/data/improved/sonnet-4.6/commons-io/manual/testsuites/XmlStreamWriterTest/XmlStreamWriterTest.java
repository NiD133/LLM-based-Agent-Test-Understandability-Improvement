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
 */
class XmlStreamWriterTest {

    /** French text containing é (U+00E9), encodable in ISO-8859-1 and above */
    private static final String TEXT_LATIN1 = "eacute: é";

    /** Greek text containing α (U+03B1), requires ISO-8859-7 or Unicode */
    private static final String TEXT_LATIN7 = "alpha: α";

    /** Text containing the Euro sign € (U+20AC), requires ISO-8859-15 or Unicode */
    private static final String TEXT_LATIN15 = "euro: €";

    /** Japanese hiragana character あ (U+3042), requires EUC-JP or Unicode */
    private static final String TEXT_EUC_JP = "hiragana A: あ";

    /** Combined text covering characters from multiple scripts; only Unicode encodings can represent all of them */
    private static final String TEXT_UNICODE = TEXT_LATIN1 + ", " + TEXT_LATIN7
            + ", " + TEXT_LATIN15 + ", " + TEXT_EUC_JP;

    /**
     * Writes {@code xml} to an {@link XmlStreamWriter} configured with {@code defaultEncodingName},
     * then asserts that:
     * <ul>
     *   <li>the writer detected {@code encodingName} as the active encoding, and</li>
     *   <li>the bytes written to the stream equal {@code xml} encoded with {@code encodingName}.</li>
     * </ul>
     *
     * @param xml                the XML string to write
     * @param encodingName       the encoding the writer is expected to detect from the XML prolog
     * @param defaultEncodingName the fallback encoding configured on the writer (may be {@code null})
     */
    @SuppressWarnings("resource") // closedWriter is captured before close so getEncoding() can be inspected afterwards
    private static void checkXmlContent(final String xml, final String encodingName, final String defaultEncodingName)
            throws IOException {
        final ByteArrayOutputStream out = new ByteArrayOutputStream();
        // Retain a reference to the writer so we can call getEncoding() after the try-with-resources closes it
        final XmlStreamWriter closedWriter;
        try (XmlStreamWriter writer = XmlStreamWriter.builder().setOutputStream(out).setCharset(defaultEncodingName).get()) {
            closedWriter = writer;
            writer.write(xml);
        }
        final byte[] actualBytes = out.toByteArray();
        final Charset expectedCharset = Charset.forName(encodingName);
        final Charset detectedCharset = Charset.forName(closedWriter.getEncoding());
        assertEquals(expectedCharset, detectedCharset,
                "Writer should have detected encoding '" + encodingName + "'");
        assertTrue(detectedCharset.contains(expectedCharset), detectedCharset.name());
        assertArrayEquals(xml.getBytes(encodingName), actualBytes,
                "Bytes written to stream should match XML encoded as '" + encodingName + "'");
    }

    /**
     * Builds an XML document from {@code text} and {@code encoding}, writes it with
     * {@link XmlStreamWriter} (no explicit default encoding), and verifies the result.
     */
    private static void checkXmlWriter(final String text, final String encoding)
            throws IOException {
        checkXmlWriter(text, encoding, null);
    }

    /**
     * Builds an XML document from {@code text} and {@code encoding}, writes it with
     * {@link XmlStreamWriter} configured with {@code defaultEncoding}, and verifies the result.
     *
     * <p>Encoding resolution order used to determine what the writer should detect:
     * <ol>
     *   <li>The {@code encoding} declared in the XML prolog, if non-{@code null}.</li>
     *   <li>The {@code defaultEncoding} configured on the writer, if non-{@code null}.</li>
     *   <li>UTF-8 when neither is provided.</li>
     * </ol>
     */
    private static void checkXmlWriter(final String text, final String encoding, final String defaultEncoding)
            throws IOException {
        final String xml = createXmlContent(text, encoding);
        // Determine the encoding that the writer is expected to select
        final String effectiveEncoding;
        if (encoding != null) {
            effectiveEncoding = encoding;
        } else if (defaultEncoding != null) {
            effectiveEncoding = defaultEncoding;
        } else {
            effectiveEncoding = StandardCharsets.UTF_8.name();
        }
        checkXmlContent(xml, effectiveEncoding, defaultEncoding);
    }

    /**
     * Creates a minimal XML document wrapping {@code text} in a {@code <text>} element.
     * Includes an {@code encoding} attribute in the XML prolog when {@code encoding} is non-{@code null}.
     */
    private static String createXmlContent(final String text, final String encoding) {
        final String xmlDecl = encoding != null
                ? "<?xml version=\"1.0\" encoding=\"" + encoding + "\"?>"
                : "<?xml version=\"1.0\"?>";
        return xmlDecl + "\n<text>" + text + "</text>";
    }

    /**
     * Verifies that when no encoding is declared in the XML prolog, the writer falls back to
     * the configured default encoding, or to UTF-8 when no default is configured.
     */
    @Test
    void testDefaultEncoding() throws IOException {
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

    /**
     * Verifies that flushing and writing to a writer (including with empty and single-character
     * strings) succeeds without errors. Tests both the deprecated constructor and the builder API.
     */
    @Test
    void testEmpty() throws IOException {
        // Using the deprecated OutputStream constructor
        try (ByteArrayOutputStream out = new ByteArrayOutputStream();
                XmlStreamWriter writer = new XmlStreamWriter(out)) {
            writer.flush();
            writer.write("");
            writer.flush();
            writer.write(".");
            writer.flush();
        }
        // Using the builder API
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
     * Verifies that lower-case encoding names (e.g. {@code "utf-8"}) are handled correctly
     * under the Turkish locale. Turkish's locale-sensitive {@code toUpperCase()} turns 'i' into
     * 'İ' (dotted capital I) rather than 'I', which would corrupt encoding names like "utf-8"
     * → "UTF-8İ". The writer must use {@link java.util.Locale#ROOT} to avoid this.
     * (Regression test for IO-557.)
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
