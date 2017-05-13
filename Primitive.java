package application;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

//��ԭ
public class Primitive {
  
    public static Map<Integer, Primitive> ALLPRIMITIVES = new HashMap<Integer, Primitive>();
    public static Map<String, Primitive> m = new HashMap<String, Primitive>();
    public static Map<String, Integer> PRIMITIVESID = new HashMap<String, Integer>();
    
    //������ԭ�ļ�
    static {
        String line = null;
        try {
            BufferedReader reader = new BufferedReader(new FileReader("/Users/lidawei/Desktop/WordSimilarity/dict/WHOLE.txt"));
            line = reader.readLine();
            while (line != null) {
                line = line.trim().replaceAll("\\s+", " ");		              
                //����trim()����ȥ���ַ�����ǰ���հ׺�β���հף�Ȼ����������ʽ��\\s�ȼ���[^\f\n\r\t\v]
                
                String[] strs = line.split(" ");
                int id = Integer.parseInt(strs[0]);			//��ǰ��ԭ�ڵ��ID
                String[] words = strs[1].split("\\|");		//�ԡ�|����Ϊ�ָ�������������Ӣ�ķָ���
                String english = words[0];					//Ӣ�Ľ���
                String chinaese = words[1];					//���Ľ���
                int parentId = Integer.parseInt(strs[2]);	//��ǰ��ԭ�ڵ�ĸ�ĸ�ڵ��ID
                
                
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

    
    //�ĸ�˽������
    private String primitive;			//��ԭ
    private int id;						//��ԭ�ڵ��ID
    private int parentId;				//��ĸ�ڵ��ID
    private String parentPrimitive;		//��ĸ�ڵ�

    //�����������Ĺ��췽��
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

	
	
	//�������Ե�get����
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

	//�ж��Ƿ���Top�ڵ�ķ���
    public boolean isTop() {
        return id == parentId;
    }

    
    /**
     * ���һ����ԭ�ڵ�����и�ĸ�ڵ㣬ֱ������λ��
     * ������ҵ���ԭû�в��ҵ����򷵻�һ����list
     */
    public static List<Integer> getParents(String primitive) {
        List<Integer> list = new ArrayList<Integer>();

        //�����ԭ��ID
        Integer id = PRIMITIVESID.get(primitive);	

        if (id != null) {
            Primitive parent = ALLPRIMITIVES.get(id);	//��õ�ǰ��ԭ�ڵ�
            list.add(id);								//�ѵ�ǰ��ԭ�ڵ��ID�ӵ�List
            while (!parent.isTop()) {					//�жϵ�ǰ��ԭ�ڵ�ĸ�ĸ�ڵ��ǲ���Top�ڵ㣬��������ѭ��
                list.add(parent.getParentId());			//�ѵ�ǰ��ԭ�ڵ�ĸ�ĸ�ڵ��ID�ӵ�List��
                parent = ALLPRIMITIVES.get(parent.getParentId());	//parent�ڵ�����
            }
        }
        return list;
    }
    
    public static List<Primitive> getParentsPrimitive(String primitive) {
        List<Primitive> list = new ArrayList<Primitive>();

        //�����ԭ��ID
        Integer id = PRIMITIVESID.get(primitive);	

        if (id != null) {
            Primitive parent = ALLPRIMITIVES.get(id);	//��õ�ǰ��ԭ�ڵ�
            list.add(parent);								//�ѵ�ǰ��ԭ�ڵ��ID�ӵ�List
            while (!parent.isTop()) {					//�жϵ�ǰ��ԭ�ڵ�ĸ�ĸ�ڵ��ǲ���Top�ڵ㣬��������ѭ��
                list.add(ALLPRIMITIVES.get(parent.getParentId()));			//�ѵ�ǰ��ԭ�ڵ�ĸ�ĸ�ڵ��ID�ӵ�List��
                parent = ALLPRIMITIVES.get(parent.getParentId());	//parent�ڵ�����
            }
        }
        return list;
    }
    
    /*
    public static List<String> getParentsPrimitive(String primitive) {
        List<String> list = new ArrayList<String>();

        //�����ԭ��ID
        Integer id = PRIMITIVESID.get(primitive);	
        if (id != null) {
            Primitive parent = ALLPRIMITIVES.get(id);			//��õ�ǰ��ԭ�ڵ�
            list.add(primitive);								//�ѵ�ǰ��ԭ�ӵ�List
            while (!parent.isTop()) {							//�жϵ�ǰ��ԭ�ڵ�ĸ�ĸ�ڵ��ǲ���Top�ڵ㣬��������ѭ��
                list.add(parent.getParentPrimitive());			//�ѵ�ǰ��ԭ�ڵ�ĸ�ĸ�ڵ��ID�ӵ�List��
                parent = m.get(parent.getParentPrimitive());	//parent�ڵ�����
            }
        }
        return list;
    }
    */
    
    
    
    
    public static boolean isPrimitive(String primitive){
        return PRIMITIVESID.containsKey(primitive);
    }
}

