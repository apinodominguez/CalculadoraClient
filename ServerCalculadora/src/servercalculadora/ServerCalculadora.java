package servercalculadora;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerCalculadora extends Thread {

    private Socket clientSocket;
    
    public ServerCalculadora (Socket socket) {
        clientSocket = socket;
    }
    
    public void run() {
        try{
            System.out.println("Arrancando hilo");
            InputStream is=clientSocket.getInputStream();
            OutputStream os=clientSocket.getOutputStream();
            System.out.println("Esperando mensaje de operacion");
            
            byte[] buffer = new byte[1];
            is.read(buffer);
            String operacion = new String(buffer);
            
            System.out.println("Operacion recibida = " + operacion);
            
            System.out.println("Esperando el primer operador");
            
            int op1 = is.read();
            
            System.out.println("Primer operando es: " + op1);
            
            System.out.println("Esperando el primer operador");
            
            int op2 = is.read();
            
            System.out.println("Primer operando es: " + op2);
            
            System.out.println("Calculando el resultado");
            
            int result = Integer.MIN_VALUE;
            
            if (operacion.equals("+")){
                result = op1 + op2;
            }else if (operacion.equals("-")){
                result = op1 - op2;
            }else if (operacion.equals("*")){
                result = op1 * op2;
            }else if (operacion.equals("/")){
                result = op1 / op2;
            }
            
            System.out.println("Enviando resultado");
            os.write(result);
            
        }catch (Exception e){
            e.printStackTrace();
        }
        System.out.println("Hilo terminado");
    }
    
    public static void main(String[] args) throws IOException{
	System.out.println("Creando socket servidor");
	
        ServerSocket serverSocket=null;
                        
        try {
            serverSocket = new ServerSocket();            
            System.out.println("Realizando el bind");
            InetSocketAddress addr=new InetSocketAddress("locahost",5555);
            serverSocket.bind(addr);
        }catch(IOException e){
            e.printStackTrace();
        }			

	System.out.println("Aceptando conexiones");

	while (serverSocket != null){
           try {
               Socket newSocket = serverSocket.accept();
               System.out.println("Conexión recibida");
               
               ServerCalculadora hilo = new ServerCalculadora (newSocket);
               hilo.start();
           }catch (Exception e){
               e.printStackTrace();
           }
        }   
                System.out.println("Hilo terminado");
        
    }

    
    
}
