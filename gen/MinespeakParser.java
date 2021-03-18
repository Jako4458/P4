// Generated from D:/SKOLE/Guthub/P4/parser\Minespeak.g4 by ANTLR 4.9.1
import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.misc.*;
import org.antlr.v4.runtime.tree.*;
import java.util.List;
import java.util.Iterator;
import java.util.ArrayList;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public class MinespeakParser extends Parser {
	static { RuntimeMetaData.checkVersion("4.9.1", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		T__0=1, T__1=2, T__2=3, T__3=4, T__4=5, T__5=6, T__6=7, T__7=8, T__8=9, 
		T__9=10, T__10=11, T__11=12, T__12=13, T__13=14, T__14=15, T__15=16, T__16=17, 
		T__17=18, T__18=19, T__19=20, T__20=21, T__21=22, T__22=23, T__23=24, 
		T__24=25, T__25=26, T__26=27, T__27=28, T__28=29, T__29=30, T__30=31, 
		T__31=32, T__32=33, T__33=34, T__34=35, T__35=36, T__36=37, T__37=38, 
		T__38=39, T__39=40, T__40=41, T__41=42, Access=43, CompAssign=44, PrimitiveType=45, 
		BooleanLiteral=46, HexadecimalDigit=47, DecimalDigit=48, StringLiteral=49, 
		ID=50, Whitespace=51, Newline=52, BlockLiteral=53, MCStmnt=54;
	public static final int
		RULE_prog = 0, RULE_blocks = 1, RULE_block = 2, RULE_mcFunc = 3, RULE_func = 4, 
		RULE_params = 5, RULE_param = 6, RULE_funcBody = 7, RULE_retVal = 8, RULE_stmnts = 9, 
		RULE_stmnt = 10, RULE_loop = 11, RULE_doWhile = 12, RULE_whileStmnt = 13, 
		RULE_foreach = 14, RULE_forStmnt = 15, RULE_ifStmnt = 16, RULE_dcls = 17, 
		RULE_instan = 18, RULE_expr = 19, RULE_factor = 20, RULE_funcCall = 21, 
		RULE_assign = 22, RULE_type = 23, RULE_literal = 24, RULE_numberLiteral = 25, 
		RULE_vector2Literal = 26, RULE_vector3Literal = 27, RULE_newlines = 28;
	private static String[] makeRuleNames() {
		return new String[] {
			"prog", "blocks", "block", "mcFunc", "func", "params", "param", "funcBody", 
			"retVal", "stmnts", "stmnt", "loop", "doWhile", "whileStmnt", "foreach", 
			"forStmnt", "ifStmnt", "dcls", "instan", "expr", "factor", "funcCall", 
			"assign", "type", "literal", "numberLiteral", "vector2Literal", "vector3Literal", 
			"newlines"
		};
	}
	public static final String[] ruleNames = makeRuleNames();

	private static String[] makeLiteralNames() {
		return new String[] {
			null, "'minespeak'", "'closespeak'", "'@mc'", "'func'", "'('", "')'", 
			"'->'", "'do'", "'endfunc'", "','", "':'", "'return'", "'while'", "'endwhile'", 
			"'foreach'", "'in'", "'endfor'", "'for'", "'until'", "'where'", "'if'", 
			"'elif'", "'else'", "'endif'", "'='", "'not'", "'-'", "'Pow'", "'*'", 
			"'/'", "'%'", "'+'", "'<'", "'>'", "'<='", "'>='", "'=='", "'!='", "'and'", 
			"'or'", "'['", "']'"
		};
	}
	private static final String[] _LITERAL_NAMES = makeLiteralNames();
	private static String[] makeSymbolicNames() {
		return new String[] {
			null, null, null, null, null, null, null, null, null, null, null, null, 
			null, null, null, null, null, null, null, null, null, null, null, null, 
			null, null, null, null, null, null, null, null, null, null, null, null, 
			null, null, null, null, null, null, null, "Access", "CompAssign", "PrimitiveType", 
			"BooleanLiteral", "HexadecimalDigit", "DecimalDigit", "StringLiteral", 
			"ID", "Whitespace", "Newline", "BlockLiteral", "MCStmnt"
		};
	}
	private static final String[] _SYMBOLIC_NAMES = makeSymbolicNames();
	public static final Vocabulary VOCABULARY = new VocabularyImpl(_LITERAL_NAMES, _SYMBOLIC_NAMES);

	/**
	 * @deprecated Use {@link #VOCABULARY} instead.
	 */
	@Deprecated
	public static final String[] tokenNames;
	static {
		tokenNames = new String[_SYMBOLIC_NAMES.length];
		for (int i = 0; i < tokenNames.length; i++) {
			tokenNames[i] = VOCABULARY.getLiteralName(i);
			if (tokenNames[i] == null) {
				tokenNames[i] = VOCABULARY.getSymbolicName(i);
			}

			if (tokenNames[i] == null) {
				tokenNames[i] = "<INVALID>";
			}
		}
	}

	@Override
	@Deprecated
	public String[] getTokenNames() {
		return tokenNames;
	}

	@Override

	public Vocabulary getVocabulary() {
		return VOCABULARY;
	}

	@Override
	public String getGrammarFileName() { return "Minespeak.g4"; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public ATN getATN() { return _ATN; }

	public MinespeakParser(TokenStream input) {
		super(input);
		_interp = new ParserATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}

	public static class ProgContext extends ParserRuleContext {
		public NewlinesContext newlines() {
			return getRuleContext(NewlinesContext.class,0);
		}
		public BlocksContext blocks() {
			return getRuleContext(BlocksContext.class,0);
		}
		public ProgContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_prog; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MinespeakListener ) ((MinespeakListener)listener).enterProg(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MinespeakListener ) ((MinespeakListener)listener).exitProg(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MinespeakVisitor ) return ((MinespeakVisitor<? extends T>)visitor).visitProg(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ProgContext prog() throws RecognitionException {
		ProgContext _localctx = new ProgContext(_ctx, getState());
		enterRule(_localctx, 0, RULE_prog);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(58);
			match(T__0);
			setState(59);
			newlines();
			setState(61);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__2) | (1L << T__3) | (1L << T__7) | (1L << T__12) | (1L << T__14) | (1L << T__17) | (1L << T__20) | (1L << Access) | (1L << ID) | (1L << MCStmnt))) != 0)) {
				{
				setState(60);
				blocks();
				}
			}

			setState(63);
			match(T__1);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class BlocksContext extends ParserRuleContext {
		public List<BlockContext> block() {
			return getRuleContexts(BlockContext.class);
		}
		public BlockContext block(int i) {
			return getRuleContext(BlockContext.class,i);
		}
		public List<NewlinesContext> newlines() {
			return getRuleContexts(NewlinesContext.class);
		}
		public NewlinesContext newlines(int i) {
			return getRuleContext(NewlinesContext.class,i);
		}
		public BlocksContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_blocks; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MinespeakListener ) ((MinespeakListener)listener).enterBlocks(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MinespeakListener ) ((MinespeakListener)listener).exitBlocks(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MinespeakVisitor ) return ((MinespeakVisitor<? extends T>)visitor).visitBlocks(this);
			else return visitor.visitChildren(this);
		}
	}

	public final BlocksContext blocks() throws RecognitionException {
		BlocksContext _localctx = new BlocksContext(_ctx, getState());
		enterRule(_localctx, 2, RULE_blocks);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(69); 
			_errHandler.sync(this);
			_la = _input.LA(1);
			do {
				{
				{
				setState(65);
				block();
				setState(67);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==Newline) {
					{
					setState(66);
					newlines();
					}
				}

				}
				}
				setState(71); 
				_errHandler.sync(this);
				_la = _input.LA(1);
			} while ( (((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__2) | (1L << T__3) | (1L << T__7) | (1L << T__12) | (1L << T__14) | (1L << T__17) | (1L << T__20) | (1L << Access) | (1L << ID) | (1L << MCStmnt))) != 0) );
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class BlockContext extends ParserRuleContext {
		public StmntsContext stmnts() {
			return getRuleContext(StmntsContext.class,0);
		}
		public McFuncContext mcFunc() {
			return getRuleContext(McFuncContext.class,0);
		}
		public FuncContext func() {
			return getRuleContext(FuncContext.class,0);
		}
		public BlockContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_block; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MinespeakListener ) ((MinespeakListener)listener).enterBlock(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MinespeakListener ) ((MinespeakListener)listener).exitBlock(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MinespeakVisitor ) return ((MinespeakVisitor<? extends T>)visitor).visitBlock(this);
			else return visitor.visitChildren(this);
		}
	}

	public final BlockContext block() throws RecognitionException {
		BlockContext _localctx = new BlockContext(_ctx, getState());
		enterRule(_localctx, 4, RULE_block);
		try {
			setState(76);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case T__7:
			case T__12:
			case T__14:
			case T__17:
			case T__20:
			case Access:
			case ID:
			case MCStmnt:
				enterOuterAlt(_localctx, 1);
				{
				setState(73);
				stmnts();
				}
				break;
			case T__2:
				enterOuterAlt(_localctx, 2);
				{
				setState(74);
				mcFunc();
				}
				break;
			case T__3:
				enterOuterAlt(_localctx, 3);
				{
				setState(75);
				func();
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class McFuncContext extends ParserRuleContext {
		public TerminalNode Newline() { return getToken(MinespeakParser.Newline, 0); }
		public FuncContext func() {
			return getRuleContext(FuncContext.class,0);
		}
		public McFuncContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_mcFunc; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MinespeakListener ) ((MinespeakListener)listener).enterMcFunc(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MinespeakListener ) ((MinespeakListener)listener).exitMcFunc(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MinespeakVisitor ) return ((MinespeakVisitor<? extends T>)visitor).visitMcFunc(this);
			else return visitor.visitChildren(this);
		}
	}

	public final McFuncContext mcFunc() throws RecognitionException {
		McFuncContext _localctx = new McFuncContext(_ctx, getState());
		enterRule(_localctx, 6, RULE_mcFunc);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(78);
			match(T__2);
			setState(79);
			match(Newline);
			setState(80);
			func();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class FuncContext extends ParserRuleContext {
		public TerminalNode ID() { return getToken(MinespeakParser.ID, 0); }
		public ParamsContext params() {
			return getRuleContext(ParamsContext.class,0);
		}
		public List<NewlinesContext> newlines() {
			return getRuleContexts(NewlinesContext.class);
		}
		public NewlinesContext newlines(int i) {
			return getRuleContext(NewlinesContext.class,i);
		}
		public FuncBodyContext funcBody() {
			return getRuleContext(FuncBodyContext.class,0);
		}
		public TerminalNode Newline() { return getToken(MinespeakParser.Newline, 0); }
		public TypeContext type() {
			return getRuleContext(TypeContext.class,0);
		}
		public FuncContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_func; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MinespeakListener ) ((MinespeakListener)listener).enterFunc(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MinespeakListener ) ((MinespeakListener)listener).exitFunc(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MinespeakVisitor ) return ((MinespeakVisitor<? extends T>)visitor).visitFunc(this);
			else return visitor.visitChildren(this);
		}
	}

	public final FuncContext func() throws RecognitionException {
		FuncContext _localctx = new FuncContext(_ctx, getState());
		enterRule(_localctx, 8, RULE_func);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(82);
			match(T__3);
			setState(83);
			match(ID);
			setState(84);
			match(T__4);
			setState(85);
			params();
			setState(86);
			match(T__5);
			setState(89);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==T__6) {
				{
				setState(87);
				match(T__6);
				setState(88);
				type();
				}
			}

			setState(91);
			match(T__7);
			setState(92);
			newlines();
			setState(93);
			funcBody();
			setState(95);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==Newline) {
				{
				setState(94);
				newlines();
				}
			}

			setState(97);
			match(T__8);
			setState(98);
			match(Newline);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ParamsContext extends ParserRuleContext {
		public List<ParamContext> param() {
			return getRuleContexts(ParamContext.class);
		}
		public ParamContext param(int i) {
			return getRuleContext(ParamContext.class,i);
		}
		public ParamsContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_params; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MinespeakListener ) ((MinespeakListener)listener).enterParams(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MinespeakListener ) ((MinespeakListener)listener).exitParams(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MinespeakVisitor ) return ((MinespeakVisitor<? extends T>)visitor).visitParams(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ParamsContext params() throws RecognitionException {
		ParamsContext _localctx = new ParamsContext(_ctx, getState());
		enterRule(_localctx, 10, RULE_params);
		int _la;
		try {
			setState(109);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case ID:
				enterOuterAlt(_localctx, 1);
				{
				setState(100);
				param();
				setState(105);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (_la==T__9) {
					{
					{
					setState(101);
					match(T__9);
					setState(102);
					param();
					}
					}
					setState(107);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				}
				break;
			case T__5:
				enterOuterAlt(_localctx, 2);
				{
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ParamContext extends ParserRuleContext {
		public TerminalNode ID() { return getToken(MinespeakParser.ID, 0); }
		public TypeContext type() {
			return getRuleContext(TypeContext.class,0);
		}
		public ParamContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_param; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MinespeakListener ) ((MinespeakListener)listener).enterParam(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MinespeakListener ) ((MinespeakListener)listener).exitParam(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MinespeakVisitor ) return ((MinespeakVisitor<? extends T>)visitor).visitParam(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ParamContext param() throws RecognitionException {
		ParamContext _localctx = new ParamContext(_ctx, getState());
		enterRule(_localctx, 12, RULE_param);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(111);
			match(ID);
			setState(112);
			match(T__10);
			setState(113);
			type();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class FuncBodyContext extends ParserRuleContext {
		public StmntsContext stmnts() {
			return getRuleContext(StmntsContext.class,0);
		}
		public RetValContext retVal() {
			return getRuleContext(RetValContext.class,0);
		}
		public FuncBodyContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_funcBody; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MinespeakListener ) ((MinespeakListener)listener).enterFuncBody(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MinespeakListener ) ((MinespeakListener)listener).exitFuncBody(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MinespeakVisitor ) return ((MinespeakVisitor<? extends T>)visitor).visitFuncBody(this);
			else return visitor.visitChildren(this);
		}
	}

	public final FuncBodyContext funcBody() throws RecognitionException {
		FuncBodyContext _localctx = new FuncBodyContext(_ctx, getState());
		enterRule(_localctx, 14, RULE_funcBody);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(116);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__7) | (1L << T__12) | (1L << T__14) | (1L << T__17) | (1L << T__20) | (1L << Access) | (1L << ID) | (1L << MCStmnt))) != 0)) {
				{
				setState(115);
				stmnts();
				}
			}

			setState(119);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==T__11) {
				{
				setState(118);
				retVal();
				}
			}

			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class RetValContext extends ParserRuleContext {
		public ExprContext expr() {
			return getRuleContext(ExprContext.class,0);
		}
		public RetValContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_retVal; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MinespeakListener ) ((MinespeakListener)listener).enterRetVal(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MinespeakListener ) ((MinespeakListener)listener).exitRetVal(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MinespeakVisitor ) return ((MinespeakVisitor<? extends T>)visitor).visitRetVal(this);
			else return visitor.visitChildren(this);
		}
	}

	public final RetValContext retVal() throws RecognitionException {
		RetValContext _localctx = new RetValContext(_ctx, getState());
		enterRule(_localctx, 16, RULE_retVal);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(121);
			match(T__11);
			setState(123);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__4) | (1L << T__25) | (1L << T__26) | (1L << T__32) | (1L << BooleanLiteral) | (1L << HexadecimalDigit) | (1L << DecimalDigit) | (1L << StringLiteral) | (1L << ID) | (1L << BlockLiteral))) != 0)) {
				{
				setState(122);
				expr(0);
				}
			}

			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class StmntsContext extends ParserRuleContext {
		public List<StmntContext> stmnt() {
			return getRuleContexts(StmntContext.class);
		}
		public StmntContext stmnt(int i) {
			return getRuleContext(StmntContext.class,i);
		}
		public List<NewlinesContext> newlines() {
			return getRuleContexts(NewlinesContext.class);
		}
		public NewlinesContext newlines(int i) {
			return getRuleContext(NewlinesContext.class,i);
		}
		public StmntsContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_stmnts; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MinespeakListener ) ((MinespeakListener)listener).enterStmnts(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MinespeakListener ) ((MinespeakListener)listener).exitStmnts(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MinespeakVisitor ) return ((MinespeakVisitor<? extends T>)visitor).visitStmnts(this);
			else return visitor.visitChildren(this);
		}
	}

	public final StmntsContext stmnts() throws RecognitionException {
		StmntsContext _localctx = new StmntsContext(_ctx, getState());
		enterRule(_localctx, 18, RULE_stmnts);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(128); 
			_errHandler.sync(this);
			_alt = 1;
			do {
				switch (_alt) {
				case 1:
					{
					{
					setState(125);
					stmnt();
					setState(126);
					newlines();
					}
					}
					break;
				default:
					throw new NoViableAltException(this);
				}
				setState(130); 
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,11,_ctx);
			} while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER );
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class StmntContext extends ParserRuleContext {
		public DclsContext dcls() {
			return getRuleContext(DclsContext.class,0);
		}
		public AssignContext assign() {
			return getRuleContext(AssignContext.class,0);
		}
		public InstanContext instan() {
			return getRuleContext(InstanContext.class,0);
		}
		public IfStmntContext ifStmnt() {
			return getRuleContext(IfStmntContext.class,0);
		}
		public LoopContext loop() {
			return getRuleContext(LoopContext.class,0);
		}
		public TerminalNode MCStmnt() { return getToken(MinespeakParser.MCStmnt, 0); }
		public FuncCallContext funcCall() {
			return getRuleContext(FuncCallContext.class,0);
		}
		public StmntContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_stmnt; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MinespeakListener ) ((MinespeakListener)listener).enterStmnt(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MinespeakListener ) ((MinespeakListener)listener).exitStmnt(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MinespeakVisitor ) return ((MinespeakVisitor<? extends T>)visitor).visitStmnt(this);
			else return visitor.visitChildren(this);
		}
	}

	public final StmntContext stmnt() throws RecognitionException {
		StmntContext _localctx = new StmntContext(_ctx, getState());
		enterRule(_localctx, 20, RULE_stmnt);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(139);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,12,_ctx) ) {
			case 1:
				{
				setState(132);
				dcls();
				}
				break;
			case 2:
				{
				setState(133);
				assign();
				}
				break;
			case 3:
				{
				setState(134);
				instan();
				}
				break;
			case 4:
				{
				setState(135);
				ifStmnt();
				}
				break;
			case 5:
				{
				setState(136);
				loop();
				}
				break;
			case 6:
				{
				setState(137);
				match(MCStmnt);
				}
				break;
			case 7:
				{
				setState(138);
				funcCall();
				}
				break;
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class LoopContext extends ParserRuleContext {
		public ForStmntContext forStmnt() {
			return getRuleContext(ForStmntContext.class,0);
		}
		public ForeachContext foreach() {
			return getRuleContext(ForeachContext.class,0);
		}
		public WhileStmntContext whileStmnt() {
			return getRuleContext(WhileStmntContext.class,0);
		}
		public DoWhileContext doWhile() {
			return getRuleContext(DoWhileContext.class,0);
		}
		public LoopContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_loop; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MinespeakListener ) ((MinespeakListener)listener).enterLoop(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MinespeakListener ) ((MinespeakListener)listener).exitLoop(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MinespeakVisitor ) return ((MinespeakVisitor<? extends T>)visitor).visitLoop(this);
			else return visitor.visitChildren(this);
		}
	}

	public final LoopContext loop() throws RecognitionException {
		LoopContext _localctx = new LoopContext(_ctx, getState());
		enterRule(_localctx, 22, RULE_loop);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(145);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case T__17:
				{
				setState(141);
				forStmnt();
				}
				break;
			case T__14:
				{
				setState(142);
				foreach();
				}
				break;
			case T__12:
				{
				setState(143);
				whileStmnt();
				}
				break;
			case T__7:
				{
				setState(144);
				doWhile();
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class DoWhileContext extends ParserRuleContext {
		public ExprContext expr() {
			return getRuleContext(ExprContext.class,0);
		}
		public StmntsContext stmnts() {
			return getRuleContext(StmntsContext.class,0);
		}
		public DoWhileContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_doWhile; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MinespeakListener ) ((MinespeakListener)listener).enterDoWhile(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MinespeakListener ) ((MinespeakListener)listener).exitDoWhile(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MinespeakVisitor ) return ((MinespeakVisitor<? extends T>)visitor).visitDoWhile(this);
			else return visitor.visitChildren(this);
		}
	}

	public final DoWhileContext doWhile() throws RecognitionException {
		DoWhileContext _localctx = new DoWhileContext(_ctx, getState());
		enterRule(_localctx, 24, RULE_doWhile);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(147);
			match(T__7);
			setState(149);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,14,_ctx) ) {
			case 1:
				{
				setState(148);
				stmnts();
				}
				break;
			}
			setState(151);
			match(T__12);
			setState(152);
			expr(0);
			setState(153);
			match(T__13);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class WhileStmntContext extends ParserRuleContext {
		public ExprContext expr() {
			return getRuleContext(ExprContext.class,0);
		}
		public StmntsContext stmnts() {
			return getRuleContext(StmntsContext.class,0);
		}
		public WhileStmntContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_whileStmnt; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MinespeakListener ) ((MinespeakListener)listener).enterWhileStmnt(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MinespeakListener ) ((MinespeakListener)listener).exitWhileStmnt(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MinespeakVisitor ) return ((MinespeakVisitor<? extends T>)visitor).visitWhileStmnt(this);
			else return visitor.visitChildren(this);
		}
	}

	public final WhileStmntContext whileStmnt() throws RecognitionException {
		WhileStmntContext _localctx = new WhileStmntContext(_ctx, getState());
		enterRule(_localctx, 26, RULE_whileStmnt);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(155);
			match(T__12);
			setState(156);
			expr(0);
			setState(157);
			match(T__7);
			setState(159);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__7) | (1L << T__12) | (1L << T__14) | (1L << T__17) | (1L << T__20) | (1L << Access) | (1L << ID) | (1L << MCStmnt))) != 0)) {
				{
				setState(158);
				stmnts();
				}
			}

			setState(161);
			match(T__13);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ForeachContext extends ParserRuleContext {
		public TypeContext type() {
			return getRuleContext(TypeContext.class,0);
		}
		public TerminalNode ID() { return getToken(MinespeakParser.ID, 0); }
		public ExprContext expr() {
			return getRuleContext(ExprContext.class,0);
		}
		public StmntsContext stmnts() {
			return getRuleContext(StmntsContext.class,0);
		}
		public ForeachContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_foreach; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MinespeakListener ) ((MinespeakListener)listener).enterForeach(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MinespeakListener ) ((MinespeakListener)listener).exitForeach(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MinespeakVisitor ) return ((MinespeakVisitor<? extends T>)visitor).visitForeach(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ForeachContext foreach() throws RecognitionException {
		ForeachContext _localctx = new ForeachContext(_ctx, getState());
		enterRule(_localctx, 28, RULE_foreach);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(163);
			match(T__14);
			setState(164);
			type();
			setState(165);
			match(ID);
			setState(166);
			match(T__15);
			setState(167);
			expr(0);
			setState(168);
			match(T__7);
			setState(170);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__7) | (1L << T__12) | (1L << T__14) | (1L << T__17) | (1L << T__20) | (1L << Access) | (1L << ID) | (1L << MCStmnt))) != 0)) {
				{
				setState(169);
				stmnts();
				}
			}

			setState(172);
			match(T__16);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ForStmntContext extends ParserRuleContext {
		public List<AssignContext> assign() {
			return getRuleContexts(AssignContext.class);
		}
		public AssignContext assign(int i) {
			return getRuleContext(AssignContext.class,i);
		}
		public ExprContext expr() {
			return getRuleContext(ExprContext.class,0);
		}
		public NewlinesContext newlines() {
			return getRuleContext(NewlinesContext.class,0);
		}
		public StmntsContext stmnts() {
			return getRuleContext(StmntsContext.class,0);
		}
		public ForStmntContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_forStmnt; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MinespeakListener ) ((MinespeakListener)listener).enterForStmnt(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MinespeakListener ) ((MinespeakListener)listener).exitForStmnt(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MinespeakVisitor ) return ((MinespeakVisitor<? extends T>)visitor).visitForStmnt(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ForStmntContext forStmnt() throws RecognitionException {
		ForStmntContext _localctx = new ForStmntContext(_ctx, getState());
		enterRule(_localctx, 30, RULE_forStmnt);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(174);
			match(T__17);
			setState(175);
			assign();
			setState(176);
			match(T__18);
			setState(177);
			expr(0);
			setState(178);
			match(T__19);
			setState(179);
			assign();
			setState(180);
			match(T__7);
			setState(181);
			newlines();
			setState(183);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__7) | (1L << T__12) | (1L << T__14) | (1L << T__17) | (1L << T__20) | (1L << Access) | (1L << ID) | (1L << MCStmnt))) != 0)) {
				{
				setState(182);
				stmnts();
				}
			}

			setState(185);
			match(T__16);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class IfStmntContext extends ParserRuleContext {
		public List<ExprContext> expr() {
			return getRuleContexts(ExprContext.class);
		}
		public ExprContext expr(int i) {
			return getRuleContext(ExprContext.class,i);
		}
		public List<NewlinesContext> newlines() {
			return getRuleContexts(NewlinesContext.class);
		}
		public NewlinesContext newlines(int i) {
			return getRuleContext(NewlinesContext.class,i);
		}
		public List<StmntsContext> stmnts() {
			return getRuleContexts(StmntsContext.class);
		}
		public StmntsContext stmnts(int i) {
			return getRuleContext(StmntsContext.class,i);
		}
		public IfStmntContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_ifStmnt; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MinespeakListener ) ((MinespeakListener)listener).enterIfStmnt(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MinespeakListener ) ((MinespeakListener)listener).exitIfStmnt(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MinespeakVisitor ) return ((MinespeakVisitor<? extends T>)visitor).visitIfStmnt(this);
			else return visitor.visitChildren(this);
		}
	}

	public final IfStmntContext ifStmnt() throws RecognitionException {
		IfStmntContext _localctx = new IfStmntContext(_ctx, getState());
		enterRule(_localctx, 32, RULE_ifStmnt);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(187);
			match(T__20);
			setState(188);
			expr(0);
			setState(189);
			match(T__7);
			setState(190);
			newlines();
			setState(192);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__7) | (1L << T__12) | (1L << T__14) | (1L << T__17) | (1L << T__20) | (1L << Access) | (1L << ID) | (1L << MCStmnt))) != 0)) {
				{
				setState(191);
				stmnts();
				}
			}

			setState(203);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==T__21) {
				{
				{
				setState(194);
				match(T__21);
				setState(195);
				expr(0);
				setState(196);
				match(T__7);
				setState(197);
				newlines();
				setState(199);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__7) | (1L << T__12) | (1L << T__14) | (1L << T__17) | (1L << T__20) | (1L << Access) | (1L << ID) | (1L << MCStmnt))) != 0)) {
					{
					setState(198);
					stmnts();
					}
				}

				}
				}
				setState(205);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(212);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==T__22) {
				{
				setState(206);
				match(T__22);
				setState(207);
				match(T__7);
				setState(208);
				newlines();
				setState(210);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__7) | (1L << T__12) | (1L << T__14) | (1L << T__17) | (1L << T__20) | (1L << Access) | (1L << ID) | (1L << MCStmnt))) != 0)) {
					{
					setState(209);
					stmnts();
					}
				}

				}
			}

			setState(214);
			match(T__23);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class DclsContext extends ParserRuleContext {
		public TerminalNode Access() { return getToken(MinespeakParser.Access, 0); }
		public List<TerminalNode> ID() { return getTokens(MinespeakParser.ID); }
		public TerminalNode ID(int i) {
			return getToken(MinespeakParser.ID, i);
		}
		public List<TypeContext> type() {
			return getRuleContexts(TypeContext.class);
		}
		public TypeContext type(int i) {
			return getRuleContext(TypeContext.class,i);
		}
		public DclsContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_dcls; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MinespeakListener ) ((MinespeakListener)listener).enterDcls(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MinespeakListener ) ((MinespeakListener)listener).exitDcls(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MinespeakVisitor ) return ((MinespeakVisitor<? extends T>)visitor).visitDcls(this);
			else return visitor.visitChildren(this);
		}
	}

	public final DclsContext dcls() throws RecognitionException {
		DclsContext _localctx = new DclsContext(_ctx, getState());
		enterRule(_localctx, 34, RULE_dcls);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(216);
			match(Access);
			setState(217);
			match(ID);
			setState(218);
			match(T__10);
			setState(219);
			type();
			setState(226);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==T__9) {
				{
				{
				setState(220);
				match(T__9);
				setState(221);
				match(ID);
				setState(222);
				match(T__10);
				setState(223);
				type();
				}
				}
				setState(228);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class InstanContext extends ParserRuleContext {
		public TerminalNode Access() { return getToken(MinespeakParser.Access, 0); }
		public List<TerminalNode> ID() { return getTokens(MinespeakParser.ID); }
		public TerminalNode ID(int i) {
			return getToken(MinespeakParser.ID, i);
		}
		public List<TypeContext> type() {
			return getRuleContexts(TypeContext.class);
		}
		public TypeContext type(int i) {
			return getRuleContext(TypeContext.class,i);
		}
		public List<ExprContext> expr() {
			return getRuleContexts(ExprContext.class);
		}
		public ExprContext expr(int i) {
			return getRuleContext(ExprContext.class,i);
		}
		public InstanContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_instan; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MinespeakListener ) ((MinespeakListener)listener).enterInstan(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MinespeakListener ) ((MinespeakListener)listener).exitInstan(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MinespeakVisitor ) return ((MinespeakVisitor<? extends T>)visitor).visitInstan(this);
			else return visitor.visitChildren(this);
		}
	}

	public final InstanContext instan() throws RecognitionException {
		InstanContext _localctx = new InstanContext(_ctx, getState());
		enterRule(_localctx, 36, RULE_instan);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(229);
			match(Access);
			setState(230);
			match(ID);
			setState(231);
			match(T__10);
			setState(232);
			type();
			setState(233);
			match(T__24);
			setState(234);
			expr(0);
			setState(244);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==T__9) {
				{
				{
				setState(235);
				match(T__9);
				setState(236);
				match(ID);
				setState(237);
				match(T__10);
				setState(238);
				type();
				setState(239);
				match(T__24);
				setState(240);
				expr(0);
				}
				}
				setState(246);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ExprContext extends ParserRuleContext {
		public FactorContext factor() {
			return getRuleContext(FactorContext.class,0);
		}
		public List<ExprContext> expr() {
			return getRuleContexts(ExprContext.class);
		}
		public ExprContext expr(int i) {
			return getRuleContext(ExprContext.class,i);
		}
		public ExprContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_expr; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MinespeakListener ) ((MinespeakListener)listener).enterExpr(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MinespeakListener ) ((MinespeakListener)listener).exitExpr(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MinespeakVisitor ) return ((MinespeakVisitor<? extends T>)visitor).visitExpr(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ExprContext expr() throws RecognitionException {
		return expr(0);
	}

	private ExprContext expr(int _p) throws RecognitionException {
		ParserRuleContext _parentctx = _ctx;
		int _parentState = getState();
		ExprContext _localctx = new ExprContext(_ctx, _parentState);
		ExprContext _prevctx = _localctx;
		int _startState = 38;
		enterRecursionRule(_localctx, 38, RULE_expr, _p);
		int _la;
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			{
			setState(249);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==T__25 || _la==T__26) {
				{
				setState(248);
				_la = _input.LA(1);
				if ( !(_la==T__25 || _la==T__26) ) {
				_errHandler.recoverInline(this);
				}
				else {
					if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
					_errHandler.reportMatch(this);
					consume();
				}
				}
			}

			setState(251);
			factor();
			}
			_ctx.stop = _input.LT(-1);
			setState(276);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,27,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					if ( _parseListeners!=null ) triggerExitRuleEvent();
					_prevctx = _localctx;
					{
					setState(274);
					_errHandler.sync(this);
					switch ( getInterpreter().adaptivePredict(_input,26,_ctx) ) {
					case 1:
						{
						_localctx = new ExprContext(_parentctx, _parentState);
						pushNewRecursionContext(_localctx, _startState, RULE_expr);
						setState(253);
						if (!(precpred(_ctx, 7))) throw new FailedPredicateException(this, "precpred(_ctx, 7)");
						setState(254);
						match(T__27);
						setState(255);
						expr(8);
						}
						break;
					case 2:
						{
						_localctx = new ExprContext(_parentctx, _parentState);
						pushNewRecursionContext(_localctx, _startState, RULE_expr);
						setState(256);
						if (!(precpred(_ctx, 6))) throw new FailedPredicateException(this, "precpred(_ctx, 6)");
						setState(257);
						_la = _input.LA(1);
						if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__28) | (1L << T__29) | (1L << T__30))) != 0)) ) {
						_errHandler.recoverInline(this);
						}
						else {
							if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
							_errHandler.reportMatch(this);
							consume();
						}
						setState(258);
						expr(7);
						}
						break;
					case 3:
						{
						_localctx = new ExprContext(_parentctx, _parentState);
						pushNewRecursionContext(_localctx, _startState, RULE_expr);
						setState(259);
						if (!(precpred(_ctx, 5))) throw new FailedPredicateException(this, "precpred(_ctx, 5)");
						setState(260);
						_la = _input.LA(1);
						if ( !(_la==T__26 || _la==T__31) ) {
						_errHandler.recoverInline(this);
						}
						else {
							if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
							_errHandler.reportMatch(this);
							consume();
						}
						setState(261);
						expr(6);
						}
						break;
					case 4:
						{
						_localctx = new ExprContext(_parentctx, _parentState);
						pushNewRecursionContext(_localctx, _startState, RULE_expr);
						setState(262);
						if (!(precpred(_ctx, 4))) throw new FailedPredicateException(this, "precpred(_ctx, 4)");
						setState(263);
						_la = _input.LA(1);
						if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__32) | (1L << T__33) | (1L << T__34) | (1L << T__35))) != 0)) ) {
						_errHandler.recoverInline(this);
						}
						else {
							if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
							_errHandler.reportMatch(this);
							consume();
						}
						setState(264);
						expr(5);
						}
						break;
					case 5:
						{
						_localctx = new ExprContext(_parentctx, _parentState);
						pushNewRecursionContext(_localctx, _startState, RULE_expr);
						setState(265);
						if (!(precpred(_ctx, 3))) throw new FailedPredicateException(this, "precpred(_ctx, 3)");
						setState(266);
						_la = _input.LA(1);
						if ( !(_la==T__36 || _la==T__37) ) {
						_errHandler.recoverInline(this);
						}
						else {
							if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
							_errHandler.reportMatch(this);
							consume();
						}
						setState(267);
						expr(4);
						}
						break;
					case 6:
						{
						_localctx = new ExprContext(_parentctx, _parentState);
						pushNewRecursionContext(_localctx, _startState, RULE_expr);
						setState(268);
						if (!(precpred(_ctx, 2))) throw new FailedPredicateException(this, "precpred(_ctx, 2)");
						setState(269);
						match(T__38);
						setState(270);
						expr(3);
						}
						break;
					case 7:
						{
						_localctx = new ExprContext(_parentctx, _parentState);
						pushNewRecursionContext(_localctx, _startState, RULE_expr);
						setState(271);
						if (!(precpred(_ctx, 1))) throw new FailedPredicateException(this, "precpred(_ctx, 1)");
						setState(272);
						match(T__39);
						setState(273);
						expr(2);
						}
						break;
					}
					} 
				}
				setState(278);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,27,_ctx);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			unrollRecursionContexts(_parentctx);
		}
		return _localctx;
	}

	public static class FactorContext extends ParserRuleContext {
		public ExprContext expr() {
			return getRuleContext(ExprContext.class,0);
		}
		public TerminalNode ID() { return getToken(MinespeakParser.ID, 0); }
		public LiteralContext literal() {
			return getRuleContext(LiteralContext.class,0);
		}
		public FuncCallContext funcCall() {
			return getRuleContext(FuncCallContext.class,0);
		}
		public FactorContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_factor; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MinespeakListener ) ((MinespeakListener)listener).enterFactor(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MinespeakListener ) ((MinespeakListener)listener).exitFactor(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MinespeakVisitor ) return ((MinespeakVisitor<? extends T>)visitor).visitFactor(this);
			else return visitor.visitChildren(this);
		}
	}

	public final FactorContext factor() throws RecognitionException {
		FactorContext _localctx = new FactorContext(_ctx, getState());
		enterRule(_localctx, 40, RULE_factor);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(286);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,28,_ctx) ) {
			case 1:
				{
				setState(279);
				match(T__4);
				setState(280);
				expr(0);
				setState(281);
				match(T__5);
				}
				break;
			case 2:
				{
				setState(283);
				match(ID);
				}
				break;
			case 3:
				{
				setState(284);
				literal();
				}
				break;
			case 4:
				{
				setState(285);
				funcCall();
				}
				break;
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class FuncCallContext extends ParserRuleContext {
		public TerminalNode ID() { return getToken(MinespeakParser.ID, 0); }
		public List<ExprContext> expr() {
			return getRuleContexts(ExprContext.class);
		}
		public ExprContext expr(int i) {
			return getRuleContext(ExprContext.class,i);
		}
		public FuncCallContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_funcCall; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MinespeakListener ) ((MinespeakListener)listener).enterFuncCall(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MinespeakListener ) ((MinespeakListener)listener).exitFuncCall(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MinespeakVisitor ) return ((MinespeakVisitor<? extends T>)visitor).visitFuncCall(this);
			else return visitor.visitChildren(this);
		}
	}

	public final FuncCallContext funcCall() throws RecognitionException {
		FuncCallContext _localctx = new FuncCallContext(_ctx, getState());
		enterRule(_localctx, 42, RULE_funcCall);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(288);
			match(ID);
			setState(289);
			match(T__4);
			setState(290);
			expr(0);
			setState(295);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==T__9) {
				{
				{
				setState(291);
				match(T__9);
				setState(292);
				expr(0);
				}
				}
				setState(297);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(298);
			match(T__5);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class AssignContext extends ParserRuleContext {
		public TerminalNode ID() { return getToken(MinespeakParser.ID, 0); }
		public ExprContext expr() {
			return getRuleContext(ExprContext.class,0);
		}
		public TerminalNode CompAssign() { return getToken(MinespeakParser.CompAssign, 0); }
		public AssignContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_assign; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MinespeakListener ) ((MinespeakListener)listener).enterAssign(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MinespeakListener ) ((MinespeakListener)listener).exitAssign(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MinespeakVisitor ) return ((MinespeakVisitor<? extends T>)visitor).visitAssign(this);
			else return visitor.visitChildren(this);
		}
	}

	public final AssignContext assign() throws RecognitionException {
		AssignContext _localctx = new AssignContext(_ctx, getState());
		enterRule(_localctx, 44, RULE_assign);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(300);
			match(ID);
			setState(301);
			_la = _input.LA(1);
			if ( !(_la==T__24 || _la==CompAssign) ) {
			_errHandler.recoverInline(this);
			}
			else {
				if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
				_errHandler.reportMatch(this);
				consume();
			}
			setState(302);
			expr(0);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class TypeContext extends ParserRuleContext {
		public TerminalNode PrimitiveType() { return getToken(MinespeakParser.PrimitiveType, 0); }
		public TypeContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_type; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MinespeakListener ) ((MinespeakListener)listener).enterType(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MinespeakListener ) ((MinespeakListener)listener).exitType(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MinespeakVisitor ) return ((MinespeakVisitor<? extends T>)visitor).visitType(this);
			else return visitor.visitChildren(this);
		}
	}

	public final TypeContext type() throws RecognitionException {
		TypeContext _localctx = new TypeContext(_ctx, getState());
		enterRule(_localctx, 46, RULE_type);
		try {
			setState(308);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,30,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(304);
				match(PrimitiveType);
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(305);
				match(PrimitiveType);
				setState(306);
				match(T__40);
				setState(307);
				match(T__41);
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class LiteralContext extends ParserRuleContext {
		public TerminalNode BooleanLiteral() { return getToken(MinespeakParser.BooleanLiteral, 0); }
		public TerminalNode BlockLiteral() { return getToken(MinespeakParser.BlockLiteral, 0); }
		public NumberLiteralContext numberLiteral() {
			return getRuleContext(NumberLiteralContext.class,0);
		}
		public TerminalNode StringLiteral() { return getToken(MinespeakParser.StringLiteral, 0); }
		public Vector2LiteralContext vector2Literal() {
			return getRuleContext(Vector2LiteralContext.class,0);
		}
		public Vector3LiteralContext vector3Literal() {
			return getRuleContext(Vector3LiteralContext.class,0);
		}
		public LiteralContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_literal; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MinespeakListener ) ((MinespeakListener)listener).enterLiteral(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MinespeakListener ) ((MinespeakListener)listener).exitLiteral(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MinespeakVisitor ) return ((MinespeakVisitor<? extends T>)visitor).visitLiteral(this);
			else return visitor.visitChildren(this);
		}
	}

	public final LiteralContext literal() throws RecognitionException {
		LiteralContext _localctx = new LiteralContext(_ctx, getState());
		enterRule(_localctx, 48, RULE_literal);
		try {
			setState(316);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,31,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(310);
				match(BooleanLiteral);
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(311);
				match(BlockLiteral);
				}
				break;
			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(312);
				numberLiteral();
				}
				break;
			case 4:
				enterOuterAlt(_localctx, 4);
				{
				setState(313);
				match(StringLiteral);
				}
				break;
			case 5:
				enterOuterAlt(_localctx, 5);
				{
				setState(314);
				vector2Literal();
				}
				break;
			case 6:
				enterOuterAlt(_localctx, 6);
				{
				setState(315);
				vector3Literal();
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class NumberLiteralContext extends ParserRuleContext {
		public TerminalNode DecimalDigit() { return getToken(MinespeakParser.DecimalDigit, 0); }
		public TerminalNode HexadecimalDigit() { return getToken(MinespeakParser.HexadecimalDigit, 0); }
		public NumberLiteralContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_numberLiteral; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MinespeakListener ) ((MinespeakListener)listener).enterNumberLiteral(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MinespeakListener ) ((MinespeakListener)listener).exitNumberLiteral(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MinespeakVisitor ) return ((MinespeakVisitor<? extends T>)visitor).visitNumberLiteral(this);
			else return visitor.visitChildren(this);
		}
	}

	public final NumberLiteralContext numberLiteral() throws RecognitionException {
		NumberLiteralContext _localctx = new NumberLiteralContext(_ctx, getState());
		enterRule(_localctx, 50, RULE_numberLiteral);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(318);
			_la = _input.LA(1);
			if ( !(_la==HexadecimalDigit || _la==DecimalDigit) ) {
			_errHandler.recoverInline(this);
			}
			else {
				if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
				_errHandler.reportMatch(this);
				consume();
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Vector2LiteralContext extends ParserRuleContext {
		public List<NumberLiteralContext> numberLiteral() {
			return getRuleContexts(NumberLiteralContext.class);
		}
		public NumberLiteralContext numberLiteral(int i) {
			return getRuleContext(NumberLiteralContext.class,i);
		}
		public List<TerminalNode> ID() { return getTokens(MinespeakParser.ID); }
		public TerminalNode ID(int i) {
			return getToken(MinespeakParser.ID, i);
		}
		public Vector2LiteralContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_vector2Literal; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MinespeakListener ) ((MinespeakListener)listener).enterVector2Literal(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MinespeakListener ) ((MinespeakListener)listener).exitVector2Literal(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MinespeakVisitor ) return ((MinespeakVisitor<? extends T>)visitor).visitVector2Literal(this);
			else return visitor.visitChildren(this);
		}
	}

	public final Vector2LiteralContext vector2Literal() throws RecognitionException {
		Vector2LiteralContext _localctx = new Vector2LiteralContext(_ctx, getState());
		enterRule(_localctx, 52, RULE_vector2Literal);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(320);
			match(T__32);
			setState(323);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case HexadecimalDigit:
			case DecimalDigit:
				{
				setState(321);
				numberLiteral();
				}
				break;
			case ID:
				{
				setState(322);
				match(ID);
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
			setState(325);
			match(T__9);
			setState(328);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case HexadecimalDigit:
			case DecimalDigit:
				{
				setState(326);
				numberLiteral();
				}
				break;
			case ID:
				{
				setState(327);
				match(ID);
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
			setState(330);
			match(T__33);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Vector3LiteralContext extends ParserRuleContext {
		public List<NumberLiteralContext> numberLiteral() {
			return getRuleContexts(NumberLiteralContext.class);
		}
		public NumberLiteralContext numberLiteral(int i) {
			return getRuleContext(NumberLiteralContext.class,i);
		}
		public List<TerminalNode> ID() { return getTokens(MinespeakParser.ID); }
		public TerminalNode ID(int i) {
			return getToken(MinespeakParser.ID, i);
		}
		public Vector3LiteralContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_vector3Literal; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MinespeakListener ) ((MinespeakListener)listener).enterVector3Literal(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MinespeakListener ) ((MinespeakListener)listener).exitVector3Literal(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MinespeakVisitor ) return ((MinespeakVisitor<? extends T>)visitor).visitVector3Literal(this);
			else return visitor.visitChildren(this);
		}
	}

	public final Vector3LiteralContext vector3Literal() throws RecognitionException {
		Vector3LiteralContext _localctx = new Vector3LiteralContext(_ctx, getState());
		enterRule(_localctx, 54, RULE_vector3Literal);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(332);
			match(T__32);
			setState(335);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case HexadecimalDigit:
			case DecimalDigit:
				{
				setState(333);
				numberLiteral();
				}
				break;
			case ID:
				{
				setState(334);
				match(ID);
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
			setState(337);
			match(T__9);
			setState(340);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case HexadecimalDigit:
			case DecimalDigit:
				{
				setState(338);
				numberLiteral();
				}
				break;
			case ID:
				{
				setState(339);
				match(ID);
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
			setState(342);
			match(T__9);
			setState(345);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case HexadecimalDigit:
			case DecimalDigit:
				{
				setState(343);
				numberLiteral();
				}
				break;
			case ID:
				{
				setState(344);
				match(ID);
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
			setState(347);
			match(T__33);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class NewlinesContext extends ParserRuleContext {
		public List<TerminalNode> Newline() { return getTokens(MinespeakParser.Newline); }
		public TerminalNode Newline(int i) {
			return getToken(MinespeakParser.Newline, i);
		}
		public NewlinesContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_newlines; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MinespeakListener ) ((MinespeakListener)listener).enterNewlines(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MinespeakListener ) ((MinespeakListener)listener).exitNewlines(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MinespeakVisitor ) return ((MinespeakVisitor<? extends T>)visitor).visitNewlines(this);
			else return visitor.visitChildren(this);
		}
	}

	public final NewlinesContext newlines() throws RecognitionException {
		NewlinesContext _localctx = new NewlinesContext(_ctx, getState());
		enterRule(_localctx, 56, RULE_newlines);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(350); 
			_errHandler.sync(this);
			_alt = 1;
			do {
				switch (_alt) {
				case 1:
					{
					{
					setState(349);
					match(Newline);
					}
					}
					break;
				default:
					throw new NoViableAltException(this);
				}
				setState(352); 
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,37,_ctx);
			} while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER );
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public boolean sempred(RuleContext _localctx, int ruleIndex, int predIndex) {
		switch (ruleIndex) {
		case 19:
			return expr_sempred((ExprContext)_localctx, predIndex);
		}
		return true;
	}
	private boolean expr_sempred(ExprContext _localctx, int predIndex) {
		switch (predIndex) {
		case 0:
			return precpred(_ctx, 7);
		case 1:
			return precpred(_ctx, 6);
		case 2:
			return precpred(_ctx, 5);
		case 3:
			return precpred(_ctx, 4);
		case 4:
			return precpred(_ctx, 3);
		case 5:
			return precpred(_ctx, 2);
		case 6:
			return precpred(_ctx, 1);
		}
		return true;
	}

	public static final String _serializedATN =
		"\3\u608b\ua72a\u8133\ub9ed\u417c\u3be7\u7786\u5964\38\u0165\4\2\t\2\4"+
		"\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4\13\t"+
		"\13\4\f\t\f\4\r\t\r\4\16\t\16\4\17\t\17\4\20\t\20\4\21\t\21\4\22\t\22"+
		"\4\23\t\23\4\24\t\24\4\25\t\25\4\26\t\26\4\27\t\27\4\30\t\30\4\31\t\31"+
		"\4\32\t\32\4\33\t\33\4\34\t\34\4\35\t\35\4\36\t\36\3\2\3\2\3\2\5\2@\n"+
		"\2\3\2\3\2\3\3\3\3\5\3F\n\3\6\3H\n\3\r\3\16\3I\3\4\3\4\3\4\5\4O\n\4\3"+
		"\5\3\5\3\5\3\5\3\6\3\6\3\6\3\6\3\6\3\6\3\6\5\6\\\n\6\3\6\3\6\3\6\3\6\5"+
		"\6b\n\6\3\6\3\6\3\6\3\7\3\7\3\7\7\7j\n\7\f\7\16\7m\13\7\3\7\5\7p\n\7\3"+
		"\b\3\b\3\b\3\b\3\t\5\tw\n\t\3\t\5\tz\n\t\3\n\3\n\5\n~\n\n\3\13\3\13\3"+
		"\13\6\13\u0083\n\13\r\13\16\13\u0084\3\f\3\f\3\f\3\f\3\f\3\f\3\f\5\f\u008e"+
		"\n\f\3\r\3\r\3\r\3\r\5\r\u0094\n\r\3\16\3\16\5\16\u0098\n\16\3\16\3\16"+
		"\3\16\3\16\3\17\3\17\3\17\3\17\5\17\u00a2\n\17\3\17\3\17\3\20\3\20\3\20"+
		"\3\20\3\20\3\20\3\20\5\20\u00ad\n\20\3\20\3\20\3\21\3\21\3\21\3\21\3\21"+
		"\3\21\3\21\3\21\3\21\5\21\u00ba\n\21\3\21\3\21\3\22\3\22\3\22\3\22\3\22"+
		"\5\22\u00c3\n\22\3\22\3\22\3\22\3\22\3\22\5\22\u00ca\n\22\7\22\u00cc\n"+
		"\22\f\22\16\22\u00cf\13\22\3\22\3\22\3\22\3\22\5\22\u00d5\n\22\5\22\u00d7"+
		"\n\22\3\22\3\22\3\23\3\23\3\23\3\23\3\23\3\23\3\23\3\23\7\23\u00e3\n\23"+
		"\f\23\16\23\u00e6\13\23\3\24\3\24\3\24\3\24\3\24\3\24\3\24\3\24\3\24\3"+
		"\24\3\24\3\24\3\24\7\24\u00f5\n\24\f\24\16\24\u00f8\13\24\3\25\3\25\5"+
		"\25\u00fc\n\25\3\25\3\25\3\25\3\25\3\25\3\25\3\25\3\25\3\25\3\25\3\25"+
		"\3\25\3\25\3\25\3\25\3\25\3\25\3\25\3\25\3\25\3\25\3\25\3\25\7\25\u0115"+
		"\n\25\f\25\16\25\u0118\13\25\3\26\3\26\3\26\3\26\3\26\3\26\3\26\5\26\u0121"+
		"\n\26\3\27\3\27\3\27\3\27\3\27\7\27\u0128\n\27\f\27\16\27\u012b\13\27"+
		"\3\27\3\27\3\30\3\30\3\30\3\30\3\31\3\31\3\31\3\31\5\31\u0137\n\31\3\32"+
		"\3\32\3\32\3\32\3\32\3\32\5\32\u013f\n\32\3\33\3\33\3\34\3\34\3\34\5\34"+
		"\u0146\n\34\3\34\3\34\3\34\5\34\u014b\n\34\3\34\3\34\3\35\3\35\3\35\5"+
		"\35\u0152\n\35\3\35\3\35\3\35\5\35\u0157\n\35\3\35\3\35\3\35\5\35\u015c"+
		"\n\35\3\35\3\35\3\36\6\36\u0161\n\36\r\36\16\36\u0162\3\36\2\3(\37\2\4"+
		"\6\b\n\f\16\20\22\24\26\30\32\34\36 \"$&(*,.\60\62\64\668:\2\t\3\2\34"+
		"\35\3\2\37!\4\2\35\35\"\"\3\2#&\3\2\'(\4\2\33\33..\3\2\61\62\2\u0180\2"+
		"<\3\2\2\2\4G\3\2\2\2\6N\3\2\2\2\bP\3\2\2\2\nT\3\2\2\2\fo\3\2\2\2\16q\3"+
		"\2\2\2\20v\3\2\2\2\22{\3\2\2\2\24\u0082\3\2\2\2\26\u008d\3\2\2\2\30\u0093"+
		"\3\2\2\2\32\u0095\3\2\2\2\34\u009d\3\2\2\2\36\u00a5\3\2\2\2 \u00b0\3\2"+
		"\2\2\"\u00bd\3\2\2\2$\u00da\3\2\2\2&\u00e7\3\2\2\2(\u00f9\3\2\2\2*\u0120"+
		"\3\2\2\2,\u0122\3\2\2\2.\u012e\3\2\2\2\60\u0136\3\2\2\2\62\u013e\3\2\2"+
		"\2\64\u0140\3\2\2\2\66\u0142\3\2\2\28\u014e\3\2\2\2:\u0160\3\2\2\2<=\7"+
		"\3\2\2=?\5:\36\2>@\5\4\3\2?>\3\2\2\2?@\3\2\2\2@A\3\2\2\2AB\7\4\2\2B\3"+
		"\3\2\2\2CE\5\6\4\2DF\5:\36\2ED\3\2\2\2EF\3\2\2\2FH\3\2\2\2GC\3\2\2\2H"+
		"I\3\2\2\2IG\3\2\2\2IJ\3\2\2\2J\5\3\2\2\2KO\5\24\13\2LO\5\b\5\2MO\5\n\6"+
		"\2NK\3\2\2\2NL\3\2\2\2NM\3\2\2\2O\7\3\2\2\2PQ\7\5\2\2QR\7\66\2\2RS\5\n"+
		"\6\2S\t\3\2\2\2TU\7\6\2\2UV\7\64\2\2VW\7\7\2\2WX\5\f\7\2X[\7\b\2\2YZ\7"+
		"\t\2\2Z\\\5\60\31\2[Y\3\2\2\2[\\\3\2\2\2\\]\3\2\2\2]^\7\n\2\2^_\5:\36"+
		"\2_a\5\20\t\2`b\5:\36\2a`\3\2\2\2ab\3\2\2\2bc\3\2\2\2cd\7\13\2\2de\7\66"+
		"\2\2e\13\3\2\2\2fk\5\16\b\2gh\7\f\2\2hj\5\16\b\2ig\3\2\2\2jm\3\2\2\2k"+
		"i\3\2\2\2kl\3\2\2\2lp\3\2\2\2mk\3\2\2\2np\3\2\2\2of\3\2\2\2on\3\2\2\2"+
		"p\r\3\2\2\2qr\7\64\2\2rs\7\r\2\2st\5\60\31\2t\17\3\2\2\2uw\5\24\13\2v"+
		"u\3\2\2\2vw\3\2\2\2wy\3\2\2\2xz\5\22\n\2yx\3\2\2\2yz\3\2\2\2z\21\3\2\2"+
		"\2{}\7\16\2\2|~\5(\25\2}|\3\2\2\2}~\3\2\2\2~\23\3\2\2\2\177\u0080\5\26"+
		"\f\2\u0080\u0081\5:\36\2\u0081\u0083\3\2\2\2\u0082\177\3\2\2\2\u0083\u0084"+
		"\3\2\2\2\u0084\u0082\3\2\2\2\u0084\u0085\3\2\2\2\u0085\25\3\2\2\2\u0086"+
		"\u008e\5$\23\2\u0087\u008e\5.\30\2\u0088\u008e\5&\24\2\u0089\u008e\5\""+
		"\22\2\u008a\u008e\5\30\r\2\u008b\u008e\78\2\2\u008c\u008e\5,\27\2\u008d"+
		"\u0086\3\2\2\2\u008d\u0087\3\2\2\2\u008d\u0088\3\2\2\2\u008d\u0089\3\2"+
		"\2\2\u008d\u008a\3\2\2\2\u008d\u008b\3\2\2\2\u008d\u008c\3\2\2\2\u008e"+
		"\27\3\2\2\2\u008f\u0094\5 \21\2\u0090\u0094\5\36\20\2\u0091\u0094\5\34"+
		"\17\2\u0092\u0094\5\32\16\2\u0093\u008f\3\2\2\2\u0093\u0090\3\2\2\2\u0093"+
		"\u0091\3\2\2\2\u0093\u0092\3\2\2\2\u0094\31\3\2\2\2\u0095\u0097\7\n\2"+
		"\2\u0096\u0098\5\24\13\2\u0097\u0096\3\2\2\2\u0097\u0098\3\2\2\2\u0098"+
		"\u0099\3\2\2\2\u0099\u009a\7\17\2\2\u009a\u009b\5(\25\2\u009b\u009c\7"+
		"\20\2\2\u009c\33\3\2\2\2\u009d\u009e\7\17\2\2\u009e\u009f\5(\25\2\u009f"+
		"\u00a1\7\n\2\2\u00a0\u00a2\5\24\13\2\u00a1\u00a0\3\2\2\2\u00a1\u00a2\3"+
		"\2\2\2\u00a2\u00a3\3\2\2\2\u00a3\u00a4\7\20\2\2\u00a4\35\3\2\2\2\u00a5"+
		"\u00a6\7\21\2\2\u00a6\u00a7\5\60\31\2\u00a7\u00a8\7\64\2\2\u00a8\u00a9"+
		"\7\22\2\2\u00a9\u00aa\5(\25\2\u00aa\u00ac\7\n\2\2\u00ab\u00ad\5\24\13"+
		"\2\u00ac\u00ab\3\2\2\2\u00ac\u00ad\3\2\2\2\u00ad\u00ae\3\2\2\2\u00ae\u00af"+
		"\7\23\2\2\u00af\37\3\2\2\2\u00b0\u00b1\7\24\2\2\u00b1\u00b2\5.\30\2\u00b2"+
		"\u00b3\7\25\2\2\u00b3\u00b4\5(\25\2\u00b4\u00b5\7\26\2\2\u00b5\u00b6\5"+
		".\30\2\u00b6\u00b7\7\n\2\2\u00b7\u00b9\5:\36\2\u00b8\u00ba\5\24\13\2\u00b9"+
		"\u00b8\3\2\2\2\u00b9\u00ba\3\2\2\2\u00ba\u00bb\3\2\2\2\u00bb\u00bc\7\23"+
		"\2\2\u00bc!\3\2\2\2\u00bd\u00be\7\27\2\2\u00be\u00bf\5(\25\2\u00bf\u00c0"+
		"\7\n\2\2\u00c0\u00c2\5:\36\2\u00c1\u00c3\5\24\13\2\u00c2\u00c1\3\2\2\2"+
		"\u00c2\u00c3\3\2\2\2\u00c3\u00cd\3\2\2\2\u00c4\u00c5\7\30\2\2\u00c5\u00c6"+
		"\5(\25\2\u00c6\u00c7\7\n\2\2\u00c7\u00c9\5:\36\2\u00c8\u00ca\5\24\13\2"+
		"\u00c9\u00c8\3\2\2\2\u00c9\u00ca\3\2\2\2\u00ca\u00cc\3\2\2\2\u00cb\u00c4"+
		"\3\2\2\2\u00cc\u00cf\3\2\2\2\u00cd\u00cb\3\2\2\2\u00cd\u00ce\3\2\2\2\u00ce"+
		"\u00d6\3\2\2\2\u00cf\u00cd\3\2\2\2\u00d0\u00d1\7\31\2\2\u00d1\u00d2\7"+
		"\n\2\2\u00d2\u00d4\5:\36\2\u00d3\u00d5\5\24\13\2\u00d4\u00d3\3\2\2\2\u00d4"+
		"\u00d5\3\2\2\2\u00d5\u00d7\3\2\2\2\u00d6\u00d0\3\2\2\2\u00d6\u00d7\3\2"+
		"\2\2\u00d7\u00d8\3\2\2\2\u00d8\u00d9\7\32\2\2\u00d9#\3\2\2\2\u00da\u00db"+
		"\7-\2\2\u00db\u00dc\7\64\2\2\u00dc\u00dd\7\r\2\2\u00dd\u00e4\5\60\31\2"+
		"\u00de\u00df\7\f\2\2\u00df\u00e0\7\64\2\2\u00e0\u00e1\7\r\2\2\u00e1\u00e3"+
		"\5\60\31\2\u00e2\u00de\3\2\2\2\u00e3\u00e6\3\2\2\2\u00e4\u00e2\3\2\2\2"+
		"\u00e4\u00e5\3\2\2\2\u00e5%\3\2\2\2\u00e6\u00e4\3\2\2\2\u00e7\u00e8\7"+
		"-\2\2\u00e8\u00e9\7\64\2\2\u00e9\u00ea\7\r\2\2\u00ea\u00eb\5\60\31\2\u00eb"+
		"\u00ec\7\33\2\2\u00ec\u00f6\5(\25\2\u00ed\u00ee\7\f\2\2\u00ee\u00ef\7"+
		"\64\2\2\u00ef\u00f0\7\r\2\2\u00f0\u00f1\5\60\31\2\u00f1\u00f2\7\33\2\2"+
		"\u00f2\u00f3\5(\25\2\u00f3\u00f5\3\2\2\2\u00f4\u00ed\3\2\2\2\u00f5\u00f8"+
		"\3\2\2\2\u00f6\u00f4\3\2\2\2\u00f6\u00f7\3\2\2\2\u00f7\'\3\2\2\2\u00f8"+
		"\u00f6\3\2\2\2\u00f9\u00fb\b\25\1\2\u00fa\u00fc\t\2\2\2\u00fb\u00fa\3"+
		"\2\2\2\u00fb\u00fc\3\2\2\2\u00fc\u00fd\3\2\2\2\u00fd\u00fe\5*\26\2\u00fe"+
		"\u0116\3\2\2\2\u00ff\u0100\f\t\2\2\u0100\u0101\7\36\2\2\u0101\u0115\5"+
		"(\25\n\u0102\u0103\f\b\2\2\u0103\u0104\t\3\2\2\u0104\u0115\5(\25\t\u0105"+
		"\u0106\f\7\2\2\u0106\u0107\t\4\2\2\u0107\u0115\5(\25\b\u0108\u0109\f\6"+
		"\2\2\u0109\u010a\t\5\2\2\u010a\u0115\5(\25\7\u010b\u010c\f\5\2\2\u010c"+
		"\u010d\t\6\2\2\u010d\u0115\5(\25\6\u010e\u010f\f\4\2\2\u010f\u0110\7)"+
		"\2\2\u0110\u0115\5(\25\5\u0111\u0112\f\3\2\2\u0112\u0113\7*\2\2\u0113"+
		"\u0115\5(\25\4\u0114\u00ff\3\2\2\2\u0114\u0102\3\2\2\2\u0114\u0105\3\2"+
		"\2\2\u0114\u0108\3\2\2\2\u0114\u010b\3\2\2\2\u0114\u010e\3\2\2\2\u0114"+
		"\u0111\3\2\2\2\u0115\u0118\3\2\2\2\u0116\u0114\3\2\2\2\u0116\u0117\3\2"+
		"\2\2\u0117)\3\2\2\2\u0118\u0116\3\2\2\2\u0119\u011a\7\7\2\2\u011a\u011b"+
		"\5(\25\2\u011b\u011c\7\b\2\2\u011c\u0121\3\2\2\2\u011d\u0121\7\64\2\2"+
		"\u011e\u0121\5\62\32\2\u011f\u0121\5,\27\2\u0120\u0119\3\2\2\2\u0120\u011d"+
		"\3\2\2\2\u0120\u011e\3\2\2\2\u0120\u011f\3\2\2\2\u0121+\3\2\2\2\u0122"+
		"\u0123\7\64\2\2\u0123\u0124\7\7\2\2\u0124\u0129\5(\25\2\u0125\u0126\7"+
		"\f\2\2\u0126\u0128\5(\25\2\u0127\u0125\3\2\2\2\u0128\u012b\3\2\2\2\u0129"+
		"\u0127\3\2\2\2\u0129\u012a\3\2\2\2\u012a\u012c\3\2\2\2\u012b\u0129\3\2"+
		"\2\2\u012c\u012d\7\b\2\2\u012d-\3\2\2\2\u012e\u012f\7\64\2\2\u012f\u0130"+
		"\t\7\2\2\u0130\u0131\5(\25\2\u0131/\3\2\2\2\u0132\u0137\7/\2\2\u0133\u0134"+
		"\7/\2\2\u0134\u0135\7+\2\2\u0135\u0137\7,\2\2\u0136\u0132\3\2\2\2\u0136"+
		"\u0133\3\2\2\2\u0137\61\3\2\2\2\u0138\u013f\7\60\2\2\u0139\u013f\7\67"+
		"\2\2\u013a\u013f\5\64\33\2\u013b\u013f\7\63\2\2\u013c\u013f\5\66\34\2"+
		"\u013d\u013f\58\35\2\u013e\u0138\3\2\2\2\u013e\u0139\3\2\2\2\u013e\u013a"+
		"\3\2\2\2\u013e\u013b\3\2\2\2\u013e\u013c\3\2\2\2\u013e\u013d\3\2\2\2\u013f"+
		"\63\3\2\2\2\u0140\u0141\t\b\2\2\u0141\65\3\2\2\2\u0142\u0145\7#\2\2\u0143"+
		"\u0146\5\64\33\2\u0144\u0146\7\64\2\2\u0145\u0143\3\2\2\2\u0145\u0144"+
		"\3\2\2\2\u0146\u0147\3\2\2\2\u0147\u014a\7\f\2\2\u0148\u014b\5\64\33\2"+
		"\u0149\u014b\7\64\2\2\u014a\u0148\3\2\2\2\u014a\u0149\3\2\2\2\u014b\u014c"+
		"\3\2\2\2\u014c\u014d\7$\2\2\u014d\67\3\2\2\2\u014e\u0151\7#\2\2\u014f"+
		"\u0152\5\64\33\2\u0150\u0152\7\64\2\2\u0151\u014f\3\2\2\2\u0151\u0150"+
		"\3\2\2\2\u0152\u0153\3\2\2\2\u0153\u0156\7\f\2\2\u0154\u0157\5\64\33\2"+
		"\u0155\u0157\7\64\2\2\u0156\u0154\3\2\2\2\u0156\u0155\3\2\2\2\u0157\u0158"+
		"\3\2\2\2\u0158\u015b\7\f\2\2\u0159\u015c\5\64\33\2\u015a\u015c\7\64\2"+
		"\2\u015b\u0159\3\2\2\2\u015b\u015a\3\2\2\2\u015c\u015d\3\2\2\2\u015d\u015e"+
		"\7$\2\2\u015e9\3\2\2\2\u015f\u0161\7\66\2\2\u0160\u015f\3\2\2\2\u0161"+
		"\u0162\3\2\2\2\u0162\u0160\3\2\2\2\u0162\u0163\3\2\2\2\u0163;\3\2\2\2"+
		"(?EIN[akovy}\u0084\u008d\u0093\u0097\u00a1\u00ac\u00b9\u00c2\u00c9\u00cd"+
		"\u00d4\u00d6\u00e4\u00f6\u00fb\u0114\u0116\u0120\u0129\u0136\u013e\u0145"+
		"\u014a\u0151\u0156\u015b\u0162";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}