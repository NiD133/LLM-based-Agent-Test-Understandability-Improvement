package org.apache.commons.cli.help;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.function.BiFunction;

import org.apache.commons.cli.Option;
import org.junit.jupiter.api.Test;

public class OptionFormatterTest_testSetSyntaxFormatFunction {

    private static final String CUSTOM_SYNTAX = "Yep, it worked";
    private static final String DEFAULT_OPTIONAL_SYNTAX = "[-o <arg>]";

    @Test
    void testSetSyntaxFormatFunction() {
        final BiFunction<OptionFormatter, Boolean, String> customSyntaxFormat = (formatter, required) -> CUSTOM_SYNTAX;
        final Option option = Option.builder().option("o").longOpt("opt").hasArg().get();

        OptionFormatter.Builder builder = OptionFormatter.builder().setSyntaxFormatFunction(customSyntaxFormat);
        assertEquals(CUSTOM_SYNTAX, builder.build(option).toSyntaxOption());

        builder = OptionFormatter.builder().setSyntaxFormatFunction(null);
        assertEquals(DEFAULT_OPTIONAL_SYNTAX, builder.build(option).toSyntaxOption());
    }
}
