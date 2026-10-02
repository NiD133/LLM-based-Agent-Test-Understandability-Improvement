package org.apache.commons.lang3;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Arrays;

import org.junit.jupiter.api.Test;

public class AppendableJoinerTest_testAllBuilderPropertiesStringBuilder extends AbstractLangTest {

    /**
     * Verifies that all builder properties (prefix, delimiter, suffix, and custom element appender)
     * are applied correctly when joining into a StringBuilder, for both varargs and Iterable inputs.
     *
     * The joiner is configured with:
     *   prefix    = "<"
     *   delimiter = "."
     *   suffix    = ">"
     *   appender  = converts each element via String.valueOf(e)
     */
    @Test
    void testAllBuilderPropertiesStringBuilder() {
        final AppendableJoiner<Object> joiner = AppendableJoiner.builder()
                .setPrefix("<")
                .setDelimiter(".")
                .setSuffix(">")
                .setElementAppender((a, e) -> a.append(String.valueOf(e)))
                .get();

        // Start with an existing value in the StringBuilder to confirm joining appends in-place.
        final StringBuilder sbuilder = new StringBuilder("A");

        // Join varargs "B" and "C" — expect prefix/delimiter/suffix applied around the elements.
        joiner.join(sbuilder, "B", "C");
        assertEquals("A<B.C>", sbuilder.toString());

        // Append additional content between two join calls to confirm the builder is reused correctly.
        sbuilder.append("1");

        // Join an Iterable ["D", "E"] — expect the same formatting applied to the second join.
        joiner.join(sbuilder, Arrays.asList("D", "E"));
        assertEquals("A<B.C>1<D.E>", sbuilder.toString());
    }
}
