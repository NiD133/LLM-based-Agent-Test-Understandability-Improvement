package org.apache.commons.cli.help;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import org.apache.commons.cli.DeprecatedAttributes;
import org.apache.commons.cli.Option;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

/**
 * Tests {@link OptionFormatter#COMPLEX_DEPRECATED_FORMAT}, which renders a deprecation
 * notice that reflects every combination of the {@link DeprecatedAttributes} fields
 * (for-removal flag, "since" version, and replacement description).
 */
public class OptionFormatterTest_testComplexDeprecationFormat {

    /**
     * Supplies a deprecation-attributes / expected-text pair for each combination under test.
     * <p>
     * A single builder is reused and progressively reconfigured so that each row only highlights
     * the field that changed relative to the previous row.
     */
    public static Stream<Arguments> deprecatedAttributesData() {
        final List<Arguments> cases = new ArrayList<>();
        final DeprecatedAttributes.Builder attributes = DeprecatedAttributes.builder();

        // No attributes set.
        cases.add(Arguments.of(attributes.get(), "[Deprecated]"));

        // Only a "since" version.
        attributes.setSince("now");
        cases.add(Arguments.of(attributes.get(), "[Deprecated since now]"));

        // "since" version plus for-removal flag.
        attributes.setForRemoval(true);
        cases.add(Arguments.of(attributes.get(), "[Deprecated for removal since now]"));

        // Only the for-removal flag.
        attributes.setSince(null);
        cases.add(Arguments.of(attributes.get(), "[Deprecated for removal]"));

        // Only a replacement description.
        attributes.setForRemoval(false);
        attributes.setDescription("Use something else");
        cases.add(Arguments.of(attributes.get(), "[Deprecated. Use something else]"));

        // For-removal flag plus replacement description.
        attributes.setForRemoval(true);
        cases.add(Arguments.of(attributes.get(), "[Deprecated for removal. Use something else]"));

        // "since" version plus replacement description.
        attributes.setForRemoval(false);
        attributes.setSince("then");
        cases.add(Arguments.of(attributes.get(), "[Deprecated since then. Use something else]"));

        // All three: for-removal flag, "since" version, and replacement description.
        attributes.setForRemoval(true);
        cases.add(Arguments.of(attributes.get(), "[Deprecated for removal since then. Use something else]"));

        return cases.stream();
    }

    @ParameterizedTest(name = "{index} {0}")
    @MethodSource("deprecatedAttributesData")
    void testComplexDeprecationFormat(final DeprecatedAttributes deprecation, final String expectedNotice) {
        final Option withoutDescription = Option.builder("o").deprecated(deprecation).get();
        final Option withDescription = Option.builder("o").desc("The description").deprecated(deprecation).get();

        // Without an option description, the output is exactly the deprecation notice.
        assertEquals(expectedNotice, OptionFormatter.COMPLEX_DEPRECATED_FORMAT.apply(withoutDescription));

        // With an option description, it is appended after the deprecation notice.
        assertEquals(expectedNotice + " The description",
                OptionFormatter.COMPLEX_DEPRECATED_FORMAT.apply(withDescription));
    }
}
