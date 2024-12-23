package br.com.avancard.managedBean;

import br.com.avancard.dao.DaoGeneric;
import br.com.avancard.model.UsuarioPessoa;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import java.util.ArrayList;
import java.util.List;

@ManagedBean(name = "usuarioPessoaManagedBean")
@ViewScoped
public class UsuarioPessoaManagedBean {

    private UsuarioPessoa usuarioPessoa = new UsuarioPessoa();
    private DaoGeneric<UsuarioPessoa> dao = new DaoGeneric<>();
    private List<UsuarioPessoa> usuarios;

   @PostConstruct
    public void init(){
        usuarios = new ArrayList<UsuarioPessoa>();
    }

    public String salvar(){
        try{
            if(usuarioPessoa.getNome() == null || usuarioPessoa.getNome().equals("")){
                FacesMessage facesMessage = new FacesMessage(FacesMessage.SEVERITY_WARN, "", "O campo NOME não pode ser vazio.");
                FacesContext.getCurrentInstance().addMessage(null, facesMessage);
            }else if (usuarioPessoa.getSobrenome() == null || usuarioPessoa.getSobrenome().equals("")) {
                FacesMessage facesMessage = new FacesMessage(FacesMessage.SEVERITY_WARN, "", "O campo SOBRENOME não pode ser vazio.");
                FacesContext.getCurrentInstance().addMessage(null, facesMessage);
                return "";

            } else if (usuarioPessoa.getEmail() == null || usuarioPessoa.getEmail().equals("")) {
                FacesMessage facesMessage = new FacesMessage(FacesMessage.SEVERITY_WARN, "", "O campo EMAIL não pode ser vazio.");
                FacesContext.getCurrentInstance().addMessage(null, facesMessage);
                return "";

            }else if (usuarioPessoa.getLogin() == null || usuarioPessoa.getLogin().equals("")) {
                FacesMessage facesMessage = new FacesMessage(FacesMessage.SEVERITY_WARN, "", "O campo LOGIN não pode ser vazio.");
                FacesContext.getCurrentInstance().addMessage(null, facesMessage);
                return "";

            }else{
                dao.salvar(usuarioPessoa);
                novo();
                FacesMessage facesMessage = new FacesMessage(FacesMessage.SEVERITY_INFO, "", "Usuario cadastrado com sucesso.");
                FacesContext.getCurrentInstance().addMessage(null, facesMessage);
                return "";
            }
        }catch (Exception e){
            FacesMessage facesMessage = new FacesMessage(FacesMessage.SEVERITY_ERROR, "", "Erro ao cadastrar usuario: "+e.getMessage());
            FacesContext.getCurrentInstance().addMessage(null, facesMessage);
            return "";
        }
        return "";
    }

    public void novo(){
        usuarioPessoa = new UsuarioPessoa();
    }

    public UsuarioPessoa getUsuarioPessoa() {
        return usuarioPessoa;
    }
    public void setUsuarioPessoa(UsuarioPessoa usuarioPessoa) {
        this.usuarioPessoa = usuarioPessoa;
    }
    public List<UsuarioPessoa> getUsuarios() {
        usuarios = dao.listarAll(UsuarioPessoa.class);
        return usuarios;
    }
    public void setUsuarios(List<UsuarioPessoa> usuarios) {
        this.usuarios = usuarios;
    }
}
