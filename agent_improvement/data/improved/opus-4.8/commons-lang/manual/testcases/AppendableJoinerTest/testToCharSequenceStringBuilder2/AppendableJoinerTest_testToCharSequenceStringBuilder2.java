package org.apache.commons.lang3;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.util.Arrays;

import org.junit.jupiter.api.Test;

/**
 * Tests that an {@link AppendableJoiner} configured with a custom element appender renders objects
 * directly into an existing {@link StringBuilder}, for both the varargs and the {@link Iterable} overloads.
 */
public class AppendableJoinerTest_testToCharSequenceStringBuilder2 extends AbstractLangTest {

    /**
     * A simple element type whose {@link #render(Appendable)} method appends its value followed by a {@code '!'}.
     * For example, {@code new Fixture("B")} renders as {@code "B!"}.
     */
    private static final class Fixture {

        private final String value;

        Fixture(final String value) {
            this.value = value;
        }

        /** Appends {@code value + "!"} onto the given target, e.g. "B" becomes "B!". */
        Appendable render(final Appendable appendable) throws IOException {
            return appendable.append(value).append('!');
        }
    }

    @Test
    void testToCharSequenceStringBuilder2() {
        // Joiner with no prefix/suffix/delimiter: each element is rendered as "<value>!" back to back.
        final AppendableJoiner<Fixture> joiner = AppendableJoiner.<Fixture>builder()
                .setElementAppender((appendable, element) -> element.render(appendable))
                .get();

        // The joiner appends into the existing StringBuilder rather than creating a new String.
        final StringBuilder target = new StringBuilder("[");

        // Varargs overload: appends "B!" then "C!" onto the initial "[".
        assertEquals("[B!C!", joiner.join(target, new Fixture("B"), new Fixture("C")).toString());

        // Mutate the same buffer, then use the Iterable overload to append "D!" then "E!".
        target.append("]");
        assertEquals("[B!C!]D!E!",
                joiner.join(target, Arrays.asList(new Fixture("D"), new Fixture("E"))).toString());
    }
}
