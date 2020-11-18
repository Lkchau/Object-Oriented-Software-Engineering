import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.*;

import static java.nio.file.Files.readAllLines;
// Singleton class to help read files
public class FileParser {

    private static FileParser fp;

    private FileParser(){};

    public static FileParser getFileParser(){
        if (fp == null)
            fp = new FileParser();
        return fp;
    }

    public List<String> readFile(String directory, String fileName) {
        try {
            String currPath = new File("").getAbsolutePath() + "\\";
            if (directory.equals("")) {
                return readAllLines(Paths.get(currPath + fileName + ".txt"));
            }
            return readAllLines(Paths.get(currPath + directory + "\\" + fileName + ".txt"));
        } catch (IOException err){
            System.err.println("Could not read file!");
        }
        return new ArrayList<String>();
    }

    public HashMap<Integer, String> readFileHash(String directory, String fileName) {
        HashMap<Integer, String> hashMap = new HashMap<>();
        List<String> options = readFile(directory, fileName);
        for(String prop: options){
            String[] parts = prop.split(": ");
            Integer currKey = Integer.parseInt(parts[0]);
            String currValue = parts[1];
            hashMap.put(currKey, currValue);
        }
        return hashMap;
    }

    public HashMap<String, String> readFileHashString(String directory, String fileName) {
        HashMap<String, String> hashMap = new HashMap<>();
        List<String> options = readFile(directory, fileName);
        Collections.reverse(options);
        for(String prop: options){
            String[] parts = prop.split(": ");
            String currKey = parts[0];
            String currValue = parts[1];
            hashMap.put(currKey, currValue);
        }
        return hashMap;
    }
    public List<String> readMonstersAndHeroes(String fileName) throws IOException {
        return readFile("Legends_Monsters_and_Heroes", fileName);
    }

    public HashMap<Integer, String[]> readMonstersAndHeroesHashArray(String fileName){
        HashMap<Integer, String[]> hashMap = new HashMap<>();
        try{
            List<String> values = readMonstersAndHeroes(fileName);
            String[] parts = values.get(0).split("/");
            int index = 0;
            hashMap.put(index,parts);
            for(int i = 1; i < values.size(); i++){
                values.set(i,values.get(i).trim().replaceAll(" +", " "));
                parts = values.get(i).split(" ");
                hashMap.put(i, parts);
            }


        } catch (IOException err){
            System.err.println("Could not read file " + fileName);
        }
        return hashMap;
    }

    public HashMap<String, String[]> readMonstersAndHeroesHashNameToInfo(String fileName){
        HashMap<String, String[]> charNameToInfo  = new HashMap<>();
        HashMap<Integer, String[]> characterInfo = readMonstersAndHeroesHashArray(fileName);
        for(Integer key: characterInfo.keySet()){
            String[] info = characterInfo.get(key);
            String name = info[0];
            String[] newInfo = Arrays.copyOfRange(info, 1, info.length);
            charNameToInfo.put(name,newInfo);
        }
        return charNameToInfo;
    }
    private String padRight(String s, int n) {
        return String.format("%-" + n + "s", s);
    }

    public HashMap<Integer, String> readMonstersAndHeroesHash(String fileName){
        HashMap<Integer, String[]> hashMap = readMonstersAndHeroesHashArray(fileName);
        int padding = longestLength(hashMap);
        HashMap<Integer, String> hashMapRet = new HashMap<>();
        for(Integer key: hashMap.keySet()){
            StringBuilder str = new StringBuilder();
            String[] currStrings = hashMap.get(key);
            for(String string: currStrings){
                str.append(padRight(string,padding) + "\t|\t");
            }
            hashMapRet.put(key,str.toString());
        }
        return hashMapRet;
    }

    public int longestLength(HashMap<Integer, String[]> mapped){
        int max = 0;
        for(Integer key: mapped.keySet()){
            if(mapped.get(key)[0].length() > max){
                max = mapped.get(key)[0].length();
            }
        }
        return max;
    }

}
