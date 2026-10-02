package org.apache.commons.cli.help;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.function.BiFunction;
import java.util.function.Function;

import org.apache.commons.cli.Option;
import org.junit.jupiter.api.Test;

public class OptionFormatterTest_testCopyConstructor {

    private static final Function<Option, String> DEPRECATED_FORMAT = option -> "Ooo Deprecated";
    private static final BiFunction<OptionFormatter, Boolean, String> SYNTAX_FORMAT = (formatter, required) -> "Yep, it worked";

    private static final String LONG_OPTION_PREFIX = "l";
    private static final String SHORT_OPTION_PREFIX = "s";
    private static final String ARGUMENT_NAME_BEGIN = "{";
    private static final String ARGUMENT_NAME_END = "}";
    private static final String DEFAULT_ARGUMENT_NAME = "Some Argument";
    private static final String OPTION_SEPARATOR = " and ";
    private static final String OPTIONAL_BEGIN = "?>";
    private static final String OPTIONAL_END = "<?";

    private OptionFormatter.Builder customFormatterBuilder() {
        return OptionFormatter.builder()
                .setLongOptPrefix(LONG_OPTION_PREFIX)
                .setOptPrefix(SHORT_OPTION_PREFIX)
                .setArgumentNameDelimiters(ARGUMENT_NAME_BEGIN, ARGUMENT_NAME_END)
                .setDefaultArgName(DEFAULT_ARGUMENT_NAME)
                .setOptSeparator(OPTION_SEPARATOR)
                .setOptionalDelimiters(OPTIONAL_BEGIN, OPTIONAL_END)
                .setSyntaxFormatFunction(SYNTAX_FORMAT)
                .setDeprecatedFormatFunction(DEPRECATED_FORMAT);
    }

    private Option regularOption() {
        return Option.builder("o").longOpt("opt").get();
    }

    private Option deprecatedRequiredOption() {
        return Option.builder("o").longOpt("opt").deprecated().required().get();
    }

    private void assertCopyPreservesFormatting(final OptionFormatter formatter, final OptionFormatter copiedFormatter) {
        assertEquals(formatter.toSyntaxOption(), copiedFormatter.toSyntaxOption());
        assertEquals(formatter.toSyntaxOption(true), copiedFormatter.toSyntaxOption(true));
        assertEquals(formatter.toSyntaxOption(false), copiedFormatter.toSyntaxOption(false));
        assertEquals(formatter.getOpt(), copiedFormatter.getOpt());
        assertEquals(formatter.getLongOpt(), copiedFormatter.getLongOpt());
        assertEquals(formatter.getBothOpt(), copiedFormatter.getBothOpt());
        assertEquals(formatter.getDescription(), copiedFormatter.getDescription());
        assertEquals(formatter.getArgName(), copiedFormatter.getArgName());
        assertEquals(formatter.toOptional("foo"), copiedFormatter.toOptional("foo"));
    }

    private void assertBuilderCopiedFromFormatter(final OptionFormatter.Builder sourceBuilder, final Option option) {
        final OptionFormatter formatter = sourceBuilder.build(option);
        final OptionFormatter.Builder copiedBuilder = new OptionFormatter.Builder(formatter);

        assertCopyPreservesFormatting(formatter, copiedBuilder.build(option));
    }

    @Test
    void testCopyConstructor() {
        final OptionFormatter.Builder builder = customFormatterBuilder();

        assertBuilderCopiedFromFormatter(builder, regularOption());
        assertBuilderCopiedFromFormatter(builder, deprecatedRequiredOption());
    }
}
