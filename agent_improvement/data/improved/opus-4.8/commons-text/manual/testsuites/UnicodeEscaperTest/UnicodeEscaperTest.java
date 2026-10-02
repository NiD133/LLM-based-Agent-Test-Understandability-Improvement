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

package org.apache.commons.text.translate;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

/**
 * Tests {@link UnicodeEscaper}.
 *
 * <p>Every test escapes the same five-character sample {@value #SAMPLE}. Each letter
 * has a fixed Unicode code point, so it is easy to predict which letters a given
 * factory method escapes and what the resulting {@code \\uXXXX} sequences look like:</p>
 *
 * <pre>
 *   letter | code point | escape sequence
 *   -------+------------+----------------
 *     A    |   0x41     |  \\u0041
 *     D    |   0x44     |  \\u0044
 *     F    |   0x46     |  \\u0046
 *     G    |   0x47     |  \\u0047
 *     Z    |   0x5A     |  \\u005A
 * </pre>
 *
 * <p>An escaper only rewrites the letters it decides to escape; every other letter is
 * copied through unchanged.</p>
 */
class UnicodeEscaperTest {

    /** Sample translated by every test; see the class Javadoc for each letter's code point. */
    private static final String SAMPLE = "ADFGZ";

    @Test
    void testAbove() {
        // above('F') escapes code points strictly greater than 'F' (0x46),
        // so only G (0x47) and Z (0x5A) are escaped; A, D and F pass through.
        final UnicodeEscaper escaper = UnicodeEscaper.above('F');

        final String result = escaper.translate(SAMPLE);

        assertEquals("ADF\\u0047\\u005A", result,
                "Failed to escape Unicode characters via the above method");
    }

    @Test
    void testBelow() {
        // below('F') escapes code points strictly less than 'F' (0x46),
        // so only A (0x41) and D (0x44) are escaped; F, G and Z pass through.
        final UnicodeEscaper escaper = UnicodeEscaper.below('F');

        final String result = escaper.translate(SAMPLE);

        assertEquals("\\u0041\\u0044FGZ", result,
                "Failed to escape Unicode characters via the below method");
    }

    @Test
    void testBetween() {
        // between('F', 'L') escapes code points in the inclusive range [0x46, 0x4C],
        // so only F (0x46) and G (0x47) are escaped; A, D and Z fall outside the range.
        final UnicodeEscaper escaper = UnicodeEscaper.between('F', 'L');

        final String result = escaper.translate(SAMPLE);

        assertEquals("AD\\u0046\\u0047Z", result,
                "Failed to escape Unicode characters via the between method");
    }
}
