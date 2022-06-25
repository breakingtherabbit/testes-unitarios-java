package br.ce.wcaquino.servicos;

import br.ce.wcaquino.entidades.Filme;
import br.ce.wcaquino.entidades.Locacao;
import br.ce.wcaquino.entidades.Usuario;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ErrorCollector;
import org.junit.rules.ExpectedException;

import java.util.Date;

import static br.ce.wcaquino.utils.DataUtils.isMesmaData;
import static br.ce.wcaquino.utils.DataUtils.obterDataComDiferencaDias;
import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

public class LocacaoServiceTest {

    @Rule
    public ErrorCollector error = new ErrorCollector();

    @Rule
    public ExpectedException exception = ExpectedException.none();

    @Test
    public void testeLocacao() throws Exception {
        // Cenário
        Usuario usuario = new Usuario("Usuário 1");
        Filme filme = new Filme("Filme 1", 2, 5.0);
        LocacaoService service = new LocacaoService();

        // Ação
        Locacao locacao = service.alugarFilme(usuario, filme);

        // Verificação
        error.checkThat(locacao.getValor(), is(equalTo(5.0)));
        error.checkThat(isMesmaData(locacao.getDataLocacao(), new Date()), is(true));
        error.checkThat(isMesmaData(locacao.getDataRetorno(), obterDataComDiferencaDias(1)), is(true));
    }

    @Test(expected = Exception.class)
    public void testeLocacao_filmeSemEstoque() throws Exception {
        // Cenário
        Usuario usuario = new Usuario("Usuario 1");
        Filme filme = new Filme("Filme 1", 0, 5.0);
        LocacaoService service = new LocacaoService();

        // Ação
        service.alugarFilme(usuario, filme);
    }

    @Test
    public void testeLocacao_filmeSemEstoque_2() {
        // Cenário
        Usuario usuario = new Usuario("Usuario 1");
        Filme filme = new Filme("Filme 1", 0, 5.0);
        LocacaoService service = new LocacaoService();

        // Ação
        try {
            service.alugarFilme(usuario, filme);
            Assert.fail("Deveria ter lancado uma excecao");
        } catch (Exception e) {
            Assert.assertThat(e.getMessage(), is("Filme sem estoque"));
        }
    }

    @Test
    public void testeLocacao_filmeSemEstoque_3() throws Exception {
        // Cenário
        Usuario usuario = new Usuario("Usuario 1");
        Filme filme = new Filme("Filme 1", 0, 5.0);
        LocacaoService service = new LocacaoService();

        exception.expect(Exception.class);
        exception.expectMessage("Filme sem estoque");

        // Ação
        service.alugarFilme(usuario, filme);

    }
}
