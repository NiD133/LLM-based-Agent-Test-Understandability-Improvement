package org.apache.commons.lang3;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.StringWriter;
import java.util.Arrays;

import org.apache.commons.lang3.text.StrBuilder;
import org.apache.commons.text.TextStringBuilder;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

public class AppendableJoinerTest_testDelimiterAppendable extends AbstractLangTest {

    /**
     * Verifies that {@link AppendableJoiner#joinA} appends joined elements onto an existing
     * {@link Appendable}, separating the elements with the configured delimiter ("."), while
     * preserving whatever content the target already holds.
     *
     * <p>The test runs against several {@link Appendable} implementations to confirm the joiner
     * behaves identically regardless of the concrete target type.</p>
     */
    @SuppressWarnings("deprecation") // StrBuilder is deprecated but still supported as an Appendable target.
    @ParameterizedTest
    @ValueSource(classes = { StringBuilder.class, StringBuffer.class, StringWriter.class, StrBuilder.class, TextStringBuilder.class })
    void testDelimiterAppendable(final Class<? extends Appendable> appendableType) throws Exception {
        final AppendableJoiner<Object> joiner = AppendableJoiner.builder().setDelimiter(".").get();

        // Start with a target that already contains "A".
        final Appendable target = appendableType.newInstance();
        target.append("A");

        // Joining the array ["B", "C"] appends "B.C" after the existing "A" -> "AB.C".
        assertEquals("AB.C", joiner.joinA(target, "B", "C").toString());

        // Append "1" directly, then join the list ["D", "E"] -> existing "AB.C" + "1" + "D.E".
        target.append("1");
        assertEquals("AB.C1D.E", joiner.joinA(target, Arrays.asList("D", "E")).toString());
    }
}
