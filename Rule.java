public class Rule<T> {
	final private State state;
	final private T input;
	final private State next;
	public Rule(State state, T input, State next) {
		this.state = state;
		this.input = input;
		this.next = next;
	}
	public State next() {
		return next;
	}
	public boolean is ApplicableTo(State state, T input) {
		return this.state.equals(state) && this.input.equals(input);
	}
	@Override
	public String toString() {
		return String.format("( Rule: %s -- %s -> %s )", state, input, next);
	}
}

