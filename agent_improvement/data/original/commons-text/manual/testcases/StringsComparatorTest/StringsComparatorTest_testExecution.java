package org.apache.commons.text.diff;

import static org.junit.jupiter.api.Assertions.assertEquals;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class StringsComparatorTest_testExecution {

    private List<String> before;

    private List<String> after;

    private int[] length;

    private int[] lcs;

    @BeforeEach
    public void setUp() {
        before = Arrays.asList("bottle", "nematode knowledge", "", "aa", "prefixed string", "ABCABBA", "glop glop", "coq", "spider-man");
        after = Arrays.asList("noodle", "empty bottle", "", "C", "prefix", "CBABAC", "pas glop pas glop", "ane", "klingon");
        length = new int[] { 6, 16, 0, 3, 9, 5, 8, 6, 13 };
        lcs = new int[] { 3, 7, 0, 0, 6, 4, 9, 0, 2 };
    }

    @AfterEach
    public void tearDown() {
        before = null;
        after = null;
        length = null;
    }

    @Test
    void testExecution() {
        for (int i = 0; i < before.size(); ++i) {
            final ExecutionVisitor<Character> ev = new ExecutionVisitor<>();
            new StringsComparator(before.get(i), after.get(i)).getScript().visit(ev);
            assertEquals(after.get(i), ev.getString());
        }
    }
}
