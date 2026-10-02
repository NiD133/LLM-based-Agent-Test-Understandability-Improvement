package org.apache.commons.cli.help;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.function.BiFunction;

import org.apache.commons.cli.Option;
import org.junit.jupiter.api.Test;

public class OptionFormatterTest_testSetSyntaxFormatFunction {

    @Test
    void testSetSyntaxFormatFunction() {
        final Option option = Option.builder().option("o").longOpt("opt").hasArg().get();

        // A custom syntax function is used verbatim by toSyntaxOption().
        final BiFunction<OptionFormatter, Boolean, String> customSyntaxFunction = (formatter, required) -> "Yep, it worked";
        final OptionFormatter withCustomFunction = OptionFormatter.builder()
                .setSyntaxFormatFunction(customSyntaxFunction)
                .build(option);
        assertEquals("Yep, it worked", withCustomFunction.toSyntaxOption());

        // Passing null restores the default syntax formatting.
        final OptionFormatter withDefaultFunction = OptionFormatter.builder()
                .setSyntaxFormatFunction(null)
                .build(option);
        assertEquals("[-o <arg>]", withDefaultFunction.toSyntaxOption());
    }
}
