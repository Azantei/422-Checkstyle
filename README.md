# CptS 422 - Checkstyle Custom Checks

## Student Info
- Name: Camille Orego
- Project: Deliverable 1 Checks

## Deliverable 1 Checks

### Category A Checks:
1. **CommentCountCheck** - Counts the total number of comment blocks
2. **LoopCountCheck** - Counts the total number of loop statements

## Definitions and Assumptions

### Comments
- **What counts as a comment**: Any comment block (single-line `//` or multi-line `/* */`)
- **Counting method**: Each comment block is counted once, regardless of how many lines it spans
- Uses `TokenTypes.COMMENT_CONTENT` to detect comments

### Looping Statements
- **Loop Types**: `for` loops, `while` loops, and `do-while` loops
- **Counting method**: Each loop statement is counted once
- Uses `TokenTypes.LITERAL_FOR`, `TokenTypes.LITERAL_WHILE`, and `TokenTypes.LITERAL_DO`