package net.malek.pipes.test;

import net.malek.pipes.MaleksPipes;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.HashSet;

import static net.malek.pipes.MaleksPipes.TEST_PIPE_BLOCK_ENTITY;

public class TestPipeBlockEntity extends BlockEntity {

    public TestPipeBlockEntity(BlockPos pos, BlockState state) {
        super(TEST_PIPE_BLOCK_ENTITY, pos, state);
    }

    public static <T extends BlockEntity> void tick(World world, BlockPos blockPos, BlockState state, T t) {
        if(world.isClient())
            return;
        ((TestPipeBlockEntity)t).tick(world, blockPos, state);
    }
    private void tick(World world, BlockPos blockPos, BlockState state) {
//        HashSet<BlockPos> paths = new HashSet<>();
//        HashSet<BlockPos> previousPos = new HashSet<>();
//        MaleksPipes.findPath(world, blockPos, paths, previousPos, (world1, blockpos) -> { ;},  (world1, blockpos) -> { ;},  (world1, pos2) -> { return (world1).getBlockState(pos2).getBlock() == MaleksPipes.TEST_PIPE || (world1).getBlockState(pos2).getBlock() == Blocks.WHITE_WOOL;}, (world1, pos1, pos2) -> {
//            return world1.getBlockState(pos1).getBlock() == Blocks.WHITE_WOOL;
//        }, 0);
//        paths.stream().forEach((pos3) -> System.out.println(pos3));
//        System.out.println(state.getBlock());
//        //System.out.println("asdfasdf");
    }
}