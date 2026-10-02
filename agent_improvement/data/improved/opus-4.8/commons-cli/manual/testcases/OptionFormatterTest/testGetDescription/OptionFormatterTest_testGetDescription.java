package org.apache.commons.cli.help;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.apache.commons.cli.DeprecatedAttributes;
import org.apache.commons.cli.Option;
import org.junit.jupiter.api.Test;

/**
 * Tests {@link OptionFormatter#getDescription()}.
 *
 * <p>{@code getDescription()} returns the option's plain description unless the option is
 * deprecated, in which case the configured deprecated-format function decides how the
 * deprecation notice is woven into the description. This test pins down that behaviour for
 * three kinds of option against three different format functions.</p>
 */
public class OptionFormatterTest_testGetDescription {

    /** The description shared by every option under test. */
    private static final String DESCRIPTION = "The description";

    /** A plain, non-deprecated option. */
    private static final Option NORMAL_OPTION =
            Option.builder().option("o").longOpt("one").hasArg().desc(DESCRIPTION).get();

    /** A deprecated option carrying no extra deprecation attributes. */
    private static final Option DEPRECATED_OPTION =
            Option.builder().option("o").longOpt("one").hasArg().desc(DESCRIPTION).deprecated().get();

    /** A deprecated option carrying "for removal", a "since" value and a replacement note. */
    private static final Option DEPRECATED_OPTION_WITH_ATTRIBUTES =
            Option.builder().option("o").longOpt("one").hasArg().desc(DESCRIPTION)
                    .deprecated(DeprecatedAttributes.builder()
                            .setForRemoval(true)
                            .setSince("now")
                            .setDescription("Use something else")
                            .get())
                    .get();

    @Test
    void testGetDescription() {
        // Default formatter (NO_DEPRECATED_FORMAT): deprecation is ignored, so every option
        // reports only its plain description.
        assertEquals(DESCRIPTION, OptionFormatter.from(NORMAL_OPTION).getDescription(),
                "normal option failure");
        assertEquals(DESCRIPTION, OptionFormatter.from(DEPRECATED_OPTION).getDescription(),
                "deprecated option failure");
        assertEquals(DESCRIPTION, OptionFormatter.from(DEPRECATED_OPTION_WITH_ATTRIBUTES).getDescription(),
                "complex deprecated option failure");

        // SIMPLE_DEPRECATED_FORMAT: deprecated options get a flat "[Deprecated]" prefix,
        // regardless of any deprecation attributes; normal options are untouched.
        final OptionFormatter.Builder simpleFormat =
                OptionFormatter.builder().setDeprecatedFormatFunction(OptionFormatter.SIMPLE_DEPRECATED_FORMAT);
        assertEquals(DESCRIPTION, simpleFormat.build(NORMAL_OPTION).getDescription(),
                "normal option failure");
        assertEquals("[Deprecated] " + DESCRIPTION, simpleFormat.build(DEPRECATED_OPTION).getDescription(),
                "deprecated option failure");
        assertEquals("[Deprecated] " + DESCRIPTION, simpleFormat.build(DEPRECATED_OPTION_WITH_ATTRIBUTES).getDescription(),
                "complex deprecated option failure");

        // COMPLEX_DEPRECATED_FORMAT: deprecated options get a detailed prefix that expands the
        // deprecation attributes (for removal / since / replacement); normal options are untouched.
        final OptionFormatter.Builder complexFormat =
                OptionFormatter.builder().setDeprecatedFormatFunction(OptionFormatter.COMPLEX_DEPRECATED_FORMAT);
        assertEquals(DESCRIPTION, complexFormat.build(NORMAL_OPTION).getDescription(),
                "normal option failure");
        assertEquals("[Deprecated] " + DESCRIPTION, complexFormat.build(DEPRECATED_OPTION).getDescription(),
                "deprecated option failure");
        assertEquals("[Deprecated for removal since now. Use something else] " + DESCRIPTION,
                complexFormat.build(DEPRECATED_OPTION_WITH_ATTRIBUTES).getDescription(),
                "complex deprecated option failure");
    }
}
