package br.com.gerenciamento.controller;

import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import br.com.gerenciamento.model.Usuario;
import br.com.gerenciamento.repository.UsuarioRepository;
import br.com.gerenciamento.util.Util;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class UsuarioControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UsuarioRepository repository;

    @Before
    public void limparBanco() {
        repository.deleteAll();
    }

    @Test
    public void loginInvalido() throws Exception {

        mockMvc.perform(
                post("/login")
                        .param("user", "admin")
                        .param("senha", "123")
        )
        .andExpect(status().isOk());

        Assert.assertNull(
                repository.buscarLogin(
                        "admin",
                        Util.md5("123")));
    }

      @Test
    public void loginValido() throws Exception {

        Usuario usuario = new Usuario();
        usuario.setUser("admin");
        usuario.setEmail("admin@gmail.com");
        usuario.setSenha(Util.md5("123"));

        repository.save(usuario);

        mockMvc.perform(
                post("/login")
                        .param("user", "admin")
                        .param("senha", "123")
        )
        .andExpect(status().isOk());

        Assert.assertNotNull(
                repository.buscarLogin(
                        "admin",
                        Util.md5("123")));
    }

     @Test
    public void loginSenhaIncorreta() throws Exception {

        Usuario usuario = new Usuario();
        usuario.setUser("admin");
        usuario.setEmail("admin@gmail.com");
        usuario.setSenha(Util.md5("123"));

        repository.save(usuario);

        mockMvc.perform(
                post("/login")
                        .param("user", "admin")
                        .param("senha", "456")
        )
        .andExpect(status().isOk());

        Assert.assertNull(
                repository.buscarLogin(
                        "admin",
                        Util.md5("456")));
    }

@Test
public void logoutUsuario() throws Exception {

    mockMvc.perform(
            post("/logout")
    )
    .andExpect(status().isOk());

}
}
