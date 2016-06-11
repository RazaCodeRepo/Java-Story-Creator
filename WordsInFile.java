import java.util.*;
import edu.duke.*;
import java.io.*;

public class WordsInFile {
    
    private HashMap<String,ArrayList<String>> map;
    
    public WordsInFile(){
        map = new HashMap<String,ArrayList<String>>();
    }
    
    private void addWordsFromFile(File f){
       // ArrayList<String> list = new ArrayList<String>();
       FileResource fr = new FileResource(f);
       for(String words : fr.words()){
           //words = words.toLowerCase();
           if(map.containsKey(words) == true){
               ArrayList<String> list=map.get(words);
               String name = f.getName();
               if(!list.contains(name)){
                   list.add(f.getName());
                   map.put(words,list);
                }
           } 
           else{
               ArrayList<String> list = new ArrayList<String>();
               list.add(f.getName());
               map.put(words,list);
           }
       }
        
    }
    
    public void buildWordFileMap(){
        map.clear();
        DirectoryResource dr = new DirectoryResource();
        for(File f : dr.selectedFiles()){
            addWordsFromFile(f);
        }
    }
    
    public int maxNumber(){
        int temp = 0;
        int maxNumber = 0;
        for(ArrayList<String> list : map.values()){
            temp = list.size();
            if(maxNumber<temp){
                maxNumber = temp;
            }
        }
        return maxNumber;
    }
    
    public ArrayList<String> wordsInNumFiles(int number){
        ArrayList<String> wordList = new ArrayList<String>();
        int temp =0;
        for(String key : map.keySet()){
            ArrayList<String> tempList = map.get(key);
            
            temp = tempList.size();
            if(temp == number){
                wordList.add(key);
            }
        }
        
        System.out.println("number of words: " + wordList.size());
        return wordList;
    }
    
    public void printFilesIn(String word){
        ArrayList<String> list = map.get(word);
        System.out.println(list);
    }
    
    public void tester(){
        buildWordFileMap();
        
        int maxNumberOfFiles = maxNumber();
        System.out.println("Number of file any word appears in :" + maxNumberOfFiles);
        ArrayList<String> result = wordsInNumFiles(maxNumberOfFiles);
        System.out.println("Words in Files :" + wordsInNumFiles(maxNumberOfFiles));
        System.out.println("Word and the file they appear in : " );
        for(String s : result){
            System.out.println(s + " is in file " + map.get(s));
        }
        
        //printFilesIn("tree");
    }

}
