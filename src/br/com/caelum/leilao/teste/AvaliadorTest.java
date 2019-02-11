package br.com.caelum.leilao.teste;

import static org.junit.Assert.assertEquals;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import br.com.caelum.leilao.builder.CriadorDeLeilao;
import br.com.caelum.leilao.dominio.Lance;
import br.com.caelum.leilao.dominio.Leilao;
import br.com.caelum.leilao.dominio.Usuario;
import br.com.caelum.leilao.servico.Avaliador;

public class AvaliadorTest {

	private Avaliador avaliador;
	private Usuario joao;
	private Usuario maria;
	private Usuario jose;

	@Before /*executa m�todo antes de cada m�todo de @Test*/
	public void criaAvaliador() {
		this.avaliador = new Avaliador();
		this.joao = new Usuario("Jo�o");
		this.maria = new Usuario("Maria");
		this.jose = new Usuario("Jos�");		
	}
	
	@After /*executa m�todo depois de cada m�todo de @Test*/
	public void finaliza() {
		//usado para fechar recursos, banco de dados, sess�o etc..			 
	}
	
	@Test
	public void deveEntenderLeilaoEmOrdemCrescente() {
		//parte 1: cria��o do cen�rio
		
		
		
		Leilao leilao = new CriadorDeLeilao().para("Play2")
                .lance(joao, 250.0)
                .lance(maria, 300.0)
                .lance(jose, 400.0)                
                .constroi();				
		
		//parte 2: cria��o de uma a��o		
		avaliador.avalia(leilao);
		
		//parte 3: valida��o
		double maiorEsperado = 400.00;
		double menorEsperado = 250.00;
		
		assertThat(avaliador.getMaiorLance(), equalTo(maiorEsperado));
		assertThat(avaliador.getMenorLance(), equalTo(menorEsperado));		
	}	
	
	@Test
	public void deveEntenderLeilaoComApenasUmLance() {
		//parte 1: cria��o do cen�rio
				
		
//		Leilao leilao = new Leilao("Play2");		
//		leilao.propoe(new Lance(joao, 1000.00));
		
		
		//data builder
		Leilao leilao = new CriadorDeLeilao().para("Playstation 3 Novo")
                .lance(joao, 1000.0)
                .constroi();			
		
		
		//parte 2: cria��o de uma a��o		
		avaliador.avalia(leilao);
		
		//parte 3: valida��o	
		/*
		assertEquals(1000.00, avaliador.getMaiorLance(), 0.00001);
		assertEquals(1000.00, avaliador.getMenorLance(), 0.00001);
		*/
		assertThat(avaliador.getMaiorLance(), equalTo(1000.0));
		assertThat(avaliador.getMenorLance(), equalTo(1000.0));
	}		
	
	@Test
	public void deveEncontrarOsTresMaioresLances() {
		//parte 1: cria��o do cen�rio
		Leilao leilao = new CriadorDeLeilao().para("Playstation 3 Novo")
                .lance(joao, 100.0)
                .lance(maria, 200.0)
                .lance(joao, 300.0)
                .lance(maria, 400.0)
                .constroi();		
		
		//parte 2: cria��o de uma a��o		
		avaliador.avalia(leilao);
		
		//parte 3: valida��o
		List<Lance> maiores = avaliador.getTresMaiores();
			
		assertEquals(3, maiores.size());
		/*
		assertEquals(400.00, maiores.get(0).getValor(), 0.00001);
		assertEquals(300.00, maiores.get(1).getValor(), 0.00001);
		assertEquals(200.00, maiores.get(2).getValor(), 0.00001);
		*/
		assertThat(maiores, hasItems(
				new Lance(maria, 400.0),
				new Lance(joao, 300.0),
				new Lance(maria, 200.0)
		));		
		
	}
	
	@Test(expected=RuntimeException.class) /*testando EXCEPTIONS TAMB�M, MUITO IMPORTANTE*/
	public void naoDeveAvaliarLeilaoSemNenhumLanceDado() {
		Leilao leilao = new CriadorDeLeilao().para("Produto que ningu�m compra")
				.constroi();
		
		avaliador.avalia(leilao);
	}
}
