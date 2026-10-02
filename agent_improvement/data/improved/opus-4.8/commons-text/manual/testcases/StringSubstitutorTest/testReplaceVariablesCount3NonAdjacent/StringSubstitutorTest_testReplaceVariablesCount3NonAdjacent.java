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
 * Tests {@link StringSubstitutor} replacement when the same template is supplied
 * through every accepted input type (String, char[], StringBuffer, StringBuilder,
 * TextStringBuilder and a generic Object), checking that a template containing
 * three non-adjacent variables is resolved consistently in each case.
 */
public class StringSubstitutorTest_testReplaceVariablesCount3NonAdjacent {

    private static final String ANIMAL = "quick brown fox";
    private static final String TARGET = "lazy dog";

    /** Variable name -&gt; value lookup shared by every assertion. */
    private Map<String, String> values;

    @BeforeEach
    public void setUp() {
        values = new HashMap<>();
        // Short keys/values exercising single- and double-character names.
        values.put("a", "1");
        values.put("aa", "11");
        values.put("b", "2");
        values.put("bb", "22");
        // Normal, word-length keys/values.
        values.put("animal", ANIMAL);
        values.put("target", TARGET);
    }

    /**
     * Feeds {@code template} to the substitutor through each supported input type
     * and asserts that every overload produces {@code expectedResult}.
     */
    private void assertReplacedThroughAllInputTypes(final String expectedResult, final String template)
            throws IOException {
        final StringSubstitutor sub = new StringSubstitutor(values);

        // The "replace" overloads return a new String, leaving the input untouched.
        assertEquals(expectedResult, sub.replace(template));
        assertEquals(expectedResult, sub.replace(template.toCharArray()));
        assertEquals(expectedResult, sub.replace(new StringBuffer(template)));
        assertEquals(expectedResult, sub.replace(new StringBuilder(template)));
        assertEquals(expectedResult, sub.replace(new TextStringBuilder(template)));
        // replace(Object) uses the argument's toString(), which here is the template.
        assertEquals(expectedResult, sub.replace(new MutableObject<>(template)));

        // The "replaceIn" overloads mutate the supplied buffer in place and return
        // true when at least one variable was replaced.
        final StringBuffer buffer = new StringBuffer(template);
        assertTrue(sub.replaceIn(buffer));
        assertEquals(expectedResult, buffer.toString());

        final StringBuilder builder = new StringBuilder(template);
        assertTrue(sub.replaceIn(builder));
        assertEquals(expectedResult, builder.toString());

        final TextStringBuilder textBuilder = new TextStringBuilder(template);
        assertTrue(sub.replaceIn(textBuilder));
        assertEquals(expectedResult, textBuilder.toString());
    }

    /**
     * Each template holds three non-adjacent variables (separated by spaces); the
     * same variable may repeat, so the expected output simply substitutes each one.
     */
    @Test
    void testReplaceVariablesCount3NonAdjacent() throws IOException {
        assertReplacedThroughAllInputTypes("1 2 1", "${a} ${b} ${a}");
        assertReplacedThroughAllInputTypes("11 22 11", "${aa} ${bb} ${aa}");
        assertReplacedThroughAllInputTypes(ANIMAL + " " + ANIMAL + " " + ANIMAL, "${animal} ${animal} ${animal}");
        assertReplacedThroughAllInputTypes(TARGET + " " + TARGET + " " + TARGET, "${target} ${target} ${target}");
    }
}
