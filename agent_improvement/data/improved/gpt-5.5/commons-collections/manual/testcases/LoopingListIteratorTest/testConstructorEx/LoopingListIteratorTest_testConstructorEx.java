package org.apache.commons.collections4.iterators;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

public class LoopingListIteratorTest_testConstructorEx {

    @Test
    void constructorRejectsNullList() {
        assertThrows(NullPointerException.class, () -> new LoopingListIterator<>(null));
    }
}
