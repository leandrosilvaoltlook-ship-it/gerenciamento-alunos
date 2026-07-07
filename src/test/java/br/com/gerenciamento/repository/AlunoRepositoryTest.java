package br.com.gerenciamento.repository;

import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import br.com.gerenciamento.model.Usuario;
import br.com.gerenciamento.util.Util;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;


@RunWith(SpringRunner.class)
@SpringBootTest
public class AlunoRepositoryTest {

      @Autowired
    private UsuarioRepository repository;

    @Before
    public void limparBanco() {
        repository.deleteAll();
    }

    @Test
    public void buscarLogin() throws Exception {

        Usuario usuario = new Usuario();
        usuario.setUser("admin");
        usuario.setEmail("admin@gmail.com");
        usuario.setSenha(Util.md5("123"));

        repository.save(usuario);

        Usuario retorno =
                repository.buscarLogin(
                        "admin",
                        Util.md5("123"));

        Assert.assertNotNull(retorno);
        Assert.assertEquals(
                "admin",
                retorno.getUser());
    }

    @Test
    public void buscarLoginUsuarioInexistente() throws Exception {

        Usuario retorno =
                repository.buscarLogin(
                        "naoExiste",
                        Util.md5("123"));

        Assert.assertNull(retorno);
    }

     @Test
    public void findByEmail() {

        Usuario usuario = new Usuario();
        usuario.setUser("joao");
        usuario.setEmail("joao@gmail.com");
        usuario.setSenha("123");

        repository.save(usuario);

        Usuario retorno =
                repository.findByEmail(
                        "joao@gmail.com");

        Assert.assertNotNull(retorno);
    }

    @Test
    public void salvarUsuarioRepository() {

        Usuario usuario = new Usuario();
        usuario.setUser("maria");
        usuario.setEmail("maria@gmail.com");
        usuario.setSenha("123");

        repository.save(usuario);

        Assert.assertNotNull(usuario.getId());
    }

}
