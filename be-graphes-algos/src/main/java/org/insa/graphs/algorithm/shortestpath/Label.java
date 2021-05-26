package org.insa.graphs.algorithm.shortestpath;

import org.insa.graphs.model.*;

public class Label implements Comparable<Label>{
	private Node point;
	private boolean mark;
	private double cost;
	private Arc father;
	
	public Label(Node point, boolean mark, double cost, Arc father) {
		this.point = point;
		this.mark = mark;
		this.cost = cost;
		this.father = father;
	}
	
	public Node getCurrent() {
		return this.point;
	}
	public double getCost() {
		return this.cost;
	}
	public Arc getFather() {
		return this.father;
	}
	
	public void setMark() {
		this.mark = true;
	}
	
	public void setFather(Arc father) {
		this.father = father;
	}
	
	public void setCost(double cost) {
		this.cost = cost;
	}
	
	public boolean isMarked() {
		return this.mark;
	}
	
	public double getTotalCost() {
		return this.cost;
	}
	
	@Override
	public int compareTo(Label o) {
		return Double.compare(this.getTotalCost(), o.getTotalCost()); 
	}
}
