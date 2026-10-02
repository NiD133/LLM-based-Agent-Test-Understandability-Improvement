package com.fasterxml.jackson.annotation;

import com.fasterxml.jackson.annotation.JsonTypeInfo.As;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class JsonTypeInfoTest_testWithRequireTypeIdForSubtypes extends AnnotationTestUtil {

    @Test
    public void testWithRequireTypeIdForSubtypes() {
        JsonTypeInfo.Value empty = JsonTypeInfo.Value.EMPTY;
        assertNull(empty.getRequireTypeIdForSubtypes());
        JsonTypeInfo.Value requireTypeIdTrue = empty.withRequireTypeIdForSubtypes(Boolean.TRUE);
        assertEquals(Boolean.TRUE, requireTypeIdTrue.getRequireTypeIdForSubtypes());
        JsonTypeInfo.Value requireTypeIdFalse = empty.withRequireTypeIdForSubtypes(Boolean.FALSE);
        assertEquals(Boolean.FALSE, requireTypeIdFalse.getRequireTypeIdForSubtypes());
        JsonTypeInfo.Value requireTypeIdDefault = empty.withRequireTypeIdForSubtypes(null);
        assertNull(requireTypeIdDefault.getRequireTypeIdForSubtypes());
    }
}
