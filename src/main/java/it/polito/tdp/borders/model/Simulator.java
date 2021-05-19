package it.polito.tdp.borders.model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultEdge;

public class Simulator {

	//Modello --> qual è lo stato del sistema ad ogni passo
	private Graph<Country,DefaultEdge> grafo;
	
	//Tipi di evento --> coda prioritaria
	private PriorityQueue<Evento> queue;   //la coda prioritaria serve quando c'è una creazione di eventi durante la simulazione
	
	//Parametri della simulazione
	private int nMigranti=1000;
	private Country partenza;
	
	//Valori in output
	private int T=-1;
	private Map<Country, Integer> stanziali;  //se avessi usato una list con countryandnumber avrei avuto valori ordinati, ma è meno immediato modificare man mano il numero di persone stanziali
	
	public void init(Country country, Graph<Country,DefaultEdge> grafo) {
		this.partenza=country;
		this.grafo=grafo;
		
		//imposto lo stato iniziale
		this.T=1;
		this.stanziali=new HashMap<Country, Integer>();
		
		for(Country c:this.grafo.vertexSet()) {
			stanziali.put(c, 0);  //metto tutti i paesi del grafo, all'inizio con 0 persone stanziali
		}
		
		//creo la coda
		this.queue = new PriorityQueue<Evento>();
		//inserisco il primo evento
		this.queue.add(new Evento(T,partenza,nMigranti));  //all'inizio la coda contiene solo un evento, gli altri verranno creati e aggiunti man mano
	}
	
	public void run() {
		//finchè la coda non si svuota, prendo un evento alla volta e lo eseguo
		Evento e;
		while((e=this.queue.poll())!=null) {
			//simulo e
			this.T=e.getT();
			int nPersone=e.getN();
			Country stato=e.getCountry();
			
			//mi recupero i vicini dello stato
			List<Country> vicini=Graphs.neighborListOf(this.grafo, stato);
			
			int migrantiPerStato=(nPersone/2)/vicini.size();  //con int ho sempre troncamento per difetto
			
			if(migrantiPerStato>0) {  //se i vicini sono>npersone che si spostano, avrò migranti per stato=0
				//le persone si spostano
				for(Country confinante:vicini) {
					queue.add(new Evento(e.getT()+1, confinante, migrantiPerStato));
				}
			}
			
			int numstanziali=nPersone-migrantiPerStato*vicini.size();
			this.stanziali.put(stato, this.stanziali.get(stato)+numstanziali); //sovrascrivo lo stato, aggiorno il num stanzaili perchè le persone possono tornare più volte nello stato
		}
	}

	public int getT() {
		return T;
	}

	public Map<Country, Integer> getStanziali() {
		return stanziali;
	}
	
	
}
