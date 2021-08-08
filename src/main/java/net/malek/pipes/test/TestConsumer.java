package net.malek.pipes.test;

import net.malek.pipes.MaleksPipes;
import net.malek.pipes.NetworkBlock;
import net.malek.pipes.PipeNetwork;
import net.malek.pipes.SideType;
import net.minecraft.block.Block;
import net.minecraft.block.BlockEntityProvider;
import net.minecraft.block.BlockState;
import net.minecraft.block.BlockWithEntity;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.state.StateManager;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class TestConsumer extends Block implements BlockEntityProvider, NetworkBlock {
    public TestConsumer(Settings settings) {
        super(settings);
        this.setDefaultState(this.setSides(this.getDefaultState(), SideType.CONSUMER));
    }

    public void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        this.appendNetworkProperties(builder);
    }

    @Nullable
    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new TestConsumerBlockEntity(pos, state);
    }

    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(World world, BlockState state, BlockEntityType<T> type) {
        return TestConsumerBlockEntity::tick;
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
