package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.temporal.TemporalAccessor;

import org.junit.jupiter.api.Test;

public class TestQuarter_test_from_TemporalAccessor_null {

    @Test
    public void test_from_TemporalAccessor_null() {
        // Quarter.from must reject a null argument with NullPointerException
        //noinspection DataFlowIssue - intentional null to verify contract
        assertThrows(NullPointerException.class, () -> Quarter.from((TemporalAccessor) null));
    }
}
