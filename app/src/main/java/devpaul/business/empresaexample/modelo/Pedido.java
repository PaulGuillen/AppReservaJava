package devpaul.business.empresaexample.modelo;

public class Pedido extends Usuario{

    private String idPedido;
    private String celular;
    private String opcional = "";
    private String direccion;
    private String distrito;
    private String nomProducto;
    private String tipoProducto;
    private double price;
    private double totalOrder;
    private int quantity;
    private String time;
    private String hour;
    private String status;
    private String imagenUrl;


    public Pedido() {

    }

    public Pedido(String nombre, String apellido, String idPedido, String celular, String opcional,
                  String direccion, String distrito, String nomProducto, String tipoProducto,
                  double price, double totalOrder, int quantity, String time, String hour, String status, String imagenUrl) {
        super(nombre, apellido);
        this.idPedido = idPedido;
        this.celular = celular;
        this.opcional = opcional;
        this.direccion = direccion;
        this.distrito = distrito;
        this.nomProducto = nomProducto;
        this.tipoProducto = tipoProducto;
        this.price = price;
        this.totalOrder = totalOrder;
        this.quantity = quantity;
        this.time = time;
        this.hour = hour;
        this.status = status;
        this.imagenUrl = imagenUrl;
    }

    public String getIdPedido() {
        return idPedido;
    }

    public void setIdPedido(String idPedido) {
        this.idPedido = idPedido;
    }

    public String getCelular() {
        return celular;
    }

    public void setCelular(String celular) {
        this.celular = celular;
    }

    public String getOpcional() {
        return opcional;
    }

    public void setOpcional(String opcional) {
        this.opcional = opcional;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getDistrito() {
        return distrito;
    }

    public void setDistrito(String distrito) {
        this.distrito = distrito;
    }

    public String getNomProducto() {
        return nomProducto;
    }

    public void setNomProducto(String nomProducto) {
        this.nomProducto = nomProducto;
    }

    public String getTipoProducto() {
        return tipoProducto;
    }

    public void setTipoProducto(String tipoProducto) {
        this.tipoProducto = tipoProducto;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getHour() {
        return hour;
    }

    public void setHour(String hour) {
        this.hour = hour;
    }

    public double getTotalOrder() {
        return totalOrder;
    }

    public void setTotalOrder(double totalOrder) {
        this.totalOrder = totalOrder;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getImagenUrl() {
        return imagenUrl;
    }

    public void setImagenUrl(String imagenUrl) {
        this.imagenUrl = imagenUrl;
    }
}

