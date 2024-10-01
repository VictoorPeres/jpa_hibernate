import br.com.avancard.dao.DaoGeneric;
import br.com.avancard.jpa_hibernate.HibernateUtil;
import br.com.avancard.model.TelefonePessoa;
import br.com.avancard.model.UsuarioPessoa;
import org.junit.jupiter.api.Test;

import java.util.List;

public class TesteHibernate {

    @Test
    public void testeHibernateUtil(){

        DaoGeneric<UsuarioPessoa> daoGeneric = new DaoGeneric<UsuarioPessoa>();
        UsuarioPessoa pessoa = new UsuarioPessoa();

        pessoa.setNome("Maria");
        pessoa.setSobrenome("Oliveira");
        pessoa.setEmail("maria@gmail.com");
        pessoa.setSenha("123456");
        pessoa.setIdade(18);
        pessoa.setLogin("victor.oliveira");

        daoGeneric.salvar(pessoa);
    }

    @Test
    public void testeListar(){
        DaoGeneric<UsuarioPessoa> dao = new DaoGeneric<UsuarioPessoa>();
        UsuarioPessoa pessoa = new UsuarioPessoa();

        pessoa.setId(4);
        pessoa = dao.listar(UsuarioPessoa.class, pessoa.getId());

        System.out.println(pessoa.getNome());

    }
    @Test
    public void testeAtualiza(){
        DaoGeneric<UsuarioPessoa> dao = new DaoGeneric<UsuarioPessoa>();
        UsuarioPessoa pessoa = dao.listar(UsuarioPessoa.class, 2L);

        pessoa.setIdade(42);
        pessoa.setNome("Maria Araujo");
        pessoa.setEmail("aluno@aluno.com");
        pessoa = dao.atualizar(pessoa);

        System.out.println(pessoa);
    }
    @Test
    public void testeExcluir(){
        DaoGeneric<UsuarioPessoa> dao = new DaoGeneric<UsuarioPessoa>();
        UsuarioPessoa pessoa = dao.listar(UsuarioPessoa.class, 2L);
        dao.excluir(pessoa);
    }
    @Test
    public void testeListaAll(){
        DaoGeneric<UsuarioPessoa> dao = new DaoGeneric<UsuarioPessoa>();
        List<UsuarioPessoa> list =  dao.listarAll(UsuarioPessoa.class);

        for(UsuarioPessoa pessoa : list){
            System.out.println(pessoa.toString());
        }
    }
    @Test
    public void testeQueryList(){
        DaoGeneric<UsuarioPessoa> dao = new DaoGeneric<UsuarioPessoa>(); /* Inicializando meu dao */
        List<UsuarioPessoa> list = dao.getEntityManager().createQuery(" from UsuarioPessoa where nome = 'Victor'").getResultList(); /* Gerando uma consulta sql que filtra por nome e resgatando os dados como uma lista usando o metodo getResultList */
        for(UsuarioPessoa pessoa : list){ /* Percorrendo a lista com os dados */
            System.out.println(pessoa);
        }
    }
    @Test
    public void testeQueryListMaxResult(){
        DaoGeneric<UsuarioPessoa> dao = new DaoGeneric<UsuarioPessoa>();
        List<UsuarioPessoa> list = dao.getEntityManager().createQuery(" from UsuarioPessoa order by id").setMaxResults(5).getResultList();

        System.out.println(list + "\n");
    }

    @Test
    public void testeQueryListParameter(){
        DaoGeneric<UsuarioPessoa> daoGeneric = new DaoGeneric<UsuarioPessoa>();

        List<UsuarioPessoa> list = daoGeneric.getEntityManager().createQuery("from UsuarioPessoa where nome = :nome or email = :email")
                                   .setParameter("nome", "Victor")
                                   .setParameter("email", "joao@gmail.com")
                                   .getResultList();

        for(UsuarioPessoa pessoa : list){
            System.out.println(pessoa);
        }

    }
    @Test
    public void testeNameQuery1(){
        DaoGeneric<UsuarioPessoa> daoGeneric = new DaoGeneric<UsuarioPessoa>();
        List<UsuarioPessoa> list = daoGeneric.getEntityManager().createNamedQuery("UsuarioPessoa.todos").getResultList(); /* Chamando a NamedQuery */

        for(UsuarioPessoa pessoa : list){
            System.out.println(pessoa);
        }
    }
    @Test
    public void testeNameQuery2(){
        DaoGeneric<UsuarioPessoa> daoGeneric = new DaoGeneric<UsuarioPessoa>();
        List<UsuarioPessoa> list = daoGeneric.getEntityManager()
                                   .createNamedQuery("UsuarioPessoa.buscaPorNome")
                                   .setParameter("nome", "Victor")
                                   .getResultList(); /* Chamando a NamedQuery */

        for(UsuarioPessoa pessoa : list){
            System.out.println(pessoa);
        }
    }
    @Test
    public void testeGravaTelefone(){
        DaoGeneric daoGeneric = new DaoGeneric();
        UsuarioPessoa pessoa = (UsuarioPessoa) daoGeneric.listar(UsuarioPessoa.class, 1L);
        TelefonePessoa telefone = new TelefonePessoa();
        telefone.setNumero("546456654");
        telefone.setTipo("Celular");
        telefone.setPessoa(pessoa);
        daoGeneric.salvarTel(telefone);
    }

    @Test
    public void testeConsultaTelefones(){
        DaoGeneric daoGeneric = new DaoGeneric();
        UsuarioPessoa pessoa = (UsuarioPessoa) daoGeneric.listar(UsuarioPessoa.class, 1L);

        for (TelefonePessoa telefone : pessoa.getTelefones()) {
            System.out.println(telefone.getNumero());
            System.out.println(telefone.getTipo());
            System.out.println(telefone.getPessoa().getNome());
            System.out.println("---------------------------------------");
        }
    }

}
