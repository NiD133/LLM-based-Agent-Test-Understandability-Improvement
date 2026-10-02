package org.jsoup.nodes;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Tests that HTML entity unescaping correctly handles prefix matches per the HTML spec.
 *
 * The HTML spec allows named character references to be matched without a trailing semicolon
 * in text content, but NOT in attribute values. For example, {@code &notit;} should be
 * parsed as {@code &not;} (U+00AC, NOT SIGN) followed by the literal text {@code it;},
 * because {@code not} is a valid named entity prefix of {@code notit}.
 *
 * @see <a href="https://github.com/jhy/jsoup/issues/2207">jsoup issue #2207</a>
 * @see <a href="https://html.spec.whatwg.org/multipage/parsing.html#character-reference-state">HTML spec: character reference state</a>
 */
public class EntitiesTest_prefixMatch {

    // Input string containing two ambiguous entity references:
    //   &notit; — "notit" is NOT a named entity, but "not" IS (U+00AC, ¬)
    //   &notin; — "notin" IS a named entity (U+2209, ∉)
    private static final String INPUT_WITH_PREFIX_ENTITIES =
        "I'm &notit; I tell you. I'm &notin; I tell you.";

    // In text (non-strict) mode: &notit; resolves to ¬ + literal "it;" because "not" is a prefix entity
    private static final String EXPECTED_TEXT_MODE_RESULT =
        "I'm ¬it; I tell you. I'm ∉ I tell you.";

    // In attribute (strict) mode: &notit; stays as-is because attributes require a trailing ";"
    // and "notit" is not a valid entity name; only &notin; (a complete, valid entity) is resolved
    private static final String EXPECTED_ATTRIBUTE_MODE_RESULT =
        "I'm &notit; I tell you. I'm ∉ I tell you.";

    @Test
    public void prefixMatch() {
        // In text content (strict=false), the parser applies prefix matching:
        // &notit; is treated as &not; (¬) followed by literal "it;"
        assertEquals(EXPECTED_TEXT_MODE_RESULT, Entities.unescape(INPUT_WITH_PREFIX_ENTITIES, false));

        // In attribute values (strict=true), prefix matching is disabled:
        // &notit; has no valid named entity match with a semicolon, so it is left unchanged
        assertEquals(EXPECTED_ATTRIBUTE_MODE_RESULT, Entities.unescape(INPUT_WITH_PREFIX_ENTITIES, true));
    }
}
