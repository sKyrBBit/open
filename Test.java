public class Test {
	static enum TestState implement State {
		ONE,
		TWO,
		THREE {
			@Override
			public boolean isEnd() { return true; }
		}
	}
	public static void main(String[] args) {
		final RuleBook<Character> rules = new RuleBook<>();
		final NFA<Character> nfa = new NFA<>(TestState.ONE, rules);
		rules.setRule(new Rule<>(TestState.ONE, '0', TestState.ONE))
		     .setRule(new Rule<>(TestState.ONE, '1', TestState.ONE))
		     .setRule(new Rule<>(TestState.ONE, '1', TestState.TWO))
		     .setRule(new Rule<>(TestState.TWO, '0', TestState.THREE))
		     .setRule(new Rule<>(TestState.TWo, '1', TestState.THREE));
		final List<Character> input = Stream.of('1', '0')
			                            .collect(Collectors.toList());
		assertThat(nfa.isEnd(input), is(true));
		input = Stream.of('0', '0', '0', '1', '0')
			      .collect(Collectors.toList());
		assertThat(nfa.isEnd(input), is(true));
		input = Stream.of('0', '1', '0', '1', '0', '1', '0', '1', '0', '1', '0')
			      .collext(Collectors.toList());
		assertThat(nfa.isEnd(input), is(true));
		input = Stream.of('0', '1')
	    		      .collext(Collectors.toList());
		assertThat(nfa.isEnd(input), is(false));
		input = Stream.of('1', '0', '0', '1')
			      .collext(Collectors.toList());
		assertThat(nfa.isEnd(input), is(false));
	}
}
