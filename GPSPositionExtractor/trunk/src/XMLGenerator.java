import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

/**
 * Created by IntelliJ IDEA.
 * User: Markus Ullrich
 * Date: 12.04.11
 * Time: 17:19
 * To change this template use File | Settings | File Templates.
 */
public class XMLGenerator {

    private Map<String,Double> tagWeights;
    private Map<String,List<String>> subTags;

    private static String[] contentList = new String[]{"A","B","C","D","E","F","G","H","I","J","K","L","M","N","O","P","Q","R","S","T","U","V","W","X","Y","Z"};

    public XMLGenerator(){
        this.subTags = new HashMap<String,List<String>>();
        this.tagWeights = new HashMap<String,Double>();
    }

    public XMLGenerator(Map<String, Double> tagWeights, Map<String, List<String>> subTags) {
        this.tagWeights = tagWeights;
        this.subTags = subTags;
    }

    public void addTag(String tag, double weight){
        this.tagWeights.put(tag, weight);
    }

    public void addSubTag(String tag, String subTag){
        if(!this.subTags.containsKey(tag) && this.tagWeights.containsKey(tag)){
            this.subTags.put(tag, new ArrayList<String>());
        }
        this.subTags.get(tag).add(subTag);
    }

    private void checkAndRecalculateWeights(){
        double sumWeights = 0;
        for(Double weight : this.tagWeights.values()){
            sumWeights += weight;
        }
        if(sumWeights != 1.0){
            for(Map.Entry<String,Double> entry :this.tagWeights.entrySet()){
                this.tagWeights.put(entry.getKey(),entry.getValue() / sumWeights);
            }
        }
    }

    public void generateFile(String location, int tagCount){
        this.checkAndRecalculateWeights();
        StringBuilder builder = new StringBuilder();
        String nl = System.getProperty("line.separator");
        builder.append("<example>" + nl);
        for(Map.Entry<String,Double> entry : this.tagWeights.entrySet()){
            double count = entry.getValue() * tagCount;
            String tag = entry.getKey();
            for(int i = 0 ; i < count ; i++){
                builder.append("\t<" + tag + ">");
                if(this.subTags.containsKey(tag)){
                    builder.append(nl);
                    for(String subTag : this.subTags.get(tag)){
                        builder.append("\t\t<" + subTag + ">" + this.randomString(5) + "</" + subTag + ">" + nl);
                    }
                }else{
                    builder.append(this.randomString(5));
                }
                builder.append("\t</" + tag + ">");
            }

        }
        builder.append("</example>");
        try{
            File file = new File(location + System.getProperty("file.separator") + "example.xml");
            if(!file.exists()){
                if(!file.getParentFile().exists()){
                    file.getParentFile().mkdir();
                }
                file.createNewFile();
                FileWriter writer = new FileWriter(file);
                writer.write(builder.toString());
                writer.flush();
                writer.close();
            }
        }catch(IOException ex){
            System.err.println("Could not write file for all tags!");
            ex.printStackTrace();
        }
    }

    public void generateMultipleFiles(String location, int tagCount){
        this.checkAndRecalculateWeights();
        String nl = System.getProperty("line.separator");
        for(Map.Entry<String,Double> entry : this.tagWeights.entrySet()){
            StringBuilder builder = new StringBuilder();
            double count = entry.getValue() * tagCount;
            String tag = entry.getKey();
            builder.append("<" + tag + "s>" + nl);
            for(int i = 0 ; i < count ; i++){
                builder.append("\t<" + tag + ">");
                if(this.subTags.containsKey(tag)){
                    builder.append(nl);
                    for(String subTag : this.subTags.get(tag)){
                        builder.append("\t\t<" + subTag + ">" + this.randomString(5) + "</" + subTag + ">" + nl);
                    }
                }else{
                    builder.append(this.randomString(5));
                }
                builder.append("\t</" + tag + ">" + nl);
            }
            builder.append("</" + tag + "s>");
            try {
                    File file = new File(location + System.getProperty("file.separator") + tag + "s.xml");
                    if (!file.exists()) {
                        if (!file.getParentFile().exists()) {
                            file.getParentFile().mkdir();
                        }
                        file.createNewFile();
                        FileWriter writer = new FileWriter(file);
                        writer.write(builder.toString());
                        writer.flush();
                        writer.close();
                    }
                } catch (IOException ex) {
                    System.err.println("Could not write file for " + tag + "!");
                    ex.printStackTrace();
                }
        }
    }

    private String randomString(int length){
        String string = "";
        Random random = new Random();
        for (int i = 0 ; i < length ; i++){
            string += XMLGenerator.contentList[random.nextInt(contentList.length)];
        }
        return string;
    }

}
