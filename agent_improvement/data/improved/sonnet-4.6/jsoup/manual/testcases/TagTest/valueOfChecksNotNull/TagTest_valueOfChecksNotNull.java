package org.jsoup.parser;

import org.jsoup.MultiLocaleExtension.MultiLocaleTest;
import org.junit.jupiter.api.Test;
import java.util.Locale;
import static org.jsoup.parser.Parser.NamespaceHtml;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests for {@link Tag#valueOf(String)} input validation and case-insensitivity behaviour.
 *
 * <ul>
 *   <li>{@link #valueOfChecksNotNull()} – passing {@code null} must throw {@link IllegalArgumentException}.</li>
 *   <li>{@link #canBeInsensitive(Locale)} – tag lookup must be case-insensitive in all locales.</li>
 * </ul>
 */
public class TagTest_valueOfChecksNotNull {

    /**
     * Verifies that {@link Tag#valueOf(String)} rejects a {@code null} tag name by throwing
     * {@link IllegalArgumentException}, preventing silent null-pointer failures downstream.
     */
    @Test
    public void valueOfChecksNotNull() {
        assertThrows(IllegalArgumentException.class, () -> Tag.valueOf(null));
    }

    /**
     * Verifies that tag lookup is case-insensitive regardless of the JVM's default {@link Locale}.
     * Both the static {@link Tag#valueOf} factory and the {@link TagSet}-based lookup are covered:
     * the static factory returns equal (but potentially distinct) instances, while the {@link TagSet}
     * lookup must return the exact same cached instance ({@code assertSame}).
     */
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
}
