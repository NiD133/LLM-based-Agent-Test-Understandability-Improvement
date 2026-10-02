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

    /**
     * A CommandVisitor that reconstructs the target string by collecting inserted
     * and kept characters while ignoring deleted ones. After visiting an EditScript,
     * {@link #getString()} returns the result of applying the script to the source.
     */
    private static final class ExecutionVisitor<T> implements CommandVisitor<T> {

        private final StringBuilder result;

        ExecutionVisitor() {
            result = new StringBuilder();
        }

        public String getString() {
            return result.toString();
        }

        @Override
        public void visitDeleteCommand(final T object) {
            // deleted characters are not included in the output
        }

        @Override
        public void visitInsertCommand(final T object) {
            result.append(object);
        }

        @Override
        public void visitKeepCommand(final T object) {
            result.append(object);
        }
    }

    // Parallel test-case arrays: each index i represents one (before[i], after[i]) pair.
    private List<String> before;
    private List<String> after;

    /** Number of edit operations (inserts + deletes) required to transform before[i] into after[i]. */
    private int[] expectedModifications;

    /** Length of the longest common subsequence between before[i] and after[i]. */
    private int[] expectedLcsLength;

    @BeforeEach
    public void setUp() {
        before = Arrays.asList(
            "bottle",             // word transformation
            "nematode knowledge", // long phrase with many edits
            "",                   // empty source
            "aa",                 // source is completely replaced
            "prefixed string",    // target is a prefix of source
            "ABCABBA",            // classic Myers-algorithm example
            "glop glop",          // repetitive word pattern
            "coq",                // full replacement, no common characters
            "spider-man");        // completely different word

        after = Arrays.asList(
            "noodle",
            "empty bottle",
            "",
            "C",
            "prefix",
            "CBABAC",
            "pas glop pas glop",
            "ane",
            "klingon");

        expectedModifications = new int[] {
            6,   // bottle      -> noodle
            16,  // nematode knowledge -> empty bottle
            0,   // (empty)     -> (empty)
            3,   // aa          -> C
            9,   // prefixed string -> prefix
            5,   // ABCABBA     -> CBABAC
            8,   // glop glop   -> pas glop pas glop
            6,   // coq         -> ane
            13   // spider-man  -> klingon
        };

        expectedLcsLength = new int[] {
            3,  // bottle      / noodle
            7,  // nematode knowledge / empty bottle
            0,  // (empty)     / (empty)
            0,  // aa          / C
            6,  // prefixed string / prefix
            4,  // ABCABBA     / CBABAC
            9,  // glop glop   / pas glop pas glop
            0,  // coq         / ane
            2   // spider-man  / klingon
        };
    }

    @AfterEach
    public void tearDown() {
        before = null;
        after  = null;
        expectedModifications = null;
    }

    /**
     * Verifies that applying the edit script produced by StringsComparator
     * actually transforms the source string into the target string.
     */
    @Test
    void testExecution() {
        for (int i = 0; i < before.size(); ++i) {
            final ExecutionVisitor<Character> visitor = new ExecutionVisitor<>();
            new StringsComparator(before.get(i), after.get(i)).getScript().visit(visitor);
            assertEquals(after.get(i), visitor.getString());
        }
    }

    /**
     * Verifies that the number of modifications (inserts + deletes) in the edit
     * script matches the expected edit distance for each string pair.
     */
    @Test
    void testLength() {
        for (int i = 0; i < before.size(); ++i) {
            final StringsComparator comparator = new StringsComparator(before.get(i), after.get(i));
            assertEquals(expectedModifications[i], comparator.getScript().getModifications());
        }
    }

    /**
     * Verifies that the length of the longest common subsequence reported by the
     * edit script matches the expected LCS length for each string pair.
     */
    @Test
    void testLongestCommonSubsequence() {
        for (int i = 0; i < before.size(); ++i) {
            final StringsComparator comparator = new StringsComparator(before.get(i), after.get(i));
            assertEquals(expectedLcsLength[i], comparator.getScript().getLCSLength());
        }
    }
}
