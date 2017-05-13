package application;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

public class ManyNodeTree   
{  
    private ManyTreeNode root;  
      
    public ManyNodeTree()  
    {  
        root = new ManyTreeNode(new TreeNode("root"));  
    }         
    
    //生成一颗多叉树，根节点为root(treeNodes为生成多叉树的节点集合 )
    public ManyNodeTree createTree(List<TreeNode> treeNodes)  
    {  
        if(treeNodes == null || treeNodes.size() < 0)  
            return null;           
        ManyNodeTree manyNodeTree =  new ManyNodeTree();  //创建了一棵以root为根节点的树
          
        //将所有节点添加到多叉树中  
        for(TreeNode treeNode : treeNodes)  
        {  
            if(treeNode.getParentId().equals("root"))  
            {  
                //如果该节点的父母节点直接为根节点，则将该节点直接添加在根节点下面 
                manyNodeTree.getRoot().getChildList().add(new ManyTreeNode(treeNode));  
            }  
            else  
            {  
            	//否则调用addChild方法，向指定多叉树节点添加子节点
                addChild(manyNodeTree.getRoot(), treeNode);  
            }  
        }  
          
        return manyNodeTree;  
    }  
              
    
    //向指定多叉树节点添加子节点 
    public void addChild(ManyTreeNode manyTreeNode, TreeNode child)  
    {  
        for(ManyTreeNode item : manyTreeNode.getChildList())  
        {  
            if(item.getData().getNodeId().equals(child.getParentId()))  
            {  
                //找到对应的父亲  
                item.getChildList().add(new ManyTreeNode(child));  
                break;  
            }  
            else  
            {  
                if(item.getChildList() != null && item.getChildList().size() > 0)  
                {  
                	//递归
                    addChild(item, child);  
                }                 
            }  
        }  
    }       
    
    //遍历多叉树
    public String iteratorTree(ManyTreeNode manyTreeNode)  
    {  
        StringBuilder buffer = new StringBuilder();  
        buffer.append("\n");  
          
        if(manyTreeNode != null)   
        {     
            for (ManyTreeNode index : manyTreeNode.getChildList())   
            {  
                buffer.append(index.getData().getNodeId() + " ");  
                  
                if (index.getChildList() != null && index.getChildList().size() > 0 )   
                {     
                    buffer.append(iteratorTree(index));  
                }  
            }  
        }  
          
        buffer.append("\n");           
        return buffer.toString();  
    }  
      
    public ManyTreeNode getRoot() {  
        return root;  
    }  
  
    public void setRoot(ManyTreeNode root) {  
        this.root = root;  
    }  
      
    public static void main(String[] args)  
    {  
    	List<TreeNode> treeNodes = new ArrayList<TreeNode>();
    	String line = null;
        try {
            BufferedReader reader = new BufferedReader(new FileReader("/Users/lidawei/Desktop/WHOLE.txt"));
            line = reader.readLine();
            while (line != null) {
                line = line.trim().replaceAll("\\s+", " ");		              
                //先用trim()方法去掉字符串的前导空白和尾部空白，然后用正则表达式，\\s等价于[^\f\n\r\t\v]
                
                String[] strs = line.split(" ");                               
                treeNodes.add(new TreeNode(strs[0], strs[2], strs[1])); 
                line = reader.readLine();
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            System.out.println(line);
            e.printStackTrace();
        }       
        
        for(TreeNode treeNode : treeNodes){
        	if(treeNode.getNodeId().equals(treeNode.getParentId())){
        		treeNode.setParentId("root"); 
        	}
        }
        
        
        ManyNodeTree tree = new ManyNodeTree();
        tree = tree.createTree(treeNodes);    
        System.out.println(tree.iteratorTree(tree.createTree(treeNodes).getRoot()));  
    }  
      
} 
