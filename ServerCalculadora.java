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
            
            byte[] digito = new byte[25];
            is.read(digito);
            System.out.println("Mensaje recibido: " + new String(digito));
            
            byte[] operando = new byte[1];
            is.read(operando);
            System.out.println("Mensaje recibido: " + new String(operando));
            
            byte[] digito2 = new byte[25];
            is.read(digito2);
            System.out.println("Mensaje recibido: " + new String(digito2));
            
            String operacion = new String (operando);
            float result = 0;
            float op1 = new Float(new String(digito));
            float op2 = new Float(new String(digito2));
            
            if (operacion.equals("+")){
                result = op1 + op2;
            }else if (operacion.equals("-")){
                result = op1 - op2;
            }else if (operacion.equals("*")){
                result = op1 * op2;
            }else if (operacion.equals("/")){
                result = op1 / op2;
            }
            
            String mensaje = String.valueOf(result);
            System.out.println("Enviando resultado");
            os.write(mensaje.getBytes());
            
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
