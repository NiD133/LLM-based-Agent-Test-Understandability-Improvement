package org.apache.commons.text;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.Test;

public class StringSubstitutorTest_testDetectsCyclicSubstitution {

    @Test
    void testDetectsCyclicSubstitution() {
        final Map<String, String> variables = new HashMap<>();
        variables.put("name", "<name>");

        assertThrows(
                IllegalStateException.class,
                () -> StringSubstitutor.replace("Hi <name>.", variables, "<", ">"));
    }
}
