package org.apache.commons.lang3;

import static org.junit.jupiter.api.Assertions.assertEquals;
import java.util.Arrays;
import org.junit.jupiter.api.Test;

public class AppendableJoinerTest_testDelimiterStringBuilder extends AbstractLangTest {

    @Test
    void testDelimiterStringBuilder() {
        // Build a joiner that inserts "." between each element; no prefix or suffix
        final AppendableJoiner<Object> joiner = AppendableJoiner.builder().setDelimiter(".").get();

        // Start the builder with "A", then join varargs "B" and "C" directly into it.
        // join(StringBuilder, T...) wraps any IOException internally, so no checked exception is thrown.
        final StringBuilder sbuilder = new StringBuilder("A");
        final String resultAfterVarargs = joiner.join(sbuilder, "B", "C").toString();
        assertEquals("AB.C", resultAfterVarargs,
                "varargs join should append delimiter-separated elements to the existing StringBuilder content");

        // Append "1" so the builder now holds "AB.C1", then join an Iterable ["D","E"].
        // The Iterable overload also returns the same StringBuilder, letting calls be chained.
        sbuilder.append("1");
        final String resultAfterIterable = joiner.join(sbuilder, Arrays.asList("D", "E")).toString();
        assertEquals("AB.C1D.E", resultAfterIterable,
                "Iterable join should continue appending into the StringBuilder accumulated so far");
    }
}
