package org.apache.commons.lang3;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotSame;

import org.apache.commons.lang3.AppendableJoiner.Builder;
import org.junit.jupiter.api.Test;

public class AppendableJoinerTest_testBuildDefaultStringBuilder extends AbstractLangTest {

    @Test
    void testBuildDefaultStringBuilder() {
        // A builder with no prefix, suffix, or delimiter configured — the "default" joiner
        final Builder<Object> joinerBuilder = AppendableJoiner.builder();

        // Each call to get() must return a distinct AppendableJoiner instance
        assertNotSame(joinerBuilder.get(), joinerBuilder.get());

        final AppendableJoiner<Object> defaultJoiner = joinerBuilder.get();

        // Start with "A" in the builder; joining "B" and "C" appends them directly → "ABC"
        final StringBuilder stringBuilder = new StringBuilder("A");
        assertEquals("ABC", defaultJoiner.join(stringBuilder, "B", "C").toString());

        // Manually append "1" → "ABC1", then join "D" and "E" → "ABC1DE"
        stringBuilder.append("1");
        assertEquals("ABC1DE", defaultJoiner.join(stringBuilder, "D", "E").toString());
    }
}
