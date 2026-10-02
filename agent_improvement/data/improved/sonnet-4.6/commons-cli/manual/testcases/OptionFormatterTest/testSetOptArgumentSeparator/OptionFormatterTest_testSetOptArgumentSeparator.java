package org.apache.commons.cli.help;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.apache.commons.cli.Option;
import org.junit.jupiter.api.Test;

public class OptionFormatterTest_testSetOptArgumentSeparator {

    /**
     * Tests that {@link OptionFormatter.Builder#setOptArgSeparator} controls the text placed between
     * the option flag and its argument name in the syntax output.
     *
     * Three separator values are exercised:
     *   - A descriptive phrase to confirm arbitrary text is accepted
     *   - {@code null}, which must fall back to an empty string (no separator)
     *   - {@code "="}, the common key=value style
     */
    @Test
    void testSetOptArgumentSeparator() {
        // An option with both a short flag (-o) and a long form (--opt) that takes an argument
        final Option option = Option.builder().option("o").longOpt("opt").hasArg().get();

        // Arbitrary phrase separator: the argument name follows after the phrase
        OptionFormatter.Builder builderWithPhrase = OptionFormatter.builder()
                .setOptArgSeparator(" with argument named ");
        assertEquals("[-o with argument named <arg>]",
                builderWithPhrase.build(option).toSyntaxOption(),
                "A custom phrase separator should appear verbatim between the flag and argument name");

        // Null separator: should collapse to empty string, so flag and argument are directly adjacent
        OptionFormatter.Builder builderWithNull = OptionFormatter.builder()
                .setOptArgSeparator(null);
        assertEquals("[-o<arg>]",
                builderWithNull.build(option).toSyntaxOption(),
                "A null separator should default to empty string, producing no space between flag and argument");

        // Equals-sign separator: common key=value style
        OptionFormatter.Builder builderWithEquals = OptionFormatter.builder()
                .setOptArgSeparator("=");
        assertEquals("[-o=<arg>]",
                builderWithEquals.build(option).toSyntaxOption(),
                "An '=' separator should appear between the flag and argument name");
    }
}
