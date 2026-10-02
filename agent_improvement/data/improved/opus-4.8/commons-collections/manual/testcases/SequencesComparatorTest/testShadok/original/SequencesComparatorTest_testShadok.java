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

public class SequencesComparatorTest_testShadok {

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
        final int lgMax = 5;
        final String[] shadokAlph = { "GA", "BU", "ZO", "MEU" };
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
        for (final List<String> element : shadokSentences) {
            for (final List<String> shadokSentence : shadokSentences) {
                ev.setList(element);
                new SequencesComparator<>(element, shadokSentence).getScript().visit(ev);
                final StringBuilder concat = new StringBuilder();
                for (final String s : shadokSentence) {
                    concat.append(s);
                }
                assertEquals(concat.toString(), ev.getString());
            }
        }
    }
}
