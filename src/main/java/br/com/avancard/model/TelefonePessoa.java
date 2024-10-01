package br.com.avancard.model;

import javax.persistence.*;

@Entity
public class TelefonePessoa {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="cd_telefone", unique=true, nullable=false)
    private long id;
    @Column(name = "nr_telefone", nullable=false)
    private String numero;
    @Column(nullable=false)
    private String tipo;
    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    private UsuarioPessoa pessoa;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public UsuarioPessoa getPessoa() {
        return pessoa;
    }

    public void setPessoa(UsuarioPessoa pessoa) {
        this.pessoa = pessoa;
    }

    @Override
    public String toString() {
        return "TelefonePessoa{" +
                "id=" + id +
                ", numero='" + numero + '\'' +
                ", tipo='" + tipo + '\'' +
                ", pessoa=" + pessoa +
                '}';
    }
}
