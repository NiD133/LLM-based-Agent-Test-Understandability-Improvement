package org.apache.commons.text.diff;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class StringsComparatorTest_testExecution {

    private List<ComparisonCase> comparisonCases;

    @BeforeEach
    public void setUp() {
        comparisonCases = Arrays.asList(
                new ComparisonCase("bottle", "noodle"),
                new ComparisonCase("nematode knowledge", "empty bottle"),
                new ComparisonCase("", ""),
                new ComparisonCase("aa", "C"),
                new ComparisonCase("prefixed string", "prefix"),
                new ComparisonCase("ABCABBA", "CBABAC"),
                new ComparisonCase("glop glop", "pas glop pas glop"),
                new ComparisonCase("coq", "ane"),
                new ComparisonCase("spider-man", "klingon"));
    }

    @AfterEach
    public void tearDown() {
        comparisonCases = null;
    }

    @Test
    void testExecution() {
        for (final ComparisonCase comparisonCase : comparisonCases) {
            final ExecutionVisitor<Character> visitor = new ExecutionVisitor<>();

            new StringsComparator(comparisonCase.before, comparisonCase.after)
                    .getScript()
                    .visit(visitor);

            assertEquals(comparisonCase.after, visitor.getString());
        }
    }

    private static final class ComparisonCase {

        private final String before;
        private final String after;

        private ComparisonCase(final String before, final String after) {
            this.before = before;
            this.after = after;
        }
    }

    private static final class ExecutionVisitor<T> implements CommandVisitor<T> {

        private final StringBuilder result = new StringBuilder();

        @Override
        public void visitDeleteCommand(final T object) {
            // Deletions remove characters from the source and do not appear in the transformed string.
        }

        @Override
        public void visitInsertCommand(final T object) {
            result.append(object);
        }

        @Override
        public void visitKeepCommand(final T object) {
            result.append(object);
        }

        private String getString() {
            return result.toString();
        }
    }
}
