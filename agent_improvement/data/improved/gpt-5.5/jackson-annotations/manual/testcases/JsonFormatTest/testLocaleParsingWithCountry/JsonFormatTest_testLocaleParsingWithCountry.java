package com.fasterxml.jackson.annotation;

import static com.fasterxml.jackson.annotation.JsonFormat.DEFAULT_RADIX;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Locale;

import org.junit.jupiter.api.Test;

import com.fasterxml.jackson.annotation.JsonFormat.Shape;

public class JsonFormatTest_testLocaleParsingWithCountry extends AnnotationTestUtil {

    // [annotations#344]: Locale parsing with language, country, variant
    @Test
    void testLocaleParsingWithCountry() {
        assertLocaleParsedAs("en", new Locale("en"));
        assertLocaleParsedAs("en_US", new Locale("en", "US"));
        assertLocaleParsedAs("en-US", new Locale("en", "US"));
        assertLocaleParsedAs("en_US_POSIX", new Locale("en", "US", "POSIX"));
        assertLocaleParsedAs("de_DE", new Locale("de", "DE"));
    }

    private void assertLocaleParsedAs(String localeText, Locale expectedLocale) {
        JsonFormat.Value value = new JsonFormat.Value("", Shape.ANY, localeText, "",
                JsonFormat.Features.empty(), null, DEFAULT_RADIX);

        assertEquals(expectedLocale, value.getLocale());
    }
}
