package com.chinawidth.zzmandroid;

public class ZZMJni {
	static{		     
		//libDecoder-2.3.so, version:2.3
		//融合真知码和QR码，解码后字符串第一位为码类型:
		//1-条形码，2-真知码，3-QR码
        System.loadLibrary("ZCode");
	}
	
	public native String decode(byte[] data, int width, int height);
}
