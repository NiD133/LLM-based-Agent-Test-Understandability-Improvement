package org.jsoup.parser;

import org.jsoup.Jsoup;
import org.jsoup.MultiLocaleExtension.MultiLocaleTest;
import org.jsoup.nodes.Document;
import org.junit.jupiter.api.Test;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Locale;
import static org.jsoup.parser.Parser.NamespaceHtml;
import static org.jsoup.parser.Parser.NamespaceSvg;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests how {@link Tag} instances compare for equality (via {@code equals})
 * and identity (via {@code ==}, asserted with {@code assertSame}).
 *
 * Two key behaviours are covered:
 *  - Tag lookup is case-insensitive, so "script" and "SCRIPT" resolve to equal tags.
 *  - {@link Tag#valueOf} clones the shared HTML {@link TagSet}, so equal tags
 *    obtained through it are not necessarily the same instance, whereas tags
 *    obtained from a single TagSet instance are.
 */
public class TagTest_equality {

    /**
     * Tag names are matched case-insensitively, regardless of the active locale.
     * Lower-case and upper-case spellings of the same tag are therefore equal,
     * and when looked up through the same TagSet they are the very same instance.
     */
    @MultiLocaleTest
    public void canBeInsensitive(Locale locale) {
        Locale.setDefault(locale);

        // Looking up the same tag in different cases via the static helper yields equal tags.
        Tag scriptLower = Tag.valueOf("script", NamespaceHtml, ParseSettings.htmlDefault);
        Tag scriptUpper = Tag.valueOf("SCRIPT", NamespaceHtml, ParseSettings.htmlDefault);
        assertEquals(scriptLower, scriptUpper);

        // Looking them up through one shared TagSet returns the identical cached instance.
        TagSet htmlTags = TagSet.Html();
        Tag scriptFromSetLower = htmlTags.valueOf("script", NamespaceHtml, ParseSettings.htmlDefault);
        Tag scriptFromSetUpper = htmlTags.valueOf("SCRIPT", NamespaceHtml, ParseSettings.htmlDefault);
        assertSame(scriptFromSetLower, scriptFromSetUpper);
    }

    /**
     * Verifies the distinction between equality and identity for tags:
     *  - {@code Tag.valueOf} returns equal-but-distinct instances, because it
     *    clones {@code TagSet.Html()} each time so mutations stay isolated.
     *  - Tags drawn from a single TagSet instance are the same cached object.
     */
    @Test
    public void equality() {
        // Tag.valueOf produces equal tags, but each call clones TagSet.Html(),
        // so the instances are distinct (changes to one don't clobber the others).
        Tag pViaValueOf1 = Tag.valueOf("p");
        Tag pViaValueOf2 = Tag.valueOf("p");
        assertEquals(pViaValueOf1, pViaValueOf2);
        assertNotSame(pViaValueOf1, pViaValueOf2);

        // Each TagSet.Html() call likewise returns an equal but distinct TagSet.
        TagSet htmlSetA = TagSet.Html();
        TagSet htmlSetB = TagSet.Html();
        assertEquals(htmlSetA, htmlSetB);
        assertNotSame(htmlSetA, htmlSetB);

        // Within one TagSet instance, repeated lookups return the same cached tag.
        Tag pFromSetA1 = htmlSetA.valueOf("p", NamespaceHtml);
        Tag pFromSetA2 = htmlSetA.valueOf("p", NamespaceHtml);
        Tag pFromSetB1 = htmlSetB.valueOf("p", NamespaceHtml);
        Tag pFromSetB2 = htmlSetB.valueOf("p", NamespaceHtml);

        // All "p" tags are equal regardless of which set or helper produced them.
        assertEquals(pViaValueOf1, pFromSetA1);
        assertEquals(pFromSetA1, pFromSetA2);
        assertEquals(pFromSetA2, pFromSetB1);

        // Same instance when from the same set; different instance across sets.
        assertSame(pFromSetA1, pFromSetA2);
        assertSame(pFromSetB1, pFromSetB2);
        assertNotSame(pFromSetA1, pFromSetB1);
    }
}
