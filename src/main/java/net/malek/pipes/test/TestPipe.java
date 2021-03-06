package net.malek.pipes.test;

import net.malek.pipes.MaleksPipes;
import net.malek.pipes.NetworkBlock;
import net.malek.pipes.PipeNetwork;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.HashSet;

public class TestPipe extends Block implements NetworkBlock {

    public TestPipe(AbstractBlock.Settings settings) {
        super(settings);
    }


    @Override
    public PipeNetwork getPipeNetwork() {
        return MaleksPipes.TEST_NETWORK;
    }

    @Override
    public void onPlaced(World world, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack itemStack) {
        super.onPlaced(world, pos, state, placer, itemStack);
        if(!world.isClient()) {
            findAndMarkConsumersAsDirty((ServerWorld) world, pos);
        }
    }


    @Override
    public void onBreak(World world, BlockPos pos, BlockState state, PlayerEntity player) {
        if(!world.isClient()) {
            findAndMarkConsumersAsDirty((ServerWorld) world, pos);
        }
        super.onBreak(world, pos, state, player);
    }
}
