package com.fasterxml.jackson.annotation;

import java.util.*;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class JsonIgnorePropertiesTest_testToString extends AnnotationTestUtil {

    private final JsonIgnoreProperties.Value EMPTY = JsonIgnoreProperties.Value.empty();

    private Set<String> _set(String... args) {
        return new LinkedHashSet<String>(Arrays.asList(args));
    }

    @Test
    public void testToString() {
        assertEquals("JsonIgnoreProperties.Value(ignored=[],ignoreUnknown=false,allowGetters=false,allowSetters=true,merge=true)", EMPTY.withAllowSetters().withMerge().toString());
        int hash = EMPTY.hashCode();
        // no real good way to test but...
        if (hash == 0) {
            fail("Should not get 0 for hash");
        }
    }
}
