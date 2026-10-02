package org.apache.commons.cli.help;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.function.BiFunction;

import org.apache.commons.cli.Option;
import org.junit.jupiter.api.Test;

public class OptionFormatterTest_testSetSyntaxFormatFunction {

    private static final Option OPTION_WITH_ARG = Option.builder().option("o").longOpt("opt").hasArg().get();

    @Test
    void testSetSyntaxFormatFunction_customFunctionIsInvoked() {
        final BiFunction<OptionFormatter, Boolean, String> alwaysYep = (o, b) -> "Yep, it worked";
        final OptionFormatter.Builder builder = OptionFormatter.builder().setSyntaxFormatFunction(alwaysYep);
        assertEquals("Yep, it worked", builder.build(OPTION_WITH_ARG).toSyntaxOption());
    }

    @Test
    void testSetSyntaxFormatFunction_nullRestoresDefaultFormatting() {
        final OptionFormatter.Builder builder = OptionFormatter.builder().setSyntaxFormatFunction(null);
        assertEquals("[-o <arg>]", builder.build(OPTION_WITH_ARG).toSyntaxOption());
    }
}
