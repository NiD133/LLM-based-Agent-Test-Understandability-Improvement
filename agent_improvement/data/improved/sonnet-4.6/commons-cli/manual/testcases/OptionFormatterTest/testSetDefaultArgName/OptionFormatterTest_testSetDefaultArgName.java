package org.apache.commons.cli.help;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.apache.commons.cli.Option;
import org.junit.jupiter.api.Test;

public class OptionFormatterTest_testSetDefaultArgName {

    /**
     * An option that declares an argument but provides no explicit arg name,
     * so the formatter's default arg name will be used.
     */
    private static Option optionWithArg() {
        return Option.builder().option("o").longOpt("opt").hasArg().get();
    }

    @Test
    void testSetDefaultArgName() {
        final Option option = optionWithArg();

        // A non-blank custom default arg name is wrapped in angle brackets and used as-is.
        OptionFormatter.Builder builderWithCustomName = OptionFormatter.builder().setDefaultArgName("foo");
        assertEquals("<foo>", builderWithCustomName.build(option).getArgName(),
                "Custom default arg name should be wrapped in angle brackets");

        // An empty string falls back to the built-in default "arg".
        OptionFormatter.Builder builderWithEmptyName = OptionFormatter.builder().setDefaultArgName("");
        assertEquals("<arg>", builderWithEmptyName.build(option).getArgName(),
                "Empty default arg name should fall back to built-in default '<arg>'");

        // null also falls back to the built-in default "arg".
        OptionFormatter.Builder builderWithNullName = OptionFormatter.builder().setDefaultArgName(null);
        assertEquals("<arg>", builderWithNullName.build(option).getArgName(),
                "Null default arg name should fall back to built-in default '<arg>'");
    }
}
