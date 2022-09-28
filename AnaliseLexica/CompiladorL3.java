package AnaliseLexica;

public class CompiladorL3 {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Lexico lexico = new Lexico("src\\AnaliseLexica\\codigo.txt");
        Token t = null;
        while((t = lexico.nextToken()) != null){
            System.out.println(t.toString());
        }

    }
    
}
