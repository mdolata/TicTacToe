package tictactoe.board;

import java.util.ArrayList;
import java.util.List;

public enum State {
    GAME_NOT_FINISHED("Game not finished"),
    DRAW("Draw"),
    X_WINS("X wins"),
    O_WINS("O wins"),
    IMPOSSIBLE("Impossible"),
    UNKNOWN("Unknown");

    private static final List<State> terminalStates = new ArrayList<>();

    static {
        terminalStates.add(State.DRAW);
        terminalStates.add(State.O_WINS);
        terminalStates.add(State.X_WINS);
    }

    private final String name;

    State(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public boolean isTerminal() {
        return terminalStates.contains(this);
    }
}
