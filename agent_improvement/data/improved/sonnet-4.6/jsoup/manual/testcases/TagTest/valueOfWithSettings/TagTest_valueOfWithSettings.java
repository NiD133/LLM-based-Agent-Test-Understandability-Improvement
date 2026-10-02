package org.jsoup.parser;

import org.jsoup.MultiLocaleExtension.MultiLocaleTest;
import org.junit.jupiter.api.Test;
import java.util.Locale;
import static org.jsoup.parser.Parser.NamespaceHtml;
import static org.junit.jupiter.api.Assertions.*;

public class TagTest_valueOfWithSettings {

    /**
     * Verifies that tag lookup is case-insensitive under htmlDefault settings, even when the
     * JVM default Locale changes (Turkish locale, for example, uppercases 'i' differently).
     * When looking up through a shared TagSet, identical tags should be the exact same instance.
     */
    @MultiLocaleTest
    public void canBeInsensitive(Locale locale) {
        Locale.setDefault(locale);

        // Tag.valueOf creates a fresh TagSet each call, so the two tags are equal but not identical
        Tag scriptLower = Tag.valueOf("script", NamespaceHtml, ParseSettings.htmlDefault);
        Tag scriptUpper = Tag.valueOf("SCRIPT", NamespaceHtml, ParseSettings.htmlDefault);
        assertEquals(scriptLower, scriptUpper,
            "Tags looked up with different cases should be equal under htmlDefault settings");

        // A shared TagSet caches tags, so the same normalized name returns the identical instance
        TagSet sharedTagSet = TagSet.Html();
        Tag scriptFromSharedLower = sharedTagSet.valueOf("script", NamespaceHtml, ParseSettings.htmlDefault);
        Tag scriptFromSharedUpper = sharedTagSet.valueOf("SCRIPT", NamespaceHtml, ParseSettings.htmlDefault);
        assertSame(scriptFromSharedLower, scriptFromSharedUpper,
            "A shared TagSet should return the same Tag instance for case-equivalent names under htmlDefault");
    }

    /**
     * Verifies the interaction between ParseSettings and Tag identity:
     * - Tag.valueOf creates a new TagSet on every call, so identical lookups yield distinct instances.
     * - ParseSettings.preserveCase retains the original casing in the tag name.
     * - A shared TagSet with htmlDefault normalises case, returning the same cached instance.
     * - A shared TagSet without explicit ParseSettings does NOT normalise case, yielding distinct instances.
     */
    @Test
    void valueOfWithSettings() {
        // Each Tag.valueOf call builds a fresh TagSet, so no instance sharing occurs here
        Tag imgLower        = Tag.valueOf("img",  ParseSettings.htmlDefault);
        Tag imgUpperDefault = Tag.valueOf("IMG",  ParseSettings.htmlDefault);
        Tag imgUpperPreserve = Tag.valueOf("IMG", ParseSettings.preserveCase);

        assertNotSame(imgLower, imgUpperDefault,
            "Tag.valueOf creates a new TagSet per call, so equal tags are never the same instance");
        assertNotSame(imgLower, imgUpperPreserve,
            "Tags with different ParseSettings should also be distinct instances");

        // preserveCase keeps the supplied casing; htmlDefault normalises to lower-case
        assertEquals("IMG", imgUpperPreserve.toString(),
            "preserveCase should keep the tag name as supplied ('IMG')");
        assertEquals("img", imgLower.toString(),
            "htmlDefault should normalise the tag name to lower-case ('img')");

        // A single shared TagSet normalises lookups with htmlDefault: same instance returned
        TagSet sharedTagSet = TagSet.Html();
        Tag imgFromSharedLower = sharedTagSet.valueOf("img", NamespaceHtml, ParseSettings.htmlDefault);
        Tag imgFromSharedUpper = sharedTagSet.valueOf("IMG", NamespaceHtml, ParseSettings.htmlDefault);
        assertSame(imgFromSharedLower, imgFromSharedUpper,
            "A shared TagSet with htmlDefault should return the same cached Tag for 'img' and 'IMG'");

        // Without ParseSettings the TagSet does not normalise case, so different names → different instances
        Tag imgSharedNoSettingsLower = sharedTagSet.valueOf("img", NamespaceHtml);
        Tag imgSharedNoSettingsUpper = sharedTagSet.valueOf("IMG", NamespaceHtml);
        assertNotSame(imgSharedNoSettingsLower, imgSharedNoSettingsUpper,
            "Without ParseSettings, the TagSet treats 'img' and 'IMG' as distinct tags");
    }
}
