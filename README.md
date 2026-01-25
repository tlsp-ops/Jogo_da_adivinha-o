# JOGO DA ADIVINHAÇÃO (NÚMERO SECRETO)

## INTEGRANTES DA EQUIPE
- Emilly Manuelly de Melo Fonseca  
- Larissa Ferreira de Sena  
- Murilo Cadete Marinho  
- Tais de Lima Silva Pereira  


##  DESCRIÇÃO DO JOGO
O **JOGO DA ADIVINHAÇÃO (NÚMERO SECRETO)** é um jogo multiplayer desenvolvido em Java utilizando sockets TCP, com o objetivo de proporcionar diversão e interação entre os jogadores.

O funcionamento do jogo ocorre da seguinte forma:
- O servidor escolhe um número secreto.
- Três jogadores entram na partida.
- O jogo acontece em rodadas, onde apenas um jogador por vez envia um palpite.
- Após cada palpite, o servidor retorna uma das seguintes respostas:
  - **MAIOR**: quando o número secreto é maior que o palpite enviado;
  - **MENOR**: quando o número secreto é menor que o palpite enviado;
  - **ACERTOU**: quando o palpite é igual ao número secreto.
- O jogador que acertar o número secreto primeiro vence a partida.

Todas as regras e o controle do jogo são realizados exclusivamente pelo servidor.


## INSTRUÇÕES DE COMO EXECUTAR O PROJETO
1. Execute a classe `TCPServer`.
2. Execute a classe `TCPClient` três vezes (uma para cada jogador).
3. No terminal de cada cliente, digite um número conforme as instruções exibidas.
4. Aguarde o início da partida após todos os jogadores se conectarem.


## INSTRUÇÕES DE COMO JOGAR
1. Inicie o servidor executando a classe `TCPServer`.
2. O servidor ficará aguardando a conexão dos jogadores.  
   **OBS.:** A partida só começa quando todos os jogadores confirmarem a participação.
3. Execute a classe `TCPClient` três vezes, uma para cada jogador.
4. Após a conexão dos três jogadores, o jogo será iniciado automaticamente.
5. O servidor escolherá um número secreto.
6. Em cada rodada, apenas um jogador por vez deverá digitar um número no terminal e enviá-lo como palpite.
7. O servidor responderá com:
   - **MAIOR**: quando o número secreto for maior que o palpite enviado;
   - **MENOR**: quando o número secreto for menor que o palpite enviado;
   - **ACERTOU**: quando o palpite for igual ao número secreto.
8. O jogo continua até que um dos jogadores acerte o número secreto.
9. O jogador que acertar primeiro o número secreto é declarado vencedor e o jogo é encerrado.
10. Ao final da partida, o servidor perguntará se os jogadores desejam jogar novamente:
    - Se todos digitarem **SIM**, uma nova partida será iniciada;
    - Se algum jogador digitar **NÃO**, o jogo será encerrado.

**OBS.:** Todos os jogadores conseguem visualizar os palpites dos outros jogadores e as respostas do servidor (maior ou menor).
