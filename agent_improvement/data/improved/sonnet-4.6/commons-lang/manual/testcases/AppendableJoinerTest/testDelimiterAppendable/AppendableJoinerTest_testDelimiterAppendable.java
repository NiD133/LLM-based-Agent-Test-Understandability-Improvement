package org.apache.commons.lang3;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.StringWriter;
import java.util.Arrays;

import org.apache.commons.lang3.AppendableJoiner.Builder;
import org.apache.commons.lang3.text.StrBuilder;
import org.apache.commons.text.TextStringBuilder;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

public class AppendableJoinerTest_testDelimiterAppendable extends AbstractLangTest {

    /**
     * Verifies that joinA appends elements with a "." delimiter directly into an existing Appendable,
     * without creating an intermediate String. The test runs against all supported Appendable
     * implementations to ensure consistent behaviour across types.
     */
    @SuppressWarnings("deprecation")
    @ParameterizedTest(name = "joinA appends into {0}")
    @DisplayName("joinA uses delimiter and accumulates into the provided Appendable")
    @ValueSource(classes = { StringBuilder.class, StringBuffer.class, StringWriter.class, StrBuilder.class, TextStringBuilder.class })
    void testDelimiterAppendable(final Class<? extends Appendable> appendableClass) throws Exception {
        // A joiner that separates elements with a dot and has no prefix/suffix.
        final AppendableJoiner<Object> dotJoiner = AppendableJoiner.builder().setDelimiter(".").get();

        // Start with pre-existing content "A" to confirm joinA appends rather than replaces.
        final Appendable appendable = appendableClass.newInstance();
        appendable.append("A");

        // joinA("B", "C") appends "B.C" → the appendable now holds "AB.C"
        assertEquals("AB.C", dotJoiner.joinA(appendable, "B", "C").toString());

        // Append "1" to confirm the same Appendable instance is reused across calls.
        appendable.append("1");

        // joinA(["D","E"]) appends "D.E" → the appendable now holds "AB.C1D.E"
        assertEquals("AB.C1D.E", dotJoiner.joinA(appendable, Arrays.asList("D", "E")).toString());
    }
}
