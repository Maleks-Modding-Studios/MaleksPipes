package net.malek.pipes.test;

import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.util.math.BlockPos;

import static net.malek.pipes.MaleksPipes.TEST_PIPE_BLOCK_ENTITY;

public class TestPipeBlockEntity extends BlockEntity {

    public TestPipeBlockEntity(BlockPos pos, BlockState state) {
        super(TEST_PIPE_BLOCK_ENTITY, pos, state);
    }
}