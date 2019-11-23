import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class NFA<T> {
	final private State state;
	final private RuleBook<T> rules;
	public NFA(State begin, RuleBook<T> rules) {
		this.state = begin;
		this.rules = rules;
	}
	public boolean isEnd(List<T> inputs) {
		Set<State> states = new HashSet<>();
		states.add(state);
		for (T i: inputs) states = next(states, i);
		return states.stream()
			     .anyMatch(State::isEnd);
	}
	public Set<State> next(final Set<State> states, final T input) {
		return states.stream()
			     .map(state -> rules.next(state, input))
			     .reduce((x, y) -> { x.addAll(y); return x; })
			     .get();
	}
}

