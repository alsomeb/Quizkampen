package org.quizkampen.server;

import java.io.Serializable;

// Kan va Record, pga final boolean samt getter
public record Initiator(boolean allConnected) implements Serializable {
}