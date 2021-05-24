import exceptions.MCFuncWrongReturnTypeException;
import logging.Logger;
import logging.logs.Log;
import org.antlr.v4.runtime.tree.ParseTree;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SignatureWalker extends MinespeakBaseVisitor<Type> {
    private boolean nextIsMCFunc = false;
    public Map<String, FuncEntry> functionSignatures = new HashMap<>();
    private List<SymEntry> currentParameters = new ArrayList<>();
    private final LogFactory logFac = new LogFactory();
    private final EntryFactory entryFac = new EntryFactory();


    /**
     * Visits the blocks node of the program if it is not empty.
     * @param ctx The CST node the program
     * @return the <i>void</i> type
     */
    @Override
    public Type visitProg(MinespeakParser.ProgContext ctx) {

        if (ctx.blocks() != null) {
            try {
                visit(ctx.blocks());
            } catch (MCFuncWrongReturnTypeException ignored){

            }
        }
        return Type._void;
    }

    /**
     * Visits all blocks
     * @param ctx The CST node for blocks
     * @return the <i>void</i> type
     */
    @Override
    public Type visitBlocks(MinespeakParser.BlocksContext ctx) {
        for (ParseTree block : ctx.block()) {
            if (block != null) {
                visit(block);
            }
        }
        return Type._void;
    }

    /**
     * Visits either the function or mcfunction in the block.
     * @param ctx The CST node for block
     * @return the <i>void</i> type
     */
    @Override
    public Type visitBlock(MinespeakParser.BlockContext ctx) {
        if (ctx.mcFunc() != null) {
            visit(ctx.mcFunc());
        }
        if (ctx.func() != null) {
            visit(ctx.func());
        }
        return Type._void;
    }

    /**
     * Sets the nextIsMCFunc variable to true, visits the func node, and then sets the nextIsMCFunc to false again.
     * @param ctx The CST node for mcfunc
     * @return the <i>void</i> type
     */
    @Override
    public Type visitMcFunc(MinespeakParser.McFuncContext ctx) {
        this.nextIsMCFunc = true;
        visit(ctx.func());
        this.nextIsMCFunc = false;
        return Type._void;
    }

    /**
     * First visits the function signature, then the body.
     * The body is only visited for the return statement.
     * Finally, resets the current parameters, since the current function declaration is handled.
     * @param ctx The CST node for the function
     * @return the <i>void</i> type
     */
    @Override
    public Type visitFunc(MinespeakParser.FuncContext ctx) {
        visit(ctx.funcSignature());
        visit(ctx.funcBody());
        this.currentParameters = new ArrayList<>();
        return Type._void;
    }

    /**
     * Logs a warning if the declared function is an mcfunc with parameters.
     * Logs an error if the declared function is an mcfunc with a return type.
     * Adds the func signature to the list of func signatures if it is not already there.
     * If it is already in the list, it logs an error, and sets the parse tree to being a duplicate.
     * @param ctx The CST node for the function signature
     * @return the <i>void</i> type
     * @throws MCFuncWrongReturnTypeException if the function is an mcfunc and declares a return type
     */
    @Override
    public Type visitFuncSignature(MinespeakParser.FuncSignatureContext ctx) throws MCFuncWrongReturnTypeException {
        visit(ctx.params());
        List<SymEntry> params = this.currentParameters;
        Type type = Type._void;

        if (ctx.primaryType() != null)
            type = visit(ctx.primaryType());

        if(this.nextIsMCFunc && params.size() > 0) {
            Logger.shared.add(logFac.createMCFuncParamsWarning(ctx.ID().getText(),ctx));
        }

        if (this.nextIsMCFunc && ctx.primaryType() != null) {
            Logger.shared.add(logFac.createMCFuncWrongReturnType(ctx.ID().getText(), ctx, type, Type._void));
            throw new MCFuncWrongReturnTypeException();
        }


        if (!functionSignatures.containsKey(ctx.ID().getText())) {
            functionSignatures.put(ctx.ID().getText(), new FuncEntry(
                    this.nextIsMCFunc, ctx.ID().getText(), type, params, ctx)
            );
        } else {
            ctx.isDuplicate = true;
            Logger.shared.add(logFac.createVariableAlreadyDeclaredLog(ctx.ID().getText(), ctx));
        }

        return Type._void;
    }

    /**
     * Only visits the return statement, since the body is irrelevant for signatures.
     * @param ctx The CST node for function body
     * @return the <i>void</i> type
     */
    @Override
    public Type visitFuncBody(MinespeakParser.FuncBodyContext ctx) {
        if (ctx.retVal() != null) {
            visit(ctx.retVal());
        }

        return Type._void;
    }

    /**
     * Searches for the function, where after it saves the return type declared by the function.
     * Adds the return entry to the function and then saves it in the list of functions.
     * Finally, returns the return type of the function.
     * @param ctx The CST node for return statement
     * @return the return type of the declared function
     */
    @Override
    public Type visitRetVal(MinespeakParser.RetValContext ctx) {
        FuncEntry func = functionSignatures.get(((MinespeakParser.FuncContext)ctx.parent.parent).funcSignature().ID().getText());
        Type type = func.getType();
        func.retVal = entryFac.createFromType("return", type, ctx, MinespeakParser.VAR);
        functionSignatures.replace(func.getName(), func);

        return type;
    }

    /**
     * Visits each parameter and adds them to the list of current parameters.
     * @param ctx The CST node for params
     * @return the <i>void</i> type
     */
    @Override
    public Type visitParams(MinespeakParser.ParamsContext ctx) {
        for (MinespeakParser.ParamContext param : ctx.param()) {
            currentParameters.add(entryFac.createFromType(
                    param.ID().getText(),
                    visit(param.primaryType()),
                    param,
                    MinespeakParser.VAR)
            );
        }

        return Type._void;
    }

    /**
     * Determines the type for the parameter.
     * @param ctx The CST node for param
     * @return the type of the node
     */
    @Override
    public Type visitParam(MinespeakParser.ParamContext ctx) {
        return visit(ctx.primaryType());
    }

    /**
     * Determines the type for the node by visiting the primitive type,
     * and determining whether it is an array type.
     * @param ctx The CST node for the primary type
     * @return the type of the node
     */
    @Override
    public Type visitPrimaryType(MinespeakParser.PrimaryTypeContext ctx) {
        Type primType = visit(ctx.primitiveType());
        if (ctx.ARRAY() != null || ctx.lArray() != null) {
            return new ArrayType(ctx, primType);
        }
        return primType;
    }

    /**
     * Determines the type for the node by looking at the children of the node.
     * @param ctx The CST node for the primitive type
     * @return the type of the node
     */
    @Override
    public Type visitPrimitiveType(MinespeakParser.PrimitiveTypeContext ctx) {
        if (ctx.NUM() != null)
            return Type._num;
        else if (ctx.BLOCK() != null)
            return Type._block;
        else if (ctx.BOOL() != null)
            return Type._bool;
        else if (ctx.STRING() != null)
            return Type._string;
        else if (ctx.VECTOR2() != null)
            return Type._vector2;
        else if (ctx.VECTOR3() != null)
            return Type._vector3;
        else
            return Type._error;
    }
}
