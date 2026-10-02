package org.apache.commons.cli;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Properties;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

// Tests deprecated parser classes.
@SuppressWarnings("deprecation")
public class OptionGroupTest_testTwoOptionsFromGroupWithProperties {

    private Options options;

    private final Parser parser = new PosixParser();

    @BeforeEach
    public void setUp() {
        final Option file = new Option("f", "file", false, "file to process");
        final Option directory = new Option("d", "directory", false, "directory to process");
        final OptionGroup fileSourceGroup = new OptionGroup();
        fileSourceGroup.addOption(file);
        fileSourceGroup.addOption(directory);
        options = new Options().addOptionGroup(fileSourceGroup);

        final Option section = new Option("s", "section", false, "section to process");
        final Option chapter = new Option("c", "chapter", false, "chapter to process");
        final OptionGroup documentPartGroup = new OptionGroup();
        documentPartGroup.addOption(section);
        documentPartGroup.addOption(chapter);
        options.addOptionGroup(documentPartGroup);

        final Option importOpt = new Option(null, "import", false, "section to process");
        final Option exportOpt = new Option(null, "export", false, "chapter to process");
        final OptionGroup transferDirectionGroup = new OptionGroup();
        transferDirectionGroup.addOption(importOpt);
        transferDirectionGroup.addOption(exportOpt);
        options.addOptionGroup(transferDirectionGroup);

        options.addOption("r", "revision", false, "revision number");
    }

    @Test
    void testTwoOptionsFromGroupWithProperties() throws Exception {
        final String[] args = { "-f" };
        final Properties properties = new Properties();
        properties.put("d", "true");

        final CommandLine commandLine = parser.parse(options, args, properties);

        assertTrue(commandLine.hasOption("f"));
        assertFalse(commandLine.hasOption("d"));
    }
}
