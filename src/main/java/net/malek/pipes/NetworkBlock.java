package net.malek.pipes;

import net.minecraft.block.BlockState;


import static net.malek.pipes.MaleksPipes.*;
import static net.malek.pipes.SideType.*;

public interface NetworkBlock {
    default BlockState setSides(BlockState state) {
        return setSides(state, NONE);
    }
    default BlockState setSides(BlockState state, SideType type) {
        return state.with(SIDE_NORTH, type).with(SIDE_SOUTH, type).with(SIDE_EAST, type).with(SIDE_WEST, type).with(SIDE_UP, type).with(SIDE_DOWN, type);
    }
}
