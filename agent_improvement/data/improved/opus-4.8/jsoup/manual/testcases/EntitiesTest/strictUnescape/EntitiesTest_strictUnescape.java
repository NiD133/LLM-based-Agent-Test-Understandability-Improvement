package org.jsoup.nodes;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class EntitiesTest_strictUnescape {

    /**
     * Verifies how {@link Entities#unescape} resolves entities in strict versus lenient mode.
     *
     * <p>In strict mode (used for attribute values) an entity is only unescaped when it is
     * properly terminated with a semicolon (e.g. {@code &amp;}). A bare {@code &amp} without a
     * trailing semicolon is left untouched. In lenient mode the bare {@code &amp} is also
     * resolved to {@code &}.</p>
     */
    @Test
    public void strictUnescape() {
        // Input contains one un-terminated entity ("&amp") and one terminated entity ("&amp;").
        String input = "Hello &amp= &amp;";

        // Strict: only the terminated "&amp;" is unescaped; the bare "&amp" stays as-is.
        assertEquals("Hello &amp= &", Entities.unescape(input, true));

        // Lenient (explicit flag): both the bare "&amp" and the terminated "&amp;" are unescaped.
        assertEquals("Hello &= &", Entities.unescape(input, false));

        // Lenient is the default, so the single-argument overload behaves like strict=false.
        assertEquals("Hello &= &", Entities.unescape(input));
    }
}
