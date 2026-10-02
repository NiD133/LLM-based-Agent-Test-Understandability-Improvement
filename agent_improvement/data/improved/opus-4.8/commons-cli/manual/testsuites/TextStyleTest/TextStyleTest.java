/*
  Licensed to the Apache Software Foundation (ASF) under one or more
  contributor license agreements.  See the NOTICE file distributed with
  this work for additional information regarding copyright ownership.
  The ASF licenses this file to You under the Apache License, Version 2.0
  (the "License"); you may not use this file except in compliance with
  the License.  You may obtain a copy of the License at

      https://www.apache.org/licenses/LICENSE-2.0

  Unless required by applicable law or agreed to in writing, software
  distributed under the License is distributed on an "AS IS" BASIS,
  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
  See the License for the specific language governing permissions and
  limitations under the License.
 */
package org.apache.commons.cli.help;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.stream.Stream;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class TextStyleTest {

    /** The text fed to {@link TextStyle#pad} in every padding scenario. */
    private static final String INPUT_TEXT = "Hello world";

    /**
     * Builds a {@link TextStyle} that shares the common settings used across all
     * padding scenarios (indent 5, left pad 5, min width 4, scalable) and only
     * varies by maximum width and alignment.
     *
     * @param maxWidth  the maximum width to apply.
     * @param alignment the alignment to apply.
     * @return the configured text style.
     */
    private static TextStyle styleWith(final int maxWidth, final TextStyle.Alignment alignment) {
        return TextStyle.builder()
                .setIndent(5)
                .setLeftPad(5)
                .setMinWidth(4)
                .setScalable(true)
                .setMaxWidth(maxWidth)
                .setAlignment(alignment)
                .get();
    }

    /**
     * Builds one parameterized case for {@link #testPad}.
     *
     * @param style                the style under test.
     * @param expectedWithoutIndent the expected result of {@code pad(false, INPUT_TEXT)}.
     * @param expectedWithIndent    the expected result of {@code pad(true, INPUT_TEXT)}.
     * @return the arguments for a single test invocation.
     */
    private static Arguments padCase(final TextStyle style, final String expectedWithoutIndent, final String expectedWithIndent) {
        return Arguments.of(style, expectedWithoutIndent, expectedWithIndent);
    }

    static Stream<Arguments> padTestData() {
        final TextStyle.Alignment left = TextStyle.Alignment.LEFT;
        final TextStyle.Alignment right = TextStyle.Alignment.RIGHT;
        final TextStyle.Alignment center = TextStyle.Alignment.CENTER;

        return Stream.of(
                // No maximum width: result is the original text plus the indent.
                padCase(styleWith(TextStyle.UNSET_MAX_WIDTH, left), "Hello world", "     Hello world"),
                padCase(styleWith(TextStyle.UNSET_MAX_WIDTH, right), "Hello world", "     Hello world"),
                padCase(styleWith(TextStyle.UNSET_MAX_WIDTH, center), "  Hello world   ", "  Hello world   "),

                // Maximum width shorter than the text: result is the original text unchanged.
                padCase(styleWith(5, left), "Hello world", "Hello world"),
                padCase(styleWith(5, right), "Hello world", "Hello world"),
                padCase(styleWith(5, center), "Hello world", "Hello world"),

                // Maximum width larger than text length + indent: text is padded to the full width.
                padCase(styleWith(20, left), "Hello world         ", "     Hello world    "),
                padCase(styleWith(20, right), "         Hello world", "         Hello world"),
                padCase(styleWith(20, center), "    Hello world     ", "    Hello world     "),

                // Maximum width between text length and text length + indent: text is padded to the width.
                padCase(styleWith(14, left), "Hello world   ", "Hello world   "),
                padCase(styleWith(14, right), "   Hello world", "   Hello world"),
                padCase(styleWith(14, center), " Hello world  ", " Hello world  "));
    }

    @Test
    void testDefaultStyle() {
        final TextStyle underTest = TextStyle.DEFAULT;
        assertEquals(TextStyle.Alignment.LEFT, underTest.getAlignment());
        assertTrue(underTest.isScalable());
        assertEquals(0, underTest.getLeftPad());
        assertEquals(0, underTest.getMinWidth());
        assertEquals(TextStyle.UNSET_MAX_WIDTH, underTest.getMaxWidth());
    }

    @ParameterizedTest(name = "{index} {0}")
    @MethodSource("padTestData")
    void testPad(final TextStyle underTest, final String expectedWithoutIndent, final String expectedWithIndent) {
        assertEquals(expectedWithoutIndent, underTest.pad(false, INPUT_TEXT), "Unindented string test failed");
        assertEquals(expectedWithIndent, underTest.pad(true, INPUT_TEXT), "Indented string test failed");
    }
}
