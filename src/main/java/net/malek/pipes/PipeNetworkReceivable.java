package net.malek.pipes;

/**
 * Implement this interface on all things you wish to have the capability to receive something
 */
public interface PipeNetworkReceivable {
    void markPipeNetworkDirty();
    void resetPipeNetworkDirty();
    boolean isPipeNetworkDirty();
}
