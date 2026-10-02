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

package org.apache.commons.lang3;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotSame;

import java.io.IOException;
import java.io.StringWriter;
import java.util.Arrays;
import java.util.Objects;

import org.apache.commons.lang3.AppendableJoiner.Builder;
import org.apache.commons.lang3.text.StrBuilder;
import org.apache.commons.text.TextStringBuilder;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

/**
 * Tests {@link AppendableJoiner}.
 */
class AppendableJoinerTest extends AbstractLangTest {

    /**
     * A custom element type whose {@link #render} method writes directly to an {@link Appendable}
     * to exercise {@link AppendableJoiner.Builder#setElementAppender}.
     */
    static class Fixture {

        private final String name;

        Fixture(final String name) {
            this.name = name;
        }

        /** Appends {@code name} followed by {@code '!'} to the given appendable. */
        void render(final Appendable appendable) throws IOException {
            appendable.append(name);
            appendable.append('!');
        }
    }

    /**
     * Verifies that a joiner built with all four builder properties (prefix, delimiter, suffix,
     * and a custom element appender) correctly wraps and separates elements when joining into
     * a {@link StringBuilder} using both the array and {@link Iterable} overloads.
     */
    @Test
    void testBuilderWithAllPropertiesJoinsIntoStringBuilder() {
        // @formatter:off
        final AppendableJoiner<Object> joiner = AppendableJoiner.builder()
                .setPrefix("<")
                .setDelimiter(".")
                .setSuffix(">")
                .setElementAppender((a, e) -> a.append(String.valueOf(e)))
                .get();
        // @formatter:on
        final StringBuilder sb = new StringBuilder("A");

        // array overload: wraps "B" and "C" between "<" and ">" with "." delimiter
        assertEquals("A<B.C>", joiner.join(sb, "B", "C").toString());

        sb.append("1");

        // iterable overload: appends another joined group after the existing content
        assertEquals("A<B.C>1<D.E>", joiner.join(sb, Arrays.asList("D", "E")).toString());
    }

    /**
     * Verifies that the default builder (no prefix, suffix, or delimiter) concatenates elements
     * without any separators, and that each call to {@link Builder#get()} returns a distinct joiner instance.
     */
    @Test
    void testDefaultBuilderConcatenatesElementsWithoutSeparators() {
        final Builder<Object> builder = AppendableJoiner.builder();

        // each call to get() must return a different instance
        assertNotSame(builder.get(), builder.get());

        final AppendableJoiner<Object> joiner = builder.get();
        final StringBuilder sb = new StringBuilder("A");

        // array overload: elements are appended with no delimiter, prefix, or suffix
        assertEquals("ABC", joiner.join(sb, "B", "C").toString());

        sb.append("1");

        // iterable overload: same behaviour for a list
        assertEquals("ABC1DE", joiner.join(sb, "D", "E").toString());
    }

    /**
     * Verifies that {@link AppendableJoiner#builder()} always returns a new, independent
     * {@link Builder} instance.
     */
    @Test
    void testBuilderAlwaysCreatesNewInstance() {
        assertNotSame(AppendableJoiner.builder(), AppendableJoiner.builder());
    }

    /**
     * Verifies that a delimiter-only joiner works correctly for every standard {@link Appendable}
     * implementation when using the {@link AppendableJoiner#joinA} overloads, which declare
     * {@link IOException}.
     */
    @SuppressWarnings("deprecation") // Test own StrBuilder
    @ParameterizedTest
    @ValueSource(classes = { StringBuilder.class, StringBuffer.class, StringWriter.class, StrBuilder.class, TextStringBuilder.class })
    void testDelimiterJoinWorksWithVariousAppendableTypes(final Class<? extends Appendable> clazz) throws Exception {
        final AppendableJoiner<Object> joiner = AppendableJoiner.builder().setDelimiter(".").get();

        // create a fresh instance of the target Appendable type and seed it with "A"
        final Appendable appendable = clazz.newInstance();
        appendable.append("A");

        // array overload (joinA declares IOException)
        assertEquals("AB.C", joiner.joinA(appendable, "B", "C").toString());

        appendable.append("1");

        // iterable overload (joinA declares IOException)
        assertEquals("AB.C1D.E", joiner.joinA(appendable, Arrays.asList("D", "E")).toString());
    }

    /**
     * Verifies that a delimiter-only joiner works correctly with {@link StringBuilder} via the
     * {@link AppendableJoiner#join} overloads, which do <em>not</em> declare {@link IOException}.
     */
    @Test
    void testDelimiterJoinWithStringBuilderDoesNotThrowIOException() {
        final AppendableJoiner<Object> joiner = AppendableJoiner.builder().setDelimiter(".").get();
        final StringBuilder sb = new StringBuilder("A");

        // array overload — no checked IOException on StringBuilder path
        assertEquals("AB.C", joiner.join(sb, "B", "C").toString());

        sb.append("1");

        // iterable overload — no checked IOException on StringBuilder path
        assertEquals("AB.C1D.E", joiner.join(sb, Arrays.asList("D", "E")).toString());
    }

    /**
     * Verifies that a custom element appender can prepend a pipe character before each element,
     * so that the delimiter appears between the pipe-prefixed element representations.
     */
    @Test
    void testCustomElementAppenderPrependsPipeBeforeEachElement() {
        // @formatter:off
        final AppendableJoiner<Object> joiner = AppendableJoiner.builder()
                .setPrefix("<")
                .setDelimiter(".")
                .setSuffix(">")
                .setElementAppender((a, e) -> a.append("|").append(Objects.toString(e)))
                .get();
        // @formatter:on
        final StringBuilder sb = new StringBuilder("A");

        assertEquals("A<|B.|C>", joiner.join(sb, "B", "C").toString());

        sb.append("1");

        assertEquals("A<|B.|C>1<|D.|E>", joiner.join(sb, Arrays.asList("D", "E")).toString());
    }

    /**
     * Verifies that a custom element appender can delegate to an object's own render method,
     * allowing complex types to write themselves to the {@link Appendable} directly.
     */
    @Test
    void testCustomElementAppenderDelegatesToFixtureRenderMethod() {
        // @formatter:off
        final AppendableJoiner<Fixture> joiner = AppendableJoiner.<Fixture>builder()
                .setElementAppender((a, e) -> e.render(a))
                .get();
        // @formatter:on
        final StringBuilder sb = new StringBuilder("[");

        // each Fixture appends its name followed by '!'
        assertEquals("[B!C!", joiner.join(sb, new Fixture("B"), new Fixture("C")).toString());

        sb.append("]");

        assertEquals("[B!C!]D!E!", joiner.join(sb, Arrays.asList(new Fixture("D"), new Fixture("E"))).toString());
    }
}
