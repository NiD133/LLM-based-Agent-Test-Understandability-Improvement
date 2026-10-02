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
 */
@Deprecated
class LookupTranslatorTest extends AbstractLangTest {

    // U+1D54F MATHEMATICAL DOUBLE-STRUCK CAPITAL X — a supplementary (surrogate-pair) code point
    private static final int SUPPLEMENTARY_CODE_POINT = 0x1D54F;

    @Test
    void testBasicLookup() throws IOException {
        final LookupTranslator translator = new LookupTranslator(new CharSequence[][] { { "one", "two" } });
        final StringWriter output = new StringWriter();

        final int codePointsConsumed = translator.translate("one", 0, output);

        // "one" is 3 characters, each a single code point
        assertEquals(3, codePointsConsumed, "Incorrect code point consumption");
        assertEquals("two", output.toString(), "Incorrect value");
    }

    // Regression test for https://issues.apache.org/jira/browse/LANG-882:
    // LookupTranslator must accept non-String CharSequence types (e.g. StringBuffer) as lookup
    // keys, because the original implementation compared key types rather than key content.
    @Test
    void testLang882() throws IOException {
        final LookupTranslator translator = new LookupTranslator(
                new CharSequence[][] { { new StringBuffer("one"), new StringBuffer("two") } });
        final StringWriter output = new StringWriter();

        final int codePointsConsumed = translator.translate(new StringBuffer("one"), 0, output);

        assertEquals(3, codePointsConsumed, "Incorrect code point consumption");
        assertEquals("two", output.toString(), "Incorrect value");
    }

    @Test
    void testSupplementaryKey() throws IOException {
        // A supplementary character occupies 2 chars (a surrogate pair) but counts as 1 code point.
        final String supplementaryKey = new String(Character.toChars(SUPPLEMENTARY_CODE_POINT));
        final LookupTranslator translator = new LookupTranslator(
                new CharSequence[][] { { supplementaryKey, "X" } });
        final StringWriter output = new StringWriter();

        final int codePointsConsumed = translator.translate(supplementaryKey, 0, output);

        assertEquals(1, codePointsConsumed, "Incorrect code point consumption");
        assertEquals("X", output.toString(), "Incorrect value");
        // Ensure the translator does not consume the character following the matched key
        assertEquals("XY", translator.translate(supplementaryKey + "Y"), "Trailing character must be preserved");
    }

}
