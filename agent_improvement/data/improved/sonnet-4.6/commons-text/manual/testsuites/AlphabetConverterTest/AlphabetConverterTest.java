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
package org.apache.commons.text;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.ArrayUtils;
import org.junit.jupiter.api.Test;

/**
 * Tests {@link AlphabetConverter}.
 */
class AlphabetConverterTest {

    // -------------------------------------------------------------------------
    // Shared alphabets used across multiple tests
    // -------------------------------------------------------------------------

    private static final Character[] LOWER_CASE_ENGLISH = {
        ' ', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l',
        'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z'
    };

    private static final Character[] ENGLISH_AND_NUMBERS = {
        '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
        'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm',
        'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z',
        'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M',
        'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', ' '
    };

    private static final Character[] LOWER_CASE_ENGLISH_AND_NUMBERS = {
        '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
        'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm',
        'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z', ' '
    };

    private static final Character[] NUMBERS = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9' };

    private static final Character[] BINARY = { '0', '1' };

    private static final Character[] HEBREW = {
        '_', ' ', 'ק', 'ר', 'א', 'ט', 'ו', 'ן',
        'ם', 'פ', 'ש', 'ד', 'ג', 'כ', 'ע',
        'י', 'ח', 'ל', 'ך', 'ף', 'ז', 'ס',
        'ב', 'ה', 'נ', 'מ', 'צ', 'ת', 'ץ'
    };

    // Unicode code-point alphabets used by testUnicodeTest
    private static final Integer[] UNICODE = {
        32, 35395, 35397, 36302, 36291, 35203, 35201, 35215, 35219, 35268,
        97, 98, 99, 100, 101, 102, 103, 104, 105, 106,
        107, 108, 109, 110, 1001, 1002, 1003, 1004, 1005
    };

    private static final Integer[] LOWER_CASE_ENGLISH_CODEPOINTS = {
        32, 97, 98, 99, 100, 101, 102, 103, 104, 105, 106, 107, 108, 109,
        110, 111, 112, 113, 114, 115, 116, 117, 118, 119, 120, 121, 122
    };

    private static final Integer[] DO_NOT_ENCODE_CODEPOINTS = { 32, 97, 98, 99 }; // space, a, b, c

    // -------------------------------------------------------------------------
    // Helper methods
    // -------------------------------------------------------------------------

    /**
     * Creates the converter described in the AlphabetConverter Javadoc example:
     * original = {a, b, c, d}, encoding = {0, 1, d}, doNotEncode = {d}.
     */
    private AlphabetConverter createJavadocExample() {
        final Character[] original    = { 'a', 'b', 'c', 'd' };
        final Character[] encoding    = { '0', '1', 'd' };
        final Character[] doNotEncode = { 'd' };
        return AlphabetConverter.createConverterFromChars(original, encoding, doNotEncode);
    }

    /**
     * Round-trip helper: builds a converter from the given alphabets, then for every
     * trial string verifies that:
     * <ul>
     *   <li>encode(null) returns null and encode("") returns ""</li>
     *   <li>a converter reconstructed from its own map compares equal to the original</li>
     *   <li>every character in the encoded form belongs to {@code encodingChars}</li>
     *   <li>every character after decoding belongs to {@code originalChars}</li>
     *   <li>decode(encode(s)) equals s for each trial string s</li>
     * </ul>
     */
    private void test(final Character[] originalChars, final Character[] encodingChars,
            final Character[] doNotEncodeChars, final String... strings)
            throws UnsupportedEncodingException {

        final AlphabetConverter ac = AlphabetConverter.createConverterFromChars(
                originalChars, encodingChars, doNotEncodeChars);

        final AlphabetConverter reconstructedAlphabetConverter =
                AlphabetConverter.createConverterFromMap(ac.getOriginalToEncoded());

        assertEquals(ac, reconstructedAlphabetConverter);
        assertEquals(ac.hashCode(), reconstructedAlphabetConverter.hashCode());
        assertEquals(ac.toString(), reconstructedAlphabetConverter.toString());
        assertNull(ac.encode(null));     // null input must produce null output
        assertEquals("", ac.encode("")); // empty input must produce empty output

        final List<Character> originalEncodingChars = Arrays.asList(encodingChars);
        final List<Character> originalCharsList     = Arrays.asList(originalChars);

        for (final String s : strings) {
            final String encoded = ac.encode(s);

            // Every character in the encoded string must belong to the encoding alphabet.
            for (int i = 0; i < encoded.length(); i++) {
                assertTrue(originalEncodingChars.contains(encoded.charAt(i)));
            }

            final String decoded = ac.decode(encoded);

            // Every character after decoding must belong to the original alphabet.
            for (int i = 0; i < decoded.length(); i++) {
                assertTrue(originalCharsList.contains(decoded.charAt(i)));
            }

            assertEquals(s, decoded,
                    () -> "Encoded '" + s + "' into '" + encoded + "', but decoded into '" + decoded + "'");
        }
    }

    // -------------------------------------------------------------------------
    // Encoding / decoding round-trip tests
    // -------------------------------------------------------------------------

    @Test
    void testBinaryTest() throws UnsupportedEncodingException {
        test(BINARY, NUMBERS, ArrayUtils.EMPTY_CHARACTER_OBJECT_ARRAY, "0", "1", "10", "11");
        test(NUMBERS, BINARY, ArrayUtils.EMPTY_CHARACTER_OBJECT_ARRAY, "12345", "0");
        test(LOWER_CASE_ENGLISH, BINARY, ArrayUtils.EMPTY_CHARACTER_OBJECT_ARRAY, "abc", "a");
    }

    @Test
    void testDoNotEncodeTest() throws UnsupportedEncodingException {
        // Lowercase letters are in the doNotEncode list: they pass through unchanged; uppercase and digits are re-encoded.
        test(ENGLISH_AND_NUMBERS, LOWER_CASE_ENGLISH_AND_NUMBERS, LOWER_CASE_ENGLISH,
                "1", "456", "abc", "ABC", "this will not be converted but THIS WILL");
        // Digits are in the doNotEncode list: they pass through unchanged; letters are re-encoded.
        test(ENGLISH_AND_NUMBERS, LOWER_CASE_ENGLISH_AND_NUMBERS, NUMBERS,
                "1", "456", "abc", "ABC", "this will be converted but 12345 and this will be");
    }

    @Test
    void testHebrewTest() throws UnsupportedEncodingException {
        test(HEBREW, BINARY, ArrayUtils.EMPTY_CHARACTER_OBJECT_ARRAY,
                "א", "ע",
                "אלף_אוהבל_בית_זה_בית_"
              + "גימל_זה_כמל_גדול");
        test(HEBREW, NUMBERS, ArrayUtils.EMPTY_CHARACTER_OBJECT_ARRAY,
                "א", "ע",
                "אלף_אוהבל_בית_זה_בית_"
              + "גימל_זה_כמל_גדול");
        test(NUMBERS, HEBREW, ArrayUtils.EMPTY_CHARACTER_OBJECT_ARRAY, "123456789", "1", "5");
        test(LOWER_CASE_ENGLISH, HEBREW, ArrayUtils.EMPTY_CHARACTER_OBJECT_ARRAY, "this is a test");
    }

    /*
     * Test example in javadocs for consistency
     */
    @Test
    void testJavadocExampleTest() throws UnsupportedEncodingException {
        final AlphabetConverter ac = createJavadocExample();

        assertEquals("00",      ac.encode("a"));
        assertEquals("01",      ac.encode("b"));
        assertEquals("0d",      ac.encode("c"));
        assertEquals("d",       ac.encode("d")); // 'd' is in doNotEncode, so it passes through as-is
        assertEquals("00010dd", ac.encode("abcd"));
    }

    /**
     * Test constructor from code points
     */
    @Test
    void testUnicodeTest() throws UnsupportedEncodingException {
        final AlphabetConverter ac = AlphabetConverter.createConverter(
                UNICODE, LOWER_CASE_ENGLISH_CODEPOINTS, DO_NOT_ENCODE_CODEPOINTS);

        assertEquals(2, ac.getEncodedCharLength());

        final String original = "詃詅 跎 ab 跃 c 覃";
        final String encoded  = ac.encode(original);
        final String decoded  = ac.decode(encoded);

        assertEquals(original, decoded,
                () -> "Encoded '" + original + "' into '" + encoded + "', but decoded into '" + decoded + "'");
    }

    // -------------------------------------------------------------------------
    // Encode / decode error-condition tests
    // -------------------------------------------------------------------------

    @Test
    void testEncodeFailureTest() {
        // '3' is not in the BINARY original alphabet, so encoding must throw.
        assertEquals("Couldn't find encoding for '3' in 3",
                assertThrows(UnsupportedEncodingException.class,
                        () -> test(BINARY, NUMBERS, ArrayUtils.EMPTY_CHARACTER_OBJECT_ARRAY, "3"))
                        .getMessage());
    }

    @Test
    void testUnexpectedEndWhileDecodingTest() {
        // "00d01d0" is one token short; the decoder cannot complete the last encoded group.
        final String toDecode = "00d01d0";
        assertEquals("Unexpected end of string while decoding " + toDecode,
                assertThrows(UnsupportedEncodingException.class,
                        () -> createJavadocExample().decode(toDecode))
                        .getMessage());
    }

    @Test
    void testUnexpectedStringWhileDecodingTest() {
        // "XX" is not a valid encoded sequence produced by this converter.
        final String toDecode = "00XX";
        assertEquals("Unexpected string without decoding (XX) in " + toDecode,
                assertThrows(UnsupportedEncodingException.class,
                        () -> createJavadocExample().decode(toDecode))
                        .getMessage());
    }

    // -------------------------------------------------------------------------
    // Factory validation: illegal-argument cases
    // -------------------------------------------------------------------------

    @Test
    void testCreateConverterFromCharsWithNullAndNull() {
        // A null encoding alphabet must cause an IllegalArgumentException.
        assertThrows(IllegalArgumentException.class, () -> {
            final Character[] characterArray = new Character[2];
            characterArray[0] = '$';
            characterArray[1] = characterArray[0];
            AlphabetConverter.createConverterFromChars(characterArray, null, null);
        });
    }

    @Test
    void testMissingDoNotEncodeLettersFromEncodingTest() {
        // '0' is in doNotEncode (NUMBERS) but absent from the encoding alphabet (LOWER_CASE_ENGLISH).
        assertEquals("Can not use 'do not encode' list because encoding alphabet does not contain '0'",
                assertThrows(IllegalArgumentException.class,
                        () -> AlphabetConverter.createConverterFromChars(
                                ENGLISH_AND_NUMBERS, LOWER_CASE_ENGLISH, NUMBERS))
                        .getMessage());
    }

    @Test
    void testMissingDoNotEncodeLettersFromOriginalTest() {
        // '0' is in doNotEncode (NUMBERS) but absent from the original alphabet (LOWER_CASE_ENGLISH).
        assertEquals("Can not use 'do not encode' list because original alphabet does not contain '0'",
                assertThrows(IllegalArgumentException.class,
                        () -> AlphabetConverter.createConverterFromChars(
                                LOWER_CASE_ENGLISH, ENGLISH_AND_NUMBERS, NUMBERS))
                        .getMessage());
    }

    @Test
    void testNoEncodingLettersTest() {
        // Every element of NUMBERS is in the doNotEncode list, leaving zero usable encoding characters.
        assertEquals("Must have at least two encoding characters (excluding those in the 'do not encode' list), but has 0",
                assertThrows(IllegalArgumentException.class,
                        () -> AlphabetConverter.createConverterFromChars(
                                ENGLISH_AND_NUMBERS, NUMBERS, NUMBERS))
                        .getMessage());
    }

    @Test
    void testOnlyOneEncodingLettersTest() {
        // NUMBERS + '_' as encoding, with NUMBERS as doNotEncode, leaves only '_': too few for multi-char encoding.
        assertEquals("Must have at least two encoding characters (excluding those in the 'do not encode' list), but has 1",
                assertThrows(IllegalArgumentException.class, () -> {
                    final Character[] numbersPlusUnderscore = Arrays.copyOf(NUMBERS, NUMBERS.length + 1);
                    numbersPlusUnderscore[numbersPlusUnderscore.length - 1] = '_';
                    AlphabetConverter.createConverterFromChars(ENGLISH_AND_NUMBERS, numbersPlusUnderscore, NUMBERS);
                }).getMessage());
    }

    // -------------------------------------------------------------------------
    // equals / hashCode / toString contract tests
    // -------------------------------------------------------------------------

    @Test
    void testCreateConverterFromCharsAndEquals() {
        // equals() must return false when compared against a non-AlphabetConverter object.
        final Character[] characterArray = new Character[2];
        final char charOne = '+';
        final char character = '+';
        characterArray[0] = character;
        characterArray[1] = characterArray[0];
        final AlphabetConverter alphabetConverter = AlphabetConverter.createConverterFromChars(
                characterArray, characterArray, characterArray);

        assertFalse(alphabetConverter.equals(charOne));
    }

    @Test
    void testCreateConverterFromCharsOne() {
        // When original, encoding, and doNotEncode all collapse to the same single character,
        // the encoded character length must be 1.
        final Character[] characterArray = new Character[2];
        characterArray[0] = '5';
        characterArray[1] = characterArray[0];
        final AlphabetConverter alphabetConverter = AlphabetConverter.createConverterFromChars(
                characterArray, characterArray, characterArray);

        assertEquals(1, alphabetConverter.getEncodedCharLength());
    }

    @Test
    void testCreateConverterFromMapAndEquals() {
        // Two converters built from maps with different contents must not be equal.
        final Map<Integer, String> hashMap = new HashMap<>();
        final AlphabetConverter alphabetConverter = AlphabetConverter.createConverterFromMap(hashMap);

        hashMap.put(0, "CtDs");
        final AlphabetConverter alphabetConverterTwo = AlphabetConverter.createConverterFromMap(hashMap);

        assertFalse(alphabetConverter.equals(alphabetConverterTwo));
        assertEquals(1, alphabetConverter.getEncodedCharLength());
    }

    @Test
    void testDecodeReturningNull() throws UnsupportedEncodingException {
        // decode(null) must not throw; the converter must still report encodedCharLength == 1.
        final Map<Integer, String> map = new HashMap<>();
        final AlphabetConverter alphabetConverter = AlphabetConverter.createConverterFromMap(map);

        alphabetConverter.decode(null);

        assertEquals(1, alphabetConverter.getEncodedCharLength());
    }

    @Test
    void testEquals() {
        // A converter built from chars and one built from an empty map must not be equal.
        final Character[] characterArray = new Character[2];
        final char character = 'R';
        characterArray[0] = character;
        characterArray[1] = character;
        final AlphabetConverter alphabetConverter = AlphabetConverter.createConverterFromChars(
                characterArray, characterArray, characterArray);

        final Map<Integer, String> map = new HashMap<>();
        final AlphabetConverter alphabetConverterTwo = AlphabetConverter.createConverterFromMap(map);

        assertEquals(1, alphabetConverterTwo.getEncodedCharLength());
        assertFalse(alphabetConverter.equals(alphabetConverterTwo));
    }

    @Test
    void testEqualsWithNull() {
        // equals(null) must return false per the Object.equals contract.
        final Character[] characterArray = ArrayUtils.EMPTY_CHARACTER_OBJECT_ARRAY;
        final AlphabetConverter alphabetConverter = AlphabetConverter.createConverterFromChars(
                characterArray, null, null);

        assertFalse(alphabetConverter.equals(null));
    }

    @Test
    void testEqualsWithSameObject() {
        // equals(this) must return true per the Object.equals reflexivity contract.
        final Character[] characterArray = new Character[2];
        final char character = 'R';
        characterArray[0] = character;
        characterArray[1] = character;
        final AlphabetConverter alphabetConverter = AlphabetConverter.createConverterFromChars(
                characterArray, characterArray, characterArray);

        assertTrue(alphabetConverter.equals(alphabetConverter));
    }
}
