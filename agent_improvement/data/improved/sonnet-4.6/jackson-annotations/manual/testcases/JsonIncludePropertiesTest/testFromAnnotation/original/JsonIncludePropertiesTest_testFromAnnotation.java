package com.fasterxml.jackson.annotation;

import java.util.*;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class JsonIncludePropertiesTest_testFromAnnotation extends AnnotationTestUtil {

    private final JsonIncludeProperties.Value ALL = JsonIncludeProperties.Value.all();

    private Set<String> _set(String... args) {
        return new LinkedHashSet<String>(Arrays.asList(args));
    }

    @Test
    public void testFromAnnotation() {
        JsonIncludeProperties.Value v = JsonIncludeProperties.Value.from(Bogus.class.getAnnotation(JsonIncludeProperties.class));
        assertNotNull(v);
        Set<String> included = v.getIncluded();
        assertEquals(2, v.getIncluded().size());
        assertEquals(_set("foo", "bar"), included);
        assertNull(v.getOrdered());
        String tmp = v.toString();
        boolean test1 = tmp.equals("JsonIncludeProperties.Value(included=[foo, bar],ordered=null)");
        boolean test2 = tmp.equals("JsonIncludeProperties.Value(included=[bar, foo],ordered=null)");
        assertTrue(test1 || test2);
        assertEquals(v, JsonIncludeProperties.Value.from(Bogus.class.getAnnotation(JsonIncludeProperties.class)));
        // Let's also verify JDK serializability
        byte[] b = jdkSerialize(v);
        JsonIncludeProperties.Value deser = jdkDeserialize(b);
        assertEquals(v, deser);
    }
}
