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
     * A sample element that knows how to render itself directly onto an {@link Appendable}, used to exercise a custom
     * element appender. Rendering "X" produces the text {@code "X!"}.
     */
    static class Fixture {

        private final String name;

        Fixture(final String name) {
            this.name = name;
        }

        /**
         * Renders myself onto an Appendable to avoid creating intermediary strings.
         */
        void render(final Appendable appendable) throws IOException {
            appendable.append(name);
            appendable.append('!');
        }
    }

    /**
     * A joiner that uses every builder property: it wraps the joined elements with a {@code "<"} prefix and {@code ">"}
     * suffix, separates them with {@code "."}, and converts each element via {@link String#valueOf(Object)}.
     */
    @Test
    void testAllBuilderPropertiesStringBuilder() {
        // @formatter:off
        final AppendableJoiner<Object> joiner = AppendableJoiner.builder()
                .setPrefix("<")
                .setDelimiter(".")
                .setSuffix(">")
                .setElementAppender((appendable, element) -> appendable.append(String.valueOf(element)))
                .get();
        // @formatter:on

        // Joining "B" and "C" onto the seed "A" wraps them as "<B.C>" and appends to the existing content.
        final StringBuilder target = new StringBuilder("A");
        assertEquals("A<B.C>", joiner.join(target, "B", "C").toString());

        // A second join (from an Iterable) appends another wrapped group onto whatever is already there.
        target.append("1");
        assertEquals("A<B.C>1<D.E>", joiner.join(target, Arrays.asList("D", "E")).toString());
    }

    /**
     * A joiner built with no properties set: no prefix, suffix, or delimiter, so elements are concatenated directly.
     */
    @Test
    void testBuildDefaultStringBuilder() {
        final Builder<Object> builder = AppendableJoiner.builder();
        // Each call to get() returns a fresh, independent joiner instance.
        assertNotSame(builder.get(), builder.get());

        final AppendableJoiner<Object> joiner = builder.get();

        // With no prefix/suffix/delimiter, "B" and "C" are simply concatenated onto "A".
        final StringBuilder target = new StringBuilder("A");
        assertEquals("ABC", joiner.join(target, "B", "C").toString());

        target.append("1");
        assertEquals("ABC1DE", joiner.join(target, "D", "E").toString());
    }

    /**
     * Each call to {@link AppendableJoiner#builder()} returns a distinct builder instance.
     */
    @Test
    void testBuilder() {
        assertNotSame(AppendableJoiner.builder(), AppendableJoiner.builder());
    }

    /**
     * The delimiter-only joiner works against any {@link Appendable} implementation via the checked-exception
     * {@code joinA} methods.
     */
    @SuppressWarnings("deprecation") // Test own StrBuilder
    @ParameterizedTest
    @ValueSource(classes = { StringBuilder.class, StringBuffer.class, StringWriter.class, StrBuilder.class, TextStringBuilder.class })
    void testDelimiterAppendable(final Class<? extends Appendable> clazz) throws Exception {
        final AppendableJoiner<Object> joiner = AppendableJoiner.builder().setDelimiter(".").get();

        final Appendable target = clazz.newInstance();
        target.append("A");

        // joinA may throw IOException because a general Appendable can fail on I/O.
        assertEquals("AB.C", joiner.joinA(target, "B", "C").toString());

        target.append("1");
        assertEquals("AB.C1D.E", joiner.joinA(target, Arrays.asList("D", "E")).toString());
    }

    /**
     * The delimiter-only joiner against a {@link StringBuilder}, using the {@code join} methods that never throw a
     * checked {@link IOException}.
     */
    @Test
    void testDelimiterStringBuilder() {
        final AppendableJoiner<Object> joiner = AppendableJoiner.builder().setDelimiter(".").get();

        final StringBuilder target = new StringBuilder("A");
        // join (onto a StringBuilder) does not declare IOException.
        assertEquals("AB.C", joiner.join(target, "B", "C").toString());

        target.append("1");
        assertEquals("AB.C1D.E", joiner.join(target, Arrays.asList("D", "E")).toString());
    }

    /**
     * A custom element appender that prefixes each element with {@code "|"}, combined with prefix, suffix, and
     * delimiter. Joining "B" and "C" therefore yields {@code "<|B.|C>"}.
     */
    @Test
    void testToCharSequenceStringBuilder1() {
        // @formatter:off
        final AppendableJoiner<Object> joiner = AppendableJoiner.builder()
                .setPrefix("<")
                .setDelimiter(".")
                .setSuffix(">")
                .setElementAppender((appendable, element) -> appendable.append("|").append(Objects.toString(element)))
                .get();
        // @formatter:on

        final StringBuilder target = new StringBuilder("A");
        assertEquals("A<|B.|C>", joiner.join(target, "B", "C").toString());

        target.append("1");
        assertEquals("A<|B.|C>1<|D.|E>", joiner.join(target, Arrays.asList("D", "E")).toString());
    }

    /**
     * A custom element appender that delegates to the element's own {@link Fixture#render(Appendable)} method. With no
     * prefix/suffix/delimiter, each fixture renders as {@code "name!"} back-to-back.
     */
    @Test
    void testToCharSequenceStringBuilder2() {
        // @formatter:off
        final AppendableJoiner<Fixture> joiner = AppendableJoiner.<Fixture>builder()
                .setElementAppender((appendable, element) -> element.render(appendable))
                .get();
        // @formatter:on

        final StringBuilder target = new StringBuilder("[");
        assertEquals("[B!C!", joiner.join(target, new Fixture("B"), new Fixture("C")).toString());

        target.append("]");
        assertEquals("[B!C!]D!E!", joiner.join(target, Arrays.asList(new Fixture("D"), new Fixture("E"))).toString());
    }
}
