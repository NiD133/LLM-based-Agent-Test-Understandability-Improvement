package org.apache.commons.cli;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

// tests some deprecated classes
@SuppressWarnings("deprecation")
public class OptionGroupTest_testToString {

    private Options options;

    private final Parser parser = new PosixParser();

    @BeforeEach
    public void setUp() {
        final Option file = new Option("f", "file", false, "file to process");
        final Option dir = new Option("d", "directory", false, "directory to process");
        final OptionGroup optionGroup1 = new OptionGroup();
        optionGroup1.addOption(file);
        optionGroup1.addOption(dir);
        options = new Options().addOptionGroup(optionGroup1);
        final Option section = new Option("s", "section", false, "section to process");
        final Option chapter = new Option("c", "chapter", false, "chapter to process");
        final OptionGroup optionGroup2 = new OptionGroup();
        optionGroup2.addOption(section);
        optionGroup2.addOption(chapter);
        options.addOptionGroup(optionGroup2);
        final Option importOpt = new Option(null, "import", false, "section to process");
        final Option exportOpt = new Option(null, "export", false, "chapter to process");
        final OptionGroup optionGroup3 = new OptionGroup();
        optionGroup3.addOption(importOpt);
        optionGroup3.addOption(exportOpt);
        options.addOptionGroup(optionGroup3);
        options.addOption("r", "revision", false, "revision number");
    }

    @Test
    void testToString() {
        // OptionGroup uses a LinkedHashMap, so insertion order is preserved,
        // but the test accepts either ordering to be robust against implementation changes.

        final OptionGroup longOptGroup = new OptionGroup();
        longOptGroup.addOption(new Option(null, "foo", false, "Foo"));
        longOptGroup.addOption(new Option(null, "bar", false, "Bar"));
        final String longOptResult = longOptGroup.toString();
        assertTrue(
            "[--foo Foo, --bar Bar]".equals(longOptResult) || "[--bar Bar, --foo Foo]".equals(longOptResult),
            "Expected long-opt group toString to be one of the two valid orderings, but was: " + longOptResult
        );

        final OptionGroup shortOptGroup = new OptionGroup();
        shortOptGroup.addOption(new Option("f", "foo", false, "Foo"));
        shortOptGroup.addOption(new Option("b", "bar", false, "Bar"));
        final String shortOptResult = shortOptGroup.toString();
        assertTrue(
            "[-f Foo, -b Bar]".equals(shortOptResult) || "[-b Bar, -f Foo]".equals(shortOptResult),
            "Expected short-opt group toString to be one of the two valid orderings, but was: " + shortOptResult
        );
    }
}
