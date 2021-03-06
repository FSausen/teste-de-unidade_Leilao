package br.com.caelum.leilao.teste;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import br.com.caelum.leilao.builder.CriadorDeLeilao;
import br.com.caelum.leilao.dominio.Lance;
import br.com.caelum.leilao.dominio.Leilao;
import br.com.caelum.leilao.dominio.Usuario;

public class LeilaoTest {

	private Usuario steveJobs;
	private Usuario steveWozniak;
	private Usuario billGates;

	@Before
	public void inicializa() {
		this.steveJobs = new Usuario("Steve Jobs");
		this.steveWozniak = new Usuario("Steve Wozniak");
		this.billGates = new Usuario("Bill Gates");
	}
	
    @Test
    public void deveReceberUmLance() {
        Leilao leilao = new CriadorDeLeilao()
        		.para("Macbook Pro 15")
        		.lance(steveJobs, 2000)        		
        		.constroi();    	
    	                       
        assertEquals(1, leilao.getLances().size());
        assertEquals(2000, leilao.getLances().get(0).getValor(), 0.00001);
    }

    @Test
    public void deveReceberVariosLances() {
    	/*
        Leilao leilao = new Leilao("Macbook Pro 15");
        leilao.propoe(new Lance(new Usuario("Steve Jobs"), 2000));
        leilao.propoe(new Lance(new Usuario("Steve Wozniak"), 3000));
        */
        
        Leilao leilao = new CriadorDeLeilao()
        		.para("Macbook Pro 15")
        		.lance(steveJobs, 2000)
        		.lance(steveWozniak, 3000)
        		.constroi();

        assertEquals(2, leilao.getLances().size());
        assertEquals(2000, leilao.getLances().get(0).getValor(), 0.00001);
        assertEquals(3000, leilao.getLances().get(1).getValor(), 0.00001);
    }
    
    @Test
    public void naoDeveAceitarDoisLancesSeguidosDoMesmoUsuario() {
        /*
    	Leilao leilao = new Leilao("Macbook Pro 15");
        Usuario steveJobs = new Usuario("Steve Jobs");
        leilao.propoe(new Lance(steveJobs, 2000));
        leilao.propoe(new Lance(steveJobs, 3000));        
        */
        Leilao leilao = new CriadorDeLeilao()
        		.para("Macbook Pro 15")
        		.lance(steveJobs, 2000)
        		.lance(steveJobs, 3000)
        		.constroi();        

        assertEquals(1, leilao.getLances().size());
        assertEquals(2000, leilao.getLances().get(0).getValor(), 0.00001);
    }    
    
    @Test
    public void naoDeveAceitarMaisDoQue5LancesDeUmMesmoUsuario() {
    	/*
        Leilao leilao = new Leilao("Macbook Pro 15");
        Usuario steveJobs = new Usuario("Steve Jobs");
        Usuario billGates = new Usuario("Bill Gates");

        leilao.propoe(new Lance(steveJobs, 2000));
        leilao.propoe(new Lance(billGates, 3000));
        leilao.propoe(new Lance(steveJobs, 4000));
        leilao.propoe(new Lance(billGates, 5000));
        leilao.propoe(new Lance(steveJobs, 6000));
        leilao.propoe(new Lance(billGates, 7000));
        leilao.propoe(new Lance(steveJobs, 8000));
        leilao.propoe(new Lance(billGates, 9000));
        leilao.propoe(new Lance(steveJobs, 10000));
        leilao.propoe(new Lance(billGates, 11000));

        // deve ser ignorado
        leilao.propoe(new Lance(steveJobs, 12000));
        */
        
        Leilao leilao = new CriadorDeLeilao()
        		.para("Macbook Pro 15")
        		.lance(steveJobs, 2000)
        		.lance(billGates, 3000)
        		.lance(steveJobs, 4000)
        		.lance(billGates, 5000)
        		.lance(steveJobs, 6000)
        		.lance(billGates, 7000)
        		.lance(steveJobs, 8000)
        		.lance(billGates, 9000)
        		.lance(steveJobs, 10000)
        		.lance(billGates, 11000)
        		.lance(steveJobs, 12000)        		
        		.constroi();             

        assertEquals(10, leilao.getLances().size());
        int ultimo = leilao.getLances().size() - 1;
        Lance ultimoLance = leilao.getLances().get(ultimo);
        assertEquals(11000.0, ultimoLance.getValor(), 0.00001);
    }
}