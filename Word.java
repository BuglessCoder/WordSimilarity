package application;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

//词
public class Word {
	
    private String word;
    private String type;
    
    private String firstPrimitive;			//第一义原

    private List<String> otherPrimitives = new ArrayList<String>();		//其他义原

    //如果structruralWords非空，则该词是一个虚词，此列表里存放的是该虚词的一个义原，部分虚词无中文虚词解释
    private List<String> structuralWords = new ArrayList<String>();

    //该词的关系义原。key:关系义原，value:基本义原|(具体词)的一个列表
    private Map<String, List<String>> relationalPrimitives = new HashMap<String, List<String>>();

    //该词的关系符号义原。Key:关系符号，value:属于该挂系符号的一组基本义原|(具体词)
    private Map<String, List<String>> relationSimbolPrimitives = new HashMap<String, List<String>>();


    //三个属性的get方法
    public String getWord() {
        return word;
    }
    
    public String getType() {
        return type;
    }
    
    public String getFirstPrimitive() {
        return firstPrimitive;
    }
    
    
    //三个属性的set方法
    public void setWord(String word) {
        this.word = word;
    }
    
    public void setType(String type) {
        this.type = type;
    }
    
    public void setFirstPrimitive(String firstPrimitive) {
        this.firstPrimitive = firstPrimitive;
    }
    
    
    //判断是否为虚词（通过structruralWords列表是否非空来判断）
    public boolean isStructruralWord(){
        return !structuralWords.isEmpty();
    }


    public List<String> getOtherPrimitives() {
        return otherPrimitives;
    }

    public void setOtherPrimitives(List<String> otherPrimitives) {
        this.otherPrimitives = otherPrimitives;
    }

    public List<String> getStructruralWords() {
        return structuralWords;
    }
    
    public void setStructruralWords(List<String> structruralWords) {
        this.structuralWords = structruralWords;
    }
    
    public void addOtherPrimitive(String otherPrimitive) {
        this.otherPrimitives.add(otherPrimitive);
    }
    
    public void addStructruralWord(String structruralWord) {
        this.structuralWords.add(structruralWord);
    }

    
    public void addRelationalPrimitive(String key, String value) {
        List<String> list = relationalPrimitives.get(key);
        if (list == null) {
            list = new ArrayList<String>();
            list.add(value);
            relationalPrimitives.put(key, list);
        } else {
            list.add(value);
        }
    }
    
    public void addRelationSimbolPrimitive(String key,String value){
        
    	List<String> list = relationSimbolPrimitives.get(key);
        if (list == null) {
            list = new ArrayList<String>();
            list.add(value);
            relationSimbolPrimitives.put(key, list);
        } else {
            list.add(value);
        }
    }
    
    public Map<String, List<String>> getRelationalPrimitives() {
        return relationalPrimitives;
    }
    
    public Map<String, List<String>> getRelationSimbolPrimitives() {
        return relationSimbolPrimitives;
    }
}

