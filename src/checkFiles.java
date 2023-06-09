import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class checkFiles {
    public static void main(String[] args) throws IOException {
        FileReader f1 = new FileReader("protein.out");
        BufferedReader br1 = new BufferedReader(f1);
        FileReader f2 = new FileReader("check");
        BufferedReader br2 = new BufferedReader(f2);
        String input1 = "";
        while(br1.ready()){
            input1+=br1.readLine();
        }
        String input2 = "";
        while(br2.ready()){
            input2+=br2.readLine();
        }
        input2.replaceAll(" ","");
        String wrong = "";
        int wrongcount = 0;
        for(int i = 0; i<input1.length();i++){
            if(input1.charAt(i)==input2.charAt(i)){
                wrong+=" ";
            } else {
                wrong+="!";
                wrongcount++;
            }
        }
        System.out.println("There are "+wrongcount+" base pairs that incorrectly match");
        System.out.println(input1);
        System.out.println(wrong);


    }
}
