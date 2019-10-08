package com.colinparrott.parallelsimulator.engine.instructions;

/**
 * List of keywords for each instruction
 */

public enum InstructionKeyword {
    LD, ST, LDI, ADD, ADDI, SUB, SUBI, MUL, MULI, DIV, DIVI, BEQ, BNE, BGT, BLT, BGE, BLE, JUMP, LABEL, ATOMIC, ENDATOMIC, AWAIT,
    SEQ, SNE, SLT, SGT, SGE, SLE;

    public static ParameterType[] getExpectedParams(InstructionKeyword keyword) {
        switch (keyword) {
            case LD:
                return new ParameterType[]{ParameterType.REGISTER, ParameterType.MEMORY_LOCATION};
            case ST:
                return new ParameterType[]{ParameterType.REGISTER, ParameterType.MEMORY_LOCATION};
            case LDI:
                return new ParameterType[]{ParameterType.REGISTER, ParameterType.CONSTANT};
            case ADD:
                return new ParameterType[]{ParameterType.REGISTER, ParameterType.REGISTER, ParameterType.REGISTER};
            case ADDI:
                return new ParameterType[]{ParameterType.REGISTER, ParameterType.REGISTER, ParameterType.CONSTANT};
            case SUB:
                return new ParameterType[]{ParameterType.REGISTER, ParameterType.REGISTER, ParameterType.REGISTER};
            case SUBI:
                return new ParameterType[]{ParameterType.REGISTER, ParameterType.REGISTER, ParameterType.CONSTANT};
            case MUL:
                return new ParameterType[]{ParameterType.REGISTER, ParameterType.REGISTER, ParameterType.REGISTER};
            case MULI:
                return new ParameterType[]{ParameterType.REGISTER, ParameterType.REGISTER, ParameterType.CONSTANT};
            case DIV:
                return new ParameterType[]{ParameterType.REGISTER, ParameterType.REGISTER, ParameterType.REGISTER};
            case DIVI:
                return new ParameterType[]{ParameterType.REGISTER, ParameterType.REGISTER, ParameterType.CONSTANT};
            case BEQ:
                return new ParameterType[]{ParameterType.REGISTER, ParameterType.REGISTER, ParameterType.LABEL_STRING};
            case BNE:
                return new ParameterType[]{ParameterType.REGISTER, ParameterType.REGISTER, ParameterType.LABEL_STRING};
            case BGT:
                return new ParameterType[]{ParameterType.REGISTER, ParameterType.REGISTER, ParameterType.LABEL_STRING};
            case BLT:
                return new ParameterType[]{ParameterType.REGISTER, ParameterType.REGISTER, ParameterType.LABEL_STRING};
            case BGE:
                return new ParameterType[]{ParameterType.REGISTER, ParameterType.REGISTER, ParameterType.LABEL_STRING};
            case BLE:
                return new ParameterType[]{ParameterType.REGISTER, ParameterType.REGISTER, ParameterType.LABEL_STRING};
            case SEQ:
                return new ParameterType[]{ParameterType.REGISTER, ParameterType.REGISTER, ParameterType.REGISTER};
            case SNE:
                return new ParameterType[]{ParameterType.REGISTER, ParameterType.REGISTER, ParameterType.REGISTER};
            case SLT:
                return new ParameterType[]{ParameterType.REGISTER, ParameterType.REGISTER, ParameterType.REGISTER};
            case SGT:
                return new ParameterType[]{ParameterType.REGISTER, ParameterType.REGISTER, ParameterType.REGISTER};
            case SLE:
                return new ParameterType[]{ParameterType.REGISTER, ParameterType.REGISTER, ParameterType.REGISTER};
            case SGE:
                return new ParameterType[]{ParameterType.REGISTER, ParameterType.REGISTER, ParameterType.REGISTER};
            case JUMP:
                return new ParameterType[]{ParameterType.LABEL_STRING};
            case LABEL:
                return new ParameterType[0];
            case ATOMIC:
                return new ParameterType[0];
            case ENDATOMIC:
                return new ParameterType[0];
            case AWAIT:
                return new ParameterType[]{ParameterType.MEMORY_LOCATION, ParameterType.COMPARATOR, ParameterType.MEMORY_LOCATION};
            default:
                return null;
        }
    }

}


