package org.apache.commons.cli.help;

import static org.junit.jupiter.api.Assertions.assertEquals;
import java.util.function.BiFunction;
import java.util.function.Function;
import org.apache.commons.cli.Option;
import org.junit.jupiter.api.Test;

public class OptionFormatterTest_testCopyConstructor {

    private void assertEquivalent(final OptionFormatter formatter, final OptionFormatter formatter2) {
        assertEquals(formatter.toSyntaxOption(), formatter2.toSyntaxOption());
        assertEquals(formatter.toSyntaxOption(true), formatter2.toSyntaxOption(true));
        assertEquals(formatter.toSyntaxOption(false), formatter2.toSyntaxOption(false));
        assertEquals(formatter.getOpt(), formatter2.getOpt());
        assertEquals(formatter.getLongOpt(), formatter2.getLongOpt());
        assertEquals(formatter.getBothOpt(), formatter2.getBothOpt());
        assertEquals(formatter.getDescription(), formatter2.getDescription());
        assertEquals(formatter.getArgName(), formatter2.getArgName());
        assertEquals(formatter.toOptional("foo"), formatter2.toOptional("foo"));
    }

    private OptionFormatter.Builder createCustomBuilder() {
        final Function<Option, String> depFunc = o -> "Ooo Deprecated";
        final BiFunction<OptionFormatter, Boolean, String> fmtFunc = (o, b) -> "Yep, it worked";
        return OptionFormatter.builder()
                .setLongOptPrefix("l")
                .setOptPrefix("s")
                .setArgumentNameDelimiters("{", "}")
                .setDefaultArgName("Some Argument")
                .setOptSeparator(" and ")
                .setOptionalDelimiters("?>", "<?")
                .setSyntaxFormatFunction(fmtFunc)
                .setDeprecatedFormatFunction(depFunc);
    }

    @Test
    void testCopyConstructor() {
        final OptionFormatter.Builder builder = createCustomBuilder();

        // Verify the copy constructor preserves all formatting settings for a plain option
        Option option = Option.builder("o").longOpt("opt").get();
        OptionFormatter formatter = builder.build(option);
        OptionFormatter.Builder builder2 = new OptionFormatter.Builder(formatter);
        assertEquivalent(formatter, builder2.build(option));

        // Verify the copy constructor preserves all formatting settings for a deprecated, required option
        option = Option.builder("o").longOpt("opt").deprecated().required().get();
        formatter = builder.build(option);
        builder2 = new OptionFormatter.Builder(formatter);
        assertEquivalent(formatter, builder2.build(option));
    }
}
