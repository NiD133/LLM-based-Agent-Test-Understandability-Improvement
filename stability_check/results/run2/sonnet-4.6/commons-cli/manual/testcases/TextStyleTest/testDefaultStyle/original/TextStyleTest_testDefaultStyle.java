package org.apache.commons.cli.help;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

public class TextStyleTest_testDefaultStyle {

    static Stream<Arguments> padTestData() {
        final List<Arguments> lst = new ArrayList<>();
        final TextStyle.Builder builder = TextStyle.builder();
        builder.setIndent(5);
        builder.setLeftPad(5);
        builder.setMinWidth(4);
        builder.setScalable(true);
        // undefined creates result of original text + indent
        builder.setMaxWidth(TextStyle.UNSET_MAX_WIDTH);
        builder.setAlignment(TextStyle.Alignment.LEFT);
        lst.add(Arguments.of(builder.get(), "Hello world", "     Hello world"));
        builder.setAlignment(TextStyle.Alignment.RIGHT);
        lst.add(Arguments.of(builder.get(), "Hello world", "     Hello world"));
        builder.setAlignment(TextStyle.Alignment.CENTER);
        lst.add(Arguments.of(builder.get(), "Hello world", "  Hello world   "));
        // width less than text length creates result of original text
        builder.setMaxWidth(5);
        builder.setAlignment(TextStyle.Alignment.LEFT);
        lst.add(Arguments.of(builder.get(), "Hello world", "Hello world"));
        builder.setAlignment(TextStyle.Alignment.RIGHT);
        lst.add(Arguments.of(builder.get(), "Hello world", "Hello world"));
        builder.setAlignment(TextStyle.Alignment.CENTER);
        lst.add(Arguments.of(builder.get(), "Hello world", "Hello world"));
        // width greater than text length + indent creates result of text length with indent
        builder.setMaxWidth(20);
        builder.setAlignment(TextStyle.Alignment.LEFT);
        lst.add(Arguments.of(builder.get(), "Hello world         ", "     Hello world    "));
        builder.setAlignment(TextStyle.Alignment.RIGHT);
        lst.add(Arguments.of(builder.get(), "         Hello world", "         Hello world"));
        builder.setAlignment(TextStyle.Alignment.CENTER);
        lst.add(Arguments.of(builder.get(), "    Hello world     ", "    Hello world     "));
        // width greater than text length and less than text length + indent creates result of text length + pad
        builder.setMaxWidth(14);
        builder.setAlignment(TextStyle.Alignment.LEFT);
        lst.add(Arguments.of(builder.get(), "Hello world   ", "Hello world   "));
        builder.setAlignment(TextStyle.Alignment.RIGHT);
        lst.add(Arguments.of(builder.get(), "   Hello world", "   Hello world"));
        builder.setAlignment(TextStyle.Alignment.CENTER);
        lst.add(Arguments.of(builder.get(), " Hello world  ", " Hello world  "));
        return lst.stream();
    }

    @Test
    void testDefaultStyle() {
        final TextStyle underTest = TextStyle.DEFAULT;
        assertEquals(TextStyle.Alignment.LEFT, underTest.getAlignment());
        assertTrue(underTest.isScalable());
        assertEquals(0, underTest.getLeftPad());
        assertEquals(0, underTest.getMinWidth());
        assertEquals(TextStyle.UNSET_MAX_WIDTH, underTest.getMaxWidth());
    }
}
