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
 * Tests for {@link org.apache.commons.lang3.text.translate.NumericEntityUnescaper}.
 *
 * <p>A {@code NumericEntityUnescaper} converts XML numeric entities such as
 * {@code &#x30;} (hex) or {@code &#48;} (decimal) into the matching code point.
 * These tests cover the edge cases around malformed, out-of-range and
 * incomplete entities, plus how the unescaper reacts to a missing trailing
 * semicolon depending on the configured {@link NumericEntityUnescaper.OPTION}.</p>
 */
@Deprecated
class NumericEntityUnescaperTest extends AbstractLangTest {

    /**
     * An input that is left untouched should translate to itself, i.e. the
     * unescaper recognised nothing to convert.
     */
    private static void assertLeftUnchanged(final NumericEntityUnescaper unescaper,
            final String input, final String reason) {
        assertEquals(input, unescaper.translate(input), reason);
    }

    /**
     * A truncated entity at the very end of the input (nothing follows the
     * {@code &}, {@code &#}, {@code &#x} or {@code &#X}) cannot be parsed, so
     * the input must be returned verbatim rather than throwing.
     */
    @Test
    void testOutOfBounds() {
        final NumericEntityUnescaper unescaper = new NumericEntityUnescaper();

        assertLeftUnchanged(unescaper, "Test &", "Failed to ignore when last character is &");
        assertLeftUnchanged(unescaper, "Test &#", "Failed to ignore when last character is &");
        assertLeftUnchanged(unescaper, "Test &#x", "Failed to ignore when last character is &");
        assertLeftUnchanged(unescaper, "Test &#X", "Failed to ignore when last character is &");
    }

    /**
     * Entities whose numeric value exceeds the largest valid Unicode code point
     * ({@code 0x10FFFF}) are not valid characters, so they are left unchanged.
     */
    @Test
    void testOutOfRangeCodePoint() {
        final NumericEntityUnescaper unescaper = new NumericEntityUnescaper();

        // 0x110000 is exactly one past the maximum code point (hex form).
        assertLeftUnchanged(unescaper, "&#x110000;", "Failed to ignore code point above 0x10FFFF");
        // 1114112 == 0x110000 expressed in decimal.
        assertLeftUnchanged(unescaper, "&#1114112;", "Failed to ignore code point above 0x10FFFF");
        // 0x7FFFFFFF is Integer.MAX_VALUE, far above the maximum code point.
        assertLeftUnchanged(unescaper, "&#x7FFFFFFF;", "Failed to ignore code point above 0x10FFFF");
    }

    /**
     * A supplementary character (code point above {@code 0xFFFF}) must be
     * unescaped into its UTF-16 surrogate pair.
     */
    @Test
    void testSupplementaryUnescaping() {
        final NumericEntityUnescaper unescaper = new NumericEntityUnescaper();

        // 68642 (0x10C22) is a supplementary code point, encoded in UTF-16 as
        // the high/low surrogate pair "𐰢".
        final String entity = "&#68642;";
        final String expectedSurrogatePair = "𐰢";

        assertEquals(expectedSurrogatePair, unescaper.translate(entity),
                "Failed to unescape numeric entities supplementary characters");
    }

    /**
     * An entity without the trailing semicolon (e.g. {@code &#x30}) is handled
     * differently depending on the {@link NumericEntityUnescaper.OPTION}:
     * <ul>
     *   <li>{@code semiColonOptional} - still unescaped,</li>
     *   <li>default (semicolon required) - left unchanged,</li>
     *   <li>{@code errorIfNoSemiColon} - throws {@link IllegalArgumentException}.</li>
     * </ul>
     */
    @Test
    void testUnfinishedEntity() {
        // &#x30 is the hex entity for '0' (U+0030) but is missing its semicolon.
        final String unfinishedEntity = "Test &#x30 not test";

        // semiColonOptional: parse the entity anyway, turning &#x30 into '0'.
        final NumericEntityUnescaper lenientUnescaper =
                new NumericEntityUnescaper(NumericEntityUnescaper.OPTION.semiColonOptional);
        assertEquals("Test 0 not test", lenientUnescaper.translate(unfinishedEntity),
                "Failed to support unfinished entities (i.e. missing semicolon)");

        // Default behaviour: a semicolon is required, so the input is unchanged.
        final NumericEntityUnescaper defaultUnescaper = new NumericEntityUnescaper();
        assertLeftUnchanged(defaultUnescaper, unfinishedEntity,
                "Failed to ignore unfinished entities (i.e. missing semicolon)");

        // errorIfNoSemiColon: the missing semicolon is a hard error.
        final NumericEntityUnescaper strictUnescaper =
                new NumericEntityUnescaper(NumericEntityUnescaper.OPTION.errorIfNoSemiColon);
        assertIllegalArgumentException(() -> strictUnescaper.translate(unfinishedEntity));
    }

}
