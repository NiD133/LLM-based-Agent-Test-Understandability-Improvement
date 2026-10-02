package org.apache.commons.cli.help;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.function.BiFunction;
import java.util.function.Function;

import org.apache.commons.cli.Option;
import org.junit.jupiter.api.Test;

public class OptionFormatterTest_testCopyConstructor {

    /**
     * Asserts that two formatters are interchangeable by comparing every
     * observable property and rendering they expose. Used to confirm that a
     * formatter produced by the copy constructor behaves exactly like the
     * original it was copied from.
     */
    private void assertEquivalent(final OptionFormatter original, final OptionFormatter copy) {
        assertEquals(original.toSyntaxOption(), copy.toSyntaxOption());
        assertEquals(original.toSyntaxOption(true), copy.toSyntaxOption(true));
        assertEquals(original.toSyntaxOption(false), copy.toSyntaxOption(false));
        assertEquals(original.getOpt(), copy.getOpt());
        assertEquals(original.getLongOpt(), copy.getLongOpt());
        assertEquals(original.getBothOpt(), copy.getBothOpt());
        assertEquals(original.getDescription(), copy.getDescription());
        assertEquals(original.getArgName(), copy.getArgName());
        assertEquals(original.toOptional("foo"), copy.toOptional("foo"));
    }

    @Test
    void testCopyConstructor() {
        // Custom formatting functions so we can tell apart the configured
        // builder from any default behavior.
        final Function<Option, String> deprecatedFormat = option -> "Ooo Deprecated";
        final BiFunction<OptionFormatter, Boolean, String> syntaxFormat = (formatter, required) -> "Yep, it worked";

        // A builder configured with fully non-default settings, so that a
        // faithful copy must reproduce every one of these values.
        final OptionFormatter.Builder builder = OptionFormatter.builder()
                .setLongOptPrefix("l")
                .setOptPrefix("s")
                .setArgumentNameDelimiters("{", "}")
                .setDefaultArgName("Some Argument")
                .setOptSeparator(" and ")
                .setOptionalDelimiters("?>", "<?")
                .setSyntaxFormatFunction(syntaxFormat)
                .setDeprecatedFormatFunction(deprecatedFormat);

        // Case 1: a plain option with a short and long name.
        Option option = Option.builder("o").longOpt("opt").get();
        OptionFormatter original = builder.build(option);
        OptionFormatter copy = new OptionFormatter.Builder(original).build(option);
        assertEquivalent(original, copy);

        // Case 2: a deprecated, required option, exercising the deprecated
        // format function carried over by the copy constructor.
        option = Option.builder("o").longOpt("opt").deprecated().required().get();
        original = builder.build(option);
        copy = new OptionFormatter.Builder(original).build(option);
        assertEquivalent(original, copy);
    }
}
