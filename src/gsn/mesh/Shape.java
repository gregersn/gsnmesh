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
import java.lang.Math;

public class Shape extends DummyObject
{
	public Shape()
	{
		this.vertices = new ArrayList<Vector>();
	}

	/** Functions for generating shapes */
	static Shape rect(float w, float h)
	{
		Shape out = new Shape();

		out.addPoint(new Vector(-w/2.0f, -h/2.0f));
		out.addPoint(new Vector(w/2.0f, -h/2.0f));
		out.addPoint(new Vector(w/2.0f, h/2.0f));
		out.addPoint(new Vector(-w/2.0f, h/2.0f));

		return out;
	}

	/*ArrayList<Vector> getVertices()
	{
		return this.vertices;
	}*/

	public static Shape circle(float x, float y, float z, float radius, int points)
	{
		Shape out = new Shape();
		float TWO_PI = (float)Math.PI*2.0f;

		float delta = TWO_PI/(float)points;
		for(int i = 0; i < points; i++)
		{
			out.addPoint(new Vector(x + radius * (float)Math.sin(i*delta), y + radius*(float)Math.cos(i*delta), z));
		}

		return out;
	}

	/**
	*	Triangulate/tesselate a shape by earclipping it
	*
	*/
	public static ArrayList<Face> earClip(ArrayList<Vector> s)
	{
		ArrayList<Face> faces = new ArrayList<Face>();
		ArrayList<Vector> shape = (ArrayList<Vector>)s.clone();

		if(shape.size() < 3) return null;

		while(shape.size() > 3)
		{
			for(int i = 0; i < shape.size(); i++)
			{
				boolean ear = true;
				Face tri = new Face();
				tri.addVertex(shape.get(i));
				tri.addVertex(shape.get((i+1)%shape.size()));
				tri.addVertex(shape.get((i+2)%shape.size()));

				for(int j = 3; j < shape.size(); j++)
				{
					if(tri.pointInside(shape.get(j%shape.size())))
						ear = false;
				}

				if(ear)
				{
					shape.remove((i+1)%shape.size());
					faces.add(tri);
					break;
				}
			}
		}

		Face tri = new Face();
		tri.addVertex(shape.get(0));
		tri.addVertex(shape.get(1));
		tri.addVertex(shape.get(2));

		faces.add(tri);

		return faces;
	}


}