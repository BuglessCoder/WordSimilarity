package application;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

//义原
public class Primitive {
  
    public static Map<Integer, Primitive> ALLPRIMITIVES = new HashMap<Integer, Primitive>();
    public static Map<String, Primitive> m = new HashMap<String, Primitive>();
    public static Map<String, Integer> PRIMITIVESID = new HashMap<String, Integer>();
    
    //加载义原文件
    static {
        String line = null;
        try {
            BufferedReader reader = new BufferedReader(new FileReader("/Users/lidawei/Desktop/WordSimilarity/dict/WHOLE.txt"));
            line = reader.readLine();
            while (line != null) {
                line = line.trim().replaceAll("\\s+", " ");		              
                //先用trim()方法去掉字符串的前导空白和尾部空白，然后用正则表达式，\\s等价于[^\f\n\r\t\v]
                
                String[] strs = line.split(" ");
                int id = Integer.parseInt(strs[0]);			//当前义原节点的ID
                String[] words = strs[1].split("\\|");		//以‘|’作为分隔符，将中文与英文分隔开
                String english = words[0];					//英文解释
                String chinaese = words[1];					//中文解释
                int parentId = Integer.parseInt(strs[2]);	//当前义原节点的父母节点的ID
                
                
                ALLPRIMITIVES.put(id, new Primitive(id, chinaese, parentId));
                //ALLPRIMITIVES.put(id, new Primitive(id, english, parentId));
                PRIMITIVESID.put(chinaese, id);
                PRIMITIVESID.put(english, id);
                // System.out.println("add: " + primitive + " " + id + " " + parentId);
                line = reader.readLine();
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            System.out.println(line);
            e.printStackTrace();
        }
    }

    
    //四个私有属性
    private String primitive;			//义原
    private int id;						//义原节点的ID
    private int parentId;				//父母节点的ID
    private String parentPrimitive;		//父母节点

    //含三个参数的构造方法
    public Primitive(int id, String primitive, int parentId) {
        this.id = id;
        this.parentId = parentId;
        this.primitive = primitive;
    }
    
    
    
    public Primitive(String primitive, String parentPrimitive) {
		this.primitive = primitive;
		this.parentPrimitive = parentPrimitive;
	}
    
    

	public Primitive(int id, int parentId) {
		this.id = id;
		this.parentId = parentId;
	}

	
	
	//三个属性的get方法
    public String getPrimitive() {
        return primitive;
    }

    public int getId() {
        return id;
    }

    public int getParentId() {
        return parentId;
    }   

	public String getParentPrimitive() {
		return parentPrimitive;
	}

	public void setParentPrimitive(String parentPrimitive) {
		this.parentPrimitive = parentPrimitive;
	}

	//判断是否是Top节点的方法
    public boolean isTop() {
        return id == parentId;
    }

    
    /**
     * 获得一个义原节点的所有父母节点，直到顶层位置
     * 如果查找的义原没有查找到，则返回一个空list
     */
    public static List<Integer> getParents(String primitive) {
        List<Integer> list = new ArrayList<Integer>();

        //获得义原的ID
        Integer id = PRIMITIVESID.get(primitive);	

        if (id != null) {
            Primitive parent = ALLPRIMITIVES.get(id);	//获得当前义原节点
            list.add(id);								//把当前义原节点的ID加到List
            while (!parent.isTop()) {					//判断当前义原节点的父母节点是不是Top节点，若不是则循环
                list.add(parent.getParentId());			//把当前义原节点的父母节点的ID加到List中
                parent = ALLPRIMITIVES.get(parent.getParentId());	//parent节点上移
            }
        }
        return list;
    }
    
    public static List<Primitive> getParentsPrimitive(String primitive) {
        List<Primitive> list = new ArrayList<Primitive>();

        //获得义原的ID
        Integer id = PRIMITIVESID.get(primitive);	

        if (id != null) {
            Primitive parent = ALLPRIMITIVES.get(id);	//获得当前义原节点
            list.add(parent);								//把当前义原节点的ID加到List
            while (!parent.isTop()) {					//判断当前义原节点的父母节点是不是Top节点，若不是则循环
                list.add(ALLPRIMITIVES.get(parent.getParentId()));			//把当前义原节点的父母节点的ID加到List中
                parent = ALLPRIMITIVES.get(parent.getParentId());	//parent节点上移
            }
        }
        return list;
    }
    
    /*
    public static List<String> getParentsPrimitive(String primitive) {
        List<String> list = new ArrayList<String>();

        //获得义原的ID
        Integer id = PRIMITIVESID.get(primitive);	
        if (id != null) {
            Primitive parent = ALLPRIMITIVES.get(id);			//获得当前义原节点
            list.add(primitive);								//把当前义原加到List
            while (!parent.isTop()) {							//判断当前义原节点的父母节点是不是Top节点，若不是则循环
                list.add(parent.getParentPrimitive());			//把当前义原节点的父母节点的ID加到List中
                parent = m.get(parent.getParentPrimitive());	//parent节点上移
            }
        }
        return list;
    }
    */
    
    
    
    
    public static boolean isPrimitive(String primitive){
        return PRIMITIVESID.containsKey(primitive);
    }
}

