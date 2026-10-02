package com.fasterxml.jackson.annotation;

import com.fasterxml.jackson.annotation.JsonFormat.Shape;
import org.junit.jupiter.api.Test;

import java.util.Locale;

import static com.fasterxml.jackson.annotation.JsonFormat.DEFAULT_RADIX;
import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Verifies that {@link JsonFormat.Value} parses a locale string into a
 * {@link Locale}, supporting language-only, language+country (with either
 * an underscore or a hyphen separator) and language+country+variant forms.
 *
 * See [annotations#344].
 */
public class JsonFormatTest_testLocaleParsingWithCountry extends AnnotationTestUtil {

    /**
     * Builds a {@link JsonFormat.Value} whose only meaningful input is the
     * locale string, then returns the {@link Locale} it parsed. All other
     * constructor arguments are fixed defaults so the test reads as a plain
     * "locale string in, Locale out" mapping.
     */
    private Locale parseLocale(String localeStr) {
        JsonFormat.Value value = new JsonFormat.Value(
                "", Shape.ANY, localeStr, "", JsonFormat.Features.empty(), null, DEFAULT_RADIX);
        return value.getLocale();
    }

    @Test
    void testLocaleParsingWithCountry() {
        // Language only
        assertEquals(new Locale("en"), parseLocale("en"));

        // Language + country, underscore separator
        assertEquals(new Locale("en", "US"), parseLocale("en_US"));

        // Language + country, hyphen separator (should behave like underscore)
        assertEquals(new Locale("en", "US"), parseLocale("en-US"));

        // Language + country + variant
        assertEquals(new Locale("en", "US", "POSIX"), parseLocale("en_US_POSIX"));

        // A second language to confirm parsing is not hard-coded to English
        assertEquals(new Locale("de", "DE"), parseLocale("de_DE"));
    }
}
