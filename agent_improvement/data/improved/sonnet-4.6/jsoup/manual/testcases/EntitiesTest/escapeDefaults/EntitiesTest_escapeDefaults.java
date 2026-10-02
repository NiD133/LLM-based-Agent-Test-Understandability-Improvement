package org.jsoup.nodes;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Tests for {@link Entities#escape(String)}, which escapes a string using the
 * default settings: base HTML escape mode and UTF-8 character set.
 *
 * <p>Under these defaults:
 * <ul>
 *   <li>{@code &}  → {@code &amp;}</li>
 *   <li>{@code <}  → {@code &lt;}</li>
 *   <li>{@code >}  → {@code &gt;}</li>
 *   <li>{@code '}  → {@code &apos;} (both text and attribute contexts are assumed)</li>
 *   <li>{@code "}  → {@code &quot;} (attribute context is assumed)</li>
 *   <li>Non-ASCII characters that UTF-8 can encode (Å, å, π, 新, ¾, ©, ») are
 *       left as-is because they do not need to be escaped when the output
 *       charset supports them.</li>
 * </ul>
 */
public class EntitiesTest_escapeDefaults {

    @Test
    public void escapeDefaults() {
        // Input contains: HTML special chars (&, <, >), non-ASCII UTF-8 chars
        // that should pass through unchanged, and quote chars (' and ") that
        // must always be escaped because both text and attribute contexts are assumed.
        String input = "Hello &<> Å å π 新 there ¾ © » ' \"";

        String escaped = Entities.escape(input);

        // &, <, > are always escaped; ', " are escaped (combined text+attribute mode);
        // all other characters are encodable in UTF-8 and therefore left unchanged.
        String expected = "Hello &amp;&lt;&gt; Å å π 新 there ¾ © » &apos; &quot;";
        assertEquals(expected, escaped);
    }
}
