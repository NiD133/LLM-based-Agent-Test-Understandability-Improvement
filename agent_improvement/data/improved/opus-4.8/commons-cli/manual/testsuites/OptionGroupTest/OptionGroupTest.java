/*
  Licensed to the Apache Software Foundation (ASF) under one or more
  contributor license agreements.  See the NOTICE file distributed with
  this work for additional information regarding copyright ownership.
  The ASF licenses this file to You under the Apache License, Version 2.0
  (the "License"); you may not use this file except in compliance with
  the License.  You may obtain a copy of the License at

      https://www.apache.org/licenses/LICENSE-2.0

  Unless required by applicable law or agreed to in writing, software
  distributed under the License is distributed on an "AS IS" BASIS,
  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
  See the License for the specific language governing permissions and
  limitations under the License.
 */

package org.apache.commons.cli;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Properties;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Tests how {@link OptionGroup} interacts with the parser to enforce mutual
 * exclusivity between options.
 *
 * <p>The fixture built in {@link #setUp()} contains three mutually exclusive
 * groups plus one standalone option:</p>
 * <ul>
 *   <li>Group 1: {@code -f/--file} and {@code -d/--directory}</li>
 *   <li>Group 2: {@code -s/--section} and {@code -c/--chapter}</li>
 *   <li>Group 3 (long only): {@code --import} and {@code --export}</li>
 *   <li>Standalone: {@code -r/--revision}</li>
 * </ul>
 */
@SuppressWarnings("deprecation") // tests some deprecated classes
class OptionGroupTest {

    /**
     * The short names of every single-letter option registered in {@link #options}.
     * Used by {@link #assertOnlySet} to verify the full set of options at once.
     */
    private static final String[] ALL_SHORT_OPTIONS = {"r", "f", "d", "s", "c"};

    private Options options;
    private final Parser parser = new PosixParser();

    @BeforeEach
    public void setUp() {
        // Group 1: file vs. directory (mutually exclusive).
        final Option file = new Option("f", "file", false, "file to process");
        final Option dir = new Option("d", "directory", false, "directory to process");
        final OptionGroup fileOrDirectory = new OptionGroup();
        fileOrDirectory.addOption(file);
        fileOrDirectory.addOption(dir);
        options = new Options().addOptionGroup(fileOrDirectory);

        // Group 2: section vs. chapter (mutually exclusive).
        final Option section = new Option("s", "section", false, "section to process");
        final Option chapter = new Option("c", "chapter", false, "chapter to process");
        final OptionGroup sectionOrChapter = new OptionGroup();
        sectionOrChapter.addOption(section);
        sectionOrChapter.addOption(chapter);
        options.addOptionGroup(sectionOrChapter);

        // Group 3: import vs. export, both long-only (no short name).
        final Option importOpt = new Option(null, "import", false, "section to process");
        final Option exportOpt = new Option(null, "export", false, "chapter to process");
        final OptionGroup importOrExport = new OptionGroup();
        importOrExport.addOption(importOpt);
        importOrExport.addOption(exportOpt);
        options.addOptionGroup(importOrExport);

        // Standalone option, not part of any group.
        options.addOption("r", "revision", false, "revision number");
    }

    /**
     * Asserts that exactly the given short options are present on the command line
     * and that all other known short options are absent.
     *
     * @param cl       the parsed command line to inspect.
     * @param setOpts  the short option names expected to be set.
     */
    private void assertOnlySet(final CommandLine cl, final String... setOpts) {
        final Set<String> expectedSet = new HashSet<>(Arrays.asList(setOpts));
        for (final String opt : ALL_SHORT_OPTIONS) {
            if (expectedSet.contains(opt)) {
                assertTrue(cl.hasOption(opt), "Confirm -" + opt + " is set");
            } else {
                assertFalse(cl.hasOption(opt), "Confirm -" + opt + " is NOT set");
            }
        }
    }

    @Test
    void testGetNames() {
        final OptionGroup optionGroup = new OptionGroup();
        assertFalse(optionGroup.isSelected());
        optionGroup.addOption(OptionBuilder.create('a'));
        optionGroup.addOption(OptionBuilder.create('b'));
        assertNotNull(optionGroup.getNames(), "null names");
        assertEquals(2, optionGroup.getNames().size());
        assertTrue(optionGroup.getNames().contains("a"));
        assertTrue(optionGroup.getNames().contains("b"));
    }

    @Test
    void testNoOptionsExtraArgs() throws Exception {
        final String[] args = {"arg1", "arg2"};
        final CommandLine cl = parser.parse(options, args);
        assertOnlySet(cl); // nothing selected
        assertEquals(2, cl.getArgList().size(), "Confirm TWO extra args");
    }

    @Test
    void testSingleLongOption() throws Exception {
        final String[] args = {"--file"};
        final CommandLine cl = parser.parse(options, args);
        assertOnlySet(cl, "f");
        assertTrue(cl.getArgList().isEmpty(), "Confirm no extra args");
    }

    @Test
    void testSingleOption() throws Exception {
        final String[] args = {"-r"};
        final CommandLine cl = parser.parse(options, args);
        assertOnlySet(cl, "r");
        assertTrue(cl.getArgList().isEmpty(), "Confirm no extra args");
    }

    @Test
    void testSingleOptionFromGroup() throws Exception {
        final String[] args = {"-f"};
        final CommandLine cl = parser.parse(options, args);
        assertOnlySet(cl, "f");
        assertTrue(cl.getArgList().isEmpty(), "Confirm no extra args");
    }

    @Test
    void testToString() {
        // Long-only options: order of iteration is not guaranteed, so accept either.
        final OptionGroup longOnlyGroup = new OptionGroup();
        longOnlyGroup.addOption(new Option(null, "foo", false, "Foo"));
        longOnlyGroup.addOption(new Option(null, "bar", false, "Bar"));
        if (!"[--bar Bar, --foo Foo]".equals(longOnlyGroup.toString())) {
            assertEquals("[--foo Foo, --bar Bar]", longOnlyGroup.toString());
        }

        // Options with short names: again accept either iteration order.
        final OptionGroup shortNamedGroup = new OptionGroup();
        shortNamedGroup.addOption(new Option("f", "foo", false, "Foo"));
        shortNamedGroup.addOption(new Option("b", "bar", false, "Bar"));
        if (!"[-b Bar, -f Foo]".equals(shortNamedGroup.toString())) {
            assertEquals("[-f Foo, -b Bar]", shortNamedGroup.toString());
        }
    }

    @Test
    void testTwoLongOptionsFromGroup() throws Exception {
        // --file and --directory belong to the same group, so the second one is rejected.
        final String[] args = {"--file", "--directory"};
        final AlreadySelectedException e = assertThrows(AlreadySelectedException.class, () -> parser.parse(options, args));
        assertNotNull(e.getOptionGroup(), "null option group");
        assertTrue(e.getOptionGroup().isSelected());
        assertEquals("f", e.getOptionGroup().getSelected(), "selected option");
        assertEquals("d", e.getOption().getOpt(), "option");
    }

    @Test
    void testTwoOptionsFromDifferentGroup() throws Exception {
        // -f and -s come from different groups, so both are allowed.
        final String[] args = {"-f", "-s"};
        final CommandLine cl = parser.parse(options, args);
        assertOnlySet(cl, "f", "s");
        assertTrue(cl.getArgList().isEmpty(), "Confirm NO extra args");
    }

    @Test
    void testTwoOptionsFromGroup() throws Exception {
        // -f and -d belong to the same group, so the second one is rejected.
        final String[] args = {"-f", "-d"};
        final AlreadySelectedException e = assertThrows(AlreadySelectedException.class, () -> parser.parse(options, args));
        assertNotNull(e.getOptionGroup(), "null option group");
        assertTrue(e.getOptionGroup().isSelected());
        assertEquals("f", e.getOptionGroup().getSelected(), "selected option");
        assertEquals("d", e.getOption().getOpt(), "option");
    }

    @Test
    void testTwoOptionsFromGroupWithProperties() throws Exception {
        // -f is selected on the command line; the -d default from properties must be
        // ignored because it conflicts with the already-selected option in the group.
        final String[] args = {"-f"};
        final Properties properties = new Properties();
        properties.put("d", "true");
        final CommandLine cl = parser.parse(options, args, properties);
        assertTrue(cl.hasOption("f"));
        assertFalse(cl.hasOption("d"));
    }

    @Test
    void testTwoValidLongOptions() throws Exception {
        // --revision is standalone and --file is from a group: no conflict.
        final String[] args = {"--revision", "--file"};
        final CommandLine cl = parser.parse(options, args);
        assertOnlySet(cl, "r", "f");
        assertTrue(cl.getArgList().isEmpty(), "Confirm no extra args");
    }

    @Test
    void testTwoValidOptions() throws Exception {
        // -r is standalone and -f is from a group: no conflict.
        final String[] args = {"-r", "-f"};
        final CommandLine cl = parser.parse(options, args);
        assertOnlySet(cl, "r", "f");
        assertTrue(cl.getArgList().isEmpty(), "Confirm no extra args");
    }

    @Test
    void testValidLongOnlyOptions() throws Exception {
        final CommandLine cl1 = parser.parse(options, new String[] {"--export"});
        assertTrue(cl1.hasOption("export"), "Confirm --export is set");
        final CommandLine cl2 = parser.parse(options, new String[] {"--import"});
        assertTrue(cl2.hasOption("import"), "Confirm --import is set");
    }
}
