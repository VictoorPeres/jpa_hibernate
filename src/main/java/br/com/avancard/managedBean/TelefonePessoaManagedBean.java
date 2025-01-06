package br.com.avancard.managedBean;

import br.com.avancard.dao.DaoGeneric;
import br.com.avancard.jpa_hibernate.SessionUtil;
import br.com.avancard.model.TelefonePessoa;
import br.com.avancard.model.UsuarioPessoa;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import java.util.ArrayList;
import java.util.List;

@ManagedBean(name = "telefonePessoaManagedBean")
@ViewScoped
public class TelefonePessoaManagedBean {

    private TelefonePessoa telefonePessoa = new TelefonePessoa();
    private DaoGeneric<TelefonePessoa> dao = new DaoGeneric<>();
    private boolean modoEdicao;
    private UsuarioPessoa usuarioPessoa;

   @PostConstruct
    public void init(){
    if(SessionUtil.getParam("usuario") != null){
        usuarioPessoa = (UsuarioPessoa) SessionUtil.getParam("usuario");
        carregaTelefonePessoa();
    }
    }

    public String salvar(){
        if(modoEdicao){
            dao.atualizar(telefonePessoa);
            novo();
            carregaTelefonePessoa();
            FacesMessage facesMessage = new FacesMessage(FacesMessage.SEVERITY_INFO, "", "Telefone atualizado com sucesso.");
            FacesContext.getCurrentInstance().addMessage(null, facesMessage);
            return "";
        }else{
            telefonePessoa.setPessoa(usuarioPessoa);
            dao.salvar(telefonePessoa);
            novo();
            carregaTelefonePessoa();
            FacesMessage facesMessage = new FacesMessage(FacesMessage.SEVERITY_INFO, "", "Telefone cadastrado com sucesso.");
            FacesContext.getCurrentInstance().addMessage(null, facesMessage);
            return "";
        }
    }

   public String excluir(TelefonePessoa telefone){
        try{
            dao.excluir(telefone);
            carregaTelefonePessoa();
            FacesMessage facesMessage = new FacesMessage(FacesMessage.SEVERITY_INFO, "", "Telefone deletado com sucesso.");
            FacesContext.getCurrentInstance().addMessage(null, facesMessage);
            return "";
        }catch (Exception e){
            if(e.getCause() instanceof org.hibernate.exception.ConstraintViolationException){
                FacesMessage facesMessage = new FacesMessage(FacesMessage.SEVERITY_ERROR, "", "Erro ao deletar o registro: "+e.getMessage());
                FacesContext.getCurrentInstance().addMessage(null, facesMessage);
            }
            return "";
        }
    }

    public String edit(TelefonePessoa telefone){
        try{
            telefonePessoa = telefone;
            setModoEdicao(true);
            FacesMessage facesMessage = new FacesMessage(FacesMessage.SEVERITY_WARN, "", "Modo de edição do telefone: " + telefonePessoa.getNumero() + ". ");            FacesContext.getCurrentInstance().addMessage("msg", facesMessage);
            return "";
        } catch (Exception e) {
            FacesMessage facesMessage = new FacesMessage(FacesMessage.SEVERITY_ERROR, "", "Erro ao editar o registro: "+e.getMessage());
            FacesContext.getCurrentInstance().addMessage(null, facesMessage);
            return "";
        }
    }

    public void novo(){
        this.modoEdicao = false;
        telefonePessoa = new TelefonePessoa();
    }

    public String carregaTelefonePessoa(){
        usuarioPessoa.setTelefones(dao.listarPorId(usuarioPessoa.getId()));
        return "";
    }

    /*###########################################METODOS ESPECIAIS#############################################*/

    public TelefonePessoa getTelefonePessoa() {
        return telefonePessoa;
    }

    public void setTelefonePessoa(TelefonePessoa telefonePessoa) {
        this.telefonePessoa = telefonePessoa;
    }

    public UsuarioPessoa getUsuarioPessoa() {
        return usuarioPessoa;
    }

    public void setUsuarioPessoa(UsuarioPessoa usuarioPessoa) {
        this.usuarioPessoa = usuarioPessoa;
    }

    public boolean getModoEdicao() {
        return modoEdicao;
    }

    public void setModoEdicao(boolean modoEdicao) {
        this.modoEdicao = modoEdicao;
    }
}





