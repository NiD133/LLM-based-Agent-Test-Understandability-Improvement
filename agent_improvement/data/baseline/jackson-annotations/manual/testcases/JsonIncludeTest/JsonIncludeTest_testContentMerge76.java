package com.fasterxml.jackson.annotation;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class JsonIncludeTest_testContentMerge76 extends AnnotationTestUtil {

    private final JsonInclude.Value EMPTY = JsonInclude.Value.empty();

    // for [annotations#76]
    @Test
    public void testContentMerge76() {
        JsonInclude.Value v1 = JsonInclude.Value.empty().withContentInclusion(JsonInclude.Include.ALWAYS).withValueInclusion(JsonInclude.Include.NON_ABSENT);
        JsonInclude.Value v2 = JsonInclude.Value.empty().withContentInclusion(JsonInclude.Include.NON_EMPTY).withValueInclusion(JsonInclude.Include.USE_DEFAULTS);
        // v1 priority
        JsonInclude.Value v12 = v2.withOverrides(v1);
        // v2 priority
        JsonInclude.Value v21 = v1.withOverrides(v2);
        assertEquals(JsonInclude.Include.ALWAYS, v12.getContentInclusion());
        assertEquals(JsonInclude.Include.NON_ABSENT, v12.getValueInclusion());
        assertEquals(JsonInclude.Include.NON_EMPTY, v21.getContentInclusion());
        assertEquals(JsonInclude.Include.NON_ABSENT, v21.getValueInclusion());
    }
}
