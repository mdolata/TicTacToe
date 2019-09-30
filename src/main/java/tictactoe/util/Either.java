package tictactoe.util;


// todo remove this impl and add vavr
public class Either<L, R> {
    private final L left;
    private final R right;
    private final boolean isRight;

    private Either(L left, R right, boolean isRight) {
        this.left = left;
        this.right = right;
        this.isRight = isRight;
    }

    public static <L,R> Either<L,R> right(R right){
        return new Either<>(null, right, true);
    }

    public static <L,R> Either<L,R> left(L left){
        return new Either<>(left, null, false);
    }

    public boolean isLeft() {
        return !isRight;
    }

    public boolean isRight() {
        return isRight;
    }

    public L getLeft() {
        return left;
    }

    public R getRight() {
        return right;
    }
}
