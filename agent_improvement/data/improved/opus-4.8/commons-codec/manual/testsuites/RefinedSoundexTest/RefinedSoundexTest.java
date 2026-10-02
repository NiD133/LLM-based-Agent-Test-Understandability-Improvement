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
 */
class RefinedSoundexTest extends AbstractStringEncoderTest<RefinedSoundex> {

    @Override
    protected RefinedSoundex createStringEncoder() {
        return new RefinedSoundex();
    }

    /**
     * {@code difference} returns how many characters of the two encoded words match,
     * so a higher number means the words sound more alike.
     */
    @Test
    void testDifference() throws EncoderException {
        final RefinedSoundex encoder = getStringEncoder();

        // Blank or null inputs share nothing, so the difference is always 0.
        assertEquals(0, encoder.difference(null, null));
        assertEquals(0, encoder.difference("", ""));
        assertEquals(0, encoder.difference(" ", " "));

        // Words that sound progressively less alike yield progressively smaller scores.
        assertEquals(6, encoder.difference("Smith", "Smythe"));
        assertEquals(3, encoder.difference("Ann", "Andrew"));
        assertEquals(1, encoder.difference("Margaret", "Andrew"));
        assertEquals(1, encoder.difference("Janet", "Margaret"));

        // Examples from the MS T-SQL DIFFERENCE reference (ts_de-dz_8co5).
        assertEquals(5, encoder.difference("Green", "Greene"));
        assertEquals(1, encoder.difference("Blotchet-Halls", "Greene"));

        // Examples from the MS T-SQL DIFFERENCE reference (ts_setu-sus_3o6w).
        assertEquals(6, encoder.difference("Smith", "Smythe"));
        assertEquals(8, encoder.difference("Smithers", "Smythers"));
        assertEquals(5, encoder.difference("Anothers", "Brothers"));
    }

    /**
     * {@code encode} produces a refined Soundex code, and encoding is case-insensitive.
     */
    @Test
    void testEncode() {
        final RefinedSoundex encoder = getStringEncoder();

        // Upper and lower case spellings of the same word encode identically.
        assertEquals("T6036084", encoder.encode("testing"));
        assertEquals("T6036084", encoder.encode("TESTING"));

        assertEquals("T60", encoder.encode("The"));
        assertEquals("Q503", encoder.encode("quick"));
        assertEquals("B1908", encoder.encode("brown"));
        assertEquals("F205", encoder.encode("fox"));
        assertEquals("J408106", encoder.encode("jumped"));
        assertEquals("O0209", encoder.encode("over"));
        assertEquals("T60", encoder.encode("the"));
        assertEquals("L7050", encoder.encode("lazy"));
        assertEquals("D6043", encoder.encode("dogs"));

        // CODEC-56: the shared US_ENGLISH instance encodes the same way as a new instance.
        assertEquals("D6043", RefinedSoundex.US_ENGLISH.encode("dogs"));
    }

    /**
     * A non-letter character maps to code 0 (the null character), not a digit.
     */
    @Test
    void testGetMappingCodeNonLetter() {
        final char code = getStringEncoder().getMappingCode('#');
        assertEquals(0, code, "Code does not equals zero");
    }

    /**
     * Encoding a string of every character from 0 to 255 skips the non-letter
     * characters and encodes only the letters.
     */
    @Test
    void testInvalidSoundexCharacter() {
        final char[] allByteValues = new char[256];
        for (int i = 0; i < allByteValues.length; i++) {
            allByteValues[i] = (char) i;
        }

        assertEquals(new RefinedSoundex().encode(new String(allByteValues)),
                "A0136024043780159360205050136024043780159360205053");
    }

    /**
     * The no-argument constructor uses the default US English mapping.
     */
    @Test
    void testNewInstance() {
        assertEquals("D6043", new RefinedSoundex().soundex("dogs"));
    }

    /**
     * The char-array constructor with the default mapping produces the default result.
     */
    @Test
    void testNewInstance2() {
        final RefinedSoundex encoder =
                new RefinedSoundex(RefinedSoundex.US_ENGLISH_MAPPING_STRING.toCharArray());
        assertEquals("D6043", encoder.soundex("dogs"));
    }

    /**
     * The String constructor with the default mapping produces the default result.
     */
    @Test
    void testNewInstance3() {
        final RefinedSoundex encoder =
                new RefinedSoundex(RefinedSoundex.US_ENGLISH_MAPPING_STRING);
        assertEquals("D6043", encoder.soundex("dogs"));
    }
}
