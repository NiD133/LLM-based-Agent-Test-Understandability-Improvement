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
package org.apache.commons.text.diff;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Tests for the StringsComparator.
 */
class StringsComparatorTest {

    private static final class ComparisonCase {

        private final String before;
        private final String after;
        private final int modifications;
        private final int lcsLength;

        private ComparisonCase(final String before, final String after, final int modifications,
                final int lcsLength) {
            this.before = before;
            this.after = after;
            this.modifications = modifications;
            this.lcsLength = lcsLength;
        }
    }

    private static final class ExecutionVisitor<T> implements CommandVisitor<T> {

        private final StringBuilder builder = new StringBuilder();

        public String getString() {
            return builder.toString();
        }

        @Override
        public void visitDeleteCommand(final T object) {
            // noop
        }

        @Override
        public void visitInsertCommand(final T object) {
            builder.append(object);
        }

        @Override
        public void visitKeepCommand(final T object) {
            builder.append(object);
        }
    }

    private List<ComparisonCase> comparisonCases;

    @BeforeEach
    public void setUp() {
        comparisonCases = Arrays.asList(
            new ComparisonCase("bottle", "noodle", 6, 3),
            new ComparisonCase("nematode knowledge", "empty bottle", 16, 7),
            new ComparisonCase("", "", 0, 0),
            new ComparisonCase("aa", "C", 3, 0),
            new ComparisonCase("prefixed string", "prefix", 9, 6),
            new ComparisonCase("ABCABBA", "CBABAC", 5, 4),
            new ComparisonCase("glop glop", "pas glop pas glop", 8, 9),
            new ComparisonCase("coq", "ane", 6, 0),
            new ComparisonCase("spider-man", "klingon", 13, 2));
    }

    @AfterEach
    public void tearDown() {
        comparisonCases = null;
    }

    @Test
    void testExecution() {
        for (final ComparisonCase comparisonCase : comparisonCases) {
            final ExecutionVisitor<Character> executionVisitor = new ExecutionVisitor<>();

            new StringsComparator(comparisonCase.before, comparisonCase.after).getScript().visit(executionVisitor);

            assertEquals(comparisonCase.after, executionVisitor.getString());
        }
    }

    @Test
    void testLength() {
        for (final ComparisonCase comparisonCase : comparisonCases) {
            final StringsComparator comparator = new StringsComparator(comparisonCase.before, comparisonCase.after);

            assertEquals(comparisonCase.modifications, comparator.getScript().getModifications());
        }
    }

    @Test
    void testLongestCommonSubsequence() {
        for (final ComparisonCase comparisonCase : comparisonCases) {
            final StringsComparator comparator = new StringsComparator(comparisonCase.before, comparisonCase.after);

            assertEquals(comparisonCase.lcsLength, comparator.getScript().getLCSLength());
        }
    }
}
