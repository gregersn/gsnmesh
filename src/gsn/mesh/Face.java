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

public class Face
{
	ArrayList<Vector> vertices;

	public int size()
	{
		return this.vertices.size();
	}

	public Face()
	{
		this.vertices = new ArrayList<Vector>();
	}

	public Face(Vector... points)
	{
		this.vertices = new ArrayList<Vector>();
		for(int i = 0; i < points.length; i++)
		{
			this.vertices.add(points[i]);
		}
	}

	public boolean pointInside(Vector point)
	{
		if(this.size() != 3)
		{
			System.out.println("Only works on triangles");
			return false;
		}

		Vector v0 = Vector.sub(getVertex(2), getVertex(0));
		Vector v1 = Vector.sub(getVertex(1), getVertex(0));
		Vector v2 = Vector.sub(point, getVertex(0));

		float dot00 = v0.dot(v0);
		float dot01 = v0.dot(v1);
		float dot02 = v0.dot(v2);

		float dot11 = v1.dot(v1);
		float dot12 = v1.dot(v2);

		float invDenom = 1.0f / (dot00*dot11 - dot01*dot01);

		float u = (dot11 * dot02 - dot01 * dot12) * invDenom;
		float v = (dot00 * dot12 - dot01 * dot02) * invDenom;

		return (u >= 0) && (v >= 0) && (u + v < 1);

	}
	public ArrayList<Vector> getVertices()
	{
		return this.vertices;
	}
	public void addVertex(Vector p)
	{
		this.vertices.add(p);
	}

	public Vector getVertex(int i)
	{
		return this.vertices.get(i);
	}
}