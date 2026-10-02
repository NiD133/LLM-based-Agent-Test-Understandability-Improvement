package org.apache.commons.lang3;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Arrays;
import java.util.Objects;

import org.junit.jupiter.api.Test;

public class AppendableJoinerTest_testToCharSequenceStringBuilder1 extends AbstractLangTest {

    @Test
    void testToCharSequenceStringBuilder1() {
        final AppendableJoiner<Object> joiner = AppendableJoiner.builder()
                .setPrefix("<")
                .setDelimiter(".")
                .setSuffix(">")
                .setElementAppender((appendable, element) -> appendable.append("|").append(Objects.toString(element)))
                .get();

        final StringBuilder sbuilder = new StringBuilder("A");

        assertEquals("A<|B.|C>", joiner.join(sbuilder, "B", "C").toString());

        sbuilder.append("1");

        assertEquals("A<|B.|C>1<|D.|E>", joiner.join(sbuilder, Arrays.asList("D", "E")).toString());
    }
}
