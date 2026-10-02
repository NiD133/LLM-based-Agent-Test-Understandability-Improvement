package org.apache.commons.lang3;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.util.Arrays;

import org.junit.jupiter.api.Test;

public class AppendableJoinerTest_testToCharSequenceStringBuilder2 extends AbstractLangTest {

    // A test fixture that renders itself onto an Appendable as "<name>!"
    private static class Fixture {

        private final String name;

        Fixture(final String name) {
            this.name = name;
        }

        void render(final Appendable appendable) throws IOException {
            appendable.append(name);
            appendable.append('!');
        }
    }

    @Test
    void testToCharSequenceStringBuilder2() {
        // A joiner with no prefix, suffix, or delimiter; each Fixture renders itself via render()
        final AppendableJoiner<Fixture> joiner = AppendableJoiner.<Fixture>builder()
                .setElementAppender((a, e) -> e.render(a))
                .get();

        // join() appends directly into the existing StringBuilder, returning the same instance
        final StringBuilder stringBuilder = new StringBuilder("[");
        assertEquals("[B!C!", joiner.join(stringBuilder, new Fixture("B"), new Fixture("C")).toString());

        // The StringBuilder retains previous content; subsequent join() calls accumulate onto it
        stringBuilder.append("]");
        assertEquals("[B!C!]D!E!", joiner.join(stringBuilder, Arrays.asList(new Fixture("D"), new Fixture("E"))).toString());
    }
}
