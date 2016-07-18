package br.com.fiap.dao;

import java.util.List;

public interface Dao<T> {
	void adicionar(T entidade);
	 List<T> listar();

	T buscarById(Long id);
	void deletarById(Long id);
	T buscarById(String id);
	void deletarById(String id);
	List<T> pesquisar(String value, String prop);
	void update(T entidade);
}
