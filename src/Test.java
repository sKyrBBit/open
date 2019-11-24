import lexical.State;

public class Test {
	static enum TestState implements State {
		ONE,
		TWO,
		THREE {
			@Override
			public boolean isEnd() { return true; }
		}
	}
	public static void main(String[] args) {
		/*
		final lexical.RuleBook<Character> rules = new lexical.RuleBook<>();
		final NFA<Character> nfa = new NFA<>(TestState.ONE, rules);
		rules.setRule(new lexical.Rule<>(TestState.ONE, '0', TestState.ONE))
		     .setRule(new lexical.Rule<>(TestState.ONE, '1', TestState.ONE))
		     .setRule(new lexical.Rule<>(TestState.ONE, '1', TestState.TWO))
		     .setRule(new lexical.Rule<>(TestState.TWO, '0', TestState.THREE))
		     .setRule(new lexical.Rule<>(TestState.TWO, '1', TestState.THREE));
		List<Character> input;
		input = Stream.of('1', '0')
				      .collect(Collectors.toList());
		System.out.println(nfa.isEnd(input));
		input = Stream.of('0', '0', '0', '1', '0')
			          .collect(Collectors.toList());
		System.out.println(nfa.isEnd(input));
		input = Stream.of('0', '1', '0', '1', '0', '1', '0', '1', '0', '1', '0')
			          .collect(Collectors.toList());
		System.out.println(nfa.isEnd(input));
		input = Stream.of('0', '1')
	    		      .collect(Collectors.toList());
		System.out.println(nfa.isEnd(input));
		input = Stream.of('1', '0', '0', '1')
				      .collect(Collectors.toList());
		System.out.println(nfa.isEnd(input));
		*/
	}
}
