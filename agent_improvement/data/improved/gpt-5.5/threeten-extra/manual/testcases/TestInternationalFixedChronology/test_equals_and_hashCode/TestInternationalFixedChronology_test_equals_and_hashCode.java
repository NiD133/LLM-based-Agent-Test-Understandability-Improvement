package org.threeten.extra.chrono;

import org.junit.jupiter.api.Test;

import com.google.common.testing.EqualsTester;

@SuppressWarnings("static-method")
public class TestInternationalFixedChronology_test_equals_and_hashCode {

    @Test
    public void test_equals_and_hashCode() {
        new EqualsTester()
                .addEqualityGroup(
                        InternationalFixedDate.of(2000, 1, 3),
                        InternationalFixedDate.of(2000, 1, 3))
                .addEqualityGroup(
                        InternationalFixedDate.of(2000, 1, 4),
                        InternationalFixedDate.of(2000, 1, 4))
                .addEqualityGroup(
                        InternationalFixedDate.of(2000, 2, 3),
                        InternationalFixedDate.of(2000, 2, 3))
                .addEqualityGroup(
                        InternationalFixedDate.of(2000, 6, 28),
                        InternationalFixedDate.of(2000, 6, 28))
                .addEqualityGroup(
                        InternationalFixedDate.of(2000, 6, 29),
                        InternationalFixedDate.of(2000, 6, 29))
                .addEqualityGroup(
                        InternationalFixedDate.of(2000, 13, 28),
                        InternationalFixedDate.of(2000, 13, 28))
                .addEqualityGroup(
                        InternationalFixedDate.of(2001, 1, 1),
                        InternationalFixedDate.of(2001, 1, 1))
                .addEqualityGroup(
                        InternationalFixedDate.of(2001, 13, 29),
                        InternationalFixedDate.of(2001, 13, 29))
                .addEqualityGroup(
                        InternationalFixedDate.of(2004, 6, 29),
                        InternationalFixedDate.of(2004, 6, 29))
                .testEquals();
    }
}
