package br.ce.wcaquino.servicos;

import br.ce.wcaquino.entidades.Filme;
import br.ce.wcaquino.entidades.Locacao;
import br.ce.wcaquino.entidades.Usuario;
import org.junit.Assert;
import org.junit.Test;

import java.util.Date;

import static br.ce.wcaquino.utils.DataUtils.isMesmaData;
import static br.ce.wcaquino.utils.DataUtils.obterDataComDiferencaDias;

public class LocacaoServiceTest {

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
