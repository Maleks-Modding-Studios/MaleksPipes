package net.malek.pipes.test;

import net.malek.pipes.MaleksPipes;
import net.minecraft.block.BlockState;
import net.minecraft.block.BlockWithEntity;
import net.minecraft.block.Blocks;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.HashSet;

public class TestPipe extends BlockWithEntity implements BlockEntityTicker<TestPipeBlockEntity> {

    public TestPipe(Settings settings) {
        super(settings);
    }


    @Override
    public void tick(World world, BlockPos pos, BlockState state, TestPipeBlockEntity blockEntity) {
        HashSet<BlockPos> paths = new HashSet<>();
        HashSet<BlockPos> previousPos = new HashSet<>();
        MaleksPipes.findPath(world, pos, paths, previousPos, (world1, blockpos) -> { ;},  (world1, blockpos) -> { ;},  (world1, pos2) -> { return (world1).getBlockState(pos2).getBlock() == MaleksPipes.TEST_PIPE;}, (world1, pos1, pos2) -> {
            return world1.getBlockState(pos1).getBlock() == Blocks.WHITE_WOOL;
        }, 0);

        paths.stream().forEach((pos3) -> System.out.println(pos3));

    }

    @Nullable
    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new TestPipeBlockEntity(pos, state);
    }
}
