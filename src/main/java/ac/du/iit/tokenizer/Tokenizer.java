package ac.du.iit.tokenizer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class Tokenizer {

	private String inputString;

	public Tokenizer(String inputString) {
		this.inputString = inputString;

	}

	public String getToken() {
		List<String> tokenList = getTokenList();
		Map<String, Integer> tokenFrquencyMap = getTokenFrquencyMap(tokenList);
		String token = createToken(tokenFrquencyMap);
		return token;
	}

	public String createToken(Map<String, Integer> map) {
		String token = "{";
		for (Map.Entry<String, Integer> entry : map.entrySet()) {
			token +="(" +entry.getKey() +"-"+ entry.getValue() +")"+ ",";
		}
		token = token.substring(0, (token.length() - 1));
		token+="}";
		return token;

	}

	public Map<String, Integer> getTokenFrquencyMap(List<String> tokenList) {

		Map<String, Integer> map = new TreeMap<String, Integer>();
		for (String token : tokenList) {
			int occurrences = Collections.frequency(tokenList, token);
			map.put(token.toLowerCase(), occurrences);

		}
		return map;

	}

	public Map<String, Integer> getTokenFrequencyMap() {
		List<String> tokenList = getTokenList();
		Map<String, Integer> tokenFrquencyMap = getTokenFrquencyMap(tokenList);
		return tokenFrquencyMap;
	}

	public List<String> getTokenList() {
		inputString = removeComments(inputString);
		inputString = replacePatter1(inputString);
		inputString = handleOps(inputString);
		inputString = handleNoiseCharacters(inputString);
		String[] tokens = tokenize(inputString);
		List<String> tokenList = new ArrayList<String>(Arrays.asList(tokens));
		List<String> strippedTokenList = stripTokens(tokenList);
		Collections.sort(strippedTokenList);
		return tokenList;

	}

	private String strip(String str) {
		return str.replaceAll("(\'|\"|\\\\|:)", "");
	}

	private List<String> stripTokens(List<String> tokens) {
		List<String> retTokens = new ArrayList<String>();
		for (String token : tokens) {
			retTokens.add(strip(token));
		}
		return retTokens;
	}

	private String handleOps(String input) {
		input = handleSimpleAssignmentOperator(input);
		input = handleArithmeticOperator(input);
		input = handleUnaryOperator(input);
		input = handleConditionalOperator(input);
		input = handleBitwiseOperator(input);
		return input;
	}

	private String[] tokenize(String input) {
		String regex = "\\s+";
		String[] tokens = input.split(regex);
		return tokens;
	}

	private String removeComments(String input) {
		String regexLineComment = "//.*(\\n|\\r|\\r\\n)";
		String x = input.replaceAll(regexLineComment, " ");
		x = x.replaceAll("\\n|\\r|\\r\\n", " ");
		String regexPattern = "/\\*.*\\*/";
		// String regexEnd = "*/";
		x = x.replaceAll(regexPattern, "");
		return x;
	}

	private String replacePatter1(String input) {
		String regexPattern = ",|\\(|\\)|\\{|\\}|\\[|\\]|<|>";
		// String regexEnd = "*/";
		String x = input.replaceAll(regexPattern, " ");
		return x;
	}

	private String handleSimpleAssignmentOperator(String input) {
		String regexPattern = "=|\\.";
		String x = input.replaceAll(regexPattern, " ");
		return x;
	}

	private String handleArithmeticOperator(String input) {
		String regexPattern = "\\+|-|\\*|/|%";
		String x = input.replaceAll(regexPattern, " ");
		return x;
	}

	private String handleUnaryOperator(String input) {
		String regexPattern = "!";
		String x = input.replaceAll(regexPattern, " ");
		return x;
	}

	private String handleConditionalOperator(String input) {
		String regexPattern = "\\?";
		String x = input.replaceAll(regexPattern, " ");
		return x;
	}

	private String handleBitwiseOperator(String input) {
		String regexPattern = "&|\\^|\\|";
		String x = input.replaceAll(regexPattern, " ");
		return x;
	}

	private String handleNoiseCharacters(String input) {
		String regexPattern = ";|@@::@@|@#@";
		String x = input.replaceAll(regexPattern, "");
		return x;
	}

}
