package org.insa.graphs.algorithm.shortestpath;

import java.util.*;
import org.insa.graphs.model.*;
import org.insa.graphs.algorithm.utils.BinaryHeap;
import org.insa.graphs.algorithm.AbstractSolution.Status;



public class DijkstraAlgorithm extends ShortestPathAlgorithm {

    public DijkstraAlgorithm(ShortestPathData data) {
        super(data);
    }
    
	protected Label createLabel(ShortestPathData data, Arc father, Label previous) {
		return new Label(father.getDestination() , false, previous.getCost()+ data.getCost(father), father);	
	}

	protected Label createLabelOrigin(ShortestPathData data) {
		return new Label(data.getOrigin(),false, 0, null);
	}
	
	protected void insert (BinaryHeap <Label> ByHe,Label[] labels, Node point, boolean mark, double cost, Arc father, ShortestPathData data){
		Label L = new Label (point, mark, cost, father);
		ByHe.insert(L) ;
		labels[point.getId()] = L;
		notifyNodeReached(L.getCurrent());		
	}

    @Override
    protected ShortestPathSolution doRun() {
        final ShortestPathData data = getInputData();

		Graph graph = data.getGraph();

		BinaryHeap<Label> tas = new BinaryHeap<Label>();
		Label labels[] = new Label[graph.getNodes().size()];
		Arrays.fill(labels, null);
		
		insert(tas,labels,data.getOrigin(),true, 0, null,data);
		Label minimum, successeur;
				
		while(!tas.isEmpty() && 
			(labels[data.getDestination().getId()] == null || !labels[data.getDestination().getId()].isMarked())) 
		{
			
			minimum = tas.findMin();
			//		tas.insert(successeur);
			if(minimum.getCurrent() == data.getOrigin()) {
				notifyOriginProcessed(data.getOrigin());
			}
			
			if(minimum.getCurrent() == data.getDestination()) {
				notifyDestinationReached(data.getDestination());
			}
			

			try {
				tas.remove(minimum); //supprime le min
			}catch(Exception e) {
				System.out.println("exception détectée");
			}
			
			minimum.setMark();
			notifyNodeMarked(minimum.getCurrent());
			
			
			for(Arc arc : minimum.getCurrent().getSuccessors()){				
				if(!data.isAllowed(arc)) {
					continue;
				}
							
				successeur = labels[arc.getDestination().getId()];
				if(successeur == null) { //créer le successeur s'il est nul
					successeur = this.createLabel(data, arc, minimum);
					labels[arc.getDestination().getId()] = successeur;
					insert(tas,labels,arc.getDestination(), false, data.getCost(arc)+minimum.getCost(), arc, data);
					notifyNodeReached(successeur.getCurrent());
				
				}else{ //l'optimiser s'il existe
					if(successeur.getCost() > minimum.getCost() + data.getCost(arc)) {
						successeur.setCost(minimum.getCost() + data.getCost(arc));
						successeur.setFather(arc);
					}					
				}				
			}
		}
		

		ShortestPathSolution solution = null;
		if(labels[data.getDestination().getId()] != null && labels[data.getDestination().getId()].isMarked()){
			ArrayList<Arc> arcsSolution = new ArrayList<>();
			Arc arc = labels[data.getDestination().getId()].getFather();
			while(arc != null) { //on remonte
				arcsSolution.add(arc);
				arc = labels[arc.getOrigin().getId()].getFather();
			}
			Collections.reverse(arcsSolution);
			solution = new ShortestPathSolution(data, Status.OPTIMAL, new Path(graph, arcsSolution));
		

		}else {
			solution = new ShortestPathSolution(data, Status.INFEASIBLE);
		}
		return solution;
	}

}
