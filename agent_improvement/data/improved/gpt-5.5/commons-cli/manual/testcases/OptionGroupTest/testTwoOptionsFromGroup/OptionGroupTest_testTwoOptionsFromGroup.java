package org.apache.commons.cli;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

// tests some deprecated classes
@SuppressWarnings("deprecation")
public class OptionGroupTest_testTwoOptionsFromGroup {

    private static final String FILE_OPTION = "f";
    private static final String DIRECTORY_OPTION = "d";

    private Options options;

    private final Parser parser = new PosixParser();

    @BeforeEach
    public void setUp() {
        final Option file = new Option(FILE_OPTION, "file", false, "file to process");
        final Option dir = new Option(DIRECTORY_OPTION, "directory", false, "directory to process");
        final OptionGroup fileSystemOptions = new OptionGroup();
        fileSystemOptions.addOption(file);
        fileSystemOptions.addOption(dir);
        options = new Options().addOptionGroup(fileSystemOptions);

        final Option section = new Option("s", "section", false, "section to process");
        final Option chapter = new Option("c", "chapter", false, "chapter to process");
        final OptionGroup documentOptions = new OptionGroup();
        documentOptions.addOption(section);
        documentOptions.addOption(chapter);
        options.addOptionGroup(documentOptions);

        final Option importOpt = new Option(null, "import", false, "section to process");
        final Option exportOpt = new Option(null, "export", false, "chapter to process");
        final OptionGroup transferOptions = new OptionGroup();
        transferOptions.addOption(importOpt);
        transferOptions.addOption(exportOpt);
        options.addOptionGroup(transferOptions);

        options.addOption("r", "revision", false, "revision number");
    }

    @Test
    void testTwoOptionsFromGroup() throws Exception {
        final String[] args = { "-" + FILE_OPTION, "-" + DIRECTORY_OPTION };

        final AlreadySelectedException e = assertThrows(AlreadySelectedException.class, () -> parser.parse(options, args));

        assertNotNull(e.getOptionGroup(), "null option group");
        assertTrue(e.getOptionGroup().isSelected());
        assertEquals(FILE_OPTION, e.getOptionGroup().getSelected(), "selected option");
        assertEquals(DIRECTORY_OPTION, e.getOption().getOpt(), "option");
    }
}
