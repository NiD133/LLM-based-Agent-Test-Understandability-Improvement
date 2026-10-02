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
 */
class UnicodeEscaperTest {

    // Characters chosen so the input spans below, at, and above the boundary 'F' (U+0046).
    // 'A'=U+0041, 'D'=U+0044, 'F'=U+0046, 'G'=U+0047, 'Z'=U+005A
    private static final char  BOUNDARY_LOW  = 'F';
    private static final char  BOUNDARY_HIGH = 'L';
    private static final String INPUT         = "ADFGZ";

    @Test
    void testAbove() {
        // above('F') escapes every code point strictly greater than 'F' (exclusive boundary).
        // 'A', 'D', 'F' stay literal; 'G' and 'Z' are above 'F', so they are escaped.
        final UnicodeEscaper escaper = UnicodeEscaper.above(BOUNDARY_LOW);
        final String result = escaper.translate(INPUT);
        assertEquals("ADF\\u0047\\u005A", result,
                "Characters strictly above '" + BOUNDARY_LOW + "' should be Unicode-escaped");
    }

    @Test
    void testBelow() {
        // below('F') escapes every code point strictly less than 'F' (exclusive boundary).
        // 'A' and 'D' are below 'F', so they are escaped; 'F', 'G', 'Z' stay literal.
        final UnicodeEscaper escaper = UnicodeEscaper.below(BOUNDARY_LOW);
        final String result = escaper.translate(INPUT);
        assertEquals("\\u0041\\u0044FGZ", result,
                "Characters strictly below '" + BOUNDARY_LOW + "' should be Unicode-escaped");
    }

    @Test
    void testBetween() {
        // between('F', 'L') escapes every code point in the inclusive range ['F'..'L'].
        // 'A' and 'D' are below the range and 'Z' is above it, so they stay literal;
        // 'F' and 'G' fall within ['F'..'L'], so they are escaped.
        final UnicodeEscaper escaper = UnicodeEscaper.between(BOUNDARY_LOW, BOUNDARY_HIGH);
        final String result = escaper.translate(INPUT);
        assertEquals("AD\\u0046\\u0047Z", result,
                "Characters in the inclusive range ['" + BOUNDARY_LOW + "', '" + BOUNDARY_HIGH + "'] should be Unicode-escaped");
    }
}
