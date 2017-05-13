package application;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.Scene;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeCell;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class TestPrimitiveTreeInUI extends Application{ 	 

	public static void main(String[] args) {
		// TODO 自动生成的方法存根
		launch(args);		
	}

	public void start(Stage stage) {  				
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
        
        TreeItem<String> rootNode = new TreeItem<> ("root");
        ManyNodeTree manyNodeTree =  new ManyNodeTree(); 
        for (TreeNode treeNode : treeNodes) {
        	TreeItem<String> leaf = new TreeItem<>(treeNode.getNodeId() + " " + treeNode.getText());
        	if(treeNode.getParentId().equals("root"))  
            {  
        		rootNode.getChildren().add(leaf);        		
            }  
            else  
            {  
            	addChild(rootNode.getChildren(), treeNode);        	 
            }
        	       
        }
        
        stage.setTitle("Primitive Tree");
        VBox box = new VBox();
        final Scene scene = new Scene(box, 400, 300);
        scene.setFill(Color.LIGHTGRAY);
 
        TreeView<String> treeView = new TreeView<>(rootNode);
        treeView.setEditable(true);
        treeView.setCellFactory((TreeView<String> p) -> 
            new TextFieldTreeCellImpl());
 
        box.getChildren().add(treeView);
        stage.setScene(scene);
        stage.show(); 
    }
	
	
	public void addChild(ObservableList<TreeItem<String>> observableList, TreeNode child)
    {  
		TreeItem<String> leaf = new TreeItem<>(child.getNodeId() + " " + child.getText());
        for(TreeItem<String> item : observableList)  
        {  
        	String[] str = item.getValue().split(" ");      	        	
            if(str[0].equals(child.getParentId()))  
            {  
                //找到对应的父亲  
                item.getChildren().add(leaf);               
                break;   
            }  
            else  
            {  
                if(item.getChildren()!= null && item.getChildren().size() > 0)  
                {  
                	//递归
                    addChild(item.getChildren(), child);  
                }                 
            }  
        }  
    }      
		
    private final class TextFieldTreeCellImpl extends TreeCell<String> {
    	 
        private TextField textField;
        private final ContextMenu addMenu = new ContextMenu();
 
        public TextFieldTreeCellImpl() {
            MenuItem addMenuItem = new MenuItem("Add Primitive");
            addMenu.getItems().add(addMenuItem);
            addMenuItem.setOnAction((ActionEvent t) -> {
                TreeItem newPrimitive = 
                    new TreeItem<>("New Primitive");
                getTreeItem().getChildren().add(newPrimitive);
            });
        }
 
        @Override
        public void startEdit() {
            super.startEdit();
 
            if (textField == null) {
                createTextField();
            }
            setText(null);
            setGraphic(textField);
            textField.selectAll();
        }
 
        @Override
        public void cancelEdit() {
            super.cancelEdit();
 
            setText((String) getItem());
            setGraphic(getTreeItem().getGraphic());
        }
 
        @Override
        public void updateItem(String item, boolean empty) {
            super.updateItem(item, empty);
 
            if (empty) {
                setText(null);
                setGraphic(null);
            } else {
                if (isEditing()) {
                    if (textField != null) {
                        textField.setText(getString());
                    }
                    setText(null);
                    setGraphic(textField);
                } else {
                    setText(getString());
                    setGraphic(getTreeItem().getGraphic());
                    if (
                        !getTreeItem().isLeaf()&&getTreeItem().getParent()!= null
                    ){
                        setContextMenu(addMenu);
                    }
                }
            }
        }
        
        private void createTextField() {
            textField = new TextField(getString());
            textField.setOnKeyReleased((KeyEvent t) -> {
                if (t.getCode() == KeyCode.ENTER) {
                    commitEdit(textField.getText());
                } else if (t.getCode() == KeyCode.ESCAPE) {
                    cancelEdit();
                }
            });              
        }
 
        private String getString() {
            return getItem() == null ? "" : getItem().toString();
        }
    }

}
