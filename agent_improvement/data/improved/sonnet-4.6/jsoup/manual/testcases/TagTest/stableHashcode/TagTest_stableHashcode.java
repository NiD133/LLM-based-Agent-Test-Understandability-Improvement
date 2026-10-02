package org.jsoup.parser;

import org.jsoup.MultiLocaleExtension.MultiLocaleTest;
import org.junit.jupiter.api.Test;
import java.util.HashSet;
import java.util.Locale;
import static org.jsoup.parser.Parser.NamespaceHtml;
import static org.jsoup.parser.Parser.NamespaceSvg;
import static org.junit.jupiter.api.Assertions.*;

public class TagTest_stableHashcode {

    @MultiLocaleTest
    public void canBeInsensitive(Locale locale) {
        Locale.setDefault(locale);
        Tag script1 = Tag.valueOf("script", NamespaceHtml, ParseSettings.htmlDefault);
        Tag script2 = Tag.valueOf("SCRIPT", NamespaceHtml, ParseSettings.htmlDefault);
        assertEquals(script1, script2);
        TagSet htmlTags = TagSet.Html();
        Tag script3 = htmlTags.valueOf("script", NamespaceHtml, ParseSettings.htmlDefault);
        Tag script4 = htmlTags.valueOf("SCRIPT", NamespaceHtml, ParseSettings.htmlDefault);
        assertSame(script3, script4);
    }

    /**
     * Verifies that Tag.hashCode() is based solely on (tagName, namespace), not on options,
     * so tags remain findable in a HashSet even after mutation via set().
     */
    @Test
    void stableHashcode() {
        // Three distinct tags: lowercase HTML img, uppercase HTML img, and SVG img.
        // Each has a unique (tagName, namespace) pair and therefore a unique hash.
        Tag imgHtml      = Tag.valueOf("img");
        Tag imgHtmlUpper = Tag.valueOf("IMG");
        Tag imgSvg       = Tag.valueOf("img", NamespaceSvg, ParseSettings.htmlDefault);

        // Hash values are deterministic and depend only on tagName and namespace
        assertEquals(-2074969810, imgHtml.hashCode());
        assertEquals(-2075954866, imgHtmlUpper.hashCode());
        assertEquals(-292873947,  imgSvg.hashCode());

        // Use the tags as HashSet keys to exercise the hash-based collection
        HashSet<Tag> tags = new HashSet<>();
        tags.add(imgHtml);
        tags.add(imgHtmlUpper);
        tags.add(imgSvg);

        // Mutating a tag's options must not alter its hashCode; otherwise the tag
        // would be lost in any hash-based collection it was added to before the mutation
        imgSvg.set(Tag.Block);
        assertEquals(-292873947, imgSvg.hashCode()); // hash is unchanged after setting Block

        // All three tags are still retrievable from the set after imgSvg was mutated
        assertTrue(tags.contains(imgHtml));
        assertTrue(tags.contains(imgHtmlUpper));
        assertTrue(tags.contains(imgSvg));
    }
}
