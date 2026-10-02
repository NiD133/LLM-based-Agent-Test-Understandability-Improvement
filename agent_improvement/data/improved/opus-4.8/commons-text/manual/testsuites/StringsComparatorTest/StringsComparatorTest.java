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

import java.util.stream.Stream;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

/**
 * Tests for the StringsComparator.
 */
class StringsComparatorTest {

    /**
     * A {@link CommandVisitor} that replays an edit script to reconstruct the
     * "after" string. It appends every kept and inserted character (the
     * characters that survive into the second sequence) while ignoring deletes,
     * so the accumulated text should equal the target string.
     */
    private static final class ExecutionVisitor<T> implements CommandVisitor<T> {

        private final StringBuilder reconstructed = new StringBuilder();

        String getString() {
            return reconstructed.toString();
        }

        @Override
        public void visitDeleteCommand(final T object) {
            // Deleted characters are not part of the target string.
        }

        @Override
        public void visitInsertCommand(final T object) {
            reconstructed.append(object);
        }

        @Override
        public void visitKeepCommand(final T object) {
            reconstructed.append(object);
        }
    }

    /**
     * A single comparison scenario: transforming {@code before} into
     * {@code after}, together with the expected number of modifications and the
     * expected length of the longest common subsequence.
     */
    private static final class DiffScenario {

        private final String before;
        private final String after;
        private final int expectedModifications;
        private final int expectedLcsLength;

        DiffScenario(final String before, final String after,
                final int expectedModifications, final int expectedLcsLength) {
            this.before = before;
            this.after = after;
            this.expectedModifications = expectedModifications;
            this.expectedLcsLength = expectedLcsLength;
        }

        @Override
        public String toString() {
            return "\"" + before + "\" -> \"" + after + "\"";
        }
    }

    /**
     * The comparison scenarios shared by every test. Pairing each input string
     * with its expected results keeps related data together instead of spreading
     * it across parallel index-aligned arrays.
     */
    private static Stream<DiffScenario> scenarios() {
        return Stream.of(
            //               before                after                 modifications  lcsLength
            new DiffScenario("bottle",             "noodle",              6,             3),
            new DiffScenario("nematode knowledge", "empty bottle",        16,            7),
            new DiffScenario("",                   "",                    0,             0),
            new DiffScenario("aa",                 "C",                   3,             0),
            new DiffScenario("prefixed string",    "prefix",              9,             6),
            new DiffScenario("ABCABBA",            "CBABAC",              5,             4),
            new DiffScenario("glop glop",          "pas glop pas glop",   8,             9),
            new DiffScenario("coq",                "ane",                 6,             0),
            new DiffScenario("spider-man",         "klingon",             13,            2));
    }

    @ParameterizedTest(name = "{0}")
    @MethodSource("scenarios")
    void replayingScriptReconstructsAfterString(final DiffScenario scenario) {
        final ExecutionVisitor<Character> visitor = new ExecutionVisitor<>();

        new StringsComparator(scenario.before, scenario.after).getScript().visit(visitor);

        assertEquals(scenario.after, visitor.getString());
    }

    @ParameterizedTest(name = "{0}")
    @MethodSource("scenarios")
    void scriptReportsExpectedModificationCount(final DiffScenario scenario) {
        final StringsComparator comparator = new StringsComparator(scenario.before, scenario.after);

        assertEquals(scenario.expectedModifications, comparator.getScript().getModifications());
    }

    @ParameterizedTest(name = "{0}")
    @MethodSource("scenarios")
    void scriptReportsExpectedLongestCommonSubsequenceLength(final DiffScenario scenario) {
        final StringsComparator comparator = new StringsComparator(scenario.before, scenario.after);

        assertEquals(scenario.expectedLcsLength, comparator.getScript().getLCSLength());
    }
}
