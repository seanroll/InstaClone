package rain.finalproject.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import rain.finalproject.minilabs.dominic.inheritance.Person;
import rain.finalproject.minilabs.dominic.inheritance.Student;
import rain.finalproject.minilabs.dominic.inheritance.Teacher;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.regex.Pattern;

import rain.finalproject.mjson.*;

@Controller
public class DomMinilabController {
	//-- Dom's mini labs

	//-- Utility functions

	// Returns the time it takes to run 'Function' in ms
	public double GetFunctionRunTime(Runnable Function) {
		long Start = System.nanoTime();
		Function.run();
		return (System.nanoTime() - Start) / 1000000.0;
	}


	public static void main(String[] args) {
		String data = "hello,,what,";
		List<String> StringList = Arrays.asList(data.split(","));

		for (String str : StringList) {
			System.out.println(str);
			System.out.println("----");
		}
	}


	//-- LinkedList minilab

	final ArrayList<String> ValidLocationTypes = new ArrayList(Arrays.asList("head", "middle", "tail"));
	final ArrayList<String> ValidOpTypes = new ArrayList(Arrays.asList("insert", "delete", "sort"));

	@GetMapping("/LLDom")
	public String llDom(@RequestParam(name = "op", required = false) String op,
						@RequestParam(name = "location", required = false) String location,
						@RequestParam(name = "amount", required = false) String amount,
						Model model) {

		// Defaults
		boolean IsOpValid = op != null && ValidOpTypes.contains(op);
		op = IsOpValid ? op : ValidOpTypes.get(0);
		location = (location != null && ValidLocationTypes.contains(location)) ? location : ValidLocationTypes.get(0);

		// Attributes
		model.addAttribute("op", op);
		for (String OpType : ValidOpTypes) {
			model.addAttribute(OpType + "Location", OpType.equals(op) ? location : ValidLocationTypes.get(0));
		}
		model.addAttribute("location", location);

		int Size;
		try {
			Size = Integer.parseInt(amount);
			if (Size < 1 || Size > 20000) {
				throw new Exception();
			}
		} catch (Exception Ex) {
			if (IsOpValid) {
				model.addAttribute(op + "Error", "Please provide a valid integer between 1 and 10,000 for the number of elements to insert.");
			}
			return "LinkedList/LLDom";
		}
		model.addAttribute(op + "Amount", amount);

		double llTime = 0;
		double alTime = 0;

		if (op.equals("insert")) {
			// I have 3 separate if statements that each create different lambda expressions
			// I could have squished it down into one lambda that contains an if inside, but I wanted to avoid
			// the overhead that comes from checking in each for loop iteration which index to insert the object in based on 'location'
			// -- might be an unnecessary optimization, but oh well

			if (location.equals("head")) {
				llTime = GetFunctionRunTime(() -> {
					LinkedList<Person> LL = new LinkedList<>();
					for (int Index = 1; Index <= Size; ++Index) {
						LL.addFirst(new Person());
					}
				});
				alTime = GetFunctionRunTime(() -> {
					ArrayList<Person> AL = new ArrayList<>();
					for (int Index = 1; Index <= Size; ++Index) {
						AL.add(0, new Person());
					}
				});
			} else if (location.equals("middle")) {
				llTime = GetFunctionRunTime(() -> {
					LinkedList<Person> LL = new LinkedList<>();
					for (int Index = 1; Index <= Size; ++Index) {
						LL.add((Index - 1) / 2, new Person());
					}
				});
				alTime = GetFunctionRunTime(() -> {
					ArrayList<Person> AL = new ArrayList<>();
					for (int Index = 1; Index <= Size; ++Index) {
						AL.add((Index - 1) / 2, new Person());
					}
				});
			} else if (location.equals("tail")) {
				llTime = GetFunctionRunTime(() -> {
					LinkedList<Person> LL = new LinkedList<>();
					for (int Index = 1; Index <= Size; ++Index) {
						LL.add(new Person());
					}
				});
				alTime = GetFunctionRunTime(() -> {
					ArrayList<Person> AL = new ArrayList<>();
					for (int Index = 1; Index <= Size; ++Index) {
						AL.add(new Person());
					}
				});
			} else {
				return "LinkedList/LLDom";
			}
		} else if (op.equals("delete")) {
			ArrayList<Person> AL = new ArrayList<>();
			LinkedList<Person> LL = new LinkedList<>();

			for (int Index = 1; Index <= Size; ++Index) {
				AL.add(new Person());
				LL.add(new Person());
			}
			if (location.equals("head")) {
				llTime = GetFunctionRunTime(() -> {
					for (int Index = 1; Index <= Size; ++Index) {
						LL.removeFirst();
					}
				});
				alTime = GetFunctionRunTime(() -> {
					for (int Index = 1; Index <= Size; ++Index) {
						AL.remove(0);
					}
				});
			} else if (location.equals("middle")) {
				llTime = GetFunctionRunTime(() -> {
					for (int Index = 1; Index <= Size; ++Index) {
						LL.remove((Size - Index) / 2);
					}
				});
				alTime = GetFunctionRunTime(() -> {
					for (int Index = 1; Index <= Size; ++Index) {
						AL.remove((Size - Index) / 2);
					}
				});
			} else if (location.equals("tail")) {
				llTime = GetFunctionRunTime(() -> {
					for (int Index = 1; Index <= Size; ++Index) {
						LL.removeLast();
					}
				});
				alTime = GetFunctionRunTime(() -> {
					for (int Index = 1; Index <= Size; ++Index) {
						AL.remove(Size - Index);
					}
				});
			} else {
				return "LinkedList/LLDom";
			}
		} else if (op.equals("sort")) {
			ArrayList<Person> AL = new ArrayList<>();
			LinkedList<Person> LL = new LinkedList<>();

			for (int Index = 1; Index <= Size; ++Index) {
				String FirstName = UUID.randomUUID().toString();
				String LastName = UUID.randomUUID().toString();
				int Age = (int)(Math.random() * 100) + 1;

				AL.add(new Person(FirstName, LastName, Age));
				LL.add(new Person(FirstName, LastName, Age));
			}

			llTime = GetFunctionRunTime(() -> Collections.sort(LL, Comparator.comparing(Person::toString)));
			alTime = GetFunctionRunTime(() -> Collections.sort(AL, Comparator.comparing(Person::toString)));

		} else {
			return "LinkedList/LLDom";
		}

		model.addAttribute("llTime", llTime);
		model.addAttribute("alTime", alTime);

		if (llTime < alTime) {
			model.addAttribute("bestTime", "Linked List");
		} else if (llTime > alTime) {
			model.addAttribute("bestTime", "Array List");
		} else {
			model.addAttribute("bestTime", "Same");
		}

		String ResultText;
		if (op.equals("insert")) {
			ResultText = "<strong>" + amount + "</strong> elements were <strong>inserted</strong> at the <strong>" + location + "</strong> of the various lists.";
		} else if (op.equals("delete")) {
			ResultText = "<strong>" + amount + "</strong> elements were <strong>deleted</strong> from the <strong>" + location + "</strong> of the various lists (lists were constructed with elements before deletion began).";
		} else {
			ResultText = "<strong>" + amount + "</strong> randomly generated elements were <strong>sorted</strong> (using Java's built-in Collections#sort algorithm) in the various lists.";
		}
		model.addAttribute("resultText", ResultText);

		if (op.equals("insert")) {
			if (location.equals("head")) {
				model.addAttribute("llComplexity", "O(1)");
				model.addAttribute("alComplexity", "O(N)");
			} else if (location.equals("middle")) {
				model.addAttribute("llComplexity", "O(N)");
				model.addAttribute("alComplexity", "O(N)");
			} else {
				model.addAttribute("llComplexity", "O(1)");
				model.addAttribute("alComplexity", "O(1)");
			}
		} else if (op.equals("delete")) {
			if (location.equals("head")) {
				model.addAttribute("llComplexity", "O(1)");
				model.addAttribute("alComplexity", "O(N)");
			} else if (location.equals("middle")) {
				model.addAttribute("llComplexity", "O(N)");
				model.addAttribute("alComplexity", "O(N)");
			} else {
				model.addAttribute("llComplexity", "O(1)");
				model.addAttribute("alComplexity", "O(1)");
			}
		} else {
			model.addAttribute("llComplexity", "O(N log N)");
			model.addAttribute("alComplexity", "O(N log N)");
		}

		model.addAttribute("amount", amount);
		return "LinkedList/LLDom";
	}

	//-- Sort minilab

	@GetMapping("/SortDom")
	public String sortDom() {
		return "Sort/SortDom";
	}

	@PostMapping("/SortDom")
	public String sortDom(@RequestParam(name = "dataType") String dataType,
						  @RequestParam(name = "data") String data,
						  Model model) {
		ArrayList<Object> ObjList = new ArrayList<>();
		model.addAttribute("dataType", dataType);
		model.addAttribute("input", data);

		if (dataType.equals("integer")) {
			// Data format: NUMBER,NUMBER,NUMBER
			List<String> StringList = Arrays.asList(data.split(","));

			for (String Str : StringList) {
				try {
					Integer Int = Integer.parseInt(Str);
					ObjList.add(Int);
				} catch (Exception Ex) {
					model.addAttribute("intError", "Invalid data format.");
					return "Sort/SortDom";
				}
			}
		} else if (dataType.equals("string")) {
			// Data format: STRING,STRING,STRING
			List<String> StringList = Arrays.asList(data.split(","));

			for (String Str : StringList) {
				if (Str.equals("")) {
					model.addAttribute("stringError", "Invalid data format.");
					return "Sort/SortDom";
				} else {
					ObjList.add(Str);
				}
			}
		} else if (dataType.equals("person")) {
			// Data format: json - {firstname:"", lastname:"", age: NUM}
			try {
				Json MasterObj = Json.read(data);
				List<Json> JsonObjs = MasterObj.asJsonList();

				for (Json PersonJsonObj : JsonObjs) {
					// Verify
					if (PersonJsonObj.has("firstname") &&
						PersonJsonObj.at("firstname").isString() &&
						!PersonJsonObj.at("firstname").asString().equals("") &&

						PersonJsonObj.has("lastname") &&
						PersonJsonObj.at("lastname").isString() &&
						!PersonJsonObj.at("lastname").asString().equals("") &&

						PersonJsonObj.has("age") &&
						PersonJsonObj.at("age").isNumber()) {

						ObjList.add(new Person(PersonJsonObj.at("firstname").asString(), PersonJsonObj.at("lastname").asString(), PersonJsonObj.at("age").asInteger()));
					} else {
						throw new Exception();
					}
				}
			} catch (Exception Ex) {
				model.addAttribute("personError", "Invalid data format.");
				return "Sort/SortDom";
			}
		} else {
			return "Sort/SortDom";
		}

		// Data has been verified; now sort
		// To-do: ensure tables are correct
		Object[] ToSortList = ObjList.toArray();

		GetFunctionRunTime(() -> rain.finalproject.minilabs.dominic.sort.SortAlgorithms.BubbleSort(ToSortList.clone(), dataType.equals("integer")));
		GetFunctionRunTime(() -> rain.finalproject.minilabs.dominic.sort.SortAlgorithms.InsertionSort(ToSortList.clone(), dataType.equals("integer")));
		GetFunctionRunTime(() -> rain.finalproject.minilabs.dominic.sort.SortAlgorithms.SelectionSort(ToSortList.clone(), dataType.equals("integer")));

		double bubbleTime = GetFunctionRunTime(() -> rain.finalproject.minilabs.dominic.sort.SortAlgorithms.BubbleSort(ToSortList.clone(), dataType.equals("integer")));
		double insertionTime = GetFunctionRunTime(() -> rain.finalproject.minilabs.dominic.sort.SortAlgorithms.InsertionSort(ToSortList.clone(), dataType.equals("integer")));
		double selectionTime = GetFunctionRunTime(() -> rain.finalproject.minilabs.dominic.sort.SortAlgorithms.SelectionSort(ToSortList, dataType.equals("integer")));

		model.addAttribute("bubbleTime", bubbleTime);
		model.addAttribute("insertionTime", insertionTime);	// create new arrays
		model.addAttribute("selectionTime", selectionTime);

		if (bubbleTime < insertionTime && bubbleTime < selectionTime) {
			model.addAttribute("bestSort", "Bubble Sort");
		} else if (insertionTime < bubbleTime && insertionTime < selectionTime) {
			model.addAttribute("bestSort", "Insertion Sort");
		} else {
			model.addAttribute("bestSort", "Selection Sort");
		}

		ArrayList<String> SortedObjects = new ArrayList<>();
		String RawOutput = "";
		for (Object O : ToSortList) {
			SortedObjects.add(O.toString());
			RawOutput += O.toString() + ",";
		}
		if (RawOutput.length() > 0) {
			RawOutput = RawOutput.substring(0, RawOutput.length() - 1);	// remove the extra comma
		}

		model.addAttribute("results", SortedObjects);
		model.addAttribute("rawResults", RawOutput);

		return "Sort/SortDom";
	}

	//-- Inheritance minilab

	@Autowired
	private rain.finalproject.minilabs.dominic.inheritance.TeacherRepository TeacherRepos_InherD;

	@Autowired
	private rain.finalproject.minilabs.dominic.inheritance.StudentRepository StudentRepos_InherD;

	final ArrayList<String> ValidStudentSortTypes = new ArrayList(Arrays.asList("firstName", "lastName", "age", "grade"));
	final ArrayList<String> ValidTeacherSortTypes = new ArrayList(Arrays.asList("firstName", "lastName", "age", "subject"));

	@GetMapping("/InheritanceDom")
	public String inheritanceDom(@RequestParam(name = "studentSortType", required = false) String studentSortType,
								 @RequestParam(name = "hideStudent", required = false) boolean hideStudent,
								 @RequestParam(name = "teacherSortType", required = false) String teacherSortType,
								 @RequestParam(name = "hideTeacher", required = false) boolean hideTeacher,
								 Model model) {

		// Default data (add for the first time)
		if (TeacherRepos_InherD.count() == 0) {
			TeacherRepos_InherD.save(new Teacher("Dora", "Lene", 20, "english"));
			TeacherRepos_InherD.save(new Teacher("Duncan", "Trent", 55, "chemistry"));
			TeacherRepos_InherD.save(new Teacher("Neven", "Heli", 35, "math"));
			TeacherRepos_InherD.save(new Teacher("Bob", "Green", 44, "math"));

			StudentRepos_InherD.save(new Student("Claire", "Lee", 18, 12));
			StudentRepos_InherD.save(new Student("Jim", "Zed", 16, 10));
			StudentRepos_InherD.save(new Student("Kevin", "Smith", 14, 8));
		}

		// Get all data
		ArrayList<String> Data = new ArrayList<>();

		// Defaults
		studentSortType = (studentSortType != null && ValidStudentSortTypes.contains(studentSortType)) ? studentSortType : "firstName";
		teacherSortType = (teacherSortType != null && ValidTeacherSortTypes.contains(teacherSortType)) ? teacherSortType : "firstName";

		// Attributes
		model.addAttribute("hideStudent", hideStudent);
		model.addAttribute("hideTeacher", hideTeacher);
		model.addAttribute("studentSortType", studentSortType);
		model.addAttribute("teacherSortType", teacherSortType);

		// Student first
		if (!hideStudent) {
			ArrayList<Student> StudentList = (ArrayList<Student>) StudentRepos_InherD.findAll();
			switch (studentSortType) {
				case "firstName":
					StudentList.sort(Comparator.comparing(Student::getFirstName));
					break;
				case "lastName":
					StudentList.sort(Comparator.comparing(Student::getLastName));
					break;
				case "age":
					StudentList.sort(Comparator.comparing(Student::getAge));
					break;
				case "grade":
					StudentList.sort(Comparator.comparing(Student::getGrade));
					break;
			}
			for (Student S : StudentList) {
				Data.add(S.toString());
			}
		}

		if (!hideTeacher) {
			ArrayList<Teacher> TeacherList = (ArrayList<Teacher>) TeacherRepos_InherD.findAll();
			switch (teacherSortType) {
				case "firstName":
					TeacherList.sort(Comparator.comparing(Teacher::getFirstName));
					break;
				case "lastName":
					TeacherList.sort(Comparator.comparing(Teacher::getLastName));
					break;
				case "age":
					TeacherList.sort(Comparator.comparing(Teacher::getAge));
					break;
				case "subject":
					TeacherList.sort(Comparator.comparing(Teacher::getSubject));
					break;
			}
			for (Teacher S : TeacherList) {
				Data.add(S.toString());
			}
		}

		model.addAttribute("data", Data);

		return "Inheritance/InheritanceDom";
	}

	@PostMapping("/InheritanceDom")
	public String inheritanceDom(@RequestParam(name = "className", required = false) String className,
								 @RequestParam(name = "firstName", required = false) String firstName,
								 @RequestParam(name = "lastName", required = false) String lastName,
								 @RequestParam(name = "age", required = false) String age,
								 @RequestParam(name = "subject", required = false) String subject,
								 @RequestParam(name = "grade", required = false) String grade,
								 Model model) {


		if (className != null && (className.equals("student") || className.equals("teacher"))) {
			model.addAttribute("className", className);

			Pattern StringPattern = Pattern.compile("^[ A-Za-z]+$");

			if (firstName == null || !StringPattern.matcher(firstName.replace('-', ' ')).matches()) {
				model.addAttribute(className + "Error", "Please input a valid first name (a-z, spaces, and dashes).");
				return inheritanceDom("", false, "", false, model);
			}

			if (lastName == null || !StringPattern.matcher(lastName.replace('-', ' ')).matches()) {
				model.addAttribute(className + "Error", "Please input a valid last name (a-z, spaces, and dashes).");
				return inheritanceDom("", false, "", false, model);
			}

			int ageL;
			int gradeL;

			try {
				ageL = Integer.parseInt(age);
				if (ageL < 1 || ageL > 150)
					throw new Exception();
			} catch (Exception Ex) {
				model.addAttribute(className + "Error", "Please input a valid age (1 - 150).");
				return inheritanceDom("", false, "", false, model);
			}

			if (className.equals("student")) {
				try {
					gradeL = Integer.parseInt(grade);
					if (gradeL < 1 || gradeL > 12)
						throw new Exception();
				} catch (Exception Ex) {
					model.addAttribute(className + "Error", "Please input a valid grade (1 - 12).");
					return inheritanceDom("", false, "", false, model);
				}

				// Data confirmed
				StudentRepos_InherD.save(new Student(firstName, lastName, ageL, gradeL));
				model.addAttribute("studentMsg", "Successfully added a new student!");
			} else {
				if (subject == null || !StringPattern.matcher(subject.replace('-', ' ')).matches()) {
					model.addAttribute(className + "Error", "Please input a valid subject (a-z, spaces, and dashes).");
					return inheritanceDom("", false, "", false, model);
				}

				// Data confirmed
				TeacherRepos_InherD.save(new Teacher(firstName, lastName, ageL, subject));
				model.addAttribute("teacherMsg", "Successfully added a new teacher!");
			}
		}

		return inheritanceDom("", false, "", false, model);
	}

	//-- Recursion minilab

	@GetMapping("/RecursionDom")
	public String recursionDom(@RequestParam(name = "type", required = false) String type,
							   @RequestParam(name = "inputA", required = false) String inputA,
							   @RequestParam(name = "inputB", required = false) String inputB,
							   Model model) {

		// Default input
		long FactorialInput = 1;
		long FibonacciInput = 1;
		long GCDA = 5;
		long GCDB = 10;

		boolean DidError = false;

		// Ensure input is valid
		if (type != null && type.equals("factorial")) {
			try {
				FactorialInput = Long.parseLong(inputA);
				if (!(FactorialInput >= 0 && FactorialInput <= 20))
					throw new Exception();
			} catch (Throwable Ex) {
				model.addAttribute("fac_error", "Please input a valid integer between 0 and 20.");
				DidError = true;
			}
		} else if (type != null && type.equals("fibonacci")) {
			try {
				FibonacciInput = Long.parseLong(inputA);
				if (!(FibonacciInput >= 0 && FibonacciInput <= 93))
					throw new Exception();
			} catch (Exception Ex) {
				model.addAttribute("fib_error", "Please input a valid integer between 0 and 93.");
				DidError = true;
			}
		} else if (type != null && type.equals("gcd")) {
			try {
				GCDA = Long.parseLong(inputA);
				GCDB = Long.parseLong(inputB);
				if (!(GCDA > 0 && GCDB > 0))
					throw new Exception();
			} catch (Exception Ex) {
				model.addAttribute("gcd_error", "Please input two positive integer values.");
				DidError = true;
			}
		}

		// Return info
		model.addAttribute("type", type);

		// Factorial
		model.addAttribute("fac_input", FactorialInput);
		if (!(DidError && type.equals("factorial"))) {
			model.addAttribute("fac_result", rain.finalproject.minilabs.dominic.recursion.Factorial.ForLoop(FactorialInput));
			long finalFactorialInput = FactorialInput;
			model.addAttribute("fac_recursionTime", GetFunctionRunTime(() -> rain.finalproject.minilabs.dominic.recursion.Factorial.Recursion(finalFactorialInput)));
			model.addAttribute("fac_forLoopTime", GetFunctionRunTime(() -> rain.finalproject.minilabs.dominic.recursion.Factorial.ForLoop(finalFactorialInput)));
			model.addAttribute("fac_streamTime", GetFunctionRunTime(() -> rain.finalproject.minilabs.dominic.recursion.Factorial.Stream(finalFactorialInput)));
		}

		// Fibonacci
		model.addAttribute("fib_input", FibonacciInput);
		if (!(DidError && type.equals("fibonacci"))) {
			model.addAttribute("fib_result", rain.finalproject.minilabs.dominic.recursion.Fibonacci.ForLoop(FibonacciInput));
			long finalFibonacciInput = FibonacciInput;
			model.addAttribute("fib_recursionTime", FibonacciInput <= 46 ? GetFunctionRunTime(() -> rain.finalproject.minilabs.dominic.recursion.Fibonacci.Recursion(finalFibonacciInput)) : "SKIPPED (too long)");
			model.addAttribute("fib_forLoopTime", GetFunctionRunTime(() -> rain.finalproject.minilabs.dominic.recursion.Fibonacci.ForLoop(finalFibonacciInput)));
			model.addAttribute("fib_streamTime", GetFunctionRunTime(() -> rain.finalproject.minilabs.dominic.recursion.Fibonacci.Stream(finalFibonacciInput)));

		}

		// GCD
		model.addAttribute("gcd_inputA", GCDA);
		model.addAttribute("gcd_inputB", GCDB);
		if (!(DidError && type.equals("gcd"))) {
			model.addAttribute("gcd_result", rain.finalproject.minilabs.dominic.recursion.GCD.Recursion(GCDA, GCDB));
			long finalGCDA = GCDA;
			long finalGCDB = GCDB;
			model.addAttribute("gcd_recursionTime", GetFunctionRunTime(() -> rain.finalproject.minilabs.dominic.recursion.GCD.Recursion(finalGCDA, finalGCDB)));
			model.addAttribute("gcd_forLoopTime", GetFunctionRunTime(() -> rain.finalproject.minilabs.dominic.recursion.GCD.ForLoop(finalGCDA, finalGCDB)));
			model.addAttribute("gcd_streamTime", GetFunctionRunTime(() -> rain.finalproject.minilabs.dominic.recursion.GCD.Stream(finalGCDA, finalGCDB)));
		}

		return "Recursion/RecursionDom";
	}
}
