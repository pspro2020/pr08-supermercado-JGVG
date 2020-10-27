package supermercado;

public class Main {

    public static void main(String[] args) {
        ColaCompra colaCompra = new ColaCompra(4);
        Thread[] clientes = new Thread[5];
        for(int i = 0; i < clientes.length; i++ ){
            clientes[i] = new Thread(new Cliente(colaCompra));
        }
        for(int i = 0; i < clientes.length; i++ ){
            clientes[i].start();
        }
    }

}
