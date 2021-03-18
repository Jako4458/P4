// Generated from D:/SKOLE/Guthub/P4/parser\Minespeak.g4 by ANTLR 4.9.1
import org.antlr.v4.runtime.tree.ParseTreeVisitor;

/**
 * This interface defines a complete generic visitor for a parse tree produced
 * by {@link MinespeakParser}.
 *
 * @param <T> The return type of the visit operation. Use {@link Void} for
 * operations with no return type.
 */
public interface MinespeakVisitor<T> extends ParseTreeVisitor<T> {
	/**
	 * Visit a parse tree produced by {@link MinespeakParser#prog}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitProg(MinespeakParser.ProgContext ctx);
	/**
	 * Visit a parse tree produced by {@link MinespeakParser#blocks}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitBlocks(MinespeakParser.BlocksContext ctx);
	/**
	 * Visit a parse tree produced by {@link MinespeakParser#block}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitBlock(MinespeakParser.BlockContext ctx);
	/**
	 * Visit a parse tree produced by {@link MinespeakParser#mcFunc}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitMcFunc(MinespeakParser.McFuncContext ctx);
	/**
	 * Visit a parse tree produced by {@link MinespeakParser#func}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFunc(MinespeakParser.FuncContext ctx);
	/**
	 * Visit a parse tree produced by {@link MinespeakParser#params}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitParams(MinespeakParser.ParamsContext ctx);
	/**
	 * Visit a parse tree produced by {@link MinespeakParser#param}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitParam(MinespeakParser.ParamContext ctx);
	/**
	 * Visit a parse tree produced by {@link MinespeakParser#funcBody}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFuncBody(MinespeakParser.FuncBodyContext ctx);
	/**
	 * Visit a parse tree produced by {@link MinespeakParser#retVal}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitRetVal(MinespeakParser.RetValContext ctx);
	/**
	 * Visit a parse tree produced by {@link MinespeakParser#stmnts}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitStmnts(MinespeakParser.StmntsContext ctx);
	/**
	 * Visit a parse tree produced by {@link MinespeakParser#stmnt}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitStmnt(MinespeakParser.StmntContext ctx);
	/**
	 * Visit a parse tree produced by {@link MinespeakParser#loop}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitLoop(MinespeakParser.LoopContext ctx);
	/**
	 * Visit a parse tree produced by {@link MinespeakParser#doWhile}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitDoWhile(MinespeakParser.DoWhileContext ctx);
	/**
	 * Visit a parse tree produced by {@link MinespeakParser#whileStmnt}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitWhileStmnt(MinespeakParser.WhileStmntContext ctx);
	/**
	 * Visit a parse tree produced by {@link MinespeakParser#foreach}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitForeach(MinespeakParser.ForeachContext ctx);
	/**
	 * Visit a parse tree produced by {@link MinespeakParser#forStmnt}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitForStmnt(MinespeakParser.ForStmntContext ctx);
	/**
	 * Visit a parse tree produced by {@link MinespeakParser#ifStmnt}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitIfStmnt(MinespeakParser.IfStmntContext ctx);
	/**
	 * Visit a parse tree produced by {@link MinespeakParser#dcls}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitDcls(MinespeakParser.DclsContext ctx);
	/**
	 * Visit a parse tree produced by {@link MinespeakParser#instan}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitInstan(MinespeakParser.InstanContext ctx);
	/**
	 * Visit a parse tree produced by {@link MinespeakParser#expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExpr(MinespeakParser.ExprContext ctx);
	/**
	 * Visit a parse tree produced by {@link MinespeakParser#factor}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFactor(MinespeakParser.FactorContext ctx);
	/**
	 * Visit a parse tree produced by {@link MinespeakParser#funcCall}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFuncCall(MinespeakParser.FuncCallContext ctx);
	/**
	 * Visit a parse tree produced by {@link MinespeakParser#assign}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAssign(MinespeakParser.AssignContext ctx);
	/**
	 * Visit a parse tree produced by {@link MinespeakParser#type}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitType(MinespeakParser.TypeContext ctx);
	/**
	 * Visit a parse tree produced by {@link MinespeakParser#literal}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitLiteral(MinespeakParser.LiteralContext ctx);
	/**
	 * Visit a parse tree produced by {@link MinespeakParser#numberLiteral}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitNumberLiteral(MinespeakParser.NumberLiteralContext ctx);
	/**
	 * Visit a parse tree produced by {@link MinespeakParser#vector2Literal}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitVector2Literal(MinespeakParser.Vector2LiteralContext ctx);
	/**
	 * Visit a parse tree produced by {@link MinespeakParser#vector3Literal}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitVector3Literal(MinespeakParser.Vector3LiteralContext ctx);
	/**
	 * Visit a parse tree produced by {@link MinespeakParser#newlines}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitNewlines(MinespeakParser.NewlinesContext ctx);
}