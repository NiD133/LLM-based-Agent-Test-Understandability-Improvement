package org.apache.commons.cli.help;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.apache.commons.cli.Option;
import org.junit.jupiter.api.Test;

public class OptionFormatterTest_testAsOptional {

    private static final String OPTION_TEXT = "what";
    private static final String DEFAULT_OPTIONAL_TEXT = "[what]";
    private static final String CUSTOM_OPTIONAL_TEXT = "-> what <-";
    private static final String EMPTY_OPTIONAL_TEXT = "";

    @Test
    void testAsOptional() {
        final Option optionWithArgument = Option.builder().option("o").longOpt("opt").hasArg().get();

        final OptionFormatter defaultFormatter = OptionFormatter.from(optionWithArgument);
        assertEquals(DEFAULT_OPTIONAL_TEXT, defaultFormatter.toOptional(OPTION_TEXT));
        assertEquals(EMPTY_OPTIONAL_TEXT, defaultFormatter.toOptional(""), "enpty string should return empty string");
        assertEquals(EMPTY_OPTIONAL_TEXT, defaultFormatter.toOptional(null), "null should return empty string");

        final OptionFormatter customFormatter = OptionFormatter.builder()
                .setOptionalDelimiters("-> ", " <-")
                .build(optionWithArgument);
        assertEquals(CUSTOM_OPTIONAL_TEXT, customFormatter.toOptional(OPTION_TEXT));
    }
}
