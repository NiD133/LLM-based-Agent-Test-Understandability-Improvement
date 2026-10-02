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

package org.apache.commons.codec.binary;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;

import org.junit.jupiter.api.Test;

/**
 * Tests {@link StringUtils}
 */
class StringUtilsTest {

    private static final byte[] BYTES_FIXTURE = { 'a', 'b', 'c' };

    // This is valid input for UTF-16BE
    private static final byte[] BYTES_FIXTURE_16BE = { 0, 'a', 0, 'b', 0, 'c' };

    // This is valid for UTF-16LE
    private static final byte[] BYTES_FIXTURE_16LE = { 'a', 0, 'b', 0, 'c', 0 };

    private static final String STRING_FIXTURE = "ABC";

    @Test
    void testByteBufferUtf8() {
        assertNull(StringUtils.getByteBufferUtf8(null), "Should return null for null input");
        final String text = "asdhjfhsadiogasdjhagsdygfjasfgsdaksjdhfk";
        final ByteBuffer bb = StringUtils.getByteBufferUtf8(text);
        assertArrayEquals(text.getBytes(StandardCharsets.UTF_8), bb.array());
    }

    /**
     * We could make the constructor private but there does not seem to be a point to jumping through extra code hoops
     * to restrict instantiation right now.
     */
    @Test
    void testConstructor() {
        new StringUtils();
    }

    /**
     * Tests {@link StringUtils#equals(CharSequence, CharSequence)} when both operands are
     * {@link StringBuilder} instances, covering null handling, equal, and unequal cases.
     */
    @Test
    void testEqualsWithStringBuilderOperands() {
        assertFalse(StringUtils.equals(new StringBuilder("abc"), null),        "non-null vs null should be false");
        assertFalse(StringUtils.equals(null, new StringBuilder("abc")),        "null vs non-null should be false");
        assertTrue(StringUtils.equals(new StringBuilder("abc"), new StringBuilder("abc")),  "identical content should be equal");
        assertFalse(StringUtils.equals(new StringBuilder("abc"), new StringBuilder("abcd")), "shorter vs longer should be false");
        assertFalse(StringUtils.equals(new StringBuilder("abcd"), new StringBuilder("abc")), "longer vs shorter should be false");
        assertFalse(StringUtils.equals(new StringBuilder("abc"), new StringBuilder("ABC")),  "case difference should be false");
    }

    /**
     * Tests {@link StringUtils#equals(CharSequence, CharSequence)} when the two operands are of
     * different {@link CharSequence} implementations ({@link String} and {@link StringBuilder}).
     */
    @Test
    void testEqualsWithMixedStringAndStringBuilderOperands() {
        assertTrue(StringUtils.equals("abc", new StringBuilder("abc")),        "String vs equal StringBuilder should be true");
        assertFalse(StringUtils.equals(new StringBuilder("abc"), "abcd"),      "StringBuilder vs longer String should be false");
        assertFalse(StringUtils.equals("abcd", new StringBuilder("abc")),      "longer String vs StringBuilder should be false");
        assertFalse(StringUtils.equals(new StringBuilder("abc"), "ABC"),       "case difference should be false");
    }

    @Test
    void testEqualsString() {
        assertTrue(StringUtils.equals(null, null),      "null equals null should be true");
        assertFalse(StringUtils.equals("abc", null),    "non-null vs null should be false");
        assertFalse(StringUtils.equals(null, "abc"),    "null vs non-null should be false");
        assertTrue(StringUtils.equals("abc", "abc"),    "identical strings should be equal");
        assertFalse(StringUtils.equals("abc", "abcd"),  "shorter vs longer should be false");
        assertFalse(StringUtils.equals("abcd", "abc"),  "longer vs shorter should be false");
        assertFalse(StringUtils.equals("abc", "ABC"),   "case difference should be false");
    }

    @Test
    void testGetBytesIso8859_1() throws UnsupportedEncodingException {
        final String charsetName = StandardCharsets.ISO_8859_1.name();
        testGetBytesUnchecked(charsetName);
        final byte[] expected = STRING_FIXTURE.getBytes(charsetName);
        final byte[] actual = StringUtils.getBytesIso8859_1(STRING_FIXTURE);
        assertArrayEquals(expected, actual);
    }

    private void testGetBytesUnchecked(final String charsetName) throws UnsupportedEncodingException {
        final byte[] expected = STRING_FIXTURE.getBytes(charsetName);
        final byte[] actual = StringUtils.getBytesUnchecked(STRING_FIXTURE, charsetName);
        assertArrayEquals(expected, actual);
    }

    @Test
    void testGetBytesUncheckedWithUnknownEncodingThrowsIllegalStateException() {
        assertThrows(IllegalStateException.class, () -> StringUtils.getBytesUnchecked(STRING_FIXTURE, "UNKNOWN"));
    }

    @Test
    void testGetBytesUncheckedNullInput() {
        assertNull(StringUtils.getBytesUnchecked(null, "UNKNOWN"));
    }

    @Test
    void testGetBytesUsAscii() throws UnsupportedEncodingException {
        final String charsetName = StandardCharsets.US_ASCII.name();
        testGetBytesUnchecked(charsetName);
        final byte[] expected = STRING_FIXTURE.getBytes(charsetName);
        final byte[] actual = StringUtils.getBytesUsAscii(STRING_FIXTURE);
        assertArrayEquals(expected, actual);
    }

    @Test
    void testGetBytesUtf16() throws UnsupportedEncodingException {
        final String charsetName = StandardCharsets.UTF_16.name();
        testGetBytesUnchecked(charsetName);
        final byte[] expected = STRING_FIXTURE.getBytes(charsetName);
        final byte[] actual = StringUtils.getBytesUtf16(STRING_FIXTURE);
        assertArrayEquals(expected, actual);
    }

    @Test
    void testGetBytesUtf16Be() throws UnsupportedEncodingException {
        final String charsetName = StandardCharsets.UTF_16BE.name();
        testGetBytesUnchecked(charsetName);
        final byte[] expected = STRING_FIXTURE.getBytes(charsetName);
        final byte[] actual = StringUtils.getBytesUtf16Be(STRING_FIXTURE);
        assertArrayEquals(expected, actual);
    }

    @Test
    void testGetBytesUtf16Le() throws UnsupportedEncodingException {
        final String charsetName = StandardCharsets.UTF_16LE.name();
        testGetBytesUnchecked(charsetName);
        final byte[] expected = STRING_FIXTURE.getBytes(charsetName);
        final byte[] actual = StringUtils.getBytesUtf16Le(STRING_FIXTURE);
        assertArrayEquals(expected, actual);
    }

    @Test
    void testGetBytesUtf8() throws UnsupportedEncodingException {
        final String charsetName = StandardCharsets.UTF_8.name();
        testGetBytesUnchecked(charsetName);
        final byte[] expected = STRING_FIXTURE.getBytes(charsetName);
        final byte[] actual = StringUtils.getBytesUtf8(STRING_FIXTURE);
        assertArrayEquals(expected, actual);
    }

    private void testNewString(final String charsetName) throws UnsupportedEncodingException {
        final String expected = new String(BYTES_FIXTURE, charsetName);
        final String actual = StringUtils.newString(BYTES_FIXTURE, charsetName);
        assertEquals(expected, actual);
    }

    @Test
    void testNewStringWithUnknownEncodingThrowsIllegalStateException() {
        assertThrows(IllegalStateException.class, () -> StringUtils.newString(BYTES_FIXTURE, "UNKNOWN"));
    }

    @Test
    void testNewStringIso8859_1() throws UnsupportedEncodingException {
        final String charsetName = StandardCharsets.ISO_8859_1.name();
        testNewString(charsetName);
        final String expected = new String(BYTES_FIXTURE, charsetName);
        final String actual = StringUtils.newStringIso8859_1(BYTES_FIXTURE);
        assertEquals(expected, actual);
    }

    @Test
    void testNewStringNullInput() {
        assertNull(StringUtils.newString(null, "UNKNOWN"));
    }

    /**
     * Regression test: all charset-specific {@code newStringXxx} convenience methods must
     * return {@code null} when the input byte array is {@code null} rather than throwing.
     */
    @Test
    void testNewStringNullInputForAllCharsetSpecificMethods() {
        assertNull(StringUtils.newStringUtf8(null));
        assertNull(StringUtils.newStringIso8859_1(null));
        assertNull(StringUtils.newStringUsAscii(null));
        assertNull(StringUtils.newStringUtf16(null));
        assertNull(StringUtils.newStringUtf16Be(null));
        assertNull(StringUtils.newStringUtf16Le(null));
    }

    @Test
    void testNewStringUsAscii() throws UnsupportedEncodingException {
        final String charsetName = StandardCharsets.US_ASCII.name();
        testNewString(charsetName);
        final String expected = new String(BYTES_FIXTURE, charsetName);
        final String actual = StringUtils.newStringUsAscii(BYTES_FIXTURE);
        assertEquals(expected, actual);
    }

    @Test
    void testNewStringUtf16() throws UnsupportedEncodingException {
        final String charsetName = StandardCharsets.UTF_16.name();
        testNewString(charsetName);
        final String expected = new String(BYTES_FIXTURE, charsetName);
        final String actual = StringUtils.newStringUtf16(BYTES_FIXTURE);
        assertEquals(expected, actual);
    }

    @Test
    void testNewStringUtf16Be() throws UnsupportedEncodingException {
        final String charsetName = StandardCharsets.UTF_16BE.name();
        testNewString(charsetName);
        final String expected = new String(BYTES_FIXTURE_16BE, charsetName);
        final String actual = StringUtils.newStringUtf16Be(BYTES_FIXTURE_16BE);
        assertEquals(expected, actual);
    }

    @Test
    void testNewStringUtf16Le() throws UnsupportedEncodingException {
        final String charsetName = StandardCharsets.UTF_16LE.name();
        testNewString(charsetName);
        final String expected = new String(BYTES_FIXTURE_16LE, charsetName);
        final String actual = StringUtils.newStringUtf16Le(BYTES_FIXTURE_16LE);
        assertEquals(expected, actual);
    }

    @Test
    void testNewStringUtf8() throws UnsupportedEncodingException {
        final String charsetName = StandardCharsets.UTF_8.name();
        testNewString(charsetName);
        final String expected = new String(BYTES_FIXTURE, charsetName);
        final String actual = StringUtils.newStringUtf8(BYTES_FIXTURE);
        assertEquals(expected, actual);
    }
}
