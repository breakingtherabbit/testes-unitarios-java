package br.ce.wcaquino.servicos;

import java.util.Date;

import br.ce.wcaquino.entidades.Filme;
import br.ce.wcaquino.entidades.Locacao;
import br.ce.wcaquino.entidades.Usuario;
import org.junit.Assert;
import org.junit.Test;

import static br.ce.wcaquino.utils.DataUtils.*;

public class LocacaoService {
	
	public Locacao alugarFilme(Usuario usuario, Filme filme) {
		Locacao locacao = new Locacao();
		locacao.setFilme(filme);
		locacao.setUsuario(usuario);
		locacao.setDataLocacao(new Date());
		locacao.setValor(filme.getPrecoLocacao());

		//Entrega no dia seguinte
		Date dataEntrega = new Date();
		dataEntrega = adicionarDias(dataEntrega, 1);
		locacao.setDataRetorno(dataEntrega);
		
		//Salvando a locacao...	
		//TODO adicionar método para salvar
		
		return locacao;
	}

	@Test
	public void teste() {
		// Cenário
		Usuario usuario = new Usuario("Usuário 1");
		Filme filme = new Filme("Filme 1", 2, 5.0);
		LocacaoService service = new LocacaoService();

		// Ação
		Locacao locacao = service.alugarFilme(usuario, filme);

		// Verificação
		Assert.assertTrue(locacao.getValor() == 5.0);
		Assert.assertTrue(isMesmaData(locacao.getDataLocacao(), new Date()));
		Assert.assertTrue(isMesmaData(locacao.getDataRetorno(), obterDataComDiferencaDias(1)));
	}
}