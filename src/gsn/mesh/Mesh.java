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

public class Mesh extends DummyObject
{
	ArrayList<Face> faces;

	private boolean faceUniform;	// Does all faces have the same number of vertices
	private int faceSize;			// What is the vertex-count if all faces are the same

	public Mesh()
	{
		this.vertices = new ArrayList<Vector>();
		this.faces = new ArrayList<Face>();

		this.faceUniform = false;
		this.faceSize = 3;
	}

	public Mesh(Shape s)
	{
		this.vertices = (ArrayList<Vector>)s.getVertices().clone();
		this.faces = Shape.earClip(this.vertices);
		this.faceUniform = true;
		this.faceSize = 3;
	}

	public ArrayList<Face> getFaces()
	{
		return this.faces;
	}

	public void addFace(Face f)
	{
		this.faces.add(f);
	}

	public void addFaces(ArrayList<Face> faces)
	{
		for(Face f: faces)
		{
			this.faces.add(f);
		}
	}

	public void update()
	{
		/*this.faceUniform = true;
		this.faceSize = this.faces.get(0).size();
		for(Face f: this.faces)
		{
			if(f.size() != this.faceSize)
			{
				this.faceUniform = false;
				return;
			}
		}*/
		this.faceUniform = false;
	}

	// Generate a cube
	static public Mesh cube(float size)
	{
		Mesh out = new Mesh();

		out.faceUniform = true;
		out.faceSize = 4;

		out.vertices.add(new Vector(-size/2.0f,  size/2.0f, -size/2.0f));
		out.vertices.add(new Vector(-size/2.0f,  size/2.0f,  size/2.0f));
		out.vertices.add(new Vector( size/2.0f,  size/2.0f,  size/2.0f));
		out.vertices.add(new Vector( size/2.0f,  size/2.0f, -size/2.0f));
		out.vertices.add(new Vector(-size/2.0f, -size/2.0f, -size/2.0f));
		out.vertices.add(new Vector( size/2.0f, -size/2.0f, -size/2.0f));
		out.vertices.add(new Vector( size/2.0f, -size/2.0f,  size/2.0f));
		out.vertices.add(new Vector(-size/2.0f, -size/2.0f,  size/2.0f));
		out.faces.add(new Face(out.vertices.get(0), out.vertices.get(1), out.vertices.get(2), out.vertices.get(3)));
		out.faces.add(new Face(out.vertices.get(4), out.vertices.get(5), out.vertices.get(6), out.vertices.get(7)));
		out.faces.add(new Face(out.vertices.get(0), out.vertices.get(4), out.vertices.get(7), out.vertices.get(1)));
		out.faces.add(new Face(out.vertices.get(2), out.vertices.get(6), out.vertices.get(5), out.vertices.get(3)));
		out.faces.add(new Face(out.vertices.get(0), out.vertices.get(3), out.vertices.get(5), out.vertices.get(4)));
		out.faces.add(new Face(out.vertices.get(2), out.vertices.get(1), out.vertices.get(7), out.vertices.get(6)));

		return out;
	}

}