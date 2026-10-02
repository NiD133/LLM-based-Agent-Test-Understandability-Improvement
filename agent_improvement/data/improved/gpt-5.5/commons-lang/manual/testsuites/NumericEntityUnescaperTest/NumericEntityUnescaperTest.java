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
 */
@Deprecated
class NumericEntityUnescaperTest extends AbstractLangTest {

    private static final String MISSING_SEMICOLON_ENTITY = "Test &#x30 not test";

    private static void assertTranslation(final NumericEntityUnescaper unescaper, final String input,
            final String expected, final String message) {
        assertEquals(expected, unescaper.translate(input), message);
    }

    @Test
    void testOutOfBounds() {
        final NumericEntityUnescaper neu = new NumericEntityUnescaper();

        assertTranslation(neu, "Test &", "Test &", "Failed to ignore when last character is &");
        assertTranslation(neu, "Test &#", "Test &#", "Failed to ignore when last character is &");
        assertTranslation(neu, "Test &#x", "Test &#x", "Failed to ignore when last character is &");
        assertTranslation(neu, "Test &#X", "Test &#X", "Failed to ignore when last character is &");
    }

    @Test
    void testOutOfRangeCodePoint() {
        final NumericEntityUnescaper neu = new NumericEntityUnescaper();

        assertTranslation(neu, "&#x110000;", "&#x110000;", "Failed to ignore code point above 0x10FFFF");
        assertTranslation(neu, "&#1114112;", "&#1114112;", "Failed to ignore code point above 0x10FFFF");
        assertTranslation(neu, "&#x7FFFFFFF;", "&#x7FFFFFFF;", "Failed to ignore code point above 0x10FFFF");
    }

    @Test
    void testSupplementaryUnescaping() {
        final NumericEntityUnescaper neu = new NumericEntityUnescaper();
        final String input = "&#68642;";
        final String expected = "\uD803\uDC22";
        final String result = neu.translate(input);

        assertEquals(expected, result, "Failed to unescape numeric entities supplementary characters");
    }

    @Test
    void testUnfinishedEntity() {
        final NumericEntityUnescaper optionalSemicolonNeu =
                new NumericEntityUnescaper(NumericEntityUnescaper.OPTION.semiColonOptional);
        final String optionalSemicolonExpected = "Test \u0030 not test";
        final String optionalSemicolonResult = optionalSemicolonNeu.translate(MISSING_SEMICOLON_ENTITY);
        assertEquals(optionalSemicolonExpected, optionalSemicolonResult,
                "Failed to support unfinished entities (i.e. missing semicolon)");

        final NumericEntityUnescaper requiredSemicolonNeu = new NumericEntityUnescaper();
        final String requiredSemicolonExpected = MISSING_SEMICOLON_ENTITY;
        final String requiredSemicolonResult = requiredSemicolonNeu.translate(MISSING_SEMICOLON_ENTITY);
        assertEquals(requiredSemicolonExpected, requiredSemicolonResult,
                "Failed to ignore unfinished entities (i.e. missing semicolon)");

        final NumericEntityUnescaper failingNeu =
                new NumericEntityUnescaper(NumericEntityUnescaper.OPTION.errorIfNoSemiColon);
        final String failingInput = MISSING_SEMICOLON_ENTITY;
        assertIllegalArgumentException(() -> failingNeu.translate(failingInput));
    }

}
