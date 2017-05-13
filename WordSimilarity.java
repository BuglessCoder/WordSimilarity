package application;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;


public class WordSimilarity {
	
    // 词库中所有的具体词，或者义原
    private static Map<String, List<Word>> ALLWORDS = new HashMap<String, List<Word>>();
    
    //两个无关义原之间的默认距离
    private static int DEFAULT_PRIMITIVE_DIS = 20;
    
    //知网中的关系符号
    private static String RELATIONAL_SYMBOL = "#%$*+&@?!";
    
    //知网中的特殊符号，虚词，或具体词
    private static String SPECIAL_SYMBOL = "{";
    
    //默认加载文件
    static {
        loadGlossary();
    }

    
    //加载 glossay.txt 文件
    public static void loadGlossary() {
        String line = null;
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader("/Users/lidawei/Desktop/WordSimilarity/dict/glossary.txt"));
            line = reader.readLine();
            while (line != null) {
                line = line.trim().replaceAll("\\s+", " ");
                String[] strs = line.split(" ");
                String word = strs[0];
                String type = strs[1];
                
                // 因为是按空格划分，最后一部分的加回去
                String related = strs[2];
                for (int i = 3; i < strs.length; i++) {
                    related += (" " + strs[i]);
                }
                
                //创建一个新的Word
                Word w = new Word();
                w.setWord(word);
                w.setType(type);
                parseDetail(related, w);
                // save this word.
                addWord(w);
                // read the next line
                line = reader.readLine();
            }
        } catch (Exception e) {
            System.out.println("Error line: " + line);
            // TODO Auto-generated catch block
            e.printStackTrace();
        } finally {
            try {
                reader.close();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }

   
    //解析具体概念部分，将解析的结果存入Word.
    public static void parseDetail(String related, Word word) {
    	
        String[] parts = related.split(",");
        boolean isFirst = true;
        boolean isRelational = false;
        boolean isSimbol = false;
        String chinese = null;
        String relationalPrimitiveKey = null;
        String simbolKey = null;
        for (int i = 0; i < parts.length; i++) {
        	
        	// 判断：如果是具体词，则以括号开始和结尾，如: (Bahrain|巴林)
            if (parts[i].startsWith("(")) {
                parts[i] = parts[i].substring(1,parts[i].length()-1);
                // parts[i] = parts[i].replaceAll("\\s+", "");
            }
            
            // 判断：如果是关系义原，之后的都是关系义原，如format: content=fact|事情
            if (parts[i].contains("=")) {
                isRelational = true;
                String[] strs = parts[i].split("=");
                relationalPrimitiveKey = strs[0];
                String value = strs[1].split("\\|")[1];
                word.addRelationalPrimitive(relationalPrimitiveKey, value);
                continue;
            }
            
            String[] strs = parts[i].split("\\|");		// 开始的第一个字符，确定是否为义原，或是其他关系。
                       
            int type = getPrimitiveType(strs[0]);
            
            // 其中中文部分的词语,部分虚词没有中文解释
            if (strs.length > 1) {
                chinese = strs[1];
            }
            if (chinese != null
                    && (chinese.endsWith(")") || chinese.endsWith("}"))) {
                chinese = chinese.substring(0, chinese.length() - 1);
            }
            // 义原
            if (type == 0) {
                //判断：之前有一个关系义原
                if (isRelational) {
                    word.addRelationalPrimitive(relationalPrimitiveKey,chinese);
                    continue;
                }
                
                //判断：之前有一个是符号义原
                if (isSimbol) {
                    word.addRelationSimbolPrimitive(simbolKey, chinese);
                    continue;
                }

          
                if (isFirst) {
                    word.setFirstPrimitive(chinese);
                    isFirst = false;
                    continue;
                } 
                
                else {
                    word.addOtherPrimitive(chinese);
                    continue;
                }
            }
            
            // 关系符号表
            if (type == 1) {
                isSimbol = true;
                isRelational = false;
                simbolKey = Character.toString(strs[0].charAt(0));
                word.addRelationSimbolPrimitive(simbolKey, chinese);
                continue;
            }
            
            if (type == 2) {
                // 虚词
                if (strs[0].startsWith("{")) {
                    // 去掉开始第一个字符 "{"
                    String english = strs[0].substring(1);
                    // 去掉有半部分 "}"
                    if (chinese != null) {
                        word.addStructruralWord(chinese);
                        continue;
                    } else {
                        // 如果没有中文部分，则使用英文词
                        word.addStructruralWord(english);
                        continue;
                    }
                }
            }
        }
    }

      
    //从英文部分确定这个义原的类别，用一个整数来代表，其值为0，1，2
    public static int getPrimitiveType(String str) {
        String first = Character.toString(str.charAt(0));
        if (RELATIONAL_SYMBOL.contains(first)) {
            return 1;
        }
        if (SPECIAL_SYMBOL.contains(first)) {
            return 2;
        }
        return 0;
    }

 
    public static double simPrimitive(String primitive1, String primitive2) {
        int dis = disPrimitive(primitive1, primitive2);
        return 1.0/dis;
    }

    //计算两个义原之间的距离，如果两个义原层次没有共同节点，则设置他们的距离为20
    public static int disPrimitive(String primitive1, String primitive2) {
        List<Integer> list1 = Primitive.getParents(primitive1);
        List<Integer> list2 = Primitive.getParents(primitive2);
        for (int i = 0; i < list1.size(); i++) {
            int id1 = list1.get(i);
            if (list2.contains(id1)) {
                int index = list2.indexOf(id1);
                return index + i;
            }
        }
        return DEFAULT_PRIMITIVE_DIS;		//默认距离是20
    }
       
    

    //加入一个词语
    public static void addWord(Word word) {
        List<Word> list = ALLWORDS.get(word.getWord());

        if (list == null) {
            list = new ArrayList<Word>();
            list.add(word);
            ALLWORDS.put(word.getWord(), list);
        } else {
            list.add(word);
        }
    }

    public static void main(String[] args) throws Exception {
        // TODO Auto-generated method stub
        BufferedReader reader = new BufferedReader(new FileReader("/Users/lidawei/Desktop/WordSimilarity/dict/glossary.txt"));
        Set<String> set = new HashSet<String>();
        String line = reader.readLine();
        while (line != null) {        	
            // System.out.println(line);
            line = line.replaceAll("\\s+", " ");
            String[] strs = line.split(" ");
            for (int i = 0; i < strs.length; i++) {
                System.out.print(" " + strs[i]);
            }
            set.add(strs[1]);
            line = reader.readLine();
        }
        /*
        System.out.println(set.size());
        for (String name : set) {
            System.out.println(name);
        }
        */
    }
}
