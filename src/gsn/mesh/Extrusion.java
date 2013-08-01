/**
 * ##library.name##
 * ##library.sentence##
 * ##library.url##
 *
 * Copyright ##copyright## ##author##
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 * 
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 * 
 * You should have received a copy of the GNU Lesser General
 * Public License along with this library; if not, write to the
 * Free Software Foundation, Inc., 59 Temple Place, Suite 330,
 * Boston, MA  02111-1307  USA
 * 
 * @author      ##author##
 * @modified    ##date##
 * @version     ##library.prettyVersion## (##library.version##)
 */

package gsn.mesh;


import java.util.ArrayList;

public class Extrusion
{
	Shape shape;
	Shape path;
	Vector vector;
	int capType;

	public static final int FLAT = 0x00;
	public static final int EARCLIP = 0x01;

	public Extrusion()
	{
		this.shape = null;
		this.path = null;
		// By default we just extrude 100 units in the z-axis
		this.vector = new Vector(0.0f, 0.0f, 100.0f);
		this.capType = FLAT;
	}	

	/**
	* Sets the shape to be extruded
	*/
	public void setShape(Shape s)
	{
		this.shape = s;
	}

	public void setCap(int captype)
	{
		this.capType = captype;
	}

	/**
	* Set a vector to extrude by, if you don't need a complicated path
	*/
	public void setVector(float x, float y, float z)
	{
		this.setVector(new Vector(x, y, z));
	}

	public void setVector(Vector v)
	{
		this.vector = v;
		this.path = null;
	}

	/**
	* Set the path to extrude by
	*/
	public void setPath(Shape p)
	{
		this.path = p;
	}

	/**
	* Generates the mesh from the extrusion
	*
	*	@param div Number of divisions, more than 0, along vector, or between points in path, default is 1
	*	@param	curve Interpolate a long a curve if applicable
	*	@param	caps Add caps to ends of extrusion 0 - no caps, 1 - cap start, 2 - cap end, 3 - cap both
	*	@return Mesh object
	*/
	public Mesh getMesh(int div, int curve, int caps)
	{
		if(this.shape == null)
			return null;

		if((this.vector == null) && (this.path == null))
			return null;

		if(curve < 0)
		{
			curve = 0;
		}

		if(curve > 1)
		{
			curve = 1;
		}

		Mesh out = new Mesh();

		if(div < 1)
			div = 1;

		// Handle vertices first
		if(this.path == null)
		{
			// This is for just extruding a long a vector. No need to care about curves
			// since that won't affect a linear extrusion
			for(Vector p: this.shape.getVertices())
			{
				out.addPoint(p);
			}
			float delta = 1.0f / (float)div;
			for(int i = 1; i < div; i++)
			{
				for(Vector p: this.shape.getVertices())
				{
					out.addPoint(
						Vector.add(p, Vector.mult(this.vector, (i*delta)))
						);
				}
			}

			for(Vector p: this.shape.getVertices())
			{
				out.addPoint(Vector.add(p, this.vector));
			}
		}
		else // Handle subdivision and curvature here
		{
			System.out.println("Subdivision and curvature not implemented!");
			float delta = 1.0f / (float)div; // Division delta
			System.out.println("Division, delta: " + div + ", " + delta);

			if(curve == 0) // No smoothing
			{
				Vector v0; // Current vector
				Vector v1; // Next vector

				// Loop through all path points, except for last(?)
				for(int si = 0; si < this.path.size()-1; si++)
				{
					v0 = this.path.getVertex(si);
					v1 = this.path.getVertex(si+1);

					Vector deltap = Vector.sub(v1, v0);

					for(int i = 0; i < div; i++)
					{
						for(int pi = 0; pi < this.shape.size(); pi++)
						{
							if(i == 0) // This is just current shape, so add it directly
							{
								out.addVertex(Vector.add(this.shape.getVertex(pi), v0));
							}
							else // Interpolate
							{
								out.addVertex(
									Vector.add(
										this.shape.getVertex(pi), Vector.add(
											v0, Vector.mult(
												deltap, (i*delta)
												)
											)
										)
									);
							}
						}
					}
				}
				// Add last shape			
				for(int pi = 0; pi < this.shape.size(); pi++)
				{
					out.addVertex(Vector.add(this.shape.getVertex(pi), this.path.getVertex(this.path.size()-1)));
				}

			}
			else if(curve == 1)
			{
				Vector[] vectors = new Vector[4];
				Vector v0 = this.path.getVertex(0);

				// Go through all path points, but last
				for(int si = 0; si < this.path.size()-1; si++)
				{
					// Get the four shapes we consider for each vertical stage
					if(si < 1)
					{
						vectors[0] = Vector.sub(this.path.getVertex(0), this.path.getVertex(0));
					}
					else
					{
						vectors[0] = Vector.sub(this.path.getVertex(si-1), this.path.getVertex(0));
					}

					vectors[1] = Vector.sub(this.path.getVertex(si), this.path.getVertex(0));
					vectors[2] = Vector.sub(this.path.getVertex(si+1), this.path.getVertex(0));

					if((si+2) >= this.path.size())
					{
						vectors[3] = Vector.sub(this.path.getVertex(si+1), this.path.getVertex(0));
					}
					else
					{
						vectors[3] = Vector.sub(this.path.getVertex(si+2), this.path.getVertex(0));
					}


					for(int i = 0; i < div; i++)
					{
						Vector deltap = Curves.catmullRomSpline(vectors[0], vectors[1], vectors[2], vectors[3], delta*i);
	
						for(int pi = 0; pi < this.shape.size(); pi++)
						{
							out.addVertex(
								Vector.add(
									this.shape.getVertex(pi), Vector.add(
										v0, deltap										
										)
									)
								);
						}
					}
				}
				// Add last shape
				for(int pi = 0; pi < this.shape.size(); pi++)
				{
					out.addVertex(Vector.add(this.shape.getVertex(pi), this.path.getVertex(this.path.size()-1)));
				}

			}

		}


		//System.out.println("Number of vertices: " + out.getVertexCount());



		// Make faces!
		int pathpoints = 1;
		if(this.path != null) pathpoints = (this.path.size()-1);

		for(int si = 0; si < div*pathpoints; si++)
		{
			for(int pi = 0; pi < this.shape.size(); pi++)
			{
				out.addFace(new Face(
					out.getPoint(pi + si*this.shape.size()),
					out.getPoint(((pi+1)%this.shape.size()) + si * this.shape.size()),
					out.getPoint(((pi+1)%this.shape.size()) + (si+1) * this.shape.size()),
					out.getPoint(pi + (si+1) * this.shape.size())
					));
				
			}
		}
		if(caps != 0)
		{
			if(this.capType == EARCLIP)
			{
				if((caps&0x1) > 0)
				{
					Shape s = new Shape();
					for(int pi = 0; pi < this.shape.size(); pi++)
					{
						s.addVertex(out.getPoint(pi));
					}
					out.addFaces(Shape.earClip(s.getVertices()));
				}
				if((caps&0x2) > 0)
				{
					Shape s = new Shape();
					for(int pi = 0; pi < this.shape.size(); pi++)
					{
						s.addVertex(out.getPoint(pi + (out.getVertexCount() - this.shape.size())));
					}

					out.addFaces(Shape.earClip(s.getVertices()));
				}
			}
			else if(this.capType == FLAT)
			{
				if((caps&0x1) > 0)
				{
					Face f = new Face();
					for(int pi = 0; pi < this.shape.size(); pi++)
					{
						f.addVertex(out.getPoint(pi));
					}
					out.addFace(f);
				}
				if((caps&0x2) > 0)
				{
					Face f = new Face();
					for(int pi = 0; pi < this.shape.size(); pi++)
					{
						f.addVertex(out.getPoint(pi + (out.getVertexCount() - this.shape.size())));
					}

					out.addFace(f);
				}

			}
		}
		
		out.update();


		return out;

	}

}