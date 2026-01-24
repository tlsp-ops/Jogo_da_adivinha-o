package Jogodaadivinhacao;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.io.*;
import java.net.*;
import java.io.*;
import java.net.*;
import java.util.Random;

public class TCPServer {
	// Porta onde o servidor ficará escutando
	static final int PORTA = 6789;
	// Quantidade total de jogadores
	static final int totaljogadores = 3;

	public static void main(String[] args) throws Exception {

		// Cria o socket servidor TCP na porta especificada
		ServerSocket serverSocket = new ServerSocket(PORTA);
		System.out.println("Servidor iniciado...");
		System.out.println("======================================");
		System.out.println("        JOGO DA ADIVINHAÇÃO");
		System.out.println("======================================");
		// Exibe as regras do jogo no servidor
		System.out.println("Regras do jogo:");
		System.out.println("1. O servidor escolhe um número secreto entre 1 e 100.");
		System.out.println("2. O jogo suporta exatamente 3 jogadores.");
		System.out.println("3. A partida só começa quando TODOS os jogadores confirmarem participação.");
		System.out.println("4. Os jogadores jogam em turnos, um por vez.");
		System.out.println("5. Em cada rodada, o jogador envia um palpite.");
		System.out.println("6. O servidor responde se o número é 'maior', 'menor' ou se 'acertou'.");
		System.out.println("7. Quem acertar o número secreto primeiro vence.");
		// Vetores para armazenar sockets, entradas e saídas dos jogadores
		Socket[] jogadores = new Socket[totaljogadores];
		BufferedReader[] entradas = new BufferedReader[totaljogadores];
		PrintWriter[] saidas = new PrintWriter[totaljogadores];
		// Indica se o jogador está ativo no jogo
		boolean[] ativos = new boolean[totaljogadores];

		// Aguarda a conexão dos jogadores
		for (int i = 0; i < totaljogadores; i++) {
			// accept() fica bloqueado até um jogador se conectar
			jogadores[i] = serverSocket.accept();
			// Fluxo de entrada (receber dados do cliente)
			entradas[i] = new BufferedReader(
					new InputStreamReader(jogadores[i].getInputStream()));
			// Fluxo de saída (enviar dados ao cliente)
			saidas[i] = new PrintWriter(jogadores[i].getOutputStream(), true);
			// Marca o jogador como ativo
			ativos[i] = true;
			//  Informa ao cliente qual jogador ele é
			saidas[i].println("Você é o jogador " + (i + 1));
		}

		// Confirmação de início do jogo
		for (int i = 0; i < totaljogadores; i++) {

			ativos[i] = false;
			// Enquanto o jogador não confirmar, o servidor espera
			while(ativos[i] == false) {
				saidas[i].println("Digite 'sim' para iniciar:");
				String verificar = entradas[i].readLine();

				if(verificar.equals("sim")) {
					ativos[i] = true;
				}
				else {
					// Informa aos outros jogadores que alguém não confirmou
					for (PrintWriter s : saidas) {
						s.println("O jogador: " + (i+1)+ " disse não, esperem ele dizer sim");
					}

				}
			}

		}
		// Controle geral do servidor
		boolean servidorAtivo = true;
		// Loop principal do servidor
		while (servidorAtivo) {
			// Gera um número secreto aleatório entre 1 e 100
			int numeroSecreto = new Random().nextInt(100) + 1;
			boolean fimDeJogo = false;
			int jogadorAtual = 0;
			System.out.println("Esse é o número secreto da partida:" + numeroSecreto);
			// Informa aos jogadores que o jogo começou
			for (PrintWriter s : saidas) {
				s.println("Novo jogo iniciado!");
			}

			// Loop de turnos dos jogadores
			while (!fimDeJogo) {
				// Pula jogadores inativos
				if (!ativos[jogadorAtual]) {
					jogadorAtual = (jogadorAtual + 1) % totaljogadores;
					continue;
				}

				PrintWriter out = saidas[jogadorAtual];
				BufferedReader in = entradas[jogadorAtual];
				// Solicita o palpite do jogador
				out.println("Sua vez! Digite um número (1 a 100):");
				int palpite = Integer.parseInt(in.readLine());

				String resposta;
				// Verifica o palpite
				if (palpite < numeroSecreto) {
					resposta = "maior";
				} else if (palpite > numeroSecreto) {
					resposta = "menor";
				} else {
					resposta = "acertou";
				}
				// Envia o resultado para todos os jogadores
				for (PrintWriter s : saidas) {
					s.println("Jogador " + (jogadorAtual + 1)
							+ " chutou " + palpite + ": " + resposta);
				}
				// Caso alguém acerte, encerra o jogo
				if (resposta.equals("acertou")) {
					fimDeJogo = true;
					for (PrintWriter s : saidas) {
						s.println("Fim do jogo!");
					}
				}
				// Passa a vez para o próximo jogador
				jogadorAtual = (jogadorAtual + 1) % totaljogadores;
			}

			// Pergunta se os jogadores querem jogar novamente
			servidorAtivo = false;
			for (int i = 0; i < totaljogadores; i++) {
				if (ativos[i]) {
					saidas[i].println("Deseja jogar novamente? (sim/nao)");
					String r = entradas[i].readLine();
					if (r.equalsIgnoreCase("nao")) {
						ativos[i] = false;
						break;
					} else {
						servidorAtivo = true;
					}
				}
			}
		}
		// Fecha as conexões dos jogadores
		for (int i=0; i < jogadores.length; i++) {
			jogadores[i].close();
		}
		// Fecha o socket do servidor
		serverSocket.close();
		System.out.println("Servidor encerrado.");
	}
}
