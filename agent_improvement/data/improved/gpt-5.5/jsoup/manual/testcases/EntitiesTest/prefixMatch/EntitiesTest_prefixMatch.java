package org.jsoup.nodes;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class EntitiesTest_prefixMatch {
    private static final String TEXT_WITH_PREFIX_MATCH =
        "I'm &notit; I tell you. I'm &notin; I tell you.";
    private static final String TEXT_MODE_UNESCAPES_LONGEST_VALID_PREFIX =
        "I'm ¬it; I tell you. I'm ∉ I tell you.";
    private static final String ATTRIBUTE_MODE_REQUIRES_COMPLETE_ENTITY_NAME =
        "I'm &notit; I tell you. I'm ∉ I tell you.";

    @Test
    public void prefixMatch() {
        // https://github.com/jhy/jsoup/issues/2207
        // Example from https://html.spec.whatwg.org/multipage/parsing.html#character-reference-state
        assertEquals(
            TEXT_MODE_UNESCAPES_LONGEST_VALID_PREFIX,
            Entities.unescape(TEXT_WITH_PREFIX_MATCH, false)
        );

        assertEquals(
            ATTRIBUTE_MODE_REQUIRES_COMPLETE_ENTITY_NAME,
            Entities.unescape(TEXT_WITH_PREFIX_MATCH, true)
        );
    }
}
