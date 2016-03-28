package com.andrea.vm;


import com.andrea.pyobjects.PyCodeObject;
import com.andrea.utils.Utils;

import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

/**
 * Virtual Machine that runs the bytecode
 * PLEASE NOTE:
 * a) this virtual machine uses Long for ALL the natural numbers and Double for all the real ones.
 */
public class VirtualMachine {

    private static final int LT = 0; // <
    private static final int LE = 1; // <=
    private static final int EQ = 2; // ==
    private static final int NE = 3; // !=
    private static final int GT = 4; // >
    private static final int GE = 5; // >=
    private static final int IN = 6; // in
    private static final int NI = 7; // not in
    private static final int IS = 8; // is
    private static final int ISN = 9; // is not
    private static final int EM = 10; // exception match
    private static final int BD = 11; // BAD

    private Stack<Frame> callStack;
    private Frame currentFrame;
    private Object returnValue;

    public VirtualMachine() {
        this.callStack = new Stack<>();
        this.currentFrame = null;
        this.returnValue = null;
    }

    public Object runCode(PyCodeObject pyCodeObject) throws Exception {
        return runFrame(createFrame(pyCodeObject));
    }

    public Frame createFrame(PyCodeObject pyCodeObject) {
        return new Frame(pyCodeObject, currentFrame);
    }

    public Frame createFrame(PyCodeObject pyCodeObject, Object[] args) {
        Map<String, Object> locals = new HashMap<>(args.length);
        for (int i = 0; i < args.length; i++)
            locals.put(pyCodeObject.getVarName(i), args[i]);
        return new Frame(pyCodeObject, currentFrame, locals);
    }

    private void pushFrame(Frame frame) {
        currentFrame = frame;
        callStack.push(frame);
    }

    private void popFrame() {
        callStack.pop();
        currentFrame = callStack.isEmpty() ? null : callStack.peek();
    }

    public Object runFrame(Frame frame) throws Exception {
        pushFrame(frame);

        Integer[] code = frame.pyCodeObject.getCo_code();
        while (currentFrame.bytecodeCounter < currentFrame.bytecodeSize) {
            int instruction = code[currentFrame.bytecodeCounter];
            currentFrame.bytecodeCounter++;
            switch (instruction) {
                case 1: // POP_TOP
                    popTop();
                    break;
                case 2:
                    rotTwo(); // ROT_TWO
                    break;
                case 3: // ROT_THREE
                    rotThree();
                    break;
                case 4:// DUP_TOP
                    dupTop();
                    break;
                case 5: // DUP_TOP_TWO
                    dupTopTwo();
                    break;
                case 9: // NOP
                    nop();
                    break;
                case 10: // UNARY_POSITIVE
                    unaryPositive();
                    break;
                case 11: // UNARY_NEGATIVE
                    unaryNegative();
                    break;
                case 12: // UNARY_NOT
                    unaryNot();
                    break;
                case 15: // UNARY_INVERT
                    unaryInvert();
                    break;
                case 16: // BINARY_MATRIX_MULTIPLY
                    break;
                case 17: // INPLACE_MATRIX_MULTIPLY
                    break;
                case 19: // BINARY_POWER
                    binaryPower();
                    break;
                case 20: // BINARY_MULTIPLY
                    binaryMultiply();
                    break;
                case 22: // BINARY_MODULO
                    binaryModulo();
                    break;
                case 23: // BINARY_ADD
                    binaryAdd();
                    break;
                case 24: // BINARY_SUBTRACT
                    binarySubtract();
                    break;
                case 25: // BINARY_SUBSCR
                    break;
                case 26: // BINARY_FLOOR_DIVIDE
                    break;
                case 27: // BINARY_TRUE_DIVIDE
                    binaryTrueDivide();
                    break;
                case 28: // INPLACE_FLOOR_DIVIDE
                    break;
                case 29: //INPLACE_TRUE_DIVIDE
                    break;
                case 50: // GET_AITER
                    break;
                case 51: // GET_ANEXT
                    break;
                case 52: // BEFORE_ASYNC_WITH
                    break;
                case 55: // INPLACE_ADD
                    inplaceAdd();
                    break;
                case 56: // INPLACE_SUBTRACT
                    break;
                case 57: // INPLACE_MULTIPLY
                    break;
                case 59: // INPLACE_MODULO
                    break;
                case 60: // STORE_SUBSCR
                    break;
                case 61: // DELETE_SUBSCR
                    break;
                case 62: // BINARY_LSHIFT
                    break;
                case 63: // BINARY_RSHIFT
                    break;
                case 64: // BINARY_AND
                    binaryAnd();
                    break;
                case 65: // BINARY_XOR
                    break;
                case 66: // BINARY_OR
                    binaryOr();
                    break;
                case 67: // INPLACE_POWER
                    break;
                case 68: // GET_ITER
                    break;
                case 69: // GET_YIELD_FROM_ITER
                    break;
                case 70: // PRINT_EXPR
                    printExpr();
                    break;
                case 71: // LOAD_BUILD_CLASS
                    break;
                case 72: // YIELD_FROM
                    break;
                case 73: // GET_AWAITABLE
                    break;
                case 75: // INPLACE_LSHIFT
                    break;
                case 76: // INPLACE_RSHIFT
                    break;
                case 77: // INPLACE_AND
                    break;
                case 78: // INPLACE_XOR
                    break;
                case 79: // INPLACE_OR
                    break;
                case 80: // BREAK_LOOP
                    breakLoop();
                    break;
                case 81: // WITH_CLEANUP_START
                    break;
                case 82: // WITH_CLEANUP_FINISH
                    break;
                case 83: // RETURN_VALUE
                    returnValue = returnValue();
                    break;
                case 84: // IMPORT_STAR
                    break;
                case 86: // YIELD_VALUE
                    break;
                case 87: // POP_BLOCK
                    popBlock();
                    break;
                case 88: // END_FINALLY
                    break;
                case 89: // POP_EXCEPT
                    break;
                case 90: // STORE_NAME
                    storeName(Utils.twoBytesToInt(code[currentFrame.bytecodeCounter++].byteValue(),
                            code[currentFrame.bytecodeCounter++].byteValue()));
                    break;
                case 91: // DELETE_NAME
                    break;
                case 92: // UNPACK_SEQUENCE
                    break;
                case 93: // FOR_ITER
                    break;
                case 94: // UNPACK_EX
                    break;
                case 95: // STORE_ATTR
                    break;
                case 96: // DELETE_ATTR
                    break;
                case 97: // STORE_GLOBAL
                    break;
                case 98: // DELETE_GLOBAL
                    break;
                case 100: // LOAD_CONST
                    loadConst(Utils.twoBytesToInt(code[currentFrame.bytecodeCounter++].byteValue(),
                            code[currentFrame.bytecodeCounter++].byteValue()));
                    break;
                case 101: // LOAD_NAME
                    loadName(Utils.twoBytesToInt(code[currentFrame.bytecodeCounter++].byteValue(),
                            code[currentFrame.bytecodeCounter++].byteValue()));
                    break;
                case 102: // BUILD_TUPLE
                    break;
                case 103: // BUILD_LIST
                    break;
                case 104: // BUILD_SET
                    break;
                case 105: // BUILD_MAP
                    break;
                case 106: // LOAD_ATTR
                    break;
                case 107: // COMPARE_OP
                    compareOp(Utils.twoBytesToInt(code[currentFrame.bytecodeCounter++].byteValue(),
                            code[currentFrame.bytecodeCounter++].byteValue()));
                    break;
                case 108: // IMPORT_NAME
                    break;
                case 109: // IMPORT_FROM
                    break;
                case 110: // JUMP_FORWARD
                    jumpForward(Utils.twoBytesToInt(code[currentFrame.bytecodeCounter++].byteValue(),
                            code[currentFrame.bytecodeCounter++].byteValue()));
                    break;
                case 111: // JUMP_IF_FALSE_OR_POP
                    jumpIfFalseOrPop(Utils.twoBytesToInt(code[currentFrame.bytecodeCounter++].byteValue(),
                            code[currentFrame.bytecodeCounter++].byteValue()));
                    break;
                case 112: // JUMP_IF_TRUE_OR_POP
                    jumpIfTrueOrPop(Utils.twoBytesToInt(code[currentFrame.bytecodeCounter++].byteValue(),
                            code[currentFrame.bytecodeCounter++].byteValue()));
                    break;
                case 113: // JUMP_ABSOLUTE
                    jumpAbsolute(Utils.twoBytesToInt(code[currentFrame.bytecodeCounter++].byteValue(),
                            code[currentFrame.bytecodeCounter++].byteValue()));
                    break;
                case 114: // POP_JUMP_IF_FALSE
                    popJumpIfFalse(Utils.twoBytesToInt(code[currentFrame.bytecodeCounter++].byteValue(),
                            code[currentFrame.bytecodeCounter++].byteValue()));
                    break;
                case 115: // POP_JUMP_IF_TRUE
                    popJumpIfTrue(Utils.twoBytesToInt(code[currentFrame.bytecodeCounter++].byteValue(),
                            code[currentFrame.bytecodeCounter++].byteValue()));
                    break;
                case 116: // LOAD_GLOBAL
                    break;
                case 119: // CONTINUE_LOOP
                    break;
                case 120: // SETUP_LOOP
                    setupLoop(Utils.twoBytesToInt(code[currentFrame.bytecodeCounter++].byteValue(),
                            code[currentFrame.bytecodeCounter++].byteValue()));
                    break;
                case 121: // SETUP_EXCEPT
                    break;
                case 122: // SETUP_FINALLY
                    break;
                case 124: // LOAD_FAST
                    loadFast(Utils.twoBytesToInt(code[currentFrame.bytecodeCounter++].byteValue(),
                            code[currentFrame.bytecodeCounter++].byteValue()));
                    break;
                case 125: // STORE_FAST
                    storeFast(Utils.twoBytesToInt(code[currentFrame.bytecodeCounter++].byteValue(),
                            code[currentFrame.bytecodeCounter++].byteValue()));
                    break;
                case 126: // DELETE_FAST
                    break;
                case 130: // RAISE_VARARGS
                    break;
                case 131: // CALL_FUNCTION
                    callFunction(Utils.twoBytesToInt(code[currentFrame.bytecodeCounter++].byteValue(),
                            code[currentFrame.bytecodeCounter++].byteValue()));
                    break;
                case 132: // MAKE_FUNCTION
                    makeFunction(Utils.twoBytesToInt(code[currentFrame.bytecodeCounter++].byteValue(),
                            code[currentFrame.bytecodeCounter++].byteValue()));
                    break;
                case 133: // BUILD_SLICE
                    break;
                case 134: // MAKE_CLOSURE
                    break;
                case 135: // LOAD_CLOSURE
                    break;
                case 136: // LOAD_DEREF
                    break;
                case 137: // STORE_DEREF
                    break;
                case 138: // DELETE_DEREF
                    break;
                case 140: // CALL_FUNCTION_VAR
                    break;
                case 141: // CALL_FUNCTION_KW
                    break;
                case 142: // CALL_FUNCTION_VAR_KW
                    break;
                case 143: // SETUP_WITH
                    break;
                case 144: // EXTENDED_ARG
                    break;
                case 145: // LIST_APPEND
                    break;
                case 146: // SET_ADD
                    break;
                case 147: // MAP_ADD
                    break;
                case 148: // LOAD_CLASSDEREF
                    break;
                case 149: // BUILD_LIST_UNPACK
                    break;
                case 150: // BUILD_MAP_UNPACK
                    break;
                case 151: // BUILD_MAP_UNPACK_WITH_CALL
                    break;
                case 152: // BUILD_TUPLE_UNPACK
                    break;
                case 153: // BUILD_SET_UNPACK
                    break;
                case 154: // SETUP_ASYNC_WITH
                    break;
                default:
                    throw new UnsupportedOperationException();
            }
        }

        popFrame();
        return returnValue;
    }

    // Helper methods for working with the currentFrame valueStack
    private Object peek() {
        return currentFrame.valueStack.peek();
    }

    private Object peek(int nToTop) {
        return currentFrame.valueStack.get(currentFrame.valueStack.size() - 1 - nToTop);
    }

    private Object pop() {
        return currentFrame.valueStack.pop();
    }

    private void push(Object obj) {
        currentFrame.valueStack.push(obj);
    }

    private Object[] popN(int n) {
        Object[] objects = new Object[n];
        for (int i = 0; i < n; i++)
            objects[i] = currentFrame.valueStack.pop();
        return objects;
    }

    // Helper methods for working with the currentFrame blockStack
    private void pushBlock(Block.BlockType blockType, int delta) {

        // TODO non è proprio così
        Block block = new Block(blockType, delta);
        currentFrame.blockStack.push(block);
    }

    // Python Bytecode Instructions ####################################################################################
    // General instructions

    /**
     * NOP
     * Do nothing code. Used as a placeholder by the bytecode optimizer.
     */
    private void nop() {
    }

    /**
     * POP_TOP
     * Removes the top-of-stack (TOS) item.
     */
    private void popTop() {
        pop();
    }

    /**
     * ROT_TWO
     * Swaps the two top-most stack items.
     */
    private void rotTwo() {
        Object[] tosN = popN(2);
        push(tosN[0]);
        push(tosN[1]);
    }

    /**
     * ROT_THREE
     * Lifts second and third stack item one position up, moves top down to position three.
     */
    private void rotThree() {
        Object[] tosN = popN(3);
        currentFrame.valueStack.push(tosN[0]);
        currentFrame.valueStack.push(tosN[2]);
        currentFrame.valueStack.push(tosN[1]);
    }

    /**
     * DUP_TOP
     * Duplicates the reference on top of the stack.
     */
    private void dupTop() {
        push(peek());
    }

    /**
     * DUP_TOP_TWO
     * Duplicates the two references on top of the stack, leaving them in the same order.
     */
    private void dupTopTwo() {
        Object tos = peek();
        Object tos1 = peek(1);
        push(tos1);
        push(tos);
    }

    // Unary operations
    // Unary operations take the top of the stack, apply the operation, and push the result back on the stack.

    /**
     * UNARY_POSITIVE
     * Implements TOS = +TOS.
     */
    private void unaryPositive() {
        Object tos = pop();
        if (tos instanceof Long) {
            push(+(Long) tos);
        } else if (tos instanceof Double) {
            push(+(Double) tos);
        } else {
            throw new UnsupportedOperationException();
        }
    }

    /**
     * NEGATIVE
     * Implements TOS = -TOS.
     */
    private void unaryNegative() {
        Object tos = pop();
        if (tos instanceof Long) {
            push(-(Long) tos);
        } else if (tos instanceof Double) {
            push(-(Double) tos);
        } else {
            throw new UnsupportedOperationException();
        }
    }

    /**
     * UNARY_NOT
     * Implements TOS = not TOS.
     */
    private void unaryNot() {
        Object tos = pop();
        if (tos instanceof Boolean) {
            push(!(Boolean) tos);
        } else if (tos == null) {
            push(Boolean.TRUE);
        } else {
            push(Boolean.FALSE);
        }
    }

    /**
     * UNARY_INVERT
     * Implements TOS = ~TOS.
     */
    private void unaryInvert() {
        Object tos = pop();
        if (tos instanceof Integer)
            push(~(Integer) tos);
        else
            throw new UnsupportedOperationException();
    }

    /**
     * GET_ITER
     * Implements TOS = iter(TOS).
     */

    /**
     * GET_YIELD_FROM_ITER
     * If TOS is a generator iterator or coroutine object it is left as is. Otherwise, implements TOS = iter(TOS).
     */

    // Binary operations
    // Binary operations remove the top of the stack (TOS) and the second top-most stack item (TOS1) from the stack.
    // They perform the operation, and put the result back on the stack.

    /**
     * BINARY_POWER
     * Implements TOS = TOS1 ** TOS.
     */
    private void binaryPower() {
        Object tos = pop();
        Object tos1 = pop();

        if (!(tos instanceof Long && tos1 instanceof Number))
            throw new UnsupportedOperationException();

        if (tos1 instanceof Long) {
            push(Math.pow((Long) tos1, (Long) tos));
        } else if (tos1 instanceof Double) {
            push(Math.pow((Double) tos1, (Long) tos));
        } else {
            throw new UnsupportedOperationException();
        }
    }

    /**
     * BINARY_MULTIPLY
     * Implements TOS = TOS1 * TOS.
     */
    private void binaryMultiply() {
        Object tos = pop();
        Object tos1 = pop();

        if (!(tos instanceof Number && tos1 instanceof Number))
            throw new UnsupportedOperationException();

        if (tos instanceof Double || tos1 instanceof Double) {
            push(((Number) tos).doubleValue() * ((Number) tos1).doubleValue());
        } else {
            push(((Number) tos).longValue() * ((Number) tos1).longValue());
        }
    }

    /**
     * BINARY_MATRIX_MULTIPLY
     * Implements TOS = TOS1 @ TOS.
     */

    /**
     * BINARY_FLOOR_DIVIDE
     * Implements TOS = TOS1 // TOS.
     */

    /**
     * BINARY_TRUE_DIVIDE
     * Implements TOS = TOS1 / TOS.
     */
    private void binaryTrueDivide() {
        Object tos = pop();
        Object tos1 = pop();

        if (!(tos instanceof Number && tos1 instanceof Number))
            throw new UnsupportedOperationException();

        push(((Number) tos1).doubleValue() / ((Number) tos).doubleValue());
    }

    /**
     * BINARY_MODULO
     * Implements TOS = TOS1 % TOS.
     */
    private void binaryModulo() {
        Object tos = pop();
        Object tos1 = pop();

        if (!(tos instanceof Number && tos1 instanceof Number))
            throw new UnsupportedOperationException();

        if (tos instanceof Long && tos1 instanceof Long)
            push(((Number) tos1).longValue() % ((Number) tos).longValue());
        else
            push(((Number) tos1).doubleValue() % ((Number) tos).doubleValue());
    }

    /**
     * BINARY_ADD
     * Implements TOS = TOS1 + TOS.
     */
    private void binaryAdd() {
        Object tos = pop();
        Object tos1 = pop();

        if (tos instanceof Long && tos1 instanceof Long)
            push(((Number) tos1).longValue() + ((Number) tos).longValue());
        else if (tos instanceof Number && tos1 instanceof Number)
            push(((Number) tos1).doubleValue() + ((Number) tos).doubleValue());
        else if ((tos1 instanceof String || tos instanceof String) ||
                (tos1 instanceof Character && tos instanceof Character))
            push(String.valueOf(tos1) + String.valueOf(tos));
        else
            throw new UnsupportedOperationException();
    }

    /**
     * BINARY_SUBTRACT
     * Implements TOS = TOS1 - TOS.
     */
    private void binarySubtract() {
        Object tos = pop();
        Object tos1 = pop();

        if (!(tos instanceof Number && tos1 instanceof Number))
            throw new UnsupportedOperationException();

        if (tos instanceof Long && tos1 instanceof Long)
            push(((Number) tos1).longValue() - ((Number) tos).longValue());
        else
            push(((Number) tos1).doubleValue() - ((Number) tos).doubleValue());
    }

    /**
     * BINARY_SUBSCR
     * Implements TOS = TOS1[TOS].
     */

    /**
     * BINARY_LSHIFT
     * Implements TOS = TOS1 << TOS.
     */

    /**
     * BINARY_RSHIFT
     * Implements TOS = TOS1 >> TOS.
     */

    /**
     * BINARY_AND
     * Implements TOS = TOS1 & TOS.
     */
    private void binaryAnd() {
        Object tos = pop();
        Object tos1 = pop();

        if (tos instanceof Boolean && tos1 instanceof Boolean) {
            push((Boolean) tos && (Boolean) tos1);
        } else if (tos != null && tos1 != null) {
            push(tos);
        } else {
            push(null);
        }
    }

    /**
     * BINARY_XOR
     * Implements TOS = TOS1 ^ TOS.
     */

    /**
     * BINARY_OR
     * Implements TOS = TOS1 | TOS.
     */
    private void binaryOr() {
        Object tos = pop();
        Object tos1 = pop();

        if (tos instanceof Boolean && tos1 instanceof Boolean) {
            push((Boolean) tos || (Boolean) tos1);
        } else if (tos1 != null) {
            push(tos1);
        } else {
            push(tos);
        }
    }

    // In-place operations
    // In-place operations are like binary operations, in that they remove TOS and TOS1, and push the result back on
    // the stack, but the operation is done in-place when TOS1 supports it, and the resulting TOS may be
    // (but does not have to be) the original TOS1.
    // TODO In-place operations

    /**
     * INPLACE_ADD
     * Implements in-place TOS = TOS1 + TOS.
     */
    private void inplaceAdd() {
        binaryAdd();
    }

    // Coroutine opcodes
    // TODO Coroutine opcodes

    // Miscellaneous opcodes

    /**
     * PRINT_EXPR
     * Implements the expression statement for the interactive mode. TOS is removed from the stack and printed.
     * In non-interactive mode, an expression statement is terminated with POP_TOP.
     */
    private void printExpr() {
        System.out.println(pop());
    }

    /**
     * BREAK_LOOP
     * Terminates a loop due to a break statement.
     */
    private void breakLoop() {
        Block block = popBlock();
        currentFrame.bytecodeCounter = block.dest;
    }

    /**
     * CONTINUE_LOOP(target)
     * Continues a loop due to a continue statement. target is the address to jump to (which should be a FOR_ITER
     * instruction).
     */

    /**
     * SET_ADD(i)
     * Calls set.add(TOS1[-i], TOS). Used to implement set comprehensions.
     */

    /**
     * LIST_APPEND(i)
     * Calls list.append(TOS[-i], TOS). Used to implement list comprehensions.
     */

    /**
     * MAP_ADD(i)
     * Calls dict.setitem(TOS1[-i], TOS, TOS1). Used to implement dict comprehensions.
     */

    // For all of the SET_ADD, LIST_APPEND and MAP_ADD instructions, while the added value or key/value pair is popped
    // off, the container object remains on the stack so that it is available for further iterations of the loop.

    /**
     * RETURN_VALUE
     * Returns with TOS to the caller of the function.
     */
    private Object returnValue() {
        return pop();
    }

    /**
     * YIELD_VALUE
     * Pops TOS and yields it from a generator.
     */

    /**
     * YIELD_FROM
     * Pops TOS and delegates to it as a subiterator from a generator.
     */

    /**
     * IMPORT_STAR
     * Loads all symbols not starting with '_' directly from the module TOS to the local namespace.
     * The module is popped after loading all names. This opcode implements from module import *.
     */

    /**
     * POP_BLOCK
     * Removes one block from the block stack. Per frame, there is a stack of blocks, denoting nested loops, try
     * statements, and such.
     */
    private Block popBlock() {
        return currentFrame.blockStack.pop();
    }

    /**
     * POP_EXCEPT
     * Removes one block from the block stack. The popped block must be an exception handler block, as implicitly
     * created when entering an except handler. In addition to popping extraneous values from the frame stack, the last
     * three popped values are used to restore the exception state.
     */

    /**
     * END_FINALLY
     * Terminates a finally clause. The interpreter recalls whether the exception has to be re-raised, or whether the
     * function returns, and continues with the outer-next block.
     */

    /**
     * LOAD_BUILD_CLASS
     * Pushes builtins.__build_class__() onto the stack. It is later called by CALL_FUNCTION to construct a class.
     */

    /**
     * SETUP_WITH(delta)
     * This opcode performs several operations before a with block starts. First, it loads __exit__() from the context
     * manager and pushes it onto the stack for later use by WITH_CLEANUP.
     * Then, __enter__() is called, and a finally block pointing to delta is pushed. Finally, the result of calling the
     * enter method is pushed onto the stack. The next opcode will either ignore it (POP_TOP), or store it in (a)
     * variable(s) (STORE_FAST, STORE_NAME, or UNPACK_SEQUENCE).
     */

    /**
     * WITH_CLEANUP_START
     * Cleans up the stack when a with statement block exits. TOS is the context manager’s __exit__() bound method.
     * Below TOS are 1–3 values indicating how/why the finally clause was entered:
     *      SECOND = None
     *      (SECOND, THIRD) = (WHY_{RETURN,CONTINUE}), retval
     *      SECOND = WHY_*; no retval below it
     *      (SECOND, THIRD, FOURTH) = exc_info()
     * In the last case, TOS(SECOND, THIRD, FOURTH) is called, otherwise TOS(None, None, None). Pushes SECOND and result
     * of the call to the stack.
     */

    /**
     * WITH_CLEANUP_FINISH
     * Pops exception type and result of ‘exit’ function call from the stack.
     * If the stack represents an exception, and the function call returns a ‘true’ value, this information is “zapped”
     * and replaced with a single WHY_SILENCED to prevent END_FINALLY from re-raising the exception.
     * (But non-local gotos will still be resumed.)
     */

    // All of the following opcodes expect arguments. An argument is two bytes, with the more significant byte last.

    /**
     * STORE_NAME(namei)
     * Implements name = TOS. namei is the index of name in the attribute co_names of the code object.
     * The compiler tries to use STORE_FAST or STORE_GLOBAL if possible.
     */
    private void storeName(int namei) {
        currentFrame.setVariable(currentFrame.pyCodeObject.getName(namei), pop());
    }

    /**
     * DELETE_NAME(namei)
     * Implements del name, where namei is the index into co_names attribute of the code object.
     */

    /**
     * UNPACK_SEQUENCE(count)
     * Unpacks TOS into count individual values, which are put onto the stack right-to-left.
     */

    /**
     * UNPACK_EX(counts)
     * Implements assignment with a starred target: Unpacks an iterable in TOS into individual values,
     * where the total number of values can be smaller than the number of items in the iterable: one the new values
     * will be a list of all leftover items.
     *
     * The low byte of counts is the number of values before the list value, the high byte of counts the number of
     * values after it. The resulting values are put onto the stack right-to-left.
     */

    /**
     * STORE_ATTR(namei)
     * Implements TOS.name = TOS1, where namei is the index of name in co_names.
     */

    /**
     * DELETE_ATTR(namei)
     * Implements del TOS.name, using namei as index into co_names.
     */

    /**
     * STORE_GLOBAL(namei)
     * Works as STORE_NAME, but stores the name as a global.
     */

    /**
     * DELETE_GLOBAL(namei)
     * Works as DELETE_NAME, but deletes a global name.
     */

    /**
     * LOAD_CONST(consti)
     * Pushes co_consts[consti] onto the stack.
     */
    private void loadConst(int consti) {
        push(currentFrame.pyCodeObject.getConst(consti));
    }

    /**
     * LOAD_NAME(namei)
     * Pushes the value associated with co_names[namei] onto the stack.
     */
    private void loadName(int namei) throws Exception {
        String name = currentFrame.pyCodeObject.getName(namei);
        if (currentFrame.isInLocals(name))
            push(currentFrame.getVariableByName(name));
            // globals
        else if (Builtins.isBuiltin(name))
            push(name);
        else
            throw new Exception("name " + currentFrame.pyCodeObject.getName(namei) + " is not defined");

    }

    /**
     * BUILD_TUPLE(count)
     * Creates a tuple consuming count items from the stack, and pushes the resulting tuple onto the stack.
     */

    /**
     * BUILD_LIST(count)
     * Works as BUILD_TUPLE, but creates a list.
     */

    /**
     * BUILD_SET(count)
     * Works as BUILD_TUPLE, but creates a set.
     */

    /**
     * BUILD_MAP(count)
     * Pushes a new dictionary object onto the stack. The dictionary is pre-sized to hold count entries.
     */

    /**
     * LOAD_ATTR(namei)
     * Replaces TOS with getattr(TOS, co_names[namei]).
     */

    /**
     * COMPARE_OP(opname)
     * Performs a Boolean operation. The operation name can be found in cmp_op[opname].
     */
    @SuppressWarnings("unchecked")
    private void compareOp(int opname) {
        Object tos = pop();
        Object tos1 = pop();

        if (!(tos1 instanceof Comparable && tos instanceof Comparable))
            throw new UnsupportedOperationException();

        switch (opname) {
            case EQ:
                push(tos1.equals(tos));
                break;
            case NE:
                push(!tos1.equals(tos));
                break;
            case GT:
                push(((Comparable) tos1).compareTo(tos) > 0);
                break;
            case GE:
                push(((Comparable) tos1).compareTo(tos) >= 0);
                break;
            case LT:
                push(((Comparable) tos1).compareTo(tos) < 0);
                break;
            case LE:
                push(((Comparable) tos1).compareTo(tos) <= 0);
                break;
            default:
                throw new UnsupportedOperationException();
        }
    }

    /**
     * IMPORT_NAME(namei)
     * Imports the module co_names[namei]. TOS and TOS1 are popped and provide the fromlist and level arguments of
     * __import__().
     * The module object is pushed onto the stack. The current namespace is not affected: for a proper import statement,
     * a subsequent STORE_FAST instruction modifies the namespace.
     */

    /**
     * IMPORT_FROM(namei)
     * Loads the attribute co_names[namei] from the module found in TOS.
     * The resulting object is pushed onto the stack, to be subsequently stored by a STORE_FAST instruction.
     */

    /**
     * JUMP_FORWARD(delta)
     * Increments bytecode counter by delta.
     */
    private void jumpForward(int delta) {
        currentFrame.bytecodeCounter += delta;
    }

    /**
     * POP_JUMP_IF_TRUE(target)
     * If TOS is true, sets the bytecode counter to target. TOS is popped.
     */
    private void popJumpIfTrue(int target) {
        Object tos = pop();

        if (!(tos instanceof Boolean))
            throw new UnsupportedOperationException();

        if ((boolean) tos)
            currentFrame.bytecodeCounter = target;
    }

    /**
     * POP_JUMP_IF_FALSE(target)
     * If TOS is false, sets the bytecode counter to target. TOS is popped.
     */
    private void popJumpIfFalse(int target) {
        Object tos = pop();

        if (!(tos instanceof Boolean))
            throw new UnsupportedOperationException();

        if (!(boolean) tos)
            currentFrame.bytecodeCounter = target;
    }

    /**
     * JUMP_IF_TRUE_OR_POP(target)
     * If TOS is true, sets the bytecode counter to target and leaves TOS on the stack. Otherwise (TOS is false),
     * TOS is popped.
     */
    private void jumpIfTrueOrPop(int target) {
        Object tos = peek();

        if (!(tos instanceof Boolean))
            throw new UnsupportedOperationException();

        if ((boolean) tos)
            currentFrame.bytecodeCounter = target;
        else
            pop();
    }

    /**
     * JUMP_IF_FALSE_OR_POP(target)
     * If TOS is false, sets the bytecode counter to target and leaves TOS on the stack. Otherwise (TOS is true),
     * TOS is popped.
     */
    private void jumpIfFalseOrPop(int target) {
        Object tos = peek();

        if (!(tos instanceof Boolean))
            throw new UnsupportedOperationException();

        if ((boolean) tos)
            pop();
        else
            currentFrame.bytecodeCounter = target;
    }

    /**
     * JUMP_ABSOLUTE(target)
     * Set bytecode counter to target.
     */
    private void jumpAbsolute(int target) {
        currentFrame.bytecodeCounter = target;
    }

    /**
     * FOR_ITER(delta)
     * TOS is an iterator. Call its __next__() method. If this yields a new value, push it on the stack (leaving the
     * iterator below it). If the iterator indicates it is exhausted TOS is popped, and the byte code counter is
     * incremented by delta.
     */

    /**
     * LOAD_GLOBAL(namei)
     * Loads the global named co_names[namei] onto the stack.
     */

    /**
     * SETUP_LOOP(delta)
     * Pushes a block for a loop onto the block stack. The block spans from the current instruction with a size of
     * delta bytes.
     */
    private void setupLoop(int delta) {
        pushBlock(Block.BlockType.LOOP, currentFrame.bytecodeCounter + delta);
    }

    /**
     * SETUP_EXCEPT(delta)
     * Pushes a try block from a try-except clause onto the block stack. delta points to the first except block.
     */

    /**
     * SETUP_FINALLY(delta)
     * Pushes a try block from a try-except clause onto the block stack. delta points to the finally block.
     */

    /**
     * LOAD_FAST(var_num)
     * Pushes a reference to the local co_varnames[var_num] onto the stack.
     */
    private void loadFast(int varNum) {
        push(currentFrame.getVariableByName(currentFrame.pyCodeObject.getVarName(varNum)));
    }

    /**
     * STORE_FAST(var_num)
     * Stores TOS into the local co_varnames[var_num].
     */
    private void storeFast(int varNum) {
        currentFrame.setVariable(currentFrame.pyCodeObject.getVarName(varNum), pop());
    }

    /**
     * DELETE_FAST(var_num)
     * Deletes local co_varnames[var_num].
     */

    /**
     * LOAD_CLOSURE(i)
     * Pushes a reference to the cell contained in slot i of the cell and free variable storage.
     * The name of the variable is co_cellvars[i] if i is less than the length of co_cellvars. Otherwise it is
     * co_freevars[i - len(co_cellvars)].
     */

    /**
     * LOAD_DEREF(i)
     * Loads the cell contained in slot i of the cell and free variable storage. Pushes a reference to the object the
     * cell contains on the stack.
     */

    /**
     * LOAD_CLASSDEREF(i)
     * Much like LOAD_DEREF but first checks the locals dictionary before consulting the cell. This is used for loading
     * free variables in class bodies.
     */

    /**
     * STORE_DEREF(i)
     * Stores TOS into the cell contained in slot i of the cell and free variable storage.
     */

    /**
     * DELETE_DEREF(i)
     * Empties the cell contained in slot i of the cell and free variable storage. Used by the del statement.
     */

    /**
     * RAISE_VARARGS(argc)
     * Raises an exception. argc indicates the number of parameters to the raise statement, ranging from 0 to 3.
     * The handler will find the traceback as TOS2, the parameter as TOS1, and the exception as TOS.
     */

    /**
     * CALL_FUNCTION(argc)
     * Calls a function. The low byte of argc indicates the number of positional parameters, the high byte the number
     * of keyword parameters.
     * On the stack, the opcode finds the keyword parameters first. For each keyword argument, the value is on top of
     * the key.
     * Below the keyword parameters, the positional parameters are on the stack, with the right-most parameter on top.
     * Below the parameters, the function object to call is on the stack. Pops all function arguments,
     * and the function itself off the stack, and pushes the return value.
     */
    private void callFunction(int argc) throws Exception {
        int nPosParams = argc & 0xFF;
        int keywordParams = (argc >> 8) & 0xFF;

        popN(keywordParams * 2); // TODO
        Object[] posParams = popN(nPosParams);

        Object funObject = pop();
        if (funObject instanceof Function) {
            push(((Function) funObject).call(posParams));
        } else if (Builtins.isBuiltin((String) funObject)) {
            switch ((String) funObject) {
                case "print":
                    Builtins.print(posParams[0]);
                    push(null);
                    break;
            }
        }
    }

    /**
     * MAKE_FUNCTION(argc)
     * Pushes a new function object on the stack. From bottom to top, the consumed stack must consist of
     * argc & 0xFF default argument objects in positional order
     * (argc >> 8) & 0xFF pairs of name and default argument, with the name just below the object on the stack,
     * for keyword-only parameters
     * (argc >> 16) & 0x7FFF parameter annotation objects
     * a tuple listing the parameter names for the annotations (only if there are ony annotation objects)
     * the code associated with the function (at TOS1)
     * the qualified name of the function (at TOS)
     */
    private void makeFunction(int argc) {
        int nDefaultArgumentObjects = argc & 0xFF;
        int nPairsNameDefaultArgument = (argc >> 8) & 0xFF;
        int nParameterAnnotationObjects = (argc >> 16) & 0x7FFF;

        String functionName = (String) pop();
        PyCodeObject pyCodeObject = (PyCodeObject) pop();
        if (nParameterAnnotationObjects > 0)
            pop(); // TODO
        popN(nParameterAnnotationObjects); // TODO
        popN(nPairsNameDefaultArgument * 2); // TODO
        Object[] defaults = popN(nDefaultArgumentObjects);
        Function function = new Function(this, functionName, pyCodeObject, defaults);
        push(function);
    }

    /**
     * MAKE_CLOSURE(argc)
     * Creates a new function object, sets its __closure__ slot, and pushes it on the stack.
     * TOS is the qualified name of the function, TOS1 is the code associated with the function,
     * and TOS2 is the tuple containing cells for the closure’s free variables.
     * argc is interpreted as in MAKE_FUNCTION; the annotations and defaults are also in the same order below TOS2.
     */

    /**
     * BUILD_SLICE(argc)
     * Pushes a slice object on the stack. argc must be 2 or 3. If it is 2, slice(TOS1, TOS) is pushed; if it is 3,
     * slice(TOS2, TOS1, TOS) is pushed. See the slice() built-in function for more information.
     */

    /**
     * EXTENDED_ARG(ext)
     * Prefixes any opcode which has an argument too big to fit into the default two bytes. ext holds two additional
     * bytes which, taken together with the subsequent opcode’s argument, comprise a four-byte argument, ext being the
     * two most-significant bytes.
     */

    /**
     * CALL_FUNCTION_VAR(argc)
     * Calls a function. argc is interpreted as in CALL_FUNCTION. The top element on the stack contains the variable
     * argument list, followed by keyword and positional arguments.
     */

    /**
     * CALL_FUNCTION_KW(argc)
     * Calls a function. argc is interpreted as in CALL_FUNCTION. The top element on the stack contains the keyword
     * arguments dictionary, followed by explicit keyword and positional arguments.
     */

    /**
     * CALL_FUNCTION_VAR_KW(argc)
     * Calls a function. argc is interpreted as in CALL_FUNCTION. The top element on the stack contains the keyword
     * arguments dictionary, followed by the variable-arguments tuple, followed by explicit keyword and positional
     * arguments.
     */

    /**
     * HAVE_ARGUMENT
     * This is not really an opcode.
     * It identifies the dividing line between opcodes which don’t take arguments < HAVE_ARGUMENT and those which do
     * >= HAVE_ARGUMENT.
     */

    // #################################################################################################################
}
