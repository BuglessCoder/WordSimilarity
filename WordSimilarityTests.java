package application;

public class WordSimilarityTests{
	
    
    public static void main(String[] args){
    	int dis = WordSimilarity.disPrimitive("是", "非");
    	double simP = WordSimilarity.simPrimitive("是", "非");
    	
        System.out.println("The distance of 是 and 非  : "+ dis);      
        System.out.println("The Similarity of 是 and 非 : "+ simP);       
    	
    	
    }
}

