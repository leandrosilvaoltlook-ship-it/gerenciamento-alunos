package br.com.gerenciamento.controller;

import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import br.com.gerenciamento.enums.Curso;
import br.com.gerenciamento.enums.Status;
import br.com.gerenciamento.enums.Turno;
import br.com.gerenciamento.model.Aluno;
import br.com.gerenciamento.repository.AlunoRepository;
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
public class AlunoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private AlunoRepository repository;

    @Before
    public void limparBanco() {
        repository.deleteAll();
    }

    @Test
    public void pesquisarAlunoNomeVazio() throws Exception {

        Aluno aluno1 = new Aluno();
        aluno1.setNome("Leonardo");
        aluno1.setMatricula("111");
        aluno1.setCurso(Curso.ADMINISTRACAO);
        aluno1.setStatus(Status.ATIVO);
        aluno1.setTurno(Turno.NOTURNO);

        repository.save(aluno1);

        Aluno aluno2 = new Aluno();
        aluno2.setNome("Maria");
        aluno2.setMatricula("222");
        aluno2.setCurso(Curso.ADMINISTRACAO);
        aluno2.setStatus(Status.ATIVO);
        aluno2.setTurno(Turno.NOTURNO);

        repository.save(aluno2);

        mockMvc.perform(
                post("/pesquisar-aluno")
                        .param("nome", "")
        )
        .andExpect(status().isOk());

        Assert.assertEquals(
                2,
                repository.findAll().size());
    }


    @Test
    public void pesquisarAlunoPorNome() throws Exception {

        Aluno aluno = new Aluno();
        aluno.setNome("Leonardo");
        aluno.setMatricula("333");
        aluno.setCurso(Curso.ADMINISTRACAO);
        aluno.setStatus(Status.ATIVO);
        aluno.setTurno(Turno.NOTURNO);

        repository.save(aluno);

        mockMvc.perform(
                post("/pesquisar-aluno")
                        .param("nome", "Leo")
        )
        .andExpect(status().isOk());

        Assert.assertEquals(
                1,
                repository.findByNomeContainingIgnoreCase("Leo").size());
    }
    @Test
    public void pesquisarAlunoNomeInexistente() throws Exception {

        mockMvc.perform(
                post("/pesquisar-aluno")
                        .param("nome", "Inexistente")
        )
        .andExpect(status().isOk());

        Assert.assertTrue(
                repository.findByNomeContainingIgnoreCase("Inexistente").isEmpty());
    }
    @Test
    public void pesquisarAlunoNomeComEspacos() throws Exception {

        Aluno aluno = new Aluno();
        aluno.setNome("Carlos");
        aluno.setMatricula("444");
        aluno.setCurso(Curso.ADMINISTRACAO);
        aluno.setStatus(Status.ATIVO);
        aluno.setTurno(Turno.NOTURNO);

        repository.save(aluno);

        mockMvc.perform(
                post("/pesquisar-aluno")
                        .param("nome", "   ")
        )
        .andExpect(status().isOk());

        Assert.assertEquals(
                1,
                repository.findAll().size());
    }
}
