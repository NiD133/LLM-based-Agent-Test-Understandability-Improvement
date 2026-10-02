package org.apache.commons.lang3;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.StringWriter;
import java.util.Arrays;

import org.apache.commons.lang3.text.StrBuilder;
import org.apache.commons.text.TextStringBuilder;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

public class AppendableJoinerTest_testDelimiterAppendable extends AbstractLangTest {

    @SuppressWarnings("deprecation")
    @ParameterizedTest
    @ValueSource(classes = { StringBuilder.class, StringBuffer.class, StringWriter.class, StrBuilder.class, TextStringBuilder.class })
    void testDelimiterAppendable(final Class<? extends Appendable> appendableClass) throws Exception {
        final AppendableJoiner<Object> joiner = AppendableJoiner.builder().setDelimiter(".").get();
        final Appendable target = appendableClass.newInstance();

        target.append("A");
        assertEquals("AB.C", joiner.joinA(target, "B", "C").toString());

        target.append("1");
        assertEquals("AB.C1D.E", joiner.joinA(target, Arrays.asList("D", "E")).toString());
    }
}
