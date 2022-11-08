package AnaliseLexica;

public class CompiladorL3 {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Lexico lexico = new Lexico("/workspace/compiladores/AnaliseLexica/codigo.txt");
        Token t = null;
        while((t = lexico.nextToken()) != null){
            System.out.println(t.toString());
        }
        Sintatico1 sintatico = new Sintatico1(lexico);
        sintatico.S();

    }
    
}
