package com.andrea.vm;


class Block {
    enum BlockType {
        LOOP,
    }

    BlockType blockType;
    int dest;

    Block(BlockType blockType, int dest) {
        this.blockType = blockType;
        this.dest = dest;
    }
}
