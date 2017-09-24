/**
 * 
 */
package Server;

/**
 * @author Sebastien
 *
 */
public class MsgParser {

	public boolean sqlParser(String context) {
		String upperContext = context.toUpperCase();
		// parsing for SQL words
		Expression exp1 = new TerminalExpression(" SELECT ");
		Expression exp2 = new TerminalExpression(" UNION ");
		Expression exp3 = new TerminalExpression(" INSERT ");
		Expression exp4 = new TerminalExpression(" UPDATE ");
		Expression exp5 = new TerminalExpression(" GROUP ");
		Expression exp6 = new TerminalExpression(" JOIN ");
		Expression exp7 = new TerminalExpression(" OR ");
		Expression exp8 = new TerminalExpression(" AND ");
		
		Expression or1 = new OrExpression(exp1,exp2);
		Expression or2 = new OrExpression(exp3,exp4);
		Expression or3 = new OrExpression(exp5,exp6);
		Expression or4 = new OrExpression(exp7,exp8);
		
		Expression or5 = new OrExpression(or1,or2);
		Expression or6 = new OrExpression(or3,or4);
		
		Expression orfinal = new OrExpression(or5,or6);
		// return true if nothing is found
		return !orfinal.interpret(upperContext);
	}
	
	public boolean symbolParser(String context) {
		// parsing for SQL words
		Expression exp1 = new TerminalExpression("\\");
		Expression exp2 = new TerminalExpression("(");
		Expression exp3 = new TerminalExpression(")");
		Expression exp4 = new TerminalExpression("/");
		Expression exp5 = new TerminalExpression("%");
		Expression exp6 = new TerminalExpression("&");
		Expression exp7 = new TerminalExpression("@");
		Expression exp8 = new TerminalExpression("|");
		
		Expression or1 = new OrExpression(exp1,exp2);
		Expression or2 = new OrExpression(exp3,exp4);
		Expression or3 = new OrExpression(exp5,exp6);
		Expression or4 = new OrExpression(exp7,exp8);
		
		Expression or5 = new OrExpression(or1,or2);
		Expression or6 = new OrExpression(or3,or4);
		
		Expression orfinal = new OrExpression(or5,or6);
		// return true if nothing is found
		return !orfinal.interpret(context);
	}
}
