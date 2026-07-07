package br.com.gerenciamento.service;

import br.com.gerenciamento.enums.Curso;
import br.com.gerenciamento.enums.Status;
import br.com.gerenciamento.enums.Turno;
import br.com.gerenciamento.model.Aluno;
import jakarta.validation.ConstraintViolationException;
import org.junit.*;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AlunoServiceTest {

    @Autowired
    private ServiceAluno serviceAluno;

    @Test
    public void getById() {
        Aluno aluno = new Aluno();
        aluno.setId(1L);
        aluno.setNome("Vinicius");
        aluno.setTurno(Turno.NOTURNO);
        aluno.setCurso(Curso.ADMINISTRACAO);
        aluno.setStatus(Status.ATIVO);
        aluno.setMatricula("123456");
        this.serviceAluno.save(aluno);

        Aluno alunoRetorno = this.serviceAluno.getById(1L);
        Assert.assertTrue(alunoRetorno.getNome().equals("Vinicius"));
    }

    @Test
    public void salvarSemNome() {
        Aluno aluno = new Aluno();
        aluno.setId(1L);
        aluno.setTurno(Turno.NOTURNO);
        aluno.setCurso(Curso.ADMINISTRACAO);
        aluno.setStatus(Status.ATIVO);
        aluno.setMatricula("123456");
        Assert.assertThrows(ConstraintViolationException.class, () -> {
                this.serviceAluno.save(aluno);});
    }

    @Test
public void findByNomeContainingIgnoreCase() {

    Aluno aluno1 = new Aluno();
    aluno1.setNome("Leonardo");
    aluno1.setMatricula("111");
    aluno1.setCurso(Curso.ADMINISTRACAO);
    aluno1.setStatus(Status.ATIVO);
    aluno1.setTurno(Turno.NOTURNO);

    Aluno aluno2 = new Aluno();
    aluno2.setNome("Maria");
    aluno2.setMatricula("222");
    aluno2.setCurso(Curso.ADMINISTRACAO);
    aluno2.setStatus(Status.ATIVO);
    aluno2.setTurno(Turno.NOTURNO);

    serviceAluno.save(aluno1);
    serviceAluno.save(aluno2);

    Assert.assertEquals(
            1,
            serviceAluno
                    .findByNomeContainingIgnoreCase("leo")
                    .size());

    Assert.assertEquals(
            "Leonardo",
            serviceAluno
                    .findByNomeContainingIgnoreCase("leo")
                    .get(0)
                    .getNome());
}

@Test
public void findAll() {

    Aluno aluno1 = new Aluno();
    aluno1.setNome("Carlos");
    aluno1.setMatricula("333");
    aluno1.setCurso(Curso.ADMINISTRACAO);
    aluno1.setStatus(Status.ATIVO);
    aluno1.setTurno(Turno.NOTURNO);

    Aluno aluno2 = new Aluno();
    aluno2.setNome("Fernanda");
    aluno2.setMatricula("444");
    aluno2.setCurso(Curso.ADMINISTRACAO);
    aluno2.setStatus(Status.ATIVO);
    aluno2.setTurno(Turno.NOTURNO);

    serviceAluno.save(aluno1);
    serviceAluno.save(aluno2);

    Assert.assertTrue(
            serviceAluno.findAll().size() >= 2);
}
}