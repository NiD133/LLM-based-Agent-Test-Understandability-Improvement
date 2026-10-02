package com.fasterxml.jackson.annotation;

import com.fasterxml.jackson.annotation.JsonFormat.Feature;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;
import org.junit.jupiter.api.Test;
import static com.fasterxml.jackson.annotation.JsonFormat.DEFAULT_RADIX;
import static org.junit.jupiter.api.Assertions.*;

public class JsonFormatTest_testRadix extends AnnotationTestUtil {

    private final JsonFormat.Value EMPTY = JsonFormat.Value.empty();

    @Test
    void testRadix() {
        //Non-Default radix overrides the default
        int binaryRadix = 2;
        final JsonFormat.Value v = JsonFormat.Value.forRadix(binaryRadix);
        JsonFormat.Value merged = EMPTY.withOverrides(v);
        assertEquals(DEFAULT_RADIX, EMPTY.getRadix());
        assertEquals(binaryRadix, merged.getRadix());
        //Default does not override
        final JsonFormat.Value v2 = JsonFormat.Value.forRadix(binaryRadix);
        merged = v2.withOverrides(EMPTY);
        assertEquals(binaryRadix, v2.getRadix());
        assertEquals(binaryRadix, merged.getRadix());
        JsonFormat.Value emptyWithBinaryRadix = EMPTY.withRadix(binaryRadix);
        assertEquals(binaryRadix, emptyWithBinaryRadix.getRadix());
        JsonFormat.Value forBinaryRadix = JsonFormat.Value.forRadix(binaryRadix);
        assertEquals(binaryRadix, forBinaryRadix.getRadix());
    }
}
