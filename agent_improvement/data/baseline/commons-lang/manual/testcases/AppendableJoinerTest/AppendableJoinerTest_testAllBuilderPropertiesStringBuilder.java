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

public class AppendableJoinerTest_testAllBuilderPropertiesStringBuilder extends AbstractLangTest {

    @Test
    void testAllBuilderPropertiesStringBuilder() {
        // @formatter:off
        final AppendableJoiner<Object> joiner = AppendableJoiner.builder().setPrefix("<").setDelimiter(".").setSuffix(">").setElementAppender((a, e) -> a.append(String.valueOf(e))).get();
        // @formatter:on
        final StringBuilder sbuilder = new StringBuilder("A");
        assertEquals("A<B.C>", joiner.join(sbuilder, "B", "C").toString());
        sbuilder.append("1");
        assertEquals("A<B.C>1<D.E>", joiner.join(sbuilder, Arrays.asList("D", "E")).toString());
    }
}
