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
 *
 * <p>
 * An {@link AlphabetConverter} maps every character of an <em>original</em> alphabet to a fixed-length
 * sequence of characters drawn from an <em>encoding</em> alphabet. Characters listed in the
 * <em>do-not-encode</em> set are passed through unchanged. The tests below exercise round-trip
 * encoding/decoding for several alphabet pairs as well as the construction-time and decoding-time
 * error conditions.
 * </p>
 */
class AlphabetConverterTest {

    // --- Alphabets used as building blocks for the converters under test. ---

    /** The space character plus the 26 lower-case English letters. */
    private static final Character[] LOWER_CASE_ENGLISH = { ' ', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's',
            't', 'u', 'v', 'w', 'x', 'y', 'z' };

    /** Digits, both letter cases of the English alphabet, and a space. */
    private static final Character[] ENGLISH_AND_NUMBERS = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j',
            'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z', 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M',
            'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', ' ' };

    /** Digits, the lower-case English alphabet, and a space. */
    private static final Character[] LOWER_CASE_ENGLISH_AND_NUMBERS = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f', 'g',
            'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z', ' ' };

    /** The ten decimal digits. */
    private static final Character[] NUMBERS = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9' };

    /** A two-symbol alphabet, useful for forcing multi-character encodings. */
    private static final Character[] BINARY = { '0', '1' };

    /**
     * A Hebrew alphabet (preceded by underscore and space separators). Expressed as Unicode code points
     * (U+05D0 .. U+05EA are the Hebrew letters) so the exact characters are unambiguous.
     */
    private static final Character[] HEBREW = { '_', ' ',
            (char) 0x05E7, (char) 0x05E8, (char) 0x05D0, (char) 0x05D8, (char) 0x05D5, (char) 0x05DF, (char) 0x05DD, (char) 0x05E4, (char) 0x05E9, (char) 0x05D3,
            (char) 0x05D2, (char) 0x05DB, (char) 0x05E2, (char) 0x05D9, (char) 0x05D7, (char) 0x05DC, (char) 0x05DA, (char) 0x05E3, (char) 0x05D6, (char) 0x05E1,
            (char) 0x05D1, (char) 0x05D4, (char) 0x05E0, (char) 0x05DE, (char) 0x05E6, (char) 0x05EA, (char) 0x05E5 };

    /** Original alphabet expressed as code points, mixing BMP and supplementary characters. */
    private static final Integer[] UNICODE = { 32, 35395, 35397, 36302, 36291, 35203, 35201, 35215, 35219, 35268, 97, 98, 99, 100, 101, 102, 103, 104, 105, 106,
            107, 108, 109, 110, 1001, 1002, 1003, 1004, 1005 };

    /** Lower-case English alphabet expressed as code points (space followed by a-z). */
    private static final Integer[] LOWER_CASE_ENGLISH_CODEPOINTS = { 32, 97, 98, 99, 100, 101, 102, 103, 104, 105, 106, 107, 108, 109, 110, 111, 112, 113, 114,
            115, 116, 117, 118, 119, 120, 121, 122 };

    /** Code points that should be passed through unencoded: space, a, b, c. */
    private static final Integer[] DO_NOT_ENCODE_CODEPOINTS = { 32, 97, 98, 99 };

    /**
     * Builds a string from the given Unicode code points. Used to spell out non-Latin sample text
     * unambiguously, without relying on the source file's character encoding.
     *
     * @param codePoints the code points making up the string.
     * @return the resulting string.
     */
    private static String fromCodePoints(final int... codePoints) {
        return new String(codePoints, 0, codePoints.length);
    }

    /**
     * Builds the converter from the class Javadoc's "Sample usage" example: original {@code a, b, c, d}
     * encoded with {@code 0, 1, d} while leaving {@code d} unencoded.
     *
     * @return the sample converter.
     */
    private AlphabetConverter createJavadocExample() {
        final Character[] original = { 'a', 'b', 'c', 'd' };
        final Character[] encoding = { '0', '1', 'd' };
        final Character[] doNotEncode = { 'd' };

        return AlphabetConverter.createConverterFromChars(original, encoding, doNotEncode);
    }

    /**
     * Verifies the full contract of a converter built from the given alphabets:
     * <ul>
     *   <li>it can be serialized to a map and reconstructed into an equal converter,</li>
     *   <li>it handles {@code null} and empty inputs, and</li>
     *   <li>every supplied string survives a round trip (encode then decode), using only the
     *       expected alphabets at each stage.</li>
     * </ul>
     *
     * @param originalChars     the original alphabet.
     * @param encodingChars     the alphabet used for the encoded form.
     * @param doNotEncodeChars  characters that must be passed through unchanged.
     * @param strings           sample strings to round-trip.
     */
    private void assertRoundTrips(final Character[] originalChars, final Character[] encodingChars, final Character[] doNotEncodeChars, final String... strings)
            throws UnsupportedEncodingException {

        final AlphabetConverter converter = AlphabetConverter.createConverterFromChars(originalChars, encodingChars, doNotEncodeChars);

        // A converter reconstructed from its serialized map must be indistinguishable from the original.
        final AlphabetConverter reconstructed = AlphabetConverter.createConverterFromMap(converter.getOriginalToEncoded());
        assertEquals(converter, reconstructed);
        assertEquals(converter.hashCode(), reconstructed.hashCode());
        assertEquals(converter.toString(), reconstructed.toString());

        assertNull(converter.encode(null), "encoding null should yield null");
        assertEquals("", converter.encode(""), "encoding the empty string should yield the empty string");

        for (final String original : strings) {
            final String encoded = converter.encode(original);

            // The encoded form may only contain characters from the encoding alphabet.
            final List<Character> allowedEncodingChars = Arrays.asList(encodingChars);
            for (int i = 0; i < encoded.length(); i++) {
                assertTrue(allowedEncodingChars.contains(encoded.charAt(i)));
            }

            final String decoded = converter.decode(encoded);

            // After decoding we must be back to characters from the original alphabet only.
            final List<Character> allowedOriginalChars = Arrays.asList(originalChars);
            for (int i = 0; i < decoded.length(); i++) {
                assertTrue(allowedOriginalChars.contains(decoded.charAt(i)));
            }

            // Decoding the encoded form must reproduce the input exactly.
            assertEquals(original, decoded, () -> "Encoded '" + original + "' into '" + encoded + "', but decoded into '" + decoded + "'");
        }
    }

    @Test
    void testBinaryTest() throws UnsupportedEncodingException {
        // Binary encoded as digits, digits encoded as binary, and letters encoded as binary.
        assertRoundTrips(BINARY, NUMBERS, ArrayUtils.EMPTY_CHARACTER_OBJECT_ARRAY, "0", "1", "10", "11");
        assertRoundTrips(NUMBERS, BINARY, ArrayUtils.EMPTY_CHARACTER_OBJECT_ARRAY, "12345", "0");
        assertRoundTrips(LOWER_CASE_ENGLISH, BINARY, ArrayUtils.EMPTY_CHARACTER_OBJECT_ARRAY, "abc", "a");
    }

    @Test
    void testCreateConverterFromCharsAndEquals() {
        // A single distinct character '+' used as the original, encoding and do-not-encode alphabet.
        final char plusSign = '+';
        final Character[] singleCharAlphabet = { plusSign, plusSign };
        final AlphabetConverter converter = AlphabetConverter.createConverterFromChars(singleCharAlphabet, singleCharAlphabet, singleCharAlphabet);

        // A converter is never equal to a bare Character.
        assertFalse(converter.equals(plusSign));
    }

    @Test
    void testCreateConverterFromCharsOne() {
        // With a single distinct original character, each character maps to one encoded character.
        final Character[] singleCharAlphabet = { '5', '5' };
        final AlphabetConverter converter = AlphabetConverter.createConverterFromChars(singleCharAlphabet, singleCharAlphabet, singleCharAlphabet);

        assertEquals(1, converter.getEncodedCharLength());
    }

    @Test
    void testCreateConverterFromCharsWithNullAndNull() {
        // A null encoding alphabet cannot be used to build a converter.
        final Character[] singleCharAlphabet = { '$', '$' };
        assertThrows(IllegalArgumentException.class,
                () -> AlphabetConverter.createConverterFromChars(singleCharAlphabet, null, null));
    }

    @Test
    void testCreateConverterFromMapAndEquals() {
        final Map<Integer, String> originalToEncoded = new HashMap<>();
        final AlphabetConverter fromEmptyMap = AlphabetConverter.createConverterFromMap(originalToEncoded);

        // Mutating the source map after the first converter is built yields a different converter.
        originalToEncoded.put(0, "CtDs");
        final AlphabetConverter fromPopulatedMap = AlphabetConverter.createConverterFromMap(originalToEncoded);

        assertFalse(fromEmptyMap.equals(fromPopulatedMap));
        assertEquals(1, fromEmptyMap.getEncodedCharLength());
    }

    @Test
    void testDecodeReturningNull() throws UnsupportedEncodingException {
        final AlphabetConverter converter = AlphabetConverter.createConverterFromMap(new HashMap<>());

        // Decoding null is a no-op that returns null; the converter remains a single-char-length converter.
        converter.decode(null);
        assertEquals(1, converter.getEncodedCharLength());
    }

    @Test
    void testDoNotEncodeTest() throws UnsupportedEncodingException {
        // Lower-case letters are left unencoded, so only the upper-case portions are converted.
        assertRoundTrips(ENGLISH_AND_NUMBERS, LOWER_CASE_ENGLISH_AND_NUMBERS, LOWER_CASE_ENGLISH,
                "1", "456", "abc", "ABC", "this will not be converted but THIS WILL");
        // Digits are left unencoded instead.
        assertRoundTrips(ENGLISH_AND_NUMBERS, LOWER_CASE_ENGLISH_AND_NUMBERS, NUMBERS,
                "1", "456", "abc", "ABC", "this will be converted but 12345 and this will be");
    }

    @Test
    void testEncodeFailureTest() {
        // '3' is not part of the BINARY original alphabet, so it cannot be encoded.
        final UnsupportedEncodingException thrown = assertThrows(UnsupportedEncodingException.class,
                () -> assertRoundTrips(BINARY, NUMBERS, ArrayUtils.EMPTY_CHARACTER_OBJECT_ARRAY, "3"));
        assertEquals("Couldn't find encoding for '3' in 3", thrown.getMessage());
    }

    @Test
    void testEquals() {
        // Converter built from a single 'R' character...
        final Character[] singleCharAlphabet = { 'R', 'R' };
        final AlphabetConverter fromChars = AlphabetConverter.createConverterFromChars(singleCharAlphabet, singleCharAlphabet, singleCharAlphabet);

        // ...is not equal to a converter built from an empty map.
        final AlphabetConverter fromEmptyMap = AlphabetConverter.createConverterFromMap(new HashMap<>());

        assertEquals(1, fromEmptyMap.getEncodedCharLength());
        assertFalse(fromChars.equals(fromEmptyMap));
    }

    @Test
    void testEqualsWithNull() {
        // A converter built from an empty alphabet is never equal to null.
        final AlphabetConverter converter = AlphabetConverter.createConverterFromChars(ArrayUtils.EMPTY_CHARACTER_OBJECT_ARRAY, null, null);

        assertFalse(converter.equals(null));
    }

    @Test
    void testEqualsWithSameObject() {
        // A converter is always equal to itself (reflexivity).
        final Character[] singleCharAlphabet = { 'R', 'R' };
        final AlphabetConverter converter = AlphabetConverter.createConverterFromChars(singleCharAlphabet, singleCharAlphabet, singleCharAlphabet);

        assertTrue(converter.equals(converter));
    }

    @Test
    void testHebrewTest() throws UnsupportedEncodingException {
        // Hebrew round-trips through both a binary and a numeric encoding alphabet, and vice versa.
        final String hebrewAleph = fromCodePoints(0x05D0);
        final String hebrewAyin = fromCodePoints(0x05E2);
        final String hebrewSentence = fromCodePoints(
                0x05D0, 0x05DC, 0x05E3, '_',
                0x05D0, 0x05D5, 0x05D4, 0x05D1, 0x05DC, '_',
                0x05D1, 0x05D9, 0x05EA, '_',
                0x05D6, 0x05D4, '_',
                0x05D1, 0x05D9, 0x05EA, '_',
                0x05D2, 0x05D9, 0x05DE, 0x05DC, '_',
                0x05D6, 0x05D4, '_',
                0x05DB, 0x05DE, 0x05DC, '_',
                0x05D2, 0x05D3, 0x05D5, 0x05DC);

        assertRoundTrips(HEBREW, BINARY, ArrayUtils.EMPTY_CHARACTER_OBJECT_ARRAY, hebrewAleph, hebrewAyin, hebrewSentence);
        assertRoundTrips(HEBREW, NUMBERS, ArrayUtils.EMPTY_CHARACTER_OBJECT_ARRAY, hebrewAleph, hebrewAyin, hebrewSentence);
        assertRoundTrips(NUMBERS, HEBREW, ArrayUtils.EMPTY_CHARACTER_OBJECT_ARRAY, "123456789", "1", "5");
        assertRoundTrips(LOWER_CASE_ENGLISH, HEBREW, ArrayUtils.EMPTY_CHARACTER_OBJECT_ARRAY, "this is a test");
    }

    /**
     * Confirms the encodings shown in the class Javadoc's "Sample usage" block.
     */
    @Test
    void testJavadocExampleTest() throws UnsupportedEncodingException {
        final AlphabetConverter ac = createJavadocExample();

        assertEquals("00", ac.encode("a"));
        assertEquals("01", ac.encode("b"));
        assertEquals("0d", ac.encode("c"));
        assertEquals("d", ac.encode("d"));
        assertEquals("00010dd", ac.encode("abcd"));
    }

    @Test
    void testMissingDoNotEncodeLettersFromEncodingTest() {
        // '0' is in the do-not-encode list but absent from the (lower-case) encoding alphabet.
        final IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class,
                () -> AlphabetConverter.createConverterFromChars(ENGLISH_AND_NUMBERS, LOWER_CASE_ENGLISH, NUMBERS));
        assertEquals("Can not use 'do not encode' list because encoding alphabet does not contain '0'", thrown.getMessage());
    }

    @Test
    void testMissingDoNotEncodeLettersFromOriginalTest() {
        // '0' is in the do-not-encode list but absent from the (lower-case) original alphabet.
        final IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class,
                () -> AlphabetConverter.createConverterFromChars(LOWER_CASE_ENGLISH, ENGLISH_AND_NUMBERS, NUMBERS));
        assertEquals("Can not use 'do not encode' list because original alphabet does not contain '0'", thrown.getMessage());
    }

    @Test
    void testNoEncodingLettersTest() {
        // Every encoding character is also in the do-not-encode list, leaving zero usable encoding characters.
        final IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class,
                () -> AlphabetConverter.createConverterFromChars(ENGLISH_AND_NUMBERS, NUMBERS, NUMBERS));
        assertEquals("Must have at least two encoding characters (excluding those in the 'do not encode' list), but has 0", thrown.getMessage());
    }

    @Test
    void testOnlyOneEncodingLettersTest() {
        // The encoding alphabet is the digits plus '_'; all digits are do-not-encode, leaving only '_'.
        final IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () -> {
            final Character[] numbersPlusUnderscore = Arrays.copyOf(NUMBERS, NUMBERS.length + 1);
            numbersPlusUnderscore[numbersPlusUnderscore.length - 1] = '_';

            AlphabetConverter.createConverterFromChars(ENGLISH_AND_NUMBERS, numbersPlusUnderscore, NUMBERS);
        });
        assertEquals("Must have at least two encoding characters (excluding those in the 'do not encode' list), but has 1", thrown.getMessage());
    }

    @Test
    void testUnexpectedEndWhileDecodingTest() {
        // The trailing '0' is an incomplete encoded group (each group is two characters long).
        final String toDecode = "00d01d0";
        final UnsupportedEncodingException thrown = assertThrows(UnsupportedEncodingException.class,
                () -> createJavadocExample().decode(toDecode));
        assertEquals("Unexpected end of string while decoding " + toDecode, thrown.getMessage());
    }

    @Test
    void testUnexpectedStringWhileDecodingTest() {
        // "XX" is not a known encoded group for the Javadoc-example converter.
        final String toDecode = "00XX";
        final UnsupportedEncodingException thrown = assertThrows(UnsupportedEncodingException.class,
                () -> createJavadocExample().decode(toDecode));
        assertEquals("Unexpected string without decoding (XX) in " + toDecode, thrown.getMessage());
    }

    /**
     * Exercises construction from code points (including supplementary characters) rather than chars.
     */
    @Test
    void testUnicodeTest() throws UnsupportedEncodingException {
        final AlphabetConverter ac = AlphabetConverter.createConverter(UNICODE, LOWER_CASE_ENGLISH_CODEPOINTS, DO_NOT_ENCODE_CODEPOINTS);
        assertEquals(2, ac.getEncodedCharLength());

        final String original = "詃詅 跎 ab 跃 c 覃";
        final String encoded = ac.encode(original);
        final String decoded = ac.decode(encoded);
        assertEquals(original, decoded, () -> "Encoded '" + original + "' into '" + encoded + "', but decoded into '" + decoded + "'");
    }

}
