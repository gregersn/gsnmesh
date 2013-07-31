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
	ArrayList<Shape> shapes;
	int shapeSize;

	int udivision;
	int vdivision;

	public Extrusion()
	{
		this.shapes = new ArrayList<Shape>();
		this.udivision = 1;
		this.vdivision = 1;
	}	

	public void addShape(Shape s)
	{
		if(this.shapes.size() == 0)
		{
			this.shapeSize = s.getVertexCount();
		}

		if(s.getVertexCount() != this.shapeSize)
		{
			System.out.println("Shape is wrong size");
			return;
		}

		this.shapes.add(s);
		System.out.println("Number of shapes: " + this.shapes.size());
		return;
	}

	public Mesh getMesh(int div, int curve, int caps)
	{
		if(this.shapes.size() < 2)
		{
			System.out.println("Can't make mesh from less than two shapes");
			return null;
		}

		if(curve < 0)
		{
			curve = 0;
		}

		if(curve > 1)
		{
			curve = 1;
		}

		if(this.shapes.size() < 3) // Can't curve from two shapes, need at least 4
		{
			curve = 0;
		}


		Mesh out = new Mesh();

		// Handle vertices first

		if(div < 2) // No subdivision, keep it simple!
		{
			for(Shape s: this.shapes)
			{
				for(Vector p: s.getVertices())
				{
					out.addPoint(p);
				}
			}
		}
		else // Handle subdivision and curvature here
		{
			System.out.println("Subdivision and curvature not implemented!");
			float delta = 1.0f / (float)div; // Division delta
			System.out.println("Division, delta: " + div + ", " + delta);

			if(curve == 0) // No smoothing
			{
				Shape s; // Current shape
				Shape ns; // Next shape

				// Loop through all shapes except for last
				for(int si = 0; si < this.shapes.size()-1; si++)
				{
					s = this.shapes.get(si);
					ns = this.shapes.get(si+1);

					for(int i = 0; i < div; i++)
					{
						for(int pi = 0; pi < this.shapeSize; pi++)
						{
							if(i == 0) // This is just current shape, so add it directly
							{
								out.vertices.add(s.getVertex(pi));
							}
							else // Interpolate
							{
								Vector p0 = s.getVertex(pi);
								Vector p1 = ns.getVertex(pi);

								Vector deltap = Vector.sub(p1, p0);
								out.addVertex(Vector.add(p0, Vector.mult(deltap, (i*delta))));
							}
						}
					}
				}
				// Add last shape
				s = this.shapes.get(this.shapes.size()-1);
				for(int pi = 0; pi < this.shapeSize; pi++)
				{
					out.addVertex(s.getVertex(pi));
				}

			}
			else if(curve == 1)
			{
				Shape[] shapes = new Shape[4];

				// Go through all shapes but last
				for(int si = 0; si < this.shapes.size()-1; si++)
				{
					// Get the four shapes we consider for each vertical stage
					if(si < 1)
					{
						shapes[0] = this.shapes.get(0);
					}
					else
					{
						shapes[0] = this.shapes.get(si-1);
					}

					shapes[1] = this.shapes.get(si);
					shapes[2] = this.shapes.get(si+1);

					if((si+2) >= this.shapes.size())
					{
						shapes[3] = this.shapes.get(si+1);
					}
					else
					{
						shapes[3] = this.shapes.get(si+2);
					}

					Vector[] points = new Vector[4];

					for(int i = 0; i < div; i++)
					{
						for(int pi = 0; pi < this.shapeSize; pi++)
						{
							points[0] = shapes[0].getVertex(pi);
							points[1] = shapes[1].getVertex(pi);
							points[2] = shapes[2].getVertex(pi);
							points[3] = shapes[3].getVertex(pi);

							out.addVertex(Curves.catmullRomSpline(points[0], points[1], points[2], points[3], delta*i));
						}
					}
				}
				// Add last shape
				Shape s = this.shapes.get(this.shapes.size()-1);
				for(int pi = 0; pi < this.shapeSize; pi++)
				{
					out.addVertex(s.getVertex(pi));
				}

			}

		}

		System.out.println("Number of vertices: " + out.getVertexCount());

		// Make faces!

		for(int si = 0; si < (this.shapes.size()-1)*div; si++)
		{
			for(int pi = 0; pi < this.shapeSize; pi++)
			{
				out.addFace(new Face(
					out.getPoint(pi + si*this.shapeSize),
					out.getPoint(((pi+1)%this.shapeSize) + si * this.shapeSize),
					out.getPoint(((pi+1)%this.shapeSize) + (si+1) * this.shapeSize),
					out.getPoint(pi + (si+1) * this.shapeSize)
					));
			}
		}

		if(caps != 0)
		{
			if((caps&0x1) > 0)
			{
				//Face f = new Face();
				Shape s = new Shape();
				for(int pi = 0; pi < this.shapeSize; pi++)
				{
					s.addVertex(out.getPoint(pi));
				}
				//out.addFace(f);
				out.addFaces(Shape.earClip(s.getVertices()));
			}
			if((caps&0x2) > 0)
			{
				//Face f = new Face();
				Shape s = new Shape();
				for(int pi = 0; pi < this.shapeSize; pi++)
				{
					s.addVertex(out.getPoint(pi + (out.getVertexCount() - this.shapeSize)));
				}

				//out.addFace(f);
				out.addFaces(Shape.earClip(s.getVertices()));
			}
		}

		out.update();


		return out;

	}

}