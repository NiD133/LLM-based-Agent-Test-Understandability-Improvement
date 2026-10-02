package org.apache.commons.lang3;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Arrays;

import org.junit.jupiter.api.Test;

public class AppendableJoinerTest_testAllBuilderPropertiesStringBuilder extends AbstractLangTest {

    /**
     * Verifies that an {@link AppendableJoiner} configured with every builder property
     * (prefix, delimiter, suffix and a custom element appender) joins both varargs and
     * an {@link Iterable} into an existing {@link StringBuilder}, appending to whatever
     * the StringBuilder already contains instead of replacing it.
     */
    @Test
    void testAllBuilderPropertiesStringBuilder() {
        // Joiner that wraps elements as "<elem.elem>" using a custom appender that
        // renders each element via String.valueOf(...).
        final AppendableJoiner<Object> joiner = AppendableJoiner.builder()
                .setPrefix("<")
                .setDelimiter(".")
                .setSuffix(">")
                .setElementAppender((appendable, element) -> appendable.append(String.valueOf(element)))
                .get();

        // Start with existing content so we can confirm the joiner appends rather than overwrites.
        final StringBuilder target = new StringBuilder("A");

        // Join varargs: "A" + "<B.C>".
        assertEquals("A<B.C>", joiner.join(target, "B", "C").toString());

        // Append more existing content, then join an Iterable: previous result + "1" + "<D.E>".
        target.append("1");
        assertEquals("A<B.C>1<D.E>", joiner.join(target, Arrays.asList("D", "E")).toString());
    }
}
