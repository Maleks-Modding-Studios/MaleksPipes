package net.malek.pipes;

import net.minecraft.block.BlockState;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;


import java.util.HashSet;

import static net.malek.pipes.MaleksPipes.*;
import static net.malek.pipes.SideType.*;

public interface NetworkBlock {
    default BlockState setSides(BlockState state) {
        return setSides(state, NONE);
    }
    default BlockState setSides(BlockState state, SideType type) {
        return state.with(SIDE_NORTH, type).with(SIDE_SOUTH, type).with(SIDE_EAST, type).with(SIDE_WEST, type).with(SIDE_UP, type).with(SIDE_DOWN, type);
    }
    /**
     * This function should be called whenever a block is broken or placed or moved that is a network block
     */
    default void findAndMarkConsumersAsDirty(ServerWorld world, BlockPos pos) {
        HashSet<BlockPos> consumerPositions = new HashSet<>();
        HashSet<BlockPos> previousPos = new HashSet<>();
        PipeNetwork.findPath(world, pos, consumerPositions, previousPos, (blockPos1) -> {
            //We look through the whole network recursivly, and every network consumer we see, we tell it to mark itself dirty.
            ((PipeNetworkConsumerBlockEntity)world.getBlockEntity(blockPos1)).markPipeNetworkDirty();
        }, (pos1) -> {;}, getPipeNetwork()::isPipeOrConsumer, getPipeNetwork()::isSideConsumer, 0);
    }

    PipeNetwork getPipeNetwork();

}
