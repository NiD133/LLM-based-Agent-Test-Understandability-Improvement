package org.apache.commons.cli.help;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.apache.commons.cli.Option;
import org.junit.jupiter.api.Test;

/**
 * Tests {@link OptionFormatter.Builder#setDefaultArgName(String)}.
 *
 * <p>The default argument name is the placeholder shown for an option that takes a value
 * but has no explicit argument name. When rendered via {@link OptionFormatter#getArgName()}
 * it is wrapped in the argument-name delimiters ({@code <} and {@code >}).</p>
 */
public class OptionFormatterTest_testSetDefaultArgName {

    @Test
    void testSetDefaultArgName() {
        // An option that takes an argument but defines no argument name of its own,
        // so the formatter must fall back to its configured default argument name.
        final Option optionWithUnnamedArg = Option.builder().option("o").longOpt("opt").hasArg().get();

        // A non-blank default is used verbatim, wrapped in the "<...>" delimiters.
        final String customDefault = OptionFormatter.builder()
                .setDefaultArgName("foo")
                .build(optionWithUnnamedArg)
                .getArgName();
        assertEquals("<foo>", customDefault);

        // An empty default falls back to OptionFormatter.DEFAULT_ARG_NAME ("arg").
        final String emptyDefault = OptionFormatter.builder()
                .setDefaultArgName("")
                .build(optionWithUnnamedArg)
                .getArgName();
        assertEquals("<arg>", emptyDefault);

        // A null default likewise falls back to OptionFormatter.DEFAULT_ARG_NAME ("arg").
        final String nullDefault = OptionFormatter.builder()
                .setDefaultArgName(null)
                .build(optionWithUnnamedArg)
                .getArgName();
        assertEquals("<arg>", nullDefault);
    }
}
