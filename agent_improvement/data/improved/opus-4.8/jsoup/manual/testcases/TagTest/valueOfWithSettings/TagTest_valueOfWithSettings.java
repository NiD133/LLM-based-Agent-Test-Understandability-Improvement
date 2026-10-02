package org.jsoup.parser;

import org.jsoup.MultiLocaleExtension.MultiLocaleTest;
import org.junit.jupiter.api.Test;

import java.util.Locale;

import static org.jsoup.parser.Parser.NamespaceHtml;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertSame;

/**
 * Tests for the case-sensitivity behaviour of {@code Tag.valueOf(...)} and
 * {@code TagSet.valueOf(...)}, driven by the supplied {@link ParseSettings}.
 */
public class TagTest_valueOfWithSettings {

    /**
     * With the case-insensitive {@code htmlDefault} settings, looking up the same tag name
     * in different cases resolves to the same logical tag.
     *
     * <p>{@code Tag.valueOf(...)} builds a fresh {@code TagSet} on each call, so results are
     * only {@code equal}. A shared {@code TagSet} instance instead returns the very same
     * ({@code ==}) cached tag.</p>
     */
    @MultiLocaleTest
    public void caseIsIgnoredWithHtmlDefaultSettings(Locale locale) {
        Locale.setDefault(locale);

        // Static lookups create a new TagSet each time, so equal but not identical.
        Tag scriptLowerStatic = Tag.valueOf("script", NamespaceHtml, ParseSettings.htmlDefault);
        Tag scriptUpperStatic = Tag.valueOf("SCRIPT", NamespaceHtml, ParseSettings.htmlDefault);
        assertEquals(scriptLowerStatic, scriptUpperStatic);

        // Lookups against one shared TagSet return the identical cached tag.
        TagSet htmlTags = TagSet.Html();
        Tag scriptLowerShared = htmlTags.valueOf("script", NamespaceHtml, ParseSettings.htmlDefault);
        Tag scriptUpperShared = htmlTags.valueOf("SCRIPT", NamespaceHtml, ParseSettings.htmlDefault);
        assertSame(scriptLowerShared, scriptUpperShared);
    }

    /**
     * {@code htmlDefault} normalises tag names to lower case, while {@code preserveCase} keeps
     * the original casing. Static {@code Tag.valueOf} calls each build a new TagSet, so equal
     * tags are never identical; a shared TagSet caches and reuses them.
     */
    @Test
    void valueOfWithSettings() {
        Tag imgDefaultLower = Tag.valueOf("img", ParseSettings.htmlDefault);
        Tag imgDefaultUpper = Tag.valueOf("IMG", ParseSettings.htmlDefault);
        Tag imgPreservedUpper = Tag.valueOf("IMG", ParseSettings.preserveCase);

        // Each static call creates a fresh TagSet, so even matching tags are distinct instances.
        assertNotSame(imgDefaultLower, imgDefaultUpper);
        assertNotSame(imgDefaultLower, imgPreservedUpper);

        // preserveCase keeps "IMG"; htmlDefault lower-cases "IMG" to "img".
        assertEquals("IMG", imgPreservedUpper.toString());
        assertEquals("img", imgDefaultLower.toString());

        // A shared TagSet caches tags: case-insensitive default settings yield the same instance...
        TagSet htmlTags = TagSet.Html();
        assertSame(
            htmlTags.valueOf("img", NamespaceHtml, ParseSettings.htmlDefault),
            htmlTags.valueOf("IMG", NamespaceHtml, ParseSettings.htmlDefault));

        // ...whereas the case-preserving overload treats "img" and "IMG" as different tags.
        assertNotSame(
            htmlTags.valueOf("img", NamespaceHtml),
            htmlTags.valueOf("IMG", NamespaceHtml));
    }
}
