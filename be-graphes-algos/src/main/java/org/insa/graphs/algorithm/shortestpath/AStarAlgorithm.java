package org.insa.graphs.algorithm.shortestpath;

import org.insa.graphs.algorithm.AbstractInputData.Mode;
import org.insa.graphs.algorithm.utils.BinaryHeap;
import org.insa.graphs.model.Arc;
import org.insa.graphs.model.Node;

public class AStarAlgorithm extends DijkstraAlgorithm {

	@Override
	protected void insert (BinaryHeap <Label> ByHe,Label[] labels, Node point, boolean mark, double cost, Arc father, ShortestPathData data){
		Label L;
		double estimated_cost;
		if (data.getMode()==Mode.LENGTH) {
			estimated_cost=point.getPoint().distanceTo(data.getDestination().getPoint());
		}
		else 
		{
			double max_vit = Math.max(data.getMaximumSpeed(),data.getGraph().getGraphInformation().getMaximumSpeed());
			estimated_cost = point.getPoint().distanceTo(data.getDestination().getPoint())/max_vit;		
		}
		L = new LabelAstar (point, mark, cost, father, estimated_cost);
		ByHe.insert(L) ;
		labels[point.getId()] = L;
		notifyNodeReached(L.getCurrent());		
	}
	
    public AStarAlgorithm(ShortestPathData data) {
        super(data);
    }

}
