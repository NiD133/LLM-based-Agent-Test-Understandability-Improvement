package org.apache.commons.collections4.sequence;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class SequencesComparatorTest_testExecution {

    private List<String> sourceStrings;

    private List<String> targetStrings;

    private int[] expectedScriptLengths;

    private List<Character> sequence(final String string) {
        final List<Character> characters = new ArrayList<>();
        for (int i = 0; i < string.length(); ++i) {
            characters.add(Character.valueOf(string.charAt(i)));
        }
        return characters;
    }

    @BeforeEach
    public void setUp() {
        sourceStrings = Arrays.asList(
                "bottle",
                "nematode knowledge",
                StringUtils.EMPTY,
                "aa",
                "prefixed string",
                "ABCABBA",
                "glop glop",
                "coq",
                "spider-man");
        targetStrings = Arrays.asList(
                "noodle",
                "empty bottle",
                StringUtils.EMPTY,
                "C",
                "prefix",
                "CBABAC",
                "pas glop pas glop",
                "ane",
                "klingon");
        expectedScriptLengths = new int[] { 6, 16, 0, 3, 9, 5, 8, 6, 13 };
    }

    @AfterEach
    public void tearDown() {
        sourceStrings = null;
        targetStrings = null;
        expectedScriptLengths = null;
    }

    @Test
    void testExecution() {
        final ExecutionVisitor<Character> visitor = new ExecutionVisitor<>();
        for (int i = 0; i < sourceStrings.size(); ++i) {
            assertScriptTransformsSourceIntoTarget(visitor, sourceStrings.get(i), targetStrings.get(i));
        }
    }

    private void assertScriptTransformsSourceIntoTarget(
            final ExecutionVisitor<Character> visitor,
            final String source,
            final String target) {
        visitor.setList(sequence(source));
        new SequencesComparator<>(sequence(source), sequence(target)).getScript().visit(visitor);
        assertEquals(target, visitor.getString());
    }

    private static final class ExecutionVisitor<T> implements CommandVisitor<T> {

        private List<T> list;

        private int index;

        void setList(final List<T> list) {
            this.list = new ArrayList<>(list);
            index = 0;
        }

        String getString() {
            final StringBuilder builder = new StringBuilder();
            for (final T element : list) {
                builder.append(element);
            }
            return builder.toString();
        }

        @Override
        public void visitDeleteCommand(final T object) {
            list.remove(index);
        }

        @Override
        public void visitInsertCommand(final T object) {
            list.add(index, object);
            ++index;
        }

        @Override
        public void visitKeepCommand(final T object) {
            ++index;
        }
    }
}
