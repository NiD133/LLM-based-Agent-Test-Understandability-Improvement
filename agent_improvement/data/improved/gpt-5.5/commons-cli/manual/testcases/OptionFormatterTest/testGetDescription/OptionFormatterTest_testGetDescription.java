package org.apache.commons.cli.help;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.apache.commons.cli.DeprecatedAttributes;
import org.apache.commons.cli.Option;
import org.junit.jupiter.api.Test;

public class OptionFormatterTest_testGetDescription {

    private static final String DESCRIPTION = "The description";
    private static final String EXPECTED_DEPRECATED_PREFIX = "[Deprecated] " + DESCRIPTION;
    private static final String EXPECTED_COMPLEX_DEPRECATED_PREFIX = "[Deprecated for removal since now. Use something else] " + DESCRIPTION;

    @Test
    void testGetDescription() {
        final Option normalOption = optionBuilder().get();
        final Option deprecatedOption = optionBuilder().deprecated().get();
        final Option deprecatedOptionWithAttributes = optionBuilder()
                .deprecated(DeprecatedAttributes.builder()
                        .setForRemoval(true)
                        .setSince("now")
                        .setDescription("Use something else")
                        .get())
                .get();

        assertDefaultDescriptionFormat(normalOption, deprecatedOption, deprecatedOptionWithAttributes);
        assertSimpleDeprecatedDescriptionFormat(normalOption, deprecatedOption, deprecatedOptionWithAttributes);
        assertComplexDeprecatedDescriptionFormat(normalOption, deprecatedOption, deprecatedOptionWithAttributes);
    }

    private static Option.Builder optionBuilder() {
        return Option.builder()
                .option("o")
                .longOpt("one")
                .hasArg()
                .desc(DESCRIPTION);
    }

    private static void assertDefaultDescriptionFormat(
            final Option normalOption,
            final Option deprecatedOption,
            final Option deprecatedOptionWithAttributes) {
        assertEquals(DESCRIPTION, OptionFormatter.from(normalOption).getDescription(), "normal option failure");
        assertEquals(DESCRIPTION, OptionFormatter.from(deprecatedOption).getDescription(), "deprecated option failure");
        assertEquals(DESCRIPTION, OptionFormatter.from(deprecatedOptionWithAttributes).getDescription(), "complex deprecated option failure");
    }

    private static void assertSimpleDeprecatedDescriptionFormat(
            final Option normalOption,
            final Option deprecatedOption,
            final Option deprecatedOptionWithAttributes) {
        final OptionFormatter.Builder builder = OptionFormatter.builder()
                .setDeprecatedFormatFunction(OptionFormatter.SIMPLE_DEPRECATED_FORMAT);

        assertEquals(DESCRIPTION, builder.build(normalOption).getDescription(), "normal option failure");
        assertEquals(EXPECTED_DEPRECATED_PREFIX, builder.build(deprecatedOption).getDescription(), "deprecated option failure");
        assertEquals(EXPECTED_DEPRECATED_PREFIX, builder.build(deprecatedOptionWithAttributes).getDescription(), "complex deprecated option failure");
    }

    private static void assertComplexDeprecatedDescriptionFormat(
            final Option normalOption,
            final Option deprecatedOption,
            final Option deprecatedOptionWithAttributes) {
        final OptionFormatter.Builder builder = OptionFormatter.builder()
                .setDeprecatedFormatFunction(OptionFormatter.COMPLEX_DEPRECATED_FORMAT);

        assertEquals(DESCRIPTION, builder.build(normalOption).getDescription(), "normal option failure");
        assertEquals(EXPECTED_DEPRECATED_PREFIX, builder.build(deprecatedOption).getDescription(), "deprecated option failure");
        assertEquals(EXPECTED_COMPLEX_DEPRECATED_PREFIX, builder.build(deprecatedOptionWithAttributes).getDescription(), "complex deprecated option failure");
    }
}
