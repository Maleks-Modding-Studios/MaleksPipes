package net.malek.pipes;

import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;

import java.util.HashSet;
import java.util.Optional;
import java.util.function.BiConsumer;
import java.util.function.BiPredicate;
import java.util.function.Consumer;

public class PipeNetwork {
    //Is recommended you use Tags to determine if a block is a producer, consumer, and/or pipe, but not required.
    final BiPredicate<World, BlockPos> isProducer;
    final BiPredicate<World, BlockPos> isConsumer;
    final BiPredicate<World, BlockPos> isPipe;

    public static final int MAX_RECURSION_DEPTH = 3000;

    public PipeNetwork(BiPredicate<World, BlockPos> isProducer, BiPredicate<World, BlockPos> isConsumer, BiPredicate<World, BlockPos> isPipe) {
        this.isProducer = isProducer;
        this.isConsumer = isConsumer;
        this.isPipe = isPipe;
    }

    public boolean isPipeOrProducer(World world, BlockPos pos) {
        return isProducer.test(world, pos) || isPipe.test(world, pos);
    }
    public boolean isPipeOrConsumer(World world, BlockPos pos) {
        return isConsumer.test(world, pos) || isPipe.test(world, pos);
    }

    public boolean isSideConsumer(World world, BlockPos currentPos, BlockPos previousPos) {
        Direction direction = Direction.fromVector(previousPos.subtract(currentPos));
        return isConsumer.test(world, currentPos) && world.getBlockState(currentPos).get(MaleksPipes.getSideTypeFromDirection(direction)).equals(SideType.CONSUMER);
    }
    public boolean isSideProducer(World world, BlockPos currentPos, BlockPos previousPos) {
        Direction direction = Direction.fromVector(previousPos.subtract(currentPos));
        return isProducer.test(world, currentPos) && world.getBlockState(currentPos).get(MaleksPipes.getSideTypeFromDirection(direction)).equals(SideType.PRODUCER);
    }

    //Some Misc Methods
    static Optional<BlockPos>[] getAroundTarget(World world, BlockPos pos, BiPredicate<World, BlockPos> predicate) {
        Optional<BlockPos>[] positions = new Optional[6];
        positions[0] = getIfMatches(world, pos.add(1, 0, 0), predicate);
        positions[1] = getIfMatches(world, pos.add(0, 1, 0), predicate);
        positions[2] = getIfMatches(world, pos.add(0, 0, 1), predicate);
        positions[3] = getIfMatches(world, pos.add(-1, 0, 0), predicate);
        positions[4] = getIfMatches(world, pos.add(0, -1, 0), predicate);
        positions[5] = getIfMatches(world, pos.add(0, 0, -1), predicate);
        return positions;
    }
    static Optional<BlockPos> getIfMatches(World world, BlockPos pos, BiPredicate<World, BlockPos> predicate) {
        if (predicate.test(world, pos)) {
            return Optional.of(pos);
        }
        return Optional.empty();
    }
    public static HashSet<BlockPos> findPath(World world,
                                             BlockPos previousPos,
                                             HashSet<BlockPos> paths,
                                             HashSet<BlockPos> previousPositions,
                                             Consumer<BlockPos> doAtEndPoint,
                                             Consumer<BlockPos> doAtPipe,
                                             BiPredicate<World, BlockPos> isPipeOrEndpoint,
                                             TriPredicate<World, BlockPos, BlockPos> isEndpoint,
                                             int recursionDepth) {
        Optional<BlockPos>[] optionals = getAroundTarget(world, previousPos, isPipeOrEndpoint);
        for (Optional<BlockPos> optional : optionals) {
            //Makes sure we don't go through with this test if we have already hit this position before
            if (optional.isPresent() && !previousPositions.contains(optional.get())) {
                previousPositions.add(optional.get());
                recursionDepth++;
                //Prevents the recursive pipe algorthim from causing a stack overflow error if we go 3000 recursion too deep.
                if (recursionDepth > MAX_RECURSION_DEPTH) {
                    return paths;
                }
                BlockPos currentPos = optional.get();
                if (isEndpoint.accept(world, currentPos, previousPos)) {
                    doAtEndPoint.accept(currentPos);
                    paths.add(currentPos);
                } else if(isPipeOrEndpoint.test(world, currentPos)) {
                    doAtPipe.accept(currentPos);
                    findPath(world, currentPos, paths, previousPositions, doAtEndPoint, doAtPipe, isPipeOrEndpoint, isEndpoint, recursionDepth);
                }
            }
        }
        return paths;
    }
}
