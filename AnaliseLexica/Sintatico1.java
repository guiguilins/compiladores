package AnaliseLexica;

public class Sintatico1 {
    private Lexico lexico;
    private Token token;

    public Sintatico1(Lexico lexico) {
        this.lexico = lexico;
    }

    public void S(){
        this.token = this.lexico.nextToken();
        this.E();
        if(this.token.getTipo() == Token.TIPO_FIM_CODIGO){
            System.out.println("O código tá massa! Arretado! Tu botou pra torar!");
        }
    }

    public void E(){
        this.T();
        this.El();
    }

    public void El(){
        if(this.token.getTipo() == Token.TIPO_OPERADOR_ARITMETICO){
            this.OP();
            this.T();
            this.El();
        }else{
        }
    }
    
    public void T(){
        if(this.token.getTipo() == Token.TIPO_IDENTIFICADOR || 
           this.token.getTipo() == Token.TIPO_INTEIRO || 
           this.token.getTipo() == Token.TIPO_REAL ){
            this.token = this.lexico.nextToken();
        }else{
            throw new RuntimeException("Oxe, era pra ter um identificador  ou número pertinho de " + this.token.getLexema());
    }
  }

   public void OP(){
    if(this.token.getTipo() == Token.TIPO_OPERADOR_ARITMETICO){
        this.token = this.lexico.nextToken();
    }else{
        throw new RuntimeException("Oxe, era pra ter um operador aritmético (+,-,/,*) pertinho de " + this.token.getLexema());
    }
   }
}
