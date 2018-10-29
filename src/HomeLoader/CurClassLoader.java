package HomeLoader;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Scanner;

/**
 * Load ClassLoader
 */
public class CurClassLoader extends ClassLoader {

    public String addedText = "";

    /**
     * The console method reads the code of another method line by line
     * @return Text reading from the console
     */
    public String readDoWord(){
        String curText;
        Boolean endInWord = false;
        Boolean firstIn = true;
        Scanner in = new Scanner(System.in);

        do
        {
            curText = in.nextLine();
            if (curText.equalsIgnoreCase("")){
                endInWord  = true;
            }
            else{
                if (firstIn.equals(true)){
                    this.addedText += curText;
                    firstIn = false;
                }
                else{
                    this.addedText +=  "\n" + curText;
                }
            }
        }while(!endInWord);
        return this.addedText;
    }


    /**
     * After entering a blank line terminates a read and read-line added to the body of the method
     * @param paths Paths to the file
     * @param addedText Added text in file
     * @throws IOException
     */
    public void setTextInFile(String paths, String addedText) throws IOException {
        Path path = Paths.get(paths);
        List<String> lines = Files.readAllLines(path, StandardCharsets.UTF_8);

        int position = lines.size() - 2;
        String extraLine = addedText;
        lines.add(position, extraLine);
        Files.write(path, lines, StandardCharsets.UTF_8);
    }

    /**
     * Search for a given class
     * @param name Package this project
     * @return Class found
     */
    @Override
    public Class<?> findClass(String name) {
        byte[] bt = loadClassData(name);
        return defineClass(name, bt, 0, bt.length);
    }

    /**
     * Loading class data
     * @param className Current method
     * @return Byte array
     */
    private byte[] loadClassData(String className) {
        InputStream is = getClass().getClassLoader().getResourceAsStream(className.replace(".", "/")+".class");
        ByteArrayOutputStream byteSt = new ByteArrayOutputStream();
        int len = 0;
        try {
            while((len=is.read())!=-1){
                byteSt.write(len);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return byteSt.toByteArray();
    }

}