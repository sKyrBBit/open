package lexical;

public interface State {
	default boolean isEnd() { return false; }
}

