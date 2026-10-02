package org.apache.commons.cli;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

// tests some deprecated classes
@SuppressWarnings("deprecation")
public class OptionGroupTest_testToString {

    private static final String LONG_OPTIONS_BAR_THEN_FOO = "[--bar Bar, --foo Foo]";
    private static final String LONG_OPTIONS_FOO_THEN_BAR = "[--foo Foo, --bar Bar]";
    private static final String SHORT_OPTIONS_BAR_THEN_FOO = "[-b Bar, -f Foo]";
    private static final String SHORT_OPTIONS_FOO_THEN_BAR = "[-f Foo, -b Bar]";

    private Options options;

    private final Parser parser = new PosixParser();

    @BeforeEach
    public void setUp() {
        final Option file = new Option("f", "file", false, "file to process");
        final Option dir = new Option("d", "directory", false, "directory to process");
        options = new Options().addOptionGroup(optionGroupWith(file, dir));

        final Option section = new Option("s", "section", false, "section to process");
        final Option chapter = new Option("c", "chapter", false, "chapter to process");
        options.addOptionGroup(optionGroupWith(section, chapter));

        final Option importOpt = new Option(null, "import", false, "section to process");
        final Option exportOpt = new Option(null, "export", false, "chapter to process");
        options.addOptionGroup(optionGroupWith(importOpt, exportOpt));

        options.addOption("r", "revision", false, "revision number");
    }

    @Test
    void testToString() {
        final OptionGroup longOnlyOptions = optionGroupWith(
                new Option(null, "foo", false, "Foo"),
                new Option(null, "bar", false, "Bar"));
        assertToStringAllowsEitherOrder(longOnlyOptions, LONG_OPTIONS_BAR_THEN_FOO, LONG_OPTIONS_FOO_THEN_BAR);

        final OptionGroup shortOptions = optionGroupWith(
                new Option("f", "foo", false, "Foo"),
                new Option("b", "bar", false, "Bar"));
        assertToStringAllowsEitherOrder(shortOptions, SHORT_OPTIONS_BAR_THEN_FOO, SHORT_OPTIONS_FOO_THEN_BAR);
    }

    private static OptionGroup optionGroupWith(final Option firstOption, final Option secondOption) {
        final OptionGroup optionGroup = new OptionGroup();
        optionGroup.addOption(firstOption);
        optionGroup.addOption(secondOption);
        return optionGroup;
    }

    private static void assertToStringAllowsEitherOrder(
            final OptionGroup optionGroup,
            final String acceptedOrder,
            final String fallbackOrder) {
        if (!acceptedOrder.equals(optionGroup.toString())) {
            assertEquals(fallbackOrder, optionGroup.toString());
        }
    }
}
