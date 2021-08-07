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

    public static final int MAX_RECURSION_DEPTH = 3000;
    
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



    //Some Misc Methods
    static Optional<BlockPos>[] getAroundTarget(World world, BlockPos pos, BiPredicate<World, BlockPos> predicate) {
        Optional<BlockPos>[] positions = new Optional[6];
        positions[0] = getIfMatches(world, pos.add(1, 0, 0), predicate);
        positions[1] = getIfMatches(world, pos.add(0, 1, 0), predicate);
        positions[2] = getIfMatches(world, pos.add(0, 0, 1), predicate);
        positions[3] = getIfMatches(world, pos.add(-1, 0, 0), predicate);
        positions[4] = getIfMatches(world, pos.add(0, -1, 0), predicate);
        positions[5] = getIfMatches(world, pos.add(0, 0, -1), predicate);
        return positions;
    }
    static Optional<BlockPos> getIfMatches(World world, BlockPos pos, BiPredicate<World, BlockPos> predicate) {
        if (predicate.test(world, pos)) {
            return Optional.of(pos);
        }
        return Optional.empty();
    }
    public static HashSet<BlockPos> findPath(World world,
                                      BlockPos previousPos,
                                      HashSet<BlockPos> paths,
                                      HashSet<BlockPos> previousPositions,
                                      BiConsumer<World, BlockPos> doAtEndPoint,
                                      BiConsumer<World, BlockPos> doAtPipe,
                                      BiPredicate<World, BlockPos> isPipe,
                                      TriPredicate<World, BlockPos, BlockPos> isEndpoint,
                                      int recursionDepth) {
        Optional<BlockPos>[] optionals = getAroundTarget(world, previousPos, isPipe);
        for (Optional<BlockPos> optional : optionals) {
            if (optional.isPresent() && !previousPositions.contains(optional.get())) {
                previousPositions.add(optional.get());
                recursionDepth++;
                //Prevents the recursive pipe algorthim from causing a stack overflow error if we go 3000 recursion too deep.
                if (recursionDepth > MAX_RECURSION_DEPTH) {
                    return paths;
                }
                BlockPos currentPos = optional.get();
                if (isEndpoint.accept(world, currentPos, previousPos)) {
                    System.out.println(world.getBlockState(currentPos));
                    System.out.println(currentPos);
                    doAtEndPoint.accept(world, currentPos);
                    paths.add(currentPos);
                } else if(isPipe.test(world, currentPos)) {
                    doAtPipe.accept(world, currentPos);
                    findPath(world, currentPos, paths, previousPositions, doAtEndPoint, doAtPipe, isPipe, isEndpoint, recursionDepth);
                }
            }
        }
        return paths;
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
