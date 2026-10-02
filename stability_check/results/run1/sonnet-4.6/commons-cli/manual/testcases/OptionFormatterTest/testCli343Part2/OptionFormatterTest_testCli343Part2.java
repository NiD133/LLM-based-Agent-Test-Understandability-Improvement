package org.apache.commons.cli.help;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;
import org.apache.commons.cli.DeprecatedAttributes;
import org.apache.commons.cli.Option;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.provider.Arguments;

public class OptionFormatterTest_testCli343Part2 {

    /**
     * Provides test data covering all combinations of DeprecatedAttributes fields
     * (forRemoval, since, description) and the expected formatted string each produces.
     */
    public static Stream<Arguments> deprecatedAttributesData() {
        final List<Arguments> testCases = new ArrayList<>();
        final DeprecatedAttributes.Builder builder = DeprecatedAttributes.builder();

        // No extra fields set — plain "[Deprecated]" label
        testCases.add(Arguments.of(builder.get(), "[Deprecated]"));

        // "since" set — label includes the version/date
        builder.setSince("now");
        testCases.add(Arguments.of(builder.get(), "[Deprecated since now]"));

        // "forRemoval" + "since" — label says "for removal" and includes version
        builder.setForRemoval(true);
        testCases.add(Arguments.of(builder.get(), "[Deprecated for removal since now]"));

        // "forRemoval" only (since cleared) — no version shown
        builder.setSince(null);
        testCases.add(Arguments.of(builder.get(), "[Deprecated for removal]"));

        // Description only (forRemoval cleared) — description appended after period
        builder.setForRemoval(false);
        builder.setDescription("Use something else");
        testCases.add(Arguments.of(builder.get(), "[Deprecated. Use something else]"));

        // "forRemoval" + description
        builder.setForRemoval(true);
        testCases.add(Arguments.of(builder.get(), "[Deprecated for removal. Use something else]"));

        // "since" + description (forRemoval cleared)
        builder.setForRemoval(false);
        builder.setSince("then");
        testCases.add(Arguments.of(builder.get(), "[Deprecated since then. Use something else]"));

        // All fields set: forRemoval + since + description
        builder.setForRemoval(true);
        testCases.add(Arguments.of(builder.get(), "[Deprecated for removal since then. Use something else]"));

        return testCases.stream();
    }

    /**
     * Asserts that two OptionFormatter instances produce identical output for every
     * formatting method, confirming they are configured equivalently.
     */
    private void assertEquivalent(final OptionFormatter formatter, final OptionFormatter formatter2) {
        assertEquals(formatter.toSyntaxOption(), formatter2.toSyntaxOption());
        assertEquals(formatter.toSyntaxOption(true), formatter2.toSyntaxOption(true));
        assertEquals(formatter.toSyntaxOption(false), formatter2.toSyntaxOption(false));
        assertEquals(formatter.getOpt(), formatter2.getOpt());
        assertEquals(formatter.getLongOpt(), formatter2.getLongOpt());
        assertEquals(formatter.getBothOpt(), formatter2.getBothOpt());
        assertEquals(formatter.getDescription(), formatter2.getDescription());
        assertEquals(formatter.getArgName(), formatter2.getArgName());
        assertEquals(formatter.toOptional("foo"), formatter2.toOptional("foo"));
    }

    /**
     * Verifies CLI-343: building an Option that has only a description (no opt or
     * longOpt) must throw IllegalStateException, because an Option requires at least
     * one of those two identifiers.
     */
    @Test
    void testCli343Part2() {
        assertThrows(IllegalStateException.class, () -> Option.builder().desc("description").build());
        assertThrows(IllegalStateException.class, () -> Option.builder().desc("description").get());
    }
}
