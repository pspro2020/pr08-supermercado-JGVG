package supermercado;

public class Cliente implements Runnable {
    private final ColaCompra colaCompra;

    public Cliente(ColaCompra colaCompra) {
        this.colaCompra = colaCompra;
    }

    @Override
    public void run() {
        int tiempoCompra = (int)(Math.random()*4);
        colaCompra.comprar(tiempoCompra);
    }
}
