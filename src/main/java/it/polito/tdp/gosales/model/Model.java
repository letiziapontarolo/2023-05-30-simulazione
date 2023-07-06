package it.polito.tdp.gosales.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;
import org.jgrapht.traverse.DepthFirstIterator;

import it.polito.tdp.gosales.dao.GOsalesDAO;

public class Model {
	
	private Graph<Retailers, DefaultWeightedEdge> grafo;
	private GOsalesDAO dao;
	private Map<Integer, Retailers> retailersIdMap;
	private List<Adiacenza> adiacenze;
	

	public Model() {
		
		dao = new GOsalesDAO();
		
	}
	
public List<String> listaRetailers() {
		
		List<String> l = new ArrayList<>();
		for (Retailers r: retailersIdMap.values()) {
			l.add(r.getName());
		}
		
		Collections.sort(l);
		return l;
		
	}
	
	
	public List<String> listaPaesi() {
		return this.dao.listaPaesi();
	}
	
	public List<Integer> listaAnni() {
		return this.dao.listaAnni();
	}
	
	
	public String calcolaConnessa(String nome) {
		
		double sommaPesi = 0;
		String s = "";
		Retailers rr = null;
		for (Retailers r : retailersIdMap.values()) {
			if (r.getName().equals(nome)) {
				rr = retailersIdMap.get(r.getCode());
		
			}
		}
		
		 Set<Retailers> visitati = new HashSet<>();
		 
		DepthFirstIterator<Retailers,DefaultWeightedEdge> it = new
		DepthFirstIterator<Retailers, DefaultWeightedEdge>(this.grafo, rr);
		
		while (it.hasNext()) {
		visitati.add(it.next());
		}
		
		for (Retailers rrr : visitati) {
			for (Retailers rrrr : visitati) {
				if (rrr.getCode()> rrrr.getCode()) {
					for (Adiacenza a : adiacenze) {
						if (a.getR1()==rrr.getCode() && a.getR2()==rrrr.getCode()) {
							sommaPesi = sommaPesi + a.getPeso();
						}
					}
					
				}
			}
			
		}
		
		s = "La componente connessa di " + nome + " ha dimensione di " + visitati.size() +
				"\n"+"Il peso totale degli archi della componente connessa è " + sommaPesi;
		return s;
		}

	
public String riempiArchi() {
		
		String s = "";
		List<Adiacenza> lista = new ArrayList<>();
		for (Adiacenza a : adiacenze) {
			lista.add(a);
		}
		
		Collections.sort(lista, new Comparator<Adiacenza>() {
			 @Override
			 public int compare(Adiacenza a1, Adiacenza a2) {
			 return (int) (a1.getPeso() - a2.getPeso ());
			 }});
		
		
		for (Adiacenza aa : lista) {
			
			s = s + aa.getPeso() + ": " + retailersIdMap.get(aa.getR1()).getName() + " <-> " + retailersIdMap.get(aa.getR2()).getName() + "\n";
		}
		return s;

		
	}
	
	public String riempiVertici() {
		
		String s = "";
		List<Retailers> lista = new ArrayList<>();
		for (Retailers r : retailersIdMap.values()) {
			lista.add(r);
		}
		
		Collections.sort(lista, new Comparator<Retailers>() {
		    @Override
		    public int compare(Retailers r1, Retailers r2)
		    {
		        return r1.getName().compareTo(r2.getName());
		    }
		});
		
		for (Retailers rr : lista) {
			s = s + rr.getName() + "\n";
		}
		return s;

		
	}
	
	public void creaGrafo(String country, int date, int m) {
		
		grafo = new SimpleWeightedGraph<>(DefaultWeightedEdge.class);
		retailersIdMap = new HashMap<Integer, Retailers>();
		adiacenze = new ArrayList<>();
		this.dao.riempiMappa(country, retailersIdMap);
		Graphs.addAllVertices(this.grafo, retailersIdMap.values());
		this.dao.creaArchi(country, date, m, adiacenze);
		for (Adiacenza a : adiacenze) {
			Graphs.addEdgeWithVertices(this.grafo, retailersIdMap.get(a.getR1()), retailersIdMap.get(a.getR2()), a.getPeso());
		}
		
		
	}
	
	public int numeroVertici() {
		return this.grafo.vertexSet().size();
		}
		 public int numeroArchi() {
		return this.grafo.edgeSet().size();
		}
	
}
