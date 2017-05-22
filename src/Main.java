import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

public class Main {

	private static String tell;
	private static String ask;
	public static final int METHOD_COUNT = 3;
	public static Method[] methods;
	
	public static void main(String[] args) {
		
		
		if (args.length < 2)
			System.out.println("Invalid number of arguments");
		String method = args[0];
		String fileName = args[1];
		
		readProblemFile(fileName);
		System.out.println(tell);
		System.out.println(ask);
		initMethods();
		Method thisMethod = null;
		
		//determine which method the user wants to use to solve the puzzles
		for(int i = 0; i < METHOD_COUNT; i++)
		{
			//do they want this one?
			if(methods[i].code.compareTo(method) == 0)
			{
				//yes, use this method.
				thisMethod = methods[i];
			}
		}
		
		//Has the method been implemented?
		if(thisMethod == null)
		{
			//No, give an error
			System.out.println("Search method identified by " + method + " not implemented. Methods are case sensitive.");
			System.exit(1);
		}
		
		String result = thisMethod.execute();
		System.out.println(result);
		
				

	}
	
	private static void readProblemFile(String fileName) 
	{
		
		try
		{
			Scanner scanner = new Scanner(new File(fileName));
			while (scanner.hasNextLine()) {
				String line = scanner.nextLine();
				if (line.contains("TELL"))
					tell = scanner.nextLine().trim();
				if (line.contains("ASK"))
					ask = scanner.nextLine().trim();
			}
			scanner.close();
		}
		catch(FileNotFoundException ex)
		{
			//The file didn't exist, show an error
			System.out.println("Error: File \"" + fileName + "\" not found.");
			System.out.println("Please check the path to the file.");
			System.exit(1);
		}
		
		
	}
	
	private static void initMethods()
	{
		methods = new Method[METHOD_COUNT];
		methods[0] = new FC(ask, tell);
		methods[1] = new BC(ask, tell);
		methods[2] = new TT(ask, tell);
		
	}

}
