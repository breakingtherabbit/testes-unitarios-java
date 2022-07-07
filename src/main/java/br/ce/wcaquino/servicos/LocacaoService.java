package br.ce.wcaquino.servicos;

import java.util.Date;
import java.util.List;

import br.ce.wcaquino.entidades.Filme;
import br.ce.wcaquino.entidades.Locacao;
import br.ce.wcaquino.entidades.Usuario;
import br.ce.wcaquino.exceptions.FilmeSemEstoqueException;
import br.ce.wcaquino.exceptions.LocadoraException;
import br.ce.wcaquino.utils.DataUtils;

import static br.ce.wcaquino.utils.DataUtils.*;
import static java.util.Calendar.SUNDAY;

public class LocacaoService {
	
	public Locacao alugarFilme(Usuario usuario, List<Filme> filmes)
			throws FilmeSemEstoqueException, LocadoraException {

		if (usuario == null) {
			throw new LocadoraException("Usuario vazio");
		}

		if (filmes == null) {
			throw new LocadoraException("Filme vazio");
		}

		for (Filme filme: filmes) {
			if (filme.getEstoque() == 0) {
				throw new FilmeSemEstoqueException();
			}
		}

		Locacao locacao = new Locacao();
		locacao.setFilmes(filmes);
		locacao.setUsuario(usuario);
		locacao.setDataLocacao(new Date());

		Double valorLocacao = 0D;
		for (int i = 0; i < filmes.size(); i++) {
			Filme filme = filmes.get(i);
			Double valorFilme = filme.getPrecoLocacao();

			switch (i) {
				case 2 -> valorFilme *= .75;
				case 3 -> valorFilme *= .5;
				case 4 -> valorFilme *= .25;
				case 5 -> valorFilme *= 0;
			}

			valorLocacao += valorFilme;
		}
		locacao.setValor(valorLocacao);

		//Entrega no dia seguinte
		Date dataEntrega = new Date();
		dataEntrega = adicionarDias(dataEntrega, 1);
		if (DataUtils.verificarDiaSemana(dataEntrega, SUNDAY)) {
			dataEntrega = adicionarDias(dataEntrega, 1);
		}
		locacao.setDataRetorno(dataEntrega);
		
		//Salvando a locacao...	
		//TODO adicionar método para salvar
		
		return locacao;
	}
}