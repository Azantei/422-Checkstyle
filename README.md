# CptS 422 - Checkstyle Plug-In Project

## Student Info
- Name: Camille Orego
- Project: Checkstyle Plug-In

---

## Implemented Checks

### Category A - Halstead Metrics
1. **HalsteadLengthCheck** - Total operators and operands
2. **HalsteadVocabularyCheck** - Unique operators and operands
3. **HalsteadVolumeCheck** - Program size (N × log₂(n))
4. **HalsteadDifficultyCheck** - How hard to understand ((n₁/2) × (N₂/n₂))
5. **HalsteadEffortCheck** - Effort needed (Difficulty × Volume)

### Category B - Code Structure Metrics
1. **CommentCountCheck** - Total number of comment blocks
2. **CommentLineCountCheck** - Number of lines containing comments
3. **LoopCountCheck** - Total number of loop statements
4. **OperatorCountCheck** - Total count of operators
5. **OperandCountCheck** - Total count of operands
6. **ExpressionCountCheck** - Total number of expressions

---

## Definitions and Assumptions

### Operators
**Tokens counted as operators:**
- Arithmetic: `+`, `-`, `*`, `/`, `%`, `++`, `--`
- Assignment: `=`, `+=`, `-=`, `*=`, `/=`, `%=`
- Comparison: `<`, `>`, `<=`, `>=`, `==`, `!=`
- Logical: `&&`, `||`, `!`
- Other: `;`, `.`, `()`, `[]`, `{}`
- Keywords: `new`, `return`, `if`, `while`, `for`, `int`, etc.

**Implementation:** Uses `HalsteadTokenTypes.halsteadOperators` list

### Operands
**Tokens counted as operands:**
- Variables and identifiers (`IDENT`)
- Numeric literals (`NUM_INT`, `NUM_FLOAT`, `NUM_DOUBLE`, `NUM_LONG`)
- String literals (`STRING_LITERAL`)
- Special keywords: `this`, `true`, `false`, `null`
- Class and method names in definitions

**Implementation:** Uses `HalsteadTokenTypes.halsteadOperands` list

### Comments
**What counts as a comment:**
- Single-line comments (`//`)
- Multi-line block comments (`/* */`)
- Javadoc comments (`/** */`)

**Counting methods:**
- **CommentCountCheck**: Each comment block counted once (uses `COMMENT_CONTENT` token)
- **CommentLineCountCheck**: Each line containing a comment counted separately (uses `SINGLE_LINE_COMMENT`, `BLOCK_COMMENT_BEGIN`, `BLOCK_COMMENT_END`)

**Known limitations:**
- Comments inside string literals are not counted (correctly excluded)
- Empty comment blocks are counted

### Looping Statements
**Loop types counted:**
- `for` loops (including for-each)
- `while` loops
- `do-while` loops

**Implementation:** Uses `LITERAL_FOR`, `LITERAL_WHILE`, `LITERAL_DO` tokens

**Counting method:** Each loop statement counted once, including nested loops

### Expressions
**What counts as an expression:**
- Any `EXPR` token in the syntax tree
- Includes: assignments, arithmetic operations, method calls, comparisons

**Implementation:** Uses `EXPR` token type

---

## Testing

### White Box Testing (JUnit)
- **Line Coverage**: 99% (234/236 lines)
- **Mutation Coverage**: 89% (75/84 mutants killed)
- **Test Strength**: 90%
- Detailed results in Pitclipse HTML report

### Black Box Testing
- 11 test case files created based on fault models
- All 11 checks tested with passing results
- Test cases cover edge cases including:
  - Multiple operators on one line
  - Nested structures
  - Comments in strings (correctly excluded)
  - Compound assignment operators
  - Pre/post increment/decrement operators

**Fault models documented in:** `FaultModel.java`

---

## Known Inaccuracies
- Halstead metrics may count package declarations and import statements differently than expected
- Division by zero in Halstead Difficulty when no operands present (returns 0)
- Empty files may produce unexpected results for computed metrics
