package back;

public class Pick {

    private Double Stake;
    private Double Cuota;
    private String Descripcion;
    private String Tipster;
    private String Resultado;
    private String Deporte;
    private String Casa;
    private Double U;
    private int id;

    public Pick(String Descripcion, String Tipster, String Resultado, int id, String Casa, Double Cuota, String Deporte, Double Stake, Double U) {
        this.Descripcion = Descripcion;
        this.Tipster = Tipster;
        this.Resultado = Resultado;
        this.id = id;
        this.Casa = Casa;
        this.Cuota = Cuota;
        this.Deporte = Deporte;
        this.Stake = Stake;
        this.U = U;
    }

    public boolean comprobar(String t){
        if (t.length() > 140)
            return false;
        else {
            return true;
        }
    }

    public String getDescripcion() {
        return Descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.Descripcion = descripcion;
    }

    public String getTipster() {
        return Tipster;
    }

    public void setTipster(String tipster) {
        this.Tipster = tipster;
    }

    public String getResultado() {
        return Resultado;
    }

    public void setResultado(String resultado) {
        this.Resultado = resultado;
    }

    public int getId() {
        return id;
    }

    public void setId(String fecha) {
        this.id = id;
    }

    public Double getStake() { return Stake; }

    public void setStake(float stake) { this.Stake = Stake;}

    public Double getCuota() {
        return Cuota;
    }

    public void setCuota(float cuota) {
        this.Cuota = Cuota;
    }

    public Double getU() {
        return U;
    }

    public void setU(float u) {
        this.U = U;
    }

    public String getDeporte() {
        return Deporte;
    }

    public void setDeporte(String deporte) {
        Deporte = deporte;
    }

    public String getCasa() {
        return Casa;
    }

    public void setCasa(String casa) {
        Casa = casa;
    }
}

