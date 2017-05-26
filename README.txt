Team details
------------
ESP team: COS30019_A02_T020
-	Nhu Anh Tran: 100001346
-	Hamish Dean: 4941748


Features/bugs/missing
---------------------
This program can query Horn-form KB's using Truth Tables, Forward Chaining and Backward Chaining. The only symbols supported are "&" (and) and "=>" (implies).

Features
-	Read and parse a Horn-form KB within a text file
-	Forward Chaining
-	Backward Chaining
-	Truth Table

Missing
-	We have not included support for parenthesis or bidirectional symbols.

Bugs
-	No known bugs were found in the final version.


Test cases
----------
A total of 9 test cases were supplied with the working solution.

Some output was validated manually using truth tables, which has also been supplied in this solution (filename: "TT.xlsx").

The format of our tests consisted of a Horn-form Knowledge Base (KB) and a query (q).

E.g. 
	TELL
	a&b => c; a; b; a&c => d;
	ASK
	b


Description of solution
-----------------------
Our solution does operate in a simple command-line form to support batch testing and returns a YES or NO depending on whether the query follows from the knowledgebase.

The Truth Table works by reading a string-representation of the KB, and deconstructing it into symbols and sentences. It then creates a table of 'n' rows of models, and calculates if the value of each model is FALSE or TRUE to ascertain whether or not the it is satisfiable/valid/invalid.

A counter is used to keep track of the total number of valid models, which if greater than zero outputs "YES: total valid models", or "NO" when there aren't any.

Forward Chaining works in a similar way, with the main differences being that we capture 'facts' from the KB and check if any of them can entail 'ASK'.

Backward Chaining works in the opposite way to FC, by starting from the 'ASK' symbols, and search for any clauses that have those symbols in their conclusion. The main difference is the use of a temporary list, for which its purpose is to check for symbols in the conclusions of a clause. Once the temporarly list is exhausted, we know to stop the entailment.


Acknowledgements & resources
----------------------------
AI Backward Chaining implementation for propositional logic horn form knowledge bases
(http://snipplr.com/view/56297)

-	This article and sample code base helped us better understand how Backward Chaining worked with Horn-form KB's and we could represent this in a Java-based Inference Engine.

AI Forward Chaining implementation for propositional logic horn form knowledge bases
(http://snipplr.com/view/56296)

-	This article and sample code base helped us better understand how Forward Chaining worked with Horn-form KB's and we could represent this in a Java-based Inference Engine.

Github Inference Engine implementation example
(https://github.com/charlottepierce/Scratchpad/tree/master/Java/IntroToAI/AIIengine)

-	Unfortunately this resource seems to have been removed in the last week, however we used this resource to validate some compoenents our implementation methods (mainly for TT) and as a inspriation for test cases.


Notes
-----
Some tests were validated manually using truth tables, which has also been supplied in this solution (filename: "TT.xlsx").


Summary Report
--------------
The workload was spread quite evenly between us, however to be completely accurate, Andy's efforts were slightly more skewed towards the programming and Hamish's efforts were slightly more skewed towards the research and testing components of the assignment.

Breakdown of our efforts roughly amounted to the following:

Andy (50%)
-	Parsing
-	Truth Table
-	Forward Chaining
-	Backward Chaining:

Hamish (50%)
-	Parsing
-	Forward Chaining
-	Truth Table
-	Tests 