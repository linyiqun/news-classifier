package NewsClassify;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * 新闻实体类
 * 
 * @author lyq
 * 
 */
public class News {
	// 新闻具体内容
	String content;
	// 新闻包含的词的个数统计值
	HashMap<String, Integer> word2Count;
	// 所有的词汇信息
	ArrayList<Word> wordDatas;

	public News(String content) {
		this.content = content;
		this.word2Count = new HashMap<String, Integer>();
		this.wordDatas = new ArrayList<Word>();
	}

	/**
	 * 将分词后的字符串进行关键词词数统计
	 * 
	 * @param parseStr
	 */
	public void statWords(String parseStr) {
		int index;
		int count;
		String w;
		String[] array;
		Word word;

		array = parseStr.split(" ");
		for (String str : array) {
			index = str.indexOf('/');
			if(index == -1){
				continue;
			}
			w = str.substring(0, index);

			//只筛选专业性的名词之类的词语，和标点符号、/wn
			if(!str.contains("n") || str.contains("wn")){
				continue;
			}
			
			count = 0;
			if (this.word2Count.containsKey(w)) {
				count = this.word2Count.get(w);
			}

			// 做计数的更新
			count++;
			this.word2Count.put(w, count);
		}

		// 进行总词语的记录汇总
		for (Map.Entry<String, Integer> entry : this.word2Count.entrySet()) {
			w = entry.getKey();
			count = entry.getValue();

			word = new Word(w, count);
			this.wordDatas.add(word);
		}
	}

	/**
	 * 计算特征向量空间值
	 * 
	 * @param vectorWords
	 *            特征向量属性词
	 * @return
	 */
	public double[] calVectorDimension(ArrayList<Word> vectorWords) {
		String word;
		int count;
		int index;
		double value;
		double[] vectorDimension;

		//判断是否已经统计
		vectorDimension = new double[vectorWords.size()];

		index = 0;
		// 逐个比较特征向量词
		for (Word entry : vectorWords) {
			value = 0;
			count = 0;
			word = entry.name;

			if (this.word2Count.containsKey(word)) {
				count = this.word2Count.get(word);
			}

			// 将特征向量词的出现频率作为一个计算值
			value = 1.0 * count / entry.count;
			//超出1按照1来算
			if(value > 1){
				value = 1;
			}
			
			vectorDimension[index] = value;
			index++;
		}

		return vectorDimension;
	}
}
