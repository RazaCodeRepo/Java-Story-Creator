import edu.duke.*;
import java.util.*;

public class GladLibMap {
    
    private HashMap<String,ArrayList<String>> myMap;
    
    private ArrayList<String> usedWords;
    
    private ArrayList<String> track;
    
    private Random myRandom;
    
    private static String dataSourceURL = "http://dukelearntoprogram.com/course3/data";
    private static String dataSourceDirectory = "data";
    
    public GladLibMap(){
        myMap = new HashMap<String,ArrayList<String>>();
        initializeFromSource(dataSourceDirectory);
        myRandom = new Random();
        usedWords = new ArrayList<String>();
        track = new ArrayList<String>();
    }
    
    public GladLibMap(String source){
        myMap = new HashMap<String,ArrayList<String>>();
        initializeFromSource(source);
        myRandom = new Random();
        usedWords = new ArrayList<String>();
        track = new ArrayList<String>();
    }
    
    private void initializeFromSource(String source) {
        String[] labels = {"country","noun","animal","adjective","name","color","timeframe","verb","fruit"};
        
        for(String s : labels){
            ArrayList<String> list = readIt(source+"/"+s+".txt");
           
            myMap.put(s,list);
        }
    }
    
    private String randomFrom(ArrayList<String> source){
        int index = myRandom.nextInt(source.size());
        return source.get(index);
    }
    
    private String getSubstitute(String label) {
        track.add(label);
        
        if (label.equals("number")){
            return ""+myRandom.nextInt(50)+5;
        }
        
        return randomFrom(myMap.get(label));
    }
    
    private String processWord(String w){
        int first = w.indexOf("<");
        int last = w.indexOf(">",first);
        if (first == -1 || last == -1){
            return w;
        }
        String prefix = w.substring(0,first);
        String suffix = w.substring(last+1);
        String sub = getSubstitute(w.substring(first+1,last));
        if(usedWords.contains(sub) ==true){
            sub = getSubstitute(w.substring(first+1,last));
            usedWords.add(sub);
            return prefix+sub+suffix;
        }
        else{
            usedWords.add(sub);
            return prefix+sub+suffix;
        }
        
    }
    
    private void printOut(String s, int lineWidth){
        int charsWritten = 0;
        for(String w : s.split("\\s+")){
            if (charsWritten + w.length() > lineWidth){
                System.out.println();
                charsWritten = 0;
            }
            System.out.print(w+" ");
            charsWritten += w.length() + 1;
        }
    }
    
    private String fromTemplate(String source){
        String story = "";
        if (source.startsWith("http")) {
            URLResource resource = new URLResource(source);
            for(String word : resource.words()){
                story = story + processWord(word) + " ";
            }
        }
        else {
            FileResource resource = new FileResource(source);
            for(String word : resource.words()){
                story = story + processWord(word) + " ";
            }
        }
        return story;
    }
    
    private ArrayList<String> readIt(String source){
        ArrayList<String> list = new ArrayList<String>();
        if (source.startsWith("http")) {
            URLResource resource = new URLResource(source);
            for(String line : resource.lines()){
                list.add(line);
            }
        }
        else {
            FileResource resource = new FileResource(source);
            for(String line : resource.lines()){
                list.add(line);
            }
        }
        return list;
    }
    
    public int totalWordsInMap(){
        int total = 0;
        //ArrayList<String> temp = new ArrayList<Sting>();
        for(ArrayList<String> temp : myMap.values()){
            total = temp.size()+total;
        }
        return total;
    }
    
    public int totalWordsConsidered(){
        int total = 0;
        ArrayList<String> temp = new ArrayList<String>();
        for(int i=0;i<track.size();i++){
            temp = myMap.get(track.get(i));
            total = temp.size() + total;
        }
        return total;
    }
    
    public void makeStory(){
       // myMap.clear();
        usedWords.clear();
        System.out.println("\n");
        String story = fromTemplate("data/madtemplate2.txt");
        printOut(story, 60);
        System.out.println("\nTotal Number of Words That were replaced: " + usedWords.size());
        System.out.println(usedWords);
    }
    

}
