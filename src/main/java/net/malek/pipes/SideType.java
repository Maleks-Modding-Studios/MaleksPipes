package net.malek.pipes;

import net.minecraft.util.StringIdentifiable;

/**
 * Every side of a network block is either marked as a consumer, producer, or neither.
 * If you want to disable or toggle between a side being for example, a producer or not ouptuing, you
 * have to handel it by just switching between the two.
 */
public enum SideType implements StringIdentifiable {
    CONSUMER,
    PRODUCER,
    NONE;

    @Override
    public String asString() {
        return switch (this) {
            case NONE -> "none";
            case CONSUMER -> "consumer";
            case PRODUCER -> "producer";
        };
    }
}
