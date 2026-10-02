package com.fasterxml.jackson.annotation;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class JsonFormatTest_testRadixInHashCode extends AnnotationTestUtil {

    @Test
    void testRadixInHashCode() {
        // Two Values with different radixes (binary vs hexadecimal) must be unequal,
        // and their hash codes must reflect that difference.
        JsonFormat.Value binaryRadixValue = JsonFormat.Value.forRadix(2);
        JsonFormat.Value hexadecimalRadixValue = JsonFormat.Value.forRadix(16);

        assertNotEquals(binaryRadixValue, hexadecimalRadixValue);
        assertNotEquals(binaryRadixValue.hashCode(), hexadecimalRadixValue.hashCode());
    }
}
