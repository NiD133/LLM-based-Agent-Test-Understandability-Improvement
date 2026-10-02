package org.apache.commons.lang3;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Arrays;

import org.junit.jupiter.api.Test;

public class AppendableJoinerTest_testDelimiterStringBuilder extends AbstractLangTest {

    @Test
    void testDelimiterStringBuilder() {
        // A joiner that separates each appended element with a "." delimiter.
        final AppendableJoiner<Object> joiner = AppendableJoiner.builder().setDelimiter(".").get();

        // Start with "A" already in the target StringBuilder.
        final StringBuilder target = new StringBuilder("A");

        // Joining the varargs "B", "C" appends "B.C" after the existing "A".
        // join into a StringBuilder does not throw IOException.
        assertEquals("AB.C", joiner.join(target, "B", "C").toString());

        // Append "1" directly to the target, then join an Iterable of "D", "E".
        // This appends "D.E" after "AB.C1", giving "AB.C1D.E".
        target.append("1");
        assertEquals("AB.C1D.E", joiner.join(target, Arrays.asList("D", "E")).toString());
    }
}
