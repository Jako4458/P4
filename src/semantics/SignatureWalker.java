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

    @Override
    public Type visitBlocks(MinespeakParser.BlocksContext ctx) {
        for (ParseTree block : ctx.block()) {
            if (block != null) {
                visit(block);
            }
        }
        return Type._void;
    }

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

    @Override
    public Type visitMcFunc(MinespeakParser.McFuncContext ctx) {
        this.nextIsMCFunc = true;
        visit(ctx.func());
        this.nextIsMCFunc = false;
        return Type._void;
    }

    @Override
    public Type visitFunc(MinespeakParser.FuncContext ctx) {
        visit(ctx.funcSignature());
        visit(ctx.funcBody());
        this.currentParameters = new ArrayList<>();
        return Type._void;
    }

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

    @Override
    public Type visitFuncBody(MinespeakParser.FuncBodyContext ctx) {
        if (ctx.retVal() != null) {
            visit(ctx.retVal());
        }

        return Type._void;
    }

    @Override
    public Type visitRetVal(MinespeakParser.RetValContext ctx) {
        FuncEntry func = functionSignatures.get(((MinespeakParser.FuncContext)ctx.parent.parent).funcSignature().ID().getText());
        Type type = func.getType();
        func.retVal = entryFac.createFromType("return", type, ctx, MinespeakParser.VAR);
        functionSignatures.replace(func.getName(), func);

        return type;
    }

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

    @Override
    public Type visitParam(MinespeakParser.ParamContext ctx) {
        return visit(ctx.primaryType());
    }

    @Override
    public Type visitPrimaryType(MinespeakParser.PrimaryTypeContext ctx) {
        Type primType = visit(ctx.primitiveType());
        if (ctx.ARRAY() != null || ctx.lArray() != null) {
            return new ArrayType(ctx, primType);
        }
        return primType;
    }

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
