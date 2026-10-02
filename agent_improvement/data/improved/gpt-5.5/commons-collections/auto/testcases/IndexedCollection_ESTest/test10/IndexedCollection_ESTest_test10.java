package org.apache.commons.collections4.collection;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import java.util.Collection;
import java.util.LinkedList;
import org.apache.commons.collections4.Transformer;
import org.apache.commons.collections4.functors.CloneTransformer;
import org.apache.commons.collections4.functors.ConstantTransformer;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class IndexedCollection_ESTest_test10 extends IndexedCollection_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test10() throws Throwable {
        LinkedList<Integer> sourceValues = new LinkedList<Integer>();
        Integer indexedValue = new Integer(0);
        ConstantTransformer<Integer, Integer> constantKeyTransformer =
                new ConstantTransformer<Integer, Integer>(indexedValue);
        IndexedCollection<Integer, Integer> sourceIndex =
                IndexedCollection.uniqueIndexedCollection(
                        (Collection<Integer>) sourceValues,
                        (Transformer<Integer, Integer>) constantKeyTransformer);

        sourceValues.add(indexedValue);

        Transformer<Object, Object> cloneTransformer = CloneTransformer.cloneTransformer();
        LinkedList<Object> targetValues = new LinkedList<Object>();
        IndexedCollection<Object, Object> targetIndex =
                IndexedCollection.uniqueIndexedCollection((Collection<Object>) targetValues, cloneTransformer);

        boolean changed = targetIndex.addAll(sourceIndex);

        assertTrue(targetValues.contains(0));
        assertTrue(changed);
    }
}
