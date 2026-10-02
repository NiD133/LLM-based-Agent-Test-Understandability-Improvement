package com.fasterxml.jackson.annotation;

import com.fasterxml.jackson.annotation.JsonFormat.Feature;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Verifies the three-valued (null / true / false) handling of a
 * {@link JsonFormat.Feature} flag on a {@link JsonFormat.Value}.
 *
 * <p>Using {@link Feature#ACCEPT_CASE_INSENSITIVE_VALUES} as the example feature,
 * {@link JsonFormat.Value#getFeature} should report:
 * <ul>
 *   <li>{@code null}  when the feature has never been set,</li>
 *   <li>{@code true}  after {@link JsonFormat.Value#withFeature},</li>
 *   <li>{@code false} after {@link JsonFormat.Value#withoutFeature}.</li>
 * </ul>
 */
public class JsonFormatTest_testCaseInsensitiveValues extends AnnotationTestUtil {

    private static final Feature CASE_INSENSITIVE = Feature.ACCEPT_CASE_INSENSITIVE_VALUES;

    @Test
    public void testCaseInsensitiveValues() {
        JsonFormat.Value unset = JsonFormat.Value.empty();
        assertNull(unset.getFeature(CASE_INSENSITIVE),
                "An empty Value should leave the feature unset (null)");

        JsonFormat.Value enabled = unset.withFeature(CASE_INSENSITIVE);
        assertTrue(enabled.getFeature(CASE_INSENSITIVE),
                "withFeature should explicitly enable the feature");

        JsonFormat.Value disabled = unset.withoutFeature(CASE_INSENSITIVE);
        assertFalse(disabled.getFeature(CASE_INSENSITIVE),
                "withoutFeature should explicitly disable the feature");
    }
}
