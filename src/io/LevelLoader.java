package io;

import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;

import co.uk.cbaker.eu.game.Config;
import co.uk.cbaker.eu.game.entity.EntityManager;
import co.uk.cbaker.eu.game.model.FloorCell;
import co.uk.cbaker.eu.game.model.Quad;

/**
 * LevelLoader.java
 *
 * @author 	Chris B
 * @date	1 Feb 2013
 * @version	1.0
 */
public class LevelLoader {

	// given a file return the entire level encapsulated as a class
	
	// the class contains all the structure necessary to run the simulation
	
	public static FloorCell[][] load(String filename, World world, EntityManager em) {
		FloorCell[][] floor = new FloorCell[1][1];
        Document dom;
        // Make an  instance of the DocumentBuilderFactory
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        try {
            // use the factory to take an instance of the document builder
            DocumentBuilder db = dbf.newDocumentBuilder();
            // parse using the builder to get the DOM mapping of the    
            // XML file
            dom = db.parse(filename);

            // Get the width and height of the floor
            Element doc = dom.getDocumentElement();
            int width = Integer.parseInt(doc.getAttribute("width"));
            int height = Integer.parseInt(doc.getAttribute("height"));
            
            // create the floor
            floor = new FloorCell[width][height];
            
            // get all the cell nodes
            NodeList cellNodes = doc.getElementsByTagName("cell");
            
            for(int i = 0; i < cellNodes.getLength(); i+=1) {
            	// with the current cell
            	Node cellNode = cellNodes.item(i);
            	
            	// get the type, x and y attributes
            	Element cellEle = (Element) cellNode;
            	int type = Integer.parseInt(cellEle.getAttribute("type"));
            	int x = Integer.parseInt(cellEle.getAttribute("x"));
            	int y = Integer.parseInt(cellEle.getAttribute("y"));
            	
            	// if this is a wall then place a wall in the physics simulation
            	if(type == Config.BLOCKED_CELL) {
            		addPhysicsWall(world, em, x, y);
            	}
            	
            	// create a new FloorCell
            	FloorCell floorCell = new FloorCell(x, y, type);
            	            	
            	// get the quads in the cell
            	NodeList quadNodes = cellEle.getElementsByTagName("quad");
            	
            	// with the quads            	
            	for(int j = 0; j < quadNodes.getLength(); j+=1) {
            		            		
            		// get the current quad
            		Node quadNode = quadNodes.item(j);
            		            		
            		// create a quad and texture
            		Quad quad = null;
            		int texture = 0;
            		            		
            		if(quadNode.hasChildNodes()) {
            			
            			Element quadEle = (Element) quadNode;
            			  			
	            		// get the first vertices element | This would be used if more than one set of vertices per quad
	                	// Node vertexNode = quadEle.getElementsByTagName("vertices").item(0);
	            		               	
	                	// we need a float array for the vertices
	                	float[] vertices = new float[24];	                	
	                	
	                	// counter for which vertex property we are adding
	                	int v = 0;	                	
	                	
	                	NodeList vertexChildList;
	                	
	                	// add the bottom left 	                		                		
	                	vertexChildList = quadEle.getElementsByTagName("bottomleft").item(0).getChildNodes();
	                	for(int k = 0; k < vertexChildList.getLength(); k += 1) {
	                		
	                		// get the child at k
	                		Node childNode = vertexChildList.item(k);
	                		
	                		// if the child is an element node (not #text)
	                		if(childNode.getNodeType() == Node.ELEMENT_NODE) {
	                			if(v < vertices.length) {
	                				float value = Float.parseFloat(childNode.getFirstChild().getNodeValue());
	                				// multiply the vertex coordinates by the scale value
	                				vertices[v] = (k < 6) ? value * Config.SCALE : value;
	                			}
	                			v += 1;
	                		}
	                	}	                	
	                	
	                	// add the bottom left 	                		                		
	                	vertexChildList = quadEle.getElementsByTagName("topleft").item(0).getChildNodes();
	                	for(int k = 0; k < vertexChildList.getLength(); k += 1) {
	                		
	                		// get the child at k
	                		Node childNode = vertexChildList.item(k);
	                		
	                		// if the child is an element node (not #text)
	                		if(childNode.getNodeType() == Node.ELEMENT_NODE) {
	                			if(v < vertices.length) {
	                				float value = Float.parseFloat(childNode.getFirstChild().getNodeValue());
	                				// multiply the vertex coordinates by the scale value
	                				vertices[v] = (k < 6) ? value * Config.SCALE : value;	                				
	                			}
	                			v += 1;
	                		}	                			                		
	                	}
	                	
	                	// add the bottom left 	                		                		
	                	vertexChildList = quadEle.getElementsByTagName("bottomright").item(0).getChildNodes();
	                	for(int k = 0; k < vertexChildList.getLength(); k += 1) {
	                		
	                		// get the child at k
	                		Node childNode = vertexChildList.item(k);
	                		
	                		// if the child is an element node (not #text)
	                		if(childNode.getNodeType() == Node.ELEMENT_NODE) {
	                			if(v < vertices.length) {
	                				float value = Float.parseFloat(childNode.getFirstChild().getNodeValue());
	                				// multiply the vertex coordinates by the scale value
	                				vertices[v] = (k < 6) ? value * Config.SCALE : value;
	                			}
	                			v += 1;
	                		}	                			                		
	                	}
	                	
	                	// add the bottom left 	                		                		
	                	vertexChildList = quadEle.getElementsByTagName("topright").item(0).getChildNodes();
	                	for(int k = 0; k < vertexChildList.getLength(); k += 1) {
	                		
	                		// get the child at k
	                		Node childNode = vertexChildList.item(k);
	                		
	                		// if the child is an element node (not #text)
	                		if(childNode.getNodeType() == Node.ELEMENT_NODE) {
	                			if(v < vertices.length) {
	                				float value = Float.parseFloat(childNode.getFirstChild().getNodeValue());
	                				// multiply the vertex coordinates by the scale value
	                				vertices[v] = (k < 6) ? value * Config.SCALE : value;
	                			}
	                			v += 1;
	                		}	                			                		
	                	}
	                	  	
	                	
	                	// get the texture	                	
            			Node textureNode = quadEle.getElementsByTagName("texture").item(0);
	                	texture = Integer.parseInt(textureNode.getFirstChild().getNodeValue());
	                		                	
	                	// create a quad and add it to the cell
	                	quad = new Quad(vertices);
	                	
            		}
            		
            		// add the quad to the cell
                	floorCell.setQuad(j, quad);
                	
                	// set the texture
                	floorCell.setTexture(j, texture);
            		
            	}            	
            	
            	// add the new cell to the Floor
            	floor[x][y] = floorCell;
            	
            }            

        } catch (ParserConfigurationException pce) {
            System.out.println(pce.getMessage());
        } catch (SAXException se) {
            System.out.println(se.getMessage());
        } catch (IOException ioe) {
            System.err.println(ioe.getMessage());
        }
        
        floor.toString();
        
        return floor;
        
    }	
	
	public static void addPhysicsWall(World world, EntityManager em, float x, float y) {
		PolygonShape groundPoly = new PolygonShape();
		groundPoly.setAsBox(2.5f, 2.5f);

		// next we create the body for the ground platform. It's
		// simply a static body.
		BodyDef groundBodyDef = new BodyDef();
		groundBodyDef.type = BodyType.StaticBody;
		groundBodyDef.position.x = (x * 5) + 2.5f;//x +0.5f;//;
		groundBodyDef.position.y = (y * 5) + 2.5f;//y +0.5f;//(y * 2) - 1;
		Body groundBody = world.createBody(groundBodyDef);
		
		// finally we add a fixture to the body using the polygon
		// defined above. Note that we have to dispose PolygonShapes
		// and CircleShapes once they are no longer used. This is the
		// only time you have to care explicitly for memory management.
		FixtureDef fixtureDef = new FixtureDef();
		fixtureDef.shape = groundPoly;
		fixtureDef.filter.groupIndex = 0;
		groundBody.createFixture(fixtureDef);
		groundPoly.dispose();
	}
	
}