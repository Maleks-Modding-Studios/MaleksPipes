package net.malek.pipes.test;

import net.malek.pipes.MaleksPipes;
import net.malek.pipes.PipeNetwork;
import net.malek.pipes.PipeNetworkConsumerBlockEntity;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.HashSet;

public class TestConsumerBlockEntity extends BlockEntity implements PipeNetworkConsumerBlockEntity {
    HashSet<BlockPos> producerPositions = new HashSet<>();
    boolean isPipeNetworkDirty = false;
    public TestConsumerBlockEntity(BlockPos pos, BlockState state) {
        super(MaleksPipes.TEST_CONSUMER_BLOCK_ENTITY, pos, state);
    }
    public static <T extends BlockEntity> void tick(World world, BlockPos blockPos, BlockState state, T t) {
        if(world.isClient())
            return;
        ((TestConsumerBlockEntity)t).tick(world, blockPos, state);
    }

    private void tick(World world, BlockPos blockPos, BlockState state) {
        this.runAtTick(producerPositions, world, blockPos);
        for(BlockPos pos1 : producerPositions) {
            System.out.println(pos1 + " : " + world.getBlockState(pos1));
        }
    }

    @Override
    public void markPipeNetworkDirty() {
        isPipeNetworkDirty = true;
    }

    @Override
    public void resetPipeNetworkDirty() {
        isPipeNetworkDirty = false;
    }

    @Override
    public boolean isPipeNetworkDirty() {
        return isPipeNetworkDirty;
    }


    @Override
    public PipeNetwork getPipeNetwork() {
        return MaleksPipes.TEST_NETWORK;
    }
}
