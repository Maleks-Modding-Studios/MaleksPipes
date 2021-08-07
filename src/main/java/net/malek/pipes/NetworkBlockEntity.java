package net.malek.pipes;

import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public interface NetworkBlockEntity {
    BlockPos getBlockPos();
    World getWorld();

}
