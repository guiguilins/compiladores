package AnaliseLexica;

import java.io.FileNotFoundException;

public class Sintatico2 {
    private Lexico lexico;
    private Token token;

    public Sintatico2(Lexico lexico) {
        this.lexico = lexico;
    }

    public void Programa() throws FileNotFoundException{
        this.token = this.lexico.nextToken();
        if(!token.getLexema().equals("int ")){
            throw new RuntimeException("Oxe, cadê o int?");
        }
        this.token = this.lexico.nextToken();
        if(!token.getLexema().equals("main ")){
            throw new RuntimeException("Oxe, cadê o main?");
        }

        this.token = this.lexico.nextToken();
        if(!token.getLexema().equals("(")){
            throw new RuntimeException("Abre o parêntese do main");
        }

        this.token = this.lexico.nextToken();
        if(!token.getLexema().equals(")")){
            throw new RuntimeException("Fecha o parêntese do main");
        }
        this.token = this.lexico.nextToken();

        this.bloco();
        this.token = this.lexico.nextToken();

        if(this.token.getTipo() == Token.TIPO_FIM_CODIGO){
            System.out.println("O código tá massa! Arretado! Tu botou pra torar!");
        }else{
            throw new RuntimeException("Oxe, deu bronca perto do fim do programa");
        } 
    }

    private void bloco() throws FileNotFoundException{
        if(!this.token.getLexema().equals("{")){
            throw new RuntimeException("Oxe, tava esperando um \"{\" perto de "+ this.token.getLexema());
        }
        this.token = this.lexico.nextToken();
        
        while((this.token.getLexema().equals("int ") ||
               this.token.getLexema().equals("float ") ||
               this.token.getLexema().equals("char "))){
                 this.declaracao();
        }
        
        while(this.token.getTipo() == Token.TIPO_IDENTIFICADOR ||
              this.token.getTipo() == Token.TIPO_PALAVRA_RESERVADA){
                this.comando();
        }
        
        if(!this.token.getLexema().equals("}")){
            throw new RuntimeException("Oxe, tava esperando um \"}\" perto de "+ this.token.getLexema());
        }   

}

    private void comando() throws FileNotFoundException{
        if((this.token.getTipo() == Token.TIPO_IDENTIFICADOR) ||
           (this.token.getTipo() == Token.TIPO_INTEIRO) ||
           (this.token.getTipo() == Token.TIPO_REAL) ||
           (this.token.getTipo() == Token.TIPO_CHAR)){
             this.comandoBasico();
        }
       else if (this.token.getLexema().equals("while ")){
            this.token = this.lexico.nextToken();
            this.iteracao();
        }
       else if(this.token.getLexema().equals("if ")){
            this.token = this.lexico.nextToken();
            if(this.token.getLexema().equals("(")){
                this.token = this.lexico.nextToken();
                this.relacional();
            }else{
                throw new RuntimeException("Abre o parêntese do if ");
            }
            
            if(this.token.getLexema().equals(")")){
                this.token = this.lexico.nextToken();
                this.bloco();
            }else{
                throw new RuntimeException("Fecha o parêntese do if ");
            }
            if(this.token.getLexema().equals("else ")){
                this.token = this.lexico.nextToken();
                this.bloco();
            }
        }
        else{
            throw new RuntimeException("Oxe, tava esperando tu declarar um comando perto de "
             + this.token.getLexema());

        }
        this.token = this.lexico.nextToken();
        
    }

    private void iteracao() throws FileNotFoundException{
        if(!(this.token.getLexema().equals("("))){
            throw new RuntimeException("Abre o parentese do while");
        }
        this.token = this.lexico.nextToken();
        this.relacional();
        if(!(this.token.getLexema().equals(")"))){
            throw new RuntimeException("Fecha o parentese do while");
        }
        this.token = this.lexico.nextToken();
        this.bloco();
    }



    private void relacional() throws FileNotFoundException {
        if(!(this.token.getTipo() == Token.TIPO_IDENTIFICADOR) ||
           (this.token.getTipo() == Token.TIPO_INTEIRO) ||
           (this.token.getTipo() == Token.TIPO_REAL) ||
           (this.token.getTipo() == Token.TIPO_CHAR)){
           throw new RuntimeException("Tu vacilou na declaração relacional. Perto de: " 
                  + this.token.getLexema());
       }
       this.exprAritmetica();

    if(this.token.getTipo() != Token.TIPO_OPERADOR_RELACIONAL){
        throw new RuntimeException("Tu vacilou na declaração relacional. Perto de: " 
                  + this.token.getLexema());
    }
    this.token = this.lexico.nextToken();

    if((this.token.getTipo() == Token.TIPO_IDENTIFICADOR) ||
           (this.token.getTipo() == Token.TIPO_INTEIRO) ||
           (this.token.getTipo() == Token.TIPO_REAL) ||
           (this.token.getTipo() == Token.TIPO_CHAR)){
            this.termo();
       }
       
}
        

    private void comandoBasico() throws FileNotFoundException{
        if(this.token.getTipo() == Token.TIPO_IDENTIFICADOR){
            this.atribuiao();
        }
        else if((this.token.getLexema().equals("int ") ||
        this.token.getLexema().equals("float ") ||
        this.token.getLexema().equals("char "))){
            this.bloco();
        }

    }

    private void declaracao(){
        if (!(this.token.getLexema().equals("int ") ||
            this.token.getLexema().equals("float ") ||
            this.token.getLexema().equals("char "))){
                throw new RuntimeException("Tu vacilou na declaração da variável. Perto de: " 
                  + this.token.getLexema());
      }
      this.token = this.lexico.nextToken();
      if(this.token.getTipo() != Token.TIPO_IDENTIFICADOR){
                throw new RuntimeException("Tu vacilou na declaração da variável. Perto de: " 
                  + this.token.getLexema());
      }
      this.token = this.lexico.nextToken();
      if(!this.token.getLexema().equalsIgnoreCase(";")){
                throw new RuntimeException("Tu vacilou na declaração da variável. Perto de: " 
                  + this.token.getLexema());
      }
      this.token = this.lexico.nextToken();
   } 

   private void atribuiao(){
      if(this.token.getTipo() != Token.TIPO_IDENTIFICADOR){
        throw new RuntimeException("Erro na atribuição. Perto de: " 
          + this.token.getLexema());
   }
      this.token = this.lexico.nextToken();
      if(this.token.getTipo() != Token.TIPO_OPERADOR_ATRIBUICAO){
        throw new RuntimeException("Erro na atribuição. Perto de: " 
          + this.token.getLexema());
   }
      this.token = this.lexico.nextToken();
      this.exprAritmetica();
      if(!this.token.getLexema().equals(";")){
        throw new RuntimeException("Erro na atribuição. Perto de: " 
          + this.token.getLexema());
      }
     
   }

   private void exprAritmetica(){
    if((this.token.getTipo() == Token.TIPO_IDENTIFICADOR) ||
       (this.token.getTipo() == Token.TIPO_INTEIRO) ||
       (this.token.getTipo() == Token.TIPO_REAL) ||
       (this.token.getTipo() == Token.TIPO_CHAR)){
            this.termo();
       if((this.token.getLexema().equals("+") || 
       this.token.getLexema().equals("-"))){
        this.token = this.lexico.nextToken();
        this.exprAritmetica();
        }
    }
        else{
            throw new RuntimeException("Erro na expressão aritmética. Perto de: " 
                        + this.token.getLexema());
     }
}

   private void termo() {
    if((this.token.getTipo() == Token.TIPO_IDENTIFICADOR) ||
    (this.token.getTipo() == Token.TIPO_INTEIRO) ||
    (this.token.getTipo() == Token.TIPO_REAL) ||
    (this.token.getTipo() == Token.TIPO_CHAR)){
            this.fator();
        if((this.token.getLexema().equals("*") || 
          this.token.getLexema().equals("/"))){
          this.token = this.lexico.nextToken();
          this.exprAritmetica();
       }
    }
    else{
        throw new RuntimeException("Erro na expressão aritmética. Perto de: " 
      + this.token.getLexema());
    }
}

private void fator() {
    if(this.token.getLexema().equals("(")){
        this.exprAritmetica();
        this.token = this.lexico.nextToken();
    if(!this.token.getLexema().equals(")")){
        throw new RuntimeException("Fecha o parêntese do teu fator");

      }
    }

    else if(!(this.token.getTipo() == Token.TIPO_IDENTIFICADOR ||
             this.token.getTipo() == Token.TIPO_INTEIRO ||
             this.token.getTipo() == Token.TIPO_REAL ||
             this.token.getTipo() == Token.TIPO_CHAR)){
            throw new RuntimeException("Erro no fator. Perto de " +
              this.token.getLexema());
    }
    this.token = this.lexico.nextToken();
  }
}
