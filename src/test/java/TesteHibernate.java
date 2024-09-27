import br.com.avancard.jpa_hibernate.HibernateUtil;
import org.junit.jupiter.api.Test;

public class TesteHibernate {

    @Test
    public void testeHibernateUtil(){
        HibernateUtil.getEntityManager();
    }
}
