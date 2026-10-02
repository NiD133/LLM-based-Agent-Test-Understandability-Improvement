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

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.io.StringWriter;

import org.apache.commons.lang3.AbstractLangTest;
import org.junit.jupiter.api.Test;

/**
 * Tests for {@link org.apache.commons.lang3.text.translate.LookupTranslator}.
 *
 * <p>A {@link LookupTranslator} is built from a table of {@code {key, replacement}}
 * pairs. When {@code translate} encounters a key at the given index it writes the
 * matching replacement and returns how many <em>code points</em> of the key it
 * consumed; an unmatched input consumes nothing and returns {@code 0}.</p>
 */
@Deprecated
class LookupTranslatorTest extends AbstractLangTest {

    /** Index at which translation starts for every case below (the start of the input). */
    private static final int START_OF_INPUT = 0;

    @Test
    void testBasicLookup() throws IOException {
        // Map the key "one" to the replacement "two".
        final LookupTranslator translator =
                new LookupTranslator(new CharSequence[][] { { "one", "two" } });

        final StringWriter output = new StringWriter();
        final int codePointsConsumed = translator.translate("one", START_OF_INPUT, output);

        assertEquals(3, codePointsConsumed, "Should consume all 3 code points of the matched key \"one\"");
        assertEquals("two", output.toString(), "Matched key \"one\" should be replaced by \"two\"");
    }

    /**
     * Keys and inputs may be any {@link CharSequence}, not just {@link String}; a
     * non-String key such as a {@link StringBuffer} must still match equivalent input.
     *
     * @see <a href="https://issues.apache.org/jira/browse/LANG-882">LANG-882</a>
     */
    @Test
    void testLang882() throws IOException {
        // Same "one" -> "two" mapping as the basic case, but expressed with StringBuffers.
        final LookupTranslator translator = new LookupTranslator(
                new CharSequence[][] { { new StringBuffer("one"), new StringBuffer("two") } });

        final StringWriter output = new StringWriter();
        final int codePointsConsumed =
                translator.translate(new StringBuffer("one"), START_OF_INPUT, output);

        assertEquals(3, codePointsConsumed, "Should consume all 3 code points of the matched key \"one\"");
        assertEquals("two", output.toString(), "StringBuffer key \"one\" should be replaced by \"two\"");
    }

    @Test
    void testSupplementaryKey() throws IOException {
        // A single supplementary character (U+1D54F) encoded as two chars but one code point.
        final String key = new String(Character.toChars(0x1D54F));
        final LookupTranslator translator =
                new LookupTranslator(new CharSequence[][] { { key, "X" } });

        final StringWriter output = new StringWriter();
        final int codePointsConsumed = translator.translate(key, START_OF_INPUT, output);

        assertEquals(1, codePointsConsumed, "Supplementary key spans two chars but only one code point");
        assertEquals("X", output.toString(), "Supplementary key should be replaced by \"X\"");

        // The match must stop at the key and leave any following character ("Y") untouched.
        assertEquals("XY", translator.translate(key + "Y"), "Trailing character after the key must be preserved");
    }

}
