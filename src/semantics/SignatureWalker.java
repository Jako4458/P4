import org.antlr.v4.runtime.tree.ParseTree;

import java.util.ArrayList;
import java.util.List;

public class SignatureWalker extends MinespeakBaseVisitor<Type> {
    private boolean nextIsMCFunc = false;
    public List<FuncEntry> functionSignatures = new ArrayList<>();
    private List<SimpleEntry> currentParameters = new ArrayList<>();


    @Override
    public Type visitProg(MinespeakParser.ProgContext ctx) {
        if (ctx.blocks() != null) {
            visit(ctx.blocks());
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
        this.currentParameters = new ArrayList<>();
        return Type._void;
    }

    @Override
    public Type visitFuncSignature(MinespeakParser.FuncSignatureContext ctx) {
        visit(ctx.params());
        List<SimpleEntry> params = this.currentParameters;
        Type type = Type._void;
        if (ctx.primaryType() != null)
            type = visit(ctx.primaryType());

        functionSignatures.add(new FuncEntry(
                this.nextIsMCFunc, ctx.ID().getText(), type, params, ctx)
        );

        return Type._void;
    }

    @Override
    public Type visitParams(MinespeakParser.ParamsContext ctx) {
        for (MinespeakParser.ParamContext param : ctx.param()) {
            currentParameters.add(new SimpleEntry(param.ID().getText(),
                    visit(param.primaryType()))
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
