import java.io.*;
import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.Scanner;

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
        input = clean(input);
        //asks user how they want their output formatted using Scanner
        Scanner sc = new Scanner(System.in);
        ArrayList<String> allowed = new ArrayList<>();
        allowed.add("1");
        allowed.add("2");
        allowed.add("3");
        allowed.add("4");
        allowed.add("5");
        allowed.add("6");
        System.out.println("What would you like?");
        System.out.println("1:spliced protein 2:full protein 3:short spliced protein 4: short full protein 5: long separated frames 6:short separated frames");
        String inp =sc.next();
        boolean f = false;
        while(!allowed.contains(inp)){
            System.out.println("Invalid Input. Please Try Again");
            inp = sc.next();
        }
        if(inp.toLowerCase().equals("three")){
            f = true;
        } else  if(inp.toLowerCase().equals("one")){
            f = false;
        }
        //calls all of the methods required to transcribe and translate the DNA.
        ArrayList<String> temp;
        String temp1;
        input = transcribe(input);
        temp = translate(input);
        temp = snip(formatProtein(temp,false));
        for(String s: temp){
            System.out.println(s);
        }
//        output(formatProtein(temp,f));
    }
    //clean removes all non base pairs and capitalizes base pairs
    public static String clean(String input){
        input = input.toUpperCase();
        input.replaceAll("N","");
        ArrayList<Character> allowed = new ArrayList<>();
        allowed.add('A');
        allowed.add('C');
        allowed.add('T');
        allowed.add('G');
        for(int i = 0; i<input.length();i++){
            if(!allowed.contains(input.charAt(i))){
                input.replace(input.charAt(i),' ');
            }
        }
        input.replaceAll(" ","");
        return input;
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
            if(i+3<input.length()){
                codon = input.substring(i,i+3);
                aminos.add(toAmino(codon));
            }
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
    //snip removes junk DNA so that only open reading frames remain
    public static ArrayList<String> snip(String in){
        String input;
        try {
            input = in.substring(in.indexOf('M'));
        } catch(Exception e){
            System.out.println("NO START CODONS");
            return null;
        }
        String cut = "";
        ArrayList<String> sequences = new ArrayList<>();
        for(int i = 0; i<input.length();i++) {
            if (input.charAt(i) == '!') {
                cut += input.substring(0, i);
                sequences.add(input.substring(0,i));
                input = input.substring(i);
                if (input.contains("M")) {
                    input = input.substring(input.indexOf('M'));
                } else {
                    break;
                }
                i = 0;
            }
        }
        return sequences;
    }
    //formatProtein formats the list of amino acids so that they are readable and in a format where they can be written to a file. This format is either one letter or three letter format, where the three letter format is separated by dashes and the one letter format is not separated by anything.
    public static String formatProtein(ArrayList<String> aminos,boolean letters){
        String out = "";
        if(letters) {
            for (String amino : aminos) {
                out += amino + "-";
            }
            out = out.substring(0, out.length() - 1);
        } else{
            toSimple(aminos);
            for (String amino : aminos) {
                out += amino;
            }
        }
        return out;
    }
    //toSimple turns the sequence of amino acids into their corresponding one letter form.
    public static ArrayList<String> toSimple(ArrayList<String> aminos){
        for(int i = 0; i<aminos.size();i++){
            switch(aminos.get(i)){
                case "Ile":
                    aminos.set(i,"I");
                    break;
                case "Met":
                    aminos.set(i,"M");
                    break;
                case "Thr":
                    aminos.set(i,"T");
                    break;
                case "Asn":
                    aminos.set(i,"N");
                    break;
                case "Lys":
                    aminos.set(i,"K");
                    break;
                case "Ser":
                    aminos.set(i,"S");
                    break;
                case "Arg":
                    aminos.set(i,"R");
                    break;
                case "Val":
                    aminos.set(i,"V");
                    break;
                case "Ala":
                    aminos.set(i,"A");
                    break;
                case "Asp":
                    aminos.set(i,"D");
                    break;
                case "Glu":
                    aminos.set(i,"E");
                    break;
                case "Gly":
                    aminos.set(i,"G");
                    break;
                case "Phe":
                    aminos.set(i,"F");
                    break;
                case "Leu":
                    aminos.set(i,"L");
                    break;
                case "Tyr":
                    aminos.set(i,"Y");
                    break;
                case "{!}":
                    aminos.set(i,"!");
                    break;
                case "Cys":
                    aminos.set(i,"C");
                    break;
                case "Trp":
                    aminos.set(i,"W");
                    break;
                case "Pro":
                    aminos.set(i,"P");
                    break;
                case "His":
                    aminos.set(i,"H");
                    break;
                case "Gln":
                    aminos.set(i,"Q");
                    break;
                default:
                    aminos.set(i,"");
                    break;
            }
        }
        return aminos;
    }
    //toArray turns a string of single characters back to a full array
    public static ArrayList<String> toArray(String input){
        ArrayList<String> aminos = new ArrayList<String>();
        for(int i = 0; i<input.length();i++){
            switch (input.substring(i,i+1)){
                case "I":
                    aminos.add("Ile");
                    break;
                case "M":
                    aminos.add("Met");
                    break;
                case "T":
                    aminos.add("Thr");
                    break;
                case "N":
                    aminos.add("Asn");
                    break;
                case "K":
                    aminos.add("Lys");
                    break;
                case "S":
                    aminos.add("Ser");
                    break;
                case "R":
                    aminos.add("Arg");
                    break;
                case "V":
                    aminos.add("Val");
                    break;
                case "A":
                    aminos.add("Ala");
                    break;
                case "D":
                    aminos.add("Asp");
                    break;
                case "E":
                    aminos.add("Glu");
                    break;
                case "G":
                    aminos.add("Gly");
                    break;
                case "F":
                    aminos.add("Phe");
                    break;
                case "L":
                    aminos.add("Leu");
                    break;
                case "Y":
                    aminos.add("Tyr");
                    break;
                case "!":
                    aminos.add("{!}");
                    break;
                case "C":
                    aminos.add("Cys");
                    break;
                case "W":
                    aminos.add("Trp");
                    break;
                case "P":
                    aminos.add("Pro");
                    break;
                case "H":
                    aminos.add("His");
                    break;
                case "Q":
                    aminos.add("Gln");
                    break;
                default:
                    break;

            }
        }
        return aminos;
    }
    //output writes the data that we've collected into a separate file called protein.out, which contains the full protein sequence of the original DNA sequence transcribed and translated.
    public static void output(String input) throws IOException {
        FileWriter fw = new FileWriter("protein.out");
        fw.write(input);
        fw.close();
    }

}
