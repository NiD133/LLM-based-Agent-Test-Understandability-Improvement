package org.apache.commons.lang3;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Arrays;

import org.junit.jupiter.api.Test;

public class AppendableJoinerTest_testAllBuilderPropertiesStringBuilder extends AbstractLangTest {

    @Test
    void testAllBuilderPropertiesStringBuilder() {
        final AppendableJoiner<Object> joiner = AppendableJoiner.builder()
                .setPrefix("<")
                .setDelimiter(".")
                .setSuffix(">")
                .setElementAppender((appendable, element) -> appendable.append(String.valueOf(element)))
                .get();
        final StringBuilder stringBuilder = new StringBuilder("A");

        assertEquals("A<B.C>", joiner.join(stringBuilder, "B", "C").toString());

        stringBuilder.append("1");
        assertEquals("A<B.C>1<D.E>", joiner.join(stringBuilder, Arrays.asList("D", "E")).toString());
    }
}
