import logging.*;
import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.tree.TerminalNode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ScopeListener extends MinespeakBaseListener {
    private Scope currentScope;
    private final Map<String, FuncEntry> functions;
    private final ScopeFactory scopeFac = new ScopeFactory();
    private final EntryFactory entryFac = new EntryFactory();
    private final LogFactory logFac = new LogFactory();
    private boolean isInvalidFunc = false;
    private Map<String, FuncEntry> builtinFunctions;

    /**
     * Constructor for only function signatures.
     * Will create an empty hash-map for built-in functions.
     * @param funcSignatures The function signatures found by the signature walker
     * @see SignatureWalker
     */
    public ScopeListener(Map<String, FuncEntry> funcSignatures) {
        enterScope(null);
        this.functions = funcSignatures;
        this.builtinFunctions = new HashMap<>();
    }

    /**
     * Constructor for function signatures and built-in functions
     * @param funcSignatures The function signatures found by the signature walker
     * @see SignatureWalker
     * @param builtinFunctions The built-in functions written in MCFunction determined by the builtinfuncs
     * @see BuiltinFuncs
     */
    public ScopeListener(Map<String, FuncEntry> funcSignatures, Map<String, FuncEntry> builtinFunctions) {
        enterScope(null);
        this.functions = funcSignatures;
        this.builtinFunctions = builtinFunctions;
    }

    /**
     * Entry point for the listener.
     * Will prepare a global scope used only by functions
     * @see Scope
     * @param ctx The prog specific parse tree
     */
    @Override
    public void enterProg(MinespeakParser.ProgContext ctx) {
        ctx.scope = scopeFac.createScope(this.currentScope);
        enterScope(ctx.scope);
    }

    /**
     * Exit point for the listener.
     * Will simply the global scope made by the enterProg method
     * @see Scope
     * @param ctx The prog specific parse tree
     */
    @Override
    public void exitProg(MinespeakParser.ProgContext ctx) {
        exitScope();
    }

    /**
     * Entry point for the block structure.
     * Will create a new scope and then enter newly created scope
     * @see Scope
     * @param ctx A block parse tree
     */
    @Override
    public void enterBlock(MinespeakParser.BlockContext ctx) {
        ctx.scope = scopeFac.createScope(this.currentScope);
        enterScope(ctx.scope);
    }

    /**
     * Exit point for the block structure
     * Will exit the scope made by the entry point for block
     * @see Scope
     * @param ctx A block parse tree
     */
    @Override
    public void exitBlock(MinespeakParser.BlockContext ctx) {
        exitScope();
    }

    /**
     * Will set the next seen function to be an mcfunc
     * @see EntryFactory
     * @param ctx A parse tree of mcfunc type
     */
    @Override
    public void enterMcFunc(MinespeakParser.McFuncContext ctx) {
        entryFac.setMCFunction();
    }

    /**
     * Will create a new scope for the function and enter the scope
     * @see Scope
     * @param ctx The parse tree for a func structure
     */
    @Override
    public void enterFunc(MinespeakParser.FuncContext ctx) {
        ctx.scope = scopeFac.createFuncScope(this.currentScope);
        enterScope(ctx.scope);
    }

    /**
     * Exiting a func first make a check to see if it is a valid func (not a duplicate).
     * It will then check the return types (mcfuncs cannot have any).
     * The declared return type must be coherent with the return type of the return statement of the body.
     * If any error is found it is logged.
     * Finally, the scope of the function is saved on the function, and the scope is exited.
     * @see Logger
     * @see Scope
     * @see Type
     * @param ctx The func parse tree
     */
    @Override
    public void exitFunc(MinespeakParser.FuncContext ctx) {
        String name = ctx.funcSignature().ID().getText();
        ctx.type = ctx.funcSignature().type;

        if (this.isInvalidFunc || (ctx.type != ctx.funcBody().type)) {
            this.isInvalidFunc = false;
            this.entryFac.resetMCFunction();
            if (ctx.type != Type._void && ctx.funcBody().type == Type._void) {
                Logger.shared.add(logFac.createTypeError(name, ctx, ctx.type, Type._void));
            } else if (ctx.type != ctx.funcBody().type && !typesAreEqual(ctx.type, ctx.funcBody().type)) {
                Logger.shared.add(logFac.createTypeError(ctx.funcBody().retVal().expr().getText(),
                        ctx.funcBody().retVal().expr(),
                        ctx.funcBody().retVal().type,
                        ctx.type)
                );
            }
        }

        functions.get(name).scope = currentScope;
        exitScope();
    }

    /**
     * Marks the function as invalid if the signature walker has marked it as a duplicate function.
     * @see SignatureWalker
     * @param ctx The func signature parse tree
     */
    @Override
    public void enterFuncSignature(MinespeakParser.FuncSignatureContext ctx) {
        if (ctx.isDuplicate)
            this.isInvalidFunc = true;
    }

    /**
     * First, checks the return type an denotes it on the parse tree.
     * Checks if the function is valid.
     * If it is valid, the names and types of parameters are saved in a new entry.
     * The name of the function is also saved on the entry.
     * The entry is finally saved on the current scope.
     * @see FuncEntry
     * @see Scope
     * @see Type
     * @param ctx A parse tree for func signature
     */
    @Override
    public void exitFuncSignature(MinespeakParser.FuncSignatureContext ctx) {
        if (ctx.primaryType() != null)
            ctx.type = ctx.primaryType().type;
        else
            ctx.type = Type._void;

        if (!this.isInvalidFunc) {
            List<SymEntry> paramIDs = new ArrayList<>();

            for (MinespeakParser.ParamContext param : ctx.params().param()) {
                String paramName = param.ID().getText();
                Type paramType = param.primaryType().type;
                paramIDs.add(entryFac.createFromType(paramName, paramType, ctx, MinespeakParser.VAR));
            }

            String funcName = ctx.ID().getText();

            FuncEntry entry = entryFac.createFunctionEntry(funcName, ctx.type, paramIDs, ctx);
            this.addToScope(ctx, funcName, entry);
        }
    }

    /**
     * Denotes the parse tree with the type of the parameter.
     * If the type is void or invalid an error is reported.
     * If not, the parameter is added to the current scope
     * @see Logger
     * @see Scope
     * @see Type
     * @param ctx The CST node of a parameter
     */
    @Override
    public void exitParam(MinespeakParser.ParamContext ctx) {
        String name = ctx.ID().getText();
        ctx.type = ctx.primaryType().type;
        if (ctx.type == Type._void || ctx.type == Type._error) {
            Logger.shared.add(logFac.createCannotBeVoid(ctx.ID().getText(), ctx, ctx.type));
        } else {
            this.addToScope(ctx, name, entryFac.createFromType(name, ctx.type, ctx, MinespeakParser.VAR));
        }
    }

    /**
     * Entering the function body creates a new scope and enters newly created scope.
     * @see Scope
     * @param ctx The CST node of the function body
     */
    @Override
    public void enterFuncBody(MinespeakParser.FuncBodyContext ctx) {
        ctx.scope = scopeFac.createScope(this.currentScope);
        enterScope(ctx.scope);
    }

    /**
     * Leaving the function body denotes the type of the return statement.
     * If there is no return statement, the type is <i>void</i>.
     * Finally, exits the current scope.
     * @see Scope
     * @see Type
     * @param ctx The CST node of the function body
     */
    @Override
    public void exitFuncBody(MinespeakParser.FuncBodyContext ctx) {
        ctx.type = ctx.retVal() != null ? ctx.retVal().type : Type._void;
        exitScope();
    }

    /**
     * Leaving the return statement denotes the parse tree with the type of the expression.
     * It also adds the expression to the scope under the name <i>return</i>.
     * @see Type
     * @see Scope
     * @param ctx The CST node for the return statement in a function
     */
    @Override
    public void exitRetVal(MinespeakParser.RetValContext ctx) {
        ctx.type = ctx.expr().type;
        this.addToScope(ctx, "return", entryFac.createFromType("return", ctx.type, ctx, MinespeakParser.CONST));
    }

    /**
     * Entering a do-while loop creates a new scope and enters it.
     * @see Scope
     * @param ctx The CST node for the do-while loop
     */
    @Override
    public void enterDoWhile(MinespeakParser.DoWhileContext ctx) {
        ctx.scope = scopeFac.createScope(this.currentScope);
        enterScope(ctx.scope);
    }

    /**
     * When exiting a do-while loop the loop condition must be checked to be a boolean.
     * If it is not a boolean, an error is logged.
     * Finally, the current scope of the do-while loop must be exited.
     * @see Scope
     * @see Type
     * @see Logger
     * @param ctx The CST node for the do-while loop
     */
    @Override
    public void exitDoWhile(MinespeakParser.DoWhileContext ctx) {
        if(ctx.expr().type != Type._bool){
            Logger.shared.add(logFac.createTypeError(ctx.expr().getText(), ctx.expr(), ctx.expr().type, Type._bool));
        }

        exitScope();
    }

    /**
     * Entering a while-loop creates a new scope and enters it.
     * @see Scope
     * @param ctx The CST node for the while-loop
     */
    @Override
    public void enterWhileStmnt(MinespeakParser.WhileStmntContext ctx) {
        ctx.scope = scopeFac.createScope(this.currentScope);
        enterScope(ctx.scope);
    }

    /**
     * When exiting a while-loop the loop condition must be checked to be a boolean.
     * If it is not a boolean, an error is logged.
     * Finally, the current scope of the while-loop must be exited.
     * @see Type
     * @see Scope
     * @see Logger
     * @param ctx The CST node for the while-loop
     */
    @Override
    public void exitWhileStmnt(MinespeakParser.WhileStmntContext ctx) {
        if(ctx.expr().type != Type._bool){
            Logger.shared.add(logFac.createTypeError(ctx.expr().getText(), ctx.expr(), ctx.expr().type, Type._bool));
        }

        exitScope();
    }

    /**
     * Entering a foreach loop creates a new scope and enters it.
     * @see Scope
     * @param ctx The CST node for the foreach loop
     */
    @Override
    public void enterForeach(MinespeakParser.ForeachContext ctx) {
        ctx.scope = scopeFac.createScope(this.currentScope);
        enterScope(ctx.scope);
    }

    /**
     * Initialising a foreach loop requires adding the ID to the scope.
     * The type of the expression must also be an <i>arraytype</i> otherwise the foreach loop is type incorrect.
     * If the type is incorrect, an error is logged.
     * Finally, the parse tree is denoted with the type of the ID node.
     * @see ArrayType
     * @see Logger
     * @see Scope
     * @param ctx The CST node for the foreach initialisation
     */
    @Override
    public void exitForeachInit(MinespeakParser.ForeachInitContext ctx) {
        String name = ctx.ID().getText();
        Type type = ctx.primaryType().type;
        this.addToScope(ctx, name, entryFac.createFromType(name, type, ctx, MinespeakParser.VAR));

        if (!(ctx.expr().type instanceof ArrayType) || ((ArrayType)ctx.expr().type).type != type) {
            Logger.shared.add(logFac.createTypeError(ctx.expr().getText(),
                    ctx.expr(),
                    ctx.expr().type,
                    ctx.primaryType().type)
            );
        }

        ctx.type = type;
    }

    /**
     * Exits the current scope which is that of the foreach loop.
     * @see Scope
     * @param ctx The CST node for the foreach loop
     */
    @Override
    public void exitForeach(MinespeakParser.ForeachContext ctx) {
        exitScope();
    }

    /**
     * Entering a for-loop creates a new scope and enters it.
     * @see Scope
     * @param ctx The CST node for the for-loop
     */
    @Override
    public void enterForStmnt(MinespeakParser.ForStmntContext ctx) {
        ctx.scope = scopeFac.createScope(this.currentScope);
        enterScope(ctx.scope);
    }

    /**
     * Exiting the for-loop requires checking all the instantiations.
     * If an instantiation is not a of type <i>num</i>, a warning is issued,
     * since it is likely a mistake to instantiate something which cannot be an iterator.
     * The expr must be a bool. Otherwise an error is logged.
     * The assignment in the for-loop must be a number type, as it is not an iterator otherwise.
     * If it is not, an error is logged.
     * Finally, the current scope which is the scope for the for-loop is exited.
     * @see Scope
     * @see Type
     * @see Logger
     * @param ctx The CST node for the for-loop
     */
    @Override
    public void exitForStmnt(MinespeakParser.ForStmntContext ctx) {
        int length = ctx.instan().primaryType().size();

        for (int i = 0; i < length; i++) {
            if(ctx.instan().primaryType(i).type != Type._num){
                Logger.shared.add(logFac.createTypeWarning(ctx.instan().ID(i).getText(),
                        ctx.instan().primaryType(i),
                        ctx.instan().primaryType(i).type,
                        Type._num)
                );
            }
        }

        if(ctx.expr().type != Type._bool){
            Logger.shared.add(logFac.createTypeError(ctx.expr().getText(), ctx.expr(), ctx.expr().type, Type._bool));
        }

        Type assignType = lookupTypeInScope(ctx.assign(), ctx.assign().ID().getText());
        if (assignType != Type._num) {
            Logger.shared.add(logFac.createTypeError(ctx.assign().getText(), ctx.assign(), assignType, Type._num));
        }

        exitScope();
    }

    /**
     * Entering an if-statement creates a new scope and enters it.
     * @see Scope
     * @param ctx The CST node for the if-statement
     */
    @Override
    public void enterIfStmnt(MinespeakParser.IfStmntContext ctx) {
        ctx.scope = scopeFac.createScope(this.currentScope);
        enterScope(ctx.scope);
    }

    /**
     * Exiting the if.statement, all expressions in the if, elif, and else must be type checked.
     * If they are not of boolean type, they are not valid logical expressions, which an if-statement requires.
     * An error is logged if this is the case.
     * Finally, the current scope is exited.
     * @see Scope
     * @see Type
     * @see Logger
     * @param ctx The CST node for the if-statement
     */
    @Override
    public void exitIfStmnt(MinespeakParser.IfStmntContext ctx) {
        List<MinespeakParser.ExprContext> ifExprs = ctx.expr();
        for (MinespeakParser.ExprContext ifExpr : ifExprs) {
            if (ifExpr.type != Type._bool) {
                Logger.shared.add(logFac.createTypeError(ifExpr.getText(), ifExpr, ifExpr.type, Type._bool));
            }
        }
        exitScope();
    }

    /**
     * Entering any body creates a new scope and enters it.
     * @see Scope
     * @param ctx The CST node for the body
     */
    @Override
    public void enterBody(MinespeakParser.BodyContext ctx) {
        ctx.scope = scopeFac.createScope(this.currentScope);
        enterScope(ctx.scope);
    }

    /**
     * Exiting any body is simply exiting the current scope which is that of the body.
     * @see Scope
     * @param ctx The CST node for the body
     */
    @Override
    public void exitBody(MinespeakParser.BodyContext ctx) {
        exitScope();
    }

    /**
     * Visiting declarations adds all the declared variables to the current scope,
     * using the method <i>addMultipleToScope</i>.
     * @param ctx The CST node for declarations
     */
    @Override
    public void exitDcls(MinespeakParser.DclsContext ctx) {
        addMultipleToScope(ctx);
    }

    /**
     * Exiting instantiations requires type checking that the declared type and the
     * type of the expression on the right hand side match.
     * If they do not, and error is logged.
     * Finally, all the variables declared are added to the current scope using
     * the method <i>addMultipleToScope</i>.
     * The values of the expressions are not considered here, but rather during code generation.
     * @see Type
     * @see Logger
     * @see CodeGenVisitor
     * @param ctx The CST node for instantiations
     */
    @Override
    public void exitInstan(MinespeakParser.InstanContext ctx) {
        int instanLength = ctx.primaryType().size();

        for (int i = 0; i < instanLength; i++) {
            if (ctx.initialValue(i).rArray() != null) {
                if (!typesAreEqual(ctx.primaryType(i).type, ctx.initialValue(i).rArray().type)) {
                    Logger.shared.add(logFac.createTypeError(ctx.initialValue(i).rArray().getText(),
                            ctx.initialValue(i).rArray(),
                            ctx.initialValue(i).rArray().type,
                            ctx.primaryType(i).type)
                    );
                }
            } else if (ctx.initialValue(i).expr() != null){
                if (!typesAreEqual(ctx.primaryType(i).type, ctx.initialValue(i).expr().type)) {
                    Logger.shared.add(logFac.createTypeError(ctx.initialValue(i).expr().getText(),
                            ctx.initialValue(i).expr(),
                            ctx.initialValue(i).expr().type,
                            ctx.primaryType(i).type)
                    );
                }
            }
        }
        addMultipleToScope(ctx);
    }

    /**
     * Exiting an array access node requires type checking.
     * First, the type of the index is found.
     * If it is not a number, it is an invalid index and an error is logged.
     * Next, the supposed array given by ID must be checked.
     * If it is not an array then a type error is logged.
     * If it is, the parse tree is denoted with the base type of the array.
     * @see Type
     * @see ArrayType
     * @see Logger
     * @param ctx The CST node for array access
     */
    @Override
    public void exitArrayAccess(MinespeakParser.ArrayAccessContext ctx) {
        Type tempType = this.lookupTypeInScope(ctx, ctx.ID().getText());
        if (ctx.expr().type != Type._num) {
            Logger.shared.add(logFac.createTypeError(ctx.ID().getText(), ctx.expr(), ctx.expr().type, Type._bool));
        }
        if (tempType != Type._error && !(tempType instanceof ArrayType))
            Logger.shared.add(logFac.createVarNotArrayLog(ctx.ID().getText(), ctx));
        else
            ctx.type = ((ArrayType)tempType).type;
    }

    /**
     * rArray is a right hand side array expression.
     * Checks whether all expressions are of same type.
     * If they are not, an error is logged for each expression not matching
     * the type of the first expression.
     * The parse tree is denoted with the type of the array.
     * @see Type
     * @see ArrayType
     * @see Logger
     * @param ctx The CST node for rArray
     */
    @Override
    public void exitRArray(MinespeakParser.RArrayContext ctx) {
        if (ctx.expr().isEmpty())
            return;
        Type baseType = ctx.expr().get(0).type;
        for (var expr : ctx.expr()) {
            if (expr.type != baseType) {
                Logger.shared.add(logFac.createTypeError(expr.getText(), expr, expr.type, baseType));
            }
        }
        ctx.type = new ArrayType(ctx, baseType);
    }

    /**
     * Seeing a variable ID the parse tree must simply
     * be denoted with the type of the variable as found by the scope entry.
     * @see Scope
     * @see SimpleEntry
     * @see Type
     * @param ctx The CST node for the rValue
     */
    @Override
    public void enterRvalue(MinespeakParser.RvalueContext ctx) {
        ctx.type = this.lookupTypeInScope(ctx, ctx.ID().getText());
    }

    /**
     * Exiting a primary type is simply denoting the parse tree with the type specified.
     * @see Type
     * @see ArrayType
     * @param ctx The CST node for primary types
     */
    @Override
    public void exitPrimaryType(MinespeakParser.PrimaryTypeContext ctx) {
        Type type = ctx.primitiveType().type;
        if (ctx.lArray() != null || ctx.ARRAY() != null) {
            ctx.type = new ArrayType(ctx, type);
        } else {
            ctx.type = type;
        }
    }

    /**
     * Exiting a primitive type is denoting the parse tree with the type.
     * Must check all the possible types as given by the parser and type system.
     * If the type is not recoqnised an error is logged.
     * @see Type
     * @see MinespeakParser
     * @see Logger
     * @param ctx The CST node for primitive types
     */
    @Override
    public void exitPrimitiveType(MinespeakParser.PrimitiveTypeContext ctx) {
        if (ctx.BOOL() != null)
            ctx.type = Type._bool;
        else if (ctx.BLOCK() != null)
            ctx.type = Type._block;
        else if (ctx.NUM() != null)
            ctx.type = Type._num;
        else if (ctx.STRING() != null)
            ctx.type = Type._string;
        else if (ctx.VECTOR2() != null)
            ctx.type = Type._vector2;
        else if (ctx.VECTOR3() != null)
            ctx.type = Type._vector3;
        else {
            ctx.type = Type._error;
            Logger.shared.add(logFac.createTypeError(ctx.getText(), ctx, ctx.type, ctx.type));
        }
    }

    /**
     * Exiting an addition or subtraction expressions requires type checking the operands and the operation.
     * The type is determined by the type system, and the parse tree is denoted by the resulting type.
     * If the type checking yields invalid, the type denoted by is the <i>error</i> type.
     * @see Type
     * @param ctx The CST node for an addition or subtraction expressions
     */
    @Override
    public void exitAddSub(MinespeakParser.AddSubContext ctx) {
        int left = ctx.expr(0).type.getTypeAsInt();
        int right = ctx.expr(1).type.getTypeAsInt();

        if(ctx.ADD() != null){
            ctx.type = Type.inferType(left, MinespeakParser.ADD, right);
        } else if (ctx.SUB() != null) {
            if (isArrayType(ctx.expr(0).type) && isArrayType((ctx.expr(1).type))) {
                ctx.type = Type._error;
                return;
            }

            ctx.type = Type.inferType(left, MinespeakParser.SUB, right);
        }
    }

    /**
     * Exiting the exponent expressions requires type checking of operands and operation.
     * The resulting type is denoted on the parse tree; it is <i>error</i> if it is type invalid.
     * @see Type
     * @param ctx The CST node for an exponent expression
     */
    @Override
    public void exitPow(MinespeakParser.PowContext ctx) {
        if (isArrayType(ctx.expr(0).type) && isArrayType((ctx.expr(1).type))) {
            ctx.type = Type._error;
            return;
        }

        int left = ctx.expr(0).type.getTypeAsInt();
        int right = ctx.expr(1).type.getTypeAsInt();

        ctx.type = Type.inferType(left, MinespeakParser.POW, right);
    }

    /**
     * Exiting an or expression requires type checking the operand and the operation using the type system.
     * The parse tree is denoted the resulting type; if type invalid, the denoted type is <i>error</i>.
     * @see Type
     * @param ctx The CST node for an or expression
     */
    @Override
    public void exitOr(MinespeakParser.OrContext ctx) {
        if (isArrayType(ctx.expr(0).type) && isArrayType((ctx.expr(1).type))) {
            ctx.type = Type._error;
            return;
        }

        int left = ctx.expr(0).type.getTypeAsInt();
        int right = ctx.expr(1).type.getTypeAsInt();

        ctx.type = Type.inferType(left, MinespeakParser.OR, right);
    }

    /**
     * Exiting an and expression requires type checking the operand and the operation using the type system.
     * The parse tree is denoted the resulting type; if type invalid, the denoted type is <i>error</i>.
     * @see Type
     * @param ctx The CST node for an and expression
     */
    @Override
    public void exitAnd(MinespeakParser.AndContext ctx) {
        if (isArrayType(ctx.expr(0).type) &&
                isArrayType((ctx.expr(1).type))) {

            ctx.type = Type._error;
            return;
        }

        int left = ctx.expr(0).type.getTypeAsInt();
        int right = ctx.expr(1).type.getTypeAsInt();

        ctx.type = Type.inferType(left, MinespeakParser.AND, right);
    }

    /**
     * Exiting an equality or inequality expression requires type checking the operand and the operation using the type system.
     * The parse tree is denoted the resulting type; if type invalid, the denoted type is <i>error</i>.
     * @see Type
     * @param ctx The CST node for an equality or inequality expression
     */
    @Override
    public void exitEquality(MinespeakParser.EqualityContext ctx) {
        int left = ctx.expr(0).type.getTypeAsInt();
        int right = ctx.expr(1).type.getTypeAsInt();

        if (ctx.EQUAL() != null) {
            ctx.type = Type.inferType(left, MinespeakParser.EQUAL, right);
        } else if(ctx.NOTEQUAL() != null) {
            ctx.type = Type.inferType(left, MinespeakParser.NOTEQUAL, right);
        }

    }

    /**
     * Exiting an relation expression requires type checking the operand and the operation using the type system.
     * The parse tree is denoted the resulting type; if type invalid, the denoted type is <i>error</i>.
     * @see Type
     * @param ctx The CST node for an relation expression
     */
    @Override
    public void exitRelations(MinespeakParser.RelationsContext ctx) {
        if (isArrayType(ctx.expr(0).type) && isArrayType((ctx.expr(1).type))) {
            ctx.type = Type._error;
            return;
        }

        int left = ctx.expr(0).type.getTypeAsInt();
        int right = ctx.expr(1).type.getTypeAsInt();

        if (ctx.LESSER() != null) {
            ctx.type = Type.inferType(left, MinespeakParser.LESSER, right);
        } else if(ctx.GREATER() != null) {
            ctx.type = Type.inferType(left, MinespeakParser.GREATER, right);
        } else if(ctx.LESSEQ() != null) {
            ctx.type = Type.inferType(left, MinespeakParser.LESSEQ, right);
        } else if(ctx.GREATEQ() != null) {
            ctx.type = Type.inferType(left, MinespeakParser.GREATEQ, right);
        }
    }

    /**
     * Exiting an multiplication, division, or modulus expression requires type checking the operand and the operation using the type system.
     * The parse tree is denoted the resulting type; if type invalid, the denoted type is <i>error</i>.
     * @see Type
     * @param ctx The CST node for an multiplication, division, or modulus expression
     */
    @Override
    public void exitMulDivMod(MinespeakParser.MulDivModContext ctx) {
        if (isArrayType(ctx.expr(0).type) && isArrayType((ctx.expr(1).type))) {
            ctx.type = Type._error;
            return;
        }

        int left = ctx.expr(0).type.getTypeAsInt();
        int right = ctx.expr(1).type.getTypeAsInt();

        if(ctx.DIV() != null){
            ctx.type = Type.inferType(left, MinespeakParser.DIV, right);
        } else if(ctx.TIMES() != null) {
            ctx.type = Type.inferType(left, MinespeakParser.TIMES, right);
        } else if(ctx.MOD() != null) {
            ctx.type = Type.inferType(left, MinespeakParser.MOD, right);
        }

    }

    /**
     * Exiting a factor node is just denoting the parse tree with the type of the appropriate child.
     * @see Type
     * @param ctx The CST node for factor
     * @throws RuntimeException if none of the children are valid
     */
    @Override
    public void exitFactor(MinespeakParser.FactorContext ctx) {
        if (ctx.rvalue() != null) {
            ctx.type = ctx.rvalue().type;
        } else if (ctx.expr() != null) {
            ctx.type = ctx.expr().type;
        } else if (ctx.literal() != null) {
            ctx.type = ctx.literal().type;
        } else if (ctx.funcCall() != null) {
            ctx.type = ctx.funcCall().type;
        } else if (ctx.arrayAccess() != null) {
            ctx.type = ctx.arrayAccess().type;
        } else if (ctx.rArray() != null) {
            ctx.type = ctx.rArray().type;
        } else {
            throw new RuntimeException();
        }
    }

    /**
     * First, determines whether the function for the call exists.
     * If it does not, an error is logged.
     * Next, checks if the return value of the function is used (if function returns).
     * If it is unused, logs a warning.
     * Next, checks that every actual parameter matches the corresponding formal parameter in type.
     * If not, an error is logged.
     * Finally, denotes the parse tree with the return type of the function.
     * @see Type
     * @see Logger
     * @param ctx The CST node for the function call
     */
    @Override
    public void exitFuncCall(MinespeakParser.FuncCallContext ctx) {
        FuncEntry function = functions.get(ctx.ID().getText());
        if (function == null)
            function = this.builtinFunctions.get(ctx.ID().getText());

        if (function == null) {
            Logger.shared.add(logFac.createVariableNotDeclaredLog(ctx.ID().getText(), ctx));
            return;
        }

        if (ctx.parent instanceof MinespeakParser.StmntContext) {
            if(function.getType() != Type._void){
                Logger.shared.add(logFac.createResultIgnoredWarning(ctx.getText(), ctx));
            }
        }

        List<SymEntry> formalParams = function.getParams();
        List<MinespeakParser.ExprContext> actualParams = ctx.expr();

        if (actualParams.size() < formalParams.size()){
            Logger.shared.add(logFac.createTooFewArgumentsError(ctx.ID().getText(), ctx));
            Logger.shared.add(logFac.createFuncDeclLocationNote(function.getCtx()));
        } else if (actualParams.size() > formalParams.size()){
            Logger.shared.add(logFac.createTooManyArgumentsError(ctx.ID().getText(), ctx));
            Logger.shared.add(logFac.createFuncDeclLocationNote(function.getCtx()));
        } else {
            for (int i = 0; i < formalParams.size(); i++) {
                Type fType = formalParams.get(i).getType();
                Type aType = actualParams.get(i).type;
                if (!typesAreEqual(fType, aType)) {
                    Logger.shared.add(logFac.createTypeError(actualParams.get(i).getText(),
                            actualParams.get(i),
                            actualParams.get(i).type,
                            formalParams.get(i).getType())
                    );
                }
            }
        }
        ctx.type = function.getType();
    }

    /**
     * Overall, searches for the ID of the left hand side, and ensures it is not a constant.
     * Then type checks to see whether expression and variable are same type.
     * Finally, checks whether the assignment operator is valid for the types of the variable and expression.
     * If at any point an error is found, and error is logged.
     * @see Scope
     * @see SymEntry
     * @see Type
     * @see Logger
     * @param ctx The CST node for assign statement
     */
    @Override
    public void exitAssign(MinespeakParser.AssignContext ctx) {
        SymEntry entry = this.currentScope.lookup(ctx.ID().getText());
        if (entry == null) {
            Logger.shared.add(logFac.createVariableNotDeclaredLog(ctx.ID().getText(), ctx));
            return;
        }

        // Checking whether the variable to be assigned to is var
        if (entry.getModifier() == MinespeakParser.CONST) {
            Logger.shared.add(logFac.createVariableCannotBeModifiedLog(ctx.ID().getText(), ctx));
        }

        // Type checking for the expression and the entry stored
        Type type = entry.getType();
        Type exprType = ctx.expr().type;
        if  (ctx.arrayAccess() != null) {
            if (type instanceof ArrayType && exprType instanceof ArrayType) {
                type = ((ArrayType) type).type;
                exprType = ((ArrayType) type).type;
            } else {
                Logger.shared.add(logFac.createTypeError(ctx.arrayAccess().getText(),
                        ctx.arrayAccess(),
                        ctx.arrayAccess().type,
                        type)
                );
            }
        }

        if (type == Type._error) {
            return;
        }

        // Using the type system to infer whether the assignment is type correct.
        Type inferredType = Type.inferType(type.getTypeAsInt(), MinespeakParser.ASSIGN, exprType.getTypeAsInt());
        if (ctx.compAssign() != null)
            inferredType = Type.inferType(type.getTypeAsInt(), ctx.compAssign().op.getType(), exprType.getTypeAsInt());

        if (inferredType == Type._error) {
            if (ctx.compAssign() != null) {
                Logger.shared.add(logFac.createInvalidOperatorError(
                        ctx.compAssign().getText(), ctx.compAssign(), type, exprType)
                );
            } else {
                Logger.shared.add(logFac.createTypeError(ctx.expr().getText(),
                        ctx.expr(),
                        exprType,
                        type)
                );
            }
        }
    }

    /**
     * Exiting a literal node is simply denoting the parse tree with the type of the appropriate literal.
     * @see Type
     * @param ctx The CST node for literals
     * @throws RuntimeException if all literals are invalid
     */
    @Override
    public void exitLiteral(MinespeakParser.LiteralContext ctx) {
        if (ctx.booleanLiteral() != null) {
            ctx.type = Type._bool;
        } else if(ctx.BlockLiteral() != null) {
            ctx.type = Type._block;
        } else if(ctx.numberLiteral() != null) {
            ctx.type = Type._num;
        } else if(ctx.StringLiteral() != null) {
            ctx.type = Type._string;
        } else if(ctx.vector2Literal() != null) {
            ctx.type = Type._vector2;
        } else if(ctx.vector3Literal() != null) {
            ctx.type = Type._vector3;
        } else {
            throw new RuntimeException();
        }
    }

    /**
     * Ensures that all expressions in the vector are of num type
     * @see Type
     * @param ctx The CST node for vector2 literal
     */
    @Override
    public void exitVector2Literal(MinespeakParser.Vector2LiteralContext ctx) {
        for (MinespeakParser.ExprContext expr : ctx.expr()) {
            if (!expr.type.equals(Type._num)) {
                Logger.shared.add(logFac.createTypeError(expr.getText(), expr, expr.type, Type._num));
            }
        }
    }

    /**
     * Ensures that all expressions in the vector are of num type
     * @see Type
     * @param ctx The CST node for vector3 literal
     */
    @Override
    public void exitVector3Literal(MinespeakParser.Vector3LiteralContext ctx) {
        for (MinespeakParser.ExprContext expr : ctx.expr()) {
            if (!expr.type.equals(Type._num)) {
                Logger.shared.add(logFac.createTypeError(expr.getText(), expr, expr.type, Type._num));
            }
        }
    }

    /**
     * Starts by denoting the parse tree with the type of the factor.
     * If other the not expression is used or the subtract expression is used,
     * these are type checked.
     * For not, if the type of the factor is not a boolean, an error is logged.
     * For sub, if the type of the factor is not a vector or a num, an error is logged.
     * @see Type
     * @see Logger
     * @param ctx The CST node for the negation expression
     */
    @Override
    public void exitNotNegFac(MinespeakParser.NotNegFacContext ctx) {
        ctx.type = ctx.factor().type;

        if (ctx.NOT() != null) {
            if (ctx.type != Type._bool) {
                ctx.type = Type._error;
                Logger.shared.add(logFac.createTypeError(ctx.factor().getText(), ctx, ctx.type, Type._bool));
            }
        } else if(ctx.SUB() != null) {
            if(ctx.type != Type._vector2 && ctx.type != Type._vector3 && ctx.type != Type._num) {
                ctx.type = Type._error;
                Type[] types = {Type._vector2, Type._vector3, Type._num};
                Logger.shared.add(logFac.createTypeError(ctx.factor().getText(), ctx, ctx.type, types));
            }
        }
    }

    /**
     * Determines whether two types are equal.
     * @see Type
     * @param t1 The type for the first argument
     * @param t2 The type for the second argument
     * @return True if the types are equal, and false if they are not
     */
    private boolean typesAreEqual(Type t1, Type t2) {
        if (t1 instanceof ArrayType && t2 instanceof ArrayType) {
            return ((ArrayType)t1).equalTypes((ArrayType)t2);
        } else {
            return t1 == t2;
        }
    }

    /**
     * Exits the current scope by entering the parent of the current scope.
     * @see Scope
     */
    private void exitScope() {
        enterScope(this.currentScope.getParent());
    }

    /**
     * Enters a scope given by parameter by setting the current scope to the parameter.
     * @see Scope
     * @param scope The scope to enter
     */
    private void enterScope(Scope scope) {
        this.currentScope = scope;
    }

    /**
     * Adds a variable by a key in the current scope.
     * If the key is already used, an error is logged.
     * @see Scope
     * @see Logger
     * @param ctx Currently unused. Previously used for logging.
     * @param key The key for the variable to add
     * @param var The entry to add with the key
     */
    private void addToScope(ParserRuleContext ctx, String key, SymEntry var) {
        if (!this.currentScope.addVariable(key, var)) {
            Logger.shared.add(logFac.createVariableAlreadyDeclaredLog(key, var.getCtx()));
            Logger.shared.add(logFac.createVarDeclLocationNote(currentScope.lookup(key).getCtx()));
        }
    }

    /**
     * Searches for the type of a variable in the current scope.
     * An error is logged if there is no variable by the key in the current scope.
     * @param ctx The parse tree for the node which requested the lookup. Used for generating the error log
     * @param key The key of the variable to lookup
     * @return The type of the variable in the entry if found, otherwise returns the type <i>error</i>
     */
    private Type lookupTypeInScope(ParserRuleContext ctx, String key) {
        SymEntry entry = this.currentScope.lookup(key);

        if (entry == null)
            Logger.shared.add(logFac.createVariableNotDeclaredLog(key, ctx));

        return entry == null ? Type._error : entry.getType();
    }

    /**
     * Adds the variables from the parse tree to the current scope.
     * @param ctx Either a declaration node or an instantiation node. Used to find the variables to add to scope
     */
    private void addMultipleToScope(ParserRuleContext ctx) {
        List<TerminalNode> ids;
        List<MinespeakParser.PrimaryTypeContext> types;
        int modifier;

        if (ctx instanceof MinespeakParser.DclsContext) {
            ids = ((MinespeakParser.DclsContext)ctx).ID();
            types = ((MinespeakParser.DclsContext)ctx).primaryType();
            modifier = ((MinespeakParser.DclsContext)ctx).modifiers().CONST() != null ? MinespeakParser.CONST : MinespeakParser.VAR;
        } else if (ctx instanceof MinespeakParser.InstanContext) {
            ids = ((MinespeakParser.InstanContext)ctx).ID();
            types = ((MinespeakParser.InstanContext)ctx).primaryType();
            modifier = ((MinespeakParser.InstanContext)ctx).modifiers().CONST() != null ? MinespeakParser.CONST : MinespeakParser.VAR;
        } else {
            return;
        }

        for (int i = 0; i < ids.size(); i++) {
            String name = ids.get(i).getText();
            Type type = types.get(i).type;
            this.addToScope(ctx, name, entryFac.createFromType(name, type, ctx, modifier));
        }
    }

    /**
     * Returns all functions
     * @see FuncEntry
     * @return functions to return
     */
    public Map<String, FuncEntry> getFunctions() {
        return this.functions;
    }

    /**
     * Determines whether a given type is an array type
     * @param type The type of the parameter
     * @return True if the type is an array type. False if the type is not an array type
     */
    private boolean isArrayType(Type type) {
        return (type instanceof ArrayType);
    }
}
