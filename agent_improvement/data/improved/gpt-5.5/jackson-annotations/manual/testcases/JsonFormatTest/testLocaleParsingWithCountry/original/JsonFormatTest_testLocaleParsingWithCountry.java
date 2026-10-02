package com.fasterxml.jackson.annotation;

import com.fasterxml.jackson.annotation.JsonFormat.Feature;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;
import org.junit.jupiter.api.Test;
import static com.fasterxml.jackson.annotation.JsonFormat.DEFAULT_RADIX;
import static org.junit.jupiter.api.Assertions.*;

public class JsonFormatTest_testLocaleParsingWithCountry extends AnnotationTestUtil {

    private final JsonFormat.Value EMPTY = JsonFormat.Value.empty();

    // [annotations#344]: Locale parsing with language, country, variant
    @Test
    void testLocaleParsingWithCountry() {
        // Simple language-only
        JsonFormat.Value v = new JsonFormat.Value("", Shape.ANY, "en", "", JsonFormat.Features.empty(), null, DEFAULT_RADIX);
        assertEquals(new java.util.Locale("en"), v.getLocale());
        // Language + country with underscore
        v = new JsonFormat.Value("", Shape.ANY, "en_US", "", JsonFormat.Features.empty(), null, DEFAULT_RADIX);
        assertEquals(new java.util.Locale("en", "US"), v.getLocale());
        // Language + country with hyphen
        v = new JsonFormat.Value("", Shape.ANY, "en-US", "", JsonFormat.Features.empty(), null, DEFAULT_RADIX);
        assertEquals(new java.util.Locale("en", "US"), v.getLocale());
        // Language + country + variant
        v = new JsonFormat.Value("", Shape.ANY, "en_US_POSIX", "", JsonFormat.Features.empty(), null, DEFAULT_RADIX);
        assertEquals(new java.util.Locale("en", "US", "POSIX"), v.getLocale());
        // German locale
        v = new JsonFormat.Value("", Shape.ANY, "de_DE", "", JsonFormat.Features.empty(), null, DEFAULT_RADIX);
        assertEquals(new java.util.Locale("de", "DE"), v.getLocale());
    }
}
