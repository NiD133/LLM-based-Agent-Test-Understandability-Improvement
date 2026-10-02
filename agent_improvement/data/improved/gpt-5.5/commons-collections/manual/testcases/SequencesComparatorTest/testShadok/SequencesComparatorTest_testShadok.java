package org.apache.commons.collections4.sequence;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class SequencesComparatorTest_testShadok {

    private static final int MAX_SHADOK_SENTENCE_LENGTH = 5;

    private static final String[] SHADOK_ALPHABET = { "GA", "BU", "ZO", "MEU" };

    private List<String> before;

    private List<String> after;

    private int[] length;

    private List<Character> sequence(final String string) {
        final List<Character> list = new ArrayList<>();
        for (int i = 0; i < string.length(); ++i) {
            list.add(Character.valueOf(string.charAt(i)));
        }
        return list;
    }

    @BeforeEach
    public void setUp() {
        before = Arrays.asList("bottle", "nematode knowledge", StringUtils.EMPTY, "aa", "prefixed string", "ABCABBA", "glop glop", "coq", "spider-man");
        after = Arrays.asList("noodle", "empty bottle", StringUtils.EMPTY, "C", "prefix", "CBABAC", "pas glop pas glop", "ane", "klingon");
        length = new int[] { 6, 16, 0, 3, 9, 5, 8, 6, 13 };
    }

    @AfterEach
    public void tearDown() {
        before = null;
        after = null;
        length = null;
    }

    @Test
    void testShadok() {
        final List<List<String>> shadokSentences = createShadokSentences();
        final ExecutionVisitor<String> visitor = new ExecutionVisitor<>();

        for (final List<String> sourceSentence : shadokSentences) {
            for (final List<String> targetSentence : shadokSentences) {
                visitor.setList(sourceSentence);

                new SequencesComparator<>(sourceSentence, targetSentence).getScript().visit(visitor);

                assertEquals(concatenate(targetSentence), visitor.getString());
            }
        }
    }

    private List<List<String>> createShadokSentences() {
        List<List<String>> shadokSentences = new ArrayList<>();

        for (int length = 0; length < MAX_SHADOK_SENTENCE_LENGTH; ++length) {
            final List<List<String>> sentencesUpToCurrentLength = new ArrayList<>();
            sentencesUpToCurrentLength.add(new ArrayList<>());

            for (final String word : SHADOK_ALPHABET) {
                for (final List<String> sentence : shadokSentences) {
                    final List<String> longerSentence = new ArrayList<>(sentence);
                    longerSentence.add(word);
                    sentencesUpToCurrentLength.add(longerSentence);
                }
            }

            shadokSentences = sentencesUpToCurrentLength;
        }

        return shadokSentences;
    }

    private String concatenate(final List<String> sentence) {
        final StringBuilder concatenated = new StringBuilder();
        for (final String word : sentence) {
            concatenated.append(word);
        }
        return concatenated.toString();
    }

    private static final class ExecutionVisitor<T> implements CommandVisitor<T> {

        private final StringBuilder string = new StringBuilder();

        private List<T> list;

        private int index;

        void setList(final List<T> list) {
            this.list = list;
            index = 0;
            string.setLength(0);
        }

        String getString() {
            return string.toString();
        }

        @Override
        public void visitDeleteCommand(final T object) {
            ++index;
        }

        @Override
        public void visitInsertCommand(final T object) {
            string.append(object);
        }

        @Override
        public void visitKeepCommand(final T object) {
            string.append(list.get(index));
            ++index;
        }
    }
}
