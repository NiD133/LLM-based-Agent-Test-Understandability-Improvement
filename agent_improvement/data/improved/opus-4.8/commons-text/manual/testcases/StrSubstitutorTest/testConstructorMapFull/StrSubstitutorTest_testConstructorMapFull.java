package org.apache.commons.text;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.Test;

/**
 * Tests the {@link StrSubstitutor} constructors that accept a variable map together with custom
 * prefix, suffix, escape character and (optionally) a default-value delimiter.
 */
public class StrSubstitutorTest_testConstructorMapFull {

    @Test
    void testConstructorMapFull() {
        // A single variable "name" -> "commons" resolved from the map.
        final Map<String, String> variables = new HashMap<>();
        variables.put("name", "commons");

        // Constructor with custom prefix "<", suffix ">" and escape char '!'.
        // In "Hi !< <name>":
        //   - "!<" is an escaped prefix, so it collapses to the literal "<".
        //   - "<name>" is a variable reference that resolves to "commons".
        StrSubstitutor sub = new StrSubstitutor(variables, "<", ">", '!');
        assertEquals("Hi < commons", sub.replace("Hi !< <name>"));

        // Same as above plus a default-value delimiter "||".
        // In "Hi !< <name2||commons>":
        //   - "!<" again collapses to the literal "<".
        //   - "<name2||commons>" references the unknown variable "name2", so it falls back
        //     to the default value "commons" given after the "||" delimiter.
        sub = new StrSubstitutor(variables, "<", ">", '!', "||");
        assertEquals("Hi < commons", sub.replace("Hi !< <name2||commons>"));
    }
}
