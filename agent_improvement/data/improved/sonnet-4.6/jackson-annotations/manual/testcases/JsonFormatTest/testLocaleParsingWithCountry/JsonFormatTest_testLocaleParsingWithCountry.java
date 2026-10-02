package com.fasterxml.jackson.annotation;

import com.fasterxml.jackson.annotation.JsonFormat.Shape;
import org.junit.jupiter.api.Test;
import static com.fasterxml.jackson.annotation.JsonFormat.DEFAULT_RADIX;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Locale;

// [annotations#344]: Locale parsing with language, country, variant
public class JsonFormatTest_testLocaleParsingWithCountry extends AnnotationTestUtil {

    /**
     * Creates a JsonFormat.Value with only the locale string set; all other fields
     * use their neutral defaults. This keeps individual test cases focused on the
     * locale-parsing behaviour under test.
     */
    private JsonFormat.Value valueWithLocale(String localeStr) {
        return new JsonFormat.Value("", Shape.ANY, localeStr, "",
                JsonFormat.Features.empty(), null, DEFAULT_RADIX);
    }

    @Test
    void testLocaleParsingWithCountry() {
        // Simple language-only tag
        assertEquals(new Locale("en"),
                valueWithLocale("en").getLocale());

        // Language + country separated by underscore (e.g. "en_US")
        assertEquals(new Locale("en", "US"),
                valueWithLocale("en_US").getLocale());

        // Language + country separated by hyphen (e.g. "en-US") — both separators accepted
        assertEquals(new Locale("en", "US"),
                valueWithLocale("en-US").getLocale());

        // Language + country + variant (three-part tag)
        assertEquals(new Locale("en", "US", "POSIX"),
                valueWithLocale("en_US_POSIX").getLocale());

        // Non-English locale: German (Germany)
        assertEquals(new Locale("de", "DE"),
                valueWithLocale("de_DE").getLocale());
    }
}
