package org.jsoup.nodes;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class EntitiesTest_escapeDefaults {
    private static final String TEXT_WITH_RESERVED_AND_UNICODE_CHARS =
        "Hello &<> Å å π 新 there ¾ © » ' \"";
    private static final String DEFAULT_ESCAPED_TEXT =
        "Hello &amp;&lt;&gt; Å å π 新 there ¾ © » &apos; &quot;";

    @Test
    public void escapeDefaults() {
        String escaped = Entities.escape(TEXT_WITH_RESERVED_AND_UNICODE_CHARS);

        assertEquals(DEFAULT_ESCAPED_TEXT, escaped);
    }
}
