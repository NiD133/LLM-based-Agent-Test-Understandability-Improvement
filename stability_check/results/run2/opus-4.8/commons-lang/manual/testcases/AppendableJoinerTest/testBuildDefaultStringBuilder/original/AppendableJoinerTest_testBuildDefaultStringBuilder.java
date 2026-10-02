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

public class AppendableJoinerTest_testBuildDefaultStringBuilder extends AbstractLangTest {

    @Test
    void testBuildDefaultStringBuilder() {
        final Builder<Object> builder = AppendableJoiner.builder();
        assertNotSame(builder.get(), builder.get());
        final AppendableJoiner<Object> joiner = builder.get();
        final StringBuilder sbuilder = new StringBuilder("A");
        assertEquals("ABC", joiner.join(sbuilder, "B", "C").toString());
        sbuilder.append("1");
        assertEquals("ABC1DE", joiner.join(sbuilder, "D", "E").toString());
    }
}
