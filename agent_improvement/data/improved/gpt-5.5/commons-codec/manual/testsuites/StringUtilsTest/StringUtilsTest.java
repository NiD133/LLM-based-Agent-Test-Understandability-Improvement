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
 */
class StringUtilsTest {

    private static final byte[] BYTES_FIXTURE = { 'a', 'b', 'c' };

    // This is valid input for UTF-16BE.
    private static final byte[] BYTES_FIXTURE_16BE = { 0, 'a', 0, 'b', 0, 'c' };

    // This is valid input for UTF-16LE.
    private static final byte[] BYTES_FIXTURE_16LE = { 'a', 0, 'b', 0, 'c', 0 };

    private static final String STRING_FIXTURE = "ABC";

    @Test
    void testByteBufferUtf8() {
        assertNull(StringUtils.getByteBufferUtf8(null), "Should be null safe");

        final String text = "asdhjfhsadiogasdjhagsdygfjasfgsdaksjdhfk";
        final ByteBuffer byteBuffer = StringUtils.getByteBufferUtf8(text);

        assertArrayEquals(text.getBytes(StandardCharsets.UTF_8), byteBuffer.array());
    }

    /**
     * We could make the constructor private but there does not seem to be a point to jumping through extra code hoops
     * to restrict instantiation right now.
     */
    @Test
    void testConstructor() {
        new StringUtils();
    }

    @Test
    void testEqualsCS1() {
        assertFalse(StringUtils.equals(new StringBuilder("abc"), null));
        assertFalse(StringUtils.equals(null, new StringBuilder("abc")));
        assertTrue(StringUtils.equals(new StringBuilder("abc"), new StringBuilder("abc")));
        assertFalse(StringUtils.equals(new StringBuilder("abc"), new StringBuilder("abcd")));
        assertFalse(StringUtils.equals(new StringBuilder("abcd"), new StringBuilder("abc")));
        assertFalse(StringUtils.equals(new StringBuilder("abc"), new StringBuilder("ABC")));
    }

    @Test
    void testEqualsCS2() {
        assertTrue(StringUtils.equals("abc", new StringBuilder("abc")));
        assertFalse(StringUtils.equals(new StringBuilder("abc"), "abcd"));
        assertFalse(StringUtils.equals("abcd", new StringBuilder("abc")));
        assertFalse(StringUtils.equals(new StringBuilder("abc"), "ABC"));
    }

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

    @Test
    void testGetBytesIso8859_1() throws UnsupportedEncodingException {
        final String charsetName = StandardCharsets.ISO_8859_1.name();

        assertGetBytesUncheckedMatchesJdk(charsetName);
        assertArrayEquals(STRING_FIXTURE.getBytes(charsetName), StringUtils.getBytesIso8859_1(STRING_FIXTURE));
    }

    @Test
    void testGetBytesUncheckedBadName() {
        assertThrows(IllegalStateException.class, () -> StringUtils.getBytesUnchecked(STRING_FIXTURE, "UNKNOWN"));
    }

    @Test
    void testGetBytesUncheckedNullInput() {
        assertNull(StringUtils.getBytesUnchecked(null, "UNKNOWN"));
    }

    @Test
    void testGetBytesUsAscii() throws UnsupportedEncodingException {
        final String charsetName = StandardCharsets.US_ASCII.name();

        assertGetBytesUncheckedMatchesJdk(charsetName);
        assertArrayEquals(STRING_FIXTURE.getBytes(charsetName), StringUtils.getBytesUsAscii(STRING_FIXTURE));
    }

    @Test
    void testGetBytesUtf16() throws UnsupportedEncodingException {
        final String charsetName = StandardCharsets.UTF_16.name();

        assertGetBytesUncheckedMatchesJdk(charsetName);
        assertArrayEquals(STRING_FIXTURE.getBytes(charsetName), StringUtils.getBytesUtf16(STRING_FIXTURE));
    }

    @Test
    void testGetBytesUtf16Be() throws UnsupportedEncodingException {
        final String charsetName = StandardCharsets.UTF_16BE.name();

        assertGetBytesUncheckedMatchesJdk(charsetName);
        assertArrayEquals(STRING_FIXTURE.getBytes(charsetName), StringUtils.getBytesUtf16Be(STRING_FIXTURE));
    }

    @Test
    void testGetBytesUtf16Le() throws UnsupportedEncodingException {
        final String charsetName = StandardCharsets.UTF_16LE.name();

        assertGetBytesUncheckedMatchesJdk(charsetName);
        assertArrayEquals(STRING_FIXTURE.getBytes(charsetName), StringUtils.getBytesUtf16Le(STRING_FIXTURE));
    }

    @Test
    void testGetBytesUtf8() throws UnsupportedEncodingException {
        final String charsetName = StandardCharsets.UTF_8.name();

        assertGetBytesUncheckedMatchesJdk(charsetName);
        assertArrayEquals(STRING_FIXTURE.getBytes(charsetName), StringUtils.getBytesUtf8(STRING_FIXTURE));
    }

    @Test
    void testNewStringBadEnc() {
        assertThrows(IllegalStateException.class, () -> StringUtils.newString(BYTES_FIXTURE, "UNKNOWN"));
    }

    @Test
    void testNewStringIso8859_1() throws UnsupportedEncodingException {
        final String charsetName = StandardCharsets.ISO_8859_1.name();

        assertNewStringMatchesJdk(BYTES_FIXTURE, charsetName);
        assertEquals(new String(BYTES_FIXTURE, charsetName), StringUtils.newStringIso8859_1(BYTES_FIXTURE));
    }

    @Test
    void testNewStringNullInput() {
        assertNull(StringUtils.newString(null, "UNKNOWN"));
    }

    @Test
    void testNewStringNullInput_CODEC229() {
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

        assertNewStringMatchesJdk(BYTES_FIXTURE, charsetName);
        assertEquals(new String(BYTES_FIXTURE, charsetName), StringUtils.newStringUsAscii(BYTES_FIXTURE));
    }

    @Test
    void testNewStringUtf16() throws UnsupportedEncodingException {
        final String charsetName = StandardCharsets.UTF_16.name();

        assertNewStringMatchesJdk(BYTES_FIXTURE, charsetName);
        assertEquals(new String(BYTES_FIXTURE, charsetName), StringUtils.newStringUtf16(BYTES_FIXTURE));
    }

    @Test
    void testNewStringUtf16Be() throws UnsupportedEncodingException {
        final String charsetName = StandardCharsets.UTF_16BE.name();

        assertNewStringMatchesJdk(BYTES_FIXTURE, charsetName);
        assertEquals(new String(BYTES_FIXTURE_16BE, charsetName), StringUtils.newStringUtf16Be(BYTES_FIXTURE_16BE));
    }

    @Test
    void testNewStringUtf16Le() throws UnsupportedEncodingException {
        final String charsetName = StandardCharsets.UTF_16LE.name();

        assertNewStringMatchesJdk(BYTES_FIXTURE, charsetName);
        assertEquals(new String(BYTES_FIXTURE_16LE, charsetName), StringUtils.newStringUtf16Le(BYTES_FIXTURE_16LE));
    }

    @Test
    void testNewStringUtf8() throws UnsupportedEncodingException {
        final String charsetName = StandardCharsets.UTF_8.name();

        assertNewStringMatchesJdk(BYTES_FIXTURE, charsetName);
        assertEquals(new String(BYTES_FIXTURE, charsetName), StringUtils.newStringUtf8(BYTES_FIXTURE));
    }

    private void assertGetBytesUncheckedMatchesJdk(final String charsetName) throws UnsupportedEncodingException {
        assertArrayEquals(STRING_FIXTURE.getBytes(charsetName), StringUtils.getBytesUnchecked(STRING_FIXTURE, charsetName));
    }

    private void assertNewStringMatchesJdk(final byte[] bytes, final String charsetName) throws UnsupportedEncodingException {
        assertEquals(new String(bytes, charsetName), StringUtils.newString(bytes, charsetName));
    }
}
