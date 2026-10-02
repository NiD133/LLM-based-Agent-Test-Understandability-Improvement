package org.apache.commons.cli.help;

import static org.junit.jupiter.api.Assertions.assertEquals;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;
import org.apache.commons.cli.DeprecatedAttributes;
import org.apache.commons.cli.Option;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

public class OptionFormatterTest_testGetDescription {

    // Shared option fixtures — all carry the same base description so differences in output
    // come solely from the formatter's deprecated-format strategy.
    private static final Option NORMAL_OPTION =
            Option.builder().option("o").longOpt("one").hasArg().desc("The description").get();

    private static final Option DEPRECATED_OPTION =
            Option.builder().option("o").longOpt("one").hasArg().desc("The description").deprecated().get();

    private static final Option DEPRECATED_OPTION_WITH_ATTRIBUTES =
            Option.builder().option("o").longOpt("one").hasArg().desc("The description")
                    .deprecated(DeprecatedAttributes.builder()
                            .setForRemoval(true)
                            .setSince("now")
                            .setDescription("Use something else")
                            .get())
                    .get();

    public static Stream<Arguments> deprecatedAttributesData() {
        final List<Arguments> lst = new ArrayList<>();
        final DeprecatedAttributes.Builder daBuilder = DeprecatedAttributes.builder();
        lst.add(Arguments.of(daBuilder.get(), "[Deprecated]"));
        daBuilder.setSince("now");
        lst.add(Arguments.of(daBuilder.get(), "[Deprecated since now]"));
        daBuilder.setForRemoval(true);
        lst.add(Arguments.of(daBuilder.get(), "[Deprecated for removal since now]"));
        daBuilder.setSince(null);
        lst.add(Arguments.of(daBuilder.get(), "[Deprecated for removal]"));
        daBuilder.setForRemoval(false);
        daBuilder.setDescription("Use something else");
        lst.add(Arguments.of(daBuilder.get(), "[Deprecated. Use something else]"));
        daBuilder.setForRemoval(true);
        lst.add(Arguments.of(daBuilder.get(), "[Deprecated for removal. Use something else]"));
        daBuilder.setForRemoval(false);
        daBuilder.setSince("then");
        lst.add(Arguments.of(daBuilder.get(), "[Deprecated since then. Use something else]"));
        daBuilder.setForRemoval(true);
        lst.add(Arguments.of(daBuilder.get(), "[Deprecated for removal since then. Use something else]"));
        return lst.stream();
    }

    private void assertEquivalent(final OptionFormatter formatter, final OptionFormatter formatter2) {
        assertEquals(formatter.toSyntaxOption(), formatter2.toSyntaxOption());
        assertEquals(formatter.toSyntaxOption(true), formatter2.toSyntaxOption(true));
        assertEquals(formatter.toSyntaxOption(false), formatter2.toSyntaxOption(false));
        assertEquals(formatter.getOpt(), formatter2.getOpt());
        assertEquals(formatter.getLongOpt(), formatter2.getLongOpt());
        assertEquals(formatter.getBothOpt(), formatter2.getBothOpt());
        assertEquals(formatter.getDescription(), formatter2.getDescription());
        assertEquals(formatter.getArgName(), formatter2.getArgName());
        assertEquals(formatter.toOptional("foo"), formatter2.toOptional("foo"));
    }

    @Test
    void testGetDescription() {
        // NO_DEPRECATED_FORMAT (default): description is returned as-is for all options,
        // regardless of whether the option is deprecated or carries deprecation attributes.
        assertEquals("The description", OptionFormatter.from(NORMAL_OPTION).getDescription(), "normal option failure");
        assertEquals("The description", OptionFormatter.from(DEPRECATED_OPTION).getDescription(), "deprecated option failure");
        assertEquals("The description", OptionFormatter.from(DEPRECATED_OPTION_WITH_ATTRIBUTES).getDescription(), "complex deprecated option failure");

        // SIMPLE_DEPRECATED_FORMAT: prepends "[Deprecated]" to deprecated options but ignores
        // any detailed deprecation attributes (since, forRemoval, description).
        OptionFormatter.Builder simpleBuilder = OptionFormatter.builder().setDeprecatedFormatFunction(OptionFormatter.SIMPLE_DEPRECATED_FORMAT);
        assertEquals("The description", simpleBuilder.build(NORMAL_OPTION).getDescription(), "normal option failure");
        assertEquals("[Deprecated] The description", simpleBuilder.build(DEPRECATED_OPTION).getDescription(), "deprecated option failure");
        assertEquals("[Deprecated] The description", simpleBuilder.build(DEPRECATED_OPTION_WITH_ATTRIBUTES).getDescription(), "complex deprecated option failure");

        // COMPLEX_DEPRECATED_FORMAT: includes all deprecation attributes (forRemoval, since, description)
        // in the prefix, giving users full information about why the option is deprecated.
        OptionFormatter.Builder complexBuilder = OptionFormatter.builder().setDeprecatedFormatFunction(OptionFormatter.COMPLEX_DEPRECATED_FORMAT);
        assertEquals("The description", complexBuilder.build(NORMAL_OPTION).getDescription(), "normal option failure");
        assertEquals("[Deprecated] The description", complexBuilder.build(DEPRECATED_OPTION).getDescription(), "deprecated option failure");
        assertEquals("[Deprecated for removal since now. Use something else] The description", complexBuilder.build(DEPRECATED_OPTION_WITH_ATTRIBUTES).getDescription(), "complex deprecated option failure");
    }
}
