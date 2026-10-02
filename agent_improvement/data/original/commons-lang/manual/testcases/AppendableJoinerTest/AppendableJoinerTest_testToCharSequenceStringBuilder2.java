package org.apache.commons.lang3;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import java.io.IOException;
import java.io.StringWriter;
import java.util.Arrays;
import java.util.Objects;
import org.apache.commons.lang3.AppendableJoiner.Builder;
import org.apache.commons.lang3.text.StrBuilder;
import org.apache.commons.text.TextStringBuilder;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

public class AppendableJoinerTest_testToCharSequenceStringBuilder2 extends AbstractLangTest {

    @Test
    void testToCharSequenceStringBuilder2() {
        // @formatter:off
        final AppendableJoiner<Fixture> joiner = AppendableJoiner.<Fixture>builder().setElementAppender((a, e) -> e.render(a)).get();
        // @formatter:on
        final StringBuilder sbuilder = new StringBuilder("[");
        assertEquals("[B!C!", joiner.join(sbuilder, new Fixture("B"), new Fixture("C")).toString());
        sbuilder.append("]");
        assertEquals("[B!C!]D!E!", joiner.join(sbuilder, Arrays.asList(new Fixture("D"), new Fixture("E"))).toString());
    }
}
