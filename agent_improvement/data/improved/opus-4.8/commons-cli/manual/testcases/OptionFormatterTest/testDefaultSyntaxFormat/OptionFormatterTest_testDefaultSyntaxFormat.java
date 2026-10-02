package org.apache.commons.cli.help;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.apache.commons.cli.Option;
import org.junit.jupiter.api.Test;

public class OptionFormatterTest_testDefaultSyntaxFormat {

    @Test
    void testDefaultSyntaxFormat() {
        // An optional option (the default) is wrapped in square brackets, while
        // requesting the required form (true) prints it without the brackets.
        final Option optionalOption = Option.builder().option("o").longOpt("opt").hasArg().get();
        final OptionFormatter optionalFormatter = OptionFormatter.from(optionalOption);
        assertEquals("[-o <arg>]", optionalFormatter.toSyntaxOption());
        assertEquals("-o <arg>", optionalFormatter.toSyntaxOption(true));

        // A required option prints without brackets by default, while requesting
        // the optional form (false) wraps it in square brackets.
        final Option requiredOption = Option.builder().option("o").longOpt("opt").hasArg().required().get();
        final OptionFormatter requiredFormatter = OptionFormatter.from(requiredOption);
        assertEquals("-o <arg>", requiredFormatter.toSyntaxOption());
        assertEquals("[-o <arg>]", requiredFormatter.toSyntaxOption(false));
    }
}
