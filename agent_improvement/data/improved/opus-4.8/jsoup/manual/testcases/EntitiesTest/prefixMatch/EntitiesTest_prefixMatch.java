package org.jsoup.nodes;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Verifies the longest-prefix entity matching behaviour of {@link Entities#unescape(String, boolean)}.
 *
 * <p>Background: when an HTML named character reference such as {@code &notit;} is not itself a known
 * entity, the parser falls back to the longest base entity that is a prefix of the reference. So
 * {@code &notit;} resolves via the {@code not} entity (¬) leaving {@code it;} as literal text, whereas
 * {@code &notin;} is a complete entity and resolves directly to ∉.</p>
 *
 * <p>The fallback only applies outside of attribute values; inside attributes the truncated form is
 * left untouched.</p>
 *
 * @see <a href="https://github.com/jhy/jsoup/issues/2207">jsoup issue #2207</a>
 * @see <a href="https://html.spec.whatwg.org/multipage/parsing.html#character-reference-state">WHATWG
 * character reference state</a>
 */
public class EntitiesTest_prefixMatch {

    @Test
    public void prefixMatch() {
        // Mixes a prefix-matched reference (&notit;) with a fully-defined one (&notin;).
        String input = "I'm &notit; I tell you. I'm &notin; I tell you.";

        // Lenient parsing (strict = false), as used for text content: &notit; falls back to the
        // "not" entity, leaving "it;" as literal text -> "¬it;".
        boolean strict = false;
        String expectedInText = "I'm ¬it; I tell you. I'm ∉ I tell you.";
        assertEquals(expectedInText, Entities.unescape(input, strict));

        // Strict parsing (strict = true), as used for attribute values: no prefix fallback, so
        // &notit; is left untouched while the complete &notin; still resolves.
        strict = true;
        String expectedInAttribute = "I'm &notit; I tell you. I'm ∉ I tell you.";
        assertEquals(expectedInAttribute, Entities.unescape(input, strict));
    }
}
