package NewsClassify;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

import ICTCLAS.I3S.AC.ICTCLAS50;

/**
 * 分类算法模型
 * 
 * @author lyq
 * 
 */
public class NewsClassifyTool {
	// 余弦向量空间维数
	private int vectorNum;
	// 余弦相似度最小满足阈值
	private double minSupportValue;
	// 当前训练数据的新闻类别
	private String newsType;
	// 训练新闻数据文件地址
	private ArrayList<String> trainDataPaths;

	public NewsClassifyTool(ArrayList<String> trainDataPaths, String newsType,
			int vectorNum, double minSupportValue) {
		this.trainDataPaths = trainDataPaths;
		this.newsType = newsType;
		this.vectorNum = vectorNum;
		this.minSupportValue = minSupportValue;
	}

	/**
	 * 从文件中读取数据
	 */
	private String readDataFile(String filePath) {
		File file = new File(filePath);
		StringBuilder strBuilder = null;

		try {
			BufferedReader in = new BufferedReader(new FileReader(file));
			String str;
			strBuilder = new StringBuilder();
			while ((str = in.readLine()) != null) {
				strBuilder.append(str);
			}
			in.close();
		} catch (IOException e) {
			e.getStackTrace();
		}

		return strBuilder.toString();
	}

	/**
	 * 计算测试数据的特征向量
	 */
	private double[] calCharacterVectors(String filePath) {
		int index;
		double[] vectorDimensions;
		double[] temp;
		News news;
		News testNews;
		String newsCotent;
		String testContent;
		String parseContent;
		// 高频词汇
		ArrayList<Word> frequentWords;
		ArrayList<Word> wordList;

		testContent = readDataFile(filePath);
		testNews = new News(testContent);
		parseNewsContent(filePath);

		index = filePath.indexOf('.');
		parseContent = readDataFile(filePath.substring(0, index) + "-split.txt");
		testNews.statWords(parseContent);

		vectorDimensions = new double[vectorNum];
		// 计算训练数据集的类别的特征向量
		for (String path : this.trainDataPaths) {
			newsCotent = readDataFile(path);
			news = new News(newsCotent);

			// 进行分词操作
			index = path.indexOf('.');
			parseNewsContent(path);
			parseContent = readDataFile(path.substring(0, index) + "-split.txt");
			news.statWords(parseContent);

			wordList = news.wordDatas;
			// 将词频统计结果降序排列
			Collections.sort(wordList);

			frequentWords = new ArrayList<Word>();
			// 截取出前vectorDimens的词语
			for (int i = 0; i < vectorNum; i++) {
				frequentWords.add(wordList.get(i));
			}

			temp = testNews.calVectorDimension(frequentWords);
			// 将特征向量值进行累加
			for (int i = 0; i < vectorDimensions.length; i++) {
				vectorDimensions[i] += temp[i];
			}
		}

		// 最后取平均向量值作为最终的特征向量值
		for (int i = 0; i < vectorDimensions.length; i++) {
			vectorDimensions[i] /= trainDataPaths.size();
		}

		return vectorDimensions;
	}

	/**
	 * 根据求得的向量空间计算余弦相似度值
	 * 
	 * @param vectorDimension
	 *            已求得的测试数据的特征向量值
	 * @return
	 */
	private double calCosValue(double[] vectorDimension) {
		double result;
		double num1;
		double num2;
		double temp1;
		double temp2;
		// 标准的特征向量，每个维度上都为1
		double[] standardVector;

		standardVector = new double[vectorNum];
		for (int i = 0; i < vectorNum; i++) {
			standardVector[i] = 1;
		}

		temp1 = 0;
		temp2 = 0;
		num1 = 0;

		for (int i = 0; i < vectorNum; i++) {
			// 累加分子的值
			num1 += vectorDimension[i] * standardVector[i];

			// 累加分母的值
			temp1 += vectorDimension[i] * vectorDimension[i];
			temp2 += standardVector[i] * standardVector[i];
		}

		num2 = Math.sqrt(temp1) * Math.sqrt(temp2);
		// 套用余弦定理公式进行计算
		result = num1 / num2;

		return result;
	}

	/**
	 * 进行新闻分类
	 * 
	 * @param filePath
	 *            测试新闻数据文件地址
	 */
	public void newsClassify(String filePath) {
		double result;
		double[] vectorDimension;

		vectorDimension = calCharacterVectors(filePath);
		result = calCosValue(vectorDimension);

		// 如果余弦相似度值满足最小阈值要求，则属于目标分类
		if (result >= minSupportValue) {
			System.out.println(String.format("最终相似度结果为%s,大于阈值%s,所以此新闻属于%s类新闻",
					result, minSupportValue, newsType));
		} else {
			System.out.println(String.format("最终相似度结果为%s,小于阈值%s,所以此新闻不属于%s类新闻",
					result, minSupportValue, newsType));
		}
	}

	/**
	 * 利用分词系统进行新闻内容的分词
	 * 
	 * @param srcPath
	 *            新闻文件路径
	 */
	private void parseNewsContent(String srcPath) {
		// TODO Auto-generated method stub
		int index;
		String dirApi;
		String desPath;

		dirApi = System.getProperty("user.dir") + "\\lib";
		// 组装输出路径值
		index = srcPath.indexOf('.');
		desPath = srcPath.substring(0, index) + "-split.txt";

		try {
			ICTCLAS50 testICTCLAS50 = new ICTCLAS50();
			// 分词所需库的路径、初始化
			if (testICTCLAS50.ICTCLAS_Init(dirApi.getBytes("GB2312")) == false) {
				System.out.println("Init Fail!");
				return;
			}
			// 将文件名string类型转为byte类型
			byte[] Inputfilenameb = srcPath.getBytes();

			// 分词处理后输出文件名、将文件名string类型转为byte类型
			byte[] Outputfilenameb = desPath.getBytes();

			// 文件分词(第一个参数为输入文件的名,第二个参数为文件编码类型,第三个参数为是否标记词性集1 yes,0
			// no,第四个参数为输出文件名)
			testICTCLAS50.ICTCLAS_FileProcess(Inputfilenameb, 0, 1,
					Outputfilenameb);
			// 退出分词器
			testICTCLAS50.ICTCLAS_Exit();
		} catch (Exception ex) {
			ex.printStackTrace();
		}

	}
}
