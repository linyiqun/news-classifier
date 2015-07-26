package NewsClassify;

import java.util.ArrayList;


/**
 * 新闻分类算法测试类
 * @author lyq
 *
 */
public class Client {
	public static void main(String[] args){
		String testFilePath1;
		String testFilePath2;
		String testFilePath3;
		String path;
		String newsType;
		int vectorNum;
		double minSupportValue;
		ArrayList<String> trainDataPaths;
		NewsClassifyTool classifyTool;
		
		//添加测试以及训练集数据文件路径
		testFilePath1 = "C:\\Users\\lyq\\Desktop\\icon\\test\\testNews1.txt";
		testFilePath2 = "C:\\Users\\lyq\\Desktop\\icon\\test\\testNews2.txt";
		testFilePath3 = "C:\\Users\\lyq\\Desktop\\icon\\test\\testNews3.txt";
		trainDataPaths = new ArrayList<String>();
		path = "C:\\Users\\lyq\\Desktop\\icon\\test\\trainNews1.txt";
		trainDataPaths.add(path);
		path = "C:\\Users\\lyq\\Desktop\\icon\\test\\trainNews2.txt";
		trainDataPaths.add(path);
		
		newsType = "金融";
		vectorNum = 10;
		minSupportValue = 0.45;
		
		classifyTool = new NewsClassifyTool(trainDataPaths, newsType, vectorNum, minSupportValue);
		classifyTool.newsClassify(testFilePath1);
		classifyTool.newsClassify(testFilePath2);
		classifyTool.newsClassify(testFilePath3);
	}

}
