package net.malek.pipes.test;

import net.malek.pipes.MaleksPipes;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class TestProducerBlockEntity extends BlockEntity {
    public TestProducerBlockEntity(BlockPos pos, BlockState state) {
        super(MaleksPipes.TEST_PRODUCER_BLOCK_ENTITY, pos, state);
    }
    public static <T extends BlockEntity> void tick(World world, BlockPos blockPos, BlockState state, T t) {
        if(world.isClient())
            return;
        ((TestProducerBlockEntity)t).tick(world, blockPos, state);
    }

    private void tick(World world, BlockPos blockPos, BlockState state) {
    }
}
