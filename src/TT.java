import java.util.*;
import java.io.*;

public class TT extends Method {
	// create variables
	public String tell;
	public String ask;
	public ArrayList<String> agenda;
	public ArrayList<String> facts;
	public ArrayList<String> symbols;
	public Map<String, Boolean> truthTable;
	public int validModels;

	public TT(String a, String t) {
		// initialize variables
		code = "TT";
		longName = "Truth Table";
		agenda = new ArrayList<String>();
		symbols = new ArrayList<String>();
		facts = new ArrayList<String>();
		truthTable = new HashMap<String, Boolean>();
		tell = t;
		ask = a;
		validModels = 0;
		init(tell);
	}

	// method which calls the main fcentails() method and returns output back to
	// iengine
	@Override
	public String execute() {
		String output = "";
		// there will be ((2 ^ symbols.size()) rows in truth table
		int rowCount = 0;
		int ttRows = (int) Math.pow(2.0, symbols.size());
		while (rowCount != ttRows) {
			updateTruthTable(Integer.toBinaryString(rowCount));
			// process each of the clauses given the current values for each
			// symbol (i.e. the current model) - takes care of incrementing
			// validModels if needed
			checkModel();
			rowCount++;
		}
		if (validModels > 0) {
			output += "YES: " + validModels;
		} else {
			output += "NO";
		}

		return output;
	}

	private void updateTruthTable(String binaryString) {
		// Pad the boolean string to number of symbols
		while (binaryString.length() != symbols.size()) {
			binaryString = "0" + binaryString;
		}
		// Change boolean values appropriately
		for (int i = 0; i < binaryString.length(); i++) {
			if (binaryString.charAt(i) == '0') {
				truthTable.put(symbols.get(i), false);
			} else if (binaryString.charAt(i) == '1') {
				truthTable.put(symbols.get(i), true);
			}
		}
	}

	private void checkModel() {
		if (!truthTable.get(ask)) {
			// if the asked symbol is false from the outset, the model is
			// already invalid - don't bother checking
			return;
		} else {
			// actually check the model
			for (String clause : this.agenda) {
				if (!checkClause(clause)) {
					return;
				}
			}
			// all clauses hold - never satisfied condition for 'return'
			validModels++;
		}
	}

	private boolean checkClause(String clause) {
		if (facts.contains(clause))
			return truthTable.get(clause);
		else {
			String[] premises = this.getPremise(clause);
			for (String premise : premises) {
				if (truthTable.get(premise) == false)
					return true;
			}
			String consequence = this.getConsequence(clause);
			return truthTable.get(consequence);
		}
		
	}

	// method which sets up initial values for forward chaining
	// takes in string representing KB and seperates symbols and clauses,
	// calculates count etc..
	public void init(String tell) {
		String[] sentences = tell.split(";");
		for (int i = 0; i < sentences.length; i++) {

			if (!sentences[i].contains("=>")) {
				this.agenda.add(sentences[i].trim());
				this.facts.add(sentences[i].trim());
				addSymbol(sentences[i].trim());
			} else {
				// add sentences
				this.agenda.add(sentences[i].trim());
				addSymbolFromClause(sentences[i]);
			}
		}
	}

	private void addSymbolFromClause(String clause) {
		String[] parts = clause.split("=>");
		for (String part : parts) {
			if (part.contains("&")) {
				String[] premiseSymbols = part.split("&");
				for (String s : premiseSymbols)
					addSymbol(s.trim());
			} else {
				addSymbol(part.trim());
			}
		}
	}

	private boolean addSymbol(String s) {
		if (this.symbols.contains(s))
			return false;
		else {
			this.symbols.add(s);
			return true;
		}
	}
	
	private String[] getPremise(String clause) {
		String premises = clause.split("=>")[0].trim();
		
		return premises.split("&");
	}
	
	private String getConsequence(String clause) {
		return clause.split("=>")[1].trim();
	}
}