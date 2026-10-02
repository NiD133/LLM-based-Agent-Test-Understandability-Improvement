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

import org.apache.commons.codec.EncoderException;
import org.junit.jupiter.api.Test;

/**
 * Tests RefinedSoundex.
 */
class RefinedSoundexTest {

    private static final String DOGS_REFINED_SOUNDEX = "D6043";
    private static final String ENCODED_ASCII_CHARACTERS = "A0136024043780159360205050136024043780159360205053";
    private final RefinedSoundex stringEncoder = createStringEncoder();

    protected RefinedSoundex createStringEncoder() {
        return new RefinedSoundex();
    }

    @Test
    void testDifference() throws EncoderException {
        assertDifference(0, null, null);
        assertDifference(0, "", "");
        assertDifference(0, " ", " ");

        assertDifference(6, "Smith", "Smythe");
        assertDifference(3, "Ann", "Andrew");
        assertDifference(1, "Margaret", "Andrew");
        assertDifference(1, "Janet", "Margaret");

        assertDifference(5, "Green", "Greene");
        assertDifference(1, "Blotchet-Halls", "Greene");

        assertDifference(6, "Smith", "Smythe");
        assertDifference(8, "Smithers", "Smythers");
        assertDifference(5, "Anothers", "Brothers");
    }

    @Test
    void testEncode() {
        assertEncodedByDefaultSoundex("T6036084", "testing");
        assertEncodedByDefaultSoundex("T6036084", "TESTING");
        assertEncodedByDefaultSoundex("T60", "The");
        assertEncodedByDefaultSoundex("Q503", "quick");
        assertEncodedByDefaultSoundex("B1908", "brown");
        assertEncodedByDefaultSoundex("F205", "fox");
        assertEncodedByDefaultSoundex("J408106", "jumped");
        assertEncodedByDefaultSoundex("O0209", "over");
        assertEncodedByDefaultSoundex("T60", "the");
        assertEncodedByDefaultSoundex("L7050", "lazy");
        assertEncodedByDefaultSoundex(DOGS_REFINED_SOUNDEX, "dogs");

        assertEquals(DOGS_REFINED_SOUNDEX, RefinedSoundex.US_ENGLISH.encode("dogs"));
    }

    @Test
    void testGetMappingCodeNonLetter() {
        final char code = getStringEncoder().getMappingCode('#');
        assertEquals(0, code, "Code does not equals zero");
    }

    @Test
    void testInvalidSoundexCharacter() {
        final char[] invalid = new char[256];
        for (int i = 0; i < invalid.length; i++) {
            invalid[i] = (char) i;
        }

        assertEquals(ENCODED_ASCII_CHARACTERS, new RefinedSoundex().encode(new String(invalid)));
    }

    @Test
    void testNewInstance() {
        assertEquals(DOGS_REFINED_SOUNDEX, new RefinedSoundex().soundex("dogs"));
    }

    @Test
    void testNewInstance2() {
        assertEquals(DOGS_REFINED_SOUNDEX, new RefinedSoundex(RefinedSoundex.US_ENGLISH_MAPPING_STRING.toCharArray()).soundex("dogs"));
    }

    @Test
    void testNewInstance3() {
        assertEquals(DOGS_REFINED_SOUNDEX, new RefinedSoundex(RefinedSoundex.US_ENGLISH_MAPPING_STRING).soundex("dogs"));
    }

    private void assertDifference(final int expectedDifference, final String left, final String right) throws EncoderException {
        assertEquals(expectedDifference, getStringEncoder().difference(left, right));
    }

    private void assertEncodedByDefaultSoundex(final String expectedEncoding, final String input) {
        assertEquals(expectedEncoding, getStringEncoder().encode(input));
    }

    private RefinedSoundex getStringEncoder() {
        return stringEncoder;
    }
}
