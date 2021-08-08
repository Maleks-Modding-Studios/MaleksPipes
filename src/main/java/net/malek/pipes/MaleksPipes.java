package net.malek.pipes;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.malek.pipes.test.*;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemGroup;
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



    public static BlockEntityType<TestConsumerBlockEntity> TEST_CONSUMER_BLOCK_ENTITY;
    public static BlockEntityType<TestProducerBlockEntity> TEST_PRODUCER_BLOCK_ENTITY;

    public static Block TEST_CONSUMER = new TestConsumer(FabricBlockSettings.of(Material.STONE));
    public static Block TEST_PRODUCER = new TestProducer(FabricBlockSettings.of(Material.STONE));

    public static String MOD_ID = "maleks_pipes";
    public static Block TEST_PIPE = new TestPipe(FabricBlockSettings.of(Material.STONE));
    public static PipeNetwork TEST_NETWORK;
    @Override
    public void onInitialize() {

/*      TEST_PIPE = Registry.register(Registry.BLOCK, new Identifier(MOD_ID, "test_pipe"), TEST_PIPE);
        TEST_CONSUMER = Registry.register(Registry.BLOCK, new Identifier(MOD_ID, "test_consumer"), TEST_CONSUMER);
        TEST_PRODUCER = Registry.register(Registry.BLOCK, new Identifier(MOD_ID, "test_producer"), TEST_PRODUCER);

        TEST_CONSUMER_BLOCK_ENTITY = Registry.register(Registry.BLOCK_ENTITY_TYPE, new Identifier(MOD_ID, "consumer"), FabricBlockEntityTypeBuilder.create(TestConsumerBlockEntity::new, TEST_CONSUMER).build(null));
        TEST_PRODUCER_BLOCK_ENTITY = Registry.register(Registry.BLOCK_ENTITY_TYPE, new Identifier(MOD_ID, "producer"), FabricBlockEntityTypeBuilder.create(TestProducerBlockEntity::new, TEST_PRODUCER).build(null));

        Registry.register(Registry.ITEM, new Identifier(MOD_ID, "test_pipe"), new BlockItem(TEST_PIPE, new FabricItemSettings().group(ItemGroup.MISC)));
        Registry.register(Registry.ITEM, new Identifier(MOD_ID, "test_consumer"), new BlockItem(TEST_CONSUMER, new FabricItemSettings().group(ItemGroup.MISC)));
        Registry.register(Registry.ITEM, new Identifier(MOD_ID, "test_producer"), new BlockItem(TEST_PRODUCER, new FabricItemSettings().group(ItemGroup.MISC)));

        TEST_NETWORK = new PipeNetwork(this::testIsProducer, this::testIsConsumer, this::testIsPipe);*/

    }

    boolean testIsProducer(World world, BlockPos pos) {
        return world.getBlockState(pos).getBlock() == TEST_PRODUCER;
    }
    boolean testIsConsumer(World world, BlockPos pos) {
        return world.getBlockState(pos).getBlock() == TEST_CONSUMER;
    }
    boolean testIsPipe(World world, BlockPos pos) {
        return world.getBlockState(pos).getBlock() == TEST_PIPE;
    }


}
