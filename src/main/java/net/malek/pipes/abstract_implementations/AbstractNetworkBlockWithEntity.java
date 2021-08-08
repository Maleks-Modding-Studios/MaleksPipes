package net.malek.pipes.abstract_implementations;

import net.malek.pipes.NetworkBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.BlockWithEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.state.StateManager;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

/**
 * this is basically for anything that isn't just a pipe, just a pipe should just use abstractnetworkblock
 * If it is a tile entity tho, then use this.
 *
 * You should make your own abstract block with entity class based off of this that overrides the getPipeNetwork() function with your pipe network,
 * and then use that as your base for all your networking stuff.
 */
public abstract class AbstractNetworkBlockWithEntity extends BlockWithEntity implements NetworkBlock {
    public AbstractNetworkBlockWithEntity(Settings settings) {
        super(settings);
    }
    public void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        this.appendNetworkProperties(builder);
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
