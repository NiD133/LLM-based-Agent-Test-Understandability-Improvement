package org.apache.commons.text;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.Test;

public class StrSubstitutorTest_testResolveVariable {

    /**
     * Verifies that the protected resolveVariable hook is invoked with the
     * correct arguments during in-place substitution, and that its return
     * value overrides the backing map lookup.
     *
     * Template layout:  "Hi ${name}!"
     * Character index:   0123456789 10
     *   startPos = 3  → index of '$' (beginning of the "${name}" token)
     *   endPos   = 10 → one past the closing '}', i.e. index of '!'
     */
    @Test
    void testResolveVariable() {
        final StrBuilder builder = new StrBuilder("Hi ${name}!");

        // Indices derived from "Hi ${name}!" — see Javadoc above.
        final int expectedStartPos = 3;
        final int expectedEndPos   = 10;

        final Map<String, String> map = new HashMap<>();
        map.put("name", "commons");

        final StrSubstitutor sub = new StrSubstitutor(map) {
            @Override
            protected String resolveVariable(final String variableName, final StrBuilder buf,
                    final int startPos, final int endPos) {
                assertEquals("name", variableName);
                assertSame(builder, buf);
                assertEquals(expectedStartPos, startPos);
                assertEquals(expectedEndPos, endPos);
                // Return an override value instead of the map's "commons"
                return "jakarta";
            }
        };

        sub.replaceIn(builder);
        assertEquals("Hi jakarta!", builder.toString());
    }
}
