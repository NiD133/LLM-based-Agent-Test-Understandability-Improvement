package org.apache.commons.lang3;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Arrays;

import org.junit.jupiter.api.Test;

public class AppendableJoinerTest_testDelimiterStringBuilder extends AbstractLangTest {

    @Test
    void testDelimiterStringBuilder() {
        final AppendableJoiner<Object> joiner = AppendableJoiner.builder().setDelimiter(".").get();
        final StringBuilder target = new StringBuilder("A");

        assertEquals("AB.C", joiner.join(target, "B", "C").toString());

        target.append("1");

        assertEquals("AB.C1D.E", joiner.join(target, Arrays.asList("D", "E")).toString());
    }
}
