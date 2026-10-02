package com.fasterxml.jackson.annotation;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class JsonTypeInfoTest_testWithRequireTypeIdForSubtypes extends AnnotationTestUtil {

    @Test
    public void testWithRequireTypeIdForSubtypes() {
        JsonTypeInfo.Value emptyValue = JsonTypeInfo.Value.EMPTY;
        assertNull(emptyValue.getRequireTypeIdForSubtypes(),
                "EMPTY should not require type ids for subtypes by default");

        JsonTypeInfo.Value valueRequiringSubtypeTypeIds =
                emptyValue.withRequireTypeIdForSubtypes(Boolean.TRUE);
        assertEquals(Boolean.TRUE, valueRequiringSubtypeTypeIds.getRequireTypeIdForSubtypes(),
                "Boolean.TRUE should enable required type ids for subtypes");

        JsonTypeInfo.Value valueNotRequiringSubtypeTypeIds =
                emptyValue.withRequireTypeIdForSubtypes(Boolean.FALSE);
        assertEquals(Boolean.FALSE, valueNotRequiringSubtypeTypeIds.getRequireTypeIdForSubtypes(),
                "Boolean.FALSE should disable required type ids for subtypes");

        JsonTypeInfo.Value valueUsingDefaultSubtypeTypeIdRequirement =
                emptyValue.withRequireTypeIdForSubtypes(null);
        assertNull(valueUsingDefaultSubtypeTypeIdRequirement.getRequireTypeIdForSubtypes(),
                "null should restore the default subtype type id requirement");
    }
}
