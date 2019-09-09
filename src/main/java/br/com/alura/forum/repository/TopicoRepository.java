package br.com.alura.forum.repository;

import br.com.alura.forum.model.Topico;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/*
* uma interface nao precisa colocar notações do spring acima dela, ela é encontrada automaticamente
*
* extends JpaRepository<Entidade, Tipo do ID>

* com o JPARepository consigo realizar os metodos de banco: carregar todos, salvar, exluir, alterar, etc.
*
* extendendo JpaRepository nao preciso mais fazer a utilização dos metodos repetitivos em padrão DAO
*
* Jpa exige que todas entidades tenham construtores default, sem parametros
*
* A interface TopicoRepository está herdando da interface correta do Spring Data JPA.
* */

public interface TopicoRepository extends JpaRepository<Topico, Long> {

    Page<Topico> findByCursoNome(String nomeCurso, Pageable paginacao); //Curso esta em Topico, e Nome é um atributo de Curso. Em caso de ambiguidade posso utilizar underline Curso_Nome caso exista uma variavel com nome CursoNome
}
