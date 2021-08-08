package net.malek.pipes.abstract_implementations;

import net.malek.pipes.NetworkBlock;
import net.malek.pipes.SideType;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.state.StateManager;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

/**
 * if you aren't extending any special block, you can use this, for stuff like pipes that aren't tile entities.
 */

public abstract class AbstractNetworkBlock extends Block implements NetworkBlock {
    public AbstractNetworkBlock(Settings settings) {
        super(settings);
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
