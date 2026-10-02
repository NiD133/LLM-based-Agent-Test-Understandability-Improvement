package com.fasterxml.jackson.annotation;

import com.fasterxml.jackson.annotation.JsonFormat.Feature;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;
import org.junit.jupiter.api.Test;
import static com.fasterxml.jackson.annotation.JsonFormat.DEFAULT_RADIX;
import static org.junit.jupiter.api.Assertions.*;

public class JsonFormatTest_testFeaturesWithoutClearsEnabled extends AnnotationTestUtil {

    private final JsonFormat.Value EMPTY = JsonFormat.Value.empty();

    @Test
    void testFeaturesWithoutClearsEnabled() {
        // without() after with() on same feature should result in disabled
        JsonFormat.Features f = JsonFormat.Features.empty().with(Feature.ACCEPT_SINGLE_VALUE_AS_ARRAY).without(Feature.ACCEPT_SINGLE_VALUE_AS_ARRAY);
        assertEquals(Boolean.FALSE, f.get(Feature.ACCEPT_SINGLE_VALUE_AS_ARRAY));
    }
}
