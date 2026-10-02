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

    /** Shared settings used by all pad test cases; only alignment and maxWidth vary per case. */
    private static final int INDENT   = 5;
    private static final int LEFT_PAD = 5;
    private static final int MIN_WIDTH = 4;

    /** Text passed to {@link TextStyle#pad} in every parameterised case. Length = 11. */
    private static final String PAD_INPUT = "Hello world";

    /**
     * Returns a builder pre-loaded with the common settings so each test case only
     * needs to set the two properties that actually differ (alignment and maxWidth).
     */
    private static TextStyle.Builder baseBuilder() {
        return TextStyle.builder()
                .setIndent(INDENT)
                .setLeftPad(LEFT_PAD)
                .setMinWidth(MIN_WIDTH)
                .setScalable(true);
    }

    /**
     * Provides test rows for {@link #testPad}.
     *
     * Each row is {@code (TextStyle, expectedPadFalse, expectedPadTrue)} where
     * {@value #PAD_INPUT} is always the text passed to {@code pad()}.
     *
     * The four scenario groups below cover:
     *   1. maxWidth = UNSET  – padding is indent-driven, not width-driven
     *   2. maxWidth < textLen – text is returned unchanged (too wide to pad)
     *   3. maxWidth > textLen + indent – there is room for both indent and trailing pad
     *   4. maxWidth > textLen but < textLen + indent – partial room: no leading indent pad
     */
    static Stream<Arguments> padTestData() {
        return Stream.of(

            // ── 1. UNSET_MAX_WIDTH: pad amount is determined by indent, not a fixed width ──

            // LEFT with unset max: pad(false) leaves text as-is; pad(true) prepends indent
            Arguments.of(
                baseBuilder()
                    .setMaxWidth(TextStyle.UNSET_MAX_WIDTH)
                    .setAlignment(TextStyle.Alignment.LEFT)
                    .get(),
                "Hello world",         // pad(false): addIndent=false → no prefix added
                "     Hello world"),   // pad(true):  5-space indent prepended

            // RIGHT with unset max: behaves identically to LEFT (indent goes before text)
            Arguments.of(
                baseBuilder()
                    .setMaxWidth(TextStyle.UNSET_MAX_WIDTH)
                    .setAlignment(TextStyle.Alignment.RIGHT)
                    .get(),
                "Hello world",
                "     Hello world"),

            // CENTER with unset max: indent(5) is split: floor(5/2)=2 left, 3 right
            Arguments.of(
                baseBuilder()
                    .setMaxWidth(TextStyle.UNSET_MAX_WIDTH)
                    .setAlignment(TextStyle.Alignment.CENTER)
                    .get(),
                "Hello world",
                "  Hello world   "),   // pad(true): 2 left + text + 3 right = indent(5)

            // ── 2. maxWidth(5) < textLen(11): text is longer than max, returned unchanged ──

            Arguments.of(
                baseBuilder()
                    .setMaxWidth(5)
                    .setAlignment(TextStyle.Alignment.LEFT)
                    .get(),
                "Hello world",
                "Hello world"),

            Arguments.of(
                baseBuilder()
                    .setMaxWidth(5)
                    .setAlignment(TextStyle.Alignment.RIGHT)
                    .get(),
                "Hello world",
                "Hello world"),

            Arguments.of(
                baseBuilder()
                    .setMaxWidth(5)
                    .setAlignment(TextStyle.Alignment.CENTER)
                    .get(),
                "Hello world",
                "Hello world"),

            // ── 3. maxWidth(20) > textLen(11) + indent(5): room for indent + trailing pad ──
            // Total padding available = 20 - 11 = 9 spaces.

            // LEFT, pad(false): no indent, 9 trailing spaces fill to width 20
            // LEFT, pad(true):  5 indent + text + 4 trailing (9 - 5 = 4)
            Arguments.of(
                baseBuilder()
                    .setMaxWidth(20)
                    .setAlignment(TextStyle.Alignment.LEFT)
                    .get(),
                "Hello world         ",  // pad(false): text + 9 trailing spaces
                "     Hello world    "), // pad(true):  5 indent + text + 4 trailing

            // RIGHT, pad(false): 9 leading spaces fill to width 20
            // RIGHT, pad(true):  5 indent + 4 leading spaces (indent consumed from leading gap)
            Arguments.of(
                baseBuilder()
                    .setMaxWidth(20)
                    .setAlignment(TextStyle.Alignment.RIGHT)
                    .get(),
                "         Hello world",  // pad(false): 9 leading spaces + text
                "         Hello world"), // pad(true):  same (restLen 9 > indent 5, so indent + rest(4))

            // CENTER: padLen = 9, left = 4, right = 5; addIndent has no effect once maxWidth is set
            Arguments.of(
                baseBuilder()
                    .setMaxWidth(20)
                    .setAlignment(TextStyle.Alignment.CENTER)
                    .get(),
                "    Hello world     ",  // pad(false): 4 left + text + 5 right
                "    Hello world     "), // pad(true):  identical (CENTER ignores addIndent when max set)

            // ── 4. maxWidth(14) > textLen(11) but < textLen + indent(16): indent does not fit ──
            // Total padding = 14 - 11 = 3 spaces; indent(5) would exceed available space.

            // LEFT: no room for indent prefix; 3 trailing spaces are added instead
            Arguments.of(
                baseBuilder()
                    .setMaxWidth(14)
                    .setAlignment(TextStyle.Alignment.LEFT)
                    .get(),
                "Hello world   ",   // pad(false): text + 3 trailing spaces
                "Hello world   "),  // pad(true):  restLen(3) <= indent(5), so no leading indent

            // RIGHT: 3 leading spaces; addIndent has no additional effect
            Arguments.of(
                baseBuilder()
                    .setMaxWidth(14)
                    .setAlignment(TextStyle.Alignment.RIGHT)
                    .get(),
                "   Hello world",
                "   Hello world"),

            // CENTER: padLen = 3, left = 1, right = 2
            Arguments.of(
                baseBuilder()
                    .setMaxWidth(14)
                    .setAlignment(TextStyle.Alignment.CENTER)
                    .get(),
                " Hello world  ",   // pad(false): 1 left + text + 2 right
                " Hello world  ")   // pad(true):  identical (CENTER ignores addIndent when max set)
        );
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
    void testPad(final TextStyle underTest, final String unindentedString, final String indentedString) {
        assertEquals(unindentedString, underTest.pad(false, PAD_INPUT), "Unindented string test failed");
        assertEquals(indentedString,   underTest.pad(true,  PAD_INPUT), "Indented string test failed");
    }
}
