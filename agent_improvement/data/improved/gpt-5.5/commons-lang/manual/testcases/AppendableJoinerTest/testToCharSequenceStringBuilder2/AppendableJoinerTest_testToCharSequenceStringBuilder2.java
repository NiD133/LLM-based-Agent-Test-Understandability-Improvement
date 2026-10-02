package org.apache.commons.lang3;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.util.Arrays;

import org.junit.jupiter.api.Test;

public class AppendableJoinerTest_testToCharSequenceStringBuilder2 extends AbstractLangTest {

    @Test
    void testToCharSequenceStringBuilder2() {
        final AppendableJoiner<Fixture> joiner = AppendableJoiner.<Fixture>builder()
                .setElementAppender((appendable, fixture) -> fixture.render(appendable))
                .get();
        final StringBuilder target = new StringBuilder("[");

        assertEquals("[B!C!", joiner.join(target, new Fixture("B"), new Fixture("C")).toString());
        target.append("]");
        assertEquals("[B!C!]D!E!", joiner.join(target, Arrays.asList(new Fixture("D"), new Fixture("E"))).toString());
    }

    private static final class Fixture {
        private final String value;

        private Fixture(final String value) {
            this.value = value;
        }

        private void render(final Appendable appendable) throws IOException {
            appendable.append(value).append("!");
        }
    }
}
