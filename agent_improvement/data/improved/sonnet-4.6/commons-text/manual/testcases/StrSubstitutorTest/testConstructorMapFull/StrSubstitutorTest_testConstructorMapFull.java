package org.apache.commons.text;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.Test;

public class StrSubstitutorTest_testConstructorMapFull {

    /**
     * Tests constructors that accept a map together with custom prefix, suffix,
     * escape character, and optional value delimiter.
     *
     * Constructor under test 1: StrSubstitutor(map, prefix, suffix, escapeChar)
     *   - Variables are delimited by prefix/suffix (e.g. {@code <name>}).
     *   - A variable reference preceded by the escape char is left as-is.
     *
     * Constructor under test 2: StrSubstitutor(map, prefix, suffix, escapeChar, valueDelimiter)
     *   - Same as above, but supports a default value separated by the valueDelimiter
     *     (e.g. {@code <name2||commons>} resolves to "commons" when "name2" is absent).
     */
    @Test
    void testConstructorMapFull() {
        final Map<String, String> map = new HashMap<>();
        map.put("name", "commons");

        // Constructor 1: map + custom delimiters + escape char
        // '<' and '>' are the variable prefix/suffix; '!' is the escape character.
        // "!<" is an escaped prefix, so it is preserved literally as "<".
        // "<name>" is a normal variable reference that resolves to "commons".
        final StrSubstitutor subWithEscape = new StrSubstitutor(map, "<", ">", '!');
        final String templateWithEscape = "Hi !< <name>";
        final String expectedWithEscape = "Hi < commons";
        assertEquals(expectedWithEscape, subWithEscape.replace(templateWithEscape));

        // Constructor 2: map + custom delimiters + escape char + value delimiter
        // "||" is the value delimiter: <varName||defaultValue> uses defaultValue when varName is absent.
        // "name2" is not in the map, so "<name2||commons>" resolves to the default value "commons".
        // "!<" is again an escaped prefix, preserved literally as "<".
        final StrSubstitutor subWithDelimiter = new StrSubstitutor(map, "<", ">", '!', "||");
        final String templateWithDefault = "Hi !< <name2||commons>";
        final String expectedWithDefault = "Hi < commons";
        assertEquals(expectedWithDefault, subWithDelimiter.replace(templateWithDefault));
    }
}
