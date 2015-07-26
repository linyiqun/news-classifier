package NewsClassify;

/**
 * 词语实体类
 * 
 * @author lyq
 * 
 */
public class Word implements Comparable<Word> {
	// 词语名称
	String name;
	// 词频
	Integer count;

	public Word(String name, Integer count) {
		this.name = name;
		this.count = count;
	}

	@Override
	public int compareTo(Word o) {
		// TODO Auto-generated method stub
		return o.count.compareTo(this.count);
	}
}
