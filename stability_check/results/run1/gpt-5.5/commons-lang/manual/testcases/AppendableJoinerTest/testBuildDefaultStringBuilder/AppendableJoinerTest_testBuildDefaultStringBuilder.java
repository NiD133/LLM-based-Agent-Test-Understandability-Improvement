package org.apache.commons.lang3;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotSame;

import org.apache.commons.lang3.AppendableJoiner.Builder;
import org.junit.jupiter.api.Test;

public class AppendableJoinerTest_testBuildDefaultStringBuilder extends AbstractLangTest {

    @Test
    void testBuildDefaultStringBuilder() {
        final Builder<Object> builder = AppendableJoiner.builder();
        assertNotSame(builder.get(), builder.get());

        final AppendableJoiner<Object> joiner = builder.get();
        final StringBuilder target = new StringBuilder("A");

        assertEquals("ABC", joiner.join(target, "B", "C").toString());

        target.append("1");
        assertEquals("ABC1DE", joiner.join(target, "D", "E").toString());
    }
}
