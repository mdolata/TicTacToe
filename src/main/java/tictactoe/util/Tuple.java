package tictactoe.util;

public class Tuple<S, T> {
    private final S _1;
    private final T _2;

    private Tuple(S s, T t) {
        _1 = s;
        _2 = t;
    }

    public static <S,T> Tuple of(S _1, T _2){
        return new Tuple<>(_1, _2);
    }

    public S get_1() {
        return _1;
    }

    public T get_2() {
        return _2;
    }
}
