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

/**
 * Tests for {@link SequencesComparator}.
 * <p>
 * The comparator turns a "before" sequence into an "after" sequence by
 * producing an {@link EditScript edit script} of delete / insert / keep
 * commands. These tests verify that:
 * <ul>
 *   <li>replaying the script on the "before" sequence reproduces the "after"
 *       sequence ({@link #testExecution}, {@link #testShadok}),</li>
 *   <li>the number of modifications in the script matches the known minimum
 *       ({@link #testLength}), and</li>
 *   <li>the script never contains more modifications than the number of random
 *       edits actually applied ({@link #testMinimal}).</li>
 * </ul>
 */
class SequencesComparatorTest {

    /**
     * A {@link CommandVisitor} that replays an edit script onto a working copy
     * of the "before" sequence. After every command has been visited, the
     * working copy should equal the "after" sequence.
     */
    private static final class ExecutionVisitor<T> implements CommandVisitor<T> {

        /** Working copy of the sequence being transformed. */
        private List<T> elements;

        /** Position in {@link #elements} that the next command applies to. */
        private int cursor;

        /** Concatenates the current working copy into a single String. */
        public String getString() {
            final StringBuilder buffer = new StringBuilder();
            for (final T element : elements) {
                buffer.append(element);
            }
            return buffer.toString();
        }

        /** Resets the working copy to a fresh transformation of the given sequence. */
        public void setList(final List<T> initialSequence) {
            elements = new ArrayList<>(initialSequence);
            cursor = 0;
        }

        @Override
        public void visitDeleteCommand(final T object) {
            // Drop the element at the cursor; the cursor now points at the next element.
            elements.remove(cursor);
        }

        @Override
        public void visitInsertCommand(final T object) {
            // Insert the new element at the cursor and step past it.
            elements.add(cursor++, object);
        }

        @Override
        public void visitKeepCommand(final T object) {
            // Leave the element untouched and step past it.
            ++cursor;
        }

    }

    /** Source sequences (compared element-by-element against {@link #afterStrings}). */
    private List<String> beforeStrings;

    /** Target sequences that each "before" string should be transformed into. */
    private List<String> afterStrings;

    /** Expected minimum number of modifications to turn each "before" into its "after". */
    private int[] expectedModifications;

    /** Splits a String into a list of its characters. */
    private List<Character> sequence(final String string) {
        final List<Character> list = new ArrayList<>();
        for (int i = 0; i < string.length(); ++i) {
            list.add(Character.valueOf(string.charAt(i)));
        }
        return list;
    }

    @BeforeEach
    public void setUp() {

        // beforeStrings[i], afterStrings[i] and expectedModifications[i] form one
        // test case each: transforming beforeStrings[i] into afterStrings[i] needs
        // exactly expectedModifications[i] delete/insert operations.
        beforeStrings = Arrays.asList(
            "bottle",
            "nematode knowledge",
            StringUtils.EMPTY,
            "aa",
            "prefixed string",
            "ABCABBA",
            "glop glop",
            "coq",
            "spider-man");

        afterStrings = Arrays.asList(
            "noodle",
            "empty bottle",
            StringUtils.EMPTY,
            "C",
            "prefix",
            "CBABAC",
            "pas glop pas glop",
            "ane",
            "klingon");

        expectedModifications = new int[] {
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
        beforeStrings = null;
        afterStrings = null;
        expectedModifications = null;
    }

    /**
     * Replaying each edit script onto its "before" sequence must reproduce the
     * corresponding "after" sequence.
     */
    @Test
    void testExecution() {
        final ExecutionVisitor<Character> visitor = new ExecutionVisitor<>();
        for (int i = 0; i < beforeStrings.size(); ++i) {
            visitor.setList(sequence(beforeStrings.get(i)));

            final EditScript<Character> script =
                    new SequencesComparator<>(sequence(beforeStrings.get(i)),
                            sequence(afterStrings.get(i))).getScript();
            script.visit(visitor);

            assertEquals(afterStrings.get(i), visitor.getString());
        }
    }

    /**
     * The number of modifications in each edit script must match the known
     * minimum for that pair of sequences.
     */
    @Test
    void testLength() {
        for (int i = 0; i < beforeStrings.size(); ++i) {
            final SequencesComparator<Character> comparator =
                    new SequencesComparator<>(sequence(beforeStrings.get(i)),
                            sequence(afterStrings.get(i)));
            assertEquals(expectedModifications[i], comparator.getScript().getModifications());
        }
    }

    /**
     * Applying a known number of random edits to a sequence and then comparing
     * must never yield more modifications than were actually applied: the
     * algorithm finds a script at least as short as the edits that created the
     * difference.
     */
    @Test
    void testMinimal() {
        final String[] shadokAlphabet = {
            "GA",
            "BU",
            "ZO",
            "MEU"
        };
        // A fixed reference sentence built from the alphabet above.
        final List<String> referenceSentence = new ArrayList<>();
        referenceSentence.add(shadokAlphabet[0]);
        referenceSentence.add(shadokAlphabet[2]);
        referenceSentence.add(shadokAlphabet[3]);
        referenceSentence.add(shadokAlphabet[1]);
        referenceSentence.add(shadokAlphabet[0]);
        referenceSentence.add(shadokAlphabet[0]);
        referenceSentence.add(shadokAlphabet[2]);
        referenceSentence.add(shadokAlphabet[1]);
        referenceSentence.add(shadokAlphabet[3]);
        referenceSentence.add(shadokAlphabet[0]);
        referenceSentence.add(shadokAlphabet[2]);
        referenceSentence.add(shadokAlphabet[1]);
        referenceSentence.add(shadokAlphabet[3]);
        referenceSentence.add(shadokAlphabet[2]);
        referenceSentence.add(shadokAlphabet[2]);
        referenceSentence.add(shadokAlphabet[0]);
        referenceSentence.add(shadokAlphabet[1]);
        referenceSentence.add(shadokAlphabet[3]);
        referenceSentence.add(shadokAlphabet[0]);
        referenceSentence.add(shadokAlphabet[3]);

        // Fixed seed keeps the random edits reproducible across runs.
        final Random random = new Random(4564634237452342L);

        final List<String> editedSentence = new ArrayList<>();
        for (int editCount = 0; editCount <= 40; editCount += 5) {
            // Start from a fresh copy of the reference, then apply editCount
            // random insertions/removals.
            editedSentence.clear();
            editedSentence.addAll(referenceSentence);
            for (int i = 0; i < editCount; i++) {
                if (random.nextInt(2) == 0) {
                    editedSentence.add(random.nextInt(editedSentence.size() + 1),
                            shadokAlphabet[random.nextInt(4)]);
                } else {
                    editedSentence.remove(random.nextInt(editedSentence.size()));
                }
            }

            final SequencesComparator<String> comparator =
                    new SequencesComparator<>(referenceSentence, editedSentence);
            assertTrue(comparator.getScript().getModifications() <= editCount);
        }
    }

    /**
     * Exhaustive check: for every pair drawn from all Shadok sentences up to
     * length 4, replaying the edit script must transform the first sentence
     * into the second.
     */
    @Test
    void testShadok() {
        final int maxLength = 5;
        final String[] shadokAlphabet = {
            "GA",
            "BU",
            "ZO",
            "MEU"
        };

        // Build every sentence of length 0..maxLength-1 over the alphabet by
        // repeatedly appending each letter to the sentences found so far.
        List<List<String>> shadokSentences = new ArrayList<>();
        for (int length = 0; length < maxLength; ++length) {
            final List<List<String>> nextGeneration = new ArrayList<>();
            nextGeneration.add(new ArrayList<>());
            for (final String letter : shadokAlphabet) {
                for (final List<String> sentence : shadokSentences) {
                    final List<String> extendedSentence = new ArrayList<>(sentence);
                    extendedSentence.add(letter);
                    nextGeneration.add(extendedSentence);
                }
            }
            shadokSentences = nextGeneration;
        }

        final ExecutionVisitor<String> visitor = new ExecutionVisitor<>();

        for (final List<String> sourceSentence : shadokSentences) {
            for (final List<String> targetSentence : shadokSentences) {
                visitor.setList(sourceSentence);
                new SequencesComparator<>(sourceSentence,
                        targetSentence).getScript().visit(visitor);

                final StringBuilder expected = new StringBuilder();
                for (final String word : targetSentence) {
                    expected.append(word);
                }
                assertEquals(expected.toString(), visitor.getString());
            }
        }
    }

}
