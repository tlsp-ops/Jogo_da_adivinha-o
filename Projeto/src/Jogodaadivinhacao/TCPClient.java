package Jogodaadivinhacao;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.Socket;
import java.io.*;

public class TCPClient {

	public static void main(String[] args) throws Exception {

		Socket socket = new Socket("localhost", 6789);
		BufferedReader entradaServidor = new BufferedReader(
				new InputStreamReader(socket.getInputStream()));
		BufferedReader teclado = new BufferedReader(
				new InputStreamReader(System.in));
		PrintWriter saidaServidor = new PrintWriter(
				socket.getOutputStream(), true);

		System.out.println("Conectado ao servidor!");

		String mensagem;
		while ((mensagem = entradaServidor.readLine()) != null) {
			System.out.println(mensagem);

			if (mensagem.toLowerCase().contains("digite")
					|| mensagem.toLowerCase().contains("jogar novamente")
					|| mensagem.toLowerCase().contains("confirmar")) {

				String resposta = teclado.readLine();
				saidaServidor.println(resposta);
			}
		}

		socket.close();
		System.out.println("Conexão encerrada.");
	}
}
