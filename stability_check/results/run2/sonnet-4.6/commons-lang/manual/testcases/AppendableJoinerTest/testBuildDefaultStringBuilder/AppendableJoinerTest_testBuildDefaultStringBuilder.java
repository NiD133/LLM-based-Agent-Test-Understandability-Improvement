package org.apache.commons.lang3;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotSame;

import org.apache.commons.lang3.AppendableJoiner.Builder;
import org.junit.jupiter.api.Test;

public class AppendableJoinerTest_testBuildDefaultStringBuilder extends AbstractLangTest {

    @Test
    void testBuildDefaultStringBuilder() {
        // A default builder (no prefix, suffix, or delimiter) must return a new joiner instance on each get() call
        final Builder<Object> builder = AppendableJoiner.builder();
        assertNotSame(builder.get(), builder.get());

        // With no prefix, suffix, or delimiter configured, the joiner simply concatenates elements directly
        final AppendableJoiner<Object> joiner = builder.get();

        // First join: StringBuilder starts with "A"; joining "B" and "C" appends them in order -> "ABC"
        final StringBuilder sbuilder = new StringBuilder("A");
        assertEquals("ABC", joiner.join(sbuilder, "B", "C").toString());

        // Second join: StringBuilder retains its prior content ("ABC1"); joining "D" and "E" continues appending -> "ABC1DE"
        sbuilder.append("1");
        assertEquals("ABC1DE", joiner.join(sbuilder, "D", "E").toString());
    }
}
