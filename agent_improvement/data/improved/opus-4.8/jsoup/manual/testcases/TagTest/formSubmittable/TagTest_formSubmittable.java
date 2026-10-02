package org.jsoup.parser;

import org.jsoup.MultiLocaleExtension.MultiLocaleTest;
import org.junit.jupiter.api.Test;

import java.util.Locale;

import static org.jsoup.parser.Parser.NamespaceHtml;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests for {@link Tag} covering case-insensitive tag resolution and the
 * {@code FormSubmittable} option exposed via {@link Tag#isFormSubmittable()}.
 */
public class TagTest_formSubmittable {

    /**
     * Resolving a tag by name is case-insensitive, regardless of the JVM's default locale.
     * "script" and "SCRIPT" must resolve to equal tags, and within a single {@link TagSet}
     * they resolve to the very same cached instance.
     */
    @MultiLocaleTest
    public void canBeInsensitive(Locale locale) {
        Locale.setDefault(locale);

        // Tag.valueOf caches per the shared HTML TagSet, so differing case yields equal tags.
        Tag scriptLowerCase = Tag.valueOf("script", NamespaceHtml, ParseSettings.htmlDefault);
        Tag scriptUpperCase = Tag.valueOf("SCRIPT", NamespaceHtml, ParseSettings.htmlDefault);
        assertEquals(scriptLowerCase, scriptUpperCase);

        // Resolving against the same TagSet returns the identical instance for either case.
        TagSet htmlTags = TagSet.Html();
        Tag scriptFromSet = htmlTags.valueOf("script", NamespaceHtml, ParseSettings.htmlDefault);
        Tag scriptFromSetUpper = htmlTags.valueOf("SCRIPT", NamespaceHtml, ParseSettings.htmlDefault);
        assertSame(scriptFromSet, scriptFromSetUpper);
    }

    /**
     * The {@code FormSubmittable} option is reported only for tags that participate in form
     * submission: {@code <input>} is form-submittable while {@code <img>} is not. Reading the
     * option through {@link Tag#isFormSubmittable()} must not mutate the tag's options.
     *
     * @see <a href="https://github.com/jhy/jsoup/issues/2323">jsoup issue 2323</a>
     */
    @Test
    void formSubmittable() {
        Tag img = Tag.valueOf("img");
        Tag input = Tag.valueOf("input");

        // Capture the option flags before querying, to confirm the query is side-effect free.
        int imgOptionsBefore = img.options;
        int inputOptionsBefore = input.options;

        assertFalse(img.isFormSubmittable(), "img must not be form-submittable");
        assertTrue(input.isFormSubmittable(), "input must be form-submittable");

        // isFormSubmittable() is a pure read; the option flags are unchanged.
        assertEquals(imgOptionsBefore, img.options);
        assertEquals(inputOptionsBefore, input.options);
    }
}
