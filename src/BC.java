import java.util.*;
import java.io.*;

// to run simply do a: new FC(ask,tell) and then fc.execute()
// ask is a propositional symbol
// and tell is a knowledge base 
// ask : r
// tell : p=>q;q=>r;p;q;

public class BC extends Method {
	// create variables
	public static String tell;
	public static String ask;
	public static ArrayList<String> agenda;

	public static ArrayList<String> facts;
	public static ArrayList<String> clauses;
	public static ArrayList<String> checked;
	public static ArrayList<String> entailed;
	

	public BC(String a, String t) {
		// initialize variables
		code = "BC";
		longName = "Backward Chaining";
		agenda = new ArrayList<String>();
		clauses = new ArrayList<String>();
		entailed = new ArrayList<String>();
		facts = new ArrayList<String>();
		checked = new ArrayList<String>();
		tell = t;
		ask = a;
		init(tell);
	}

	// method which calls the main fcentails() method and returns output back to
	// iengine
	@Override
	public String execute() {
		String output = "";
		if (bcentails()) {
			// the method returned true so it entails
			output = "YES: ";
			// for each entailed symbol
			Collections.reverse(entailed);
			for (int i = 0; i < entailed.size(); i++) {
				output += entailed.get(i) + ", ";
			}
			output = output.substring(0, output.lastIndexOf(", "));
		} else {
			output = "NO";
		}
		return output;
	}

	// FC algorithm
	public boolean bcentails() {
		// loop through while there are unprocessed facts
		
		while (!agenda.isEmpty()) {
			// take the first item and process it
			String p = agenda.remove(0);
			System.out.println("P: " + p);
			entailed.add(p);
			
			// for each of the clauses....
			List<String> temp = new ArrayList<>();
			if (!facts.contains(p)) {
				for (int i = 0; i < clauses.size(); i++) {
					String clause = clauses.get(i);
					// .... that contain p in its premise
					if (conclusionContains(clause, p)) {
						temp = getPremises(clause);
						addGoal(clause, p);
						checked.add(p);
					}
				}
				if (temp.isEmpty())
					return false;
			}
		}
		
		// if we arrive here then ask cannot be entailed
		return true;
	}

	// method which sets up initial values for forward chaining
	// takes in string representing KB and seperates symbols and clauses,
	// calculates count etc..
	public static void init(String tell) {
		String[] sentences = tell.split(";");
		for (int i = 0; i < sentences.length; i++) {

			if (!sentences[i].contains("=>"))
				// add facts to be processed
				facts.add(sentences[i].trim());
			else {
				// add sentences
				clauses.add(sentences[i].trim());
				
			}
		}
		agenda.add(ask.trim());
	}

	// method which checks if p appears in the premise of a given clause
	// input : clause, p
	// output : true if p is in the premise of clause
	public boolean conclusionContains(String clause, String p) {
		String goal = clause.split("=>")[1].trim();
		return goal.equals(p);
	}
	
	private void addGoal(String clause, String p) {
		String premise = clause.split("=>")[0].trim();
		String goal = clause.split("=>")[1].trim();
		if (goal.equals(p)) {
			if (premise.contains("&")) {
				String[] conjuncts = premise.split("&");
				for (int i = 0; i < conjuncts.length; i++) {
					addAgenda(conjuncts[i]);
				}
			} else {
				addAgenda(premise);
			}
		}	
	}
	
	private boolean addAgenda(String p) {
		if (agenda.contains(p) || checked.contains(p))
			return false;
		else {
			agenda.add(p);
			return true;
		}
	}
	
	private List<String> getPremises(String clause) {
		List<String> result = new ArrayList<>();
		String premise = clause.split("=>")[0].trim();
		String[] conjuncts = premise.split("&");
		for (int i = 0; i < conjuncts.length; i++) {
			result.add(conjuncts[i]);
		}
		return result;
	}
}