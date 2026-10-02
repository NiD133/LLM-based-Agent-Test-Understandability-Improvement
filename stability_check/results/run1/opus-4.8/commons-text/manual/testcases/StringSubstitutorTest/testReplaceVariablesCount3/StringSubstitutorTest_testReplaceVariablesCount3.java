package org.apache.commons.text;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.mutable.MutableObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Tests that {@link StringSubstitutor} substitutes a variable that occurs three
 * times in the same template, across every input type it accepts.
 */
public class StringSubstitutorTest_testReplaceVariablesCount3 {

    private static final String ANIMAL = "quick brown fox";

    private static final String TARGET = "lazy dog";

    /** Variable name to value mapping used by the substitutor under test. */
    private Map<String, String> values;

    @BeforeEach
    public void setUp() {
        values = new HashMap<>();
        // shortest possible keys and values
        values.put("a", "1");
        values.put("aa", "11");
        values.put("aaa", "111");
        values.put("b", "2");
        values.put("bb", "22");
        values.put("bbb", "222");
        values.put("a2b", "b");
        // normal keys and values
        values.put("animal", ANIMAL);
        values.put("target", TARGET);
    }

    /**
     * Substitutes {@code template} through every replacement API offered by
     * {@link StringSubstitutor} and asserts that each one yields
     * {@code expectedResult}.
     */
    private void assertReplacedToInAllApis(final String expectedResult, final String template) throws IOException {
        final StringSubstitutor substitutor = new StringSubstitutor(values);

        // replace(...) returning a new String, one call per accepted input type
        assertEquals(expectedResult, substitutor.replace(template));
        assertEquals(expectedResult, substitutor.replace(template.toCharArray()));
        assertEquals(expectedResult, substitutor.replace(new StringBuffer(template)));
        assertEquals(expectedResult, substitutor.replace(new StringBuilder(template)));
        assertEquals(expectedResult, substitutor.replace(new TextStringBuilder(template)));
        // an arbitrary Object whose toString() is the template
        assertEquals(expectedResult, substitutor.replace(new MutableObject<>(template)));

        // replaceIn(...) mutating the argument in place and reporting whether it changed
        final StringBuffer stringBuffer = new StringBuffer(template);
        assertTrue(substitutor.replaceIn(stringBuffer), template);
        assertEquals(expectedResult, stringBuffer.toString());

        final StringBuilder stringBuilder = new StringBuilder(template);
        assertTrue(substitutor.replaceIn(stringBuilder));
        assertEquals(expectedResult, stringBuilder.toString());

        final TextStringBuilder textStringBuilder = new TextStringBuilder(template);
        assertTrue(substitutor.replaceIn(textStringBuilder));
        assertEquals(expectedResult, textStringBuilder.toString());
    }

    /**
     * Tests replacing a single variable repeated three times.
     */
    @Test
    void testReplaceVariablesCount3() throws IOException {
        assertReplacedToInAllApis("121", "${a}${b}${a}");
        assertReplacedToInAllApis("112211", "${aa}${bb}${aa}");
        assertReplacedToInAllApis(ANIMAL + ANIMAL + ANIMAL, "${animal}${animal}${animal}");
        assertReplacedToInAllApis(TARGET + TARGET + TARGET, "${target}${target}${target}");
    }
}
