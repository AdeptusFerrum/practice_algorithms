package kt5;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class BalancedParenthesesTest {
    @Test
    void singlePair() {
        assertEquals(List.of("()"), BalancedParentheses.generate(1));
    }

    @Test
    void twoPairs() {
        assertEquals(List.of("(())", "()()"), BalancedParentheses.generate(2));
    }

    @Test
    void generatesSequencesInLexicographicOrder() {
        assertEquals(List.of("((()))", "(()())", "(())()", "()(())", "()()()"),
                BalancedParentheses.generate(3));
    }

    @Test
    void handlesMaximumNumberOfPairs() {
        List<String> sequences = BalancedParentheses.generate(10);
        assertEquals(16_796, sequences.size());
        String previous = "";
        for (String sequence : sequences) {
            assertEquals(20, sequence.length());
            assertTrue(previous.compareTo(sequence) < 0);
            int balance = 0;
            for (char bracket : sequence.toCharArray()) {
                balance += bracket == '(' ? 1 : -1;
                assertTrue(balance >= 0);
            }
            assertEquals(0, balance);
            previous = sequence;
        }
    }

    @Test
    void callsAreIndependent() {
        BalancedParentheses.generate(3);
        assertEquals(List.of("()"), BalancedParentheses.generate(1));
    }
}
