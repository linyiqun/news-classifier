package ICTCLAS.I3S.AC;
import java.io.*;
public class ICTCLAS50
{
	//public enum eCodeType
	//{
	//    CODE_TYPE_UNKNOWN,//type unknown 
	//    CODE_TYPE_ASCII,//ASCII
	//    CODE_TYPE_GB,//GB2312,GBK,GB10380
	//    CODE_TYPE_UTF8,//UTF-8
	//    CODE_TYPE_BIG5//BIG5
	//}
	
	public native boolean ICTCLAS_Init(byte[] sPath);
	public native boolean ICTCLAS_Exit();
	public native int ICTCLAS_ImportUserDictFile(byte[] sPath,int eCodeType);
	public native int ICTCLAS_SaveTheUsrDic();
	public native int ICTCLAS_SetPOSmap(int nPOSmap);
	public native boolean ICTCLAS_FileProcess(byte[] sSrcFilename, int eCodeType, int bPOSTagged,byte[] sDestFilename);
	public native byte[] ICTCLAS_ParagraphProcess(byte[] sSrc, int eCodeType, int bPOSTagged);
	public native byte[] nativeProcAPara(byte[] sSrc, int eCodeType, int bPOStagged);
	/* Use static intializer */
	static
	{
		System.loadLibrary("ICTCLAS50");
	}
}


