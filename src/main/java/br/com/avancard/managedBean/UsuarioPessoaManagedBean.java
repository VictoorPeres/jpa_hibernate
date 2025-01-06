package br.com.avancard.managedBean;

import br.com.avancard.dao.DaoGeneric;
import br.com.avancard.jpa_hibernate.SessionUtil;
import br.com.avancard.model.UsuarioPessoa;
import com.google.gson.Gson;
import org.primefaces.component.barchart.BarChart;
import org.primefaces.model.charts.ChartData;
import org.primefaces.model.charts.bar.BarChartDataSet;
import org.primefaces.model.charts.bar.BarChartModel;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.AjaxBehaviorEvent;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

@ManagedBean(name = "usuarioPessoaManagedBean")
@ViewScoped
public class UsuarioPessoaManagedBean {

    private UsuarioPessoa usuarioPessoa = new UsuarioPessoa();
    private DaoGeneric<UsuarioPessoa> dao = new DaoGeneric<>();
    private List<UsuarioPessoa> usuarios;
    private boolean modoEdicao;
    private BarChartModel barChartModel;

   @PostConstruct
    public void init(){
        usuarios = new ArrayList<UsuarioPessoa>();
        carregarPessoas();
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
                if(modoEdicao){
                    dao.atualizar(usuarioPessoa);
                    novo();
                    carregarPessoas();
                    FacesMessage facesMessage = new FacesMessage(FacesMessage.SEVERITY_INFO, "", "Usuario atualizado com sucesso.");
                    FacesContext.getCurrentInstance().addMessage(null, facesMessage);
                    return "";
                }else{
                    dao.salvar(usuarioPessoa);
                    novo();
                    carregarPessoas();
                    FacesMessage facesMessage = new FacesMessage(FacesMessage.SEVERITY_INFO, "", "Usuario cadastrado com sucesso.");
                    FacesContext.getCurrentInstance().addMessage(null, facesMessage);
                    return "";
                }

            }
        }catch (Exception e){
            FacesMessage facesMessage = new FacesMessage(FacesMessage.SEVERITY_ERROR, "", "Erro ao cadastrar usuario: "+e.getMessage());
            FacesContext.getCurrentInstance().addMessage(null, facesMessage);
            return "";
        }
        return "";
    }

    public String excluir(UsuarioPessoa usuario){
       try{
           dao.excluir(usuario);
           carregarPessoas();
           FacesMessage facesMessage = new FacesMessage(FacesMessage.SEVERITY_INFO, "", "Usuario deletado com sucesso.");
           FacesContext.getCurrentInstance().addMessage(null, facesMessage);
           return "";
       }catch (Exception e){
           if(e.getCause() instanceof org.hibernate.exception.ConstraintViolationException){
               FacesMessage facesMessage = new FacesMessage(FacesMessage.SEVERITY_ERROR, "", "Erro ao deletar usuario: "+e.getMessage());
               FacesContext.getCurrentInstance().addMessage(null, facesMessage);
           }
           return "";
       }
    }

    public String edit(UsuarioPessoa usuario){
       try{
           usuarioPessoa = usuario;
           setModoEdicao(true);
           FacesMessage facesMessage = new FacesMessage(FacesMessage.SEVERITY_WARN, "", "Modo de edição do usuário " + usuarioPessoa.getNome() + ". ");            FacesContext.getCurrentInstance().addMessage("msg", facesMessage);
           return "";
       } catch (Exception e) {
           FacesMessage facesMessage = new FacesMessage(FacesMessage.SEVERITY_ERROR, "", "Erro ao editar o registro: "+e.getMessage());
           FacesContext.getCurrentInstance().addMessage(null, facesMessage);
           return "";
       }
    }

    public void novo(){
        this.modoEdicao = false;
        usuarioPessoa = new UsuarioPessoa();
    }

    public String cadastrarTelefone(UsuarioPessoa usuarioP){
        SessionUtil.setParam("usuario", usuarioP);
                return "novo";
    }

    public void carregarPessoas(){
        usuarios = dao.listarAll(UsuarioPessoa.class);
    }

    public void pesquisaCep(AjaxBehaviorEvent event){

       try{
           System.out.println(usuarioPessoa.getCep());
           URL url = new URL("https://viacep.com.br/ws/"+usuarioPessoa.getCep()+"/json/");
           URLConnection connectioon = url.openConnection();
           InputStream inputStream = connectioon.getInputStream();
           BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));

           String cep = "";
           StringBuilder jsonCep = new StringBuilder();

           while((cep = reader.readLine()) != null){
               jsonCep.append(cep);
           }

           UsuarioPessoa userCepPessoa = new Gson().fromJson(jsonCep.toString(), UsuarioPessoa.class);
           System.out.println(userCepPessoa);

           usuarioPessoa.setCep(userCepPessoa.getCep());
           usuarioPessoa.setLogradouro(userCepPessoa.getLogradouro());
           usuarioPessoa.setComplemento(userCepPessoa.getComplemento());
           usuarioPessoa.setBairro(userCepPessoa.getBairro());
           usuarioPessoa.setLocalidade(userCepPessoa.getLocalidade());
           usuarioPessoa.setEstado(userCepPessoa.getEstado());
           usuarioPessoa.setIbge(userCepPessoa.getIbge());


       }catch (Exception e){
           e.printStackTrace();
       }

    }

    public UsuarioPessoa getUsuarioPessoa() {
        return usuarioPessoa;
    }
    public void setUsuarioPessoa(UsuarioPessoa usuarioPessoa) {
        this.usuarioPessoa = usuarioPessoa;
    }
    public List<UsuarioPessoa> getUsuarios() {
        return usuarios;
    }
    public void setUsuarios(List<UsuarioPessoa> usuarios) {
        this.usuarios = usuarios;
    }
    public boolean getModoEdicao() {
        return modoEdicao;
    }
    public void setModoEdicao(boolean modoEdicao) {
        this.modoEdicao = modoEdicao;
    }

    public BarChartModel getBarChartModel() {
        return barChartModel;
    }
}
