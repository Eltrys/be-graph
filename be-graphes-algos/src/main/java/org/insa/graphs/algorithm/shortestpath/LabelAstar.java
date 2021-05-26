package org.insa.graphs.algorithm.shortestpath;

import org.insa.graphs.model.*;

public class LabelAstar extends Label {
	
	private double estimated_cost;
	
	public LabelAstar (Node point,boolean mark,double cost, Arc father, double estimated_cost) {
		super (point, mark, cost, father);
		this.estimated_cost = estimated_cost ;
	}
	
	public double getTotalCost(){
		return this.getCost()+this.estimated_cost;
	}
}