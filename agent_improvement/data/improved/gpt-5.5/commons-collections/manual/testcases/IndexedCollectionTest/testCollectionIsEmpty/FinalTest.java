I’ll use the required compile-check workflow for this test change. First I need to read the skill instructions, then I’ll inspect the original test and CUT signatures before editing only the generated test file in the working directory.

I’ve loaded the compile-check instructions. Next I’m reading the existing test and the public surface it depends on so the refactor preserves the exact calls and assertions.

The original file is much larger than the single behavior under test and includes inherited harness fragments that obscure the actual `isEmpty()` check. I’m checking the local compile script now so the output file name and package match what the pipeline expects.

I’m replacing the generated test with a focused version of the same harness behavior: create the empty and full indexed collections, run the same `isEmpty()` assertions, and keep the same verification checks without unrelated collection-test scaffolding.

The refactored test file is in place. I’m running the required compile check exactly through `bash compile.sh` from the working directory.

The compile check failed on the local transformer method name. I’m switching to the required repair-loop instructions, then I’ll make the narrow API correction and recompile.

The Commons Collections `Transformer` interface in this classpath requires `transform(String)`. I’m changing only that method declaration in the test transformer.

The focused repair is applied. I’m rerunning the required compile check now.

COMPILE_OK — refactored `IndexedCollectionTest_testCollectionIsEmpty.java` compiles successfully.