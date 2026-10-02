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

import java.util.Properties;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

@SuppressWarnings("deprecation") // tests some deprecated classes
class OptionGroupTest {

    private Options options;
    private final Parser parser = new PosixParser();

    @BeforeEach
    public void setUp() {
        final Option file = new Option("f", "file", false, "file to process");
        final Option dir = new Option("d", "directory", false, "directory to process");
        final OptionGroup fileOrDirGroup = new OptionGroup();
        fileOrDirGroup.addOption(file);
        fileOrDirGroup.addOption(dir);
        options = new Options().addOptionGroup(fileOrDirGroup);

        final Option section = new Option("s", "section", false, "section to process");
        final Option chapter = new Option("c", "chapter", false, "chapter to process");
        final OptionGroup sectionOrChapterGroup = new OptionGroup();
        sectionOrChapterGroup.addOption(section);
        sectionOrChapterGroup.addOption(chapter);
        options.addOptionGroup(sectionOrChapterGroup);

        final Option importOpt = new Option(null, "import", false, "section to process");
        final Option exportOpt = new Option(null, "export", false, "chapter to process");
        final OptionGroup importOrExportGroup = new OptionGroup();
        importOrExportGroup.addOption(importOpt);
        importOrExportGroup.addOption(exportOpt);
        options.addOptionGroup(importOrExportGroup);

        options.addOption("r", "revision", false, "revision number");
    }

    private void assertOptionsNotSet(CommandLine cl, String... optionNames) {
        for (String opt : optionNames) {
            assertFalse(cl.hasOption(opt), "Confirm -" + opt + " is NOT set");
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
        assertOptionsNotSet(cl, "r", "f", "d", "s", "c");
        assertEquals(2, cl.getArgList().size(), "Confirm TWO extra args");
    }

    @Test
    void testSingleLongOption() throws Exception {
        final String[] args = {"--file"};
        final CommandLine cl = parser.parse(options, args);
        assertOptionsNotSet(cl, "r", "d", "s", "c");
        assertTrue(cl.hasOption("f"), "Confirm -f is set");
        assertTrue(cl.getArgList().isEmpty(), "Confirm no extra args");
    }

    @Test
    void testSingleOption() throws Exception {
        final String[] args = {"-r"};
        final CommandLine cl = parser.parse(options, args);
        assertTrue(cl.hasOption("r"), "Confirm -r is set");
        assertOptionsNotSet(cl, "f", "d", "s", "c");
        assertTrue(cl.getArgList().isEmpty(), "Confirm no extra args");
    }

    @Test
    void testSingleOptionFromGroup() throws Exception {
        final String[] args = {"-f"};
        final CommandLine cl = parser.parse(options, args);
        assertOptionsNotSet(cl, "r", "d", "s", "c");
        assertTrue(cl.hasOption("f"), "Confirm -f is set");
        assertTrue(cl.getArgList().isEmpty(), "Confirm no extra args");
    }

    @Test
    void testToString() {
        final OptionGroup longOnlyGroup = new OptionGroup();
        longOnlyGroup.addOption(new Option(null, "foo", false, "Foo"));
        longOnlyGroup.addOption(new Option(null, "bar", false, "Bar"));
        final String longOnlyResult = longOnlyGroup.toString();
        assertTrue(
            "[--foo Foo, --bar Bar]".equals(longOnlyResult) || "[--bar Bar, --foo Foo]".equals(longOnlyResult),
            "Expected '[--foo Foo, --bar Bar]' or '[--bar Bar, --foo Foo]' but was: " + longOnlyResult
        );

        final OptionGroup shortAndLongGroup = new OptionGroup();
        shortAndLongGroup.addOption(new Option("f", "foo", false, "Foo"));
        shortAndLongGroup.addOption(new Option("b", "bar", false, "Bar"));
        final String shortAndLongResult = shortAndLongGroup.toString();
        assertTrue(
            "[-f Foo, -b Bar]".equals(shortAndLongResult) || "[-b Bar, -f Foo]".equals(shortAndLongResult),
            "Expected '[-f Foo, -b Bar]' or '[-b Bar, -f Foo]' but was: " + shortAndLongResult
        );
    }

    @Test
    void testTwoLongOptionsFromGroup() throws Exception {
        final String[] args = { "--file", "--directory" };
        final AlreadySelectedException e = assertThrows(AlreadySelectedException.class, () -> parser.parse(options, args));
        assertNotNull(e.getOptionGroup(), "null option group");
        assertTrue(e.getOptionGroup().isSelected());
        assertEquals("f", e.getOptionGroup().getSelected(), "selected option");
        assertEquals("d", e.getOption().getOpt(), "option");
    }

    @Test
    void testTwoOptionsFromDifferentGroup() throws Exception {
        final String[] args = {"-f", "-s"};
        final CommandLine cl = parser.parse(options, args);
        assertOptionsNotSet(cl, "r", "d", "c");
        assertTrue(cl.hasOption("f"), "Confirm -f is set");
        assertTrue(cl.hasOption("s"), "Confirm -s is set");
        assertTrue(cl.getArgList().isEmpty(), "Confirm NO extra args");
    }

    @Test
    void testTwoOptionsFromGroup() throws Exception {
        final String[] args = { "-f", "-d" };
        final AlreadySelectedException e = assertThrows(AlreadySelectedException.class, () -> parser.parse(options, args));
        assertNotNull(e.getOptionGroup(), "null option group");
        assertTrue(e.getOptionGroup().isSelected());
        assertEquals("f", e.getOptionGroup().getSelected(), "selected option");
        assertEquals("d", e.getOption().getOpt(), "option");
    }

    @Test
    void testTwoOptionsFromGroupWithProperties() throws Exception {
        // A property default cannot override a group selection already made on the command line
        final String[] args = {"-f"};
        final Properties properties = new Properties();
        properties.put("d", "true");
        final CommandLine cl = parser.parse(options, args, properties);
        assertTrue(cl.hasOption("f"));
        assertFalse(cl.hasOption("d"));
    }

    @Test
    void testTwoValidLongOptions() throws Exception {
        final String[] args = {"--revision", "--file"};
        final CommandLine cl = parser.parse(options, args);
        assertTrue(cl.hasOption("r"), "Confirm -r is set");
        assertTrue(cl.hasOption("f"), "Confirm -f is set");
        assertOptionsNotSet(cl, "d", "s", "c");
        assertTrue(cl.getArgList().isEmpty(), "Confirm no extra args");
    }

    @Test
    void testTwoValidOptions() throws Exception {
        final String[] args = {"-r", "-f"};
        final CommandLine cl = parser.parse(options, args);
        assertTrue(cl.hasOption("r"), "Confirm -r is set");
        assertTrue(cl.hasOption("f"), "Confirm -f is set");
        assertOptionsNotSet(cl, "d", "s", "c");
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
