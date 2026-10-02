package org.jsoup.parser;

import org.jsoup.MultiLocaleExtension.MultiLocaleTest;
import org.junit.jupiter.api.Test;
import java.util.Locale;
import static org.jsoup.parser.Parser.NamespaceHtml;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests for Tag.isFormSubmittable() and related locale-insensitive tag lookup.
 */
public class TagTest_formSubmittable {

    /**
     * Verifies that tag lookup is case-insensitive under any locale, including Turkish
     * where "SCRIPT".toLowerCase() would otherwise produce "scrıpt" (dotless-i).
     */
    @MultiLocaleTest
    public void canBeInsensitive(Locale locale) {
        Locale.setDefault(locale);

        // Static valueOf should treat "script" and "SCRIPT" as the same tag
        Tag script1 = Tag.valueOf("script", NamespaceHtml, ParseSettings.htmlDefault);
        Tag script2 = Tag.valueOf("SCRIPT", NamespaceHtml, ParseSettings.htmlDefault);
        assertEquals(script1, script2);

        // TagSet lookup should return the identical (same-reference) canonical instance
        TagSet htmlTags = TagSet.Html();
        Tag script3 = htmlTags.valueOf("script", NamespaceHtml, ParseSettings.htmlDefault);
        Tag script4 = htmlTags.valueOf("SCRIPT", NamespaceHtml, ParseSettings.htmlDefault);
        assertSame(script3, script4);
    }

    /**
     * Verifies that isFormSubmittable() returns the correct value for known HTML tags
     * and that the method is purely read-only — it must not mutate the tag's internal
     * options bitmask (regression guard for https://github.com/jhy/jsoup/issues/2323).
     */
    @Test
    void formSubmittable() {
        Tag img   = Tag.valueOf("img");
        Tag input = Tag.valueOf("input");

        // Snapshot the options bitmask before any query so we can detect accidental mutation
        int imgOptionsSnapshot   = img.options;
        int inputOptionsSnapshot = input.options;

        // <img> is a void/replaced element — its value is never submitted with a form
        assertFalse(img.isFormSubmittable(), "img should not be form-submittable");
        // <input> carries user data and must always be form-submittable
        assertTrue(input.isFormSubmittable(), "input should be form-submittable");

        // isFormSubmittable() must be a pure read — options must be unchanged after the calls
        assertEquals(imgOptionsSnapshot,   img.options,   "img.options must not be mutated by isFormSubmittable()");
        assertEquals(inputOptionsSnapshot, input.options, "input.options must not be mutated by isFormSubmittable()");
    }
}
