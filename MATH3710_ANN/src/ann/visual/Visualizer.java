package ann.visual;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Paint;
import java.awt.Point;
import java.awt.Stroke;
import java.awt.Toolkit;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.Hashtable;

import javax.swing.JFrame;

import org.apache.commons.collections15.Transformer;

import ann.fixed.Network;
import ann.fixed.WeightSet;
import edu.uci.ics.jung.algorithms.layout.ISOMLayout;
import edu.uci.ics.jung.algorithms.layout.Layout;
import edu.uci.ics.jung.algorithms.layout.SpringLayout;
import edu.uci.ics.jung.algorithms.layout.StaticLayout;
import edu.uci.ics.jung.graph.Graph;
import edu.uci.ics.jung.graph.SparseMultigraph;
import edu.uci.ics.jung.graph.util.EdgeType;
import edu.uci.ics.jung.visualization.BasicVisualizationServer;
import edu.uci.ics.jung.visualization.decorators.ToStringLabeller;
import edu.uci.ics.jung.visualization.renderers.Renderer.VertexLabel.Position;


public class Visualizer {
	private Network _network;
	private Graph<String, String> _graph;

	public Visualizer(Network n) {
		_network = n;
		_graph = new SparseMultigraph<String, String>();
		
		// add vertices and edges

		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		screenSize.setSize(screenSize.getWidth()-100, screenSize.getHeight()-100);
		
		int inputCount = _network.getInputCount();
		int hiddenCount = _network.getHiddenCount();
		int outputCount = _network.getOutputCount();
		
		
		
		final int scaleFactorInput = Math.floorDiv(screenSize.height,inputCount);
		final int scaleFactorHidden = Math.floorDiv(screenSize.height,hiddenCount);
		final int scaleFactorOutput = Math.floorDiv(screenSize.height,outputCount);
		
		
		// add all input vertices
		for (int i=0; i<inputCount; i++) {
			_graph.addVertex("i"+i);
		}
		
		// add all hidden vertices
		for (int i=0; i<hiddenCount; i++) {
			_graph.addVertex("h"+i);
		}
		
		// add all output vertices
		for (int i=0; i<outputCount; i++) {
			_graph.addVertex("o"+i);
		}
		
		// add all input-hidden edges
		for (int i=0; i<inputCount; i++) {
			for (int j=1; j<hiddenCount; j++) {
				double weight = _network.getWeights().getWeight(WeightSet.INPUT_LAYER, i, j);
				_graph.addEdge(weight+"","i"+i,"h"+j,EdgeType.DIRECTED);
			}
		}
		
		// add all hidden-output edges
		for (int i=0; i<hiddenCount; i++) {
			for (int j=0; j<outputCount; j++) {
				double weight = _network.getWeights().getWeight(WeightSet.HIDDEN_LAYER, i, j);
				_graph.addEdge(weight+"","h"+i,"o"+j,EdgeType.DIRECTED);
			}
		}		
	
		// vertex location transformer
		Transformer<String, Point2D> locationTransformer = new Transformer<String, Point2D>() {
			public Point2D transform(String v) {
				Point2D p2d = null;
				int neuronIndex = Integer.parseInt(v.substring(1));
				if (v.contains("i")) {
					p2d = new Point(Math.toIntExact(Math.round(screenSize.getWidth()/4)) , Math.toIntExact(Math.round(screenSize.getHeight()/(inputCount+1))*(neuronIndex+1)));
					//System.out.println("i"+neuronIndex+": "+p2d);
				}
				else if (v.contains("h")) {
					p2d = new Point(Math.toIntExact(Math.round(screenSize.getWidth()/4)*2) , Math.toIntExact(Math.round(screenSize.getHeight()/(hiddenCount+1))*(neuronIndex+1)));
					//System.out.println("h"+neuronIndex+": "+p2d);
				}
				else {
					p2d = new Point(Math.toIntExact(Math.round(screenSize.getWidth()/4)*3) , Math.toIntExact(Math.round(screenSize.getHeight()/(outputCount+1))*(neuronIndex+1)));
					//System.out.println("o"+neuronIndex+": "+p2d);
				}
				
				return p2d;
			}
		};
		// set up vertex label transformer
		Transformer<String,String> vertexLabelTransformer = new Transformer<String,String>() {
			public String transform(String v) {
				return new String(v);
			}
		};
		// Setup a new vertex to paint transformer...
		Transformer<String,Paint> vertexPaint = new Transformer<String,Paint>() {
			public Paint transform(String v) {
				if (v.contains("i")) {
					return new Color(0.0f,0.0f,1.0f,0.3f);
				}
				else if (v.contains("h")) {
					return new Color(1.0f,1.0f,0.0f,0.3f);
				}
				else if (v.contains("o")) {
					return new Color(0.0f,1.0f,0.0f,0.3f);
				}
				else {
					return Color.WHITE;
				}
			}
		};
		
		// Set up a new edge stroke Transformer
		float dash[] = {0.1f};
		final Stroke pathStroke = new BasicStroke(0.2f, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER, 10.0f, dash, 0.0f);
		final Stroke solutionStroke = new BasicStroke(2f);
		Transformer<String, Stroke> edgeStrokeTransformer = new Transformer<String, Stroke>() {
			public Stroke transform(String p) {
				return pathStroke;
			}
		};
/*		// set up edge label transformer
		Transformer<TSP.Path,String> edgeLabelTransformer = new Transformer<TSP.Path,String>() {
			public String transform(TSP.Path p) {
				if (p.isSolutionPath()) {
					return new String(p.getPathLength()+"");
				}
				else {
					return new String(p.getCityA().getId()+"<->"+p.getCityB().getId()+" ("+p.getPathLength()+")");
				}
				
			}
		};
*/
		
		Layout<String, String> layout = new StaticLayout<String, String>(_graph);
		layout.setInitializer(locationTransformer);
		layout.setSize(Toolkit.getDefaultToolkit().getScreenSize());
		BasicVisualizationServer<String, String> vv = new BasicVisualizationServer<String, String>(layout);
		vv.setPreferredSize(Toolkit.getDefaultToolkit().getScreenSize());

		vv.getRenderContext().setVertexFillPaintTransformer(vertexPaint);
		vv.getRenderContext().setEdgeStrokeTransformer(edgeStrokeTransformer);
		vv.getRenderContext().setVertexLabelTransformer(vertexLabelTransformer);
		//vv.getRenderContext().setEdgeLabelTransformer(edgeLabelTransformer);
		vv.getRenderContext().setLabelOffset(20);
		vv.getRenderer().getVertexLabelRenderer().setPosition(Position.CNTR);
	
/*
		Layout<String, String> layout = new ISOMLayout<String, String>(_graph);
		layout.setSize(Toolkit.getDefaultToolkit().getScreenSize()); // sets the initial size of the space
		// The BasicVisualizationServer<V,E> is parameterized by the edge types
		BasicVisualizationServer<String, String> vv = new BasicVisualizationServer<String, String>(layout);
		vv.setPreferredSize(Toolkit.getDefaultToolkit().getScreenSize()); //Sets the viewing area size
*/	
		

		JFrame frame = new JFrame("Network Visualization");
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.getContentPane().add(vv);
		frame.pack();
		frame.setVisible(true); 
		
		
		
	}

	
	
	
	
	
	
	
	
}