package org.apache.commons.lang3;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotSame;

import org.apache.commons.lang3.AppendableJoiner.Builder;
import org.junit.jupiter.api.Test;

/**
 * Tests that an {@link AppendableJoiner} created from a default {@link Builder}
 * (no prefix, suffix, delimiter, or custom appender configured) joins array
 * elements straight into a {@link StringBuilder}, preserving whatever content the
 * StringBuilder already held.
 */
public class AppendableJoinerTest_testBuildDefaultStringBuilder extends AbstractLangTest {

    @Test
    void testBuildDefaultStringBuilder() {
        final Builder<Object> builder = AppendableJoiner.builder();

        // The builder is a factory: each get() returns a fresh joiner instance.
        assertNotSame(builder.get(), builder.get());

        final AppendableJoiner<Object> joiner = builder.get();

        // With no delimiter/prefix/suffix, elements are appended directly after
        // the StringBuilder's existing content ("A").
        final StringBuilder target = new StringBuilder("A");
        assertEquals("ABC", joiner.join(target, "B", "C").toString());

        // Joining again keeps appending to the same StringBuilder, including any
        // content added between joins ("1").
        target.append("1");
        assertEquals("ABC1DE", joiner.join(target, "D", "E").toString());
    }
}
