package org.apache.commons.cli.help;

import static org.junit.jupiter.api.Assertions.assertEquals;
import java.util.stream.Stream;
import org.apache.commons.cli.DeprecatedAttributes;
import org.apache.commons.cli.Option;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

public class OptionFormatterTest_testComplexDeprecationFormat {

    /**
     * Each entry is a self-contained combination of DeprecatedAttributes fields paired with the
     * expected output of COMPLEX_DEPRECATED_FORMAT, so readers do not need to trace shared mutations.
     */
    public static Stream<Arguments> deprecatedAttributesData() {
        // No extra fields set
        DeprecatedAttributes.Builder noExtras = DeprecatedAttributes.builder();

        // "since" only
        DeprecatedAttributes.Builder sinceOnly = DeprecatedAttributes.builder();
        sinceOnly.setSince("now");

        // "since" + "for removal"
        DeprecatedAttributes.Builder sinceAndForRemoval = DeprecatedAttributes.builder();
        sinceAndForRemoval.setSince("now");
        sinceAndForRemoval.setForRemoval(true);

        // "for removal" only (no "since")
        DeprecatedAttributes.Builder forRemovalOnly = DeprecatedAttributes.builder();
        forRemovalOnly.setForRemoval(true);

        // description only
        DeprecatedAttributes.Builder descriptionOnly = DeprecatedAttributes.builder();
        descriptionOnly.setDescription("Use something else");

        // "for removal" + description
        DeprecatedAttributes.Builder forRemovalAndDesc = DeprecatedAttributes.builder();
        forRemovalAndDesc.setForRemoval(true);
        forRemovalAndDesc.setDescription("Use something else");

        // "since" + description
        DeprecatedAttributes.Builder sinceAndDesc = DeprecatedAttributes.builder();
        sinceAndDesc.setSince("then");
        sinceAndDesc.setDescription("Use something else");

        // All three fields: "since" + "for removal" + description
        DeprecatedAttributes.Builder allFields = DeprecatedAttributes.builder();
        allFields.setSince("then");
        allFields.setForRemoval(true);
        allFields.setDescription("Use something else");

        return Stream.of(
            Arguments.of(noExtras.get(),          "[Deprecated]"),
            Arguments.of(sinceOnly.get(),          "[Deprecated since now]"),
            Arguments.of(sinceAndForRemoval.get(), "[Deprecated for removal since now]"),
            Arguments.of(forRemovalOnly.get(),     "[Deprecated for removal]"),
            Arguments.of(descriptionOnly.get(),    "[Deprecated. Use something else]"),
            Arguments.of(forRemovalAndDesc.get(),  "[Deprecated for removal. Use something else]"),
            Arguments.of(sinceAndDesc.get(),       "[Deprecated since then. Use something else]"),
            Arguments.of(allFields.get(),          "[Deprecated for removal since then. Use something else]")
        );
    }

    @ParameterizedTest(name = "{index} {0}")
    @MethodSource("deprecatedAttributesData")
    void testComplexDeprecationFormat(final DeprecatedAttributes da, final String expected) {
        final Option.Builder builder = Option.builder("o").deprecated(da);
        final Option.Builder builderWithDesc = Option.builder("o").desc("The description").deprecated(da);
        assertEquals(expected, OptionFormatter.COMPLEX_DEPRECATED_FORMAT.apply(builder.get()));
        assertEquals(expected + " The description", OptionFormatter.COMPLEX_DEPRECATED_FORMAT.apply(builderWithDesc.get()));
    }
}
