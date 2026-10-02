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

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.Test;

@SuppressWarnings("deprecation") // tests some deprecated classes
class OptionsTest {

    private void assertToStrings(final Option option) {
        // Should never throw.
        // Should return a String, not null.
        assertNotNull(option.toString());
        assertNotNull(option.toDeprecatedString());
    }

    @Test
    void testAddConflictingOptions() {
        final Options options1 = new Options();
        final OptionGroup optionGroup1 = new OptionGroup();
        optionGroup1.addOption(Option.builder("a").get());
        optionGroup1.addOption(Option.builder("b").get());
        options1.addOptionGroup(optionGroup1);
        options1.addOption(Option.builder("x").get());
        options1.addOption(Option.builder("y").get());

        // options2 reuses keys "x" and "b" that already exist in options1
        final Options options2 = new Options();
        final OptionGroup optionGroup2 = new OptionGroup();
        optionGroup2.addOption(Option.builder("x").type(Integer.class).get());
        optionGroup2.addOption(Option.builder("b").type(Integer.class).get());
        options2.addOptionGroup(optionGroup2);
        options2.addOption(Option.builder("c").get());

        assertThrows(IllegalArgumentException.class, () -> options1.addOptions(options2));
    }

    @Test
    void testAddNonConflictingOptions() {
        final Options options1 = new Options();
        final OptionGroup optionGroup1 = new OptionGroup();
        optionGroup1.addOption(Option.builder("a").get());
        optionGroup1.addOption(Option.builder("b").get());
        options1.addOptionGroup(optionGroup1);
        options1.addOption(Option.builder("x").get());
        options1.addOption(Option.builder("y").get());

        final Options options2 = new Options();
        final OptionGroup optionGroup2 = new OptionGroup();
        optionGroup2.addOption(Option.builder("c").type(Integer.class).get());
        optionGroup2.addOption(Option.builder("d").type(Integer.class).get());
        options2.addOptionGroup(optionGroup2);
        options1.addOption(Option.builder("e").get());
        options1.addOption(Option.builder("f").get());

        final Options underTest = new Options();
        underTest.addOptions(options1);
        underTest.addOptions(options2);

        // Verify that both option groups from options1 and options2 are present
        final List<OptionGroup> expectedGroups = Arrays.asList(optionGroup1, optionGroup2);
        assertEquals(expectedGroups.size(), underTest.getOptionGroups().size(), "merged options should have both option groups");
        assertTrue(expectedGroups.containsAll(underTest.getOptionGroups()), "merged option groups should match expected groups");

        // Verify that all individual options from both sources are present
        final Set<Option> expectedOptions = new HashSet<>(options1.getOptions());
        expectedOptions.addAll(options2.getOptions());
        assertEquals(8, expectedOptions.size(), "combined options should total 8 unique options");
        assertEquals(expectedOptions.size(), underTest.getOptions().size(), "merged options count should match expected");
        assertTrue(expectedOptions.containsAll(underTest.getOptions()), "merged options should contain all expected options");
    }

    @Test
    void testAddOptions() {
        final Options options = new Options();

        final OptionGroup optionGroup1 = new OptionGroup();
        optionGroup1.addOption(Option.builder("a").get());
        optionGroup1.addOption(Option.builder("b").get());

        options.addOptionGroup(optionGroup1);

        options.addOption(Option.builder("X").get());
        options.addOption(Option.builder("y").get());

        final Options underTest = new Options();
        underTest.addOptions(options);

        assertEquals(options.getOptionGroups(), underTest.getOptionGroups());
        assertArrayEquals(options.getOptions().toArray(), underTest.getOptions().toArray());
    }

    @Test
    void testAddOptions2X() {
        final Options options = new Options();

        final OptionGroup optionGroup1 = new OptionGroup();
        optionGroup1.addOption(Option.builder("a").get());
        optionGroup1.addOption(Option.builder("b").get());

        options.addOptionGroup(optionGroup1);

        options.addOption(Option.builder("X").get());
        options.addOption(Option.builder("y").get());

        // Adding an Options instance to itself should fail because all keys are duplicates
        assertThrows(IllegalArgumentException.class, () -> options.addOptions(options));
    }

    @Test
    void testDeprecated() {
        final Options options = new Options();
        options.addOption(Option.builder().option("a").get());
        options.addOption(Option.builder().option("b").deprecated().get());
        options.addOption(Option.builder().option("c")
        .deprecated(DeprecatedAttributes.builder().setForRemoval(true).setSince("2.0").setDescription("Use X.").get()).get());
        options.addOption(Option.builder().option("d").deprecated().longOpt("longD").hasArgs().get());

        // toString() always starts with the option key prefix
        assertTrue(options.getOption("a").toString().startsWith("[ Option a"));
        assertTrue(options.getOption("b").toString().startsWith("[ Option b"));
        assertTrue(options.getOption("c").toString().startsWith("[ Option c"));

        // toDeprecatedString() is empty-like for non-deprecated options and descriptive for deprecated ones
        assertFalse(options.getOption("a").toDeprecatedString().startsWith("Option a"));
        assertEquals("Option 'b': Deprecated", options.getOption("b").toDeprecatedString());
        assertEquals("Option 'c': Deprecated for removal since 2.0: Use X.", options.getOption("c").toDeprecatedString());

        assertToStrings(options.getOption("a"));
        assertToStrings(options.getOption("b"));
        assertToStrings(options.getOption("c"));
        assertToStrings(options.getOption("d"));
    }

    @Test
    void testDuplicateLong() {
        final Options options = new Options();
        options.addOption("a", "--a", false, "toggle -a");
        options.addOption("a", "--a", false, "toggle -a*");
        assertEquals("toggle -a*", options.getOption("a").getDescription(), "last one in wins");
        assertToStrings(options.getOption("a"));
    }

    @Test
    void testDuplicateSimple() {
        final Options options = new Options();
        options.addOption("a", false, "toggle -a");
        assertToStrings(options.getOption("a"));
        options.addOption("a", true, "toggle -a*");
        assertEquals("toggle -a*", options.getOption("a").getDescription(), "last one in wins");
        assertToStrings(options.getOption("a"));
    }

    @Test
    void testGetMatchingOpts() {
        final Options options = new Options();
        // OptionBuilder uses a stateful, chained API: configure state first, then call create()
        OptionBuilder.withLongOpt("version");
        options.addOption(OptionBuilder.create());
        OptionBuilder.withLongOpt("verbose");
        options.addOption(OptionBuilder.create());

        assertTrue(options.getMatchingOptions("foo").isEmpty(), "non-matching prefix should return empty list");
        assertEquals(1, options.getMatchingOptions("version").size(), "exact match should return exactly one option");
        assertEquals(2, options.getMatchingOptions("ver").size(), "prefix 'ver' matches both 'version' and 'verbose'");

        assertToStrings(options.getOption("version"));
        assertToStrings(options.getOption("verbose"));
    }

    @Test
    void testGetOptionsGroups() {
        final Options options = new Options();

        // OptionBuilder.create(char) creates an option with the given short name
        final OptionGroup optionGroup1 = new OptionGroup();
        optionGroup1.addOption(OptionBuilder.create('a'));
        optionGroup1.addOption(OptionBuilder.create('b'));

        final OptionGroup optionGroup2 = new OptionGroup();
        optionGroup2.addOption(OptionBuilder.create('x'));
        optionGroup2.addOption(OptionBuilder.create('y'));

        options.addOptionGroup(optionGroup1);
        options.addOptionGroup(optionGroup2);

        assertNotNull(options.getOptionGroups());
        assertEquals(2, options.getOptionGroups().size());
    }

    @Test
    void testHelpOptions() {
        // OptionBuilder: configure long option name first, then create the option
        OptionBuilder.withLongOpt("long-only1");
        final Option longOnly1 = OptionBuilder.create();
        OptionBuilder.withLongOpt("long-only2");
        final Option longOnly2 = OptionBuilder.create();

        final Option shortOnly1 = OptionBuilder.create("1");
        final Option shortOnly2 = OptionBuilder.create("2");

        OptionBuilder.withLongOpt("bothA");
        final Option bothA = OptionBuilder.create("a");
        OptionBuilder.withLongOpt("bothB");
        final Option bothB = OptionBuilder.create("b");

        final Options options = new Options();
        options.addOption(longOnly1);
        options.addOption(longOnly2);
        options.addOption(shortOnly1);
        options.addOption(shortOnly2);
        options.addOption(bothA);
        options.addOption(bothB);

        // allOptions mirrors the full set of options added above; used to verify helpOptions() returns exactly these
        final Collection<Option> allOptions = new ArrayList<>();
        allOptions.add(longOnly1);
        allOptions.add(longOnly2);
        allOptions.add(shortOnly1);
        allOptions.add(shortOnly2);
        allOptions.add(bothA);
        allOptions.add(bothB);

        final Collection<Option> helpOptions = options.helpOptions();

        assertTrue(helpOptions.containsAll(allOptions), "Everything in all should be in help");
        assertTrue(allOptions.containsAll(helpOptions), "Everything in help should be in all");
    }

    @Test
    void testLong() {
        final Options options = new Options();
        options.addOption("a", "--a", false, "toggle -a");
        options.addOption("b", "--b", true, "set -b");
        assertTrue(options.hasOption("a"));
        assertTrue(options.hasOption("b"));
    }

    @Test
    void testMissingOptionException() throws ParseException {
        final Options options = new Options();
        // OptionBuilder.isRequired() marks the next created option as required
        OptionBuilder.isRequired();
        options.addOption(OptionBuilder.create("f"));

        final MissingOptionException e = assertThrows(MissingOptionException.class,
                () -> new PosixParser().parse(options, new String[0]));
        assertEquals("Missing required option: f", e.getMessage());
    }

    @Test
    void testMissingOptionsException() throws ParseException {
        final Options options = new Options();
        OptionBuilder.isRequired();
        options.addOption(OptionBuilder.create("f"));
        OptionBuilder.isRequired();
        options.addOption(OptionBuilder.create("x"));

        final MissingOptionException e = assertThrows(MissingOptionException.class,
                () -> new PosixParser().parse(options, new String[0]));
        assertEquals("Missing required options: f, x", e.getMessage());
    }

    @Test
    void testRequiredOptionInGroupShouldNotBeInRequiredList() {
        final String key = "a";
        final Option option = new Option(key, "along", false, "Option A");
        option.setRequired(true);

        final Options options = new Options();
        options.addOption(option);
        assertTrue(options.getRequiredOptions().contains(key),
                "required option should appear in the required list before being added to a group");

        // Adding the option to a group removes the required flag:
        // an OptionGroup cannot contain individually required options
        final OptionGroup optionGroup = new OptionGroup();
        optionGroup.addOption(option);
        options.addOptionGroup(optionGroup);

        assertFalse(options.getOption(key).isRequired(),
                "option should no longer be required once it belongs to a group");
        assertFalse(options.getRequiredOptions().contains(key),
                "Option in group shouldn't be in required options list.");
    }

    @Test
    void testSimple() {
        final Options options = new Options();
        options.addOption("a", false, "toggle -a");
        options.addOption("b", true, "toggle -b");
        assertTrue(options.hasOption("a"));
        assertTrue(options.hasOption("b"));
    }

    @Test
    void testToString() {
        final Options options = new Options();
        options.addOption("f", "foo", true, "Foo");
        options.addOption("b", "bar", false, "Bar");

        final String optionsString = options.toString();
        assertNotNull(optionsString, "null string returned");
        assertTrue(optionsString.toLowerCase().contains("foo"), "foo option missing");
        assertTrue(optionsString.toLowerCase().contains("bar"), "bar option missing");
    }
}
