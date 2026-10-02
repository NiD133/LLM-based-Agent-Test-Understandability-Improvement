package com.fasterxml.jackson.annotation;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class JsonIncludeTest_testWithOverridesContentFilter extends AnnotationTestUtil {

    private final JsonInclude.Value EMPTY = JsonInclude.Value.empty();

    // Verify that withOverrides() properly detects content filter changes
    @Test
    public void testWithOverridesContentFilter() {
        JsonInclude.Value base = new JsonInclude.Value(Include.NON_EMPTY, Include.NON_EMPTY, null, null);
        JsonInclude.Value overrideContentFilter = new JsonInclude.Value(Include.USE_DEFAULTS, Include.USE_DEFAULTS, null, Long.class);
        JsonInclude.Value merged = base.withOverrides(overrideContentFilter);
        assertEquals(Long.class, merged.getContentFilter());
    }
}
