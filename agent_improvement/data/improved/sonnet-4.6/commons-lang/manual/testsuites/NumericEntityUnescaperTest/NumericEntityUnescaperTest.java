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

package org.apache.commons.lang3.text.translate;

import static org.apache.commons.lang3.LangAssertions.assertIllegalArgumentException;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.apache.commons.lang3.AbstractLangTest;
import org.junit.jupiter.api.Test;

/**
 * Tests for {@link NumericEntityUnescaper}.
 *
 * <p>NumericEntityUnescaper translates XML numeric character references
 * (e.g., {@code &#68642;} or {@code &#x10C22;}) into the corresponding
 * Unicode characters. These tests verify boundary conditions, out-of-range
 * code points, supplementary character handling, and semicolon-handling
 * options.</p>
 */
@Deprecated
class NumericEntityUnescaperTest extends AbstractLangTest {

    /**
     * Verifies that strings containing incomplete or truncated numeric entity
     * prefixes (e.g., {@code &}, {@code &#}, {@code &#x}) at the end of input
     * are passed through unchanged rather than being partially consumed or
     * causing an error.
     */
    @Test
    void testOutOfBounds() {
        final NumericEntityUnescaper unescaper = new NumericEntityUnescaper();

        assertEquals("Test &",   unescaper.translate("Test &"),   "Should pass through when input ends with '&'");
        assertEquals("Test &#",  unescaper.translate("Test &#"),  "Should pass through when input ends with '&#'");
        assertEquals("Test &#x", unescaper.translate("Test &#x"), "Should pass through when input ends with '&#x'");
        assertEquals("Test &#X", unescaper.translate("Test &#X"), "Should pass through when input ends with '&#X'");
    }

    /**
     * Verifies that numeric entity references whose code point value exceeds
     * the maximum valid Unicode code point ({@code 0x10FFFF / 1114111}) are
     * left untranslated and returned as-is.
     */
    @Test
    void testOutOfRangeCodePoint() {
        final NumericEntityUnescaper unescaper = new NumericEntityUnescaper();

        // Hex entity one above MAX_CODE_POINT (0x110000 == 1114112)
        assertEquals("&#x110000;", unescaper.translate("&#x110000;"),
                "Should not translate hex entity with code point above 0x10FFFF");

        // Decimal entity one above MAX_CODE_POINT (1114112 == 0x110000)
        assertEquals("&#1114112;", unescaper.translate("&#1114112;"),
                "Should not translate decimal entity with code point above 0x10FFFF");

        // Hex entity far above MAX_CODE_POINT (0x7FFFFFFF)
        assertEquals("&#x7FFFFFFF;", unescaper.translate("&#x7FFFFFFF;"),
                "Should not translate hex entity with very large code point (0x7FFFFFFF)");
    }

    /**
     * Verifies that a numeric entity whose code point lies in the supplementary
     * character range (above U+FFFF) is correctly translated to a UTF-16
     * surrogate pair.
     *
     * <p>U+10C22 (decimal 68642) must be encoded as the surrogate pair
     * {@code 𐰢} in Java's internal UTF-16 representation.</p>
     */
    @Test
    void testSupplementaryUnescaping() {
        final NumericEntityUnescaper unescaper = new NumericEntityUnescaper();

        final String input    = "&#68642;";         // decimal reference for supplementary character U+10C22
        final String expected = "𐰢";     // UTF-16 surrogate pair for U+10C22

        assertEquals(expected, unescaper.translate(input),
                "Should translate supplementary code point to its UTF-16 surrogate pair");
    }

    /**
     * Verifies the three supported behaviors when a numeric entity is missing
     * its trailing semicolon (e.g., {@code &#x30} instead of {@code &#x30;}):
     *
     * <ol>
     *   <li><b>semiColonOptional</b> – the entity is parsed and translated.</li>
     *   <li>Default ({@code semiColonRequired}) – the entity is left untranslated.</li>
     *   <li><b>errorIfNoSemiColon</b> – an {@link IllegalArgumentException} is thrown.</li>
     * </ol>
     */
    @Test
    void testUnfinishedEntity() {
        // &#x30 is '0' (U+0030) without a trailing semicolon
        final String inputWithMissingSemicolon = "Test &#x30 not test";

        // semiColonOptional: entity without semicolon should be parsed and translated
        final NumericEntityUnescaper optionalSemicolonUnescaper =
                new NumericEntityUnescaper(NumericEntityUnescaper.OPTION.semiColonOptional);
        assertEquals("Test 0 not test", optionalSemicolonUnescaper.translate(inputWithMissingSemicolon),
                "semiColonOptional: entity without semicolon should be translated");

        // Default (semiColonRequired): entity without semicolon should be left untouched
        final NumericEntityUnescaper defaultUnescaper = new NumericEntityUnescaper();
        assertEquals(inputWithMissingSemicolon, defaultUnescaper.translate(inputWithMissingSemicolon),
                "Default behavior: entity without semicolon should be left untranslated");

        // errorIfNoSemiColon: entity without semicolon should throw IllegalArgumentException
        final NumericEntityUnescaper strictUnescaper =
                new NumericEntityUnescaper(NumericEntityUnescaper.OPTION.errorIfNoSemiColon);
        assertIllegalArgumentException(() -> strictUnescaper.translate(inputWithMissingSemicolon));
    }
}
