// Generated from D:/SKOLE/Guthub/P4/parser\Minespeak.g4 by ANTLR 4.9.1
import org.antlr.v4.runtime.tree.ParseTreeListener;

/**
 * This interface defines a complete listener for a parse tree produced by
 * {@link MinespeakParser}.
 */
public interface MinespeakListener extends ParseTreeListener {
	/**
	 * Enter a parse tree produced by {@link MinespeakParser#prog}.
	 * @param ctx the parse tree
	 */
	void enterProg(MinespeakParser.ProgContext ctx);
	/**
	 * Exit a parse tree produced by {@link MinespeakParser#prog}.
	 * @param ctx the parse tree
	 */
	void exitProg(MinespeakParser.ProgContext ctx);
	/**
	 * Enter a parse tree produced by {@link MinespeakParser#blocks}.
	 * @param ctx the parse tree
	 */
	void enterBlocks(MinespeakParser.BlocksContext ctx);
	/**
	 * Exit a parse tree produced by {@link MinespeakParser#blocks}.
	 * @param ctx the parse tree
	 */
	void exitBlocks(MinespeakParser.BlocksContext ctx);
	/**
	 * Enter a parse tree produced by {@link MinespeakParser#block}.
	 * @param ctx the parse tree
	 */
	void enterBlock(MinespeakParser.BlockContext ctx);
	/**
	 * Exit a parse tree produced by {@link MinespeakParser#block}.
	 * @param ctx the parse tree
	 */
	void exitBlock(MinespeakParser.BlockContext ctx);
	/**
	 * Enter a parse tree produced by {@link MinespeakParser#mcFunc}.
	 * @param ctx the parse tree
	 */
	void enterMcFunc(MinespeakParser.McFuncContext ctx);
	/**
	 * Exit a parse tree produced by {@link MinespeakParser#mcFunc}.
	 * @param ctx the parse tree
	 */
	void exitMcFunc(MinespeakParser.McFuncContext ctx);
	/**
	 * Enter a parse tree produced by {@link MinespeakParser#func}.
	 * @param ctx the parse tree
	 */
	void enterFunc(MinespeakParser.FuncContext ctx);
	/**
	 * Exit a parse tree produced by {@link MinespeakParser#func}.
	 * @param ctx the parse tree
	 */
	void exitFunc(MinespeakParser.FuncContext ctx);
	/**
	 * Enter a parse tree produced by {@link MinespeakParser#params}.
	 * @param ctx the parse tree
	 */
	void enterParams(MinespeakParser.ParamsContext ctx);
	/**
	 * Exit a parse tree produced by {@link MinespeakParser#params}.
	 * @param ctx the parse tree
	 */
	void exitParams(MinespeakParser.ParamsContext ctx);
	/**
	 * Enter a parse tree produced by {@link MinespeakParser#param}.
	 * @param ctx the parse tree
	 */
	void enterParam(MinespeakParser.ParamContext ctx);
	/**
	 * Exit a parse tree produced by {@link MinespeakParser#param}.
	 * @param ctx the parse tree
	 */
	void exitParam(MinespeakParser.ParamContext ctx);
	/**
	 * Enter a parse tree produced by {@link MinespeakParser#funcBody}.
	 * @param ctx the parse tree
	 */
	void enterFuncBody(MinespeakParser.FuncBodyContext ctx);
	/**
	 * Exit a parse tree produced by {@link MinespeakParser#funcBody}.
	 * @param ctx the parse tree
	 */
	void exitFuncBody(MinespeakParser.FuncBodyContext ctx);
	/**
	 * Enter a parse tree produced by {@link MinespeakParser#retVal}.
	 * @param ctx the parse tree
	 */
	void enterRetVal(MinespeakParser.RetValContext ctx);
	/**
	 * Exit a parse tree produced by {@link MinespeakParser#retVal}.
	 * @param ctx the parse tree
	 */
	void exitRetVal(MinespeakParser.RetValContext ctx);
	/**
	 * Enter a parse tree produced by {@link MinespeakParser#stmnts}.
	 * @param ctx the parse tree
	 */
	void enterStmnts(MinespeakParser.StmntsContext ctx);
	/**
	 * Exit a parse tree produced by {@link MinespeakParser#stmnts}.
	 * @param ctx the parse tree
	 */
	void exitStmnts(MinespeakParser.StmntsContext ctx);
	/**
	 * Enter a parse tree produced by {@link MinespeakParser#stmnt}.
	 * @param ctx the parse tree
	 */
	void enterStmnt(MinespeakParser.StmntContext ctx);
	/**
	 * Exit a parse tree produced by {@link MinespeakParser#stmnt}.
	 * @param ctx the parse tree
	 */
	void exitStmnt(MinespeakParser.StmntContext ctx);
	/**
	 * Enter a parse tree produced by {@link MinespeakParser#loop}.
	 * @param ctx the parse tree
	 */
	void enterLoop(MinespeakParser.LoopContext ctx);
	/**
	 * Exit a parse tree produced by {@link MinespeakParser#loop}.
	 * @param ctx the parse tree
	 */
	void exitLoop(MinespeakParser.LoopContext ctx);
	/**
	 * Enter a parse tree produced by {@link MinespeakParser#doWhile}.
	 * @param ctx the parse tree
	 */
	void enterDoWhile(MinespeakParser.DoWhileContext ctx);
	/**
	 * Exit a parse tree produced by {@link MinespeakParser#doWhile}.
	 * @param ctx the parse tree
	 */
	void exitDoWhile(MinespeakParser.DoWhileContext ctx);
	/**
	 * Enter a parse tree produced by {@link MinespeakParser#whileStmnt}.
	 * @param ctx the parse tree
	 */
	void enterWhileStmnt(MinespeakParser.WhileStmntContext ctx);
	/**
	 * Exit a parse tree produced by {@link MinespeakParser#whileStmnt}.
	 * @param ctx the parse tree
	 */
	void exitWhileStmnt(MinespeakParser.WhileStmntContext ctx);
	/**
	 * Enter a parse tree produced by {@link MinespeakParser#foreach}.
	 * @param ctx the parse tree
	 */
	void enterForeach(MinespeakParser.ForeachContext ctx);
	/**
	 * Exit a parse tree produced by {@link MinespeakParser#foreach}.
	 * @param ctx the parse tree
	 */
	void exitForeach(MinespeakParser.ForeachContext ctx);
	/**
	 * Enter a parse tree produced by {@link MinespeakParser#forStmnt}.
	 * @param ctx the parse tree
	 */
	void enterForStmnt(MinespeakParser.ForStmntContext ctx);
	/**
	 * Exit a parse tree produced by {@link MinespeakParser#forStmnt}.
	 * @param ctx the parse tree
	 */
	void exitForStmnt(MinespeakParser.ForStmntContext ctx);
	/**
	 * Enter a parse tree produced by {@link MinespeakParser#ifStmnt}.
	 * @param ctx the parse tree
	 */
	void enterIfStmnt(MinespeakParser.IfStmntContext ctx);
	/**
	 * Exit a parse tree produced by {@link MinespeakParser#ifStmnt}.
	 * @param ctx the parse tree
	 */
	void exitIfStmnt(MinespeakParser.IfStmntContext ctx);
	/**
	 * Enter a parse tree produced by {@link MinespeakParser#dcls}.
	 * @param ctx the parse tree
	 */
	void enterDcls(MinespeakParser.DclsContext ctx);
	/**
	 * Exit a parse tree produced by {@link MinespeakParser#dcls}.
	 * @param ctx the parse tree
	 */
	void exitDcls(MinespeakParser.DclsContext ctx);
	/**
	 * Enter a parse tree produced by {@link MinespeakParser#instan}.
	 * @param ctx the parse tree
	 */
	void enterInstan(MinespeakParser.InstanContext ctx);
	/**
	 * Exit a parse tree produced by {@link MinespeakParser#instan}.
	 * @param ctx the parse tree
	 */
	void exitInstan(MinespeakParser.InstanContext ctx);
	/**
	 * Enter a parse tree produced by {@link MinespeakParser#expr}.
	 * @param ctx the parse tree
	 */
	void enterExpr(MinespeakParser.ExprContext ctx);
	/**
	 * Exit a parse tree produced by {@link MinespeakParser#expr}.
	 * @param ctx the parse tree
	 */
	void exitExpr(MinespeakParser.ExprContext ctx);
	/**
	 * Enter a parse tree produced by {@link MinespeakParser#factor}.
	 * @param ctx the parse tree
	 */
	void enterFactor(MinespeakParser.FactorContext ctx);
	/**
	 * Exit a parse tree produced by {@link MinespeakParser#factor}.
	 * @param ctx the parse tree
	 */
	void exitFactor(MinespeakParser.FactorContext ctx);
	/**
	 * Enter a parse tree produced by {@link MinespeakParser#funcCall}.
	 * @param ctx the parse tree
	 */
	void enterFuncCall(MinespeakParser.FuncCallContext ctx);
	/**
	 * Exit a parse tree produced by {@link MinespeakParser#funcCall}.
	 * @param ctx the parse tree
	 */
	void exitFuncCall(MinespeakParser.FuncCallContext ctx);
	/**
	 * Enter a parse tree produced by {@link MinespeakParser#assign}.
	 * @param ctx the parse tree
	 */
	void enterAssign(MinespeakParser.AssignContext ctx);
	/**
	 * Exit a parse tree produced by {@link MinespeakParser#assign}.
	 * @param ctx the parse tree
	 */
	void exitAssign(MinespeakParser.AssignContext ctx);
	/**
	 * Enter a parse tree produced by {@link MinespeakParser#type}.
	 * @param ctx the parse tree
	 */
	void enterType(MinespeakParser.TypeContext ctx);
	/**
	 * Exit a parse tree produced by {@link MinespeakParser#type}.
	 * @param ctx the parse tree
	 */
	void exitType(MinespeakParser.TypeContext ctx);
	/**
	 * Enter a parse tree produced by {@link MinespeakParser#literal}.
	 * @param ctx the parse tree
	 */
	void enterLiteral(MinespeakParser.LiteralContext ctx);
	/**
	 * Exit a parse tree produced by {@link MinespeakParser#literal}.
	 * @param ctx the parse tree
	 */
	void exitLiteral(MinespeakParser.LiteralContext ctx);
	/**
	 * Enter a parse tree produced by {@link MinespeakParser#numberLiteral}.
	 * @param ctx the parse tree
	 */
	void enterNumberLiteral(MinespeakParser.NumberLiteralContext ctx);
	/**
	 * Exit a parse tree produced by {@link MinespeakParser#numberLiteral}.
	 * @param ctx the parse tree
	 */
	void exitNumberLiteral(MinespeakParser.NumberLiteralContext ctx);
	/**
	 * Enter a parse tree produced by {@link MinespeakParser#vector2Literal}.
	 * @param ctx the parse tree
	 */
	void enterVector2Literal(MinespeakParser.Vector2LiteralContext ctx);
	/**
	 * Exit a parse tree produced by {@link MinespeakParser#vector2Literal}.
	 * @param ctx the parse tree
	 */
	void exitVector2Literal(MinespeakParser.Vector2LiteralContext ctx);
	/**
	 * Enter a parse tree produced by {@link MinespeakParser#vector3Literal}.
	 * @param ctx the parse tree
	 */
	void enterVector3Literal(MinespeakParser.Vector3LiteralContext ctx);
	/**
	 * Exit a parse tree produced by {@link MinespeakParser#vector3Literal}.
	 * @param ctx the parse tree
	 */
	void exitVector3Literal(MinespeakParser.Vector3LiteralContext ctx);
	/**
	 * Enter a parse tree produced by {@link MinespeakParser#newlines}.
	 * @param ctx the parse tree
	 */
	void enterNewlines(MinespeakParser.NewlinesContext ctx);
	/**
	 * Exit a parse tree produced by {@link MinespeakParser#newlines}.
	 * @param ctx the parse tree
	 */
	void exitNewlines(MinespeakParser.NewlinesContext ctx);
}