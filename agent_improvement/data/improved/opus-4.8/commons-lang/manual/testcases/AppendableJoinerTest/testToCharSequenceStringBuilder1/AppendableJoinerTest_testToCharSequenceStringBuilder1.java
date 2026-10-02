package org.apache.commons.lang3;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Arrays;
import java.util.Objects;

import org.junit.jupiter.api.Test;

public class AppendableJoinerTest_testToCharSequenceStringBuilder1 extends AbstractLangTest {

    /**
     * Verifies that a single {@link AppendableJoiner} instance, configured with a prefix,
     * suffix, delimiter and a custom element appender, joins both a varargs array and an
     * {@link Iterable} directly into an existing {@link StringBuilder}.
     * <p>
     * The custom appender renders each element as {@code "|" + element}, so the joiner
     * wraps the joined elements as {@code "<|first.|second>"} (prefix {@code "<"},
     * delimiter {@code "."}, suffix {@code ">"}). Each join appends to the same builder,
     * preserving any text already present.
     * </p>
     */
    @Test
    void testToCharSequenceStringBuilder1() {
        // Joiner that wraps elements as "<...>", separates them with ".",
        // and prefixes each rendered element with "|".
        final AppendableJoiner<Object> joiner = AppendableJoiner.builder()
                .setPrefix("<")
                .setDelimiter(".")
                .setSuffix(">")
                .setElementAppender((appendable, element) -> appendable.append("|").append(Objects.toString(element)))
                .get();

        // The builder already holds "A"; the array join appends "<|B.|C>" after it.
        final StringBuilder builder = new StringBuilder("A");
        assertEquals("A<|B.|C>", joiner.join(builder, "B", "C").toString());

        // Append "1", then the Iterable join appends "<|D.|E>" after that.
        builder.append("1");
        assertEquals("A<|B.|C>1<|D.|E>", joiner.join(builder, Arrays.asList("D", "E")).toString());
    }
}
