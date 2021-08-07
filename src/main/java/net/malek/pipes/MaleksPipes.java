package net.malek.pipes;

import net.fabricmc.api.ModInitializer;
import net.minecraft.state.property.EnumProperty;

import java.util.HashSet;
import java.util.Set;

public class MaleksPipes {
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
}
