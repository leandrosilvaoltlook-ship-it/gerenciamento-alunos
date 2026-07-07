package br.com.gerenciamento.repository;

import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import br.com.gerenciamento.enums.Curso;
import br.com.gerenciamento.enums.Status;
import br.com.gerenciamento.enums.Turno;
import br.com.gerenciamento.model.Aluno;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UsuarioRepositoryTest {
    
    @Autowired
    private AlunoRepository repository;

    @Before
    public void limparBanco() {
        repository.deleteAll();
    }

    @Test
    public void findByStatusInativo() {

        Aluno ativo = new Aluno();
        ativo.setNome("Carlos Silva");
        ativo.setMatricula("111");
        ativo.setCurso(Curso.ADMINISTRACAO);
        ativo.setStatus(Status.ATIVO);
        ativo.setTurno(Turno.NOTURNO);

        Aluno inativo = new Aluno();
        inativo.setNome("Maria Souza");
        inativo.setMatricula("222");
        inativo.setCurso(Curso.ADMINISTRACAO);
        inativo.setStatus(Status.INATIVO);
        inativo.setTurno(Turno.NOTURNO);

        repository.save(ativo);
        repository.save(inativo);

        List<Aluno> lista =
                repository.findByStatusInativo();

        Assert.assertEquals(1, lista.size());
        Assert.assertEquals(
                Status.INATIVO,
                lista.get(0).getStatus());
    }

     @Test
    public void findByStatusAtivo() {

        Aluno aluno = new Aluno();
        aluno.setNome("João Pedro");
        aluno.setMatricula("333");
        aluno.setCurso(Curso.ADMINISTRACAO);
        aluno.setStatus(Status.ATIVO);
        aluno.setTurno(Turno.NOTURNO);

        repository.save(aluno);

        Assert.assertEquals(
                1,
                repository.findByStatusAtivo().size());
    }
     @Test
    public void findByNomeContainingIgnoreCase() {

        Aluno aluno = new Aluno();
        aluno.setNome("Leonardo");
        aluno.setMatricula("444");
        aluno.setCurso(Curso.ADMINISTRACAO);
        aluno.setStatus(Status.ATIVO);
        aluno.setTurno(Turno.NOTURNO);

        repository.save(aluno);

        Assert.assertEquals(
                1,
                repository
                        .findByNomeContainingIgnoreCase("leo")
                        .size());
    }


    @Test
    public void salvarAlunoRepository() {

        Aluno aluno = new Aluno();
        aluno.setNome("Fernanda Lima");
        aluno.setMatricula("555");
        aluno.setCurso(Curso.ADMINISTRACAO);
        aluno.setStatus(Status.ATIVO);
        aluno.setTurno(Turno.NOTURNO);

        repository.save(aluno);

        Assert.assertNotNull(aluno.getId());
    }

}
