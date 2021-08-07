package net.malek.pipes;

import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.HashSet;

/**
 * Implement this interface on all things you wish to have the capability to receive something
 */
public interface PipeNetworkConsumerBlockEntity {
    void markPipeNetworkDirty();
    void resetPipeNetworkDirty();
    boolean isPipeNetworkDirty();

    BlockPos getBlockPos();
    World getWorld();

    default void runAtTick(HashSet<BlockPos> producerPositions) {
        //If the network is dirty we re-find all the paths we can make towards the producers
        if(isPipeNetworkDirty()) {
            resetPipeNetworkDirty();
            producerPositions.clear();
            HashSet<BlockPos> previousPositions = new HashSet<>();
            producerPositions = PipeNetwork.findPath(getWorld(), getBlockPos(), producerPositions, previousPositions, this::doAtPipe, this::doAtEndpoint, getPipeNetwork()::isPipeOrProducer, getPipeNetwork()::isSideProducer, 0);
        }
    }

    /**
     * override this if you want some extra behaviour to occur when you find the next pipe.
     * For example, if you wanted to track the amount of pipes you have you could have an integer tracking the number of pipes you have encountered
     * and caluatle efficeny based on that.
     */
    default void doAtPipe(BlockPos pos) {

    }
    /**
     *  override this if you want some extra behaviour to occur when you find a producer.
     */
    default void doAtEndpoint(BlockPos pos) {

    }

    PipeNetwork getPipeNetwork();
}
