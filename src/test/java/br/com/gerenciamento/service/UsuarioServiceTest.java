package br.com.gerenciamento.service;

import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import br.com.gerenciamento.exception.EmailExistsException;
import br.com.gerenciamento.model.Usuario;
import br.com.gerenciamento.repository.UsuarioRepository;
import br.com.gerenciamento.util.Util;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UsuarioServiceTest {

    @Autowired
private ServiceUsuario serviceUsuario;

@Autowired
private UsuarioRepository usuarioRepository;

@Before
public void limparBanco() {
    usuarioRepository.deleteAll();
}

 @Test
    public void salvarUsuario() throws Exception {

        Usuario usuario = new Usuario();
        usuario.setUser("leonardo");
        usuario.setEmail("leo@gmail.com");
        usuario.setSenha("123");

        serviceUsuario.salvarUsuario(usuario);

        Assert.assertNotNull(
                usuarioRepository.findByEmail("leo@gmail.com"));
    }

     @Test(expected = EmailExistsException.class)
    public void salvarUsuarioEmailDuplicado() throws Exception {

        Usuario usuario1 = new Usuario();
        usuario1.setUser("leo");
        usuario1.setEmail("teste@gmail.com");
        usuario1.setSenha("123");

        Usuario usuario2 = new Usuario();
        usuario2.setUser("maria");
        usuario2.setEmail("teste@gmail.com");
        usuario2.setSenha("456");

        serviceUsuario.salvarUsuario(usuario1);

        serviceUsuario.salvarUsuario(usuario2);
    }

     @Test
    public void loginUsuarioValido() throws Exception {

        Usuario usuario = new Usuario();
        usuario.setUser("admin");
        usuario.setEmail("admin@gmail.com");
        usuario.setSenha("123");

        serviceUsuario.salvarUsuario(usuario);

        Usuario retorno =
                serviceUsuario.loginUser(
                        "admin",
                        Util.md5("123"));

        Assert.assertNotNull(retorno);
    }

     @Test
    public void loginUsuarioInvalido() {

        Usuario retorno =
                serviceUsuario.loginUser(
                        "usuarioInexistente",
                        "senhaErrada");

        Assert.assertNull(retorno);
    }
}
