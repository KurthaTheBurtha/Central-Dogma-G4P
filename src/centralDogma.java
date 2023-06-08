import java.io.*;
import java.util.ArrayList;

public class centralDogma {
    public static void main(String[] args) throws IOException {
        //reads input from file "dna.in" using a buffered reader, which reduces the amount of computing power by reading chunks of characters and storing them in an internal buffer
        FileReader f1 = new FileReader("dna.in");
        BufferedReader br = new BufferedReader(f1);
        //input is held in a string
        String input = "";
        while(br.ready()){
            input+=br.readLine();
        }
        output(formatProtein(translate(transcribe(input))));
    }
    //transcribe replaces all Thymine in the sequence with Uracil using the replaceAll method in the String library.
    public static String transcribe(String input){
        return input.replaceAll("T","U");
    }
    //translate translates the mRNA sequence into an amino acid sequence. It uses the ArrayList data structure to store and organize the amino acids. The mRNA is read in sets of three, and processed with toAmino, which turns the codon into its corresponding amino acid.
    public static ArrayList<String> translate(String input){
        ArrayList<String> aminos = new ArrayList<String>();
        String codon = "";
        for(int i = 0; i<input.length();i+=3){
            codon = input.substring(i,i+3);
            aminos.add(toAmino(codon));
        }
        return aminos;
    }
    //toAmino converts a codon to its respective amino acid using the codon table. A switch case statement, a more advanced if-else statement, is used to quickly process the amino.
    public static String toAmino(String codon){
        String amino = "";
        switch(codon){
            case "AUU", "AUA", "AUC":
                amino = "Ile";
                break;
            case "AUG":
                amino = "Met";
                break;
            case "ACU", "ACG", "ACA", "ACC":
                amino = "Thr";
                break;
            case "AAU", "AAC":
                amino = "Asn";
                break;
            case "AAA", "AAG":
                amino = "Lys";
                break;
            case "AGU", "AGC", "UCU", "UCC", "UCA", "UCG":
                amino = "Ser";
                break;
            case "AGA", "AGG", "CGU", "CGC", "CGA", "CGG":
                amino = "Arg";
                break;
            case "GUU", "GUG", "GUA", "GUC":
                amino = "Val";
                break;
            case "GCU", "GCC","GCA","GCG":
                amino = "Ala";
                break;
            case "GAU", "GAC":
                amino = "Asp";
                break;
            case "GAA", "GAG":
                amino = "Glu";
                break;
            case "GGU", "GGC","GGA","GGG":
                amino = "Gly";
                break;
            case "UUU", "UUC":
                amino = "Phe";
                break;
            case "UUA", "UUG", "CUU", "CUC", "CUA", "CUG":
                amino = "Leu";
                break;
            case "UAU", "UAC":
                amino = "Tyr";
                break;
            case "UAA", "UAG","UGA":
                amino = "{!}";
                break;
            case "UGU", "UGC":
                amino = "Cys";
                break;
            case "UGG":
                amino = "Trp";
                break;
            case "CCU", "CCC","CCA","CCG":
                amino = "Pro";
                break;
            case "CAU", "CAC":
                amino = "His";
                break;
            case "CAA", "CAG":
                amino = "Gln";
                break;
            default:
                amino="";
                break;
        }
        return amino;
    }
    //formatProtein formats the list of amino acids so that they are readable and in a format where they can be written to a file.
    public static String formatProtein(ArrayList<String> aminos){
        String out = "";
        for(String amino: aminos){
            out+= amino+"-";
        }
        return out.substring(0,out.length()-1);
    }
    //output writes the data that we've collected into a separate file called protein.out, which contains the full protein sequence of the original DNA sequence transcribed and translated.
    public static void output(String input) throws IOException {
        FileWriter fw = new FileWriter("protein.out");
        fw.write(input);
        fw.close();
    }

}
