package org.apache.commons.lang3;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotSame;

import org.apache.commons.lang3.AppendableJoiner.Builder;
import org.junit.jupiter.api.Test;

/**
 * Tests {@link AppendableJoiner} created from a default (unconfigured) builder,
 * joining varargs elements directly into a {@link StringBuilder}.
 */
public class AppendableJoinerTest_testBuildDefaultStringBuilder extends AbstractLangTest {

    @Test
    void testBuildDefaultStringBuilder() {
        final Builder<Object> builder = AppendableJoiner.builder();

        // Each call to get() must return a fresh, distinct joiner instance.
        assertNotSame(builder.get(), builder.get());

        // A default joiner has no prefix, suffix, or delimiter, so it simply
        // appends the stringified elements to the existing StringBuilder content.
        final AppendableJoiner<Object> joiner = builder.get();
        final StringBuilder target = new StringBuilder("A");

        // "A" + "B" + "C" -> "ABC"; join returns the same StringBuilder.
        assertEquals("ABC", joiner.join(target, "B", "C").toString());

        // Content appended between joins is preserved, and joining continues from it.
        target.append("1");
        // "ABC1" + "D" + "E" -> "ABC1DE"
        assertEquals("ABC1DE", joiner.join(target, "D", "E").toString());
    }
}
