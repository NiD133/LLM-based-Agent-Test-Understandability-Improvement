package org.jsoup.parser;

import org.jsoup.MultiLocaleExtension.MultiLocaleTest;
import org.junit.jupiter.api.Test;
import java.util.Locale;
import static org.jsoup.parser.Parser.NamespaceHtml;
import static org.junit.jupiter.api.Assertions.*;

public class TagTest_equality {

    /**
     * Verifies that tag lookup is case-insensitive: "script" and "SCRIPT" resolve to equal tags.
     * When resolved from a shared TagSet, they must be the identical object (same instance).
     */
    @MultiLocaleTest
    public void canBeInsensitive(Locale locale) {
        Locale.setDefault(locale);

        // Tags created via the static factory use a fresh TagSet clone each time,
        // so they are equal by value but not the same instance.
        Tag scriptLower = Tag.valueOf("script", NamespaceHtml, ParseSettings.htmlDefault);
        Tag scriptUpper = Tag.valueOf("SCRIPT", NamespaceHtml, ParseSettings.htmlDefault);
        assertEquals(scriptLower, scriptUpper);

        // Tags resolved from the same shared TagSet are cached: same lookup → same object.
        TagSet htmlTags = TagSet.Html();
        Tag scriptLowerFromSet = htmlTags.valueOf("script", NamespaceHtml, ParseSettings.htmlDefault);
        Tag scriptUpperFromSet = htmlTags.valueOf("SCRIPT", NamespaceHtml, ParseSettings.htmlDefault);
        assertSame(scriptLowerFromSet, scriptUpperFromSet);
    }

    /**
     * Verifies equality and identity semantics for Tags across different TagSet instances.
     *
     * Key rules exercised:
     *  - Two TagSet.Html() calls produce equal but distinct sets (defensive copy pattern).
     *  - Tags from the same TagSet instance are cached: identical lookups return the same object.
     *  - Tags from different TagSet instances are equal by value but not the same object.
     *  - The static Tag.valueOf shortcut also uses a fresh clone, so it is equal but not same.
     */
    @Test
    public void equality() {
        // Static Tag.valueOf uses a fresh TagSet.Html clone internally, so two calls for "p"
        // yield equal tags but distinct objects.
        Tag pFromStaticFactory1 = Tag.valueOf("p");
        Tag pFromStaticFactory2 = Tag.valueOf("p");
        assertEquals(pFromStaticFactory1, pFromStaticFactory2);
        assertNotSame(pFromStaticFactory1, pFromStaticFactory2);

        // TagSet.Html() produces a defensive copy each time: equal sets, not the same object.
        TagSet htmlSet1 = TagSet.Html();
        TagSet htmlSet2 = TagSet.Html();
        assertEquals(htmlSet1, htmlSet2);
        assertNotSame(htmlSet1, htmlSet2);

        // Repeated lookups within the same TagSet return the cached instance (assertSame).
        Tag pFromSet1_callA = htmlSet1.valueOf("p", NamespaceHtml);
        Tag pFromSet1_callB = htmlSet1.valueOf("p", NamespaceHtml);
        Tag pFromSet2_callA = htmlSet2.valueOf("p", NamespaceHtml);
        Tag pFromSet2_callB = htmlSet2.valueOf("p", NamespaceHtml);

        // Cross-set equality: same tag name/namespace/options → equal regardless of origin.
        assertEquals(pFromStaticFactory1, pFromSet1_callA);
        assertEquals(pFromSet1_callA, pFromSet1_callB);
        assertEquals(pFromSet1_callB, pFromSet2_callA);

        // Within-set identity: same TagSet caches the Tag, so repeated lookups are identical.
        assertSame(pFromSet1_callA, pFromSet1_callB);
        assertSame(pFromSet2_callA, pFromSet2_callB);

        // Cross-set identity: different TagSet instances hold separate caches, so "p" from
        // htmlSet1 and "p" from htmlSet2 are equal by value but not the same object.
        assertNotSame(pFromSet1_callA, pFromSet2_callA);
    }
}
