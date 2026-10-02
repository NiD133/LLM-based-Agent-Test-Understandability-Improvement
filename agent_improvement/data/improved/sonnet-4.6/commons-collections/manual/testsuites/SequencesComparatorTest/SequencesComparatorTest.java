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
package org.apache.commons.collections4.sequence;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class SequencesComparatorTest {

    /**
     * A CommandVisitor that applies the edit script to a working list, allowing
     * the result to be compared against the expected "after" sequence.
     */
    private static final class ExecutionVisitor<T> implements CommandVisitor<T> {

        private List<T> list;
        private int index;

        public String getString() {
            final StringBuilder buffer = new StringBuilder();
            for (final T c : list) {
                buffer.append(c);
            }
            return buffer.toString();
        }

        public void setList(final List<T> array) {
            list = new ArrayList<>(array);
            index = 0;
        }

        @Override
        public void visitDeleteCommand(final T object) {
            list.remove(index);
        }

        @Override
        public void visitInsertCommand(final T object) {
            list.add(index++, object);
        }

        @Override
        public void visitKeepCommand(final T object) {
            ++index;
        }

    }

    // Parallel test-case arrays: before[i], after[i], and length[i] form one test case.
    // length[i] is the expected minimum number of edit operations to transform before[i] into after[i].
    private List<String> before;
    private List<String> after;
    private int[]        length;

    /** Converts a string into a list of its characters. */
    private List<Character> sequence(final String string) {
        final List<Character> list = new ArrayList<>();
        for (int i = 0; i < string.length(); ++i) {
            list.add(string.charAt(i));
        }
        return list;
    }

    @BeforeEach
    public void setUp() {

        before = Arrays.asList(
            "bottle",
            "nematode knowledge",
            StringUtils.EMPTY,
            "aa",
            "prefixed string",
            "ABCABBA",
            "glop glop",
            "coq",
            "spider-man");

        after = Arrays.asList(
            "noodle",
            "empty bottle",
            StringUtils.EMPTY,
            "C",
            "prefix",
            "CBABAC",
            "pas glop pas glop",
            "ane",
            "klingon");

        length = new int[] {
            6,
            16,
            0,
            3,
            9,
            5,
            8,
            6,
            13
        };

    }

    @AfterEach
    public void tearDown() {
        before = null;
        after  = null;
        length = null;
    }

    /**
     * Verifies that applying the edit script produced by SequencesComparator
     * to the "before" sequence yields the "after" sequence exactly.
     */
    @Test
    void testExecution() {
        final ExecutionVisitor<Character> ev = new ExecutionVisitor<>();
        for (int i = 0; i < before.size(); ++i) {
            ev.setList(sequence(before.get(i)));
            new SequencesComparator<>(sequence(before.get(i)),
                    sequence(after.get(i))).getScript().visit(ev);
            assertEquals(after.get(i), ev.getString());
        }
    }

    /**
     * Verifies that the edit script contains the expected number of modifications
     * (inserts + deletes) for each before/after pair.
     */
    @Test
    void testLength() {
        for (int i = 0; i < before.size(); ++i) {
            final SequencesComparator<Character> comparator =
                    new SequencesComparator<>(sequence(before.get(i)),
                            sequence(after.get(i)));
            assertEquals(length[i], comparator.getScript().getModifications());
        }
    }

    /**
     * Verifies that the edit script is minimal: after applying at most {@code nbCom}
     * random modifications, the comparator never produces more than {@code nbCom}
     * edits to recover the result.
     */
    @Test
    void testMinimal() {
        final String[] shadokAlph = { "GA", "BU", "ZO", "MEU" };

        // Fixed starting sentence built from the Shadok alphabet
        final List<String> sentenceBefore = new ArrayList<>(Arrays.asList(
            "GA", "ZO", "MEU", "BU", "GA", "GA", "ZO", "BU", "MEU", "GA",
            "ZO", "BU", "MEU", "ZO", "ZO", "GA", "BU", "MEU", "GA", "MEU"
        ));

        final Random random = new Random(4564634237452342L);

        for (int nbCom = 0; nbCom <= 40; nbCom += 5) {
            final List<String> sentenceAfter = new ArrayList<>(sentenceBefore);
            for (int i = 0; i < nbCom; i++) {
                final boolean shouldInsert = random.nextInt(2) == 0;
                if (shouldInsert) {
                    sentenceAfter.add(random.nextInt(sentenceAfter.size() + 1), shadokAlph[random.nextInt(4)]);
                } else {
                    sentenceAfter.remove(random.nextInt(sentenceAfter.size()));
                }
            }

            final SequencesComparator<String> comparator = new SequencesComparator<>(sentenceBefore, sentenceAfter);
            assertTrue(comparator.getScript().getModifications() <= nbCom);
        }
    }

    /**
     * Exhaustively checks all pairs of Shadok sentences (up to length {@code lgMax})
     * by verifying that applying the edit script transforms one into the other.
     */
    @Test
    void testShadok() {
        final int lgMax = 5;
        final String[] shadokAlph = { "GA", "BU", "ZO", "MEU" };

        // Build all Shadok sentences of length 0..lgMax by iteratively appending each word
        List<List<String>> shadokSentences = new ArrayList<>();
        for (int lg = 0; lg < lgMax; ++lg) {
            final List<List<String>> newTab = new ArrayList<>();
            newTab.add(new ArrayList<>());
            for (final String element : shadokAlph) {
                for (final List<String> sentence : shadokSentences) {
                    final List<String> newSentence = new ArrayList<>(sentence);
                    newSentence.add(element);
                    newTab.add(newSentence);
                }
            }
            shadokSentences = newTab;
        }

        final ExecutionVisitor<String> ev = new ExecutionVisitor<>();

        for (final List<String> sourceSentence : shadokSentences) {
            for (final List<String> targetSentence : shadokSentences) {
                ev.setList(sourceSentence);
                new SequencesComparator<>(sourceSentence, targetSentence).getScript().visit(ev);
                assertEquals(String.join("", targetSentence), ev.getString());
            }
        }
    }

}
