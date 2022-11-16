package AnaliseLexica;

public class Sintatico2 {
    private Lexico lexico;
    private Token token;

    public Sintatico2(Lexico lexico) {
        this.lexico = lexico;
    }

    public void Programa(){
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
        if(this.token.getTipo() == Token.TIPO_FIM_CODIGO){
            System.out.println("O código tá massa! Arretado! Tu botou pra torar!");
        }else{
            throw new RuntimeException("Oxe, deu bronca perto do fim do programa");
        } 
    }

    private void bloco(){
        if(!this.token.getLexema().equals("{")){
            throw new RuntimeException("Oxe, tava esperando um \"{\" perto de "+ this.token.getLexema());
        }
        this.token = this.lexico.nextToken();
        
        while(this.token.getTipo() == Token.TIPO_PALAVRA_RESERVADA){
            this.declaracao();
        }
        
        while(this.token.getTipo() == Token.TIPO_IDENTIFICADOR){
            this.comando();
        }
        
        if(!this.token.getLexema().equals("}")){
            throw new RuntimeException("Oxe, tava esperando um \"}\" perto de "+ this.token.getLexema());
        }
        this.token = this.lexico.nextToken();
}

    private void comando(){
        if((this.token.getTipo() == Token.TIPO_IDENTIFICADOR) ||
          (this.token.getLexema().equals("int ") ||
           this.token.getLexema().equals("float ") ||
           this.token.getLexema().equals("char "))){
             this.comandoBasico();
        }
        if (this.token.getLexema().equals("while ")) {
            this.iteracao();
        }
        if(this.token.getLexema().equals("if ")){
            this.token = this.lexico.nextToken();
            if(this.token.getLexema().equals("(")){
                this.relacional();
            }else{
                throw new RuntimeException("Abre o parêntese do if ");
            }
            
            if(this.token.getLexema().equals(")")){
                this.comando();
            }else{
                throw new RuntimeException("Fecha o parêntese do if ");
            }
            if(this.token.getLexema().equals("else ")){
                this.comando();
            }else{
             throw new RuntimeException("Tá faltando um else perto de "
             + this.token.getLexema());
            }
            this.token = this.lexico.nextToken();
        }
        else{
            throw new RuntimeException("Oxe, tava esperando tu declarar um comando perto de "
             + this.token.getLexema());

        }
        
    }

    private void iteracao(){
        if(!(this.token.getLexema().equals("("))){
            throw new RuntimeException("Abre o parentese do while");
        }
        this.token = this.lexico.nextToken();
        this.relacional();
        if(!(this.token.getLexema().equals(")"))){
            throw new RuntimeException("Fecha o parentese do while");
        }
        this.token = this.lexico.nextToken();
        this.comando();
    }

    private void relacional() {
    }

    private void comandoBasico(){
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
