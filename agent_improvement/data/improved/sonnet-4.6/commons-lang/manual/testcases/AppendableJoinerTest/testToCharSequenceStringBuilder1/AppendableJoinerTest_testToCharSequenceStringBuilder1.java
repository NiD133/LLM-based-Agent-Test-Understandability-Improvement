package org.apache.commons.lang3;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Arrays;
import java.util.Objects;

import org.junit.jupiter.api.Test;

public class AppendableJoinerTest_testToCharSequenceStringBuilder1 extends AbstractLangTest {

    /**
     * Verifies that AppendableJoiner correctly appends joined output into an existing
     * StringBuilder, preserving pre-existing content and accumulating across multiple join calls.
     *
     * The joiner is configured to:
     *   - wrap elements with prefix "<" and suffix ">"
     *   - separate elements with delimiter "."
     *   - prepend "|" to each element's string representation
     *
     * Example: joining "B", "C" produces "<|B.|C>"
     */
    @Test
    void testToCharSequenceStringBuilder1() {
        final AppendableJoiner<Object> joiner = AppendableJoiner.builder()
                .setPrefix("<")
                .setDelimiter(".")
                .setSuffix(">")
                .setElementAppender((appendable, element) -> appendable.append("|").append(Objects.toString(element)))
                .get();

        final StringBuilder sb = new StringBuilder("A");

        // First join: varargs "B", "C" are appended after the initial "A"
        joiner.join(sb, "B", "C");
        assertEquals("A<|B.|C>", sb.toString());

        // Manually append "1", then join an Iterable — content accumulates in the same builder
        sb.append("1");
        joiner.join(sb, Arrays.asList("D", "E"));
        assertEquals("A<|B.|C>1<|D.|E>", sb.toString());
    }
}
