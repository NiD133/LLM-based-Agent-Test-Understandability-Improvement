"""lib -- shared code for feature_analysis.py and stability_check.py.

  common.py            subprocess / JDK detection / json / dedup helpers
  feature_registry.py  canonical feature names + display order
  gumtree.py           GumTree executor -> comment changes only
  refactoringminer.py  RefactoringMiner executor (throwaway git repo)
  summary.py           GumTree + RefactoringMiner -> one structural_diff/ per test
  javaparser_blocks.py JavaParser @Test-body block counts
"""
