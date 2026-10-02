package org.jsoup.parser;

import org.jsoup.MultiLocaleExtension.MultiLocaleTest;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Locale;

import static org.jsoup.parser.Parser.NamespaceHtml;
import static org.jsoup.parser.Parser.NamespaceSvg;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TagTest_stableHashcode {

    /**
     * Tag lookup ignores case: resolving a tag by a differently-cased name yields an
     * equal (and, when looked up through the same TagSet, the identical) Tag instance.
     */
    @MultiLocaleTest
    public void canBeInsensitive(Locale locale) {
        // Run under the supplied locale to confirm case-folding is locale independent.
        Locale.setDefault(locale);

        // Tag.valueOf builds against a fresh HTML TagSet, so lower- and upper-case
        // lookups produce Tags that are equal but not necessarily the same instance.
        Tag scriptLower = Tag.valueOf("script", NamespaceHtml, ParseSettings.htmlDefault);
        Tag scriptUpper = Tag.valueOf("SCRIPT", NamespaceHtml, ParseSettings.htmlDefault);
        assertEquals(scriptLower, scriptUpper);

        // Looking up through one shared TagSet caches the Tag, so both casings return
        // the exact same instance.
        TagSet htmlTags = TagSet.Html();
        Tag sharedScriptLower = htmlTags.valueOf("script", NamespaceHtml, ParseSettings.htmlDefault);
        Tag sharedScriptUpper = htmlTags.valueOf("SCRIPT", NamespaceHtml, ParseSettings.htmlDefault);
        assertSame(sharedScriptLower, sharedScriptUpper);
    }

    /**
     * A Tag's hashCode is derived only from its name and namespace, so it stays stable
     * even after the Tag's options are mutated. This keeps a Tag usable as a HashSet key.
     */
    @Test
    void stableHashcode() {
        // Three distinct Tags: same name but different case, plus a same-name tag in
        // another namespace. Each yields its own fixed hashCode.
        Tag imgLower = Tag.valueOf("img");
        Tag imgUpper = Tag.valueOf("IMG");
        Tag imgSvg = Tag.valueOf("img", NamespaceSvg, ParseSettings.htmlDefault);

        int imgLowerHash = -2074969810;
        int imgUpperHash = -2075954866;
        int imgSvgHash = -292873947;
        assertEquals(imgLowerHash, imgLower.hashCode());
        assertEquals(imgUpperHash, imgUpper.hashCode());
        assertEquals(imgSvgHash, imgSvg.hashCode());

        // Store all three as keys.
        HashSet<Tag> tags = new HashSet<>();
        tags.add(imgLower);
        tags.add(imgUpper);
        tags.add(imgSvg);

        // Mutating an option must not change the hashCode, or the key would be lost.
        imgSvg.set(Tag.Block);
        assertEquals(imgSvgHash, imgSvg.hashCode());

        // All three are still found after the mutation.
        assertTrue(tags.contains(imgLower));
        assertTrue(tags.contains(imgUpper));
        assertTrue(tags.contains(imgSvg));
    }
}
