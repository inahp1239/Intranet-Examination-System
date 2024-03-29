/*
 * package in.ac.ksrmce.exam;
 * 
 * import jakarta.servlet.ServletException; import
 * jakarta.servlet.annotation.WebServlet; import
 * jakarta.servlet.http.HttpServlet; import
 * jakarta.servlet.http.HttpServletRequest; import
 * jakarta.servlet.http.HttpServletResponse; import
 * jakarta.servlet.http.HttpSession;
 * 
 * import java.io.IOException; import java.util.Arrays; import java.util.List;
 * 
 * import in.ac.ksrmce.config.questions_config.QuestionsDao; import
 * in.ac.ksrmce.config.questions_config.QuestionsEntity;
 * 
 * 
 * 
 * 
 * @WebServlet("/examTestStart") public class StartExam extends HttpServlet {
 * private static final long serialVersionUID = 1L;
 * 
 * public StartExam() { super(); }
 * 
 * 
 * static int totalQuestions = QuestionsDao.count()+1;
 * 
 * // int qNum = 0 ; // int[] qNums = new int[totalQuestions];
 * 
 * int[] buttons_color = new int[totalQuestions];
 * 
 * boolean mO = false; boolean pO = false; boolean cO = false;
 * 
 * static List<QuestionsEntity> questions = null;
 * 
 * 
 * static boolean oneTime = true; static boolean singleTime = true;
 * 
 * 
 * static String sub= "maths";
 * 
 * static int mathsC = QuestionsDao.countQuestions("maths"); static int physicsC
 * = QuestionsDao.countQuestions("physics"); static int chemistryC =
 * QuestionsDao.countQuestions("chemistry");
 * 
 * 
 * static int[] mathsSelected = new int[mathsC]; static int[] physicsSelected =
 * new int[physicsC]; static int[] chemistrySelected = new int[chemistryC];
 * 
 * static int[] btnmathsSelected = new int[mathsC]; static int[]
 * btnphysicsSelected = new int[physicsC]; static int[] btnchemistrySelected =
 * new int[chemistryC];
 * 
 * 
 * 
 * String[] selectedOptions = new String[totalQuestions];
 * 
 * static int notVisited = QuestionsDao.count()-1; // notVisited = 0 static int
 * answered = 0; // answered = 2 static int notAnswered = 0; // notAnswered = 1
 * static int markForReview = 0; // markForReview = 3 static int
 * answeredMarkForReview = 0; // answeredMarkForReview = 4 static int totalMarks
 * = 0;
 * 
 * 
 * protected void doGet(HttpServletRequest request, HttpServletResponse
 * response) throws ServletException, IOException {
 * 
 * int currentQuestionIndex = 0; int button = 0;
 * 
 * HttpSession session = request.getSession(); String reference_number =
 * (String)session.getAttribute("reference_number");
 * 
 * String selectedSubject = request.getParameter("subject"); if(selectedSubject
 * != null) { sub = selectedSubject; singleTime = true; }
 * 
 * String questionIndexParam = request.getParameter("questionIndex"); if
 * (questionIndexParam != null && !questionIndexParam.isEmpty()) {
 * currentQuestionIndex = Integer.parseInt(questionIndexParam); }
 * 
 * if(currentQuestionIndex > 0) { button =
 * Integer.parseInt(request.getParameter("button"));
 * System.out.println("buttons : : "+ button); }
 * 
 * String selectedOption = request.getParameter("option"); int selectedOptionInt
 * = 0; if(selectedOption != null) { selectedOptionInt =
 * Integer.parseInt(selectedOption); }else if(selectedOption == null) {
 * selectedOptionInt = 0 ; }
 * 
 * if(currentQuestionIndex != 0) { mathsSelected[currentQuestionIndex-1] =
 * selectedOptionInt; } if(sub == "physics" && currentQuestionIndex != 0) {
 * physicsSelected[currentQuestionIndex-1] = selectedOptionInt; } if(sub ==
 * "chemistry" && currentQuestionIndex != 0) {
 * chemistrySelected[currentQuestionIndex-1] = selectedOptionInt; }
 * 
 * 
 * buttons_color[currentQuestionIndex] = 1; if(selectedOption != null &&
 * selectedOptions[currentQuestionIndex+1] == null && button==1) { totalMarks++;
 * answered++; notVisited--;
 * 
 * if(sub == "maths") { btnmathsSelected[currentQuestionIndex-1] = 2; }else
 * if(sub == "physics" && currentQuestionIndex != 0) {
 * btnphysicsSelected[currentQuestionIndex-1] = 2; }else if(sub == "chemistry"
 * && currentQuestionIndex != 0) { btnchemistrySelected[currentQuestionIndex-1]
 * = 2; }
 * 
 * }else if(selectedOption == null && currentQuestionIndex!=0 && button == 1) {
 * notAnswered++; notVisited--; if(sub == "maths") {
 * btnmathsSelected[currentQuestionIndex-1] = 1; }else if(sub == "physics" &&
 * currentQuestionIndex != 0) { btnphysicsSelected[currentQuestionIndex-1] = 1;
 * }else if(sub == "chemistry" && currentQuestionIndex != 0) {
 * btnchemistrySelected[currentQuestionIndex-1] = 1; } }else if(selectedOption
 * != null && selectedOptions[currentQuestionIndex+1] == null && button==2) {
 * totalMarks++; answeredMarkForReview++; notVisited--;
 * 
 * if(sub == "maths") { btnmathsSelected[currentQuestionIndex-1] = 4; }else
 * if(sub == "physics" && currentQuestionIndex != 0) {
 * btnphysicsSelected[currentQuestionIndex-1] = 4; }else if(sub == "chemistry"
 * && currentQuestionIndex != 0) { btnchemistrySelected[currentQuestionIndex-1]
 * = 4; }
 * 
 * }else if(selectedOption == null && currentQuestionIndex!=0 && button == 2) {
 * buttons_color[currentQuestionIndex] = 1 ; markForReview++; notVisited--;
 * if(sub == "maths") { btnmathsSelected[currentQuestionIndex-1] = 3; }else
 * if(sub == "physics" && currentQuestionIndex != 0) {
 * btnphysicsSelected[currentQuestionIndex-1] = 3; }else if(sub == "chemistry"
 * && currentQuestionIndex != 0) { btnchemistrySelected[currentQuestionIndex-1]
 * = 3; } }
 * 
 * 
 * 
 * // store the selected answered if(currentQuestionIndex ==
 * QuestionsDao.countQuestions(sub)) { String array = "[ ]"; if(sub == "maths")
 * { array = Arrays.toString(mathsSelected); }else if(sub == "physics") { array
 * = Arrays.toString(physicsSelected); }else if(sub == "chemistry") { array =
 * Arrays.toString(chemistrySelected); }
 * QuestionsDao.saveMarks(reference_number,array , sub); }
 * 
 * if(sub == "chemistry" && currentQuestionIndex == chemistryC) { pO=false; }
 * if(sub == "physics" && currentQuestionIndex == physicsC && mO == true) {
 * pO=true; }
 * 
 * // without selecting subject if( mathsC == currentQuestionIndex ) { sub =
 * "physics"; mO = true; oneTime = true; singleTime = true; answered = 0;
 * notAnswered = 0; currentQuestionIndex = 0 ; }else if( physicsC ==
 * currentQuestionIndex && mO == true && pO == true) { sub="chemistry"; oneTime
 * = true; singleTime = true; answered = 0; notAnswered = 0;
 * currentQuestionIndex = 0 ; }
 * 
 * if(singleTime) { for(int i = 0 ; i < buttons_color.length ; i++) {
 * buttons_color[i]=0; } for(int i = 0 ; i < btnmathsSelected.length ; i++) {
 * buttons_color[i]=0; } for(int i = 0 ; i < btnphysicsSelected.length ; i++) {
 * buttons_color[i]=0; } for(int i = 0 ; i < btnchemistrySelected.length ; i++)
 * { buttons_color[i]=0; } questions =
 * QuestionsDao.listrandomQuestions(sub,reference_number); singleTime = false; }
 * 
 * 
 * String selectedQuestion = request.getParameter("num"); if(selectedQuestion !=
 * null) { currentQuestionIndex = Integer.parseInt(selectedQuestion); }
 * System.out.println("selectedQuestion : "+ selectedQuestion);
 * System.out.println("currentQuestionIndex : "+ currentQuestionIndex);
 * 
 * if(sub == "chemistry" && currentQuestionIndex == chemistryC) {
 * currentQuestionIndex = chemistryC - 1; }
 * 
 * 
 * // setting the next question and selected question if (currentQuestionIndex
 * >= 0 && currentQuestionIndex <= mathsC) { QuestionsEntity currentQuestion =
 * questions.get(currentQuestionIndex);
 * 
 * currentQuestion.setId(currentQuestion.getId()); currentQuestion.setQuestion(
 * currentQuestion.getQuestion()); currentQuestion.setOption_one(
 * currentQuestion.getOption_one()); currentQuestion.setOption_two(
 * currentQuestion.getOption_two()); currentQuestion.setOption_three(
 * currentQuestion.getOption_three()); currentQuestion.setOption_four(
 * currentQuestion.getOption_four()); request.setAttribute("question",
 * currentQuestion); }
 * 
 * 
 * 
 * 
 * for(int b : buttons_color) { System.out.print(b+"  "); }
 * System.out.print("\nmaths : "); for(int b : mathsSelected) {
 * System.out.print(b+"  "); } System.out.print("\nphysics : "); for(int b :
 * physicsSelected) { System.out.print(b+"  "); }
 * System.out.print("\nchemistry : "); for(int b : chemistrySelected) {
 * System.out.print(b+"  "); }
 * 
 * 
 * 
 * 
 * System.out.print("\nmaths : "); for(int b : btnmathsSelected) {
 * System.out.print(b+"  "); } System.out.print("\nphysics : "); for(int b :
 * btnphysicsSelected) { System.out.print(b+"  "); }
 * System.out.print("\nchemistry : "); for(int b : btnchemistrySelected) {
 * System.out.print(b+"  "); }
 * 
 * 
 * if(currentQuestionIndex == 0) { answered = 0; }
 * 
 * 
 * if(oneTime) { notAnswered++; oneTime = false; }else if(currentQuestionIndex
 * == QuestionsDao.countQuestions(sub)) { notAnswered--; oneTime=true; }
 * 
 * 
 * System.out.println("Subject : : " + sub);
 * 
 * String s = "";
 * 
 * if(sub == "maths" && currentQuestionIndex != 0) { s =
 * mathsSelected[currentQuestionIndex]+""; }else if(sub == "physics" &&
 * currentQuestionIndex != 0) { s = physicsSelected[currentQuestionIndex]+"";
 * }else if(sub == "chemistry" && currentQuestionIndex != 0) { s =
 * chemistrySelected[currentQuestionIndex]+""; }
 * 
 * if(sub == "maths") { request.setAttribute("buttons_color", btnmathsSelected);
 * System.out.println("btn mathsl : : "+ Arrays.toString(btnmathsSelected));
 * }else if(sub == "physics" ) { request.setAttribute("buttons_color",
 * btnphysicsSelected); System.out.println("btn physics : : "+
 * Arrays.toString(btnphysicsSelected)); }else if(sub == "chemistry" ) {
 * request.setAttribute("buttons_color", btnchemistrySelected);
 * System.out.println("btn chemistry : : "+
 * Arrays.toString(btnchemistrySelected)); }
 * 
 * 
 * 
 * request.setAttribute("answered", answered);
 * request.setAttribute("notAnswered", notAnswered);
 * request.setAttribute("markForReview", markForReview);
 * request.setAttribute("answeredMarkForReview", answeredMarkForReview);
 * request.setAttribute("sub", sub); request.setAttribute("s", s);
 * request.setAttribute("count", questions.size());
 * request.setAttribute("currentQuestionIndex", currentQuestionIndex);
 * 
 * request.getRequestDispatcher("/html/exam/quizTestingQuestioins.jsp").forward(
 * request, response); }
 * 
 * 
 * protected void doPost(HttpServletRequest request, HttpServletResponse
 * response) throws ServletException, IOException { doGet(request, response); }
 * 
 * }
 */







package in.ac.ksrmce.exam;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import in.ac.ksrmce.config.questions_config.QuestionsDao;
import in.ac.ksrmce.config.questions_config.QuestionsEntity;

@WebServlet("/examTestStart")
public class StartExam extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public StartExam() {
        super();
    }

    static int totalQuestions = QuestionsDao.count() + 1;

    int[] buttons_color = new int[totalQuestions];

    boolean mO = false;
    boolean pO = false;
    boolean cO = false;

    static List<QuestionsEntity> questions = null;

    static boolean oneTime = true;
    static boolean singleTime = true;
    static boolean absolute = true;

    static String sub = "maths";

    static int mathsC = QuestionsDao.countQuestions("maths");
    static int physicsC = QuestionsDao.countQuestions("physics");
    static int chemistryC = QuestionsDao.countQuestions("chemistry");

    static int[] mathsSelected = new int[mathsC];
    static int[] physicsSelected = new int[physicsC];
    static int[] chemistrySelected = new int[chemistryC];

    static int[] btnmathsSelected = new int[mathsC];
    static int[] btnphysicsSelected = new int[physicsC];
    static int[] btnchemistrySelected = new int[chemistryC];

    String[] selectedOptions = new String[totalQuestions];

    static int notVisited = QuestionsDao.count() - 1;                // notVisited = 0
    static int answered = 0;                                    	 // answered = 2
    static int notAnswered = 0;                                		 // notAnswered = 1
    static int markForReview = 0;                           		 // markForReview = 3
    static int answeredMarkForReview = 0;                   		 // answeredMarkForReview = 4
    static int totalMarks = 0;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        int currentQuestionIndex = 0;
        int button = 0;

        HttpSession session = request.getSession();
        String reference_number = (String) session.getAttribute("reference_number");
        
        
        

        String selectedSubject = request.getParameter("subject");
        if (selectedSubject != null) {
            sub = selectedSubject;
            singleTime = true;
        }

        String questionIndexParam = request.getParameter("questionIndex");
        if (questionIndexParam != null && !questionIndexParam.isEmpty()) {
            currentQuestionIndex = Integer.parseInt(questionIndexParam);
        }

        if (currentQuestionIndex > 0) {
            button = Integer.parseInt(request.getParameter("button"));
            
            absolute = QuestionsDao.isTrue(reference_number);
//            System.out.println("buttons : : " + button);
        }

        String selectedOption = request.getParameter("option");
        int selectedOptionInt = 0;
        if (selectedOption != null) {
            selectedOptionInt = Integer.parseInt(selectedOption);
        } else if (selectedOption == null) {
            selectedOptionInt = 0;
        }

        if (currentQuestionIndex != 0) {
            mathsSelected[currentQuestionIndex - 1] = selectedOptionInt;
        }
        if (sub.equals("physics") && currentQuestionIndex != 0) {
            physicsSelected[currentQuestionIndex - 1] = selectedOptionInt;
        }
        if (sub.equals("chemistry") && currentQuestionIndex != 0) {
            chemistrySelected[currentQuestionIndex - 1] = selectedOptionInt;
        }

//        buttons_color[currentQuestionIndex] = 1;
        if (selectedOption != null && selectedOptions[currentQuestionIndex + 1] == null && button == 1) {
            answered++;
            notVisited--;

//            if(btnmathsSelected[currentQuestionIndex ] == 4 || btnphysicsSelected[currentQuestionIndex ] == 4 || btnchemistrySelected[currentQuestionIndex ] == 4 ) {
//            	answeredMarkForReview--;
//            }
//            if(btnmathsSelected[currentQuestionIndex ] == 3 || btnphysicsSelected[currentQuestionIndex ] == 3 || btnchemistrySelected[currentQuestionIndex ] == 3) {
//            	markForReview--;
//            }
//            if(btnmathsSelected[currentQuestionIndex ] == 1 || btnphysicsSelected[currentQuestionIndex ] == 1 || btnchemistrySelected[currentQuestionIndex ] == 1) {
//            	notAnswered--;
//            }
                        
            if (sub.equals("maths")) {
                btnmathsSelected[currentQuestionIndex - 1] = 2;
            } else if (sub.equals("physics") && currentQuestionIndex != 0) {
                btnphysicsSelected[currentQuestionIndex - 1] = 2;
            } else if (sub.equals("chemistry") && currentQuestionIndex != 0) {
                btnchemistrySelected[currentQuestionIndex - 1] = 2;
            }

        } else if (selectedOption == null && currentQuestionIndex != 0 && button == 1) {
            notAnswered++;
            notVisited--;
            if (sub.equals("maths")) {
                btnmathsSelected[currentQuestionIndex - 1] = 1;
            } else if (sub.equals("physics") && currentQuestionIndex != 0) {
                btnphysicsSelected[currentQuestionIndex - 1] = 1;
            } else if (sub.equals("chemistry") && currentQuestionIndex != 0) {
                btnchemistrySelected[currentQuestionIndex - 1] = 1;
            }
        } else if (selectedOption != null && selectedOptions[currentQuestionIndex + 1] == null && button == 2) {
            answeredMarkForReview++;
            notVisited--;

            if (sub.equals("maths")) {
                btnmathsSelected[currentQuestionIndex - 1] = 4;
            } else if (sub.equals("physics") && currentQuestionIndex != 0) {
                btnphysicsSelected[currentQuestionIndex - 1] = 4;
            } else if (sub.equals("chemistry") && currentQuestionIndex != 0) {
                btnchemistrySelected[currentQuestionIndex - 1] = 4;
            }

        } else if (selectedOption == null && currentQuestionIndex != 0 && button == 2) {
            buttons_color[currentQuestionIndex] = 1;
            markForReview++;
            notVisited--;
            if (sub.equals("maths")) {
                btnmathsSelected[currentQuestionIndex - 1] = 3;
            } else if (sub.equals("physics") && currentQuestionIndex != 0) {
                btnphysicsSelected[currentQuestionIndex - 1] = 3;
            } else if (sub.equals("chemistry") && currentQuestionIndex != 0) {
                btnchemistrySelected[currentQuestionIndex - 1] = 3;
            }
        }

        // store the selected answers and button colors
        if (currentQuestionIndex == QuestionsDao.countQuestions(sub)) {
            String array = "[ ]";
            String btnColor = "";
            if (sub.equals("maths")) {
                array = Arrays.toString(mathsSelected);
                btnColor = Arrays.toString(btnmathsSelected);
            } else if (sub.equals("physics")) {
                array = Arrays.toString(physicsSelected);
                btnColor = Arrays.toString(btnphysicsSelected);
            } else if (sub.equals("chemistry")) {
                array = Arrays.toString(chemistrySelected);
                btnColor = Arrays.toString(btnchemistrySelected);
            }
            QuestionsDao.saveMarks(reference_number, array, sub,btnColor);
        }

        if (sub.equals("chemistry") && currentQuestionIndex == chemistryC) {
            pO = false;
        }
        if (sub.equals("physics") && currentQuestionIndex == physicsC && mO) {
            pO = true;
        }

        // without selecting subject
        if (mathsC == currentQuestionIndex) {
            sub = "physics";
            mO = true;
            oneTime = true;
            singleTime = true;
            answered = 0;
            notAnswered = 0;
            markForReview = 0;
            answeredMarkForReview = 0;
            currentQuestionIndex = 0;
        } else if (physicsC == currentQuestionIndex && mO && pO) {
            sub = "chemistry";
            oneTime = true;
            singleTime = true;
            answered = 0;
            notAnswered = 0;
            markForReview = 0;
            answeredMarkForReview = 0;
            currentQuestionIndex = 0;
        }

        if (singleTime) {
            questions = QuestionsDao.listrandomQuestions(sub, reference_number);
            singleTime = false;
        }
        
        if(absolute) {
        	for (int i = 0; i < btnmathsSelected.length; i++) {
                btnmathsSelected[i] = 0;
            }
            for (int i = 0; i < btnphysicsSelected.length; i++) {
                btnphysicsSelected[i] = 0;
            }
            for (int i = 0; i < btnchemistrySelected.length; i++) {
                btnchemistrySelected[i] = 0;
            }
            
            btnmathsSelected = QuestionsDao.subjectColorOptionsGet("maths", reference_number);
            mathsSelected = QuestionsDao.subjectOptionsGet("maths", reference_number);
            
            btnphysicsSelected = QuestionsDao.subjectColorOptionsGet("physics", reference_number);
            physicsSelected = QuestionsDao.subjectOptionsGet("physics", reference_number);
            
            btnchemistrySelected = QuestionsDao.subjectColorOptionsGet("chemistry", reference_number);
            chemistrySelected = QuestionsDao.subjectOptionsGet("chemistry", reference_number);
        }

        String selectedQuestion = request.getParameter("num");
        if (selectedQuestion != null) {
            currentQuestionIndex = Integer.parseInt(selectedQuestion);
        }

        if (sub.equals("chemistry") && currentQuestionIndex == chemistryC) {
            currentQuestionIndex = chemistryC - 1;
        }

        // setting the next question and selected question
        if (currentQuestionIndex >= 0 && currentQuestionIndex <= mathsC) {
            QuestionsEntity currentQuestion = questions.get(currentQuestionIndex);

            currentQuestion.setId(currentQuestion.getId());
            currentQuestion.setQuestion(currentQuestion.getQuestion());
            currentQuestion.setOption_one(currentQuestion.getOption_one());
            currentQuestion.setOption_two(currentQuestion.getOption_two());
            currentQuestion.setOption_three(currentQuestion.getOption_three());
            currentQuestion.setOption_four(currentQuestion.getOption_four());
            request.setAttribute("question", currentQuestion);
        }

        if (currentQuestionIndex == 0) {
            answered = 0;
        }

        if (oneTime) {
            notAnswered++;
            oneTime = false;
        } else if (currentQuestionIndex == QuestionsDao.countQuestions(sub)) {
            notAnswered--;
            oneTime = true;
        }

        String s = "";

        if (sub.equals("maths") && currentQuestionIndex != 0) {
            s = Integer.toString(mathsSelected[currentQuestionIndex]);
        } else if (sub.equals("physics") && currentQuestionIndex != 0) {
            s = Integer.toString(physicsSelected[currentQuestionIndex]);
        } else if (sub.equals("chemistry") && currentQuestionIndex != 0) {
            s = Integer.toString(chemistrySelected[currentQuestionIndex]);
        }

        if (sub.equals("maths")) {
            request.setAttribute("buttons_color", btnmathsSelected);
        } else if (sub.equals("physics")) {
            request.setAttribute("buttons_color", btnphysicsSelected);
        } else if (sub.equals("chemistry")) {
            request.setAttribute("buttons_color", btnchemistrySelected);
        }
        
        for(int b : buttons_color) { System.out.print(b+"  "); }
        System.out.print("\nmaths : "); 
        for(int b : mathsSelected) {System.out.print(b+"  "); } 
        System.out.print("\nphysics : "); 
        for(int b :physicsSelected) { System.out.print(b+"  "); }
        System.out.print("\nchemistry : "); 
        for(int b : chemistrySelected) {System.out.print(b+"  "); }

        System.out.print("\nmaths : "); 
        for(int b : btnmathsSelected) {System.out.print(b+"  "); } 
        System.out.print("\nphysics : "); 
        for(int b : btnphysicsSelected) { System.out.print(b+"  "); }
        System.out.print("\nchemistry : "); 
        for(int b : btnchemistrySelected) {System.out.print(b+"  "); }

        request.setAttribute("answered", answered);
        request.setAttribute("notAnswered", notAnswered);
        request.setAttribute("markForReview", markForReview);
        request.setAttribute("answeredMarkForReview", answeredMarkForReview);
        request.setAttribute("sub", sub);
        request.setAttribute("s", s);
        request.setAttribute("count", questions.size());
        request.setAttribute("currentQuestionIndex", currentQuestionIndex);

        request.getRequestDispatcher("/html/exam/quizTestingQuestioins.jsp").forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }

}
