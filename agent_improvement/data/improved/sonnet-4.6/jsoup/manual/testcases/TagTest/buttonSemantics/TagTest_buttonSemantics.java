package org.jsoup.parser;

import org.jsoup.MultiLocaleExtension.MultiLocaleTest;
import org.junit.jupiter.api.Test;

import java.util.Locale;

import static org.jsoup.parser.Parser.NamespaceHtml;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests for {@link Tag} covering the semantic properties of the HTML {@code <button>} element
 * and case-insensitive tag lookup behaviour.
 *
 * <p>The {@code <button>} element is an inline, non-block element that acts as a text boundary
 * (e.g. for {@code Element.text()}) and is registered as a known HTML tag.</p>
 */
public class TagTest_buttonSemantics {

    /**
     * Verifies that tag lookup is case-insensitive in HTML mode: "script" and "SCRIPT" must
     * resolve to equal tags, and within a {@link TagSet} they must resolve to the identical
     * (same reference) instance.
     */
    @MultiLocaleTest
    public void canBeInsensitive(Locale locale) {
        Locale.setDefault(locale);

        // Tags obtained via the static helper should be equal regardless of case.
        Tag script1 = Tag.valueOf("script", NamespaceHtml, ParseSettings.htmlDefault);
        Tag script2 = Tag.valueOf("SCRIPT", NamespaceHtml, ParseSettings.htmlDefault);
        assertEquals(script1, script2);

        // Tags obtained from the same TagSet must be the exact same cached instance.
        TagSet htmlTags = TagSet.Html();
        Tag script3 = htmlTags.valueOf("script", NamespaceHtml, ParseSettings.htmlDefault);
        Tag script4 = htmlTags.valueOf("SCRIPT", NamespaceHtml, ParseSettings.htmlDefault);
        assertSame(script3, script4);
    }

    /**
     * Verifies the semantic properties of the {@code <button>} tag as defined by the HTML spec:
     * <ul>
     *   <li>Inline — button flows within text content, not as a block-level element.</li>
     *   <li>Not block — confirms the inverse of the inline check.</li>
     *   <li>TextBoundary — button delimits readable text regions for {@code Element.text()}.</li>
     *   <li>Known tag — button is pre-defined in the HTML TagSet, not auto-created.</li>
     * </ul>
     */
    @Test
    public void buttonSemantics() {
        Tag button = Tag.valueOf("button");

        // <button> is an inline element — it does not force a new block layout context.
        assertTrue(button.isInline());
        assertFalse(button.isBlock());

        // <button> is a text boundary: its content is excluded when collecting text via Element.text().
        assertTrue(button.is(Tag.TextBoundary));

        // <button> must be recognised as a pre-defined HTML tag, not an unknown/custom tag.
        assertTrue(button.isKnownTag());
    }
}
