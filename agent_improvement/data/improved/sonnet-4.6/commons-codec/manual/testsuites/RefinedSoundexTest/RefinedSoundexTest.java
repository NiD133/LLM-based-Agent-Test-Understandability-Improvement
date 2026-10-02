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

package org.apache.commons.codec.language;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.apache.commons.codec.AbstractStringEncoderTest;
import org.apache.commons.codec.EncoderException;
import org.junit.jupiter.api.Test;

/**
 * Tests {@link RefinedSoundex}.
 *
 * <p>RefinedSoundex is a phonetic encoding algorithm optimised for spell-checking.
 * It maps each letter to a digit and collapses consecutive identical digits, so
 * words that sound alike produce the same (or very similar) codes.</p>
 */
class RefinedSoundexTest extends AbstractStringEncoderTest<RefinedSoundex> {

    @Override
    protected RefinedSoundex createStringEncoder() {
        return new RefinedSoundex();
    }

    /**
     * Verifies that {@code difference()} returns a phonetic similarity score
     * between two strings.  The score ranges from 0 (no similarity) to the
     * length of the shorter encoded string (strong similarity / identical).
     */
    @Test
    void testDifference() throws EncoderException {
        // Blank / null inputs produce a score of 0 (nothing to compare)
        assertEquals(0, getStringEncoder().difference(null, null));
        assertEquals(0, getStringEncoder().difference("", ""));
        assertEquals(0, getStringEncoder().difference(" ", " "));

        // General name comparisons
        assertEquals(6, getStringEncoder().difference("Smith", "Smythe"));
        assertEquals(3, getStringEncoder().difference("Ann", "Andrew"));
        assertEquals(1, getStringEncoder().difference("Margaret", "Andrew"));
        assertEquals(1, getStringEncoder().difference("Janet", "Margaret"));

        // Examples from MS T-SQL DIFFERENCE documentation
        // https://msdn.microsoft.com/library/default.asp?url=/library/en-us/tsqlref/ts_de-dz_8co5.asp
        assertEquals(5, getStringEncoder().difference("Green", "Greene"));
        assertEquals(1, getStringEncoder().difference("Blotchet-Halls", "Greene"));

        // Examples from MS T-SQL SOUNDEX documentation
        // https://msdn.microsoft.com/library/default.asp?url=/library/en-us/tsqlref/ts_setu-sus_3o6w.asp
        assertEquals(6, getStringEncoder().difference("Smith", "Smythe"));
        assertEquals(8, getStringEncoder().difference("Smithers", "Smythers"));
        assertEquals(5, getStringEncoder().difference("Anothers", "Brothers"));
    }

    /**
     * Verifies that {@code encode()} produces the correct Refined Soundex code.
     *
     * <p>Each word of the phrase "The quick brown fox jumped over the lazy dogs"
     * is encoded individually to confirm correct phonetic mapping.</p>
     */
    @Test
    void testEncode() {
        // Case-insensitive: lower and upper case must yield the same code
        assertEquals("T6036084", getStringEncoder().encode("testing"));
        assertEquals("T6036084", getStringEncoder().encode("TESTING"));

        // Words from the classic pangram "The quick brown fox jumped over the lazy dogs"
        assertEquals("T60",      getStringEncoder().encode("The"));
        assertEquals("Q503",     getStringEncoder().encode("quick"));
        assertEquals("B1908",    getStringEncoder().encode("brown"));
        assertEquals("F205",     getStringEncoder().encode("fox"));
        assertEquals("J408106",  getStringEncoder().encode("jumped"));
        assertEquals("O0209",    getStringEncoder().encode("over"));
        assertEquals("T60",      getStringEncoder().encode("the"));
        assertEquals("L7050",    getStringEncoder().encode("lazy"));
        assertEquals("D6043",    getStringEncoder().encode("dogs"));

        // CODEC-56: static US_ENGLISH instance must produce the same result as a fresh instance
        assertEquals("D6043", RefinedSoundex.US_ENGLISH.encode("dogs"));
    }

    /**
     * Verifies that {@code getMappingCode()} returns {@code 0} for any character
     * that is not a letter, because non-letters have no phonetic mapping.
     */
    @Test
    void testGetMappingCodeNonLetter() {
        final char code = getStringEncoder().getMappingCode('#');
        assertEquals(0, code, "Non-letter character '#' should map to code 0");
    }

    /**
     * Verifies that encoding a string containing all 256 ASCII characters does
     * not throw and produces the expected output.  Characters outside the A-Z
     * alphabet (digits, symbols, control characters) are silently ignored by the
     * algorithm, so only the letter portions contribute to the encoded result.
     */
    @Test
    void testInvalidSoundexCharacter() {
        final char[] allAsciiChars = new char[256];
        for (int i = 0; i < allAsciiChars.length; i++) {
            allAsciiChars[i] = (char) i;
        }

        assertEquals(
            "A0136024043780159360205050136024043780159360205053",
            new RefinedSoundex().encode(new String(allAsciiChars))
        );
    }

    /**
     * Verifies that the no-argument constructor creates a working encoder
     * using the default US-English mapping.
     */
    @Test
    void testDefaultConstructorProducesCorrectEncoding() {
        assertEquals("D6043", new RefinedSoundex().soundex("dogs"));
    }

    /**
     * Verifies that the {@code char[]} constructor accepts a custom mapping
     * array and produces the same result as the default encoder when given the
     * standard US-English mapping.
     */
    @Test
    void testCharArrayMappingConstructorProducesCorrectEncoding() {
        assertEquals("D6043", new RefinedSoundex(RefinedSoundex.US_ENGLISH_MAPPING_STRING.toCharArray()).soundex("dogs"));
    }

    /**
     * Verifies that the {@code String} constructor accepts a custom mapping
     * string and produces the same result as the default encoder when given the
     * standard US-English mapping.
     */
    @Test
    void testStringMappingConstructorProducesCorrectEncoding() {
        assertEquals("D6043", new RefinedSoundex(RefinedSoundex.US_ENGLISH_MAPPING_STRING).soundex("dogs"));
    }
}
