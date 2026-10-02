package com.fasterxml.jackson.annotation;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class JsonIncludeTest_testFilters extends AnnotationTestUtil {

    private final JsonInclude.Value EMPTY = JsonInclude.Value.empty();

    @Test
    public void testFilters() {
        JsonInclude.Value empty = JsonInclude.Value.empty();
        assertNull(empty.getValueFilter());
        assertNull(empty.getContentFilter());
        // note: filter class choices are arbitrary, just confirming assignments
        JsonInclude.Value v1 = empty.withValueFilter(String.class);
        assertEquals(JsonInclude.Include.CUSTOM, v1.getValueInclusion());
        assertEquals(String.class, v1.getValueFilter());
        assertNull(v1.withValueFilter(null).getValueFilter());
        assertNull(v1.withValueFilter(Void.class).getValueFilter());
        JsonInclude.Value v2 = empty.withContentFilter(Long.class);
        assertEquals(JsonInclude.Include.CUSTOM, v2.getContentInclusion());
        assertEquals(Long.class, v2.getContentFilter());
        assertNull(v2.withContentFilter(null).getContentFilter());
        assertNull(v2.withContentFilter(Void.class).getContentFilter());
    }
}
