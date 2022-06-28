package dao;

import model.Aluno;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AlunoDao extends JpaRepository<Aluno,Long> {

    //findAll, findById, get, save, delete, update
}
