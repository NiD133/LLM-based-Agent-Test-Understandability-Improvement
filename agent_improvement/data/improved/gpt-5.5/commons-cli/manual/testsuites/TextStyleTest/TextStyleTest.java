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

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class TextStyleTest {

    private static final String SAMPLE_TEXT = "Hello world";

    static Stream<Arguments> padTestData() {
        final List<Arguments> cases = new ArrayList<>();
        final TextStyle.Builder builder = paddingStyleBuilder();

        addUnsetWidthCases(cases, builder);
        addCasesWhereTextExceedsWidth(cases, builder);
        addCasesWhereWidthAllowsIndent(cases, builder);
        addCasesWhereWidthDoesNotAllowIndent(cases, builder);

        return cases.stream();
    }

    private static TextStyle.Builder paddingStyleBuilder() {
        final TextStyle.Builder builder = TextStyle.builder();
        builder.setIndent(5);
        builder.setLeftPad(5);
        builder.setMinWidth(4);
        builder.setScalable(true);
        return builder;
    }

    private static void addUnsetWidthCases(final List<Arguments> cases, final TextStyle.Builder builder) {
        builder.setMaxWidth(TextStyle.UNSET_MAX_WIDTH);
        addPadCase(cases, builder, TextStyle.Alignment.LEFT, SAMPLE_TEXT, "     Hello world");
        addPadCase(cases, builder, TextStyle.Alignment.RIGHT, SAMPLE_TEXT, "     Hello world");
        addPadCase(cases, builder, TextStyle.Alignment.CENTER, SAMPLE_TEXT, "  Hello world   ");
    }

    private static void addCasesWhereTextExceedsWidth(final List<Arguments> cases, final TextStyle.Builder builder) {
        builder.setMaxWidth(5);
        addPadCase(cases, builder, TextStyle.Alignment.LEFT, SAMPLE_TEXT, SAMPLE_TEXT);
        addPadCase(cases, builder, TextStyle.Alignment.RIGHT, SAMPLE_TEXT, SAMPLE_TEXT);
        addPadCase(cases, builder, TextStyle.Alignment.CENTER, SAMPLE_TEXT, SAMPLE_TEXT);
    }

    private static void addCasesWhereWidthAllowsIndent(final List<Arguments> cases, final TextStyle.Builder builder) {
        builder.setMaxWidth(20);
        addPadCase(cases, builder, TextStyle.Alignment.LEFT, "Hello world         ", "     Hello world    ");
        addPadCase(cases, builder, TextStyle.Alignment.RIGHT, "         Hello world", "         Hello world");
        addPadCase(cases, builder, TextStyle.Alignment.CENTER, "    Hello world     ", "    Hello world     ");
    }

    private static void addCasesWhereWidthDoesNotAllowIndent(final List<Arguments> cases, final TextStyle.Builder builder) {
        builder.setMaxWidth(14);
        addPadCase(cases, builder, TextStyle.Alignment.LEFT, "Hello world   ", "Hello world   ");
        addPadCase(cases, builder, TextStyle.Alignment.RIGHT, "   Hello world", "   Hello world");
        addPadCase(cases, builder, TextStyle.Alignment.CENTER, " Hello world  ", " Hello world  ");
    }

    private static void addPadCase(final List<Arguments> cases, final TextStyle.Builder builder,
            final TextStyle.Alignment alignment, final String expectedWithoutIndent, final String expectedWithIndent) {
        builder.setAlignment(alignment);
        cases.add(Arguments.of(builder.get(), expectedWithoutIndent, expectedWithIndent));
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
        assertEquals(expectedWithoutIndent, underTest.pad(false, SAMPLE_TEXT), "Unindented string test failed");
        assertEquals(expectedWithIndent, underTest.pad(true, SAMPLE_TEXT), "Indented string test failed");
    }
}
