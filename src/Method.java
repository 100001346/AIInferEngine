import java.util.*;

public abstract class Method {
	public String code;				//the code used to identify the method at the command line
	public String longName;			//the actual name of the method.
	
	public abstract String execute();
}