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

    /** French */
    private static final String TEXT_LATIN1 = "eacute: \u00E9";

    /** Greek */
    private static final String TEXT_LATIN7 = "alpha: \u03B1";

    /** Euro support */
    private static final String TEXT_LATIN15 = "euro: \u20AC";

    /** Japanese */
    private static final String TEXT_EUC_JP = "hiragana A: \u3042";

    /** Unicode: support everything */
    private static final String TEXT_UNICODE = TEXT_LATIN1 + ", " + TEXT_LATIN7
            + ", " + TEXT_LATIN15 + ", " + TEXT_EUC_JP;

    private static final String XML_DECLARATION_WITHOUT_ENCODING = "<?xml version=\"1.0\"?>";

    @SuppressWarnings("resource")
    private static void assertXmlIsWrittenWithEncoding(final String xml, final String expectedEncodingName,
            final String defaultEncodingName) throws IOException {
        final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        final XmlStreamWriter closedWriter;
        try (XmlStreamWriter writer = XmlStreamWriter.builder().setOutputStream(outputStream).setCharset(defaultEncodingName).get()) {
            closedWriter = writer;
            writer.write(xml);
        }

        final Charset expectedEncoding = Charset.forName(expectedEncodingName);
        final Charset detectedEncoding = Charset.forName(closedWriter.getEncoding());

        assertEquals(expectedEncoding, detectedEncoding);
        assertTrue(detectedEncoding.contains(expectedEncoding), detectedEncoding.name());
        assertArrayEquals(xml.getBytes(expectedEncodingName), outputStream.toByteArray());
    }

    private static void assertXmlWriterUsesDeclaredEncoding(final String text, final String declaredEncoding)
            throws IOException {
        assertXmlWriterUsesDeclaredEncoding(text, declaredEncoding, null);
    }

    private static void assertXmlWriterUsesDeclaredEncoding(final String text, final String declaredEncoding,
            final String defaultEncoding) throws IOException {
        final String xml = createXmlContent(text, declaredEncoding);
        final String expectedEncoding = resolveExpectedEncoding(declaredEncoding, defaultEncoding);
        assertXmlIsWrittenWithEncoding(xml, expectedEncoding, defaultEncoding);
    }

    private static String createXmlContent(final String text, final String encoding) {
        final String xmlDeclaration = encoding == null
                ? XML_DECLARATION_WITHOUT_ENCODING
                : "<?xml version=\"1.0\" encoding=\"" + encoding + "\"?>";
        return xmlDeclaration + "\n<text>" + text + "</text>";
    }

    private static String resolveExpectedEncoding(final String declaredEncoding, final String defaultEncoding) {
        if (declaredEncoding != null) {
            return declaredEncoding;
        }
        return defaultEncoding == null ? StandardCharsets.UTF_8.name() : defaultEncoding;
    }

    @Test
    void testDefaultEncoding() throws IOException {
        assertXmlWriterUsesDeclaredEncoding(TEXT_UNICODE, null, null);
        assertXmlWriterUsesDeclaredEncoding(TEXT_UNICODE, null, StandardCharsets.UTF_8.name());
        assertXmlWriterUsesDeclaredEncoding(TEXT_UNICODE, null, StandardCharsets.UTF_16.name());
        assertXmlWriterUsesDeclaredEncoding(TEXT_UNICODE, null, StandardCharsets.UTF_16BE.name());
        assertXmlWriterUsesDeclaredEncoding(TEXT_UNICODE, null, StandardCharsets.ISO_8859_1.name());
    }

    @Test
    void testEBCDICEncoding() throws IOException {
        assertXmlWriterUsesDeclaredEncoding("simple text in EBCDIC", "CP1047");
    }

    @Test
    void testEmpty() throws IOException {
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
        assertXmlWriterUsesDeclaredEncoding(TEXT_EUC_JP, "EUC-JP");
    }

    @Test
    void testLatin15Encoding() throws IOException {
        assertXmlWriterUsesDeclaredEncoding(TEXT_LATIN15, "ISO-8859-15");
    }

    @Test
    void testLatin1Encoding() throws IOException {
        assertXmlWriterUsesDeclaredEncoding(TEXT_LATIN1, StandardCharsets.ISO_8859_1.name());
    }

    @Test
    void testLatin7Encoding() throws IOException {
        assertXmlWriterUsesDeclaredEncoding(TEXT_LATIN7, "ISO-8859-7");
    }

    /** Turkish language has specific rules to convert dotted and dotless i character. */
    @Test
    @DefaultLocale(language = "tr")
    void testLowerCaseEncodingWithTurkishLocale_IO_557() throws IOException {
        assertXmlWriterUsesDeclaredEncoding(TEXT_UNICODE, "utf-8");
        assertXmlWriterUsesDeclaredEncoding(TEXT_LATIN1, "iso-8859-1");
        assertXmlWriterUsesDeclaredEncoding(TEXT_LATIN7, "iso-8859-7");
    }

    @Test
    void testNoXmlHeader() throws IOException {
        assertXmlIsWrittenWithEncoding("<text>text with no XML header</text>", StandardCharsets.UTF_8.name(), null);
    }

    @Test
    void testUTF16BEEncoding() throws IOException {
        assertXmlWriterUsesDeclaredEncoding(TEXT_UNICODE, StandardCharsets.UTF_16BE.name());
    }

    @Test
    void testUTF16Encoding() throws IOException {
        assertXmlWriterUsesDeclaredEncoding(TEXT_UNICODE, StandardCharsets.UTF_16.name());
    }

    @Test
    void testUTF16LEEncoding() throws IOException {
        assertXmlWriterUsesDeclaredEncoding(TEXT_UNICODE, StandardCharsets.UTF_16LE.name());
    }

    @Test
    void testUTF8Encoding() throws IOException {
        assertXmlWriterUsesDeclaredEncoding(TEXT_UNICODE, StandardCharsets.UTF_8.name());
    }
}
