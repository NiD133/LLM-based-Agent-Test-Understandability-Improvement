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

    private static final String REVISION = "r";
    private static final String FILE = "f";
    private static final String DIRECTORY = "d";
    private static final String SECTION = "s";
    private static final String CHAPTER = "c";

    private Options options;
    private final Parser parser = new PosixParser();

    @BeforeEach
    public void setUp() {
        final Option file = new Option(FILE, "file", false, "file to process");
        final Option dir = new Option(DIRECTORY, "directory", false, "directory to process");
        options = new Options().addOptionGroup(optionGroup(file, dir));

        final Option section = new Option(SECTION, "section", false, "section to process");
        final Option chapter = new Option(CHAPTER, "chapter", false, "chapter to process");
        options.addOptionGroup(optionGroup(section, chapter));

        final Option importOpt = new Option(null, "import", false, "section to process");
        final Option exportOpt = new Option(null, "export", false, "chapter to process");
        options.addOptionGroup(optionGroup(importOpt, exportOpt));

        options.addOption(REVISION, "revision", false, "revision number");
    }

    @Test
    void testGetNames() {
        final OptionGroup optionGroup = optionGroup(OptionBuilder.create('a'), OptionBuilder.create('b'));

        assertFalse(optionGroup.isSelected());
        assertNotNull(optionGroup.getNames(), "null names");
        assertEquals(2, optionGroup.getNames().size());
        assertTrue(optionGroup.getNames().contains("a"));
        assertTrue(optionGroup.getNames().contains("b"));
    }

    @Test
    void testNoOptionsExtraArgs() throws Exception {
        final String[] args = {"arg1", "arg2"};
        final CommandLine cl = parser.parse(options, args);

        assertStandardOptions(cl, false, false, false, false, false, "Confirm TWO extra args");
        assertEquals(2, cl.getArgList().size(), "Confirm TWO extra args");
    }

    @Test
    void testSingleLongOption() throws Exception {
        final String[] args = {"--file"};
        final CommandLine cl = parser.parse(options, args);

        assertStandardOptions(cl, false, true, false, false, false, "Confirm no extra args");
        assertTrue(cl.getArgList().isEmpty(), "Confirm no extra args");
    }

    @Test
    void testSingleOption() throws Exception {
        final String[] args = {"-r"};
        final CommandLine cl = parser.parse(options, args);

        assertStandardOptions(cl, true, false, false, false, false, "Confirm no extra args");
        assertTrue(cl.getArgList().isEmpty(), "Confirm no extra args");
    }

    @Test
    void testSingleOptionFromGroup() throws Exception {
        final String[] args = {"-f"};
        final CommandLine cl = parser.parse(options, args);

        assertStandardOptions(cl, false, true, false, false, false, "Confirm no extra args");
        assertTrue(cl.getArgList().isEmpty(), "Confirm no extra args");
    }

    @Test
    void testToString() {
        final OptionGroup optionGroup1 = optionGroup(new Option(null, "foo", false, "Foo"), new Option(null, "bar", false, "Bar"));
        if (!"[--bar Bar, --foo Foo]".equals(optionGroup1.toString())) {
            assertEquals("[--foo Foo, --bar Bar]", optionGroup1.toString());
        }

        final OptionGroup optionGroup2 = optionGroup(new Option("f", "foo", false, "Foo"), new Option("b", "bar", false, "Bar"));
        if (!"[-b Bar, -f Foo]".equals(optionGroup2.toString())) {
            assertEquals("[-f Foo, -b Bar]", optionGroup2.toString());
        }
    }

    @Test
    void testTwoLongOptionsFromGroup() throws Exception {
        final String[] args = {"--file", "--directory"};

        final AlreadySelectedException e = assertThrows(AlreadySelectedException.class, () -> parser.parse(options, args));
        assertMutuallyExclusiveOptionFailure(e, "f", "d");
    }

    @Test
    void testTwoOptionsFromDifferentGroup() throws Exception {
        final String[] args = {"-f", "-s"};
        final CommandLine cl = parser.parse(options, args);

        assertStandardOptions(cl, false, true, false, true, false, "Confirm NO extra args");
        assertTrue(cl.getArgList().isEmpty(), "Confirm NO extra args");
    }

    @Test
    void testTwoOptionsFromGroup() throws Exception {
        final String[] args = {"-f", "-d"};

        final AlreadySelectedException e = assertThrows(AlreadySelectedException.class, () -> parser.parse(options, args));
        assertMutuallyExclusiveOptionFailure(e, "f", "d");
    }

    @Test
    void testTwoOptionsFromGroupWithProperties() throws Exception {
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

        assertStandardOptions(cl, true, true, false, false, false, "Confirm no extra args");
        assertTrue(cl.getArgList().isEmpty(), "Confirm no extra args");
    }

    @Test
    void testTwoValidOptions() throws Exception {
        final String[] args = {"-r", "-f"};
        final CommandLine cl = parser.parse(options, args);

        assertStandardOptions(cl, true, true, false, false, false, "Confirm no extra args");
        assertTrue(cl.getArgList().isEmpty(), "Confirm no extra args");
    }

    @Test
    void testValidLongOnlyOptions() throws Exception {
        final CommandLine cl1 = parser.parse(options, new String[] {"--export"});
        assertTrue(cl1.hasOption("export"), "Confirm --export is set");

        final CommandLine cl2 = parser.parse(options, new String[] {"--import"});
        assertTrue(cl2.hasOption("import"), "Confirm --import is set");
    }

    private static OptionGroup optionGroup(final Option... options) {
        final OptionGroup optionGroup = new OptionGroup();
        for (final Option option : options) {
            optionGroup.addOption(option);
        }
        return optionGroup;
    }

    private static void assertMutuallyExclusiveOptionFailure(final AlreadySelectedException exception, final String selectedOption,
            final String conflictingOption) {
        assertNotNull(exception.getOptionGroup(), "null option group");
        assertTrue(exception.getOptionGroup().isSelected());
        assertEquals(selectedOption, exception.getOptionGroup().getSelected(), "selected option");
        assertEquals(conflictingOption, exception.getOption().getOpt(), "option");
    }

    private static void assertStandardOptions(final CommandLine commandLine, final boolean revision, final boolean file,
            final boolean directory, final boolean section, final boolean chapter, final String extraArgsMessage) {
        assertOption(commandLine, REVISION, revision, "Confirm -r is set", "Confirm -r is NOT set");
        assertOption(commandLine, FILE, file, "Confirm -f is set", "Confirm -f is NOT set");
        assertOption(commandLine, DIRECTORY, directory, "Confirm -d is set", "Confirm -d is NOT set");
        assertOption(commandLine, SECTION, section, "Confirm -s is set", "Confirm -s is NOT set");
        assertOption(commandLine, CHAPTER, chapter, "Confirm -c is set", "Confirm -c is NOT set");
        if (extraArgsMessage != null) {
            commandLine.getArgList();
        }
    }

    private static void assertOption(final CommandLine commandLine, final String option, final boolean expected, final String trueMessage,
            final String falseMessage) {
        if (expected) {
            assertTrue(commandLine.hasOption(option), trueMessage);
        } else {
            assertFalse(commandLine.hasOption(option), falseMessage);
        }
    }
}
