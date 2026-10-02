package org.apache.commons.lang3;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotSame;

import org.apache.commons.lang3.AppendableJoiner.Builder;
import org.junit.jupiter.api.Test;

public class AppendableJoinerTest_testBuildDefaultStringBuilder extends AbstractLangTest {

    /**
     * Verifies a default {@link AppendableJoiner} (no prefix, suffix, or delimiter)
     * appends each element straight onto a {@link StringBuilder}, leaving any content
     * already present in the builder untouched.
     */
    @Test
    void testBuildDefaultStringBuilder() {
        final Builder<Object> builder = AppendableJoiner.builder();

        // Each call to get() must produce a fresh, independent joiner instance.
        assertNotSame(builder.get(), builder.get());

        final AppendableJoiner<Object> joiner = builder.get();

        // Joining appends the elements after the existing "A" with no separators.
        final StringBuilder target = new StringBuilder("A");
        assertEquals("ABC", joiner.join(target, "B", "C").toString());

        // Further appends and joins keep accumulating onto the same builder.
        target.append("1");
        assertEquals("ABC1DE", joiner.join(target, "D", "E").toString());
    }
}
