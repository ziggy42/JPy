package com.andrea.vm;


public class Block {
    public enum BlockType {
        LOOP,
    }

    public BlockType blockType;
    public int dest;

    public Block(BlockType blockType, int dest) {
        this.blockType = blockType;
        this.dest = dest;
    }
}
