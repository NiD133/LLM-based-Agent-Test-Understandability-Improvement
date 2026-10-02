package org.apache.commons.text.diff;

import static org.junit.jupiter.api.Assertions.assertEquals;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class StringsComparatorTest_testExecution {

    /** Applies an edit script to reconstruct the target string from the source. */
    private static final class ExecutionVisitor<T> implements CommandVisitor<T> {

        private final StringBuilder result = new StringBuilder();

        public String getString() {
            return result.toString();
        }

        @Override
        public void visitDeleteCommand(final T object) {
            // deleted characters are not appended
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

    private List<String> sourceStrings;
    private List<String> targetStrings;

    @BeforeEach
    public void setUp() {
        sourceStrings = Arrays.asList("bottle", "nematode knowledge", "", "aa", "prefixed string", "ABCABBA", "glop glop", "coq", "spider-man");
        targetStrings = Arrays.asList("noodle", "empty bottle", "", "C", "prefix", "CBABAC", "pas glop pas glop", "ane", "klingon");
    }

    @AfterEach
    public void tearDown() {
        sourceStrings = null;
        targetStrings = null;
    }

    /**
     * Verifies that executing the edit script produced by StringsComparator
     * transforms each source string into the corresponding target string.
     */
    @Test
    void testExecution() {
        for (int i = 0; i < sourceStrings.size(); ++i) {
            String source = sourceStrings.get(i);
            String target = targetStrings.get(i);
            ExecutionVisitor<Character> visitor = new ExecutionVisitor<>();
            new StringsComparator(source, target).getScript().visit(visitor);
            assertEquals(target, visitor.getString(),
                "Edit script should transform \"" + source + "\" into \"" + target + "\"");
        }
    }
}
