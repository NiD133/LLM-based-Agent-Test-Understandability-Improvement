package org.apache.commons.lang3;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotSame;

import org.apache.commons.lang3.AppendableJoiner.Builder;
import org.junit.jupiter.api.Test;

public class AppendableJoinerTest_testBuildDefaultStringBuilder extends AbstractLangTest {

    /**
     * Verifies that a default-configured AppendableJoiner (no prefix, suffix, or delimiter)
     * appends elements directly and that each call to Builder.get() produces a distinct instance.
     */
    @Test
    void testBuildDefaultStringBuilder() {
        final Builder<Object> builder = AppendableJoiner.builder();

        // Builder.get() must produce a new AppendableJoiner instance on every call
        assertNotSame(builder.get(), builder.get());

        final AppendableJoiner<Object> joiner = builder.get();

        // With no prefix/suffix/delimiter, join simply concatenates elements onto the StringBuilder
        final StringBuilder sb = new StringBuilder("A");
        assertEquals("ABC", joiner.join(sb, "B", "C").toString());

        // The StringBuilder retains its previous content; subsequent joins append to it
        sb.append("1");
        assertEquals("ABC1DE", joiner.join(sb, "D", "E").toString());
    }
}
