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

    private static final String CODE_POINT_CONSUMPTION_MESSAGE = "Incorrect code point consumption";
    private static final String TRANSLATED_VALUE_MESSAGE = "Incorrect value";

    @Test
    void testBasicLookup() throws IOException {
        final LookupTranslator translator = new LookupTranslator(new CharSequence[][] { { "one", "two" } });
        final StringWriter output = new StringWriter();

        final int consumedCodePoints = translator.translate("one", 0, output);

        assertEquals(3, consumedCodePoints, CODE_POINT_CONSUMPTION_MESSAGE);
        assertEquals("two", output.toString(), TRANSLATED_VALUE_MESSAGE);
    }

    // Tests: https://issues.apache.org/jira/browse/LANG-882
    @Test
    void testLang882() throws IOException {
        final LookupTranslator translator = new LookupTranslator(new CharSequence[][] { { new StringBuffer("one"), new StringBuffer("two") } });
        final StringWriter output = new StringWriter();

        final int consumedCodePoints = translator.translate(new StringBuffer("one"), 0, output);

        assertEquals(3, consumedCodePoints, CODE_POINT_CONSUMPTION_MESSAGE);
        assertEquals("two", output.toString(), TRANSLATED_VALUE_MESSAGE);
    }

    @Test
    void testSupplementaryKey() throws IOException {
        final String supplementaryCodePoint = new String(Character.toChars(0x1D54F));
        final LookupTranslator translator = new LookupTranslator(new CharSequence[][] { { supplementaryCodePoint, "X" } });
        final StringWriter output = new StringWriter();

        final int consumedCodePoints = translator.translate(supplementaryCodePoint, 0, output);

        assertEquals(1, consumedCodePoints, CODE_POINT_CONSUMPTION_MESSAGE);
        assertEquals("X", output.toString(), TRANSLATED_VALUE_MESSAGE);
        assertEquals("XY", translator.translate(supplementaryCodePoint + "Y"), "Trailing character must be preserved");
    }

}
