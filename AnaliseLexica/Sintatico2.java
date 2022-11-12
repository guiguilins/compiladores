package AnaliseLexica;

public class Sintatico2 {
    private Lexico lexico;
    private Token token;

    public Sintatico2(Lexico lexico) {
        this.lexico = lexico;
    }

    public void S(){
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

        this.B();
        if(this.token.getTipo() == Token.TIPO_FIM_CODIGO){
            System.out.println("O código tá massa! Arretado! Tu botou pra torar!");
        }else{
            throw new RuntimeException("Oxe, deu bronca perto do fim do programa");
        } 

    }

    private void B(){
        if(!this.token.getLexema().equals("{")){
            throw new RuntimeException("Oxe, tava esperando um \"{\" perto de "+ this.token.getLexema());
        }
        this.token = this.lexico.nextToken();
        this.CS();
        if(!this.token.getLexema().equals("}")){
            throw new RuntimeException("Oxe, tava esperando um \"}\" perto de "+ this.token.getLexema());
        }
        this.token = this.lexico.nextToken();
    }

    private void C(){
        if(this.token.getTipo() == Token.TIPO_IDENTIFICADOR){
            this.ATRIBUICAO();
        }else if (this.token.getLexema().equals("int ") ||
                 this.token.getLexema().equals("float ")) {
            this.DECLARACAO();
        }else{
            throw new RuntimeException("Oxe, tava esperando tu declarar um comando perto de "
             + this.token.getLexema());
        }
    }
    
    private void CS(){
        if(this.token.getTipo() == Token.TIPO_IDENTIFICADOR || 
           this.token.getLexema().equals("int ")|| 
           this.token.getLexema().equals("float ")){
           this.C();
           this.CS();
        }else{
      }
  }

    private void DECLARACAO(){
        if (!(this.token.getLexema().equals("int ") ||
            this.token.getLexema().equals("float "))){
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

   private void ATRIBUICAO(){
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
      this.E();
      if(!this.token.getLexema().equals(";")){
        throw new RuntimeException("Erro na atribuição. Perto de: " 
          + this.token.getLexema());
      }
      this.token = this.lexico.nextToken();
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
        throw new RuntimeException("Oxe, era pra ter um identificador  ou número pertinho de "
         + this.token.getLexema());
   }
}

public void OP(){
if(this.token.getTipo() == Token.TIPO_OPERADOR_ARITMETICO){
    this.token = this.lexico.nextToken();
}else{
    throw new RuntimeException("Oxe, era pra ter um operador aritmético (+,-,/,*) pertinho de " 
       + this.token.getLexema());
  }
 }
}
