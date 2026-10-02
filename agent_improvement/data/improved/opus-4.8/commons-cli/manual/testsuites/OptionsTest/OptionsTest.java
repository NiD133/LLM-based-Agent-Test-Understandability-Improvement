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

/**
 * Tests for {@link Options}, the registry that collects {@link Option} and
 * {@link OptionGroup} instances for a command line.
 */
@SuppressWarnings("deprecation") // tests some deprecated classes
class OptionsTest {

    /**
     * Builds a bare {@link Option} that carries only the given short name.
     */
    private static Option shortOption(final String shortName) {
        return Option.builder(shortName).get();
    }

    /**
     * Builds an {@link OptionGroup} containing one bare option per supplied short name.
     */
    private static OptionGroup groupOf(final String... shortNames) {
        final OptionGroup group = new OptionGroup();
        for (final String shortName : shortNames) {
            group.addOption(shortOption(shortName));
        }
        return group;
    }

    /**
     * Asserts that both string renderings of an option are non-null and never throw.
     */
    private void assertToStringsNeverNull(final Option option) {
        assertNotNull(option.toString());
        assertNotNull(option.toDeprecatedString());
    }

    @Test
    void testAddConflictingOptions() {
        // 'b' is shared between a group of options1 and a group of options2,
        // and 'x' is shared between a single option and a group, so merging must fail.
        final Options options1 = new Options();
        options1.addOptionGroup(groupOf("a", "b"));
        options1.addOption(shortOption("x"));
        options1.addOption(shortOption("y"));

        final Options options2 = new Options();
        final OptionGroup conflictingGroup = new OptionGroup();
        conflictingGroup.addOption(Option.builder("x").type(Integer.class).get());
        conflictingGroup.addOption(Option.builder("b").type(Integer.class).get());
        options2.addOptionGroup(conflictingGroup);
        options2.addOption(shortOption("c"));

        assertThrows(IllegalArgumentException.class, () -> options1.addOptions(options2));
    }

    @Test
    void testAddNonConflictingOptions() {
        final OptionGroup group1 = groupOf("a", "b");
        final Options options1 = new Options();
        options1.addOptionGroup(group1);
        options1.addOption(shortOption("x"));
        options1.addOption(shortOption("y"));

        final Options options2 = new Options();
        final OptionGroup group2 = new OptionGroup();
        group2.addOption(Option.builder("c").type(Integer.class).get());
        group2.addOption(Option.builder("d").type(Integer.class).get());
        options2.addOptionGroup(group2);
        // Note: these two are intentionally added to options1, matching the original test.
        options1.addOption(shortOption("e"));
        options1.addOption(shortOption("f"));

        final Options underTest = new Options();
        underTest.addOptions(options1);
        underTest.addOptions(options2);

        // The merged groups are exactly the two source groups (order independent).
        final List<OptionGroup> expectedGroups = Arrays.asList(group1, group2);
        final Collection<OptionGroup> actualGroups = underTest.getOptionGroups();
        assertEquals(expectedGroups.size(), actualGroups.size());
        assertTrue(actualGroups.containsAll(expectedGroups));

        // The merged options are exactly the union of both sources: a, b, x, y, e, f, c, d.
        final Set<Option> expectedOptions = new HashSet<>(options1.getOptions());
        expectedOptions.addAll(options2.getOptions());
        assertEquals(8, expectedOptions.size());
        final Collection<Option> actualOptions = underTest.getOptions();
        assertEquals(expectedOptions.size(), actualOptions.size());
        assertTrue(actualOptions.containsAll(expectedOptions));
    }

    @Test
    void testAddOptions() {
        final Options source = new Options();
        source.addOptionGroup(groupOf("a", "b"));
        source.addOption(shortOption("X"));
        source.addOption(shortOption("y"));

        final Options underTest = new Options();
        underTest.addOptions(source);

        // Adding into an empty Options must reproduce the source exactly.
        assertEquals(source.getOptionGroups(), underTest.getOptionGroups());
        assertArrayEquals(source.getOptions().toArray(), underTest.getOptions().toArray());
    }

    @Test
    void testAddOptions2X() {
        final Options options = new Options();
        options.addOptionGroup(groupOf("a", "b"));
        options.addOption(shortOption("X"));
        options.addOption(shortOption("y"));

        // Adding an Options to itself duplicates every key, which is rejected.
        assertThrows(IllegalArgumentException.class, () -> options.addOptions(options));
    }

    @Test
    void testDeprecated() {
        final Options options = new Options();
        options.addOption(Option.builder().option("a").get());
        options.addOption(Option.builder().option("b").deprecated().get());
        options.addOption(Option.builder().option("c").deprecated(
                DeprecatedAttributes.builder().setForRemoval(true).setSince("2.0").setDescription("Use X.").get()).get());
        options.addOption(Option.builder().option("d").deprecated().longOpt("longD").hasArgs().get());

        // toString() always renders as a generic option dump regardless of deprecation.
        assertTrue(options.getOption("a").toString().startsWith("[ Option a"));
        assertTrue(options.getOption("b").toString().startsWith("[ Option b"));
        assertTrue(options.getOption("c").toString().startsWith("[ Option c"));

        // toDeprecatedString() describes the deprecation; a non-deprecated option is not labeled.
        assertFalse(options.getOption("a").toDeprecatedString().startsWith("Option a"));
        assertEquals("Option 'b': Deprecated", options.getOption("b").toDeprecatedString());
        assertEquals("Option 'c': Deprecated for removal since 2.0: Use X.", options.getOption("c").toDeprecatedString());

        assertToStringsNeverNull(options.getOption("a"));
        assertToStringsNeverNull(options.getOption("b"));
        assertToStringsNeverNull(options.getOption("c"));
        assertToStringsNeverNull(options.getOption("d"));
    }

    @Test
    void testDuplicateLong() {
        final Options options = new Options();
        options.addOption("a", "--a", false, "toggle -a");
        options.addOption("a", "--a", false, "toggle -a*");

        assertEquals("toggle -a*", options.getOption("a").getDescription(), "last one in wins");
        assertToStringsNeverNull(options.getOption("a"));
    }

    @Test
    void testDuplicateSimple() {
        final Options options = new Options();
        options.addOption("a", false, "toggle -a");
        assertToStringsNeverNull(options.getOption("a"));

        options.addOption("a", true, "toggle -a*");
        assertEquals("toggle -a*", options.getOption("a").getDescription(), "last one in wins");
        assertToStringsNeverNull(options.getOption("a"));
    }

    @Test
    void testGetMatchingOpts() {
        final Options options = new Options();
        OptionBuilder.withLongOpt("version");
        options.addOption(OptionBuilder.create());
        OptionBuilder.withLongOpt("verbose");
        options.addOption(OptionBuilder.create());

        assertTrue(options.getMatchingOptions("foo").isEmpty(), "no option matches 'foo'");
        assertEquals(1, options.getMatchingOptions("version").size(), "exact match returns one");
        assertEquals(2, options.getMatchingOptions("ver").size(), "'ver' prefixes both options");

        assertToStringsNeverNull(options.getOption("version"));
        assertToStringsNeverNull(options.getOption("verbose"));
    }

    @Test
    void testGetOptionsGroups() {
        final Options options = new Options();

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

        final Collection<Option> allOptions = new ArrayList<>(
                Arrays.asList(longOnly1, longOnly2, shortOnly1, shortOnly2, bothA, bothB));

        final Collection<Option> helpOptions = options.helpOptions();

        // helpOptions() and the full set must contain exactly the same options.
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
        OptionBuilder.isRequired();
        options.addOption(OptionBuilder.create("f"));

        // Parsing with no arguments must report the single missing required option.
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

        // Parsing with no arguments must report both missing required options.
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
        // While standalone, the required option is tracked in the required list.
        assertTrue(options.getRequiredOptions().contains(key));

        // Once the option joins a group, the group (not the option) carries the requirement.
        final OptionGroup optionGroup = new OptionGroup();
        optionGroup.addOption(option);
        options.addOptionGroup(optionGroup);
        assertFalse(options.getOption(key).isRequired());
        assertFalse(options.getRequiredOptions().contains(key), "Option in group shouldn't be in required options list.");
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

        final String s = options.toString();
        assertNotNull(s, "null string returned");
        assertTrue(s.toLowerCase().contains("foo"), "foo option missing");
        assertTrue(s.toLowerCase().contains("bar"), "bar option missing");
    }
}
