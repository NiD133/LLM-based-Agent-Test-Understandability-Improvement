package org.jsoup.parser;

import org.jsoup.MultiLocaleExtension.MultiLocaleTest;
import org.junit.jupiter.api.Test;

import java.util.Locale;

import static org.jsoup.parser.Parser.NamespaceHtml;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;

/**
 * Tests how {@link Tag#valueOf} normalizes the supplied tag name, covering both
 * case-insensitivity and surrounding-whitespace trimming.
 */
public class TagTest_trims {

    /**
     * Tag name lookup ignores case: "script" and "SCRIPT" resolve to equal tags.
     * For a shared {@link TagSet} the predefined tag is also the very same instance.
     */
    @MultiLocaleTest
    public void canBeInsensitive(Locale locale) {
        // Locale is varied to ensure case-folding is not affected by the default locale
        // (e.g. the Turkish dotless-i rule).
        Locale.setDefault(locale);

        Tag lowerCaseScript = Tag.valueOf("script", NamespaceHtml, ParseSettings.htmlDefault);
        Tag upperCaseScript = Tag.valueOf("SCRIPT", NamespaceHtml, ParseSettings.htmlDefault);
        assertEquals(lowerCaseScript, upperCaseScript);

        TagSet htmlTags = TagSet.Html();
        Tag sharedLowerCaseScript = htmlTags.valueOf("script", NamespaceHtml, ParseSettings.htmlDefault);
        Tag sharedUpperCaseScript = htmlTags.valueOf("SCRIPT", NamespaceHtml, ParseSettings.htmlDefault);
        assertSame(sharedLowerCaseScript, sharedUpperCaseScript);
    }

    /**
     * Leading and trailing whitespace around a tag name is trimmed, so "p" and " p "
     * resolve to equal tags.
     */
    @Test
    public void trims() {
        Tag plainName = Tag.valueOf("p");
        Tag paddedName = Tag.valueOf(" p ");
        assertEquals(plainName, paddedName);
    }
}
