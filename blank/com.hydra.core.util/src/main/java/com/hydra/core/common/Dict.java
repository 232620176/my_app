package com.hydra.core.common;

public class Dict {
	public static final char[] HEXADECIMAL_CHAR = { '0', '1', '2', '3', '4',
		'5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};
	
	public static final char[] HEXADECIMAL_CHAR_UPPER = { '0', '1', '2', '3',
		'4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};
	
	public static final String ALL_CHAR = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
	
	public static final String _PROCESSID = "_ProcessId";// 服务交易码
	
	public static final String _RETURNCODE = "_ReturnCode";// 交易状态码
	
	public static final String _RETURNMSG = "_ReturnMsg";// 交易信息
	
	public static final String _SUBCHANNELID = "_SubChannelId";// 子渠道代码
	
	public static final String BROKEN_BAR = "\\|";
	
	public static final String MASK_FILL = "*";
	
	public static final String POINT = ".";
	
	public static final String SEMICOLON = ";";
	
	// 静态工具类，防误生成
	private Dict(){throw new UnsupportedOperationException();}
}
