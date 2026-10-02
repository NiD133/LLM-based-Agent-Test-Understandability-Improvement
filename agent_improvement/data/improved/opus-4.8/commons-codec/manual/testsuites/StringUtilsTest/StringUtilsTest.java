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
 * Tests {@link StringUtils}.
 *
 * <p>
 * The general strategy of this suite is to treat the JDK's own
 * {@link String#getBytes(String)} / {@link String#String(byte[], String)} as the source of truth
 * and assert that the corresponding {@code StringUtils} convenience methods produce identical
 * results. Each charset therefore has a paired encode/decode test:
 * </p>
 * <ul>
 * <li>{@code getBytesXxx}  &rarr; encode a String to bytes for charset {@code Xxx}.</li>
 * <li>{@code newStringXxx} &rarr; decode bytes back to a String for charset {@code Xxx}.</li>
 * </ul>
 * <p>
 * {@code null} handling is verified explicitly because {@code StringUtils} promises to be
 * null-safe (returning {@code null} rather than throwing).
 * </p>
 */
class StringUtilsTest {

    /** Plain ASCII bytes ("abc"); usable as-is for single-byte charsets. */
    private static final byte[] BYTES_FIXTURE = { 'a', 'b', 'c' };

    /** "abc" encoded as big-endian UTF-16 (high byte first). */
    private static final byte[] BYTES_FIXTURE_16BE = { 0, 'a', 0, 'b', 0, 'c' };

    /** "abc" encoded as little-endian UTF-16 (low byte first). */
    private static final byte[] BYTES_FIXTURE_16LE = { 'a', 0, 'b', 0, 'c', 0 };

    /** Sample String used as input to the {@code getBytesXxx} encoding tests. */
    private static final String STRING_FIXTURE = "ABC";

    /**
     * Asserts that {@link StringUtils#getBytesUnchecked(String, String)} encodes
     * {@link #STRING_FIXTURE} exactly as the JDK does for the given charset.
     */
    private void assertGetBytesUncheckedMatchesJdk(final String charsetName) throws UnsupportedEncodingException {
        final byte[] expected = STRING_FIXTURE.getBytes(charsetName);
        final byte[] actual = StringUtils.getBytesUnchecked(STRING_FIXTURE, charsetName);
        assertArrayEquals(expected, actual);
    }

    /**
     * Asserts that {@link StringUtils#newString(byte[], String)} decodes {@link #BYTES_FIXTURE}
     * exactly as the JDK does for the given charset.
     */
    private void assertNewStringMatchesJdk(final String charsetName) throws UnsupportedEncodingException {
        final String expected = new String(BYTES_FIXTURE, charsetName);
        final String actual = StringUtils.newString(BYTES_FIXTURE, charsetName);
        assertEquals(expected, actual);
    }

    // ------------------------------------------------------------------
    // getByteBufferUtf8
    // ------------------------------------------------------------------

    @Test
    void testByteBufferUtf8() {
        assertNull(StringUtils.getByteBufferUtf8(null), "Should be null safe");
        final String text = "asdhjfhsadiogasdjhagsdygfjasfgsdaksjdhfk";
        final ByteBuffer actual = StringUtils.getByteBufferUtf8(text);
        assertArrayEquals(text.getBytes(StandardCharsets.UTF_8), actual.array());
    }

    /**
     * We could make the constructor private but there does not seem to be a point to jumping through extra code hoops
     * to restrict instantiation right now.
     */
    @Test
    void testConstructor() {
        new StringUtils();
    }

    // ------------------------------------------------------------------
    // equals(CharSequence, CharSequence)
    // ------------------------------------------------------------------

    /** equals(...) where at least one argument is a (non-String) CharSequence and the other may be null. */
    @Test
    void testEqualsCS1() {
        assertFalse(StringUtils.equals(new StringBuilder("abc"), null));
        assertFalse(StringUtils.equals(null, new StringBuilder("abc")));
        assertTrue(StringUtils.equals(new StringBuilder("abc"), new StringBuilder("abc")));
        assertFalse(StringUtils.equals(new StringBuilder("abc"), new StringBuilder("abcd")));
        assertFalse(StringUtils.equals(new StringBuilder("abcd"), new StringBuilder("abc")));
        assertFalse(StringUtils.equals(new StringBuilder("abc"), new StringBuilder("ABC")));
    }

    /** equals(...) mixing String and StringBuilder arguments; comparison is case-sensitive. */
    @Test
    void testEqualsCS2() {
        assertTrue(StringUtils.equals("abc", new StringBuilder("abc")));
        assertFalse(StringUtils.equals(new StringBuilder("abc"), "abcd"));
        assertFalse(StringUtils.equals("abcd", new StringBuilder("abc")));
        assertFalse(StringUtils.equals(new StringBuilder("abc"), "ABC"));
    }

    /** equals(...) with plain String arguments, including the null/null case. */
    @Test
    void testEqualsString() {
        assertTrue(StringUtils.equals(null, null));
        assertFalse(StringUtils.equals("abc", null));
        assertFalse(StringUtils.equals(null, "abc"));
        assertTrue(StringUtils.equals("abc", "abc"));
        assertFalse(StringUtils.equals("abc", "abcd"));
        assertFalse(StringUtils.equals("abcd", "abc"));
        assertFalse(StringUtils.equals("abc", "ABC"));
    }

    // ------------------------------------------------------------------
    // getBytesXxx (encoding) - one test per charset, plus error/null cases
    // ------------------------------------------------------------------

    @Test
    void testGetBytesIso8859_1() throws UnsupportedEncodingException {
        final String charsetName = StandardCharsets.ISO_8859_1.name();
        assertGetBytesUncheckedMatchesJdk(charsetName);
        final byte[] expected = STRING_FIXTURE.getBytes(charsetName);
        final byte[] actual = StringUtils.getBytesIso8859_1(STRING_FIXTURE);
        assertArrayEquals(expected, actual);
    }

    @Test
    void testGetBytesUsAscii() throws UnsupportedEncodingException {
        final String charsetName = StandardCharsets.US_ASCII.name();
        assertGetBytesUncheckedMatchesJdk(charsetName);
        final byte[] expected = STRING_FIXTURE.getBytes(charsetName);
        final byte[] actual = StringUtils.getBytesUsAscii(STRING_FIXTURE);
        assertArrayEquals(expected, actual);
    }

    @Test
    void testGetBytesUtf16() throws UnsupportedEncodingException {
        final String charsetName = StandardCharsets.UTF_16.name();
        assertGetBytesUncheckedMatchesJdk(charsetName);
        final byte[] expected = STRING_FIXTURE.getBytes(charsetName);
        final byte[] actual = StringUtils.getBytesUtf16(STRING_FIXTURE);
        assertArrayEquals(expected, actual);
    }

    @Test
    void testGetBytesUtf16Be() throws UnsupportedEncodingException {
        final String charsetName = StandardCharsets.UTF_16BE.name();
        assertGetBytesUncheckedMatchesJdk(charsetName);
        final byte[] expected = STRING_FIXTURE.getBytes(charsetName);
        final byte[] actual = StringUtils.getBytesUtf16Be(STRING_FIXTURE);
        assertArrayEquals(expected, actual);
    }

    @Test
    void testGetBytesUtf16Le() throws UnsupportedEncodingException {
        final String charsetName = StandardCharsets.UTF_16LE.name();
        assertGetBytesUncheckedMatchesJdk(charsetName);
        final byte[] expected = STRING_FIXTURE.getBytes(charsetName);
        final byte[] actual = StringUtils.getBytesUtf16Le(STRING_FIXTURE);
        assertArrayEquals(expected, actual);
    }

    @Test
    void testGetBytesUtf8() throws UnsupportedEncodingException {
        final String charsetName = StandardCharsets.UTF_8.name();
        assertGetBytesUncheckedMatchesJdk(charsetName);
        final byte[] expected = STRING_FIXTURE.getBytes(charsetName);
        final byte[] actual = StringUtils.getBytesUtf8(STRING_FIXTURE);
        assertArrayEquals(expected, actual);
    }

    /** An unknown charset name must surface as an IllegalStateException, not a checked exception. */
    @Test
    void testGetBytesUncheckedBadName() {
        assertThrows(IllegalStateException.class, () -> StringUtils.getBytesUnchecked(STRING_FIXTURE, "UNKNOWN"));
    }

    /** A null input string must yield a null result (null-safe), even for an unknown charset name. */
    @Test
    void testGetBytesUncheckedNullInput() {
        assertNull(StringUtils.getBytesUnchecked(null, "UNKNOWN"));
    }

    // ------------------------------------------------------------------
    // newStringXxx (decoding) - one test per charset, plus error/null cases
    // ------------------------------------------------------------------

    @Test
    void testNewStringIso8859_1() throws UnsupportedEncodingException {
        final String charsetName = StandardCharsets.ISO_8859_1.name();
        assertNewStringMatchesJdk(charsetName);
        final String expected = new String(BYTES_FIXTURE, charsetName);
        final String actual = StringUtils.newStringIso8859_1(BYTES_FIXTURE);
        assertEquals(expected, actual);
    }

    @Test
    void testNewStringUsAscii() throws UnsupportedEncodingException {
        final String charsetName = StandardCharsets.US_ASCII.name();
        assertNewStringMatchesJdk(charsetName);
        final String expected = new String(BYTES_FIXTURE, charsetName);
        final String actual = StringUtils.newStringUsAscii(BYTES_FIXTURE);
        assertEquals(expected, actual);
    }

    @Test
    void testNewStringUtf16() throws UnsupportedEncodingException {
        final String charsetName = StandardCharsets.UTF_16.name();
        assertNewStringMatchesJdk(charsetName);
        final String expected = new String(BYTES_FIXTURE, charsetName);
        final String actual = StringUtils.newStringUtf16(BYTES_FIXTURE);
        assertEquals(expected, actual);
    }

    @Test
    void testNewStringUtf16Be() throws UnsupportedEncodingException {
        final String charsetName = StandardCharsets.UTF_16BE.name();
        assertNewStringMatchesJdk(charsetName);
        // UTF-16BE needs two bytes per char, so decode the dedicated big-endian fixture.
        final String expected = new String(BYTES_FIXTURE_16BE, charsetName);
        final String actual = StringUtils.newStringUtf16Be(BYTES_FIXTURE_16BE);
        assertEquals(expected, actual);
    }

    @Test
    void testNewStringUtf16Le() throws UnsupportedEncodingException {
        final String charsetName = StandardCharsets.UTF_16LE.name();
        assertNewStringMatchesJdk(charsetName);
        // UTF-16LE needs two bytes per char, so decode the dedicated little-endian fixture.
        final String expected = new String(BYTES_FIXTURE_16LE, charsetName);
        final String actual = StringUtils.newStringUtf16Le(BYTES_FIXTURE_16LE);
        assertEquals(expected, actual);
    }

    @Test
    void testNewStringUtf8() throws UnsupportedEncodingException {
        final String charsetName = StandardCharsets.UTF_8.name();
        assertNewStringMatchesJdk(charsetName);
        final String expected = new String(BYTES_FIXTURE, charsetName);
        final String actual = StringUtils.newStringUtf8(BYTES_FIXTURE);
        assertEquals(expected, actual);
    }

    /** An unknown charset name must surface as an IllegalStateException, not a checked exception. */
    @Test
    void testNewStringBadEnc() {
        assertThrows(IllegalStateException.class, () -> StringUtils.newString(BYTES_FIXTURE, "UNKNOWN"));
    }

    /** A null byte array must yield a null result (null-safe), even for an unknown charset name. */
    @Test
    void testNewStringNullInput() {
        assertNull(StringUtils.newString(null, "UNKNOWN"));
    }

    /** Every charset-specific {@code newStringXxx} helper must be null-safe (see CODEC-229). */
    @Test
    void testNewStringNullInput_CODEC229() {
        assertNull(StringUtils.newStringUtf8(null));
        assertNull(StringUtils.newStringIso8859_1(null));
        assertNull(StringUtils.newStringUsAscii(null));
        assertNull(StringUtils.newStringUtf16(null));
        assertNull(StringUtils.newStringUtf16Be(null));
        assertNull(StringUtils.newStringUtf16Le(null));
    }
}
