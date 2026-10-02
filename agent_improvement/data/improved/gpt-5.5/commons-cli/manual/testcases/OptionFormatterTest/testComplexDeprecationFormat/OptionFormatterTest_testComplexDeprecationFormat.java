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

public class OptionFormatterTest_testComplexDeprecationFormat {

    private static final String OPTION_DESCRIPTION = "The description";

    public static Stream<Arguments> deprecatedAttributesData() {
        final List<Arguments> cases = new ArrayList<>();
        final DeprecatedAttributes.Builder deprecatedAttributes = DeprecatedAttributes.builder();

        cases.add(Arguments.of(deprecatedAttributes.get(), "[Deprecated]"));
        deprecatedAttributes.setSince("now");
        cases.add(Arguments.of(deprecatedAttributes.get(), "[Deprecated since now]"));
        deprecatedAttributes.setForRemoval(true);
        cases.add(Arguments.of(deprecatedAttributes.get(), "[Deprecated for removal since now]"));
        deprecatedAttributes.setSince(null);
        cases.add(Arguments.of(deprecatedAttributes.get(), "[Deprecated for removal]"));
        deprecatedAttributes.setForRemoval(false);
        deprecatedAttributes.setDescription("Use something else");
        cases.add(Arguments.of(deprecatedAttributes.get(), "[Deprecated. Use something else]"));
        deprecatedAttributes.setForRemoval(true);
        cases.add(Arguments.of(deprecatedAttributes.get(), "[Deprecated for removal. Use something else]"));
        deprecatedAttributes.setForRemoval(false);
        deprecatedAttributes.setSince("then");
        cases.add(Arguments.of(deprecatedAttributes.get(), "[Deprecated since then. Use something else]"));
        deprecatedAttributes.setForRemoval(true);
        cases.add(Arguments.of(deprecatedAttributes.get(), "[Deprecated for removal since then. Use something else]"));

        return cases.stream();
    }

    @ParameterizedTest(name = "{index} {0}")
    @MethodSource("deprecatedAttributesData")
    void testComplexDeprecationFormat(final DeprecatedAttributes deprecatedAttributes, final String expectedPrefix) {
        assertComplexDeprecatedFormat(deprecatedAttributes, expectedPrefix);
        assertComplexDeprecatedFormatWithDescription(deprecatedAttributes, expectedPrefix + " " + OPTION_DESCRIPTION);
    }

    private void assertComplexDeprecatedFormat(final DeprecatedAttributes deprecatedAttributes, final String expected) {
        final Option.Builder builder = Option.builder("o").deprecated(deprecatedAttributes);

        assertEquals(expected, OptionFormatter.COMPLEX_DEPRECATED_FORMAT.apply(builder.get()));
    }

    private void assertComplexDeprecatedFormatWithDescription(final DeprecatedAttributes deprecatedAttributes, final String expected) {
        final Option.Builder builderWithDescription = Option.builder("o").desc(OPTION_DESCRIPTION).deprecated(deprecatedAttributes);

        assertEquals(expected, OptionFormatter.COMPLEX_DEPRECATED_FORMAT.apply(builderWithDescription.get()));
    }
}
