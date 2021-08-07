package net.malek.pipes;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.malek.pipes.test.TestPipe;
import net.malek.pipes.test.TestPipeBlockEntity;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.state.property.EnumProperty;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.BiPredicate;
import java.util.function.Predicate;

public class MaleksPipes implements ModInitializer{
    //This is for yall who wanna know how it works.
    /*
    First, things have sides that are either set as reciver, sender, or none
    Any individual block can be just a reciver, just a sender, or both, a dual thing.
    When you update the network, the network searches for each receiver, and marks it as dirty.
    Then in the next tick, each receiver finds all the generators it is validly connected too.
     */


    //The sidedness of each block
    public static final EnumProperty<SideType> SIDE_NORTH;
    public static final EnumProperty<SideType> SIDE_SOUTH;
    public static final EnumProperty<SideType> SIDE_EAST;
    public static final EnumProperty<SideType> SIDE_WEST;
    public static final EnumProperty<SideType> SIDE_UP;
    public static final EnumProperty<SideType> SIDE_DOWN;
    public static final Set<EnumProperty<SideType>> SIDE_DIRECTIONS;

    
    static {
        SIDE_NORTH = EnumProperty.of("side_north", SideType.class);
        SIDE_SOUTH = EnumProperty.of("side_south", SideType.class);
        SIDE_EAST = EnumProperty.of("side_east", SideType.class);
        SIDE_WEST = EnumProperty.of("side_west", SideType.class);
        SIDE_UP = EnumProperty.of("side_up", SideType.class);
        SIDE_DOWN = EnumProperty.of("side_down", SideType.class);

        HashSet<EnumProperty<SideType>> temp2 = new HashSet<>();
        setConnectedSideTypes(temp2);
        SIDE_DIRECTIONS = temp2;
    }
    private static void setConnectedSideTypes(HashSet<EnumProperty<SideType>> temp) {
        temp.add(SIDE_NORTH);
        temp.add(SIDE_SOUTH);
        temp.add(SIDE_UP);
        temp.add(SIDE_DOWN);
        temp.add(SIDE_EAST);
        temp.add(SIDE_WEST);
    }
    public static EnumProperty<SideType> getSideTypeFromDirection(Direction direction) {
        return switch(direction) {
            case UP -> SIDE_UP;
            case DOWN -> SIDE_DOWN;
            case EAST -> SIDE_EAST;
            case WEST -> SIDE_WEST;
            case NORTH -> SIDE_NORTH;
            case SOUTH -> SIDE_SOUTH;
        };
    }



    public static BlockEntityType<TestPipeBlockEntity> TEST_PIPE_BLOCK_ENTITY;
    public static String MOD_ID = "maleks_pipes";
    public static Block TEST_PIPE = new TestPipe(FabricBlockSettings.of(Material.STONE));
    @Override
    public void onInitialize() {
        TEST_PIPE_BLOCK_ENTITY = Registry.register(Registry.BLOCK_ENTITY_TYPE, new Identifier(MOD_ID, "test_pipe"), FabricBlockEntityTypeBuilder.create(TestPipeBlockEntity::new, TEST_PIPE).build(null));
        TEST_PIPE = Registry.register(Registry.BLOCK, new Identifier(MOD_ID, "test_pipe"), TEST_PIPE);
    }


}
