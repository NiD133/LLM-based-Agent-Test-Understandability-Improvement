package org.apache.commons.cli.help;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.apache.commons.cli.Option;
import org.junit.jupiter.api.Test;

public class OptionFormatterTest_testAsOptional {

    @Test
    void testAsOptional() {
        final Option option = Option.builder().option("o").longOpt("opt").hasArg().get();

        // Default delimiters are "[" and "]"
        final OptionFormatter defaultFormatter = OptionFormatter.from(option);
        assertEquals("[what]", defaultFormatter.toOptional("what"), "non-empty string should be wrapped in default delimiters");
        assertEquals("", defaultFormatter.toOptional(""), "empty string should return empty string");
        assertEquals("", defaultFormatter.toOptional(null), "null should return empty string");

        // Custom delimiters "-> " and " <-"
        final OptionFormatter customFormatter = OptionFormatter.builder()
                .setOptionalDelimiters("-> ", " <-")
                .build(option);
        assertEquals("-> what <-", customFormatter.toOptional("what"), "non-empty string should be wrapped in custom delimiters");
    }
}
