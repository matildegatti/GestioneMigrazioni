package it.polito.tdp.borders.model;

public class Evento implements Comparable<Evento>{
//evento n persone arrivano in uno stato (non usiamo enum type, usiamo solo questo tipo di evento)
	
	private int t;
	private Country country;
	private int n; //numero persone arrivate al tempo t nello stato
	
	public Evento(int t, Country country, int n) {
		super();
		this.t = t;
		this.country = country;
		this.n = n;
	}

	public int getT() {
		return t;
	}

	public void setT(int t) {
		this.t = t;
	}

	public Country getCountry() {
		return country;
	}

	public void setCountry(Country country) {
		this.country = country;
	}

	public int getN() {
		return n;
	}

	public void setN(int n) {
		this.n = n;
	}

	@Override
	public int compareTo(Evento o) {
		return this.t-o.t;  //se this è > ritorniamo un val>0, se è < un val < di 0, altrimenti se sono = torniamo 0
	}
}
